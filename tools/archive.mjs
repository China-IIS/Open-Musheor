// Small read-only JAR/ZIP reader: Node.js standard library only; never extracts paths.
import { readFileSync } from 'node:fs';
import { inflateRawSync } from 'node:zlib';

const table = Array.from({ length: 256 }, (_, n) => {
  for (let i = 0; i < 8; i++) n = n & 1 ? 0xedb88320 ^ (n >>> 1) : n >>> 1;
  return n >>> 0;
});
function crc32(bytes) {
  let crc = 0xffffffff;
  for (const byte of bytes) crc = table[(crc ^ byte) & 255] ^ (crc >>> 8);
  return (crc ^ 0xffffffff) >>> 0;
}
export function readArchive(input) {
  const data = Buffer.isBuffer(input) ? input : readFileSync(input);
  let end = -1;
  for (let i = data.length - 22; i >= Math.max(0, data.length - 65557); i--) {
    if (data.readUInt32LE(i) === 0x06054b50 && i + 22 + data.readUInt16LE(i + 20) === data.length) { end = i; break; }
  }
  if (end < 0) throw Error('Invalid ZIP: missing end record');
  const count = data.readUInt16LE(end + 10), size = data.readUInt32LE(end + 12);
  let position = data.readUInt32LE(end + 16);
  if (data.readUInt16LE(end + 4) || data.readUInt16LE(end + 6) || count === 65535 || position === 0xffffffff) throw Error('Multi-disk / ZIP64 unsupported');
  if (position + size > end) throw Error('Invalid ZIP central directory bounds');
  const entries = new Map();
  let expanded = 0;
  for (let i = 0; i < count; i++) {
    if (data.readUInt32LE(position) !== 0x02014b50) throw Error('Invalid central entry');
    const flags = data.readUInt16LE(position + 8), method = data.readUInt16LE(position + 10);
    const crc = data.readUInt32LE(position + 16), compressed = data.readUInt32LE(position + 20), length = data.readUInt32LE(position + 24);
    const n = data.readUInt16LE(position + 28), extra = data.readUInt16LE(position + 30), comment = data.readUInt16LE(position + 32);
    const name = data.subarray(position + 46, position + 46 + n).toString('utf8');
    const offset = data.readUInt32LE(position + 42);
    position += 46 + n + extra + comment;
    if (entries.has(name)) throw Error('Duplicate ZIP entry: ' + name);
    if (name.includes('\\') || name.startsWith('/') || name.split('/').includes('..')) throw Error('Unsafe ZIP path: ' + name);
    if (flags & 1) throw Error('Encrypted ZIP entry: ' + name);
    expanded += length;
    if (length > 128 * 1024 * 1024 || expanded > 512 * 1024 * 1024) throw Error('ZIP expansion limit exceeded');
    if (data.readUInt32LE(offset) !== 0x04034b50) throw Error('Invalid local entry: ' + name);
    const start = offset + 30 + data.readUInt16LE(offset + 26) + data.readUInt16LE(offset + 28);
    if (start + compressed > data.length) throw Error('ZIP content out of bounds: ' + name);
    const bytes = data.subarray(start, start + compressed);
    const value = method === 0 ? Buffer.from(bytes) : method === 8 ? inflateRawSync(bytes, { maxOutputLength: Math.max(length, 1) }) : null;
    if (!value || value.length !== length || crc32(value) !== crc) throw Error('ZIP length / CRC / method mismatch: ' + name);
    if (!name.endsWith('/')) entries.set(name, value);
  }
  return entries;
}

// Parses class identity and enclosing metadata, not executable semantics.
export function parseClass(bytes) {
  let position = 0;
  const u1 = () => bytes.readUInt8(position++);
  const u2 = () => { const n = bytes.readUInt16BE(position); position += 2; return n; };
  const u4 = () => { const n = bytes.readUInt32BE(position); position += 4; return n; };
  if (u4() !== 0xcafebabe) throw Error('Invalid class magic');
  u2(); const major = u2(), cp = [null], size = u2();
  for (let i = 1; i < size; i++) {
    const tag = u1();
    if (tag === 1) { const n = u2(); cp[i] = bytes.subarray(position, position + n).toString('utf8'); position += n; }
    else if ([7, 8, 16, 19, 20].includes(tag)) cp[i] = { ref: u2() };
    else if ([3, 4, 9, 10, 11, 12, 17, 18].includes(tag)) position += 4;
    else if ([5, 6].includes(tag)) { position += 8; i++; }
    else if (tag === 15) position += 3;
    else throw Error('Unsupported constant-pool tag ' + tag);
  }
  const className = (index) => index ? cp[cp[index].ref] : null;
  const access = u2(), self = u2(), name = className(self); u2();
  const interfaces = u2(); position += interfaces * 2;
  function skipAttributes() { const n = u2(); for (let i = 0; i < n; i++) { u2(); const length = u4(); position += length; } }
  for (let kind = 0; kind < 2; kind++) { const n = u2(); for (let i = 0; i < n; i++) { position += 6; skipAttributes(); } }
  let nested = false, outerName = null;
  const attrs = u2();
  for (let i = 0; i < attrs; i++) {
    const attr = cp[u2()], length = u4(), end = position + length;
    if (attr === 'EnclosingMethod') nested = true;
    if (attr === 'InnerClasses') {
      const n = u2();
      for (let j = 0; j < n; j++) { const inner = u2(), outer = u2(); u2(); u2(); if (inner === self) { nested = true; outerName = className(outer); } }
    }
    position = end;
  }
  if (position !== bytes.length) throw Error('Class parsing did not consume full input: ' + name);
  return { name, major, access, nested, outerName, strings: cp.filter((value) => typeof value === 'string') };
}
