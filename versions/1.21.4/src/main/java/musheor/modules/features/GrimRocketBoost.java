package musheor.modules.features;

import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.DoubleSetting.Builder;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import musheor.utils.manager.RotationManager;
import musheor.utils.manager.gVOhtBej1x;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.util.math.Vec3d;

public class GrimRocketBoost extends Module {
   private final SettingGroup fStlZIvY9lS8 = this.settings.getDefaultGroup();
   private final Setting<Double> QfZy2gWnKInxFPr = this.fStlZIvY9lS8
      .add(
         ((Builder)((Builder)new Builder().name("boost-speed")).description("Target speed in blocks/tick to fly at while boosting."))
            .defaultValue(1.65)
            .sliderRange(0.5, 2.5)
            .decimalPlaces(2)
            .build()
      );
   private int TJImwK2;
   private float wSJeYsJcmjNZf;
   private float i2Nu6XHKic;
   private Float vGAMA89H;
   private Float qhulAUwd;

   public GrimRocketBoost() {
      super(
         musheor.musheor.MAIN,
         "grim-rocket-boost",
         "While a rocket is active, aims velocity along your flight direction at 'boost-speed' then rescales it up to a per-axis box, so flying diagonally (e.g. north + up) boosts every axis at once and total speed passes the single-axis cap. Jitters yaw ±0.01°/tick so GrimAC credits the boost."
      );
   }

   public void onActivate() {
      this.TJImwK2 = 0;
      this.vGAMA89H = null;
      this.qhulAUwd = null;
      if (this.mc.player != null) {
         this.wSJeYsJcmjNZf = this.mc.player.getYaw();
         this.i2Nu6XHKic = this.mc.player.getPitch();
      }
   }

   public void onDeactivate() {
      this.fStlZIvY9lS8();
   }

   @EventHandler(priority = -200)
   private void fStlZIvY9lS8(Pre event) {
      if (this.mc.player != null && this.mc.world != null) {
         this.TJImwK2++;
         this.fStlZIvY9lS8();
         if (this.mc.player.isGliding()) {
            if (this.QfZy2gWnKInxFPr()) {
               if (!RotationManager.outranksMovement()) {
                  Float pendingYaw = RotationManager.getPendingYaw();
                  Float pendingPitch = RotationManager.getPendingPitch();
                  float flightYaw = pendingYaw != null ? pendingYaw : this.mc.player.getYaw();
                  float flightPitch = pendingPitch != null ? pendingPitch : this.mc.player.getPitch();
                  Vec3d look = this.mc.player.getRotationVector(flightPitch, flightYaw);
                  this.mc.player.setVelocity(this.fStlZIvY9lS8(look.multiply((Double)this.QfZy2gWnKInxFPr.get()), look));
                  float yaw = flightYaw + (this.TJImwK2 % 2 == 0 ? 0.01F : -0.01F);
                  if (RotationManager.setYaw(yaw, 0) && RotationManager.setPitch(flightPitch, 0)) {
                     this.vGAMA89H = yaw;
                     this.qhulAUwd = flightPitch;
                  }

                  this.wSJeYsJcmjNZf = flightYaw;
                  this.i2Nu6XHKic = flightPitch;
               }
            }
         }
      }
   }

   private void fStlZIvY9lS8() {
      if (this.vGAMA89H != null) {
         if (this.vGAMA89H.equals(RotationManager.getPendingYaw()) && this.qhulAUwd.equals(RotationManager.getPendingPitch())) {
            RotationManager.clear();
         }

         this.vGAMA89H = null;
         this.qhulAUwd = null;
      }
   }

   private boolean QfZy2gWnKInxFPr() {
      for (Entity entity : this.mc.world.getEntities()) {
         if (entity instanceof FireworkRocketEntity rocket && rocket.getOwner() == this.mc.player) {
            return true;
         }
      }

      return gVOhtBej1x.V7zKJ4n6D();
   }

   private Vec3d fStlZIvY9lS8(Vec3d motion, Vec3d look) {
      if (motion.lengthSquared() < 1.0E-6) {
         return motion;
      }

      if (this.mc.player.isTouchingWater()) {
         return motion;
      }

      Vec3d currentLook = look.normalize();
      Vec3d lastLook = this.mc.player.getRotationVector(this.i2Nu6XHKic, this.wSJeYsJcmjNZf).normalize();
      double a = 0.05;
      double minX = Math.min(-a, currentLook.x) + Math.min(-a, lastLook.x);
      double minY = Math.min(-a, currentLook.y) + Math.min(-a, lastLook.y);
      double minZ = Math.min(-a, currentLook.z) + Math.min(-a, lastLook.z);
      double maxX = Math.max(a, currentLook.x) + Math.max(a, lastLook.x);
      double maxY = Math.max(a, currentLook.y) + Math.max(a, lastLook.y);
      double maxZ = Math.max(a, currentLook.z) + Math.max(a, lastLook.z);
      double threshold = Math.min((Double)this.QfZy2gWnKInxFPr.get(), motion.length());
      minX = Math.max(-threshold, minX * threshold);
      minY = Math.max(-threshold, minY * threshold);
      minZ = Math.max(-threshold, minZ * threshold);
      maxX = Math.min(threshold, maxX * threshold);
      maxY = Math.min(threshold, maxY * threshold);
      maxZ = Math.min(threshold, maxZ * threshold);
      double gravity = this.mc.player.hasNoGravity() ? 0.0 : (this.mc.player.hasStatusEffect(StatusEffects.SLOW_FALLING) ? 0.01 : 0.08);
      Vec3d used = motion.add(0.0, -gravity, 0.0);
      double scaleX = Math.abs(used.x) < 1.0E-6 ? 0.0 : (used.x < 0.0 ? used.x / minX : used.x / maxX);
      double scaleY = Math.abs(used.y) < 1.0E-6 ? 0.0 : (used.y < 0.0 ? (-used.y - gravity + 0.02) / -minY : (used.y + gravity + 0.02) / maxY);
      double scaleZ = Math.abs(used.z) < 1.0E-6 ? 0.0 : (used.z < 0.0 ? used.z / minZ : used.z / maxZ);
      double maxScale = Math.max(scaleX, Math.max(scaleY, scaleZ));
      return !(maxScale < 1.0E-6) && !(maxScale > 1.0) ? motion.multiply(1.0 / maxScale) : motion;
   }
}
