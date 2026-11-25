package musheor.utils.hud;

import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.utils.misc.Keybind;
import meteordevelopment.orbit.EventHandler;
import musheor.utils.hud.screen.CustomButtonManagerScreen;
import musheor.utils.system.MusheorSystem;
import net.minecraft.client.MinecraftClient;

public class HudEditorKeybind {
   private final MinecraftClient fStlZIvY9lS8 = MinecraftClient.getInstance();

   public HudEditorKeybind() {
   }

   @EventHandler
   private void fStlZIvY9lS8(Pre event) {
      if (this.fStlZIvY9lS8.currentScreen == null) {
         if (((Keybind)MusheorSystem.get().openEditor.get()).isPressed()) {
            this.fStlZIvY9lS8.execute(() -> this.fStlZIvY9lS8.setScreen(new CustomButtonManagerScreen(this.fStlZIvY9lS8.currentScreen)));
         }
      }
   }
}
