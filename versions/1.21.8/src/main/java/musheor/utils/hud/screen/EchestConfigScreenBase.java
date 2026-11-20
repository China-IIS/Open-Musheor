package musheor.utils.hud.screen;

import meteordevelopment.meteorclient.utils.player.EChestMemory;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import obf.gVOhtBej1x;

public abstract class EchestConfigScreenBase extends Screen {
   private final Screen fStlZIvY9lS8;
   private int QfZy2gWnKInxFPr;
   private int TJImwK2;

   protected EchestConfigScreenBase(Screen parent) {
      super(Text.literal("E-Chest Restock Config"));
      this.fStlZIvY9lS8 = parent;
   }

   protected void init() {
      int gridW = 162;
      int gridH = 54;
      this.QfZy2gWnKInxFPr = (this.width - gridW) / 2;
      this.TJImwK2 = (this.height - gridH) / 2;
   }

   private DefaultedList<ItemStack> fStlZIvY9lS8() {
      return EChestMemory.isKnown() ? EChestMemory.ITEMS : null;
   }

   private int fStlZIvY9lS8(double mouseX, double mouseY) {
      int relX = (int)(mouseX - this.QfZy2gWnKInxFPr);
      int relY = (int)(mouseY - this.TJImwK2);
      if (relX >= 0 && relY >= 0) {
         int col = relX / 18;
         int row = relY / 18;
         return col < 9 && row < 3 ? row * 9 + col : -1;
      } else {
         return -1;
      }
   }

   protected boolean fStlZIvY9lS8(double mouseX, double mouseY, int button) {
      int slot = this.fStlZIvY9lS8(mouseX, mouseY);
      if (slot == -1) {
         return false;
      } else {
         if (button == 0) {
            gVOhtBej1x.TJImwK2(slot);
         } else if (button == 1) {
            gVOhtBej1x.wSJeYsJcmjNZf(slot);
         }

         return true;
      }
   }

   public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
      context.fill(0, 0, this.width, this.height, -1072689136);
   }

   public void render(DrawContext context, int mouseX, int mouseY, float delta) {
      super.render(context, mouseX, mouseY, delta);
      int gridW = 162;
      int gridH = 54;
      context.fill(this.QfZy2gWnKInxFPr - 8, this.TJImwK2 - 8, this.QfZy2gWnKInxFPr + gridW + 8, this.TJImwK2 + gridH + 8, -266856424);
      context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.TJImwK2 - 8 - 28, -1);
      context.drawCenteredTextWithShadow(
         this.textRenderer,
         Text.literal("Left-click = whitelist (green)   |   Right-click = blacklist (red)"),
         this.width / 2,
         this.TJImwK2 - 8 - 14,
         -5592406
      );
      DefaultedList<ItemStack> items = this.fStlZIvY9lS8();
      if (items == null) {
         context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("unknown inventory"), this.width / 2, this.TJImwK2 + gridH + 8 + 8, -7829368);
      }

      int hovered = this.fStlZIvY9lS8(mouseX, mouseY);
      ItemStack hoveredStack = null;

      for (int slot = 0; slot < 27; slot++) {
         int col = slot % 9;
         int row = slot / 9;
         int x = this.QfZy2gWnKInxFPr + col * 18;
         int y = this.TJImwK2 + row * 18;
         context.fill(x, y, x + 18, y + 18, -13948117);
         int bg = gVOhtBej1x.QfZy2gWnKInxFPr(slot) ? -1228219 : -12868259;
         context.fill(x + 1, y + 1, x + 18 - 1, y + 18 - 1, bg);
         if (items != null) {
            ItemStack stack = (ItemStack)items.get(slot);
            if (!stack.isEmpty()) {
               context.drawItem(stack, x + 1, y + 1);
               context.drawStackOverlay(this.textRenderer, stack, x + 1, y + 1);
               if (slot == hovered) {
                  hoveredStack = stack;
               }
            }
         }

         if (slot == hovered) {
            context.fill(x + 1, y + 1, x + 18 - 1, y + 18 - 1, 1090519039);
         }
      }

      if (hoveredStack != null && !hoveredStack.isEmpty()) {
         context.drawItemTooltip(this.textRenderer, hoveredStack, mouseX, mouseY);
      }
   }

   public void close() {
      gVOhtBej1x.QfZy2gWnKInxFPr();
      if (this.client != null) {
         this.client.setScreen(this.fStlZIvY9lS8);
      } else {
         super.close();
      }
   }

   public boolean shouldPause() {
      return false;
   }
}
