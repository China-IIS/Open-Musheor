# Musheor 1.7.1 静态安全与隐私复核

复核更新：2026-11-24（Asia/Shanghai）。对象：Minecraft 1.21.4 / 1.21.5 / 1.21.8 / 1.21.11 四版恢复工程及相关原始 JAR 检查记录。

> **本报告不是“无病毒 / 无后门”的证明，也不是运行安全或供应链认证。** 原报告中“低风险（无恶意代码）”“关闭 post-stats 即可停止上传”“GET 不外发数据”“反射只用于兼容类”“剪贴板只写不读”和“四版本字节码完全一致”等结论已撤回；以下内容替代旧结论，而不是在互相矛盾的旧正文前追加免责声明。

## 1. 范围、方法与局限

本轮对恢复源码进行只读静态复核，核对上传逻辑、远程请求、进程启动、反射调用和配置剪贴板入口。没有为此运行模组、提交 API key、向统计服务发送测试数据或完成隔离环境抓包。1.21.4与1.21.11已通过完整构建，其他版本不在本次验收范围，见[工程状态](<README.md>)；反编译文本可能存在类型、控制流和行号变化，重要结论仍需与对应原始字节码和后续构建产物交叉核验。

历史关键词扫描结果（不在当前上传范围）可用于定位候选类及字符串，但它不是当前源码的完整审计证明：关键词命中不等于恶意行为，未命中也不能排除恶意行为。ZIP CRC 或下载 Hash 只能验证一定范围的字节一致性，不能证明代码可信。没有完成所有第三方依赖、原生组件、可选扩展和服务端的安全审查。

四个版本应分别复核。类路径集合相同或相似不能推出方法体、Mixin、资源或网络行为完全一致，更不能推出某一版绝无单独植入。下文优先链接 1.21.4 可读源码，上传主路径另外核对了其余三版；不把这一局部交叉检查扩大为全量等价证明。

## 2. 最重要的隐私问题：统计上传开关与实际执行不一致

### 实际控制条件

[ServerStatsTracker](<versions/1.21.4/src/main/java/musheor/utils/ServerStatsTracker.java>) 的 tick 上传路径检查玩家、世界、HighwayBuilder 激活状态及 tick 间隔，然后读取 API key 调用 upload；upload 检查 key 非空、玩家/会话和服务器信息可用。**该路径没有读取 postStats 开关。**

[设置定义](<versions/1.21.4/src/main/java/musheor/utils/system/MusheorSystem.java>)中 post-stats 默认 false，API key 默认空字符串；开关还控制 API key 与间隔设置在界面中的可见性，但界面隐藏不等于清空已保存的 key，也不等于阻止上述调用。默认间隔为 10 秒、最小 5 秒；实际按设置值乘 20 的 tick 数触发，因此受游戏 tick 运行情况影响，不保证精确墙钟间隔。

对应版本的上传实现：[1.21.4](<versions/1.21.4/src/main/java/musheor/utils/ServerStatsTracker.java>)、[1.21.5](<versions/1.21.5/src/main/java/musheor/utils/ServerStatsTracker.java>)、[1.21.11](<versions/1.21.11/src/main/java/musheor/utils/ServerStatsTracker.java>)。这些版本已检查的同一路径同样没有将 postStats 用作上传门禁。

### 端点、认证和数据

向 https://api.musheck.dev/stats 发送 POST JSON，通过 Authorization: Bearer 携带用户配置的 API key。源码中构造的字段为：

| 字段 | 内容 |
|---|---|
| uuid | 当前玩家 UUID |
| username | 当前会话用户名 |
| server | 当前服务器地址 |
| obsidianMined / obsidianPlaced | 黑曜石挖掘 / 使用统计 |
| echestsMined / netherrackMined | 末影箱 / 下界岩挖掘统计 |
| blocksMined | 本地统计汇总值 |
| timestamp | 客户端当前毫秒时间戳 |

本条构造路径未显式加入 XYZ 坐标，但**服务器地址、身份标识、统计和时间戳确实被外发**；不能用“未发送坐标”概括为“未发送身份/位置相关数据”。正常网络请求也会让服务方或代理观察到来源 IP、访问时间等信息。

去重键包含整个 JSON body，而 body 包含每次生成的 timestamp；因此不能承诺“统计不变就不会重复发送”。401 / 403 时的客户端提示提到 key、黑名单或 UUID 不匹配，**该提示不足以证明服务端如何存储、校验、保留或共享数据**，服务端实现没有纳入本次审查。

**建议：** 不希望通过这条路径上传时，应清空已保存的 API key，而不是只关闭 post-stats。清空 key 不能撤回已发送的数据或已经排队/正在进行的请求，也不会阻止其他功能联网。更强的限制应在不运行模组或受控网络隔离环境中实施并验证。源码恢复保留原始行为，本次文档修订没有偷偷修复上传逻辑。

## 3. 其他已识别的远程请求

