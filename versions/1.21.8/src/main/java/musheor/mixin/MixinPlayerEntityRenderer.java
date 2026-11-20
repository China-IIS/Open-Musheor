package musheor.mixin;

import meteordevelopment.meteorclient.systems.modules.Modules;
import musheor.modules.automation.KekBounce;
import musheor.modules.features.MoreTags;
import musheor.utils.JhujBv5;
import musheor.utils.manager.RotationManager;
import musheor.utils.manager.gVOhtBej1x;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({PlayerEntityRenderer.class})
public class MixinPlayerEntityRenderer {
   @Inject(
      method = {"updateRenderState"},
      at = {@At("TAIL")}
   )
   private void applyServerRotation(AbstractClientPlayerEntity player, PlayerEntityRenderState state, float tickDelta, CallbackInfo ci) {
      KekBounce bounce = (KekBounce)Modules.get().get(KekBounce.class);
      if (player == JhujBv5.fStlZIvY9lS8.player) {
         if (RotationManager.isActive()) {
            Float yaw = RotationManager.getPendingYaw();
            Float pitch = RotationManager.getPendingPitch();
            if (yaw != null) {
               state.bodyYaw = yaw;
            }

            if (pitch != null) {
               state.pitch = pitch;
            }

            RotationManager.setModelHeadYaw(state, 0.0F);
            state.flyingRotation = 0.0F;
         }

         if (gVOhtBej1x.I3psabmOrOI() && !player.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA)) {
            state.pose = EntityPose.STANDING;
            state.isGliding = false;
            state.isSwimming = false;
            state.leaningPitch = 0.0F;
            state.flyingRotation = 0.0F;
            state.glidingTicks = 0.0F;
            state.applyFlyingRotation = false;
         }
      }
   }

   @Inject(
      method = {"renderLabelIfPresent"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void cancelDefaultNametag(
      PlayerEntityRenderState playerEntityRenderState, Text text, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci
   ) {
      MoreTags module = (MoreTags)Modules.get().get(MoreTags.class);
      if (module != null && module.isActive() && (Boolean)module.fStlZIvY9lS8.get()) {
         ci.cancel();
      }
   }
}
