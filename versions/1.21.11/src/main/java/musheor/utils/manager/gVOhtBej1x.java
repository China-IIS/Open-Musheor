package musheor.utils.manager;

import java.util.List;
import java.util.function.BooleanSupplier;
import meteordevelopment.meteorclient.events.packets.PacketEvent.Receive;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.world.TickRate;
import musheor.modules.automation.ElytraTweakz;
import musheor.utils.JhujBv5;
import musheor.utils.system.MusheorSystem;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.DataTracker.SerializedEntry;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import obf.jU8xXhmkg;

public class gVOhtBej1x {
   private static BooleanSupplier fStlZIvY9lS8;
   private static Module QfZy2gWnKInxFPr;
   private static boolean TJImwK2;
   private static final Object wSJeYsJcmjNZf = new Object();
   private static volatile boolean i2Nu6XHKic;
   private static volatile boolean vGAMA89H;
   private static volatile boolean qhulAUwd;
   private static int BrmKXSYhWhqoB1;
   private static int TXWyNb;
   private static int I3psabmOrOI;
   private static int duJ6sPeetAcBh6P;
   private static long j376sIQ;
   private static boolean V7zKJ4n6D;
   private static boolean voxTwgNzyrpaAnP;

   public static void fStlZIvY9lS8(Module module, BooleanSupplier setting) {
      QfZy2gWnKInxFPr = module;
      fStlZIvY9lS8 = setting;
      TJImwK2 = false;
      Nt3xeF();
      if (fStlZIvY9lS8()) {
         wSJeYsJcmjNZf();
      }
   }

   public static void fStlZIvY9lS8(Module module) {
      if (QfZy2gWnKInxFPr == module) {
         fStlZIvY9lS8(true);
         QfZy2gWnKInxFPr = null;
         fStlZIvY9lS8 = null;
         TJImwK2 = false;
         Nt3xeF();
      }
   }

   public static boolean fStlZIvY9lS8() {
      return fStlZIvY9lS8 != null && fStlZIvY9lS8.getAsBoolean();
   }

