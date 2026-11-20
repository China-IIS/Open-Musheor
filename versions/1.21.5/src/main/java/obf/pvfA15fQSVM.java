package obf;

import musheor.modules.automation.HighwayBuilder;
import musheor.modules.automation.highway.HighwayNetwork;
import musheor.utils.oyDPiOVBGKG;
import musheor.utils.system.MusheorSystem;
import net.minecraft.util.math.BlockPos;

public class pvfA15fQSVM {
   private static final double fStlZIvY9lS8 = 8.0 * Math.sqrt(2.0);

   public static obf.pvfA15fQSVM.gVOhtBej1x fStlZIvY9lS8(int px, int pz, int py, oyDPiOVBGKG.gVOhtBej1x dir) {
      return fStlZIvY9lS8(dir) ? QfZy2gWnKInxFPr(px, pz, py, dir) : TJImwK2(px, pz, py, dir);
   }

   private static boolean fStlZIvY9lS8(oyDPiOVBGKG.gVOhtBej1x dir) {
      return dir == oyDPiOVBGKG.gVOhtBej1x.i2Nu6XHKic
         || dir == oyDPiOVBGKG.gVOhtBej1x.fStlZIvY9lS8
         || dir == oyDPiOVBGKG.gVOhtBej1x.qhulAUwd
         || dir == oyDPiOVBGKG.gVOhtBej1x.TJImwK2;
   }

   private static obf.pvfA15fQSVM.gVOhtBej1x QfZy2gWnKInxFPr(int px, int pz, int py, oyDPiOVBGKG.gVOhtBej1x dir) {
      boolean travelX = dir == oyDPiOVBGKG.gVOhtBej1x.qhulAUwd || dir == oyDPiOVBGKG.gVOhtBej1x.TJImwK2;
      int perpCoord = travelX ? pz : px;
      double bestDist = 8.0;
      String bestId = null;
      String bestLabel = null;
      obf.pvfA15fQSVM.pvfA15fQSVM bestCat = null;
      double bestAxis = 0.0;
      double d = Math.abs(perpCoord);
      if (d < bestDist) {
         bestDist = d;
         bestId = travelX ? (dir == oyDPiOVBGKG.gVOhtBej1x.qhulAUwd ? "+X" : "-X") : (dir == oyDPiOVBGKG.gVOhtBej1x.fStlZIvY9lS8 ? "+Z" : "-Z");
         bestLabel = travelX
            ? (dir == oyDPiOVBGKG.gVOhtBej1x.qhulAUwd ? "+X (East)" : "-X (West)")
            : (dir == oyDPiOVBGKG.gVOhtBej1x.fStlZIvY9lS8 ? "+Z (South)" : "-Z (North)");
         bestCat = obf.pvfA15fQSVM.pvfA15fQSVM.fStlZIvY9lS8;
         bestAxis = 0.0;
      }

      for (double dist : HighwayNetwork.fStlZIvY9lS8()) {
         for (double candidate : new double[]{dist, -dist}) {
            d = Math.abs(perpCoord - candidate);
            if (d < bestDist) {
               int parallelCoord = travelX ? px : pz;
               if (Math.abs(parallelCoord) <= dist + 8.0) {
                  bestDist = d;
                  bestId = "ring_" + QfZy2gWnKInxFPr(dist);
                  bestLabel = fStlZIvY9lS8(dist) + " Ring";
                  bestCat = obf.pvfA15fQSVM.pvfA15fQSVM.QfZy2gWnKInxFPr;
                  bestAxis = candidate;
               }
            }
         }
      }

      for (int n = 1; n * 5000 <= 50000; n++) {
         int pCoord = n * 5000;
         int nCoord = -(n * 5000 + 1);

         for (double candidatex : new double[]{pCoord, nCoord}) {
            d = Math.abs(perpCoord - candidatex);
            if (d < bestDist) {
               int parallelCoord = travelX ? px : pz;
               if (Math.abs(parallelCoord) <= 50008.0) {
                  bestDist = d;
                  long cLong = (long)(n * 5000) * (candidatex > 0.0 ? 1 : -1);
                  bestId = (travelX ? "grid_ns_" : "grid_ew_") + cLong;
                  bestLabel = (candidatex > 0.0 ? "+" : "-") + fStlZIvY9lS8(n * 5000) + (travelX ? " N/S Grid" : " E/W Grid");
                  bestCat = obf.pvfA15fQSVM.pvfA15fQSVM.i2Nu6XHKic;
                  bestAxis = candidatex;
               }
            }
         }
      }

      if (bestId == null) {
         return null;
      } else {
         int width = fStlZIvY9lS8(bestCat);
         boolean isEvenWidth = width % 2 == 0;
         double alignX;
         double alignZ;
         int startX;
         int startZ;
         if (travelX) {
            alignX = px;
            alignZ = isEvenWidth ? bestAxis : bestAxis + 0.5;
            startX = px;
            startZ = isEvenWidth && dir == oyDPiOVBGKG.gVOhtBej1x.TJImwK2 ? (int)bestAxis - 1 : (int)bestAxis;
         } else {
            alignX = isEvenWidth ? bestAxis : bestAxis + 0.5;
            alignZ = pz;
            startX = isEvenWidth && dir == oyDPiOVBGKG.gVOhtBej1x.fStlZIvY9lS8 ? (int)bestAxis - 1 : (int)bestAxis;
            startZ = pz;
         }

         return new obf.pvfA15fQSVM.gVOhtBej1x(
            bestId, bestLabel, bestCat, HighwayBuilder.HighwayType.fStlZIvY9lS8, dir, width, bestAxis, alignX, alignZ, startX, startZ
         );
      }
   }

