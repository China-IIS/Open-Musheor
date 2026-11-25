package musheor.modules.features;

import meteordevelopment.meteorclient.events.game.GameJoinedEvent;
import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.DoubleSetting.Builder;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import musheor.musheor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.passive.AbstractHorseEntity;

public class HorseFly extends Module {
   private final SettingGroup vGAMA89H = this.settings.getDefaultGroup();
   public final Setting<Double> fStlZIvY9lS8 = this.vGAMA89H
      .add(
         ((Builder)((Builder)new Builder().name("speed")).description("Horizontal speed, blocks per tick."))
            .defaultValue(5.0)
            .range(0.1, 50.0)
            .sliderRange(0.1, 10.0)
            .build()
      );
   public final Setting<Double> QfZy2gWnKInxFPr = this.vGAMA89H
      .add(
         ((Builder)((Builder)new Builder().name("vertical-speed")).description("Up/down speed, blocks per tick. Jump goes up, sprint goes down."))
            .defaultValue(0.5)
            .range(0.0, 5.0)
            .sliderRange(0.0, 5.0)
            .build()
      );
   public final Setting<Double> TJImwK2 = this.vGAMA89H
      .add(
         ((Builder)((Builder)new Builder().name("glide")).description("How far you sink each tick when you aren't going up or down."))
            .defaultValue(0.15)
            .range(0.0, 0.5)
            .sliderRange(0.0, 0.5)
            .decimalPlaces(3)
            .build()
      );
   public final Setting<Boolean> wSJeYsJcmjNZf = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("nan-input"))
                     .description("Sends NaN horse steering input through ViaFabricPlus (server version <= 1.21.1). Needed to bypass Grim."))
                  .defaultValue(true))
               .onChanged(value -> QfZy2gWnKInxFPr()))
            .build()
      );
   public final Setting<Integer> i2Nu6XHKic = this.vGAMA89H
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                           .name("nan-delay"))
                        .description("Ordinary input ticks between NaN inputs. 0 sends NaN every tick."))
                     .defaultValue(0))
                  .range(0, 20)
                  .sliderRange(0, 20)
                  .visible(this.wSJeYsJcmjNZf::get))
               .onChanged(value -> QfZy2gWnKInxFPr()))
            .build()
      );
   private static volatile boolean qhulAUwd;
   private static int BrmKXSYhWhqoB1;

   public HorseFly() {
      super(musheor.MAIN, "horse-fly", "Fly horses.");
   }

   public void onActivate() {
      QfZy2gWnKInxFPr();
   }

   public void onDeactivate() {
      QfZy2gWnKInxFPr();
   }

   public static boolean fStlZIvY9lS8() {
      return qhulAUwd;
   }

   private static void QfZy2gWnKInxFPr() {
      qhulAUwd = false;
      BrmKXSYhWhqoB1 = 0;
   }

   @EventHandler
   private void fStlZIvY9lS8(Pre event) {
      Entity mount = this.mc.player == null ? null : this.mc.player.getVehicle();
      boolean eligible = (Boolean)this.wSJeYsJcmjNZf.get() && this.QfZy2gWnKInxFPr(mount);
      qhulAUwd = eligible && BrmKXSYhWhqoB1 == 0;
      BrmKXSYhWhqoB1 = eligible && BrmKXSYhWhqoB1 < this.i2Nu6XHKic.get() ? BrmKXSYhWhqoB1 + 1 : 0;
   }

   @EventHandler
   private void fStlZIvY9lS8(GameLeftEvent event) {
      QfZy2gWnKInxFPr();
   }

   @EventHandler
   private void fStlZIvY9lS8(GameJoinedEvent event) {
      QfZy2gWnKInxFPr();
   }

   private boolean QfZy2gWnKInxFPr(Entity entity) {
      return this.isActive()
         && this.mc.player != null
         && this.mc.world != null
         && entity != this.mc.player
         && entity instanceof AbstractHorseEntity
         && entity.getControllingPassenger() == this.mc.player;
   }

   public boolean fStlZIvY9lS8(Entity e) {
      if (!this.QfZy2gWnKInxFPr(e)) {
         return false;
      }

      double f = (this.mc.options.forwardKey.isPressed() ? 1 : 0) - (this.mc.options.backKey.isPressed() ? 1 : 0);
      double s = (this.mc.options.leftKey.isPressed() ? 1 : 0) - (this.mc.options.rightKey.isPressed() ? 1 : 0);
      double x = 0.0;
      double z = 0.0;
      double len = Math.sqrt(s * s + f * f);
      if (len > 1.0E-4) {
         s /= len;
         f /= len;
         double yaw = Math.toRadians(this.mc.player.getYaw());
         double sin = Math.sin(yaw);
         double cos = Math.cos(yaw);
         x = (s * cos - f * sin) * (Double)this.fStlZIvY9lS8.get();
         z = (f * cos + s * sin) * (Double)this.fStlZIvY9lS8.get();
      }

      boolean up = this.mc.options.jumpKey.isPressed();
      boolean down = this.mc.options.sprintKey.isPressed();
      double y = up == down ? -(Double)this.TJImwK2.get() : (up ? (Double)this.QfZy2gWnKInxFPr.get() : -(Double)this.QfZy2gWnKInxFPr.get());
      e.setVelocity(x, y, z);
      e.move(MovementType.SELF, e.getVelocity());
      e.onLanding();
      return true;
   }
}
