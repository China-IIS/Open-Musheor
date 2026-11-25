package musheor.utils.hud.screen;

import java.util.List;
import musheor.utils.hud.CustomHudButton;
import musheor.utils.system.MusheorSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CustomButtonManagerScreen extends Screen {
   private final Screen fStlZIvY9lS8;
   private List<CustomHudButton> QfZy2gWnKInxFPr;

   public CustomButtonManagerScreen(Screen parent) {
      super(Text.literal("Custom HUD Buttons"));
      this.fStlZIvY9lS8 = parent;
   }

   protected void init() {
      this.QfZy2gWnKInxFPr = MusheorSystem.get().customButtons;
      this.fStlZIvY9lS8();
   }

   private void fStlZIvY9lS8() {
      this.clearChildren();
      int cx = this.width / 2;
      int y = 40;

      for (CustomHudButton btn : this.QfZy2gWnKInxFPr) {
         CustomHudButton b = btn;
         this.addDrawableChild(
            ButtonWidget.builder(Text.literal("✎ Edit"), button -> this.client.setScreen(new EditButtonScreen(this, b, this.QfZy2gWnKInxFPr)))
               .position(cx + 188, y)
               .size(50, 20)
               .build()
         );
         this.addDrawableChild(ButtonWidget.builder(Text.literal("✗ Delete"), button -> {
            this.QfZy2gWnKInxFPr.remove(b);
            MusheorSystem.get().save();
            this.init();
         }).position(cx + 240, y).size(50, 20).build());
         y += 26;
      }

      this.addDrawableChild(ButtonWidget.builder(Text.literal("+ New Button"), bx -> {
         CustomHudButton newBtn = CustomHudButton.fStlZIvY9lS8("New", this.width / 2 - 20, 40);
         this.QfZy2gWnKInxFPr.add(newBtn);
         MusheorSystem.get().save();
         this.client.setScreen(new EditButtonScreen(this, newBtn, this.QfZy2gWnKInxFPr));
      }).position(cx - 50, this.height - 48).size(100, 20).build());
      this.addDrawableChild(
         ButtonWidget.builder(Text.literal("← Back"), bx -> this.client.setScreen(this.fStlZIvY9lS8)).position(cx - 25, this.height - 24).size(50, 16).build()
      );
   }

   public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
      context.fill(0, 0, this.width, this.height, -1072689136);
   }

   public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
      super.render(context, mouseX, mouseY, deltaTicks);
      context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 14, -1);
      context.drawTextWithShadow(this.textRenderer, Text.literal("Label"), this.width / 2 - 150, 28, -5592406);
      context.drawTextWithShadow(this.textRenderer, Text.literal("Actions"), this.width / 2 + 50, 28, -5592406);
      int y = 40;

      for (CustomHudButton btn : this.QfZy2gWnKInxFPr) {
         context.drawTextWithShadow(this.textRenderer, Text.literal(btn.wSJeYsJcmjNZf), this.width / 2 - 150, y + 5, -1);
         context.drawTextWithShadow(this.textRenderer, Text.literal(btn.V7zKJ4n6D.size() + " action(s)"), this.width / 2 + 50, y + 5, -7798904);
         y += 26;
      }

      if (this.QfZy2gWnKInxFPr.isEmpty()) {
         context.drawCenteredTextWithShadow(
            this.textRenderer, Text.literal("No buttons yet. Press '+ New Button' to create one."), this.width / 2, 50, -7829368
         );
      }
   }

   public boolean shouldPause() {
      return false;
   }
}
