package musheor.utils.hud.actions;

import musheor.compat.VersionHelper;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;

public interface ButtonAction {
   void fStlZIvY9lS8(ScreenHandler var1);

   String fStlZIvY9lS8();

   NbtCompound QfZy2gWnKInxFPr();

   static ButtonAction fStlZIvY9lS8(NbtCompound tag) {
      if (!tag.contains("type")) {
         return null;
      } else {
         String var1 = VersionHelper.get().getString(tag, "type");

         return (ButtonAction)(switch (var1) {
            case "toggle_module" -> ToggleModuleAction.QfZy2gWnKInxFPr(tag);
            case "send_message" -> SendMessageAction.QfZy2gWnKInxFPr(tag);
            case "run_command" -> RunCommandAction.QfZy2gWnKInxFPr(tag);
            case "change_setting" -> ChangeSettingAction.QfZy2gWnKInxFPr(tag);
            default -> null;
         });
      }
   }
}
