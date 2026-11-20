package musheor.modules.automation;

import meteordevelopment.meteorclient.events.world.TickEvent.Post;
import meteordevelopment.meteorclient.settings.ItemSetting.Builder;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import musheor.musheor;
import musheor.utils.manager.pvfA15fQSVM;
import musheor.utils.oyDPiOVBGKG;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class Refill extends Module {
   private final SettingGroup I3psabmOrOI = this.settings.getDefaultGroup();
   private final MinecraftClient duJ6sPeetAcBh6P = MinecraftClient.getInstance();
   public static Refill INSTANCE;
   public final Setting<Item> fStlZIvY9lS8 = this.I3psabmOrOI
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("item")).description("What block to steal / loot from shulker.")).defaultValue(Items.OBSIDIAN))
            .build()
      );
   private final Setting<Boolean> j376sIQ = this.I3psabmOrOI
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("fill-all"))
                  .description("When this setting is enabled, the inventory will be completely filled up until there are no empty slots"))
               .defaultValue(true))
            .build()
      );
   private final Setting<Integer> V7zKJ4n6D = this.I3psabmOrOI
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("max-slots"))
                     .description("How many slots can maximumly be filled up"))
                  .defaultValue(10))
               .visible(() -> !(Boolean)this.j376sIQ.get()))
            .build()
      );
   private final Setting<Boolean> voxTwgNzyrpaAnP = this.I3psabmOrOI
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                  .name("place-upside-down-below"))
               .defaultValue(true))
            .build()
      );
   boolean QfZy2gWnKInxFPr;
   boolean TJImwK2;
   boolean wSJeYsJcmjNZf;
   int i2Nu6XHKic;
   int vGAMA89H;
   int qhulAUwd;
   BlockPos BrmKXSYhWhqoB1;
   Direction TXWyNb;

   public Refill() {
      super(musheor.AUTOMATION, "refill", "Places a shulkerbox and refills the inventory with items.");
      INSTANCE = this;
   }

   public void onActivate() {
      this.QfZy2gWnKInxFPr = false;
      this.BrmKXSYhWhqoB1 = null;
      this.TXWyNb = Direction.DOWN;
      this.TJImwK2 = false;
      this.wSJeYsJcmjNZf = false;
      this.i2Nu6XHKic = 0;
      this.vGAMA89H = 0;
      this.qhulAUwd = 5;
   }

   @EventHandler
   private void fStlZIvY9lS8(Post event) {
      if (this.duJ6sPeetAcBh6P.player != null && this.duJ6sPeetAcBh6P.world != null) {
         if (this.isActive()) {
            if (!this.QfZy2gWnKInxFPr) {
               ItemStack lookingFor = pvfA15fQSVM.BrmKXSYhWhqoB1((Item)this.fStlZIvY9lS8.get());
               if (lookingFor != null) {
                  int slot = this.duJ6sPeetAcBh6P.player.getInventory().getSlotWithStack(lookingFor);
                  if (this.duJ6sPeetAcBh6P.player.getInventory().selectedSlot == slot) {
                     this.QfZy2gWnKInxFPr = true;
                     return;
                  }

                  for (int i = 0; i < 9; i++) {
                     if (this.duJ6sPeetAcBh6P.player.getInventory().getStack(i) == lookingFor) {
                        pvfA15fQSVM.fStlZIvY9lS8(i);
                        this.QfZy2gWnKInxFPr = true;
                        return;
                     }
                  }
               }

               int emptySlot = pvfA15fQSVM.wSJeYsJcmjNZf();
               if (pvfA15fQSVM.fStlZIvY9lS8((Item)this.fStlZIvY9lS8.get(), emptySlot)) {
                  pvfA15fQSVM.fStlZIvY9lS8(emptySlot);
                  this.QfZy2gWnKInxFPr = true;
                  return;
               }
            }

            if (this.BrmKXSYhWhqoB1 == null) {
               if ((Boolean)this.voxTwgNzyrpaAnP.get()) {
                  this.BrmKXSYhWhqoB1 = this.duJ6sPeetAcBh6P
                     .player
                     .getBlockPos()
                     .withY(this.duJ6sPeetAcBh6P.player.getBlockPos().getY() - 2);
               } else if (this.duJ6sPeetAcBh6P.crosshairTarget instanceof BlockHitResult bhr) {
                  BlockPos adjacent = bhr.getBlockPos();
                  this.TXWyNb = bhr.getSide();
                  this.BrmKXSYhWhqoB1 = adjacent.offset(this.TXWyNb);
               }

               return;
            }

            if (!this.TJImwK2) {
               if (BlockUtils.canPlace(this.BrmKXSYhWhqoB1, true)) {
                  this.TJImwK2 = oyDPiOVBGKG.fStlZIvY9lS8(this.BrmKXSYhWhqoB1, this.TXWyNb);
               } else {
                  this.toggle();
                  this.info("Cannot place shulkerbox at desired position", new Object[0]);
               }

               return;
            }

            if (this.duJ6sPeetAcBh6P.world.getBlockState(this.BrmKXSYhWhqoB1).getBlock() instanceof ShulkerBoxBlock) {
               if (!this.wSJeYsJcmjNZf) {
                  if (!(this.duJ6sPeetAcBh6P.player.currentScreenHandler instanceof ShulkerBoxScreenHandler)) {
                     this.duJ6sPeetAcBh6P
                        .player
                        .networkHandler
                        .sendPacket(
                           new PlayerInteractBlockC2SPacket(
                              Hand.MAIN_HAND,
                              new BlockHitResult(Vec3d.ofCenter(this.BrmKXSYhWhqoB1), Direction.UP, this.BrmKXSYhWhqoB1, false),
                              0
                           )
                        );
                  } else {
                     this.wSJeYsJcmjNZf = true;
                  }
               } else {
                  ScreenHandler handler = this.duJ6sPeetAcBh6P.player.currentScreenHandler;
                  if (this.qhulAUwd > 0) {
                     this.qhulAUwd--;
                     return;
                  }

                  if ((Boolean)this.j376sIQ.get()) {
                     for (int ix = 0; ix < 27 && pvfA15fQSVM.duJ6sPeetAcBh6P() > 0; ix++) {
                        if (handler.getSlot(this.i2Nu6XHKic).getStack().getItem() == this.fStlZIvY9lS8.get()) {
                           InvUtils.shiftClick().slotId(this.i2Nu6XHKic);
                        }

                        this.i2Nu6XHKic++;
                     }

                     this.duJ6sPeetAcBh6P.player.closeHandledScreen();
                     this.toggle();
                  } else if (this.vGAMA89H >= (Integer)this.V7zKJ4n6D.get() && this.i2Nu6XHKic >= 27) {
                     this.duJ6sPeetAcBh6P.player.closeHandledScreen();
                     this.toggle();
                  } else {
                     if (handler.getSlot(this.i2Nu6XHKic).getStack().getItem() == this.fStlZIvY9lS8.get()) {
                        InvUtils.shiftClick().slotId(this.i2Nu6XHKic);
                        this.vGAMA89H++;
                     }

                     this.i2Nu6XHKic++;
                  }
               }
            }
         }
      }
   }
}
