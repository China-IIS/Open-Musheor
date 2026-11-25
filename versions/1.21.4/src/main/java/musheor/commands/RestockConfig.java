package musheor.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.commands.Command;
import meteordevelopment.meteorclient.events.entity.player.InteractBlockEvent;
import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
import meteordevelopment.meteorclient.events.world.TickEvent.Post;
import meteordevelopment.meteorclient.systems.Systems;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.orbit.EventHandler;
import musheor.compat.VersionHelper;
import musheor.utils.oyDPiOVBGKG;
import musheor.utils.system.MusheorSystem;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtLong;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class RestockConfig extends Command {
   private static final Map<Item, List<BlockPos>> fStlZIvY9lS8 = new HashMap<>();
   private static boolean QfZy2gWnKInxFPr = false;
   private static Item TJImwK2 = null;
   private static boolean wSJeYsJcmjNZf = false;
   private static BlockPos i2Nu6XHKic = null;
   private static int vGAMA89H = -1;

   public RestockConfig() {
      super("rc", "Configure restocking container locations for items.", new String[]{"rc"});
   }

   public void build(LiteralArgumentBuilder<CommandSource> builder) {
      builder.executes(this::fStlZIvY9lS8);
      builder.then(literal("auto").executes(this::QfZy2gWnKInxFPr));
      builder.then(literal("add").then(argument("item", ItemStackArgumentType.itemStack(REGISTRY_ACCESS)).executes(this::TJImwK2)));
      builder.then(literal("clear").executes(this::wSJeYsJcmjNZf));
      builder.then(literal("list").executes(this::i2Nu6XHKic));
      builder.then(literal("remove").then(argument("item", ItemStackArgumentType.itemStack(REGISTRY_ACCESS)).executes(this::vGAMA89H)));
   }

   private int fStlZIvY9lS8(CommandContext<CommandSource> context) {
      if (!wSJeYsJcmjNZf) {
         this.info("Usage: .rc auto | add <item> | remove <item> | list | clear", new Object[0]);
         return 1;
      }

      wSJeYsJcmjNZf = false;
      i2Nu6XHKic = null;
      vGAMA89H = -1;
      if (MeteorClient.EVENT_BUS.isListening(RestockConfig.AutoConfigListener.class)) {
         MeteorClient.EVENT_BUS.unsubscribe(RestockConfig.AutoConfigListener.fStlZIvY9lS8);
      }

      this.info("Exited auto config mode", new Object[0]);
      return 1;
   }

   private int QfZy2gWnKInxFPr(CommandContext<CommandSource> context) {
      wSJeYsJcmjNZf = true;
      i2Nu6XHKic = null;
      vGAMA89H = -1;
      this.info("Auto config mode enabled", new Object[0]);
      this.info("§7Open containers to automatically register their contents", new Object[0]);
      this.info("§7Type .rc to exit", new Object[0]);
      if (!MeteorClient.EVENT_BUS.isListening(RestockConfig.AutoConfigListener.class)) {
         MeteorClient.EVENT_BUS.subscribe(RestockConfig.AutoConfigListener.fStlZIvY9lS8);
      }

      return 1;
   }

   private int TJImwK2(CommandContext<CommandSource> context) {
      Item item = ItemStackArgumentType.getItemStackArgument(context, "item").getItem();
      if (item != null && item != Items.AIR) {
         QfZy2gWnKInxFPr = true;
         TJImwK2 = item;
         this.info("Restocking config mode enabled for §b%s", new Object[]{fStlZIvY9lS8(item)});
         this.info("§7Right-click containers to add them", new Object[0]);
         this.info("§7Esc to exit config mode", new Object[0]);
         if (!MeteorClient.EVENT_BUS.isListening(RestockConfig.RestockingConfigListener.class)) {
            MeteorClient.EVENT_BUS.subscribe(RestockConfig.RestockingConfigListener.fStlZIvY9lS8);
         }

         return 1;
      } else {
         this.error("Invalid item: %s", new Object[]{item});
         return 1;
      }
   }

   private int wSJeYsJcmjNZf(CommandContext<CommandSource> context) {
      int totalContainers = fStlZIvY9lS8.values().stream().mapToInt(List::size).sum();
      fStlZIvY9lS8.clear();
      i2Nu6XHKic();
      this.info("Cleared all restocking containers (%d total)", new Object[]{totalContainers});
      return 1;
   }

   private int i2Nu6XHKic(CommandContext<CommandSource> context) {
      if (fStlZIvY9lS8.isEmpty()) {
         this.info("No restocking containers configured", new Object[0]);
         return 1;
      }

      this.info("Restocking containers:", new Object[0]);

      for (Entry<Item, List<BlockPos>> entry : fStlZIvY9lS8.entrySet()) {
         Item item = entry.getKey();
         List<BlockPos> positions = entry.getValue();
         this.info("§b%s§r (%d containers):", new Object[]{fStlZIvY9lS8(item), positions.size()});

         for (BlockPos pos : positions) {
            this.info("  - %s, blocks away", new Object[]{Math.ceil(oyDPiOVBGKG.fStlZIvY9lS8(mc.player.getBlockPos(), pos))});
         }
      }

      return 1;
   }

   private int vGAMA89H(CommandContext<CommandSource> context) {
      Item item = ItemStackArgumentType.getItemStackArgument(context, "item").getItem();
      if (item != null && item != Items.AIR) {
         List<BlockPos> removed = fStlZIvY9lS8.remove(item);
         if (removed != null && !removed.isEmpty()) {
            this.info("Removed %d containers for %s", new Object[]{removed.size(), fStlZIvY9lS8(item)});
            i2Nu6XHKic();
         } else {
            this.warning("No containers configured for %s", new Object[]{fStlZIvY9lS8(item)});
         }

         return 1;
      } else {
         this.error("Invalid item: %s", new Object[]{item});
         return 1;
      }
   }

   public static String fStlZIvY9lS8(Item item) {
      return Registries.ITEM.getId(item).toString().replace("minecraft:", "");
   }

   public static List<BlockPos> QfZy2gWnKInxFPr(Item item) {
      return fStlZIvY9lS8.getOrDefault(item, new ArrayList<>());
   }

   public static boolean TJImwK2(Item item) {
      return fStlZIvY9lS8.containsKey(item) && !fStlZIvY9lS8.get(item).isEmpty();
   }

   private static void i2Nu6XHKic() {
      ((MusheorSystem)Systems.get(MusheorSystem.class)).save();
   }

   public static NbtCompound fStlZIvY9lS8() {
      NbtCompound tag = new NbtCompound();

      for (Entry<Item, List<BlockPos>> entry : fStlZIvY9lS8.entrySet()) {
         String key = Registries.ITEM.getId(entry.getKey()).toString();
         NbtList posList = new NbtList();

         for (BlockPos pos : entry.getValue()) {
            posList.add(NbtLong.of(pos.asLong()));
         }

         tag.put(key, posList);
      }

      return tag;
   }

   public static void fStlZIvY9lS8(NbtCompound tag) {
      fStlZIvY9lS8.clear();

      for (String key : tag.getKeys()) {
         Identifier id = Identifier.tryParse(key);
         if (id != null && Registries.ITEM.containsId(id)) {
            Item item = (Item)Registries.ITEM.get(id);
            NbtList posList = VersionHelper.get().getList(tag, key, 4);
            List<BlockPos> positions = new ArrayList<>();

            for (int i = 0; i < posList.size(); i++) {
               positions.add(BlockPos.fromLong(((NbtLong)posList.get(i)).longValue()));
            }

            fStlZIvY9lS8.put(item, positions);
         }
      }
   }

   private static class AutoConfigListener {
      private static final RestockConfig.AutoConfigListener fStlZIvY9lS8 = new RestockConfig.AutoConfigListener();

      private AutoConfigListener() {
      }

      @EventHandler(priority = 200)
      private void fStlZIvY9lS8(InteractBlockEvent event) {
         if (RestockConfig.wSJeYsJcmjNZf) {
            RestockConfig.i2Nu6XHKic = event.result.getBlockPos();
            RestockConfig.vGAMA89H = -1;
         }
      }

      @EventHandler(priority = 200)
      private void fStlZIvY9lS8(OpenScreenEvent event) {
         if (RestockConfig.wSJeYsJcmjNZf && RestockConfig.i2Nu6XHKic != null) {
            if (event.screen != null) {
               RestockConfig.vGAMA89H = 4;
            }
         }
      }

      @EventHandler
      private void fStlZIvY9lS8(Post event) {
         if (RestockConfig.wSJeYsJcmjNZf && RestockConfig.vGAMA89H >= 0) {
            if (RestockConfig.vGAMA89H > 0) {
               RestockConfig.vGAMA89H--;
            } else {
               RestockConfig.vGAMA89H = -1;
               if (RestockConfig.mc.player != null && RestockConfig.mc.player.currentScreenHandler != null && RestockConfig.i2Nu6XHKic != null) {
                  ScreenHandler handler = RestockConfig.mc.player.currentScreenHandler;
                  Set<Item> foundItems = new HashSet<>();
                  int containerSlotCount = handler.slots.size() - 36;
                  if (containerSlotCount > 0) {
                     for (int i = 0; i < containerSlotCount; i++) {
                        Slot slot = (Slot)handler.slots.get(i);
                        ItemStack stack = slot.getStack();
                        if (!stack.isEmpty() && stack.getItem() != Items.AIR) {
                           foundItems.add(stack.getItem());
                        }
                     }

                     if (foundItems.isEmpty()) {
                        ChatUtils.info(
                           "Container at §b%d %d %d§r is empty, skipping",
                           new Object[]{RestockConfig.i2Nu6XHKic.getX(), RestockConfig.i2Nu6XHKic.getY(), RestockConfig.i2Nu6XHKic.getZ()}
                        );
                     } else {
                        BlockPos pos = RestockConfig.i2Nu6XHKic;

                        for (Item item : foundItems) {
                           List<BlockPos> positions = RestockConfig.fStlZIvY9lS8.computeIfAbsent(item, k -> new ArrayList<>());
                           if (!positions.contains(pos)) {
                              positions.add(pos);
                           }
                        }

                        RestockConfig.i2Nu6XHKic();
                        ChatUtils.info("Registered §b%d§r item(s) from container", new Object[]{foundItems.size(), pos.getX(), pos.getY(), pos.getZ()});

                        for (Item item : foundItems) {
                           ChatUtils.info("  - §b%s", new Object[]{RestockConfig.fStlZIvY9lS8(item)});
                        }
                     }

                     RestockConfig.i2Nu6XHKic = null;
                  }
               }
            }
         }
      }
   }

   private static class RestockingConfigListener {
      private static final RestockConfig.RestockingConfigListener fStlZIvY9lS8 = new RestockConfig.RestockingConfigListener();

      private RestockingConfigListener() {
      }

      @EventHandler(priority = 200)
      private void fStlZIvY9lS8(InteractBlockEvent event) {
         if (RestockConfig.QfZy2gWnKInxFPr && RestockConfig.TJImwK2 != null) {
            BlockPos pos = event.result.getBlockPos();
            RestockConfig.fStlZIvY9lS8.computeIfAbsent(RestockConfig.TJImwK2, k -> new ArrayList<>()).add(pos);
            RestockConfig.i2Nu6XHKic();
            ChatUtils.info("Added container for §b%s", new Object[]{Registries.ITEM.getId(RestockConfig.TJImwK2).toString()});
            event.cancel();
         }
      }

      @EventHandler(priority = 200)
      private void fStlZIvY9lS8(OpenScreenEvent event) {
         if (RestockConfig.QfZy2gWnKInxFPr) {
            if (event.screen != null) {
               this.fStlZIvY9lS8();
               event.cancel();
            }
         }
      }

      private void fStlZIvY9lS8() {
         RestockConfig.QfZy2gWnKInxFPr = false;
         Item item = RestockConfig.TJImwK2;
         RestockConfig.TJImwK2 = null;
         int count = RestockConfig.fStlZIvY9lS8.getOrDefault(item, new ArrayList<>()).size();
         ChatUtils.info("Exited restocking config mode. Added §b%d§r containers for §b%s", new Object[]{count, RestockConfig.fStlZIvY9lS8(item)});
      }
   }
}
