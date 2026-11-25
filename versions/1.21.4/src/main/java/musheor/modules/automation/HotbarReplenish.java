package musheor.modules.automation;

import java.util.List;
import java.util.function.Predicate;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.IntSetting.Builder;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.orbit.EventHandler;
import musheor.musheor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HotbarReplenish extends Module {
   private final SettingGroup QfZy2gWnKInxFPr = this.settings.getDefaultGroup();
   private static final MinecraftClient TJImwK2 = MinecraftClient.getInstance();
   private final Setting<Integer> wSJeYsJcmjNZf = this.QfZy2gWnKInxFPr
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("threshold")).description("The threshold of items left to trigger replenishment.")).defaultValue(16))
            .sliderRange(1, 63)
            .build()
      );
   private final Setting<Integer> i2Nu6XHKic = this.QfZy2gWnKInxFPr
      .add(((Builder)((Builder)((Builder)new Builder().name("delay")).description("Delay between slot movements in ticks")).defaultValue(2)).build());
   private final Setting<List<Item>> vGAMA89H = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)new meteordevelopment.meteorclient.settings.ItemListSetting.Builder()
                  .name("offhand-item"))
               .description("Item to maintain offhand slot.."))
            .defaultValue(new Item[0])
            .build()
      );
   private final Setting<List<Item>> qhulAUwd = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)new meteordevelopment.meteorclient.settings.ItemListSetting.Builder()
                  .name("slot-1"))
               .description("Item to maintain in the first hotbar slot."))
            .defaultValue(new Item[0])
            .build()
      );
   private final Setting<List<Item>> BrmKXSYhWhqoB1 = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)new meteordevelopment.meteorclient.settings.ItemListSetting.Builder()
                  .name("slot-2"))
               .description("Item to maintain in the second hotbar slot."))
            .defaultValue(new Item[0])
            .build()
      );
   private final Setting<List<Item>> TXWyNb = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)new meteordevelopment.meteorclient.settings.ItemListSetting.Builder()
                  .name("slot-3"))
               .description("Item to maintain in the third hotbar slot."))
            .defaultValue(new Item[0])
            .build()
      );
   private final Setting<List<Item>> I3psabmOrOI = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)new meteordevelopment.meteorclient.settings.ItemListSetting.Builder()
                  .name("slot-4"))
               .description("Item to maintain in the fourth hotbar slot."))
            .defaultValue(new Item[0])
            .build()
      );
   private final Setting<List<Item>> duJ6sPeetAcBh6P = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)new meteordevelopment.meteorclient.settings.ItemListSetting.Builder()
                  .name("slot-5"))
               .description("Item to maintain in the fifth hotbar slot."))
            .defaultValue(new Item[0])
            .build()
      );
   private final Setting<List<Item>> j376sIQ = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)new meteordevelopment.meteorclient.settings.ItemListSetting.Builder()
                  .name("slot-6"))
               .description("Item to maintain in the sixth hotbar slot."))
            .defaultValue(new Item[0])
            .build()
      );
   private final Setting<List<Item>> V7zKJ4n6D = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)new meteordevelopment.meteorclient.settings.ItemListSetting.Builder()
                  .name("slot-7"))
               .description("Item to maintain in the seventh hotbar slot."))
            .defaultValue(new Item[0])
            .build()
      );
   private final Setting<List<Item>> voxTwgNzyrpaAnP = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)new meteordevelopment.meteorclient.settings.ItemListSetting.Builder()
                  .name("slot-8"))
               .description("Item to maintain in the eighth hotbar slot."))
            .defaultValue(new Item[0])
            .build()
      );
   private final Setting<List<Item>> xileXsbo = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)new meteordevelopment.meteorclient.settings.ItemListSetting.Builder()
                  .name("slot-9"))
               .description("Item to maintain in the ninth hotbar slot."))
            .defaultValue(new Item[0])
            .build()
      );
   private static final int[] nOwp0NYl = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 45};
   private int umvNbb7iD9UpR;

   public HotbarReplenish() {
      super(musheor.AUTOMATION, "hotbar-replenish", "Automatically refills specific items in each hotbar slot. Each slot independently configurable.");
   }

   public void onActivate() {
      this.umvNbb7iD9UpR = 0;
   }

   @EventHandler
   private void fStlZIvY9lS8(Pre event) {
      if (this.isActive()) {
         if (TJImwK2.player != null && TJImwK2.world != null) {
            if (((HighwayBuilder)Modules.get().get(HighwayBuilder.class)).isActive() && !((List)this.xileXsbo.get()).isEmpty()) {
               this.xileXsbo.reset();
               ChatUtils.warning("Warning: Reset last slot in Hotbar-Replenish because HighwayBuilder restocking reserves this for restocking!", new Object[0]);
            }

            if (this.umvNbb7iD9UpR > 0) {
               this.umvNbb7iD9UpR--;
            } else {
               for (int slot : nOwp0NYl) {
                  if (this.fStlZIvY9lS8(slot)) {
                     this.umvNbb7iD9UpR = (Integer)this.i2Nu6XHKic.get();
                     return;
                  }
               }
            }
         }
      }
   }

   private boolean fStlZIvY9lS8(int slot) {
      List<Item> allowed = this.TJImwK2(slot);
      if (allowed.isEmpty()) {
         return false;
      }

      ItemStack current = this.QfZy2gWnKInxFPr(slot);
      if (!current.isEmpty() && allowed.contains(current.getItem())) {
         if (current.isStackable() && current.getCount() <= (Integer)this.wSJeYsJcmjNZf.get()) {
            int source = this.fStlZIvY9lS8(stack -> ItemStack.areItemsAndComponentsEqual(stack, current), slot);
            if (source == -1) {
               return false;
            }

            InvUtils.move().from(source).to(slot);
            return true;
         } else {
            return false;
         }
      } else {
         int source = this.fStlZIvY9lS8(stack -> allowed.contains(stack.getItem()), slot);
         if (source == -1) {
            return false;
         }

         InvUtils.move().from(source).to(slot);
         return true;
      }
   }

   private int fStlZIvY9lS8(Predicate<ItemStack> wanted, int excludedSlot) {
      assert TJImwK2.player != null;
      int best = -1;
      int bestCount = Integer.MAX_VALUE;

      for (int i = 0; i <= 35; i++) {
         if (i != excludedSlot && this.TJImwK2(i).isEmpty()) {
            ItemStack stack = TJImwK2.player.getInventory().getStack(i);
            if (!stack.isEmpty() && wanted.test(stack) && stack.getCount() < bestCount) {
               best = i;
               bestCount = stack.getCount();
            }
         }
      }

      return best;
   }

   private ItemStack QfZy2gWnKInxFPr(int slot) {
      assert TJImwK2.player != null;
      return slot == 45 ? TJImwK2.player.getOffHandStack() : TJImwK2.player.getInventory().getStack(slot);
   }

   private List<Item> TJImwK2(int slot) {
      if (slot == 45) {
         return (List<Item>)this.vGAMA89H.get();
      }

      return switch (slot) {
         case 0 -> (List)this.qhulAUwd.get();
         case 1 -> (List)this.BrmKXSYhWhqoB1.get();
         case 2 -> (List)this.TXWyNb.get();
         case 3 -> (List)this.I3psabmOrOI.get();
         case 4 -> (List)this.duJ6sPeetAcBh6P.get();
         case 5 -> (List)this.j376sIQ.get();
         case 6 -> (List)this.V7zKJ4n6D.get();
         case 7 -> (List)this.voxTwgNzyrpaAnP.get();
         case 8 -> (List)this.xileXsbo.get();
         default -> List.of();
      };
   }
}
