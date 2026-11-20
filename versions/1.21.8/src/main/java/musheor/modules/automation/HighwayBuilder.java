package musheor.modules.automation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.gui.GuiTheme;
import meteordevelopment.meteorclient.gui.widgets.WWidget;
import meteordevelopment.meteorclient.gui.widgets.containers.WVerticalList;
import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
import meteordevelopment.meteorclient.settings.EnumSetting.Builder;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.combat.KillAura;
import meteordevelopment.meteorclient.systems.modules.movement.AutoWalk.Direction;
import meteordevelopment.meteorclient.systems.modules.movement.AutoWalk;
import meteordevelopment.meteorclient.systems.modules.player.AutoEat;
import meteordevelopment.meteorclient.systems.modules.player.AutoGap;
import meteordevelopment.meteorclient.systems.modules.render.FreeLook;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import musheor.compat.VersionHelper;
import musheor.musheor;
import musheor.utils.JhujBv5;
import musheor.utils.hud.screen.EchestConfigScreen;
import musheor.utils.irzXX;
import musheor.utils.jU8xXhmkg;
import musheor.utils.manager.PathKit;
import musheor.utils.oyDPiOVBGKG;
import musheor.utils.system.MusheorSystem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import obf.GyhoRo;
import obf.U6PQSXqW;
import obf.gVOhtBej1x;