   private static obf.pvfA15fQSVM.gVOhtBej1x TJImwK2(int px, int pz, int py, oyDPiOVBGKG.gVOhtBej1x dir) {
      boolean isNESW = dir == oyDPiOVBGKG.gVOhtBej1x.vGAMA89H || dir == oyDPiOVBGKG.gVOhtBej1x.QfZy2gWnKInxFPr;
      double diagVal = isNESW ? px + pz : px - pz;
      double bestDist = fStlZIvY9lS8;
      String bestId = null;
      String bestLabel = null;
      obf.pvfA15fQSVM.pvfA15fQSVM bestCat = null;
      double bestK = 0.0;
      double d = Math.abs(diagVal);
      if (d < bestDist) {
         bestDist = d;
         bestId = isNESW ? (dir == oyDPiOVBGKG.gVOhtBej1x.vGAMA89H ? "NE" : "SW") : (dir == oyDPiOVBGKG.gVOhtBej1x.wSJeYsJcmjNZf ? "NW" : "SE");
         bestLabel = isNESW
            ? (dir == oyDPiOVBGKG.gVOhtBej1x.vGAMA89H ? "NE Diagonal" : "SW Diagonal")
            : (dir == oyDPiOVBGKG.gVOhtBej1x.wSJeYsJcmjNZf ? "NW Diagonal" : "SE Diagonal");
         bestCat = obf.pvfA15fQSVM.pvfA15fQSVM.TJImwK2;
         bestK = 0.0;
      }

      for (double dist : HighwayNetwork.QfZy2gWnKInxFPr()) {
         for (double candidate : new double[]{dist, -dist}) {
            d = Math.abs(diagVal - candidate);
            if (d < bestDist) {
               bestDist = d;
               bestId = "diamond_" + QfZy2gWnKInxFPr(dist);
               bestLabel = fStlZIvY9lS8(dist) + " Diamond";
               bestCat = obf.pvfA15fQSVM.pvfA15fQSVM.wSJeYsJcmjNZf;
               bestK = candidate;
            }
         }
      }

      if (bestId == null) {
         return null;
      } else {
         int width = fStlZIvY9lS8(bestCat);
         int blockK = isNESW ? (int)bestK - 1 : (int)bestK;
         double cz;
         double cx;
         if (isNESW) {
            cx = (px - pz + blockK) / 2.0;
            cz = blockK - cx;
         } else {
            cx = (px + pz + blockK) / 2.0;
            cz = cx - blockK;
         }

         int startX_i = (int)Math.round(cx);
         int startZ_i = isNESW ? blockK - startX_i : startX_i - blockK;
         return new obf.pvfA15fQSVM.gVOhtBej1x(
            bestId, bestLabel, bestCat, HighwayBuilder.HighwayType.QfZy2gWnKInxFPr, dir, width, blockK, cx + 0.5, cz + 0.5, startX_i, startZ_i
         );
      }
   }

