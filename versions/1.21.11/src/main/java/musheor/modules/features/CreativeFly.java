package musheor.modules.features;

import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent.Receive;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.EnumSetting.Builder;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.player.ChestSwap;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.orbit.EventHandler;
import musheor.compat.VersionHelper;
import musheor.utils.manager.RotationManager;
import musheor.utils.manager.gVOhtBej1x;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;

public class CreativeFly extends Module {
   private final SettingGroup QfZy2gWnKInxFPr = this.settings.getDefaultGroup();
   private final SettingGroup TJImwK2 = this.settings.createGroup("Grim");
   private final Setting<CreativeFly.HoverMode> wSJeYsJcmjNZf = this.QfZy2gWnKInxFPr
      .add(((Builder)((Builder)new Builder().name("hover")).defaultValue(CreativeFly.HoverMode.FullStop)).build());
   private final Setting<Boolean> i2Nu6XHKic = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("auto-swap"))
                  .description("Automatically swaps to your elytra when activating"))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> vGAMA89H = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("swap-back"))
                     .description("Automatically swaps back to your chestplate when deactivating"))
                  .defaultValue(true))
               .visible(this.i2Nu6XHKic::get))
            .build()
      );
   private final Setting<Boolean> qhulAUwd = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("pause-on-ground"))
                  .description("Pauses when the player is touching the ground"))
               .defaultValue(false))
            .build()
      );
   private final Setting<Boolean> BrmKXSYhWhqoB1 = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("fake-fly"))
                  .description("Allows you to fly without having an elytra equipped (requires elytra in the hotbar)"))
               .defaultValue(false))
            .build()
      );
   public static CreativeFly fStlZIvY9lS8;
   private boolean TXWyNb;
   private Vec3d I3psabmOrOI;
   private boolean duJ6sPeetAcBh6P;
   private int j376sIQ;

   public CreativeFly() {
      super(musheor.musheor.MAIN, "creative-fly", "Creative flight, but in survival, optimized for GrimAC");
      fStlZIvY9lS8 = this;
   }

   public void onActivate() {
      gVOhtBej1x.vGAMA89H();
      if (gVOhtBej1x.qhulAUwd()) {
         ChatUtils.info("§cNo elytra found, disabling...", new Object[0]);
         this.toggle();
      } else {
         gVOhtBej1x.fStlZIvY9lS8(this, this.BrmKXSYhWhqoB1::get);
         if (this.mc.player != null && this.mc.world != null) {
            if ((Boolean)this.i2Nu6XHKic.get()
               && !(Boolean)this.BrmKXSYhWhqoB1.get()
               && !this.mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem().equals(Items.ELYTRA)) {
               ((ChestSwap)Modules.get().get(ChestSwap.class)).swap();
            }

            if (this.mc.player.isOnGround()) {
               this.mc.player.jump();
            }

            this.TXWyNb = false;
            this.I3psabmOrOI = null;
            this.duJ6sPeetAcBh6P = false;
         }
      }
   }

   public void onDeactivate() {
      RotationManager.clear();
      gVOhtBej1x.BrmKXSYhWhqoB1();
      if (this.mc.player != null && this.mc.world != null) {
         this.mc.player.setNoGravity(false);
         this.I3psabmOrOI = null;
         if ((Boolean)this.i2Nu6XHKic.get() && (Boolean)this.vGAMA89H.get() && !(Boolean)this.BrmKXSYhWhqoB1.get()) {
            ((ChestSwap)Modules.get().get(ChestSwap.class)).swap();
         } else {
            gVOhtBej1x.nOwp0NYl();
         }

         gVOhtBej1x.fStlZIvY9lS8(this);
      } else {
         gVOhtBej1x.fStlZIvY9lS8(this);
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(Receive event) {
      gVOhtBej1x.fStlZIvY9lS8(event);
   }

   @EventHandler
   private void fStlZIvY9lS8(GameLeftEvent event) {
      gVOhtBej1x.TJImwK2();
   }

   @EventHandler
   private void fStlZIvY9lS8(Pre event) {
      if (this.mc.player != null) {
         RotationManager.release(0);
         if ((Boolean)this.qhulAUwd.get() && this.mc.player.isOnGround()) {
            gVOhtBej1x.QfZy2gWnKInxFPr();
         } else {
            gVOhtBej1x.j376sIQ();
            boolean up = this.mc.options.jumpKey.isPressed();
            boolean down = this.mc.options.sneakKey.isPressed();
            boolean w = this.mc.options.forwardKey.isPressed();
            boolean a = this.mc.options.leftKey.isPressed();
            boolean s = this.mc.options.backKey.isPressed();
            boolean d = this.mc.options.rightKey.isPressed();
            boolean any = up || down || w || a || s || d;
            this.duJ6sPeetAcBh6P = any;
            float cameraYaw = this.mc.player.getYaw();
            if (any) {
               if (this.I3psabmOrOI != null) {
                  this.mc.player.setPosition(this.I3psabmOrOI.x, this.I3psabmOrOI.y, this.I3psabmOrOI.z);
                  this.mc.player.setVelocity(Vec3d.ZERO);
                  this.mc.player.setNoGravity(false);
                  this.mc.player.resetPosition();
                  this.I3psabmOrOI = null;
               }

               float flightYaw = cameraYaw;
               if (w && a) {
                  flightYaw -= 45.0F;
               } else if (w && d) {
                  flightYaw += 45.0F;
               } else if (s && a) {
                  flightYaw -= 135.0F;
               } else if (s && d) {
                  flightYaw += 135.0F;
               } else if (a) {
                  flightYaw -= 90.0F;
               } else if (d) {
                  flightYaw += 90.0F;
               } else if (s) {
                  flightYaw += 180.0F;
               }

               float flightPitch = -1.0F;
               if (!up || !w && !a && !s && !d) {
                  if (up) {
                     flightPitch = -90.0F;
                  } else if (!down || !w && !a && !s && !d) {
                     if (down) {
                        flightPitch = 90.0F;
                     }
                  } else {
                     flightPitch = 45.0F;
                  }
               } else {
                  flightPitch = -45.0F;
               }

               RotationManager.setServerYaw(flightYaw);
               RotationManager.setServerPitch(flightPitch);
               RotationManager.setMoveMode(RotationManager.gVOhtBej1x.Forward, 0);
            }

            if (this.I3psabmOrOI == null || (Boolean)this.BrmKXSYhWhqoB1.get()) {
               gVOhtBej1x.nOwp0NYl();
            }

            if (!any) {
               if (this.wSJeYsJcmjNZf.get() != CreativeFly.HoverMode.FullStop) {
                  if (gVOhtBej1x.V7zKJ4n6D()) {
                     float flightYaw = cameraYaw;
                     float flightPitch = -1.0F;
                     if (this.wSJeYsJcmjNZf.get() == CreativeFly.HoverMode.FlipHorizontal) {
                        if (this.TXWyNb) {
                           flightYaw += 180.0F;
                        }
                     } else if (this.wSJeYsJcmjNZf.get() == CreativeFly.HoverMode.FlipVertical) {
                        flightPitch = this.TXWyNb ? -90.0F : 90.0F;
                     }

                     this.TXWyNb = !this.TXWyNb;
                     RotationManager.setServerYaw(flightYaw);
                     RotationManager.setServerPitch(flightPitch);
                  } else {
                     gVOhtBej1x.voxTwgNzyrpaAnP();
                  }
               } else {
                  this.fStlZIvY9lS8();
               }
            } else {
               gVOhtBej1x.voxTwgNzyrpaAnP();
            }
         }
      }
   }

   private void fStlZIvY9lS8() {
      if (this.I3psabmOrOI == null) {
         this.I3psabmOrOI = VersionHelper.get().getPlayerPos();
         this.j376sIQ = 0;
      }

      if (++this.j376sIQ >= 60 && !gVOhtBej1x.V7zKJ4n6D()) {
         this.j376sIQ = 0;
         this.I3psabmOrOI = this.I3psabmOrOI.subtract(0.0, 0.0313, 0.0);
      }

      this.mc.player.setVelocity(Vec3d.ZERO);
      this.mc.player.setNoGravity(true);
      this.mc.player.setPosition(this.I3psabmOrOI.x, this.I3psabmOrOI.y, this.I3psabmOrOI.z);
      gVOhtBej1x.voxTwgNzyrpaAnP();
   }

   public enum HoverMode {
      FullStop,
      FlipHorizontal,
      FlipVertical;

      HoverMode() {
      }
   }
}
