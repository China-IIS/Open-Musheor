package musheor.utils;

import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.systems.modules.Modules;
import musheor.modules.automation.HighwayBuilder;
import net.minecraft.item.Items;

public class oX7ku {
   public static int fStlZIvY9lS8 = 0;

   public oX7ku() {
   }

   public static String fStlZIvY9lS8(int sectionSize) {
      double distanceLeft = TJImwK2(sectionSize);
      double percentage = (sectionSize - distanceLeft) / sectionSize * 100.0;
      return String.format("%.2f", percentage);
   }

   public static int fStlZIvY9lS8() {
      oyDPiOVBGKG.gVOhtBej1x direction = HighwayBuilder.i2Nu6XHKic();
      assert MeteorClient.mc.player != null;
      int distanceTravelled = 0;
      if (direction == oyDPiOVBGKG.gVOhtBej1x.NORTH || direction == oyDPiOVBGKG.gVOhtBej1x.SOUTH) {
         distanceTravelled = Math.abs(MeteorClient.mc.player.getBlockZ()) - Math.abs(obf.U6PQSXqW.fStlZIvY9lS8().vGAMA89H());
      } else if (direction == oyDPiOVBGKG.gVOhtBej1x.EAST || direction == oyDPiOVBGKG.gVOhtBej1x.WEST) {
         distanceTravelled = Math.abs(MeteorClient.mc.player.getBlockX()) - Math.abs(obf.U6PQSXqW.fStlZIvY9lS8().i2Nu6XHKic());
      }

      if (direction == oyDPiOVBGKG.gVOhtBej1x.NORTH_EAST
         || direction == oyDPiOVBGKG.gVOhtBej1x.NORTH_WEST
         || direction == oyDPiOVBGKG.gVOhtBej1x.SOUTH_EAST
         || direction == oyDPiOVBGKG.gVOhtBej1x.SOUTH_WEST) {
         distanceTravelled = Math.abs(Math.abs(MeteorClient.mc.player.getBlockX()) - Math.abs(obf.U6PQSXqW.fStlZIvY9lS8().i2Nu6XHKic()));
      }

      return Math.abs(distanceTravelled);
   }

   public static int QfZy2gWnKInxFPr() {
      if (((HighwayBuilder)Modules.get().get(HighwayBuilder.class)).isActive()) {
         fStlZIvY9lS8 = fStlZIvY9lS8();
      }

      return fStlZIvY9lS8;
   }

   public static double TJImwK2() {
      if (!((HighwayBuilder)Modules.get().get(HighwayBuilder.class)).isActive()) {
         return -1.0;
      }

      double obsidianPlaced = obf.U6PQSXqW.fStlZIvY9lS8().DqGSCWplcjMi();
      double ticksPassed = obf.U6PQSXqW.fStlZIvY9lS8().pF16zz4NJECfg7();
      return obsidianPlaced != 0.0 && ticksPassed != 0.0 ? obsidianPlaced / (ticksPassed / 20.0) : 0.0;
   }

   public static double wSJeYsJcmjNZf() {
      if (!((HighwayBuilder)Modules.get().get(HighwayBuilder.class)).isActive()) {
         return -1.0;
      }

      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      double blocksBroken = state.g0iiTDJzfIGXdui() + state.RE8q1TMDRWx() + state.HomZ7BbhM7wB();
      double ticksPassed = state.pF16zz4NJECfg7();
      return blocksBroken != 0.0 && ticksPassed != 0.0 ? blocksBroken / (ticksPassed / 20.0) : 0.0;
   }

   public static int i2Nu6XHKic() {
      return musheor.utils.manager.pvfA15fQSVM.vGAMA89H(Items.ENDER_CHEST) * 8 + musheor.utils.manager.pvfA15fQSVM.vGAMA89H(Items.OBSIDIAN);
   }

   public static double fStlZIvY9lS8(double distanceTravelled, double ticksPassed) {
      double secondsPassed = ticksPassed / 20.0;
      return secondsPassed <= 0.0 ? 0.0 : distanceTravelled / secondsPassed;
   }

   public static double QfZy2gWnKInxFPr(int sectionSize) {
      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      if (QfZy2gWnKInxFPr() >= 1 && state.pF16zz4NJECfg7() >= 1) {
         long remainingDistance = TJImwK2(sectionSize);
         long distanceTravelled = QfZy2gWnKInxFPr();
         long distancePerSecond = QfZy2gWnKInxFPr() / (state.pF16zz4NJECfg7() / 20);
         if (distanceTravelled <= 1L) {
            return -2.0;
         } else {
            return state.pF16zz4NJECfg7() <= 200 ? -1.0 : remainingDistance / fStlZIvY9lS8(distanceTravelled, state.pF16zz4NJECfg7());
         }
      } else {
         return 0.0;
      }
   }

