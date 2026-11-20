package musheor.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.List;
import meteordevelopment.meteorclient.commands.Command;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import musheor.modules.automation.ReKit;
import net.minecraft.command.CommandSource;

public class KitCommand extends Command {
   public KitCommand() {
      super("kit", "Manage ReKit loadouts. Usage: .kit create|load|delete|list [name]", new String[0]);
   }

   public void build(LiteralArgumentBuilder<CommandSource> builder) {
      builder.then(literal("create").then(argument("name", StringArgumentType.word()).executes(ctx -> {
         String name = StringArgumentType.getString(ctx, "name");
         ((ReKit)Modules.get().get(ReKit.class)).fStlZIvY9lS8(name);
         return 1;
      })));
      builder.then(literal("load").then(argument("name", StringArgumentType.word()).executes(ctx -> {
         String name = StringArgumentType.getString(ctx, "name");
         ReKit rekit = (ReKit)Modules.get().get(ReKit.class);
         if (!rekit.i2Nu6XHKic(name)) {
            ChatUtils.error("Kit '§b" + name + "§c' not found. Use §b.kit create§c to save one.", new Object[0]);
            return 1;
         } else {
            rekit.TJImwK2.set(name);
            rekit.QfZy2gWnKInxFPr(name);
            ChatUtils.info("Active kit set to '§b" + name + "§r'.", new Object[0]);
            return 1;
         }
      })));
      builder.then(literal("delete").then(argument("name", StringArgumentType.word()).executes(ctx -> {
         String name = StringArgumentType.getString(ctx, "name");
         ((ReKit)Modules.get().get(ReKit.class)).wSJeYsJcmjNZf(name);
         return 1;
      })));
      builder.then(literal("list").executes(ctx -> {
         List<String> kits = ((ReKit)Modules.get().get(ReKit.class)).QfZy2gWnKInxFPr();
         if (kits.isEmpty()) {
            ChatUtils.info("No kits saved.", new Object[0]);
         } else {
            ChatUtils.info("Saved kits: §b" + String.join("§r, §b", kits) + "§r", new Object[0]);
         }

         return 1;
      }));
   }
}
