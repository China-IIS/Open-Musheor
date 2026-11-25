package musheor.utils.hud;

import java.util.function.Supplier;
import meteordevelopment.meteorclient.settings.Setting;
import musheor.compat.VersionHelper;
import net.minecraft.client.font.DrawnTextConsumer;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.DrawContext.HoverType;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget.PressAction;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;

public class DraggableButtonWidget extends ButtonWidget {
   private boolean fStlZIvY9lS8 = false;
   private int QfZy2gWnKInxFPr;
   private int TJImwK2;
   private final net.minecraft.text.Text wSJeYsJcmjNZf;
   private final Supplier<net.minecraft.text.Text> i2Nu6XHKic;
   private final int vGAMA89H;
   private final DraggableButtonWidget.PositionSaveCallback qhulAUwd;

   public DraggableButtonWidget(int x, int y, int width, int height, net.minecraft.text.Text label, PressAction action, Setting<Integer> settingX, Setting<Integer> settingY) {
      super(x, y, width, height, net.minecraft.text.Text.empty(), action, DEFAULT_NARRATION_SUPPLIER);
      this.wSJeYsJcmjNZf = label;
      this.i2Nu6XHKic = null;
      this.vGAMA89H = 16777215;
      this.qhulAUwd = (nx, ny) -> {
         settingX.set(nx);
         settingY.set(ny);
      };
   }

   public DraggableButtonWidget(
      int x, int y, int width, int height, net.minecraft.text.Text label, int textColor, PressAction action, DraggableButtonWidget.PositionSaveCallback onPositionSaved
   ) {
      super(x, y, width, height, net.minecraft.text.Text.empty(), action, DEFAULT_NARRATION_SUPPLIER);
      this.wSJeYsJcmjNZf = label;
      this.i2Nu6XHKic = null;
      this.vGAMA89H = textColor;
      this.qhulAUwd = onPositionSaved;
   }

   public boolean mouseClicked(Click click, boolean doubled) {
      if (VersionHelper.get().hasControlDown() && this.isMouseOver(click.x(), click.y())) {
         this.fStlZIvY9lS8 = true;
         this.QfZy2gWnKInxFPr = (int)click.x() - this.getX();
         this.TJImwK2 = (int)click.y() - this.getY();
         return true;
      } else {
         return super.mouseClicked(click, doubled);
      }
   }

   public boolean mouseDragged(Click click, double offsetX, double offsetY) {
      if (this.fStlZIvY9lS8 && VersionHelper.get().hasControlDown()) {
         this.setX((int)click.x() - this.QfZy2gWnKInxFPr);
         this.setY((int)click.y() - this.TJImwK2);
         return true;
      }

      if (this.fStlZIvY9lS8) {
         this.fStlZIvY9lS8 = false;
      }

      return super.mouseDragged(click, offsetX, offsetY);
   }

   public boolean mouseReleased(Click click) {
      if (this.fStlZIvY9lS8) {
         this.fStlZIvY9lS8 = false;
         this.qhulAUwd.save(this.getX(), this.getY());
         return true;
      } else {
         return super.mouseReleased(click);
      }
   }

   protected void drawLabel(DrawnTextConsumer drawer) {
      net.minecraft.text.Text text;
      if (VersionHelper.get().hasControlDown()) {
         text = net.minecraft.text.Text.literal("✥");
      } else {
         net.minecraft.text.Text label = this.i2Nu6XHKic != null ? this.i2Nu6XHKic.get() : this.wSJeYsJcmjNZf;
         text = net.minecraft.text.Text.literal(label.getString()).setStyle(Style.EMPTY.withColor(net.minecraft.text.TextColor.fromRgb(0xFF000000 | this.vGAMA89H & 16777215)));
      }

      this.drawTextWithMargin(drawer, text, 2);
   }

   protected void drawIcon(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
      this.drawButton(context);
      this.drawLabel(context.getHoverListener(this, HoverType.NONE));
      if (VersionHelper.get().hasControlDown()) {
         context.fill(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), -2011907099);
      }
   }

   public interface PositionSaveCallback {
      void save(int var1, int var2);
   }
}