| 服务 / 方法 | 可见用途 | 本地证据与边界 |
|---|---|---|
| https://highways.musheck.dev / GET | 获取高速公路配置 | [HighwayNetworkManager](<versions/1.21.4/src/main/java/musheor/utils/system/HighwayNetworkManager.java>)；初始化路径发起异步请求，设置 5 秒连接/读取超时和 MusheorAddon User-Agent；失败保留 fallback 配置 |
| https://mutuals.musheck.dev / GET | 获取互关 UUID 分组 | [MutualManager](<versions/1.21.4/src/main/java/musheor/utils/system/MutualManager.java>)；初始化路径发起异步请求，同样设置超时及 User-Agent；失败路径记录错误并保留已有状态 |
| https://mc-heads.net/avatar/&lt;playerName&gt;/64 / GET | 获取玩家头像图片 | [MoreTags](<versions/1.21.4/src/main/java/musheor/modules/features/MoreTags.java>)；请求路径直接含 playerName，设置 musheor-addon User-Agent 和 5 秒超时，在线程中读取图片 |

前两个可见构造路径没有显式添加玩家身份到请求参数/正文，但不能称为“完全不外发数据”。GET 会暴露请求源 IP、时间、请求路径和请求头；头像请求还显式包含玩家名，可能是功能涉及的其他玩家，不应默认是用户自己的名字。

这张表是本轮识别的 addon 请求路径，不是整个 Minecraft / Meteor / Fabric / 第三方模组进程的完整网络白名单。第三方库、重定向、可选扩展和依赖更新可能扩大行为范围。

## 4. 本地执行、反射与剪贴板

### 打开文件夹

[FolderCommand](<versions/1.21.4/src/main/java/musheor/commands/FolderCommand.java>) 的本地命令使用 ProcessBuilder 启动 explorer.exe，并把目录绝对路径作为独立参数。所读路径体现的是打开目录，不是已证实的下载执行链；但它确实启动了系统进程，不能写“完全没有进程启动”。用户输入的目录名参与路径构造，不能仅凭使用参数数组就断言不存在任何路径或环境风险。

### 反射不只用于兼容模块

[Execute 命令](<versions/1.21.4/src/main/java/musheor/commands/Execute.java>)扫描当前 musheor mod 容器中指定包下的 class，通过 Class.forName 建立类表；用户本地命令按类名/方法名及参数数量选择 declared method，调用 setAccessible(true) 后 invoke，并可能构造实例。它不是任意远程 JAR 下载器，但确实是动态方法调用入口。类扫描时 Class.forName 还可能触发类初始化，不能概括为“只有用户执行命令时才会加载类”。本轮没有证明该入口能被远程服务器直接控制，也没有证明其所有可调用方法都安全；不要执行来源不明的命令字符串。

[模组入口](<versions/1.21.4/src/main/java/musheor/musheor.java>)还反射加载版本适配、Litematica / Xaero 适配和可选 PlusInitializer；[聊天界面 Mixin](<versions/1.21.4/src/main/java/musheor/mixin/ChatScreenMixin.java>)及[玩家工具](<versions/1.21.4/src/main/java/musheor/utils/irzXX.java>)也存在可选 Plus 反射调用。没有提供的 Plus 实现或其他运行时扩展不能算作已审查代码。

### 剪贴板既有导出也有导入

[MusheorTab](<versions/1.21.4/src/main/java/musheor/utils/system/MusheorTab.java>)同时实现 toClipboard 与 fromClipboard，分别交给 Meteor NbtUtils 导出/导入配置。故旧报告“只复制、不读取剪贴板”错误。所读源码没有在此处展示后台持续收集或上传剪贴板的实现，但配置导入仍应只信任自己了解的内容；导出配置前注意其中可能保存 API key 等敏感设置，不能直接公开粘贴完整配置。

## 5. 可以说什么，不能说什么

- 可以确认：已识别到统计上传、头像/配置请求、本地进程启动和动态方法调用，并有上述真实源码定位。
- 本轮未确认一条恶意下载执行、持久化或凭据窃取链；**这不等于证明这些风险不存在**，也不对尚未覆盖的代码和依赖作排除结论。
- 不根据字符串里没有硬编码 IP、只有公开库、类数量相同或 CRC 正确，给出“无后门”“无病毒”“所有版本安全”的保证。
- 模组在游戏行为、服务器规则及账号方面另有风险；不能把账号风险一律归因为服务器规则而排除代码、凭据或供应链风险。
- 编译通过也只能证明一部分工程一致性；隐私控制、Mixin 应用、依赖兼容和实际出站请求仍需在受控客户端中独立测试。

## 6. 后续验证建议

1. 先完成无 stub / 无 source excludes 的各版完整构建，记录退出码及 remap 产物；不要把旧 class 输出当作这轮验证结果。
2. 在不含真实账号凭据、API key 和重要存档的隔离客户端测试；从网络层记录出站行为，再核对源码中的开关、key 与模块激活组合。
3. 修复上传门禁等行为变更应单独授权、单独记录并测试，不与“忠实恢复原功能”混淆。
4. 对第三方依赖与可选扩展另做审查。下载记录及 Hash 见[工程依赖说明](<README.md>)，不把来源可追溯当作安全认证。
5. 原始模组声明 All-Rights-Reserved；源码恢复和本报告不改变许可，也不是公开分发授权。
