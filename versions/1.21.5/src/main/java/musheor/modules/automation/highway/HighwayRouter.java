package musheor.modules.automation.highway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import musheor.utils.oyDPiOVBGKG;

public class HighwayRouter {
   public static HighwayRouter.Route fStlZIvY9lS8(double px, double pz, double gx, double gz) {
      oyDPiOVBGKG.pvfA15fQSVM player = new oyDPiOVBGKG.pvfA15fQSVM(px, pz);
      HighwayNetwork.Highway playerHighway = fStlZIvY9lS8(px, pz);
      if (playerHighway == null) {
         return null;
      } else {
         HighwayRouter.SnapResult goalSnap = QfZy2gWnKInxFPr(gx, gz);
         if (goalSnap == null) {
            return null;
         } else {
            oyDPiOVBGKG.pvfA15fQSVM goalPos = goalSnap.fStlZIvY9lS8;
            HighwayNetwork.Highway goalHighway = goalSnap.QfZy2gWnKInxFPr;
            if (playerHighway.equals(goalHighway)) {
               return new HighwayRouter.Route(List.of(new HighwayRouter.Leg(player, goalPos, playerHighway, HighwayNetwork.fStlZIvY9lS8(player, goalPos))));
            } else {
               oyDPiOVBGKG.pvfA15fQSVM intersection = fStlZIvY9lS8(playerHighway, goalHighway, player, goalPos);
               if (intersection != null) {
                  HighwayRouter.Leg leg1 = new HighwayRouter.Leg(player, intersection, playerHighway, HighwayNetwork.fStlZIvY9lS8(player, intersection));
                  HighwayRouter.Leg leg2 = new HighwayRouter.Leg(intersection, goalPos, goalHighway, HighwayNetwork.fStlZIvY9lS8(intersection, goalPos));
                  return new HighwayRouter.Route(List.of(leg1, leg2));
               } else {
                  return fStlZIvY9lS8(player, playerHighway, goalPos, goalHighway);
               }
            }
         }
      }
   }

   private static HighwayNetwork.Highway fStlZIvY9lS8(double x, double z) {
      HighwayNetwork.Highway best = null;
      double bestDist = Double.MAX_VALUE;
      oyDPiOVBGKG.pvfA15fQSVM pos = new oyDPiOVBGKG.pvfA15fQSVM(x, z);

      for (HighwayNetwork.Highway hw : HighwayNetwork.fStlZIvY9lS8) {
         double d = HighwayNetwork.fStlZIvY9lS8(hw.fStlZIvY9lS8(x, z), pos);
         if (d < bestDist) {
            bestDist = d;
            best = hw;
         }
      }

      return bestDist <= 150.0 ? best : null;
   }

   private static HighwayRouter.SnapResult QfZy2gWnKInxFPr(double x, double z) {
      HighwayNetwork.Highway best = null;
      oyDPiOVBGKG.pvfA15fQSVM bestPoint = null;
      double bestDist = Double.MAX_VALUE;
      oyDPiOVBGKG.pvfA15fQSVM pos = new oyDPiOVBGKG.pvfA15fQSVM(x, z);

      for (HighwayNetwork.Highway hw : HighwayNetwork.fStlZIvY9lS8) {
         oyDPiOVBGKG.pvfA15fQSVM nearest = hw.fStlZIvY9lS8(x, z);
         double d = HighwayNetwork.fStlZIvY9lS8(nearest, pos);
         if (d < bestDist) {
            bestDist = d;
            bestPoint = nearest;
            best = hw;
         }
      }

      return best != null ? new HighwayRouter.SnapResult(bestPoint, best) : null;
   }

   private static oyDPiOVBGKG.pvfA15fQSVM fStlZIvY9lS8(
      HighwayNetwork.Highway hwA, HighwayNetwork.Highway hwB, oyDPiOVBGKG.pvfA15fQSVM player, oyDPiOVBGKG.pvfA15fQSVM goal
   ) {
      List<oyDPiOVBGKG.pvfA15fQSVM> candidates = new ArrayList<>();
      oyDPiOVBGKG.pvfA15fQSVM direct = HighwayNetwork.fStlZIvY9lS8(hwA, hwB);
      if (direct != null
         && hwA.fStlZIvY9lS8(direct.fStlZIvY9lS8(), direct.QfZy2gWnKInxFPr(), 1.0)
         && hwB.fStlZIvY9lS8(direct.fStlZIvY9lS8(), direct.QfZy2gWnKInxFPr(), 1.0)) {
         candidates.add(direct);
      }

      for (HighwayNetwork.Intersection node : HighwayNetwork.QfZy2gWnKInxFPr) {
         if (hwA.fStlZIvY9lS8(node.fStlZIvY9lS8().fStlZIvY9lS8(), node.fStlZIvY9lS8().QfZy2gWnKInxFPr(), 1.0)
            && hwB.fStlZIvY9lS8(node.fStlZIvY9lS8().fStlZIvY9lS8(), node.fStlZIvY9lS8().QfZy2gWnKInxFPr(), 1.0)) {
            candidates.add(node.fStlZIvY9lS8());
         }
      }

      if (candidates.isEmpty()) {
         return null;
      } else {
         oyDPiOVBGKG.pvfA15fQSVM best = null;
         double bestCost = Double.MAX_VALUE;

         for (oyDPiOVBGKG.pvfA15fQSVM candidate : candidates) {
            double cost = HighwayNetwork.fStlZIvY9lS8(player, candidate) + HighwayNetwork.fStlZIvY9lS8(candidate, goal);
            double directDist = HighwayNetwork.fStlZIvY9lS8(player, goal);
            if (!(cost > directDist * 1.5) && cost < bestCost) {
               bestCost = cost;
               best = candidate;
            }
         }

         return best;
      }
   }

