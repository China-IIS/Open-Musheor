#!/usr/bin/env node
// Download and verify the release's declared libraries using Node.js only.
// Existing files are verified and skipped; mismatches are never overwritten.
import { copyFile, mkdir, open, readFile, unlink } from 'node:fs/promises';
import { constants as fsConstants } from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import { createHash, randomBytes } from 'node:crypto';
import https from 'node:https';
import { readArchive } from './archive.mjs';

const ROOT = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '..');
const ALLOWED_VERSIONS = ['1.21.4', '1.21.11'];
const DEFAULT_VERSIONS = [...ALLOWED_VERSIONS];
const MAX_REDIRECTS = 5;
const MAX_DOWNLOAD_BYTES = 128 * 1024 * 1024;
const REQUEST_TIMEOUT_MS = 60_000;
// Maven Central's Java-WebSocket 1.5.4 hash is 4a7037bd...0c3657, not the original nested Fabric jar; keep the pinned original source below.
const JAVA_WEBSOCKET_SHA256 = '163970acb5211d84e9a072badad7c415bcf90ffb24d7dff777a67ae49962d174';
const JAVA_WEBSOCKET_URL = commit =>
  'https://raw.githubusercontent.com/China-IIS/Open-Musheor/' + commit +
  '/versions/{version}/libs/Java-WebSocket-1.5.4.jar';
const SOURCE_COMMIT = '995d7039add350bad5cbc43329128b012ff6e0f6';

function fail(message) {
  throw new Error(message);
}

function parseArguments(argv) {
  const versions = [];
  let dryRun = false;
  for (let i = 0; i < argv.length; i++) {
    const arg = argv[i];
    if (arg === '--dry-run') {
      dryRun = true;
    } else if (arg === '--version') {
      const version = argv[++i];
      if (!version || !ALLOWED_VERSIONS.includes(version)) fail('Unsupported or missing --version');
      if (versions.includes(version)) fail('Duplicate --version');
      versions.push(version);
    } else if (arg === '--help' || arg === '-h') {
      return { help: true, versions: DEFAULT_VERSIONS, dryRun };
    } else {
      fail('Unknown argument: ' + arg);
    }
  }
  return { help: false, versions: versions.length ? versions : DEFAULT_VERSIONS, dryRun };
}

function safeFileName(value) {
  if (typeof value !== 'string' || !value || value === '.' || value === '..') fail('Invalid dependency file name');
  if (value.includes('\\') || value.includes('/') || value.includes('\0') || path.isAbsolute(value) || path.basename(value) !== value) {
    fail('Unsafe dependency path: ' + value);
  }
  return value;
}

function targetPath(libs, file) {
  const name = safeFileName(file);
  const resolved = path.resolve(libs, name);
  const prefix = path.resolve(libs) + path.sep;
  if (!resolved.startsWith(prefix)) fail('Dependency path escapes libs directory');
  return resolved;
}

function splitDownloadUrl(raw) {
  if (typeof raw !== 'string' || !raw) fail('Missing dependency URL');
  const marker = raw.indexOf('!/');
  if (marker < 0) return { outerUrl: validateHttpsUrl(raw), archivePath: null };
  if (raw.indexOf('!/', marker + 2) >= 0) fail('Nested archive URL has multiple !/ components');
  const outer = raw.slice(0, marker);
  const archivePath = raw.slice(marker + 2);
  if (!archivePath || archivePath.startsWith('/') || archivePath.includes('\\') || archivePath.split('/').includes('..') || archivePath.includes('\0')) {
    fail('Unsafe nested archive path');
  }
  return { outerUrl: validateHttpsUrl(outer), archivePath };
}

function validateHttpsUrl(raw) {
  let parsed;
  try { parsed = new URL(raw); } catch { fail('Invalid dependency URL'); }
  if (parsed.protocol !== 'https:' || parsed.username || parsed.password) fail('Dependency URL must use HTTPS without credentials');
  return parsed.toString();
}

