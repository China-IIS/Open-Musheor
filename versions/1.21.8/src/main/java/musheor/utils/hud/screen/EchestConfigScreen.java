package musheor.utils.hud.screen;

import net.minecraft.client.gui.screen.Screen;

public class EchestConfigScreen extends EchestConfigScreenBase {
   public EchestConfigScreen(Screen parent) {
      super(parent);
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      return this.fStlZIvY9lS8(mouseX, mouseY, button) ? true : super.mouseClicked(mouseX, mouseY, button);
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      return this.fStlZIvY9lS8(mouseX, mouseY, button) ? true : super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
   }
}
