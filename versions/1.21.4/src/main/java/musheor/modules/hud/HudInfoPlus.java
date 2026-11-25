package musheor.modules.hud;

import java.util.ArrayDeque;
import java.util.Deque;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.DoubleSetting.Builder;
import meteordevelopment.meteorclient.systems.hud.HudElement;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.HudRenderer;
import meteordevelopment.meteorclient.utils.render.color.Color;
import musheor.utils.system.MusheorSystem;

public class HudInfoPlus extends HudElement {
   public static final HudElementInfo<HudInfoPlus> INFO = new HudElementInfo(
      musheor.musheor.MUSHEOR_HUD, "packet-limits", "A hud module that displays how many packets you are sending to the server", HudInfoPlus::new
   );
   private final Setting<Double> fStlZIvY9lS8 = this.settings.getDefaultGroup().add(((Builder)new Builder().name("scale")).defaultValue(1.0).build());
   private static final Deque<Long> QfZy2gWnKInxFPr = new ArrayDeque<>();
   private static final Deque<Long> TJImwK2 = new ArrayDeque<>();
   private static final Deque<Long> wSJeYsJcmjNZf = new ArrayDeque<>();

   public HudInfoPlus() {
      super(INFO);
   }

   public static void fStlZIvY9lS8() {
      synchronized (QfZy2gWnKInxFPr) {
         QfZy2gWnKInxFPr.addLast(System.currentTimeMillis());
      }
   }

   public static void QfZy2gWnKInxFPr() {
      synchronized (TJImwK2) {
         TJImwK2.addLast(System.currentTimeMillis());
      }
   }

   public static void TJImwK2() {
      synchronized (wSJeYsJcmjNZf) {
         wSJeYsJcmjNZf.addLast(System.currentTimeMillis());
      }
   }

   public static void wSJeYsJcmjNZf() {
      long now = System.currentTimeMillis();
      Long first;
      synchronized (QfZy2gWnKInxFPr) {
         while ((first = QfZy2gWnKInxFPr.peekFirst()) != null && now - first > 4000L) {
            QfZy2gWnKInxFPr.pollFirst();
         }
      }

      synchronized (TJImwK2) {
         while ((first = TJImwK2.peekFirst()) != null && now - first > 4000L) {
            TJImwK2.pollFirst();
         }
      }

      synchronized (wSJeYsJcmjNZf) {
         first = wSJeYsJcmjNZf.peekFirst();
         if (first != null && now - first > 310L) {
            wSJeYsJcmjNZf.clear();
         }
      }
   }

   public static int i2Nu6XHKic() {
      synchronized (QfZy2gWnKInxFPr) {
         return QfZy2gWnKInxFPr.size();
      }
   }

   public static int vGAMA89H() {
      synchronized (TJImwK2) {
         return TJImwK2.size();
      }
   }

   public static int qhulAUwd() {
      synchronized (wSJeYsJcmjNZf) {
         return wSJeYsJcmjNZf.size();
      }
   }

   public void render(HudRenderer renderer) {
      int inventoryCount = i2Nu6XHKic();
      int generalCount = vGAMA89H();
      int InteractionCount = qhulAUwd();
      String[][] lines = new String[][]{
         {"Inv", String.valueOf(inventoryCount)}, {"Global", String.valueOf(generalCount)}, {"Int", String.valueOf(InteractionCount)}
      };
      double lineHeight = renderer.textHeight(true, (Double)this.fStlZIvY9lS8.get());
      double width = 0.0;

      for (String[] line : lines) {
         double lineWidth = renderer.textWidth(line[0], true)
            + renderer.textWidth(": ", true)
            + renderer.textWidth(line[1], true, (Double)this.fStlZIvY9lS8.get());
         width = Math.max(width, lineWidth);
      }

      this.setSize(width, lineHeight * lines.length);
      double Y = this.y;
      double X = this.x;
      Color color;
      if (inventoryCount < ((Integer)MusheorSystem.Manager.invPacketLimit.get()).intValue() * 0.5) {
         color = Color.GREEN;
      } else if (inventoryCount < ((Integer)MusheorSystem.Manager.invPacketLimit.get()).intValue() * 0.8) {
         color = Color.YELLOW;
      } else {
         color = Color.RED;
      }

      Color color2;
      if (generalCount < ((Integer)MusheorSystem.Manager.globalPacketLimit.get()).intValue() * 0.5) {
         color2 = Color.GREEN;
      } else if (generalCount < ((Integer)MusheorSystem.Manager.globalPacketLimit.get()).intValue() * 0.8) {
         color2 = Color.YELLOW;
      } else {
         color2 = Color.RED;
      }

      Color color3;
      if (InteractionCount < 4.5) {
         color3 = Color.GREEN;
      } else if (InteractionCount < 7.2) {
         color3 = Color.YELLOW;
      } else {
         color3 = Color.RED;
      }

      String text2 = String.format("global: %d/%d", generalCount, MusheorSystem.Manager.globalPacketLimit.get());
      renderer.text(text2, X, Y, color2, true, (Double)this.fStlZIvY9lS8.get());
      Y += lineHeight;
      String text = String.format("inv: %d/%d", inventoryCount, MusheorSystem.Manager.invPacketLimit.get());
      renderer.text(text, X, Y, color, true, (Double)this.fStlZIvY9lS8.get());
      Y += lineHeight;
      String text3 = String.format("int: %d/%d", InteractionCount, 9);
      renderer.text(text3, X, Y, color3, true, (Double)this.fStlZIvY9lS8.get());
   }
}