   private static int fStlZIvY9lS8(obf.pvfA15fQSVM.pvfA15fQSVM cat) {
      MusheorSystem m = MusheorSystem.Manager;
      if (m == null) {
         return 4;
      } else {
         return switch (cat) {
            case fStlZIvY9lS8, QfZy2gWnKInxFPr -> m.cardinalHighwayWidth.get();
            case TJImwK2, wSJeYsJcmjNZf -> m.diagonalHighwayWidth.get();
            case i2Nu6XHKic -> m.gridHighwayWidth.get();
         };
      }
   }

   public static boolean fStlZIvY9lS8(BlockPos railPos, obf.pvfA15fQSVM.gVOhtBej1x detected) {
      int rx = railPos.getX();
      int rz = railPos.getZ();
      MusheorSystem m = MusheorSystem.Manager;
      int cardinalW = m != null ? (Integer)m.cardinalHighwayWidth.get() : 4;
      int diagonalW = m != null ? (Integer)m.diagonalHighwayWidth.get() : 9;
      int gridW = m != null ? (Integer)m.gridHighwayWidth.get() : 3;
      int diagHalf = diagonalW / 2 + 1;
      boolean travelX = detected.i2Nu6XHKic == oyDPiOVBGKG.gVOhtBej1x.qhulAUwd || detected.i2Nu6XHKic == oyDPiOVBGKG.gVOhtBej1x.TJImwK2;
      boolean travelZ = detected.i2Nu6XHKic == oyDPiOVBGKG.gVOhtBej1x.i2Nu6XHKic || detected.i2Nu6XHKic == oyDPiOVBGKG.gVOhtBej1x.fStlZIvY9lS8;
      boolean travelNESW = detected.i2Nu6XHKic == oyDPiOVBGKG.gVOhtBej1x.vGAMA89H || detected.i2Nu6XHKic == oyDPiOVBGKG.gVOhtBej1x.QfZy2gWnKInxFPr;
      boolean travelNWSE = detected.i2Nu6XHKic == oyDPiOVBGKG.gVOhtBej1x.wSJeYsJcmjNZf || detected.i2Nu6XHKic == oyDPiOVBGKG.gVOhtBej1x.BrmKXSYhWhqoB1;
      int cardinalHalf = cardinalW / 2;
      int gridHalf = gridW / 2;
      if (!travelZ) {
         if (fStlZIvY9lS8(rx, 0, cardinalW)) {
            return true;
         }

         for (double dist : HighwayNetwork.fStlZIvY9lS8()) {
            if (Math.abs(rz) <= dist + cardinalHalf) {
               if (fStlZIvY9lS8(rx, (int)dist, cardinalW)) {
                  return true;
               }

               if (fStlZIvY9lS8(rx, -((int)dist), cardinalW)) {
                  return true;
               }
            }
         }

         if (Math.abs(rz) <= 50000 + gridHalf) {
            for (int n = 1; n * 5000 <= 50000; n++) {
               if (fStlZIvY9lS8(rx, n * 5000, gridW)) {
                  return true;
               }

               if (fStlZIvY9lS8(rx, -(n * 5000 + 1), gridW)) {
                  return true;
               }
            }
         }
      }

      if (!travelX) {
         if (fStlZIvY9lS8(rz, 0, cardinalW)) {
            return true;
         }

         for (double distx : HighwayNetwork.fStlZIvY9lS8()) {
            if (Math.abs(rx) <= distx + cardinalHalf) {
               if (fStlZIvY9lS8(rz, (int)distx, cardinalW)) {
                  return true;
               }

               if (fStlZIvY9lS8(rz, -((int)distx), cardinalW)) {
                  return true;
               }
            }
         }

         if (Math.abs(rx) <= 50000 + gridHalf) {
            for (int n = 1; n * 5000 <= 50000; n++) {
               if (fStlZIvY9lS8(rz, n * 5000, gridW)) {
                  return true;
               }

               if (fStlZIvY9lS8(rz, -(n * 5000 + 1), gridW)) {
                  return true;
               }
            }
         }
      }

      if (!travelNESW) {
         int nesw = rx + rz;
         if (Math.abs(nesw + 1) <= diagHalf) {
            return true;
         }

         for (double distxx : HighwayNetwork.QfZy2gWnKInxFPr()) {
            if (Math.abs(nesw - ((int)distxx - 1)) <= diagHalf && rx >= 0 && rz >= 0) {
               return true;
            }

            if (Math.abs(nesw + (int)distxx + 1) <= diagHalf && rx <= 0 && rz <= 0) {
               return true;
            }
         }
      }

      if (!travelNWSE) {
         int nwse = rx - rz;
         if (Math.abs(nwse) <= diagHalf) {
            return true;
         }

         for (double distxx : HighwayNetwork.QfZy2gWnKInxFPr()) {
            if (Math.abs(nwse - (int)distxx) <= diagHalf && rx >= 0 && rz <= 0) {
               return true;
            }

            if (Math.abs(nwse + (int)distxx) <= diagHalf && rx <= 0 && rz >= 0) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean QfZy2gWnKInxFPr(BlockPos pos, obf.pvfA15fQSVM.gVOhtBej1x detected) {
      if (detected.wSJeYsJcmjNZf == HighwayBuilder.HighwayType.fStlZIvY9lS8) {
         boolean travelX = detected.i2Nu6XHKic == oyDPiOVBGKG.gVOhtBej1x.qhulAUwd || detected.i2Nu6XHKic == oyDPiOVBGKG.gVOhtBej1x.TJImwK2;
         int perpCoord = travelX ? pos.getZ() : pos.getX();
         return fStlZIvY9lS8(perpCoord, (int)detected.qhulAUwd, detected.vGAMA89H);
      } else {
         boolean isNESW = detected.i2Nu6XHKic == oyDPiOVBGKG.gVOhtBej1x.vGAMA89H || detected.i2Nu6XHKic == oyDPiOVBGKG.gVOhtBej1x.QfZy2gWnKInxFPr;
         int K = (int)detected.qhulAUwd;
         int u = isNESW ? pos.getX() + pos.getZ() - K : pos.getX() - pos.getZ() - K;
         int HW = (detected.vGAMA89H - 1) / 2;
         return Math.abs(u) <= HW;
      }
   }

   private static boolean fStlZIvY9lS8(int coord, int center, int width) {
      int halfLeft = width / 2;
      int halfRight = width % 2 == 0 ? halfLeft - 1 : halfLeft;
      return coord >= center - halfLeft && coord <= center + halfRight;
   }

   public static String fStlZIvY9lS8(double d) {
      if (d >= 1000000.0) {
         return fStlZIvY9lS8(String.format("%.3f", d / 1000000.0)) + "M";
      } else {
         return d >= 1000.0 ? fStlZIvY9lS8(String.format("%.3f", d / 1000.0)) + "k" : String.valueOf((long)d);
      }
   }

   private static String fStlZIvY9lS8(String s) {
      return s.replaceAll("0+$", "").replaceAll("\\.$", "");
   }

   private static String QfZy2gWnKInxFPr(double d) {
      long l = (long)d;
      return l == d ? String.valueOf(l) : String.valueOf(d);
   }

   public static class gVOhtBej1x {
      public final String fStlZIvY9lS8;
      public final String QfZy2gWnKInxFPr;
      public final obf.pvfA15fQSVM.pvfA15fQSVM TJImwK2;
      public final HighwayBuilder.HighwayType wSJeYsJcmjNZf;
      public final oyDPiOVBGKG.gVOhtBej1x i2Nu6XHKic;
      public final int vGAMA89H;
      public final double qhulAUwd;
      public final double BrmKXSYhWhqoB1;
      public final double TXWyNb;
      public final int I3psabmOrOI;
      public final int duJ6sPeetAcBh6P;

      public gVOhtBej1x(
         String id,
         String label,
         obf.pvfA15fQSVM.pvfA15fQSVM category,
         HighwayBuilder.HighwayType type,
         oyDPiOVBGKG.gVOhtBej1x direction,
         int width,
         double axisValue,
         double alignX,
         double alignZ,
         int startX,
         int startZ
      ) {
         this.fStlZIvY9lS8 = id;
         this.QfZy2gWnKInxFPr = label;
         this.TJImwK2 = category;
         this.wSJeYsJcmjNZf = type;
         this.i2Nu6XHKic = direction;
         this.vGAMA89H = width;
         this.qhulAUwd = axisValue;
         this.BrmKXSYhWhqoB1 = alignX;
         this.TXWyNb = alignZ;
         this.I3psabmOrOI = startX;
         this.duJ6sPeetAcBh6P = startZ;
      }
   }

   public static enum pvfA15fQSVM {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr,
      TJImwK2,
      wSJeYsJcmjNZf,
      i2Nu6XHKic;
   }
}
