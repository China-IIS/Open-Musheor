package obf;

import java.util.HashSet;
import java.util.Set;
import meteordevelopment.meteorclient.systems.modules.Modules;

public final class gVOhtBej1x {
   private static final Set<Integer> fStlZIvY9lS8 = new HashSet<>();

   public static boolean fStlZIvY9lS8(int slot) {
      return slot >= 0 && slot < 27 && !fStlZIvY9lS8.contains(slot);
   }

   public static boolean QfZy2gWnKInxFPr(int slot) {
      return fStlZIvY9lS8.contains(slot);
   }

   public static void TJImwK2(int slot) {
      if (slot >= 0 && slot < 27) {
         fStlZIvY9lS8.remove(slot);
      }
   }

   public static void wSJeYsJcmjNZf(int slot) {
      if (slot >= 0 && slot < 27) {
         fStlZIvY9lS8.add(slot);
      }
   }

   public static int fStlZIvY9lS8() {
      int mask = 0;

      for (int slot : fStlZIvY9lS8) {
         if (slot >= 0 && slot < 27) {
            mask |= 1 << slot;
         }
      }

      return mask;
   }

   public static void i2Nu6XHKic(int mask) {
      fStlZIvY9lS8.clear();

      for (int slot = 0; slot < 27; slot++) {
         if ((mask & 1 << slot) != 0) {
            fStlZIvY9lS8.add(slot);
         }
      }
   }

   public static void QfZy2gWnKInxFPr() {
      Modules.get().save();
   }
}
