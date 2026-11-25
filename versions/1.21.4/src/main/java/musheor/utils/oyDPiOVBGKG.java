package musheor.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import musheor.compat.VersionHelper;
import musheor.modules.automation.HighwayBuilder;
import musheor.modules.automation.KekNuker;
import musheor.utils.system.MusheorSystem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.VineBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.World;

public class oyDPiOVBGKG {
   private static final MinecraftClient QfZy2gWnKInxFPr = MinecraftClient.getInstance();

   public oyDPiOVBGKG() {
   }

   public static boolean fStlZIvY9lS8(Block block) {
      if (QfZy2gWnKInxFPr.player != null && QfZy2gWnKInxFPr.world != null) {
         BlockPos posBelow = QfZy2gWnKInxFPr.player.getBlockPos().down();
         return QfZy2gWnKInxFPr.world.getBlockState(posBelow).getBlock() == block;
      } else {
         return false;
      }
   }

   public static BlockPos fStlZIvY9lS8(oyDPiOVBGKG.gVOhtBej1x dir) {
      int x = QfZy2gWnKInxFPr.player.getBlockX();
      int y = QfZy2gWnKInxFPr.player.getBlockY();
      int z = QfZy2gWnKInxFPr.player.getBlockZ();
      int dx = 0;
      int dz = 0;
      switch (dir) {
         case SOUTH:
            dz = -2;
            break;
         case SOUTH_WEST:
            dx = 2;
            dz = -2;
            break;
         case WEST:
            dx = 2;
            break;
         case NORTH_WEST:
            dx = 2;
            dz = 2;
            break;
         case NORTH:
            dz = 2;
            break;
         case NORTH_EAST:
            dx = -2;
            dz = 2;
            break;
         case EAST:
            dx = -2;
            break;
         case SOUTH_EAST:
            dx = -2;
            dz = -2;
      }

      return new BlockPos(x + dx, y, z + dz);
   }

   public static boolean fStlZIvY9lS8() {
      if (QfZy2gWnKInxFPr.player != null && QfZy2gWnKInxFPr.world != null) {
         obf.pvfA15fQSVM.gVOhtBej1x detected = QfZy2gWnKInxFPr();
         if (detected != null) {
            return obf.pvfA15fQSVM.QfZy2gWnKInxFPr(QfZy2gWnKInxFPr.player.getBlockPos(), detected);
         }

         int py = obf.U6PQSXqW.fStlZIvY9lS8().BrmKXSYhWhqoB1() != null ? obf.U6PQSXqW.fStlZIvY9lS8().BrmKXSYhWhqoB1() : QfZy2gWnKInxFPr.player.getBlockY();
         BlockPos below = new BlockPos(QfZy2gWnKInxFPr.player.getBlockX(), py - 1, QfZy2gWnKInxFPr.player.getBlockZ());
         return QfZy2gWnKInxFPr.world.getBlockState(below).getBlock() == Blocks.OBSIDIAN;
      } else {
         return false;
      }
   }

   public static obf.pvfA15fQSVM.gVOhtBej1x QfZy2gWnKInxFPr() {
      if (QfZy2gWnKInxFPr.player == null) {
         return null;
      }

      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      obf.pvfA15fQSVM.gVOhtBej1x detected = state.npfimrDr0hky();
      if (detected != null) {
         return detected;
      }

      oyDPiOVBGKG.gVOhtBej1x dir = state.TJImwK2() != null ? state.TJImwK2() : duJ6sPeetAcBh6P();
      if (dir == null) {
         return null;
      }

      int py = state.BrmKXSYhWhqoB1() != null ? state.BrmKXSYhWhqoB1() : QfZy2gWnKInxFPr.player.getBlockY();
      return obf.pvfA15fQSVM.fStlZIvY9lS8(QfZy2gWnKInxFPr.player.getBlockX(), QfZy2gWnKInxFPr.player.getBlockZ(), py, dir);
   }

   public static BlockPos TJImwK2() {
      obf.pvfA15fQSVM.gVOhtBej1x detected = QfZy2gWnKInxFPr();
      if (detected != null) {
         return fStlZIvY9lS8(detected);
      }

      if (QfZy2gWnKInxFPr.player == null) {
         return null;
      }

      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      oyDPiOVBGKG.gVOhtBej1x dir = state.TJImwK2() != null ? state.TJImwK2() : duJ6sPeetAcBh6P();
      if (dir == null) {
         return null;
      }

      int ay = state.BrmKXSYhWhqoB1() != null ? state.BrmKXSYhWhqoB1() : QfZy2gWnKInxFPr.player.getBlockY();
      if (dir != oyDPiOVBGKG.gVOhtBej1x.EAST && dir != oyDPiOVBGKG.gVOhtBej1x.WEST) {
         if (dir != oyDPiOVBGKG.gVOhtBej1x.NORTH && dir != oyDPiOVBGKG.gVOhtBej1x.SOUTH) {
            return null;
         } else {
            return state.I3psabmOrOI() == null ? null : new BlockPos((int)Math.floor(state.I3psabmOrOI()), ay, QfZy2gWnKInxFPr.player.getBlockZ());
         }
      } else {
         return state.duJ6sPeetAcBh6P() == null ? null : new BlockPos(QfZy2gWnKInxFPr.player.getBlockX(), ay, (int)Math.floor(state.duJ6sPeetAcBh6P()));
      }
   }

