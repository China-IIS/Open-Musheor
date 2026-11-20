package musheor.utils.hud.screen;

import java.util.List;
import musheor.utils.hud.CustomHudButton;
import musheor.utils.hud.actions.ButtonAction;
import musheor.utils.hud.actions.ChangeSettingAction;
import musheor.utils.hud.actions.RunCommandAction;
import musheor.utils.hud.actions.SendMessageAction;
import musheor.utils.hud.actions.ToggleModuleAction;
import musheor.utils.system.MusheorSystem;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class EditButtonScreen extends Screen {
   private final Screen fStlZIvY9lS8;
   private final CustomHudButton QfZy2gWnKInxFPr;
   private final List<CustomHudButton> TJImwK2;
   private TextFieldWidget wSJeYsJcmjNZf;
   private EditButtonScreen.ActionType i2Nu6XHKic = EditButtonScreen.ActionType.fStlZIvY9lS8;
   private TextFieldWidget vGAMA89H;
   private TextFieldWidget qhulAUwd;
   private TextFieldWidget BrmKXSYhWhqoB1;

   public EditButtonScreen(Screen parent, CustomHudButton button, List<CustomHudButton> allButtons) {
      super(Text.literal("Edit Button: " + button.wSJeYsJcmjNZf));
      this.fStlZIvY9lS8 = parent;
      this.QfZy2gWnKInxFPr = button;
      this.TJImwK2 = allButtons;
   }

   protected void init() {
      int cx = this.width / 2;
      this.wSJeYsJcmjNZf = new TextFieldWidget(this.textRenderer, cx - 75, 30, 150, 18, Text.literal("Label"));
      this.wSJeYsJcmjNZf.setText(this.QfZy2gWnKInxFPr.wSJeYsJcmjNZf);
      this.wSJeYsJcmjNZf.setMaxLength(32);
      this.addDrawableChild(this.wSJeYsJcmjNZf);
      this.addDrawableChild(ButtonWidget.builder(Text.literal(""), b -> {
         this.QfZy2gWnKInxFPr.fStlZIvY9lS8();
         MusheorSystem.get().save();
      }).position(cx - 75 + 152, 30).size(20, 20).build());

      for (CustomHudButton.ScreenTarget target : CustomHudButton.ScreenTarget.values()) {
         this.addDrawableChild(ButtonWidget.builder(Text.literal(target.name()), b -> {
            this.QfZy2gWnKInxFPr.I3psabmOrOI = target;
            MusheorSystem.get().save();
         }).position(cx - 75 + target.ordinal() * 52, 62).size(50, 16).build());
      }

      this.addDrawableChild(ButtonWidget.builder(Text.literal("−"), b -> {
         this.QfZy2gWnKInxFPr.BrmKXSYhWhqoB1 = Math.max(20, this.QfZy2gWnKInxFPr.BrmKXSYhWhqoB1 - 2);
         MusheorSystem.get().save();
      }).position(cx - 75, 88).size(16, 16).build());
      this.addDrawableChild(ButtonWidget.builder(Text.literal("+"), b -> {
         this.QfZy2gWnKInxFPr.BrmKXSYhWhqoB1 = Math.min(200, this.QfZy2gWnKInxFPr.BrmKXSYhWhqoB1 + 2);
         MusheorSystem.get().save();
      }).position(cx - 75 + 50, 88).size(16, 16).build());
      this.addDrawableChild(ButtonWidget.builder(Text.literal("−"), b -> {
         this.QfZy2gWnKInxFPr.TXWyNb = Math.max(10, this.QfZy2gWnKInxFPr.TXWyNb - 2);
         MusheorSystem.get().save();
      }).position(cx + 10, 88).size(16, 16).build());
      this.addDrawableChild(ButtonWidget.builder(Text.literal("+"), b -> {
         this.QfZy2gWnKInxFPr.TXWyNb = Math.min(40, this.QfZy2gWnKInxFPr.TXWyNb + 2);
         MusheorSystem.get().save();
      }).position(cx + 10 + 50, 88).size(16, 16).build());

      for (EditButtonScreen.ActionType type : EditButtonScreen.ActionType.values()) {
         this.addDrawableChild(ButtonWidget.builder(Text.literal(type.name()), b -> {
            this.i2Nu6XHKic = type;
            this.fStlZIvY9lS8();
         }).position(cx - 150 + type.ordinal() * 78, 116).size(76, 16).build());
      }

      this.fStlZIvY9lS8();
      this.addDrawableChild(
         ButtonWidget.builder(Text.literal("Add Action"), b -> this.QfZy2gWnKInxFPr())
            .position(cx - 80, this.height - 55)
            .size(76, 20)
            .build()
      );
      this.addDrawableChild(ButtonWidget.builder(Text.literal("Save & Back"), b -> {
         if (!this.wSJeYsJcmjNZf.getText().isBlank()) {
            this.QfZy2gWnKInxFPr.wSJeYsJcmjNZf = this.wSJeYsJcmjNZf.getText();
         }

         MusheorSystem.get().save();
         this.client.setScreen(this.fStlZIvY9lS8);
      }).position(cx + 4, this.height - 55).size(76, 20).build());
      this.addDrawableChild(
         ButtonWidget.builder(Text.literal("← Back"), b -> this.client.setScreen(this.fStlZIvY9lS8))
            .position(cx - 25, this.height - 28)
            .size(50, 16)
            .build()
      );
   }

   private void fStlZIvY9lS8() {
      if (this.vGAMA89H != null) {
         this.remove(this.vGAMA89H);
      }

      if (this.qhulAUwd != null) {
         this.remove(this.qhulAUwd);
      }

      if (this.BrmKXSYhWhqoB1 != null) {
         this.remove(this.BrmKXSYhWhqoB1);
      }

      int cx = this.width / 2;
      int fy = 136;
      switch (this.i2Nu6XHKic) {
         case fStlZIvY9lS8:
            this.vGAMA89H = this.fStlZIvY9lS8(cx - 75, fy, "Module name (e.g. KillAura)");
            this.qhulAUwd = null;
            this.BrmKXSYhWhqoB1 = null;
            break;
         case QfZy2gWnKInxFPr:
            this.vGAMA89H = this.fStlZIvY9lS8(cx - 75, fy, "Message text");
            this.qhulAUwd = null;
            this.BrmKXSYhWhqoB1 = null;
            break;
         case TJImwK2:
            this.vGAMA89H = this.fStlZIvY9lS8(cx - 75, fy, "Command (e.g. .tp or /gamemode)");
            this.qhulAUwd = null;
            this.BrmKXSYhWhqoB1 = null;
            break;
         case wSJeYsJcmjNZf:
            this.vGAMA89H = this.fStlZIvY9lS8(cx - 75, fy, "Module name");
            this.qhulAUwd = this.fStlZIvY9lS8(cx - 75, fy + 24, "Setting name");
            this.BrmKXSYhWhqoB1 = this.fStlZIvY9lS8(cx - 75, fy + 48, "Value");
      }

      if (this.vGAMA89H != null) {
         this.addDrawableChild(this.vGAMA89H);
      }

      if (this.qhulAUwd != null) {
         this.addDrawableChild(this.qhulAUwd);
      }

      if (this.BrmKXSYhWhqoB1 != null) {
         this.addDrawableChild(this.BrmKXSYhWhqoB1);
      }
   }

   private TextFieldWidget fStlZIvY9lS8(int x, int y, String placeholder) {
      TextFieldWidget f = new TextFieldWidget(this.textRenderer, x, y, 150, 20, Text.literal(placeholder));
      f.setPlaceholder(Text.literal(placeholder));
      f.setMaxLength(128);
      return f;
   }

   private void QfZy2gWnKInxFPr() {
      if (this.vGAMA89H != null && !this.vGAMA89H.getText().isBlank()) {
         ButtonAction action = (ButtonAction)(switch (this.i2Nu6XHKic) {
            case fStlZIvY9lS8 -> new ToggleModuleAction(this.vGAMA89H.getText().trim());
            case QfZy2gWnKInxFPr -> new SendMessageAction(this.vGAMA89H.getText().trim());
            case TJImwK2 -> new RunCommandAction(this.vGAMA89H.getText().trim());
            case wSJeYsJcmjNZf -> this.qhulAUwd == null || this.BrmKXSYhWhqoB1 == null
               ? null
               : (
                  !this.qhulAUwd.getText().isBlank() && !this.BrmKXSYhWhqoB1.getText().isBlank()
                     ? new ChangeSettingAction(this.vGAMA89H.getText().trim(), this.qhulAUwd.getText().trim(), this.BrmKXSYhWhqoB1.getText().trim())
                     : null
               );
         });
         if (action != null) {
            this.QfZy2gWnKInxFPr.V7zKJ4n6D.add(action);
            this.vGAMA89H.setText("");
            if (this.qhulAUwd != null) {
               this.qhulAUwd.setText("");
            }

            if (this.BrmKXSYhWhqoB1 != null) {
               this.BrmKXSYhWhqoB1.setText("");
            }
         }
      }
   }

   public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
      context.fill(0, 0, this.width, this.height, -1072689136);
   }

   public void render(DrawContext context, int mouseX, int mouseY, float delta) {
      super.render(context, mouseX, mouseY, delta);
      int cx = this.width / 2;
      context.drawCenteredTextWithShadow(this.textRenderer, this.title, cx, 10, -1);
      context.drawTextWithShadow(this.textRenderer, Text.literal("Label:"), cx - 75, 20, -5592406);
      int swatchX = cx - 75 + 152;
      context.fill(swatchX + 2, 32, swatchX + 18, 48, 0xFF000000 | this.QfZy2gWnKInxFPr.QfZy2gWnKInxFPr());
      if (mouseX >= swatchX && mouseX <= swatchX + 20 && mouseY >= 30 && mouseY <= 50) {
         context.drawTextWithShadow(
            this.textRenderer,
            Text.literal(CustomHudButton.QfZy2gWnKInxFPr[this.QfZy2gWnKInxFPr.i2Nu6XHKic]),
            swatchX + 22,
            37,
            0xFF000000 | this.QfZy2gWnKInxFPr.QfZy2gWnKInxFPr()
         );
      }

      context.drawTextWithShadow(this.textRenderer, Text.literal("Show on:"), cx - 75, 52, -5592406);

      for (CustomHudButton.ScreenTarget target : CustomHudButton.ScreenTarget.values()) {
         if (this.QfZy2gWnKInxFPr.I3psabmOrOI == target) {
            int bx = cx - 75 + target.ordinal() * 52;
            context.fill(bx, 62, bx + 50, 78, -2011907099);
         }
      }

      context.drawTextWithShadow(this.textRenderer, Text.literal("Size:"), cx - 75, 80, -5592406);
      context.drawTextWithShadow(this.textRenderer, Text.literal("W:"), cx - 75 + 18, 90, -5592406);
      context.drawCenteredTextWithShadow(this.textRenderer, Text.literal(String.valueOf(this.QfZy2gWnKInxFPr.BrmKXSYhWhqoB1)), cx - 75 + 33, 90, -1);
      context.drawTextWithShadow(this.textRenderer, Text.literal("H:"), cx + 10 + 18, 90, -5592406);
      context.drawCenteredTextWithShadow(this.textRenderer, Text.literal(String.valueOf(this.QfZy2gWnKInxFPr.TXWyNb)), cx + 10 + 33, 90, -1);
      context.drawTextWithShadow(this.textRenderer, Text.literal("Action type:"), cx - 75, 106, -5592406);

      for (EditButtonScreen.ActionType type : EditButtonScreen.ActionType.values()) {
         if (this.i2Nu6XHKic == type) {
            int bx = cx - 150 + type.ordinal() * 78;
            context.fill(bx, 116, bx + 76, 132, -2011907099);
         }
      }

      context.drawTextWithShadow(this.textRenderer, Text.literal("── Current actions ──"), cx - 75, 208, -5592406);
      int ay = 220;

      for (int i = 0; i < this.QfZy2gWnKInxFPr.V7zKJ4n6D.size(); i++) {
         context.drawTextWithShadow(
            this.textRenderer, Text.literal(i + 1 + ". " + this.fStlZIvY9lS8(this.QfZy2gWnKInxFPr.V7zKJ4n6D.get(i))), cx - 75, ay, -1
         );
         int removeX = cx + 80;
         if (mouseX >= removeX && mouseX <= removeX + 30 && mouseY >= ay - 2 && mouseY <= ay + 10) {
            context.fill(removeX, ay - 2, removeX + 30, ay + 10, -1996541133);
         }

         context.drawTextWithShadow(this.textRenderer, Text.literal("[x]"), removeX, ay, -48060);
         ay += 14;
      }

      if (this.QfZy2gWnKInxFPr.V7zKJ4n6D.isEmpty()) {
         context.drawTextWithShadow(this.textRenderer, Text.literal("No actions yet."), cx - 75, ay, -7829368);
      }
   }

   public boolean mouseClicked(Click click, boolean doubled) {
      int cx = this.width / 2;
      int ay = 220;
      double mouseX = click.comp_4798();
      double mouseY = click.comp_4799();

      for (int i = 0; i < this.QfZy2gWnKInxFPr.V7zKJ4n6D.size(); i++) {
         int removeX = cx + 80;
         if (mouseX >= removeX && mouseX <= removeX + 30 && mouseY >= ay - 2 && mouseY <= ay + 10) {
            this.QfZy2gWnKInxFPr.V7zKJ4n6D.remove(i);
            return true;
         }

         ay += 14;
      }

      return super.mouseClicked(click, doubled);
   }

   private String fStlZIvY9lS8(ButtonAction action) {
      return switch (action) {
         case ToggleModuleAction a -> "Toggle: " + a.fStlZIvY9lS8;
         case SendMessageAction ax -> "Message: " + this.fStlZIvY9lS8(ax.fStlZIvY9lS8, 24);
         case RunCommandAction axx -> "Command: " + this.fStlZIvY9lS8(axx.fStlZIvY9lS8, 24);
         case ChangeSettingAction axxx -> "Setting: " + axxx.fStlZIvY9lS8 + "." + axxx.QfZy2gWnKInxFPr + "=" + axxx.TJImwK2;
         default -> action.fStlZIvY9lS8();
      };
   }

   private String fStlZIvY9lS8(String s, int max) {
      return s.length() > max ? s.substring(0, max) + "…" : s;
   }

   public boolean shouldPause() {
      return false;
   }

   private static enum ActionType {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr,
      TJImwK2,
      wSJeYsJcmjNZf;
   }
}
