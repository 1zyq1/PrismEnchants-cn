# AGENTS.md

**PrismEnchants** Spigot 插件的完整中文本地化分支（55 种自定义附魔、附魔师/分解师 GUI、铁砧与附魔书应用、卷轴）。纯 Java 源码，用 `javac` 编译、用 Python 脚本打包。无测试、无 CI、不是 Git 仓库。

## 构建

构建入口是 `compile_all.py` 和 `repackage.py`，按此顺序运行即可。`WORK` 已改为脚本所在目录（`os.path.dirname(os.path.abspath(__file__))`），即本仓库根，无需再手改路径。

`WORK` 下需要的结构（`temp/`、`compiled/` 已被 gitignore，仓库默认没有）：

- `src_full/` —— 源码，已提交
- `temp/` —— 原始 jar 的解包目录；若缺失，先把 `PrismEnchants-中文.jar` 解包到 `temp/` 重建（`temp/` 下执行 `jar xf ..\PrismEnchants-中文.jar`）
- `compiled/` —— `compile_all.py` 的输出
- `lang/`、`config.yml`、`lib/` —— 已提交

流程：`python compile_all.py`（编译进 `compiled/`）→ `python repackage.py`（把 `compiled/` 的 class + `lang/` + `config.yml` 覆盖进 `temp/`，打包为 `PrismEnchants-中文.jar`）。`repackage.py` Step 5 的 enchant 计数在 Windows 上恒为 0（路径分隔符问题），是误报，以 jar 实际内容为准。

## 工具链

- JDK 25 位于 `C:\Program Files\Java\jdk-25.0.2`；脚本用绝对路径调用 `javac.exe`/`jar.exe`。
- 编译参数：`javac --release 16 -encoding UTF-8`。
- `compile_all.py` 会把源码复制到 `%TEMP%\opencode\pe_src`、输出到 `pe_out`（避开中文路径），并在编译前去掉 UTF-8 BOM —— javac 不接受 BOM。要编译就用这份副本，别在原目录直接编。
- 类路径：源码目录 + `WORK\temp` + `C:\Users\1zyq1\.m2\...` 下的 Spigot `1.21.4-R0.1-SNAPSHOT` API + `WORK\lib\VaultAPI-1.7.jar`。
- Vault 虽在类路径但代码中未使用；没有 NMS。插件只依赖 Spigot API（尽管构建基于 1.21.4，`plugin.yml` 里写的是 `api-version: '1.16'`）。
- 控制台为 GBK；脚本以 `gbk` 解码子进程输出。

## 源码约定

- `src_full/com/prismenchants/**` 是 CFR 0.152 反编译产物。局部变量名被混淆（`string`、`n`、`bl`、`stringArray`）；请沿用周边风格，不要重命名。
- 文件为 UTF-8 **带 BOM**；非 ASCII 文本在 `.java` 里写作 `\uXXXX` 转义，在 `lang/*.yml` 里则是原样字符。编辑时保留各自约定。
- 新增附魔：在 `enchant/types/` 继承 `CustomEnchant`，并在 `PrismEnchants.registerEnchants()` 注册。`enchants.yml` 首次运行时生成（不提交），控制每项附魔的 `enabled` / `success` / `destroy` / `max-level`。
- 连锁/范围破坏（`Veinminer` / `Timber` / `Excavator`）必须走 `util/SafeBreak.breakBlock(...)`，不要直接 `Block.breakNaturally()`：前者会先触发可取消的 `BlockBreakEvent`（Residence / WorldGuard 等据此拦截）再破坏，并跳过 `pe_placed` 玩家放置方块。`MiningListener` 用 `SafeBreak.isFiring()` 防止自己触发的事件被二次处理导致递归。
- 物品状态通过 PersistentDataContainer 存储，键定义在 `util/Keys.java`（`ench_<id>`、`book_*`、`scroll_type`、`protected`、`gui_action`）。`EnchantManager.refreshLore` 会重建 lore，并跳过以 `» ` 或 `Protected` 开头的行；两处要一起改。
- 战斗结算区分伤害来源：`CombatListener.onDamage` 里投射物伤害只走 `handleArrowHit`（读取箭矢 PDC 上的 `ARROW_ENCHANTS`），只有近战才用主手物品的附魔。别用“当前主手物品”给投射物结算 `onAttack`，否则射箭后切武器会误触发近战附魔。
- `GuiListener` 用自带的 1.21.4 经验表（`totalXpForLevels`、`REFUND_RATE = 0.85`）把配置里的等级成本换算为**固定经验点数**，而不是 `player.giveExpLevels`。该文件及其他少数文件含手工修改/注释，并非纯反编译代码。
- 面向玩家的文本在 `lang/*.yml`（13 种语言，`config.yml` 默认 `zh`）。取值按“数据目录选中语言 → jar 内同语言 → 数据目录 en → jar 内 en”逐级回退，所以新增键不需要玩家删旧语言文件。稀有度/分类的键为 `rarity.<name>` / `category.<name>`（由 `Rarity.key()` / `ItemCategory.key()` 生成）。附魔**名称与描述仍硬编码**在各 `enchant/types/*.java`（`super(...)` 与 `description()`），切换语言不会变。
