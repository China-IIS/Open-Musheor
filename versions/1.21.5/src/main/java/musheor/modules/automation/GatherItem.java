package musheor.modules.automation;

import baritone.api.pathing.goals.GoalBlock;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.settings.ItemSetting.Builder;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.orbit.EventHandler;
import musheor.musheor;
import musheor.utils.irzXX;
import musheor.utils.manager.pvfA15fQSVM;
import musheor.utils.oyDPiOVBGKG;
import musheor.utils.system.MusheorSystem;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import obf.GyhoRo;

public class GatherItem extends Module {
   private static final Set<Block> fStlZIvY9lS8 = Set.of(
      Blocks.MAGMA_BLOCK,
      Blocks.SLIME_BLOCK,
      Blocks.HONEY_BLOCK,
      Blocks.POWDER_SNOW,
      Blocks.SCAFFOLDING,
      Blocks.CACTUS,
      Blocks.FIRE,
      Blocks.SOUL_FIRE,
      Blocks.NETHER_PORTAL
   );
   private static final Set<Block> QfZy2gWnKInxFPr = Set.of(
      Blocks.COBWEB,
      Blocks.FIRE,
      Blocks.SOUL_FIRE,
      Blocks.NETHER_PORTAL,
      Blocks.END_PORTAL,
      Blocks.POWDER_SNOW,
      Blocks.SWEET_BERRY_BUSH
   );
   private final SettingGroup TJImwK2 = this.settings.getDefaultGroup();
   private final Setting<Item> wSJeYsJcmjNZf = this.TJImwK2
      .add(((Builder)((Builder)((Builder)new Builder().name("item")).description("Item to gather")).defaultValue(Items.OBSIDIAN)).build());
   private final Setting<Integer> i2Nu6XHKic = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("search-radius"))
                  .description("Maximum radius to scan for items"))
               .defaultValue(16))
            .sliderRange(1, 128)
            .build()
      );
   private final Setting<Integer> vGAMA89H = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("stuck-timeout"))
                  .description("Seconds to reach a stand position before giving up on it and trying the next one"))
               .defaultValue(5))
            .sliderRange(1, 30)
            .build()
      );
   private final Setting<Integer> qhulAUwd = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("unreachable-cooldown"))
                  .description("Seconds an item is ignored after every position around it failed"))
               .defaultValue(10))
            .sliderRange(3, 60)
            .build()
      );
   private final Setting<Boolean> BrmKXSYhWhqoB1 = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("ignore-below-y119"))
                  .description("Ignores items below Y-level 119 to prevent the paver from digging underground"))
               .defaultValue(true))
            .build()
      );
   private ItemEntity TXWyNb = null;
   private BlockPos I3psabmOrOI = null;
   private BlockPos duJ6sPeetAcBh6P = null;
   private final Deque<BlockPos> j376sIQ = new ArrayDeque<>();
   private final Map<UUID, Integer> V7zKJ4n6D = new HashMap<>();
   private int voxTwgNzyrpaAnP = 0;
   private int xileXsbo = 20;
   private int nOwp0NYl = 0;
   private boolean umvNbb7iD9UpR = false;

   public GatherItem() {
      super(musheor.AUTOMATION, "pickup-item", "A helper module that finds and picks up nearby items.");
   }

   public void onActivate() {
      this.fStlZIvY9lS8();
      this.V7zKJ4n6D.clear();
      this.xileXsbo = 20;
      this.umvNbb7iD9UpR = false;
   }

   public void onDeactivate() {
      irzXX.fStlZIvY9lS8();
      this.fStlZIvY9lS8();
   }

   private void fStlZIvY9lS8() {
      this.TXWyNb = null;
      this.I3psabmOrOI = null;
      this.duJ6sPeetAcBh6P = null;
      this.j376sIQ.clear();
      this.nOwp0NYl = 0;
   }

   private ItemEntity QfZy2gWnKInxFPr() {
      if (this.mc.player != null && this.mc.world != null) {
         int radius = (Integer)this.i2Nu6XHKic.get();
         Box searchArea = new Box(
            this.mc.player.getX() - radius,
            this.mc.player.getY() - radius,
            this.mc.player.getZ() - radius,
            this.mc.player.getX() + radius,
            this.mc.player.getY() + radius,
            this.mc.player.getZ() + radius
         );
         double radiusSq = (double)radius * radius;
         return this.mc
            .world
            .getEntitiesByClass(
               ItemEntity.class,
               searchArea,
               e -> e.getStack().getItem() == this.wSJeYsJcmjNZf.get()
                  && e.squaredDistanceTo(this.mc.player) <= radiusSq
                  && !this.V7zKJ4n6D.containsKey(e.getUuid())
                  && (!(Boolean)this.BrmKXSYhWhqoB1.get() || e.getBlockY() >= 119)
            )
            .stream()
            .min(Comparator.comparingDouble(e -> e.squaredDistanceTo(this.mc.player)))
            .orElse(null);
      } else {
         return null;
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(Pre event) {
      if (this.mc.player != null && this.mc.world != null) {
         Module echestFarmer = Modules.get().get(EchestFarmer.class);
         Module hwb = Modules.get().get(HighwayBuilder.class);
         if (echestFarmer == null || !echestFarmer.isActive() || !HighwayBuilder.vGAMA89H()) {
            if (pvfA15fQSVM.duJ6sPeetAcBh6P() == 0) {
               if (!oyDPiOVBGKG.fStlZIvY9lS8() && hwb.isActive()) {
                  irzXX.fStlZIvY9lS8(false);
                  if (!GyhoRo.fStlZIvY9lS8()) {
                     oyDPiOVBGKG.wSJeYsJcmjNZf();
                  }
               }

               MusheorSystem.debug("Inventory full, toggling off gather item...");
               this.toggle();
            } else {
               this.voxTwgNzyrpaAnP++;
               this.V7zKJ4n6D.values().removeIf(expiry -> expiry <= this.voxTwgNzyrpaAnP);
               this.xileXsbo++;
               boolean targetGone = this.TXWyNb == null || !this.TXWyNb.isAlive() || this.TXWyNb.isRemoved();
               if (targetGone || this.xileXsbo >= 20) {
                  ItemEntity found = this.QfZy2gWnKInxFPr();
                  if (found != this.TXWyNb) {
                     this.fStlZIvY9lS8();
                     this.TXWyNb = found;
                  }

                  this.xileXsbo = 0;
               }

               if (this.TXWyNb == null) {
                  ChatUtils.info(
                     this.umvNbb7iD9UpR
                        ? "No reachable " + ((Item)this.wSJeYsJcmjNZf.get()).getName().getString() + " found nearby."
                        : "No more " + ((Item)this.wSJeYsJcmjNZf.get()).getName().getString() + " found nearby.",
                     new Object[0]
                  );
                  if (!oyDPiOVBGKG.fStlZIvY9lS8() && hwb.isActive()) {
                     irzXX.fStlZIvY9lS8(false);
                     if (!GyhoRo.fStlZIvY9lS8()) {
                        oyDPiOVBGKG.wSJeYsJcmjNZf();
                     }
                  }

                  this.toggle();
               } else {
                  BlockPos itemPos = this.TXWyNb.getBlockPos();
                  if (!itemPos.equals(this.I3psabmOrOI)) {
                     this.I3psabmOrOI = itemPos;
                     this.j376sIQ.clear();
                     this.j376sIQ.addAll(this.fStlZIvY9lS8(this.TXWyNb));
                     this.TJImwK2();
                  }

                  if (this.duJ6sPeetAcBh6P != null) {
                     if (!this.fStlZIvY9lS8(this.duJ6sPeetAcBh6P)) {
                        this.fStlZIvY9lS8("stand position is no longer safe");
                     } else if (++this.nOwp0NYl > (Integer)this.vGAMA89H.get() * 20) {
                        this.fStlZIvY9lS8("timed out");
                     } else if (!this.mc.player.getBlockPos().equals(this.duJ6sPeetAcBh6P) && !this.wSJeYsJcmjNZf()) {
                        if (this.nOwp0NYl > 5 && !GyhoRo.TJImwK2()) {
                           this.fStlZIvY9lS8("no path to it");
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void fStlZIvY9lS8(String reason) {
      if (reason != null && this.duJ6sPeetAcBh6P != null) {
         MusheorSystem.debug("Stand position %s failed (%s), trying the next one...", this.duJ6sPeetAcBh6P.toShortString(), reason);
      }

      this.TJImwK2();
   }

   private void TJImwK2() {
      this.duJ6sPeetAcBh6P = this.j376sIQ.poll();
      this.nOwp0NYl = 0;
      if (this.duJ6sPeetAcBh6P == null) {
         MusheorSystem.debug("No reachable position around the %s, skipping it...", ((Item)this.wSJeYsJcmjNZf.get()).getName().getString());
         if (this.TXWyNb != null) {
            this.V7zKJ4n6D.put(this.TXWyNb.getUuid(), this.voxTwgNzyrpaAnP + (Integer)this.qhulAUwd.get() * 20);
         }

         this.umvNbb7iD9UpR = true;
         irzXX.fStlZIvY9lS8();
         this.fStlZIvY9lS8();
         this.xileXsbo = 20;
      } else {
         GyhoRo.fStlZIvY9lS8(new GoalBlock(this.duJ6sPeetAcBh6P));
      }
   }

   private List<BlockPos> fStlZIvY9lS8(ItemEntity target) {
      List<BlockPos> positions = new ArrayList<>();
      BlockPos itemPos = target.getBlockPos();

      for (int dy = 1; dy >= -2; dy--) {
         for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
               BlockPos stand = new BlockPos(itemPos.getX() + dx, itemPos.getY() + dy, itemPos.getZ() + dz);
               if (this.fStlZIvY9lS8(stand) && this.fStlZIvY9lS8(stand, target)) {
                  positions.add(stand);
               }
            }
         }
      }

      positions.sort(Comparator.comparingDouble(pos -> target.squaredDistanceTo(Vec3d.ofCenter(pos))));
      return positions;
   }

   private boolean wSJeYsJcmjNZf() {
      return this.mc.player.getBoundingBox().expand(0.8, 0.4, 0.8).intersects(this.TXWyNb.getBoundingBox());
   }

   private boolean fStlZIvY9lS8(BlockPos stand, ItemEntity target) {
      double cx = stand.getX() + 0.5;
      double cz = stand.getZ() + 0.5;
      double y = stand.getY();
      Box playerBox = new Box(cx - 0.3, y, cz - 0.3, cx + 0.3, y + 1.8, cz + 0.3).expand(0.8, 0.4, 0.8);
      return playerBox.intersects(target.getBoundingBox());
   }

   private boolean fStlZIvY9lS8(BlockPos pos) {
      if (this.mc.world == null) {
         return false;
      } else if (pos.getY() <= this.mc.world.getBottomY()) {
         return false;
      } else {
         BlockPos groundPos = pos.down();
         BlockState ground = this.mc.world.getBlockState(groundPos);
         if (ground.isAir()) {
            return false;
         } else if (!ground.getFluidState().isEmpty()) {
            return false;
         } else if (fStlZIvY9lS8.contains(ground.getBlock())) {
            return false;
         } else if (ground.getBlock() instanceof AbstractChestBlock) {
            return false;
         } else if (ground.getBlock() instanceof BedBlock) {
            return false;
         } else {
            return !ground.isSideSolidFullSquare(this.mc.world, groundPos, Direction.UP)
               ? false
               : this.QfZy2gWnKInxFPr(pos) && this.QfZy2gWnKInxFPr(pos.up());
         }
      }
   }

   private boolean QfZy2gWnKInxFPr(BlockPos pos) {
      BlockState state = this.mc.world.getBlockState(pos);
      if (!state.getCollisionShape(this.mc.world, pos).isEmpty()) {
         return false;
      } else if (QfZy2gWnKInxFPr.contains(state.getBlock())) {
         return false;
      } else {
         FluidState fluid = state.getFluidState();
         return fluid.getFluid() != Fluids.LAVA && fluid.getFluid() != Fluids.FLOWING_LAVA;
      }
   }
}
