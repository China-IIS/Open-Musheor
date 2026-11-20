package musheor.utils.hud.screen;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.screen.Screen;

public class EchestConfigScreen extends EchestConfigScreenBase {
   public EchestConfigScreen(Screen parent) {
      super(parent);
   }

   public boolean mouseClicked(Click click, boolean doubled) {
      return this.fStlZIvY9lS8(click.comp_4798(), click.comp_4799(), click.button()) ? true : super.mouseClicked(click, doubled);
   }

   public boolean mouseDragged(Click click, double deltaX, double deltaY) {
      return this.fStlZIvY9lS8(click.comp_4798(), click.comp_4799(), click.button()) ? true : super.mouseDragged(click, deltaX, deltaY);
   }
}
