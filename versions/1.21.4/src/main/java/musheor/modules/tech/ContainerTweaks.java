package musheor.modules.tech;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.BoolSetting.Builder;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.meteorclient.utils.misc.Keybind;
import meteordevelopment.meteorclient.utils.network.MeteorExecutor;
import meteordevelopment.meteorclient.utils.player.SlotUtils;
import musheor.utils.manager.pvfA15fQSVM;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import obf.jU8xXhmkg;

public class ContainerTweaks extends Module {
   private final SettingGroup voxTwgNzyrpaAnP = this.settings.getDefaultGroup();
   private final MinecraftClient xileXsbo = MinecraftClient.getInstance();
   public static ContainerTweaks fStlZIvY9lS8;
   private final Setting<Boolean> nOwp0NYl = this.voxTwgNzyrpaAnP
      .add(((Builder)((Builder)((Builder)new Builder().name("only-shulkers")).description("Only moves shulkers.")).defaultValue(true)).build());
   public final Setting<Boolean> QfZy2gWnKInxFPr = this.voxTwgNzyrpaAnP
      .add(((Builder)((Builder)((Builder)new Builder().name("show-buttons")).description("Shows stealing and dumping buttons.")).defaultValue(false)).build());
   public final Setting<Keybind> TJImwK2 = this.voxTwgNzyrpaAnP
      .add(
         ((meteordevelopment.meteorclient.settings.KeybindSetting.Builder)((meteordevelopment.meteorclient.settings.KeybindSetting.Builder)((meteordevelopment.meteorclient.settings.KeybindSetting.Builder)new meteordevelopment.meteorclient.settings.KeybindSetting.Builder()
                     .name("move-matching"))
                  .description("Moves all matching items between inventories."))
               .defaultValue(Keybind.none()))
            .build()
      );
   public final Setting<Keybind> wSJeYsJcmjNZf = this.voxTwgNzyrpaAnP
      .add(
         ((meteordevelopment.meteorclient.settings.KeybindSetting.Builder)((meteordevelopment.meteorclient.settings.KeybindSetting.Builder)((meteordevelopment.meteorclient.settings.KeybindSetting.Builder)new meteordevelopment.meteorclient.settings.KeybindSetting.Builder()
                     .name("move-all"))
                  .description("Moves all items from one inventory to the other."))
               .defaultValue(Keybind.none()))
            .build()
      );
   public final Setting<List<Item>> i2Nu6XHKic = this.voxTwgNzyrpaAnP
      .add(
         ((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)((meteordevelopment.meteorclient.settings.ItemListSetting.Builder)new meteordevelopment.meteorclient.settings.ItemListSetting.Builder()
                     .name("bundle-item-whitelist"))
                  .description("Items that are allowed to be bundled / unbundled"))
               .defaultValue(new ArrayList()))
            .build()
      );
   public final Setting<Integer> vGAMA89H = this.voxTwgNzyrpaAnP
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("steal-offset-x"))
                  .defaultValue(0))
               .visible(() -> false))
            .build()
      );
   public final Setting<Integer> qhulAUwd = this.voxTwgNzyrpaAnP
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("steal-offset-y"))
                  .defaultValue(0))
               .visible(() -> false))
            .build()
      );
   public final Setting<Integer> BrmKXSYhWhqoB1 = this.voxTwgNzyrpaAnP
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("dump-offset-x"))
                  .defaultValue(0))
               .visible(() -> false))
            .build()
      );
   public final Setting<Integer> TXWyNb = this.voxTwgNzyrpaAnP
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("dump-offset-y"))
                  .defaultValue(0))
               .visible(() -> false))
            .build()
      );
   public final Setting<Integer> I3psabmOrOI = this.voxTwgNzyrpaAnP
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("bundle-offset-x"))
                  .defaultValue(0))
               .visible(() -> false))
            .build()
      );
   public final Setting<Integer> duJ6sPeetAcBh6P = this.voxTwgNzyrpaAnP
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("bundle-offset-y"))
                  .defaultValue(0))
               .visible(() -> false))
            .build()
      );
   public final Setting<Integer> j376sIQ = this.voxTwgNzyrpaAnP
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("unbundle-offset-x"))
                  .defaultValue(0))
               .visible(() -> false))
            .build()
      );
   public final Setting<Integer> V7zKJ4n6D = this.voxTwgNzyrpaAnP
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("unbundle-offset-y"))
                  .defaultValue(0))
               .visible(() -> false))
            .build()
      );

   public ContainerTweaks() {
      super(musheor.musheor.MAIN, "container-tweaks", "A more advanced way to steal and store items in containers");
      fStlZIvY9lS8 = this;
   }

   private void fStlZIvY9lS8(ScreenHandler handler, int start, int end, boolean steal) {
      for (int i = start;
         i < end && !this.fStlZIvY9lS8() && (pvfA15fQSVM.duJ6sPeetAcBh6P() != 0 || !steal) && (pvfA15fQSVM.fStlZIvY9lS8(handler) != 0 || steal);
         i++
      ) {
         if (handler.getSlot(i).hasStack()) {
            ItemStack stack = handler.getSlot(i).getStack();
            if (this.fStlZIvY9lS8(stack) || !(Boolean)this.nOwp0NYl.get()) {
               if (this.xileXsbo.currentScreen == null || !Utils.canUpdate()) {
                  break;
               }

               this.xileXsbo.interactionManager.clickSlot(handler.syncId, i, 0, SlotActionType.QUICK_MOVE, this.xileXsbo.player);
            }
         }
      }
   }

   public void fStlZIvY9lS8(ScreenHandler handler, Slot clickedSlot) {
      ItemStack target = clickedSlot.getStack().copy();
      if (!target.isEmpty()) {
         boolean clickedInPlayerInv = clickedSlot.inventory == this.xileXsbo.player.getInventory();
         MeteorExecutor.execute(
            () -> {
               for (int i = 0;
                  i < handler.slots.size()
                     && (pvfA15fQSVM.duJ6sPeetAcBh6P() != 0 || clickedInPlayerInv)
                     && (pvfA15fQSVM.fStlZIvY9lS8(handler) != 0 || !clickedInPlayerInv)
                     && !this.fStlZIvY9lS8();
                  i++
               ) {
                  Slot slot = handler.getSlot(i);
                  if ((clickedInPlayerInv ? slot.inventory == this.xileXsbo.player.getInventory() : slot.inventory != this.xileXsbo.player.getInventory())
                     && slot.hasStack()
                     && (this.fStlZIvY9lS8(slot.getStack()) || !(Boolean)this.nOwp0NYl.get())
                     && ItemStack.areItemsAndComponentsEqual(slot.getStack(), target)) {
                     if (this.xileXsbo.currentScreen == null || !Utils.canUpdate()) {
                        break;
                     }

                     this.xileXsbo.interactionManager.clickSlot(handler.syncId, i, 0, SlotActionType.QUICK_MOVE, this.xileXsbo.player);
                  }
               }
            }
         );
      }
   }

   public void QfZy2gWnKInxFPr(ScreenHandler handler, Slot clickedSlot) {
      ItemStack target = clickedSlot.getStack();
      if (!target.isEmpty()) {
         boolean clickedInPlayerInv = clickedSlot.inventory == this.xileXsbo.player.getInventory();
         MeteorExecutor.execute(
            () -> {
               for (int i = 0;
                  i < handler.slots.size()
                     && (pvfA15fQSVM.duJ6sPeetAcBh6P() != 0 || clickedInPlayerInv)
                     && (pvfA15fQSVM.fStlZIvY9lS8(handler) != 0 || !clickedInPlayerInv)
                     && !this.fStlZIvY9lS8();
                  i++
               ) {
                  Slot slot = handler.getSlot(i);
                  if ((clickedInPlayerInv ? slot.inventory == this.xileXsbo.player.getInventory() : slot.inventory != this.xileXsbo.player.getInventory())
                     && slot.hasStack()
                     && (this.fStlZIvY9lS8(slot.getStack()) || !(Boolean)this.nOwp0NYl.get())) {
                     if (this.xileXsbo.currentScreen == null || !Utils.canUpdate()) {
                        break;
                     }

                     this.xileXsbo.interactionManager.clickSlot(handler.syncId, i, 0, SlotActionType.QUICK_MOVE, this.xileXsbo.player);
                  }
               }
            }
         );
      }
   }

   public void fStlZIvY9lS8(ScreenHandler handler) {
      MeteorExecutor.execute(() -> this.fStlZIvY9lS8(handler, 0, SlotUtils.indexToId(9), true));
   }

   public void QfZy2gWnKInxFPr(ScreenHandler handler) {
      int playerInvOffset = SlotUtils.indexToId(9);
      MeteorExecutor.execute(() -> this.fStlZIvY9lS8(handler, playerInvOffset, playerInvOffset + 36, false));
   }

   public void TJImwK2(ScreenHandler handler) {
      if (!((List)this.i2Nu6XHKic.get()).isEmpty()) {
         MeteorExecutor.execute(() -> {
            int playerInvStart = SlotUtils.indexToId(9);

            for (int i = 0; i < handler.slots.size(); i++) {
               Slot bundleSlotRef = handler.getSlot(i);
               if (bundleSlotRef.hasStack() && bundleSlotRef.getStack().getItem() instanceof BundleItem && this.QfZy2gWnKInxFPr(bundleSlotRef.getStack())) {
                  boolean bundleInPlayerInv = i >= playerInvStart;
                  int srcStart = bundleInPlayerInv ? 0 : playerInvStart;
                  int srcEnd = bundleInPlayerInv ? playerInvStart : handler.slots.size();

                  for (Item targetItem : this.i2Nu6XHKic.get()) {
                     if (this.xileXsbo.currentScreen == null || !Utils.canUpdate()) {
                        return;
                     }

                     if (this.fStlZIvY9lS8()) {
                        return;
                     }

                     int srcSlot = -1;

                     for (int j = srcStart; j < srcEnd; j++) {
                        Slot s = handler.getSlot(j);
                        if (s.hasStack() && s.getStack().getItem() == targetItem) {
                           srcSlot = j;
                           break;
                        }
                     }

                     if (srcSlot != -1) {
                        this.xileXsbo.interactionManager.clickSlot(handler.syncId, srcSlot, 0, SlotActionType.PICKUP, this.xileXsbo.player);
                        this.xileXsbo.interactionManager.clickSlot(handler.syncId, i, 0, SlotActionType.PICKUP, this.xileXsbo.player);
                     }
                  }
               }
            }
         });
      }
   }

   public void wSJeYsJcmjNZf(ScreenHandler handler) {
      if (!((List)this.i2Nu6XHKic.get()).isEmpty()) {
         MeteorExecutor.execute(
            () -> {
               int playerInvStart = SlotUtils.indexToId(9);

               for (int i = 0; i < handler.slots.size(); i++) {
                  Slot slot = handler.getSlot(i);
                  if (slot.hasStack() && slot.getStack().getItem() instanceof BundleItem) {
                     BundleContentsComponent contents = (BundleContentsComponent)slot.getStack()
                        .getOrDefault(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT);
                     if (!contents.isEmpty()) {
                        int extractCount = 0;

                        for (ItemStack bundled : contents.iterate()) {
                           if (((List)this.i2Nu6XHKic.get()).contains(bundled.getItem())) {
                              extractCount++;
                           }
                        }

                        if (extractCount != 0) {
                           boolean bundleInPlayerInv = i >= playerInvStart;
                           int dstStart = bundleInPlayerInv ? 0 : playerInvStart;
                           int dstEnd = bundleInPlayerInv ? playerInvStart : handler.slots.size();

                           for (int k = 0; k < extractCount; k++) {
                              if (this.xileXsbo.currentScreen == null || !Utils.canUpdate()) {
                                 return;
                              }

                              if (this.fStlZIvY9lS8()) {
                                 return;
                              }

                              if (bundleInPlayerInv && pvfA15fQSVM.fStlZIvY9lS8(handler) == 0) {
                                 return;
                              }

                              if (!bundleInPlayerInv && pvfA15fQSVM.duJ6sPeetAcBh6P() == 0) {
                                 return;
                              }

                              this.xileXsbo.interactionManager.clickSlot(handler.syncId, i, 1, SlotActionType.PICKUP, this.xileXsbo.player);

                              for (int j = dstStart; j < dstEnd; j++) {
                                 if (!handler.getSlot(j).hasStack()) {
                                    this.xileXsbo.interactionManager.clickSlot(handler.syncId, j, 0, SlotActionType.PICKUP, this.xileXsbo.player);
                                    break;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         );
      }
   }

   private boolean fStlZIvY9lS8() {
      if (!jU8xXhmkg.QfZy2gWnKInxFPr()) {
         this.warning("§cCancelled slot movement to prevent packet kick!", new Object[0]);
         return true;
      } else {
         return false;
      }
   }

   private boolean QfZy2gWnKInxFPr(ItemStack stack) {
      return ((BundleContentsComponent)stack.getOrDefault(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT)).isEmpty();
   }

   boolean fStlZIvY9lS8(ItemStack stack) {
      return stack.getItem() instanceof BlockItem && ((BlockItem)stack.getItem()).getBlock() instanceof ShulkerBoxBlock;
   }
}
