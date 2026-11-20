package musheor.utils.hud.actions;

import musheor.compat.VersionHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;

public class SendMessageAction implements ButtonAction {
   public String fStlZIvY9lS8;
   private static final MinecraftClient QfZy2gWnKInxFPr = MinecraftClient.getInstance();

   public SendMessageAction(String message) {
      this.fStlZIvY9lS8 = message;
   }

   @Override
   public void fStlZIvY9lS8(ScreenHandler handler) {
      if (QfZy2gWnKInxFPr.player != null && QfZy2gWnKInxFPr.player.networkHandler != null) {
         QfZy2gWnKInxFPr.player.networkHandler.sendChatMessage(this.fStlZIvY9lS8);
      }
   }

   @Override
   public String fStlZIvY9lS8() {
      return "send_message";
   }

   @Override
   public NbtCompound QfZy2gWnKInxFPr() {
      NbtCompound tag = new NbtCompound();
      tag.putString("type", this.fStlZIvY9lS8());
      tag.putString("message", this.fStlZIvY9lS8);
      return tag;
   }

   public static SendMessageAction QfZy2gWnKInxFPr(NbtCompound tag) {
      return new SendMessageAction(VersionHelper.get().getString(tag, "message"));
   }
}
