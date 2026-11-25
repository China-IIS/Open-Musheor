package musheor.mixin;

import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.misc.Keybind;
import musheor.modules.tech.ContainerTweaks;
import musheor.utils.hud.CustomHudButton;
import musheor.utils.hud.DraggableButtonWidget;
import musheor.utils.system.MusheorSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import obf.jU8xXhmkg;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public abstract class ScreenHandlerMixin<T extends ScreenHandler> extends Screen implements ScreenHandlerProvider<T> {
   @Shadow
   protected T handler;
   @Shadow
   protected int x;
   @Shadow
   protected int y;

   @Shadow
   public abstract T getScreenHandler();

   public ScreenHandlerMixin(Text title) {
      super(title);
   }

   @Inject(method = "init", at = @At("TAIL"))
   private void onInit(CallbackInfo info) {
      ContainerTweaks ct = (ContainerTweaks)Modules.get().get(ContainerTweaks.class);
      if (ct.isActive() && (Boolean)ct.QfZy2gWnKInxFPr.get()) {
         int stealX = ct.vGAMA89H.get() != 0 ? (Integer)ct.vGAMA89H.get() : this.x;
         int stealY = ct.qhulAUwd.get() != 0 ? (Integer)ct.qhulAUwd.get() : this.y - 22;
         int dumpX = ct.BrmKXSYhWhqoB1.get() != 0 ? (Integer)ct.BrmKXSYhWhqoB1.get() : this.x + 42;
         int dumpY = ct.TXWyNb.get() != 0 ? (Integer)ct.TXWyNb.get() : this.y - 22;
         int bundleX = ct.I3psabmOrOI.get() != 0 ? (Integer)ct.I3psabmOrOI.get() : this.x + 84;
         int bundleY = ct.duJ6sPeetAcBh6P.get() != 0 ? (Integer)ct.duJ6sPeetAcBh6P.get() : this.y - 22;
         int unbundleX = ct.j376sIQ.get() != 0 ? (Integer)ct.j376sIQ.get() : this.x + 126;
         int unbundleY = ct.V7zKJ4n6D.get() != 0 ? (Integer)ct.V7zKJ4n6D.get() : this.y - 22;
         this.addDrawableChild(
            new DraggableButtonWidget(
               stealX, stealY, 40, 20, Text.literal("Steal"), button -> ct.fStlZIvY9lS8(this.getScreenHandler()), ct.vGAMA89H, ct.qhulAUwd
            )
         );
         this.addDrawableChild(
            new DraggableButtonWidget(
               dumpX, dumpY, 40, 20, Text.literal("Dump"), button -> ct.QfZy2gWnKInxFPr(this.getScreenHandler()), ct.BrmKXSYhWhqoB1, ct.TXWyNb
            )
         );
         this.addDrawableChild(
            new DraggableButtonWidget(
               bundleX, bundleY, 50, 20, Text.literal("Bundle"), button -> ct.TJImwK2(this.getScreenHandler()), ct.I3psabmOrOI, ct.duJ6sPeetAcBh6P
            )
         );
         this.addDrawableChild(
            new DraggableButtonWidget(
               unbundleX, unbundleY, 60, 20, Text.literal("Unbundle"), button -> ct.wSJeYsJcmjNZf(this.getScreenHandler()), ct.j376sIQ, ct.V7zKJ4n6D
            )
         );
      }

      for (CustomHudButton btn : MusheorSystem.get().customButtons) {
         if (btn.I3psabmOrOI.fStlZIvY9lS8(CustomHudButton.ScreenTarget.Inventory)) {
            this.addDrawableChild(btn.fStlZIvY9lS8(this.getScreenHandler()));
         }
      }
   }

   @Inject(method = "onMouseClick", at = @At("HEAD"), cancellable = true)
   private void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
      if (ContainerTweaks.fStlZIvY9lS8.isActive() && (Boolean)MusheorSystem.Manager.noPacketKick.get() && !jU8xXhmkg.QfZy2gWnKInxFPr()) {
         ci.cancel();
      } else if (ContainerTweaks.fStlZIvY9lS8 != null && ContainerTweaks.fStlZIvY9lS8.isActive()) {
         if (slot != null && slot.hasStack()) {
            if (((Keybind)ContainerTweaks.fStlZIvY9lS8.wSJeYsJcmjNZf.get()).isPressed()) {
               ContainerTweaks.fStlZIvY9lS8.QfZy2gWnKInxFPr(this.handler, slot);
               ci.cancel();
            }

            if (((Keybind)ContainerTweaks.fStlZIvY9lS8.TJImwK2.get()).isPressed()) {
               ContainerTweaks.fStlZIvY9lS8.fStlZIvY9lS8(this.handler, slot);
               ci.cancel();
            }
         }
      }
   }
}
