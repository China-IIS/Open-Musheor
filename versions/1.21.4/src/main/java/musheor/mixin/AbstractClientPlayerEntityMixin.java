package musheor.mixin;

import musheor.utils.system.MusheorSystem;
import musheor.utils.system.MutualManager;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin {
   public AbstractClientPlayerEntityMixin() {
   }

   @Inject(method = "getSkinTextures", at = @At("RETURN"), cancellable = true)
   private void onGetSkinTextures(CallbackInfoReturnable<SkinTextures> cir) {
      AbstractClientPlayerEntity player = (AbstractClientPlayerEntity)(Object)this;
      MutualManager m = MutualManager.getInstance();
      if (!(Boolean)MusheorSystem.Manager.disableMutuals.get()) {
         Identifier cape = null;
         if (m.hasCape(player)) {
            cape = MutualManager.PLUS_CAPE;
         }

         if (cape != null) {
            SkinTextures original = (SkinTextures)cir.getReturnValue();
            SkinTextures modified = new SkinTextures(
               original.texture(), original.textureUrl(), cape, original.elytraTexture(), original.model(), original.secure()
            );
            cir.setReturnValue(modified);
         }
      }
   }
}
