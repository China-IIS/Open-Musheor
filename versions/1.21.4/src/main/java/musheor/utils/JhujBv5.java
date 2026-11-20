package musheor.utils;

import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalXZ;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.meteorclient.utils.render.RenderUtils;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import musheor.modules.automation.EchestFarmer;
import musheor.modules.automation.HighwayBuilder;
import musheor.modules.automation.KekBounce;
import musheor.modules.automation.KekNuker;
import musheor.modules.automation.SourceRemover;
import musheor.utils.system.MusheorSystem;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class JhujBv5 {
   public static final MinecraftClient fStlZIvY9lS8 = MinecraftClient.getInstance();
   private static final int TJImwK2 = 2;
   private static final int wSJeYsJcmjNZf = 1;
   private static boolean i2Nu6XHKic = false;
   private static int vGAMA89H = 0;
   private static final int qhulAUwd = 20;
   private static final int BrmKXSYhWhqoB1 = 32;
   private static boolean TXWyNb = false;
   private static int I3psabmOrOI = 0;
   private static int duJ6sPeetAcBh6P = 0;
   private static final int j376sIQ = 15;
   private static final int V7zKJ4n6D = 40;
   private static final int voxTwgNzyrpaAnP = 24;

   public static void fStlZIvY9lS8() {
      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      irzXX.fStlZIvY9lS8(EchestFarmer.class, "self-toggle", true);
      state.QfZy2gWnKInxFPr(fStlZIvY9lS8.player.getBlockPos());
      if (!oyDPiOVBGKG.fStlZIvY9lS8()) {
         irzXX.fStlZIvY9lS8(false);
         if (!obf.GyhoRo.fStlZIvY9lS8()) {
            oyDPiOVBGKG.wSJeYsJcmjNZf();
         }
      } else {
         if (!state.Nt3xeF()) {
            assert fStlZIvY9lS8.player != null;

            int remaining = musheor.utils.manager.pvfA15fQSVM.i2Nu6XHKic(Items.ENDER_CHEST) - 8;
            if (remaining > 0) {
               irzXX.fStlZIvY9lS8(EchestFarmer.class, "amount", Math.min(remaining, musheor.utils.manager.pvfA15fQSVM.duJ6sPeetAcBh6P() * 8));
            }

            irzXX.fStlZIvY9lS8(false);
            state.j376sIQ(true);
            Module BetterEchestFarmer = Modules.get().get(EchestFarmer.class);
            if (!BetterEchestFarmer.isActive()) {
               state.duJ6sPeetAcBh6P(true);
               BetterEchestFarmer.toggle();
            }
         }
      }
   }

   public static void QfZy2gWnKInxFPr() {
      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      if (!HighwayBuilder.vGAMA89H() && state.FVjbhYkk53UT()) {
         if (EchestFarmer.fStlZIvY9lS8 != null && fStlZIvY9lS8.world.getBlockState(EchestFarmer.fStlZIvY9lS8).getBlock() == Blocks.ENDER_CHEST) {
            MusheorSystem.debug("Attempting to break incorrect block...");
            BlockUtils.breakBlock(EchestFarmer.fStlZIvY9lS8, true);
            return;
         }

         if (irzXX.qhulAUwd()) {
            return;
         }

         irzXX.fStlZIvY9lS8(Items.OBSIDIAN, false);
         state.j376sIQ(false);
         state.duJ6sPeetAcBh6P(false);
      }
   }

   public static void TJImwK2() {
      assert fStlZIvY9lS8.player != null && fStlZIvY9lS8.world != null;

      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      if (state.TJImwK2() != null && state.qhulAUwd() != null && state.BrmKXSYhWhqoB1() != null && state.TXWyNb() != null) {
         if (!fStlZIvY9lS8(state)) {
            Module BetterEChestFarmer = Modules.get().get(EchestFarmer.class);
            Map<String, Boolean> conditions = new LinkedHashMap<>();
            conditions.put("BetterEchestFarmer", BetterEChestFarmer.isActive());
            conditions.put("Gathering Items", irzXX.qhulAUwd());
            conditions.put("Removing Lava", SourceRemover.fStlZIvY9lS8());
            if (!BetterEChestFarmer.isActive() && !irzXX.qhulAUwd() && !SourceRemover.fStlZIvY9lS8()) {
               if (KekNuker.TJImwK2() || HighwayBuilder.TJImwK2() || HighwayBuilder.wSJeYsJcmjNZf() || musheor.utils.manager.pvfA15fQSVM.TJImwK2) {
                  BrmKXSYhWhqoB1();
                  irzXX.fStlZIvY9lS8(false);
               }

               if (HighwayBuilder.Nt3xeF()) {
                  oyDPiOVBGKG.i2Nu6XHKic();
               }

               if (HighwayBuilder.WGmfQKK() && HighwayBuilder.BrmKXSYhWhqoB1() == HighwayBuilder.Mode.QfZy2gWnKInxFPr) {
                  if (vGAMA89H > 0) {
                     vGAMA89H--;
                     return;
                  }

                  boolean interrupted = KekNuker.TJImwK2()
                     || HighwayBuilder.TJImwK2()
                     || HighwayBuilder.wSJeYsJcmjNZf()
                     || musheor.utils.manager.pvfA15fQSVM.TJImwK2
                     || irzXX.qhulAUwd()
                     || SourceRemover.fStlZIvY9lS8()
                     || BetterEChestFarmer.isActive()
                     || obf.GyhoRo.fStlZIvY9lS8();
                  if (!interrupted && fStlZIvY9lS8(true, HighwayBuilder.icQ738dpWr7W())) {
                     j376sIQ();
                  } else {
                     BrmKXSYhWhqoB1();
                  }

                  if (i2Nu6XHKic) {
                     irzXX.vGAMA89H();
                     return;
                  }
               }

               if (HighwayBuilder.BrmKXSYhWhqoB1() == HighwayBuilder.Mode.QfZy2gWnKInxFPr) {
                  irzXX.I3psabmOrOI();
                  oyDPiOVBGKG.TXWyNb();
                  irzXX.i2Nu6XHKic();
                  irzXX.BrmKXSYhWhqoB1();
               }

               BlockPos[] positions = gVOhtBej1x.fStlZIvY9lS8(2, 3, HighwayBuilder.xileXsbo(), HighwayBuilder.nOwp0NYl());
               if ((Boolean)HighwayBuilder.TJImwK2.QfZy2gWnKInxFPr.get()) {
                  KekNuker.vGAMA89H.clear();
               }

               state.qhulAUwd(false);
               oyDPiOVBGKG.fStlZIvY9lS8(positions);
               List<Block> blacklistedBlocks = KekNuker.QfZy2gWnKInxFPr();
               state.vGAMA89H(false);
               BlockPos nonObsidianBlock = null;
               boolean foundBlockage = false;
               BlockPos blockage = null;
               if (HighwayBuilder.BrmKXSYhWhqoB1() == HighwayBuilder.Mode.QfZy2gWnKInxFPr) {
                  for (BlockPos currentPos : gVOhtBej1x.fStlZIvY9lS8(-3, 5, HighwayBuilder.xileXsbo(), HighwayBuilder.nOwp0NYl())) {
                     Block block = fStlZIvY9lS8.world.getBlockState(currentPos).getBlock();
                     if ((HighwayBuilder.j376sIQ() || block != Blocks.CRYING_OBSIDIAN)
                        && block != HighwayBuilder.V7zKJ4n6D()
                        && oX7ku.QfZy2gWnKInxFPr() > 10
                        && !blacklistedBlocks.contains(block)) {
                        state.vGAMA89H(true);
                        nonObsidianBlock = currentPos;
                        break;
                     }
                  }

                  if (HighwayBuilder.zXcO5rZ()) {
                     for (BlockPos currentPosx : gVOhtBej1x.QfZy2gWnKInxFPr(-3, 4, HighwayBuilder.xileXsbo(), HighwayBuilder.nOwp0NYl())) {
                        Block block = fStlZIvY9lS8.world.getBlockState(currentPosx).getBlock();
                        if ((HighwayBuilder.j376sIQ() || block != Blocks.CRYING_OBSIDIAN)
                           && block != Blocks.BEDROCK
                           && block == Blocks.AIR
                           && oX7ku.QfZy2gWnKInxFPr() > 10
                           && !blacklistedBlocks.contains(block)) {
                           state.vGAMA89H(true);
                           nonObsidianBlock = currentPosx;
                           break;
                        }
                     }
                  }

                  for (BlockPos currentPosxx : gVOhtBej1x.fStlZIvY9lS8()) {
                     Block block = fStlZIvY9lS8.world.getBlockState(currentPosxx).getBlock();
                     if (!(block instanceof AirBlock) && oX7ku.QfZy2gWnKInxFPr() > 10 && !blacklistedBlocks.contains(block)) {
                        foundBlockage = true;
                        blockage = currentPosxx;
                        break;
                     }
                  }
               }

               if (foundBlockage) {
                  irzXX.fStlZIvY9lS8(false);
                  MusheorSystem.debug("Detected blockage behind player... %s", fStlZIvY9lS8.world.getBlockState(blockage).getBlock().getName());

                  assert blockage != null;

                  Goal baritoneGoal = switch (HighwayBuilder.i2Nu6XHKic()) {
                     case i2Nu6XHKic -> new GoalBlock(state.qhulAUwd(), state.BrmKXSYhWhqoB1(), blockage.getZ() + 2);
                     case qhulAUwd -> new GoalBlock(blockage.getX() - 2, state.BrmKXSYhWhqoB1(), state.TXWyNb());
                     case fStlZIvY9lS8 -> new GoalBlock(state.qhulAUwd(), state.BrmKXSYhWhqoB1(), blockage.getZ() - 2);
                     case TJImwK2 -> new GoalBlock(blockage.getX() + 2, state.BrmKXSYhWhqoB1(), state.TXWyNb());
                     default -> new GoalBlock(state.qhulAUwd(), state.BrmKXSYhWhqoB1(), state.TXWyNb());
                  };
                  obf.GyhoRo.fStlZIvY9lS8(baritoneGoal);
               } else {
                  if (state.xileXsbo()) {
                     irzXX.fStlZIvY9lS8(false);
                     MusheorSystem.debug("Found missing block, going back...");

                     assert nonObsidianBlock != null;
                     Goal baritoneGoal = switch (HighwayBuilder.i2Nu6XHKic()) {
                        case i2Nu6XHKic, fStlZIvY9lS8 -> new GoalBlock(state.qhulAUwd(), state.BrmKXSYhWhqoB1(), nonObsidianBlock.getZ());
                        case qhulAUwd, TJImwK2 -> new GoalBlock(nonObsidianBlock.getX(), state.BrmKXSYhWhqoB1(), state.TXWyNb());
                        default -> new GoalBlock(nonObsidianBlock.getX(), state.BrmKXSYhWhqoB1(), nonObsidianBlock.getZ());
                     };
                     obf.GyhoRo.fStlZIvY9lS8(baritoneGoal);
                  }

                  if ((Boolean)HighwayBuilder.TJImwK2.QfZy2gWnKInxFPr.get()) {
                     for (BlockPos pos : positions) {
                        Block block = fStlZIvY9lS8.world.getBlockState(pos).getBlock();
                        if (block != Blocks.NETHER_PORTAL
                           && block != Blocks.BEDROCK
                           && (HighwayBuilder.j376sIQ() || block != Blocks.CRYING_OBSIDIAN)
                           && oyDPiOVBGKG.fStlZIvY9lS8(pos, HighwayBuilder.V7zKJ4n6D())) {
                           KekNuker.vGAMA89H.add(pos.toImmutable());
                        }
                     }

                     for (BlockPos posx : gVOhtBej1x.fStlZIvY9lS8(3, 2)) {
                        Block block = fStlZIvY9lS8.world.getBlockState(posx).getBlock();
                        if (block != Blocks.NETHER_PORTAL && block != Blocks.BEDROCK && oyDPiOVBGKG.fStlZIvY9lS8(posx, Blocks.AIR)) {
                           KekNuker.vGAMA89H.add(posx.toImmutable());
                        }
                     }
                  }

                  state.i2Nu6XHKic(false);
                  oyDPiOVBGKG.fStlZIvY9lS8(positions, false);
                  if (HighwayBuilder.zXcO5rZ()) {
                     oyDPiOVBGKG.fStlZIvY9lS8(gVOhtBej1x.QfZy2gWnKInxFPr(2, 2, HighwayBuilder.xileXsbo(), HighwayBuilder.nOwp0NYl()), false);
                  }

                  if (!state.voxTwgNzyrpaAnP() || !state.V7zKJ4n6D() || !state.xileXsbo() || !KekNuker.TJImwK2() || !state.nOwp0NYl()) {
                     irzXX.fStlZIvY9lS8(true);
                  }
               }
            } else {
               irzXX.fStlZIvY9lS8(false);

               for (Entry<String, Boolean> entry : conditions.entrySet()) {
                  if (entry.getValue()) {
                     MusheorSystem.debug(entry.getKey() + " was triggered.");
                  }
               }
            }
         }
      }
   }

   public static void wSJeYsJcmjNZf() {
      assert fStlZIvY9lS8.player != null && fStlZIvY9lS8.world != null;

      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      if (state.TJImwK2() != null && state.qhulAUwd() != null && state.BrmKXSYhWhqoB1() != null && state.TXWyNb() != null) {
         if (!fStlZIvY9lS8(state)) {
            double dx = fStlZIvY9lS8.player.getBlockX() - state.wSJeYsJcmjNZf().getX();
            double dz = fStlZIvY9lS8.player.getBlockZ() - state.wSJeYsJcmjNZf().getZ();
            if (Math.abs(dx) == Math.abs(dz)) {
               state.TJImwK2(Integer.valueOf(fStlZIvY9lS8.player.getBlockZ()));
               state.fStlZIvY9lS8(Integer.valueOf(fStlZIvY9lS8.player.getBlockX()));
            }

            if (!oyDPiOVBGKG.fStlZIvY9lS8()) {
               irzXX.fStlZIvY9lS8(false);
               if (!obf.GyhoRo.fStlZIvY9lS8() && state.ZlNrrNcv3Ylc() == null && state.zbN84Osn9p5lD() == null) {
                  oyDPiOVBGKG.wSJeYsJcmjNZf();
               }
            }

            Module BetterEChestFarmer = Modules.get().get(EchestFarmer.class);
            if (!BetterEChestFarmer.isActive() && !irzXX.qhulAUwd() && !SourceRemover.fStlZIvY9lS8()) {
               if (KekNuker.TJImwK2() || HighwayBuilder.TJImwK2() || HighwayBuilder.wSJeYsJcmjNZf() || musheor.utils.manager.pvfA15fQSVM.TJImwK2) {
                  BrmKXSYhWhqoB1();
                  irzXX.fStlZIvY9lS8(false);
               }

               if (HighwayBuilder.Nt3xeF()) {
                  oyDPiOVBGKG.i2Nu6XHKic();
               }

               if (HighwayBuilder.WGmfQKK() && HighwayBuilder.BrmKXSYhWhqoB1() == HighwayBuilder.Mode.QfZy2gWnKInxFPr) {
                  if (vGAMA89H > 0) {
                     vGAMA89H--;
                     return;
                  }

                  boolean interrupted = KekNuker.TJImwK2()
                     || HighwayBuilder.TJImwK2()
                     || HighwayBuilder.wSJeYsJcmjNZf()
                     || musheor.utils.manager.pvfA15fQSVM.TJImwK2
                     || irzXX.qhulAUwd()
                     || SourceRemover.fStlZIvY9lS8()
                     || BetterEChestFarmer.isActive()
                     || obf.GyhoRo.fStlZIvY9lS8();
                  if (!interrupted && fStlZIvY9lS8(false, HighwayBuilder.icQ738dpWr7W())) {
                     j376sIQ();
                  } else {
                     BrmKXSYhWhqoB1();
                  }

                  if (i2Nu6XHKic) {
                     irzXX.vGAMA89H();
                     return;
                  }
               }

               if (HighwayBuilder.BrmKXSYhWhqoB1() == HighwayBuilder.Mode.QfZy2gWnKInxFPr) {
                  irzXX.TXWyNb();
                  oyDPiOVBGKG.TXWyNb();
                  irzXX.i2Nu6XHKic();
               }

               List<Block> blacklistedBlocks = KekNuker.QfZy2gWnKInxFPr();
               boolean foundBlockage = false;
               BlockPos blockage = null;
               if (HighwayBuilder.BrmKXSYhWhqoB1() == HighwayBuilder.Mode.QfZy2gWnKInxFPr) {
                  for (BlockPos currentPos : gVOhtBej1x.QfZy2gWnKInxFPr(-2, 5)) {
                     Block block = fStlZIvY9lS8.world.getBlockState(currentPos).getBlock();
                     if (!(block instanceof AirBlock) && oX7ku.QfZy2gWnKInxFPr() > 10 && !blacklistedBlocks.contains(block)) {
                        foundBlockage = true;
                        blockage = currentPos;
                        break;
                     }
                  }

                  if (foundBlockage) {
                     irzXX.fStlZIvY9lS8(false);
                     MusheorSystem.debug(
                        "Detected blockage behind player... "
                           + Registries.BLOCK.getId(fStlZIvY9lS8.world.getBlockState(blockage).getBlock()).getPath()
                     );

                     assert blockage != null;

                     int distance = 4;

                     Goal baritoneGoal = switch (HighwayBuilder.i2Nu6XHKic()) {
                        case vGAMA89H -> new GoalBlock(state.qhulAUwd() - distance, state.BrmKXSYhWhqoB1(), state.TXWyNb() + distance);
                        case wSJeYsJcmjNZf -> new GoalBlock(state.qhulAUwd() + distance, state.BrmKXSYhWhqoB1(), state.TXWyNb() + distance);
                        case BrmKXSYhWhqoB1 -> new GoalBlock(state.qhulAUwd() - distance, state.BrmKXSYhWhqoB1(), state.TXWyNb() - distance);
                        case QfZy2gWnKInxFPr -> new GoalBlock(state.qhulAUwd() + distance, state.BrmKXSYhWhqoB1(), state.TXWyNb() - distance);
                        default -> new GoalBlock(blockage.getX(), state.BrmKXSYhWhqoB1(), blockage.getZ());
                     };
                     obf.GyhoRo.fStlZIvY9lS8(baritoneGoal);
                     return;
                  }

                  state.vGAMA89H(false);
                  BlockPos nonObsidianBlock = null;

                  for (BlockPos currentPosx : gVOhtBej1x.TJImwK2(-3, 4, HighwayBuilder.xileXsbo(), HighwayBuilder.nOwp0NYl())) {
                     Block block = fStlZIvY9lS8.world.getBlockState(currentPosx).getBlock();
                     if ((HighwayBuilder.j376sIQ() || block != Blocks.CRYING_OBSIDIAN)
                        && block != HighwayBuilder.V7zKJ4n6D()
                        && oX7ku.QfZy2gWnKInxFPr() > 10
                        && (block != Blocks.CRYING_OBSIDIAN || HighwayBuilder.j376sIQ())
                        && block != Blocks.BEDROCK
                        && !blacklistedBlocks.contains(block)) {
                        state.vGAMA89H(true);
                        nonObsidianBlock = currentPosx;
                        break;
                     }
                  }

                  if (HighwayBuilder.zXcO5rZ()) {
                     for (BlockPos currentPosxx : gVOhtBej1x.wSJeYsJcmjNZf(-3, 4, HighwayBuilder.xileXsbo(), HighwayBuilder.nOwp0NYl())) {
                        Block block = fStlZIvY9lS8.world.getBlockState(currentPosxx).getBlock();
                        if ((HighwayBuilder.j376sIQ() || block != Blocks.CRYING_OBSIDIAN)
                           && block == Blocks.AIR
                           && oX7ku.QfZy2gWnKInxFPr() > 10
                           && !blacklistedBlocks.contains(block)) {
                           state.vGAMA89H(true);
                           nonObsidianBlock = currentPosxx;
                           break;
                        }
                     }
                  }

                  if (state.xileXsbo()) {
                     irzXX.fStlZIvY9lS8(false);
                     MusheorSystem.debug("Found missing block, going back...");

                     assert nonObsidianBlock != null;

                     RenderUtils.renderTickingBlock(nonObsidianBlock, Color.WHITE, Color.WHITE, ShapeMode.Lines, 0, 20, true, false);

                     Goal baritoneGoal = switch (HighwayBuilder.i2Nu6XHKic()) {
                        case vGAMA89H -> new GoalBlock(state.qhulAUwd() - 8, state.BrmKXSYhWhqoB1(), state.TXWyNb() + 8);
                        case wSJeYsJcmjNZf -> new GoalBlock(state.qhulAUwd() + 8, state.BrmKXSYhWhqoB1(), state.TXWyNb() + 8);
                        case BrmKXSYhWhqoB1 -> new GoalBlock(state.qhulAUwd() - 8, state.BrmKXSYhWhqoB1(), state.TXWyNb() - 8);
                        case QfZy2gWnKInxFPr -> new GoalBlock(state.qhulAUwd() + 8, state.BrmKXSYhWhqoB1(), state.TXWyNb() - 8);
                        default -> new GoalBlock(nonObsidianBlock.getX(), state.BrmKXSYhWhqoB1(), nonObsidianBlock.getZ());
                     };
                     obf.GyhoRo.fStlZIvY9lS8(baritoneGoal);
                     return;
                  }
               }

               BlockPos[] positions = gVOhtBej1x.TJImwK2(2, 2, HighwayBuilder.xileXsbo(), HighwayBuilder.nOwp0NYl());
               if ((Boolean)HighwayBuilder.TJImwK2.QfZy2gWnKInxFPr.get()) {
                  KekNuker.vGAMA89H.clear();
               }

               state.qhulAUwd(false);
               oyDPiOVBGKG.fStlZIvY9lS8(positions);
               if ((Boolean)HighwayBuilder.TJImwK2.QfZy2gWnKInxFPr.get()) {
                  for (BlockPos pos : positions) {
                     Block block = fStlZIvY9lS8.world.getBlockState(pos).getBlock();
                     if (block != Blocks.NETHER_PORTAL
                        && block != Blocks.BEDROCK
                        && (HighwayBuilder.j376sIQ() || block != Blocks.CRYING_OBSIDIAN)
                        && oyDPiOVBGKG.fStlZIvY9lS8(pos, HighwayBuilder.V7zKJ4n6D())) {
                        KekNuker.vGAMA89H.add(pos.toImmutable());
                     }
                  }

                  for (BlockPos posx : gVOhtBej1x.QfZy2gWnKInxFPr(2, 1)) {
                     Block block = fStlZIvY9lS8.world.getBlockState(posx).getBlock();
                     if (block != Blocks.NETHER_PORTAL && block != Blocks.BEDROCK && oyDPiOVBGKG.fStlZIvY9lS8(posx, Blocks.AIR)) {
                        KekNuker.vGAMA89H.add(posx.toImmutable());
                     }
                  }
               }

               state.i2Nu6XHKic(false);
               oyDPiOVBGKG.fStlZIvY9lS8(positions, false);
               BlockPos[] ceilingPositions = gVOhtBej1x.wSJeYsJcmjNZf(2, 1, HighwayBuilder.xileXsbo(), HighwayBuilder.nOwp0NYl());
               if (HighwayBuilder.zXcO5rZ()) {
                  for (BlockPos posxx : ceilingPositions) {
                     if (oyDPiOVBGKG.TJImwK2(posxx) && BlockUtils.canPlace(posxx, true)) {
                        irzXX.fStlZIvY9lS8(false);
                     }
                  }

                  oyDPiOVBGKG.fStlZIvY9lS8(ceilingPositions, false);
               }

               if (!state.voxTwgNzyrpaAnP() || !state.V7zKJ4n6D() || !state.xileXsbo() || !KekNuker.TJImwK2() || !state.nOwp0NYl()) {
                  irzXX.fStlZIvY9lS8(true);
               }
            } else {
               BrmKXSYhWhqoB1();
               irzXX.fStlZIvY9lS8(false);
            }
         }
      }
   }

   public static void i2Nu6XHKic() {
      if (fStlZIvY9lS8.player != null && fStlZIvY9lS8.world != null) {
         obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
         if (!irzXX.qhulAUwd()
            && !HighwayBuilder.TJImwK2()
            && !musheor.utils.manager.pvfA15fQSVM.TJImwK2
            && !HighwayBuilder.wSJeYsJcmjNZf()
            && (!SourceRemover.fStlZIvY9lS8() || !Modules.get().get("source-remover").isActive())) {
            if (fStlZIvY9lS8.player.getY() < state.BrmKXSYhWhqoB1().intValue()) {
               irzXX.fStlZIvY9lS8(false);
            }

            if (HighwayBuilder.Nt3xeF()) {
               oyDPiOVBGKG.i2Nu6XHKic();
            }

            irzXX.i2Nu6XHKic();
            irzXX.BrmKXSYhWhqoB1();
            oyDPiOVBGKG.TXWyNb();
            if (HighwayBuilder.zkcZ4Xkp9Ez7Y() != HighwayBuilder.ScaffoldMode.fStlZIvY9lS8) {
               if (musheor.utils.manager.pvfA15fQSVM.wSJeYsJcmjNZf(HighwayBuilder.umvNbb7iD9UpR().asItem()) == null) {
                  state.i2Nu6XHKic(fStlZIvY9lS8.player.getBlockPos());
                  oyDPiOVBGKG.QfZy2gWnKInxFPr(HighwayBuilder.umvNbb7iD9UpR().asItem());
                  return;
               }

               if (state.EVSMbf() != null && musheor.utils.manager.pvfA15fQSVM.wSJeYsJcmjNZf(HighwayBuilder.umvNbb7iD9UpR().asItem()) != null) {
                  obf.GyhoRo.fStlZIvY9lS8(state.EVSMbf());
               }

               state.i2Nu6XHKic(null);
               if (obf.GyhoRo.fStlZIvY9lS8()) {
                  return;
               }
            }

            if (HighwayBuilder.zkcZ4Xkp9Ez7Y() == HighwayBuilder.ScaffoldMode.TJImwK2) {
               oyDPiOVBGKG.fStlZIvY9lS8(gVOhtBej1x.fStlZIvY9lS8(3, 2, HighwayBuilder.flWHzEyn(), HighwayBuilder.FVjbhYkk53UT()), true);
            }

            if (HighwayBuilder.zkcZ4Xkp9Ez7Y() == HighwayBuilder.ScaffoldMode.QfZy2gWnKInxFPr) {
               oyDPiOVBGKG.BrmKXSYhWhqoB1();
            }

            for (BlockPos currentPos : gVOhtBej1x.fStlZIvY9lS8(3, 1)) {
               BlockState blockState = fStlZIvY9lS8.world.getBlockState(currentPos);
               if (!(fStlZIvY9lS8.world.getBlockState(currentPos).getBlock() instanceof AirBlock)
                  && blockState.getBlock() != Blocks.NETHER_PORTAL
                  && blockState.getBlock() != Blocks.BEDROCK
                  && oyDPiOVBGKG.fStlZIvY9lS8(currentPos, Blocks.AIR)) {
                  KekNuker.vGAMA89H.add(currentPos.toImmutable());
               }
            }

            for (BlockPos currentPosx : gVOhtBej1x.fStlZIvY9lS8()) {
               if (!(fStlZIvY9lS8.world.getBlockState(currentPosx).getBlock() instanceof AirBlock) && oX7ku.fStlZIvY9lS8 > 5) {
                  boolean hasLavaNeighbor = false;

                  for (Direction direction : Direction.values()) {
                     BlockPos neighborPos = currentPosx.offset(direction);
                     FluidState neighborFluid = fStlZIvY9lS8.world.getFluidState(neighborPos);
                     if (neighborFluid.getFluid() == Fluids.LAVA) {
                        hasLavaNeighbor = true;
                        break;
                     }
                  }

                  if (!hasLavaNeighbor) {
                     irzXX.fStlZIvY9lS8(false);

                     GoalXZ goal = switch (HighwayBuilder.i2Nu6XHKic()) {
                        case i2Nu6XHKic, fStlZIvY9lS8 -> new GoalXZ(state.qhulAUwd(), currentPosx.getZ());
                        case qhulAUwd, TJImwK2 -> new GoalXZ(currentPosx.getX(), state.TXWyNb());
                        default -> new GoalXZ(currentPosx.getX(), currentPosx.getZ());
                     };
                     obf.GyhoRo.fStlZIvY9lS8(goal);
                     return;
                  }
               } else {
                  irzXX.fStlZIvY9lS8(true);
               }
            }
         } else {
            irzXX.fStlZIvY9lS8(false);
         }
      }
   }

   public static void vGAMA89H() {
      if (fStlZIvY9lS8.player != null && fStlZIvY9lS8.world != null) {
         obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
         if (state.wSJeYsJcmjNZf() == null) {
            ChatUtils.error("Set player center using .center", new Object[0]);
            irzXX.TJImwK2();
         } else {
            double dx = fStlZIvY9lS8.player.getBlockX() - state.wSJeYsJcmjNZf().getX();
            double dz = fStlZIvY9lS8.player.getBlockZ() - state.wSJeYsJcmjNZf().getZ();
            if (Math.abs(dx) == Math.abs(dz)) {
               state.fStlZIvY9lS8(Integer.valueOf(fStlZIvY9lS8.player.getBlockX()));
               state.TJImwK2(Integer.valueOf(fStlZIvY9lS8.player.getBlockZ()));
            }

            List<Block> nukerBlacklist = KekNuker.QfZy2gWnKInxFPr();
            Map<String, Boolean> conditions = new LinkedHashMap<>();
            conditions.put("Gathering Items", irzXX.qhulAUwd());
            conditions.put("Removing Lava", SourceRemover.fStlZIvY9lS8());
            conditions.put("Eating", HighwayBuilder.TJImwK2());
            if (!HighwayBuilder.TJImwK2() && !irzXX.qhulAUwd() && !SourceRemover.fStlZIvY9lS8() && !HighwayBuilder.wSJeYsJcmjNZf()) {
               if (HighwayBuilder.Nt3xeF()) {
                  oyDPiOVBGKG.i2Nu6XHKic();
               }

               irzXX.i2Nu6XHKic();
               irzXX.TXWyNb();
               oyDPiOVBGKG.TXWyNb();
               if (HighwayBuilder.zkcZ4Xkp9Ez7Y() == HighwayBuilder.ScaffoldMode.TJImwK2) {
                  if (musheor.utils.manager.pvfA15fQSVM.wSJeYsJcmjNZf(Items.NETHERRACK) == null) {
                     oyDPiOVBGKG.QfZy2gWnKInxFPr(Items.NETHERRACK);
                  }

                  oyDPiOVBGKG.fStlZIvY9lS8(gVOhtBej1x.TJImwK2(2, 1, HighwayBuilder.xileXsbo(), HighwayBuilder.nOwp0NYl()), true);
               }

               if (HighwayBuilder.zkcZ4Xkp9Ez7Y() == HighwayBuilder.ScaffoldMode.QfZy2gWnKInxFPr) {
                  if (musheor.utils.manager.pvfA15fQSVM.wSJeYsJcmjNZf(Items.NETHERRACK) == null) {
                     oyDPiOVBGKG.QfZy2gWnKInxFPr(Items.NETHERRACK);
                  }

                  oyDPiOVBGKG.BrmKXSYhWhqoB1();
               }

               if (!KekNuker.TJImwK2()) {
                  if (oyDPiOVBGKG.I3psabmOrOI()) {
                     MusheorSystem.debug("Placing block so player doesn't fall...");
                     irzXX.fStlZIvY9lS8(false);
                  } else {
                     for (BlockPos currentPos : gVOhtBej1x.QfZy2gWnKInxFPr(3, 1)) {
                        BlockState blockState = fStlZIvY9lS8.world.getBlockState(currentPos);
                        if (!(fStlZIvY9lS8.world.getBlockState(currentPos).getBlock() instanceof AirBlock)
                           && blockState.getBlock() != Blocks.NETHER_PORTAL
                           && blockState.getBlock() != Blocks.BEDROCK
                           && oyDPiOVBGKG.fStlZIvY9lS8(currentPos, Blocks.AIR)) {
                           KekNuker.vGAMA89H.add(currentPos.toImmutable());
                        }
                     }

                     for (BlockPos currentPosx : gVOhtBej1x.QfZy2gWnKInxFPr(-2, 5)) {
                        if (!(fStlZIvY9lS8.world.getBlockState(currentPosx).getBlock() instanceof AirBlock) && oX7ku.fStlZIvY9lS8 > 5) {
                           boolean hasLavaNeighbor = false;

                           for (Direction direction : Direction.values()) {
                              BlockPos neighborPos = currentPosx.offset(direction);
                              FluidState neighborFluid = fStlZIvY9lS8.world.getFluidState(neighborPos);
                              if (neighborFluid.getFluid() == Fluids.LAVA) {
                                 hasLavaNeighbor = true;
                                 break;
                              }
                           }

                           if (!hasLavaNeighbor && !nukerBlacklist.contains(fStlZIvY9lS8.world.getBlockState(currentPosx).getBlock())) {
                              MusheorSystem.debug(
                                 "Detected obstruction behind player... "
                                    + Registries.BLOCK.getId(fStlZIvY9lS8.world.getBlockState(currentPosx).getBlock()).getPath()
                                    + "at %s %s %s",
                                 currentPosx.getX(),
                                 currentPosx.getY(),
                                 currentPosx.getZ()
                              );
                              irzXX.fStlZIvY9lS8(false);
                              obf.GyhoRo.fStlZIvY9lS8(new GoalXZ(currentPosx.getX(), currentPosx.getX()));
                              return;
                           }
                        } else {
                           irzXX.fStlZIvY9lS8(true);
                        }
                     }
                  }
               }
            } else {
               irzXX.fStlZIvY9lS8(false);

               for (Entry<String, Boolean> entry : conditions.entrySet()) {
                  if (entry.getValue()) {
                     MusheorSystem.debug(entry.getKey() + " was triggered.");
                  }
               }
            }
         }
      }
   }

   private static boolean fStlZIvY9lS8(obf.pvfA15fQSVM.gVOhtBej1x d, int px, int pz) {
      double D = Math.abs(d.qhulAUwd);
      double K = d.qhulAUwd;
      switch (d.i2Nu6XHKic) {
         case i2Nu6XHKic:
            return pz <= -D + 2.0;
         case qhulAUwd:
            return px >= D - 2.0;
         case fStlZIvY9lS8:
            return pz >= D - 2.0;
         case TJImwK2:
            return px <= -D + 2.0;
         case vGAMA89H:
            return K > 0.0 ? pz <= 2 : px >= -2;
         case wSJeYsJcmjNZf:
            return K > 0.0 ? px <= 2 : pz <= 2;
         case BrmKXSYhWhqoB1:
            return K > 0.0 ? pz >= -2 : px >= -2;
         case QfZy2gWnKInxFPr:
            return K > 0.0 ? px <= 2 : pz >= -2;
         default:
            return false;
      }
   }

   private static int[] fStlZIvY9lS8(obf.pvfA15fQSVM.gVOhtBej1x d) {
      int D = (int)Math.abs(d.qhulAUwd);
      int K = (int)d.qhulAUwd;
      int ax = (int)d.qhulAUwd;
      switch (d.i2Nu6XHKic) {
         case i2Nu6XHKic:
            return new int[]{ax, -D};
         case qhulAUwd:
            return new int[]{D, ax};
         case fStlZIvY9lS8:
            return new int[]{ax, D};
         case TJImwK2:
            return new int[]{-D, ax};
         case vGAMA89H:
            return K > 0 ? new int[]{K, 0} : new int[]{0, K};
         case wSJeYsJcmjNZf:
            return K > 0 ? new int[]{0, -K} : new int[]{K, 0};
         case BrmKXSYhWhqoB1:
            return K > 0 ? new int[]{K, 0} : new int[]{0, -K};
         case QfZy2gWnKInxFPr:
            return K > 0 ? new int[]{0, K} : new int[]{K, 0};
         default:
            return null;
      }
   }

   private static oyDPiOVBGKG.gVOhtBej1x QfZy2gWnKInxFPr(obf.pvfA15fQSVM.gVOhtBej1x d) {
      boolean pos = d.qhulAUwd > 0.0;
      switch (d.i2Nu6XHKic) {
         case i2Nu6XHKic:
         case fStlZIvY9lS8:
            return pos ? oyDPiOVBGKG.gVOhtBej1x.TJImwK2 : oyDPiOVBGKG.gVOhtBej1x.qhulAUwd;
         case qhulAUwd:
         case TJImwK2:
            return pos ? oyDPiOVBGKG.gVOhtBej1x.i2Nu6XHKic : oyDPiOVBGKG.gVOhtBej1x.fStlZIvY9lS8;
         case vGAMA89H:
         case QfZy2gWnKInxFPr:
            return pos ? oyDPiOVBGKG.gVOhtBej1x.wSJeYsJcmjNZf : oyDPiOVBGKG.gVOhtBej1x.BrmKXSYhWhqoB1;
         case wSJeYsJcmjNZf:
         case BrmKXSYhWhqoB1:
            return pos ? oyDPiOVBGKG.gVOhtBej1x.QfZy2gWnKInxFPr : oyDPiOVBGKG.gVOhtBej1x.vGAMA89H;
         default:
            return null;
      }
   }

   private static boolean fStlZIvY9lS8(obf.pvfA15fQSVM.gVOhtBej1x detected, obf.U6PQSXqW state) {
      assert fStlZIvY9lS8.player != null;

      int px = fStlZIvY9lS8.player.getBlockX();
      int pz = fStlZIvY9lS8.player.getBlockZ();
      if (state.BrmKXSYhWhqoB1() == null) {
         return false;
      } else {
         int py = state.BrmKXSYhWhqoB1();
         if (!fStlZIvY9lS8(detected, px, pz)) {
            return false;
         } else {
            int[] corner = fStlZIvY9lS8(detected);
            if (corner == null) {
               return false;
            } else {
               int cx = corner[0];
               int cz = corner[1];
               double dist = Math.hypot(px - cx, pz - cz);
               if (dist > 1.0) {
                  irzXX.fStlZIvY9lS8(false);
                  obf.GyhoRo.fStlZIvY9lS8(new GoalBlock(cx, py, cz));
                  return true;
               } else {
                  oyDPiOVBGKG.gVOhtBej1x nextDir = QfZy2gWnKInxFPr(detected);
                  if (nextDir == null) {
                     return false;
                  } else {
                     obf.pvfA15fQSVM.gVOhtBej1x newDetected = obf.pvfA15fQSVM.fStlZIvY9lS8(
                        fStlZIvY9lS8.player.getBlockX(), fStlZIvY9lS8.player.getBlockZ(), py, nextDir
                     );
                     if (newDetected == null) {
                        return false;
                     } else {
                        state.fStlZIvY9lS8(newDetected);
                        state.fStlZIvY9lS8(newDetected.i2Nu6XHKic);
                        state.fStlZIvY9lS8(newDetected.I3psabmOrOI);
                        state.QfZy2gWnKInxFPr(newDetected.duJ6sPeetAcBh6P);
                        state.fStlZIvY9lS8(newDetected.BrmKXSYhWhqoB1);
                        state.QfZy2gWnKInxFPr(newDetected.TXWyNb);
                        state.fStlZIvY9lS8(Integer.valueOf(fStlZIvY9lS8.player.getBlockX()));
                        state.TJImwK2(Integer.valueOf(fStlZIvY9lS8.player.getBlockZ()));
                        irzXX.i2Nu6XHKic();
                        return true;
                     }
                  }
               }
            }
         }
      }
   }

   public static boolean qhulAUwd() {
      return i2Nu6XHKic;
   }

   private static boolean fStlZIvY9lS8(boolean isCardinal, int distance) {
      if (fStlZIvY9lS8.world == null) {
         return false;
      } else {
         List<Block> blacklist = KekNuker.QfZy2gWnKInxFPr();
         BlockPos[] floor = isCardinal
            ? gVOhtBej1x.fStlZIvY9lS8(distance, 1, HighwayBuilder.xileXsbo(), HighwayBuilder.nOwp0NYl())
            : gVOhtBej1x.TJImwK2(distance, 1, HighwayBuilder.xileXsbo(), HighwayBuilder.nOwp0NYl());

         for (BlockPos pos : floor) {
            Block block = fStlZIvY9lS8.world.getBlockState(pos).getBlock();
            if (!blacklist.contains(block)
               && (HighwayBuilder.j376sIQ() || block != Blocks.CRYING_OBSIDIAN)
               && block != Blocks.BEDROCK
               && block != HighwayBuilder.V7zKJ4n6D()) {
               return false;
            }
         }

         BlockPos[] tunnel = isCardinal ? gVOhtBej1x.fStlZIvY9lS8(distance, 1) : gVOhtBej1x.QfZy2gWnKInxFPr(distance, 1);

         for (BlockPos posx : tunnel) {
            Block block = fStlZIvY9lS8.world.getBlockState(posx).getBlock();
            if (!blacklist.contains(block)
               && block != Blocks.BEDROCK
               && block != Blocks.NETHER_PORTAL
               && !fStlZIvY9lS8.world.getBlockState(posx).isAir()) {
               return false;
            }
         }

         return true;
      }
   }

   private static boolean TJImwK2(obf.pvfA15fQSVM.gVOhtBej1x d) {
      if (fStlZIvY9lS8.player == null) {
         return false;
      } else {
         int px = fStlZIvY9lS8.player.getBlockX();
         int pz = fStlZIvY9lS8.player.getBlockZ();
         double D = Math.abs(d.qhulAUwd);
         double K = d.qhulAUwd;
         int m = 32;

         return switch (d.i2Nu6XHKic) {
            case i2Nu6XHKic -> pz <= -D + m;
            case qhulAUwd -> px >= D - m;
            case fStlZIvY9lS8 -> pz >= D - m;
            case TJImwK2 -> px <= -D + m;
            case vGAMA89H -> K > 0.0 ? pz <= m : px >= -m;
            case wSJeYsJcmjNZf -> K > 0.0 ? px <= m : pz <= m;
            case BrmKXSYhWhqoB1 -> K > 0.0 ? pz >= -m : px >= -m;
            case QfZy2gWnKInxFPr -> K > 0.0 ? px <= m : pz >= -m;
            default -> false;
         };
      }
   }

   private static void duJ6sPeetAcBh6P() {
      irzXX.fStlZIvY9lS8(KekBounce.class, "mode", KekBounce.Mode.fStlZIvY9lS8);
      irzXX.fStlZIvY9lS8(KekBounce.class, "y-motion", false);
      if (fStlZIvY9lS8.player != null) {
         irzXX.fStlZIvY9lS8(KekBounce.class, "y-level", fStlZIvY9lS8.player.getBlockY());
      }
   }

   private static void j376sIQ() {
      if (!i2Nu6XHKic) {
         if (fStlZIvY9lS8.player != null) {
            boolean hasElytra = false;

            for (int i = 0; i < fStlZIvY9lS8.player.getInventory().size(); i++) {
               if (fStlZIvY9lS8.player.getInventory().getStack(i).getItem() == Items.ELYTRA) {
                  hasElytra = true;
                  break;
               }
            }

            if (hasElytra) {
               duJ6sPeetAcBh6P();
               Module kekBounce = Modules.get().get(KekBounce.class);
               if (!kekBounce.isActive()) {
                  kekBounce.toggle();
               }

               i2Nu6XHKic = true;
               vGAMA89H = 0;
            }
         }
      }
   }

   public static void BrmKXSYhWhqoB1() {
      if (i2Nu6XHKic) {
         Module kekBounce = Modules.get().get(KekBounce.class);
         if (kekBounce.isActive()) {
            kekBounce.toggle();
         }

         i2Nu6XHKic = false;
         vGAMA89H = 20;
      }
   }

   public static void TXWyNb() {
      i2Nu6XHKic = false;
      vGAMA89H = 0;
      TXWyNb = false;
      I3psabmOrOI = 0;
      duJ6sPeetAcBh6P = 0;
   }

   public static boolean fStlZIvY9lS8(obf.U6PQSXqW state) {
      if (fStlZIvY9lS8.player == null || fStlZIvY9lS8.world == null) {
         return false;
      } else if (state.BrmKXSYhWhqoB1() != null && state.TJImwK2() != null) {
         Module echestFarmer = Modules.get().get(EchestFarmer.class);
         if (!echestFarmer.isActive()
            && !irzXX.qhulAUwd()
            && !SourceRemover.fStlZIvY9lS8()
            && !musheor.utils.manager.pvfA15fQSVM.TJImwK2
            && !state.j376sIQ()
            && !state.umvNbb7iD9UpR()
            && !state.zkcZ4Xkp9Ez7Y()
            && !state.flWHzEyn()) {
            if ((i2Nu6XHKic || vGAMA89H > 0) && !TXWyNb) {
               I3psabmOrOI = 0;
               return false;
            } else {
               int targetY = state.BrmKXSYhWhqoB1();
               boolean atCorrectY = fStlZIvY9lS8.player.getBlockY() == targetY;
               if (!TXWyNb) {
                  if (atCorrectY) {
                     I3psabmOrOI = 0;
                     return false;
                  }

                  if (++I3psabmOrOI < 15) {
                     return false;
                  }

                  TXWyNb = true;
                  duJ6sPeetAcBh6P = 0;
                  BrmKXSYhWhqoB1();
                  irzXX.fStlZIvY9lS8(false);
                  MusheorSystem.debug("Fell off the highway (Y %s != %s) — pathing back...", fStlZIvY9lS8.player.getBlockY(), targetY);
               }

               if (atCorrectY && fStlZIvY9lS8.player.isOnGround()) {
                  TXWyNb = false;
                  I3psabmOrOI = 0;
                  if (obf.GyhoRo.fStlZIvY9lS8()) {
                     obf.GyhoRo.wSJeYsJcmjNZf();
                  }

                  MusheorSystem.debug("Back on the highway — resuming paving.");
                  return false;
               } else {
                  if (duJ6sPeetAcBh6P <= 0) {
                     fStlZIvY9lS8(state, targetY);
                     duJ6sPeetAcBh6P = 40;
                  } else {
                     duJ6sPeetAcBh6P--;
                  }

                  return true;
               }
            }
         } else {
            I3psabmOrOI = 0;
            TXWyNb = false;
            return false;
         }
      } else {
         return false;
      }
   }

   private static void fStlZIvY9lS8(obf.U6PQSXqW state, int targetY) {
      BlockPos nearest = fStlZIvY9lS8(targetY, 24);
      if (nearest != null) {
         obf.GyhoRo.fStlZIvY9lS8(new GoalBlock(nearest));
      } else {
         if (state.qhulAUwd() != null && state.TXWyNb() != null) {
            obf.GyhoRo.fStlZIvY9lS8(new GoalBlock(state.qhulAUwd(), targetY, state.TXWyNb()));
         }
      }
   }

   private static BlockPos fStlZIvY9lS8(int targetY, int range) {
      if (fStlZIvY9lS8.player != null && fStlZIvY9lS8.world != null) {
         BlockPos p = fStlZIvY9lS8.player.getBlockPos();
         Block pavement = HighwayBuilder.V7zKJ4n6D();
         BlockPos best = null;
         double bestDistSq = Double.MAX_VALUE;

         for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
               BlockPos stand = new BlockPos(p.getX() + x, targetY, p.getZ() + z);
               if (fStlZIvY9lS8.world.getBlockState(stand.down()).getBlock() == pavement
                  && fStlZIvY9lS8.world.getBlockState(stand).isAir()
                  && fStlZIvY9lS8.world.getBlockState(stand.up()).isAir()) {
                  double distSq = p.getSquaredDistance(stand);
                  if (distSq < bestDistSq) {
                     bestDistSq = distSq;
                     best = stand;
                  }
               }
            }
         }

         return best;
      } else {
         return null;
      }
   }

   public static void I3psabmOrOI() {
      assert fStlZIvY9lS8.player != null && fStlZIvY9lS8.world != null;

      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      if (state.BrmKXSYhWhqoB1() != null && state.TJImwK2() != null) {
         obf.pvfA15fQSVM.gVOhtBej1x detected = state.npfimrDr0hky();
         if (detected != null) {
            if (!fStlZIvY9lS8(state)) {
               boolean isCardinal = detected.wSJeYsJcmjNZf == HighwayBuilder.HighwayType.fStlZIvY9lS8;
               boolean isRingOrDiamond = detected.TJImwK2 == obf.pvfA15fQSVM.pvfA15fQSVM.QfZy2gWnKInxFPr
                  || detected.TJImwK2 == obf.pvfA15fQSVM.pvfA15fQSVM.wSJeYsJcmjNZf;
               if (isRingOrDiamond && fStlZIvY9lS8(detected, state)) {
                  BrmKXSYhWhqoB1();
               }

               Module betterEChestFarmer = Modules.get().get(EchestFarmer.class);
               if (!betterEChestFarmer.isActive() && !irzXX.qhulAUwd() && !SourceRemover.fStlZIvY9lS8()) {
                  if (KekNuker.TJImwK2() || HighwayBuilder.TJImwK2() || HighwayBuilder.wSJeYsJcmjNZf() || musheor.utils.manager.pvfA15fQSVM.TJImwK2) {
                     BrmKXSYhWhqoB1();
                     irzXX.fStlZIvY9lS8(false);
                  }

                  if (HighwayBuilder.Nt3xeF()) {
                     oyDPiOVBGKG.i2Nu6XHKic();
                  }

                  if (HighwayBuilder.WGmfQKK()) {
                     if (vGAMA89H > 0) {
                        vGAMA89H--;
                        return;
                     }

                     boolean nearLegEnd = isRingOrDiamond && TJImwK2(detected);
                     boolean interrupted = KekNuker.TJImwK2()
                        || HighwayBuilder.TJImwK2()
                        || HighwayBuilder.wSJeYsJcmjNZf()
                        || musheor.utils.manager.pvfA15fQSVM.TJImwK2
                        || irzXX.qhulAUwd()
                        || SourceRemover.fStlZIvY9lS8()
                        || betterEChestFarmer.isActive()
                        || obf.GyhoRo.fStlZIvY9lS8();
                     if (!nearLegEnd && !interrupted && fStlZIvY9lS8(isCardinal, HighwayBuilder.icQ738dpWr7W())) {
                        j376sIQ();
                     } else {
                        BrmKXSYhWhqoB1();
                     }

                     if (i2Nu6XHKic) {
                        irzXX.vGAMA89H();
                        irzXX.fStlZIvY9lS8(false);
                        return;
                     }
                  }

                  if (isCardinal) {
                     oyDPiOVBGKG.TXWyNb();
                     irzXX.i2Nu6XHKic();
                     irzXX.BrmKXSYhWhqoB1();
                  } else {
                     boolean isNESW = state.TJImwK2() == oyDPiOVBGKG.gVOhtBej1x.vGAMA89H || state.TJImwK2() == oyDPiOVBGKG.gVOhtBej1x.QfZy2gWnKInxFPr;
                     int playerDiagVal = isNESW
                        ? fStlZIvY9lS8.player.getBlockX() + fStlZIvY9lS8.player.getBlockZ()
                        : fStlZIvY9lS8.player.getBlockX() - fStlZIvY9lS8.player.getBlockZ();
                     if (playerDiagVal == (int)detected.qhulAUwd) {
                        state.fStlZIvY9lS8(Integer.valueOf(fStlZIvY9lS8.player.getBlockX()));
                        state.TJImwK2(Integer.valueOf(fStlZIvY9lS8.player.getBlockZ()));
                     }

                     irzXX.TXWyNb();
                     oyDPiOVBGKG.TXWyNb();
                     irzXX.i2Nu6XHKic();
                  }

                  BlockPos[] allPositions = isCardinal
                     ? gVOhtBej1x.fStlZIvY9lS8(
                        (Integer)MusheorSystem.Manager.highwayForwardRange.get(), 2, HighwayBuilder.xileXsbo(), HighwayBuilder.nOwp0NYl()
                     )
                     : gVOhtBej1x.TJImwK2((Integer)MusheorSystem.Manager.highwayForwardRange.get(), 2, HighwayBuilder.xileXsbo(), HighwayBuilder.nOwp0NYl());
                  List<BlockPos> placePositions = new ArrayList<>();
                  List<BlockPos> clearPositions = new ArrayList<>();

                  for (BlockPos pos : allPositions) {
                     if (pos.getY() != state.BrmKXSYhWhqoB1() || !obf.pvfA15fQSVM.fStlZIvY9lS8(pos, detected)) {
                        placePositions.add(pos);
                     } else if (obf.pvfA15fQSVM.QfZy2gWnKInxFPr(pos, detected)) {
                        clearPositions.add(pos);
                     }
                  }

                  if ((Boolean)HighwayBuilder.TJImwK2.QfZy2gWnKInxFPr.get()) {
                     KekNuker.vGAMA89H.clear();
                  }

                  state.qhulAUwd(false);
                  oyDPiOVBGKG.fStlZIvY9lS8(allPositions);
                  if ((Boolean)HighwayBuilder.TJImwK2.QfZy2gWnKInxFPr.get()) {
                     for (BlockPos posx : placePositions) {
                        Block block = fStlZIvY9lS8.world.getBlockState(posx).getBlock();
                        if (block != Blocks.NETHER_PORTAL
                           && block != Blocks.BEDROCK
                           && (HighwayBuilder.j376sIQ() || block != Blocks.CRYING_OBSIDIAN)
                           && oyDPiOVBGKG.fStlZIvY9lS8(posx, HighwayBuilder.V7zKJ4n6D())) {
                           KekNuker.vGAMA89H.add(posx.toImmutable());
                        }
                     }

                     BlockPos[] tunnelClear = isCardinal
                        ? gVOhtBej1x.fStlZIvY9lS8((Integer)MusheorSystem.Manager.highwayForwardRange.get(), 1)
                        : gVOhtBej1x.QfZy2gWnKInxFPr((Integer)MusheorSystem.Manager.highwayForwardRange.get(), 1);

                     for (BlockPos posxx : tunnelClear) {
                        Block block = fStlZIvY9lS8.world.getBlockState(posxx).getBlock();
                        if (block != Blocks.NETHER_PORTAL && block != Blocks.BEDROCK && oyDPiOVBGKG.fStlZIvY9lS8(posxx, Blocks.AIR)) {
                           KekNuker.vGAMA89H.add(posxx.toImmutable());
                        }
                     }

                     for (BlockPos posxxx : clearPositions) {
                        Block block = fStlZIvY9lS8.world.getBlockState(posxxx).getBlock();
                        if (block != Blocks.NETHER_PORTAL && block != Blocks.BEDROCK && oyDPiOVBGKG.fStlZIvY9lS8(posxxx, Blocks.AIR)) {
                           KekNuker.vGAMA89H.add(posxxx.toImmutable());
                        }
                     }
                  }

                  state.i2Nu6XHKic(false);
                  oyDPiOVBGKG.fStlZIvY9lS8(placePositions.toArray(new BlockPos[0]), false);
                  if (HighwayBuilder.zXcO5rZ()) {
                     BlockPos[] ceilingPositions = isCardinal
                        ? gVOhtBej1x.QfZy2gWnKInxFPr(
                           (Integer)MusheorSystem.Manager.highwayForwardRange.get(), 2, HighwayBuilder.xileXsbo(), HighwayBuilder.nOwp0NYl()
                        )
                        : gVOhtBej1x.wSJeYsJcmjNZf(
                           (Integer)MusheorSystem.Manager.highwayForwardRange.get(), 1, HighwayBuilder.xileXsbo(), HighwayBuilder.nOwp0NYl()
                        );
                     oyDPiOVBGKG.fStlZIvY9lS8(ceilingPositions, false);
                  }

                  if (!state.voxTwgNzyrpaAnP()
                     || !state.V7zKJ4n6D()
                     || !oyDPiOVBGKG.qhulAUwd()
                     || !state.xileXsbo()
                     || !KekNuker.TJImwK2()
                     || !state.voxTwgNzyrpaAnP()) {
                     irzXX.fStlZIvY9lS8(true);
                  }
               } else {
                  BrmKXSYhWhqoB1();
                  irzXX.fStlZIvY9lS8(false);
               }
            }
         }
      }
   }
}
