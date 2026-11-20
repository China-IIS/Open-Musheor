package musheor.modules.features;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent.Post;
import meteordevelopment.meteorclient.settings.BlockListSetting.Builder;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import musheor.musheor;
import musheor.utils.manager.PathKit;
import musheor.utils.system.MusheorSystem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import obf.U6PQSXqW;
import obf.irzXX;
import obf.jU8xXhmkg;

public class GrimScaffold extends Module {
   private final SettingGroup fStlZIvY9lS8 = this.settings.getDefaultGroup();
   private static final MinecraftClient QfZy2gWnKInxFPr = MinecraftClient.getInstance();
   private final Setting<List<Block>> TJImwK2 = this.fStlZIvY9lS8
      .add(
         ((Builder)((Builder)new Builder().name("allowed-blocks")).description("Which blocks scaffold is allowed to place"))
            .defaultValue(new Block[]{Blocks.OBSIDIAN, Blocks.NETHERRACK})
            .build()
      );
   private final Setting<Boolean> wSJeYsJcmjNZf = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("expand"))
                  .description("Extend the scaffold area beyond directly under your feet"))
               .defaultValue(false))
            .build()
      );
   private final Setting<Double> i2Nu6XHKic = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)new meteordevelopment.meteorclient.settings.DoubleSetting.Builder()
                     .name("expand-range"))
                  .description("How many blocks to expand the scaffold area"))
               .defaultValue(2.0)
               .sliderRange(1.0, 5.0)
               .decimalPlaces(1)
               .visible(this.wSJeYsJcmjNZf::get))
            .build()
      );
   private final Setting<Boolean> vGAMA89H = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("expand-forward-only"))
                     .description("Only expand in the direction you are currently facing"))
                  .defaultValue(true))
               .visible(this.wSJeYsJcmjNZf::get))
            .build()
      );
   private final Setting<Boolean> qhulAUwd = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("render"))
                  .description("Renders an overlay where blocks will be placed"))
               .defaultValue(true))
            .build()
      );
   private final List<BlockPos> BrmKXSYhWhqoB1 = new ArrayList<>();
   private Block TXWyNb = null;

   public GrimScaffold() {
      super(musheor.MAIN, "simple-scaffold", "Place blocks under your feet, even if you are mid-air");
   }

   public void onActivate() {
      this.BrmKXSYhWhqoB1.clear();
      this.TXWyNb = null;
   }

   public void onDeactivate() {
      this.BrmKXSYhWhqoB1.clear();
      this.TXWyNb = null;
   }

   @EventHandler
   private void fStlZIvY9lS8(Post event) {
      if (QfZy2gWnKInxFPr.player != null && QfZy2gWnKInxFPr.world != null) {
         this.TXWyNb = this.fStlZIvY9lS8();
         this.BrmKXSYhWhqoB1.clear();
         List<BlockPos> toPlace = this.QfZy2gWnKInxFPr();
         toPlace.removeIf(pos -> !BlockUtils.canPlace(pos, true));
         if ((Boolean)this.qhulAUwd.get()) {
            this.BrmKXSYhWhqoB1.addAll(toPlace);
         }

         if (this.TXWyNb != null && !toPlace.isEmpty()) {
            if (!(QfZy2gWnKInxFPr.player.getMainHandStack().getItem() instanceof BlockItem bi && bi.getBlock() == this.TXWyNb)) {
               PathKit.fStlZIvY9lS8(this.TXWyNb.asItem(), (Boolean)MusheorSystem.Manager.silentPlace.get());
            }

            for (BlockPos pos : toPlace) {
               if (!jU8xXhmkg.fStlZIvY9lS8()) {
                  break;
               }

               irzXX.fStlZIvY9lS8(pos, Direction.UP, U6PQSXqW.fStlZIvY9lS8().QfZy2gWnKInxFPr(), (Boolean)MusheorSystem.Manager.silentPlace.get());
            }
         }
      }
   }

   private Block fStlZIvY9lS8() {
      ItemStack main = QfZy2gWnKInxFPr.player.getMainHandStack();
      if (main.getItem() instanceof BlockItem bi && ((List)this.TJImwK2.get()).contains(bi.getBlock()) && !main.isEmpty()) {
         return bi.getBlock();
      } else {
         for (int i = 0; i < 9; i++) {
            ItemStack s = QfZy2gWnKInxFPr.player.getInventory().getStack(i);
            if (s.getItem() instanceof BlockItem bi && ((List)this.TJImwK2.get()).contains(bi.getBlock()) && !s.isEmpty()) {
               return bi.getBlock();
            }
         }

         return null;
      }
   }

   private List<BlockPos> QfZy2gWnKInxFPr() {
      List<BlockPos> positions = new ArrayList<>();
      Set<BlockPos> seen = new HashSet<>();
      BlockPos below = QfZy2gWnKInxFPr.player.getBlockPos().down();
      if (seen.add(below)) {
         positions.add(below);
      }

      if ((Boolean)this.wSJeYsJcmjNZf.get()) {
         int range = (int)Math.ceil((Double)this.i2Nu6XHKic.get());
         double rangeSq = (Double)this.i2Nu6XHKic.get() * (Double)this.i2Nu6XHKic.get();
         Direction facing = QfZy2gWnKInxFPr.player.getHorizontalFacing();
         if ((Boolean)this.vGAMA89H.get()) {
            for (int i = 1; i <= range; i++) {
               BlockPos p = new BlockPos(
                  below.getX() + facing.getOffsetX() * i, below.getY(), below.getZ() + facing.getOffsetZ() * i
               );
               if (seen.add(p)) {
                  positions.add(p);
               }
            }
         } else {
            for (int x = -range; x <= range; x++) {
               for (int z = -range; z <= range; z++) {
                  if ((x != 0 || z != 0) && !(x * x + z * z > rangeSq)) {
                     BlockPos p = below.add(x, 0, z);
                     if (seen.add(p)) {
                        positions.add(p);
                     }
                  }
               }
            }
         }
      }

      return positions;
   }

   @EventHandler
   private void fStlZIvY9lS8(Render3DEvent event) {
      if ((Boolean)this.qhulAUwd.get() && !this.BrmKXSYhWhqoB1.isEmpty()) {
         Block b = this.TXWyNb != null ? this.TXWyNb : Blocks.OBSIDIAN;
         musheor.utils.jU8xXhmkg.fStlZIvY9lS8(event, this.BrmKXSYhWhqoB1, b);
      }
   }
}