public class HighwayBuilder extends Module {
   private final MinecraftClient i2Nu6XHKic = MinecraftClient.getInstance();
   private final SettingGroup vGAMA89H = this.settings.getDefaultGroup();
   private final SettingGroup qhulAUwd = this.settings.createGroup("Placement");
   private final SettingGroup BrmKXSYhWhqoB1 = this.settings.createGroup("Restocking");
   private final SettingGroup TXWyNb = this.settings.createGroup("Auto Eat");
   private final SettingGroup I3psabmOrOI = this.settings.createGroup("Nuker");
   private final SettingGroup duJ6sPeetAcBh6P = this.settings.createGroup("Kill Aura");
   private final SettingGroup j376sIQ = this.settings.createGroup("Safety");
   private final SettingGroup V7zKJ4n6D = this.settings.createGroup("Inventory");
   private final SettingGroup voxTwgNzyrpaAnP = this.settings.createGroup("Miscellaneous");
   private final Setting<HighwayBuilder.BuildMode> xileXsbo = this.vGAMA89H
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("build-mode")).description("Pave mode places blocks - Dig mode digs tunnels."))
               .defaultValue(HighwayBuilder.BuildMode.fStlZIvY9lS8))
            .build()
      );
   public final Setting<HighwayBuilder.Mode> fStlZIvY9lS8 = this.vGAMA89H
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("mode"))
                  .description(
                     "Which mode to run the highwaybuilder in; Auto = fully automatic alignment, width, etc... - Semi = Chosen highway type and other parameters - Manual = full manual control"
                  ))
               .defaultValue(HighwayBuilder.Mode.fStlZIvY9lS8))
            .build()
      );
   private final Setting<HighwayBuilder.HighwayType> nOwp0NYl = this.vGAMA89H
      .add(
         ((Builder)((Builder)((Builder)((Builder)new Builder().name("highway-type")).description("Cardinal or diagonal highway type."))
                  .defaultValue(HighwayBuilder.HighwayType.fStlZIvY9lS8))
               .visible(() -> this.fStlZIvY9lS8.get() != HighwayBuilder.Mode.fStlZIvY9lS8))
            .build()
      );
   private final Setting<Integer> umvNbb7iD9UpR = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("pavement-width"))
                     .description("Width of the pavement below the player's feet."))
                  .defaultValue(4))
               .sliderRange(3, 9)
               .visible(() -> this.fStlZIvY9lS8.get() != HighwayBuilder.Mode.fStlZIvY9lS8))
            .build()
      );
   private final Setting<Boolean> zkcZ4Xkp9Ez7Y = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("auto-bounce"))
                     .description("Automatically start bouncing when no blockages or missing blocks can be found ahead"))
                  .defaultValue(true))
               .visible(() -> this.fStlZIvY9lS8.get() != HighwayBuilder.Mode.TJImwK2))
            .build()
      );
   private final Setting<Integer> flWHzEyn = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("bounce-distance-check"))
                     .description("How many blocks to scan ahead and check before allowing the paver to start bouncing"))
                  .defaultValue(24))
               .sliderRange(1, 64)
               .visible(() -> this.fStlZIvY9lS8.get() != HighwayBuilder.Mode.TJImwK2 && (Boolean)this.zkcZ4Xkp9Ez7Y.get()))
            .build()
      );
   private final Setting<Block> FVjbhYkk53UT = this.qhulAUwd
      .add(
         ((meteordevelopment.meteorclient.settings.BlockSetting.Builder)((meteordevelopment.meteorclient.settings.BlockSetting.Builder)((meteordevelopment.meteorclient.settings.BlockSetting.Builder)((meteordevelopment.meteorclient.settings.BlockSetting.Builder)new meteordevelopment.meteorclient.settings.BlockSetting.Builder()
                        .name("pavement-block"))
                     .description("Block that is used to build highway pavement with."))
                  .defaultValue(Blocks.OBSIDIAN))
               .visible(() -> this.xileXsbo.get() == HighwayBuilder.BuildMode.fStlZIvY9lS8))
            .build()
      );
   private final Setting<HighwayBuilder.ScaffoldMode> Nt3xeF = this.qhulAUwd
      .add(
         ((Builder)((Builder)((Builder)((Builder)new Builder().name("scaffold-mode"))
                     .description("What type of scaffolding to use when going over caves and open area's."))
                  .defaultValue(HighwayBuilder.ScaffoldMode.QfZy2gWnKInxFPr))
               .visible(() -> this.xileXsbo.get() == HighwayBuilder.BuildMode.QfZy2gWnKInxFPr))
            .build()
      );
   private final Setting<Block> zXcO5rZ = this.qhulAUwd
      .add(
         ((meteordevelopment.meteorclient.settings.BlockSetting.Builder)((meteordevelopment.meteorclient.settings.BlockSetting.Builder)((meteordevelopment.meteorclient.settings.BlockSetting.Builder)((meteordevelopment.meteorclient.settings.BlockSetting.Builder)new meteordevelopment.meteorclient.settings.BlockSetting.Builder()
                        .name("scaffold-block"))
                     .description("Block that is used to fix the flooring with."))
                  .defaultValue(Blocks.NETHERRACK))
               .visible(() -> this.xileXsbo.get() == HighwayBuilder.BuildMode.QfZy2gWnKInxFPr && this.Nt3xeF.get() != HighwayBuilder.ScaffoldMode.fStlZIvY9lS8))
            .build()
      );
   private final Setting<Boolean> jbUe84V1vykfK = this.qhulAUwd
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("scaffold-left-rail"))
                     .description("Places the railing on the left side of the player."))
                  .defaultValue(true))
               .visible(() -> this.xileXsbo.get() == HighwayBuilder.BuildMode.QfZy2gWnKInxFPr))
            .build()
      );
   private final Setting<Boolean> zbN84Osn9p5lD = this.qhulAUwd
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("scaffold-right-rail"))
                     .description("Places the railing on the right side of the player."))
                  .defaultValue(true))
               .visible(() -> this.xileXsbo.get() == HighwayBuilder.BuildMode.QfZy2gWnKInxFPr))
            .build()
      );
   private final Setting<Boolean> ZlNrrNcv3Ylc = this.qhulAUwd
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("place-rails"))
                     .description("Places railings on both sides of the highway 1 block above the pavement."))
                  .defaultValue(true))
               .visible(() -> this.xileXsbo.get() == HighwayBuilder.BuildMode.fStlZIvY9lS8 && this.fStlZIvY9lS8.get() != HighwayBuilder.Mode.fStlZIvY9lS8))
            .build()
      );
   private final Setting<Boolean> EVSMbf = this.qhulAUwd
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("place-left-rail"))
                     .description("Places railings on the left side sides of the highway 1 block above the pavement."))
                  .defaultValue(true))
               .visible(
                  () -> this.xileXsbo.get() == HighwayBuilder.BuildMode.fStlZIvY9lS8
                     && (Boolean)this.ZlNrrNcv3Ylc.get()
                     && this.fStlZIvY9lS8.get() != HighwayBuilder.Mode.fStlZIvY9lS8
               ))
            .build()
      );
   private final Setting<Boolean> pF16zz4NJECfg7 = this.qhulAUwd
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("place-right-rail"))
                     .description("Places railings on the right sides of the highway 1 block above the pavement."))
                  .defaultValue(true))
               .visible(
                  () -> this.xileXsbo.get() == HighwayBuilder.BuildMode.fStlZIvY9lS8
                     && (Boolean)this.ZlNrrNcv3Ylc.get()
                     && this.fStlZIvY9lS8.get() != HighwayBuilder.Mode.fStlZIvY9lS8
               ))
            .build()
      );
   private final Setting<Boolean> WGmfQKK = this.qhulAUwd
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("replace-crying-obsidian"))
                     .description("Mines and replaces crying obsidian if part of the pavement positions."))
                  .defaultValue(false))
               .visible(() -> this.xileXsbo.get() == HighwayBuilder.BuildMode.fStlZIvY9lS8))
            .build()
      );
   private final Setting<Boolean> icQ738dpWr7W = this.qhulAUwd
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("fill-ceiling"))
                     .description("Fills the ceiling at Y level 123 with blocks."))
                  .defaultValue(false))
               .visible(() -> this.xileXsbo.get() == HighwayBuilder.BuildMode.fStlZIvY9lS8))
            .build()
      );
   private final Setting<Boolean> JAj4YwE2MVx = this.BrmKXSYhWhqoB1
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("echest-farming"))
                     .description("Allow the player to farm obsidian by mining enderchests."))
                  .defaultValue(true))
               .visible(() -> this.xileXsbo.get() == HighwayBuilder.BuildMode.fStlZIvY9lS8 && this.FVjbhYkk53UT.get() == Blocks.OBSIDIAN))
            .build()
      );
   private final Setting<Boolean> JPp6VyZP9f = this.BrmKXSYhWhqoB1
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("enable-item-restocking"))
                  .description("Allow the restocking process to grab items from shulkers."))
               .defaultValue(false))
            .build()
      );
   private final Setting<Boolean> MR1DKzw = this.BrmKXSYhWhqoB1
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("swap-broken-pickaxes"))
                     .description("Swaps broken pickaxes with new ones when restocking these, allowing you to repair them later."))
                  .defaultValue(true))
               .visible(this.JPp6VyZP9f::get))
            .build()
      );
   private final Setting<Boolean> DqGSCWplcjMi = this.BrmKXSYhWhqoB1
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("shulker-restocking"))
                  .description("Allow the restocking process to grab shulkers from the player's enderchest."))
               .defaultValue(false))
            .build()
      );
   private final Setting<Boolean> cO1FU7fW = this.BrmKXSYhWhqoB1
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("material-shulker-restocking"))
                     .description("Allow the restocking process to grab shulkers with pavement material from the player's enderchest."))
                  .defaultValue(false))
               .visible(this.DqGSCWplcjMi::get))
            .build()
      );
   private final Setting<Boolean> g0iiTDJzfIGXdui = this.BrmKXSYhWhqoB1
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("tool-shulker-restocking"))
                     .description("Allow the restocking process to grab tool shulkers from the player's enderchest."))
                  .defaultValue(false))
               .visible(this.DqGSCWplcjMi::get))
            .build()
      );
   private final Setting<Boolean> VIbihOaIVyhvJw = this.TXWyNb
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("toggle-auto-eat"))
                  .description("Pauses the current task and automatically eats."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> RE8q1TMDRWx = this.TXWyNb
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("toggle-auto-gap"))
                  .description("Allow the player to eat golden apples when required"))
               .defaultValue(true))
            .build()
      );
   public final Setting<Boolean> QfZy2gWnKInxFPr = this.I3psabmOrOI
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("toggle-kek-nuker"))
                  .description("Allows the paver to use the nuker module."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> vLRVDxlh = this.I3psabmOrOI
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("mine-above-rails"))
                  .description("Cleans and removes blocks above rails"))
               .defaultValue(false))
            .build()
      );
   private final Setting<Boolean> HomZ7BbhM7wB = this.duJ6sPeetAcBh6P
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("toggle-kill-aura"))
                  .description("Automatically attack nearby hostile entities."))
               .defaultValue(false))
            .build()
      );
   private final Setting<Boolean> lVX1t0MC = this.j376sIQ
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("disconnect-if-no-materials"))
                  .description("Automatically disconnect when materials are low (less than 8 Obsidian/E-Chests)."))
               .defaultValue(false))
            .build()
      );
   private final Setting<Boolean> xarqg2oUiwotDG4 = this.j376sIQ
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("toggle-source-filler"))
                  .description("Automatically remove lava sources when you can reach them."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> RFflNsLXot = this.j376sIQ
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("advanced-source-filler"))
                     .description("Uses baritone to path to lava sources ahead of the player. Not recommended."))
                  .defaultValue(false))
               .visible(null))
            .build()
      );
   private final Setting<Boolean> F4hivB = this.V7zKJ4n6D
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("toggle-auto-replenish"))
                  .description("Automatically move items from inventory to hotbar when slots are empty."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> bDU9FLOzvL = this.V7zKJ4n6D
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("toggle-inventory-cleaner"))
                  .description("Allow the paver to dispose of unwanted items."))
               .defaultValue(false))
            .build()
      );
   private final Setting<Boolean> PuZZ3s4mpaTz = this.voxTwgNzyrpaAnP
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("discord-rpc"))
                  .description("Send paver stats to discord activity."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> Njcn1X5he = this.voxTwgNzyrpaAnP
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("enable-freelook"))
                  .description("Freelook when using the paver."))
               .defaultValue(false))
            .build()
      );
   public static HighwayBuilder TJImwK2;
   private boolean PVlYDeNipUe = false;
   private int npfimrDr0hky = 0;

   public HighwayBuilder() {
      super(musheor.AUTOMATION, "HighwayBuilder", "Automated highway builder for 2b2t.");
      TJImwK2 = this;
   }

   public WWidget getWidget(GuiTheme theme) {
      WVerticalList list = theme.verticalList();
      ((WButton)list.add(theme.button("Configure E-Chest slots")).widget()).action = () -> this.i2Nu6XHKic
         .setScreen(new EchestConfigScreen(this.i2Nu6XHKic.currentScreen));
      return list;
   }

   public NbtCompound toTag() {
      NbtCompound tag = super.toTag();
      tag.putInt("echestSlotBlacklist", gVOhtBej1x.fStlZIvY9lS8());
      return tag;
   }

   public Module fromTag(NbtCompound tag) {
      Module module = super.fromTag(tag);
      gVOhtBej1x.i2Nu6XHKic(VersionHelper.get().getInt(tag, "echestSlotBlacklist", 0));
      return module;
   }

   public void onActivate() {
      this.PVlYDeNipUe = true;
      this.npfimrDr0hky = 0;
      U6PQSXqW.fStlZIvY9lS8().TJImwK2(0);
   }

   private void JAj4YwE2MVx() {
      U6PQSXqW state = U6PQSXqW.fStlZIvY9lS8();
      state.UPNmQKi0eiVUue();
      boolean noPavementBlockPresent = this.FVjbhYkk53UT.get() == Blocks.OBSIDIAN && PathKit.i2Nu6XHKic(V7zKJ4n6D().asItem()) < 1;
      boolean noPickaxePresent = PathKit.TJImwK2(false) < 1;
      if (this.xileXsbo.get() != HighwayBuilder.BuildMode.fStlZIvY9lS8 || !noPavementBlockPresent && !noPickaxePresent) {
         boolean noNonSilkPickaxePresent = PathKit.TJImwK2(true) < 1;
         if (this.xileXsbo.get() == HighwayBuilder.BuildMode.QfZy2gWnKInxFPr) {
            if (noNonSilkPickaxePresent) {
               this.error("No pickaxe found in inventory...!", new Object[0]);
            }

            irzXX.TJImwK2();
         } else {
            this.PVlYDeNipUe = false;
            state.fStlZIvY9lS8(this.i2Nu6XHKic.player.getBlockPos());
            state.TJImwK2(0);
            PathKit.fStlZIvY9lS8(false);
            if (!this.JPp6VyZP9f()) {
               irzXX.TJImwK2();
            } else {
               irzXX.fStlZIvY9lS8(KekNuker.class, "nuker-mode", KekNuker.NukerMode.QfZy2gWnKInxFPr);
               irzXX.fStlZIvY9lS8(KekNuker.class, "range", 5.5);
               this.MR1DKzw();
               state.Njcn1X5he().clear();
               state.Njcn1X5he().add(this.i2Nu6XHKic.player.getBlockPos());
               state.PVlYDeNipUe().clear();
               irzXX.fStlZIvY9lS8(AutoWalk.class, "mode", meteordevelopment.meteorclient.systems.modules.movement.AutoWalk.Mode.Simple);
               irzXX.fStlZIvY9lS8(AutoWalk.class, "simple-direction", Direction.Forwards);
            }
         }
      } else {
         if (noPavementBlockPresent) {
            this.error("No pavement material found in inventory...", new Object[0]);
         }

         if (noPickaxePresent) {
            this.error("No non-silk touch pickaxe found in inventory...!", new Object[0]);
         }

         irzXX.TJImwK2();
      }
   }

   public void onDeactivate() {
      this.PVlYDeNipUe = false;
      JhujBv5.TXWyNb();
      irzXX.fStlZIvY9lS8(false);
      GyhoRo.wSJeYsJcmjNZf();
      fStlZIvY9lS8();
      musheor.utils.U6PQSXqW.QfZy2gWnKInxFPr();
      U6PQSXqW.fStlZIvY9lS8().UPNmQKi0eiVUue();
      this.info("Deactivated.", new Object[0]);
   }

   @EventHandler
   public void fStlZIvY9lS8(Pre event) {
      U6PQSXqW state = U6PQSXqW.fStlZIvY9lS8();
      if (this.i2Nu6XHKic.player == null || this.i2Nu6XHKic.world == null || this.i2Nu6XHKic.interactionManager == null) {
         state.TJImwK2(0);
      } else if (this.i2Nu6XHKic.player.isSpectator()) {
         this.info("Player is likely in queue, waiting to proceed with highwaybuilder state checks...", new Object[0]);
         state.TJImwK2(0);
      } else if (this.PVlYDeNipUe) {
         this.JAj4YwE2MVx();
      } else {
         state.WGmfQKK();
         oyDPiOVBGKG.fStlZIvY9lS8(state.pF16zz4NJECfg7(), V7zKJ4n6D());
         if (state.zbN84Osn9p5lD() != null) {
            irzXX.fStlZIvY9lS8(false);
         }

         state.JAj4YwE2MVx();
         if (state.icQ738dpWr7W() >= 400) {
            state.wSJeYsJcmjNZf(0);
            if ((Boolean)this.PuZZ3s4mpaTz.get()) {
               musheor.utils.U6PQSXqW.TJImwK2();
            } else {
               musheor.utils.U6PQSXqW.QfZy2gWnKInxFPr();
            }
         }

         Module BetterEChestFarmer = Modules.get().get(EchestFarmer.class);
         boolean isGapEating = ((AutoGap)Modules.get().get(AutoGap.class)).isEating();
         if (!TJImwK2() && !isGapEating && !vGAMA89H() && !wSJeYsJcmjNZf() && !irzXX.qhulAUwd() && !state.Nt3xeF()) {
            int enderChestCount = this.FVjbhYkk53UT.get() == Blocks.OBSIDIAN ? PathKit.i2Nu6XHKic(Items.ENDER_CHEST) : 64;
            int obsidianCount = PathKit.i2Nu6XHKic(((Block)this.FVjbhYkk53UT.get()).asItem());
            state.i2Nu6XHKic(PathKit.TJImwK2(false));
            state.vGAMA89H(PathKit.TJImwK2(true));
            if (this.xileXsbo.get() == HighwayBuilder.BuildMode.fStlZIvY9lS8) {
               state.TJImwK2(enderChestCount <= 8 && obsidianCount <= 8 || state.JPp6VyZP9f() < 1 || !QfZy2gWnKInxFPr());
            }

            if (this.xileXsbo.get() == HighwayBuilder.BuildMode.QfZy2gWnKInxFPr) {
               state.TJImwK2(state.MR1DKzw() < 1 || !QfZy2gWnKInxFPr());
            }

            if (state.flWHzEyn()) {
               PathKit.V7zKJ4n6D();
            } else if (state.umvNbb7iD9UpR()) {
               PathKit.QfZy2gWnKInxFPr(state.zXcO5rZ(), PathKit.i2Nu6XHKic);
               MusheorSystem.debug("Restocking shulker from echest %s; Amount: %s", state.zXcO5rZ(), PathKit.i2Nu6XHKic);
            } else if (state.zkcZ4Xkp9Ez7Y()) {
               PathKit.fStlZIvY9lS8(state.zXcO5rZ(), PathKit.i2Nu6XHKic);
               MusheorSystem.debug("Restocking %s; Amount: %s", state.zXcO5rZ(), PathKit.i2Nu6XHKic);
            } else if (state.FVjbhYkk53UT() && !BetterEChestFarmer.isActive() && (Boolean)this.JAj4YwE2MVx.get()) {
               JhujBv5.QfZy2gWnKInxFPr();
               MusheorSystem.debug("Running post-echest farmer...");
            } else if (this.QfZy2gWnKInxFPr(obsidianCount, enderChestCount)
               && (Boolean)this.JAj4YwE2MVx.get()
               && this.xileXsbo.get() == HighwayBuilder.BuildMode.fStlZIvY9lS8) {
               JhujBv5.fStlZIvY9lS8();
               MusheorSystem.debug("Running echest farmer...");
            } else if (!state.j376sIQ()) {
               this.npfimrDr0hky = 0;
               if (this.xileXsbo.get() == HighwayBuilder.BuildMode.fStlZIvY9lS8) {
                  if (this.fStlZIvY9lS8.get() == HighwayBuilder.Mode.fStlZIvY9lS8) {
                     JhujBv5.I3psabmOrOI();
                  } else {
                     switch ((HighwayBuilder.HighwayType)this.nOwp0NYl.get()) {
                        case fStlZIvY9lS8:
                           JhujBv5.TJImwK2();
                           break;
                        case QfZy2gWnKInxFPr:
                           JhujBv5.wSJeYsJcmjNZf();
                     }
                  }
               }

               if (this.xileXsbo.get() == HighwayBuilder.BuildMode.QfZy2gWnKInxFPr) {
                  switch ((HighwayBuilder.HighwayType)this.nOwp0NYl.get()) {
                     case fStlZIvY9lS8:
                        JhujBv5.i2Nu6XHKic();
                        break;
                     case QfZy2gWnKInxFPr:
                        JhujBv5.vGAMA89H();
                  }
               }
            } else if (((Boolean)this.JPp6VyZP9f.get() || (Boolean)this.DqGSCWplcjMi.get()) && this.fStlZIvY9lS8.get() != HighwayBuilder.Mode.TJImwK2) {
               if (!PathKit.QfZy2gWnKInxFPr && !PathKit.wSJeYsJcmjNZf) {
                  if (this.npfimrDr0hky >= 6) {
                     this.fStlZIvY9lS8(enderChestCount, obsidianCount);
                  } else {
                     PathKit.fStlZIvY9lS8 = this.i2Nu6XHKic.player.getBlockPos();
                     irzXX.fStlZIvY9lS8(false);
                     PathKit.QfZy2gWnKInxFPr();
                     if (!PathKit.QfZy2gWnKInxFPr) {
                        this.fStlZIvY9lS8(enderChestCount, obsidianCount);
                     } else {
                        this.npfimrDr0hky++;
                     }
                  }
               }
            } else {
               this.fStlZIvY9lS8(enderChestCount, obsidianCount);
            }
         } else {
            irzXX.fStlZIvY9lS8(false);
         }
      }
   }

   private void fStlZIvY9lS8(int enderChestCount, int obsidianCount) {
      U6PQSXqW state = U6PQSXqW.fStlZIvY9lS8();
      String reason = "Low on materials and restocking failed or is unavailable. Missing: ";
      if (enderChestCount < 8) {
         reason = reason + "Enderchest count: " + enderChestCount + "/8 ";
      }

      if (obsidianCount < 1) {
         reason = reason + "Obsidian count: " + obsidianCount + "/8 ";
      }

      if (!QfZy2gWnKInxFPr()) {
         reason = reason + "Food - golden apples blacklisted in auto-eat module? ";
      }

      if (qhulAUwd() == HighwayBuilder.BuildMode.fStlZIvY9lS8 && state.MR1DKzw() < 1) {
         reason = reason + "Non SilkTouch pickaxe.";
      }

      if (qhulAUwd() == HighwayBuilder.BuildMode.QfZy2gWnKInxFPr && state.JPp6VyZP9f() < 1) {
         reason = reason + "Pickaxe.";
      }

      this.npfimrDr0hky = 0;
      irzXX.TJImwK2();
      if ((Boolean)this.lVX1t0MC.get()) {
         irzXX.fStlZIvY9lS8(reason);
      } else {
         this.error(reason, new Object[0]);
      }
   }

   private boolean QfZy2gWnKInxFPr(int obsidianCount, int echestCount) {
      U6PQSXqW state = U6PQSXqW.fStlZIvY9lS8();
      if (!state.zkcZ4Xkp9Ez7Y() && !state.umvNbb7iD9UpR() && !state.flWHzEyn() && !irzXX.qhulAUwd()) {
         return this.FVjbhYkk53UT.get() != Blocks.OBSIDIAN ? false : echestCount > 8 && PathKit.duJ6sPeetAcBh6P() > 0 && obsidianCount <= 8;
      } else {
         return false;
      }
   }

   private boolean JPp6VyZP9f() {
      assert this.i2Nu6XHKic.player != null;

      U6PQSXqW state = U6PQSXqW.fStlZIvY9lS8();
      state.fStlZIvY9lS8(oyDPiOVBGKG.duJ6sPeetAcBh6P());
      state.fStlZIvY9lS8((MusheorSystem.PlacementType)MusheorSystem.Manager.placementType.get());
      state.fStlZIvY9lS8(this.i2Nu6XHKic.player.getBlockX());
      state.QfZy2gWnKInxFPr(this.i2Nu6XHKic.player.getBlockZ());
      state.fStlZIvY9lS8(VersionHelper.get().getPlayerPos().getX());
      state.QfZy2gWnKInxFPr(VersionHelper.get().getPlayerPos().getZ());
      state.fStlZIvY9lS8(Integer.valueOf(this.i2Nu6XHKic.player.getBlockX()));
      state.QfZy2gWnKInxFPr(Integer.valueOf(this.i2Nu6XHKic.player.getBlockY()));
      state.TJImwK2(Integer.valueOf(this.i2Nu6XHKic.player.getBlockZ()));
      if (this.fStlZIvY9lS8.get() == HighwayBuilder.Mode.fStlZIvY9lS8) {
         obf.Cat.gVOhtBej1x detected = obf.Cat.fStlZIvY9lS8(
            this.i2Nu6XHKic.player.getBlockX(), this.i2Nu6XHKic.player.getBlockZ(), this.i2Nu6XHKic.player.getBlockY(), state.TJImwK2()
         );
         if (detected == null) {
            this.error("Auto mode could not detect a highway. Stand on a highway or use Semi mode.", new Object[0]);
            return false;
         }

         state.fStlZIvY9lS8(detected);
         state.fStlZIvY9lS8(detected.I3psabmOrOI);
         state.QfZy2gWnKInxFPr(detected.duJ6sPeetAcBh6P);
         state.fStlZIvY9lS8(detected.BrmKXSYhWhqoB1);
         state.QfZy2gWnKInxFPr(detected.TXWyNb);
         state.fStlZIvY9lS8(Integer.valueOf(detected.I3psabmOrOI));
         state.TJImwK2(Integer.valueOf(detected.duJ6sPeetAcBh6P));
         this.info("Detected: §b" + detected.QfZy2gWnKInxFPr + "§r | Width: §e" + detected.vGAMA89H + "§r | Dir: §a" + detected.i2Nu6XHKic, new Object[0]);
      }

      return true;
   }

   private void MR1DKzw() {
      Module KekNuker = Modules.get().get(KekNuker.class);
      Module AutoEat = Modules.get().get(AutoEat.class);
      Module AutoGap = Modules.get().get(AutoGap.class);
      Module KillAura = Modules.get().get(KillAura.class);
      Module SourceRemover = Modules.get().get(SourceRemover.class);
      Module InventoryCleaner = Modules.get().get(InventoryCleaner.class);
      Module HotbarReplenish = Modules.get().get(HotbarReplenish.class);
      Module FreeLook = Modules.get().get(FreeLook.class);
      if ((Boolean)this.QfZy2gWnKInxFPr.get() && !KekNuker.isActive()) {
         KekNuker.toggle();
      }

      if ((Boolean)this.VIbihOaIVyhvJw.get() && !AutoEat.isActive()) {
         AutoEat.toggle();
      }

      if ((Boolean)this.RE8q1TMDRWx.get() && !AutoGap.isActive()) {
         AutoGap.toggle();
      }

      if ((Boolean)this.HomZ7BbhM7wB.get() && !KillAura.isActive()) {
         KillAura.toggle();
      }

      if ((Boolean)this.bDU9FLOzvL.get() && !InventoryCleaner.isActive()) {
         InventoryCleaner.toggle();
      }

      if ((Boolean)this.Njcn1X5he.get() && !FreeLook.isActive()) {
         irzXX.wSJeYsJcmjNZf();
      }

      if ((Boolean)this.F4hivB.get() && !HotbarReplenish.isActive()) {
         HotbarReplenish.toggle();
      }

      if ((Boolean)this.PuZZ3s4mpaTz.get()) {
         musheor.utils.U6PQSXqW.fStlZIvY9lS8();
      }

      if ((Boolean)this.xarqg2oUiwotDG4.get() && !SourceRemover.isActive()) {
         SourceRemover.toggle();
      }
   }

   public static void fStlZIvY9lS8() {
      Module[] alwaysDisableModules = new Module[]{
         Modules.get().get(EchestFarmer.class),
         Modules.get().get(KekNuker.class),
         Modules.get().get(AutoWalk.class),
         Modules.get().get(HotbarReplenish.class),
         Modules.get().get(FreeLook.class),
         Modules.get().get(SourceRemover.class),
         Modules.get().get(InventoryCleaner.class),
         Modules.get().get(KekBounce.class)
      };

      for (Module module : alwaysDisableModules) {
         if (module.isActive()) {
            module.toggle();
         }
      }

      Module KillAura = Modules.get().get(KillAura.class);
      Module AutoEat = Modules.get().get(AutoEat.class);
      Module AutoGap = Modules.get().get(AutoGap.class);
      if ((Boolean)TJImwK2.HomZ7BbhM7wB.get() && KillAura.isActive()) {
         KillAura.toggle();
      }

      if ((Boolean)TJImwK2.VIbihOaIVyhvJw.get() && AutoEat.isActive()) {
         AutoEat.toggle();
      }

      if ((Boolean)TJImwK2.RE8q1TMDRWx.get() && AutoGap.isActive()) {
         AutoGap.toggle();
      }
   }

   public static boolean QfZy2gWnKInxFPr() {
      List<Item> blacklistedFood = (List<Item>)((AutoEat)Modules.get().get(AutoEat.class)).blacklist.get();

      for (int i = 0; i < Objects.requireNonNull(MinecraftClient.getInstance().player).getInventory().main.size(); i++) {
         ItemStack stack = MinecraftClient.getInstance().player.getInventory().getStack(i);
         if (stack.getComponents().contains(DataComponentTypes.FOOD)
            && !(stack.getComponents() instanceof ShulkerBoxBlock)
            && !blacklistedFood.contains(stack.getItem())) {
            return true;
         }

         if (stack.getItem() == Items.ENCHANTED_GOLDEN_APPLE && !blacklistedFood.contains(stack.getItem())) {
            return true;
         }
      }

      return false;
   }

   public static void fStlZIvY9lS8(BlockState s) {
      U6PQSXqW state = U6PQSXqW.fStlZIvY9lS8();
      if (s.getBlock() == Blocks.OBSIDIAN) {
         state.VIbihOaIVyhvJw();
      }

      if (s.getBlock() == Blocks.NETHERRACK) {
         state.vLRVDxlh();
      }

      if (s.getBlock() == Blocks.ENDER_CHEST) {
         state.lVX1t0MC();
      }
   }

   public static boolean TJImwK2() {
      return ((AutoEat)Modules.get().get(AutoEat.class)).eating || ((AutoGap)Modules.get().get(AutoGap.class)).isEating();
   }

   public static boolean wSJeYsJcmjNZf() {
      return ((KillAura)Modules.get().get(KillAura.class)).attacking;
   }

   public static oyDPiOVBGKG.gVOhtBej1x i2Nu6XHKic() {
      return U6PQSXqW.fStlZIvY9lS8().TJImwK2();
   }

   public static boolean vGAMA89H() {
      return EchestFarmer.QfZy2gWnKInxFPr.isActive();
   }

   public static HighwayBuilder.BuildMode qhulAUwd() {
      return (HighwayBuilder.BuildMode)TJImwK2.xileXsbo.get();
   }

   public static HighwayBuilder.Mode BrmKXSYhWhqoB1() {
      return (HighwayBuilder.Mode)TJImwK2.fStlZIvY9lS8.get();
   }

   public static HighwayBuilder.HighwayType TXWyNb() {
      if (TJImwK2.fStlZIvY9lS8.get() == HighwayBuilder.Mode.fStlZIvY9lS8) {
         obf.Cat.gVOhtBej1x d = U6PQSXqW.fStlZIvY9lS8().npfimrDr0hky();
         if (d != null) {
            return d.wSJeYsJcmjNZf;
         }
      }

      return (HighwayBuilder.HighwayType)TJImwK2.nOwp0NYl.get();
   }

   public static int I3psabmOrOI() {
      if (TJImwK2.fStlZIvY9lS8.get() == HighwayBuilder.Mode.fStlZIvY9lS8) {
         obf.Cat.gVOhtBej1x d = U6PQSXqW.fStlZIvY9lS8().npfimrDr0hky();
         if (d != null) {
            return d.vGAMA89H;
         }
      }

      return (Integer)TJImwK2.umvNbb7iD9UpR.get();
   }

   public static boolean duJ6sPeetAcBh6P() {
      return (Boolean)TJImwK2.vLRVDxlh.get();
   }

   public static boolean j376sIQ() {
      return (Boolean)TJImwK2.WGmfQKK.get();
   }

   public static Block V7zKJ4n6D() {
      return (Block)TJImwK2.FVjbhYkk53UT.get();
   }

   public static boolean voxTwgNzyrpaAnP() {
      return (Boolean)TJImwK2.ZlNrrNcv3Ylc.get();
   }

   public static boolean xileXsbo() {
      return (Boolean)TJImwK2.EVSMbf.get();
   }

   public static boolean nOwp0NYl() {
      return (Boolean)TJImwK2.pF16zz4NJECfg7.get();
   }

   public static Block umvNbb7iD9UpR() {
      return (Block)TJImwK2.zXcO5rZ.get();
   }

   public static HighwayBuilder.ScaffoldMode zkcZ4Xkp9Ez7Y() {
      return (HighwayBuilder.ScaffoldMode)TJImwK2.Nt3xeF.get();
   }

   public static boolean flWHzEyn() {
      return (Boolean)TJImwK2.jbUe84V1vykfK.get();
   }

   public static boolean FVjbhYkk53UT() {
      return (Boolean)TJImwK2.zbN84Osn9p5lD.get();
   }

   public static boolean Nt3xeF() {
      return (Boolean)TJImwK2.RFflNsLXot.get();
   }

   public static boolean zXcO5rZ() {
      return (Boolean)TJImwK2.icQ738dpWr7W.get();
   }

   public static boolean jbUe84V1vykfK() {
      return (Boolean)TJImwK2.JPp6VyZP9f.get();
   }

   public static boolean zbN84Osn9p5lD() {
      return (Boolean)TJImwK2.MR1DKzw.get();
   }

   public static boolean ZlNrrNcv3Ylc() {
      return (Boolean)TJImwK2.DqGSCWplcjMi.get();
   }

   public static boolean EVSMbf() {
      return (Boolean)TJImwK2.cO1FU7fW.get();
   }

   public static boolean pF16zz4NJECfg7() {
      return (Boolean)TJImwK2.g0iiTDJzfIGXdui.get();
   }

   public static boolean WGmfQKK() {
      return (Boolean)TJImwK2.zkcZ4Xkp9Ez7Y.get() && TJImwK2.xileXsbo.get() == HighwayBuilder.BuildMode.fStlZIvY9lS8;
   }

   public static int icQ738dpWr7W() {
      return (Integer)TJImwK2.flWHzEyn.get();
   }

   @EventHandler
   private void fStlZIvY9lS8(Render3DEvent event) {
      U6PQSXqW state = U6PQSXqW.fStlZIvY9lS8();

      assert this.i2Nu6XHKic.player != null;

      if (state.TJImwK2() != null) {
         if (state.qhulAUwd() != null && state.BrmKXSYhWhqoB1() != null && state.TXWyNb() != null) {
            if (state.I3psabmOrOI() != null && state.duJ6sPeetAcBh6P() != null) {
               if ((Boolean)MusheorSystem.Manager.placeRender.get()) {
                  if (this.xileXsbo.get() == HighwayBuilder.BuildMode.fStlZIvY9lS8) {
                     obf.Cat.gVOhtBej1x detected = obf.Cat.fStlZIvY9lS8(
                        this.i2Nu6XHKic.player.getBlockX(),
                        this.i2Nu6XHKic.player.getBlockZ(),
                        this.i2Nu6XHKic.player.getBlockY(),
                        i2Nu6XHKic()
                     );
                     int railY = state.BrmKXSYhWhqoB1();
                     List<BlockPos> cardinalPositions = new ArrayList<>();

                     for (BlockPos pos : musheor.utils.gVOhtBej1x.fStlZIvY9lS8(3, 2, xileXsbo(), nOwp0NYl())) {
                        if ((detected == null || pos.getY() != railY || !obf.Cat.fStlZIvY9lS8(pos, detected)) && BlockUtils.canPlace(pos, true)
                           )
                         {
                           cardinalPositions.add(pos);
                        }
                     }

                     List<BlockPos> diagonalPositions = new ArrayList<>();

                     for (BlockPos posx : musheor.utils.gVOhtBej1x.TJImwK2(2, 2, xileXsbo(), nOwp0NYl())) {
                        if ((detected == null || posx.getY() != railY || !obf.Cat.fStlZIvY9lS8(posx, detected))
                           && BlockUtils.canPlace(posx, true)) {
                           diagonalPositions.add(posx);
                        }
                     }

                     if (TXWyNb() == HighwayBuilder.HighwayType.fStlZIvY9lS8) {
                        jU8xXhmkg.fStlZIvY9lS8(event, cardinalPositions, V7zKJ4n6D());
                     }

                     if (TXWyNb() == HighwayBuilder.HighwayType.QfZy2gWnKInxFPr) {
                        jU8xXhmkg.fStlZIvY9lS8(event, diagonalPositions, V7zKJ4n6D());
                     }
                  }
               }
            }
         }
      }
   }

   public static enum BuildMode {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr;
   }

   public static enum HighwayType {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr;
   }

   public static enum Mode {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr,
      TJImwK2;
   }

   public static enum ScaffoldMode {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr,
      TJImwK2;
   }
}
