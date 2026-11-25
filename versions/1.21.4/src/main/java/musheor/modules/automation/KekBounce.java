package musheor.modules.automation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent.Receive;
import meteordevelopment.meteorclient.events.world.PlaySoundEvent;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.EnumSetting.Builder;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.player.AutoEat;
import meteordevelopment.meteorclient.systems.modules.player.ChestSwap;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.orbit.EventHandler;
import musheor.compat.VersionHelper;
import musheor.compat.XearoHelper;
import musheor.modules.automation.highway.HighwayNavigator;
import musheor.modules.automation.highway.HighwayRouter;
import musheor.utils.irzXX;
import musheor.utils.oyDPiOVBGKG;
import musheor.utils.pvfA15fQSVM;
import musheor.utils.manager.RotationManager;
import musheor.utils.manager.U6PQSXqW;
import musheor.utils.manager.gVOhtBej1x;
import musheor.utils.system.HighwayNetworkManager;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.DataTracker.SerializedEntry;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import obf.GyhoRo;

public class KekBounce extends Module {
   private final SettingGroup fStlZIvY9lS8 = this.settings.getDefaultGroup();
   private final SettingGroup QfZy2gWnKInxFPr = this.settings.createGroup("Grim");
   private final SettingGroup TJImwK2 = this.settings.createGroup("Rotation");
   private final SettingGroup wSJeYsJcmjNZf = this.settings.createGroup("Obstacle-Passer");
   private final SettingGroup i2Nu6XHKic = this.settings.createGroup("Auto-Repair");
   private final SettingGroup vGAMA89H = this.settings.createGroup("2bNav");
   private final Setting<KekBounce.Mode> qhulAUwd = this.fStlZIvY9lS8
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("mode"))
                  .description("Simple: freeform bounce. Navigate: Set and follows temporary waypoints created using Xearo"))
               .defaultValue(KekBounce.Mode.Simple))
            .build()
      );
   private final Setting<Boolean> BrmKXSYhWhqoB1 = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("mute-swap-sounds"))
                  .description("Mutes the armour equip and elytra wind sounds that fake-fly's swaps would play every hop."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> TXWyNb = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("fake-fly"))
                  .description(
                     "Bounce while wearing a chestplate. Keeps the elytra in your hotbar and only puts it on for the instant the glide is started, so it takes no durability."
                  ))
               .defaultValue(false))
            .build()
      );
   private final Setting<Boolean> I3psabmOrOI = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("toggle-elytra"))
                     .description("Equips an elytra on activate, and a chestplate on deactivate."))
                  .defaultValue(true))
               .visible(() -> !(Boolean)this.TXWyNb.get()))
            .build()
      );
   private final Setting<Integer> duJ6sPeetAcBh6P = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("fake-fly-hold"))
                     .description("Max ticks to keep gliding after the server ends the glide, holding back pongs meanwhile. 0 re-deploys mid-air instead."))
                  .defaultValue(12))
               .sliderRange(0, 20)
               .min(0)
               .visible(() -> false))
            .build()
      );
   private final Setting<Boolean> j376sIQ = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("lock-pitch"))
                  .description("Whether to lock your pitch when bouncing."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> V7zKJ4n6D = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                           .name("variable-pitch"))
                        .description(
                           "Changes the pitch every tick to the one that builds the most speed (about 18% faster than a fixed 72.4). Only the server-side rotation moves, your camera stays free."
                        ))
                     .defaultValue(false))
                  .visible(this.j376sIQ::get))
               .onChanged(value -> {
                  if (value) {
                     qhulAUwd();
                  }
               }))
            .build()
      );
   private final Setting<Double> voxTwgNzyrpaAnP = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)new meteordevelopment.meteorclient.settings.DoubleSetting.Builder()
                     .name("pitch"))
                  .description("Degrees of pitch to lock to"))
               .defaultValue(72.4)
               .sliderRange(-90.0, 90.0)
               .decimalPlaces(2)
               .visible(() -> (Boolean)this.j376sIQ.get() && !(Boolean)this.V7zKJ4n6D.get()))
            .build()
      );
   private final Setting<KekBounce.YawLock> xileXsbo = this.TJImwK2
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("yaw-lock"))
                  .description("Snap on activation, Snap to nearest axis, configure custom value or disable."))
               .defaultValue(KekBounce.YawLock.Onactivate))
            .build()
      );
   private final Setting<Double> nOwp0NYl = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)new meteordevelopment.meteorclient.settings.DoubleSetting.Builder()
                     .name("yaw"))
                  .description("Degrees of yaw to lock to"))
               .defaultValue(0.0)
               .sliderRange(-180.0, 180.0)
               .decimalPlaces(2)
               .visible(() -> this.xileXsbo.get() == KekBounce.YawLock.Custom))
            .build()
      );
   private final Setting<Boolean> umvNbb7iD9UpR = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("simple-obstacle-passer"))
                  .description("Uses baritone to pass obstacles and aligns you to your starting position when enabling the module"))
               .defaultValue(false))
            .build()
      );
   private final Setting<KekBounce.StartPos> zkcZ4Xkp9Ez7Y = this.wSJeYsJcmjNZf
      .add(
         ((Builder)((Builder)((Builder)((Builder)new Builder().name("start-pos-type"))
                     .description("Automatic or custom starting position for aligning with obstacle-passer"))
                  .defaultValue(KekBounce.StartPos.OnActivate))
               .visible(() -> this.qhulAUwd.get() == KekBounce.Mode.Simple && (Boolean)this.umvNbb7iD9UpR.get()))
            .build()
      );
   private final Setting<KekBounce.Height> flWHzEyn = this.wSJeYsJcmjNZf
      .add(
         ((Builder)((Builder)((Builder)((Builder)new Builder().name("target-y")).description("The Y level to bounce at."))
                  .defaultValue(KekBounce.Height.Auto))
               .visible(this.umvNbb7iD9UpR::get))
            .build()
      );
   private final Setting<Integer> FVjbhYkk53UT = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("y-level"))
                     .description("The Y level to bounce at."))
                  .defaultValue(120))
               .sliderRange(-64, 120)
               .visible(() -> (Boolean)this.umvNbb7iD9UpR.get() && this.flWHzEyn.get() == KekBounce.Height.Manual))
            .build()
      );
   private final Setting<BlockPos> Nt3xeF = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.BlockPosSetting.Builder)((meteordevelopment.meteorclient.settings.BlockPosSetting.Builder)((meteordevelopment.meteorclient.settings.BlockPosSetting.Builder)((meteordevelopment.meteorclient.settings.BlockPosSetting.Builder)((meteordevelopment.meteorclient.settings.BlockPosSetting.Builder)new meteordevelopment.meteorclient.settings.BlockPosSetting.Builder()
                           .name("custom-start-pos"))
                        .description("The position to align with when using the custom starting position option."))
                     .defaultValue(new BlockPos(0, 0, 0)))
                  .visible(
                     () -> this.qhulAUwd.get() == KekBounce.Mode.Simple
                        && (Boolean)this.umvNbb7iD9UpR.get()
                        && this.zkcZ4Xkp9Ez7Y.get() == KekBounce.StartPos.Custom
                  ))
               .onChanged(value -> this.npfimrDr0hky = value))
            .build()
      );
   private final Setting<Integer> zXcO5rZ = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("distance"))
                     .description("The distance to set the baritone goal for path realignment."))
                  .defaultValue(8))
               .visible(this.umvNbb7iD9UpR::get))
            .build()
      );
   private final Setting<Integer> jbUe84V1vykfK = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("stuck-window"))
                     .description("Ticks over which your progress is measured to decide whether you are stuck."))
                  .defaultValue(30))
               .sliderRange(10, 100)
               .min(5)
               .visible(this.umvNbb7iD9UpR::get))
            .build()
      );
   private final Setting<Double> zbN84Osn9p5lD = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)new meteordevelopment.meteorclient.settings.DoubleSetting.Builder()
                     .name("stuck-distance"))
                  .description("You count as stuck when you moved less than this many blocks over the stuck window."))
               .defaultValue(4.0)
               .sliderRange(0.5, 20.0)
               .decimalPlaces(1)
               .min(0.0)
               .visible(this.umvNbb7iD9UpR::get))
            .build()
      );
   private final Setting<Integer> ZlNrrNcv3Ylc = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("setback-limit"))
                     .description("You count as stuck after this many server setbacks (rubberbands) within the stuck window. 0 disables."))
                  .defaultValue(3))
               .sliderRange(0, 10)
               .min(0)
               .visible(this.umvNbb7iD9UpR::get))
            .build()
      );
   private final Setting<Boolean> EVSMbf = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("show-eta"))
                     .description("Displays and calculates arrival time above the player's hotbar."))
                  .defaultValue(true))
               .visible(
                  () -> XearoHelper.isLoaded() && FabricLoader.getInstance().isModLoaded("xaeroplus") && this.qhulAUwd.get() == KekBounce.Mode.Advanced
               ))
            .build()
      );
   private final Setting<Boolean> pF16zz4NJECfg7 = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("render-path-on-map"))
                     .description("Displays your current navigation path on your minimap / worldmap."))
                  .defaultValue(true))
               .visible(
                  () -> XearoHelper.isLoaded() && FabricLoader.getInstance().isModLoaded("xaeroplus") && this.qhulAUwd.get() == KekBounce.Mode.Advanced
               ))
            .build()
      );
   private final Setting<Double> WGmfQKK = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)new meteordevelopment.meteorclient.settings.DoubleSetting.Builder()
                     .name("approach-radius"))
                  .description("Start slowing down when within this many blocks of a waypoint."))
               .defaultValue(64.0)
               .sliderRange(16.0, 256.0)
               .decimalPlaces(0)
               .visible(() -> XearoHelper.isLoaded() && this.qhulAUwd.get() == KekBounce.Mode.Advanced))
            .build()
      );
   private final Setting<Double> icQ738dpWr7W = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)new meteordevelopment.meteorclient.settings.DoubleSetting.Builder()
                     .name("arrive-radius"))
                  .description("Consider a waypoint reached when within this many blocks."))
               .defaultValue(32.0)
               .sliderRange(8.0, 128.0)
               .decimalPlaces(0)
               .visible(() -> XearoHelper.isLoaded() && this.qhulAUwd.get() == KekBounce.Mode.Advanced))
            .build()
      );
   private final Setting<Integer> JAj4YwE2MVx = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("align-timeout"))
                     .description("Ticks to spend aligning yaw before bouncing anyway."))
                  .defaultValue(40))
               .sliderRange(10, 100)
               .visible(() -> XearoHelper.isLoaded() && this.qhulAUwd.get() == KekBounce.Mode.Advanced))
            .build()
      );
   private final Setting<Double> JPp6VyZP9f = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)new meteordevelopment.meteorclient.settings.DoubleSetting.Builder()
                     .name("yaw-tolerance"))
                  .description("Degrees of yaw error considered close enough to start bouncing."))
               .defaultValue(4.0)
               .sliderRange(1.0, 20.0)
               .decimalPlaces(1)
               .visible(() -> XearoHelper.isLoaded() && this.qhulAUwd.get() == KekBounce.Mode.Advanced))
            .build()
      );
   private final Setting<Integer> MR1DKzw = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("transition-distance"))
                     .description("Baritone goal distance when walking an intersection."))
                  .defaultValue(8))
               .sliderRange(1, 32)
               .visible(() -> XearoHelper.isLoaded() && this.qhulAUwd.get() == KekBounce.Mode.Advanced))
            .build()
      );
   private final Setting<Integer> DqGSCWplcjMi = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("settle-ticks"))
                     .description("Ticks to wait at an intersection after arriving before resuming bounce."))
                  .defaultValue(10))
               .sliderRange(0, 40)
               .visible(() -> XearoHelper.isLoaded() && this.qhulAUwd.get() == KekBounce.Mode.Advanced))
            .build()
      );
   private final Setting<Boolean> cO1FU7fW = this.i2Nu6XHKic
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("repair-elytra"))
                  .description("Uses experience bottles to repair your elytra."))
               .defaultValue(false))
            .build()
      );
   private final Setting<Integer> g0iiTDJzfIGXdui = this.i2Nu6XHKic
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("damage-threshold-%"))
                     .description("At what durability percentage should it start repairing the elytra."))
                  .defaultValue(5))
               .sliderRange(0, 100)
               .visible(this.cO1FU7fW::get))
            .build()
      );
   private final Setting<Integer> VIbihOaIVyhvJw = this.i2Nu6XHKic
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("repair-threshold-%"))
                     .description("At what durability percentage should it stop repairing the elytra."))
                  .defaultValue(90))
               .sliderRange(0, 100)
               .visible(this.cO1FU7fW::get))
            .build()
      );
   private final Setting<String> RE8q1TMDRWx = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.StringSetting.Builder)((meteordevelopment.meteorclient.settings.StringSetting.Builder)((meteordevelopment.meteorclient.settings.StringSetting.Builder)((meteordevelopment.meteorclient.settings.StringSetting.Builder)((meteordevelopment.meteorclient.settings.StringSetting.Builder)new meteordevelopment.meteorclient.settings.StringSetting.Builder()
                           .name("server"))
                        .description("Which server's highway network to use. Must match a key in the remote config (e.g. \"2b2t\")."))
                     .defaultValue("2b2t"))
                  .onChanged(
                     value -> {
                        HighwayNetworkManager mgr = HighwayNetworkManager.getInstance();
                        if (!mgr.isLoaded()) {
                           this.warning("Highway configs are still loading — server key will be validated once loaded.", new Object[0]);
                        } else {
                           if (!mgr.getServerNames().contains(value)) {
                              this.error(
                                 "Unknown server \"(highlight)%s(default)\". Known servers: (highlight)%s(default).",
                                 new Object[]{value, String.join(", ", mgr.getServerNames())}
                              );
                           }
                        }
                     }
                  ))
               .visible(() -> false))
            .build()
      );
   private oyDPiOVBGKG.gVOhtBej1x vLRVDxlh;
   private int HomZ7BbhM7wB;
   private int lVX1t0MC;
   private boolean xarqg2oUiwotDG4;
   private boolean RFflNsLXot;
   private Float F4hivB;
   private HighwayNavigator bDU9FLOzvL = null;
   private XearoHelper.WaypointData PuZZ3s4mpaTz = null;
   private int Njcn1X5he = -1;
   private int PVlYDeNipUe;
   private BlockPos npfimrDr0hky;
   private final ArrayDeque<Vec3d> UPNmQKi0eiVUue = new ArrayDeque<>();
   private final ArrayDeque<Integer> UsQcxwgBUuV = new ArrayDeque<>();
   private final AtomicInteger yYqDk4cMM0 = new AtomicInteger();
   private int X9ysJTxpWn2 = Integer.MIN_VALUE;
   private static volatile pvfA15fQSVM H7J5bawM;
   private static boolean Uag2UiCQCqA;
   private double Kl8YPWLVNx7VE;
   private boolean ez9qLff15LVy;
   private int pAwSMtP8;
   private int GSmbIrINgvYzyhK;
   private int dje1llF2o;
   private int VLEShZ5mkNBQ4PS0;
   private boolean vijg33HMhCnyX;
   private volatile boolean oF40kxwxfq4ZX;
   private volatile boolean jAZp22H8V;
   private int U1qL6cLGWxa;
   private final Object ZSlhR16SSPV = new Object();
   private int pq4J656cQwYF = -1;
   private int QMnoHQWxY8gY = -1;
   private boolean auOYKcACfh9b;

   public KekBounce() {
      super(musheor.musheor.AUTOMATION, "kek-bounce", "Elytra bounce module with some extra juice.");
   }

   public void onActivate() {
      if (this.mc.player != null && this.mc.world != null) {
         this.vLRVDxlh = oyDPiOVBGKG.duJ6sPeetAcBh6P();
         this.zXcO5rZ();
         this.HomZ7BbhM7wB = 0;
         this.lVX1t0MC = 0;
         this.xarqg2oUiwotDG4 = false;
         this.RFflNsLXot = false;
         this.PVlYDeNipUe = 0;
         this.Kl8YPWLVNx7VE = this.mc.player.getY();
         this.ez9qLff15LVy = false;
         if ((Boolean)this.j376sIQ.get() && (Boolean)this.V7zKJ4n6D.get()) {
            qhulAUwd();
         }

         this.npfimrDr0hky = this.zkcZ4Xkp9Ez7Y.get() == KekBounce.StartPos.OnActivate
            ? BlockPos.ofFloored(VersionHelper.get().getPlayerPos())
            : (BlockPos)this.Nt3xeF.get();
         if (this.flWHzEyn.get() == KekBounce.Height.Auto) {
            this.FVjbhYkk53UT.set(this.mc.player.getBlockY());
         }

         this.pAwSMtP8 = 0;
         this.GSmbIrINgvYzyhK = -2;
         this.dje1llF2o = -2;
         this.VLEShZ5mkNBQ4PS0 = 0;
         this.vijg33HMhCnyX = true;
         this.oF40kxwxfq4ZX = false;
         this.duJ6sPeetAcBh6P();
         this.pq4J656cQwYF = -1;
         this.QMnoHQWxY8gY = -1;
         this.auOYKcACfh9b = false;
         if ((Boolean)this.TXWyNb.get()) {
            this.QMnoHQWxY8gY = this.mc.player.getInventory().selectedSlot;
            gVOhtBej1x.wSJeYsJcmjNZf();
            this.pq4J656cQwYF = gVOhtBej1x.i2Nu6XHKic();
            if (this.pq4J656cQwYF == -1) {
               this.warning("Fake-fly needs an elytra in your hotbar — bouncing on whatever is on your chest instead.", new Object[0]);
            }
         } else if ((Boolean)this.I3psabmOrOI.get() && !this.mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem().equals(Items.ELYTRA)) {
            ((ChestSwap)Modules.get().get(ChestSwap.class)).swap();
         }

         if (this.xileXsbo.get() == KekBounce.YawLock.Onactivate) {
            this.F4hivB = this.mc.player.getYaw();
         }

         if (this.qhulAUwd.get() == KekBounce.Mode.Advanced) {
            if ((Boolean)this.pF16zz4NJECfg7.get() && !FabricLoader.getInstance().isModLoaded("xaeroplus")) {
               this.warning("Cannot render path on map, %sXaeroPlus %srequired but not installed!", new Object[]{Formatting.GOLD, Formatting.YELLOW});
               this.pF16zz4NJECfg7.set(false);
            }

            if ((Boolean)this.EVSMbf.get() && !FabricLoader.getInstance().isModLoaded("xaeroplus")) {
               this.warning("Cannot show ETA, %sXaeroPlus %srequired but not installed!", new Object[]{Formatting.GOLD, Formatting.YELLOW});
               this.EVSMbf.set(false);
            }

            if (!XearoHelper.isLoaded()) {
               this.warning("AdvancedNavigation requires %sXaero%s. Install it or switch to Simple mode.", new Object[]{Formatting.RED, Formatting.YELLOW});
               this.toggle();
               return;
            }

            this.TJImwK2();
         } else {
            this.QfZy2gWnKInxFPr();
         }
      }
   }

   public void onDeactivate() {
      if (this.bDU9FLOzvL != null) {
         this.bDU9FLOzvL.TJImwK2();
         this.bDU9FLOzvL = null;
      }

      this.PuZZ3s4mpaTz = null;
      this.Njcn1X5he = -1;
      if (XearoHelper.isLoaded()) {
         XearoHelper.get().clearLinesOnMap();
      }

      this.oF40kxwxfq4ZX = false;
      this.duJ6sPeetAcBh6P();
      if ((Boolean)this.TXWyNb.get()) {
         if (this.QMnoHQWxY8gY >= 0) {
            musheor.utils.manager.pvfA15fQSVM.fStlZIvY9lS8(this.QMnoHQWxY8gY);
         }

         this.QMnoHQWxY8gY = -1;
         this.auOYKcACfh9b = false;
      } else if ((Boolean)this.I3psabmOrOI.get()) {
         ((ChestSwap)Modules.get().get(ChestSwap.class)).swap();
      }

      if (GyhoRo.fStlZIvY9lS8()) {
         GyhoRo.wSJeYsJcmjNZf();
      }

      irzXX.QfZy2gWnKInxFPr();
      this.F4hivB = null;
      if (this.ez9qLff15LVy) {
         RotationManager.clear();
      }

      this.ez9qLff15LVy = false;
   }

   private void QfZy2gWnKInxFPr() {
      this.vLRVDxlh = oyDPiOVBGKG.duJ6sPeetAcBh6P();
      this.zXcO5rZ();
      this.HomZ7BbhM7wB = 0;
      this.lVX1t0MC = 0;
      this.xarqg2oUiwotDG4 = false;
      this.RFflNsLXot = false;
   }

   private void TJImwK2() {
      XearoHelper.WaypointData wp = XearoHelper.get().getOldestWaypoint(true);
      if (wp == null) {
         ChatUtils.info(Formatting.AQUA + "[2bNav] " + Formatting.WHITE + "No temp waypoints found!", new Object[0]);
         this.toggle();
      } else {
         this.fStlZIvY9lS8(wp);
      }
   }

   private void fStlZIvY9lS8(XearoHelper.WaypointData wp) {
      this.PuZZ3s4mpaTz = wp;
      double px = this.mc.player.getX();
      double pz = this.mc.player.getZ();
      double gx = wp.x();
      double gz = wp.z();
      ChatUtils.info(
         Formatting.AQUA
            + "[2bNav] "
            + Formatting.WHITE
            + "Navigating to waypoint: "
            + Formatting.YELLOW
            + wp.name()
            + Formatting.WHITE
            + " ("
            + (int)gx
            + ", "
            + (int)gz
            + ")",
         new Object[0]
      );
      HighwayRouter.Route route = HighwayRouter.fStlZIvY9lS8(px, pz, gx, gz);
      if (route == null) {
         ChatUtils.info(Formatting.AQUA + "[2bNav] " + Formatting.WHITE + "No highway route found to waypoint: " + wp.name(), new Object[0]);
         this.toggle();
      } else {
         this.fStlZIvY9lS8(route);
         HighwayNavigator.Config navConfig = new HighwayNavigator.Config(
            (Double)this.WGmfQKK.get(),
            (Double)this.icQ738dpWr7W.get(),
            (Integer)this.JAj4YwE2MVx.get(),
            ((Double)this.JPp6VyZP9f.get()).floatValue(),
            (Integer)this.MR1DKzw.get(),
            (Integer)this.DqGSCWplcjMi.get()
         );
         this.bDU9FLOzvL = new HighwayNavigator(route, navConfig, new HighwayNavigator.BounceController() {
            @Override
            public void fStlZIvY9lS8(boolean active) {
               KekBounce.this.xarqg2oUiwotDG4 = !active;
            }
         }, new HighwayNavigator.Listener() {
            @Override
            public void fStlZIvY9lS8(HighwayNavigator.State newState, String description) {
               ChatUtils.info(Formatting.AQUA + "[2bNav] " + Formatting.WHITE + description, new Object[0]);
            }

            @Override
            public void fStlZIvY9lS8() {
               XearoHelper.get().deleteCurrentWaypoint(KekBounce.this.PuZZ3s4mpaTz);
               KekBounce.this.PuZZ3s4mpaTz = null;
               XearoHelper.WaypointData next = XearoHelper.get().getOldestWaypoint(true);
               if (next == null) {
                  ChatUtils.info(Formatting.AQUA + "[2bNav] " + Formatting.WHITE + "No waypoints found, assuming destination was reached!", new Object[0]);
                  KekBounce.this.toggle();
               } else {
                  KekBounce.this.fStlZIvY9lS8(next);
               }
            }
         });
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(Pre event) {
      if (this.isActive()) {
         if (this.mc.player != null && this.mc.world != null) {
            this.oF40kxwxfq4ZX = false;
            if (this.ez9qLff15LVy) {
               RotationManager.release(0);
               this.ez9qLff15LVy = false;
            }

            if (this.mc.player.isOnGround()) {
               this.Kl8YPWLVNx7VE = this.mc.player.getY();
            }

            if (this.qhulAUwd.get() == KekBounce.Mode.Advanced) {
               this.wSJeYsJcmjNZf();
            } else {
               this.i2Nu6XHKic();
            }

            if (!this.oF40kxwxfq4ZX && (this.jAZp22H8V || U6PQSXqW.QfZy2gWnKInxFPr())) {
               this.duJ6sPeetAcBh6P();
            }
         }
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(Receive event) {
      if (event.packet instanceof PlayerPositionLookS2CPacket) {
         this.yYqDk4cMM0.incrementAndGet();
      }

      if (this.oF40kxwxfq4ZX) {
         if (!(event.packet instanceof PlayerPositionLookS2CPacket)) {
            if (event.packet instanceof EntityTrackerUpdateS2CPacket packet && (Integer)this.duJ6sPeetAcBh6P.get() > 0) {
               ClientPlayerEntity player = this.mc.player;
               if (player != null && packet.id() == player.getId() && player.isGliding()) {
                  List<SerializedEntry<?>> values = packet.trackedValues();

                  for (int i = 0; i < values.size(); i++) {
                     SerializedEntry<?> entry = values.get(i);
                     if (entry.id() == 0 && entry.value() instanceof Byte flags) {
                        if ((flags & -128) != 0) {
                           return;
                        }

                        TrackedDataHandler<Byte> handler = (TrackedDataHandler<Byte>)(TrackedDataHandler<?>)entry.handler();
                        synchronized (this.ZSlhR16SSPV) {
                           try {
                              values.set(i, new SerializedEntry(entry.id(), handler, (byte)(flags | -128)));
                           } catch (UnsupportedOperationException e) {
                              return;
                           }

                           this.U1qL6cLGWxa = 0;
                           this.jAZp22H8V = true;
                           U6PQSXqW.fStlZIvY9lS8();
                           return;
                        }
                     }
                  }
               }
            }
         } else {
            if (this.jAZp22H8V || U6PQSXqW.QfZy2gWnKInxFPr()) {
               U6PQSXqW.TJImwK2();
               this.mc.execute(this::duJ6sPeetAcBh6P);
            }
         }
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(PlaySoundEvent event) {
      if (this.oF40kxwxfq4ZX && (Boolean)this.BrmKXSYhWhqoB1.get()) {
         String path = event.sound.getId().getPath();
         if (path.startsWith("item.armor.equip_") || path.equals("item.elytra.flying")) {
            event.cancel();
         }
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(GameLeftEvent event) {
      this.oF40kxwxfq4ZX = false;
      this.jAZp22H8V = false;
      this.U1qL6cLGWxa = 0;
      U6PQSXqW.wSJeYsJcmjNZf();
   }

   private void wSJeYsJcmjNZf() {
      if (this.RFflNsLXot) {
         this.ZlNrrNcv3Ylc();
      } else if (this.zbN84Osn9p5lD()) {
         this.RFflNsLXot = true;
      } else {
         if (this.qhulAUwd.get() == KekBounce.Mode.Advanced && (Boolean)this.pF16zz4NJECfg7.get()) {
            this.EVSMbf();
         }

         if ((Boolean)this.EVSMbf.get()) {
            this.pF16zz4NJECfg7();
         }

         if (this.bDU9FLOzvL != null) {
            if ((Boolean)this.umvNbb7iD9UpR.get() && this.bDU9FLOzvL.fStlZIvY9lS8() == HighwayNavigator.State.BOUNCING) {
               this.umvNbb7iD9UpR();
            }

            this.bDU9FLOzvL.QfZy2gWnKInxFPr();
            if (this.fStlZIvY9lS8()) {
               this.mc.player.setSprinting(true);
               if (!GyhoRo.TJImwK2()) {
                  this.vGAMA89H();
               }

               irzXX.fStlZIvY9lS8(this.mc.options.jumpKey, true);
               irzXX.fStlZIvY9lS8(this.mc.options.forwardKey, true);
               this.BrmKXSYhWhqoB1();
            }
         }
      }
   }

   private void i2Nu6XHKic() {
      if ((Boolean)this.umvNbb7iD9UpR.get()) {
         this.umvNbb7iD9UpR();
      }

      if (!this.xarqg2oUiwotDG4 && !GyhoRo.TJImwK2()) {
         this.mc.player.setSprinting(true);
         if (this.RFflNsLXot) {
            this.ZlNrrNcv3Ylc();
         } else if (this.zbN84Osn9p5lD()) {
            this.RFflNsLXot = true;
         } else {
            this.FVjbhYkk53UT();
            this.vGAMA89H();
            irzXX.fStlZIvY9lS8(this.mc.options.jumpKey, true);
            irzXX.fStlZIvY9lS8(this.mc.options.forwardKey, true);
            this.BrmKXSYhWhqoB1();
         }
      } else {
         this.fStlZIvY9lS8(false);
      }
   }

   private void vGAMA89H() {
      if ((Boolean)this.j376sIQ.get()) {
         pvfA15fQSVM policy = this.V7zKJ4n6D.get() ? qhulAUwd() : null;
         if (policy != null
            && !this.mc.player.hasStatusEffect(StatusEffects.SLOW_FALLING)
            && !this.mc.player.hasStatusEffect(StatusEffects.LEVITATION)
            && !this.mc.player.hasStatusEffect(StatusEffects.JUMP_BOOST)) {
            float p = policy.fStlZIvY9lS8(this.mc.player.getY() - this.Kl8YPWLVNx7VE, this.mc.player.getVelocity().y, this.mc.player.isOnGround());
            if (RotationManager.setPitch(p, 0)) {
               this.ez9qLff15LVy = true;
            }
         } else {
            this.mc.player.setPitch(((Double)this.voxTwgNzyrpaAnP.get()).floatValue());
         }
      }
   }

   private static pvfA15fQSVM qhulAUwd() {
      if (H7J5bawM == null && !Uag2UiCQCqA) {
         Uag2UiCQCqA = true;
         Thread solver = new Thread(() -> H7J5bawM = pvfA15fQSVM.fStlZIvY9lS8(0.08, 0.42), "musheor-bounce-pitch");
         solver.setDaemon(true);
         solver.start();
      }

      return H7J5bawM;
   }

   private void BrmKXSYhWhqoB1() {
      if (!(Boolean)this.TXWyNb.get() || !this.TXWyNb()) {
         if (!this.mc.player.isGliding() && !this.mc.player.isOnGround()) {
            this.mc.player.startGliding();
            this.mc
               .getNetworkHandler()
               .sendPacket(new ClientCommandC2SPacket(this.mc.player, net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode.START_FALL_FLYING));
         }
      }
   }

   private boolean TXWyNb() {
      if (((AutoEat)Modules.get().get(AutoEat.class)).eating) {
         if (!this.auOYKcACfh9b) {
            this.auOYKcACfh9b = true;
            if (!this.voxTwgNzyrpaAnP() && this.xileXsbo()) {
               this.j376sIQ();
            }

            if (this.QMnoHQWxY8gY >= 0) {
               musheor.utils.manager.pvfA15fQSVM.fStlZIvY9lS8(this.QMnoHQWxY8gY);
            }
         }

         return false;
      } else {
         if (this.auOYKcACfh9b) {
            this.auOYKcACfh9b = false;
            if (this.V7zKJ4n6D() >= 0) {
               musheor.utils.manager.pvfA15fQSVM.fStlZIvY9lS8(this.pq4J656cQwYF);
            }

            if (this.voxTwgNzyrpaAnP() && this.nOwp0NYl()) {
               this.j376sIQ();
            }
         }

         if (this.V7zKJ4n6D() < 0) {
            return false;
         }

         musheor.utils.manager.pvfA15fQSVM.fStlZIvY9lS8(this.pq4J656cQwYF);
         this.oF40kxwxfq4ZX = true;
         this.pAwSMtP8++;
         boolean ground = this.mc.player.isOnGround();
         if (ground) {
            this.VLEShZ5mkNBQ4PS0 = 0;
            this.vijg33HMhCnyX = true;
         } else {
            this.VLEShZ5mkNBQ4PS0++;
         }

         if (this.voxTwgNzyrpaAnP() && this.nOwp0NYl() && this.pAwSMtP8 - this.dje1llF2o >= 2) {
            this.dje1llF2o = this.pAwSMtP8;
            this.j376sIQ();
         }

         if (this.jAZp22H8V) {
            this.U1qL6cLGWxa++;
            boolean nextHop = this.vijg33HMhCnyX && this.VLEShZ5mkNBQ4PS0 >= 2;
            if (nextHop || this.U1qL6cLGWxa > (Integer)this.duJ6sPeetAcBh6P.get()) {
               this.duJ6sPeetAcBh6P();
            }
         }

         if (!ground && this.VLEShZ5mkNBQ4PS0 >= 2 && !this.mc.player.isGliding() && this.pAwSMtP8 - this.GSmbIrINgvYzyhK >= 2) {
            this.I3psabmOrOI();
         }

         return true;
      }
   }

   private void I3psabmOrOI() {
      if (this.xileXsbo() && !this.voxTwgNzyrpaAnP()) {
         this.j376sIQ();
         if (this.voxTwgNzyrpaAnP()) {
            this.mc
               .getNetworkHandler()
               .sendPacket(new ClientCommandC2SPacket(this.mc.player, net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode.START_FALL_FLYING));
            this.mc.player.startGliding();
            this.j376sIQ();
            this.GSmbIrINgvYzyhK = this.pAwSMtP8;
            this.vijg33HMhCnyX = false;
         }
      }
   }

   private void duJ6sPeetAcBh6P() {
      boolean held;
      synchronized (this.ZSlhR16SSPV) {
         held = this.jAZp22H8V;
         this.jAZp22H8V = false;
         this.U1qL6cLGWxa = 0;
         U6PQSXqW.TJImwK2();
      }

      if (held && this.mc.player != null && this.mc.player.isGliding()) {
         this.mc.player.stopGliding();
      }
   }

   private void j376sIQ() {
      if (this.mc.interactionManager != null) {
         float savedYaw = this.mc.player.getYaw();
         float savedPitch = this.mc.player.getPitch();
         this.mc.player.setYaw(RotationManager.yawOr(savedYaw));
         this.mc.player.setPitch(RotationManager.pitchOr(savedPitch));

         try {
            this.mc.interactionManager.interactItem(this.mc.player, Hand.MAIN_HAND);
         } finally {
            this.mc.player.setYaw(savedYaw);
            this.mc.player.setPitch(savedPitch);
         }
      }
   }

   private int V7zKJ4n6D() {
      if (this.pq4J656cQwYF >= 0 && this.pq4J656cQwYF <= 8) {
         ItemStack held = this.mc.player.getInventory().getStack(this.pq4J656cQwYF);
         if (held.isOf(Items.ELYTRA) || fStlZIvY9lS8(held)) {
            return this.pq4J656cQwYF;
         }
      }

      return this.pq4J656cQwYF = gVOhtBej1x.i2Nu6XHKic();
   }

   private boolean voxTwgNzyrpaAnP() {
      return this.mc.player.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA);
   }

   private boolean xileXsbo() {
      return this.mc.player.getMainHandStack().isOf(Items.ELYTRA);
   }

   private boolean nOwp0NYl() {
      return fStlZIvY9lS8(this.mc.player.getMainHandStack());
   }

   private static boolean fStlZIvY9lS8(ItemStack stack) {
      if (stack.isOf(Items.ELYTRA)) {
         return false;
      }

      EquippableComponent equippable = (EquippableComponent)stack.get(DataComponentTypes.EQUIPPABLE);
      return equippable != null && equippable.slot() == EquipmentSlot.CHEST;
   }

   private void umvNbb7iD9UpR() {
      if (this.xarqg2oUiwotDG4) {
         if (GyhoRo.TJImwK2() && ++this.HomZ7BbhM7wB < 200) {
            this.fStlZIvY9lS8(false);
         } else {
            this.flWHzEyn();
         }
      } else if (this.lVX1t0MC > 0) {
         this.lVX1t0MC--;
      } else {
         boolean yLevelWrong = this.qhulAUwd.get() != KekBounce.Mode.Advanced
            && (
               VersionHelper.get().getPlayerPos().getY() < ((Integer)this.FVjbhYkk53UT.get()).intValue()
                  || VersionHelper.get().getPlayerPos().getY() > (Integer)this.FVjbhYkk53UT.get() + 2
            );
         if (!yLevelWrong && (!this.mc.player.horizontalCollision || this.mc.player.collidedSoftly)) {
            if (!this.RFflNsLXot && this.Nt3xeF()) {
               this.zkcZ4Xkp9Ez7Y();
            }
         } else {
            this.zkcZ4Xkp9Ez7Y();
         }
      }
   }

   private void zkcZ4Xkp9Ez7Y() {
      this.xarqg2oUiwotDG4 = true;
      this.zXcO5rZ();
      this.HomZ7BbhM7wB = 0;
      this.PVlYDeNipUe = 0;
      this.fStlZIvY9lS8(false);
      GyhoRo.fStlZIvY9lS8(this.fStlZIvY9lS8((Integer)this.zXcO5rZ.get()));
   }

   private void flWHzEyn() {
      if (GyhoRo.TJImwK2()) {
         GyhoRo.wSJeYsJcmjNZf();
      }

      this.xarqg2oUiwotDG4 = false;
      this.zXcO5rZ();
      this.HomZ7BbhM7wB = 0;
      this.lVX1t0MC = 20;
      this.PVlYDeNipUe = 0;
      if (this.qhulAUwd.get() != KekBounce.Mode.Advanced && this.vLRVDxlh != null) {
         this.mc.player.setYaw(oyDPiOVBGKG.gVOhtBej1x.fStlZIvY9lS8(this.vLRVDxlh));
      }
   }

   private void FVjbhYkk53UT() {
      KekBounce.YawLock yawMode = (KekBounce.YawLock)this.xileXsbo.get();
      switch (yawMode) {
         case Onactivate:
            if (this.F4hivB != null) {
               this.mc.player.setYaw(oyDPiOVBGKG.gVOhtBej1x.fStlZIvY9lS8(oyDPiOVBGKG.gVOhtBej1x.fStlZIvY9lS8(this.F4hivB)));
            }
            break;
         case Snap:
            oyDPiOVBGKG.gVOhtBej1x direction = oyDPiOVBGKG.duJ6sPeetAcBh6P();
            this.mc.player.setYaw(oyDPiOVBGKG.gVOhtBej1x.fStlZIvY9lS8(direction));
            break;
         case Custom:
            this.mc.player.setYaw(((Double)this.nOwp0NYl.get()).floatValue());
      }
   }

   private void fStlZIvY9lS8(boolean notifyServer) {
      irzXX.QfZy2gWnKInxFPr();
      if (this.mc.player != null && this.mc.player.isGliding()) {
         if (notifyServer && this.mc.getNetworkHandler() != null) {
            this.mc
               .getNetworkHandler()
               .sendPacket(new ClientCommandC2SPacket(this.mc.player, net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode.START_FALL_FLYING));
         }

         this.mc.player.stopGliding();
      }
   }

   private boolean Nt3xeF() {
      if (this.mc.player == null) {
         return false;
      }

      int age = this.mc.player.age;
      if (age != this.X9ysJTxpWn2 + 1) {
         this.zXcO5rZ();
      }

      this.X9ysJTxpWn2 = age;
      int window = (Integer)this.jbUe84V1vykfK.get();

      for (int i = this.yYqDk4cMM0.getAndSet(0); i > 0; i--) {
         this.UsQcxwgBUuV.addLast(age);
      }

      while (!this.UsQcxwgBUuV.isEmpty() && age - this.UsQcxwgBUuV.peekFirst() >= window) {
         this.UsQcxwgBUuV.removeFirst();
      }

      if ((Integer)this.ZlNrrNcv3Ylc.get() > 0 && this.UsQcxwgBUuV.size() >= (Integer)this.ZlNrrNcv3Ylc.get()) {
         return true;
      }

      this.UPNmQKi0eiVUue.addLast(VersionHelper.get().getPlayerPos());
      if (this.UPNmQKi0eiVUue.size() <= window) {
         return false;
      }

      Vec3d then = this.UPNmQKi0eiVUue.removeFirst();
      Vec3d now = this.UPNmQKi0eiVUue.peekLast();
      double dx = now.x - then.x;
      double dz = now.z - then.z;
      double min = (Double)this.zbN84Osn9p5lD.get();
      return dx * dx + dz * dz < min * min;
   }

   private void zXcO5rZ() {
      this.UPNmQKi0eiVUue.clear();
      this.UsQcxwgBUuV.clear();
      this.yYqDk4cMM0.set(0);
      this.X9ysJTxpWn2 = Integer.MIN_VALUE;
   }

   private float jbUe84V1vykfK() {
      ItemStack stack = this.mc.player.getEquippedStack(EquipmentSlot.CHEST);
      return (1.0F - (float)stack.getDamage() / stack.getMaxDamage()) * 100.0F;
   }

   private boolean zbN84Osn9p5lD() {
      if (!(Boolean)this.cO1FU7fW.get()) {
         return false;
      } else if (!this.mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem().equals(Items.ELYTRA)) {
         return false;
      } else {
         return ((AutoEat)Modules.get().get(AutoEat.class)).eating ? false : this.jbUe84V1vykfK() <= ((Integer)this.g0iiTDJzfIGXdui.get()).intValue();
      }
   }

   private void ZlNrrNcv3Ylc() {
      if (!((AutoEat)Modules.get().get(AutoEat.class)).eating) {
         if (this.jbUe84V1vykfK() >= ((Integer)this.VIbihOaIVyhvJw.get()).intValue()) {
            this.RFflNsLXot = false;
         } else {
            int xpSlot = musheor.utils.manager.pvfA15fQSVM.TJImwK2(Items.EXPERIENCE_BOTTLE);
            if (xpSlot == -1) {
               ChatUtils.info("%sNo experience bottles found, waiting ...", new Object[]{Formatting.GOLD});
            } else if (xpSlot > 8) {
               musheor.utils.manager.pvfA15fQSVM.fStlZIvY9lS8(Items.EXPERIENCE_BOTTLE);
            } else {
               irzXX.QfZy2gWnKInxFPr();
               this.mc.player.getInventory().selectedSlot = xpSlot;
               this.mc.player.setPitch(90.0F);
               this.mc.interactionManager.interactItem(this.mc.player, Hand.MAIN_HAND);
            }
         }
      }
   }

   private BlockPos fStlZIvY9lS8(int distance) {
      int px = this.mc.player.getBlockX();
      int py = (Integer)this.FVjbhYkk53UT.get();
      int pz = this.mc.player.getBlockZ();
      if (this.qhulAUwd.get() == KekBounce.Mode.Advanced) {
         return switch (this.vLRVDxlh) {
            case NORTH -> new BlockPos(px, py, pz - distance);
            case SOUTH -> new BlockPos(px, py, pz + distance);
            case EAST -> new BlockPos(px + distance, py, pz);
            case WEST -> new BlockPos(px - distance, py, pz);
            case NORTH_EAST -> new BlockPos(px + distance, py, pz - distance);
            case NORTH_WEST -> new BlockPos(px - distance, py, pz - distance);
            case SOUTH_EAST -> new BlockPos(px + distance, py, pz + distance);
            case SOUTH_WEST -> new BlockPos(px - distance, py, pz + distance);
         };
      } else {
         int sx = this.npfimrDr0hky.getX();
         int sz = this.npfimrDr0hky.getZ();

         return switch (this.vLRVDxlh) {
            case NORTH -> new BlockPos(sx, py, pz - distance);
            case SOUTH -> new BlockPos(sx, py, pz + distance);
            case EAST -> new BlockPos(px + distance, py, sz);
            case WEST -> new BlockPos(px - distance, py, sz);
            case NORTH_EAST -> {
               int gx = px + distance;
               yield new BlockPos(gx, py, sx + sz - gx);
            }
            case NORTH_WEST -> {
               int gx = px - distance;
               yield new BlockPos(gx, py, gx - (sx - sz));
            }
            case SOUTH_EAST -> {
               int gx = px + distance;
               yield new BlockPos(gx, py, gx - (sx - sz));
            }
            case SOUTH_WEST -> {
               int gx = px - distance;
               yield new BlockPos(gx, py, sx + sz - gx);
            }
         };
      }
   }

   private void fStlZIvY9lS8(HighwayRouter.Route route) {
      if (XearoHelper.isLoaded()) {
         List<XearoHelper.LineData> lines = new ArrayList<>();
         List<oyDPiOVBGKG.pvfA15fQSVM> wps = route.TJImwK2;

         for (int i = 0; i + 1 < wps.size(); i++) {
            lines.add(
               new XearoHelper.LineData(
                  (int)wps.get(i).fStlZIvY9lS8(), (int)wps.get(i).QfZy2gWnKInxFPr(), (int)wps.get(i + 1).fStlZIvY9lS8(), (int)wps.get(i + 1).QfZy2gWnKInxFPr()
               )
            );
         }

         XearoHelper.get().drawLinesOnMap(lines, -16733441);
      }
   }

   private void EVSMbf() {
      if (XearoHelper.isLoaded()) {
         List<XearoHelper.WaypointData> wps = XearoHelper.get().getWaypoints(true);
         if (wps != null) {
            if (wps.size() != this.Njcn1X5he) {
               this.Njcn1X5he = wps.size();
               List<XearoHelper.WaypointData> sorted = new ArrayList<>(wps);
               sorted.sort(Comparator.comparingLong(XearoHelper.WaypointData::createdAt));
               List<XearoHelper.LineData> allLines = new ArrayList<>();
               double fromX = this.mc.player.getX();
               double fromZ = this.mc.player.getZ();

               for (XearoHelper.WaypointData wp : sorted) {
                  HighwayRouter.Route route = HighwayRouter.fStlZIvY9lS8(fromX, fromZ, wp.x(), wp.z());
                  if (route != null) {
                     List<oyDPiOVBGKG.pvfA15fQSVM> pts = route.TJImwK2;

                     for (int i = 0; i + 1 < pts.size(); i++) {
                        allLines.add(
                           new XearoHelper.LineData(
                              (int)pts.get(i).fStlZIvY9lS8(),
                              (int)pts.get(i).QfZy2gWnKInxFPr(),
                              (int)pts.get(i + 1).fStlZIvY9lS8(),
                              (int)pts.get(i + 1).QfZy2gWnKInxFPr()
                           )
                        );
                     }
                  }

                  fromX = wp.x();
                  fromZ = wp.z();
               }

               XearoHelper.get().drawLinesOnMap(allLines, -16733441);
            }
         }
      }
   }

   private void pF16zz4NJECfg7() {
      if (this.PuZZ3s4mpaTz != null) {
         String etaStr = XearoHelper.get().getEtaSuffix(this.PuZZ3s4mpaTz);
         if (etaStr == null || etaStr.isBlank()) {
            etaStr = "...";
         }

         this.mc
            .player
            .sendMessage(
               Text.literal("[2bNav] ")
                  .setStyle(Style.EMPTY.withColor(Formatting.AQUA))
                  .append(Text.literal("ETA ").setStyle(Style.EMPTY.withColor(Formatting.GRAY)))
                  .append(Text.literal(etaStr).setStyle(Style.EMPTY.withColor(Formatting.WHITE).withBold(true))),
               true
            );
      }
   }

   public boolean fStlZIvY9lS8() {
      return this.isActive() && !this.xarqg2oUiwotDG4 && !this.RFflNsLXot && !GyhoRo.TJImwK2();
   }

   private enum Height {
      Auto,
      Manual;

      Height() {
      }
   }

   public enum Mode {
      Simple,
      Advanced;

      Mode() {
      }
   }

   private enum StartPos {
      OnActivate,
      Custom;

      StartPos() {
      }
   }

   public enum YawLock {
      Onactivate,
      Snap,
      Custom,
      Disable;

      YawLock() {
      }
   }
}
