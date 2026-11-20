package musheor.mixin;

import java.util.UUID;
import meteordevelopment.meteorclient.systems.modules.Modules;
import musheor.modules.automation.KekBounce;
import musheor.utils.JhujBv5;
import musheor.utils.manager.RotationManager;
import musheor.utils.manager.gVOhtBej1x;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Entity.class})
public class MixinEntity {
   @Shadow
   protected UUID uuid;

   @Inject(
      at = {@At("RETURN")},
      method = {"getRotationVector"},
      cancellable = true
   )
   private void getRotationVector(CallbackInfoReturnable<Vec3d> cir) {
      if (JhujBv5.fStlZIvY9lS8.player != null && this.uuid == JhujBv5.fStlZIvY9lS8.player.getUuid() && RotationManager.isActive()) {
         float yaw = RotationManager.yawOr(JhujBv5.fStlZIvY9lS8.player.getYaw());
         float pitch = RotationManager.pitchOr(JhujBv5.fStlZIvY9lS8.player.getPitch());
         float f = pitch * (float) (Math.PI / 180.0);
         float g = -yaw * (float) (Math.PI / 180.0);
         float cosG = MathHelper.cos(g);
         float sinG = MathHelper.sin(g);
         float cosF = MathHelper.cos(f);
         float sinF = MathHelper.sin(f);
         cir.setReturnValue(new Vec3d(sinG * cosF, -sinF, cosG * cosF));
      }
   }

   @Redirect(
      method = {"updateVelocity"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/Entity;getYaw()F"
      )
   )
   private float musheor$travelYaw(Entity self) {
      float yaw = self.getYaw();
      return JhujBv5.fStlZIvY9lS8.player != null && this.uuid == JhujBv5.fStlZIvY9lS8.player.getUuid() && RotationManager.isInputManaged()
         ? RotationManager.yawOr(yaw)
         : yaw;
   }

   @Inject(
      at = {@At("HEAD")},
      method = {"getPose"},
      cancellable = true
   )
   private void getPose(CallbackInfoReturnable<EntityPose> cir) {
      KekBounce bounce = (KekBounce)Modules.get().get(KekBounce.class);
      if (JhujBv5.fStlZIvY9lS8.player != null && this.uuid == JhujBv5.fStlZIvY9lS8.player.getUuid()) {
         if (gVOhtBej1x.I3psabmOrOI()) {
            boolean wearingElytra = JhujBv5.fStlZIvY9lS8.player.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA);
            cir.setReturnValue(wearingElytra ? EntityPose.GLIDING : EntityPose.STANDING);
         } else if (bounce != null && bounce.fStlZIvY9lS8()) {
            cir.setReturnValue(EntityPose.GLIDING);
         }
      }
   }
}
