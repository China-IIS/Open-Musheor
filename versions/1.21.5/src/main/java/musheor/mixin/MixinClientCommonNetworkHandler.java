package musheor.mixin;

import musheor.utils.manager.U6PQSXqW;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.common.CommonPingS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientCommonNetworkHandler.class})
public class MixinClientCommonNetworkHandler {
   @Inject(
      method = {"onPing"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/ClientCommonNetworkHandler;sendPacket(Lnet/minecraft/Packet;)V"
      )},
      cancellable = true
   )
   private void musheor$holdPong(CommonPingS2CPacket packet, CallbackInfo ci) {
      if (U6PQSXqW.fStlZIvY9lS8(packet.getParameter())) {
         ci.cancel();
      }
   }
}
