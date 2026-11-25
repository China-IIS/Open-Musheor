package musheor.modules.tech;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.BoolSetting.Builder;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import musheor.utils.oyDPiOVBGKG;
import musheor.utils.manager.pvfA15fQSVM;
import musheor.utils.system.MusheorSystem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import obf.U6PQSXqW;
import obf.irzXX;
import obf.jU8xXhmkg;

public class AutoPortal extends Module {
   private static final MinecraftClient fStlZIvY9lS8 = MinecraftClient.getInstance();
   private static final SettingColor QfZy2gWnKInxFPr = new SettingColor(255, 45, 45, 220);
   private static final SettingColor TJImwK2 = new SettingColor(255, 45, 45, 60);
   private final Setting<Boolean> wSJeYsJcmjNZf = this.settings
      .getDefaultGroup()
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("auto-disable")).description("Automatically disables the module when a portal is built"))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> i2Nu6XHKic = this.settings
      .getDefaultGroup()
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("pause-on-input"))
                  .description("Pauses the building process when you are moving, sneaking or jumping"))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> vGAMA89H = this.settings
      .getDefaultGroup()
      .add(((Builder)((Builder)((Builder)new Builder().name("render-portal")).description("Renders the portal's position")).defaultValue(true)).build());
   private final Setting<SettingColor> qhulAUwd = this.settings
      .getDefaultGroup()
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                     .name("render-color"))
                  .defaultValue(new SettingColor(0, 225, 255, 200))
                  .description("Custom color for rendering (lines / wireframe)"))
               .visible(this.vGAMA89H::get))
            .build()
      );
   private boolean BrmKXSYhWhqoB1;
   private AutoPortal.Phase TXWyNb;
   private final List<BlockPos> I3psabmOrOI = new ArrayList<>();
   private final List<BlockPos> duJ6sPeetAcBh6P = new ArrayList<>();
   private BlockPos j376sIQ;
   private int V7zKJ4n6D;
   private int voxTwgNzyrpaAnP;

   public AutoPortal() {
      super(musheor.musheor.MAIN, "auto-portal", "Automatically builds and lights a nether portal");
   }

   public void onActivate() {
      this.BrmKXSYhWhqoB1 = false;
      this.TXWyNb = AutoPortal.Phase.PLACING;
      this.I3psabmOrOI.clear();
      this.duJ6sPeetAcBh6P.clear();
      this.j376sIQ = null;
      this.V7zKJ4n6D = 0;
      this.voxTwgNzyrpaAnP = 0;
      U6PQSXqW.fStlZIvY9lS8().PVlYDeNipUe().clear();
   }

   public void onDeactivate() {
      this.I3psabmOrOI.clear();
      this.duJ6sPeetAcBh6P.clear();
      this.j376sIQ = null;
   }

   @EventHandler
   private void fStlZIvY9lS8(Pre event) {
      if (fStlZIvY9lS8.player != null && fStlZIvY9lS8.world != null && fStlZIvY9lS8.interactionManager != null) {
         if (!this.BrmKXSYhWhqoB1) {
            this.vGAMA89H();
         }

         U6PQSXqW.fStlZIvY9lS8()
            .PVlYDeNipUe()
            .entrySet()
            .removeIf(
               entry -> {
                  BlockState targetState = fStlZIvY9lS8.world.getBlockState(entry.getKey());
                  return targetState != null && fStlZIvY9lS8.world.getBlockState(entry.getKey()).getBlock() == Blocks.OBSIDIAN
                     ? true
                     : this.voxTwgNzyrpaAnP - entry.getValue() > (Integer)MusheorSystem.Manager.placementTimeout.get();
               }
            );
         if (this.V7zKJ4n6D > 0) {
            this.V7zKJ4n6D--;
         }

         if (!(Boolean)this.i2Nu6XHKic.get() || !this.i2Nu6XHKic()) {
            if (!this.BrmKXSYhWhqoB1) {
               int obsidianCount = pvfA15fQSVM.vGAMA89H(Items.OBSIDIAN);
               if (obsidianCount < 10) {
                  this.error("Not enough obsidian (need %d, have %d), disabling.", new Object[]{10, obsidianCount});
                  this.toggle();
                  return;
               }

               if (!InvUtils.find(stack -> stack.getItem() == Items.FLINT_AND_STEEL).found()) {
                  this.error("No flint and steel found in inventory, disabling.", new Object[0]);
                  this.toggle();
                  return;
               }

               this.BrmKXSYhWhqoB1 = true;
               this.TXWyNb = AutoPortal.Phase.PLACING;
               this.info("Position locked, building portal...", new Object[0]);
            }

            switch (this.TXWyNb) {
               case PLACING:
                  this.fStlZIvY9lS8(this.voxTwgNzyrpaAnP++);
                  break;
               case LIGHTING:
                  this.fStlZIvY9lS8();
                  break;
               case DONE:
                  this.QfZy2gWnKInxFPr();
            }
         }
      }
   }

   private void fStlZIvY9lS8(int currentTick) {
      boolean anyPlaceable = false;

      for (BlockPos pos : this.I3psabmOrOI) {
         if (fStlZIvY9lS8.world.getBlockState(pos).getBlock() != Blocks.OBSIDIAN
            && fStlZIvY9lS8.world.getBlockState(pos).isReplaceable()
            && oyDPiOVBGKG.TJImwK2(pos)) {
            anyPlaceable = true;
            break;
         }
      }

      if (!anyPlaceable) {
         if (this.TJImwK2()) {
            this.TXWyNb = AutoPortal.Phase.LIGHTING;
            this.V7zKJ4n6D = (Integer)MusheorSystem.Manager.swapDelay.get();
         }
      } else if (this.V7zKJ4n6D <= 0) {
         FindItemResult inHotbar = InvUtils.findInHotbar(stack -> stack.getItem() == Items.OBSIDIAN);
         if (!inHotbar.found()) {
            pvfA15fQSVM.fStlZIvY9lS8(Items.OBSIDIAN);
            this.V7zKJ4n6D = (Integer)MusheorSystem.Manager.swapDelay.get();
         } else {
            pvfA15fQSVM.fStlZIvY9lS8(Items.OBSIDIAN, (Boolean)MusheorSystem.Manager.silentPlace.get());

            for (BlockPos pos : this.I3psabmOrOI) {
               if (!jU8xXhmkg.fStlZIvY9lS8()) {
                  break;
               }

               if (!U6PQSXqW.fStlZIvY9lS8().PVlYDeNipUe().containsKey(pos)
                  && fStlZIvY9lS8.world.getBlockState(pos).getBlock() != Blocks.OBSIDIAN
                  && fStlZIvY9lS8.world.getBlockState(pos).isReplaceable()
                  && oyDPiOVBGKG.TJImwK2(pos)) {
                  U6PQSXqW.fStlZIvY9lS8().PVlYDeNipUe().put(pos, currentTick);
                  irzXX.fStlZIvY9lS8(pos, Direction.DOWN, U6PQSXqW.fStlZIvY9lS8().QfZy2gWnKInxFPr(), (Boolean)MusheorSystem.Manager.silentPlace.get());
               }
            }
         }
      }
   }

   private void fStlZIvY9lS8() {
      if (this.j376sIQ != null && oyDPiOVBGKG.TJImwK2(this.j376sIQ)) {
         if (this.V7zKJ4n6D <= 0 && jU8xXhmkg.fStlZIvY9lS8()) {
            FindItemResult inHotbar = InvUtils.findInHotbar(stack -> stack.getItem() == Items.FLINT_AND_STEEL);
            if (inHotbar.found()) {
               InvUtils.swap(inHotbar.slot(), false);
               BlockHitResult hit = new BlockHitResult(Vec3d.ofCenter(this.j376sIQ).add(0.0, 0.5, 0.0), Direction.UP, this.j376sIQ, false);
               fStlZIvY9lS8.interactionManager
                  .sendSequencedPacket(fStlZIvY9lS8.world, sequence -> new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, hit, sequence));
               this.TXWyNb = AutoPortal.Phase.DONE;
            } else if (!InvUtils.find(stack -> stack.getItem() == Items.FLINT_AND_STEEL).found()) {
               this.error("Lost flint and steel during build, disabling.", new Object[0]);
               this.toggle();
            } else {
               pvfA15fQSVM.fStlZIvY9lS8(Items.FLINT_AND_STEEL);
               this.V7zKJ4n6D = (Integer)MusheorSystem.Manager.swapDelay.get();
            }
         }
      }
   }

   private void QfZy2gWnKInxFPr() {
      if (this.wSJeYsJcmjNZf()) {
         this.info("Portal built successfully!", new Object[0]);
         if ((Boolean)this.wSJeYsJcmjNZf.get()) {
            this.toggle();
         }
      } else {
         this.TXWyNb = AutoPortal.Phase.LIGHTING;
      }
   }

   private boolean TJImwK2() {
      for (BlockPos pos : this.I3psabmOrOI) {
         if (fStlZIvY9lS8.world.getBlockState(pos).getBlock() != Blocks.OBSIDIAN) {
            return false;
         }
      }

      return true;
   }

   private boolean wSJeYsJcmjNZf() {
      for (BlockPos pos : this.duJ6sPeetAcBh6P) {
         if (fStlZIvY9lS8.world.getBlockState(pos).getBlock() == Blocks.NETHER_PORTAL) {
            return true;
         }
      }

      return false;
   }

   private boolean i2Nu6XHKic() {
      return fStlZIvY9lS8.options.forwardKey.isPressed()
         || fStlZIvY9lS8.options.backKey.isPressed()
         || fStlZIvY9lS8.options.leftKey.isPressed()
         || fStlZIvY9lS8.options.rightKey.isPressed()
         || fStlZIvY9lS8.options.jumpKey.isPressed()
         || fStlZIvY9lS8.options.sneakKey.isPressed();
   }

   private Direction fStlZIvY9lS8(float yaw) {
      float n = (yaw % 360.0F + 360.0F) % 360.0F;
      if (n >= 315.0F || n < 45.0F) {
         return Direction.SOUTH;
      } else if (n < 135.0F) {
         return Direction.WEST;
      } else {
         return n < 225.0F ? Direction.NORTH : Direction.EAST;
      }
   }

   private int QfZy2gWnKInxFPr(float pitch) {
      float clamped = Math.max(-45.0F, Math.min(45.0F, pitch));
      int step = Math.round(-clamped / 9.0F);
      return Math.max(-5, Math.min(5, step));
   }

   private void vGAMA89H() {
      this.I3psabmOrOI.clear();
      this.duJ6sPeetAcBh6P.clear();
      this.j376sIQ = null;
      BlockPos foot = fStlZIvY9lS8.player.getBlockPos();
      Direction facing = this.fStlZIvY9lS8(fStlZIvY9lS8.player.getYaw());
      int yOffset = this.QfZy2gWnKInxFPr(fStlZIvY9lS8.player.getPitch());
      int fx = facing.getOffsetX();
      int fz = facing.getOffsetZ();
      int rx = -fz;
      int rz = fx;
      int planeX = foot.getX() + fx * 2;
      int planeZ = foot.getZ() + fz * 2;
      int baseY = foot.getY() + yOffset;

      for (int h = 0; h < 5; h++) {
         for (int w = -1; w <= 2; w++) {
            BlockPos pos = new BlockPos(planeX + rx * w, baseY + h, planeZ + rz * w);
            boolean isCorner = (h == 0 || h == 4) && (w == -1 || w == 2);
            boolean isInterior = h >= 1 && h <= 3 && w >= 0 && w <= 1;
            if (!isCorner) {
               if (isInterior) {
                  this.duJ6sPeetAcBh6P.add(pos);
               } else {
                  this.I3psabmOrOI.add(pos);
               }
            }
         }
      }

      for (BlockPos pos : this.I3psabmOrOI) {
         if (pos.getY() == baseY) {
            this.j376sIQ = pos;
            break;
         }
      }
   }

   private boolean qhulAUwd() {
      for (BlockPos pos : this.I3psabmOrOI) {
         BlockState state = fStlZIvY9lS8.world.getBlockState(pos);
         if (state.getBlock() != Blocks.OBSIDIAN && !state.isReplaceable()) {
            return true;
         }
      }

      for (BlockPos pos : this.duJ6sPeetAcBh6P) {
         BlockState state = fStlZIvY9lS8.world.getBlockState(pos);
         if (state.getBlock() != Blocks.NETHER_PORTAL && state.getBlock() != Blocks.FIRE && !state.isReplaceable()) {
            return true;
         }
      }

      return false;
   }

   @EventHandler
   private void fStlZIvY9lS8(Render3DEvent event) {
      if (fStlZIvY9lS8.player != null && fStlZIvY9lS8.world != null) {
         if ((Boolean)this.vGAMA89H.get()) {
            if (!this.I3psabmOrOI.isEmpty() || !this.duJ6sPeetAcBh6P.isEmpty()) {
               boolean blocked = this.qhulAUwd();
               SettingColor lineColor = blocked ? QfZy2gWnKInxFPr : (SettingColor)this.qhulAUwd.get();
               SettingColor sideColor = blocked ? TJImwK2 : (SettingColor)this.qhulAUwd.get();
               List<BlockPos> all = new ArrayList<>(this.I3psabmOrOI.size() + this.duJ6sPeetAcBh6P.size());
               all.addAll(this.I3psabmOrOI);
               all.addAll(this.duJ6sPeetAcBh6P);
               musheor.utils.jU8xXhmkg.fStlZIvY9lS8(event, all, lineColor, sideColor, ShapeMode.Lines);
            }
         }
      }
   }

   private enum Phase {
      PLACING,
      LIGHTING,
      DONE;

      Phase() {
      }
   }
}
