package musheor.utils;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.meteorclient.systems.friends.Friends;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class AVZhkxEO {
   public static List<ItemStack> fStlZIvY9lS8(PlayerEntity player) {
      List<ItemStack> stacks = new ArrayList<>();
      stacks.add(player.getEquippedStack(EquipmentSlot.HEAD));
      stacks.add(player.getEquippedStack(EquipmentSlot.CHEST));
      stacks.add(player.getEquippedStack(EquipmentSlot.LEGS));
      stacks.add(player.getEquippedStack(EquipmentSlot.FEET));
      return stacks;
   }

   public static ItemStack QfZy2gWnKInxFPr(PlayerEntity player) {
      return player.getMainHandStack();
   }

   public static ItemStack TJImwK2(PlayerEntity player) {
      return player.getOffHandStack();
   }

   public static boolean wSJeYsJcmjNZf(PlayerEntity player) {
      if (!QfZy2gWnKInxFPr(player).isEmpty()) {
         return true;
      } else if (!TJImwK2(player).isEmpty()) {
         return true;
      } else {
         for (ItemStack s : fStlZIvY9lS8(player)) {
            if (!s.isEmpty()) {
               return true;
            }
         }

         return false;
      }
   }

   public static int fStlZIvY9lS8(double distance) {
      float t = (float)MathHelper.clamp(distance / 100.0, 0.0, 1.0);
      int r;
      int g;
      if (t < 0.5F) {
         r = 255;
         g = (int)(255.0F * (t * 2.0F));
      } else {
         r = (int)(255.0F * (1.0F - (t - 0.5F) * 2.0F));
         g = 255;
      }

      return r << 16 | g << 8 | 0;
   }

   public static int fStlZIvY9lS8(int colorA, int colorB, float t) {
      int ar = colorA >> 16 & 0xFF;
      int ag = colorA >> 8 & 0xFF;
      int ab = colorA & 0xFF;
      int br = colorB >> 16 & 0xFF;
      int bg = colorB >> 8 & 0xFF;
      int bb = colorB & 0xFF;
      int r = (int)(ar + (br - ar) * t);
      int g = (int)(ag + (bg - ag) * t);
      int b = (int)(ab + (bb - ab) * t);
      return r << 16 | g << 8 | b;
   }

   public static double i2Nu6XHKic(PlayerEntity target) {
      MinecraftClient mc = MinecraftClient.getInstance();
      return mc.player == null ? 0.0 : mc.player.distanceTo(target);
   }

   public static boolean vGAMA89H(PlayerEntity player) {
      return Friends.get().isFriend(player);
   }
}
