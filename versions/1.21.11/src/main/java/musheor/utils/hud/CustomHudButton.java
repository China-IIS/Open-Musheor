package musheor.utils.hud;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import musheor.compat.VersionHelper;
import musheor.utils.hud.actions.ButtonAction;
import musheor.utils.hud.actions.CompositeAction;
import musheor.utils.system.MusheorSystem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

public class CustomHudButton {
   public static final int[] fStlZIvY9lS8 = new int[]{
      16777215, 16777045, 5636095, 5635925, 16733695, 16733525, 11184810, 5592405, 5592575, 43520, 43690, 11141120, 11141290, 16755200, 170, 0
   };
   public static final String[] QfZy2gWnKInxFPr = new String[]{
      "White",
      "Yellow",
      "Aqua",
      "Green",
      "Light Purple",
      "Red",
      "Gray",
      "Dark Gray",
      "Blue",
      "Dark Green",
      "Dark Aqua",
      "Dark Red",
      "Dark Purple",
      "Gold",
      "Dark Blue",
      "Black"
   };
   public String TJImwK2;
   public String wSJeYsJcmjNZf;
   public int i2Nu6XHKic = 0;
   public int vGAMA89H;
   public int qhulAUwd;
   public int BrmKXSYhWhqoB1 = 40;
   public int TXWyNb = 20;
   public CustomHudButton.ScreenTarget I3psabmOrOI = CustomHudButton.ScreenTarget.fStlZIvY9lS8;
   public int duJ6sPeetAcBh6P = Integer.MIN_VALUE;
   public int j376sIQ = Integer.MIN_VALUE;
   public final List<ButtonAction> V7zKJ4n6D = new ArrayList<>();

   public CustomHudButton(String id, String label, int defaultX, int defaultY) {
      this.TJImwK2 = id;
      this.wSJeYsJcmjNZf = label;
      this.vGAMA89H = defaultX;
      this.qhulAUwd = defaultY;
   }

   public static CustomHudButton fStlZIvY9lS8(String label, int defaultX, int defaultY) {
      return new CustomHudButton(UUID.randomUUID().toString(), label, defaultX, defaultY);
   }

   public void fStlZIvY9lS8() {
      this.i2Nu6XHKic = (this.i2Nu6XHKic + 1) % fStlZIvY9lS8.length;
   }

   public int QfZy2gWnKInxFPr() {
      return fStlZIvY9lS8[this.i2Nu6XHKic];
   }

   public int TJImwK2() {
      return this.duJ6sPeetAcBh6P != Integer.MIN_VALUE ? this.duJ6sPeetAcBh6P : this.vGAMA89H;
   }

   public int wSJeYsJcmjNZf() {
      return this.j376sIQ != Integer.MIN_VALUE ? this.j376sIQ : this.qhulAUwd;
   }

   public DraggableButtonWidget fStlZIvY9lS8(ScreenHandler handler) {
      CompositeAction composite = new CompositeAction(this.V7zKJ4n6D);
      return new DraggableButtonWidget(
         this.TJImwK2(),
         this.wSJeYsJcmjNZf(),
         this.BrmKXSYhWhqoB1,
         this.TXWyNb,
         Text.literal(this.wSJeYsJcmjNZf),
         this.QfZy2gWnKInxFPr(),
         btn -> composite.fStlZIvY9lS8(handler),
         (newX, newY) -> {
            this.duJ6sPeetAcBh6P = newX;
            this.j376sIQ = newY;
            MusheorSystem.get().save();
         }
      );
   }

   public NbtCompound i2Nu6XHKic() {
      NbtCompound tag = new NbtCompound();
      tag.putString("id", this.TJImwK2);
      tag.putString("label", this.wSJeYsJcmjNZf);
      tag.putInt("textColorIndex", this.i2Nu6XHKic);
      tag.putInt("defaultX", this.vGAMA89H);
      tag.putInt("defaultY", this.qhulAUwd);
      tag.putInt("width", this.BrmKXSYhWhqoB1);
      tag.putInt("height", this.TXWyNb);
      tag.putInt("savedX", this.duJ6sPeetAcBh6P);
      tag.putInt("savedY", this.j376sIQ);
      tag.putString("screenTarget", this.I3psabmOrOI.name());
      NbtList actionList = new NbtList();

      for (ButtonAction action : this.V7zKJ4n6D) {
         actionList.add(action.QfZy2gWnKInxFPr());
      }

      tag.put("actions", actionList);
      return tag;
   }

   public static CustomHudButton fStlZIvY9lS8(NbtCompound tag) {
      CustomHudButton btn = new CustomHudButton(
         VersionHelper.get().getString(tag, "id"),
         VersionHelper.get().getString(tag, "label"),
         VersionHelper.get().getInt(tag, "defaultX", 0),
         VersionHelper.get().getInt(tag, "defaultY", 0)
      );
      if (tag.contains("textColorIndex")) {
         btn.i2Nu6XHKic = VersionHelper.get().getInt(tag, "textColorIndex", 0);
      }

      if (tag.contains("width")) {
         btn.BrmKXSYhWhqoB1 = VersionHelper.get().getInt(tag, "width", 40);
      }

      if (tag.contains("height")) {
         btn.TXWyNb = VersionHelper.get().getInt(tag, "height", 20);
      }

      if (tag.contains("savedX")) {
         btn.duJ6sPeetAcBh6P = VersionHelper.get().getInt(tag, "savedX", Integer.MIN_VALUE);
      }

      if (tag.contains("savedY")) {
         btn.j376sIQ = VersionHelper.get().getInt(tag, "savedY", Integer.MIN_VALUE);
      }

      if (tag.contains("screenTarget")) {
         try {
            btn.I3psabmOrOI = CustomHudButton.ScreenTarget.valueOf(VersionHelper.get().getString(tag, "screenTarget"));
         } catch (IllegalArgumentException var6) {
            btn.I3psabmOrOI = CustomHudButton.ScreenTarget.fStlZIvY9lS8;
         }
      }

      for (NbtElement el : VersionHelper.get().getList(tag, "actions", 10)) {
         ButtonAction action = ButtonAction.fStlZIvY9lS8((NbtCompound)el);
         if (action != null) {
            btn.V7zKJ4n6D.add(action);
         }
      }

      return btn;
   }

   public static enum ScreenTarget {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr,
      TJImwK2;

      public boolean fStlZIvY9lS8(CustomHudButton.ScreenTarget context) {
         return this == TJImwK2 || this == context;
      }
   }
}