   public static BlockPos fStlZIvY9lS8(obf.pvfA15fQSVM.gVOhtBej1x detected) {
      if (QfZy2gWnKInxFPr.player != null && detected != null) {
         int ay = obf.U6PQSXqW.fStlZIvY9lS8().BrmKXSYhWhqoB1() != null ? obf.U6PQSXqW.fStlZIvY9lS8().BrmKXSYhWhqoB1() : QfZy2gWnKInxFPr.player.getBlockY();
         int px = QfZy2gWnKInxFPr.player.getBlockX();
         int pz = QfZy2gWnKInxFPr.player.getBlockZ();
         boolean cardinal = detected.i2Nu6XHKic == oyDPiOVBGKG.gVOhtBej1x.NORTH
            || detected.i2Nu6XHKic == oyDPiOVBGKG.gVOhtBej1x.SOUTH
            || detected.i2Nu6XHKic == oyDPiOVBGKG.gVOhtBej1x.EAST
            || detected.i2Nu6XHKic == oyDPiOVBGKG.gVOhtBej1x.WEST;
         if (cardinal) {
            boolean travelX = detected.i2Nu6XHKic == oyDPiOVBGKG.gVOhtBej1x.EAST || detected.i2Nu6XHKic == oyDPiOVBGKG.gVOhtBej1x.WEST;
            int axis = (int)detected.qhulAUwd;
            return travelX ? new BlockPos(px, ay, axis) : new BlockPos(axis, ay, pz);
         }

         boolean isNESW = detected.i2Nu6XHKic == oyDPiOVBGKG.gVOhtBej1x.NORTH_EAST || detected.i2Nu6XHKic == oyDPiOVBGKG.gVOhtBej1x.SOUTH_WEST;
         int K = (int)detected.qhulAUwd;
         int tx;
         int tz;
         if (isNESW) {
            tx = (int)Math.round((px - pz + K) / 2.0);
            tz = K - tx;
         } else {
            tx = (int)Math.round((px + pz + K) / 2.0);
            tz = tx - K;
         }

         return new BlockPos(tx, ay, tz);
      } else {
         return null;
      }
   }

   public static void wSJeYsJcmjNZf() {
      if (QfZy2gWnKInxFPr.player != null && QfZy2gWnKInxFPr.world != null) {
         obf.pvfA15fQSVM.gVOhtBej1x detected = QfZy2gWnKInxFPr();
         if (detected != null) {
            BlockPos lane = fStlZIvY9lS8(detected);
            if (lane != null) {
               if (!QfZy2gWnKInxFPr.player.getBlockPos().equals(lane)) {
                  obf.GyhoRo.fStlZIvY9lS8(lane);
                  MusheorSystem.debug("Returning to the %s lane...", detected.QfZy2gWnKInxFPr);
               }

               return;
            }
         }

         BlockPos playerPos = QfZy2gWnKInxFPr.player.getBlockPos();
         int py = obf.U6PQSXqW.fStlZIvY9lS8().BrmKXSYhWhqoB1() != null ? obf.U6PQSXqW.fStlZIvY9lS8().BrmKXSYhWhqoB1() : playerPos.getY();
         BlockPos nearest = null;
         double nearestDistSq = Double.MAX_VALUE;
         int range = 20;

         for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
               BlockPos pos = new BlockPos(playerPos.getX() + x, py, playerPos.getZ() + z);
               if (QfZy2gWnKInxFPr.world.getBlockState(pos.down()).getBlock() == Blocks.OBSIDIAN) {
                  double distSq = pos.getSquaredDistance(playerPos);
                  if (distSq < nearestDistSq) {
                     nearestDistSq = distSq;
                     nearest = pos;
                  }
               }
            }
         }

