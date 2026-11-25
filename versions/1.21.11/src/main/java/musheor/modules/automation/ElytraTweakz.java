package musheor.modules.automation;

import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.BoolSetting.Builder;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.player.ChestSwap;
import meteordevelopment.meteorclient.utils.misc.Keybind;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.orbit.EventHandler;
import musheor.utils.manager.gVOhtBej1x;
import musheor.utils.manager.pvfA15fQSVM;
import musheor.utils.system.MusheorSystem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ElytraTweakz extends Module {
   private final Setting<Boolean> TJImwK2 = this.settings
      .getDefaultGroup()
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("double-jump-equip-elytra"))
                  .description("Equip your elytra and start gliding when double-tapping spacebar while airborne"))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> wSJeYsJcmjNZf = this.settings
      .getDefaultGroup()
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("land-equip-chestplate"))
                  .description("Automatically equip your chestplate when landing after gliding"))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> i2Nu6XHKic = this.settings
      .getDefaultGroup()
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("auto-swap-broken"))
                  .description("Automatically swap with a new elytra when your current one is broken / damaged"))
               .defaultValue(true))
            .build()
      );
   public final Setting<Integer> fStlZIvY9lS8 = this.settings
      .getDefaultGroup()
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("durability-threshold"))
                  .defaultValue(10))
               .sliderRange(1, 100)
               .visible(this.i2Nu6XHKic::get))
            .build()
      );
   private final Setting<Keybind> vGAMA89H = this.settings
      .getDefaultGroup()
      .add(
         ((meteordevelopment.meteorclient.settings.KeybindSetting.Builder)((meteordevelopment.meteorclient.settings.KeybindSetting.Builder)((meteordevelopment.meteorclient.settings.KeybindSetting.Builder)new meteordevelopment.meteorclient.settings.KeybindSetting.Builder()
                     .name("fire-rocket-keybind"))
                  .description("Fires a rocket when pressed/held. Does nothing if there is already an active rocket"))
               .defaultValue(Keybind.none()))
            .build()
      );
   public static ElytraTweakz QfZy2gWnKInxFPr;
   private boolean qhulAUwd;
   private boolean BrmKXSYhWhqoB1;
   private boolean TXWyNb;
   private long I3psabmOrOI;
   private int duJ6sPeetAcBh6P;
   private int j376sIQ;

   public ElytraTweakz() {
      super(musheor.musheor.MAIN, "elytra-tweakz", "Useful elytra enhancements and tweaks");
      QfZy2gWnKInxFPr = this;
   }

   public void onActivate() {
      this.qhulAUwd = false;
      this.BrmKXSYhWhqoB1 = false;
      this.TXWyNb = false;
      this.I3psabmOrOI = 0L;
      this.duJ6sPeetAcBh6P = 0;
      this.j376sIQ = 0;
   }

   @EventHandler
   private void fStlZIvY9lS8(Pre event) {
      if (this.mc.player != null && this.mc.world != null) {
         boolean onGround = this.mc.player.isOnGround();
         boolean gliding = this.mc.player.isGliding();
         boolean jumpPressed = this.mc.options.jumpKey.isPressed();
         boolean jumpJustPressed = jumpPressed && !this.qhulAUwd;
         if ((Boolean)this.i2Nu6XHKic.get()) {
            ItemStack chest = this.mc.player.getEquippedStack(EquipmentSlot.CHEST);
            if (chest.isOf(Items.ELYTRA)) {
               int dura = chest.getMaxDamage() - chest.getDamage();
               if (dura <= (Integer)this.fStlZIvY9lS8.get()) {
                  for (int i = 0; i < this.mc.player.getInventory().main.size(); i++) {
                     ItemStack stack = this.mc.player.getInventory().getStack(i);
                     if (stack.isOf(Items.ELYTRA) && stack.getMaxDamage() - stack.getDamage() > (Integer)this.fStlZIvY9lS8.get()) {
                        InvUtils.move().from(i).toArmor(2);
                        break;
                     }
                  }
               }
            }
         }

         if (!gVOhtBej1x.TXWyNb() && !((KekBounce)Modules.get().get(KekBounce.class)).isActive()) {
            gVOhtBej1x.j376sIQ();
            if ((Boolean)this.TJImwK2.get()) {
               if (jumpJustPressed) {
                  long now = System.currentTimeMillis();
                  if (!onGround && now - this.I3psabmOrOI < 300L) {
                     if (this.mc.player.isCreative() || this.mc.player.getAbilities().flying) {
                        return;
                     }

                     ItemStack chest = this.mc.player.getEquippedStack(EquipmentSlot.CHEST);
                     if (!chest.isOf(Items.ELYTRA)) {
                        boolean hasElytra = this.mc
                           .player
                           .getInventory()
                           .main
                           .stream()
                           .anyMatch(s -> s.isOf(Items.ELYTRA) && s.getMaxDamage() - s.getDamage() > (Integer)this.fStlZIvY9lS8.get());
                        if (hasElytra) {
                           pvfA15fQSVM.BrmKXSYhWhqoB1();
                           this.duJ6sPeetAcBh6P = 1;
                           return;
                        }
                     } else if (chest.getMaxDamage() - chest.getDamage() > (Integer)this.fStlZIvY9lS8.get()) {
                        gVOhtBej1x.nOwp0NYl();
                     }
                  }

                  this.I3psabmOrOI = now;
               }

               if (this.duJ6sPeetAcBh6P > 0) {
                  this.duJ6sPeetAcBh6P--;
                  gVOhtBej1x.nOwp0NYl();
               }
            }

            if ((Boolean)this.wSJeYsJcmjNZf.get()
               && !this.BrmKXSYhWhqoB1
               && onGround
               && this.TXWyNb
               && this.mc.player.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA)) {
               ((ChestSwap)Modules.get().get(ChestSwap.class)).swap();
            }

            if (this.mc.player.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA) && this.mc.player.isGliding()) {
               if (this.j376sIQ > 0) {
                  this.j376sIQ--;
               }

               if (((Keybind)this.vGAMA89H.get()).isPressed() && this.j376sIQ == 0) {
                  gVOhtBej1x.voxTwgNzyrpaAnP();
                  this.j376sIQ = (Integer)MusheorSystem.Manager.rocketTimeout.get();
               }
            }

            this.qhulAUwd = jumpPressed;
            this.BrmKXSYhWhqoB1 = onGround;
            this.TXWyNb = gliding;
         } else {
            this.qhulAUwd = jumpPressed;
            this.BrmKXSYhWhqoB1 = onGround;
            this.TXWyNb = gliding;
         }
      }
   }
}
