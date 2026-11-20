package musheor.modules.tech;

import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalNear;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import meteordevelopment.meteorclient.events.game.ReceiveMessageEvent;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.settings.EnumSetting.Builder;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.player.Rotations;
import meteordevelopment.orbit.EventHandler;
import musheor.compat.VersionHelper;
import musheor.modules.features.MoreTags;
import musheor.musheor;
import musheor.utils.GyhoRo;
import musheor.utils.manager.PathKit;
import musheor.utils.oyDPiOVBGKG;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class MessageInteract extends Module {
   private static final MinecraftClient TJImwK2 = MinecraftClient.getInstance();
   private final SettingGroup wSJeYsJcmjNZf = this.settings.getDefaultGroup();
   private final Setting<MessageInteract.Action> i2Nu6XHKic = this.wSJeYsJcmjNZf
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("action")).description("What kind of action to perform."))
               .defaultValue(MessageInteract.Action.TJImwK2))
            .build()
      );
   private final Setting<BlockPos> vGAMA89H = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.BlockPosSetting.Builder)((meteordevelopment.meteorclient.settings.BlockPosSetting.Builder)((meteordevelopment.meteorclient.settings.BlockPosSetting.Builder)((meteordevelopment.meteorclient.settings.BlockPosSetting.Builder)new meteordevelopment.meteorclient.settings.BlockPosSetting.Builder()
                        .name("position"))
                     .description("What block position the action is supposed to be for"))
                  .defaultValue(new BlockPos(0, 0, 0)))
               .visible(() -> this.i2Nu6XHKic.get() != MessageInteract.Action.TJImwK2))
            .build()
      );
   private final Setting<Boolean> qhulAUwd = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("drop-pearl"))
                     .description("Drops a pearl after performing the interaction"))
                  .defaultValue(true))
               .visible(() -> this.i2Nu6XHKic.get() == MessageInteract.Action.TJImwK2))
            .build()
      );
   private final Setting<Boolean> BrmKXSYhWhqoB1 = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("return-to-start-pos"))
                     .description("Pathfinds back to your starting position."))
                  .defaultValue(true))
               .visible(() -> this.i2Nu6XHKic.get() == MessageInteract.Action.TJImwK2))
            .build()
      );
   private final Setting<String> TXWyNb = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.StringSetting.Builder)((meteordevelopment.meteorclient.settings.StringSetting.Builder)((meteordevelopment.meteorclient.settings.StringSetting.Builder)new meteordevelopment.meteorclient.settings.StringSetting.Builder()
                     .name("allowed-player"))
                  .description("If set, only reacts to !tp whispers from this player. Leave empty to allow anyone."))
               .defaultValue(""))
            .build()
      );
   boolean fStlZIvY9lS8 = false;
   int QfZy2gWnKInxFPr = 0;
   private int I3psabmOrOI = 0;
   private BlockPos duJ6sPeetAcBh6P = null;
   private BlockPos j376sIQ = null;
   private static final Pattern V7zKJ4n6D = Pattern.compile("([A-Za-z0-9_]{3,16}) whispers: ");
   private static final Random voxTwgNzyrpaAnP = new Random();

   public MessageInteract() {
      super(musheor.MAIN, "message-interact", "Performs a certain action upon receiving a message from a specific player.");
   }

   public void onDeactivate() {
      obf.GyhoRo.wSJeYsJcmjNZf();
      this.fStlZIvY9lS8 = false;
      this.QfZy2gWnKInxFPr = 0;
      this.I3psabmOrOI = 0;
      this.duJ6sPeetAcBh6P = null;
      this.j376sIQ = null;
   }

   public void onActivate() {
      if (this.i2Nu6XHKic.get() == MessageInteract.Action.TJImwK2 && !((MoreTags)Modules.get().get(MoreTags.class)).isActive()) {
         ((MoreTags)Modules.get().get(MoreTags.class)).toggle();
      }
   }

   private BlockPos fStlZIvY9lS8(String ownerName) {
      if (TJImwK2.world != null && TJImwK2.player != null) {
         BlockPos closest = null;
         double closestDist = Double.MAX_VALUE;

         for (Entry<Integer, String> entry : MoreTags.QfZy2gWnKInxFPr.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(ownerName) && TJImwK2.world.getEntityById(entry.getKey()) instanceof EnderPearlEntity pearl) {
               for (BlockPos candidate : new BlockPos[]{pearl.getBlockPos(), pearl.getBlockPos().down()}) {
                  if (TJImwK2.world.getBlockState(candidate).getBlock() instanceof TrapdoorBlock) {
                     double dist = VersionHelper.get().getPlayerPos().squaredDistanceTo(Vec3d.ofCenter(candidate));
                     if (dist < closestDist) {
                        closestDist = dist;
                        closest = candidate;
                     }
                  }
               }
            }
         }

         if (closest != null) {
            return closest;
         }
      }

      List<GyhoRo.gVOhtBej1x> saved = GyhoRo.QfZy2gWnKInxFPr(ownerName);
      if (saved.isEmpty()) {
         return null;
      } else {
         return TJImwK2.player != null && saved.size() != 1
            ? saved.stream()
               .min(Comparator.comparingDouble(r -> VersionHelper.get().getPlayerPos().squaredDistanceTo(Vec3d.ofCenter(r.QfZy2gWnKInxFPr))))
               .map(r -> r.QfZy2gWnKInxFPr)
               .orElse(null)
            : saved.getFirst().QfZy2gWnKInxFPr;
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(ReceiveMessageEvent event) {
      String msg = event.getMessage().getString();
      if (msg.contains("!tp")) {
         Matcher matcher = V7zKJ4n6D.matcher(msg);
         if (matcher.find()) {
            String senderName = matcher.group(1);
            this.info("Received message from: " + senderName, new Object[0]);
            if (((String)this.TXWyNb.get()).isEmpty() || senderName.equalsIgnoreCase((String)this.TXWyNb.get())) {
               if (this.i2Nu6XHKic.get() == MessageInteract.Action.TJImwK2) {
                  this.duJ6sPeetAcBh6P = this.fStlZIvY9lS8(senderName);
                  if (this.duJ6sPeetAcBh6P == null) {
                     this.info("!tp from " + senderName + " but no tracked pearl with trapdoor found.", new Object[0]);
                     this.fStlZIvY9lS8(senderName, "Could not find a tracked pearl or trapdoor for you.", fStlZIvY9lS8(8));
                     return;
                  }

                  if (TJImwK2.player.squaredDistanceTo(Vec3d.ofCenter(this.duJ6sPeetAcBh6P)) > 256.0) {
                     this.info("!tp from " + senderName + " but pearl is out of render distance.", new Object[0]);
                     this.fStlZIvY9lS8(senderName, "Pearl is out of render distance.", fStlZIvY9lS8(8));
                     return;
                  }

                  this.info("Pearl found at " + this.duJ6sPeetAcBh6P + " — pathfinding to load for " + senderName, new Object[0]);
                  this.fStlZIvY9lS8(senderName, "Pearl found, loading...", fStlZIvY9lS8(8));
                  this.j376sIQ = BlockPos.ofFloored(VersionHelper.get().getPlayerPos());
               }

               this.I3psabmOrOI = 0;
               this.fStlZIvY9lS8 = true;
            }
         }
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(Pre event) {
      if (TJImwK2.player != null && TJImwK2.world != null && this.fStlZIvY9lS8) {
         BlockPos resolvedPos = this.i2Nu6XHKic.get() == MessageInteract.Action.TJImwK2 ? this.duJ6sPeetAcBh6P : (BlockPos)this.vGAMA89H.get();
         if (resolvedPos == null) {
            this.fStlZIvY9lS8 = false;
         } else if (this.QfZy2gWnKInxFPr > 0) {
            this.QfZy2gWnKInxFPr--;
            if (this.QfZy2gWnKInxFPr == 0) {
               PathKit.fStlZIvY9lS8(new BlockHitResult(Vec3d.ofCenter(resolvedPos), Direction.UP, resolvedPos, false));
               if (this.i2Nu6XHKic.get() == MessageInteract.Action.TJImwK2 && (Boolean)this.qhulAUwd.get()) {
                  int pearlSlot = PathKit.TJImwK2(Items.ENDER_PEARL);
                  if (pearlSlot != -1) {
                     PathKit.fStlZIvY9lS8(pearlSlot, false);
                  }
               }

               this.fStlZIvY9lS8 = false;
               this.duJ6sPeetAcBh6P = null;
               if ((Boolean)this.BrmKXSYhWhqoB1.get() && this.j376sIQ != null) {
                  obf.GyhoRo.fStlZIvY9lS8(new GoalBlock(this.j376sIQ));
               }
            }
         } else if (!oyDPiOVBGKG.fStlZIvY9lS8(resolvedPos, 4.0)) {
            this.I3psabmOrOI = 0;
            obf.GyhoRo.fStlZIvY9lS8(new GoalNear(resolvedPos, 2));
         } else {
            obf.GyhoRo.wSJeYsJcmjNZf();
            double yaw = Rotations.getYaw(resolvedPos);
            double pitch = Rotations.getPitch(resolvedPos);
            Rotations.rotate(yaw, pitch);
            this.I3psabmOrOI++;
            if (this.I3psabmOrOI >= 3) {
               if (this.i2Nu6XHKic.get() == MessageInteract.Action.QfZy2gWnKInxFPr) {
                  TJImwK2.interactionManager.attackBlock(resolvedPos, Direction.UP);
                  this.I3psabmOrOI = 0;
                  this.fStlZIvY9lS8 = false;
               } else if (this.i2Nu6XHKic.get() == MessageInteract.Action.fStlZIvY9lS8) {
                  PathKit.fStlZIvY9lS8(new BlockHitResult(Vec3d.ofCenter(resolvedPos), Direction.UP, resolvedPos, false));
                  this.I3psabmOrOI = 0;
                  this.fStlZIvY9lS8 = false;
               } else if (TJImwK2.world.getBlockState(resolvedPos).getBlock() instanceof TrapdoorBlock) {
                  PathKit.fStlZIvY9lS8(new BlockHitResult(Vec3d.ofCenter(resolvedPos), Direction.UP, resolvedPos, false));
                  this.I3psabmOrOI = 0;
                  this.QfZy2gWnKInxFPr = 10;
               } else {
                  this.info("Trapdoor no longer present at " + resolvedPos + ", aborting.", new Object[0]);
                  this.I3psabmOrOI = 0;
                  this.fStlZIvY9lS8 = false;
                  this.duJ6sPeetAcBh6P = null;
               }
            }
         }
      }
   }

   private void fStlZIvY9lS8(String playerName, String message, String antiSpam) {
      if (TJImwK2.player != null) {
         TJImwK2.player.networkHandler.sendChatCommand("msg " + playerName + " " + message + " " + antiSpam);
      }
   }

   public static String fStlZIvY9lS8(int length) {
      StringBuilder sb = new StringBuilder(length);

      for (int i = 0; i < length; i++) {
         sb.append(
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
               .charAt(voxTwgNzyrpaAnP.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".length()))
         );
      }

      return "[" + sb + "]";
   }

   public static enum Action {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr,
      TJImwK2;
   }
}
