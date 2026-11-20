package musheor.modules.automation;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.pathing.goals.GoalNear;
import baritone.api.selection.ISelection;
import baritone.api.selection.ISelectionManager;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.EnumSetting.Builder;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import musheor.compat.LitematicaHelper;
import musheor.modules.features.KekMine;
import musheor.modules.hud.HudInfoPlus;
import musheor.musheor;
import musheor.utils.gVOhtBej1x;
import musheor.utils.jU8xXhmkg;
import musheor.utils.oyDPiOVBGKG;
import musheor.utils.system.MusheorSystem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import obf.GyhoRo;
import obf.U6PQSXqW;
import obf.pvfA15fQSVM;

public class KekNuker extends Module {
   private final SettingGroup BrmKXSYhWhqoB1 = this.settings.getDefaultGroup();
   private final SettingGroup TXWyNb = this.settings.createGroup("Pathfind");
   private final SettingGroup I3psabmOrOI = this.settings.createGroup("Render");
   private static final MinecraftClient duJ6sPeetAcBh6P = MinecraftClient.getInstance();
   public static KekNuker fStlZIvY9lS8;
   public final Setting<KekNuker.NukerMode> QfZy2gWnKInxFPr = this.BrmKXSYhWhqoB1
      .add(((Builder)((Builder)new Builder().name("nuker-mode")).defaultValue(KekNuker.NukerMode.fStlZIvY9lS8)).build());
   private final Setting<KekNuker.LayerType> j376sIQ = this.BrmKXSYhWhqoB1
      .add(
         ((Builder)((Builder)((Builder)((Builder)new Builder().name("layer-type"))
                     .description("Controls where blocks are allowed to be broken (layer handling)"))
                  .defaultValue(KekNuker.LayerType.QfZy2gWnKInxFPr))
               .visible(() -> this.QfZy2gWnKInxFPr.get() != KekNuker.NukerMode.QfZy2gWnKInxFPr))
            .build()
      );
   private final Setting<KekNuker.SortMode> V7zKJ4n6D = this.BrmKXSYhWhqoB1
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("sort-mode")).description("Choose what order to mine the blocks in"))
               .defaultValue(KekNuker.SortMode.QfZy2gWnKInxFPr))
            .build()
      );
   public final Setting<KekNuker.Shape> TJImwK2 = this.BrmKXSYhWhqoB1
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("shape")).defaultValue(KekNuker.Shape.QfZy2gWnKInxFPr))
               .visible(() -> this.QfZy2gWnKInxFPr.get() != KekNuker.NukerMode.QfZy2gWnKInxFPr))
            .build()
      );
   public final Setting<Double> wSJeYsJcmjNZf = this.BrmKXSYhWhqoB1
      .add(
         ((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)new meteordevelopment.meteorclient.settings.DoubleSetting.Builder().name("range"))
            .defaultValue(5.5)
            .min(2.0)
            .max(8.0)
            .decimalPlaces(1)
            .build()
      );
   private final Setting<Boolean> voxTwgNzyrpaAnP = this.BrmKXSYhWhqoB1
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("below-feet-last"))
                     .description("Makes it so the block you are standing on is only mined when all other blocks in range are broken"))
                  .defaultValue(false))
               .visible(() -> this.QfZy2gWnKInxFPr.get() != KekNuker.NukerMode.QfZy2gWnKInxFPr))
            .build()
      );
   private final Setting<Boolean> xileXsbo = this.BrmKXSYhWhqoB1
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("ignore-air"))
                     .description("Ignores air blocks in the schematic."))
                  .visible(() -> LitematicaHelper.isLoaded() && this.QfZy2gWnKInxFPr.get() == KekNuker.NukerMode.TJImwK2))
               .defaultValue(false))
            .build()
      );
   private final Setting<Boolean> nOwp0NYl = this.BrmKXSYhWhqoB1
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("avoid-spilling-liquids"))
                  .description("Prevents you from mining blocks that will cause liquid to flow everywhere"))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> umvNbb7iD9UpR = this.BrmKXSYhWhqoB1
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("prevent-packet-kick"))
                  .description("+100000 aura setting"))
               .defaultValue(true))
            .build()
      );
   public final Setting<Integer> i2Nu6XHKic = this.BrmKXSYhWhqoB1
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("blocks-per-tick"))
                  .description("Maximum blocks to try to break per tick. (Only for instant-breakable-blocks)"))
               .defaultValue(35))
            .min(1)
            .sliderRange(1, 100)
            .build()
      );
   private final Setting<KekNuker.ListMode> zkcZ4Xkp9Ez7Y = this.BrmKXSYhWhqoB1
      .add(((Builder)((Builder)((Builder)new Builder().name("list-mode")).description("Selection mode")).defaultValue(KekNuker.ListMode.fStlZIvY9lS8)).build());
   private final Setting<List<Block>> flWHzEyn = this.BrmKXSYhWhqoB1
      .add(
         ((meteordevelopment.meteorclient.settings.BlockListSetting.Builder)((meteordevelopment.meteorclient.settings.BlockListSetting.Builder)((meteordevelopment.meteorclient.settings.BlockListSetting.Builder)new meteordevelopment.meteorclient.settings.BlockListSetting.Builder()
                     .name("whitelist"))
                  .description("The blocks you want to mine."))
               .defaultValue(new Block[0])
               .visible(() -> this.zkcZ4Xkp9Ez7Y.get() == KekNuker.ListMode.QfZy2gWnKInxFPr))
            .build()
      );
   private final Setting<List<Block>> FVjbhYkk53UT = this.BrmKXSYhWhqoB1
      .add(
         ((meteordevelopment.meteorclient.settings.BlockListSetting.Builder)((meteordevelopment.meteorclient.settings.BlockListSetting.Builder)((meteordevelopment.meteorclient.settings.BlockListSetting.Builder)new meteordevelopment.meteorclient.settings.BlockListSetting.Builder()
                     .name("blacklist"))
                  .description("The blocks you don't want to mine."))
               .defaultValue(new Block[0])
               .visible(() -> this.zkcZ4Xkp9Ez7Y.get() == KekNuker.ListMode.TJImwK2))
            .build()
      );
   private final Setting<Boolean> Nt3xeF = this.TXWyNb
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("auto-pathfind"))
                     .description("Automatically pathfinds when no blocks are in range"))
                  .defaultValue(false))
               .visible(() -> this.QfZy2gWnKInxFPr.get() != KekNuker.NukerMode.QfZy2gWnKInxFPr))
            .build()
      );
   private final Setting<Integer> zXcO5rZ = this.TXWyNb
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("pathfind-chunk-range"))
                     .description("Maximum range in chunks the pathfinder can scan for more blocks to break"))
                  .defaultValue(4))
               .sliderMax(8)
               .visible(() -> this.QfZy2gWnKInxFPr.get() != KekNuker.NukerMode.QfZy2gWnKInxFPr))
            .build()
      );
   private final Setting<Integer> jbUe84V1vykfK = this.TXWyNb
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("chunks-per-tick"))
                     .description(
                        "How many chunks to scan per tick when searching for more blocks. Higher values find targets faster at the cost of CPU per tick."
                     ))
                  .defaultValue(2))
               .min(1)
               .sliderMax(8)
               .visible(() -> this.QfZy2gWnKInxFPr.get() != KekNuker.NukerMode.QfZy2gWnKInxFPr))
            .build()
      );
   private final Setting<Boolean> zbN84Osn9p5lD = this.I3psabmOrOI
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("global-rendering"))
                  .defaultValue(true))
               .description("Synchronize rendering with Musheor-Tab"))
            .build()
      );
   private final Setting<SettingColor> ZlNrrNcv3Ylc = this.I3psabmOrOI
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                     .name("color"))
                  .defaultValue(new SettingColor(Color.cyan))
                  .description("Custom color for rendering (lines / wireframe)"))
               .visible(() -> !(Boolean)this.zbN84Osn9p5lD.get()))
            .build()
      );
   public static List<BlockPos> vGAMA89H = new ArrayList<>();
   private static List<BlockPos> EVSMbf = new ArrayList<>();
   public static final Map<BlockPos, Integer> qhulAUwd = new HashMap<>();
   private int pF16zz4NJECfg7;
   private int WGmfQKK = -1;
   private int icQ738dpWr7W = 0;
   private BlockPos JAj4YwE2MVx = null;
   private BlockPos JPp6VyZP9f = null;
   private boolean MR1DKzw = false;

   public KekNuker() {
      super(musheor.AUTOMATION, "kek-nuker", "Mines blocks in radius of the player");
      fStlZIvY9lS8 = this;
   }

   public static boolean fStlZIvY9lS8() {
      return !EVSMbf.isEmpty() && fStlZIvY9lS8.isActive() || !KekMine.duJ6sPeetAcBh6P.QfZy2gWnKInxFPr();
   }

   public static List<Block> QfZy2gWnKInxFPr() {
      return (List<Block>)fStlZIvY9lS8.FVjbhYkk53UT.get();
   }

   public void onActivate() {
      qhulAUwd.clear();
      this.pF16zz4NJECfg7 = 0;
      this.WGmfQKK = -1;
      this.icQ738dpWr7W = 0;
      this.JAj4YwE2MVx = null;
      this.JPp6VyZP9f = null;
      this.MR1DKzw = false;
   }

   public void onDeactivate() {
      vGAMA89H.clear();
      EVSMbf.clear();
      KekMine.duJ6sPeetAcBh6P.V7zKJ4n6D.clear();
      this.JAj4YwE2MVx = null;
      this.JPp6VyZP9f = null;
      this.WGmfQKK = -1;
      if ((Boolean)this.Nt3xeF.get() && (GyhoRo.fStlZIvY9lS8() || GyhoRo.QfZy2gWnKInxFPr())) {
         GyhoRo.wSJeYsJcmjNZf();
      }
   }

   @EventHandler
   public void fStlZIvY9lS8(Pre event) {
      if (duJ6sPeetAcBh6P.player != null && duJ6sPeetAcBh6P.world != null) {
         if (!HighwayBuilder.TJImwK2()) {
            this.pF16zz4NJECfg7++;
            qhulAUwd.entrySet()
               .removeIf(
                  entry -> duJ6sPeetAcBh6P.world.getBlockState(entry.getKey()).isAir()
                     ? true
                     : this.pF16zz4NJECfg7 - entry.getValue() > (Integer)KekMine.duJ6sPeetAcBh6P.vGAMA89H.get()
               );
            Module kekMine = Modules.get().get(KekMine.class);
            if (this.QfZy2gWnKInxFPr.get() == KekNuker.NukerMode.fStlZIvY9lS8) {
               vGAMA89H.clear();
               vGAMA89H = fStlZIvY9lS8((KekNuker.Shape)this.TJImwK2.get(), (Double)this.wSJeYsJcmjNZf.get());
            } else if (this.QfZy2gWnKInxFPr.get() == KekNuker.NukerMode.TJImwK2) {
               if (!LitematicaHelper.isLoaded()) {
                  this.warning("Litematica is not installed. Install Litematica or switch to another mode.", new Object[0]);
                  this.toggle();
                  return;
               }

               vGAMA89H.clear();
               vGAMA89H.addAll(LitematicaHelper.get().getWrongSchematicBlocks((Double)this.wSJeYsJcmjNZf.get(), (Boolean)this.xileXsbo.get()));
            } else if (this.QfZy2gWnKInxFPr.get() == KekNuker.NukerMode.wSJeYsJcmjNZf) {
               vGAMA89H.clear();
               vGAMA89H.addAll(this.wSJeYsJcmjNZf());
            }

            EVSMbf.clear();

            for (BlockPos pos : vGAMA89H) {
               BlockState state = duJ6sPeetAcBh6P.world.getBlockState(pos);
               if ((
                     this.QfZy2gWnKInxFPr.get() == KekNuker.NukerMode.QfZy2gWnKInxFPr
                        || (
                              this.j376sIQ.get() != KekNuker.LayerType.QfZy2gWnKInxFPr
                                 || pos.getY() >= duJ6sPeetAcBh6P.player.getBlockPos().getY()
                           )
                           && (this.j376sIQ.get() != KekNuker.LayerType.TJImwK2 || LitematicaHelper.get().isPositionInRenderLayer(pos))
                  )
                  && !state.isLiquid()
                  && !state.isAir()
                  && BlockUtils.canBreak(pos)
                  && (!(Boolean)this.nOwp0NYl.get() || !oyDPiOVBGKG.fStlZIvY9lS8(pos))
                  && !(fStlZIvY9lS8(duJ6sPeetAcBh6P.player.getEyePos(), pos) > (Double)this.wSJeYsJcmjNZf.get() * (Double)this.wSJeYsJcmjNZf.get())
                  && (
                     this.zkcZ4Xkp9Ez7Y.get() == KekNuker.ListMode.fStlZIvY9lS8
                        || (this.zkcZ4Xkp9Ez7Y.get() != KekNuker.ListMode.QfZy2gWnKInxFPr || ((List)this.flWHzEyn.get()).contains(state.getBlock()))
                           && (this.zkcZ4Xkp9Ez7Y.get() != KekNuker.ListMode.TJImwK2 || !((List)this.FVjbhYkk53UT.get()).contains(state.getBlock()))
                  )) {
                  EVSMbf.add(pos);
               }
            }

            this.fStlZIvY9lS8(EVSMbf);
            if ((Boolean)this.voxTwgNzyrpaAnP.get()) {
               BlockPos p = duJ6sPeetAcBh6P.player.getBlockPos();
               List<BlockPos> below = EVSMbf.stream()
                  .filter(posx -> posx.getX() == p.getX() && posx.getZ() == p.getZ() && posx.getY() < p.getY())
                  .sorted(Comparator.comparingInt(Vec3i::getY).reversed())
                  .toList();
               EVSMbf.removeAll(below);
               EVSMbf.addAll(below);
            }

            if (EVSMbf.isEmpty() && (Boolean)this.Nt3xeF.get() && this.QfZy2gWnKInxFPr.get() != KekNuker.NukerMode.QfZy2gWnKInxFPr) {
               if (this.MR1DKzw) {
                  this.MR1DKzw = false;
                  this.WGmfQKK = -1;
                  this.icQ738dpWr7W = 0;
                  this.JAj4YwE2MVx = null;
                  this.JPp6VyZP9f = null;
               }

               if (this.JAj4YwE2MVx != null) {
                  if (oyDPiOVBGKG.fStlZIvY9lS8(this.JAj4YwE2MVx, (Double)this.wSJeYsJcmjNZf.get())) {
                     GyhoRo.wSJeYsJcmjNZf();
                     this.JAj4YwE2MVx = null;
                     this.JPp6VyZP9f = null;
                     this.WGmfQKK = 0;
                     this.icQ738dpWr7W = 0;
                  } else if (!GyhoRo.fStlZIvY9lS8()) {
                     BlockPos standPos = this.fStlZIvY9lS8(this.JAj4YwE2MVx);
                     if (standPos == null) {
                        this.JAj4YwE2MVx = null;
                        this.JPp6VyZP9f = null;
                     } else {
                        BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(new GoalNear(standPos, 1));
                     }
                  }
               } else {
                  if (this.WGmfQKK == -1) {
                     this.WGmfQKK = 0;
                     this.icQ738dpWr7W = 0;
                  }

                  int maxChunkRadius = (Integer)this.zXcO5rZ.get();
                  ChunkPos playerChunk = duJ6sPeetAcBh6P.player.getChunkPos();

                  for (int i = 0; i < this.jbUe84V1vykfK.get() && this.WGmfQKK <= maxChunkRadius && this.JAj4YwE2MVx == null; i++) {
                     ChunkPos toScan = this.fStlZIvY9lS8(playerChunk, this.WGmfQKK, this.icQ738dpWr7W);
                     BlockPos candidate = this.fStlZIvY9lS8(toScan);
                     if (candidate != null
                        && (
                           this.JPp6VyZP9f == null
                              || duJ6sPeetAcBh6P.player
                                    .squaredDistanceTo(candidate.getX() + 0.5, candidate.getY() + 0.5, candidate.getZ() + 0.5)
                                 < duJ6sPeetAcBh6P.player
                                    .squaredDistanceTo(
                                       this.JPp6VyZP9f.getX() + 0.5, this.JPp6VyZP9f.getY() + 0.5, this.JPp6VyZP9f.getZ() + 0.5
                                    )
                        )) {
                        this.JPp6VyZP9f = candidate;
                     }

                     int ringSize = this.WGmfQKK == 0 ? 1 : 8 * this.WGmfQKK;
                     this.icQ738dpWr7W++;
                     if (this.icQ738dpWr7W >= ringSize) {
                        this.icQ738dpWr7W = 0;
                        this.WGmfQKK++;
                        if (this.JPp6VyZP9f != null) {
                           BlockPos standPos = this.fStlZIvY9lS8(this.JPp6VyZP9f);
                           if (standPos != null) {
                              this.JAj4YwE2MVx = this.JPp6VyZP9f;
                              BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(new GoalNear(standPos, 1));
                           }

                           this.JPp6VyZP9f = null;
                        }
                     }
                  }
               }
            }

            if (!EVSMbf.isEmpty()) {
               this.MR1DKzw = true;
               int count = 0;

               for (BlockPos posx : EVSMbf) {
                  BlockState state = duJ6sPeetAcBh6P.world.getBlockState(posx);
                  if (HudInfoPlus.vGAMA89H() >= ((Integer)MusheorSystem.Manager.globalPacketLimit.get()).intValue() * 0.9 && (Boolean)this.umvNbb7iD9UpR.get()) {
                     break;
                  }

                  if (!qhulAUwd.containsKey(posx)) {
                     KekMine.MineContext ctx = new KekMine.MineContext(posx, duJ6sPeetAcBh6P.world.getBlockState(posx), true);
                     if (kekMine.isActive()) {
                        if (!KekMine.duJ6sPeetAcBh6P.fStlZIvY9lS8()) {
                           Direction face = QfZy2gWnKInxFPr(duJ6sPeetAcBh6P.player.getEyePos(), posx);
                           if (ctx.j376sIQ) {
                              KekMine.fStlZIvY9lS8(posx, face, false);
                              count++;
                           } else {
                              KekMine.duJ6sPeetAcBh6P.fStlZIvY9lS8(posx, state, face, false);
                           }

                           qhulAUwd.put(posx, this.pF16zz4NJECfg7);
                        }
                     } else {
                        KekMine.duJ6sPeetAcBh6P.QfZy2gWnKInxFPr(posx);
                        BlockUtils.breakBlock(posx, true);
                        count++;
                        if (!BlockUtils.canInstaBreak(posx)) {
                           break;
                        }
                     }

                     if (count >= (Integer)this.i2Nu6XHKic.get()) {
                        break;
                     }
                  }
               }

               if (kekMine.isActive() && !KekMine.duJ6sPeetAcBh6P.V7zKJ4n6D.isEmpty()) {
                  List<BlockPos> queueList = new ArrayList<>(KekMine.duJ6sPeetAcBh6P.V7zKJ4n6D);
                  this.fStlZIvY9lS8(queueList);
                  if ((Boolean)this.voxTwgNzyrpaAnP.get()) {
                     BlockPos p = duJ6sPeetAcBh6P.player.getBlockPos();
                     List<BlockPos> below = queueList.stream()
                        .filter(
                           posx -> posx.getX() == p.getX() && posx.getZ() == p.getZ() && posx.getY() < p.getY()
                        )
                        .sorted(Comparator.comparingInt(Vec3i::getY).reversed())
                        .toList();
                     queueList.removeAll(below);
                     queueList.addAll(below);
                  }

                  KekMine.duJ6sPeetAcBh6P.V7zKJ4n6D.clear();
                  KekMine.duJ6sPeetAcBh6P.V7zKJ4n6D.addAll(queueList);
               }
            }
         }
      }
   }

   private void fStlZIvY9lS8(List<BlockPos> list) {
      switch ((KekNuker.SortMode)this.V7zKJ4n6D.get()) {
         case Closest:
            list.sort(Comparator.comparingDouble(p -> duJ6sPeetAcBh6P.player.squaredDistanceTo(Vec3d.of(p))));
            break;
         case Furthest:
            list.sort(Comparator.comparingDouble(p -> -duJ6sPeetAcBh6P.player.squaredDistanceTo(Vec3d.of(p))));
            break;
         case TopDown:
            list.sort(Comparator.comparingDouble(p -> -p.getY()));
            break;
         case BottomUp:
            list.sort(Comparator.comparingDouble(Vec3i::getY));
            break;
         case Efficient:
            this.QfZy2gWnKInxFPr(list);
         case null:
      }
   }

   private void QfZy2gWnKInxFPr(List<BlockPos> list) {
      if (duJ6sPeetAcBh6P.player != null && duJ6sPeetAcBh6P.world != null && !list.isEmpty()) {
         Map<BlockPos, KekNuker.EffKey> keys = new HashMap<>();

         for (BlockPos pos : list) {
            BlockState state = duJ6sPeetAcBh6P.world.getBlockState(pos);
            boolean toolMatch = KekMine.fStlZIvY9lS8(state);
            KekMine.MineContext ctx = new KekMine.MineContext(pos, state, true);
            boolean insta = ctx.j376sIQ;
            keys.put(pos, new KekNuker.EffKey(toolMatch, insta, ctx.QfZy2gWnKInxFPr()));
         }

         list.sort(
            Comparator.<BlockPos>comparingInt(p -> keys.get(p).QfZy2gWnKInxFPr() ? 0 : 1)
               .thenComparingInt(p -> keys.get(p).fStlZIvY9lS8() ? 0 : 1)
               .thenComparingDouble(p -> -keys.get(p).TJImwK2())
         );
      }
   }

   public static boolean TJImwK2() {
      oyDPiOVBGKG.gVOhtBej1x dir = HighwayBuilder.i2Nu6XHKic();
      if (dir == null) {
         return false;
      } else {
         boolean isCardinal = dir == oyDPiOVBGKG.gVOhtBej1x.i2Nu6XHKic
            || dir == oyDPiOVBGKG.gVOhtBej1x.fStlZIvY9lS8
            || dir == oyDPiOVBGKG.gVOhtBej1x.qhulAUwd
            || dir == oyDPiOVBGKG.gVOhtBej1x.TJImwK2;
         BlockPos[] zone = isCardinal ? gVOhtBej1x.fStlZIvY9lS8(0, 2) : gVOhtBej1x.QfZy2gWnKInxFPr(0, 2);
         Set<BlockPos> zoneSet = new HashSet<>(Arrays.asList(zone));
         U6PQSXqW state = U6PQSXqW.fStlZIvY9lS8();
         pvfA15fQSVM.gVOhtBej1x detected = state.npfimrDr0hky();
         if (detected != null && state.BrmKXSYhWhqoB1() != null) {
            int railY = state.BrmKXSYhWhqoB1();
            BlockPos[] railZone = isCardinal ? gVOhtBej1x.fStlZIvY9lS8(0, 2, true, true) : gVOhtBej1x.TJImwK2(0, 2, true, true);

            for (BlockPos pos : railZone) {
               if (pos.getY() == railY && pvfA15fQSVM.fStlZIvY9lS8(pos, detected)) {
                  zoneSet.add(pos);
               }
            }
         }

         BlockPos p = KekMine.duJ6sPeetAcBh6P.wSJeYsJcmjNZf();
         if (p != null && zoneSet.contains(p)) {
            return true;
         } else {
            p = KekMine.duJ6sPeetAcBh6P.i2Nu6XHKic();
            if (p != null && zoneSet.contains(p)) {
               return true;
            } else {
               for (BlockPos posx : EVSMbf) {
                  if (zoneSet.contains(posx)) {
                     return true;
                  }
               }

               return false;
            }
         }
      }
   }

   public static List<BlockPos> fStlZIvY9lS8(KekNuker.Shape shape, double radius) {
      List<BlockPos> positions = new ArrayList<>();
      Vec3d eyePos = duJ6sPeetAcBh6P.player.getEyePos();
      int blockRadius = (int)Math.ceil(radius);

      for (int x = -blockRadius; x < blockRadius; x++) {
         for (int y = -blockRadius; y < blockRadius; y++) {
            for (int z = -blockRadius; z < blockRadius; z++) {
               BlockPos pos = BlockPos.ofFloored(eyePos.x + x, eyePos.y + y, eyePos.z + z);
               if (BlockUtils.canBreak(pos)) {
                  if (shape == KekNuker.Shape.QfZy2gWnKInxFPr) {
                     if (fStlZIvY9lS8(eyePos, pos) <= radius * radius) {
                        positions.add(pos);
                     }
                  } else if (shape == KekNuker.Shape.fStlZIvY9lS8) {
                     positions.add(pos);
                  }
               }
            }
         }
      }

      return positions;
   }

   static double fStlZIvY9lS8(Vec3d eye, BlockPos pos) {
      double cx = Math.max((double)pos.getX(), Math.min(eye.x, pos.getX() + 1.0));
      double cy = Math.max((double)pos.getY(), Math.min(eye.y, pos.getY() + 1.0));
      double cz = Math.max((double)pos.getZ(), Math.min(eye.z, pos.getZ() + 1.0));
      double dx = eye.x - cx;
      double dy = eye.y - cy;
      double dz = eye.z - cz;
      return dx * dx + dy * dy + dz * dz;
   }

   public static Direction QfZy2gWnKInxFPr(Vec3d eye, BlockPos pos) {
      double dx = eye.x - (pos.getX() + 0.5);
      double dy = eye.y - (pos.getY() + 0.5);
      double dz = eye.z - (pos.getZ() + 0.5);
      double adx = Math.abs(dx);
      double ady = Math.abs(dy);
      double adz = Math.abs(dz);
      if (adx >= ady && adx >= adz) {
         return dx > 0.0 ? Direction.EAST : Direction.WEST;
      } else if (ady >= adz) {
         return dy > 0.0 ? Direction.UP : Direction.DOWN;
      } else {
         return dz > 0.0 ? Direction.SOUTH : Direction.NORTH;
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
      if (duJ6sPeetAcBh6P.world == null || duJ6sPeetAcBh6P.player == null) {
         return null;
      } else if (!duJ6sPeetAcBh6P.world.isChunkLoaded(chunk.x, chunk.z)) {
         return null;
      } else {
         int minX = chunk.getStartX();
         int maxX = chunk.getEndX();
         int minZ = chunk.getStartZ();
         int maxZ = chunk.getEndZ();
         BlockPos closest = null;
         double closestDist = Double.MAX_VALUE;
         double px = duJ6sPeetAcBh6P.player.getX();
         double py = duJ6sPeetAcBh6P.player.getY();
         double pz = duJ6sPeetAcBh6P.player.getZ();
         if (this.QfZy2gWnKInxFPr.get() == KekNuker.NukerMode.TJImwK2) {
            if (!LitematicaHelper.isLoaded()) {
               return null;
            }

            int minY = duJ6sPeetAcBh6P.world.getBottomY();
            int maxY = duJ6sPeetAcBh6P.world.getBottomY() + duJ6sPeetAcBh6P.world.getHeight() - 1;

            for (BlockPos pos : LitematicaHelper.get()
               .getWrongBlocksInBox(new BlockPos(minX, minY, minZ), new BlockPos(maxX, maxY, maxZ), !(Boolean)this.xileXsbo.get(), 64)) {
               BlockState state = duJ6sPeetAcBh6P.world.getBlockState(pos);
               if (this.QfZy2gWnKInxFPr(pos)
                  && BlockUtils.canBreak(pos)
                  && (!(Boolean)this.nOwp0NYl.get() || !oyDPiOVBGKG.fStlZIvY9lS8(pos))
                  && this.fStlZIvY9lS8(state)
                  && this.fStlZIvY9lS8(pos, 8)) {
                  double dist = pos.getSquaredDistance(px, py, pz);
                  if (dist < closestDist) {
                     closestDist = dist;
                     closest = pos;
                  }
               }
            }
         } else {
            ISelection[] selections = null;
            if (this.QfZy2gWnKInxFPr.get() == KekNuker.NukerMode.wSJeYsJcmjNZf) {
               selections = BaritoneAPI.getProvider().getPrimaryBaritone().getSelectionManager().getSelections();
               if (selections == null || selections.length == 0) {
                  return null;
               }
            }

            int feetY = duJ6sPeetAcBh6P.player.getBlockPos().getY();
            int r = (int)Math.ceil((Double)this.wSJeYsJcmjNZf.get());
            int minY = this.j376sIQ.get() == KekNuker.LayerType.QfZy2gWnKInxFPr ? feetY : Math.max(duJ6sPeetAcBh6P.world.getBottomY(), feetY - r);
            int maxY = Math.min(duJ6sPeetAcBh6P.world.getBottomY() + duJ6sPeetAcBh6P.world.getHeight() - 1, feetY + r);
            BlockPos.Mutable mpos = new BlockPos.Mutable();

            for (int x = minX; x <= maxX; x++) {
               for (int z = minZ; z <= maxZ; z++) {
                  for (int y = minY; y <= maxY; y++) {
                     mpos.set(x, y, z);
                     BlockState state = duJ6sPeetAcBh6P.world.getBlockState(mpos);
                     if (!state.isAir()
                        && !state.isLiquid()
                        && BlockUtils.canBreak(mpos)
                        && (!(Boolean)this.nOwp0NYl.get() || !oyDPiOVBGKG.fStlZIvY9lS8(mpos))
                        && this.QfZy2gWnKInxFPr(mpos)
                        && this.fStlZIvY9lS8(state)
                        && (selections == null || fStlZIvY9lS8(mpos, selections))
                        && this.fStlZIvY9lS8(mpos, 8)) {
                        double dist = mpos.getSquaredDistance(px, py, pz);
                        if (dist < closestDist) {
                           closestDist = dist;
                           closest = mpos.toImmutable();
                        }
                     }
                  }
               }
            }
         }

         return closest;
      }
   }

   private BlockPos fStlZIvY9lS8(BlockPos candidate) {
      int playerY = duJ6sPeetAcBh6P.player.getBlockY();
      int r = (int)Math.ceil((Double)this.wSJeYsJcmjNZf.get());
      double rangeSq = (Double)this.wSJeYsJcmjNZf.get() * (Double)this.wSJeYsJcmjNZf.get();
      int dy = playerY - candidate.getY();
      if (dy * dy > rangeSq) {
         return null;
      } else {
         BlockPos best = null;
         double bestDist = Double.MAX_VALUE;
         BlockPos.Mutable standMut = new BlockPos.Mutable();
         BlockPos.Mutable groundMut = new BlockPos.Mutable();

         for (int dx = -r; dx <= r; dx++) {
            for (int dz = -r; dz <= r; dz++) {
               if (!(dx * dx + dy * dy + dz * dz > rangeSq)) {
                  int sx = candidate.getX() + dx;
                  int sz = candidate.getZ() + dz;
                  groundMut.set(sx, playerY - 1, sz);
                  BlockState ground = duJ6sPeetAcBh6P.world.getBlockState(groundMut);
                  if (!ground.isAir() && !ground.isLiquid()) {
                     standMut.set(sx, playerY, sz);
                     double dist = standMut.getSquaredDistance(candidate);
                     if (dist < bestDist) {
                        bestDist = dist;
                        best = standMut.toImmutable();
                     }
                  }
               }
            }
         }

         return best;
      }
   }

   private boolean fStlZIvY9lS8(BlockPos pos, int depth) {
      int bottom = duJ6sPeetAcBh6P.world.getBottomY();
      int x = pos.getX();
      int z = pos.getZ();
      BlockPos.Mutable check = new BlockPos.Mutable(x, 0, z);

      for (int dy = 1; dy <= depth; dy++) {
         int y = pos.getY() - dy;
         if (y <= bottom) {
            break;
         }

         check.set(x, y, z);
         BlockState s = duJ6sPeetAcBh6P.world.getBlockState(check);
         if (!s.isAir() && !s.isLiquid()) {
            return true;
         }
      }

      return false;
   }

   private boolean QfZy2gWnKInxFPr(BlockPos pos) {
      return this.j376sIQ.get() == KekNuker.LayerType.QfZy2gWnKInxFPr && pos.getY() < duJ6sPeetAcBh6P.player.getBlockPos().getY()
         ? false
         : this.j376sIQ.get() != KekNuker.LayerType.TJImwK2 || !LitematicaHelper.isLoaded() || LitematicaHelper.get().isPositionInRenderLayer(pos);
   }

   private boolean fStlZIvY9lS8(BlockState state) {
      if (this.zkcZ4Xkp9Ez7Y.get() == KekNuker.ListMode.QfZy2gWnKInxFPr) {
         return ((List)this.flWHzEyn.get()).contains(state.getBlock());
      } else {
         return this.zkcZ4Xkp9Ez7Y.get() == KekNuker.ListMode.TJImwK2 ? !((List)this.FVjbhYkk53UT.get()).contains(state.getBlock()) : true;
      }
   }

   private static boolean fStlZIvY9lS8(BlockPos pos, ISelection[] selections) {
      for (ISelection sel : selections) {
         BlockPos min = sel.min();
         BlockPos max = sel.max();
         if (pos.getX() >= min.getX()
            && pos.getX() <= max.getX()
            && pos.getY() >= min.getY()
            && pos.getY() <= max.getY()
            && pos.getZ() >= min.getZ()
            && pos.getZ() <= max.getZ()) {
            return true;
         }
      }

      return false;
   }

   private List<BlockPos> wSJeYsJcmjNZf() {
      List<BlockPos> list = new ArrayList<>();
      if (duJ6sPeetAcBh6P.player != null && duJ6sPeetAcBh6P.world != null) {
         IBaritone baritone = BaritoneAPI.getProvider().getPrimaryBaritone();
         ISelectionManager selectionManager = baritone.getSelectionManager();
         if (selectionManager.getSelections() == null) {
            return list;
         } else {
            for (ISelection selection : selectionManager.getSelections()) {
               BlockPos min = selection.min();
               BlockPos max = selection.max();
               int regionMinX = Math.min(min.getX(), max.getX());
               int regionMinY = Math.min(min.getY(), max.getY());
               int regionMinZ = Math.min(min.getZ(), max.getZ());
               int regionMaxX = Math.max(min.getX(), max.getX());
               int regionMaxY = Math.max(min.getY(), max.getY());
               int regionMaxZ = Math.max(min.getZ(), max.getZ());
               int px = duJ6sPeetAcBh6P.player.getBlockX();
               int py = duJ6sPeetAcBh6P.player.getBlockY();
               int pz = duJ6sPeetAcBh6P.player.getBlockZ();
               int minX = Math.max(regionMinX, px - 12);
               int minY = Math.max(regionMinY, py - 12);
               int minZ = Math.max(regionMinZ, pz - 12);
               int maxX = Math.min(regionMaxX, px + 12);
               int maxY = Math.min(regionMaxY, py + 12);
               int maxZ = Math.min(regionMaxZ, pz + 12);
               if (minX <= maxX && minY <= maxY && minZ <= maxZ) {
                  for (int x = minX; x <= maxX; x++) {
                     for (int y = minY; y <= maxY; y++) {
                        for (int z = minZ; z <= maxZ; z++) {
                           BlockPos pos = new BlockPos(x, y, z);
                           if (!duJ6sPeetAcBh6P.world.getBlockState(pos).isAir()
                              && !duJ6sPeetAcBh6P.world.getBlockState(pos).isLiquid()
                              && !list.contains(pos)
                              && oyDPiOVBGKG.fStlZIvY9lS8(pos, (Double)this.wSJeYsJcmjNZf.get())) {
                              list.add(pos);
                           }
                        }
                     }
                  }
               }
            }

            return list;
         }
      } else {
         return list;
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(Render3DEvent event) {
      if (duJ6sPeetAcBh6P.player != null && duJ6sPeetAcBh6P.world != null) {
         if ((Boolean)this.zbN84Osn9p5lD.get()) {
            jU8xXhmkg.QfZy2gWnKInxFPr(event, EVSMbf);
         } else {
            jU8xXhmkg.fStlZIvY9lS8(
               event,
               EVSMbf,
               (meteordevelopment.meteorclient.utils.render.color.Color)this.ZlNrrNcv3Ylc.get(),
               (meteordevelopment.meteorclient.utils.render.color.Color)this.ZlNrrNcv3Ylc.get(),
               ShapeMode.Lines
            );
         }
      }
   }

   private record EffKey(boolean fStlZIvY9lS8, boolean QfZy2gWnKInxFPr, float TJImwK2) {
   }

   private static enum LayerType {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr,
      TJImwK2;
   }

   public static enum ListMode {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr,
      TJImwK2;
   }

   public static enum NukerMode {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr,
      TJImwK2,
      wSJeYsJcmjNZf;
   }

   public static enum Shape {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr;
   }

   public static enum SortMode {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr,
      TJImwK2,
      wSJeYsJcmjNZf,
      i2Nu6XHKic,
      vGAMA89H;
   }
}