         if (nearest != null) {
            obf.GyhoRo.fStlZIvY9lS8(nearest);
            MusheorSystem.debug("Going back onto the highway...");
         }
      }
   }

   public static boolean fStlZIvY9lS8(BlockPos pos) {
      return Arrays.stream(Direction.values())
         .filter(dir -> dir != Direction.DOWN)
         .anyMatch(dir -> QfZy2gWnKInxFPr.world.getBlockState(pos.offset(dir)).isLiquid());
   }

   public static void i2Nu6XHKic() {
      if (QfZy2gWnKInxFPr.player != null && QfZy2gWnKInxFPr.world != null) {
         if (obf.U6PQSXqW.fStlZIvY9lS8().zbN84Osn9p5lD() != null) {
            if (QfZy2gWnKInxFPr.world.getFluidState(obf.U6PQSXqW.fStlZIvY9lS8().zbN84Osn9p5lD()).getFluid() != Fluids.LAVA) {
               obf.GyhoRo.wSJeYsJcmjNZf();
               obf.U6PQSXqW.fStlZIvY9lS8().TJImwK2((BlockPos)null);
            } else {
               obf.GyhoRo.fStlZIvY9lS8(obf.U6PQSXqW.fStlZIvY9lS8().zbN84Osn9p5lD().withY(obf.U6PQSXqW.fStlZIvY9lS8().BrmKXSYhWhqoB1()));
            }
         } else if (obf.U6PQSXqW.fStlZIvY9lS8().ZlNrrNcv3Ylc() != null) {
            if (QfZy2gWnKInxFPr.player.getBlockPos().equals(obf.U6PQSXqW.fStlZIvY9lS8().ZlNrrNcv3Ylc())) {
               obf.GyhoRo.wSJeYsJcmjNZf();
               obf.U6PQSXqW.fStlZIvY9lS8().wSJeYsJcmjNZf(null);
            } else {
               obf.GyhoRo.fStlZIvY9lS8(obf.U6PQSXqW.fStlZIvY9lS8().ZlNrrNcv3Ylc());
            }
         } else {
            if (HighwayBuilder.TXWyNb() == HighwayBuilder.HighwayType.Cardinal) {
               for (BlockPos pos : musheor.utils.gVOhtBej1x.TJImwK2()) {
                  if (obf.GyhoRo.fStlZIvY9lS8()) {
                     return;
                  }

                  if (QfZy2gWnKInxFPr.world.getFluidState(pos).getFluid() != Fluids.FLOWING_LAVA
                     && QfZy2gWnKInxFPr.world.getFluidState(pos).getFluid() == Fluids.LAVA) {
                     MusheorSystem.debug("Found obstructing lava ahead, removing...");
                     irzXX.fStlZIvY9lS8(false);
                     obf.U6PQSXqW.fStlZIvY9lS8().TJImwK2(pos);
                     obf.U6PQSXqW.fStlZIvY9lS8().wSJeYsJcmjNZf(QfZy2gWnKInxFPr.player.getBlockPos().toImmutable());
                     return;
                  }
               }
            }

            if (HighwayBuilder.TXWyNb() == HighwayBuilder.HighwayType.Diagonal) {
               for (BlockPos pos : musheor.utils.gVOhtBej1x.wSJeYsJcmjNZf()) {
                  if (obf.GyhoRo.fStlZIvY9lS8()) {
                     return;
                  }

                  if (QfZy2gWnKInxFPr.world.getFluidState(pos).getFluid() != Fluids.FLOWING_LAVA
                     && QfZy2gWnKInxFPr.world.getFluidState(pos).getFluid() == Fluids.LAVA) {
                     MusheorSystem.debug("Found obstructing lava ahead, removing...");
                     irzXX.fStlZIvY9lS8(false);
                     obf.U6PQSXqW.fStlZIvY9lS8().TJImwK2(pos);
                     obf.U6PQSXqW.fStlZIvY9lS8().wSJeYsJcmjNZf(QfZy2gWnKInxFPr.player.getBlockPos().toImmutable());
                     return;
                  }
               }
            }
         }
      }
   }

   public static List<BlockPos> fStlZIvY9lS8(Entity entity) {
      assert QfZy2gWnKInxFPr.world != null;
      List<BlockPos> blocks = new ArrayList<>();
      Box box = entity.getBoundingBox();
      int y = (int)Math.floor(box.minY - 0.35);
      int minX = (int)Math.floor(box.minX);
      int maxX = (int)Math.floor(box.maxX);
      int minZ = (int)Math.floor(box.minZ);
      int maxZ = (int)Math.floor(box.maxZ);
      Mutable pos = new Mutable();

      for (int x = minX; x <= maxX; x++) {
         for (int z = minZ; z <= maxZ; z++) {
            pos.set(x, y, z);
            BlockState state = QfZy2gWnKInxFPr.world.getBlockState(pos);
            if (!state.isAir() && !state.getCollisionShape(QfZy2gWnKInxFPr.world, pos).isEmpty()) {
               blocks.add(pos.toImmutable());
            }
         }
      }

      return blocks;
   }

   public static Entity fStlZIvY9lS8(World world, BlockPos pos) {
      Box box = new Box(pos);
      List<Entity> entities = world.getEntitiesByClass(Entity.class, box, e -> true);
      return entities.isEmpty() ? null : entities.getFirst();
   }

   public static boolean fStlZIvY9lS8(BlockPos pos, Direction direction) {
      if (QfZy2gWnKInxFPr.player != null && QfZy2gWnKInxFPr.getNetworkHandler() != null && QfZy2gWnKInxFPr.interactionManager != null) {
         vGAMA89H();
         fStlZIvY9lS8(Hand.OFF_HAND, QfZy2gWnKInxFPr(pos, direction));
         vGAMA89H();
         return true;
      } else {
         return false;
      }
   }

   public static BlockHitResult QfZy2gWnKInxFPr(BlockPos pos, Direction direction) {
      return new BlockHitResult(Vec3d.ofCenter(pos), direction, pos, false);
   }

   public static void fStlZIvY9lS8(Hand hand, BlockHitResult hit) {
      QfZy2gWnKInxFPr.interactionManager.sendSequencedPacket(QfZy2gWnKInxFPr.world, sequence -> new PlayerInteractBlockC2SPacket(hand, hit, sequence));
   }

   public static void vGAMA89H() {
      if (QfZy2gWnKInxFPr.player != null && QfZy2gWnKInxFPr.world != null) {
         QfZy2gWnKInxFPr.interactionManager
            .sendSequencedPacket(QfZy2gWnKInxFPr.world, sequence -> new PlayerActionC2SPacket(Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN));
         ItemStack mainHandStack = QfZy2gWnKInxFPr.player.getMainHandStack();
         ItemStack offHandStack = QfZy2gWnKInxFPr.player.getOffHandStack();
         QfZy2gWnKInxFPr.player.getInventory().setStack(QfZy2gWnKInxFPr.player.getInventory().selectedSlot, offHandStack);
         QfZy2gWnKInxFPr.player.getInventory().setStack(40, mainHandStack);
      }
   }

   public static boolean fStlZIvY9lS8(Item item, BlockPos pos, Direction direction) {
      if (!BlockUtils.canPlaceBlock(pos, true, Block.getBlockFromItem(item))) {
         return false;
      }

      boolean silent = (Boolean)MusheorSystem.Manager.silentPlace.get();
      musheor.utils.manager.pvfA15fQSVM.fStlZIvY9lS8(item, silent);
      boolean placed = fStlZIvY9lS8(pos, direction);
      if (silent) {
         InvUtils.swapBack();
      }

      return placed;
   }

   public static void QfZy2gWnKInxFPr(BlockPos blockPos) {
      Vec3d hitPos = Vec3d.ofCenter(blockPos);
      Direction side = BlockUtils.getPlaceSide(blockPos);
      if (side != null) {
         blockPos.offset(side);
         hitPos = hitPos.add(Vec3d.of(side.getVector()).multiply(0.5));
      }

      assert QfZy2gWnKInxFPr.player != null;
      float[] rotation = fStlZIvY9lS8(QfZy2gWnKInxFPr.player, hitPos);
      QfZy2gWnKInxFPr.player.setYaw(rotation[0]);
      QfZy2gWnKInxFPr.player.setPitch(rotation[1]);
   }

   public static void TJImwK2(BlockPos pos, Direction side) {
      Vec3d center = Vec3d.ofCenter(pos);
      float[] rotation = fStlZIvY9lS8(QfZy2gWnKInxFPr.player, center.add(Vec3d.of(side.getVector()).multiply(0.5)));
      QfZy2gWnKInxFPr.player.setYaw(rotation[0]);
      QfZy2gWnKInxFPr.player.setPitch(rotation[1]);
   }

   static float[] fStlZIvY9lS8(ClientPlayerEntity player, Vec3d vec) {
      Vec3d eyesPos = player.getEyePos();
      double diffX = vec.x - eyesPos.x;
      double diffY = vec.y - eyesPos.y;
      double diffZ = vec.z - eyesPos.z;
      double r = Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);
      double yaw = -Math.atan2(diffX, diffZ) / Math.PI * 180.0;
      double pitch = -Math.asin(diffY / r) / Math.PI * 180.0;
      return new float[]{(float)yaw, (float)pitch};
   }

   public static boolean fStlZIvY9lS8(List<BlockPos> locations) {
      if (locations.isEmpty()) {
         return false;
      }

      assert QfZy2gWnKInxFPr.player != null;

      for (BlockPos pos : locations) {
         boolean hasGroundBelow = false;

         for (int x = -1; x <= 1 && !hasGroundBelow; x++) {
            for (int z = -1; z <= 1 && !hasGroundBelow; z++) {
               BlockPos checkPos = pos.add(x, -1, z);
               if (!QfZy2gWnKInxFPr.world.getBlockState(checkPos).isAir()) {
                  hasGroundBelow = true;
               }
            }
         }

         if (!hasGroundBelow) {
            return false;
         }
      }

      return true;
   }

   public static boolean fStlZIvY9lS8(BlockPos pos, Block block) {
      assert QfZy2gWnKInxFPr.world != null;
      return !QfZy2gWnKInxFPr.world.getBlockState(pos).isAir()
         && (
            !QfZy2gWnKInxFPr.world.getBlockState(pos).isReplaceable()
               || QfZy2gWnKInxFPr.world.getBlockState(pos).getBlock() instanceof VineBlock
               || QfZy2gWnKInxFPr.world.getBlockState(pos).getBlock() instanceof SnowBlock
         )
         && QfZy2gWnKInxFPr.world.getBlockState(pos).getBlock() != block;
   }

   public static void fStlZIvY9lS8(BlockPos[] positions) {
      Set<BlockPos> placementTargets = new HashSet<>(Arrays.asList(positions));

      for (BlockPos currentPos : positions) {
         if (BlockUtils.canPlace(currentPos, false) && !BlockUtils.canPlace(currentPos, true)) {
            if (QfZy2gWnKInxFPr(HighwayBuilder.V7zKJ4n6D()) < 1.4) {
               irzXX.fStlZIvY9lS8(false);
            }

            Entity entity = fStlZIvY9lS8(QfZy2gWnKInxFPr.world, currentPos);
            if (entity != null
               && entity != QfZy2gWnKInxFPr.player
               && entity.isAlive()
               && !(entity instanceof EndCrystalEntity)
               && !(entity instanceof BoatEntity)) {
               List<BlockPos> blocksToBreak = fStlZIvY9lS8(entity);
               if (!blocksToBreak.isEmpty()) {
                  for (BlockPos spleefPos : blocksToBreak) {
                     if (!placementTargets.contains(spleefPos)) {
                        KekNuker.vGAMA89H.add(spleefPos);
                        KekNuker.vGAMA89H.add(spleefPos.down());
                        KekNuker.vGAMA89H.add(spleefPos.down().down());
                        MusheorSystem.debug(
                           "Spleefing %s at x: %s y: %s z: %s",
                           entity.getName().getString(),
                           entity.getBlockPos().getX(),
                           entity.getBlockPos().getY(),
                           entity.getBlockPos().getZ()
                        );
                     }
                  }
               }
            }
         }
      }
   }

   public static boolean TJImwK2(BlockPos pos) {
      return QfZy2gWnKInxFPr.player.squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ())
         <= (Double)MusheorSystem.Manager.placementRange.get() * (Double)MusheorSystem.Manager.placementRange.get();
   }

   public static boolean fStlZIvY9lS8(BlockPos pos, double range) {
      return QfZy2gWnKInxFPr.player.squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ()) <= range * range;
   }

   public static void fStlZIvY9lS8(int currentTick, Block block) {
      obf.U6PQSXqW.fStlZIvY9lS8()
         .PVlYDeNipUe()
         .entrySet()
         .removeIf(entry -> currentTick - entry.getValue() > (Integer)MusheorSystem.Manager.placementTimeout.get());
   }

   public static void fStlZIvY9lS8(BlockPos[] positions, boolean scaffold) {
      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      if (QfZy2gWnKInxFPr.player != null && QfZy2gWnKInxFPr.world != null && !state.nOwp0NYl() && !HighwayBuilder.wSJeYsJcmjNZf()) {
         boolean diagonal = switch (HighwayBuilder.i2Nu6XHKic()) {
            case SOUTH, WEST, NORTH, EAST -> false;
            default -> true;
         };
         double distance = QfZy2gWnKInxFPr(HighwayBuilder.V7zKJ4n6D());
         MusheorSystem.debug("Distance %s", Math.round(distance * 100.0) / 100.0);
         if (diagonal) {
            if (!(distance <= 2.0) && !qhulAUwd()) {
               state.wSJeYsJcmjNZf(false);
               irzXX.fStlZIvY9lS8(true);
            } else {
               irzXX.fStlZIvY9lS8(false);
               state.wSJeYsJcmjNZf(true);
            }
         } else if (!(distance <= 1.5) && !qhulAUwd()) {
            state.wSJeYsJcmjNZf(false);
            irzXX.fStlZIvY9lS8(true);
         } else {
            irzXX.fStlZIvY9lS8(false);
            state.wSJeYsJcmjNZf(true);
         }

         if (!KekNuker.fStlZIvY9lS8()) {
            obf.irzXX.fStlZIvY9lS8(positions, scaffold ? HighwayBuilder.umvNbb7iD9UpR() : HighwayBuilder.V7zKJ4n6D(), Direction.UP, state.QfZy2gWnKInxFPr());
         }
      }
   }

   public static boolean qhulAUwd() {
      oyDPiOVBGKG.gVOhtBej1x dir = HighwayBuilder.i2Nu6XHKic();
      if (dir == null) {
         return false;
      }

      boolean leftRail = HighwayBuilder.xileXsbo();
      boolean rightRail = HighwayBuilder.nOwp0NYl();
      boolean cardinal = dir == oyDPiOVBGKG.gVOhtBej1x.NORTH
         || dir == oyDPiOVBGKG.gVOhtBej1x.SOUTH
         || dir == oyDPiOVBGKG.gVOhtBej1x.EAST
         || dir == oyDPiOVBGKG.gVOhtBej1x.WEST;
      BlockPos[] zone = cardinal
         ? musheor.utils.gVOhtBej1x.fStlZIvY9lS8(0, 1, leftRail, rightRail)
         : musheor.utils.gVOhtBej1x.TJImwK2(0, 1, leftRail, rightRail);
      if (HighwayBuilder.zXcO5rZ()) {
         BlockPos[] ceiling = cardinal
            ? musheor.utils.gVOhtBej1x.QfZy2gWnKInxFPr(0, 1, leftRail, rightRail)
            : musheor.utils.gVOhtBej1x.wSJeYsJcmjNZf(0, 1, leftRail, rightRail);
         BlockPos[] combined = new BlockPos[zone.length + ceiling.length];
         System.arraycopy(zone, 0, combined, 0, zone.length);
         System.arraycopy(ceiling, 0, combined, zone.length, ceiling.length);
         zone = combined;
      }

      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      obf.pvfA15fQSVM.gVOhtBej1x detected = state.npfimrDr0hky();
      int railY = state.BrmKXSYhWhqoB1() != null ? state.BrmKXSYhWhqoB1() : Integer.MIN_VALUE;

      for (BlockPos pos : zone) {
         if ((detected == null || pos.getY() != railY || !obf.pvfA15fQSVM.fStlZIvY9lS8(pos, detected)) && BlockUtils.canPlace(pos, true)) {
            return true;
         }
      }

      return false;
   }

   public static void fStlZIvY9lS8(List<BlockPos> list, Block block) {
      boolean silent = (Boolean)MusheorSystem.Manager.silentPlace.get();
      musheor.utils.manager.pvfA15fQSVM.fStlZIvY9lS8(block.asItem(), silent);
      vGAMA89H();

      for (BlockPos currentPos : list) {
         if (BlockUtils.canPlace(currentPos, true) && PlayerUtils.isWithin(currentPos, 4.5)) {
            if (!obf.jU8xXhmkg.fStlZIvY9lS8()) {
               break;
            }

            fStlZIvY9lS8(Hand.OFF_HAND, QfZy2gWnKInxFPr(currentPos, Direction.DOWN));
         }
      }

      vGAMA89H();
      if (silent) {
         InvUtils.swapBack();
      }
   }

   public static void BrmKXSYhWhqoB1() {
      if (QfZy2gWnKInxFPr.player != null && QfZy2gWnKInxFPr.world != null) {
         BlockPos playerPos = QfZy2gWnKInxFPr.player.getBlockPos();

         for (int offset = 1; offset <= 4; offset++) {
            BlockPos targetPos = switch (HighwayBuilder.i2Nu6XHKic()) {
               case SOUTH -> playerPos.add(0, -1, offset);
               case SOUTH_WEST -> playerPos.add(-offset, -1, offset);
               case WEST -> playerPos.add(-offset, -1, 0);
               case NORTH_WEST -> playerPos.add(-offset, -1, -offset);
               case NORTH -> playerPos.add(0, -1, -offset);
               case NORTH_EAST -> playerPos.add(offset, -1, -offset);
               case EAST -> playerPos.add(offset, -1, 0);
               case SOUTH_EAST -> playerPos.add(offset, -1, offset);
            };
            if (targetPos == null) {
               return;
            }

            if (QfZy2gWnKInxFPr.getNetworkHandler() == null) {
               return;
            }

            if (QfZy2gWnKInxFPr.world.getBlockState(targetPos).getBlock() == Blocks.SOUL_SAND) {
               BlockUtils.breakBlock(targetPos, false);
            }

            if (QfZy2gWnKInxFPr.world.getBlockState(targetPos).isAir()) {
               fStlZIvY9lS8(Items.NETHERRACK, targetPos, Direction.DOWN);
            }
         }
      }
   }

   public static double QfZy2gWnKInxFPr(Block block) {
      int offsetX = 0;
      int offsetZ = 0;
      oyDPiOVBGKG.gVOhtBej1x direction = HighwayBuilder.i2Nu6XHKic();
      switch (direction) {
         case SOUTH:
            offsetZ = 1;
            break;
         case SOUTH_WEST:
            offsetX = -1;
            offsetZ = 1;
            break;
         case WEST:
            offsetX = -1;
            break;
         case NORTH_WEST:
            offsetX = -1;
            offsetZ = -1;
            break;
         case NORTH:
            offsetZ = -1;
            break;
         case NORTH_EAST:
            offsetX = 1;
            offsetZ = -1;
            break;
         case EAST:
            offsetX = 1;
            break;
         case SOUTH_EAST:
            offsetX = 1;
            offsetZ = 1;
      }

      assert QfZy2gWnKInxFPr.player != null;
      assert QfZy2gWnKInxFPr.world != null;
      Vec3d playerPos = VersionHelper.get().getPlayerPos();
      Vec3d blockPos = null;

      for (int i = 0; i <= 5; i++) {
         double pX = VersionHelper.get().getPlayerPos().getX() + offsetX * i;
         double pZ = VersionHelper.get().getPlayerPos().getZ() + offsetZ * i;
         BlockPos centerPos = BlockPos.ofFloored(new Vec3d(pX, playerPos.getY() - 1.0, pZ));
         if (block == Blocks.OBSIDIAN) {
            if (QfZy2gWnKInxFPr.world.getBlockState(centerPos).getBlock() != Blocks.OBSIDIAN
               && QfZy2gWnKInxFPr.world.getBlockState(centerPos).getBlock() != Blocks.CRYING_OBSIDIAN) {
               blockPos = Vec3d.ofCenter(centerPos);
               break;
            }
         } else if (!QfZy2gWnKInxFPr.world.getBlockState(centerPos).getBlock().getDefaultState().isSolid()) {
            blockPos = Vec3d.ofCenter(centerPos);
            break;
         }
      }

      return blockPos == null ? 10.0 : fStlZIvY9lS8(blockPos, playerPos);
   }

   public static Block wSJeYsJcmjNZf(BlockPos pos) {
      assert QfZy2gWnKInxFPr.world != null && QfZy2gWnKInxFPr.player != null;
      return QfZy2gWnKInxFPr.world.getBlockState(pos).getBlock();
   }

   public static void wSJeYsJcmjNZf(BlockPos pos, Direction face) {
      Objects.requireNonNull(QfZy2gWnKInxFPr.getNetworkHandler()).sendPacket(new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, pos, face));
   }

   public static double fStlZIvY9lS8(BlockPos bP, BlockPos pP) {
      double dx;
      if (bP.getX() > pP.getX()) {
         dx = bP.getX() - pP.getX();
      } else {
         dx = pP.getX() - bP.getX();
      }

      double dz;
      if (bP.getZ() > pP.getZ()) {
         dz = bP.getZ() - pP.getZ();
      } else {
         dz = pP.getZ() - bP.getZ();
      }

      return Math.sqrt(dx * dx + dz * dz);
   }

   public static double fStlZIvY9lS8(Vec3d pos1, Vec3d pos2) {
      double dx;
      if (pos1.getX() > pos2.getX()) {
         dx = pos1.getX() - pos2.getX();
      } else {
         dx = pos2.getX() - pos1.getX();
      }

      double dz;
      if (pos1.getZ() > pos2.getZ()) {
         dz = pos1.getZ() - pos2.getZ();
      } else {
         dz = pos2.getZ() - pos1.getZ();
      }

      return Math.sqrt(dx * dx + dz * dz);
   }

   public static void TXWyNb() {
      assert QfZy2gWnKInxFPr.player != null && QfZy2gWnKInxFPr.world != null;
      int offset = 1;
      int offsetX = 0;
      int offsetZ = 0;
      boolean isDiagonal = false;
      oyDPiOVBGKG.gVOhtBej1x direction = HighwayBuilder.i2Nu6XHKic();
      switch (direction) {
         case SOUTH:
            offsetZ = offset;
            break;
         case SOUTH_WEST:
            offsetX = -offset;
            offsetZ = offset;
            isDiagonal = true;
            break;
         case WEST:
            offsetX = -offset;
            break;
         case NORTH_WEST:
            offsetX = -offset;
            offsetZ = -offset;
            isDiagonal = true;
            break;
         case NORTH:
            offsetZ = -offset;
            break;
         case NORTH_EAST:
            offsetX = offset;
            offsetZ = -offset;
            isDiagonal = true;
            break;
         case EAST:
            offsetX = offset;
            break;
         case SOUTH_EAST:
            offsetX = offset;
            offsetZ = offset;
            isDiagonal = true;
      }

      int[][] checks = isDiagonal ? new int[][]{{offsetX, offsetZ}, {offsetX, 0}, {0, offsetZ}} : new int[][]{{offsetX, offsetZ}};
      int height = 0;
      if (HighwayBuilder.qhulAUwd() == HighwayBuilder.BuildMode.Pave) {
         height = 3;
      }

      if (HighwayBuilder.qhulAUwd() == HighwayBuilder.BuildMode.Tunnel) {
         height = 4;
      }

      for (int[] check : checks) {
         for (int i = 0; i < height; i++) {
            BlockPos checkPos = QfZy2gWnKInxFPr.player.getBlockPos().add(check[0], i, check[1]);
            if (!QfZy2gWnKInxFPr.world.getBlockState(checkPos).getCollisionShape(QfZy2gWnKInxFPr.world, checkPos).isEmpty()) {
               irzXX.fStlZIvY9lS8(false);
               MusheorSystem.debug("Collision detected in front of the player");
            }
         }
      }
   }

   public static boolean I3psabmOrOI() {
      assert QfZy2gWnKInxFPr.player != null && QfZy2gWnKInxFPr.world != null;
      int offsetX = 0;
      int offsetZ = 0;
      oyDPiOVBGKG.gVOhtBej1x direction = HighwayBuilder.i2Nu6XHKic();
      switch (direction) {
         case SOUTH_WEST:
            offsetX = -1;
            offsetZ = 1;
         case WEST:
         case NORTH:
         case EAST:
         default:
            break;
         case NORTH_WEST:
            offsetX = -1;
            offsetZ = -1;
            break;
         case NORTH_EAST:
            offsetX = 1;
            offsetZ = -1;
            break;
         case SOUTH_EAST:
            offsetX = 1;
            offsetZ = 1;
      }

      if (QfZy2gWnKInxFPr.world.getBlockState(QfZy2gWnKInxFPr.player.getBlockPos().add(offsetX, -1, offsetZ)).isAir()) {
         BlockPos position = QfZy2gWnKInxFPr.player.getBlockPos().add(offsetX, -1, offsetZ);
         boolean silent = (Boolean)MusheorSystem.Manager.silentPlace.get();
         musheor.utils.manager.pvfA15fQSVM.fStlZIvY9lS8(Items.NETHERRACK, silent);
         fStlZIvY9lS8(position, Direction.DOWN);
         if (silent) {
            InvUtils.swapBack();
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean fStlZIvY9lS8(Item targetItem) {
      if (QfZy2gWnKInxFPr.world != null && QfZy2gWnKInxFPr.player != null) {
         int SEARCH_RADIUS = 10;
         int MAX_Y = 122;
         Box searchArea = new Box(
            QfZy2gWnKInxFPr.player.getX() - SEARCH_RADIUS,
            QfZy2gWnKInxFPr.player.getY() - SEARCH_RADIUS,
            QfZy2gWnKInxFPr.player.getZ() - SEARCH_RADIUS,
            QfZy2gWnKInxFPr.player.getX() + SEARCH_RADIUS,
            MAX_Y,
            QfZy2gWnKInxFPr.player.getZ() + SEARCH_RADIUS
         );
         List<ItemEntity> items = QfZy2gWnKInxFPr.world
            .getEntitiesByClass(ItemEntity.class, searchArea, itemEntity -> itemEntity.getStack().getItem() == targetItem);
         return !items.isEmpty();
      } else {
         return false;
      }
   }

   public static void QfZy2gWnKInxFPr(Item item) {
      if (QfZy2gWnKInxFPr.player != null && QfZy2gWnKInxFPr.world != null) {
         ItemEntity nearestItemEntity = null;
         double closestDistanceSq = Double.MAX_VALUE;

         for (Entity entity : QfZy2gWnKInxFPr.world.getEntities()) {
            if (entity instanceof ItemEntity itemEntity && itemEntity.getStack().getItem() == item) {
               double distSq = entity.squaredDistanceTo(QfZy2gWnKInxFPr.player);
               if (distSq < closestDistanceSq) {
                  closestDistanceSq = distSq;
                  nearestItemEntity = itemEntity;
               }
            }
         }

         if (nearestItemEntity != null) {
            BlockPos targetPos = nearestItemEntity.getBlockPos();
            obf.GyhoRo.QfZy2gWnKInxFPr(targetPos);
         } else {
            ChatUtils.error("No matching item found nearby.", new Object[0]);
         }
      }
   }

   public static Direction fStlZIvY9lS8(float yaw, float pitch) {
      if (pitch > 60.0F) {
         return Direction.DOWN;
      }

      if (pitch < -60.0F) {
         return Direction.UP;
      }

      yaw %= 360.0F;
      if (yaw < 0.0F) {
         yaw += 360.0F;
      }

      if (yaw >= 315.0F || yaw < 45.0F) {
         return Direction.SOUTH;
      } else if (yaw >= 45.0F && yaw < 135.0F) {
         return Direction.WEST;
      } else {
         return yaw >= 135.0F && yaw < 225.0F ? Direction.NORTH : Direction.EAST;
      }
   }

   public static oyDPiOVBGKG.gVOhtBej1x duJ6sPeetAcBh6P() {
      assert QfZy2gWnKInxFPr.player != null;
      return oyDPiOVBGKG.gVOhtBej1x.fStlZIvY9lS8(QfZy2gWnKInxFPr.player.getYaw());
   }

   public enum gVOhtBej1x {
      SOUTH,
      SOUTH_WEST,
      WEST,
      NORTH_WEST,
      NORTH,
      NORTH_EAST,
      EAST,
      SOUTH_EAST;

      gVOhtBej1x() {
      }

      public static oyDPiOVBGKG.gVOhtBej1x fStlZIvY9lS8(float yaw) {
         float normalized = (yaw % 360.0F + 360.0F) % 360.0F;
         int i = Math.round(normalized / 45.0F) % 8;
         return values()[i];
      }

      public oyDPiOVBGKG.gVOhtBej1x fStlZIvY9lS8() {
         return values()[(this.ordinal() + 4) % 8];
      }

      public static float QfZy2gWnKInxFPr(float currentYaw) {
         float normalized = (currentYaw % 360.0F + 360.0F) % 360.0F;
         return (normalized + 180.0F) % 360.0F;
      }

      public static float fStlZIvY9lS8(oyDPiOVBGKG.gVOhtBej1x direction) {
         return direction.ordinal() * 45.0F;
      }
   }

   public record pvfA15fQSVM(double fStlZIvY9lS8, double QfZy2gWnKInxFPr) {


      @Override
      public final String toString() {
         return "pvfA15fQSVM[x=" + this.fStlZIvY9lS8 + ", z=" + this.QfZy2gWnKInxFPr + "]";
      }
   }
}
