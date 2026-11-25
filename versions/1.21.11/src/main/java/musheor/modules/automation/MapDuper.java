package musheor.modules.automation;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.IntSetting.Builder;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import musheor.musheor;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.component.type.MapIdComponent;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.CartographyTableScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import obf.jU8xXhmkg;

public class MapDuper extends Module {
   private final SettingGroup fStlZIvY9lS8 = this.settings.getDefaultGroup();
   private final SettingGroup QfZy2gWnKInxFPr = this.settings.createGroup("Bundles");
   private final Setting<Integer> TJImwK2 = this.fStlZIvY9lS8
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("copies")).description("How many duplicates to make of each map. Each one costs one empty map."))
               .defaultValue(1))
            .sliderRange(1, 16)
            .build()
      );
   private final Setting<Integer> wSJeYsJcmjNZf = this.fStlZIvY9lS8
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("delay")).description("Extra ticks to wait between slot actions. 0 acts once every tick."))
               .defaultValue(3))
            .sliderRange(0, 20)
            .build()
      );
   private final Setting<Boolean> i2Nu6XHKic = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("disable-when-done"))
                  .description("Turn the module off once a run finishes."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> vGAMA89H = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("close-table-when-done"))
                  .description("Close the cartography table once a run finishes."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> qhulAUwd = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("chat-feedback"))
                  .description("Reports what was duplicated, and why a run stopped early."))
               .defaultValue(false))
            .build()
      );
   private final Setting<Boolean> BrmKXSYhWhqoB1 = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("use-bundles"))
                  .description("Take the maps to copy out of a bundle first. Off, the loose maps in your inventory are the job."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> TXWyNb = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("drop-overflow"))
                     .description(
                        "A bundle holds more maps than the inventory has slots, so once it is full the rest are thrown on the floor — looking straight down, so they land at your feet."
                     ))
                  .defaultValue(false))
               .visible(this.BrmKXSYhWhqoB1::get))
            .build()
      );
   private final Setting<Boolean> I3psabmOrOI = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("originals-to-source"))
                     .description("Put one of each map back into the bundle it came out of."))
                  .defaultValue(true))
               .visible(this.BrmKXSYhWhqoB1::get))
            .build()
      );
   private final Setting<Boolean> duJ6sPeetAcBh6P = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("copies-to-bundles"))
                     .description(
                        "Put the new copies into the other bundles in your inventory, filling them in slot order. Off, or with no bundle free, copies are left loose."
                     ))
                  .defaultValue(true))
               .visible(this.BrmKXSYhWhqoB1::get))
            .build()
      );
   private final Setting<Integer> j376sIQ = this.QfZy2gWnKInxFPr
      .add(
         ((Builder)((Builder)((Builder)((Builder)new Builder().name("maps-per-bundle"))
                     .description("How many maps to put in a copy bundle before moving on to the next one. A bundle holds 64."))
                  .defaultValue(64))
               .min(1)
               .sliderRange(1, 64)
               .visible(() -> (Boolean)this.BrmKXSYhWhqoB1.get() && (Boolean)this.duJ6sPeetAcBh6P.get()))
            .build()
      );
   private MapDuper.Stage V7zKJ4n6D = MapDuper.Stage.Done;
   private final Set<MapIdComponent> voxTwgNzyrpaAnP = new LinkedHashSet<>();
   private final Deque<MapIdComponent> xileXsbo = new ArrayDeque<>();
   private int nOwp0NYl;
   private boolean umvNbb7iD9UpR;
   private final Set<MapIdComponent> zkcZ4Xkp9Ez7Y = new LinkedHashSet<>();
   private final Set<MapIdComponent> flWHzEyn = new LinkedHashSet<>();
   private int FVjbhYkk53UT = -1;
   private int Nt3xeF = -1;
   private int zXcO5rZ;
   private int jbUe84V1vykfK;
   private boolean zbN84Osn9p5lD;
   private int ZlNrrNcv3Ylc = -1;

   public MapDuper() {
      super(musheor.AUTOMATION, "map-duper", "Duplicates maparts at a cartography table, optionally straight out of and back into bundles.");
   }

   public void onActivate() {
      this.QfZy2gWnKInxFPr();
   }

   public void onDeactivate() {
      this.QfZy2gWnKInxFPr();
   }

   private void fStlZIvY9lS8() {
      this.V7zKJ4n6D = MapDuper.Stage.Done;
      this.voxTwgNzyrpaAnP.clear();
      this.xileXsbo.clear();
      this.nOwp0NYl = 0;
      this.umvNbb7iD9UpR = false;
      this.zkcZ4Xkp9Ez7Y.clear();
      this.flWHzEyn.clear();
      this.FVjbhYkk53UT = -1;
      this.Nt3xeF = -1;
      this.zXcO5rZ = 0;
      this.jbUe84V1vykfK = 0;
      this.zbN84Osn9p5lD = false;
   }

   private void QfZy2gWnKInxFPr() {
      this.fStlZIvY9lS8();
      this.ZlNrrNcv3Ylc = -1;
   }

   @EventHandler
   private void fStlZIvY9lS8(Pre event) {
      if (this.mc.player != null && this.mc.world != null && this.mc.interactionManager != null) {
         CartographyTableScreenHandler handler = this.TJImwK2();
         if (handler == null) {
            if (this.V7zKJ4n6D != MapDuper.Stage.Done) {
               this.QfZy2gWnKInxFPr("Cartography table closed");
            }
         } else {
            if (this.V7zKJ4n6D == MapDuper.Stage.Done) {
               if (handler.syncId == this.ZlNrrNcv3Ylc) {
                  return;
               }

               this.fStlZIvY9lS8(handler);
               if (this.V7zKJ4n6D == MapDuper.Stage.Done) {
                  return;
               }
            }

            if (--this.zXcO5rZ <= 0) {
               this.zXcO5rZ = (Integer)this.wSJeYsJcmjNZf.get() + 1;
               if (jU8xXhmkg.QfZy2gWnKInxFPr()) {
                  this.TJImwK2(handler);
               }
            }
         }
      }
   }

   private CartographyTableScreenHandler TJImwK2() {
      if (this.mc.player == null) {
         return null;
      } else {
         return this.mc.player.currentScreenHandler instanceof CartographyTableScreenHandler table ? table : null;
      }
   }

   private void fStlZIvY9lS8(CartographyTableScreenHandler handler) {
      this.voxTwgNzyrpaAnP.clear();
      this.xileXsbo.clear();
      this.nOwp0NYl = 0;
      this.umvNbb7iD9UpR = false;
      this.zkcZ4Xkp9Ez7Y.clear();
      this.flWHzEyn.clear();
      this.Nt3xeF = -1;
      this.zbN84Osn9p5lD = false;
      this.FVjbhYkk53UT = this.BrmKXSYhWhqoB1.get() ? this.duJ6sPeetAcBh6P(handler) : -1;
      boolean bundled = this.FVjbhYkk53UT >= 0;
      this.voxTwgNzyrpaAnP.addAll(bundled ? this.j376sIQ(handler) : this.V7zKJ4n6D(handler));
      if (this.voxTwgNzyrpaAnP.isEmpty()) {
         this.FVjbhYkk53UT = -1;
      } else {
         this.jbUe84V1vykfK = this.voxTwgNzyrpaAnP.size();
         if (bundled) {
            this.V7zKJ4n6D = MapDuper.Stage.Extract;
         } else {
            this.QfZy2gWnKInxFPr(handler);
         }

         if ((Boolean)this.qhulAUwd.get()) {
            this.info(
               "Duplicating §b%d§r map(s) §b%d§r time(s) each%s.",
               new Object[]{this.voxTwgNzyrpaAnP.size(), this.TJImwK2.get(), bundled ? " from a bundle" : ""}
            );
         }
      }
   }

   private void QfZy2gWnKInxFPr(CartographyTableScreenHandler handler) {
      this.xileXsbo.clear();
      this.umvNbb7iD9UpR = false;

      for (MapIdComponent id : this.voxTwgNzyrpaAnP) {
         if (!this.flWHzEyn.contains(id) && this.fStlZIvY9lS8((ScreenHandler)handler, id) > 0) {
            this.xileXsbo.addLast(id);
         }
      }

      this.nOwp0NYl = (Integer)this.TJImwK2.get();
      this.V7zKJ4n6D = MapDuper.Stage.Copy;
   }

   private boolean TJImwK2(CartographyTableScreenHandler handler) {
      return switch (this.V7zKJ4n6D) {
         case Extract -> this.wSJeYsJcmjNZf(handler);
         case Copy -> this.vGAMA89H(handler);
         case Store -> this.BrmKXSYhWhqoB1(handler);
         case Done -> false;
      };
   }

   private boolean wSJeYsJcmjNZf(CartographyTableScreenHandler handler) {
      boolean acted = false;

      for (int i = 0; i < 160 && jU8xXhmkg.QfZy2gWnKInxFPr(); i++) {
         boolean hadPitch = this.zbN84Osn9p5lD;
         if (!this.i2Nu6XHKic(handler)) {
            break;
         }

         acted = true;
         if (this.TJImwK2() == null || this.V7zKJ4n6D != MapDuper.Stage.Extract || !hadPitch && this.zbN84Osn9p5lD) {
            break;
         }
      }

      return acted;
   }

   private boolean i2Nu6XHKic(CartographyTableScreenHandler handler) {
      if (fStlZIvY9lS8(handler.getCursorStack()) != null) {
         int free = this.countFreeInventorySlots(handler) > 4 ? this.findFreeInventorySlot(handler) : -1;
         if (free >= 0) {
            this.fStlZIvY9lS8(handler, free, 0, SlotActionType.PICKUP);
            return true;
         } else if (!(Boolean)this.TXWyNb.get()) {
            return this.fStlZIvY9lS8("Inventory full and drop-overflow is off");
         } else if (!this.zbN84Osn9p5lD) {
            this.mc.player.setPitch(90.0F);
            this.zbN84Osn9p5lD = true;
            return true;
         } else {
            this.fStlZIvY9lS8(handler, -999, 0, SlotActionType.PICKUP);
            return true;
         }
      } else if (this.I3psabmOrOI(handler)) {
         return true;
      } else if (!this.i2Nu6XHKic(handler, this.FVjbhYkk53UT)) {
         this.QfZy2gWnKInxFPr(handler);
         return true;
      } else {
         this.fStlZIvY9lS8(handler, this.FVjbhYkk53UT, 1, SlotActionType.PICKUP);
         return true;
      }
   }

   private boolean vGAMA89H(CartographyTableScreenHandler handler) {
      if (this.I3psabmOrOI(handler)) {
         return true;
      }

      if (this.countFreeInventorySlots(handler) < 2) {
         return this.fStlZIvY9lS8(handler, true) ? true : this.fStlZIvY9lS8("Inventory full and nothing left that can be put away");
      }

      MapIdComponent id = this.xileXsbo.peekFirst();
      if (id == null) {
         this.V7zKJ4n6D = MapDuper.Stage.Store;
         return true;
      }

      if (handler.getSlot(2).hasStack()) {
         this.fStlZIvY9lS8(handler, 2, 0, SlotActionType.QUICK_MOVE);
         if (this.umvNbb7iD9UpR) {
            this.umvNbb7iD9UpR = false;
            if (--this.nOwp0NYl <= 0) {
               this.fStlZIvY9lS8(id);
            }
         }

         return true;
      } else if (fStlZIvY9lS8(handler.getSlot(0).getStack()) != null) {
         if (this.qhulAUwd(handler)) {
            return true;
         }

         this.umvNbb7iD9UpR = true;
         return false;
      } else if (this.qhulAUwd(handler)) {
         return true;
      } else {
         int slot = this.fStlZIvY9lS8(handler, stack -> id.equals(fStlZIvY9lS8(stack)));
         if (slot < 0) {
            this.fStlZIvY9lS8(id);
            return true;
         } else {
            this.fStlZIvY9lS8(handler, slot, 0, SlotActionType.QUICK_MOVE);
            this.umvNbb7iD9UpR = true;
            return true;
         }
      }
   }

   private void fStlZIvY9lS8(MapIdComponent id) {
      this.flWHzEyn.add(id);
      this.xileXsbo.removeFirstOccurrence(id);
      this.nOwp0NYl = (Integer)this.TJImwK2.get();
      this.umvNbb7iD9UpR = false;
   }

   private boolean qhulAUwd(CartographyTableScreenHandler handler) {
      if (handler.getSlot(1).hasStack()) {
         return false;
      } else {
         int empty = this.fStlZIvY9lS8(handler, stack -> stack.isOf(Items.MAP));
         if (empty < 0) {
            this.fStlZIvY9lS8("Out of empty maps");
            return true;
         } else {
            this.fStlZIvY9lS8(handler, empty, 0, SlotActionType.QUICK_MOVE);
            return true;
         }
      }
   }

   private boolean BrmKXSYhWhqoB1(CartographyTableScreenHandler handler) {
      if (this.fStlZIvY9lS8(handler, false)) {
         return true;
      } else if (this.hasUnfinishedMapsInSourceBundle(handler) && this.countFreeInventorySlots(handler) > 4) {
         this.V7zKJ4n6D = MapDuper.Stage.Extract;
         return true;
      } else {
         this.QfZy2gWnKInxFPr((String)null);
         return false;
      }
   }

   private boolean fStlZIvY9lS8(CartographyTableScreenHandler handler, boolean onlyCompleted) {
      boolean acted = false;

      for (int i = 0; i < 160 && jU8xXhmkg.QfZy2gWnKInxFPr() && this.QfZy2gWnKInxFPr(handler, onlyCompleted); i++) {
         acted = true;
         if (this.TJImwK2() == null) {
            break;
         }
      }

      return acted;
   }

   private boolean QfZy2gWnKInxFPr(CartographyTableScreenHandler handler, boolean onlyCompleted) {
      ItemStack cursor = handler.getCursorStack();
      if (fStlZIvY9lS8(cursor) != null) {
         if (cursor.getCount() > 1) {
            int free = this.findFreeInventorySlot(handler);
            if (free >= 0) {
               this.fStlZIvY9lS8(handler, free, 1, SlotActionType.PICKUP);
               return true;
            }
         }

         if (this.Nt3xeF >= 0 && this.fStlZIvY9lS8(handler, this.Nt3xeF)) {
            this.fStlZIvY9lS8(handler, this.Nt3xeF, 0, SlotActionType.PICKUP);
            this.Nt3xeF = -1;
            return true;
         } else {
            this.Nt3xeF = -1;
            return this.I3psabmOrOI(handler);
         }
      } else {
         if (this.I3psabmOrOI(handler)) {
            return true;
         }

         for (int slot = 3; slot < handler.slots.size(); slot++) {
            MapIdComponent id = fStlZIvY9lS8(handler.getSlot(slot).getStack());
            if (id != null && this.voxTwgNzyrpaAnP.contains(id) && (!onlyCompleted || this.flWHzEyn.contains(id))) {
               int destination = this.fStlZIvY9lS8(handler, id);
               if (destination >= 0) {
                  this.Nt3xeF = destination;
                  this.fStlZIvY9lS8(handler, slot, 0, SlotActionType.PICKUP);
                  return true;
               }
            }
         }

         return false;
      }
   }

   private int fStlZIvY9lS8(CartographyTableScreenHandler handler, MapIdComponent id) {
      if ((Boolean)this.I3psabmOrOI.get()
         && this.FVjbhYkk53UT >= 0
         && !this.zkcZ4Xkp9Ez7Y.contains(id)
         && this.fStlZIvY9lS8((ScreenHandler)handler, this.FVjbhYkk53UT)
         && this.TJImwK2(handler, this.FVjbhYkk53UT) < 64) {
         this.zkcZ4Xkp9Ez7Y.add(id);
         return this.FVjbhYkk53UT;
      } else {
         return this.duJ6sPeetAcBh6P.get() ? this.TXWyNb(handler) : -1;
      }
   }

   private int TXWyNb(CartographyTableScreenHandler handler) {
      for (int slot = 3; slot < handler.slots.size(); slot++) {
         if (slot != this.FVjbhYkk53UT
            && this.fStlZIvY9lS8((ScreenHandler)handler, slot)
            && this.wSJeYsJcmjNZf(handler, slot)
            && this.TJImwK2(handler, slot) < (Integer)this.j376sIQ.get()) {
            return slot;
         }
      }

      return -1;
   }

   private boolean fStlZIvY9lS8(CartographyTableScreenHandler handler, int slot) {
      if (!this.fStlZIvY9lS8((ScreenHandler)handler, slot)) {
         return false;
      }

      int limit = slot == this.FVjbhYkk53UT ? 64 : (Integer)this.j376sIQ.get();
      return this.TJImwK2(handler, slot) < limit;
   }

   private boolean I3psabmOrOI(CartographyTableScreenHandler handler) {
      if (handler.getCursorStack().isEmpty()) {
         return false;
      }

      int free = this.findFreeInventorySlot(handler);
      if (free < 0) {
         return false;
      }

      this.fStlZIvY9lS8(handler, free, 0, SlotActionType.PICKUP);
      return true;
   }

   private void fStlZIvY9lS8(ScreenHandler handler, int slot, int button, SlotActionType action) {
      this.mc.interactionManager.clickSlot(handler.syncId, slot, button, action, this.mc.player);
   }

   private boolean fStlZIvY9lS8(String reason) {
      this.QfZy2gWnKInxFPr(reason);
      return false;
   }

   private void QfZy2gWnKInxFPr(String reason) {
      if ((Boolean)this.qhulAUwd.get()) {
         if (reason == null) {
            this.info("Done — §b%d§r mapart(s) duplicated.", new Object[]{this.jbUe84V1vykfK});
         } else {
            this.warning("Stopped: %s.", new Object[]{reason});
         }
      }

      CartographyTableScreenHandler handler = this.TJImwK2();
      if (handler != null) {
         this.ZlNrrNcv3Ylc = handler.syncId;
      }

      this.fStlZIvY9lS8();
      if ((Boolean)this.vGAMA89H.get() && this.mc.player != null) {
         this.mc.player.closeHandledScreen();
      }

      if ((Boolean)this.i2Nu6XHKic.get()) {
         this.toggle();
      }
   }

   private static MapIdComponent fStlZIvY9lS8(ItemStack stack) {
      return !stack.isEmpty() && stack.isOf(Items.FILLED_MAP) ? (MapIdComponent)stack.get(DataComponentTypes.MAP_ID) : null;
   }

   private int fStlZIvY9lS8(ScreenHandler handler, MapIdComponent id) {
      int count = 0;

      for (int slot = 3; slot < handler.slots.size(); slot++) {
         if (id.equals(fStlZIvY9lS8(handler.getSlot(slot).getStack()))) {
            count++;
         }
      }

      return count;
   }

   private int fStlZIvY9lS8(ScreenHandler handler, Predicate<ItemStack> test) {
      for (int slot = 3; slot < handler.slots.size(); slot++) {
         ItemStack stack = handler.getSlot(slot).getStack();
         if (!stack.isEmpty() && test.test(stack)) {
            return slot;
         }
      }

      return -1;
   }

   private int countFreeInventorySlots(ScreenHandler handler) {
      int count = 0;

      for (int slot = 3; slot < handler.slots.size(); slot++) {
         if (!handler.getSlot(slot).hasStack()) {
            count++;
         }
      }

      return count;
   }

   private int findFreeInventorySlot(ScreenHandler handler) {
      for (int slot = 3; slot < handler.slots.size(); slot++) {
         if (!handler.getSlot(slot).hasStack()) {
            return slot;
         }
      }

      return -1;
   }

   private boolean fStlZIvY9lS8(ScreenHandler handler, int slot) {
      return slot >= 3 && slot < handler.slots.size() ? handler.getSlot(slot).getStack().getItem() instanceof BundleItem : false;
   }

   private static BundleContentsComponent QfZy2gWnKInxFPr(ScreenHandler handler, int slot) {
      return (BundleContentsComponent)handler.getSlot(slot).getStack().get(DataComponentTypes.BUNDLE_CONTENTS);
   }

   private int TJImwK2(ScreenHandler handler, int slot) {
      BundleContentsComponent contents = QfZy2gWnKInxFPr(handler, slot);
      if (contents == null) {
         return 0;
      }

      int count = 0;

      for (ItemStack inner : contents.iterate()) {
         if (fStlZIvY9lS8(inner) != null) {
            count += inner.getCount();
         }
      }

      return count;
   }

   private boolean wSJeYsJcmjNZf(ScreenHandler handler, int slot) {
      BundleContentsComponent contents = QfZy2gWnKInxFPr(handler, slot);
      if (contents != null && !contents.isEmpty()) {
         for (ItemStack inner : contents.iterate()) {
            if (fStlZIvY9lS8(inner) == null) {
               return false;
            }
         }

         return true;
      } else {
         return true;
      }
   }

   private boolean i2Nu6XHKic(ScreenHandler handler, int slot) {
      return this.fStlZIvY9lS8(handler, slot) && this.TJImwK2(handler, slot) > 0;
   }

   private boolean hasUnfinishedMapsInSourceBundle(ScreenHandler handler) {
      if (!this.fStlZIvY9lS8(handler, this.FVjbhYkk53UT)) {
         return false;
      }

      BundleContentsComponent contents = QfZy2gWnKInxFPr(handler, this.FVjbhYkk53UT);
      if (contents == null) {
         return false;
      }

      for (ItemStack inner : contents.iterate()) {
         MapIdComponent id = fStlZIvY9lS8(inner);
         if (id != null && !this.flWHzEyn.contains(id)) {
            return true;
         }
      }

      return false;
   }

   private int duJ6sPeetAcBh6P(CartographyTableScreenHandler handler) {
      for (int slot = 3; slot < handler.slots.size(); slot++) {
         if (this.i2Nu6XHKic(handler, slot)) {
            return slot;
         }
      }

      return -1;
   }

   private Set<MapIdComponent> j376sIQ(CartographyTableScreenHandler handler) {
      Set<MapIdComponent> ids = new LinkedHashSet<>();
      if (this.FVjbhYkk53UT < 0) {
         return ids;
      }

      BundleContentsComponent contents = QfZy2gWnKInxFPr(handler, this.FVjbhYkk53UT);
      if (contents == null) {
         return ids;
      }

      for (ItemStack inner : contents.iterate()) {
         MapIdComponent id = fStlZIvY9lS8(inner);
         if (id != null) {
            ids.add(id);
         }
      }

      return ids;
   }

   private Set<MapIdComponent> V7zKJ4n6D(CartographyTableScreenHandler handler) {
      Set<MapIdComponent> ids = new LinkedHashSet<>();

      for (int slot = 3; slot < handler.slots.size(); slot++) {
         MapIdComponent id = fStlZIvY9lS8(handler.getSlot(slot).getStack());
         if (id != null) {
            ids.add(id);
         }
      }

      return ids;
   }

   public String getInfoString() {
      return this.V7zKJ4n6D == MapDuper.Stage.Done ? null : this.V7zKJ4n6D.name().toLowerCase();
   }

   private enum Stage {
      Extract,
      Copy,
      Store,
      Done;

      Stage() {
      }
   }
}
