# 本机构建验证记录

在Windows本机使用Zulu Java21.0.10、官方Gradle9.5.1 Wrapper执行完整clean build（未启动Minecraft），两版均exit0。Loom分别1.14.10/1.17.20。随后完整原始class/Mixin/资源/AW/嵌套JAR检查通过；工具回归13/13通过。

| MC | Java源码 | 产物class | 验证产物SHA-256 |
|---|---:|---:|---|
|1.21.4|141|243|964e777e439356835ee63666651569f8ac067d0346ee8c9f3ced24d0463fde74|
|1.21.11|141|243|87b20c1a775c4972904c82bfa60bc1c342d1f6075fc077e453ef92cd34fa915b|

这些哈希是原工作区的已验证产物记录，不是GitHub CI的结果。本次发布前也在独立克隆的 versions 布局下重跑两版 clean build 和 verify-build：均成功，243 classes / 141 sources，产物SHA-256与表中一致。1.21.11首次 --offline 因缓存缺少sponge-mixin失败，联网补齐后构建和校验通过。完整构建仍有deprecated/unchecked提示，无编译错误。三个必要嵌套类型合法化改名为Grid、Single、Mode；Highway从不合法record改为保持字段与数据方法语义的final class，反射元数据可能不同。未承诺所有反射/序列化行为完全等价。

生产JAR不提交Git；本仓库版本的构建与校验命令见[根说明](<../../README.md>)。
