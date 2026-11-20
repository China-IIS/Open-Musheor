package musheor.modules.features;

import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent.Post;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.orbit.EventHandler;
import musheor.musheor;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.block.entity.TrappedChestBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class DepthInteract extends Module {
   public static DepthInteract INSTANCE;
   private final MinecraftClient QfZy2gWnKInxFPr = MinecraftClient.getInstance();
   public BlockPos fStlZIvY9lS8 = null;

   public DepthInteract() {
      super(musheor.MAIN, "depth-interact", "Interact with containers through blocks.");
      INSTANCE = this;
   }

   public void onDeactivate() {
      this.fStlZIvY9lS8 = null;
   }

   @EventHandler
   private void fStlZIvY9lS8(Post event) {
      if (this.QfZy2gWnKInxFPr.player != null && this.QfZy2gWnKInxFPr.world != null) {
         this.fStlZIvY9lS8 = this.fStlZIvY9lS8(this.QfZy2gWnKInxFPr.player.getBlockInteractionRange());
      }
   }

   private BlockPos fStlZIvY9lS8(double maxDistance) {
      Vec3d start = this.QfZy2gWnKInxFPr.player.getCameraPosVec(1.0F);
      Vec3d look = this.QfZy2gWnKInxFPr.player.getRotationVec(1.0F);

      for (double d = 0.0; d <= maxDistance; d += 0.1) {
         Vec3d point = start.add(look.multiply(d));
         BlockPos pos = BlockPos.ofFloored(point);
         if (this.QfZy2gWnKInxFPr.world.isChunkLoaded(pos)) {
            BlockEntity be = this.QfZy2gWnKInxFPr.world.getBlockEntity(pos);
            if (this.fStlZIvY9lS8(be)) {
               return pos;
            }
         }
      }

      return null;
   }

   private boolean fStlZIvY9lS8(BlockEntity be) {
      return be instanceof ChestBlockEntity || be instanceof TrappedChestBlockEntity || be instanceof BarrelBlockEntity || be instanceof ShulkerBoxBlockEntity || be instanceof EnderChestBlockEntity;
   }

   @EventHandler
   private void fStlZIvY9lS8(Render3DEvent event) {
      if (this.fStlZIvY9lS8 != null && this.QfZy2gWnKInxFPr.world != null) {
         event.renderer.box(this.fStlZIvY9lS8, Color.CYAN, Color.CYAN, ShapeMode.Lines, 0);
      }
   }
}
