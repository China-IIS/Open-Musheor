package musheor.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.commands.Command;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.orbit.EventHandler;
import musheor.utils.hud.screen.EchestConfigScreen;
import net.minecraft.command.CommandSource;

public class EchestConfigCommand extends Command {
   private boolean fStlZIvY9lS8 = false;
   private int QfZy2gWnKInxFPr = 0;

   public EchestConfigCommand() {
      super("erc", "Opens the echest-configuration screen", new String[0]);
      MeteorClient.EVENT_BUS.subscribe(this);
   }

   public void build(LiteralArgumentBuilder<CommandSource> builder) {
      builder.executes(context -> {
         this.fStlZIvY9lS8();
         return 1;
      });
   }

   @EventHandler
   private void fStlZIvY9lS8(Pre event) {
      if (this.fStlZIvY9lS8 && --this.QfZy2gWnKInxFPr == 0) {
         mc.execute(() -> mc.setScreen(new EchestConfigScreen(null)));
         this.fStlZIvY9lS8 = false;
      }
   }

   private void fStlZIvY9lS8() {
      this.fStlZIvY9lS8 = true;
      this.QfZy2gWnKInxFPr = 2;
   }
}
