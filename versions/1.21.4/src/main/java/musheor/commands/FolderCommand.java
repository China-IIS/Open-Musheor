package musheor.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.io.File;
import java.io.IOException;
import meteordevelopment.meteorclient.commands.Command;
import net.minecraft.command.CommandSource;

public class FolderCommand extends Command {
   public FolderCommand() {
      super("folder", "Opens a folder inside your .minecraft directory.", new String[0]);
   }

   public void build(LiteralArgumentBuilder<CommandSource> builder) {
      builder.executes(ctx -> {
         this.fStlZIvY9lS8(mc.runDirectory);
         return 1;
      });
      builder.then(argument("folder", StringArgumentType.word()).suggests((ctx, suggestions) -> {
         File mcDir = mc.runDirectory;
         File[] entries = mcDir.listFiles(File::isDirectory);
         if (entries != null) {
            for (File dir : entries) {
               suggestions.suggest(dir.getName());
            }
         }

         return suggestions.buildFuture();
      }).executes(ctx -> {
         String folderName = StringArgumentType.getString(ctx, "folder");
         File target = new File(mc.runDirectory, folderName);
         if (target.exists() && target.isDirectory()) {
            this.fStlZIvY9lS8(target);
            return 1;
         } else {
            this.error("Folder not found: " + folderName, new Object[0]);
            return 1;
         }
      }));
   }

   private void fStlZIvY9lS8(File folder) {
      try {
         new ProcessBuilder("explorer.exe", folder.getAbsolutePath()).start();
      } catch (IOException var3) {
         this.error("Failed to open folder: " + var3.getMessage(), new Object[0]);
      }
   }
}
