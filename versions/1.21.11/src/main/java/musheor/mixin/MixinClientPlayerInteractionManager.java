package musheor.mixin;

import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import musheor.modules.features.KekMine;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class MixinClientPlayerInteractionManager {
   public MixinClientPlayerInteractionManager() {
   }

   @Inject(method = "attackBlock", at = @At("HEAD"), cancellable = true)
   private void onAttackBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
      KekMine kekMine = (KekMine)Modules.get().get(KekMine.class);
      if (kekMine.isActive() && !MinecraftClient.getInstance().player.isCreative()) {
         if (!BlockUtils.canInstaBreak(pos) && (!kekMine.TJImwK2() || !KekMine.fStlZIvY9lS8(pos))) {
            kekMine.TJImwK2(pos);
         } else {
            KekMine.i2Nu6XHKic(pos);
         }

         cir.setReturnValue(true);
      }
   }

   @Inject(method = "updateBlockBreakingProgress", at = @At("HEAD"), cancellable = true)
   private void onUpdateBlockBreakingProgress(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
      KekMine kekMine = (KekMine)Modules.get().get(KekMine.class);
      if (kekMine.isActive() && !MinecraftClient.getInstance().player.isCreative()) {
         if (!BlockUtils.canInstaBreak(pos) && (!kekMine.TJImwK2() || !KekMine.fStlZIvY9lS8(pos))) {
            kekMine.TJImwK2(pos);
         } else {
            KekMine.i2Nu6XHKic(pos);
         }

         cir.setReturnValue(true);
      }
   }
}
