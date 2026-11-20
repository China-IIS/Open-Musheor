package musheor.utils.hud;

import java.util.function.Supplier;
import meteordevelopment.meteorclient.settings.Setting;
import musheor.compat.VersionHelper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget.PressAction;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

public class DraggableButtonWidget extends ButtonWidget {
   private boolean fStlZIvY9lS8 = false;
   private int QfZy2gWnKInxFPr;
   private int TJImwK2;
   private final Text wSJeYsJcmjNZf;
   private final Supplier<Text> i2Nu6XHKic;
   private final int vGAMA89H;
   private final DraggableButtonWidget.PositionSaveCallback qhulAUwd;

   public DraggableButtonWidget(int x, int y, int width, int height, Text label, ButtonWidget.PressAction action, Setting<Integer> settingX, Setting<Integer> settingY) {
      super(x, y, width, height, Text.empty(), action, DEFAULT_NARRATION_SUPPLIER);
      this.wSJeYsJcmjNZf = label;
      this.i2Nu6XHKic = null;
      this.vGAMA89H = 16777215;
      this.qhulAUwd = (nx, ny) -> {
         settingX.set(nx);
         settingY.set(ny);
      };
   }

   public DraggableButtonWidget(
      int x, int y, int width, int height, Text label, int textColor, ButtonWidget.PressAction action, DraggableButtonWidget.PositionSaveCallback onPositionSaved
   ) {
      super(x, y, width, height, Text.empty(), action, DEFAULT_NARRATION_SUPPLIER);
      this.wSJeYsJcmjNZf = label;
      this.i2Nu6XHKic = null;
      this.vGAMA89H = textColor;
      this.qhulAUwd = onPositionSaved;
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (VersionHelper.get().hasControlDown() && this.isHovered()) {
         this.fStlZIvY9lS8 = true;
         this.QfZy2gWnKInxFPr = (int)mouseX - this.getX();
         this.TJImwK2 = (int)mouseY - this.getY();
         return true;
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      if (this.fStlZIvY9lS8 && VersionHelper.get().hasControlDown()) {
         this.setX((int)mouseX - this.QfZy2gWnKInxFPr);
         this.setY((int)mouseY - this.TJImwK2);
         return true;
      } else {
         if (this.fStlZIvY9lS8) {
            this.fStlZIvY9lS8 = false;
         }

         return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
      }
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      if (this.fStlZIvY9lS8) {
         this.fStlZIvY9lS8 = false;
         this.qhulAUwd.save(this.getX(), this.getY());
         return true;
      } else {
         return super.mouseReleased(mouseX, mouseY, button);
      }
   }

   public Text getMessage() {
      if (VersionHelper.get().hasControlDown()) {
         return Text.literal("✥");
      } else {
         Text label = this.i2Nu6XHKic != null ? this.i2Nu6XHKic.get() : this.wSJeYsJcmjNZf;
         return Text.literal(label.getString()).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(this.vGAMA89H & 16777215)));
      }
   }

   public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
      super.renderWidget(context, mouseX, mouseY, delta);
      if (VersionHelper.get().hasControlDown()) {
         context.fill(
            this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), -2011907099
         );
      }
   }

   public interface PositionSaveCallback {
      void save(int var1, int var2);
   }
}
