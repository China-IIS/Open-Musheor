package musheor.utils.hud.actions;

import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import musheor.compat.VersionHelper;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;

public class ChangeSettingAction implements ButtonAction {
   public String fStlZIvY9lS8;
   public String QfZy2gWnKInxFPr;
   public String TJImwK2;

   public ChangeSettingAction(String moduleName, String settingName, String value) {
      this.fStlZIvY9lS8 = moduleName;
      this.QfZy2gWnKInxFPr = settingName;
      this.TJImwK2 = value;
   }

   @Override
   public void fStlZIvY9lS8(ScreenHandler handler) {
      Module mod = Modules.get().get(this.fStlZIvY9lS8);
      if (mod != null) {
         mod.settings.forEach(group -> group.forEach(setting -> {
            if (setting.name.equalsIgnoreCase(this.QfZy2gWnKInxFPr)) {
               setting.parse(this.TJImwK2);
            }
         }));
      }
   }

   @Override
   public String fStlZIvY9lS8() {
      return "change_setting";
   }

   @Override
   public NbtCompound QfZy2gWnKInxFPr() {
      NbtCompound tag = new NbtCompound();
      tag.putString("type", this.fStlZIvY9lS8());
      tag.putString("module", this.fStlZIvY9lS8);
      tag.putString("setting", this.QfZy2gWnKInxFPr);
      tag.putString("value", this.TJImwK2);
      return tag;
   }

   public static ChangeSettingAction QfZy2gWnKInxFPr(NbtCompound tag) {
      return new ChangeSettingAction(
         VersionHelper.get().getString(tag, "module"), VersionHelper.get().getString(tag, "setting"), VersionHelper.get().getString(tag, "value")
      );
   }
}
