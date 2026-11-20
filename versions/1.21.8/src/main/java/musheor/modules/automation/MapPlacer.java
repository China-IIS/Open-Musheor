package musheor.modules.automation;

import java.util.List;
import meteordevelopment.meteorclient.events.render.Render2DEvent;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.renderer.text.TextRenderer;
import meteordevelopment.meteorclient.settings.DoubleSetting.Builder;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.render.NametagUtils;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import musheor.compat.VersionHelper;
import musheor.musheor;
import musheor.utils.manager.RotationManager;
import musheor.utils.manager.PathKit;
import musheor.utils.system.MusheorSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import obf.JhujBv5;
import org.joml.Vector3d;

public class MapPlacer extends Module {
   private final SettingGroup fStlZIvY9lS8 = this.settings.getDefaultGroup();
   private final SettingGroup QfZy2gWnKInxFPr = this.settings.createGroup("Timing");
   private final SettingGroup TJImwK2 = this.settings.createGroup("Render");
   private final Setting<Double> wSJeYsJcmjNZf = this.fStlZIvY9lS8
      .add(
         ((Builder)((Builder)new Builder().name("placement-range"))
               .description("How close a frame's hitbox has to be to your eyes before we try to fill it. The server's own limit is 3."))
            .defaultValue(3.0)
            .min(1.0)
            .sliderRange(1.0, 6.0)
            .decimalPlaces(1)
            .build()
      );
   private final Setting<MapPlacer.RotateMode> i2Nu6XHKic = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)new meteordevelopment.meteorclient.settings.EnumSetting.Builder()
                     .name("rotate"))
                  .description("How to face the frame being filled. Packet keeps the camera still, Client turns it for real, None sends the interact as-is."))
               .defaultValue(MapPlacer.RotateMode.fStlZIvY9lS8))
            .build()
      );
   private final Setting<Boolean> vGAMA89H = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("restore-slot"))
                  .description(
                     "Selects the hotbar slot you were on again once placing stops. The swap itself can never be hidden — the server reads the map out of the slot we select."
                  ))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> qhulAUwd = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("auto-scan"))
                  .description("Keeps picking up more frames of the canvas as you walk, so .mp scan only has to be run once."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> BrmKXSYhWhqoB1 = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("pause-on-screen"))
                  .description("Stops placing while any screen or inventory is open."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> TXWyNb = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("disable-when-done"))
                  .description("Turns the module off once every frame of the canvas holds a map."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> I3psabmOrOI = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("chat-feedback"))
                  .description("Reports each placement in chat."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Integer> duJ6sPeetAcBh6P = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("placement-delay"))
                  .description("Ticks to wait between placements."))
               .defaultValue(1))
            .sliderRange(0, 20)
            .build()
      );
   private final Setting<Integer> j376sIQ = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("swap-delay"))
                  .description("Ticks to wait after selecting a map before interacting. Raise this on high ping."))
               .defaultValue(1))
            .sliderRange(0, 10)
            .build()
      );
   private final Setting<Integer> V7zKJ4n6D = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("scan-interval"))
                     .description("Ticks between rescans while auto-scan is on."))
                  .defaultValue(50))
               .sliderRange(1, 100)
               .visible(this.qhulAUwd::get))
            .build()
      );
   private final Setting<Boolean> voxTwgNzyrpaAnP = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("render"))
                  .description("Draws the canvas so you can check every coordinate before you start placing."))
               .defaultValue(true))
            .build()
      );
   private final Setting<ShapeMode> xileXsbo = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)new meteordevelopment.meteorclient.settings.EnumSetting.Builder()
                        .name("shape-mode"))
                     .description("How the frame outlines are drawn."))
                  .defaultValue(ShapeMode.Lines))
               .visible(this.voxTwgNzyrpaAnP::get))
            .build()
      );
   private final Setting<Double> nOwp0NYl = this.TJImwK2
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("render-distance")).description("Only draw cells this close to you."))
               .defaultValue(32.0)
               .sliderRange(8.0, 128.0)
               .visible(this.voxTwgNzyrpaAnP::get))
            .build()
      );
   private final Setting<Boolean> umvNbb7iD9UpR = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("show-placed"))
                     .description("Keep drawing frames that already hold a map. Off by default, so the render empties out as the mapart fills in."))
                  .defaultValue(false))
               .visible(this.voxTwgNzyrpaAnP::get))
            .build()
      );
   private final Setting<MapPlacer.LabelMode> zkcZ4Xkp9Ez7Y = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)new meteordevelopment.meteorclient.settings.EnumSetting.Builder()
                        .name("labels"))
                     .description("What to write on each frame: its grid coordinate, its row-major index, both, or nothing."))
                  .defaultValue(MapPlacer.LabelMode.TJImwK2))
               .visible(this.voxTwgNzyrpaAnP::get))
            .build()
      );
   private final Setting<Double> flWHzEyn = this.TJImwK2
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("label-scale")).description("Size of the labels."))
               .defaultValue(2.0)
               .sliderRange(0.25, 3.0)
               .decimalPlaces(2)
               .visible(() -> (Boolean)this.voxTwgNzyrpaAnP.get() && this.zkcZ4Xkp9Ez7Y.get() != MapPlacer.LabelMode.wSJeYsJcmjNZf))
            .build()
      );
   private final Setting<Boolean> FVjbhYkk53UT = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("label-distance-scaling"))
                     .description("Shrink labels with distance instead of keeping them one size on screen."))
                  .defaultValue(true))
               .visible(() -> (Boolean)this.voxTwgNzyrpaAnP.get() && this.zkcZ4Xkp9Ez7Y.get() != MapPlacer.LabelMode.wSJeYsJcmjNZf))
            .build()
      );
   private final Setting<SettingColor> Nt3xeF = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                     .name("ready-color"))
                  .description("A frame whose map is in your inventory."))
               .defaultValue(new SettingColor(25, 225, 125, 180))
               .visible(this.voxTwgNzyrpaAnP::get))
            .build()
      );
   private final Setting<SettingColor> zXcO5rZ = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                     .name("missing-color"))
                  .description("A frame whose map is NOT in your inventory."))
               .defaultValue(new SettingColor(225, 75, 50, 180))
               .visible(this.voxTwgNzyrpaAnP::get))
            .build()
      );
   private final Setting<SettingColor> jbUe84V1vykfK = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                     .name("target-color"))
                  .description("The frame being filled right now."))
               .defaultValue(new SettingColor(255, 210, 40, 220))
               .visible(this.voxTwgNzyrpaAnP::get))
            .build()
      );
   private final Setting<SettingColor> zbN84Osn9p5lD = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                     .name("placed-color"))
                  .description("A frame that already holds a map."))
               .defaultValue(new SettingColor(120, 120, 120, 110))
               .visible(() -> (Boolean)this.voxTwgNzyrpaAnP.get() && (Boolean)this.umvNbb7iD9UpR.get()))
            .build()
      );
   private static final JhujBv5 ZlNrrNcv3Ylc = JhujBv5.fStlZIvY9lS8();
   private static boolean EVSMbf;
   private int pF16zz4NJECfg7;
   private int WGmfQKK;
   private int icQ738dpWr7W;
   private int JAj4YwE2MVx;
   private int JPp6VyZP9f = -1;
   private float MR1DKzw;
   private float DqGSCWplcjMi;
   private int cO1FU7fW = Integer.MIN_VALUE;
   private JhujBv5.gVOhtBej1x g0iiTDJzfIGXdui;
   private MapPlacer.Status VIbihOaIVyhvJw = MapPlacer.Status.fStlZIvY9lS8;

   public MapPlacer() {
      super(musheor.AUTOMATION, "map-placer", "Places a mapart into a wall of item frames, reading each map's position out of its name. Set it up with .mp.");
   }

   public static MapPlacer fStlZIvY9lS8() {
      return (MapPlacer)Modules.get().get(MapPlacer.class);
   }

   public static void fStlZIvY9lS8(boolean value) {
      EVSMbf = value;
   }

   public void onActivate() {
      this.pF16zz4NJECfg7 = 0;
      this.WGmfQKK = 0;
      this.icQ738dpWr7W = 0;
      this.JAj4YwE2MVx = 0;
      this.JPp6VyZP9f = -1;
      this.cO1FU7fW = Integer.MIN_VALUE;
      this.g0iiTDJzfIGXdui = null;
      if (!ZlNrrNcv3Ylc.QfZy2gWnKInxFPr()) {
         this.error("No canvas — look at the mapart's corner frame and run §b.mp origin topleft§r first.", new Object[0]);
         this.toggle();
      } else {
         ZlNrrNcv3Ylc.j376sIQ();
         ZlNrrNcv3Ylc.V7zKJ4n6D();
         ZlNrrNcv3Ylc.FVjbhYkk53UT();
         if (ZlNrrNcv3Ylc.nOwp0NYl() == 0) {
            this.warning("No item frames on the canvas yet. Walk closer so they load — auto-scan will pick them up.", new Object[0]);
         }
      }
   }

   public void onDeactivate() {
      RotationManager.release(10);
      this.vGAMA89H();
      this.g0iiTDJzfIGXdui = null;
      this.VIbihOaIVyhvJw = ZlNrrNcv3Ylc.QfZy2gWnKInxFPr() ? MapPlacer.Status.qhulAUwd : MapPlacer.Status.fStlZIvY9lS8;
   }

   @EventHandler
   private void fStlZIvY9lS8(Pre event) {
      if (this.mc.player != null && this.mc.world != null && this.mc.interactionManager != null) {
         this.pF16zz4NJECfg7++;
         RotationManager.release(10);
         if (!ZlNrrNcv3Ylc.QfZy2gWnKInxFPr()) {
            this.VIbihOaIVyhvJw = MapPlacer.Status.fStlZIvY9lS8;
         } else if (!EVSMbf && (!(Boolean)this.BrmKXSYhWhqoB1.get() || this.mc.currentScreen == null)) {
            if ((Boolean)this.qhulAUwd.get() && --this.JAj4YwE2MVx <= 0) {
               this.JAj4YwE2MVx = (Integer)this.V7zKJ4n6D.get();
               ZlNrrNcv3Ylc.j376sIQ();
            }

            ZlNrrNcv3Ylc.voxTwgNzyrpaAnP();
            ZlNrrNcv3Ylc.FVjbhYkk53UT();
            if (ZlNrrNcv3Ylc.nOwp0NYl() == 0) {
               this.VIbihOaIVyhvJw = MapPlacer.Status.QfZy2gWnKInxFPr;
            } else if (ZlNrrNcv3Ylc.zkcZ4Xkp9Ez7Y()) {
               boolean announce = this.VIbihOaIVyhvJw != MapPlacer.Status.BrmKXSYhWhqoB1;
               this.VIbihOaIVyhvJw = MapPlacer.Status.BrmKXSYhWhqoB1;
               this.g0iiTDJzfIGXdui = null;
               this.vGAMA89H();
               if (announce) {
                  if ((Boolean)this.I3psabmOrOI.get()) {
                     this.info("Mapart complete — §b%d§r frames filled.", new Object[]{ZlNrrNcv3Ylc.nOwp0NYl()});
                  }

                  if ((Boolean)this.TXWyNb.get()) {
                     this.toggle();
                  }
               }
            } else if (this.WGmfQKK > 0) {
               this.WGmfQKK--;
            } else {
               this.g0iiTDJzfIGXdui = this.wSJeYsJcmjNZf();
               if (this.g0iiTDJzfIGXdui == null) {
                  this.vGAMA89H();
               } else {
                  ItemFrameEntity frame = this.g0iiTDJzfIGXdui.wSJeYsJcmjNZf();
                  if (frame != null) {
                     if (!this.fStlZIvY9lS8(frame)) {
                        this.VIbihOaIVyhvJw = MapPlacer.Status.wSJeYsJcmjNZf;
                     } else {
                        int hotbarSlot = this.fStlZIvY9lS8(this.g0iiTDJzfIGXdui.QfZy2gWnKInxFPr());
                        if (hotbarSlot >= 0) {
                           if (this.icQ738dpWr7W > 0) {
                              this.icQ738dpWr7W--;
                              this.VIbihOaIVyhvJw = MapPlacer.Status.wSJeYsJcmjNZf;
                           } else {
                              this.fStlZIvY9lS8(frame, this.g0iiTDJzfIGXdui);
                           }
                        }
                     }
                  }
               }
            }
         } else {
            this.VIbihOaIVyhvJw = MapPlacer.Status.qhulAUwd;
            this.g0iiTDJzfIGXdui = null;
            this.vGAMA89H();
         }
      }
   }

   private JhujBv5.gVOhtBej1x wSJeYsJcmjNZf() {
      List<JhujBv5.gVOhtBej1x> pending = ZlNrrNcv3Ylc.flWHzEyn();
      boolean anyInRange = false;
      boolean anyCarried = false;

      for (JhujBv5.gVOhtBej1x cell : pending) {
         ItemFrameEntity frame = cell.wSJeYsJcmjNZf();
         if (frame != null && !(JhujBv5.fStlZIvY9lS8(frame) > (Double)this.wSJeYsJcmjNZf.get())) {
            anyInRange = true;
            if (cell.QfZy2gWnKInxFPr() >= 0) {
               anyCarried = true;
               if (!cell.fStlZIvY9lS8(this.pF16zz4NJECfg7, (Integer)MusheorSystem.Manager.placementTimeout.get())) {
                  this.VIbihOaIVyhvJw = MapPlacer.Status.TJImwK2;
                  return cell;
               }
            }
         }
      }

      this.VIbihOaIVyhvJw = !anyInRange ? MapPlacer.Status.i2Nu6XHKic : (anyCarried ? MapPlacer.Status.TJImwK2 : MapPlacer.Status.vGAMA89H);
      return null;
   }

   private boolean fStlZIvY9lS8(ItemFrameEntity frame) {
      if (this.i2Nu6XHKic.get() == MapPlacer.RotateMode.TJImwK2) {
         return true;
      } else {
         Vec3d aimPoint = frame.getBoundingBox().getCenter();
         Vec3d eye = this.mc.player.getEyePos();
         double dx = aimPoint.x - eye.x;
         double dy = aimPoint.y - eye.y;
         double dz = aimPoint.z - eye.z;
         float yaw = (float)Math.toDegrees(Math.atan2(dz, dx)) - 90.0F;
         float pitch = (float)(-Math.toDegrees(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz))));
         if (this.i2Nu6XHKic.get() == MapPlacer.RotateMode.QfZy2gWnKInxFPr) {
            this.mc.player.setYaw(yaw);
            this.mc.player.setPitch(pitch);
            return true;
         } else {
            boolean onTheWire = this.cO1FU7fW == this.pF16zz4NJECfg7 - 1
               && Math.abs(MathHelper.wrapDegrees(this.MR1DKzw - yaw)) <= 8.0F
               && Math.abs(this.DqGSCWplcjMi - pitch) <= 8.0F;
            if (RotationManager.setYaw(yaw, 10) && RotationManager.setPitch(pitch, 10)) {
               this.MR1DKzw = yaw;
               this.DqGSCWplcjMi = pitch;
               this.cO1FU7fW = this.pF16zz4NJECfg7;
            }

            return onTheWire;
         }
      }
   }

   private int fStlZIvY9lS8(int slot) {
      int selected = this.mc.player.getInventory().selectedSlot;
      if (slot > 8) {
         ItemStack stack = this.mc.player.getInventory().getStack(slot);
         InvUtils.move().from(slot).to(this.i2Nu6XHKic());
         if ((Boolean)this.I3psabmOrOI.get()) {
            this.info("Moved §b%s§r into the hotbar.", new Object[]{stack.getName().getString()});
         }

         this.icQ738dpWr7W = Math.max(1, (Integer)this.j376sIQ.get());
         this.VIbihOaIVyhvJw = MapPlacer.Status.wSJeYsJcmjNZf;
         return -1;
      } else {
         if (selected != slot) {
            if (this.JPp6VyZP9f < 0) {
               this.JPp6VyZP9f = selected;
            }

            PathKit.fStlZIvY9lS8(slot);
            this.icQ738dpWr7W = (Integer)this.j376sIQ.get();
            if (this.icQ738dpWr7W > 0) {
               this.VIbihOaIVyhvJw = MapPlacer.Status.wSJeYsJcmjNZf;
               return -1;
            }
         }

         return slot;
      }
   }

   private void fStlZIvY9lS8(ItemFrameEntity frame, JhujBv5.gVOhtBej1x cell) {
      String name = this.mc.player.getMainHandStack().getName().getString();
      VersionHelper.get().interactEntityAt(frame, Hand.MAIN_HAND);
      this.mc.interactionManager.sendSequencedPacket(this.mc.world, sequence -> PlayerInteractEntityC2SPacket.interact(frame, this.mc.player.isSneaking(), Hand.MAIN_HAND));
      cell.fStlZIvY9lS8(this.pF16zz4NJECfg7);
      this.WGmfQKK = (Integer)this.duJ6sPeetAcBh6P.get();
      if ((Boolean)this.I3psabmOrOI.get()) {
         this.info("Placed §b%s§r at §b%s§r.", new Object[]{name, cell.QfZy2gWnKInxFPr(ZlNrrNcv3Ylc.duJ6sPeetAcBh6P())});
      }
   }

   private int i2Nu6XHKic() {
      for (int slot = 0; slot < 9; slot++) {
         if (this.mc.player.getInventory().getStack(slot).isEmpty()) {
            return slot;
         }
      }

      for (int slotx = 0; slotx < 9; slotx++) {
         if (this.mc.player.getInventory().getStack(slotx).getItem() == Items.FILLED_MAP) {
            return slotx;
         }
      }

      return PathKit.wSJeYsJcmjNZf();
   }

   private void vGAMA89H() {
      if (this.JPp6VyZP9f >= 0) {
         if ((Boolean)this.vGAMA89H.get()) {
            PathKit.fStlZIvY9lS8(this.JPp6VyZP9f);
         }

         this.JPp6VyZP9f = -1;
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(Render3DEvent event) {
      if ((Boolean)this.voxTwgNzyrpaAnP.get() && ZlNrrNcv3Ylc.QfZy2gWnKInxFPr() && this.mc.player != null && !this.mc.options.hudHidden) {
         Direction facing = ZlNrrNcv3Ylc.vGAMA89H();
         double maxDistSq = (Double)this.nOwp0NYl.get() * (Double)this.nOwp0NYl.get();
         Vec3d eye = this.mc.player.getEyePos();

         for (JhujBv5.gVOhtBej1x cell : ZlNrrNcv3Ylc.xileXsbo()) {
            if ((!cell.fStlZIvY9lS8() || (Boolean)this.umvNbb7iD9UpR.get()) && !(Vec3d.ofCenter(cell.TJImwK2).squaredDistanceTo(eye) > maxDistSq)) {
               Color lines = this.fStlZIvY9lS8(cell);
               Color sides = new Color(lines.r, lines.g, lines.b, lines.a / 3);
               BlockPos pos = cell.TJImwK2;
               Vec3d front = JhujBv5.fStlZIvY9lS8(pos, facing, 0.0625);
               Vec3d back = JhujBv5.fStlZIvY9lS8(pos, facing, 0.0);
               double inset = 0.0625;
               boolean onX = facing.getAxis() == Direction.Axis.X;
               boolean onY = facing.getAxis() == Direction.Axis.Y;
               boolean onZ = facing.getAxis() == Direction.Axis.Z;
               event.renderer
                  .box(
                     onX ? Math.min(front.x, back.x) : pos.getX() + inset,
                     onY ? Math.min(front.y, back.y) : pos.getY() + inset,
                     onZ ? Math.min(front.z, back.z) : pos.getZ() + inset,
                     onX ? Math.max(front.x, back.x) : pos.getX() + 1 - inset,
                     onY ? Math.max(front.y, back.y) : pos.getY() + 1 - inset,
                     onZ ? Math.max(front.z, back.z) : pos.getZ() + 1 - inset,
                     lines,
                     sides,
                     (ShapeMode)this.xileXsbo.get(),
                     0
                  );
            }
         }
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(Render2DEvent event) {
      if ((Boolean)this.voxTwgNzyrpaAnP.get() && this.zkcZ4Xkp9Ez7Y.get() != MapPlacer.LabelMode.wSJeYsJcmjNZf) {
         if (ZlNrrNcv3Ylc.QfZy2gWnKInxFPr() && this.mc.player != null && !this.mc.options.hudHidden) {
            Direction facing = ZlNrrNcv3Ylc.vGAMA89H();
            double maxDistSq = (Double)this.nOwp0NYl.get() * (Double)this.nOwp0NYl.get();
            Vec3d eye = this.mc.player.getEyePos();
            TextRenderer textRenderer = TextRenderer.get();

            for (JhujBv5.gVOhtBej1x cell : ZlNrrNcv3Ylc.xileXsbo()) {
               if ((!cell.fStlZIvY9lS8() || (Boolean)this.umvNbb7iD9UpR.get()) && !(Vec3d.ofCenter(cell.TJImwK2).squaredDistanceTo(eye) > maxDistSq)) {
                  String text = this.QfZy2gWnKInxFPr(cell);
                  if (!text.isEmpty()) {
                     Vec3d world = JhujBv5.fStlZIvY9lS8(cell.TJImwK2, facing, 0.1);
                     Vector3d pos = new Vector3d(world.x, world.y, world.z);
                     if (NametagUtils.to2D(pos, (Double)this.flWHzEyn.get(), (Boolean)this.FVjbhYkk53UT.get())) {
                        NametagUtils.begin(pos);
                        textRenderer.begin();
                        double width = textRenderer.getWidth(text);
                        textRenderer.render(text, -width / 2.0, -textRenderer.getHeight() / 2.0, this.fStlZIvY9lS8(cell));
                        textRenderer.end();
                        NametagUtils.end();
                     }
                  }
               }
            }
         }
      }
   }

   private Color fStlZIvY9lS8(JhujBv5.gVOhtBej1x cell) {
      if (cell.fStlZIvY9lS8()) {
         return (Color)this.zbN84Osn9p5lD.get();
      } else if (cell == this.g0iiTDJzfIGXdui) {
         return (Color)this.jbUe84V1vykfK.get();
      } else {
         return cell.QfZy2gWnKInxFPr() >= 0 ? (Color)this.Nt3xeF.get() : (Color)this.zXcO5rZ.get();
      }
   }

   private String QfZy2gWnKInxFPr(JhujBv5.gVOhtBej1x cell) {
      int base = ZlNrrNcv3Ylc.duJ6sPeetAcBh6P();
      int index = cell.QfZy2gWnKInxFPr(ZlNrrNcv3Ylc.TXWyNb(), base);

      return switch ((MapPlacer.LabelMode)this.zkcZ4Xkp9Ez7Y.get()) {
         case fStlZIvY9lS8 -> cell.QfZy2gWnKInxFPr(base);
         case QfZy2gWnKInxFPr -> index < 0 ? cell.QfZy2gWnKInxFPr(base) : String.valueOf(index);
         case TJImwK2 -> index < 0 ? cell.QfZy2gWnKInxFPr(base) : cell.QfZy2gWnKInxFPr(base) + " #" + index;
         case wSJeYsJcmjNZf -> "";
      };
   }

   public String QfZy2gWnKInxFPr() {
      if (!ZlNrrNcv3Ylc.QfZy2gWnKInxFPr()) {
         return "§7no canvas";
      } else {
         String state = switch (this.VIbihOaIVyhvJw) {
            case fStlZIvY9lS8 -> "§7no canvas";
            case QfZy2gWnKInxFPr -> "§eno frames scanned";
            case TJImwK2 -> "§aplacing";
            case wSJeYsJcmjNZf -> "§aaiming";
            case i2Nu6XHKic -> "§ewalk closer";
            case vGAMA89H -> "§cmaps missing";
            case qhulAUwd -> "§epaused";
            case BrmKXSYhWhqoB1 -> "§adone";
         };
         return String.format("%s §7(%d/%d frames)", state, ZlNrrNcv3Ylc.umvNbb7iD9UpR(), ZlNrrNcv3Ylc.nOwp0NYl());
      }
   }

   public String getInfoString() {
      return ZlNrrNcv3Ylc.QfZy2gWnKInxFPr() ? ZlNrrNcv3Ylc.umvNbb7iD9UpR() + "/" + ZlNrrNcv3Ylc.nOwp0NYl() : "no canvas";
   }

   public static int TJImwK2() {
      ClientPlayerEntity player = MinecraftClient.getInstance().player;
      if (player == null) {
         return 0;
      } else {
         int count = 0;

         for (int slot = 0; slot < 36; slot++) {
            if (player.getInventory().getStack(slot).getItem() == Items.FILLED_MAP) {
               count++;
            }
         }

         return count;
      }
   }

   public static enum LabelMode {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr,
      TJImwK2,
      wSJeYsJcmjNZf;
   }

   public static enum RotateMode {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr,
      TJImwK2;
   }

   public static enum Status {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr,
      TJImwK2,
      wSJeYsJcmjNZf,
      i2Nu6XHKic,
      vGAMA89H,
      qhulAUwd,
      BrmKXSYhWhqoB1;
   }
}
