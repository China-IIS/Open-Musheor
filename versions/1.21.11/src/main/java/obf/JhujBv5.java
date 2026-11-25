package obf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import musheor.utils.oyDPiOVBGKG;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class JhujBv5 {
   private static final MinecraftClient fStlZIvY9lS8 = MinecraftClient.getInstance();
   private static final JhujBv5 QfZy2gWnKInxFPr = new JhujBv5();
   private JhujBv5.U6PQSXqW TJImwK2 = JhujBv5.U6PQSXqW.TopLeft;
   private BlockPos wSJeYsJcmjNZf;
   private Direction i2Nu6XHKic;
   private Direction vGAMA89H;
   private Direction qhulAUwd;
   private int BrmKXSYhWhqoB1;
   private int TXWyNb;
   private int I3psabmOrOI;
   private final Map<Long, JhujBv5.gVOhtBej1x> duJ6sPeetAcBh6P = new LinkedHashMap<>();
   private final List<String> j376sIQ = new ArrayList<>();
   private static final Pattern V7zKJ4n6D = Pattern.compile("\\d{1,4}");
   private static final Pattern voxTwgNzyrpaAnP = Pattern.compile("[(\\[{]([^)\\]}]*)[)\\]}]");

   public JhujBv5() {
   }

   public static JhujBv5 fStlZIvY9lS8() {
      return QfZy2gWnKInxFPr;
   }

   public boolean QfZy2gWnKInxFPr() {
      return this.wSJeYsJcmjNZf != null && this.i2Nu6XHKic != null;
   }

   public void fStlZIvY9lS8(ItemFrameEntity frame, JhujBv5.U6PQSXqW origin, float playerYaw) {
      this.TJImwK2 = origin;
      this.wSJeYsJcmjNZf = frame.getAttachedBlockPos();
      this.i2Nu6XHKic = frame.getHorizontalFacing();
      if (this.i2Nu6XHKic.getAxis().isVertical()) {
         Direction away = oyDPiOVBGKG.fStlZIvY9lS8(playerYaw, 0.0F);
         this.qhulAUwd = origin == JhujBv5.U6PQSXqW.TopLeft ? away : away.getOpposite();
         this.vGAMA89H = this.qhulAUwd.rotateYClockwise();
         if (this.i2Nu6XHKic == Direction.DOWN) {
            this.vGAMA89H = this.vGAMA89H.getOpposite();
         }
      } else {
         this.vGAMA89H = this.i2Nu6XHKic.rotateYCounterclockwise();
         this.qhulAUwd = origin == JhujBv5.U6PQSXqW.TopLeft ? Direction.DOWN : Direction.UP;
      }

      this.duJ6sPeetAcBh6P.clear();
      this.j376sIQ.clear();
   }

   public void TJImwK2() {
      this.wSJeYsJcmjNZf = null;
      this.i2Nu6XHKic = null;
      this.vGAMA89H = null;
      this.qhulAUwd = null;
      this.duJ6sPeetAcBh6P.clear();
      this.j376sIQ.clear();
   }

   public JhujBv5.U6PQSXqW wSJeYsJcmjNZf() {
      return this.TJImwK2;
   }

   public BlockPos i2Nu6XHKic() {
      return this.wSJeYsJcmjNZf;
   }

   public Direction vGAMA89H() {
      return this.i2Nu6XHKic;
   }

   public Direction qhulAUwd() {
      return this.vGAMA89H;
   }

   public Direction BrmKXSYhWhqoB1() {
      return this.qhulAUwd;
   }

   public int TXWyNb() {
      return this.BrmKXSYhWhqoB1;
   }

   public int I3psabmOrOI() {
      return this.TXWyNb;
   }

   public int duJ6sPeetAcBh6P() {
      return this.I3psabmOrOI;
   }

   public void fStlZIvY9lS8(int width, int height) {
      this.BrmKXSYhWhqoB1 = Math.max(0, width);
      this.TXWyNb = Math.max(0, height);
      this.duJ6sPeetAcBh6P
         .values()
         .removeIf(cell -> this.BrmKXSYhWhqoB1 > 0 && cell.fStlZIvY9lS8 >= this.BrmKXSYhWhqoB1 || this.TXWyNb > 0 && cell.QfZy2gWnKInxFPr >= this.TXWyNb);
   }

   public void fStlZIvY9lS8(int indexBase) {
      this.I3psabmOrOI = indexBase;
   }

   public int j376sIQ() {
      if (this.QfZy2gWnKInxFPr() && fStlZIvY9lS8.world != null) {
         for (Entity entity : fStlZIvY9lS8.world.getEntities()) {
            if (entity instanceof ItemFrameEntity frame && frame.getHorizontalFacing() == this.i2Nu6XHKic) {
               Vec3i delta = frame.getAttachedBlockPos().subtract(this.wSJeYsJcmjNZf);
               if (fStlZIvY9lS8(delta, this.i2Nu6XHKic) == 0) {
                  int col = fStlZIvY9lS8(delta, this.vGAMA89H);
                  int row = fStlZIvY9lS8(delta, this.qhulAUwd);
                  if (col >= 0 && row >= 0 && (this.BrmKXSYhWhqoB1 <= 0 || col < this.BrmKXSYhWhqoB1) && (this.TXWyNb <= 0 || row < this.TXWyNb)) {
                     JhujBv5.gVOhtBej1x cell = this.duJ6sPeetAcBh6P
                        .computeIfAbsent(QfZy2gWnKInxFPr(col, row), k -> new JhujBv5.gVOhtBej1x(col, row, frame.getAttachedBlockPos()));
                     cell.wSJeYsJcmjNZf = frame.getId();
                     cell.i2Nu6XHKic = !frame.getHeldItemStack().isEmpty();
                  }
               }
            }
         }

         return this.duJ6sPeetAcBh6P.size();
      } else {
         return this.duJ6sPeetAcBh6P.size();
      }
   }

   public void V7zKJ4n6D() {
      for (JhujBv5.gVOhtBej1x cell : this.duJ6sPeetAcBh6P.values()) {
         cell.TJImwK2();
      }
   }

   public void voxTwgNzyrpaAnP() {
      for (JhujBv5.gVOhtBej1x cell : this.duJ6sPeetAcBh6P.values()) {
         ItemFrameEntity frame = cell.wSJeYsJcmjNZf();
         if (frame != null) {
            cell.i2Nu6XHKic = !frame.getHeldItemStack().isEmpty();
         }
      }
   }

   public Collection<JhujBv5.gVOhtBej1x> xileXsbo() {
      return this.duJ6sPeetAcBh6P.values();
   }

   public int nOwp0NYl() {
      return this.duJ6sPeetAcBh6P.size();
   }

   public long umvNbb7iD9UpR() {
      return this.duJ6sPeetAcBh6P.values().stream().filter(c -> c.i2Nu6XHKic).count();
   }

   public boolean zkcZ4Xkp9Ez7Y() {
      return !this.duJ6sPeetAcBh6P.isEmpty() && this.duJ6sPeetAcBh6P.values().stream().allMatch(c -> c.i2Nu6XHKic);
   }

   public List<JhujBv5.gVOhtBej1x> flWHzEyn() {
      List<JhujBv5.gVOhtBej1x> pending = new ArrayList<>();

      for (JhujBv5.gVOhtBej1x cell : this.duJ6sPeetAcBh6P.values()) {
         if (!cell.i2Nu6XHKic) {
            pending.add(cell);
         }
      }

      if (fStlZIvY9lS8.player != null) {
         Vec3d eye = fStlZIvY9lS8.player.getEyePos();
         pending.sort(Comparator.comparingDouble(cellx -> Vec3d.ofCenter(cellx.TJImwK2).squaredDistanceTo(eye)));
      }

      return pending;
   }

   public void FVjbhYkk53UT() {
      for (JhujBv5.gVOhtBej1x cell : this.duJ6sPeetAcBh6P.values()) {
         cell.vGAMA89H = -1;
      }

      this.j376sIQ.clear();
      if (fStlZIvY9lS8.player != null) {
         for (int slot = 0; slot < 36; slot++) {
            ItemStack stack = fStlZIvY9lS8.player.getInventory().getStack(slot);
            if (!stack.isEmpty() && stack.getItem() == Items.FILLED_MAP) {
               String name = stack.getName().getString();
               JhujBv5.pvfA15fQSVM target = fStlZIvY9lS8(name);
               long key = target == null ? Long.MIN_VALUE : this.fStlZIvY9lS8(target);
               JhujBv5.gVOhtBej1x cell = key == Long.MIN_VALUE ? null : this.duJ6sPeetAcBh6P.get(key);
               if (cell != null && !cell.i2Nu6XHKic && cell.vGAMA89H < 0) {
                  cell.vGAMA89H = slot;
               } else if (cell == null) {
                  this.j376sIQ.add(name);
               }
            }
         }
      }
   }

   public List<String> Nt3xeF() {
      return this.j376sIQ;
   }

   public static JhujBv5.pvfA15fQSVM fStlZIvY9lS8(String name) {
      if (name != null && !name.isBlank()) {
         Integer bracketedIndex = null;
         Matcher groups = voxTwgNzyrpaAnP.matcher(name);

         while (groups.find()) {
            List<Integer> nums = TJImwK2(groups.group(1));
            if (nums.size() >= 2) {
               return new JhujBv5.pvfA15fQSVM.Grid(nums.get(0), nums.get(1));
            }

            if (nums.size() == 1 && bracketedIndex == null) {
               bracketedIndex = nums.getFirst();
            }
         }

         if (bracketedIndex != null) {
            return new JhujBv5.pvfA15fQSVM.Single(bracketedIndex);
         } else {
            List<Integer> nums = TJImwK2(name);
            if (nums.size() >= 2) {
               return new JhujBv5.pvfA15fQSVM.Grid(nums.get(nums.size() - 2), nums.getLast());
            } else {
               return nums.size() == 1 ? new JhujBv5.pvfA15fQSVM.Single(nums.getFirst()) : null;
            }
         }
      } else {
         return null;
      }
   }

   private static List<Integer> TJImwK2(String text) {
      List<Integer> nums = new ArrayList<>();
      Matcher matcher = V7zKJ4n6D.matcher(text);

      while (matcher.find()) {
         nums.add(Integer.parseInt(matcher.group()));
      }

      return nums;
   }

   private long fStlZIvY9lS8(JhujBv5.pvfA15fQSVM target) {
      int col;
      int row;
      if (target instanceof JhujBv5.pvfA15fQSVM.Grid grid) {
         col = grid.fStlZIvY9lS8() - this.I3psabmOrOI;
         row = grid.QfZy2gWnKInxFPr() - this.I3psabmOrOI;
      } else {
         if (this.BrmKXSYhWhqoB1 <= 0) {
            return Long.MIN_VALUE;
         }

         int index = ((JhujBv5.pvfA15fQSVM.Single)target).fStlZIvY9lS8() - this.I3psabmOrOI;
         if (index < 0) {
            return Long.MIN_VALUE;
         }

         col = index % this.BrmKXSYhWhqoB1;
         row = index / this.BrmKXSYhWhqoB1;
      }

      if (col < 0 || row < 0) {
         return Long.MIN_VALUE;
      } else if (this.BrmKXSYhWhqoB1 > 0 && col >= this.BrmKXSYhWhqoB1) {
         return Long.MIN_VALUE;
      } else {
         return this.TXWyNb > 0 && row >= this.TXWyNb ? Long.MIN_VALUE : QfZy2gWnKInxFPr(col, row);
      }
   }

   public String QfZy2gWnKInxFPr(String name) {
      JhujBv5.pvfA15fQSVM target = fStlZIvY9lS8(name);
      if (target == null) {
         return "no numbers in the name";
      } else if (target instanceof JhujBv5.pvfA15fQSVM.Grid grid) {
         return "grid " + grid.fStlZIvY9lS8() + "," + grid.QfZy2gWnKInxFPr();
      } else {
         int index = ((JhujBv5.pvfA15fQSVM.Single)target).fStlZIvY9lS8();
         if (this.BrmKXSYhWhqoB1 <= 0) {
            return "index " + index + " (needs .mp size to resolve)";
         }

         int n = index - this.I3psabmOrOI;
         return "index " + index + " -> grid " + (n % this.BrmKXSYhWhqoB1 + this.I3psabmOrOI) + "," + (n / this.BrmKXSYhWhqoB1 + this.I3psabmOrOI);
      }
   }

   private static long QfZy2gWnKInxFPr(int col, int row) {
      return (long)col << 32 | row & 4294967295L;
   }

   private static int fStlZIvY9lS8(Vec3i vec, Direction dir) {
      return vec.getX() * dir.getOffsetX() + vec.getY() * dir.getOffsetY() + vec.getZ() * dir.getOffsetZ();
   }

   public static Vec3d fStlZIvY9lS8(BlockPos pos, Direction facing, double out) {
      double depth = 0.46875 - out;
      return Vec3d.ofCenter(pos).add(-facing.getOffsetX() * depth, -facing.getOffsetY() * depth, -facing.getOffsetZ() * depth);
   }

   public static double fStlZIvY9lS8(ItemFrameEntity frame) {
      if (fStlZIvY9lS8.player == null) {
         return Double.MAX_VALUE;
      }

      Vec3d eye = fStlZIvY9lS8.player.getEyePos();
      Box box = frame.getBoundingBox();
      double x = Math.max(box.minX, Math.min(eye.x, box.maxX));
      double y = Math.max(box.minY, Math.min(eye.y, box.maxY));
      double z = Math.max(box.minZ, Math.min(eye.z, box.maxZ));
      return eye.distanceTo(new Vec3d(x, y, z));
   }

   public enum U6PQSXqW {
      TopLeft,
      BottomLeft;

      U6PQSXqW() {
      }
   }

   public static final class gVOhtBej1x {
      public final int fStlZIvY9lS8;
      public final int QfZy2gWnKInxFPr;
      public final BlockPos TJImwK2;
      private int wSJeYsJcmjNZf = -1;
      private boolean i2Nu6XHKic;
      private int vGAMA89H = -1;
      private int qhulAUwd = Integer.MIN_VALUE;

      private gVOhtBej1x(int col, int row, BlockPos pos) {
         this.fStlZIvY9lS8 = col;
         this.QfZy2gWnKInxFPr = row;
         this.TJImwK2 = pos;
      }

      public boolean fStlZIvY9lS8() {
         return this.i2Nu6XHKic;
      }

      public int QfZy2gWnKInxFPr() {
         return this.vGAMA89H;
      }

      public void fStlZIvY9lS8(int tick) {
         this.qhulAUwd = tick;
      }

      public void TJImwK2() {
         this.qhulAUwd = Integer.MIN_VALUE;
      }

      public boolean fStlZIvY9lS8(int tick, int timeout) {
         if (this.qhulAUwd == Integer.MIN_VALUE) {
            return false;
         }

         int elapsed = tick - this.qhulAUwd;
         return elapsed >= 0 && elapsed < timeout;
      }

      public ItemFrameEntity wSJeYsJcmjNZf() {
         if (JhujBv5.fStlZIvY9lS8.world != null && this.wSJeYsJcmjNZf >= 0) {
            return JhujBv5.fStlZIvY9lS8.world.getEntityById(this.wSJeYsJcmjNZf) instanceof ItemFrameEntity frame && frame.isAlive() ? frame : null;
         } else {
            return null;
         }
      }

      public String QfZy2gWnKInxFPr(int indexBase) {
         return this.fStlZIvY9lS8 + indexBase + "," + (this.QfZy2gWnKInxFPr + indexBase);
      }

      public int QfZy2gWnKInxFPr(int width, int indexBase) {
         return width > 0 ? this.QfZy2gWnKInxFPr * width + this.fStlZIvY9lS8 + indexBase : -1;
      }
   }

   public sealed interface pvfA15fQSVM permits JhujBv5.pvfA15fQSVM.Grid, JhujBv5.pvfA15fQSVM.Single {
      record Grid(int fStlZIvY9lS8, int QfZy2gWnKInxFPr) implements JhujBv5.pvfA15fQSVM {


         @Override
         public final String toString() {
            return "gVOhtBej1x[x=" + this.fStlZIvY9lS8 + ", y=" + this.QfZy2gWnKInxFPr + "]";
         }
      }

      record Single(int fStlZIvY9lS8) implements JhujBv5.pvfA15fQSVM {


         @Override
         public final String toString() {
            return "pvfA15fQSVM[index=" + this.fStlZIvY9lS8 + "]";
         }
      }
   }
}
