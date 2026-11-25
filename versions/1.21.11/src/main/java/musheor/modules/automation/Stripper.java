package musheor.modules.automation;

import com.google.common.collect.BiMap;
import java.util.ArrayList;
import java.util.List;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.BlockSetting.Builder;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.misc.Names;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import musheor.mixin.AxeItemAccessor;
import musheor.modules.features.KekMine;
import musheor.utils.oyDPiOVBGKG;
import musheor.utils.manager.pvfA15fQSVM;
import musheor.utils.system.MusheorSystem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoneycombItem;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import obf.U6PQSXqW;
import obf.irzXX;
import obf.jU8xXhmkg;

public class Stripper extends Module {
   private final SettingGroup fStlZIvY9lS8 = this.settings.getDefaultGroup();
   private final Setting<Block> QfZy2gWnKInxFPr = this.fStlZIvY9lS8
      .add(
         ((Builder)((Builder)((Builder)((Builder)new Builder().name("block")).description("Block to place and strip."))
                  .filter(Stripper::fStlZIvY9lS8)
                  .defaultValue(Blocks.OAK_LOG))
               .onChanged(block -> {
                  if (this.TJImwK2 != null) {
                     List<Block> chain = TJImwK2(block);
                     if (!chain.isEmpty()) {
                        this.TJImwK2.set(chain.getLast());
                     }
                  }
               }))
            .build()
      );
   private final Setting<Block> TJImwK2 = this.fStlZIvY9lS8
      .add(
         ((Builder)((Builder)((Builder)((Builder)new Builder().name("target-state")).description("Keep interacting until the block reaches this state."))
                  .filter(block -> TJImwK2((Block)this.QfZy2gWnKInxFPr.get()).contains(block))
                  .defaultValue(Blocks.STRIPPED_OAK_LOG))
               .visible(() -> TJImwK2((Block)this.QfZy2gWnKInxFPr.get()).size() > 1))
            .build()
      );
   private final Setting<Integer> wSJeYsJcmjNZf = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("step-delay"))
                  .description("Ticks to wait between placing, stripping and breaking. 0 runs one step per tick (3 ticks per block)."))
               .defaultValue(0))
            .sliderRange(0, 20)
            .build()
      );
   private final Setting<Boolean> i2Nu6XHKic = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("self-disable"))
                  .description("Disables when you run out of blocks to strip, or when there is no axe left in your inventory."))
               .defaultValue(true))
            .build()
      );
   private Stripper.Stage vGAMA89H = Stripper.Stage.PLACE;
   private BlockPos qhulAUwd;
   private BlockPos BrmKXSYhWhqoB1;
   private int TXWyNb;
   private int I3psabmOrOI;

   public Stripper() {
      super(musheor.musheor.AUTOMATION, "stripper", "Automatically places, strips and breaks logs and waxed copper blocks");
   }

   public void onActivate() {
      this.vGAMA89H = Stripper.Stage.PLACE;
      this.qhulAUwd = null;
      this.BrmKXSYhWhqoB1 = null;
      this.TXWyNb = 0;
      this.I3psabmOrOI = 0;
   }

   public void onDeactivate() {
      this.qhulAUwd = null;
      this.BrmKXSYhWhqoB1 = null;
      this.I3psabmOrOI = 0;
   }

   @EventHandler
   private void fStlZIvY9lS8(Pre event) {
      if (this.mc.player != null && this.mc.world != null) {
         if (!HighwayBuilder.TJImwK2() && !HighwayBuilder.wSJeYsJcmjNZf()) {
            if (this.I3psabmOrOI > 0) {
               this.I3psabmOrOI--;
            } else if (!this.i2Nu6XHKic()) {
               if ((Boolean)this.i2Nu6XHKic.get()) {
                  this.warning("No axe left in your inventory, disabling.", new Object[0]);
                  this.toggle();
               }
            } else {
               BlockPos pos = this.mc.player.getBlockPos().offset(this.mc.player.getHorizontalFacing());
               if (!pos.equals(this.qhulAUwd)) {
                  this.qhulAUwd = pos;
                  this.TXWyNb = 0;
                  this.vGAMA89H = Stripper.Stage.PLACE;
               }

               switch (this.vGAMA89H) {
                  case PLACE:
                     this.QfZy2gWnKInxFPr();
                     break;
                  case STRIP:
                     this.TJImwK2();
                     break;
                  case BREAK:
                     this.wSJeYsJcmjNZf();
               }
            }
         }
      }
   }

   private void QfZy2gWnKInxFPr() {
      BlockState state = this.mc.world.getBlockState(this.qhulAUwd);
      if (state.getBlock() == this.QfZy2gWnKInxFPr.get()) {
         this.fStlZIvY9lS8(Stripper.Stage.STRIP);
      } else if (!state.isReplaceable()) {
         this.fStlZIvY9lS8(Stripper.Stage.BREAK);
      } else if (pvfA15fQSVM.i2Nu6XHKic(((Block)this.QfZy2gWnKInxFPr.get()).asItem()) == 0) {
         if ((Boolean)this.i2Nu6XHKic.get()) {
            this.info("§2Out of %s, disabling.", new Object[]{Names.get((Block)this.QfZy2gWnKInxFPr.get())});
            this.toggle();
         }
      } else {
         boolean silent = (Boolean)MusheorSystem.Manager.silentPlace.get();
         if (!pvfA15fQSVM.I3psabmOrOI(((Block)this.QfZy2gWnKInxFPr.get()).asItem())) {
            this.I3psabmOrOI = (Integer)MusheorSystem.Manager.swapDelay.get();
         } else {
            MusheorSystem.PlacementType type = U6PQSXqW.fStlZIvY9lS8().QfZy2gWnKInxFPr();
            if (type.equals(MusheorSystem.PlacementType.Rotate)) {
               oyDPiOVBGKG.TJImwK2(this.qhulAUwd, Direction.DOWN);
            }

            irzXX.fStlZIvY9lS8(this.qhulAUwd, Direction.DOWN, type, silent);
            this.fStlZIvY9lS8(Stripper.Stage.STRIP);
         }
      }
   }

   private void TJImwK2() {
      Block block = this.mc.world.getBlockState(this.qhulAUwd).getBlock();
      int needed = this.wSJeYsJcmjNZf(block);
      if (needed == 0) {
         this.fStlZIvY9lS8(Stripper.Stage.BREAK);
      } else if (!(this.mc.player.getMainHandStack().getItem() instanceof AxeItem)) {
         if (this.vGAMA89H()) {
            this.I3psabmOrOI = (Integer)MusheorSystem.Manager.swapDelay.get();
         }
      } else {
         Direction side = this.fStlZIvY9lS8(this.qhulAUwd);

         for (int i = 0; i < needed; i++) {
            if (!jU8xXhmkg.fStlZIvY9lS8()) {
               return;
            }

            this.fStlZIvY9lS8(this.qhulAUwd, side);
         }

         this.fStlZIvY9lS8(Stripper.Stage.BREAK);
      }
   }

   private void wSJeYsJcmjNZf() {
      BlockState state = this.mc.world.getBlockState(this.qhulAUwd);
      if (state.isAir()) {
         this.fStlZIvY9lS8(Stripper.Stage.PLACE);
      } else {
         if (this.qhulAUwd.equals(this.BrmKXSYhWhqoB1) && this.TXWyNb > 0) {
            pvfA15fQSVM.QfZy2gWnKInxFPr(this.BrmKXSYhWhqoB1);
            KekMine.duJ6sPeetAcBh6P.fStlZIvY9lS8(Action.STOP_DESTROY_BLOCK, this.qhulAUwd);
            if (!(Boolean)KekMine.duJ6sPeetAcBh6P.TXWyNb.get()) {
               this.mc.world.syncWorldEvent(2001, this.qhulAUwd, Block.getRawIdFromState(this.mc.world.getBlockState(this.qhulAUwd)));
               this.mc.world.setBlockState(this.qhulAUwd, Blocks.AIR.getDefaultState(), 3);
            }
         } else if (KekMine.duJ6sPeetAcBh6P.isActive()) {
            KekMine.duJ6sPeetAcBh6P.fStlZIvY9lS8(this.qhulAUwd, state, false);
         } else {
            BlockUtils.breakBlock(this.qhulAUwd, true);
         }

         this.BrmKXSYhWhqoB1 = this.qhulAUwd;
         this.TXWyNb++;
         this.fStlZIvY9lS8(Stripper.Stage.PLACE);
      }
   }

   private void fStlZIvY9lS8(Stripper.Stage next) {
      this.vGAMA89H = next;
      this.I3psabmOrOI = (Integer)this.wSJeYsJcmjNZf.get();
   }

   private boolean i2Nu6XHKic() {
      return InvUtils.find(stack -> stack.getItem() instanceof AxeItem).found();
   }

   private boolean vGAMA89H() {
      FindItemResult axe = InvUtils.find(stack -> stack.getItem() instanceof AxeItem);
      if (!axe.found()) {
         return false;
      }

      pvfA15fQSVM.TXWyNb(this.mc.player.getInventory().getStack(axe.slot()).getItem());
      return true;
   }

   private void fStlZIvY9lS8(BlockPos pos, Direction side) {
      if (this.mc.interactionManager != null) {
         Vec3d hitPos = Vec3d.ofCenter(pos).add(side.getOffsetX() * 0.5, side.getOffsetY() * 0.5, side.getOffsetZ() * 0.5);
         BlockUtils.interact(new BlockHitResult(hitPos, side, pos, false), Hand.MAIN_HAND, true);
      }
   }

   private Direction fStlZIvY9lS8(BlockPos pos) {
      return KekMine.duJ6sPeetAcBh6P != null ? KekMine.duJ6sPeetAcBh6P.vGAMA89H(pos) : Direction.UP;
   }

   public static boolean fStlZIvY9lS8(Block block) {
      return QfZy2gWnKInxFPr(block) != null;
   }

   public static Block QfZy2gWnKInxFPr(Block block) {
      Block stripped = AxeItemAccessor.getStrippedBlocks().get(block);
      if (stripped != null) {
         return stripped;
      }

      Block unwaxed = (Block)((BiMap)HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get()).get(block);
      return unwaxed != null ? unwaxed : (Block)Oxidizable.getDecreasedOxidationBlock(block).orElse(null);
   }

   public static List<Block> TJImwK2(Block from) {
      List<Block> chain = new ArrayList<>(4);
      Block current = from;

      while ((current = QfZy2gWnKInxFPr(current)) != null && !chain.contains(current)) {
         chain.add(current);
      }

      return chain;
   }

   private int wSJeYsJcmjNZf(Block from) {
      List<Block> chain = TJImwK2(from);
      int index = chain.indexOf(this.TJImwK2.get());
      return index < 0 ? chain.size() : index + 1;
   }

   public int fStlZIvY9lS8() {
      return this.wSJeYsJcmjNZf((Block)this.QfZy2gWnKInxFPr.get());
   }

   public String getInfoString() {
      int needed = this.fStlZIvY9lS8();
      return needed > 1 ? Names.get((Block)this.QfZy2gWnKInxFPr.get()) + " x" + needed : Names.get((Block)this.QfZy2gWnKInxFPr.get());
   }

   @EventHandler
   private void fStlZIvY9lS8(Render3DEvent event) {
      if (this.mc.player != null && this.mc.world != null && this.qhulAUwd != null) {
         musheor.utils.jU8xXhmkg.fStlZIvY9lS8(event, this.qhulAUwd);
      }
   }

   private enum Stage {
      PLACE,
      STRIP,
      BREAK;

      Stage() {
      }
   }
}
