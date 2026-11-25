package musheor.modules.automation.highway;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import musheor.utils.oyDPiOVBGKG;
import musheor.utils.system.HighwayNetworkManager;

public class HighwayNetwork {
   public static final List<HighwayNetwork.Highway> fStlZIvY9lS8 = wSJeYsJcmjNZf();
   public static final List<HighwayNetwork.Intersection> QfZy2gWnKInxFPr = fStlZIvY9lS8(fStlZIvY9lS8);
   public static final List<HighwayNetwork.Edge> TJImwK2 = fStlZIvY9lS8(fStlZIvY9lS8, QfZy2gWnKInxFPr);

   public static double[] fStlZIvY9lS8() {
      return HighwayNetworkManager.getInstance().getRingDistances("2b2t");
   }

   public static double[] QfZy2gWnKInxFPr() {
      return HighwayNetworkManager.getInstance().getDiamondDistances("2b2t");
   }

   private static List<HighwayNetwork.Highway> wSJeYsJcmjNZf() {
      List<HighwayNetwork.Highway> h = new ArrayList<>();
      h.add(HighwayNetwork.Highway.fStlZIvY9lS8());
      h.add(HighwayNetwork.Highway.QfZy2gWnKInxFPr());
      h.add(HighwayNetwork.Highway.TJImwK2());
      h.add(HighwayNetwork.Highway.wSJeYsJcmjNZf());

      for (double d : fStlZIvY9lS8()) {
         h.add(HighwayNetwork.Highway.fStlZIvY9lS8(d));
         h.add(HighwayNetwork.Highway.fStlZIvY9lS8(-d));
         h.add(HighwayNetwork.Highway.QfZy2gWnKInxFPr(d));
         h.add(HighwayNetwork.Highway.QfZy2gWnKInxFPr(-d));
      }

      for (double d : QfZy2gWnKInxFPr()) {
         h.add(HighwayNetwork.Highway.fStlZIvY9lS8(true, d));
         h.add(HighwayNetwork.Highway.fStlZIvY9lS8(true, -d));
         h.add(HighwayNetwork.Highway.fStlZIvY9lS8(false, d));
         h.add(HighwayNetwork.Highway.fStlZIvY9lS8(false, -d));
      }

      for (int i = 1; i <= 10; i++) {
         double coord = i * 5000.0 + 0.5;
         h.add(HighwayNetwork.Highway.TJImwK2(coord));
         h.add(HighwayNetwork.Highway.TJImwK2(-coord));
         h.add(HighwayNetwork.Highway.wSJeYsJcmjNZf(coord));
         h.add(HighwayNetwork.Highway.wSJeYsJcmjNZf(-coord));
      }

      return Collections.unmodifiableList(h);
   }

   private static List<HighwayNetwork.Intersection> fStlZIvY9lS8(List<HighwayNetwork.Highway> highways) {
      Map<String, List<HighwayNetwork.Highway>> byPos = new LinkedHashMap<>();

      for (int i = 0; i < highways.size(); i++) {
         for (int j = i + 1; j < highways.size(); j++) {
            HighwayNetwork.Highway a = highways.get(i);
            HighwayNetwork.Highway b = highways.get(j);
            oyDPiOVBGKG.pvfA15fQSVM pt = fStlZIvY9lS8(a, b);
            if (pt != null && a.fStlZIvY9lS8(pt.fStlZIvY9lS8(), pt.QfZy2gWnKInxFPr(), 1.0) && b.fStlZIvY9lS8(pt.fStlZIvY9lS8(), pt.QfZy2gWnKInxFPr(), 1.0)) {
               String key = fStlZIvY9lS8(pt);
               byPos.computeIfAbsent(key, k -> new ArrayList<>()).add(a);
               byPos.get(key).add(b);
            }
         }
      }

      List<HighwayNetwork.Intersection> nodes = new ArrayList<>();

      for (Entry<String, List<HighwayNetwork.Highway>> entry : byPos.entrySet()) {
         oyDPiOVBGKG.pvfA15fQSVM pos = fStlZIvY9lS8(entry.getKey());
         List<HighwayNetwork.Highway> unique = entry.getValue().stream().distinct().toList();
         nodes.add(new HighwayNetwork.Intersection(pos, unique));
      }

      return Collections.unmodifiableList(nodes);
   }