function fetchHttps(rawUrl, redirects = 0) {
  const url = validateHttpsUrl(rawUrl);
  if (redirects > MAX_REDIRECTS) return Promise.reject(new Error('Too many HTTPS redirects'));
  return new Promise((resolve, reject) => {
    let settled = false;
    const finish = (error, value) => {
      if (settled) return;
      settled = true;
      if (error) reject(error); else resolve(value);
    };
    let request;
    try {
      request = https.get(new URL(url), { headers: { 'user-agent': 'Open-Musheor dependency fetcher' } }, response => {
        const status = response.statusCode ?? 0;
        if (status >= 300 && status < 400) {
          const location = response.headers.location;
          response.resume();
          if (!location) return finish(new Error('HTTPS redirect has no location'));
          let next;
          try { next = validateHttpsUrl(new URL(location, url).toString()); }
          catch (error) { return finish(error); }
          fetchHttps(next, redirects + 1).then(value => finish(null, value), error => finish(error));
          return;
        }
        if (status < 200 || status >= 300) {
          response.resume();
          finish(new Error('HTTPS request failed with HTTP status ' + status));
          return;
        }
        const chunks = [];
        let total = 0;
        response.on('data', chunk => {
          total += chunk.length;
          if (total > MAX_DOWNLOAD_BYTES) {
            response.destroy();
            finish(new Error('Dependency exceeds download size limit'));
          } else {
            chunks.push(chunk);
          }
        });
        response.on('end', () => finish(null, Buffer.concat(chunks)));
        response.on('error', error => finish(error));
      });
      request.setTimeout(REQUEST_TIMEOUT_MS, () => {
        request.destroy(new Error('HTTPS request timed out'));
      });
      request.on('error', error => finish(error));
    } catch (error) {
      finish(error);
    }
  });
}

function hash(data, algorithm) {
  return createHash(algorithm).update(data).digest('hex');
}

function verifyBytes(data, entry, label) {
  if (entry.bytes !== undefined && (!Number.isSafeInteger(entry.bytes) || entry.bytes < 0 || data.length !== entry.bytes)) {
    fail(label + ': byte count mismatch');
  }
  let checked = false;
  for (const algorithm of ['sha256', 'sha512']) {
    if (entry[algorithm] !== undefined) {
      checked = true;
      if (typeof entry[algorithm] !== 'string' || !new RegExp('^[0-9a-f]{' + (algorithm === 'sha256' ? 64 : 128) + '}$').test(entry[algorithm].toLowerCase())) {
        fail(label + ': invalid ' + algorithm + ' digest');
      }
      if (hash(data, algorithm) !== entry[algorithm].toLowerCase()) fail(label + ': ' + algorithm + ' mismatch');
    }
  }
  if (!checked) fail(label + ': manifest has no supported digest');
}

async function readVerifiedExisting(filePath, entry, label) {
  try {
    const data = await readFile(filePath);
    verifyBytes(data, entry, label);
    return data;
  } catch (error) {
    if (error?.code === 'ENOENT') return null;
    throw error;
  }
}

async function installNew(filePath, data, label) {
  await mkdir(path.dirname(filePath), { recursive: true });
  const temp = filePath + '.tmp-' + process.pid + '-' + randomBytes(8).toString('hex');
  try {
    await (await open(temp, 'wx')).close();
    await import('node:fs/promises').then(fs => fs.writeFile(temp, data, { flag: 'w' }));
    try {
      await copyFile(temp, filePath, fsConstants.COPYFILE_EXCL);
    } catch (error) {
      if (error?.code === 'EEXIST') fail(label + ': destination appeared while downloading; refusing overwrite');
      throw error;
    }
  } finally {
    await unlink(temp).catch(() => {});
  }
}

function normaliseEntry(entry, manifestName) {
  if (!entry || typeof entry !== 'object' || Array.isArray(entry)) fail('Invalid entry in ' + manifestName);
  const file = safeFileName(entry.file);
  const { outerUrl, archivePath } = splitDownloadUrl(entry.url);
  if (entry.embeddedIn !== undefined && (typeof entry.embeddedIn !== 'string' || !entry.embeddedIn)) fail('Invalid embeddedIn for ' + file);
  return { ...entry, file, outerUrl, archivePath, manifestName };
}