   private static boolean zkcZ4Xkp9Ez7Y() {
      if (!fStlZIvY9lS8()) {
         return false;
      }

      if (JhujBv5.fStlZIvY9lS8.player.isOnGround()) {
         QfZy2gWnKInxFPr();
         return true;
      }

      BrmKXSYhWhqoB1++;
      if (qhulAUwd && ++TXWyNb > 8) {
         fStlZIvY9lS8(true);
      }

      if (flWHzEyn() && jU8xXhmkg.wSJeYsJcmjNZf() > 4) {
         boolean wearingElytra = JhujBv5.fStlZIvY9lS8.player.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA);
         int elytraSlot = wearingElytra ? -1 : i2Nu6XHKic();
         if (wearingElytra || elytraSlot != -1 && JhujBv5.fStlZIvY9lS8.player.currentScreenHandler == JhujBv5.fStlZIvY9lS8.player.playerScreenHandler) {
            boolean swapped = false;
            fStlZIvY9lS8(false);
            vGAMA89H = false;
            i2Nu6XHKic = true;
            BrmKXSYhWhqoB1 = 0;
            if (!wearingElytra) {
               fStlZIvY9lS8(elytraSlot);
               swapped = true;
            }

            umvNbb7iD9UpR();
            if (TJImwK2) {
               TJImwK2 = false;
               if (pvfA15fQSVM.TJImwK2(Items.FIREWORK_ROCKET) != -1) {
                  xileXsbo();
               }
            }

            if (swapped) {
               fStlZIvY9lS8(elytraSlot);
            }

            return true;
         } else {
            return true;
         }
      } else {
         return true;
      }
   }

   private static boolean flWHzEyn() {
      if (BrmKXSYhWhqoB1 < 2) {
         return false;
      } else if (TickRate.INSTANCE.getTimeSinceLastTick() > 1.5F) {
         return false;
      } else {
         return i2Nu6XHKic && !vGAMA89H ? BrmKXSYhWhqoB1 > FVjbhYkk53UT() : true;
      }
   }

   private static int FVjbhYkk53UT() {
      float tps = TickRate.INSTANCE.getTickRate();
      if (tps <= 0.0F) {
         tps = 20.0F;
      }

      int ping = 0;
      if (JhujBv5.fStlZIvY9lS8.getNetworkHandler() != null) {
         PlayerListEntry entry = JhujBv5.fStlZIvY9lS8.getNetworkHandler().getPlayerListEntry(JhujBv5.fStlZIvY9lS8.player.getUuid());
         if (entry != null) {
            ping = entry.getLatency();
         }
      }

      return 2 + (int)Math.ceil((ping + 2000.0 / tps) / 50.0);
   }

   public static void fStlZIvY9lS8(Receive event) {
      if (fStlZIvY9lS8()) {
         if (!(event.packet instanceof PlayerPositionLookS2CPacket)) {
            if (event.packet instanceof EntityTrackerUpdateS2CPacket packet && i2Nu6XHKic) {
               ClientPlayerEntity player = JhujBv5.fStlZIvY9lS8.player;
               if (player != null && packet.id() == player.getId()) {
                  List<SerializedEntry<?>> values = packet.trackedValues();

                  for (int i = 0; i < values.size(); i++) {
                     SerializedEntry<?> entry = values.get(i);
                     if (entry.id() == 0 && entry.value() instanceof Byte flags) {
                        if ((flags & -128) != 0) {
                           return;
                        }

                        vGAMA89H = true;
                        if (!player.isGliding()) {
                           return;
                        }

                        TrackedDataHandler<Byte> handler = (TrackedDataHandler<Byte>)(TrackedDataHandler<?>)entry.handler();
                        synchronized (wSJeYsJcmjNZf) {
                           try {
                              values.set(i, new SerializedEntry(entry.id(), handler, (byte)(flags | -128)));
                           } catch (UnsupportedOperationException e) {
                              return;
                           }

                           TXWyNb = 0;
                           qhulAUwd = true;
                           U6PQSXqW.fStlZIvY9lS8();
                           return;
                        }
                     }
                  }
               }
            }
         } else {
            if (qhulAUwd || U6PQSXqW.QfZy2gWnKInxFPr()) {
               U6PQSXqW.TJImwK2();
               JhujBv5.fStlZIvY9lS8.execute(() -> {
                  fStlZIvY9lS8(true);
                  i2Nu6XHKic = false;
               });
            }
         }
      }
   }

   public static void QfZy2gWnKInxFPr() {
      fStlZIvY9lS8(true);
      i2Nu6XHKic = false;
   }

   public static void TJImwK2() {
      U6PQSXqW.wSJeYsJcmjNZf();
      Nt3xeF();
   }

   private static void fStlZIvY9lS8(boolean dropClientGlide) {
      boolean held;
      synchronized (wSJeYsJcmjNZf) {
         held = qhulAUwd;
         qhulAUwd = false;
         TXWyNb = 0;
         U6PQSXqW.TJImwK2();
      }

      if (held && dropClientGlide && JhujBv5.fStlZIvY9lS8.player != null && JhujBv5.fStlZIvY9lS8.player.isGliding()) {
         JhujBv5.fStlZIvY9lS8.player.stopGliding();
      }
   }

   private static void Nt3xeF() {
      synchronized (wSJeYsJcmjNZf) {
         qhulAUwd = false;
         TXWyNb = 0;
      }

      i2Nu6XHKic = false;
      vGAMA89H = false;
      BrmKXSYhWhqoB1 = 2;
   }

   private static void fStlZIvY9lS8(int hotbarSlot) {
      JhujBv5.fStlZIvY9lS8
         .interactionManager
         .clickSlot(JhujBv5.fStlZIvY9lS8.player.playerScreenHandler.syncId, 6, hotbarSlot, SlotActionType.SWAP, JhujBv5.fStlZIvY9lS8.player);
   }

   public static void wSJeYsJcmjNZf() {
      if (JhujBv5.fStlZIvY9lS8.player != null && JhujBv5.fStlZIvY9lS8.interactionManager != null) {
         if (JhujBv5.fStlZIvY9lS8.player.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA)) {
            if (JhujBv5.fStlZIvY9lS8.player.currentScreenHandler == JhujBv5.fStlZIvY9lS8.player.playerScreenHandler) {
               int chestplate = pvfA15fQSVM.qhulAUwd();
               if (chestplate >= 0 && chestplate <= 8) {
                  fStlZIvY9lS8(chestplate);
               } else {
                  int free = zXcO5rZ();
                  if (free == -1) {
                     MusheorSystem.debug("%sFake-fly: no free hotbar slot to stow the equipped elytra.", Formatting.RED);
                  } else {
                     fStlZIvY9lS8(free);
                     if (chestplate != -1) {
                        InvUtils.move().from(chestplate).toArmor(2);
                     }
                  }
               }
            }
         }
      }
   }

   public static int i2Nu6XHKic() {
      for (int i = 0; i < 9; i++) {
         ItemStack stack = JhujBv5.fStlZIvY9lS8.player.getInventory().getStack(i);
         if (stack.isOf(Items.ELYTRA) && stack.getMaxDamage() - stack.getDamage() > 1) {
            return i;
         }
      }

      return -1;
   }

   private static int zXcO5rZ() {
      for (int i = 0; i < 9; i++) {
         if (JhujBv5.fStlZIvY9lS8.player.getInventory().getStack(i).isEmpty()) {
            return i;
         }
      }

      return -1;
   }

   public static void vGAMA89H() {
      I3psabmOrOI = -1;
      duJ6sPeetAcBh6P = 0;
      j376sIQ = 0L;
      V7zKJ4n6D = true;
      voxTwgNzyrpaAnP = false;
   }

   public static boolean qhulAUwd() {
      if (JhujBv5.fStlZIvY9lS8.player == null) {
         return true;
      }

      ItemStack worn = JhujBv5.fStlZIvY9lS8.player.getEquippedStack(EquipmentSlot.CHEST);
      return worn.isOf(Items.ELYTRA) && worn.getMaxDamage() - worn.getDamage() > ElytraTweakz.QfZy2gWnKInxFPr.fStlZIvY9lS8.get()
         ? false
         : pvfA15fQSVM.vGAMA89H() == -1;
   }

   public static void BrmKXSYhWhqoB1() {
      V7zKJ4n6D = false;
      voxTwgNzyrpaAnP = false;
   }

   public static boolean TXWyNb() {
      return V7zKJ4n6D;
   }

   public static boolean I3psabmOrOI() {
      return voxTwgNzyrpaAnP && V7zKJ4n6D;
   }

   public static void duJ6sPeetAcBh6P() {
      voxTwgNzyrpaAnP = true;
   }

   public static void j376sIQ() {
      I3psabmOrOI++;
      if (I3psabmOrOI >= (Integer)MusheorSystem.Manager.launchDelay.get()) {
         if (JhujBv5.fStlZIvY9lS8.player != null) {
            boolean gliding = JhujBv5.fStlZIvY9lS8.player.isGliding();
            if (gliding) {
               voxTwgNzyrpaAnP = true;
            } else if (!V7zKJ4n6D) {
               voxTwgNzyrpaAnP = false;
            }
         }

         if (duJ6sPeetAcBh6P > 0) {
            duJ6sPeetAcBh6P--;
         }
      }
   }

   public static boolean V7zKJ4n6D() {
      if ((Boolean)MusheorSystem.Manager.autoRocket.get()) {
         for (Entity entity : JhujBv5.fStlZIvY9lS8.world.getEntities()) {
            if (entity instanceof FireworkRocketEntity rocket && rocket.getOwner() == JhujBv5.fStlZIvY9lS8.player) {
               return true;
            }
         }

         return false;
      } else {
         return System.currentTimeMillis() - j376sIQ < (Double)MusheorSystem.Manager.rocketDelay.get() * 1000.0;
      }
   }

   public static void voxTwgNzyrpaAnP() {
      if (!RotationManager.outranksMovement()) {
         if (duJ6sPeetAcBh6P <= 0 && !V7zKJ4n6D()) {
            if (pvfA15fQSVM.TJImwK2(Items.FIREWORK_ROCKET) != -1) {
               if (!JhujBv5.fStlZIvY9lS8.player.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA)) {
                  if (fStlZIvY9lS8()) {
                     TJImwK2 = true;
                  }
               } else {
                  xileXsbo();
               }
            }
         }
      }
   }

   public static void xileXsbo() {
      if (!RotationManager.outranksMovement()) {
         int rocket = pvfA15fQSVM.TJImwK2(Items.FIREWORK_ROCKET);
         if (rocket != -1) {
            float yaw = RotationManager.yawOr(JhujBv5.fStlZIvY9lS8.player.getYaw());
            float pitch = RotationManager.pitchOr(JhujBv5.fStlZIvY9lS8.player.getPitch());
            pvfA15fQSVM.fStlZIvY9lS8(
               rocket,
               () -> JhujBv5.fStlZIvY9lS8
                  .interactionManager
                  .sendSequencedPacket(JhujBv5.fStlZIvY9lS8.world, sequence -> new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, sequence, yaw, pitch))
            );
            MusheorSystem.debug("%sAttempted to fire a rocket!", Formatting.AQUA);
            duJ6sPeetAcBh6P = (Integer)MusheorSystem.Manager.rocketTimeout.get();
            j376sIQ = System.currentTimeMillis();
         }
      }
   }

   public static void nOwp0NYl() {
      if (JhujBv5.fStlZIvY9lS8.player != null && JhujBv5.fStlZIvY9lS8.getNetworkHandler() != null && JhujBv5.fStlZIvY9lS8.interactionManager != null) {
         if (!JhujBv5.fStlZIvY9lS8.player.isSubmergedInWater()) {
            if (!zkcZ4Xkp9Ez7Y()) {
               if (!JhujBv5.fStlZIvY9lS8.player.isGliding() && !JhujBv5.fStlZIvY9lS8.player.isOnGround()) {
                  umvNbb7iD9UpR();
                  duJ6sPeetAcBh6P();
               }
            }
         }
      }
   }

   public static void umvNbb7iD9UpR() {
      JhujBv5.fStlZIvY9lS8
         .interactionManager
         .sendSequencedPacket(JhujBv5.fStlZIvY9lS8.world, sequence -> new ClientCommandC2SPacket(JhujBv5.fStlZIvY9lS8.player, Mode.START_FALL_FLYING));
      JhujBv5.fStlZIvY9lS8.player.startGliding();
      duJ6sPeetAcBh6P();
   }
}
