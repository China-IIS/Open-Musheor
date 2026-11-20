package musheor.utils.manager;

import java.util.ArrayDeque;
import musheor.utils.JhujBv5;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;

public class U6PQSXqW {
   private static final ArrayDeque<Integer> fStlZIvY9lS8 = new ArrayDeque<>();
   private static volatile boolean QfZy2gWnKInxFPr;

   public static synchronized void fStlZIvY9lS8() {
      QfZy2gWnKInxFPr = true;
   }

   public static synchronized boolean QfZy2gWnKInxFPr() {
      return QfZy2gWnKInxFPr;
   }

   public static synchronized boolean fStlZIvY9lS8(int parameter) {
      if (!QfZy2gWnKInxFPr) {
         return false;
      } else {
         fStlZIvY9lS8.addLast(parameter);
         return true;
      }
   }

   public static synchronized void TJImwK2() {
      QfZy2gWnKInxFPr = false;
      if (!fStlZIvY9lS8.isEmpty()) {
         if (JhujBv5.fStlZIvY9lS8.getNetworkHandler() == null) {
            fStlZIvY9lS8.clear();
         } else {
            while (!fStlZIvY9lS8.isEmpty()) {
               JhujBv5.fStlZIvY9lS8.getNetworkHandler().sendPacket(new CommonPongC2SPacket(fStlZIvY9lS8.pollFirst()));
            }
         }
      }
   }

   public static synchronized void wSJeYsJcmjNZf() {
      QfZy2gWnKInxFPr = false;
      fStlZIvY9lS8.clear();
   }
}
