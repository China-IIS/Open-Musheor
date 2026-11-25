# 源码阅读与恢复说明

## 阅读入口

- [1.21.4 入口](<../versions/1.21.4/src/main/java/musheor/musheor.java>) / [1.21.11 入口](<../versions/1.21.11/src/main/java/musheor/musheor.java>)：Meteor addon 初始化、模块/命令/HUD 注册。
- [高速公路构建模块](<../versions/1.21.4/src/main/java/musheor/modules/automation/HighwayBuilder.java>)：automation 下各功能模块是主要业务入口，features、tech、hud 分别组织移动/显示、交互、HUD。
- [地图复制状态机](<../versions/1.21.4/src/main/java/musheor/modules/automation/MapDuper.java>)：Stage 恢复为 Extract / Copy / Store / Done，区分 countFreeInventorySlots、findFreeInventorySlot、hasUnfinishedMapsInSourceBundle，避免混淆重载选错。
- [地图布局工具](<../versions/1.21.4/src/main/java/obf/JhujBv5.java>)：Grid/Single 记录类型、物品展示框布局；[高速公路网络](<../versions/1.21.4/src/main/java/musheor/modules/automation/highway/HighwayNetwork.java>)：几何、节点、边。
- [版本适配](<../versions/1.21.11/src/main/java/musheor/compat/VersionHelperImpl.java>) / [Litematica 兼容](<../versions/1.21.11/src/main/java/musheor/compat/LitematicaHelperImpl.java>)：不同 MC API 与可选模组适配。
- [统计上传](<../versions/1.21.4/src/main/java/musheor/utils/ServerStatsTracker.java>)和[安全复核](<../SECURITY_AUDIT.md>)：保留原始联网行为，注意隐私。

Minecraft 类型/字段/方法均尽量使用对应版本 Yarn 可读名，Mixin selectors/shadows/accessors 同步为 named，最终由 Loom remap 回 intermediary。原作者自己的随机标识符没有公开映射，不能凭反编译找回已丢失的原名；这些标识符保留，以避免对反射、调用和配置语义作无依据改写。可从已恢复的模块名、参数、方法体及本说明追踪。

## 重要修复

1. 每个版本独立从原始 JAR 完整恢复，TinyRemapper 0.11.1 带 Minecraft intermediary 层级 classpath 后再用 Vineflower 1.12.0，避免继承成员漏映射。mjt v2.3 的 Windows 发行不带新 CLI，采用其同类 Vineflower 后端而非假称执行 mjt CLI。
2. 恢复旧 Vineflower 丢失的 oX7ku.TJImwK2(int) 方法体，并对照原字节码的负坐标 floorDiv 和 section 边界行为；新版 VF 可完整恢复该 switch。
3. 修复16个损坏的 record 声明，保留全部业务方法与原数据标签；Highway 因字段名与静态工厂冲突使用 final class 保持字段/构造/equals/hashCode/toString 逻辑。原始 Record 反射元数据不能完全等价，见下文限制。
4. 所有 enum 常量恢复原运行时 name 和 ordinal 顺序，避免配置序列化或 switch 名称错误。最后单独同步 MapDuper.Stage 及两处 HUD ScreenTarget 引用。
5. 修复 Java package 与 musheor 主类同名遮蔽、泛型擦除推断、Mixin 交叉类型 cast、Runnable/Callable 重载和错误的嵌套类 AW 分隔符。
6. 完整编译可选 Litematica / Xaero / Via 等实现，不排除兼容类；修正 1.21.11 ButtonWidget.Text 遮蔽与新版 FileType 的 Path 参数。
7. Java-WebSocket 使用原嵌套字节不变打包；原全部 Mixin 注册、资源和 AW intermediary 权限条目完整保留。

## 可核查证据

- [本机构建记录](<verification/local-build.md>)：含全部源码、依赖、脚本的输入 SHA-256 和最终产物 SHA-256。
- [必要类改名及限制](<#重要修复>)：仅 Grid、Single、Mode 三个合法化改名，顶级类未改名。
- 本机构建的类清单、资源和依赖哈希保存在两个版本的结构基线中，详见[构建记录](<verification/local-build.md>)。
- 工具回归测试13/13通过；record恢复阶段做过384样例与36864次 equals 对比、NaN/正负零/数组浅语义等验证，不能替代全模组运行测试。

## 限制与清理边界

构建无编译错误，仍有 deprecated/unchecked 提示；没有运行 Minecraft 或测试外部服务。Highway 已不是 java.lang.Record；恢复的其他 record 元数据与混淆原始字节码、三个内部类二进制名存在合法化差异，不能承诺任意外部反射/序列化用户完全无差异。

最终交付只验收1.21.4和1.21.11。仓库不新增原始mod JAR、临时反编译目录或第三方依赖二进制；libs由下载脚本恢复。保留此前1.21.5/1.21.8历史目录，不属本次完成承诺。obf包本身是原业务代码，不能当作垃圾删除。
