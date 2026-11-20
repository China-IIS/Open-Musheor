# musheor 1.7.1 安全审计（后门 / 病毒 / 遥测）

审计时间：2026-11-20 · 对象：`musheor-1.7.1` 的 1.21.4 / 1.21.5 / 1.21.8 / 1.21.11 四个发布 jar
方法：**只读静态分析**——jar 常量池全量扫描 + 141 个反编译源码文件逐条核对。未运行 jar、未联网。

## 一、结论

**风险等级：低（无恶意代码），但存在默认关闭的自愿遥测，且其上报字段比界面描述更宽。**

| 类别 | 结论 |
|---|---|
| 病毒 / 后门 | **未发现**。无下载执行、无持久化、无进程注入、无 C2、无硬编码恶意 IP |
| 遥测 | **存在 1 个上报端点**（`api.musheck.dev/stats`），需同时满足 3 个条件才触发 |
| 只读远程请求 | 2 个（高速公路配置、互关 UUID 列表），只 GET 不 POST |
| 用户数据外发 | 上报包含 **UUID、用户名、服务器地址**、挖掘统计 |
| 四版本一致性 | 网络/遥测代码四版本完全一致（同源编译） |

---

## 二、遥测（唯一的上报行为）

### 端点

`POST https://api.musheck.dev/stats`，`Authorization: Bearer <apiKey>`，body 为 JSON。

### 上报字段（`musheor/utils/ServerStatsTracker.java:63-73`）

```java
p.addProperty("uuid", mc.player.getUuidAsString());     // 玩家 UUID
p.addProperty("username", mc.getSession().getUsername()); // 正版用户名
p.addProperty("server", server.address);                  // 当前服务器地址
p.addProperty("obsidianMined", ...);                      // 黑曜石挖掘数
p.addProperty("obsidianPlaced", ...);                     // 黑曜石放置数
p.addProperty("echestsMined", ...);                       // 末影箱挖掘数
p.addProperty("netherrackMined", ...);                    // 下界岩挖掘数
p.addProperty("blocksMined", ...);                        // 总方块数
p.addProperty("timestamp", System.currentTimeMillis());   // 时间戳
```

### 触发条件（三者必须同时满足）

1. `MusheorSystem.Manager.postStats` 开关为开（默认 **false**，`MusheorSystem.java:327`）；
2. 已填写 `apiKey`（默认空字符串，需去 `discord.gg/crankers` 申请，`MusheorSystem.java:337`）；
3. `HighwayBuilder` 模块处于激活状态（`ServerStatsTracker.onTick` 里判断）。

满足后每 `postIntervalSeconds`（默认 **10 秒**，最小 5）上报一次；内容不变时不重复发送（`lastStamp` 去重）。

### 与界面描述不一致的地方（值得注意）

设置项 `post-stats` 的描述写着：

> "ONLY HIGHWAY PLACEMENT / BREAK STATS, COORDINATES OR LOCATION IS NEVER SENT!"

但代码实际还发送了 **`uuid`、`username`、`server.address`**。服务器地址不是坐标，但属于"你在哪个服务器玩"的位置信息。**描述只承诺不发坐标，技术上没撒谎，但用户的合理预期会更高。**

服务端在 401/403 时返回的调试信息是 `"key invalid, blacklisted, or UUID not yours."`，说明服务端按 UUID 做了归属校验与黑名单——进一步证明 UUID 被持久化关联。

---

## 三、两个只读远程请求（不上传）

| 端点 | 方法 | 用途 | 超时 | 失败行为 |
|---|---|---|---|---|
| `https://highways.musheck.dev` | GET | 下载高速公路坐标配置（`ring_distances` 等） | 5s | 打印错误，用内置 fallback |
| `https://mutuals.musheck.dev` | GET | 下载互关/好友 UUID 列表（`kek`、`post` 分组） | 5s | 打印错误，用内置 fallback |

两者都只读，`User-Agent: MusheorAddon`，**不携带任何本地数据**。属于功能数据同步，不是遥测。

---

## 四、明确排除的风险项

| 检查项 | 结果 |
|---|---|
| 下载并执行（`URLDownloadToFile` / 远程加载 jar） | 无 |
| `Runtime.exec` / `ProcessBuilder` | 仅 1 处：`FolderCommand` 打开本地配置文件夹（`explorer.exe <path>`），无参数拼接、无远程输入 |
| 持久化（注册表 Run / 计划任务 / 启动项） | 无 |
| 进程注入 / 反射加载任意类 | 无；`Class.forName` 仅用于加载自身 compat 实现（Litematica / Xaero） |
| 键盘记录 / 屏幕截取 / 摄像头 | 无 |
| 剪贴板 | 仅 `MusheorTab` 里复制 GUI 配置文本，非读取用户剪贴板内容 |
| 硬件指纹（HWID / MAC / 主板序列号） | 无 |
| 硬编码恶意 IP | 无（扫描结果 IP 列表为空） |
| 内嵌可执行文件 | 仅 `META-INF/jars/Java-WebSocket-1.5.4.jar`，公开标准库 |

### 关于扫描里的"高频命中"

- `process: 113 classes`、`reflection: 106 classes` —— 是常量池里 `java/lang/Runtime`、`Class.forName` 这类**符号引用**，Minecraft mod 生态普遍存在，逐条核对后无实际 exec/加载行为。
- `telemetry: 9 classes` —— 全部指向 `ServerStatsTracker` 及其调用链（`MusheorSystem`、`HighwayBuilder`、`musheor` 入口）。

---

## 五、四个版本对比

四个 jar 的 class 数（243）、`obf/` 包（17 个 class）、内嵌 jar、URL 集合、遥测代码**完全一致**，差异只在编译目标 MC 版本。不存在某个版本被单独植入后门的情况。

---

## 六、建议

1. **不填 `apiKey`、不开 `post-stats`** 即可完全杜绝上报——这是默认状态，无需额外操作。
2. 若开启，知悉会上传 **UUID + 用户名 + 服务器地址**；不想暴露服务器可用小号。
3. 想彻底断开：hosts 黑洞 `api.musheck.dev`、`highways.musheck.dev`、`mutuals.musheck.dev`（后两个会导致高速配置回退到内置值）。
4. 该 mod 是 Minecraft 外挂（Meteor addon），本身违反多数服务器规则；**账号风险来自使用行为，不来自代码**。

---

## 七、证据文件

- `work/security_audit.json` —— 四 jar + 源码的全量扫描结果（含每个命中的类名/文件/行号）
- 关键源码：`ServerStatsTracker.java`、`MusheorSystem.java`、`HighwayNetworkManager.java`、`MutualManager.java`、`FolderCommand.java`、`MusheorTab.java`
- 扫描脚本：`~/.workbuddy-ai/tools/decompilers/audit_musheor.py`（只读，无网络）
