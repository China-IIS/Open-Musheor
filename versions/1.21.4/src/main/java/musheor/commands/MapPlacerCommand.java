package musheor.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.List;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.commands.Command;
import meteordevelopment.meteorclient.events.entity.player.InteractEntityEvent;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.orbit.EventHandler;
import musheor.modules.automation.MapPlacer;
import musheor.modules.features.CoordHider;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.util.math.BlockPos;
import obf.JhujBv5;

public class MapPlacerCommand extends Command {
   private static final JhujBv5 fStlZIvY9lS8 = JhujBv5.fStlZIvY9lS8();
   private static JhujBv5.U6PQSXqW QfZy2gWnKInxFPr;

   public MapPlacerCommand() {
      super("mp", "Sets up the map-placer canvas for hanging a mapart.", new String[]{"mapplacer"});
   }

   public void build(LiteralArgumentBuilder<CommandSource> builder) {
      builder.executes(this::fStlZIvY9lS8);
      builder.then(
         ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literal("origin")
                     .then(literal("topleft").executes(ctx -> this.fStlZIvY9lS8(JhujBv5.U6PQSXqW.TopLeft))))
                  .then(literal("tl").executes(ctx -> this.fStlZIvY9lS8(JhujBv5.U6PQSXqW.TopLeft))))
               .then(literal("bottomleft").executes(ctx -> this.fStlZIvY9lS8(JhujBv5.U6PQSXqW.BottomLeft))))
            .then(literal("bl").executes(ctx -> this.fStlZIvY9lS8(JhujBv5.U6PQSXqW.BottomLeft)))
      );
      builder.then(
         literal("size")
            .then(
               argument("width", IntegerArgumentType.integer(0, 128))
                  .then(argument("height", IntegerArgumentType.integer(0, 128)).executes(this::QfZy2gWnKInxFPr))
            )
      );
      builder.then(literal("base").then(argument("base", IntegerArgumentType.integer(0, 1)).executes(this::TJImwK2)));
      builder.then(literal("scan").executes(this::wSJeYsJcmjNZf));
      builder.then(literal("list").executes(this::i2Nu6XHKic));
      builder.then(literal("place").executes(this::vGAMA89H));
      builder.then(literal("pause").executes(this::qhulAUwd));
      builder.then(literal("resume").executes(this::BrmKXSYhWhqoB1));
      builder.then(literal("stop").executes(this::TXWyNb));
      builder.then(literal("clear").executes(this::I3psabmOrOI));
      builder.then(literal("name").then(argument("name", StringArgumentType.greedyString()).executes(this::duJ6sPeetAcBh6P)));
   }

   private int fStlZIvY9lS8(CommandContext<CommandSource> context) {
      if (!fStlZIvY9lS8.QfZy2gWnKInxFPr()) {
         this.info("No canvas set. Look at the mapart's §b0,0§r frame and run:", new Object[0]);
         this.info("  §b1. .mp origin topleft§r §7(or bottomleft) — anchors the grid and shows the preview", new Object[0]);
         this.info("  §b2. .mp size <w> <h>§r §7— only needed for maps named with a single index", new Object[0]);
         this.info("  §b3. .mp place§r §7— starts placing; you walk along the wall yourself", new Object[0]);
         return 1;
      } else {
         MapPlacer module = MapPlacer.fStlZIvY9lS8();
         this.info(
            "Canvas: §b%s§r, origin §b%s§r, facing §b%s§r, base §b%d§r",
            new Object[]{
               fStlZIvY9lS8.TXWyNb() > 0 && fStlZIvY9lS8.I3psabmOrOI() > 0 ? fStlZIvY9lS8.TXWyNb() + "x" + fStlZIvY9lS8.I3psabmOrOI() : "auto-size",
               fStlZIvY9lS8.wSJeYsJcmjNZf(),
               fStlZIvY9lS8.vGAMA89H(),
               fStlZIvY9lS8.duJ6sPeetAcBh6P()
            }
         );
         this.info("Status: %s%s", new Object[]{module.QfZy2gWnKInxFPr(), module.isActive() ? "" : " §7[module off]"});
         this.info(
            "Maps in inventory: §b%d§r%s",
            new Object[]{
               MapPlacer.TJImwK2(), fStlZIvY9lS8.Nt3xeF().isEmpty() ? "" : String.format(" §7(%d unplaceable — see .mp list)", fStlZIvY9lS8.Nt3xeF().size())
            }
         );
         this.info("§7.mp scan | list | place | pause | resume | stop | clear | name <text>", new Object[0]);
         return 1;
      }
   }

   private int fStlZIvY9lS8(JhujBv5.U6PQSXqW corner) {
      if (mc.player == null) {
         this.error("You must be in a world.", new Object[0]);
         return 1;
      } else if (mc.targetedEntity instanceof ItemFrameEntity frame) {
         fStlZIvY9lS8(frame, corner);
         return 1;
      } else {
         QfZy2gWnKInxFPr = corner;
         if (!MeteorClient.EVENT_BUS.isListening(MapPlacerCommand.PickListener.class)) {
            MeteorClient.EVENT_BUS.subscribe(MapPlacerCommand.PickListener.fStlZIvY9lS8);
         }

         this.info("Right-click the §b%s§r frame of the mapart to make it §b0,0§r. §7(.mp clear cancels)", new Object[]{corner});
         return 1;
      }
   }

   private static void fStlZIvY9lS8(ItemFrameEntity frame, JhujBv5.U6PQSXqW corner) {
      fStlZIvY9lS8.fStlZIvY9lS8(frame, corner, mc.player.getYaw());
      int found = fStlZIvY9lS8.j376sIQ();
      fStlZIvY9lS8.FVjbhYkk53UT();
      ChatUtils.info("Origin set: §b%s§r at §b%s§r, facing §b%s§r.", new Object[]{corner, fStlZIvY9lS8(fStlZIvY9lS8.i2Nu6XHKic()), fStlZIvY9lS8.vGAMA89H()});
      ChatUtils.info("Columns run §b%s§r, rows run §b%s§r. Scanned §b%d§r frames.", new Object[]{fStlZIvY9lS8.qhulAUwd(), fStlZIvY9lS8.BrmKXSYhWhqoB1(), found});
      if (fStlZIvY9lS8.vGAMA89H().getAxis().isVertical()) {
         ChatUtils.warning(
            "Flat mapart — the axes were taken from the way you are facing right now. Re-run this while standing at the mapart's corner if they come out wrong.",
            new Object[0]
         );
      }

      if (found <= 1) {
         ChatUtils.warning(
            "Only the origin frame is loaded. Walk along the wall to pick the rest up (auto-scan does it for you, or run §b.mp scan§r).", new Object[0]
         );
      }

      MapPlacer.fStlZIvY9lS8(true);
      MapPlacer module = MapPlacer.fStlZIvY9lS8();
      if (!module.isActive()) {
         module.toggle();
      }

      ChatUtils.info("Preview on, §epaused§r — check the labels on the frames, then run §b.mp place§r.", new Object[0]);
   }

   private static String fStlZIvY9lS8(BlockPos pos) {
      CoordHider hider = (CoordHider)Modules.get().get(CoordHider.class);
      return hider != null && hider.isActive() ? "? ? ?" : pos.getX() + " " + pos.getY() + " " + pos.getZ();
   }

   private static void fStlZIvY9lS8() {
      QfZy2gWnKInxFPr = null;
      if (MeteorClient.EVENT_BUS.isListening(MapPlacerCommand.PickListener.class)) {
         MeteorClient.EVENT_BUS.unsubscribe(MapPlacerCommand.PickListener.fStlZIvY9lS8);
      }
   }

   private int QfZy2gWnKInxFPr(CommandContext<CommandSource> context) {
      int width = IntegerArgumentType.getInteger(context, "width");
      int height = IntegerArgumentType.getInteger(context, "height");
      fStlZIvY9lS8.fStlZIvY9lS8(width, height);
      fStlZIvY9lS8.j376sIQ();
      fStlZIvY9lS8.FVjbhYkk53UT();
      if (width == 0 && height == 0) {
         this.info("Size cleared — the canvas takes every aligned frame it can see.", new Object[0]);
         this.warning("Maps named with a single index cannot be placed without a width to wrap rows at.", new Object[0]);
      } else {
         this.info("Size set to §b%d§r wide x §b%d§r tall (§b%d§r frames scanned so far).", new Object[]{width, height, fStlZIvY9lS8.nOwp0NYl()});
      }

      return 1;
   }

   private int TJImwK2(CommandContext<CommandSource> context) {
      int base = IntegerArgumentType.getInteger(context, "base");
      fStlZIvY9lS8.fStlZIvY9lS8(base);
      fStlZIvY9lS8.FVjbhYkk53UT();
      this.info("Coordinates in map names now count from §b%d§r. §7(\"Clown 0 4\" is base 0, \"Clown 1 5\" base 1)", new Object[]{base});
      return 1;
   }

   private int wSJeYsJcmjNZf(CommandContext<CommandSource> context) {
      if (!this.QfZy2gWnKInxFPr()) {
         return 1;
      }

      int before = fStlZIvY9lS8.nOwp0NYl();
      int found = fStlZIvY9lS8.j376sIQ();
      fStlZIvY9lS8.FVjbhYkk53UT();
      this.info("Scanned §b%d§r frames (§b+%d§r new), §b%d§r already filled.", new Object[]{found, found - before, fStlZIvY9lS8.umvNbb7iD9UpR()});
      return 1;
   }

   private int i2Nu6XHKic(CommandContext<CommandSource> context) {
      if (!this.QfZy2gWnKInxFPr()) {
         return 1;
      }

      fStlZIvY9lS8.voxTwgNzyrpaAnP();
      fStlZIvY9lS8.FVjbhYkk53UT();
      if (fStlZIvY9lS8.nOwp0NYl() == 0) {
         this.warning("No frames scanned yet. Walk to the mapart and run §b.mp scan§r.", new Object[0]);
         return 1;
      }

      int width = fStlZIvY9lS8.TXWyNb();
      int height = fStlZIvY9lS8.I3psabmOrOI();

      for (JhujBv5.gVOhtBej1x cell : fStlZIvY9lS8.xileXsbo()) {
         width = Math.max(width, cell.fStlZIvY9lS8 + 1);
         height = Math.max(height, cell.QfZy2gWnKInxFPr + 1);
      }

      this.info("§a#§r placed  §b#§r ready  §c#§r map missing  §8.§r no frame", new Object[0]);

      for (int row = 0; row < height; row++) {
         StringBuilder line = new StringBuilder();

         for (int col = 0; col < width; col++) {
            JhujBv5.gVOhtBej1x cell = this.fStlZIvY9lS8(col, row);
            if (cell == null) {
               line.append("§8.");
            } else if (cell.fStlZIvY9lS8()) {
               line.append("§a#");
            } else if (cell.QfZy2gWnKInxFPr() >= 0) {
               line.append("§b#");
            } else {
               line.append("§c#");
            }
         }

         this.info("§7%2d§r %s", new Object[]{row + fStlZIvY9lS8.duJ6sPeetAcBh6P(), line});
      }

      this.info(
         "§b%d§r/§b%d§r frames filled, §b%d§r maps in inventory.", new Object[]{fStlZIvY9lS8.umvNbb7iD9UpR(), fStlZIvY9lS8.nOwp0NYl(), MapPlacer.TJImwK2()}
      );
      List<String> unmatched = fStlZIvY9lS8.Nt3xeF();
      if (!unmatched.isEmpty()) {
         this.warning("§b%d§r map(s) do not land on this canvas:", new Object[]{unmatched.size()});

         for (String name : unmatched.stream().distinct().limit(10L).toList()) {
            this.info("  §7%s §8-> %s", new Object[]{name, fStlZIvY9lS8.QfZy2gWnKInxFPr(name)});
         }

         if (unmatched.size() > 10) {
            this.info("  §7...and %d more", new Object[]{unmatched.size() - 10});
         }
      }

      return 1;
   }

   private JhujBv5.gVOhtBej1x fStlZIvY9lS8(int col, int row) {
      for (JhujBv5.gVOhtBej1x cell : fStlZIvY9lS8.xileXsbo()) {
         if (cell.fStlZIvY9lS8 == col && cell.QfZy2gWnKInxFPr == row) {
            return cell;
         }
      }

      return null;
   }

   private int vGAMA89H(CommandContext<CommandSource> context) {
      if (!this.QfZy2gWnKInxFPr()) {
         return 1;
      }

      MapPlacer.fStlZIvY9lS8(false);
      MapPlacer module = MapPlacer.fStlZIvY9lS8();
      if (!module.isActive()) {
         module.toggle();
      } else {
         this.info("Already placing — %s", new Object[]{module.QfZy2gWnKInxFPr()});
      }

      return 1;
   }

   private int qhulAUwd(CommandContext<CommandSource> context) {
      MapPlacer.fStlZIvY9lS8(true);
      this.info("Paused. §7The preview stays up — .mp resume carries on where it left off.", new Object[0]);
      return 1;
   }

   private int BrmKXSYhWhqoB1(CommandContext<CommandSource> context) {
      if (!this.QfZy2gWnKInxFPr()) {
         return 1;
      }

      MapPlacer.fStlZIvY9lS8(false);
      MapPlacer module = MapPlacer.fStlZIvY9lS8();
      if (!module.isActive()) {
         module.toggle();
      }

      this.info("Resumed — %s", new Object[]{module.QfZy2gWnKInxFPr()});
      return 1;
   }

   private int TXWyNb(CommandContext<CommandSource> context) {
      MapPlacer module = MapPlacer.fStlZIvY9lS8();
      if (module.isActive()) {
         module.toggle();
      }

      this.info("Stopped — preview off. §7The canvas is kept, so .mp place picks it straight back up.", new Object[0]);
      return 1;
   }

   private int I3psabmOrOI(CommandContext<CommandSource> context) {
      fStlZIvY9lS8();
      MapPlacer module = MapPlacer.fStlZIvY9lS8();
      if (module.isActive()) {
         module.toggle();
      }

      fStlZIvY9lS8.TJImwK2();
      MapPlacer.fStlZIvY9lS8(false);
      this.info("Canvas cleared.", new Object[0]);
      return 1;
   }

   private int duJ6sPeetAcBh6P(CommandContext<CommandSource> context) {
      String name = StringArgumentType.getString(context, "name");
      this.info("§7%s §8-> §r%s", new Object[]{name, fStlZIvY9lS8.QfZy2gWnKInxFPr(name)});
      return 1;
   }

   private boolean QfZy2gWnKInxFPr() {
      if (fStlZIvY9lS8.QfZy2gWnKInxFPr()) {
         return true;
      }

      this.error("No canvas — look at the mapart's corner frame and run §b.mp origin topleft§r.", new Object[0]);
      return false;
   }

   private static class PickListener {
      private static final MapPlacerCommand.PickListener fStlZIvY9lS8 = new MapPlacerCommand.PickListener();

      private PickListener() {
      }

      @EventHandler(priority = 200)
      private void fStlZIvY9lS8(InteractEntityEvent event) {
         if (MapPlacerCommand.QfZy2gWnKInxFPr != null) {
            if (event.entity instanceof ItemFrameEntity frame) {
               JhujBv5.U6PQSXqW var4 = MapPlacerCommand.QfZy2gWnKInxFPr;
               MapPlacerCommand.fStlZIvY9lS8();
               MapPlacerCommand.fStlZIvY9lS8(frame, var4);
               event.cancel();
            }
         }
      }
   }
}
