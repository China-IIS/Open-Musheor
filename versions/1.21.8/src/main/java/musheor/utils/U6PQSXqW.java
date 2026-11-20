package musheor.utils;

import meteordevelopment.discordipc.DiscordIPC;
import meteordevelopment.discordipc.RichPresence;
import musheor.utils.system.MusheorSystem;

public class U6PQSXqW {
   private static final long fStlZIvY9lS8 = 1346947991059300434L;
   private static final RichPresence QfZy2gWnKInxFPr = new RichPresence();

   public static void fStlZIvY9lS8() {
      DiscordIPC.start(1346947991059300434L, null);
      QfZy2gWnKInxFPr.setStart(System.currentTimeMillis() / 1000L);
      QfZy2gWnKInxFPr.setLargeImage("musheor_logo", "Musheor Highway Builder");
      TJImwK2();
   }

   public static void QfZy2gWnKInxFPr() {
      DiscordIPC.stop();
   }

   public static void TJImwK2() {
      if (obf.U6PQSXqW.fStlZIvY9lS8().pF16zz4NJECfg7() > 20) {
         QfZy2gWnKInxFPr.setDetails(wSJeYsJcmjNZf());
         QfZy2gWnKInxFPr.setState(i2Nu6XHKic());
         DiscordIPC.setActivity(QfZy2gWnKInxFPr);
         MusheorSystem.debug("Updated RPC.");
      }
   }

   private static String wSJeYsJcmjNZf() {
      return String.format(
         "Obsidian placed | mined: %s | %s",
         oX7ku.wSJeYsJcmjNZf(obf.U6PQSXqW.fStlZIvY9lS8().DqGSCWplcjMi()),
         oX7ku.wSJeYsJcmjNZf(obf.U6PQSXqW.fStlZIvY9lS8().g0iiTDJzfIGXdui())
      );
   }

   private static String i2Nu6XHKic() {
      return String.format(
         "Lifetime: %s placed | %s mined",
         oX7ku.wSJeYsJcmjNZf(obf.U6PQSXqW.fStlZIvY9lS8().xarqg2oUiwotDG4()),
         oX7ku.wSJeYsJcmjNZf(obf.U6PQSXqW.fStlZIvY9lS8().RFflNsLXot())
      );
   }
}