   private static HighwayRouter.Route fStlZIvY9lS8(
      oyDPiOVBGKG.pvfA15fQSVM player, HighwayNetwork.Highway playerHighway, oyDPiOVBGKG.pvfA15fQSVM goal, HighwayNetwork.Highway goalHighway
   ) {
      int startNode = fStlZIvY9lS8(playerHighway, player, goal);
      int goalNode = fStlZIvY9lS8(goalHighway, goal);
      if (startNode < 0 || goalNode < 0) {
         return null;
      } else if (startNode == goalNode) {
         return HighwayRouter.Route.fStlZIvY9lS8();
      } else {
         Map<Integer, List<HighwayNetwork.Edge>> adj = HighwayNetwork.TJImwK2();
         int n = HighwayNetwork.QfZy2gWnKInxFPr.size();
         double[] dist = new double[n];
         int[] prev = new int[n];
         HighwayNetwork.Edge[] prevEdge = new HighwayNetwork.Edge[n];
         boolean[] visited = new boolean[n];
         Arrays.fill(dist, Double.MAX_VALUE);
         Arrays.fill(prev, -1);
         dist[startNode] = 0.0;
         PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingDouble(a -> Double.longBitsToDouble(a[0])));
         pq.offer(new long[]{Double.doubleToLongBits(0.0), startNode});

         while (!pq.isEmpty()) {
            long[] top = pq.poll();
            int curr = (int)top[1];
            double d = Double.longBitsToDouble(top[0]);
            if (!visited[curr]) {
               visited[curr] = true;
               if (curr == goalNode) {
                  break;
               }

               for (HighwayNetwork.Edge edge : adj.getOrDefault(curr, List.of())) {
                  double nd = d + edge.TJImwK2();
                  if (nd < dist[edge.QfZy2gWnKInxFPr()]) {
                     dist[edge.QfZy2gWnKInxFPr()] = nd;
                     prev[edge.QfZy2gWnKInxFPr()] = curr;
                     prevEdge[edge.QfZy2gWnKInxFPr()] = edge;
                     pq.offer(new long[]{Double.doubleToLongBits(nd), edge.QfZy2gWnKInxFPr()});
                  }
               }
            }
         }

         if (dist[goalNode] == Double.MAX_VALUE) {
            return null;
         } else {
            List<HighwayRouter.Leg> legs = new ArrayList<>();
            int cur = goalNode;

            while (prev[cur] != -1) {
               int from = prev[cur];
               HighwayNetwork.Edge edgex = prevEdge[cur];
               legs.add(
                  new HighwayRouter.Leg(
                     HighwayNetwork.QfZy2gWnKInxFPr.get(from).fStlZIvY9lS8(),
                     HighwayNetwork.QfZy2gWnKInxFPr.get(cur).fStlZIvY9lS8(),
                     edgex.wSJeYsJcmjNZf(),
                     edgex.TJImwK2()
                  )
               );
               cur = from;
            }

            Collections.reverse(legs);
            oyDPiOVBGKG.pvfA15fQSVM startPos = HighwayNetwork.QfZy2gWnKInxFPr.get(startNode).fStlZIvY9lS8();
            if (HighwayNetwork.fStlZIvY9lS8(player, startPos) > 10.0) {
               legs.add(0, new HighwayRouter.Leg(player, startPos, playerHighway, HighwayNetwork.fStlZIvY9lS8(player, startPos)));
            }

            oyDPiOVBGKG.pvfA15fQSVM lastPos = legs.isEmpty() ? player : legs.get(legs.size() - 1).QfZy2gWnKInxFPr();
            if (HighwayNetwork.fStlZIvY9lS8(lastPos, goal) > 10.0) {
               legs.add(new HighwayRouter.Leg(lastPos, goal, goalHighway, HighwayNetwork.fStlZIvY9lS8(lastPos, goal)));
            }

            return new HighwayRouter.Route(legs);
         }
      }
   }

   private static int fStlZIvY9lS8(HighwayNetwork.Highway hw, oyDPiOVBGKG.pvfA15fQSVM from, oyDPiOVBGKG.pvfA15fQSVM goal) {
      List<Integer> candidates = HighwayNetwork.fStlZIvY9lS8(hw);
      int best = -1;
      double bestCost = Double.MAX_VALUE;

      for (int idx : candidates) {
         oyDPiOVBGKG.pvfA15fQSVM p = HighwayNetwork.QfZy2gWnKInxFPr.get(idx).fStlZIvY9lS8();
         double cost = HighwayNetwork.fStlZIvY9lS8(from, p) + HighwayNetwork.fStlZIvY9lS8(p, goal);
         if (cost < bestCost) {
            bestCost = cost;
            best = idx;
         }
      }

      return best;
   }

   private static int fStlZIvY9lS8(HighwayNetwork.Highway hw, oyDPiOVBGKG.pvfA15fQSVM target) {
      List<Integer> candidates = HighwayNetwork.fStlZIvY9lS8(hw);
      int best = -1;
      double bestDist = Double.MAX_VALUE;

      for (int idx : candidates) {
         double d = HighwayNetwork.fStlZIvY9lS8(target, HighwayNetwork.QfZy2gWnKInxFPr.get(idx).fStlZIvY9lS8());
         if (d < bestDist) {
            bestDist = d;
            best = idx;
         }
      }

      return best;
   }

   public record Leg(oyDPiOVBGKG.pvfA15fQSVM fStlZIvY9lS8, oyDPiOVBGKG.pvfA15fQSVM QfZy2gWnKInxFPr, HighwayNetwork.Highway TJImwK2, double wSJeYsJcmjNZf) {
   }

   public static class Route {
      public final List<HighwayRouter.Leg> fStlZIvY9lS8;
      public final double QfZy2gWnKInxFPr;
      public final List<oyDPiOVBGKG.pvfA15fQSVM> TJImwK2;

      private Route(List<HighwayRouter.Leg> legs) {
         this.fStlZIvY9lS8 = Collections.unmodifiableList(fStlZIvY9lS8(legs));
         this.QfZy2gWnKInxFPr = this.fStlZIvY9lS8.stream().mapToDouble(HighwayRouter.Leg::wSJeYsJcmjNZf).sum();
         List<oyDPiOVBGKG.pvfA15fQSVM> wp = new ArrayList<>();
         if (!this.fStlZIvY9lS8.isEmpty()) {
            wp.add(this.fStlZIvY9lS8.get(0).fStlZIvY9lS8());
         }

         for (HighwayRouter.Leg leg : this.fStlZIvY9lS8) {
            wp.add(leg.QfZy2gWnKInxFPr());
         }

         this.TJImwK2 = Collections.unmodifiableList(wp);
      }

      public static HighwayRouter.Route fStlZIvY9lS8() {
         return new HighwayRouter.Route(List.of());
      }

      private static List<HighwayRouter.Leg> fStlZIvY9lS8(List<HighwayRouter.Leg> legs) {
         if (legs.size() < 2) {
            return new ArrayList<>(legs);
         } else {
            List<HighwayRouter.Leg> out = new ArrayList<>();
            HighwayRouter.Leg cur = legs.get(0);

            for (int i = 1; i < legs.size(); i++) {
               HighwayRouter.Leg next = legs.get(i);
               if (cur.TJImwK2().equals(next.TJImwK2()) && fStlZIvY9lS8(cur, next)) {
                  cur = new HighwayRouter.Leg(cur.fStlZIvY9lS8(), next.QfZy2gWnKInxFPr(), cur.TJImwK2(), cur.wSJeYsJcmjNZf() + next.wSJeYsJcmjNZf());
               } else {
                  out.add(cur);
                  cur = next;
               }
            }

            out.add(cur);
            return out;
         }
      }

      private static boolean fStlZIvY9lS8(HighwayRouter.Leg a, HighwayRouter.Leg b) {
         double ax = a.QfZy2gWnKInxFPr().fStlZIvY9lS8() - a.fStlZIvY9lS8().fStlZIvY9lS8();
         double az = a.QfZy2gWnKInxFPr().QfZy2gWnKInxFPr() - a.fStlZIvY9lS8().QfZy2gWnKInxFPr();
         double bx = b.QfZy2gWnKInxFPr().fStlZIvY9lS8() - b.fStlZIvY9lS8().fStlZIvY9lS8();
         double bz = b.QfZy2gWnKInxFPr().QfZy2gWnKInxFPr() - b.fStlZIvY9lS8().QfZy2gWnKInxFPr();
         return ax * bx + az * bz > 0.0;
      }
   }

   private record SnapResult(oyDPiOVBGKG.pvfA15fQSVM fStlZIvY9lS8, HighwayNetwork.Highway QfZy2gWnKInxFPr) {
   }
}