   private static List<HighwayNetwork.Edge> fStlZIvY9lS8(List<HighwayNetwork.Highway> highways, List<HighwayNetwork.Intersection> nodes) {
      List<HighwayNetwork.Edge> edges = new ArrayList<>();

      for (HighwayNetwork.Highway hw : highways) {
         List<Integer> onHw = new ArrayList<>();

         for (int i = 0; i < nodes.size(); i++) {
            oyDPiOVBGKG.pvfA15fQSVM p = nodes.get(i).fStlZIvY9lS8();
            if (hw.fStlZIvY9lS8(p.fStlZIvY9lS8(), p.QfZy2gWnKInxFPr(), 1.0)) {
               onHw.add(i);
            }
         }

         onHw.sort((a, b) -> {
            oyDPiOVBGKG.pvfA15fQSVM pa = nodes.get(a).fStlZIvY9lS8();
            oyDPiOVBGKG.pvfA15fQSVM pb = nodes.get(b).fStlZIvY9lS8();
            double ca = fStlZIvY9lS8(hw, pa);
            double cb = fStlZIvY9lS8(hw, pb);
            return Double.compare(ca, cb);
         });

         for (int k = 0; k < onHw.size() - 1; k++) {
            int from = onHw.get(k);
            int to = onHw.get(k + 1);
            oyDPiOVBGKG.pvfA15fQSVM pf = nodes.get(from).fStlZIvY9lS8();
            oyDPiOVBGKG.pvfA15fQSVM pt = nodes.get(to).fStlZIvY9lS8();
            double dist = fStlZIvY9lS8(pf, pt);
            edges.add(new HighwayNetwork.Edge(from, to, dist, hw));
            edges.add(new HighwayNetwork.Edge(to, from, dist, hw));
         }
      }

      return Collections.unmodifiableList(edges);
   }

   static oyDPiOVBGKG.pvfA15fQSVM fStlZIvY9lS8(HighwayNetwork.Highway a, HighwayNetwork.Highway b) {
      double[] la = QfZy2gWnKInxFPr(a);
      double[] lb = QfZy2gWnKInxFPr(b);
      if (la != null && lb != null) {
         double det = la[0] * lb[1] - lb[0] * la[1];
         if (Math.abs(det) < 1.0E-9) {
            return null;
         }

         double x = (la[2] * lb[1] - lb[2] * la[1]) / det;
         double z = (la[0] * lb[2] - lb[0] * la[2]) / det;
         return new oyDPiOVBGKG.pvfA15fQSVM(x, z);
      } else {
         return null;
      }
   }

   private static double[] QfZy2gWnKInxFPr(HighwayNetwork.Highway hw) {
      return switch (hw.qhulAUwd()) {
         case CARDINAL, RING_ROAD, GRID -> hw.BrmKXSYhWhqoB1() ? new double[]{0.0, 1.0, hw.TXWyNb()} : new double[]{1.0, 0.0, hw.TXWyNb()};
         case DIAGONAL -> hw.BrmKXSYhWhqoB1() ? new double[]{1.0, 1.0, hw.TXWyNb()} : new double[]{1.0, -1.0, hw.TXWyNb()};
         case DIAMOND -> hw.BrmKXSYhWhqoB1() ? new double[]{1.0, 1.0, hw.TXWyNb()} : new double[]{1.0, -1.0, hw.TXWyNb()};
      };
   }

   private static double fStlZIvY9lS8(HighwayNetwork.Highway hw, oyDPiOVBGKG.pvfA15fQSVM p) {
      return hw.i2Nu6XHKic() ? p.fStlZIvY9lS8() : (hw.BrmKXSYhWhqoB1() ? p.fStlZIvY9lS8() : p.QfZy2gWnKInxFPr());
   }

