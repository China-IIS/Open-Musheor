package musheor.utils.hud.actions;

import java.util.List;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;

public class CompositeAction implements ButtonAction {
   private final List<ButtonAction> fStlZIvY9lS8;

   public CompositeAction(List<ButtonAction> actions) {
      this.fStlZIvY9lS8 = actions;
   }

   @Override
   public void fStlZIvY9lS8(ScreenHandler handler) {
      for (ButtonAction action : this.fStlZIvY9lS8) {
         action.fStlZIvY9lS8(handler);
      }
   }

   @Override
   public String fStlZIvY9lS8() {
      return "composite";
   }

   @Override
   public NbtCompound QfZy2gWnKInxFPr() {
      return new NbtCompound();
   }
}
