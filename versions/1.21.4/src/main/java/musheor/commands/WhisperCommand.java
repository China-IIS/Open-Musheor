package musheor.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.commands.Command;
import meteordevelopment.meteorclient.commands.arguments.PlayerListEntryArgumentType;
import meteordevelopment.meteorclient.systems.modules.Modules;
import musheor.compat.VersionHelper;
import musheor.modules.tech.Whisper;
import musheor.utils.irzXX;
import net.minecraft.command.CommandSource;

public class WhisperCommand extends Command {
   public static String fStlZIvY9lS8;
   static int QfZy2gWnKInxFPr = (Integer)((Whisper)Modules.get().get(Whisper.class)).fStlZIvY9lS8.get();

   public WhisperCommand() {
      super("tp", "Sends a random message to another player.", new String[0]);
   }

   public void build(LiteralArgumentBuilder<CommandSource> builder) {
      builder.then(argument("player", PlayerListEntryArgumentType.create()).executes(context -> {
         if (VersionHelper.get().getName(context) != null) {
            fStlZIvY9lS8 = VersionHelper.get().getName(context);
         }

         QfZy2gWnKInxFPr = (Integer)((Whisper)Modules.get().get(Whisper.class)).fStlZIvY9lS8.get();
         irzXX.fStlZIvY9lS8(fStlZIvY9lS8, QfZy2gWnKInxFPr);
         return 1;
      }));
      builder.executes(context -> {
         QfZy2gWnKInxFPr = (Integer)((Whisper)Modules.get().get(Whisper.class)).fStlZIvY9lS8.get();
         irzXX.fStlZIvY9lS8(fStlZIvY9lS8, QfZy2gWnKInxFPr);
         return 1;
      });
   }
}