async function loadManifest(filePath, version, name) {
  let value;
  try { value = JSON.parse(await readFile(filePath, 'utf8')); }
  catch (error) { if (error?.code === 'ENOENT') fail('Missing ' + name + ' for ' + version); throw error; }
  const entries = Array.isArray(value) ? value : [value];
  return entries.map(entry => normaliseEntry(entry, name));
}

async function fetchVersion(version, dryRun) {
  const versionRoot = path.join(ROOT, 'versions', version);
  const libs = path.join(versionRoot, 'libs');
  const manifests = ['compat-dependencies.json', 'baritone-provenance.json'];
  const entries = [];
  for (const manifest of manifests) entries.push(...await loadManifest(path.join(libs, manifest), version, manifest));
  const websocket = normaliseEntry({
    file: 'Java-WebSocket-1.5.4.jar',
    url: JAVA_WEBSOCKET_URL(SOURCE_COMMIT).replace('{version}', version),
    bytes: 139590,
    sha256: JAVA_WEBSOCKET_SHA256
  }, 'pinned Java-WebSocket source');
  entries.push(websocket);
  const byFile = new Map();
  for (const entry of entries) {
    if (byFile.has(entry.file)) fail(version + ': duplicate dependency file ' + entry.file);
    byFile.set(entry.file, entry);
  }
  const state = new Map();
  const ensure = async entry => {
    const previous = state.get(entry.file);
    if (previous === 'done') return;
    if (previous === 'active') fail(version + ': cyclic embedded dependency at ' + entry.file);
    state.set(entry.file, 'active');
    const destination = targetPath(libs, entry.file);
    let data;
    if (entry.archivePath !== null) {
      if (!entry.embeddedIn) fail(version + ': nested dependency lacks embeddedIn: ' + entry.file);
      const parent = byFile.get(entry.embeddedIn);
      if (!parent) fail(version + ': embeddedIn target not found: ' + entry.embeddedIn);
      await ensure(parent);
      const parentData = await readFile(targetPath(libs, parent.file));
      let archive;
      try { archive = readArchive(parentData); } catch (error) { fail(version + ': cannot read outer archive for ' + entry.file + ': ' + error.message); }
      data = archive.get(entry.archivePath);
      if (!data) fail(version + ': nested archive entry not found: ' + entry.file);
      verifyBytes(data, entry, version + '/' + entry.file);
    } else {
      const existing = await readVerifiedExisting(destination, entry, version + '/' + entry.file);
      if (existing) {
        console.log(version + ': SKIP ' + entry.file + ' (verified)');
        state.set(entry.file, 'done');
        return;
      }
      if (dryRun) {
        console.log(version + ': WOULD-DOWNLOAD ' + entry.file);
        state.set(entry.file, 'done');
        return;
      }
      data = await fetchHttps(entry.outerUrl);
      verifyBytes(data, entry, version + '/' + entry.file);
    }
    const existing = await readVerifiedExisting(destination, entry, version + '/' + entry.file);
    if (existing) {
      console.log(version + ': SKIP ' + entry.file + ' (verified)');
    } else if (dryRun) {
      console.log(version + ': WOULD-WRITE ' + entry.file + ' (verified source)');
    } else {
      await installNew(destination, data, version + '/' + entry.file);
      console.log(version + ': DOWNLOADED ' + entry.file);
    }
    state.set(entry.file, 'done');
  };
  for (const entry of entries) await ensure(entry);
  return { version, dependencies: entries.length, dryRun };
}

async function main() {
  const options = parseArguments(process.argv.slice(2));
  if (options.help) {
    console.log('Usage: node tools/fetch-dependencies.mjs [--version 1.21.4|1.21.11] [--dry-run]');
    return;
  }
  const reports = [];
  for (const version of options.versions) reports.push(await fetchVersion(version, options.dryRun));
  console.log(JSON.stringify({ ok: true, reports }));
}

try {
  await main();
} catch (error) {
  console.error('fetch-dependencies: ' + (error?.message || String(error)));
  process.exitCode = 1;
}
