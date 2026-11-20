package obf;

import meteordevelopment.meteorclient.systems.modules.Modules;
import musheor.modules.features.CreativeFly;
import musheor.modules.hud.HudInfoPlus;
import musheor.utils.system.MusheorSystem;
import net.minecraft.client.MinecraftClient;

public class jU8xXhmkg {
   public static boolean fStlZIvY9lS8() {
      if (MinecraftClient.getInstance().player.isCreative()) {
         return true;
      } else {
         return ((CreativeFly)Modules.get().get(CreativeFly.class)).isActive() ? HudInfoPlus.qhulAUwd() < 8 : HudInfoPlus.qhulAUwd() < 9;
      }
   }

   public static boolean QfZy2gWnKInxFPr() {
      return MinecraftClient.getInstance().player.isCreative() ? true : HudInfoPlus.i2Nu6XHKic() < (Integer)MusheorSystem.Manager.invPacketLimit.get();
   }

   public static int TJImwK2() {
      return HudInfoPlus.i2Nu6XHKic();
   }

   public static int wSJeYsJcmjNZf() {
      return (Integer)MusheorSystem.Manager.invPacketLimit.get() - TJImwK2();
   }
}
