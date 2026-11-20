package musheor.utils.hud.actions;

import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import musheor.compat.VersionHelper;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;

public class ToggleModuleAction implements ButtonAction {
   public String fStlZIvY9lS8;

   public ToggleModuleAction(String moduleName) {
      this.fStlZIvY9lS8 = moduleName;
   }

   @Override
   public void fStlZIvY9lS8(ScreenHandler handler) {
      Module mod = Modules.get().get(this.fStlZIvY9lS8);
      if (mod != null) {
         mod.toggle();
      }
   }

   @Override
   public String fStlZIvY9lS8() {
      return "toggle_module";
   }

   @Override
   public NbtCompound QfZy2gWnKInxFPr() {
      NbtCompound tag = new NbtCompound();
      tag.putString("type", this.fStlZIvY9lS8());
      tag.putString("module", this.fStlZIvY9lS8);
      return tag;
   }

   public static ToggleModuleAction QfZy2gWnKInxFPr(NbtCompound tag) {
      return new ToggleModuleAction(VersionHelper.get().getString(tag, "module"));
   }
}
