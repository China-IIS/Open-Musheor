#!/usr/bin/env node
import {spawnSync} from 'node:child_process';
import {mkdirSync,readFileSync} from 'node:fs';
import path from 'node:path';
import {fileURLToPath} from 'node:url';
import {createHash} from 'node:crypto';
const root=path.resolve(path.dirname(fileURLToPath(import.meta.url)),'..');
const args=process.argv.slice(2),i=args.indexOf('--version'),versions=i<0?['1.21.4','1.21.11']:[args[i+1]];
for(const a of args)if(!['--version','--offline','--skip-download','1.21.4','1.21.11'].includes(a))throw Error('Unknown argument '+a);
for(const v of versions)if(!['1.21.4','1.21.11'].includes(v))throw Error('Unsupported version');
const java=process.env.JAVA_HOME?path.join(process.env.JAVA_HOME,'bin',process.platform==='win32'?'java.exe':'java'):'java';
const wrapper=path.join(root,'gradle/wrapper/gradle-wrapper.jar');
if(createHash('sha256').update(readFileSync(wrapper)).digest('hex')!=='497c8c2a7e5031f6aa847f88104aa80a93532ec32ee17bdb8d1d2f67a194a9c7')throw Error('Wrapper checksum mismatch');
const run=(exe,argv)=>{const r=spawnSync(exe,argv,{cwd:root,env:process.env,stdio:'inherit',shell:false});if(r.error)throw r.error;if(r.status!==0)throw Error(exe+' failed: '+r.status);};
const temp=path.join(root,'.build-tmp');mkdirSync(temp,{recursive:true});
for(const v of versions){
 if(!args.includes('--skip-download'))run(process.execPath,[path.join(root,'tools/fetch-dependencies.mjs'),'--version',v]);
 const gradle=['-Djava.io.tmpdir='+temp,'-Dfile.encoding=UTF-8','-classpath',wrapper,'org.gradle.wrapper.GradleWrapperMain','--project-dir',path.join(root,'versions',v),'--no-daemon','--no-parallel','--max-workers=1','--console=plain'];
 if(args.includes('--offline'))gradle.push('--offline');
 run(java,[...gradle,'clean','build']);
 run(process.execPath,[path.join(root,'tools/verify-build.mjs'),v]);
}
