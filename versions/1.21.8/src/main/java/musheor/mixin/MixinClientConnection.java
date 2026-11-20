package musheor.mixin;

import musheor.modules.hud.HudInfoPlus;
import musheor.modules.tech.ContainerTweaks;
import musheor.utils.system.MusheorSystem;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ButtonClickC2SPacket;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import obf.jU8xXhmkg;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientConnection.class})
public abstract class MixinClientConnection {
   @Inject(
      method = {"send"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onSendPacket(Packet<?> packet, CallbackInfo ci) {
      if (packet instanceof ClickSlotC2SPacket || packet instanceof CreativeInventoryActionC2SPacket || packet instanceof ButtonClickC2SPacket) {
         if (ContainerTweaks.fStlZIvY9lS8.isActive() && (Boolean)MusheorSystem.Manager.noPacketKick.get() && !jU8xXhmkg.QfZy2gWnKInxFPr()) {
            ci.cancel();
            return;
         }

         HudInfoPlus.fStlZIvY9lS8();
      }

      if (packet instanceof PlayerInteractBlockC2SPacket) {
         HudInfoPlus.TJImwK2();
      }

      HudInfoPlus.QfZy2gWnKInxFPr();
   }
}
