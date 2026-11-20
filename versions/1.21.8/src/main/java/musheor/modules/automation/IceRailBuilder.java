package musheor.modules.automation;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.settings.EnumSetting.Builder;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import musheor.musheor;
import musheor.utils.jU8xXhmkg;
import musheor.utils.oyDPiOVBGKG;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

public class IceRailBuilder extends Module {
   private final Setting<IceRailBuilder.Axis> i2Nu6XHKic = this.settings
      .getDefaultGroup()
      .add(((Builder)((Builder)new Builder().name("axis")).defaultValue(IceRailBuilder.Axis.wSJeYsJcmjNZf)).build());
   BlockPos fStlZIvY9lS8;
   BlockPos QfZy2gWnKInxFPr;
   List<BlockPos> TJImwK2 = new ArrayList<>();
   List<BlockPos> wSJeYsJcmjNZf = new ArrayList<>();

   public IceRailBuilder() {
      super(musheor.AUTOMATION, "ice-rail-builder", "Semi-automated ice highway building");
   }

   public void onActivate() {
      if (this.mc.player != null && this.mc.world != null) {
         this.fStlZIvY9lS8((IceRailBuilder.Axis)this.i2Nu6XHKic.get());
      }
   }

   @EventHandler
   public void fStlZIvY9lS8(Pre event) {
      if (this.mc.player != null && this.mc.world != null) {
         if (this.fStlZIvY9lS8 != null) {
         }

         if (this.QfZy2gWnKInxFPr != null) {
            this.TJImwK2.clear();
            this.wSJeYsJcmjNZf.clear();

            for (BlockPos pos : this.QfZy2gWnKInxFPr(true)) {
               if (this.mc.world.getBlockState(pos).getBlock() != Blocks.BLUE_ICE) {
                  this.TJImwK2.add(pos.toImmutable());
               }
            }

            for (BlockPos posx : this.fStlZIvY9lS8(true)) {
               if (this.mc.world.getBlockState(posx).isReplaceable()) {
                  this.wSJeYsJcmjNZf.add(posx.toImmutable());
               }
            }

            if (!this.wSJeYsJcmjNZf.isEmpty()) {
               oyDPiOVBGKG.fStlZIvY9lS8(this.wSJeYsJcmjNZf, Blocks.NETHERRACK);
               return;
            }

            if (!this.TJImwK2.isEmpty()) {
               oyDPiOVBGKG.fStlZIvY9lS8(this.TJImwK2, Blocks.BLUE_ICE);
               return;
            }
         }
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(Render3DEvent event) {
      if (this.mc.player != null && this.mc.world != null) {
         jU8xXhmkg.fStlZIvY9lS8(event, this.wSJeYsJcmjNZf, Blocks.NETHERRACK);
         jU8xXhmkg.fStlZIvY9lS8(event, this.TJImwK2, Blocks.BLUE_ICE);
      }
   }

   private void fStlZIvY9lS8(IceRailBuilder.Axis axis) {
      this.fStlZIvY9lS8 = this.QfZy2gWnKInxFPr = null;
      switch (axis) {
         case fStlZIvY9lS8:
         case QfZy2gWnKInxFPr:
            this.fStlZIvY9lS8 = new BlockPos(0, 115, -200);
            break;
         case TJImwK2:
         case wSJeYsJcmjNZf:
            this.QfZy2gWnKInxFPr = new BlockPos(-200, 115, 0);
      }
   }

   private List<BlockPos> fStlZIvY9lS8(boolean x) {
      List<BlockPos> positions = new ArrayList<>();
      if (x) {
         for (int i = -2; i < 2; i++) {
            positions.add(new BlockPos(this.mc.player.getBlockX() + i, 116, -201));
            positions.add(new BlockPos(this.mc.player.getBlockX() + i, 116, -198));
            positions.add(new BlockPos(this.mc.player.getBlockX() + i, 113, -199));
         }
      }

      return positions;
   }

   private List<BlockPos> QfZy2gWnKInxFPr(boolean x) {
      List<BlockPos> positions = new ArrayList<>();
      if (x) {
         for (int i = -5; i < 5; i++) {
            if ((this.mc.player.getBlockX() + i) % 2 == 0) {
               positions.add(new BlockPos(this.mc.player.getBlockX() + i, 115, -200));
            }
         }
      }

      return positions;
   }

   private static enum Axis {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr,
      TJImwK2,
      wSJeYsJcmjNZf;
   }
}
