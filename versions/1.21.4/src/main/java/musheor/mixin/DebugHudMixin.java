package musheor.mixin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import meteordevelopment.meteorclient.systems.modules.Modules;
import musheor.modules.features.CoordHider;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DebugHud.class)
public class DebugHudMixin {
   public DebugHudMixin() {
   }

   @Inject(method = "getLeftText", at = @At("RETURN"), cancellable = true)
   private void hideLeft(CallbackInfoReturnable<List<String>> cir) {
      CoordHider ch = (CoordHider)Modules.get().get(CoordHider.class);
      if (ch != null && ch.isActive()) {
         List<String> filtered = new ArrayList<>((Collection<? extends String>)cir.getReturnValue());
         filtered.removeIf(line -> {
            if (line == null) {
               return false;
            } else if (line.startsWith("XYZ:")) {
               return true;
            } else {
               return line.startsWith("Block:") ? true : line.startsWith("Chunk:");
            }
         });
         cir.setReturnValue(filtered);
      }
   }

   @Inject(method = "getRightText", at = @At("RETURN"), cancellable = true)
   private void hideRight(CallbackInfoReturnable<List<String>> cir) {
      CoordHider ch = (CoordHider)Modules.get().get(CoordHider.class);
      if (ch != null && ch.isActive()) {
         List<String> filtered = new ArrayList<>((Collection<? extends String>)cir.getReturnValue());
         filtered.removeIf(line -> {
            if (line == null) {
               return false;
            } else {
               return line.contains("Targeted Block") ? true : line.contains("Targeted Fluid");
            }
         });
         cir.setReturnValue(filtered);
      }
   }
}
