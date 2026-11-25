package musheor.modules.automation;

import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;
import javax.net.ssl.HttpsURLConnection;
import meteordevelopment.meteorclient.events.entity.EntityAddedEvent;
import meteordevelopment.meteorclient.events.entity.EntityRemovedEvent;
import meteordevelopment.meteorclient.events.world.ChunkDataEvent;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.StorageBlockListSetting;
import meteordevelopment.meteorclient.settings.EnumSetting.Builder;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.orbit.EventHandler;
import musheor.compat.VersionHelper;
import musheor.compat.XearoHelper;
import musheor.modules.features.CoordHider;
import musheor.utils.system.MusheorSystem;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.chunk.WorldChunk;

public class Dispatcher extends Module {
   private final SettingGroup wSJeYsJcmjNZf = this.settings.getDefaultGroup();
   private final SettingGroup i2Nu6XHKic = this.settings.createGroup("Visual Range");
   private final SettingGroup vGAMA89H = this.settings.createGroup("Stashes");
   private final SettingGroup qhulAUwd = this.settings.createGroup("Ender Pearls");
   private final Setting<Dispatcher.NotificationType> BrmKXSYhWhqoB1 = this.wSJeYsJcmjNZf
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("notification-type")).description("How notifications are being handled"))
               .defaultValue(Dispatcher.NotificationType.Both))
            .build()
      );
   private final Setting<Integer> TXWyNb = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("minimum-distance"))
                  .description("Minimum distance away from spawn before alerts are sent"))
               .defaultValue(1000))
            .sliderRange(0, 10000)
            .build()
      );
   private final Setting<Boolean> I3psabmOrOI = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("webhook"))
                     .description("Sends notifications over a discord webhook"))
                  .defaultValue(true))
               .visible(
                  () -> this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Both
                     || this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Webhook
               ))
            .build()
      );
   public final Setting<String> fStlZIvY9lS8 = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.StringSetting.Builder)((meteordevelopment.meteorclient.settings.StringSetting.Builder)((meteordevelopment.meteorclient.settings.StringSetting.Builder)new meteordevelopment.meteorclient.settings.StringSetting.Builder()
                     .name("webhook-link"))
                  .defaultValue(""))
               .visible(
                  () -> (
                        this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Both
                           || this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Webhook
                     )
                     && (Boolean)this.I3psabmOrOI.get()
               ))
            .build()
      );
   public final Setting<Integer> QfZy2gWnKInxFPr = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("webhook-timeout-MS"))
                     .description("Timeout for webhook requests in ms, set to 0 to disable, increase when getting rate-limited"))
                  .defaultValue(350))
               .sliderRange(0, 5000)
               .visible(
                  () -> (
                        this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Both
                           || this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Webhook
                     )
                     && (Boolean)this.I3psabmOrOI.get()
               ))
            .build()
      );
   private final Setting<Boolean> duJ6sPeetAcBh6P = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("webhook-coords"))
                     .description("Includes coordinates in webhook message"))
                  .defaultValue(true))
               .visible(
                  () -> (
                        this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Both
                           || this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Webhook
                     )
                     && (Boolean)this.I3psabmOrOI.get()
               ))
            .build()
      );
   private final Setting<Boolean> j376sIQ = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("allow-ping"))
                     .description("Pings a role when a notification is sent"))
                  .defaultValue(false))
               .visible(
                  () -> (
                        this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Both
                           || this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Webhook
                     )
                     && (Boolean)this.I3psabmOrOI.get()
               ))
            .build()
      );
   public final Setting<String> TJImwK2 = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.StringSetting.Builder)((meteordevelopment.meteorclient.settings.StringSetting.Builder)((meteordevelopment.meteorclient.settings.StringSetting.Builder)new meteordevelopment.meteorclient.settings.StringSetting.Builder()
                     .name("role-ID"))
                  .defaultValue(""))
               .visible(
                  () -> (
                        this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Both
                           || this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Webhook
                     )
                     && (Boolean)this.I3psabmOrOI.get()
                     && (Boolean)this.j376sIQ.get()
               ))
            .build()
      );
   private final Setting<Boolean> V7zKJ4n6D = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                           .name("create-waypoints"))
                        .description("Creates xaeros minimap waypoints"))
                     .defaultValue(false))
                  .onChanged(value -> {
                     if (XearoHelper.isLoaded()) {
                        XearoHelper.get().updateWaypointSettings();
                     }
                  }))
               .visible(XearoHelper::isLoaded))
            .build()
      );
   private final Setting<Boolean> voxTwgNzyrpaAnP = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("record-signs"))
                     .description("Alerts and records signs and their text"))
                  .defaultValue(true))
               .visible(() -> false))
            .build()
      );
   private final Setting<Boolean> xileXsbo = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("play-sound"))
                     .description("Plays a sound when dispatch event is triggered"))
                  .defaultValue(true))
               .visible(
                  () -> this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Both
                     || this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Chat
               ))
            .build()
      );
   private final Setting<Boolean> nOwp0NYl = this.i2Nu6XHKic
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("visual-range-alerts"))
                  .description("Sends an alert when a player enters or leaves visual range"))
               .defaultValue(true))
            .build()
      );
   private final Setting<Dispatcher.VisualRangeType> umvNbb7iD9UpR = this.i2Nu6XHKic
      .add(
         ((Builder)((Builder)((Builder)((Builder)new Builder().name("visual-range-type"))
                     .description("Choose what type of visual range alerts you want to receive"))
                  .defaultValue(Dispatcher.VisualRangeType.Enter))
               .visible(this.nOwp0NYl::get))
            .build()
      );
   private final Setting<Boolean> zkcZ4Xkp9Ez7Y = this.i2Nu6XHKic
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("webhook-visual-range"))
                     .description("Sends visual range alerts over discord webhook"))
                  .defaultValue(true))
               .visible(
                  () -> (
                        this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Both
                           || this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Webhook
                     )
                     && (Boolean)this.I3psabmOrOI.get()
                     && (Boolean)this.nOwp0NYl.get()
               ))
            .build()
      );
   private final Setting<Boolean> flWHzEyn = this.i2Nu6XHKic
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("waypoint-visual-range"))
                     .description("Creates a xaero waypoint when a player enters or leaves visual range"))
                  .defaultValue(true))
               .visible(() -> (Boolean)this.nOwp0NYl.get() && (Boolean)this.V7zKJ4n6D.get()))
            .build()
      );
   private final Setting<Boolean> FVjbhYkk53UT = this.i2Nu6XHKic
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("ping-visual-range"))
                     .description("Pings a discord role when a player enters or leaves visual range"))
                  .defaultValue(false))
               .visible(
                  () -> (
                        this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Both
                           || this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Webhook
                     )
                     && (Boolean)this.I3psabmOrOI.get()
                     && (Boolean)this.nOwp0NYl.get()
                     && (Boolean)this.j376sIQ.get()
               ))
            .build()
      );
   private final Setting<Boolean> Nt3xeF = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("stash-alerts"))
                  .description("Sends an alert when a stash or unnatural blocks are found"))
               .defaultValue(true))
            .build()
      );
   private final Setting<List<BlockEntityType<?>>> zXcO5rZ = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.StorageBlockListSetting.Builder)((meteordevelopment.meteorclient.settings.StorageBlockListSetting.Builder)((meteordevelopment.meteorclient.settings.StorageBlockListSetting.Builder)new meteordevelopment.meteorclient.settings.StorageBlockListSetting.Builder()
                     .name("container-list"))
                  .description("List of container blocks to track or search for"))
               .defaultValue(StorageBlockListSetting.STORAGE_BLOCKS)
               .visible(this.Nt3xeF::get))
            .build()
      );
   private final Setting<Integer> jbUe84V1vykfK = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("container-threshold"))
                     .description("How many containers need to be in visual range before an alert is sent"))
                  .defaultValue(8))
               .sliderRange(1, 64)
               .visible(this.Nt3xeF::get))
            .build()
      );
   private final Setting<List<BlockEntityType<?>>> zbN84Osn9p5lD = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.StorageBlockListSetting.Builder)((meteordevelopment.meteorclient.settings.StorageBlockListSetting.Builder)((meteordevelopment.meteorclient.settings.StorageBlockListSetting.Builder)new meteordevelopment.meteorclient.settings.StorageBlockListSetting.Builder()
                     .name("instant-hit-list"))
                  .description("List of container blocks to track or search for, when a single one is found it will alert"))
               .defaultValue(new BlockEntityType[]{BlockEntityType.SHULKER_BOX})
               .visible(this.Nt3xeF::get))
            .build()
      );
   private final Setting<Boolean> ZlNrrNcv3Ylc = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("webhook-stash-alerts"))
                     .description("Sends visual range alerts over discord webhook"))
                  .defaultValue(true))
               .visible(
                  () -> (
                        this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Both
                           || this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Webhook
                     )
                     && (Boolean)this.I3psabmOrOI.get()
                     && (Boolean)this.Nt3xeF.get()
               ))
            .build()
      );
   private final Setting<Boolean> EVSMbf = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("waypoint-stash"))
                     .description("Creates a xaero waypoint when a stash is found"))
                  .defaultValue(true))
               .visible(() -> (Boolean)this.Nt3xeF.get() && (Boolean)this.V7zKJ4n6D.get()))
            .build()
      );
   private final Setting<Boolean> pF16zz4NJECfg7 = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("ping-stash-alerts"))
                     .description("Pings a discord role when a player enters or leaves visual range"))
                  .defaultValue(false))
               .visible(
                  () -> (
                        this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Both
                           || this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Webhook
                     )
                     && (Boolean)this.I3psabmOrOI.get()
                     && (Boolean)this.Nt3xeF.get()
                     && (Boolean)this.j376sIQ.get()
               ))
            .build()
      );
   private final Setting<Boolean> WGmfQKK = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("illegal-bedrock"))
                  .description("Sends an alert when illegal bedrock is found"))
               .defaultValue(false))
            .build()
      );
   private final Setting<Boolean> icQ738dpWr7W = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("waypoint-illegal-bedrock"))
                     .description("Creates a xaero waypoint when illegal bedrock is found"))
                  .defaultValue(true))
               .visible(() -> (Boolean)this.WGmfQKK.get() && (Boolean)this.V7zKJ4n6D.get()))
            .build()
      );
   private final Setting<Boolean> JAj4YwE2MVx = this.qhulAUwd
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("pearl-alerts"))
                  .description("Sends an alert when an enderpearl or stasis-chamber is found"))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> JPp6VyZP9f = this.qhulAUwd
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("webhook-pearl-alerts"))
                     .description("Sends a pearl alerts over discord webhook"))
                  .defaultValue(true))
               .visible(
                  () -> (
                        this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Both
                           || this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Webhook
                     )
                     && (Boolean)this.I3psabmOrOI.get()
                     && (Boolean)this.JAj4YwE2MVx.get()
               ))
            .build()
      );
   private final Setting<Boolean> MR1DKzw = this.qhulAUwd
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("waypoint-pearl"))
                     .description("Creates a xaero waypoint when an enderpearl is found"))
                  .defaultValue(true))
               .visible(() -> (Boolean)this.JAj4YwE2MVx.get() && (Boolean)this.V7zKJ4n6D.get()))
            .build()
      );
   private final Setting<Boolean> DqGSCWplcjMi = this.qhulAUwd
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("ping-pearl-alerts"))
                     .description("Pings a discord role when a pearl or stasis chamber is found"))
                  .defaultValue(false))
               .visible(
                  () -> (
                        this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Both
                           || this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Webhook
                     )
                     && (Boolean)this.I3psabmOrOI.get()
                     && (Boolean)this.JAj4YwE2MVx.get()
                     && (Boolean)this.j376sIQ.get()
               ))
            .build()
      );
   private final Map<Integer, Vec3d> cO1FU7fW = new HashMap<>();
   private final Map<Long, List<Vec3d>> g0iiTDJzfIGXdui = new HashMap<>();
   private final Set<Long> VIbihOaIVyhvJw = new HashSet<>();
   private long RE8q1TMDRWx = 0L;
   private Object vLRVDxlh = null;
   private final LinkedBlockingQueue<String> HomZ7BbhM7wB = new LinkedBlockingQueue<>();
   private Thread lVX1t0MC;

   public Dispatcher() {
      super(musheor.musheor.AUTOMATION, "dispatcher", "Records specific data and notifies the player in chat or through a discord webhook");
   }

   public void onActivate() {
      if (this.mc.player != null && this.mc.world != null) {
         if (XearoHelper.isLoaded()) {
            this.vLRVDxlh = XearoHelper.get().getCurrentWaypointSetHandle();
            if (this.vLRVDxlh != null) {
               XearoHelper.get().setWaypointSet("Dispatcher");
            }
         }

         this.lVX1t0MC = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
               try {
                  String json = this.HomZ7BbhM7wB.take();
                  fStlZIvY9lS8((String)this.fStlZIvY9lS8.get(), json);
                  int timeout = (Integer)this.QfZy2gWnKInxFPr.get();
                  if (timeout > 0) {
                     Thread.sleep(timeout);
                  }
               } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
               }
            }
         });
         this.lVX1t0MC.setDaemon(true);
         this.lVX1t0MC.start();
      }
   }

   public void onDeactivate() {
      this.VIbihOaIVyhvJw.clear();
      this.g0iiTDJzfIGXdui.clear();
      this.cO1FU7fW.clear();
      this.HomZ7BbhM7wB.clear();
      if (this.lVX1t0MC != null) {
         this.lVX1t0MC.interrupt();
         this.lVX1t0MC = null;
      }

      if (XearoHelper.isLoaded()) {
         XearoHelper.get().restoreWaypointSet(this.vLRVDxlh);
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(ChunkDataEvent event) {
      if (this.mc.player != null && this.mc.world != null) {
         if ((Boolean)this.Nt3xeF.get()) {
            Vec3d playerPos = VersionHelper.get().getPlayerPos();
            if (!(Math.abs(playerPos.x) < ((Integer)this.TXWyNb.get()).intValue()) && !(Math.abs(playerPos.z) < ((Integer)this.TXWyNb.get()).intValue())) {
               WorldChunk chunk = event.chunk();
               long chunkKey = ChunkPos.toLong(chunk.getPos().x, chunk.getPos().z);
               if (this.VIbihOaIVyhvJw.add(chunkKey)) {
                  this.fStlZIvY9lS8(chunk.getPos());
               }
            }
         }
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(EntityAddedEvent event) {
      if (this.mc.player != null && this.mc.world != null) {
         Vec3d playerPos = VersionHelper.get().getPlayerPos();
         if (!(Math.abs(playerPos.x) < ((Integer)this.TXWyNb.get()).intValue()) && !(Math.abs(playerPos.z) < ((Integer)this.TXWyNb.get()).intValue())) {
            if ((Boolean)this.nOwp0NYl.get()
               && this.umvNbb7iD9UpR.get() != Dispatcher.VisualRangeType.Leave
               && event.entity.getUuid() != this.mc.player.getUuid()
               && event.entity instanceof PlayerEntity player) {
               if (this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Both || this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Chat) {
                  if (((CoordHider)Modules.get().get(CoordHider.class)).isActive()) {
                     ChatUtils.sendMsg(
                        player.getId() + 100, Formatting.GRAY, "(highlight)%s(default) has entered visual range!", new Object[]{player.getName().getString()}
                     );
                  } else {
                     ChatUtils.sendMsg(
                        player.getId() + 100,
                        Formatting.GRAY,
                        "(highlight)%s(default) has entered visual range at %d, %d, %d!",
                        new Object[]{player.getName().getString(), player.getBlockX(), player.getBlockY(), player.getBlockZ()}
                     );
                  }
               }

               if ((Boolean)this.I3psabmOrOI.get() && (Boolean)this.zkcZ4Xkp9Ez7Y.get() && !((String)this.fStlZIvY9lS8.get()).isEmpty()) {
                  String pos = this.duJ6sPeetAcBh6P.get() ? String.format("||%d, %d, %d||", player.getBlockX(), player.getBlockY(), player.getBlockZ()) : "";
                  this.fStlZIvY9lS8(
                     "Visual Range",
                     player.getName().getString() + " has entered visual range!\n" + pos,
                     16733525,
                     (Boolean)this.FVjbhYkk53UT.get() && (Boolean)this.j376sIQ.get()
                  );
               }

               if ((Boolean)this.V7zKJ4n6D.get() && (Boolean)this.flWHzEyn.get() && XearoHelper.isLoaded()) {
                  XearoHelper.get()
                     .addWaypointToCurrent(
                        "VisualRange E: " + player.getName().getString(), "P", VersionHelper.get().getPlayerPos(), XearoHelper.WaypointColorHint.WHITE
                     );
               }

               if ((Boolean)this.xileXsbo.get()) {
                  this.mc
                     .world
                     .playSoundFromEntity(this.mc.player, this.mc.player, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT, 3.0F, 1.0F);
               }
            }

            if ((Boolean)this.JAj4YwE2MVx.get() && event.entity instanceof EnderPearlEntity pearlEntity && !this.cO1FU7fW.containsKey(pearlEntity.getId())) {
               Vec3d pos = new Vec3d(pearlEntity.getX(), pearlEntity.getY(), pearlEntity.getZ());
               this.cO1FU7fW.put(pearlEntity.getId(), pos);
               long chunkKey = ((int)pearlEntity.getX() >> 4) * 1000000L + ((int)pearlEntity.getZ() >> 4);
               this.g0iiTDJzfIGXdui.computeIfAbsent(chunkKey, k -> new ArrayList<>()).add(pos);
               this.RE8q1TMDRWx = System.currentTimeMillis() + 2000L;
            }
         }
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(EntityRemovedEvent event) {
      if (this.mc.player != null && this.mc.world != null) {
         Vec3d playerPos = VersionHelper.get().getPlayerPos();
         if (!(Math.abs(playerPos.x) < ((Integer)this.TXWyNb.get()).intValue()) && !(Math.abs(playerPos.z) < ((Integer)this.TXWyNb.get()).intValue())) {
            if ((Boolean)this.nOwp0NYl.get()
               && this.umvNbb7iD9UpR.get() != Dispatcher.VisualRangeType.Enter
               && event.entity.getUuid() != this.mc.player.getUuid()
               && event.entity instanceof PlayerEntity player) {
               if (this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Both || this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Chat) {
                  if (((CoordHider)Modules.get().get(CoordHider.class)).isActive()) {
                     ChatUtils.sendMsg(
                        player.getId() + 100, Formatting.GRAY, "(highlight)%s(default) has left visual range!", new Object[]{player.getName().getString()}
                     );
                  } else {
                     ChatUtils.sendMsg(
                        player.getId() + 100,
                        Formatting.GRAY,
                        "(highlight)%s(default) has left visual range at %d, %d, %d!",
                        new Object[]{player.getName().getString(), player.getBlockX(), player.getBlockY(), player.getBlockZ()}
                     );
                  }
               }

               if ((Boolean)this.I3psabmOrOI.get() && (Boolean)this.zkcZ4Xkp9Ez7Y.get() && !((String)this.fStlZIvY9lS8.get()).isEmpty()) {
                  String pos = this.duJ6sPeetAcBh6P.get() ? String.format("||%d, %d, %d||", player.getBlockX(), player.getBlockY(), player.getBlockZ()) : "";
                  this.fStlZIvY9lS8(
                     "Visual Range",
                     player.getName().getString() + " has left visual range!\n" + pos,
                     8530731,
                     (Boolean)this.FVjbhYkk53UT.get() && (Boolean)this.j376sIQ.get()
                  );
               }

               if ((Boolean)this.V7zKJ4n6D.get() && (Boolean)this.flWHzEyn.get() && XearoHelper.isLoaded()) {
                  XearoHelper.get()
                     .addWaypointToCurrent(
                        "VisualRange L: " + player.getName().getString(), "P", VersionHelper.get().getPlayerPos(), XearoHelper.WaypointColorHint.WHITE
                     );
               }

               if ((Boolean)this.xileXsbo.get()) {
                  this.mc
                     .world
                     .playSoundFromEntity(this.mc.player, this.mc.player, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT, 3.0F, 1.0F);
               }
            }
         }
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(Pre event) {
      if (!this.g0iiTDJzfIGXdui.isEmpty() && System.currentTimeMillis() >= this.RE8q1TMDRWx) {
         for (Entry<Long, List<Vec3d>> entry : this.g0iiTDJzfIGXdui.entrySet()) {
            List<Vec3d> pearls = entry.getValue();
            Vec3d first = pearls.getFirst();
            int count = pearls.size();
            if (count == 1) {
               if (this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Both || this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Chat) {
                  if (((CoordHider)Modules.get().get(CoordHider.class)).isActive()) {
                     ChatUtils.sendMsg(0, Formatting.BLUE, "Found an Enderpearl!", new Object[0]);
                  } else {
                     ChatUtils.sendMsg(
                        0,
                        Formatting.BLUE,
                        "Enderpearl %sfound at [%s%d, %d, %d%s]",
                        new Object[]{Formatting.WHITE, Formatting.GRAY, (int)first.x, (int)first.y, (int)first.z, Formatting.WHITE}
                     );
                  }
               }
            } else if (((CoordHider)Modules.get().get(CoordHider.class)).isActive()) {
               ChatUtils.sendMsg(0, Formatting.BLUE, "Found %d Enderpearls!", new Object[]{count});
            } else {
               ChatUtils.sendMsg(
                  0,
                  Formatting.BLUE,
                  "%d Enderpearls %sfound at [%s%d, %d, %d%s]",
                  new Object[]{count, Formatting.WHITE, Formatting.GRAY, (int)first.x, (int)first.y, (int)first.z, Formatting.WHITE}
               );
            }

            if ((Boolean)this.I3psabmOrOI.get() && (Boolean)this.JPp6VyZP9f.get() && !((String)this.fStlZIvY9lS8.get()).isEmpty()) {
               String pos = this.duJ6sPeetAcBh6P.get() ? String.format("||%d, %d, %d||", (int)first.x, (int)first.y, (int)first.z) : "";
               this.fStlZIvY9lS8(
                  "Pearl Detected", "Found " + count + " enderpearls!\n" + pos, 5592575, (Boolean)this.DqGSCWplcjMi.get() && (Boolean)this.j376sIQ.get()
               );
            }

            if ((Boolean)this.V7zKJ4n6D.get() && (Boolean)this.MR1DKzw.get() && XearoHelper.isLoaded()) {
               XearoHelper.get().addWaypointToCurrent("Pearls: " + count, "P", first, XearoHelper.WaypointColorHint.BLUE);
            }
         }

         this.g0iiTDJzfIGXdui.clear();
      }
   }

   private void fStlZIvY9lS8(ChunkPos chunkPos) {
      if (this.mc.world != null && this.mc.player != null) {
         Vec3d playerPos = VersionHelper.get().getPlayerPos();
         if (!(Math.abs(playerPos.x) < ((Integer)this.TXWyNb.get()).intValue()) && !(Math.abs(playerPos.z) < ((Integer)this.TXWyNb.get()).intValue())) {
            WorldChunk chunk = this.mc.world.getChunk(chunkPos.x, chunkPos.z);
            MusheorSystem.debug("[DEBUG] Scanning chunk " + chunkPos.x + ", " + chunkPos.z + " - block entities: " + chunk.getBlockEntities().size());
            Map<BlockEntityType<?>, Integer> foundContainers = new HashMap<>();
            boolean foundSingleAlert = false;
            BlockPos firstContainerPos = null;

            for (Entry<BlockPos, BlockEntity> entry : chunk.getBlockEntities().entrySet()) {
               BlockEntity be = entry.getValue();
               BlockPos pos = entry.getKey();
               boolean isThresholdContainer = ((List)this.zXcO5rZ.get()).contains(be.getType());
               boolean isSingleContainer = ((List)this.zbN84Osn9p5lD.get()).contains(be.getType());
               if (isThresholdContainer || isSingleContainer) {
                  foundContainers.merge(be.getType(), 1, Integer::sum);
                  if (firstContainerPos == null) {
                     firstContainerPos = pos;
                  }
               }

               if (isSingleContainer) {
                  foundSingleAlert = true;
               }
            }

            int totalThresholdContainers = ((List)this.zXcO5rZ.get()).isEmpty()
               ? 0
               : foundContainers.entrySet().stream().filter(e -> ((List)this.zXcO5rZ.get()).contains(e.getKey())).mapToInt(Entry::getValue).sum();
            boolean thresholdMet = !((List)this.zXcO5rZ.get()).isEmpty() && totalThresholdContainers >= (Integer)this.jbUe84V1vykfK.get();
            if ((thresholdMet || foundSingleAlert) && firstContainerPos != null) {
               StringBuilder summary = new StringBuilder();

               for (Entry<BlockEntityType<?>, Integer> entry : foundContainers.entrySet()) {
                  String name = Registries.BLOCK_ENTITY_TYPE.getId(entry.getKey()).toString().replace("minecraft:", "");
                  summary.append("**[").append(entry.getValue()).append("x]** ").append(name).append("\n");
               }

               int totalContainers = foundContainers.values().stream().mapToInt(Integer::intValue).sum();
               String blockCoords = String.format("|%d, %d, %d|", firstContainerPos.getX(), firstContainerPos.getY(), firstContainerPos.getZ());
               String description = summary.toString().trim() + "\n||" + blockCoords + "||";
               if (this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Both || this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Chat) {
                  if (((CoordHider)Modules.get().get(CoordHider.class)).isActive()) {
                     ChatUtils.sendMsg(
                        0, Formatting.WHITE, "Stash found! %s- %s%d containers", new Object[]{Formatting.WHITE, Formatting.AQUA, totalContainers}
                     );
                  } else {
                     ChatUtils.sendMsg(
                        0,
                        Formatting.WHITE,
                        "Stash found at %s%d, %d, %d %s- %s%d containers",
                        new Object[]{
                           Formatting.GRAY,
                           firstContainerPos.getX(),
                           firstContainerPos.getY(),
                           firstContainerPos.getZ(),
                           Formatting.WHITE,
                           Formatting.AQUA,
                           totalContainers
                        }
                     );
                  }
               }

               if ((Boolean)this.I3psabmOrOI.get() && (Boolean)this.ZlNrrNcv3Ylc.get() && !((String)this.fStlZIvY9lS8.get()).isEmpty()) {
                  this.fStlZIvY9lS8("GOLD GOLD GOLD", description, 16759603, (Boolean)this.pF16zz4NJECfg7.get() && (Boolean)this.j376sIQ.get());
               }

               if ((Boolean)this.V7zKJ4n6D.get() && (Boolean)this.EVSMbf.get() && XearoHelper.isLoaded()) {
                  XearoHelper.get().addWaypointToCurrent("Stash: " + totalContainers, "S", Vec3d.of(firstContainerPos), XearoHelper.WaypointColorHint.GOLD);
               }
            }

            if ((Boolean)this.WGmfQKK.get()) {
               boolean isOverworld = this.mc.world.getRegistryKey() == World.OVERWORLD;
               boolean isNether = this.mc.world.getRegistryKey() == World.NETHER;
               boolean isEnd = this.mc.world.getRegistryKey() == World.END;
               if (isEnd) {
                  for (BlockEntity be : chunk.getBlockEntities().values()) {
                     if (be instanceof EndGatewayBlockEntity) {
                        return;
                     }
                  }
               }

               BlockPos illegalPos = null;

               for (int x = chunkPos.getStartX(); x <= chunkPos.getEndX(); x++) {
                  for (int z = chunkPos.getStartZ(); z <= chunkPos.getEndZ(); z++) {
                     int minY = this.mc.world.getBottomY();
                     int maxY = this.mc.world.getTopY(Type.MOTION_BLOCKING, x, z);

                     for (int y = minY; y < maxY; y++) {
                        BlockPos pos = new BlockPos(x, y, z);
                        if (this.mc.world.getBlockState(pos).getBlock() == Blocks.BEDROCK) {
                           boolean illegal = false;
                           if (isOverworld && y > 5) {
                              illegal = true;
                           } else if (isNether && y > 5 && y < 122) {
                              illegal = true;
                           } else if (isEnd) {
                              illegal = true;
                           }

                           if (illegal) {
                              illegalPos = pos;
                              break;
                           }
                        }
                     }

                     if (illegalPos != null) {
                        break;
                     }
                  }

                  if (illegalPos != null) {
                     break;
                  }
               }

               if (illegalPos != null) {
                  String pos = this.duJ6sPeetAcBh6P.get() ? String.format("|%d, %d, %d|", illegalPos.getX(), illegalPos.getY(), illegalPos.getZ()) : "";
                  if (this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Both || this.BrmKXSYhWhqoB1.get() == Dispatcher.NotificationType.Chat
                     )
                   {
                     if (((CoordHider)Modules.get().get(CoordHider.class)).isActive()) {
                        ChatUtils.sendMsg(0, Formatting.RED, "Illegal bedrock found!", new Object[0]);
                     } else {
                        ChatUtils.sendMsg(
                           0,
                           Formatting.RED,
                           "Illegal bedrock found at %s%d, %d, %d",
                           new Object[]{Formatting.GRAY, illegalPos.getX(), illegalPos.getY(), illegalPos.getZ()}
                        );
                     }
                  }

                  if ((Boolean)this.I3psabmOrOI.get() && (Boolean)this.ZlNrrNcv3Ylc.get() && !((String)this.fStlZIvY9lS8.get()).isEmpty()) {
                     this.fStlZIvY9lS8(
                        "Illegal Bedrock", "**Found illegal bedrock**!!\n" + pos, 16711935, (Boolean)this.pF16zz4NJECfg7.get() && (Boolean)this.j376sIQ.get()
                     );
                  }

                  if ((Boolean)this.V7zKJ4n6D.get() && (Boolean)this.icQ738dpWr7W.get() && XearoHelper.isLoaded()) {
                     XearoHelper.get().addWaypointToCurrent("Illegal Bedrock", "B", Vec3d.of(illegalPos), XearoHelper.WaypointColorHint.RED);
                  }
               }
            }
         }
      }
   }

   private void fStlZIvY9lS8(String title, String description, int color, boolean pingRole) {
      String content = pingRole && this.j376sIQ.get() && !((String)this.TJImwK2.get()).isEmpty()
         ? "\"content\": \"<@&" + (String)this.TJImwK2.get() + ">\","
         : "";
      String json = "{"
         + content
         + "\"embeds\": [{\"title\": \""
         + title.replace("\"", "\\\"")
         + "\",\"description\": \""
         + description.replace("\"", "\\\"").replace("\n", "\\n")
         + "\",\"color\": "
         + color
         + "}]}";
      this.HomZ7BbhM7wB.offer(json);
   }

   private static void fStlZIvY9lS8(String webhookURL, String json) {
      try {
         URL url = new URL(webhookURL);
         HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
         connection.addRequestProperty("Content-Type", "application/json");
         connection.addRequestProperty("User-Agent", "Mozilla");
         connection.setDoOutput(true);
         connection.setRequestMethod("POST");
         OutputStream stream = connection.getOutputStream();
         stream.write(json.getBytes());
         stream.flush();
         stream.close();
         connection.getInputStream().close();
         connection.disconnect();
      } catch (MalformedURLException | UnknownServiceException var5) {
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private enum NotificationType {
      Chat,
      Webhook,
      Both;

      NotificationType() {
      }
   }

   private enum VisualRangeType {
      Enter,
      Leave,
      Both;

      VisualRangeType() {
      }
   }
}