   public static double fStlZIvY9lS8(oyDPiOVBGKG.pvfA15fQSVM a, oyDPiOVBGKG.pvfA15fQSVM b) {
      double dx = a.fStlZIvY9lS8() - b.fStlZIvY9lS8();
      double dz = a.QfZy2gWnKInxFPr() - b.QfZy2gWnKInxFPr();
      return Math.sqrt(dx * dx + dz * dz);
   }

   private static String fStlZIvY9lS8(oyDPiOVBGKG.pvfA15fQSVM p) {
      return Math.round(p.fStlZIvY9lS8()) + "," + Math.round(p.QfZy2gWnKInxFPr());
   }

   private static oyDPiOVBGKG.pvfA15fQSVM fStlZIvY9lS8(String key) {
      String[] parts = key.split(",");
      return new oyDPiOVBGKG.pvfA15fQSVM(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
   }

   public static List<Integer> fStlZIvY9lS8(HighwayNetwork.Highway hw) {
      List<Integer> result = new ArrayList<>();

      for (int i = 0; i < QfZy2gWnKInxFPr.size(); i++) {
         oyDPiOVBGKG.pvfA15fQSVM p = QfZy2gWnKInxFPr.get(i).fStlZIvY9lS8();
         if (hw.fStlZIvY9lS8(p.fStlZIvY9lS8(), p.QfZy2gWnKInxFPr(), 1.0)) {
            result.add(i);
         }
      }

      return result;
   }

   public static Map<Integer, List<HighwayNetwork.Edge>> TJImwK2() {
      Map<Integer, List<HighwayNetwork.Edge>> adj = new HashMap<>();

      for (int i = 0; i < QfZy2gWnKInxFPr.size(); i++) {
         adj.put(i, new ArrayList<>());
      }

      for (HighwayNetwork.Edge e : TJImwK2) {
         adj.get(e.fStlZIvY9lS8()).add(e);
      }

      return adj;
   }

   public record Edge(int fStlZIvY9lS8, int QfZy2gWnKInxFPr, double TJImwK2, HighwayNetwork.Highway wSJeYsJcmjNZf) {


      @Override
      public final String toString() {
         return "Edge[from=" + this.fStlZIvY9lS8 + ", to=" + this.QfZy2gWnKInxFPr + ", distance=" + this.TJImwK2 + ", highway=" + this.wSJeYsJcmjNZf + "]";
      }
   }

   public static final class Highway {
      private final String fStlZIvY9lS8;
      private final HighwayNetwork.HighwayType QfZy2gWnKInxFPr;
      private final boolean TJImwK2;
      private final double wSJeYsJcmjNZf;
      private final double i2Nu6XHKic;
      private final double vGAMA89H;

      public Highway(String name, HighwayNetwork.HighwayType type, boolean isHorizontal, double constCoord, double minCoord, double maxCoord) {
         this.fStlZIvY9lS8 = name;
         this.QfZy2gWnKInxFPr = type;
         this.TJImwK2 = isHorizontal;
         this.wSJeYsJcmjNZf = constCoord;
         this.i2Nu6XHKic = minCoord;
         this.vGAMA89H = maxCoord;
      }

      static HighwayNetwork.Highway fStlZIvY9lS8() {
         return new HighwayNetwork.Highway("+X/-X axis (Z=0)", HighwayNetwork.HighwayType.CARDINAL, true, 0.0, -Double.MAX_VALUE, Double.MAX_VALUE);
      }

      static HighwayNetwork.Highway QfZy2gWnKInxFPr() {
         return new HighwayNetwork.Highway("+Z/-Z axis (X=0)", HighwayNetwork.HighwayType.CARDINAL, false, 0.0, -Double.MAX_VALUE, Double.MAX_VALUE);
      }

      static HighwayNetwork.Highway TJImwK2() {
         return new HighwayNetwork.Highway("diagonal (X=Z)", HighwayNetwork.HighwayType.DIAGONAL, false, 0.0, -Double.MAX_VALUE, Double.MAX_VALUE);
      }

      static HighwayNetwork.Highway wSJeYsJcmjNZf() {
         return new HighwayNetwork.Highway("diagonal (X=-Z)", HighwayNetwork.HighwayType.DIAGONAL, true, 0.0, -Double.MAX_VALUE, Double.MAX_VALUE);
      }

      static HighwayNetwork.Highway fStlZIvY9lS8(double coord) {
         double limit = Math.abs(coord);
         return new HighwayNetwork.Highway(String.format("ring road Z=%.0f", coord), HighwayNetwork.HighwayType.RING_ROAD, true, coord, -limit, limit);
      }

      static HighwayNetwork.Highway QfZy2gWnKInxFPr(double coord) {
         double limit = Math.abs(coord);
         return new HighwayNetwork.Highway(String.format("ring road X=%.0f", coord), HighwayNetwork.HighwayType.RING_ROAD, false, coord, -limit, limit);
      }

      static HighwayNetwork.Highway fStlZIvY9lS8(boolean isSum, double K) {
         double minX = Math.min(0.0, K);
         double maxX = Math.max(0.0, K);
         String eq = isSum ? String.format("diamond X+Z=%.0f", K) : String.format("diamond X-Z=%.0f", K);
         return new HighwayNetwork.Highway(eq, HighwayNetwork.HighwayType.DIAMOND, isSum, K, minX, maxX);
      }

      static HighwayNetwork.Highway TJImwK2(double coord) {
         return new HighwayNetwork.Highway(String.format("grid Z=%.1f", coord), HighwayNetwork.HighwayType.GRID, true, coord, -50000.5, 50000.5);
      }

      static HighwayNetwork.Highway wSJeYsJcmjNZf(double coord) {
         return new HighwayNetwork.Highway(String.format("grid X=%.1f", coord), HighwayNetwork.HighwayType.GRID, false, coord, -50000.5, 50000.5);
      }

      boolean i2Nu6XHKic() {
         return this.QfZy2gWnKInxFPr == HighwayNetwork.HighwayType.DIAGONAL || this.QfZy2gWnKInxFPr == HighwayNetwork.HighwayType.DIAMOND;
      }

      boolean fStlZIvY9lS8(double x, double z, double tol) {
         return switch (this.QfZy2gWnKInxFPr) {
            case CARDINAL, RING_ROAD, GRID -> this.TJImwK2
               ? Math.abs(z - this.wSJeYsJcmjNZf) <= tol && x >= this.i2Nu6XHKic - tol && x <= this.vGAMA89H + tol
               : Math.abs(x - this.wSJeYsJcmjNZf) <= tol && z >= this.i2Nu6XHKic - tol && z <= this.vGAMA89H + tol;
            case DIAGONAL -> {
               double val = this.TJImwK2 ? x + z : x - z;
               yield Math.abs(val - this.wSJeYsJcmjNZf) <= tol;
            }
            case DIAMOND -> {
               double val = this.TJImwK2 ? x + z : x - z;
               yield Math.abs(val - this.wSJeYsJcmjNZf) <= tol && x >= this.i2Nu6XHKic - tol && x <= this.vGAMA89H + tol;
            }
         };
      }

      double i2Nu6XHKic(double coord) {
         return Math.max(this.i2Nu6XHKic, Math.min(this.vGAMA89H, coord));
      }

      oyDPiOVBGKG.pvfA15fQSVM fStlZIvY9lS8(double x, double z) {
         return switch (this.QfZy2gWnKInxFPr) {
            case CARDINAL, RING_ROAD, GRID -> {
               if (this.TJImwK2) {
                  double cx = this.i2Nu6XHKic(x);
                  yield new oyDPiOVBGKG.pvfA15fQSVM(cx, this.wSJeYsJcmjNZf);
               } else {
                  double cz = this.i2Nu6XHKic(z);
                  yield new oyDPiOVBGKG.pvfA15fQSVM(this.wSJeYsJcmjNZf, cz);
               }
            }
            case DIAGONAL -> {
               if (this.TJImwK2) {
                  double px = (x - z) / 2.0;
                  double pz = -px;
                  yield new oyDPiOVBGKG.pvfA15fQSVM(px, pz);
               } else {
                  double px = (x + z) / 2.0;
                  double pz = px;
                  yield new oyDPiOVBGKG.pvfA15fQSVM(px, pz);
               }
            }
            case DIAMOND -> {
               boolean isSum = this.TJImwK2;
               double cx = isSum ? (x - z + this.wSJeYsJcmjNZf) / 2.0 : (x + z + this.wSJeYsJcmjNZf) / 2.0;
               cx = this.i2Nu6XHKic(cx);
               double cz = isSum ? this.wSJeYsJcmjNZf - cx : cx - this.wSJeYsJcmjNZf;
               yield new oyDPiOVBGKG.pvfA15fQSVM(cx, cz);
            }
         };
      }

      public String vGAMA89H() {
         return this.fStlZIvY9lS8;
      }

      public HighwayNetwork.HighwayType qhulAUwd() {
         return this.QfZy2gWnKInxFPr;
      }

      public boolean BrmKXSYhWhqoB1() {
         return this.TJImwK2;
      }

      public double TXWyNb() {
         return this.wSJeYsJcmjNZf;
      }

      // Reconstructed from the original ObjectMethods bootstrap, in field order.
      @Override
      public final boolean equals(Object value) {
         if (this == value) return true;
         if (!(value instanceof Highway other)) return false;
         return java.util.Objects.equals(this.fStlZIvY9lS8, other.fStlZIvY9lS8)
      && java.util.Objects.equals(this.QfZy2gWnKInxFPr, other.QfZy2gWnKInxFPr)
      && this.TJImwK2 == other.TJImwK2
      && Double.compare(this.wSJeYsJcmjNZf, other.wSJeYsJcmjNZf) == 0
      && Double.compare(this.i2Nu6XHKic, other.i2Nu6XHKic) == 0
      && Double.compare(this.vGAMA89H, other.vGAMA89H) == 0;
      }

      @Override
      public final int hashCode() {
         int hash = 0;
         hash = 31 * hash + java.util.Objects.hashCode(this.fStlZIvY9lS8);
         hash = 31 * hash + java.util.Objects.hashCode(this.QfZy2gWnKInxFPr);
         hash = 31 * hash + Boolean.hashCode(this.TJImwK2);
         hash = 31 * hash + Double.hashCode(this.wSJeYsJcmjNZf);
         hash = 31 * hash + Double.hashCode(this.i2Nu6XHKic);
         hash = 31 * hash + Double.hashCode(this.vGAMA89H);
         return hash;
      }

      @Override
      public final String toString() {
         return "Highway[name=" + this.fStlZIvY9lS8 + ", type=" + this.QfZy2gWnKInxFPr + ", isHorizontal=" + this.TJImwK2 + ", constCoord=" + this.wSJeYsJcmjNZf + ", minCoord=" + this.i2Nu6XHKic + ", maxCoord=" + this.vGAMA89H + "]";
      }
   }

   public enum HighwayType {
      CARDINAL,
      DIAGONAL,
      RING_ROAD,
      DIAMOND,
      GRID;

      HighwayType() {
      }
   }

   public record Intersection(oyDPiOVBGKG.pvfA15fQSVM fStlZIvY9lS8, List<HighwayNetwork.Highway> QfZy2gWnKInxFPr) {


      @Override
      public final String toString() {
         return "Intersection[pos=" + this.fStlZIvY9lS8 + ", highways=" + this.QfZy2gWnKInxFPr + "]";
      }
   }
}
