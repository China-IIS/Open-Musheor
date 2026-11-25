package musheor.utils.manager;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.GoalBlock;
import java.util.List;
import java.util.Objects;
import meteordevelopment.meteorclient.mixin.ContainerComponentAccessor;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.IntSetting.Builder;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.player.AutoEat;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import meteordevelopment.meteorclient.utils.player.SlotUtils;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import musheor.compat.VersionHelper;
import musheor.modules.automation.ElytraTweakz;
import musheor.modules.automation.HighwayBuilder;
import musheor.modules.automation.KekNuker;
import musheor.modules.features.KekMine;
import musheor.utils.WOGVSBb;
import musheor.utils.irzXX;
import musheor.utils.oyDPiOVBGKG;
import musheor.utils.system.MusheorSystem;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.DropperBlock;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class pvfA15fQSVM extends Module {
   private final SettingGroup zXcO5rZ = this.settings.createGroup("Items");
   private final SettingGroup jbUe84V1vykfK = this.settings.createGroup("Shulkers");
   private final SettingGroup zbN84Osn9p5lD = this.settings.createGroup("Delays");
   private static final MinecraftClient ZlNrrNcv3Ylc = MinecraftClient.getInstance();
   private static int EVSMbf;
   private static int pF16zz4NJECfg7;
   private static int WGmfQKK;
   private static int icQ738dpWr7W;
   private static int JAj4YwE2MVx;
   public static BlockPos fStlZIvY9lS8;
   private static BlockPos JPp6VyZP9f;
   private static int MR1DKzw;
   private static boolean DqGSCWplcjMi;
   private static int cO1FU7fW = 0;
   public static boolean QfZy2gWnKInxFPr;
   public static boolean TJImwK2;
   private static boolean g0iiTDJzfIGXdui;
   private static boolean VIbihOaIVyhvJw;
   private static boolean RE8q1TMDRWx;
   public static boolean wSJeYsJcmjNZf;
   private static int vLRVDxlh = 0;
   private static int HomZ7BbhM7wB = 0;
   private static boolean lVX1t0MC;
   public static int i2Nu6XHKic;
   static int vGAMA89H;
   static int qhulAUwd;
   static int BrmKXSYhWhqoB1;
   static int TXWyNb;
   public static pvfA15fQSVM I3psabmOrOI;
   public final Setting<Integer> duJ6sPeetAcBh6P = this.zXcO5rZ
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("tool-restock-amount"))
                  .description("How many pickaxes should be taken on restock when the player runs out."))
               .defaultValue(1))
            .sliderRange(1, 20)
            .build()
      );
   public final Setting<Integer> j376sIQ = this.zXcO5rZ
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("food-restock-amount"))
                  .description("How many stacks of food should be taken on restock when the player runs out."))
               .defaultValue(1))
            .sliderRange(1, 10)
            .build()
      );
   public final Setting<Integer> V7zKJ4n6D = this.jbUe84V1vykfK
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("material-shulker-restock-amount"))
                  .description("How many shulkers of pavement materials should be taken when performing an enderchest restock."))
               .defaultValue(1))
            .sliderRange(1, 20)
            .build()
      );
   public final Setting<Integer> voxTwgNzyrpaAnP = this.jbUe84V1vykfK
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("tool-shulker-restock-amount"))
                  .description("How many shulkers of pickaxes should be taken when performing an enderchest restock."))
               .defaultValue(1))
            .sliderRange(1, 10)
            .build()
      );
   public final Setting<Integer> xileXsbo = this.zbN84Osn9p5lD
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("stealing-delay"))
                  .description("Ticks to wait between stealing slots from a container. 0 steals every matching slot in a single tick."))
               .defaultValue(1))
            .min(0)
            .sliderRange(0, 20)
            .build()
      );
   public final Setting<Integer> nOwp0NYl = this.zbN84Osn9p5lD
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("delay-after-placing-container"))
                  .description("Time the player should wait before placing a container in ticks"))
               .defaultValue(5))
            .sliderRange(1, 20)
            .build()
      );
   public final Setting<Integer> umvNbb7iD9UpR = this.zbN84Osn9p5lD
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("delay-before-opening-container"))
                  .description("Time the player should wait before opening a container in ticks"))
               .defaultValue(5))
            .sliderRange(1, 20)
            .build()
      );
   public final Setting<Integer> zkcZ4Xkp9Ez7Y = this.zbN84Osn9p5lD
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("delay-after-opening-container"))
                  .description("Time the player should wait after opening a container in ticks"))
               .defaultValue(5))
            .sliderRange(1, 20)
            .build()
      );
   public final Setting<Integer> flWHzEyn = this.zbN84Osn9p5lD
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("delay-after-swapping-item"))
                  .description("Time the player should wait before it attempts to swap items"))
               .defaultValue(5))
            .sliderRange(1, 20)
            .build()
      );
   public final Setting<Integer> FVjbhYkk53UT = this.zbN84Osn9p5lD
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("delay-after-restocking"))
                  .description("Time the player should wait completing the restocking process"))
               .defaultValue(10))
            .sliderRange(1, 20)
            .build()
      );

   public pvfA15fQSVM() {
      super(musheor.musheor.AUTOMATION, "inventory-manager", "Manager module for inventory related stuff");
      I3psabmOrOI = this;
   }

   public static void fStlZIvY9lS8() {
      MR1DKzw = (Integer)I3psabmOrOI.nOwp0NYl.get();
      WGmfQKK = (Integer)I3psabmOrOI.umvNbb7iD9UpR.get();
      pF16zz4NJECfg7 = (Integer)I3psabmOrOI.zkcZ4Xkp9Ez7Y.get();
      JAj4YwE2MVx = (Integer)I3psabmOrOI.flWHzEyn.get();
      icQ738dpWr7W = (Integer)I3psabmOrOI.FVjbhYkk53UT.get();
      BrmKXSYhWhqoB1 = (Integer)I3psabmOrOI.V7zKJ4n6D.get();
      TXWyNb = (Integer)I3psabmOrOI.voxTwgNzyrpaAnP.get();
      vGAMA89H = (Integer)I3psabmOrOI.duJ6sPeetAcBh6P.get();
      qhulAUwd = (Integer)I3psabmOrOI.j376sIQ.get();
   }

   public static void fStlZIvY9lS8(boolean handlePostRestocking) {
      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      fStlZIvY9lS8();
      wSJeYsJcmjNZf = false;
      state.TXWyNb(false);
      state.BrmKXSYhWhqoB1(false);
      RE8q1TMDRWx = false;
      VIbihOaIVyhvJw = false;
      vLRVDxlh = 0;
      cO1FU7fW = 0;
      DqGSCWplcjMi = false;
      HomZ7BbhM7wB = 0;
      fStlZIvY9lS8 = null;
      QfZy2gWnKInxFPr = false;
      state.QfZy2gWnKInxFPr(false);
      state.fStlZIvY9lS8((WOGVSBb)null);
      i2Nu6XHKic = 0;
      state.I3psabmOrOI(false);
      EVSMbf = 0;
      if (handlePostRestocking) {
         wSJeYsJcmjNZf = true;
         state.I3psabmOrOI(true);
      }
   }

   public static void QfZy2gWnKInxFPr() {
      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      if (HighwayBuilder.TJImwK2.fStlZIvY9lS8.get() != HighwayBuilder.Mode.Manual) {
         if (HighwayBuilder.qhulAUwd() == HighwayBuilder.BuildMode.Pave) {
            if (HighwayBuilder.ZlNrrNcv3Ylc() && HighwayBuilder.pF16zz4NJECfg7() && QfZy2gWnKInxFPr(DataComponentTypes.TOOL) == null && TJImwK2(false) == 0) {
               QfZy2gWnKInxFPr = true;
               state.fStlZIvY9lS8(WOGVSBb.PICKAXE_NON_SILK);
               i2Nu6XHKic = TXWyNb;
               state.BrmKXSYhWhqoB1(true);
               MusheorSystem.debug("Tool shulker restocking process started");
               return;
            }

            if (HighwayBuilder.ZlNrrNcv3Ylc()
               && HighwayBuilder.EVSMbf()
               && qhulAUwd(Items.ENDER_CHEST) == null
               && qhulAUwd(HighwayBuilder.V7zKJ4n6D().asItem()) == null) {
               QfZy2gWnKInxFPr = true;
               state.fStlZIvY9lS8(WOGVSBb.PAVEMENT);
               i2Nu6XHKic = BrmKXSYhWhqoB1;
               state.BrmKXSYhWhqoB1(true);
               MusheorSystem.debug("Echest shulker restocking process started");
               return;
            }

            if (HighwayBuilder.jbUe84V1vykfK()
               && i2Nu6XHKic(HighwayBuilder.V7zKJ4n6D().asItem()) <= 8
               && i2Nu6XHKic(Items.ENDER_CHEST) <= 8
               && (qhulAUwd(Items.ENDER_CHEST) != null || qhulAUwd(HighwayBuilder.V7zKJ4n6D().asItem()) != null)) {
               QfZy2gWnKInxFPr = true;
               state.fStlZIvY9lS8(WOGVSBb.PAVEMENT);
               i2Nu6XHKic = duJ6sPeetAcBh6P() / 2;
               state.TXWyNb(true);
               MusheorSystem.debug("Material restocking process started");
               return;
            }

            if (HighwayBuilder.jbUe84V1vykfK() && QfZy2gWnKInxFPr(DataComponentTypes.TOOL) != null && TJImwK2(false) < 1) {
               QfZy2gWnKInxFPr = true;
               state.fStlZIvY9lS8(WOGVSBb.PICKAXE_NON_SILK);
               i2Nu6XHKic = vGAMA89H;
               state.TXWyNb(true);
               MusheorSystem.debug("Pickaxe restocking process started");
               return;
            }

            if (HighwayBuilder.jbUe84V1vykfK() && !HighwayBuilder.QfZy2gWnKInxFPr() && QfZy2gWnKInxFPr(DataComponentTypes.FOOD) != null) {
               QfZy2gWnKInxFPr = true;
               state.fStlZIvY9lS8(WOGVSBb.FOOD);
               i2Nu6XHKic = qhulAUwd;
               state.TXWyNb(true);
               MusheorSystem.debug("Food restocking process started");
               return;
            }
         }

         if (HighwayBuilder.qhulAUwd() == HighwayBuilder.BuildMode.Tunnel) {
            if (HighwayBuilder.ZlNrrNcv3Ylc() && HighwayBuilder.pF16zz4NJECfg7() && QfZy2gWnKInxFPr(DataComponentTypes.TOOL) == null && TJImwK2(true) == 0) {
               QfZy2gWnKInxFPr = true;
               state.fStlZIvY9lS8(WOGVSBb.PICKAXE_SILK);
               i2Nu6XHKic = TXWyNb;
               state.BrmKXSYhWhqoB1(true);
               MusheorSystem.debug("Tool shulker restocking process started");
               return;
            }

            if (HighwayBuilder.jbUe84V1vykfK() && QfZy2gWnKInxFPr(DataComponentTypes.TOOL) != null && TJImwK2(true) == 0) {
               QfZy2gWnKInxFPr = true;
               state.fStlZIvY9lS8(WOGVSBb.PICKAXE_SILK);
               i2Nu6XHKic = TXWyNb;
               state.TXWyNb(true);
               MusheorSystem.debug("Tool restocking process started");
               return;
            }

            if (HighwayBuilder.jbUe84V1vykfK() && !HighwayBuilder.QfZy2gWnKInxFPr()) {
               QfZy2gWnKInxFPr = true;
               state.fStlZIvY9lS8(WOGVSBb.FOOD);
               i2Nu6XHKic = qhulAUwd;
               state.TXWyNb(true);
               MusheorSystem.debug("Food restocking process started");
               return;
            }
         }
      }
   }

   private static boolean TJImwK2(BlockPos pos) {
      Block block = ZlNrrNcv3Ylc.world.getBlockState(pos).getBlock();
      return block instanceof ChestBlock
         || block instanceof ShulkerBoxBlock
         || block instanceof BarrelBlock
         || block instanceof HopperBlock
         || block instanceof DispenserBlock
         || block instanceof DropperBlock;
   }

   public static boolean TJImwK2() {
      if (ZlNrrNcv3Ylc.player == null) {
         return false;
      }

      ScreenHandler handler = ZlNrrNcv3Ylc.player.currentScreenHandler;
      return handler instanceof GenericContainerScreenHandler || handler instanceof ShulkerBoxScreenHandler;
   }

   public static boolean fStlZIvY9lS8(BlockPos pos) {
      if (ZlNrrNcv3Ylc.player == null || ZlNrrNcv3Ylc.world == null) {
         return false;
      }

      if (!TJImwK2(pos)) {
         return false;
      }

      if (TJImwK2()) {
         return true;
      }

      Vec3d hitVec = Vec3d.ofCenter(pos);
      oyDPiOVBGKG.QfZy2gWnKInxFPr(pos);
      BlockHitResult hitResult = new BlockHitResult(hitVec, Direction.UP, pos, false);
      fStlZIvY9lS8(hitResult);
      return false;
   }

   public static void fStlZIvY9lS8(BlockHitResult bhr) {
      if (ZlNrrNcv3Ylc.player != null && ZlNrrNcv3Ylc.world != null && ZlNrrNcv3Ylc.interactionManager != null) {
         ZlNrrNcv3Ylc.interactionManager.sendSequencedPacket(ZlNrrNcv3Ylc.world, sequence -> new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, bhr, sequence));
      }
   }

   public static boolean fStlZIvY9lS8(ItemStack stack) {
      return stack.getMaxDamage() - stack.getDamage() > (Integer)KekMine.duJ6sPeetAcBh6P.QfZy2gWnKInxFPr.get();
   }

   public static void fStlZIvY9lS8(Item item) {
      int slot = 0;

      for (int i = 9; i < ZlNrrNcv3Ylc.player.getInventory().main.size(); i++) {
         ItemStack stack = ZlNrrNcv3Ylc.player.getInventory().getStack(i);
         if (stack.getItem() == item) {
            slot = i;
            break;
         }
      }

      if (slot != 0) {
         InvUtils.move().from(slot).to(wSJeYsJcmjNZf());
      }
   }

   public static int QfZy2gWnKInxFPr(ItemStack stack) {
      int dest = wSJeYsJcmjNZf();
      InvUtils.move().from(ZlNrrNcv3Ylc.player.getInventory().getSlotWithStack(stack)).to(dest);
      return dest;
   }

   public static void fStlZIvY9lS8(int slot) {
      if (ZlNrrNcv3Ylc.player != null && ZlNrrNcv3Ylc.getNetworkHandler() != null) {
         slot = Math.max(0, Math.min(8, slot));
         if (ZlNrrNcv3Ylc.player.getInventory().selectedSlot != slot) {
            ZlNrrNcv3Ylc.player.getInventory().selectedSlot = slot;
            ZlNrrNcv3Ylc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(slot));
         }
      }
   }

   public static int wSJeYsJcmjNZf() {
      if (ZlNrrNcv3Ylc.player == null) {
         return 8;
      }

      for (int i = 0; i <= 8; i++) {
         ItemStack current = ZlNrrNcv3Ylc.player.getInventory().getStack(i);
         if (current.isEmpty()) {
            return i;
         }
      }

      return Utils.random(1, 7);
   }

   public static int i2Nu6XHKic() {
      if (ZlNrrNcv3Ylc.player == null) {
         return 35;
      }

      for (int i = 9; i < 36; i++) {
         ItemStack current = ZlNrrNcv3Ylc.player.getInventory().getStack(i);
         if (current.isEmpty()) {
            return i;
         }
      }

      return Utils.random(9, 35);
   }

   public static boolean fStlZIvY9lS8(int slot, Runnable action) {
      if (ZlNrrNcv3Ylc.player == null || action == null) {
         return false;
      }

      if (slot >= 0 && slot <= 35) {
         boolean swapped = QfZy2gWnKInxFPr(slot);

         try {
            action.run();
            return true;
         } finally {
            if (swapped) {
               QfZy2gWnKInxFPr(slot);
            }
         }
      } else {
         return false;
      }
   }

   public static boolean QfZy2gWnKInxFPr(int slot) {
      int currentSlot = ZlNrrNcv3Ylc.player.getInventory().selectedSlot;
      if (slot == currentSlot) {
         return false;
      }

      int screenSlot = TJImwK2(slot);
      ZlNrrNcv3Ylc.interactionManager
         .clickSlot(ZlNrrNcv3Ylc.player.currentScreenHandler.syncId, screenSlot, currentSlot, SlotActionType.SWAP, ZlNrrNcv3Ylc.player);
      MusheorSystem.debug(
         "Swapped slot %d to selected in screen %s with ID: %d",
         slot,
         ZlNrrNcv3Ylc.player.currentScreenHandler.toString(),
         ZlNrrNcv3Ylc.player.currentScreenHandler.syncId
      );
      return true;
   }

   public static int TJImwK2(int slot) {
      return slot >= 0 && slot <= 8 ? 36 + slot : slot;
   }

   public static int QfZy2gWnKInxFPr(Item item) {
      for (int i = 0; i < 9; i++) {
         ItemStack stack = ZlNrrNcv3Ylc.player.getInventory().getStack(i);
         if (stack.getItem() == item) {
            return i;
         }
      }

      return -1;
   }

   public static int TJImwK2(Item item) {
      for (int i = 0; i < Objects.requireNonNull(ZlNrrNcv3Ylc.player).getInventory().main.size(); i++) {
         ItemStack stack = ZlNrrNcv3Ylc.player.getInventory().getStack(i);
         if (stack.getItem() == item) {
            return i;
         }
      }

      return -1;
   }

   public static int vGAMA89H() {
      for (int i = 0; i < Objects.requireNonNull(ZlNrrNcv3Ylc.player).getInventory().main.size(); i++) {
         ItemStack stack = ZlNrrNcv3Ylc.player.getInventory().getStack(i);
         if (stack.getItem() == Items.ELYTRA && stack.getMaxDamage() - stack.getDamage() > (Integer)ElytraTweakz.QfZy2gWnKInxFPr.fStlZIvY9lS8.get()) {
            return i;
         }
      }

      return -1;
   }

   public static int qhulAUwd() {
      if (ZlNrrNcv3Ylc.player == null) {
         return -1;
      }

      PlayerInventory inventory = ZlNrrNcv3Ylc.player.getInventory();

      for (int i = 0; i < inventory.main.size(); i++) {
         ItemStack stack = inventory.getStack(i);
         EquippableComponent equippable = (EquippableComponent)stack.get(DataComponentTypes.EQUIPPABLE);
         if (equippable != null && equippable.slot() == EquipmentSlot.CHEST && !stack.isOf(Items.ELYTRA)) {
            return i;
         }
      }

      return -1;
   }

   public static void fStlZIvY9lS8(int slot, boolean entireStack) {
      if (ZlNrrNcv3Ylc.player != null && ZlNrrNcv3Ylc.getNetworkHandler() != null) {
         ZlNrrNcv3Ylc.interactionManager
            .clickSlot(ZlNrrNcv3Ylc.player.currentScreenHandler.syncId, TJImwK2(slot), entireStack ? 1 : 0, SlotActionType.THROW, ZlNrrNcv3Ylc.player);
      }
   }

   public static void BrmKXSYhWhqoB1() {
      ItemStack currentItem = ZlNrrNcv3Ylc.player.getEquippedStack(EquipmentSlot.CHEST);
      if (currentItem.contains(DataComponentTypes.GLIDER)) {
         int chest = qhulAUwd();
         InvUtils.move().from(chest).toArmor(2);
      } else if (currentItem.contains(DataComponentTypes.EQUIPPABLE)
         && ((EquippableComponent)currentItem.get(DataComponentTypes.EQUIPPABLE)).slot().getEntitySlotId() == EquipmentSlot.CHEST.getEntitySlotId()) {
         int elytra = vGAMA89H();
         InvUtils.move().from(elytra).toArmor(2);
      } else {
         int elytra = vGAMA89H();
         InvUtils.move().from(elytra).toArmor(2);
      }
   }

   public static ItemStack wSJeYsJcmjNZf(Item item) {
      for (int i = 0; i < Objects.requireNonNull(ZlNrrNcv3Ylc.player).getInventory().main.size(); i++) {
         ItemStack stack = ZlNrrNcv3Ylc.player.getInventory().getStack(i);
         if (stack.getItem() == item) {
            return stack;
         }
      }

      return null;
   }

   public static int fStlZIvY9lS8(ItemStack shulker, Item item) {
      ItemStack[] containerItems = new ItemStack[27];
      Utils.getItemsInContainerItem(shulker, containerItems);
      int count = 0;

      for (ItemStack stack : containerItems) {
         if (!stack.isEmpty() && stack.getItem() == item) {
            count += stack.getCount();
         }
      }

      return count;
   }

   public static int fStlZIvY9lS8(ItemStack shulker, ComponentType<?> component) {
      ItemStack[] containerItems = new ItemStack[27];
      Utils.getItemsInContainerItem(shulker, containerItems);
      int count = 0;

      for (ItemStack stack : containerItems) {
         if (!stack.isEmpty() && stack.getComponents().contains(component)) {
            count += stack.getCount();
         }
      }

      return count;
   }

   public static int i2Nu6XHKic(Item item) {
      int count = 0;

      for (int i = 0; i < ZlNrrNcv3Ylc.player.getInventory().main.size(); i++) {
         ItemStack stack = ZlNrrNcv3Ylc.player.getInventory().getStack(i);
         if (!stack.isEmpty() && stack.getItem() == item) {
            count += stack.getCount();
         }
      }

      return count;
   }

   public static int vGAMA89H(Item item) {
      int count = 0;

      for (int i = 0; i < ZlNrrNcv3Ylc.player.getInventory().main.size(); i++) {
         ItemStack itemStack = ZlNrrNcv3Ylc.player.getInventory().getStack(i);
         if (!itemStack.isEmpty()) {
            if (itemStack.getItem() instanceof BlockItem && ((BlockItem)itemStack.getItem()).getBlock() instanceof ShulkerBoxBlock) {
               count += fStlZIvY9lS8(itemStack, item);
            }

            if (itemStack.getItem() == item) {
               count += itemStack.getCount();
            }
         }
      }

      return count;
   }

   public static ItemStack qhulAUwd(Item item) {
      for (int i = 0; i < Objects.requireNonNull(ZlNrrNcv3Ylc.player).getInventory().main.size(); i++) {
         ItemStack stack = ZlNrrNcv3Ylc.player.getInventory().getStack(i);
         if (stack.getItem() instanceof BlockItem && ((BlockItem)stack.getItem()).getBlock() instanceof ShulkerBoxBlock && QfZy2gWnKInxFPr(stack, item)) {
            return stack;
         }
      }

      return null;
   }

   public static ItemStack BrmKXSYhWhqoB1(Item item) {
      int count = 0;
      int least = 1729;
      ItemStack stack = null;

      for (int i = 0; i < Objects.requireNonNull(ZlNrrNcv3Ylc.player).getInventory().size(); i++) {
         ItemStack itemStack = ZlNrrNcv3Ylc.player.getInventory().getStack(i);
         if (itemStack.getItem() instanceof BlockItem && ((BlockItem)itemStack.getItem()).getBlock() instanceof ShulkerBoxBlock) {
            count = fStlZIvY9lS8(itemStack, item);
            if (least > count && count > 0) {
               least = count;
               stack = itemStack;
            }
         }
      }

      return stack;
   }

   public static ItemStack fStlZIvY9lS8(ComponentType<?> component) {
      int count = 0;
      int least = 99;
      ItemStack stack = null;

      for (int i = 0; i < Objects.requireNonNull(ZlNrrNcv3Ylc.player).getInventory().size(); i++) {
         ItemStack itemStack = ZlNrrNcv3Ylc.player.getInventory().getStack(i);
         if (itemStack.getItem() instanceof BlockItem && ((BlockItem)itemStack.getItem()).getBlock() instanceof ShulkerBoxBlock) {
            count = fStlZIvY9lS8(itemStack, component);
            if (least > count && count > 0) {
               least = count;
               stack = itemStack;
            }
         }
      }

      return stack;
   }

   public static ItemStack QfZy2gWnKInxFPr(ComponentType<?> component) {
      for (int i = 0; i < Objects.requireNonNull(ZlNrrNcv3Ylc.player).getInventory().main.size(); i++) {
         ItemStack stack = ZlNrrNcv3Ylc.player.getInventory().getStack(i);
         if (stack.getItem() instanceof BlockItem && ((BlockItem)stack.getItem()).getBlock() instanceof ShulkerBoxBlock && QfZy2gWnKInxFPr(stack, component)) {
            return stack;
         }
      }

      return null;
   }

   public static boolean QfZy2gWnKInxFPr(ItemStack shulker, ComponentType<?> component) {
      ItemStack[] containerItems = new ItemStack[27];
      Utils.getItemsInContainerItem(shulker, containerItems);

      for (ItemStack stack : containerItems) {
         if (!stack.isEmpty() && stack.getComponents().contains(component)) {
            if (component != DataComponentTypes.TOOL) {
               return true;
            }

            if (stack.getMaxDamage() - stack.getDamage() > 50) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean QfZy2gWnKInxFPr(ItemStack shulker, Item targetItem) {
      ItemStack[] containerItems = new ItemStack[27];
      Utils.getItemsInContainerItem(shulker, containerItems);

      for (ItemStack stack : containerItems) {
         if (!stack.isEmpty() && stack.getItem() == targetItem) {
            if (!VersionHelper.get().isPickaxe(stack)) {
               return true;
            }

            if (stack.getMaxDamage() - stack.getDamage() > 50) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean TJImwK2(ItemStack shulker) {
      ComponentMap components = shulker.getComponents();
      if (!components.contains(DataComponentTypes.CONTAINER)) {
         return false;
      }

      ContainerComponentAccessor container = (ContainerComponentAccessor)(Object)components.get(DataComponentTypes.CONTAINER);
      if (container != null) {
         for (ItemStack stack : VersionHelper.get().getStacks(container)) {
            if (!stack.isEmpty()) {
               return false;
            }
         }
      }

      return true;
   }

   public static void TXWyNb(Item item) {
      fStlZIvY9lS8(item, false);
   }

   public static void fStlZIvY9lS8(Item item, boolean silent) {
      assert ZlNrrNcv3Ylc.player != null;
      FindItemResult result = InvUtils.findInHotbar(itemStack -> itemStack.getItem() == item);
      if (result.found()) {
         InvUtils.swap(result.slot(), silent);
      } else {
         fStlZIvY9lS8(item);
      }

      MusheorSystem.debug("Swapping to %s", Registries.ITEM.getId(item.asItem()).toString());
   }

   public static boolean I3psabmOrOI(Item item) {
      assert ZlNrrNcv3Ylc.player != null;
      if (ZlNrrNcv3Ylc.player.getMainHandStack().getItem() == item) {
         return true;
      }

      boolean silent = (Boolean)MusheorSystem.Manager.silentPlace.get();
      fStlZIvY9lS8(item, silent);
      return silent && ZlNrrNcv3Ylc.player.getMainHandStack().getItem() == item;
   }

   public static void QfZy2gWnKInxFPr(boolean silkTouch) {
      assert ZlNrrNcv3Ylc.player != null;
      double bestScore = -1.0;
      int bestSlot = -1;

      for (int i = 0; i < 9; i++) {
         ItemStack itemStack = ZlNrrNcv3Ylc.player.getInventory().getStack(i);
         if (!Utils.hasEnchantments(itemStack, new RegistryKey[]{Enchantments.SILK_TOUCH}) || !silkTouch) {
            double score = itemStack.getMiningSpeedMultiplier(Blocks.ENDER_CHEST.getDefaultState());
            if (score > bestScore) {
               bestScore = score;
               bestSlot = i;
            }
         }
      }

      if (bestSlot != -1) {
         InvUtils.swap(bestSlot, false);
      }
   }

   public static ItemStack fStlZIvY9lS8(BlockState state) {
      if (ZlNrrNcv3Ylc.player == null) {
         return ItemStack.EMPTY;
      }

      ItemStack bestStack = ZlNrrNcv3Ylc.player.getMainHandStack();
      float bestSpeed = 1.0F;

      for (int i = 0; i < ZlNrrNcv3Ylc.player.getInventory().size(); i++) {
         ItemStack stack = ZlNrrNcv3Ylc.player.getInventory().getStack(i);
         if (!(Boolean)KekMine.duJ6sPeetAcBh6P.fStlZIvY9lS8.get() || fStlZIvY9lS8(stack)) {
            float speed = stack.getMiningSpeedMultiplier(state);
            if (speed > bestSpeed) {
               bestSpeed = speed;
               bestStack = stack;
            }
         }
      }

      return bestStack;
   }

   public static void QfZy2gWnKInxFPr(BlockPos blockPos) {
      assert ZlNrrNcv3Ylc.world != null && ZlNrrNcv3Ylc.player != null && blockPos != null;
      BlockState state = ZlNrrNcv3Ylc.world.getBlockState(blockPos);
      float bestSpeed = 1.0F;
      int bestSlot = -1;

      for (int i = 0; i < 9; i++) {
         ItemStack itemStack = ZlNrrNcv3Ylc.player.getInventory().getStack(i);
         if (!itemStack.isEmpty() && (!(Boolean)KekMine.duJ6sPeetAcBh6P.fStlZIvY9lS8.get() || fStlZIvY9lS8(itemStack))) {
            float speed = itemStack.getMiningSpeedMultiplier(state);
            if (speed > bestSpeed && itemStack.isSuitableFor(state)) {
               bestSpeed = speed;
               bestSlot = i;
            }
         }
      }

      if (bestSlot != -1) {
         ZlNrrNcv3Ylc.player.getInventory().selectedSlot = bestSlot;
      } else if (VersionHelper.get().isTool(ZlNrrNcv3Ylc.player.getMainHandStack()) && !fStlZIvY9lS8(ZlNrrNcv3Ylc.player.getMainHandStack())) {
         InvUtils.move().from(ZlNrrNcv3Ylc.player.getInventory().selectedSlot).to(i2Nu6XHKic());
      } else {
         for (int i = 9; i < ZlNrrNcv3Ylc.player.getInventory().main.size(); i++) {
            ItemStack itemStack = ZlNrrNcv3Ylc.player.getInventory().getStack(i);
            if (!itemStack.isEmpty() && (!(Boolean)KekMine.duJ6sPeetAcBh6P.fStlZIvY9lS8.get() || fStlZIvY9lS8(itemStack))) {
               float speed = itemStack.getMiningSpeedMultiplier(state);
               if (speed > bestSpeed && itemStack.isSuitableFor(state)) {
                  bestSpeed = speed;
                  bestSlot = i;
               }
            }
         }

         if (bestSlot != -1) {
            int hotbarSlot = QfZy2gWnKInxFPr(ZlNrrNcv3Ylc.player.getInventory().getStack(bestSlot));
            ZlNrrNcv3Ylc.player.getInventory().selectedSlot = Math.max(0, Math.min(8, hotbarSlot));
         }
      }
   }

   public static int TJImwK2(boolean allowSilkTouch) {
      assert ZlNrrNcv3Ylc.player != null;
      int count = 0;

      for (int i = 0; i < ZlNrrNcv3Ylc.player.getInventory().size(); i++) {
         ItemStack itemStack = ZlNrrNcv3Ylc.player.getInventory().getStack(i);
         if (VersionHelper.get().isPickaxe(itemStack)) {
            if ((Boolean)KekMine.duJ6sPeetAcBh6P.fStlZIvY9lS8.get()) {
               if (!allowSilkTouch
                  && !Utils.hasEnchantments(itemStack, new RegistryKey[]{Enchantments.SILK_TOUCH})
                  && itemStack.getMaxDamage() - itemStack.getDamage() > (Integer)KekMine.duJ6sPeetAcBh6P.QfZy2gWnKInxFPr.get()) {
                  count++;
               }

               if (allowSilkTouch && itemStack.getMaxDamage() - itemStack.getDamage() > (Integer)KekMine.duJ6sPeetAcBh6P.QfZy2gWnKInxFPr.get()) {
                  count++;
               }
            } else if (!allowSilkTouch && !Utils.hasEnchantments(itemStack, new RegistryKey[]{Enchantments.SILK_TOUCH})) {
               count++;
            } else if (allowSilkTouch) {
               count++;
            }
         }
      }

      return count;
   }

   public static int TXWyNb() {
      assert ZlNrrNcv3Ylc.player != null;
      int count = 0;

      for (int i = 0; i < ZlNrrNcv3Ylc.player.getInventory().main.size(); i++) {
         ItemStack itemStack = ZlNrrNcv3Ylc.player.getInventory().getStack(i);
         if (VersionHelper.get().isPickaxe(itemStack)
            && itemStack.getMaxDamage() - itemStack.getDamage() <= (Integer)KekMine.duJ6sPeetAcBh6P.QfZy2gWnKInxFPr.get()) {
            count++;
         }
      }

      return count;
   }

   public static int I3psabmOrOI() {
      assert ZlNrrNcv3Ylc.player != null;

      for (int i = 0; i < ZlNrrNcv3Ylc.player.currentScreenHandler.slots.size(); i++) {
         ItemStack itemStack = ZlNrrNcv3Ylc.player.currentScreenHandler.getSlot(i).getStack();
         if (VersionHelper.get().isPickaxe(itemStack)
            && itemStack.getMaxDamage() - itemStack.getDamage() <= (Integer)KekMine.duJ6sPeetAcBh6P.QfZy2gWnKInxFPr.get()
            && i > 26) {
            return i;
         }
      }

      return -1;
   }

   public static int duJ6sPeetAcBh6P() {
      int emptyCount = 0;
      assert ZlNrrNcv3Ylc.player != null;

      for (ItemStack itemStack : ZlNrrNcv3Ylc.player.getInventory().main) {
         if (itemStack.isEmpty()) {
            emptyCount++;
         }
      }

      return emptyCount;
   }

   public static int fStlZIvY9lS8(ScreenHandler handler) {
      int emptyCount = 0;
      int playerInvOffset = SlotUtils.indexToId(9);

      for (int i = 0; i < playerInvOffset; i++) {
         if (handler.getSlot(i).getStack().isEmpty()) {
            emptyCount++;
         }
      }

      return emptyCount;
   }

   public static boolean fStlZIvY9lS8(Item itemInShulker, int hotbarSlot) {
      ItemStack stack = BrmKXSYhWhqoB1(itemInShulker);
      if (stack == null) {
         MusheorSystem.debug("No shulker with " + itemInShulker.toString() + " found in the inventory");
         return false;
      }

      int slot = ZlNrrNcv3Ylc.player.getInventory().getSlotWithStack(stack);
      if (slot == hotbarSlot) {
         return true;
      }

      if (slot == -1) {
         return false;
      }

      InvUtils.move().from(slot).to(hotbarSlot);
      MusheorSystem.debug("Moving shulker with " + itemInShulker.toString() + " to slot " + hotbarSlot);
      return wSJeYsJcmjNZf(ZlNrrNcv3Ylc.player.getInventory().getStack(hotbarSlot));
   }

   public static boolean fStlZIvY9lS8(ComponentType<?> type, int hotbarSlot) {
      ItemStack stack = fStlZIvY9lS8(type);
      if (stack == null) {
         MusheorSystem.debug("No shulker with " + type.toString() + " found in the inventory");
         return false;
      }

      int slot = ZlNrrNcv3Ylc.player.getInventory().getSlotWithStack(stack);
      if (slot == hotbarSlot) {
         return true;
      }

      if (slot == -1) {
         return false;
      }

      InvUtils.move().from(slot).to(hotbarSlot);
      MusheorSystem.debug("Moving shulker with " + type.toString() + " to slot " + hotbarSlot);
      return wSJeYsJcmjNZf(ZlNrrNcv3Ylc.player.getInventory().getStack(hotbarSlot));
   }

   public static Item fStlZIvY9lS8(double radius) {
      if (ZlNrrNcv3Ylc.world != null && ZlNrrNcv3Ylc.player != null) {
         Box area = ZlNrrNcv3Ylc.player.getBoundingBox().expand(radius);

         for (ItemEntity itemEntity : ZlNrrNcv3Ylc.world.getEntitiesByClass(ItemEntity.class, area, e -> true)) {
            ItemStack stack = itemEntity.getStack();
            if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof ShulkerBoxBlock) {
               return itemEntity.getStack().getItem();
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public static void fStlZIvY9lS8(WOGVSBb type, int stacks) {
      if (ZlNrrNcv3Ylc.world != null && ZlNrrNcv3Ylc.player != null && type != null) {
         obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
         if (!irzXX.qhulAUwd() && !state.Nt3xeF()) {
            irzXX.fStlZIvY9lS8(false);
            Module nuker = Modules.get().get(KekNuker.class);
            if (nuker.isActive()) {
               nuker.toggle();
            }

            if (EVSMbf > 0) {
               EVSMbf--;
            } else {
               if (!DqGSCWplcjMi) {
                  fStlZIvY9lS8(8);
                  VersionHelper.get().syncInventory();
                  DqGSCWplcjMi = true;
                  MusheorSystem.debug("Restocking has been initialized, proceeding...");
               }

               if (ZlNrrNcv3Ylc.player.getBlockPos().getSquaredDistance(fStlZIvY9lS8) > 1.5) {
                  BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(new GoalBlock(fStlZIvY9lS8));
                  MusheorSystem.debug("Player is not at restocking position, calling baritone...");
               } else if (!RE8q1TMDRWx) {
                  boolean moved = false;
                  switch (type) {
                     case PICKAXE_SILK:
                     case PICKAXE_NON_SILK:
                        moved = fStlZIvY9lS8(DataComponentTypes.TOOL, 8);
                        break;
                     case FOOD:
                        moved = fStlZIvY9lS8(DataComponentTypes.FOOD, 8);
                        break;
                     case PAVEMENT:
                        Item item = null;
                        if (qhulAUwd(HighwayBuilder.V7zKJ4n6D().asItem()) != null) {
                           item = HighwayBuilder.V7zKJ4n6D().asItem();
                        } else if (HighwayBuilder.V7zKJ4n6D() == Blocks.OBSIDIAN && qhulAUwd(Items.ENDER_CHEST) != null) {
                           item = Items.ENDER_CHEST;
                        }

                        if (item != null) {
                           moved = fStlZIvY9lS8(item, 8);
                        }
                  }

                  if (moved) {
                     RE8q1TMDRWx = true;
                  }

                  EVSMbf = JAj4YwE2MVx;
               } else {
                  if (!VIbihOaIVyhvJw) {
                     JPp6VyZP9f = oyDPiOVBGKG.fStlZIvY9lS8(HighwayBuilder.i2Nu6XHKic());
                     if (MR1DKzw > 0) {
                        irzXX.fStlZIvY9lS8(ZlNrrNcv3Ylc.options.sneakKey, false);
                        MR1DKzw--;
                        oyDPiOVBGKG.TJImwK2(JPp6VyZP9f, Direction.DOWN);
                        return;
                     }

                     if (ZlNrrNcv3Ylc.world.getBlockState(JPp6VyZP9f).getBlock() instanceof ShulkerBoxBlock) {
                        MusheorSystem.debug("Shulker box placed successfully!");
                        VIbihOaIVyhvJw = true;
                        EVSMbf = MR1DKzw;
                     } else {
                        if (BlockUtils.canPlace(JPp6VyZP9f, true) || ZlNrrNcv3Ylc.world.getBlockState(JPp6VyZP9f).isReplaceable()) {
                           if (!wSJeYsJcmjNZf(ZlNrrNcv3Ylc.player.getInventory().getStack(8))) {
                              MusheorSystem.debug("Slot 8 is not holding a shulker, redoing the swap");
                              RE8q1TMDRWx = false;
                              EVSMbf = JAj4YwE2MVx;
                              return;
                           } else {
                              MusheorSystem.debug("Placing shulkerbox at x: %s y: %s z: %s", JPp6VyZP9f.getX(), JPp6VyZP9f.getY(), JPp6VyZP9f.getZ());
                              fStlZIvY9lS8(8);
                              obf.irzXX.fStlZIvY9lS8(JPp6VyZP9f, Direction.UP, state.QfZy2gWnKInxFPr(), (Boolean)MusheorSystem.Manager.silentPlace.get());
                              return;
                           }
                        }

                        if (KekMine.duJ6sPeetAcBh6P.isActive()) {
                           KekMine.duJ6sPeetAcBh6P.fStlZIvY9lS8(JPp6VyZP9f, ZlNrrNcv3Ylc.world.getBlockState(JPp6VyZP9f), false);
                           MusheorSystem.debug("Breaking obstructing block at restocking position");
                           return;
                        }

                        BlockUtils.breakBlock(JPp6VyZP9f, true);
                        MusheorSystem.debug("Breaking obstructing block at restocking position");
                     }
                  }

                  if (!(ZlNrrNcv3Ylc.player.currentScreenHandler instanceof ShulkerBoxScreenHandler)) {
                     oyDPiOVBGKG.QfZy2gWnKInxFPr(JPp6VyZP9f);
                     ZlNrrNcv3Ylc.interactionManager
                        .sendSequencedPacket(
                           ZlNrrNcv3Ylc.world,
                           sequence -> new PlayerInteractBlockC2SPacket(
                              Hand.MAIN_HAND, new BlockHitResult(Vec3d.ofCenter(JPp6VyZP9f), Direction.UP, JPp6VyZP9f, false), sequence
                           )
                        );
                     MusheorSystem.debug("Opening shulkerbox at x: %s y: %s z: %s", JPp6VyZP9f.getX(), JPp6VyZP9f.getY(), JPp6VyZP9f.getZ());
                     EVSMbf = WGmfQKK;
                  } else if (pF16zz4NJECfg7 > 0) {
                     pF16zz4NJECfg7--;
                  } else {
                     ScreenHandler handler = ZlNrrNcv3Ylc.player.currentScreenHandler;
                     int containerSlots = QfZy2gWnKInxFPr(handler);
                     if (vLRVDxlh < stacks && cO1FU7fW < containerSlots) {
                        int stealDelay = (Integer)I3psabmOrOI.xileXsbo.get();
                        if (stealDelay <= 0) {
                           for (; cO1FU7fW < containerSlots && vLRVDxlh < stacks; cO1FU7fW++) {
                              if (fStlZIvY9lS8(type, handler, cO1FU7fW)) {
                                 vLRVDxlh++;
                              }
                           }
                        } else {
                           if (lVX1t0MC) {
                              if (HomZ7BbhM7wB < stealDelay) {
                                 HomZ7BbhM7wB++;
                                 return;
                              }

                              lVX1t0MC = false;
                           }

                           if (fStlZIvY9lS8(type, handler, cO1FU7fW)) {
                              vLRVDxlh++;
                              lVX1t0MC = true;
                              HomZ7BbhM7wB = 0;
                           }

                           cO1FU7fW++;
                        }
                     } else {
                        MusheorSystem.debug("Completed item restocking process");
                        fStlZIvY9lS8(true);
                     }
                  }
               }
            }
         } else {
            QfZy2gWnKInxFPr = false;
         }
      }
   }

   private static int QfZy2gWnKInxFPr(ScreenHandler handler) {
      return Math.max(0, handler.slots.size() - ZlNrrNcv3Ylc.player.getInventory().main.size());
   }

   private static boolean fStlZIvY9lS8(WOGVSBb type, ScreenHandler handler, int slot) {
      ItemStack stack = handler.getSlot(slot).getStack();
      if (!fStlZIvY9lS8(type, stack)) {
         return false;
      }

      String stolen = stack.getItem().getName().getString();
      InvUtils.shiftClick().slotId(slot);
      if ((type == WOGVSBb.PICKAXE_NON_SILK || type == WOGVSBb.PICKAXE_SILK) && HighwayBuilder.zbN84Osn9p5lD()) {
         int brokenPickaxeSlot = I3psabmOrOI();
         if (brokenPickaxeSlot != -1) {
            InvUtils.shiftClick().slotId(brokenPickaxeSlot);
         }
      }

      MusheorSystem.debug("Stealing {%s} from slot [%s]", stolen, slot);
      return true;
   }

   private static boolean fStlZIvY9lS8(WOGVSBb type, ItemStack stack) {
      if (stack.isEmpty()) {
         return false;
      }

      return switch (type) {
         case PICKAXE_SILK -> VersionHelper.get().isPickaxe(stack)
            && Utils.hasEnchantments(stack, new RegistryKey[]{Enchantments.SILK_TOUCH})
            && fStlZIvY9lS8(stack);
         case PICKAXE_NON_SILK -> VersionHelper.get().isPickaxe(stack)
            && !Utils.hasEnchantments(stack, new RegistryKey[]{Enchantments.SILK_TOUCH})
            && fStlZIvY9lS8(stack);
         case FOOD -> stack.getComponents().contains(DataComponentTypes.FOOD)
            && !((List)((AutoEat)Modules.get().get(AutoEat.class)).blacklist.get()).contains(stack.getItem());
         case PAVEMENT -> stack.getItem() == HighwayBuilder.V7zKJ4n6D().asItem()
            || HighwayBuilder.V7zKJ4n6D() == Blocks.OBSIDIAN && stack.getItem() == Items.ENDER_CHEST;
      };
   }

   public static void QfZy2gWnKInxFPr(WOGVSBb type, int shulkers) {
      if (ZlNrrNcv3Ylc.world != null && ZlNrrNcv3Ylc.player != null && type != null) {
         obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
         if (!irzXX.qhulAUwd() && !state.Nt3xeF()) {
            irzXX.fStlZIvY9lS8(false);
            Module nuker = Modules.get().get(KekNuker.class);
            if (nuker.isActive()) {
               nuker.toggle();
            }

            if (EVSMbf > 0) {
               EVSMbf--;
            } else {
               if (!DqGSCWplcjMi) {
                  fStlZIvY9lS8(TJImwK2(Items.ENDER_CHEST));
                  VersionHelper.get().syncInventory();
                  DqGSCWplcjMi = true;
                  MusheorSystem.debug("Echest shulker restocking has been initialized, proceeding...");
               }

               if (ZlNrrNcv3Ylc.player.getBlockPos().getSquaredDistance(fStlZIvY9lS8) > 1.5) {
                  BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(new GoalBlock(fStlZIvY9lS8));
                  MusheorSystem.debug("Player is not at restocking position, calling baritone...");
               } else if (!RE8q1TMDRWx) {
                  int echestSlot = TJImwK2(Items.ENDER_CHEST);
                  if (echestSlot == -1) {
                     MusheorSystem.debug("No enderchest in inventory to place for echest restock — aborting.");
                     fStlZIvY9lS8(false);
                  } else {
                     fStlZIvY9lS8(echestSlot);
                     RE8q1TMDRWx = true;
                     EVSMbf = JAj4YwE2MVx;
                  }
               } else {
                  if (!VIbihOaIVyhvJw) {
                     JPp6VyZP9f = oyDPiOVBGKG.fStlZIvY9lS8(HighwayBuilder.i2Nu6XHKic());
                     if (MR1DKzw > 0) {
                        irzXX.fStlZIvY9lS8(ZlNrrNcv3Ylc.options.sneakKey, false);
                        MR1DKzw--;
                        oyDPiOVBGKG.TJImwK2(JPp6VyZP9f, Direction.DOWN);
                        return;
                     }

                     if (ZlNrrNcv3Ylc.world.getBlockState(JPp6VyZP9f).getBlock() instanceof EnderChestBlock) {
                        MusheorSystem.debug("Enderchest placed successfully!");
                        VIbihOaIVyhvJw = true;
                        EVSMbf = MR1DKzw;
                     } else {
                        if (state.PVlYDeNipUe().containsKey(JPp6VyZP9f)) {
                           return;
                        }

                        if (BlockUtils.canPlace(JPp6VyZP9f, true) || ZlNrrNcv3Ylc.world.getBlockState(JPp6VyZP9f).isReplaceable()) {
                           MusheorSystem.debug("Placing enderchest at x: %s y: %s z: %s", JPp6VyZP9f.getX(), JPp6VyZP9f.getY(), JPp6VyZP9f.getZ());
                           fStlZIvY9lS8(Items.ENDER_CHEST, (Boolean)MusheorSystem.Manager.silentPlace.get());
                           obf.irzXX.fStlZIvY9lS8(JPp6VyZP9f, Direction.DOWN, state.QfZy2gWnKInxFPr(), (Boolean)MusheorSystem.Manager.silentPlace.get());
                           state.PVlYDeNipUe().put(JPp6VyZP9f, state.pF16zz4NJECfg7());
                           return;
                        }

                        if (KekMine.duJ6sPeetAcBh6P.isActive()) {
                           KekMine.duJ6sPeetAcBh6P.fStlZIvY9lS8(JPp6VyZP9f, ZlNrrNcv3Ylc.world.getBlockState(JPp6VyZP9f), false);
                           MusheorSystem.debug("Breaking obstructing block at restocking position");
                           return;
                        }

                        BlockUtils.breakBlock(JPp6VyZP9f, true);
                        MusheorSystem.debug("Breaking obstructing block at restocking position");
                     }
                  }

                  if (!(ZlNrrNcv3Ylc.player.currentScreenHandler instanceof GenericContainerScreenHandler)) {
                     oyDPiOVBGKG.QfZy2gWnKInxFPr(JPp6VyZP9f);
                     ZlNrrNcv3Ylc.interactionManager
                        .sendSequencedPacket(
                           ZlNrrNcv3Ylc.world,
                           sequence -> new PlayerInteractBlockC2SPacket(
                              Hand.MAIN_HAND, new BlockHitResult(Vec3d.ofCenter(JPp6VyZP9f), Direction.UP, JPp6VyZP9f, false), sequence
                           )
                        );
                     MusheorSystem.debug("Opening enderchest at x: %s y: %s z: %s", JPp6VyZP9f.getX(), JPp6VyZP9f.getY(), JPp6VyZP9f.getZ());
                     EVSMbf = WGmfQKK;
                  } else if (pF16zz4NJECfg7 > 0) {
                     pF16zz4NJECfg7--;
                  } else {
                     ScreenHandler handler = ZlNrrNcv3Ylc.player.currentScreenHandler;
                     int containerSlots = QfZy2gWnKInxFPr(handler);
                     if (vLRVDxlh < shulkers && cO1FU7fW < containerSlots) {
                        int stealDelay = (Integer)I3psabmOrOI.xileXsbo.get();
                        if (stealDelay <= 0) {
                           for (; cO1FU7fW < containerSlots && vLRVDxlh < shulkers; cO1FU7fW++) {
                              if (QfZy2gWnKInxFPr(type, handler, cO1FU7fW)) {
                                 vLRVDxlh++;
                              }
                           }
                        } else {
                           if (lVX1t0MC) {
                              if (HomZ7BbhM7wB < stealDelay) {
                                 HomZ7BbhM7wB++;
                                 return;
                              }

                              lVX1t0MC = false;
                           }

                           if (QfZy2gWnKInxFPr(type, handler, cO1FU7fW)) {
                              vLRVDxlh++;
                              lVX1t0MC = true;
                              HomZ7BbhM7wB = 0;
                           }

                           cO1FU7fW++;
                        }
                     } else {
                        MusheorSystem.debug("Completed echest shulker restocking process");
                        fStlZIvY9lS8(true);
                     }
                  }
               }
            }
         } else {
            QfZy2gWnKInxFPr = false;
         }
      }
   }

   private static boolean wSJeYsJcmjNZf(ItemStack stack) {
      return stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof ShulkerBoxBlock;
   }

   private static boolean QfZy2gWnKInxFPr(WOGVSBb type, ItemStack shulker) {
      if (!wSJeYsJcmjNZf(shulker)) {
         return false;
      }

      ItemStack[] contents = new ItemStack[27];
      Utils.getItemsInContainerItem(shulker, contents);

      for (ItemStack stack : contents) {
         if (stack != null && fStlZIvY9lS8(type, stack)) {
            return true;
         }
      }

      return false;
   }

   private static boolean QfZy2gWnKInxFPr(WOGVSBb type, ScreenHandler handler, int slot) {
      if (!obf.gVOhtBej1x.fStlZIvY9lS8(slot)) {
         return false;
      }

      ItemStack stack = handler.getSlot(slot).getStack();
      if (!QfZy2gWnKInxFPr(type, stack)) {
         return false;
      }

      String stolen = stack.getItem().getName().getString();
      InvUtils.shiftClick().slotId(slot);
      MusheorSystem.debug("Grabbing shulker {%s} from echest slot [%s]", stolen, slot);
      return true;
   }

   public static boolean j376sIQ() {
      if (ZlNrrNcv3Ylc.player == null) {
         return false;
      }

      BlockState echest = Blocks.ENDER_CHEST.getDefaultState();
      int bestSlot = -1;
      float bestSpeed = -1.0F;

      for (int i = 0; i < ZlNrrNcv3Ylc.player.getInventory().main.size(); i++) {
         ItemStack stack = ZlNrrNcv3Ylc.player.getInventory().getStack(i);
         if (VersionHelper.get().isPickaxe(stack)) {
            float speed = stack.getMiningSpeedMultiplier(echest);
            if (speed > bestSpeed) {
               bestSpeed = speed;
               bestSlot = i;
            }
         }
      }

      if (bestSlot == -1) {
         return false;
      }

      if (bestSlot < 9) {
         InvUtils.swap(bestSlot, false);
      } else {
         int hotbarSlot = QfZy2gWnKInxFPr(ZlNrrNcv3Ylc.player.getInventory().getStack(bestSlot));
         fStlZIvY9lS8(hotbarSlot);
      }

      return true;
   }

   public static void V7zKJ4n6D() {
      if (ZlNrrNcv3Ylc.player != null && ZlNrrNcv3Ylc.world != null && JPp6VyZP9f != null) {
         if (!TJImwK2 && !HighwayBuilder.vGAMA89H() && !QfZy2gWnKInxFPr) {
            obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
            if (ZlNrrNcv3Ylc.currentScreen instanceof HandledScreen && ZlNrrNcv3Ylc.player.currentScreenHandler != ZlNrrNcv3Ylc.player.playerScreenHandler) {
               ZlNrrNcv3Ylc.player.closeHandledScreen();
               ZlNrrNcv3Ylc.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(ZlNrrNcv3Ylc.player.playerScreenHandler.syncId));
            } else {
               Module nuker = Modules.get().get(KekNuker.class);
               if (!nuker.isActive()) {
                  nuker.toggle();
               }

               if (wSJeYsJcmjNZf) {
                  MusheorSystem.debug("Post-restocking task running...");
                  irzXX.fStlZIvY9lS8(false);
                  Block restockBlock = ZlNrrNcv3Ylc.world.getBlockState(JPp6VyZP9f).getBlock();
                  if (restockBlock instanceof ShulkerBoxBlock || restockBlock instanceof EnderChestBlock) {
                     g0iiTDJzfIGXdui = true;
                     if (restockBlock instanceof EnderChestBlock) {
                        if (!j376sIQ()) {
                           QfZy2gWnKInxFPr(JPp6VyZP9f);
                        }
                     } else {
                        QfZy2gWnKInxFPr(JPp6VyZP9f);
                     }

                     if (PlayerUtils.isWithinReach(JPp6VyZP9f)) {
                        irzXX.fStlZIvY9lS8();
                        if (KekMine.duJ6sPeetAcBh6P.isActive()) {
                           KekMine.duJ6sPeetAcBh6P.fStlZIvY9lS8(JPp6VyZP9f, ZlNrrNcv3Ylc.world.getBlockState(JPp6VyZP9f), false);
                        }

                        if (BlockUtils.breakBlock(JPp6VyZP9f, true)) {
                           return;
                        }
                     } else if (!BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing()) {
                        BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(new GoalBlock(JPp6VyZP9f));
                     }
                  }

                  if (!ZlNrrNcv3Ylc.world.getBlockState(JPp6VyZP9f).isAir()) {
                     return;
                  }

                  g0iiTDJzfIGXdui = false;
                  if (icQ738dpWr7W > 0) {
                     icQ738dpWr7W--;
                     return;
                  }

                  Item item = fStlZIvY9lS8(12.0);
                  if (item != null) {
                     irzXX.fStlZIvY9lS8(item, false);
                  }

                  if (ZlNrrNcv3Ylc.world.getBlockState(JPp6VyZP9f).getBlock().equals(Blocks.AIR)
                     && !irzXX.qhulAUwd()
                     && !state.zkcZ4Xkp9Ez7Y()
                     && !state.umvNbb7iD9UpR()
                     && !g0iiTDJzfIGXdui
                     && !state.Nt3xeF()) {
                     fStlZIvY9lS8(false);
                  }
               }
            }
         }
      }
   }
}
