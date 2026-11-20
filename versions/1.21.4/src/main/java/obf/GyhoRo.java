package obf;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalNear;
import baritone.api.pathing.goals.GoalXZ;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

public class GyhoRo {
   private static final MinecraftClient fStlZIvY9lS8 = MinecraftClient.getInstance();

   public static boolean fStlZIvY9lS8() {
      return BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing();
   }

   public static boolean QfZy2gWnKInxFPr() {
      return BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().hasPath();
   }

   public static boolean TJImwK2() {
      IBaritone baritone = BaritoneAPI.getProvider().getPrimaryBaritone();
      return baritone.getPathingBehavior().isPathing()
         || baritone.getPathingBehavior().getInProgress().isPresent()
         || baritone.getCustomGoalProcess().isActive();
   }

   public static void wSJeYsJcmjNZf() {
      BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
   }

   public static void fStlZIvY9lS8(Goal goal) {
      BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(goal);
   }

   public static void fStlZIvY9lS8(GoalNear goal) {
      BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(goal);
   }

   public static boolean fStlZIvY9lS8(int x, int z) {
      if (!BaritoneAPI.getProvider().getPrimaryBaritone().getElytraProcess().isActive()) {
         BaritoneAPI.getProvider().getPrimaryBaritone().getElytraProcess().pathTo(new GoalXZ(x, z));
         return true;
      } else {
         return false;
      }
   }

   public static void fStlZIvY9lS8(BlockPos pos) {
      fStlZIvY9lS8(new GoalBlock(pos));
   }

   public static void fStlZIvY9lS8(String command) {
      BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute(command);
   }

   public static void QfZy2gWnKInxFPr(BlockPos itemPos) {
      fStlZIvY9lS8(String.format("goto %d %d %d", itemPos.getX(), itemPos.getY(), itemPos.getZ()));
   }
}
