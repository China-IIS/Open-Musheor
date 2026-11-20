package musheor.modules.automation;

import baritone.api.pathing.goals.GoalBlock;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.settings.BoolSetting.Builder;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import musheor.modules.features.KekMine;
import musheor.musheor;
import musheor.utils.irzXX;
import musheor.utils.manager.pvfA15fQSVM;
import musheor.utils.oyDPiOVBGKG;
import musheor.utils.system.MusheorSystem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import obf.GyhoRo;
import obf.U6PQSXqW;

public class EchestFarmer extends Module {
   private final Setting<Boolean> TJImwK2 = this.settings
      .getDefaultGroup()
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("self-toggle")).description("Disables when you reach the desired amount of ender chests."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Integer> wSJeYsJcmjNZf = this.settings
      .getDefaultGroup()
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("amount"))
                     .description("The amount of ender chests to farm."))
                  .defaultValue(64))
               .sliderRange(1, 64)
               .visible(this.TJImwK2::get))
            .build()
      );
   private final Setting<Boolean> i2Nu6XHKic = this.settings
      .getDefaultGroup()
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("super-farm-20bps"))
                  .description("Uses instant rebreak when mining enderchests to speed up the farming process up to 20 blocks per second."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Integer> vGAMA89H = this.settings
      .getDefaultGroup()
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("rebreak-stall-timeout"))
                     .description("Ticks without progress during instant rebreak before falling back to one normal break."))
                  .defaultValue(40))
               .min(5)
               .sliderRange(10, 200)
               .visible(this.i2Nu6XHKic::get))
            .build()
      );
   public static BlockPos fStlZIvY9lS8;
   private int qhulAUwd;
   private boolean BrmKXSYhWhqoB1 = false;
   private boolean TXWyNb = false;
   private int I3psabmOrOI;
   private int duJ6sPeetAcBh6P;
   private Block j376sIQ;
   private int V7zKJ4n6D;
   private int voxTwgNzyrpaAnP;
   public static EchestFarmer QfZy2gWnKInxFPr;

   public EchestFarmer() {
      super(musheor.AUTOMATION, "obi-farmer", "An enderchest farmer that considers the player's inventory space when mining for obsidian, its also really fast");
      QfZy2gWnKInxFPr = this;
   }

   public void onDeactivate() {
      fStlZIvY9lS8 = null;
      irzXX.fStlZIvY9lS8();
      this.I3psabmOrOI = 0;
   }

   public void onActivate() {
      fStlZIvY9lS8 = null;
      this.qhulAUwd = 0;
      this.BrmKXSYhWhqoB1 = false;
      this.TXWyNb = false;
      this.duJ6sPeetAcBh6P = 0;
      this.j376sIQ = null;
      this.V7zKJ4n6D = 0;
      this.voxTwgNzyrpaAnP = 0;
   }

   @EventHandler
   private void fStlZIvY9lS8(Pre event) {
      if (this.mc.player != null && this.mc.world != null) {
         U6PQSXqW state = U6PQSXqW.fStlZIvY9lS8();
         if (!HighwayBuilder.TJImwK2() && !HighwayBuilder.wSJeYsJcmjNZf() && !SourceRemover.fStlZIvY9lS8() && !GyhoRo.fStlZIvY9lS8()) {
            if (this.I3psabmOrOI > 0) {
               this.I3psabmOrOI--;
            } else {
               if (this.duJ6sPeetAcBh6P > 0 && this.TXWyNb) {
                  this.duJ6sPeetAcBh6P--;
               }

               if (!this.BrmKXSYhWhqoB1) {
                  state.QfZy2gWnKInxFPr(this.mc.player.getBlockPos());
                  if (((HighwayBuilder)Modules.get().get(HighwayBuilder.class)).isActive()) {
                     fStlZIvY9lS8 = oyDPiOVBGKG.fStlZIvY9lS8(HighwayBuilder.i2Nu6XHKic());
                  } else {
                     fStlZIvY9lS8 = oyDPiOVBGKG.fStlZIvY9lS8(oyDPiOVBGKG.duJ6sPeetAcBh6P().fStlZIvY9lS8());
                  }

                  this.BrmKXSYhWhqoB1 = true;
               } else {
                  boolean isEchestHere = this.mc.world.getBlockState(fStlZIvY9lS8).getBlock() == Blocks.ENDER_CHEST;
                  boolean isEmpty = this.mc.world.getBlockState(fStlZIvY9lS8).isReplaceable();
                  boolean isObstructed = !isEchestHere && !isEmpty;
                  boolean notAligned = oyDPiOVBGKG.fStlZIvY9lS8(state.jbUe84V1vykfK(), this.mc.player.getBlockPos()) > 1.25;
                  if (notAligned) {
                     GyhoRo.fStlZIvY9lS8(new GoalBlock(state.jbUe84V1vykfK()));
                  } else if ((!(Boolean)this.TJImwK2.get() || this.qhulAUwd < (Integer)this.wSJeYsJcmjNZf.get()) && pvfA15fQSVM.TJImwK2(false) != 0) {
                     FindItemResult echest = InvUtils.findInHotbar(new Item[]{Items.ENDER_CHEST});
                     if (!echest.found()) {
                        if (pvfA15fQSVM.i2Nu6XHKic(Items.ENDER_CHEST) < 8) {
                           state.j376sIQ(false);
                           this.toggle();
                        } else {
                           pvfA15fQSVM.fStlZIvY9lS8(Items.ENDER_CHEST);
                        }
                     } else {
                        if (this.mc.currentScreen instanceof GenericContainerScreen) {
                           this.mc.currentScreen.close();
                        }

                        if (isObstructed && this.qhulAUwd < 2) {
                           if (KekMine.duJ6sPeetAcBh6P.isActive()) {
                              KekMine.duJ6sPeetAcBh6P.fStlZIvY9lS8(fStlZIvY9lS8, this.mc.world.getBlockState(fStlZIvY9lS8), false);
                           } else {
                              BlockUtils.breakBlock(fStlZIvY9lS8, true);
                           }
                        } else {
                           if ((Boolean)this.i2Nu6XHKic.get()) {
                              if (this.qhulAUwd < 2) {
                                 if (isEmpty && (!this.TXWyNb || this.duJ6sPeetAcBh6P == 0)) {
                                    if (!pvfA15fQSVM.I3psabmOrOI(Items.ENDER_CHEST)) {
                                       this.I3psabmOrOI = (Integer)MusheorSystem.Manager.swapDelay.get();
                                       return;
                                    }

                                    this.fStlZIvY9lS8();
                                    this.TXWyNb = true;
                                    this.duJ6sPeetAcBh6P = 10;
                                 } else if (isEchestHere) {
                                    if (KekMine.duJ6sPeetAcBh6P.isActive()) {
                                       KekMine.duJ6sPeetAcBh6P.fStlZIvY9lS8(fStlZIvY9lS8, this.mc.world.getBlockState(fStlZIvY9lS8), false);
                                    } else {
                                       pvfA15fQSVM.QfZy2gWnKInxFPr(false);
                                       BlockUtils.breakBlock(fStlZIvY9lS8, true);
                                    }

                                    this.TXWyNb = false;
                                    this.duJ6sPeetAcBh6P = 0;
                                 }
                              } else {
                                 Block here = this.mc.world.getBlockState(fStlZIvY9lS8).getBlock();
                                 if (here != this.j376sIQ) {
                                    this.j376sIQ = here;
                                    this.V7zKJ4n6D = 0;
                                 } else if (!isEmpty && this.voxTwgNzyrpaAnP == 0) {
                                    this.V7zKJ4n6D++;
                                 }

                                 if (this.voxTwgNzyrpaAnP > 0) {
                                    if (!isEmpty) {
                                       this.voxTwgNzyrpaAnP--;
                                       pvfA15fQSVM.QfZy2gWnKInxFPr(false);
                                       if (KekMine.duJ6sPeetAcBh6P.isActive()) {
                                          KekMine.duJ6sPeetAcBh6P.fStlZIvY9lS8(fStlZIvY9lS8, this.mc.world.getBlockState(fStlZIvY9lS8), false);
                                       } else {
                                          BlockUtils.breakBlock(fStlZIvY9lS8, true);
                                       }

                                       return;
                                    }

                                    this.voxTwgNzyrpaAnP = 0;
                                 } else if (this.V7zKJ4n6D >= (Integer)this.vGAMA89H.get()) {
                                    MusheorSystem.debug("Rebreak stalled for %s ticks, falling back to a normal break", this.V7zKJ4n6D);
                                    this.V7zKJ4n6D = 0;
                                    this.voxTwgNzyrpaAnP = 40;
                                    return;
                                 }

                                 if (isEmpty && this.duJ6sPeetAcBh6P == 0) {
                                    this.fStlZIvY9lS8();
                                 }

                                 pvfA15fQSVM.QfZy2gWnKInxFPr(false);
                                 KekMine.duJ6sPeetAcBh6P.fStlZIvY9lS8(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, fStlZIvY9lS8);
                              }
                           } else {
                              if (isEchestHere) {
                                 pvfA15fQSVM.QfZy2gWnKInxFPr(false);
                                 if (KekMine.duJ6sPeetAcBh6P.isActive()) {
                                    KekMine.duJ6sPeetAcBh6P.fStlZIvY9lS8(fStlZIvY9lS8, this.mc.world.getBlockState(fStlZIvY9lS8), false);
                                 } else {
                                    BlockUtils.breakBlock(fStlZIvY9lS8, true);
                                 }

                                 this.TXWyNb = false;
                                 this.duJ6sPeetAcBh6P = 0;
                                 return;
                              }

                              if (isEmpty && (!this.TXWyNb || this.duJ6sPeetAcBh6P == 0)) {
                                 if (!pvfA15fQSVM.I3psabmOrOI(Items.ENDER_CHEST)) {
                                    this.I3psabmOrOI = (Integer)MusheorSystem.Manager.swapDelay.get();
                                    return;
                                 }

                                 this.fStlZIvY9lS8();
                                 this.TXWyNb = true;
                                 this.duJ6sPeetAcBh6P = 10;
                              }
                           }
                        }
                     }
                  } else {
                     MusheorSystem.debug("Mining last enderchest...");
                     if (!isEchestHere && !isObstructed) {
                        state.j376sIQ(false);
                        this.toggle();
                     } else {
                        pvfA15fQSVM.QfZy2gWnKInxFPr(false);
                        if (KekMine.duJ6sPeetAcBh6P.isActive()) {
                           KekMine.duJ6sPeetAcBh6P.fStlZIvY9lS8(fStlZIvY9lS8, this.mc.world.getBlockState(fStlZIvY9lS8), false);
                        } else {
                           BlockUtils.breakBlock(fStlZIvY9lS8, true);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void fStlZIvY9lS8() {
      pvfA15fQSVM.fStlZIvY9lS8(Items.ENDER_CHEST, (Boolean)MusheorSystem.Manager.silentPlace.get());
      if (U6PQSXqW.fStlZIvY9lS8().QfZy2gWnKInxFPr().equals(MusheorSystem.PlacementType.Rotate)) {
         oyDPiOVBGKG.TJImwK2(fStlZIvY9lS8, Direction.DOWN);
      }

      obf.irzXX.fStlZIvY9lS8(fStlZIvY9lS8, Direction.DOWN, U6PQSXqW.fStlZIvY9lS8().QfZy2gWnKInxFPr(), (Boolean)MusheorSystem.Manager.silentPlace.get());
      this.qhulAUwd++;
   }
}
