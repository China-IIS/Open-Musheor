package musheor.utils.hud;

import java.util.function.Supplier;
import meteordevelopment.meteorclient.settings.Setting;
import musheor.compat.VersionHelper;
import net.minecraft.client.font.DrawnTextConsumer;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext.HoverType;
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

   public boolean mouseClicked(Click click, boolean doubled) {
      if (VersionHelper.get().hasControlDown() && this.isMouseOver(click.comp_4798(), click.comp_4799())) {
         this.fStlZIvY9lS8 = true;
         this.QfZy2gWnKInxFPr = (int)click.comp_4798() - this.getX();
         this.TJImwK2 = (int)click.comp_4799() - this.getY();
         return true;
      } else {
         return super.mouseClicked(click, doubled);
      }
   }

   public boolean mouseDragged(Click click, double deltaX, double deltaY) {
      if (this.fStlZIvY9lS8 && VersionHelper.get().hasControlDown()) {
         this.setX((int)click.comp_4798() - this.QfZy2gWnKInxFPr);
         this.setY((int)click.comp_4799() - this.TJImwK2);
         return true;
      } else {
         if (this.fStlZIvY9lS8) {
            this.fStlZIvY9lS8 = false;
         }

         return super.mouseDragged(click, deltaX, deltaY);
      }
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

   protected void drawLabel(DrawnTextConsumer consumer) {
      Text text;
      if (VersionHelper.get().hasControlDown()) {
         text = Text.literal("✥");
      } else {
         Text label = this.i2Nu6XHKic != null ? this.i2Nu6XHKic.get() : this.wSJeYsJcmjNZf;
         text = Text.literal(label.getString())
            .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF000000 | this.vGAMA89H & 16777215)));
      }

      this.drawTextWithMargin(consumer, text, 2);
   }

   protected void drawIcon(DrawContext context, int mouseX, int mouseY, float delta) {
      this.drawButton(context);
      this.drawLabel(context.getHoverListener(this, DrawContext.HoverType.NONE));
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
