package musheor.modules.automation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.BoolSetting.Builder;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.RenderUtils;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import musheor.musheor;
import musheor.utils.manager.PathKit;
import musheor.utils.oyDPiOVBGKG;
import musheor.utils.system.MusheorSystem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import obf.U6PQSXqW;
import obf.irzXX;
import obf.jU8xXhmkg;

public class SourceRemover extends Module {
   private final SettingGroup QfZy2gWnKInxFPr = this.settings.getDefaultGroup();
   private final Setting<Boolean> TJImwK2 = this.QfZy2gWnKInxFPr
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("only-sources")).description("Only fills source blocks, not flowing fluids")).defaultValue(true))
            .build()
      );
   private final Setting<Boolean> wSJeYsJcmjNZf = this.QfZy2gWnKInxFPr
      .add(((Builder)((Builder)((Builder)new Builder().name("lava")).description("Fills lava source blocks")).defaultValue(true)).build());
   private final Setting<Boolean> i2Nu6XHKic = this.QfZy2gWnKInxFPr
      .add(((Builder)((Builder)((Builder)new Builder().name("water")).description("Fills water source blocks")).defaultValue(false)).build());
   private final Setting<Boolean> vGAMA89H = this.QfZy2gWnKInxFPr
      .add(
         ((Builder)((Builder)((Builder)((Builder)new Builder().name("bubble-column")).description("Fills bubble column source blocks")).defaultValue(false))
               .visible(this.i2Nu6XHKic::get))
            .build()
      );
   private final Setting<SourceRemover.ListMode> qhulAUwd = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)new meteordevelopment.meteorclient.settings.EnumSetting.Builder()
                     .name("list-mode"))
                  .description("Selection mode."))
               .defaultValue(SourceRemover.ListMode.fStlZIvY9lS8))
            .build()
      );
   private final Setting<List<Block>> BrmKXSYhWhqoB1 = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.BlockListSetting.Builder)((meteordevelopment.meteorclient.settings.BlockListSetting.Builder)((meteordevelopment.meteorclient.settings.BlockListSetting.Builder)new meteordevelopment.meteorclient.settings.BlockListSetting.Builder()
                     .name("whitelist"))
                  .description("The allowed blocks that it will use to fill up the lava."))
               .defaultValue(
                  new Block[]{Blocks.NETHERRACK, Blocks.COBBLESTONE, Blocks.DIRT, Blocks.BLACKSTONE, Blocks.OBSIDIAN}
               )
               .visible(() -> this.qhulAUwd.get() == SourceRemover.ListMode.QfZy2gWnKInxFPr))
            .build()
      );
   private final Setting<List<Block>> TXWyNb = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.BlockListSetting.Builder)((meteordevelopment.meteorclient.settings.BlockListSetting.Builder)((meteordevelopment.meteorclient.settings.BlockListSetting.Builder)new meteordevelopment.meteorclient.settings.BlockListSetting.Builder()
                     .name("blacklist"))
                  .description("The denied blocks that it not will use to fill up the lava."))
               .visible(() -> this.qhulAUwd.get() == SourceRemover.ListMode.TJImwK2))
            .build()
      );
   private final Setting<Boolean> I3psabmOrOI = this.QfZy2gWnKInxFPr
      .add(((Builder)((Builder)((Builder)new Builder().name("render")).description("Render source blocks in specific range")).defaultValue(true)).build());
   private final Setting<Integer> duJ6sPeetAcBh6P = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("render-range"))
                     .description("Maximum range at which sourceblocks are rendered"))
                  .defaultValue(16))
               .sliderRange(1, 64)
               .visible(this.I3psabmOrOI::get))
            .build()
      );
   private final List<BlockPos> j376sIQ = new ArrayList<>();
   private int V7zKJ4n6D;
   private int voxTwgNzyrpaAnP;
   private int xileXsbo = 0;
   public static boolean fStlZIvY9lS8;

   public static boolean fStlZIvY9lS8() {
      return fStlZIvY9lS8;
   }

   public SourceRemover() {
      super(musheor.AUTOMATION, "source-filler", "Fill up lava and or water source blocks");
   }

   public void onActivate() {
      U6PQSXqW.fStlZIvY9lS8().PVlYDeNipUe().clear();
      this.V7zKJ4n6D = 0;
      this.xileXsbo = 0;
      this.voxTwgNzyrpaAnP = 0;
   }

   @EventHandler
   private void fStlZIvY9lS8(Pre event) {
      if (this.mc.player != null && this.mc.world != null) {
         if (!HighwayBuilder.wSJeYsJcmjNZf() && !HighwayBuilder.vGAMA89H()) {
            this.V7zKJ4n6D++;
            oyDPiOVBGKG.fStlZIvY9lS8(this.V7zKJ4n6D, Blocks.AIR);
            if (++this.xileXsbo > 5 && (Boolean)this.I3psabmOrOI.get()) {
               this.xileXsbo = 0;
               this.QfZy2gWnKInxFPr();
            }

            Item bestItem = null;

            for (int i = 0; i < 9; i++) {
               ItemStack itemStack = this.mc.player.getInventory().getStack(i);
               if (this.fStlZIvY9lS8(itemStack)) {
                  bestItem = itemStack.getItem();
               }
            }

            if (bestItem == null) {
               for (int ix = 9; ix < this.mc.player.getInventory().main.size(); ix++) {
                  ItemStack itemStack = this.mc.player.getInventory().getStack(ix);
                  if (this.fStlZIvY9lS8(itemStack)) {
                     PathKit.fStlZIvY9lS8(itemStack.getItem());
                     return;
                  }
               }
            } else {
               List<BlockPos> positions = this.fStlZIvY9lS8((Double)MusheorSystem.Manager.placementRange.get());
               positions.sort(Comparator.comparingDouble(posx -> this.mc.player.squaredDistanceTo(Vec3d.of(posx))));
               boolean place = false;

               for (BlockPos pos : positions) {
                  if (BlockUtils.canPlace(pos, true) && oyDPiOVBGKG.TJImwK2(pos)) {
                     RenderUtils.renderTickingBlock(pos, Color.PINK, Color.PINK, ShapeMode.Lines, 0, 1, false, false);
                     place = true;
                     break;
                  }
               }

               if (this.voxTwgNzyrpaAnP > 0) {
                  this.voxTwgNzyrpaAnP--;
               } else {
                  if (place) {
                     if (!PathKit.I3psabmOrOI(bestItem)) {
                        this.voxTwgNzyrpaAnP = (Integer)MusheorSystem.Manager.swapDelay.get();
                        return;
                     }

                     for (BlockPos posx : positions) {
                        if (BlockUtils.canPlace(posx, true)) {
                           Direction side = KekNuker.QfZy2gWnKInxFPr(this.mc.player.getEyePos(), posx);
                           if (!jU8xXhmkg.fStlZIvY9lS8()) {
                              break;
                           }

                           irzXX.fStlZIvY9lS8(posx, side, U6PQSXqW.fStlZIvY9lS8().QfZy2gWnKInxFPr(), (Boolean)MusheorSystem.Manager.silentPlace.get());
                           U6PQSXqW.fStlZIvY9lS8().PVlYDeNipUe().put(posx, this.V7zKJ4n6D);
                           MusheorSystem.debug(
                              "Filled liquid at %d, %d, %d with a distance of: %s",
                              posx.getX(),
                              posx.getY(),
                              posx.getZ(),
                              Math.sqrt(KekNuker.fStlZIvY9lS8(this.mc.player.getEyePos(), posx))
                           );
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private boolean fStlZIvY9lS8(ItemStack stack) {
      return this.qhulAUwd.get() == SourceRemover.ListMode.TJImwK2 && !((List)this.TXWyNb.get()).contains(Block.getBlockFromItem(stack.getItem()))
         || this.qhulAUwd.get() == SourceRemover.ListMode.QfZy2gWnKInxFPr
            && ((List)this.BrmKXSYhWhqoB1.get()).contains(Block.getBlockFromItem(stack.getItem()))
         || this.qhulAUwd.get() == SourceRemover.ListMode.fStlZIvY9lS8
            && stack.getItem() instanceof BlockItem blockItem
            && blockItem.getBlock().getDefaultState().isSolid();
   }

   private void QfZy2gWnKInxFPr() {
      if (this.mc.world != null && this.mc.player != null) {
         this.j376sIQ.clear();
         BlockPos playerPos = this.mc.player.getBlockPos();
         int dist = (Integer)this.duJ6sPeetAcBh6P.get();

         for (int x = -dist; x <= dist; x++) {
            for (int y = -dist; y <= dist; y++) {
               for (int z = -dist; z <= dist; z++) {
                  BlockPos pos = playerPos.add(x, y, z);
                  if (this.mc
                        .player
                        .getEyePos()
                        .distanceTo(new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5))
                     <= dist) {
                     BlockState state = this.mc.world.getBlockState(pos);
                     FluidState fluid = state.getFluidState();
                     if (!(Boolean)this.TJImwK2.get() || fluid.isStill()) {
                        if ((Boolean)this.wSJeYsJcmjNZf.get()
                           && (fluid.getFluid() == Fluids.LAVA || fluid.getFluid() == Fluids.FLOWING_LAVA)) {
                           this.j376sIQ.add(pos);
                        }

                        if ((Boolean)this.i2Nu6XHKic.get()
                           && (fluid.getFluid() == Fluids.WATER || fluid.getFluid() == Fluids.FLOWING_WATER)
                           && (!(state.getBlock() instanceof BubbleColumnBlock) || (Boolean)this.vGAMA89H.get())) {
                           this.j376sIQ.add(pos);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private List<BlockPos> fStlZIvY9lS8(double radius) {
      List<BlockPos> positions = new ArrayList<>();
      Vec3d eyePos = this.mc.player.getEyePos();
      int blockRadius = (int)Math.ceil(radius);

      for (int x = -blockRadius; x < radius; x++) {
         for (int y = -blockRadius; y < blockRadius; y++) {
            for (int z = -blockRadius; z < blockRadius; z++) {
               BlockPos pos = BlockPos.ofFloored(eyePos.x + x, eyePos.y + y, eyePos.z + z);
               BlockState blockState = this.mc.world.getBlockState(pos);
               FluidState fluid = blockState.getFluidState();
               if (fluid != null && !(KekNuker.fStlZIvY9lS8(eyePos, pos) > radius * radius) && (!(Boolean)this.TJImwK2.get() || fluid.isStill())) {
                  if ((Boolean)this.i2Nu6XHKic.get() && (fluid.getFluid() == Fluids.WATER || fluid.getFluid() == Fluids.FLOWING_WATER)) {
                     if (!(blockState.getBlock() instanceof BubbleColumnBlock) || (Boolean)this.vGAMA89H.get()) {
                        positions.add(pos);
                     }
                  } else if ((Boolean)this.wSJeYsJcmjNZf.get()
                     && (fluid.getFluid() == Fluids.LAVA || fluid.getFluid() == Fluids.FLOWING_LAVA)) {
                     positions.add(pos);
                  }
               }
            }
         }
      }

      return positions;
   }

   @EventHandler
   private void fStlZIvY9lS8(Render3DEvent event) {
      if ((Boolean)this.I3psabmOrOI.get() && !this.j376sIQ.isEmpty() && this.mc.world != null) {
         musheor.utils.jU8xXhmkg.QfZy2gWnKInxFPr(event, this.j376sIQ);
      }
   }

   public static enum ListMode {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr,
      TJImwK2;
   }
}
