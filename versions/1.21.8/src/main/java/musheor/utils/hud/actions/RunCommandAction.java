package musheor.utils.hud.actions;

import meteordevelopment.meteorclient.utils.player.ChatUtils;
import musheor.compat.VersionHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;

public class RunCommandAction implements ButtonAction {
   public String fStlZIvY9lS8;
   private static final MinecraftClient QfZy2gWnKInxFPr = MinecraftClient.getInstance();

   public RunCommandAction(String command) {
      this.fStlZIvY9lS8 = command;
   }

   @Override
   public void fStlZIvY9lS8(ScreenHandler handler) {
      if (QfZy2gWnKInxFPr.player != null && QfZy2gWnKInxFPr.getNetworkHandler() != null) {
         if (this.fStlZIvY9lS8.startsWith(".")) {
            ChatUtils.sendPlayerMsg(this.fStlZIvY9lS8);
         } else if (this.fStlZIvY9lS8.startsWith("/")) {
            VersionHelper.get().sendCommand(this.fStlZIvY9lS8.substring(1));
         } else {
            VersionHelper.get().sendCommand(this.fStlZIvY9lS8);
         }
      }
   }

   @Override
   public String fStlZIvY9lS8() {
      return "run_command";
   }

   @Override
   public NbtCompound QfZy2gWnKInxFPr() {
      NbtCompound tag = new NbtCompound();
      tag.putString("type", this.fStlZIvY9lS8());
      tag.putString("command", this.fStlZIvY9lS8);
      return tag;
   }

   public static RunCommandAction QfZy2gWnKInxFPr(NbtCompound tag) {
      return new RunCommandAction(VersionHelper.get().getString(tag, "command"));
   }
}
