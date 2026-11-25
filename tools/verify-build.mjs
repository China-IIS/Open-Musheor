#!/usr/bin/env node
import {readFileSync,readdirSync,existsSync} from 'node:fs';
import path from 'node:path';
import {fileURLToPath} from 'node:url';
import {createHash} from 'node:crypto';
import {readArchive,parseClass} from './archive.mjs';
const root=path.resolve(path.dirname(fileURLToPath(import.meta.url)),'..');
const versions=process.argv[2]?[process.argv[2]]:['1.21.4','1.21.11'];
const hash=b=>createHash('sha256').update(b).digest('hex');
let failed=false;
for(const v of versions){
 if(!['1.21.4','1.21.11'].includes(v))throw Error('Unsupported verified version: '+v);
 const baseline=JSON.parse(readFileSync(path.join(root,'docs/verification',v+'.baseline.json'),'utf8'));
 const p=path.join(root,'versions',v),jar=path.join(p,'build/libs/musheor-1.7.1.jar');
 const errors=[];const check=(b,s)=>{if(!b)errors.push(s);};
 try{
  const entries=readArchive(jar),classes=[...entries.keys()].filter(n=>n.endsWith('.class')).sort();
  check(JSON.stringify(classes)===JSON.stringify(baseline.expectedClasses),'Class inventory differs from verified baseline');
  for(const name of classes){const c=parseClass(entries.get(name));check(c.major===65&&c.name+'.class'===name,'Invalid class identity/Java version: '+name);check(existsSync(path.join(p,'build/classes/java/main',name)),'Missing corresponding compiled output: '+name);}
  const metadata=JSON.parse(entries.get('fabric.mod.json').toString());
  check(metadata.id==='musheor'&&metadata.depends.minecraft.includes(v),'Wrong metadata/version');
  for(const config of metadata.mixins){const n=typeof config==='string'?config:config.config;const c=JSON.parse(entries.get(n).toString());for(const group of ['mixins','client','server'])for(const name of c[group]??[])check(entries.has((c.package+'.'+name).replaceAll('.','/')+'.class'),'Missing registered Mixin: '+name);}
  for(const list of Object.values(metadata.entrypoints))for(const e of list){const n=(typeof e==='string'?e:e.value).split('::')[0];check(entries.has(n.replaceAll('.','/')+'.class'),'Missing entrypoint: '+n);}
  for(const [n,h] of Object.entries(baseline.resourceSha256))check(entries.has(n)&&hash(entries.get(n))===h,'Resource/AW changed: '+n);
  const nested='META-INF/jars/Java-WebSocket-1.5.4.jar';check(entries.has(nested)&&hash(entries.get(nested))===baseline.nestedJarSha256,'Nested dependency hash mismatch');
  check(/^accessWidener\s+v2\s+intermediary/.test(entries.get(metadata.accessWidener).toString()),'Production AW not intermediary');
  const walk=d=>readdirSync(d,{withFileTypes:true}).flatMap(e=>e.isDirectory()?walk(path.join(d,e.name)):[path.join(d,e.name)]);
  const sources=walk(path.join(p,'src/main/java')).filter(n=>n.endsWith('.java'));check(sources.length===141,'Wrong Java source count');
  for(const f of sources)check(!/Please report this to the Vineflower|could not be decompiled|decompilation failed/i.test(readFileSync(f,'utf8')),'Decompiler failure marker: '+f);
  check(!/\bexclude\s*['"(]|enabled\s*=\s*false/.test(readFileSync(path.join(p,'build.gradle'),'utf8')),'Source exclusion/disabled task');
  console.log(JSON.stringify({version:v,passed:errors.length===0,classes:classes.length,sources:sources.length,artifactSha256:hash(readFileSync(jar)),errors}));
 }catch(e){errors.push(e.message);console.error(v+': '+e.message);}
 if(errors.length)failed=true;
}
if(failed)process.exitCode=1;
