package musheor.modules.automation;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalNear;
import baritone.api.selection.ISelection;
import baritone.api.selection.ISelectionManager;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
import meteordevelopment.meteorclient.systems.modules.combat.AutoTotem;
import meteordevelopment.meteorclient.systems.modules.combat.KillAura;
import meteordevelopment.meteorclient.systems.modules.player.AutoEat;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import musheor.commands.RestockConfig;
import musheor.compat.LitematicaHelper;
import musheor.compat.VersionHelper;
import musheor.musheor;
import musheor.utils.irzXX;
import musheor.utils.manager.RotationManager;
import musheor.utils.manager.PathKit;
import musheor.utils.oyDPiOVBGKG;
import musheor.utils.system.MusheorSystem;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import obf.GyhoRo;
import obf.U6PQSXqW;
import obf.WOGVSBb;
import obf.jU8xXhmkg;

public class Printer extends Module {
   private final SettingGroup fStlZIvY9lS8 = this.settings.getDefaultGroup();
   private final SettingGroup QfZy2gWnKInxFPr = this.settings.createGroup("Placement");
   private final SettingGroup TJImwK2 = this.settings.createGroup("Rotation");
   private final SettingGroup wSJeYsJcmjNZf = this.settings.createGroup("Pathfinding/Restocking");
   private final SettingGroup i2Nu6XHKic = this.settings.createGroup("Render");
   private final SettingGroup vGAMA89H = this.settings.createGroup("Litematica");
   private final MinecraftClient qhulAUwd = MinecraftClient.getInstance();
   private final Long2ObjectMap<Map<BlockPos, BlockState>> BrmKXSYhWhqoB1 = new Long2ObjectOpenHashMap();
   private final List<Pair<BlockPos, BlockState>> TXWyNb = new ArrayList<>();
   private final List<Pair<BlockPos, BlockState>> I3psabmOrOI = new ArrayList<>();
   private int duJ6sPeetAcBh6P;
   private int j376sIQ = 0;
   private int V7zKJ4n6D = 0;
   private int voxTwgNzyrpaAnP = 0;
   private int xileXsbo = 0;
   private boolean nOwp0NYl;
   private int umvNbb7iD9UpR;
   private final Set<BlockPos> zkcZ4Xkp9Ez7Y = new HashSet<>();
   private Item flWHzEyn = null;
   private final List<Item> FVjbhYkk53UT = new ArrayList<>();
   private boolean Nt3xeF = false;
   private int zXcO5rZ = 0;
   private Printer.PlacementStrategy jbUe84V1vykfK = null;
   private Map<Block, Integer> zbN84Osn9p5lD = new HashMap<>();
   private int ZlNrrNcv3Ylc = 0;
   private int EVSMbf = -1;
   private int pF16zz4NJECfg7 = 0;
   private BlockPos WGmfQKK = null;
   private BlockPos icQ738dpWr7W = null;
   private boolean JAj4YwE2MVx = false;
   private boolean JPp6VyZP9f = false;
   private final Map<BlockPos, Integer> MR1DKzw = new HashMap<>();
   private final Map<BlockPos, Integer> DqGSCWplcjMi = new HashMap<>();
   private final Map<BlockPos, Integer> cO1FU7fW = new HashMap<>();
   private final Set<BlockPos> g0iiTDJzfIGXdui = new HashSet<>();
   private int VIbihOaIVyhvJw = 0;
   private static final Set<net.minecraft.state.property.Property<?>> RE8q1TMDRWx = new HashSet<>(
      Arrays.asList(
         Properties.FACING, Properties.HORIZONTAL_FACING, Properties.BLOCK_HALF, Properties.SLAB_TYPE, Properties.AXIS, Properties.HOPPER_FACING
      )
   );
   private static final Direction[] vLRVDxlh = new Direction[]{
      Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST
   };
   private static final float[] HomZ7BbhM7wB = new float[]{-90.0F, 0.0F, 90.0F};
   private static final double[] lVX1t0MC = new double[]{0.25, 0.75};
   private final Map<BlockState, Printer.PlacementStrategy> xarqg2oUiwotDG4 = new HashMap<>();
   private static Method RFflNsLXot = null;
   private final Setting<Printer.SelectionType> F4hivB = this.fStlZIvY9lS8
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("selection-type")).description("Get selection from litematica or baritone"))
               .defaultValue(Printer.SelectionType.QfZy2gWnKInxFPr))
            .build()
      );
   private final Setting<Boolean> bDU9FLOzvL = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("pause-when-eating"))
                  .description("Pauses the printing process when the player eats (only when using meteor's auto-eat)"))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> PuZZ3s4mpaTz = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("pause-on-aura"))
                  .description("Pauses the printing process when the player is killing mobs using killaura"))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> Njcn1X5he = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("fix-desync"))
                  .description("Attempts to fix your offhand / inventory desyncs from getting you stuck"))
               .defaultValue(false))
            .build()
      );
   private final Setting<Printer.LayerType> PVlYDeNipUe = this.QfZy2gWnKInxFPr
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("layer-type")).description("Choose how layering is handled when printing"))
               .defaultValue(Printer.LayerType.fStlZIvY9lS8))
            .build()
      );
   private final Setting<List<Block>> npfimrDr0hky = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.BlockListSetting.Builder)((meteordevelopment.meteorclient.settings.BlockListSetting.Builder)new meteordevelopment.meteorclient.settings.BlockListSetting.Builder()
                  .name("ignored-block-list"))
               .description("Avoids placing blocks configured in this list"))
            .defaultValue(new Block[0])
            .build()
      );
   private final Setting<Block> UPNmQKi0eiVUue = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.BlockSetting.Builder)((meteordevelopment.meteorclient.settings.BlockSetting.Builder)((meteordevelopment.meteorclient.settings.BlockSetting.Builder)((meteordevelopment.meteorclient.settings.BlockSetting.Builder)new meteordevelopment.meteorclient.settings.BlockSetting.Builder()
                        .name("block"))
                     .description("What block to place (used for Baritone mode)."))
                  .defaultValue(Blocks.OBSIDIAN))
               .visible(() -> this.F4hivB.get() == Printer.SelectionType.fStlZIvY9lS8))
            .build()
      );
   private final Setting<Boolean> UsQcxwgBUuV = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("only-air"))
                  .description("Places blocks only when the desired location is an air block (meaning it doesn't replace non-solid blocks)"))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> yYqDk4cMM0 = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("gravity-check"))
                  .description("Skip gravity-affected blocks (sand, gravel, etc.) if there is no solid block below them to prevent unwanted falling."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> X9ysJTxpWn2 = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("set-correct-states"))
                  .description("Sets the correct states of a block by interacting with it (e.g: opening trapdoors, setting repeater ticks...)"))
               .defaultValue(true))
            .build()
      );
   private final Setting<Integer> H7J5bawM = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("set-state-delay"))
                     .description("Ticks to wait between interacting with a block to achieve the desired state."))
                  .defaultValue(0))
               .sliderRange(0, 10)
               .visible(this.X9ysJTxpWn2::get))
            .build()
      );
   private final Setting<List<Block>> Uag2UiCQCqA = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.BlockListSetting.Builder)((meteordevelopment.meteorclient.settings.BlockListSetting.Builder)((meteordevelopment.meteorclient.settings.BlockListSetting.Builder)new meteordevelopment.meteorclient.settings.BlockListSetting.Builder()
                     .name("ignored-blockstates-list"))
                  .description("Which blocks to ignore or avoid setting the correct state for (e.g: note blocks, doors...)"))
               .defaultValue(new Block[0])
               .visible(this.X9ysJTxpWn2::get))
            .build()
      );
   private final Setting<Printer.RotationType> Kl8YPWLVNx7VE = this.TJImwK2
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("rotation-type"))
                  .description("Automatically rotate the camera to the correct direction when placing directional blocks."))
               .defaultValue(Printer.RotationType.QfZy2gWnKInxFPr))
            .build()
      );
   private final Setting<Integer> ez9qLff15LVy = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("rotation-delay"))
                     .description("Ticks to wait after rotating before attempting placement (allows rotation to settle server-side)."))
                  .defaultValue(0))
               .min(0)
               .sliderMax(10)
               .visible(() -> this.Kl8YPWLVNx7VE.get() == Printer.RotationType.QfZy2gWnKInxFPr))
            .build()
      );
   private final Setting<Boolean> pAwSMtP8 = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("auto-pathfinding"))
                  .description("Basically baritone building but better..."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Integer> GSmbIrINgvYzyhK = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("pathfind-chunk-range"))
                     .description("The maximum distance in chunks to search for a block to pathfind to and place"))
                  .defaultValue(8))
               .visible(this.pAwSMtP8::get))
            .build()
      );
   private final Setting<Printer.RestockType> dje1llF2o = this.wSJeYsJcmjNZf
      .add(
         ((Builder)((Builder)((Builder)((Builder)new Builder().name("auto-restock"))
                     .description("Automatically restock more materials from either configured containers or from shulkers you have on you"))
                  .defaultValue(Printer.RestockType.fStlZIvY9lS8))
               .visible(this.pAwSMtP8::get))
            .build()
      );
   private final Setting<Boolean> VLEShZ5mkNBQ4PS0 = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("play-sound"))
                     .description("Plays a sound when its finished building or failed"))
                  .defaultValue(true))
               .visible(this.pAwSMtP8::get))
            .build()
      );
   private final Setting<Boolean> vijg33HMhCnyX = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("debug-mode"))
                  .description("Logs why each nearby candidate is rejected from placement, once per second"))
               .defaultValue(false))
            .build()
      );
   private int oF40kxwxfq4ZX = 0;
   private final Setting<Boolean> jAZp22H8V = this.i2Nu6XHKic
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("render"))
                  .description("Renders placement positions"))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> U1qL6cLGWxa = this.i2Nu6XHKic
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("render-placeable-only"))
                  .description("Only renders blocks that are placeable or stuff that can be replaced"))
               .defaultValue(true))
            .build()
      );
   private final Setting<Integer> ZSlhR16SSPV = this.i2Nu6XHKic
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("render-radius"))
                     .description("Only renders placeable blocks in a specified radius (lower if you experience FPS drops)"))
                  .defaultValue(16))
               .sliderMin(2)
               .sliderMax(128)
               .visible(MusheorSystem.Manager.placeRender::get))
            .build()
      );
   private final Setting<Integer> pq4J656cQwYF = this.i2Nu6XHKic
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("render-max-results"))
                  .description("How many blocks are allowed to be rendered at once (increasing can cause lag)"))
               .defaultValue(2000))
            .sliderRange(50, 10000)
            .build()
      );
   private final Setting<String> QMnoHQWxY8gY = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.StringSetting.Builder)((meteordevelopment.meteorclient.settings.StringSetting.Builder)((meteordevelopment.meteorclient.settings.StringSetting.Builder)((meteordevelopment.meteorclient.settings.StringSetting.Builder)new meteordevelopment.meteorclient.settings.StringSetting.Builder()
                        .name("schematic-name"))
                     .description("The name of the schematic you want to place"))
                  .visible(() -> LitematicaHelper.isLoaded() && this.F4hivB.get() == Printer.SelectionType.QfZy2gWnKInxFPr))
               .defaultValue(""))
            .build()
      );
   private final Setting<BlockPos> auOYKcACfh9b = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.BlockPosSetting.Builder)((meteordevelopment.meteorclient.settings.BlockPosSetting.Builder)((meteordevelopment.meteorclient.settings.BlockPosSetting.Builder)((meteordevelopment.meteorclient.settings.BlockPosSetting.Builder)new meteordevelopment.meteorclient.settings.BlockPosSetting.Builder()
                        .name("schematic-pos"))
                     .description("The position of the schematic you want to place"))
                  .visible(() -> LitematicaHelper.isLoaded() && this.F4hivB.get() == Printer.SelectionType.QfZy2gWnKInxFPr && this.Nt3xeF))
               .defaultValue(new BlockPos(0, 0, 0)))
            .build()
      );

   private static BlockState fStlZIvY9lS8(Block block, ItemPlacementContext ctx) {
      try {
         if (RFflNsLXot == null) {
            for (Method m : Block.class.getMethods()) {
               if (m.getParameterCount() == 1
                  && BlockState.class.isAssignableFrom(m.getReturnType())
                  && m.getParameterTypes()[0].isAssignableFrom(ItemPlacementContext.class)) {
                  RFflNsLXot = m;
                  break;
               }
            }

            if (RFflNsLXot == null) {
               for (Class<?> clazz = Block.class; clazz != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
                  for (Method mx : clazz.getDeclaredMethods()) {
                     if (mx.getParameterCount() == 1
                        && BlockState.class.isAssignableFrom(mx.getReturnType())
                        && mx.getParameterTypes()[0].isAssignableFrom(ItemPlacementContext.class)) {
                        mx.setAccessible(true);
                        RFflNsLXot = mx;
                        return RFflNsLXot != null ? (BlockState)RFflNsLXot.invoke(block, ctx) : null;
                     }
                  }
               }
            }
         }

         return RFflNsLXot != null ? (BlockState)RFflNsLXot.invoke(block, ctx) : null;
      } catch (Exception var7) {
         return null;
      }
   }

   public Printer() {
      super(musheor.AUTOMATION, "kek-printer", "Places blocks from schematics or fills baritone selections pretty damn fast");
   }

   public WWidget getWidget(GuiTheme theme) {
      WVerticalList list = theme.verticalList();
      WButton loadAndPlaceSchematic = (WButton)list.add(theme.button("Load and place schematic")).widget();
      loadAndPlaceSchematic.action = () -> {
         if (this.qhulAUwd.player != null && this.qhulAUwd.world != null) {
            if (!LitematicaHelper.isLoaded()) {
               this.error("Litematica is not installed", new Object[0]);
            } else {
               String name = (String)this.QMnoHQWxY8gY.get();
               BlockPos pos = (BlockPos)this.auOYKcACfh9b.get();
               if (name.isEmpty()) {
                  this.error("No schematic name specified", new Object[0]);
               } else {
                  if (LitematicaHelper.get().ensureSchematicAt(name, pos)) {
                     this.info("Loaded schematic: §b%s", new Object[]{name});
                  }
               }
            }
         }
      };
      WButton clearPlacement = (WButton)list.add(theme.button("Remove placement")).widget();
      clearPlacement.action = () -> {
         if (LitematicaHelper.isLoaded()) {
            LitematicaHelper.get().clearAllPlacements();
         }
      };
      WButton toggleSettingVisibility = (WButton)list.add(theme.button("Toggle BlockPos visibility")).widget();
      toggleSettingVisibility.action = () -> {
         this.Nt3xeF = !this.Nt3xeF;
         toggleSettingVisibility.set(this.Nt3xeF ? "Hide Coordinates" : "Show Coordinates");
         this.auOYKcACfh9b.onChanged();
      };
      return list;
   }

   private void fStlZIvY9lS8(BlockPos pos, BlockState targetState) {
      long chunkKey = ChunkPos.toLong(pos.getX() >> 4, pos.getZ() >> 4);
      ((Map)this.BrmKXSYhWhqoB1.computeIfAbsent(chunkKey, k -> new HashMap())).put(pos, targetState);
   }

   private void fStlZIvY9lS8(ISelection selection) {
      if (selection != null && this.qhulAUwd.world != null) {
         BlockPos min = selection.min();
         BlockPos max = selection.max();

         for (int x = min.getX(); x <= max.getX(); x++) {
            for (int y = min.getY(); y <= max.getY(); y++) {
               for (int z = min.getZ(); z <= max.getZ(); z++) {
                  BlockPos pos = new BlockPos(x, y, z);
                  if (this.UsQcxwgBUuV.get() ? this.qhulAUwd.world.getBlockState(pos).getBlock() instanceof AirBlock : BlockUtils.canPlace(pos, false)
                     )
                   {
                     this.fStlZIvY9lS8(pos, ((Block)this.UPNmQKi0eiVUue.get()).getDefaultState());
                  }
               }
            }
         }
      }
   }

   private boolean fStlZIvY9lS8() {
      return this.Kl8YPWLVNx7VE.get() == Printer.RotationType.QfZy2gWnKInxFPr;
   }

   public void onActivate() {
      this.BrmKXSYhWhqoB1.clear();
      this.FVjbhYkk53UT.clear();
      U6PQSXqW.fStlZIvY9lS8().PVlYDeNipUe().clear();
      this.duJ6sPeetAcBh6P = 0;
      this.voxTwgNzyrpaAnP = 0;
      this.zXcO5rZ = 0;
      this.jbUe84V1vykfK = null;
      this.xarqg2oUiwotDG4.clear();
      this.xileXsbo = 0;
      this.umvNbb7iD9UpR = 0;
      this.nOwp0NYl = false;
      this.zbN84Osn9p5lD = new HashMap<>();
      this.ZlNrrNcv3Ylc = 0;
      this.EVSMbf = -1;
      this.pF16zz4NJECfg7 = 0;
      this.WGmfQKK = null;
      this.icQ738dpWr7W = null;
      this.JAj4YwE2MVx = false;
      this.JPp6VyZP9f = false;
      this.QfZy2gWnKInxFPr();
      if (this.F4hivB.get() == Printer.SelectionType.fStlZIvY9lS8) {
         IBaritone baritone = BaritoneAPI.getProvider().getPrimaryBaritone();
         ISelectionManager selectionManager = baritone.getSelectionManager();
         if (selectionManager.getSelections() == null) {
            this.error("No Baritone selection found.", new Object[0]);
            this.toggle();
            return;
         }

         for (ISelection selection : selectionManager.getSelections()) {
            this.fStlZIvY9lS8(selection);
         }
      }

      if (this.F4hivB.get() == Printer.SelectionType.QfZy2gWnKInxFPr) {
         if (!LitematicaHelper.isLoaded()) {
            this.error("Litematica is not installed. Install Litematica or switch to Baritone mode.", new Object[0]);
            this.toggle();
            return;
         }

         if (!LitematicaHelper.get().verifySchematic()) {
            this.error("Invalid or missing schematic", new Object[0]);
            this.toggle();
            return;
         }

         this.zbN84Osn9p5lD = LitematicaHelper.get().getRemainingMaterialCounts((List<Block>)this.npfimrDr0hky.get(), (Boolean)this.UsQcxwgBUuV.get());
         int total = this.zbN84Osn9p5lD.values().stream().mapToInt(Integer::intValue).sum();
         this.info("Schematic ready — §b" + total + "§r blocks remaining to place", new Object[0]);
      }
   }

   public void onDeactivate() {
      irzXX.fStlZIvY9lS8();
      RotationManager.release(10);
      this.I3psabmOrOI.clear();
      this.zbN84Osn9p5lD.clear();
      this.ZlNrrNcv3Ylc = 0;
      this.EVSMbf = -1;
      this.pF16zz4NJECfg7 = 0;
      this.WGmfQKK = null;
      this.icQ738dpWr7W = null;
      this.JAj4YwE2MVx = false;
      this.JPp6VyZP9f = false;
      this.QfZy2gWnKInxFPr();
      this.j376sIQ = 0;
      this.voxTwgNzyrpaAnP = 0;
      this.V7zKJ4n6D = 0;
   }

   private void QfZy2gWnKInxFPr() {
      this.MR1DKzw.clear();
      this.DqGSCWplcjMi.clear();
      this.cO1FU7fW.clear();
      this.g0iiTDJzfIGXdui.clear();
      this.VIbihOaIVyhvJw = 0;
   }

   @EventHandler
   private void fStlZIvY9lS8(Pre event) {
      if (this.qhulAUwd.player != null && this.qhulAUwd.world != null) {
         U6PQSXqW state = U6PQSXqW.fStlZIvY9lS8();
         this.duJ6sPeetAcBh6P++;
         RotationManager.release(10);
         if (this.F4hivB.get() == Printer.SelectionType.QfZy2gWnKInxFPr && (Boolean)this.pAwSMtP8.get() && ++this.ZlNrrNcv3Ylc >= 200) {
            this.ZlNrrNcv3Ylc = 0;
            this.wSJeYsJcmjNZf();
         }

         U6PQSXqW.fStlZIvY9lS8()
            .PVlYDeNipUe()
            .entrySet()
            .removeIf(entryx -> this.duJ6sPeetAcBh6P - (Integer)entryx.getValue() > (Integer)MusheorSystem.Manager.placementTimeout.get());
         if (!(Boolean)this.bDU9FLOzvL.get() || !((AutoEat)Modules.get().get(AutoEat.class)).eating) {
            if (!(Boolean)this.PuZZ3s4mpaTz.get() || !((KillAura)Modules.get().get(KillAura.class)).attacking) {
               if ((Boolean)this.Njcn1X5he.get()) {
                  if (this.nOwp0NYl) {
                     this.nOwp0NYl = false;
                     VersionHelper.get().syncInventory();
                  } else if (this.qhulAUwd.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING
                     && ((AutoTotem)Modules.get().get(AutoTotem.class)).isActive()) {
                     this.umvNbb7iD9UpR++;
                     if (this.umvNbb7iD9UpR >= 20) {
                        this.nOwp0NYl = true;
                        this.umvNbb7iD9UpR = 0;
                     }
                  }
               }

               this.TXWyNb.clear();
               if (this.F4hivB.get() == Printer.SelectionType.QfZy2gWnKInxFPr) {
                  BlockPos playerPos = this.qhulAUwd.player.getBlockPos();
                  int pRange = (int)Math.ceil((Double)MusheorSystem.Manager.placementRange.get()) + 1;
                  Map<BlockPos, BlockState> placementCandidates = LitematicaHelper.get()
                     .getBlocksInBox(
                        playerPos.add(-pRange, -pRange, -pRange),
                        playerPos.add(pRange, pRange, pRange),
                        (Boolean)this.UsQcxwgBUuV.get(),
                        (List<Block>)this.npfimrDr0hky.get(),
                        1500
                     );
                  boolean doDebug = (Boolean)this.vijg33HMhCnyX.get() && ++this.oF40kxwxfq4ZX >= 20;
                  if (doDebug) {
                     this.oF40kxwxfq4ZX = 0;
                  }

                  if (doDebug && placementCandidates.isEmpty()) {
                     this.info("[DBG] No schematic candidates in box (pRange=%d)", new Object[]{pRange});
                  }

                  BlockPos playerFeet = this.qhulAUwd.player.getBlockPos();

                  for (Entry<BlockPos, BlockState> entry : placementCandidates.entrySet()) {
                     BlockPos pos = entry.getKey();
                     BlockState targetState = entry.getValue();
                     String blockName = Registries.BLOCK.getId(targetState.getBlock()).getPath();
                     if (PathKit.i2Nu6XHKic(targetState.getBlock().asItem()) <= 0) {
                        if (doDebug) {
                           this.info("[DBG] %s @ %s — skip: no items in inventory", new Object[]{blockName, pos.toShortString()});
                        }
                     } else if (!BlockUtils.canPlace(pos, true)) {
                        if (doDebug) {
                           this.info("[DBG] %s @ %s — skip: BlockUtils.canPlace=false", new Object[]{blockName, pos.toShortString()});
                        }
                     } else if (this.PVlYDeNipUe.get() == Printer.LayerType.QfZy2gWnKInxFPr
                        && pos.getY() >= Math.floor(this.qhulAUwd.player.getY())) {
                        if (doDebug) {
                           this.info("[DBG] %s @ %s — skip: above feet layer", new Object[]{blockName, pos.toShortString()});
                        }
                     } else if (this.PVlYDeNipUe.get() == Printer.LayerType.TJImwK2 && !LitematicaHelper.get().isPositionInRenderLayer(pos)) {
                        if (doDebug) {
                           this.info("[DBG] %s @ %s — skip: not in render layer", new Object[]{blockName, pos.toShortString()});
                        }
                     } else if (state.PVlYDeNipUe().containsKey(pos)) {
                        if (doDebug) {
                           this.info(
                              "[DBG] %s @ %s — skip: recent attempt (tick %d, now %d)",
                              new Object[]{blockName, pos.toShortString(), state.PVlYDeNipUe().get(pos), this.duJ6sPeetAcBh6P}
                           );
                        }
                     } else if (!pos.equals(playerFeet) && !pos.equals(playerFeet.up())) {
                        Printer.PlacementStrategy strat = this.fStlZIvY9lS8(targetState);
                        if (!this.fStlZIvY9lS8(pos, strat)) {
                           if (doDebug) {
                              this.info(
                                 "[DBG] %s @ %s — skip: not in placement range (hitDist=%.2f, range=%.1f)",
                                 new Object[]{
                                    blockName,
                                    pos.toShortString(),
                                    Math.sqrt(
                                       this.qhulAUwd.player.getEyePos().squaredDistanceTo(this.fStlZIvY9lS8(pos, strat, this.TJImwK2(strat)).getPos())
                                    ),
                                    MusheorSystem.Manager.placementRange.get()
                                 }
                              );
                           }
                        } else {
                           if (doDebug) {
                              this.info("[DBG] %s @ %s — ADDED to posAndBlocks", new Object[]{blockName, pos.toShortString()});
                           }

                           this.TXWyNb.add(Pair.of(pos, targetState));
                        }
                     } else if (doDebug) {
                        this.info("[DBG] %s @ %s — skip: player standing here", new Object[]{blockName, pos.toShortString()});
                     }
                  }

                  if ((Boolean)this.jAZp22H8V.get()) {
                     int rRange = (Integer)this.ZSlhR16SSPV.get();
                     Map<BlockPos, BlockState> renderCandidates = LitematicaHelper.get()
                        .getBlocksInBox(
                           playerPos.add(-rRange, -rRange, -rRange),
                           playerPos.add(rRange, rRange, rRange),
                           (Boolean)this.UsQcxwgBUuV.get(),
                           (List<Block>)this.npfimrDr0hky.get(),
                           (Integer)this.pq4J656cQwYF.get()
                        );
                     this.I3psabmOrOI.clear();

                     for (Entry<BlockPos, BlockState> entryx : renderCandidates.entrySet()) {
                        if (PathKit.i2Nu6XHKic(entryx.getValue().getBlock().asItem()) > 0
                           && (
                              this.PVlYDeNipUe.get() != Printer.LayerType.QfZy2gWnKInxFPr
                                 || !(entryx.getKey().getY() >= Math.floor(this.qhulAUwd.player.getY()))
                           )
                           && (this.PVlYDeNipUe.get() != Printer.LayerType.TJImwK2 || LitematicaHelper.get().isPositionInRenderLayer(entryx.getKey()))
                           && oyDPiOVBGKG.fStlZIvY9lS8(entryx.getKey(), rRange)) {
                           this.I3psabmOrOI.add(Pair.of(entryx.getKey(), entryx.getValue()));
                        }
                     }
                  }
               } else {
                  this.I3psabmOrOI.clear();
                  ChunkPos playerChunk = this.qhulAUwd.player.getChunkPos();
                  int chunkRange = (Integer)this.GSmbIrINgvYzyhK.get();

                  for (int cx = playerChunk.x - chunkRange; cx <= playerChunk.x + chunkRange; cx++) {
                     for (int cz = playerChunk.z - chunkRange; cz <= playerChunk.z + chunkRange; cz++) {
                        long key = ChunkPos.toLong(cx, cz);
                        Map<BlockPos, BlockState> posMap = (Map<BlockPos, BlockState>)this.BrmKXSYhWhqoB1.get(key);
                        if (posMap != null && !posMap.isEmpty()) {
                           for (Entry<BlockPos, BlockState> entryxx : posMap.entrySet()) {
                              BlockPos pos = entryxx.getKey();
                              BlockState currentState = this.qhulAUwd.world.getBlockState(pos);
                              BlockState targetState = entryxx.getValue();
                              if (PathKit.i2Nu6XHKic(targetState.getBlock().asItem()) > 0
                                 && BlockUtils.canPlace(pos, true)
                                 && (
                                    this.PVlYDeNipUe.get() != Printer.LayerType.QfZy2gWnKInxFPr
                                       || !(pos.getY() >= Math.floor(this.qhulAUwd.player.getY()))
                                 )
                                 && (
                                    this.PVlYDeNipUe.get() != Printer.LayerType.TJImwK2
                                       || LitematicaHelper.isLoaded() && LitematicaHelper.get().isPositionInRenderLayer(pos)
                                 )
                                 && (currentState.getBlock() instanceof AirBlock || !(Boolean)this.UsQcxwgBUuV.get())) {
                                 if ((Boolean)this.jAZp22H8V.get() && oyDPiOVBGKG.fStlZIvY9lS8(pos, ((Integer)this.ZSlhR16SSPV.get()).intValue())) {
                                    this.I3psabmOrOI.add(Pair.of(pos, targetState));
                                 }

                                 if (!state.PVlYDeNipUe().containsKey(pos)
                                    && this.fStlZIvY9lS8(pos, this.fStlZIvY9lS8(targetState))
                                    && this.TXWyNb.stream().noneMatch(pairx -> ((BlockPos)pairx.first()).equals(pos))) {
                                    this.TXWyNb.add(Pair.of(pos, targetState));
                                 }
                              }
                           }

                           if (posMap.isEmpty()) {
                              this.BrmKXSYhWhqoB1.remove(key);
                           }
                        }
                     }
                  }
               }

               this.TXWyNb
                  .sort(
                     Comparator.comparingDouble(
                        pairx -> this.qhulAUwd
                           .player
                           .squaredDistanceTo(
                              ((BlockPos)pairx.first()).getX(),
                              ((BlockPos)pairx.first()).getY(),
                              ((BlockPos)pairx.first()).getZ()
                           )
                     )
                  );
               this.i2Nu6XHKic();
               if (this.dje1llF2o.get() != Printer.RestockType.fStlZIvY9lS8 && (Boolean)this.pAwSMtP8.get()) {
                  Block neededBlock = this.TJImwK2();
                  if (neededBlock == null) {
                     boolean nothingLeft = this.F4hivB.get() == Printer.SelectionType.QfZy2gWnKInxFPr
                        ? this.TXWyNb.isEmpty()
                        : this.TXWyNb.isEmpty() && this.BrmKXSYhWhqoB1.isEmpty();
                     if (nothingLeft) {
                        this.info("All blocks placed!", new Object[0]);
                        if ((Boolean)this.VLEShZ5mkNBQ4PS0.get()) {
                           VersionHelper.get().playSoundPlayer(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE);
                        }

                        this.toggle();
                        return;
                     }

                     if (this.F4hivB.get() == Printer.SelectionType.fStlZIvY9lS8) {
                        this.info("Cannot reach or find more blocks to place", new Object[0]);
                        if ((Boolean)this.VLEShZ5mkNBQ4PS0.get()) {
                           VersionHelper.get().playSoundPlayer(SoundEvents.ENTITY_VILLAGER_NO);
                        }

                        this.toggle();
                        return;
                     }
                  }

                  if (PathKit.i2Nu6XHKic(neededBlock.asItem()) <= 0 && this.FVjbhYkk53UT.isEmpty()) {
                     if (this.F4hivB.get() == Printer.SelectionType.QfZy2gWnKInxFPr && this.zbN84Osn9p5lD.getOrDefault(neededBlock, 0) == 0) {
                        this.zbN84Osn9p5lD.remove(neededBlock);
                        return;
                     }

                     this.fStlZIvY9lS8(neededBlock);
                  }

                  if (this.dje1llF2o.get() == Printer.RestockType.TJImwK2) {
                     if (!this.FVjbhYkk53UT.isEmpty()) {
                        if (GyhoRo.fStlZIvY9lS8()) {
                           GyhoRo.wSJeYsJcmjNZf();
                        }

                        Refill refill = (Refill)Modules.get().get(Refill.class);
                        if (refill.isActive()) {
                           return;
                        }

                        if (this.JPp6VyZP9f) {
                           this.JPp6VyZP9f = false;
                           this.FVjbhYkk53UT.clear();
                           this.j376sIQ = 0;
                           return;
                        }

                        while (!this.FVjbhYkk53UT.isEmpty() && PathKit.vGAMA89H(this.FVjbhYkk53UT.getFirst()) <= 0) {
                           this.FVjbhYkk53UT.removeFirst();
                        }

                        if (this.FVjbhYkk53UT.isEmpty()) {
                           this.info("No shulkers found for needed blocks, disabling...", new Object[0]);
                           if ((Boolean)this.VLEShZ5mkNBQ4PS0.get()) {
                              VersionHelper.get().playSoundPlayer(SoundEvents.ENTITY_VILLAGER_NO);
                           }

                           this.toggle();
                           return;
                        }

                        if (this.j376sIQ <= (Integer)PathKit.I3psabmOrOI.nOwp0NYl.get()) {
                           this.j376sIQ++;
                           return;
                        }

                        refill.fStlZIvY9lS8.set(this.FVjbhYkk53UT.getFirst());
                        refill.toggle();
                        this.JPp6VyZP9f = true;
                        return;
                     }
                  } else if (!this.FVjbhYkk53UT.isEmpty() && this.TXWyNb.isEmpty()) {
                     Item neededItem = this.FVjbhYkk53UT.getFirst();
                     int amount = this.QfZy2gWnKInxFPr(Block.getBlockFromItem(neededItem));
                     if (this.flWHzEyn != neededItem) {
                        this.zkcZ4Xkp9Ez7Y.clear();
                        this.flWHzEyn = neededItem;
                        this.xileXsbo = 0;
                        this.info("Restocking %s", new Object[]{RestockConfig.fStlZIvY9lS8(neededItem)});
                     }

                     if (!RestockConfig.TJImwK2(neededItem)) {
                        if (neededItem == neededBlock.asItem()) {
                           this.info("Cannot find container for %s, disabling...", new Object[]{RestockConfig.fStlZIvY9lS8(neededItem)});
                           if ((Boolean)this.VLEShZ5mkNBQ4PS0.get()) {
                              VersionHelper.get().playSoundPlayer(SoundEvents.ENTITY_VILLAGER_NO);
                           }

                           this.FVjbhYkk53UT.clear();
                           this.toggle();
                           return;
                        }

                        this.FVjbhYkk53UT.removeFirst();
                        return;
                     }

                     BlockPos container = this.fStlZIvY9lS8(neededItem);
                     if (container == null) {
                        if (neededItem == neededBlock.asItem()) {
                           this.info("All containers for %s are empty, disabling...", new Object[]{RestockConfig.fStlZIvY9lS8(neededItem)});
                           if ((Boolean)this.VLEShZ5mkNBQ4PS0.get()) {
                              VersionHelper.get().playSoundPlayer(SoundEvents.ENTITY_VILLAGER_NO);
                           }

                           this.zkcZ4Xkp9Ez7Y.clear();
                           this.FVjbhYkk53UT.clear();
                           this.toggle();
                           return;
                        }

                        this.FVjbhYkk53UT.removeFirst();
                        return;
                     }

                     if (!oyDPiOVBGKG.TJImwK2(container)) {
                        if (!GyhoRo.fStlZIvY9lS8()) {
                           BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(new GoalNear(container, 2));
                        }

                        return;
                     }

                     irzXX.fStlZIvY9lS8();
                     if (!PathKit.TJImwK2() && this.V7zKJ4n6D == 0) {
                        oyDPiOVBGKG.QfZy2gWnKInxFPr(container);
                        PathKit.fStlZIvY9lS8(container);
                        this.V7zKJ4n6D = 3;
                        return;
                     }

                     if (this.V7zKJ4n6D > 0) {
                        this.V7zKJ4n6D--;
                        return;
                     }

                     if (this.j376sIQ <= (Integer)PathKit.I3psabmOrOI.zkcZ4Xkp9Ez7Y.get()) {
                        this.j376sIQ++;
                        return;
                     }

                     if (!jU8xXhmkg.QfZy2gWnKInxFPr()) {
                        return;
                     }

                     ScreenHandler handler = this.qhulAUwd.player.currentScreenHandler;
                     int containerSize = handler.slots.size() - 36;

                     for (int i = 0; i < containerSize; i++) {
                        ItemStack currentStack = handler.getSlot(i).getStack();
                        if (!currentStack.isEmpty()) {
                           if (currentStack.getItem() == neededItem) {
                              this.xileXsbo = this.xileXsbo + currentStack.getCount();
                              this.qhulAUwd.interactionManager.clickSlot(handler.syncId, i, 0, SlotActionType.QUICK_MOVE, this.qhulAUwd.player);
                           }

                           if (this.xileXsbo >= amount || PathKit.duJ6sPeetAcBh6P() <= 0 || !jU8xXhmkg.QfZy2gWnKInxFPr()) {
                              break;
                           }
                        }
                     }

                     if (this.xileXsbo == 0) {
                        this.zkcZ4Xkp9Ez7Y.add(container);
                     } else if (this.xileXsbo < amount && PathKit.duJ6sPeetAcBh6P() > 0) {
                        this.zkcZ4Xkp9Ez7Y.add(container);
                     } else if (PathKit.duJ6sPeetAcBh6P() > 0) {
                        this.zkcZ4Xkp9Ez7Y.clear();
                        this.FVjbhYkk53UT.removeFirst();
                     }

                     this.j376sIQ = 0;
                     this.V7zKJ4n6D = 0;
                     this.qhulAUwd.player.closeScreen();
                     if (this.FVjbhYkk53UT.isEmpty() || PathKit.duJ6sPeetAcBh6P() <= 0) {
                        this.FVjbhYkk53UT.clear();
                     }

                     return;
                  }
               }

               if ((Boolean)this.pAwSMtP8.get()) {
                  if (this.TXWyNb.isEmpty()) {
                     if (this.JAj4YwE2MVx) {
                        this.EVSMbf = -1;
                        this.pF16zz4NJECfg7 = 0;
                        this.WGmfQKK = null;
                        this.icQ738dpWr7W = null;
                        this.JAj4YwE2MVx = false;
                     }

                     if (this.F4hivB.get() == Printer.SelectionType.QfZy2gWnKInxFPr) {
                        if (this.WGmfQKK != null) {
                           double distToGoal = this.qhulAUwd
                              .player
                              .squaredDistanceTo(this.WGmfQKK.getX() + 0.5, this.WGmfQKK.getY() + 0.5, this.WGmfQKK.getZ() + 0.5);
                           if ((Boolean)this.vijg33HMhCnyX.get()) {
                              this.info(
                                 "[DBG-PF] goal=%s dist=%.1f pathing=%b radius=%d ringIdx=%d",
                                 new Object[]{this.WGmfQKK.toShortString(), Math.sqrt(distToGoal), GyhoRo.fStlZIvY9lS8(), this.EVSMbf, this.pF16zz4NJECfg7}
                              );
                           }

                           if (!GyhoRo.fStlZIvY9lS8()) {
                              if (distToGoal <= 16.0) {
                                 if ((Boolean)this.vijg33HMhCnyX.get()) {
                                    this.info("[DBG-PF] arrived at goal, clearing and re-scanning", new Object[0]);
                                 }

                                 this.WGmfQKK = null;
                                 this.icQ738dpWr7W = null;
                                 this.EVSMbf = -1;
                                 this.pF16zz4NJECfg7 = 0;
                              } else {
                                 GyhoRo.fStlZIvY9lS8(new GoalNear(this.WGmfQKK, 2));
                              }
                           }

                           return;
                        }

                        if (this.EVSMbf == -1) {
                           Block priorityBlock = this.TJImwK2();
                           if (priorityBlock != null
                              && PathKit.i2Nu6XHKic(priorityBlock.asItem()) > 0
                              && this.zbN84Osn9p5lD.getOrDefault(priorityBlock, 0) == 0) {
                              this.zbN84Osn9p5lD.remove(priorityBlock);
                              return;
                           }

                           this.EVSMbf = 0;
                           this.pF16zz4NJECfg7 = 0;
                        }

                        int maxChunkRadius = (Integer)this.GSmbIrINgvYzyhK.get();
                        if (this.EVSMbf > maxChunkRadius) {
                           this.wSJeYsJcmjNZf();
                           Block exhaustedBlock = this.TJImwK2();
                           if (exhaustedBlock != null && this.zbN84Osn9p5lD.getOrDefault(exhaustedBlock, 0) == 0) {
                              this.zbN84Osn9p5lD.remove(exhaustedBlock);
                              this.EVSMbf = -1;
                              this.pF16zz4NJECfg7 = 0;
                              this.WGmfQKK = null;
                              this.icQ738dpWr7W = null;
                              return;
                           }

                           if (exhaustedBlock == null) {
                              this.info("All blocks placed!", new Object[0]);
                              if ((Boolean)this.VLEShZ5mkNBQ4PS0.get()) {
                                 VersionHelper.get().playSoundPlayer(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE);
                              }

                              this.toggle();
                           } else {
                              this.info("Cannot reach any blocks to place, disabling...", new Object[0]);
                              if ((Boolean)this.VLEShZ5mkNBQ4PS0.get()) {
                                 VersionHelper.get().playSoundPlayer(SoundEvents.ENTITY_VILLAGER_NO);
                              }

                              this.toggle();
                           }

                           return;
                        }

                        ChunkPos playerChunk = this.qhulAUwd.player.getChunkPos();
                        int currentRingSize = this.EVSMbf == 0 ? 1 : 8 * this.EVSMbf;
                        int chunksThisTick = Math.max(1, (currentRingSize + 3) / 4);

                        for (int bi = 0; bi < chunksThisTick && this.WGmfQKK == null && this.EVSMbf <= maxChunkRadius; bi++) {
                           ChunkPos toScan = this.fStlZIvY9lS8(playerChunk, this.EVSMbf, this.pF16zz4NJECfg7);
                           BlockPos candidate = this.fStlZIvY9lS8(toScan);
                           if (candidate != null
                              && (
                                 this.icQ738dpWr7W == null
                                    || this.qhulAUwd
                                          .player
                                          .squaredDistanceTo(candidate.getX() + 0.5, candidate.getY() + 0.5, candidate.getZ() + 0.5)
                                       < this.qhulAUwd
                                          .player
                                          .squaredDistanceTo(
                                             this.icQ738dpWr7W.getX() + 0.5,
                                             this.icQ738dpWr7W.getY() + 0.5,
                                             this.icQ738dpWr7W.getZ() + 0.5
                                          )
                              )) {
                              this.icQ738dpWr7W = candidate;
                           }

                           int ringSize = this.EVSMbf == 0 ? 1 : 8 * this.EVSMbf;
                           this.pF16zz4NJECfg7++;
                           if (this.pF16zz4NJECfg7 >= ringSize) {
                              this.pF16zz4NJECfg7 = 0;
                              this.EVSMbf++;
                              if (this.icQ738dpWr7W != null) {
                                 this.WGmfQKK = this.icQ738dpWr7W;
                                 this.icQ738dpWr7W = null;
                                 if ((Boolean)this.vijg33HMhCnyX.get()) {
                                    this.info(
                                       "[DBG-PF] new goal from ring %d: %s (dist=%.1f)",
                                       new Object[]{
                                          this.EVSMbf - 1,
                                          this.WGmfQKK.toShortString(),
                                          Math.sqrt(
                                             this.qhulAUwd
                                                .player
                                                .squaredDistanceTo(
                                                   this.WGmfQKK.getX() + 0.5, this.WGmfQKK.getY() + 0.5, this.WGmfQKK.getZ() + 0.5
                                                )
                                          )
                                       }
                                    );
                                 }

                                 GyhoRo.fStlZIvY9lS8(new GoalNear(this.WGmfQKK, 2));
                                 return;
                              }

                              currentRingSize = this.EVSMbf == 0 ? 1 : 8 * this.EVSMbf;
                              chunksThisTick = Math.max(1, (currentRingSize + 3) / 4);
                           }
                        }
                     } else if (!this.BrmKXSYhWhqoB1.isEmpty()) {
                        BlockPos closest = this.fStlZIvY9lS8(this.BrmKXSYhWhqoB1);
                        if (closest != null) {
                           if (oyDPiOVBGKG.fStlZIvY9lS8(this.qhulAUwd.player.getBlockPos(), closest) > 2.5) {
                              GyhoRo.fStlZIvY9lS8(new GoalNear(closest, 2));
                              return;
                           }

                           irzXX.fStlZIvY9lS8();
                           if (this.qhulAUwd.player.getBlockPos() == closest) {
                              GyhoRo.fStlZIvY9lS8(new GoalBlock(closest.east()));
                           }
                        }
                     }
                  } else {
                     this.JAj4YwE2MVx = true;
                  }
               }

               if (this.voxTwgNzyrpaAnP > 0) {
                  this.voxTwgNzyrpaAnP--;
               } else {
                  if (!this.TXWyNb.isEmpty()) {
                     Pair<BlockPos, BlockState> firstPair = null;

                     for (Pair<BlockPos, BlockState> candidatex : this.TXWyNb) {
                        if (this.QfZy2gWnKInxFPr((BlockPos)candidatex.first(), (BlockState)candidatex.second())) {
                           firstPair = candidatex;
                           break;
                        }
                     }

                     if ((Boolean)this.vijg33HMhCnyX.get()) {
                        if (firstPair == null) {
                           this.info("[DBG] posAndBlocks has %d entries but all failed canPlaceSafely", new Object[]{this.TXWyNb.size()});
                        } else {
                           this.info(
                              "[DBG] firstPair=%s, swap=%d, rotationTimer=%d, hand=%s",
                              new Object[]{
                                 ((BlockPos)firstPair.first()).toShortString(),
                                 this.voxTwgNzyrpaAnP,
                                 this.zXcO5rZ,
                                 this.qhulAUwd.player.getMainHandStack().getItem()
                              }
                           );
                        }
                     }

                     label578:
                     if (firstPair != null) {
                        BlockState firstState = (BlockState)firstPair.second();
                        Block firstBlock = firstState.getBlock();
                        Printer.PlacementStrategy firstStrat = this.fStlZIvY9lS8(firstState);
                        if (this.zXcO5rZ > 0) {
                           this.zXcO5rZ--;
                           if (this.fStlZIvY9lS8() && this.jbUe84V1vykfK != null) {
                              this.QfZy2gWnKInxFPr(this.jbUe84V1vykfK);
                           }

                           return;
                        }

                        Printer.PlacementStrategy appliedStrategy = this.jbUe84V1vykfK;
                        this.jbUe84V1vykfK = null;
                        boolean rotationApplied = appliedStrategy != null;
                        boolean needsRotation = this.fStlZIvY9lS8() && (firstStrat.TJImwK2() != null || firstStrat.wSJeYsJcmjNZf() != null);
                        if (needsRotation) {
                           if (this.fStlZIvY9lS8()) {
                              if (!rotationApplied) {
                                 this.QfZy2gWnKInxFPr(firstStrat);
                                 this.jbUe84V1vykfK = firstStrat;
                                 this.zXcO5rZ = (Integer)this.ez9qLff15LVy.get();
                                 if ((Boolean)this.vijg33HMhCnyX.get()) {
                                    this.info("[DBG] Waiting for rotation: yaw=%s pitch=%s", new Object[]{firstStrat.TJImwK2(), firstStrat.wSJeYsJcmjNZf()});
                                 }

                                 return;
                              }

                              this.QfZy2gWnKInxFPr(firstStrat);
                           } else if (!this.fStlZIvY9lS8(firstStrat)) {
                              if ((Boolean)this.vijg33HMhCnyX.get()) {
                                 this.info(
                                    "[DBG] Wrong facing for %s — waiting (yaw=%s pitch=%s)",
                                    new Object[]{
                                       Registries.BLOCK.getId(firstBlock).getPath(), firstStrat.TJImwK2(), firstStrat.wSJeYsJcmjNZf()
                                    }
                                 );
                              }

                              this.voxTwgNzyrpaAnP = (Integer)MusheorSystem.Manager.swapDelay.get();
                              return;
                           }
                        }

                        try (obf.irzXX.gVOhtBej1x batch = obf.irzXX.fStlZIvY9lS8(firstBlock, state.QfZy2gWnKInxFPr())) {
                           if (batch.fStlZIvY9lS8()) {
                              for (Pair<BlockPos, BlockState> pair : this.TXWyNb) {
                                 if (!BlockUtils.canPlace((BlockPos)pair.first(), true)) {
                                    if ((Boolean)this.vijg33HMhCnyX.get()) {
                                       this.info(
                                          "[DBG] %s @ %s — inner skip: canPlace=false",
                                          new Object[]{
                                             Registries.BLOCK.getId(((BlockState)pair.second()).getBlock()).getPath(),
                                             ((BlockPos)pair.first()).toShortString()
                                          }
                                       );
                                    }
                                 } else {
                                    Printer.PlacementStrategy pairStrat = this.fStlZIvY9lS8((BlockState)pair.second());
                                    if (!this.fStlZIvY9lS8((BlockPos)pair.first(), pairStrat)) {
                                       if ((Boolean)this.vijg33HMhCnyX.get()) {
                                          this.info(
                                             "[DBG] %s @ %s — inner skip: out of range (hitDist=%.2f, range=%.1f)",
                                             new Object[]{
                                                Registries.BLOCK.getId(((BlockState)pair.second()).getBlock()).getPath(),
                                                ((BlockPos)pair.first()).toShortString(),
                                                Math.sqrt(
                                                   this.qhulAUwd
                                                      .player
                                                      .getEyePos()
                                                      .squaredDistanceTo(
                                                         this.fStlZIvY9lS8((BlockPos)pair.first(), pairStrat, this.TJImwK2(pairStrat)).getPos()
                                                      )
                                                ),
                                                MusheorSystem.Manager.placementRange.get()
                                             }
                                          );
                                       }
                                    } else if (((BlockState)pair.second()).getBlock() != firstBlock) {
                                       if ((Boolean)this.vijg33HMhCnyX.get()) {
                                          this.info(
                                             "[DBG] %s @ %s — inner skip: wrong block type (want %s)",
                                             new Object[]{
                                                Registries.BLOCK.getId(((BlockState)pair.second()).getBlock()).getPath(),
                                                ((BlockPos)pair.first()).toShortString(),
                                                Registries.BLOCK.getId(firstBlock).getPath()
                                             }
                                          );
                                       }
                                    } else if (!this.QfZy2gWnKInxFPr((BlockPos)pair.first(), (BlockState)pair.second())) {
                                       if ((Boolean)this.vijg33HMhCnyX.get()) {
                                          this.info(
                                             "[DBG] %s @ %s — inner skip: canPlaceSafely=false",
                                             new Object[]{
                                                Registries.BLOCK.getId(firstBlock).getPath(), ((BlockPos)pair.first()).toShortString()
                                             }
                                          );
                                       }
                                    } else {
                                       boolean pairNeedsRotation = this.fStlZIvY9lS8() && (pairStrat.TJImwK2() != null || pairStrat.wSJeYsJcmjNZf() != null);
                                       if (pairNeedsRotation) {
                                          boolean mismatch;
                                          if (this.fStlZIvY9lS8() && rotationApplied) {
                                             mismatch = pairStrat.TJImwK2() != appliedStrategy.TJImwK2()
                                                || !Objects.equals(pairStrat.wSJeYsJcmjNZf(), appliedStrategy.wSJeYsJcmjNZf());
                                          } else {
                                             mismatch = !this.fStlZIvY9lS8(pairStrat);
                                          }

                                          if (mismatch) {
                                             if (!this.fStlZIvY9lS8()) {
                                                this.voxTwgNzyrpaAnP = (Integer)MusheorSystem.Manager.swapDelay.get();
                                             }

                                             return;
                                          }
                                       }

                                       if (!jU8xXhmkg.fStlZIvY9lS8()) {
                                          if ((Boolean)this.vijg33HMhCnyX.get()) {
                                             this.info("[DBG] canInteract()=false, skipping placement this tick", new Object[0]);
                                          }

                                          return;
                                       }

                                       boolean freeHit = this.TJImwK2(pairStrat);
                                       BlockHitResult hit = this.fStlZIvY9lS8((BlockPos)pair.first(), pairStrat, freeHit);
                                       if (!batch.fStlZIvY9lS8((BlockPos)pair.first(), hit, freeHit)) {
                                          return;
                                       }

                                       if (((BlockState)pair.second()).getBlock() == Blocks.OBSIDIAN) {
                                          U6PQSXqW.fStlZIvY9lS8().cO1FU7fW();
                                       }

                                       state.PVlYDeNipUe().put((BlockPos)pair.first(), this.duJ6sPeetAcBh6P);
                                       if (this.F4hivB.get() == Printer.SelectionType.QfZy2gWnKInxFPr) {
                                          this.zbN84Osn9p5lD.computeIfPresent(((BlockState)pair.second()).getBlock(), (k, v) -> v <= 1 ? null : v - 1);
                                       }
                                    }
                                 }
                              }
                              break label578;
                           }

                           if ((Boolean)this.vijg33HMhCnyX.get()) {
                              this.info(
                                 "[DBG] Wrong item in hand (%s), switching to %s",
                                 new Object[]{this.qhulAUwd.player.getMainHandStack().getItem(), firstBlock.asItem()}
                              );
                           }

                           this.voxTwgNzyrpaAnP = (Integer)MusheorSystem.Manager.swapDelay.get();
                        }

                        return;
                     }
                  }
               }
            }
         }
      }
   }

   private void fStlZIvY9lS8(Block primaryBlock) {
      this.FVjbhYkk53UT.clear();
      this.FVjbhYkk53UT.add(primaryBlock.asItem());
      Set<Item> seen = new HashSet<>();
      seen.add(primaryBlock.asItem());
      List<Entry<Item, Integer>> others = new ArrayList<>();
      if (this.F4hivB.get() == Printer.SelectionType.QfZy2gWnKInxFPr) {
         for (Entry<Block, Integer> entry : this.zbN84Osn9p5lD.entrySet()) {
            if (entry.getValue() > 0) {
               Item item = entry.getKey().asItem();
               if (!seen.contains(item)) {
                  seen.add(item);
                  if (PathKit.i2Nu6XHKic(item) <= 0 && RestockConfig.TJImwK2(item)) {
                     others.add(Map.entry(item, entry.getValue()));
                  }
               }
            }
         }
      } else {
         ObjectIterator var9 = this.BrmKXSYhWhqoB1.values().iterator();

         while (var9.hasNext()) {
            Map<BlockPos, BlockState> posMap = (Map<BlockPos, BlockState>)var9.next();

            for (BlockState blockState : posMap.values()) {
               Item item = blockState.getBlock().asItem();
               if (!seen.contains(item)) {
                  seen.add(item);
                  if (PathKit.i2Nu6XHKic(item) <= 0 && RestockConfig.TJImwK2(item)) {
                     others.add(Map.entry(item, this.QfZy2gWnKInxFPr(blockState.getBlock())));
                  }
               }
            }
         }
      }

      others.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

      for (Entry<Item, Integer> entryx : others) {
         this.FVjbhYkk53UT.add(entryx.getKey());
      }
   }

   private BlockPos fStlZIvY9lS8(Item item) {
      List<BlockPos> containers = RestockConfig.QfZy2gWnKInxFPr(item);
      BlockPos playerPos = this.qhulAUwd.player.getBlockPos();
      BlockPos closest = null;
      double closestDist = Double.MAX_VALUE;

      for (BlockPos pos : containers) {
         if (!this.zkcZ4Xkp9Ez7Y.contains(pos)) {
            double dist = playerPos.getSquaredDistance(pos);
            if (dist < closestDist) {
               closestDist = dist;
               closest = pos;
            }
         }
      }

      return closest;
   }

   private int QfZy2gWnKInxFPr(Block block) {
      if (this.F4hivB.get() == Printer.SelectionType.QfZy2gWnKInxFPr) {
         return this.zbN84Osn9p5lD.getOrDefault(block, 0);
      } else {
         int count = 0;
         ObjectIterator var3 = this.BrmKXSYhWhqoB1.values().iterator();

         while (var3.hasNext()) {
            Map<BlockPos, BlockState> posMap = (Map<BlockPos, BlockState>)var3.next();

            for (BlockState blockState : posMap.values()) {
               if (blockState.getBlock() == block) {
                  count++;
               }
            }
         }

         return count;
      }
   }

   private Block TJImwK2() {
      Block firstInInventory = null;
      String firstInInventoryName = null;
      Block firstOverall = null;
      String firstOverallName = null;
      Iterable<Block> blockSource;
      if (this.F4hivB.get() == Printer.SelectionType.QfZy2gWnKInxFPr) {
         blockSource = this.zbN84Osn9p5lD.keySet();
      } else {
         Set<Block> fromMap = new HashSet<>();
         ObjectIterator b = this.BrmKXSYhWhqoB1.values().iterator();

         while (b.hasNext()) {
            Map<BlockPos, BlockState> posMap = (Map<BlockPos, BlockState>)b.next();

            for (BlockState s : posMap.values()) {
               fromMap.add(s.getBlock());
            }
         }

         blockSource = fromMap;
      }

      for (Block b : blockSource) {
         String name = Registries.BLOCK.getId(b).getPath();
         if (firstOverall == null || name.compareTo(firstOverallName) < 0) {
            firstOverall = b;
            firstOverallName = name;
         }

         if (PathKit.i2Nu6XHKic(b.asItem()) > 0 && (firstInInventory == null || name.compareTo(firstInInventoryName) < 0)) {
            firstInInventory = b;
            firstInInventoryName = name;
         }
      }

      if (firstInInventory != null) {
         return firstInInventory;
      } else if (firstOverall != null) {
         return firstOverall;
      } else {
         return this.F4hivB.get() == Printer.SelectionType.fStlZIvY9lS8 ? (Block)this.UPNmQKi0eiVUue.get() : null;
      }
   }

   private void wSJeYsJcmjNZf() {
      if (LitematicaHelper.isLoaded() && this.F4hivB.get() == Printer.SelectionType.QfZy2gWnKInxFPr) {
         Map<Block, Integer> fresh = LitematicaHelper.get()
            .getRemainingMaterialCounts((List<Block>)this.npfimrDr0hky.get(), (Boolean)this.UsQcxwgBUuV.get());
         fresh.entrySet().removeIf(e -> e.getValue() <= 0);
         this.zbN84Osn9p5lD = fresh;
      }
   }

   private Printer.PlacementStrategy fStlZIvY9lS8(BlockState targetState) {
      return this.xarqg2oUiwotDG4.computeIfAbsent(targetState, this::QfZy2gWnKInxFPr);
   }

   private Printer.PlacementStrategy QfZy2gWnKInxFPr(BlockState targetState) {
      if (this.qhulAUwd.player != null && this.qhulAUwd.world != null) {
         boolean hasOrientation = RE8q1TMDRWx.stream().anyMatch(targetState::contains);
         if (!hasOrientation) {
            return new Printer.PlacementStrategy(Direction.DOWN, 0.5, null, null, true);
         } else {
            Item item = targetState.getBlock().asItem();
            if (item == Items.AIR) {
               return new Printer.PlacementStrategy(Direction.DOWN, 0.5, null, null, true);
            } else {
               ItemStack stack = new ItemStack(item);
               BlockPos simPos = this.qhulAUwd.player.getBlockPos();
               float savedYaw = this.qhulAUwd.player.getYaw();
               float savedPitch = this.qhulAUwd.player.getPitch();

               try {
                  for (Direction hitDir : Direction.values()) {
                     double[] yFracs = hitDir.getAxis() == Direction.Axis.Y ? new double[]{hitDir == Direction.UP ? 1.0 : 0.0} : lVX1t0MC;

                     for (double hitFracY : yFracs) {
                        Vec3d hitVec = new Vec3d(
                           simPos.getX() + 0.5 + hitDir.getOffsetX() * 0.5,
                           simPos.getY() + hitFracY,
                           simPos.getZ() + 0.5 + hitDir.getOffsetZ() * 0.5
                        );
                        BlockHitResult hit = new BlockHitResult(hitVec, hitDir, simPos, false);
                        boolean anyMatch = false;
                        boolean mismatchFound = false;

                        label292:
                        for (Direction yawDir : vLRVDxlh) {
                           this.qhulAUwd.player.setYaw(this.fStlZIvY9lS8(yawDir));

                           for (float pitch : HomZ7BbhM7wB) {
                              this.qhulAUwd.player.setPitch(pitch);
                              ItemPlacementContext ctx = new ItemPlacementContext(this.qhulAUwd.world, this.qhulAUwd.player, Hand.MAIN_HAND, stack, hit) {};
                              BlockState placed = fStlZIvY9lS8(targetState.getBlock(), ctx);
                              if (placed != null) {
                                 if (!this.fStlZIvY9lS8(placed, targetState)) {
                                    mismatchFound = true;
                                    break label292;
                                 }

                                 anyMatch = true;
                              }
                           }
                        }

                        if (anyMatch && !mismatchFound) {
                           return new Printer.PlacementStrategy(hitDir, hitFracY, null, null, false);
                        }
                     }
                  }

                  for (Direction hitDir : Direction.values()) {
                     double[] yFracs = hitDir.getAxis() == Direction.Axis.Y ? new double[]{hitDir == Direction.UP ? 1.0 : 0.0} : lVX1t0MC;

                     for (double hitFracY : yFracs) {
                        Vec3d hitVec = new Vec3d(
                           simPos.getX() + 0.5 + hitDir.getOffsetX() * 0.5,
                           simPos.getY() + hitFracY,
                           simPos.getZ() + 0.5 + hitDir.getOffsetZ() * 0.5
                        );
                        BlockHitResult hit = new BlockHitResult(hitVec, hitDir, simPos, false);

                        for (Direction yawDir : vLRVDxlh) {
                           this.qhulAUwd.player.setYaw(this.fStlZIvY9lS8(yawDir));

                           for (float pitchx : HomZ7BbhM7wB) {
                              this.qhulAUwd.player.setPitch(pitchx);
                              ItemPlacementContext ctx = new ItemPlacementContext(this.qhulAUwd.world, this.qhulAUwd.player, Hand.MAIN_HAND, stack, hit) {};
                              BlockState placed = fStlZIvY9lS8(targetState.getBlock(), ctx);
                              if (placed != null && this.fStlZIvY9lS8(placed, targetState)) {
                                 boolean needsH = targetState.contains(Properties.HORIZONTAL_FACING)
                                    || targetState.contains(Properties.FACING)
                                       && ((Direction)targetState.get(Properties.FACING)).getAxis().isHorizontal();
                                 boolean pitchMatters = false;

                                 for (float otherPitch : HomZ7BbhM7wB) {
                                    if (otherPitch != pitchx) {
                                       this.qhulAUwd.player.setPitch(otherPitch);
                                       ItemPlacementContext ctx2 = new ItemPlacementContext(this.qhulAUwd.world, this.qhulAUwd.player, Hand.MAIN_HAND, stack, hit) {};
                                       BlockState placed2 = fStlZIvY9lS8(targetState.getBlock(), ctx2);
                                       if (placed2 == null || !this.fStlZIvY9lS8(placed2, targetState)) {
                                          pitchMatters = true;
                                          break;
                                       }
                                    }
                                 }

                                 this.qhulAUwd.player.setPitch(pitchx);
                                 Direction reqYaw = needsH ? yawDir : null;
                                 Float reqPitch = pitchMatters ? pitchx : null;
                                 return new Printer.PlacementStrategy(hitDir, hitFracY, reqYaw, reqPitch, false);
                              }
                           }
                        }
                     }
                  }

                  return new Printer.PlacementStrategy(Direction.DOWN, 0.5, null, null, false);
               } finally {
                  this.qhulAUwd.player.setYaw(savedYaw);
                  this.qhulAUwd.player.setPitch(savedPitch);
               }
            }
         }
      } else {
         return new Printer.PlacementStrategy(Direction.DOWN, 0.5, null, null, true);
      }
   }

   private boolean fStlZIvY9lS8(BlockState placed, BlockState target) {
      if (placed.getBlock() != target.getBlock()) {
         return false;
      } else {
         for (net.minecraft.state.property.Property<?> prop : RE8q1TMDRWx) {
            if (placed.contains(prop) && target.contains(prop) && !placed.get(prop).equals(target.get(prop))) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean fStlZIvY9lS8(Printer.PlacementStrategy strat) {
      if (this.qhulAUwd.player == null) {
         return false;
      } else {
         float effYaw = RotationManager.yawOr(this.qhulAUwd.player.getYaw());
         float effPitch = RotationManager.pitchOr(this.qhulAUwd.player.getPitch());
         if (strat.TJImwK2() != null && fStlZIvY9lS8(effYaw) != strat.TJImwK2()) {
            return false;
         } else {
            if (strat.wSJeYsJcmjNZf() != null) {
               float required = strat.wSJeYsJcmjNZf();
               boolean reqUp = required < -45.0F;
               boolean reqDown = required > 45.0F;
               boolean isUp = effPitch < -45.0F;
               boolean isDown = effPitch > 45.0F;
               if (reqUp && !isUp) {
                  return false;
               }

               if (reqDown && !isDown) {
                  return false;
               }

               if (!reqUp && !reqDown && (isUp || isDown)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private float fStlZIvY9lS8(Direction facing) {
      return switch (facing) {
         case SOUTH -> 0.0F;
         case WEST -> 90.0F;
         case NORTH -> 180.0F;
         case EAST -> -90.0F;
         default -> this.qhulAUwd.player.getYaw();
      };
   }

   private static Direction fStlZIvY9lS8(float yaw) {
      float normalized = (yaw % 360.0F + 360.0F) % 360.0F;
      if (normalized >= 315.0F || normalized < 45.0F) {
         return Direction.SOUTH;
      } else if (normalized < 135.0F) {
         return Direction.WEST;
      } else {
         return normalized < 225.0F ? Direction.NORTH : Direction.EAST;
      }
   }

   private void QfZy2gWnKInxFPr(Printer.PlacementStrategy strat) {
      if (strat.TJImwK2() != null) {
         RotationManager.setYaw(this.fStlZIvY9lS8(strat.TJImwK2()), 10);
      }

      if (strat.wSJeYsJcmjNZf() != null) {
         RotationManager.setPitch(RotationManager.snapPitch(strat.wSJeYsJcmjNZf()), 10);
      }
   }

   private void i2Nu6XHKic() {
      if ((Boolean)this.X9ysJTxpWn2.get()) {
         if (this.F4hivB.get() == Printer.SelectionType.QfZy2gWnKInxFPr && LitematicaHelper.isLoaded()) {
            if (this.qhulAUwd.player != null && this.qhulAUwd.world != null) {
               if (!this.qhulAUwd.player.isSneaking() && this.qhulAUwd.currentScreen == null) {
                  if (this.duJ6sPeetAcBh6P % 200 == 0) {
                     this.MR1DKzw.entrySet().removeIf(e -> this.duJ6sPeetAcBh6P >= e.getValue());
                     this.DqGSCWplcjMi.keySet().removeIf(this::fStlZIvY9lS8);
                     this.cO1FU7fW.keySet().removeIf(this::fStlZIvY9lS8);
                  }

                  if (this.VIbihOaIVyhvJw > 0) {
                     this.VIbihOaIVyhvJw--;
                  } else {
                     BlockPos playerPos = this.qhulAUwd.player.getBlockPos();
                     int range = (int)Math.ceil(this.vGAMA89H()) + 1;
                     Map<BlockPos, BlockState> mismatched = LitematicaHelper.get()
                        .getMismatchedStatesInBox(
                           playerPos.add(-range, -range, -range),
                           playerPos.add(range, range, range),
                           (List<Block>)this.npfimrDr0hky.get(),
                           512
                        );
                     if (!mismatched.isEmpty()) {
                        List<Entry<BlockPos, BlockState>> targets = new ArrayList<>(mismatched.entrySet());
                        targets.sort(
                           Comparator.comparingDouble(
                              e -> this.qhulAUwd
                                 .player
                                 .squaredDistanceTo(e.getKey().getX() + 0.5, e.getKey().getY() + 0.5, e.getKey().getZ() + 0.5)
                           )
                        );
                        boolean batched = (Integer)this.H7J5bawM.get() == 0;

                        for (Entry<BlockPos, BlockState> entry : targets) {
                           BlockPos pos = entry.getKey();
                           BlockState target = entry.getValue();
                           if (!this.g0iiTDJzfIGXdui.contains(pos)
                              && !((List)this.Uag2UiCQCqA.get()).contains(target.getBlock())
                              && (
                                 this.PVlYDeNipUe.get() != Printer.LayerType.QfZy2gWnKInxFPr
                                    || !(pos.getY() >= Math.floor(this.qhulAUwd.player.getY()))
                              )
                              && (this.PVlYDeNipUe.get() != Printer.LayerType.TJImwK2 || LitematicaHelper.get().isPositionInRenderLayer(pos))) {
                              Integer waitUntil = this.MR1DKzw.get(pos);
                              if (waitUntil != null) {
                                 if (this.duJ6sPeetAcBh6P < waitUntil) {
                                    continue;
                                 }

                                 this.MR1DKzw.remove(pos);
                              }

                              int needed = WOGVSBb.fStlZIvY9lS8(this.qhulAUwd.world.getBlockState(pos), target);
                              if (needed != -1 && needed != 0) {
                                 if (this.QfZy2gWnKInxFPr(pos)) {
                                    if (!jU8xXhmkg.fStlZIvY9lS8()) {
                                       break;
                                    }

                                    Integer lastNeeded = this.DqGSCWplcjMi.get(pos);
                                    if (lastNeeded != null && needed >= lastNeeded) {
                                       if (this.cO1FU7fW.merge(pos, 1, Integer::sum) >= 3) {
                                          this.g0iiTDJzfIGXdui.add(pos);
                                          if ((Boolean)this.vijg33HMhCnyX.get()) {
                                             this.info(
                                                "[DBG-STATE] %s @ %s — giving up, %d rounds changed nothing",
                                                new Object[]{Registries.BLOCK.getId(target.getBlock()).getPath(), pos.toShortString(), 3}
                                             );
                                          }
                                          continue;
                                       }
                                    } else {
                                       this.cO1FU7fW.remove(pos);
                                    }

                                    this.DqGSCWplcjMi.put(pos, needed);
                                    BlockHitResult hit = this.TJImwK2(pos);
                                    int sent = 0;
                                    if (!batched) {
                                       BlockUtils.interact(hit, Hand.MAIN_HAND, true);
                                       sent = 1;
                                       this.VIbihOaIVyhvJw = (Integer)this.H7J5bawM.get();
                                       this.MR1DKzw.put(pos, this.duJ6sPeetAcBh6P + (Integer)this.H7J5bawM.get());
                                    } else {
                                       while (sent < needed && jU8xXhmkg.fStlZIvY9lS8()) {
                                          BlockUtils.interact(hit, Hand.MAIN_HAND, true);
                                          sent++;
                                       }

                                       this.MR1DKzw.put(pos, this.duJ6sPeetAcBh6P + 5);
                                    }

                                    if ((Boolean)this.vijg33HMhCnyX.get()) {
                                       this.info(
                                          "[DBG-STATE] %s @ %s — sent %d/%d interactions",
                                          new Object[]{
                                             Registries.BLOCK.getId(target.getBlock()).getPath(), pos.toShortString(), sent, needed
                                          }
                                       );
                                    }

                                    if (!batched || sent < needed) {
                                       break;
                                    }
                                 }
                              } else {
                                 this.DqGSCWplcjMi.remove(pos);
                                 this.cO1FU7fW.remove(pos);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private boolean fStlZIvY9lS8(BlockPos pos) {
      return this.qhulAUwd.player.squaredDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) > 1024.0;
   }

   private double vGAMA89H() {
      return Math.min((Double)MusheorSystem.Manager.placementRange.get(), this.qhulAUwd.player.getBlockInteractionRange());
   }

   private boolean QfZy2gWnKInxFPr(BlockPos pos) {
      double range = this.vGAMA89H();
      return this.qhulAUwd.player.getEyePos().squaredDistanceTo(this.TJImwK2(pos).getPos()) <= range * range;
   }

   private BlockHitResult TJImwK2(BlockPos pos) {
      Vec3d eye = this.qhulAUwd.player.getEyePos();
      BlockState state = this.qhulAUwd.world.getBlockState(pos);
      double minX = pos.getX();
      double minY = pos.getY();
      double minZ = pos.getZ();
      double maxX = pos.getX() + 1.0;
      double maxY = pos.getY() + 1.0;
      double maxZ = pos.getZ() + 1.0;
      VoxelShape shape = state.getOutlineShape(this.qhulAUwd.world, pos);
      if (!shape.isEmpty()) {
         Box box = shape.getBoundingBox();
         minX = pos.getX() + box.minX;
         maxX = pos.getX() + box.maxX;
         minY = pos.getY() + box.minY;
         maxY = pos.getY() + box.maxY;
         minZ = pos.getZ() + box.minZ;
         maxZ = pos.getZ() + box.maxZ;
      }

      Vec3d hit = new Vec3d(
         Math.max(minX, Math.min(eye.x, maxX)), Math.max(minY, Math.min(eye.y, maxY)), Math.max(minZ, Math.min(eye.z, maxZ))
      );
      return new BlockHitResult(hit, KekNuker.QfZy2gWnKInxFPr(eye, pos), pos, false);
   }

   private boolean TJImwK2(Printer.PlacementStrategy strat) {
      return !this.fStlZIvY9lS8() && strat.i2Nu6XHKic();
   }

   private BlockHitResult fStlZIvY9lS8(BlockPos pos, Printer.PlacementStrategy strat, boolean freeHit) {
      Vec3d eye = this.qhulAUwd.player.getEyePos();
      if (freeHit) {
         double hx = Math.max((double)pos.getX(), Math.min(eye.x, pos.getX() + 1.0));
         double hy = Math.max((double)pos.getY(), Math.min(eye.y, pos.getY() + 1.0));
         double hz = Math.max((double)pos.getZ(), Math.min(eye.z, pos.getZ() + 1.0));
         return new BlockHitResult(new Vec3d(hx, hy, hz), KekNuker.QfZy2gWnKInxFPr(eye, pos), pos, false);
      } else {
         Direction dir = strat.fStlZIvY9lS8();
         double hx = pos.getX() + 0.5 + dir.getOffsetX() * 0.5;
         double hy = pos.getY() + strat.QfZy2gWnKInxFPr();
         double hz = pos.getZ() + 0.5 + dir.getOffsetZ() * 0.5;
         if (dir.getAxis() != Direction.Axis.X) {
            hx = Math.max(pos.getX() + 0.05, Math.min(eye.x, pos.getX() + 0.95));
         }

         if (dir.getAxis() != Direction.Axis.Z) {
            hz = Math.max(pos.getZ() + 0.05, Math.min(eye.z, pos.getZ() + 0.95));
         }

         return new BlockHitResult(new Vec3d(hx, hy, hz), dir, pos, false);
      }
   }

   private boolean fStlZIvY9lS8(BlockPos pos, Printer.PlacementStrategy strat) {
      Vec3d eye = this.qhulAUwd.player.getEyePos();
      Vec3d hit = this.fStlZIvY9lS8(pos, strat, this.TJImwK2(strat)).getPos();
      double range = (Double)MusheorSystem.Manager.placementRange.get();
      return eye.squaredDistanceTo(hit) <= range * range;
   }

   private boolean QfZy2gWnKInxFPr(BlockPos pos, BlockState targetState) {
      if (targetState.getBlock() instanceof LanternBlock) {
         boolean hanging = (Boolean)targetState.get(Properties.HANGING);
         BlockPos attach = hanging ? pos.up() : pos.down();
         return !this.qhulAUwd.world.getBlockState(attach).isAir();
      } else if (targetState.getBlock() instanceof RedstoneWireBlock) {
         return !this.qhulAUwd.world.getBlockState(pos.down()).isAir();
      } else {
         return this.yYqDk4cMM0.get() && targetState.getBlock() instanceof FallingBlock
            ? !this.qhulAUwd.world.getBlockState(pos.down()).isAir()
            : true;
      }
   }

   private ChunkPos fStlZIvY9lS8(ChunkPos center, int radius, int index) {
      if (radius == 0) {
         return center;
      } else {
         int cx = center.x;
         int cz = center.z;
         if (index < 2 * radius + 1) {
            return new ChunkPos(cx - radius + index, cz - radius);
         } else {
            index -= 2 * radius + 1;
            if (index < 2 * radius) {
               return new ChunkPos(cx + radius, cz - radius + 1 + index);
            } else {
               index -= 2 * radius;
               if (index < 2 * radius) {
                  return new ChunkPos(cx + radius - 1 - index, cz + radius);
               } else {
                  index -= 2 * radius;
                  return new ChunkPos(cx - radius, cz + radius - 1 - index);
               }
            }
         }
      }
   }

   private BlockPos fStlZIvY9lS8(ChunkPos chunk) {
      if (this.qhulAUwd.world != null && this.qhulAUwd.player != null) {
         if (!this.qhulAUwd.world.isChunkLoaded(chunk.x, chunk.z)) {
            return null;
         } else {
            int minX = chunk.getStartX();
            int minZ = chunk.getStartZ();
            int maxX = chunk.getEndX();
            int maxZ = chunk.getEndZ();
            int minY = this.qhulAUwd.world.getBottomY();
            int maxY = this.qhulAUwd.world.getBottomY() + this.qhulAUwd.world.getHeight() - 1;
            Map<BlockPos, BlockState> candidates = LitematicaHelper.get()
               .getBlocksInBox(
                  new BlockPos(minX, minY, minZ),
                  new BlockPos(maxX, maxY, maxZ),
                  (Boolean)this.UsQcxwgBUuV.get(),
                  (List<Block>)this.npfimrDr0hky.get(),
                  512
               );
            if (candidates.isEmpty()) {
               return null;
            } else {
               BlockPos playerFeet = this.qhulAUwd.player.getBlockPos();
               BlockPos closest = null;
               double closestDist = Double.MAX_VALUE;

               for (Entry<BlockPos, BlockState> entry : candidates.entrySet()) {
                  BlockPos pos = entry.getKey();
                  BlockState targetState = entry.getValue();
                  if (PathKit.i2Nu6XHKic(targetState.getBlock().asItem()) > 0
                     && (
                        this.PVlYDeNipUe.get() != Printer.LayerType.QfZy2gWnKInxFPr
                           || !(pos.getY() >= Math.floor(this.qhulAUwd.player.getY()))
                     )
                     && (this.PVlYDeNipUe.get() != Printer.LayerType.TJImwK2 || LitematicaHelper.get().isPositionInRenderLayer(pos))
                     && !pos.equals(playerFeet)
                     && !pos.equals(playerFeet.up())) {
                     double dist = this.qhulAUwd.player.squaredDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                     if (dist < closestDist) {
                        closestDist = dist;
                        closest = pos;
                     }
                  }
               }

               return closest;
            }
         }
      } else {
         return null;
      }
   }

   private BlockPos fStlZIvY9lS8(Long2ObjectMap<Map<BlockPos, BlockState>> chunkPositions) {
      if (this.qhulAUwd.player != null && this.qhulAUwd.world != null) {
         double px = this.qhulAUwd.player.getX();
         double py = this.qhulAUwd.player.getY();
         double pz = this.qhulAUwd.player.getZ();
         List<Entry<Long, Map<BlockPos, BlockState>>> chunks = new ArrayList<>();
         ObjectIterator var9 = chunkPositions.long2ObjectEntrySet().iterator();

         while (var9.hasNext()) {
            it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry<Map<BlockPos, BlockState>> entry = (it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry<Map<BlockPos, BlockState>>)var9.next();
            chunks.add(Map.entry(entry.getLongKey(), (Map<BlockPos, BlockState>)entry.getValue()));
         }

         chunks.sort(Comparator.comparingDouble(e -> {
            int cx = ChunkPos.getPackedX(e.getKey());
            int cz = ChunkPos.getPackedZ(e.getKey());
            double centerX = (cx << 4) + 8.0;
            double centerZ = (cz << 4) + 8.0;
            return (centerX - px) * (centerX - px) + (centerZ - pz) * (centerZ - pz);
         }));

         for (Entry<Long, Map<BlockPos, BlockState>> chunk : chunks) {
            Map<BlockPos, BlockState> posMap = chunk.getValue();
            if (posMap != null && !posMap.isEmpty()) {
               BlockPos bestPos = null;
               double bestDist = Double.MAX_VALUE;

               for (Entry<BlockPos, BlockState> entry : posMap.entrySet()) {
                  BlockPos pos = entry.getKey();
                  BlockState targetState = entry.getValue();
                  if (this.qhulAUwd.world.getBlockState(pos).getBlock() != targetState.getBlock()
                     && this.qhulAUwd.world.getBlockState(pos).getBlock() instanceof AirBlock
                     && BlockUtils.canPlaceBlock(pos, true, targetState.getBlock())
                     && PathKit.i2Nu6XHKic(targetState.getBlock().asItem()) > 0) {
                     double dx = pos.getX() + 0.5 - px;
                     double dy = pos.getY() + 0.5 - py;
                     double dz = pos.getZ() + 0.5 - pz;
                     double distSq = dx * dx + dy * dy + dz * dz;
                     if (distSq < bestDist) {
                        bestDist = distSq;
                        bestPos = pos;
                     }
                  }
               }

               if (bestPos != null) {
                  return bestPos;
               }
            }
         }

         return null;
      } else {
         return null;
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(Render3DEvent event) {
      if (this.qhulAUwd.player != null && this.qhulAUwd.world != null) {
         if ((Boolean)MusheorSystem.Manager.placeRender.get() && (Boolean)this.jAZp22H8V.get()) {
            if (!this.I3psabmOrOI.isEmpty()) {
               List<Pair<BlockPos, Block>> renderBlocks = new ArrayList<>();

               for (Pair<BlockPos, BlockState> pair : this.I3psabmOrOI) {
                  if (!(Boolean)this.U1qL6cLGWxa.get() || BlockUtils.canPlace((BlockPos)pair.first())) {
                     renderBlocks.add(Pair.of((BlockPos)pair.first(), ((BlockState)pair.second()).getBlock()));
                  }
               }

               musheor.utils.jU8xXhmkg.fStlZIvY9lS8(event, renderBlocks);
            }
         }
      }
   }

   public static enum LayerType {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr,
      TJImwK2;
   }

   private record PlacementStrategy(Direction fStlZIvY9lS8, double QfZy2gWnKInxFPr, Direction TJImwK2, Float wSJeYsJcmjNZf, boolean i2Nu6XHKic) {
   }

   private static enum RestockType {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr,
      TJImwK2;
   }

   private static enum RotationType {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr;
   }

   private static enum SelectionType {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr;
   }
}
