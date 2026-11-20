package obf;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ComparatorBlock;
import net.minecraft.block.DaylightDetectorBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.NoteBlock;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Property;

public final class WOGVSBb {
   private static final Set<String> fStlZIvY9lS8 = Set.of(
      "powered",
      "locked",
      "lit",
      "waterlogged",
      "triggered",
      "extended",
      "north",
      "south",
      "east",
      "west",
      "up",
      "down",
      "distance",
      "persistent",
      "snowy",
      "signal_fire",
      "attached",
      "disarmed",
      "in_wall",
      "occupied",
      "bloom",
      "shrieking",
      "can_summon",
      "berries",
      "age",
      "stage",
      "level",
      "moisture",
      "hatch",
      "eggs",
      "power"
   );
   private static final Set<String> QfZy2gWnKInxFPr = Set.of("iron_door", "iron_trapdoor");

   public static int fStlZIvY9lS8(BlockState current, BlockState target) {
      if (current != null && target != null) {
         if (current.getBlock() != target.getBlock()) {
            return -1;
         } else {
            net.minecraft.state.property.Property<?> cycled = fStlZIvY9lS8(current);
            if (cycled == null) {
               return -1;
            } else {
               for (net.minecraft.state.property.Property<?> other : target.getProperties()) {
                  if (other != cycled
                     && !fStlZIvY9lS8.contains(other.getName())
                     && current.contains(other)
                     && !current.get(other).equals(target.get(other))) {
                     return -1;
                  }
               }

               return fStlZIvY9lS8(cycled, current, target);
            }
         }
      } else {
         return -1;
      }
   }

   private static net.minecraft.state.property.Property<?> fStlZIvY9lS8(BlockState state) {
      Block block = state.getBlock();
      String name;
      if (block instanceof TrapdoorBlock || block instanceof DoorBlock || block instanceof FenceGateBlock) {
         if (QfZy2gWnKInxFPr.contains(Registries.BLOCK.getId(block).getPath())) {
            return null;
         }

         if (block instanceof DoorBlock) {
            net.minecraft.state.property.Property<?> half = block.getStateManager().getProperty("half");
            if (half != null && "upper".equals(fStlZIvY9lS8(state, half))) {
               return null;
            }
         }

         name = "open";
      } else if (block instanceof RepeaterBlock) {
         name = "delay";
      } else if (block instanceof ComparatorBlock) {
         name = "mode";
      } else if (block instanceof NoteBlock) {
         name = "note";
      } else if (block instanceof DaylightDetectorBlock) {
         name = "inverted";
      } else {
         if (!(block instanceof LeverBlock)) {
            return null;
         }

         name = "powered";
      }

      return block.getStateManager().getProperty(name);
   }

   private static <T extends Comparable<T>> int fStlZIvY9lS8(net.minecraft.state.property.Property<T> prop, BlockState current, BlockState target) {
      if (current.contains(prop) && target.contains(prop)) {
         List<T> values = new ArrayList<>(prop.getValues());
         int from = values.indexOf(current.get(prop));
         int to = values.indexOf(target.get(prop));
         if (from >= 0 && to >= 0) {
            int size = values.size();
            return ((to - from) % size + size) % size;
         } else {
            return -1;
         }
      } else {
         return -1;
      }
   }

   private static <T extends Comparable<T>> String fStlZIvY9lS8(BlockState state, net.minecraft.state.property.Property<T> prop) {
      return prop.name(state.get(prop));
   }
}
