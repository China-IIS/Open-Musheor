package musheor.modules.features;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent.Post;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.BlockPosSetting.Builder;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import musheor.musheor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.World;
import net.minecraft.world.Heightmap.Type;

public class PortalSpawnESP extends Module {
   private final AtomicBoolean TJImwK2 = new AtomicBoolean(false);
   private final List<BlockPos> wSJeYsJcmjNZf = new CopyOnWriteArrayList<>();
   public final Setting<BlockPos> fStlZIvY9lS8 = this.settings
      .getDefaultGroup()
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("Target Position"))
                  .description("Block position to look around for possible / valid portal spawn locations."))
               .defaultValue(new BlockPos(0, 0, 0)))
            .build()
      );
   private final Setting<Integer> i2Nu6XHKic = this.settings
      .getDefaultGroup()
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("radius"))
                  .description("The range at which portal locations are scanned and rendered."))
               .sliderRange(8, 128)
               .defaultValue(32))
            .build()
      );
   private final Setting<ShapeMode> vGAMA89H = this.settings
      .getDefaultGroup()
      .add(
         ((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)new meteordevelopment.meteorclient.settings.EnumSetting.Builder()
                     .name("shape"))
                  .description("How the blocks scheduled for placement are rendered."))
               .defaultValue(ShapeMode.Both))
            .build()
      );
   private final Setting<SettingColor> qhulAUwd = this.settings
      .getDefaultGroup()
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                  .name("line-color"))
               .description("Color of blocks scheduled for placement."))
            .defaultValue(new SettingColor(25, 25, 225, 255))
            .build()
      );
   private final Setting<SettingColor> BrmKXSYhWhqoB1 = this.settings
      .getDefaultGroup()
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                  .name("render-placements-side-color"))
               .description("Side color of blocks scheduled for placement."))
            .defaultValue(new SettingColor(25, 25, 225, 25))
            .build()
      );

   public PortalSpawnESP() {
      super(musheor.AUTOMATION, "portal-spawn-esp", "Client-side scan for where a Nether portal could spawn.");
   }

   public void onDeactivate() {
      this.wSJeYsJcmjNZf.clear();
      this.TJImwK2.set(false);
   }

   @EventHandler
   private void fStlZIvY9lS8(Post event) {
      if (this.mc.player != null && this.mc.world != null) {
         if (this.TJImwK2.compareAndSet(false, true)) {
            new Thread(this::fStlZIvY9lS8, "PortalScanThread").start();
         }
      } else {
         this.wSJeYsJcmjNZf.clear();
         this.TJImwK2.set(false);
      }
   }

   private void fStlZIvY9lS8() {
      World world = this.mc.world;
      assert this.mc.player != null && world != null;
      BlockPos center = (BlockPos)this.fStlZIvY9lS8.get();
      int horizontalRadius = (Integer)this.i2Nu6XHKic.get();
      int yMin = world.getBottomY();
      List<BlockPos> newPortals = new ArrayList<>();

      for (Mutable candidate : BlockPos.iterateInSquare(center, horizontalRadius, Direction.EAST, Direction.SOUTH)) {
         int x = candidate.getX();
         int z = candidate.getZ();
         int surfaceY = world.getTopY(Type.MOTION_BLOCKING, x, z);
         int yMax = Math.min(surfaceY, center.getY() + horizontalRadius);

         for (int y = yMax; y >= yMin; y--) {
            candidate.set(x, y, z);
            if (this.fStlZIvY9lS8(candidate)) {
               newPortals.add(candidate.toImmutable());
               break;
            }
         }
      }

      this.wSJeYsJcmjNZf.clear();
      this.wSJeYsJcmjNZf.addAll(newPortals);
      this.TJImwK2.set(false);
   }

   private boolean fStlZIvY9lS8(Mutable basePos) {
      List<BlockPos> requiredSolidBlocks = new ArrayList<>();
      requiredSolidBlocks.add(basePos);
      requiredSolidBlocks.add(basePos.offset(Direction.NORTH, 1));
      requiredSolidBlocks.add(basePos.offset(Direction.NORTH, 2));
      requiredSolidBlocks.add(basePos.offset(Direction.NORTH, 3));

      for (BlockPos pos : requiredSolidBlocks) {
         assert this.mc.world != null;
         if (!this.mc.world.getBlockState(pos).isSideSolidFullSquare(this.mc.world, pos, Direction.UP)) {
            return false;
         }
      }

      for (BlockPos pos : requiredSolidBlocks) {
         for (int i = 1; i < 5; i++) {
            if (!this.mc.world.getBlockState(pos.up(i)).isAir()) {
               return false;
            }
         }
      }

      return true;
   }

   @EventHandler
   private void fStlZIvY9lS8(Render3DEvent event) {
      for (BlockPos portalBase : this.wSJeYsJcmjNZf) {
         for (int i = 0; i < 5; i++) {
            event.renderer
               .box(portalBase.offset(Direction.NORTH, i), (Color)this.BrmKXSYhWhqoB1.get(), (Color)this.qhulAUwd.get(), (ShapeMode)this.vGAMA89H.get(), 0);
         }
      }
   }
}
