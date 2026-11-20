package musheor.modules.automation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.gui.GuiTheme;
import meteordevelopment.meteorclient.gui.widgets.WWidget;
import meteordevelopment.meteorclient.gui.widgets.containers.WVerticalList;
import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
import meteordevelopment.meteorclient.settings.BoolSetting.Builder;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.orbit.EventHandler;
import musheor.musheor;
import musheor.utils.irzXX;
import musheor.utils.manager.PathKit;
import musheor.utils.oyDPiOVBGKG;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.LookAndOnGround;

public class InventoryCleaner extends Module {
   private static final MinecraftClient QfZy2gWnKInxFPr = MinecraftClient.getInstance();
   private final SettingGroup TJImwK2 = this.settings.getDefaultGroup();
   private final Setting<Boolean> wSJeYsJcmjNZf = this.TJImwK2
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("drop-empty-shulker")).description("Allow the paver to dispose of empty shulkerboxes."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Integer> i2Nu6XHKic = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("shulker-grace-ticks"))
                     .description(
                        "Ticks a shulker must appear empty before it is dropped. Prevents dropping freshly picked-up shulkers whose contents haven't synced from the server yet."
                     ))
                  .defaultValue(5))
               .sliderRange(2, 20)
               .visible(this.wSJeYsJcmjNZf::get))
            .build()
      );
   private final Setting<Boolean> vGAMA89H = this.TJImwK2
      .add(((Builder)((Builder)((Builder)new Builder().name("ignore-hotbar")).description("Ignore items in the player's hotbar.")).defaultValue(true)).build());
   private final Setting<Boolean> qhulAUwd = this.TJImwK2
      .add(((Builder)((Builder)((Builder)new Builder().name("rotate-drop")).description("Drop items behind you")).defaultValue(false)).build());
   private final Setting<InventoryCleaner.ItemFilterList> BrmKXSYhWhqoB1 = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)new meteordevelopment.meteorclient.settings.EnumSetting.Builder()
                     .name("item-filter"))
                  .description("Whitelist keeps selected items in the inventory, Blacklist throws selected items out."))
               .defaultValue(InventoryCleaner.ItemFilterList.fStlZIvY9lS8))
            .build()
      );
   private final Setting<List<Item>> TXWyNb = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)new meteordevelopment.meteorclient.settings.ItemListSetting.Builder()
                  .name("drop-item-list"))
               .description("List of items."))
            .defaultValue(new Item[]{Items.NETHERRACK})
            .build()
      );
   private final Setting<Integer> I3psabmOrOI = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("drop-delay"))
                  .description("Delay between dropping items."))
               .defaultValue(2))
            .sliderRange(1, 20)
            .build()
      );
   private int duJ6sPeetAcBh6P = 0;
   private final Map<Integer, Integer> j376sIQ = new HashMap<>();

   public InventoryCleaner() {
      super(musheor.AUTOMATION, "inv-cleaner", "Automatically dispose of unwanted items.");
   }

   public WWidget getWidget(GuiTheme theme) {
      WVerticalList list = theme.verticalList();
      WButton scanInventoryAndSetItems = (WButton)list.add(theme.button("Blacklist inventory")).widget();
      scanInventoryAndSetItems.action = this::fStlZIvY9lS8;
      return list;
   }

   public void onDeactivate() {
      this.duJ6sPeetAcBh6P = 0;
      this.j376sIQ.clear();
   }

   @EventHandler
   private void fStlZIvY9lS8(Pre event) {
      if (QfZy2gWnKInxFPr.player != null && QfZy2gWnKInxFPr.world != null) {
         int firstSlot = !this.vGAMA89H.get() ? 0 : 9;

         for (int i = firstSlot; i < QfZy2gWnKInxFPr.player.getInventory().main.size(); i++) {
            ItemStack stack = QfZy2gWnKInxFPr.player.getInventory().getStack(i);
            if (stack.getItem() instanceof BlockItem bi && bi.getBlock() instanceof ShulkerBoxBlock && PathKit.TJImwK2(stack)) {
               this.j376sIQ.merge(i, 1, Integer::sum);
            } else {
               this.j376sIQ.remove(i);
            }
         }

         for (int ix = firstSlot; ix < QfZy2gWnKInxFPr.player.getInventory().main.size(); ix++) {
            if (this.duJ6sPeetAcBh6P < (Integer)this.I3psabmOrOI.get()) {
               this.duJ6sPeetAcBh6P++;
               return;
            }

            ItemStack itemStack = QfZy2gWnKInxFPr.player.getInventory().getStack(ix);
            if (!(itemStack.getItem() instanceof BlockItem) || !(((BlockItem)itemStack.getItem()).getBlock() instanceof ShulkerBoxBlock)) {
               if (itemStack.getItem() != Items.AIR
                  && (this.BrmKXSYhWhqoB1.get() != InventoryCleaner.ItemFilterList.fStlZIvY9lS8 || !((List)this.TXWyNb.get()).contains(itemStack.getItem()))
                  && (
                     this.BrmKXSYhWhqoB1.get() != InventoryCleaner.ItemFilterList.QfZy2gWnKInxFPr
                        || ((List)this.TXWyNb.get()).contains(itemStack.getItem())
                  )) {
                  if ((Boolean)this.qhulAUwd.get()) {
                     this.fStlZIvY9lS8(ix);
                     this.duJ6sPeetAcBh6P = 0;
                  } else {
                     InvUtils.drop().slot(ix);
                     this.duJ6sPeetAcBh6P = 0;
                  }
               }
            } else if ((Boolean)this.wSJeYsJcmjNZf.get()
               && PathKit.TJImwK2(itemStack)
               && this.j376sIQ.getOrDefault(ix, 0) >= (Integer)this.i2Nu6XHKic.get()) {
               this.j376sIQ.remove(ix);
               if ((Boolean)this.qhulAUwd.get()) {
                  this.fStlZIvY9lS8(ix);
               } else {
                  InvUtils.drop().slot(ix);
               }

               this.duJ6sPeetAcBh6P = 0;
            }
         }
      }
   }

   private void fStlZIvY9lS8() {
      if (QfZy2gWnKInxFPr.player != null && QfZy2gWnKInxFPr.world != null) {
         ((List)this.TXWyNb.get()).clear();
         this.BrmKXSYhWhqoB1.set(InventoryCleaner.ItemFilterList.fStlZIvY9lS8);

         for (int i = 0; i <= QfZy2gWnKInxFPr.player.getInventory().size(); i++) {
            Item currentItem = QfZy2gWnKInxFPr.player.getInventory().getStack(i).getItem();
            if (!((List)this.TXWyNb.get()).contains(currentItem)) {
               ((List)this.TXWyNb.get()).add(currentItem);
            }
         }

         ChatUtils.info("Inventory scanned and blacklisted!", new Object[0]);
      }
   }

   private void fStlZIvY9lS8(int i) {
      assert QfZy2gWnKInxFPr.player != null;

      float previousYaw = QfZy2gWnKInxFPr.player.getYaw();
      irzXX.fStlZIvY9lS8(false);
      QfZy2gWnKInxFPr.player
         .networkHandler
         .sendPacket(
            new PlayerMoveC2SPacket.LookAndOnGround(
               oyDPiOVBGKG.gVOhtBej1x.QfZy2gWnKInxFPr(QfZy2gWnKInxFPr.player.getYaw()), -20.0F, QfZy2gWnKInxFPr.player.isOnGround(), false
            )
         );
      InvUtils.drop().slot(i);
      QfZy2gWnKInxFPr.player.setYaw(previousYaw);
   }

   public static enum ItemFilterList {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr;
   }
}
