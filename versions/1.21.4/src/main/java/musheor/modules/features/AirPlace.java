package musheor.modules.features;

import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent.Post;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.BoolSetting.Builder;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import musheor.utils.jU8xXhmkg;
import musheor.utils.oyDPiOVBGKG;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BlockItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class AirPlace extends Module {
   private final SettingGroup fStlZIvY9lS8 = this.settings.getDefaultGroup();
   private static final MinecraftClient QfZy2gWnKInxFPr = MinecraftClient.getInstance();
   private final Setting<Boolean> TJImwK2 = this.fStlZIvY9lS8
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("render")).description("Renders an overlay where the block will be placed.")).defaultValue(true))
            .build()
      );
   private final Setting<Boolean> wSJeYsJcmjNZf = this.fStlZIvY9lS8
      .add(((Builder)((Builder)((Builder)new Builder().name("custom-range")).description("Use custom range for air place.")).defaultValue(false)).build());
   private final Setting<Double> i2Nu6XHKic = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)new meteordevelopment.meteorclient.settings.DoubleSetting.Builder()
                     .name("range"))
                  .description("Custom range to place at."))
               .visible(this.wSJeYsJcmjNZf::get))
            .defaultValue(4.5)
            .min(0.0)
            .sliderMax(6.0)
            .build()
      );
   private BlockPos vGAMA89H = null;
   private boolean qhulAUwd = false;
   private int BrmKXSYhWhqoB1 = 0;

   public AirPlace() {
      super(musheor.musheor.MAIN, "airplace", "Place blocks mid-air");
   }

   public void onActivate() {
      this.qhulAUwd = false;
      this.BrmKXSYhWhqoB1 = 0;
      this.vGAMA89H = null;
   }

   @EventHandler
   private void fStlZIvY9lS8(Post event) {
      if (QfZy2gWnKInxFPr.player != null && QfZy2gWnKInxFPr.world != null) {
         this.vGAMA89H = this.fStlZIvY9lS8();
         boolean pressed = QfZy2gWnKInxFPr.options.useKey.isPressed();
         boolean justPressed = pressed && !this.qhulAUwd;
         this.qhulAUwd = pressed;
         if (!pressed) {
            this.BrmKXSYhWhqoB1 = 0;
         } else if (this.vGAMA89H != null) {
            if (QfZy2gWnKInxFPr.player.getMainHandStack().getItem() instanceof BlockItem
               || QfZy2gWnKInxFPr.player.getMainHandStack().getItem() instanceof SpawnEggItem) {
               if (BlockUtils.canPlace(this.vGAMA89H, true)) {
                  if (justPressed) {
                     this.BrmKXSYhWhqoB1 = 0;
                     this.fStlZIvY9lS8(this.vGAMA89H);
                  } else {
                     this.BrmKXSYhWhqoB1++;
                     if (this.BrmKXSYhWhqoB1 >= 4) {
                        this.BrmKXSYhWhqoB1 = 0;
                        this.fStlZIvY9lS8(this.vGAMA89H);
                     }
                  }
               }
            }
         }
      }
   }

   private BlockPos fStlZIvY9lS8() {
      if (QfZy2gWnKInxFPr.player != null && QfZy2gWnKInxFPr.world != null) {
         double r = this.wSJeYsJcmjNZf.get() ? (Double)this.i2Nu6XHKic.get() : QfZy2gWnKInxFPr.player.getBlockInteractionRange();
         if (QfZy2gWnKInxFPr.getCameraEntity() == null) {
            return null;
         }

         if (QfZy2gWnKInxFPr.getCameraEntity().raycast(r, 0.0F, false) instanceof BlockHitResult bhr) {
            BlockPos hitPos = bhr.getBlockPos();
            if (QfZy2gWnKInxFPr.world.getBlockState(hitPos).isReplaceable()) {
               return hitPos;
            }

            BlockPos adjacent = hitPos.offset(bhr.getSide());
            return QfZy2gWnKInxFPr.world.getBlockState(adjacent).isReplaceable() ? adjacent : null;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private void fStlZIvY9lS8(BlockPos pos) {
      BlockHitResult hit = new BlockHitResult(Vec3d.ofCenter(pos), Direction.DOWN, pos, false);
      oyDPiOVBGKG.vGAMA89H();
      oyDPiOVBGKG.fStlZIvY9lS8(Hand.OFF_HAND, hit);
      oyDPiOVBGKG.vGAMA89H();
      if (QfZy2gWnKInxFPr.player.getMainHandStack().getItem() instanceof BlockItem bi) {
         QfZy2gWnKInxFPr.world.setBlockState(pos, bi.getBlock().getDefaultState(), 3);
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(Render3DEvent event) {
      if (QfZy2gWnKInxFPr.player != null && QfZy2gWnKInxFPr.world != null && (Boolean)this.TJImwK2.get() && this.vGAMA89H != null) {
         if (QfZy2gWnKInxFPr.player.getMainHandStack().getItem() instanceof BlockItem
            || QfZy2gWnKInxFPr.player.getMainHandStack().getItem() instanceof SpawnEggItem) {
            if (QfZy2gWnKInxFPr.world.getBlockState(this.vGAMA89H).isReplaceable()) {
               jU8xXhmkg.fStlZIvY9lS8(event, this.vGAMA89H, Block.getBlockFromItem(QfZy2gWnKInxFPr.player.getMainHandStack().getItem()));
            }
         }
      }
   }
}
