package musheor.mixin;

import musheor.utils.JhujBv5;
import musheor.utils.manager.RotationManager;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRotationS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientPlayNetworkHandler.class})
public class MixinClientPlayNetworkHandler {
   @Unique
   private boolean musheor$keepLook;
   @Unique
   private float musheor$yaw;
   @Unique
   private float musheor$pitch;

   @Unique
   private void musheor$capture() {
      this.musheor$keepLook = JhujBv5.fStlZIvY9lS8.player != null && RotationManager.isActive();
      if (this.musheor$keepLook) {
         this.musheor$yaw = JhujBv5.fStlZIvY9lS8.player.getYaw();
         this.musheor$pitch = JhujBv5.fStlZIvY9lS8.player.getPitch();
      }
   }

   @Unique
   private void musheor$restore() {
      if (this.musheor$keepLook) {
         this.musheor$keepLook = false;
         if (JhujBv5.fStlZIvY9lS8.player != null) {
            JhujBv5.fStlZIvY9lS8.player.setYaw(this.musheor$yaw);
            JhujBv5.fStlZIvY9lS8.player.setPitch(this.musheor$pitch);
         }
      }
   }

   @Inject(
      method = {"onPlayerPositionLook"},
      at = {@At("HEAD")}
   )
   private void musheor$captureOnTeleport(PlayerPositionLookS2CPacket packet, CallbackInfo ci) {
      this.musheor$capture();
   }

   @Inject(
      method = {"onPlayerPositionLook"},
      at = {@At("RETURN")}
   )
   private void musheor$restoreOnTeleport(PlayerPositionLookS2CPacket packet, CallbackInfo ci) {
      this.musheor$restore();
   }

   @Inject(
      method = {"onPlayerRotation"},
      at = {@At("HEAD")}
   )
   private void musheor$captureOnRotation(PlayerRotationS2CPacket packet, CallbackInfo ci) {
      this.musheor$capture();
   }

   @Inject(
      method = {"onPlayerRotation"},
      at = {@At("RETURN")}
   )
   private void musheor$restoreOnRotation(PlayerRotationS2CPacket packet, CallbackInfo ci) {
      this.musheor$restore();
   }
}
