# Open-Musheor

Musheor 1.7.1 的反编译源码恢复工程，非作者官方源码。

## 已验证版本

| Minecraft | 工程 | 当前状态 |
|---|---|---|
| **1.21.4** | [源码与构建配置](<versions/1.21.4/build.gradle>) | Java 21 / Gradle 9.5.1：clean build + 完整性 PASS |
| **1.21.11** | [源码与构建配置](<versions/1.21.11/build.gradle>) | Java 21 / Gradle 9.5.1：clean build + 完整性 PASS |
| 1.21.5 / 1.21.8 | 保留历史版本 | 不在本次验收范围，不承诺可编译 |

两版各141个Java文件、243个编译产物类，完整保留可选兼容实现、原始Mixin注册、资源与access widener权限。无stub、无source excludes。仍有deprecated/unchecked提示；**未启动Minecraft，不代表游戏内功能或依赖兼容性已验证**。

## 构建

需要 JDK 21（设置 JAVA_HOME）、Node.js 22+、可访问依赖仓库的网络。在仓库根目录运行：

	node tools/build-all.mjs

单版本：

	node tools/build-all.mjs --version 1.21.4
	node tools/build-all.mjs --version 1.21.11

下载器按[1.21.4清单](<versions/1.21.4/libs/compat-dependencies.json>)、[1.21.11清单](<versions/1.21.11/libs/compat-dependencies.json>)获取真实编译依赖，检查SHA-256/SHA-512；下载后串行执行clean build与结构校验。**不需要GitHub令牌。** 缓存齐全时加 --offline；只构建不下载可加 --skip-download。

手动：先运行 node tools/fetch-dependencies.mjs，然后 ./gradlew --project-dir versions/1.21.4 clean build（Windows使用gradlew.bat）。产物位于相应工程的build/libs目录，不提交到Git。校验：node tools/verify-build.mjs。

## 恢复与可读性

各版本按自身Minecraft层级及Yarn映射，先TinyRemapper 0.11.1重映射字节码，再Vineflower 1.12.0完整反编译；不是对反编译文本做跨版本盲替换。研究了[mjt](https://github.com/luiox/mjt)；v2.3发行只有MCP宿主，不含main文档中新加CLI，因此本次直接使用Vineflower后端。

恢复缺失方法、损坏record、enum运行时名称和ordinal、Mixin selector/shadow/accessor映射、泛型推断与重载，并完整编译Litematica/Xaero/Via等兼容类。原作者部分随机标识符没有公开原名映射，保留以避免无依据破坏反射/行为，详见[阅读导航与限制](<docs/RESTORATION.md>)。

## 证据和边界

[本机构建记录](<docs/verification/local-build.md>)、[1.21.4产物结构基线](<docs/verification/1.21.4.baseline.json>)、[1.21.11基线](<docs/verification/1.21.11.baseline.json>)记录本次实际验收。基线来自本机构建，不是GitHub CI通过声明；SNAPSHOT依赖与JDK差异可能改变产物字节，验证器检查类清单/Mixin/资源/依赖而不强制JAR哈希相同。

**隐私提醒：原统计上传路径没有检查postStats开关；已有非空API key时只关闭开关不可靠。** 参见[安全复核](<SECURITY_AUDIT.md>)。本次忠实恢复原行为，没有私自修改联网功能。

## 许可

原mod声明 **All-Rights-Reserved**，仓库名称不代表已取得开源许可。本仓库不主张对原始代码的权利；反编译、校验或构建成功不授予再分发许可。请在发布/使用前自行核实权利人授权与第三方依赖许可。按原样提供，不保证安全或适用性，使用可能违反服务器规则。
