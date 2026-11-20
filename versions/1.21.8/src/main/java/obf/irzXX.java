package obf;

import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.player.Rotations;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import musheor.modules.automation.HighwayBuilder;
import musheor.utils.manager.RotationManager;
import musheor.utils.system.MusheorSystem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.registry.Registries;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class irzXX {
   private static final MinecraftClient fStlZIvY9lS8 = MinecraftClient.getInstance();

   public static int fStlZIvY9lS8(BlockPos[] positions, Block block, Direction side, MusheorSystem.PlacementType type) {
      if (fStlZIvY9lS8.player != null && fStlZIvY9lS8.world != null) {
         U6PQSXqW state = U6PQSXqW.fStlZIvY9lS8();
         int placed = 0;
         boolean canPlace = false;

         for (BlockPos pos : positions) {
            if (fStlZIvY9lS8.world.getBlockState(pos).getBlock() != block
               && BlockUtils.canPlace(pos, true)
               && QfZy2gWnKInxFPr(pos)
               && !state.PVlYDeNipUe().containsKey(pos)) {
               canPlace = true;
               break;
            }
         }

         if (!canPlace) {
            return 0;
         } else {
            boolean silent = (Boolean)MusheorSystem.Manager.silentPlace.get();
            musheor.utils.manager.PathKit.fStlZIvY9lS8(block.asItem(), silent);
            switch (type) {
               case Airplace:
               case AirPlaceSwap:
                  if (type == MusheorSystem.PlacementType.AirPlaceSwap) {
                     fStlZIvY9lS8();
                  }

                  for (BlockPos posx : positions) {
                     if (BlockUtils.canPlace(posx, true) && QfZy2gWnKInxFPr(posx)) {
                        if (!jU8xXhmkg.fStlZIvY9lS8()) {
                           break;
                        }

                        if (!state.PVlYDeNipUe().containsKey(posx)) {
                           state.i2Nu6XHKic(true);
                           fStlZIvY9lS8(
                              type == MusheorSystem.PlacementType.AirPlaceSwap ? Hand.OFF_HAND : Hand.MAIN_HAND, fStlZIvY9lS8(posx, side)
                           );
                           state.PVlYDeNipUe().put(posx, state.pF16zz4NJECfg7());
                           if (block == Blocks.OBSIDIAN) {
                              state.cO1FU7fW();
                           }

                           placed++;
                           fStlZIvY9lS8(block, posx);
                        }
                     }
                  }

                  if (type == MusheorSystem.PlacementType.AirPlaceSwap) {
                     fStlZIvY9lS8();
                  }
                  break;
               case Rotate:
                  for (BlockPos posxx : positions) {
                     Direction placeSide = BlockUtils.getPlaceSide(posxx);
                     if (placeSide != null && BlockUtils.canPlace(posxx, true) && QfZy2gWnKInxFPr(posxx)) {
                        if (jU8xXhmkg.fStlZIvY9lS8()) {
                           state.i2Nu6XHKic(true);
                           BlockUtils.place(posxx, Hand.MAIN_HAND, fStlZIvY9lS8.player.getInventory().selectedSlot, true, 0, false, true, false);
                           fStlZIvY9lS8(block, posxx);
                           placed++;
                        }
                        break;
                     }
                  }
            }

            if (silent) {
               InvUtils.swapBack();
            }

            state.i2Nu6XHKic(false);
            return placed;
         }
      } else {
         return 0;
      }
   }

   public static irzXX.gVOhtBej1x fStlZIvY9lS8(Block block, MusheorSystem.PlacementType type) {
      if (type == null) {
         type = (MusheorSystem.PlacementType)MusheorSystem.Manager.placementType.get();
      }

      if (fStlZIvY9lS8.player != null && fStlZIvY9lS8.world != null && fStlZIvY9lS8.interactionManager != null) {
         Item item = block.asItem();
         boolean silent = (Boolean)MusheorSystem.Manager.silentPlace.get();
         boolean ready = musheor.utils.manager.PathKit.I3psabmOrOI(item);
         irzXX.gVOhtBej1x batch = new irzXX.gVOhtBej1x(block, type, silent, ready, musheor.utils.manager.PathKit.QfZy2gWnKInxFPr(item));
         if (ready && type == MusheorSystem.PlacementType.AirPlaceSwap) {
            fStlZIvY9lS8();
            batch.vGAMA89H = true;
         }

         return batch;
      } else {
         return new irzXX.gVOhtBej1x(block, type, false, false, -1);
      }
   }

   public static void fStlZIvY9lS8(BlockPos pos, Direction direction, MusheorSystem.PlacementType type, boolean silent) {
      if (fStlZIvY9lS8.player != null && fStlZIvY9lS8.world != null) {
         switch (type) {
            case Airplace:
               fStlZIvY9lS8(pos, direction, false, silent);
               break;
            case AirPlaceSwap:
               fStlZIvY9lS8(pos, direction, true, silent);
               break;
            case Rotate:
               fStlZIvY9lS8(pos);
         }
      }
   }

   public static boolean fStlZIvY9lS8(BlockPos pos, Direction direction, boolean requireSwap, boolean silent) {
      return fStlZIvY9lS8(pos, direction, Block.getBlockFromItem(fStlZIvY9lS8.player.getMainHandStack().getItem()), requireSwap, silent);
   }

   public static boolean fStlZIvY9lS8(BlockPos pos, Direction direction, Block block, boolean requireSwap, boolean silent) {
      if (fStlZIvY9lS8.player != null && fStlZIvY9lS8.getNetworkHandler() != null && fStlZIvY9lS8.interactionManager != null) {
         InvUtils.swap(musheor.utils.manager.PathKit.QfZy2gWnKInxFPr(block.asItem()), silent);
         if (requireSwap) {
            fStlZIvY9lS8();
         }

         fStlZIvY9lS8(requireSwap ? Hand.OFF_HAND : Hand.MAIN_HAND, fStlZIvY9lS8(pos, direction));
         if (requireSwap) {
            fStlZIvY9lS8();
         }

         if (silent) {
            InvUtils.swapBack();
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean fStlZIvY9lS8(BlockPos pos) {
      if (fStlZIvY9lS8.player != null && fStlZIvY9lS8.getNetworkHandler() != null && fStlZIvY9lS8.interactionManager != null) {
         Direction side = BlockUtils.getPlaceSide(pos);
         return side == null
            ? false
            : BlockUtils.place(pos, Hand.MAIN_HAND, fStlZIvY9lS8.player.getInventory().selectedSlot, true, 0, false, true, false);
      } else {
         return false;
      }
   }

   private static void fStlZIvY9lS8(Hand hand, BlockHitResult hit) {
      fStlZIvY9lS8.interactionManager.sendSequencedPacket(fStlZIvY9lS8.world, sequence -> new PlayerInteractBlockC2SPacket(hand, hit, sequence));
   }

   private static BlockHitResult fStlZIvY9lS8(BlockPos pos, Direction direction) {
      return new BlockHitResult(Vec3d.ofCenter(pos), direction, pos, false);
   }

   private static void fStlZIvY9lS8() {
      if (fStlZIvY9lS8.player != null) {
         fStlZIvY9lS8.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN));
         ItemStack mainHand = fStlZIvY9lS8.player.getMainHandStack();
         ItemStack offHand = fStlZIvY9lS8.player.getOffHandStack();
         fStlZIvY9lS8.player.getInventory().setStack(fStlZIvY9lS8.player.getInventory().selectedSlot, offHand);
         fStlZIvY9lS8.player.getInventory().setStack(40, mainHand);
      }
   }

   private static boolean QfZy2gWnKInxFPr(BlockPos pos) {
      if (fStlZIvY9lS8.player == null) {
         return false;
      } else {
         double rangeSq = (Double)MusheorSystem.Manager.placementRange.get();
         return fStlZIvY9lS8.player.squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ()) <= rangeSq * rangeSq;
      }
   }

   private static void fStlZIvY9lS8(Block block, BlockPos pos) {
      if (HighwayBuilder.TJImwK2 != null && (Boolean)MusheorSystem.Manager.enableChatDebugging.get()) {
         MusheorSystem.debug(
            "placed §5%s §rat %s, %s, %s",
            Registries.ITEM.getId(block.asItem()).toString(),
            pos.getX(),
            pos.getY(),
            pos.getZ()
         );
      }
   }

   public static final class gVOhtBej1x implements AutoCloseable {
      private final Block fStlZIvY9lS8;
      private final MusheorSystem.PlacementType QfZy2gWnKInxFPr;
      private final boolean TJImwK2;
      private final boolean wSJeYsJcmjNZf;
      private final int i2Nu6XHKic;
      private boolean vGAMA89H;
      private boolean qhulAUwd;
      private boolean BrmKXSYhWhqoB1;
      private int TXWyNb;

      private gVOhtBej1x(Block block, MusheorSystem.PlacementType type, boolean silent, boolean ready, int slot) {
         this.fStlZIvY9lS8 = block;
         this.QfZy2gWnKInxFPr = type;
         this.TJImwK2 = silent;
         this.wSJeYsJcmjNZf = ready;
         this.i2Nu6XHKic = slot;
      }

      public boolean fStlZIvY9lS8() {
         return this.wSJeYsJcmjNZf;
      }

      public Hand QfZy2gWnKInxFPr() {
         return this.vGAMA89H ? Hand.OFF_HAND : Hand.MAIN_HAND;
      }

      public boolean fStlZIvY9lS8(BlockPos pos, BlockHitResult hit, boolean allowRotate) {
         if (this.wSJeYsJcmjNZf
            && !this.qhulAUwd
            && !this.BrmKXSYhWhqoB1
            && irzXX.fStlZIvY9lS8.player != null
            && irzXX.fStlZIvY9lS8.world != null
            && irzXX.fStlZIvY9lS8.interactionManager != null) {
            if (this.QfZy2gWnKInxFPr == MusheorSystem.PlacementType.Rotate && allowRotate && this.i2Nu6XHKic >= 0 && !RotationManager.isActive()) {
               Vec3d target = hit.getPos();
               Rotations.rotate(Rotations.getYaw(target), Rotations.getPitch(target), () -> {
                  InvUtils.swap(this.i2Nu6XHKic, this.TJImwK2);
                  BlockUtils.interact(hit, Hand.MAIN_HAND, true);
                  if (this.TJImwK2) {
                     InvUtils.swapBack();
                  }
               });
               this.BrmKXSYhWhqoB1 = true;
            } else {
               irzXX.fStlZIvY9lS8(this.QfZy2gWnKInxFPr(), hit);
            }

            this.TXWyNb++;
            irzXX.fStlZIvY9lS8(this.fStlZIvY9lS8, pos);
            return true;
         } else {
            return false;
         }
      }

      @Override
      public void close() {
         if (!this.qhulAUwd) {
            this.qhulAUwd = true;
            if (this.vGAMA89H) {
               irzXX.fStlZIvY9lS8();
               this.vGAMA89H = false;
            }

            if (this.TJImwK2) {
               InvUtils.swapBack();
            }
         }
      }
   }
}
