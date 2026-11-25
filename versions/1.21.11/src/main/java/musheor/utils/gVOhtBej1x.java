package musheor.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import meteordevelopment.meteorclient.MeteorClient;
import musheor.modules.automation.HighwayBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public class gVOhtBej1x {
   public gVOhtBej1x() {
   }

   @NotNull
   public static BlockPos[] fStlZIvY9lS8(int f, int b, boolean leftRail, boolean rightRail) {
      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      boolean placeRails = HighwayBuilder.voxTwgNzyrpaAnP();
      int width = HighwayBuilder.I3psabmOrOI();
      oyDPiOVBGKG.gVOhtBej1x direction = HighwayBuilder.i2Nu6XHKic();
      int playerX = MeteorClient.mc.player.getBlockX();
      int playerY = state.BrmKXSYhWhqoB1();
      int playerZ = MeteorClient.mc.player.getBlockZ();
      int dx;
      int dz;
      int start;
      int end;
      int step;
      int sideSign;
      switch (direction) {
         case NORTH:
            dx = 0;
            dz = 1;
            start = b;
            end = -f;
            step = -1;
            sideSign = 1;
            playerX = state.i2Nu6XHKic();
            break;
         case SOUTH:
            dx = 0;
            dz = 1;
            start = -b;
            end = f;
            step = 1;
            sideSign = -1;
            playerX = state.i2Nu6XHKic();
            break;
         case EAST:
            dx = 1;
            dz = 0;
            start = -b;
            end = f;
            step = 1;
            sideSign = 1;
            playerZ = state.vGAMA89H();
            break;
         case WEST:
            dx = 1;
            dz = 0;
            start = b;
            end = -f;
            step = -1;
            sideSign = -1;
            playerZ = state.vGAMA89H();
            break;
         default:
            return new BlockPos[0];
      }

      int leftBound;
      int rightBound;
      if (width % 2 == 0) {
         leftBound = width / 2;
         rightBound = leftBound - 1;
      } else {
         leftBound = rightBound = (width - 1) / 2;
      }

      List<BlockPos> positions = new ArrayList<>();

      for (int i = start; i != end + step; i += step) {
         for (int j = -leftBound; j <= rightBound; j++) {
            int x = playerX + dx * i + dz * j * sideSign;
            int z = playerZ + dz * i + dx * j * sideSign;
            positions.add(new BlockPos(x, playerY - 1, z));
         }

         if (placeRails) {
            int leftRailX = playerX + dx * i + dz * (-leftBound - 1) * sideSign;
            int leftRailZ = playerZ + dz * i + dx * (-leftBound - 1) * sideSign;
            int rightRailX = playerX + dx * i + dz * (rightBound + 1) * sideSign;
            int rightRailZ = playerZ + dz * i + dx * (rightBound + 1) * sideSign;
            if (leftRail) {
               positions.add(new BlockPos(leftRailX, playerY, leftRailZ));
            }

            if (rightRail) {
               positions.add(new BlockPos(rightRailX, playerY, rightRailZ));
            }
         }
      }

      positions.sort(Comparator.comparingDouble(p -> MeteorClient.mc.player.squaredDistanceTo(Vec3d.of(p))));
      return positions.toArray(new BlockPos[0]);
   }

   public static BlockPos[] fStlZIvY9lS8(int f, int b) {
      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      int width = HighwayBuilder.I3psabmOrOI();
      int height = 2;
      if (HighwayBuilder.qhulAUwd() == HighwayBuilder.BuildMode.Tunnel) {
         height = 3;
      }

      oyDPiOVBGKG.gVOhtBej1x direction = HighwayBuilder.i2Nu6XHKic();
      int playerX = MeteorClient.mc.player.getBlockX();
      int playerY = state.BrmKXSYhWhqoB1();
      int playerZ = MeteorClient.mc.player.getBlockZ();
      int dx;
      int dz;
      int start;
      int end;
      int step;
      int sideSign;
      switch (direction) {
         case NORTH:
            dx = 0;
            dz = 1;
            start = b;
            end = -f;
            step = -1;
            sideSign = 1;
            playerX = state.i2Nu6XHKic();
            break;
         case SOUTH:
            dx = 0;
            dz = 1;
            start = -b;
            end = f;
            step = 1;
            sideSign = -1;
            playerX = state.i2Nu6XHKic();
            break;
         case EAST:
            dx = 1;
            dz = 0;
            start = -b;
            end = f;
            step = 1;
            sideSign = 1;
            playerZ = state.vGAMA89H();
            break;
         case WEST:
            dx = 1;
            dz = 0;
            start = b;
            end = -f;
            step = -1;
            sideSign = -1;
            playerZ = state.vGAMA89H();
            break;
         default:
            return new BlockPos[0];
      }

      int leftBound;
      int rightBound;
      if (width % 2 == 0) {
         leftBound = width / 2;
         rightBound = leftBound - 1;
      } else {
         leftBound = rightBound = (width - 1) / 2;
      }

      List<BlockPos> positions = new ArrayList<>();

      for (int i = start; i != end + step; i += step) {
         for (int j = 0; j <= height; j++) {
            for (int k = -leftBound; k <= rightBound; k++) {
               int x = playerX + dx * i + dz * k * sideSign;
               int z = playerZ + dz * i + dx * k * sideSign;
               positions.add(new BlockPos(x, playerY + j, z));
            }

            if (HighwayBuilder.duJ6sPeetAcBh6P() && j > 0) {
               int leftRailX = playerX + dx * i + dz * (-leftBound - 1) * sideSign;
               int leftRailZ = playerZ + dz * i + dx * (-leftBound - 1) * sideSign;
               int rightRailX = playerX + dx * i + dz * (rightBound + 1) * sideSign;
               int rightRailZ = playerZ + dz * i + dx * (rightBound + 1) * sideSign;
               positions.add(new BlockPos(leftRailX, playerY + j, leftRailZ));
               positions.add(new BlockPos(rightRailX, playerY + j, rightRailZ));
            }
         }
      }

      positions.sort(Comparator.comparingDouble(p -> MeteorClient.mc.player.squaredDistanceTo(Vec3d.of(p))));
      return positions.toArray(new BlockPos[0]);
   }

   public static BlockPos[] QfZy2gWnKInxFPr(int f, int b, boolean leftRail, boolean rightRail) {
      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      boolean placeRails = HighwayBuilder.voxTwgNzyrpaAnP();
      int width = HighwayBuilder.I3psabmOrOI();
      assert MeteorClient.mc.player != null;
      int playerX = MeteorClient.mc.player.getBlockX();
      int playerY = MeteorClient.mc.player.getBlockY();
      int playerZ = MeteorClient.mc.player.getBlockZ();
      int ceilingY = playerY + 3;
      int leftBound;
      int rightBound;
      if (width % 2 == 0) {
         leftBound = width / 2;
         rightBound = leftBound - 1;
      } else {
         leftBound = rightBound = (width - 1) / 2;
      }

      oyDPiOVBGKG.gVOhtBej1x direction = HighwayBuilder.i2Nu6XHKic();
      int dx;
      int dz;
      int start;
      int end;
      int step;
      int sideSign;
      switch (direction) {
         case NORTH:
            dx = 0;
            dz = 1;
            start = b;
            end = -f;
            step = -1;
            sideSign = 1;
            playerX = state.i2Nu6XHKic();
            break;
         case SOUTH:
            dx = 0;
            dz = 1;
            start = -b;
            end = f;
            step = 1;
            sideSign = -1;
            playerX = state.i2Nu6XHKic();
            break;
         case EAST:
            dx = 1;
            dz = 0;
            start = -b;
            end = f;
            step = 1;
            sideSign = 1;
            playerZ = state.vGAMA89H();
            break;
         case WEST:
            dx = 1;
            dz = 0;
            start = b;
            end = -f;
            step = -1;
            sideSign = -1;
            playerZ = state.vGAMA89H();
            break;
         default:
            return new BlockPos[0];
      }

      List<BlockPos> positions = new ArrayList<>();

      for (int i = start; i != end + step; i += step) {
         for (int j = -leftBound; j <= rightBound; j++) {
            int x = playerX + dx * i + dz * j * sideSign;
            int z = playerZ + dz * i + dx * j * sideSign;
            positions.add(new BlockPos(x, ceilingY, z));
         }

         if (placeRails) {
            if (leftRail) {
               int x = playerX + dx * i + dz * (-leftBound - 1) * sideSign;
               int z = playerZ + dz * i + dx * (-leftBound - 1) * sideSign;
               positions.add(new BlockPos(x, ceilingY, z));
            }

            if (rightRail) {
               int x = playerX + dx * i + dz * (rightBound + 1) * sideSign;
               int z = playerZ + dz * i + dx * (rightBound + 1) * sideSign;
               positions.add(new BlockPos(x, ceilingY, z));
            }
         }
      }

      positions.sort(Comparator.comparingDouble(p -> MeteorClient.mc.player.squaredDistanceTo(Vec3d.of(p))));
      return positions.toArray(new BlockPos[0]);
   }

   private static void fStlZIvY9lS8(int vMin, int vMax, int uMin, int uMax, int K, boolean isNESW, int y, List<BlockPos> out) {
      for (int v = vMin; v <= vMax; v++) {
         for (int u = uMin; u <= uMax; u++) {
            if ((u + K + v & 1) == 0) {
               int x = u + K + v >> 1;
               int z = isNESW ? u + K - v >> 1 : v - u - K >> 1;
               out.add(new BlockPos(x, y, z));
            }
         }
      }
   }

   private static int[] fStlZIvY9lS8(obf.U6PQSXqW state, int f, int b) {
      oyDPiOVBGKG.gVOhtBej1x dir = HighwayBuilder.i2Nu6XHKic();
      if (dir != oyDPiOVBGKG.gVOhtBej1x.NORTH_EAST
         && dir != oyDPiOVBGKG.gVOhtBej1x.SOUTH_WEST
         && dir != oyDPiOVBGKG.gVOhtBej1x.NORTH_WEST
         && dir != oyDPiOVBGKG.gVOhtBej1x.SOUTH_EAST) {
         return null;
      }

      int px = state.qhulAUwd();
      int pz = state.TXWyNb();
      boolean isNESW = dir == oyDPiOVBGKG.gVOhtBej1x.NORTH_EAST || dir == oyDPiOVBGKG.gVOhtBej1x.SOUTH_WEST;
      obf.pvfA15fQSVM.gVOhtBej1x detected = state.npfimrDr0hky();
      int K = detected != null ? (int)detected.qhulAUwd : (isNESW ? px + pz : px - pz);
      int HW = (HighwayBuilder.I3psabmOrOI() - 1) / 2;
      int lpx = MeteorClient.mc.player.getBlockX();
      int lpz = MeteorClient.mc.player.getBlockZ();
      int vPlayer = isNESW ? lpx - lpz : lpx + lpz;
      boolean vInc = dir == oyDPiOVBGKG.gVOhtBej1x.NORTH_EAST || dir == oyDPiOVBGKG.gVOhtBej1x.SOUTH_EAST;
      int vMin = vInc ? vPlayer - 2 * b : vPlayer - 2 * f;
      int vMax = vInc ? vPlayer + 2 * f : vPlayer + 2 * b;
      return new int[]{K, HW, vMin, vMax, state.BrmKXSYhWhqoB1(), isNESW ? 1 : 0};
   }

   public static BlockPos[] TJImwK2(int f, int b, boolean leftRail, boolean rightRail) {
      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      assert MeteorClient.mc.player != null;
      if (state.qhulAUwd() == null) {
         state.fStlZIvY9lS8(Integer.valueOf(MeteorClient.mc.player.getBlockX()));
      }

      if (state.BrmKXSYhWhqoB1() == null) {
         state.QfZy2gWnKInxFPr(Integer.valueOf(MeteorClient.mc.player.getBlockY()));
      }

      if (state.TXWyNb() == null) {
         state.TJImwK2(Integer.valueOf(MeteorClient.mc.player.getBlockZ()));
      }

      int[] s = fStlZIvY9lS8(state, f, b);
      if (s == null) {
         return new BlockPos[0];
      }

      int K = s[0];
      int HW = s[1];
      int vMin = s[2];
      int vMax = s[3];
      int py = s[4];
      boolean isNESW = s[5] == 1;
      List<BlockPos> positions = new ArrayList<>();
      fStlZIvY9lS8(vMin, vMax, -HW, HW, K, isNESW, py - 1, positions);
      if (HighwayBuilder.voxTwgNzyrpaAnP()) {
         int leftU = isNESW ? HW + 1 : -(HW + 1);
         int rightU = isNESW ? -(HW + 1) : HW + 1;
         if (leftRail) {
            fStlZIvY9lS8(vMin, vMax, leftU, leftU, K, isNESW, py, positions);
         }

         if (rightRail) {
            fStlZIvY9lS8(vMin, vMax, rightU, rightU, K, isNESW, py, positions);
         }
      }

      positions.sort(Comparator.comparingDouble(p -> MeteorClient.mc.player.squaredDistanceTo(Vec3d.of(p))));
      return positions.toArray(new BlockPos[0]);
   }

   public static BlockPos[] wSJeYsJcmjNZf(int f, int b, boolean leftRail, boolean rightRail) {
      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      assert MeteorClient.mc.player != null;
      if (state.qhulAUwd() == null) {
         state.fStlZIvY9lS8(Integer.valueOf(MeteorClient.mc.player.getBlockX()));
      }

      if (state.BrmKXSYhWhqoB1() == null) {
         state.QfZy2gWnKInxFPr(Integer.valueOf(MeteorClient.mc.player.getBlockY()));
      }

      if (state.TXWyNb() == null) {
         state.TJImwK2(Integer.valueOf(MeteorClient.mc.player.getBlockZ()));
      }

      int[] s = fStlZIvY9lS8(state, f, b);
      if (s == null) {
         return new BlockPos[0];
      }

      int K = s[0];
      int HW = s[1];
      int vMin = s[2];
      int vMax = s[3];
      int py = s[4];
      boolean isNESW = s[5] == 1;
      int ceilingY = py + 3;
      List<BlockPos> positions = new ArrayList<>();
      fStlZIvY9lS8(vMin, vMax, -HW, HW, K, isNESW, ceilingY, positions);
      if (HighwayBuilder.voxTwgNzyrpaAnP()) {
         int leftU = isNESW ? HW + 1 : -(HW + 1);
         int rightU = isNESW ? -(HW + 1) : HW + 1;
         if (leftRail) {
            fStlZIvY9lS8(vMin, vMax, leftU, leftU, K, isNESW, ceilingY, positions);
         }

         if (rightRail) {
            fStlZIvY9lS8(vMin, vMax, rightU, rightU, K, isNESW, ceilingY, positions);
         }
      }

      positions.sort(Comparator.comparingDouble(p -> MeteorClient.mc.player.squaredDistanceTo(Vec3d.of(p))));
      return positions.toArray(new BlockPos[0]);
   }

   public static BlockPos[] QfZy2gWnKInxFPr(int f, int b) {
      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      int height = 2;
      if (HighwayBuilder.qhulAUwd() == HighwayBuilder.BuildMode.Tunnel) {
         height = 3;
      }

      if (state.qhulAUwd() == null) {
         state.fStlZIvY9lS8(Integer.valueOf(MeteorClient.mc.player.getBlockX()));
      }

      if (state.BrmKXSYhWhqoB1() == null) {
         state.QfZy2gWnKInxFPr(Integer.valueOf(MeteorClient.mc.player.getBlockY()));
      }

      if (state.TXWyNb() == null) {
         state.TJImwK2(Integer.valueOf(MeteorClient.mc.player.getBlockZ()));
      }

      int[] s = fStlZIvY9lS8(state, f, b);
      if (s == null) {
         return new BlockPos[0];
      }

      int K = s[0];
      int HW = s[1];
      int vMin = s[2];
      int vMax = s[3];
      int py = s[4];
      boolean isNESW = s[5] == 1;
      List<BlockPos> positions = new ArrayList<>();

      for (int j = 0; j <= height; j++) {
         fStlZIvY9lS8(vMin, vMax, -HW, HW, K, isNESW, py + j, positions);
         if (HighwayBuilder.duJ6sPeetAcBh6P() && j > 0) {
            fStlZIvY9lS8(vMin, vMax, -(HW + 1), -(HW + 1), K, isNESW, py + j, positions);
            fStlZIvY9lS8(vMin, vMax, HW + 1, HW + 1, K, isNESW, py + j, positions);
         }
      }

      positions.sort(Comparator.comparingDouble(p -> MeteorClient.mc.player.squaredDistanceTo(Vec3d.of(p))));
      return positions.toArray(new BlockPos[0]);
   }

   public static BlockPos[] fStlZIvY9lS8() {
      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      int width = HighwayBuilder.I3psabmOrOI();
      int leftBound = 0;
      int rightBound = 0;
      if (HighwayBuilder.i2Nu6XHKic() == null) {
         return new BlockPos[0];
      }

      assert MeteorClient.mc.player != null;
      if (state.qhulAUwd() == null) {
         state.fStlZIvY9lS8(Integer.valueOf(MeteorClient.mc.player.getBlockX()));
      }

      if (state.BrmKXSYhWhqoB1() == null) {
         state.QfZy2gWnKInxFPr(Integer.valueOf(MeteorClient.mc.player.getBlockY()));
      }

      if (state.TXWyNb() == null) {
         state.TJImwK2(Integer.valueOf(MeteorClient.mc.player.getBlockZ()));
      }

      if (width % 2 == 0) {
         leftBound = width / 2 + 1;
      }

      rightBound = leftBound - 1;
      if (width % 2 != 0) {
         leftBound = rightBound = (width - 1) / 2 + 1;
      }

      int height = 2;
      int length = 4;

      return switch (HighwayBuilder.i2Nu6XHKic()) {
         case NORTH -> {
            List<BlockPos> positions = new ArrayList<>();
            if (HighwayBuilder.duJ6sPeetAcBh6P()) {
               for (int i = length; i > 0; i--) {
                  for (int k = 0; k < height; k++) {
                     positions.add(new BlockPos(state.qhulAUwd() - leftBound, state.BrmKXSYhWhqoB1() + k + 1, MeteorClient.mc.player.getBlockZ() + i));
                     positions.add(new BlockPos(state.qhulAUwd() + rightBound, state.BrmKXSYhWhqoB1() + k + 1, MeteorClient.mc.player.getBlockZ() + i));
                  }
               }
            }

            for (int i = length; i > 0; i--) {
               for (int j = -leftBound + 1; j < rightBound; j++) {
                  for (int k = 0; k <= height; k++) {
                     positions.add(new BlockPos(state.qhulAUwd() + j, state.BrmKXSYhWhqoB1() + k, MeteorClient.mc.player.getBlockZ() + i));
                  }
               }
            }

            yield positions.toArray(new BlockPos[0]);
         }
         case SOUTH -> {
            List<BlockPos> positions = new ArrayList<>();
            if (HighwayBuilder.duJ6sPeetAcBh6P()) {
               for (int i = -length; i < 0; i++) {
                  for (int k = 0; k < height; k++) {
                     positions.add(new BlockPos(state.qhulAUwd() + leftBound, state.BrmKXSYhWhqoB1() + k + 1, MeteorClient.mc.player.getBlockZ() + i));
                     positions.add(new BlockPos(state.qhulAUwd() - rightBound, state.BrmKXSYhWhqoB1() + k + 1, MeteorClient.mc.player.getBlockZ() + i));
                  }
               }
            }

            for (int i = -length; i < 0; i++) {
               for (int j = leftBound - 1; j > -rightBound; j--) {
                  for (int k = 0; k <= height; k++) {
                     positions.add(new BlockPos(state.qhulAUwd() + j, state.BrmKXSYhWhqoB1() + k, MeteorClient.mc.player.getBlockZ() + i));
                  }
               }
            }

            yield positions.toArray(new BlockPos[0]);
         }
         case EAST -> {
            List<BlockPos> positions = new ArrayList<>();
            if (HighwayBuilder.duJ6sPeetAcBh6P()) {
               for (int i = -length; i < 0; i++) {
                  for (int k = 0; k < height; k++) {
                     positions.add(new BlockPos(state.qhulAUwd() + i, state.BrmKXSYhWhqoB1() + k + 1, MeteorClient.mc.player.getBlockZ() - leftBound));
                     positions.add(new BlockPos(state.qhulAUwd() + i, state.BrmKXSYhWhqoB1() + k + 1, MeteorClient.mc.player.getBlockZ() + rightBound));
                  }
               }
            }

            for (int i = -length; i < 0; i++) {
               for (int j = -leftBound + 1; j < rightBound; j++) {
                  for (int k = 0; k <= height; k++) {
                     positions.add(new BlockPos(MeteorClient.mc.player.getBlockX() + i, state.BrmKXSYhWhqoB1() + k, state.TXWyNb() + j));
                  }
               }
            }

            yield positions.toArray(new BlockPos[0]);
         }
         case WEST -> {
            List<BlockPos> positions = new ArrayList<>();
            if (HighwayBuilder.duJ6sPeetAcBh6P()) {
               for (int i = length; i > 0; i--) {
                  for (int k = 0; k < height; k++) {
                     positions.add(new BlockPos(MeteorClient.mc.player.getBlockX() + i, state.BrmKXSYhWhqoB1() + k + 1, state.TXWyNb() + leftBound));
                     positions.add(new BlockPos(MeteorClient.mc.player.getBlockX() + i, state.BrmKXSYhWhqoB1() + k + 1, state.TXWyNb() - rightBound));
                  }
               }
            }

            for (int i = length; i > 0; i--) {
               for (int j = leftBound - 1; j > -rightBound; j--) {
                  for (int k = 0; k <= height; k++) {
                     positions.add(new BlockPos(MeteorClient.mc.player.getBlockX() + i, state.BrmKXSYhWhqoB1() + k, state.TXWyNb() + j));
                  }
               }
            }

            yield positions.toArray(new BlockPos[0]);
         }
         default -> new BlockPos[0];
      };
   }

   public static BlockPos[] QfZy2gWnKInxFPr() {
      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      int width = HighwayBuilder.I3psabmOrOI();
      int leftBound = 0;
      int rightBound = 0;
      if (HighwayBuilder.i2Nu6XHKic() == null) {
         return new BlockPos[0];
      }

      assert MeteorClient.mc.player != null;
      if (state.qhulAUwd() == null) {
         state.fStlZIvY9lS8(Integer.valueOf(MeteorClient.mc.player.getBlockX()));
      }

      if (state.BrmKXSYhWhqoB1() == null) {
         state.QfZy2gWnKInxFPr(Integer.valueOf(MeteorClient.mc.player.getBlockY()));
      }

      if (state.BrmKXSYhWhqoB1() == null) {
         state.TJImwK2(Integer.valueOf(MeteorClient.mc.player.getBlockZ()));
      }

      if (width % 2 == 0) {
         leftBound = width / 2;
      }

      rightBound = leftBound - 1;
      if (width % 2 != 0) {
         leftBound = rightBound = (width - 1) / 2;
      }
      return switch (HighwayBuilder.i2Nu6XHKic()) {
         case NORTH -> {
            List<BlockPos> positions = new ArrayList<>();

            for (int i = 4; i <= 7; i++) {
               int z = state.TXWyNb() + i;

               for (int j = -leftBound; j < rightBound; j++) {
                  positions.add(new BlockPos(state.qhulAUwd() + j, state.BrmKXSYhWhqoB1() - 1, z));
               }

               if (HighwayBuilder.voxTwgNzyrpaAnP()) {
                  positions.add(new BlockPos(state.qhulAUwd() - leftBound - 1, state.BrmKXSYhWhqoB1(), z));
                  positions.add(new BlockPos(state.qhulAUwd() + rightBound + 1, state.BrmKXSYhWhqoB1(), z));
               }
            }

            yield positions.toArray(new BlockPos[0]);
         }
         case SOUTH -> {
            List<BlockPos> positions = new ArrayList<>();

            for (int i = 4; i <= 7; i++) {
               int z = state.TXWyNb() - i;

               for (int j = leftBound; j > -rightBound; j--) {
                  positions.add(new BlockPos(state.qhulAUwd() + j, state.BrmKXSYhWhqoB1() - 1, z));
               }

               if (HighwayBuilder.voxTwgNzyrpaAnP()) {
                  positions.add(new BlockPos(state.qhulAUwd() + leftBound + 1, state.BrmKXSYhWhqoB1(), z));
                  positions.add(new BlockPos(state.qhulAUwd() - rightBound - 1, state.BrmKXSYhWhqoB1(), z));
               }
            }

            yield positions.toArray(new BlockPos[0]);
         }
         case EAST -> {
            List<BlockPos> positions = new ArrayList<>();

            for (int i = 4; i <= 7; i++) {
               int x = state.qhulAUwd() - i;

               for (int j = -leftBound; j < rightBound; j++) {
                  positions.add(new BlockPos(x, state.BrmKXSYhWhqoB1() - 1, state.TXWyNb() + j));
               }

               if (HighwayBuilder.voxTwgNzyrpaAnP()) {
                  positions.add(new BlockPos(x, state.BrmKXSYhWhqoB1(), state.TXWyNb() - leftBound - 1));
                  positions.add(new BlockPos(x, state.BrmKXSYhWhqoB1(), state.TXWyNb() + rightBound + 1));
               }
            }

            yield positions.toArray(new BlockPos[0]);
         }
         case WEST -> {
            List<BlockPos> positions = new ArrayList<>();

            for (int i = 4; i <= 7; i++) {
               int x = state.qhulAUwd() + i;

               for (int j = leftBound; j > -rightBound; j--) {
                  positions.add(new BlockPos(x, state.BrmKXSYhWhqoB1() - 1, state.TXWyNb() + j));
               }

               if (HighwayBuilder.voxTwgNzyrpaAnP()) {
                  positions.add(new BlockPos(x, state.BrmKXSYhWhqoB1(), state.TXWyNb() + leftBound + 1));
                  positions.add(new BlockPos(x, state.BrmKXSYhWhqoB1(), state.TXWyNb() - rightBound - 1));
               }
            }

            yield positions.toArray(new BlockPos[0]);
         }
         default -> new BlockPos[0];
      };
   }

   public static BlockPos[] TJImwK2() {
      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      int width = HighwayBuilder.I3psabmOrOI();
      int leftBound = 0;
      int rightBound = 0;
      if (HighwayBuilder.i2Nu6XHKic() == null) {
         return new BlockPos[0];
      }

      assert MeteorClient.mc.player != null;
      if (state.qhulAUwd() == null) {
         state.fStlZIvY9lS8(Integer.valueOf(MeteorClient.mc.player.getBlockX()));
      }

      if (state.BrmKXSYhWhqoB1() == null) {
         state.QfZy2gWnKInxFPr(Integer.valueOf(MeteorClient.mc.player.getBlockY()));
      }

      if (state.TXWyNb() == null) {
         state.TJImwK2(Integer.valueOf(MeteorClient.mc.player.getBlockZ()));
      }

      if (width % 2 == 0) {
         leftBound = width / 2 + 1;
      }

      rightBound = leftBound - 1;
      if (width % 2 != 0) {
         leftBound = rightBound = (width - 1) / 2 + 1;
      }

      int height = 0;
      if (HighwayBuilder.qhulAUwd() == HighwayBuilder.BuildMode.Pave) {
         height = 3;
      }

      if (HighwayBuilder.qhulAUwd() == HighwayBuilder.BuildMode.Tunnel) {
         height = 4;
      }
      return switch (HighwayBuilder.i2Nu6XHKic()) {
         case NORTH -> {
            List<BlockPos> positions = new ArrayList<>();
            if (HighwayBuilder.duJ6sPeetAcBh6P()) {
               for (int i = 0; i > -10; i--) {
                  for (int k = 0; k < height; k++) {
                     positions.add(new BlockPos(state.qhulAUwd() - leftBound - 1, state.BrmKXSYhWhqoB1() + k + 1, MeteorClient.mc.player.getBlockZ() + i));
                     positions.add(new BlockPos(state.qhulAUwd() + rightBound + 1, state.BrmKXSYhWhqoB1() + k + 1, MeteorClient.mc.player.getBlockZ() + i));
                     positions.add(new BlockPos(state.qhulAUwd() - leftBound, state.BrmKXSYhWhqoB1() + k + 1, MeteorClient.mc.player.getBlockZ() + i));
                     positions.add(new BlockPos(state.qhulAUwd() + rightBound, state.BrmKXSYhWhqoB1() + k + 1, MeteorClient.mc.player.getBlockZ() + i));
                  }
               }
            }

            for (int i = 0; i >= -10; i--) {
               for (int j = -leftBound; j < rightBound; j++) {
                  for (int k = 0; k <= height; k++) {
                     positions.add(new BlockPos(state.qhulAUwd() + j, state.BrmKXSYhWhqoB1() + k, MeteorClient.mc.player.getBlockZ() + i));
                  }
               }
            }

            yield positions.toArray(new BlockPos[0]);
         }
         case SOUTH -> {
            List<BlockPos> positions = new ArrayList<>();
            if (HighwayBuilder.duJ6sPeetAcBh6P()) {
               for (int i = 10; i >= 0; i--) {
                  for (int k = 0; k < height; k++) {
                     positions.add(new BlockPos(state.qhulAUwd() + leftBound, state.BrmKXSYhWhqoB1() + k + 1, MeteorClient.mc.player.getBlockZ() + i));
                     positions.add(new BlockPos(state.qhulAUwd() - rightBound, state.BrmKXSYhWhqoB1() + k + 1, MeteorClient.mc.player.getBlockZ() + i));
                     positions.add(new BlockPos(state.qhulAUwd() + leftBound + 1, state.BrmKXSYhWhqoB1() + k + 1, MeteorClient.mc.player.getBlockZ() + i));
                     positions.add(new BlockPos(state.qhulAUwd() - rightBound - 1, state.BrmKXSYhWhqoB1() + k + 1, MeteorClient.mc.player.getBlockZ() + i));
                  }
               }
            }

            for (int i = 10; i >= 0; i--) {
               for (int j = leftBound; j > -rightBound; j--) {
                  for (int k = 0; k <= height; k++) {
                     positions.add(new BlockPos(state.qhulAUwd() + j, state.BrmKXSYhWhqoB1() + k, MeteorClient.mc.player.getBlockZ() + i));
                  }
               }
            }

            yield positions.toArray(new BlockPos[0]);
         }
         case EAST -> {
            List<BlockPos> positions = new ArrayList<>();

            for (int i = 10; i >= 0; i--) {
               for (int k = 0; k < height; k++) {
                  positions.add(
                     new BlockPos(MeteorClient.mc.player.getBlockX() + i, state.BrmKXSYhWhqoB1() + k + 1, MeteorClient.mc.player.getBlockZ() - leftBound - 1)
                  );
                  positions.add(
                     new BlockPos(MeteorClient.mc.player.getBlockX() + i, state.BrmKXSYhWhqoB1() + k + 1, MeteorClient.mc.player.getBlockZ() + rightBound + 1)
                  );
                  positions.add(
                     new BlockPos(MeteorClient.mc.player.getBlockX() + i, state.BrmKXSYhWhqoB1() + k + 1, MeteorClient.mc.player.getBlockZ() - leftBound)
                  );
                  positions.add(
                     new BlockPos(MeteorClient.mc.player.getBlockX() + i, state.BrmKXSYhWhqoB1() + k + 1, MeteorClient.mc.player.getBlockZ() + rightBound)
                  );
               }
            }

            for (int i = 10; i >= 0; i--) {
               for (int j = -leftBound + 1; j < rightBound; j++) {
                  for (int k = 0; k <= height; k++) {
                     positions.add(new BlockPos(MeteorClient.mc.player.getBlockX() + i, state.BrmKXSYhWhqoB1() + k, state.TXWyNb() + j));
                  }
               }
            }

            yield positions.toArray(new BlockPos[0]);
         }
         case WEST -> {
            List<BlockPos> positions = new ArrayList<>();
            if (HighwayBuilder.duJ6sPeetAcBh6P()) {
               for (int i = -10; i <= 0; i++) {
                  for (int k = 0; k < height; k++) {
                     positions.add(new BlockPos(MeteorClient.mc.player.getBlockX() + i, state.BrmKXSYhWhqoB1() + k + 1, state.TXWyNb() + leftBound));
                     positions.add(new BlockPos(MeteorClient.mc.player.getBlockX() + i, state.BrmKXSYhWhqoB1() + k + 1, state.TXWyNb() - rightBound));
                     positions.add(new BlockPos(MeteorClient.mc.player.getBlockX() + i, state.BrmKXSYhWhqoB1() + k + 1, state.TXWyNb() + leftBound + 1));
                     positions.add(new BlockPos(MeteorClient.mc.player.getBlockX() + i, state.BrmKXSYhWhqoB1() + k + 1, state.TXWyNb() - rightBound - 1));
                  }
               }
            }

            for (int i = -10; i <= 0; i++) {
               for (int j = leftBound; j > -rightBound; j--) {
                  for (int k = 0; k <= height; k++) {
                     positions.add(new BlockPos(MeteorClient.mc.player.getBlockX() + i, state.BrmKXSYhWhqoB1() + k, state.TXWyNb() + j));
                  }
               }
            }

            yield positions.toArray(new BlockPos[0]);
         }
         default -> new BlockPos[0];
      };
   }

   public static BlockPos[] wSJeYsJcmjNZf() {
      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      int width = HighwayBuilder.I3psabmOrOI();
      int leftBound = 0;
      int rightBound = 0;
      int height = 0;
      if (HighwayBuilder.qhulAUwd() == HighwayBuilder.BuildMode.Pave) {
         height = 3;
      }

      if (HighwayBuilder.qhulAUwd() == HighwayBuilder.BuildMode.Tunnel) {
         height = 4;
      }

      if (width % 2 == 0) {
         leftBound = width / 2;
      }

      rightBound = leftBound - 1;
      if (width % 2 != 0) {
         leftBound = rightBound = (width - 1) / 2;
      }

      assert MeteorClient.mc.player != null;
      if (state.qhulAUwd() == null) {
         state.fStlZIvY9lS8(Integer.valueOf(MeteorClient.mc.player.getBlockX()));
      }

      if (state.BrmKXSYhWhqoB1() == null) {
         state.QfZy2gWnKInxFPr(Integer.valueOf(MeteorClient.mc.player.getBlockY()));
      }

      if (state.TXWyNb() == null) {
         state.TJImwK2(Integer.valueOf(MeteorClient.mc.player.getBlockZ()));
      }

      oyDPiOVBGKG.gVOhtBej1x direction = HighwayBuilder.i2Nu6XHKic();

      return switch (direction) {
         case NORTH_WEST -> {
            List<BlockPos> positions = new ArrayList<>();

            for (int i = -8; i <= -3; i++) {
               for (int j = 0; j <= height; j++) {
                  for (int left = leftBound; left > 0; left--) {
                     positions.add(new BlockPos(state.qhulAUwd() + i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() + left + i));
                  }

                  if (j > 0) {
                     positions.add(new BlockPos(state.qhulAUwd() + i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() + leftBound + 1 + i));
                     positions.add(new BlockPos(state.qhulAUwd() + i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() + leftBound + 2 + i));
                  }

                  for (int right = rightBound; right >= 0; right--) {
                     positions.add(new BlockPos(state.qhulAUwd() + right + i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() + i));
                  }

                  if (j > 0) {
                     positions.add(new BlockPos(state.qhulAUwd() + rightBound + 1 + i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() + i));
                     positions.add(new BlockPos(state.qhulAUwd() + rightBound + 2 + i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() + i));
                  }
               }
            }

            yield positions.toArray(new BlockPos[0]);
         }
         case NORTH_EAST -> {
            List<BlockPos> positions = new ArrayList<>();

            for (int i = -8; i <= -3; i++) {
               for (int j = 0; j <= height; j++) {
                  for (int left = leftBound; left > 0; left--) {
                     positions.add(new BlockPos(state.qhulAUwd() - i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() + left + i));
                  }

                  if (j > 0) {
                     positions.add(new BlockPos(state.qhulAUwd() - i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() + leftBound + 1 + i));
                     positions.add(new BlockPos(state.qhulAUwd() - i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() + leftBound + 2 + i));
                  }

                  for (int right = rightBound; right >= 0; right--) {
                     positions.add(new BlockPos(state.qhulAUwd() - right - i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() + i));
                  }

                  if (j > 0) {
                     positions.add(new BlockPos(state.qhulAUwd() - rightBound - 1 - i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() + i));
                     positions.add(new BlockPos(state.qhulAUwd() - rightBound - 2 - i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() + i));
                  }
               }
            }

            yield positions.toArray(new BlockPos[0]);
         }
         case SOUTH_EAST -> {
            List<BlockPos> positions = new ArrayList<>();

            for (int i = -8; i <= -3; i++) {
               for (int j = 0; j <= height; j++) {
                  for (int left = leftBound; left > 0; left--) {
                     positions.add(new BlockPos(state.qhulAUwd() - i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() - left - i));
                  }

                  if (j > 0) {
                     positions.add(new BlockPos(state.qhulAUwd() - i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() - leftBound - 1 - i));
                     positions.add(new BlockPos(state.qhulAUwd() - i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() - leftBound - 2 - i));
                  }

                  for (int right = rightBound; right >= 0; right--) {
                     positions.add(new BlockPos(state.qhulAUwd() - right - i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() - i));
                  }

                  if (j > 0) {
                     positions.add(new BlockPos(state.qhulAUwd() - rightBound - 1 - i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() - i));
                     positions.add(new BlockPos(state.qhulAUwd() - rightBound - 2 - i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() - i));
                  }
               }
            }

            yield positions.toArray(new BlockPos[0]);
         }
         case SOUTH_WEST -> {
            List<BlockPos> positions = new ArrayList<>();

            for (int i = -8; i <= -3; i++) {
               for (int j = 0; j <= height; j++) {
                  for (int left = leftBound; left > 0; left--) {
                     positions.add(new BlockPos(state.qhulAUwd() + i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() - left - i));
                  }

                  if (j > 0) {
                     positions.add(new BlockPos(state.qhulAUwd() + i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() - leftBound - 1 - i));
                     positions.add(new BlockPos(state.qhulAUwd() + i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() - leftBound - 2 - i));
                  }

                  for (int right = rightBound; right >= 0; right--) {
                     positions.add(new BlockPos(state.qhulAUwd() + right + i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() - i));
                  }

                  if (j > 0) {
                     positions.add(new BlockPos(state.qhulAUwd() + rightBound + 1 + i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() - i));
                     positions.add(new BlockPos(state.qhulAUwd() + rightBound + 2 + i, state.BrmKXSYhWhqoB1() + j, state.TXWyNb() - i));
                  }
               }
            }

            yield positions.toArray(new BlockPos[0]);
         }
         default -> new BlockPos[0];
      };
   }
}
