package musheor.modules.features;

import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent.Receive;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.settings.BoolSetting.Builder;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.player.ChestSwap;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import meteordevelopment.meteorclient.utils.world.Dimension;
import meteordevelopment.orbit.EventHandler;
import musheor.musheor;
import musheor.utils.manager.gVOhtBej1x;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;

public class KekFly extends Module {
   private final SettingGroup wSJeYsJcmjNZf = this.settings.getDefaultGroup();
   private final SettingGroup i2Nu6XHKic = this.settings.createGroup("Grim");
   private final Setting<Boolean> vGAMA89H = this.wSJeYsJcmjNZf
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("auto-swap")).description("Automatically swaps to your elytra when activating")).defaultValue(true))
            .build()
      );
   private final Setting<Boolean> qhulAUwd = this.wSJeYsJcmjNZf
      .add(
         ((Builder)((Builder)((Builder)((Builder)new Builder().name("swap-back")).description("Automatically swaps back to your chestplate when deactivating"))
                  .defaultValue(true))
               .visible(this.vGAMA89H::get))
            .build()
      );
   public final Setting<Boolean> fStlZIvY9lS8 = this.wSJeYsJcmjNZf
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("lock-y"))
                  .description("Steer the player's pitch each tick to maintain a target Y level while gliding."))
               .defaultValue(false))
            .build()
      );
   public final Setting<Integer> QfZy2gWnKInxFPr = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("y-level"))
                     .description("The Y level to maintain when lock-y is enabled."))
                  .defaultValue(350))
               .sliderRange(64, 512)
               .visible(this.fStlZIvY9lS8::get))
            .build()
      );
   private final Setting<Boolean> BrmKXSYhWhqoB1 = this.i2Nu6XHKic
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("fake-fly")).description("Allows you to fly while wearing a chestplate")).defaultValue(false))
            .build()
      );
   public static KekFly TJImwK2;

   public KekFly() {
      super(musheor.MAIN, "kek-fly", "ZOOM thru the air, or even on ground...");
      TJImwK2 = this;
   }

   public void onActivate() {
      gVOhtBej1x.vGAMA89H();
      if (gVOhtBej1x.qhulAUwd()) {
         ChatUtils.info("§cNo elytra found, disabling...", new Object[0]);
         this.toggle();
      } else {
         gVOhtBej1x.fStlZIvY9lS8(this, this.BrmKXSYhWhqoB1::get);
         if (this.mc.player != null && this.mc.world != null) {
            if ((Boolean)this.vGAMA89H.get()
               && !(Boolean)this.BrmKXSYhWhqoB1.get()
               && !this.mc.player.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA)) {
               ((ChestSwap)Modules.get().get(ChestSwap.class)).swap();
            }

            if (this.mc.player.isOnGround()) {
               this.mc.player.jump();
            }
         }
      }
   }

   public void onDeactivate() {
      gVOhtBej1x.BrmKXSYhWhqoB1();
      if (this.mc.player != null && this.mc.world != null) {
         this.mc.player.setNoGravity(false);
         if ((Boolean)this.vGAMA89H.get() && (Boolean)this.qhulAUwd.get() && !(Boolean)this.BrmKXSYhWhqoB1.get()) {
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
         gVOhtBej1x.j376sIQ();
         gVOhtBej1x.nOwp0NYl();
         if ((Boolean)this.fStlZIvY9lS8.get()) {
            this.fStlZIvY9lS8();
         }

         gVOhtBej1x.voxTwgNzyrpaAnP();
      }
   }

   private void fStlZIvY9lS8() {
      if (!this.mc.player.isOnGround()) {
         if (PlayerUtils.getDimension() != Dimension.Nether) {
            double deltaY = ((Integer)this.QfZy2gWnKInxFPr.get()).intValue() - this.mc.player.getY();
            float desiredPitch = (float)Math.toDegrees(Math.atan2(-deltaY, 16.0));
            desiredPitch = Math.max(-45.0F, Math.min(45.0F, desiredPitch));
            this.mc.player.setPitch(desiredPitch);
         }
      }
   }
}