   public static int TJImwK2(int sectionSize) {
      assert MeteorClient.mc.player != null;
      oyDPiOVBGKG.gVOhtBej1x direction = HighwayBuilder.i2Nu6XHKic();

      int blockCoordinate = switch (direction) {
         case NORTH, SOUTH -> MeteorClient.mc.player.getBlockZ();
         case EAST, WEST, NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST -> MeteorClient.mc.player.getBlockX();
         case null, default -> 0;
      };
      int sectionStart = Math.floorDiv(blockCoordinate, sectionSize) * sectionSize;
      int sectionEnd = sectionStart + sectionSize;
      if (direction == oyDPiOVBGKG.gVOhtBej1x.SOUTH
         || direction == oyDPiOVBGKG.gVOhtBej1x.WEST
         || direction == oyDPiOVBGKG.gVOhtBej1x.NORTH_EAST
         || direction == oyDPiOVBGKG.gVOhtBej1x.SOUTH_EAST) {
         sectionStart = sectionEnd;
      }

      int blocksLeft = sectionStart - blockCoordinate;
      return Math.abs(blocksLeft);
   }

   public static String wSJeYsJcmjNZf(int value) {
      if (value >= 1000000) {
         return String.format("%.2fm", value / 1000000.0);
      } else {
         return value >= 1000 ? String.format("%.2fk", value / 1000.0) : String.valueOf(value);
      }
   }

   public static String fStlZIvY9lS8(double placementsPerSecond) {
      return placementsPerSecond == -1.0 ? "Waiting..." : String.format("%.2f blocks / s", placementsPerSecond);
   }

   public static String QfZy2gWnKInxFPr(double placementsPerSecond) {
      return placementsPerSecond == -1.0 ? "Waiting..." : String.format("%.2f blocks / h", placementsPerSecond * 3600.0);
   }

   public static String i2Nu6XHKic(int distanceTravelled) {
      return distanceTravelled < 1
         ? "Waiting..."
         : String.format("%.2f blocks / s", (float)fStlZIvY9lS8(distanceTravelled, obf.U6PQSXqW.fStlZIvY9lS8().pF16zz4NJECfg7()));
   }

   public static String vGAMA89H(int distanceTravelled) {
      return distanceTravelled < 1
         ? "Waiting..."
         : String.format("%.2f blocks / h", (float)fStlZIvY9lS8(distanceTravelled, obf.U6PQSXqW.fStlZIvY9lS8().pF16zz4NJECfg7()) * 3600.0F);
   }

   public static String TJImwK2(double breakingPerSecond) {
      return breakingPerSecond == -1.0 ? "Waiting..." : String.format("%.2f blocks / s", breakingPerSecond);
   }

   public static String vGAMA89H() {
      if (TJImwK2() == -1.0) {
         return "Waiting...";
      }

      long timeLeft = (long)(i2Nu6XHKic() / TJImwK2());
      long hours = timeLeft / 3600L;
      long minutes = timeLeft % 3600L / 60L;
      long seconds = timeLeft % 60L;
      return String.format("%d:%02d:%02d", hours, minutes, seconds);
   }

   public static String qhulAUwd(int sectionSize) {
      if (TJImwK2() == -1.0) {
         return "Waiting...";
      }

      long timeLeft = (long)(TJImwK2(sectionSize) / fStlZIvY9lS8(QfZy2gWnKInxFPr(), obf.U6PQSXqW.fStlZIvY9lS8().pF16zz4NJECfg7()));
      long hours = timeLeft / 3600L;
      long minutes = timeLeft % 3600L / 60L;
      long seconds = timeLeft % 60L;
      return String.format("%d:%02d:%02d", hours, minutes, seconds);
   }

   public static String fStlZIvY9lS8(long ticks) {
      if (ticks == -1L) {
         return "Calculating...";
      }

      if (ticks == -2L) {
         return "Waiting...";
      }

      long totalSeconds = ticks / 20L;
      long hours = totalSeconds / 3600L;
      long minutes = totalSeconds % 3600L / 60L;
      long seconds = totalSeconds % 60L;
      return String.format("%d:%02d:%02d", hours, minutes, seconds);
   }
}
