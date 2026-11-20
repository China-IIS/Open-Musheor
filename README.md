# Open-Musheor

`musheor` 1.7.1 的反编译源码，面向 Minecraft **1.21.4 / 1.21.5 / 1.21.8 / 1.21.11**。

这不是官方源码。所有代码由发布 jar 反编译得到，仅用于逆向工程学习与研究。

## 内容

| Minecraft | tag | 目录 |
|---|---|---|
| 1.21.4 | `v1.7.1-1.21.4` | [`versions/1.21.4`](versions/1.21.4) |
| 1.21.5 | `v1.7.1-1.21.5` | [`versions/1.21.5`](versions/1.21.5) |
| 1.21.8 | `v1.7.1-1.21.8` | [`versions/1.21.8`](versions/1.21.8) |
| 1.21.11 | `v1.7.1-1.21.11` | [`versions/1.21.11`](versions/1.21.11) |

四个版本源码同源，差异只在编译目标。每个目录都是一个独立可构建的 Gradle 工程。

## 工程结构

```
versions/<mc>/
├── build.gradle          Fabric Loom 1.10.5 + Meteor Client
├── settings.gradle
├── gradle.properties
├── libs/
│   ├── Java-WebSocket-1.5.4.jar   原 jar 内嵌依赖
│   └── baritone.jar               baritone API（compileOnly）
└── src/main/
    ├── java/             141 个 .java（Yarn 可读名）
    └── resources/
        ├── fabric.mod.json
        ├── musheor.mixins.json
        ├── musheor.accesswidener
        └── assets/
```

## 构建

需要 JDK 21 与 Gradle 8.12+（Loom 1.10.5 的版本要求）。

```bash
cd versions/1.21.8
gradle build --no-daemon --console=plain
```

产物在 `build/libs/`。

依赖来源：`maven.fabricmc.net`（Loom、Yarn、Fabric Loader）、`maven.meteordev.org/snapshots`（Meteor Client `<mc>-SNAPSHOT`）、Mojang libraries。

## 反编译与处理过程

1. **Vineflower 1.11.1** 反编译（保留精确大小写路径，先输出 jar 再解包，避免 Windows 大小写覆盖）；
2. **Yarn 映射**把 intermediary 名（`class_1234` / `method_5678`）换成可读名，含内部类点号形式与跨版本 mappings 兜底；
3. 清理未使用 import 与反编译噪音；
4. 修复 Vineflower 的 record 缺陷（空 header + 非法实例字段 → 正确 record 组件），并处理混淆器造成的非法标识符与同名嵌套类型；
5. 生成 Loom 工程，重映射 accesswidener 到 `named` 命名空间。

## 已知限制

- **1.21.8 尚未完全编译通过**：混淆器在 `obf` / `musheor.utils` / `musheor.utils.manager` 三个包复用了 7 个类名（`pvfA15fQSVM`、`gVOhtBej1x`、`U6PQSXqW`、`GyhoRo`、`irzXX`、`JhujBv5`、`jU8xXhmkg`、`WOGVSBb`），反编译后简单名歧义。其余 140 个文件可通过。
- **Litematica / Xaero 兼容模块已排除编译**：两者闭源、无公开 maven；`musheor.java` 通过 `Class.forName` 反射加载其实现，排除不影响主工程。需要时把对应 jar 放入 `libs/` 并删掉 `build.gradle` 里对应的 `exclude`。
- **baritone 使用 1.21.11 的 API jar**：`meteordevelopment:baritone:1.21.8-SNAPSHOT` 在 maven 上只有 pom 没有 jar。
- `obf/` 包的方法名仍是作者自己的混淆名（如 `fStlZIvY9lS8`），不在 Yarn 映射范围。

## 安全审计

见 [SECURITY_AUDIT.md](SECURITY_AUDIT.md)。摘要：

- 无病毒 / 后门 / 持久化 / 进程注入 / C2 / 硬编码恶意 IP；
- 存在**默认关闭**的遥测：`POST https://api.musheck.dev/stats`，需 `post-stats` 开关 + `apiKey` + `HighwayBuilder` 模块激活，上报 UUID、用户名、服务器地址与挖掘统计；
- 两个只读端点：`highways.musheck.dev`（高速配置）、`mutuals.musheck.dev`（互关列表），只 GET。

## 免责声明

原 mod 的 `fabric.mod.json` 声明许可为 `All-Rights-Reserved`。本仓库不主张对原始代码的任何权利，仅因学习、兼容性研究与安全分析目的而发布反编译产物。若权利人要求，可随时删除。

代码按原样提供，不附带任何担保。使用第三方 Minecraft 外挂可能违反服务器规则并导致封禁，风险自负。
