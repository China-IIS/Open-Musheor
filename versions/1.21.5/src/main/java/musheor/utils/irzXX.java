package musheor.utils;

import baritone.api.pathing.goals.GoalBlock;
import meteordevelopment.meteorclient.settings.BlockSetting;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.ItemSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.StringSetting;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.misc.AutoReconnect;
import meteordevelopment.meteorclient.systems.modules.movement.AutoWalk;
import meteordevelopment.meteorclient.systems.modules.render.FreeLook.Mode;
import meteordevelopment.meteorclient.systems.modules.render.FreeLook;
import meteordevelopment.meteorclient.utils.misc.input.Input;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import musheor.compat.VersionHelper;
import musheor.modules.automation.GatherItem;
import musheor.modules.automation.HighwayBuilder;
import musheor.modules.tech.MessageInteract;
import musheor.utils.system.MusheorSystem;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.network.packet.c2s.play.CommandExecutionC2SPacket;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class irzXX {
   private static final MinecraftClient QfZy2gWnKInxFPr = MinecraftClient.getInstance();

   public static boolean fStlZIvY9lS8(double x, double y, double z) {
      return PlayerUtils.squaredDistance(
            QfZy2gWnKInxFPr.player.getX(), QfZy2gWnKInxFPr.player.getEyeY(), QfZy2gWnKInxFPr.player.getZ(), x, y, z
         )
         <= QfZy2gWnKInxFPr.player.getBlockInteractionRange() * QfZy2gWnKInxFPr.player.getBlockInteractionRange();
   }

   public static boolean fStlZIvY9lS8(BlockPos blockPos) {
      return fStlZIvY9lS8(blockPos.toCenterPos().getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().getZ());
   }

   public static void fStlZIvY9lS8() {
      obf.GyhoRo.wSJeYsJcmjNZf();
   }

   public static void fStlZIvY9lS8(String reason) {
      assert QfZy2gWnKInxFPr.player != null;

      MutableText text = Text.literal("[Musheor]: " + reason);
      if (((AutoReconnect)Modules.get().get(AutoReconnect.class)).isActive()) {
         ((AutoReconnect)Modules.get().get(AutoReconnect.class)).toggle();
      }

      QfZy2gWnKInxFPr.player.networkHandler.onDisconnect(new DisconnectS2CPacket(text));
   }

   public static void fStlZIvY9lS8(boolean active) {
      if (HighwayBuilder.BrmKXSYhWhqoB1() == HighwayBuilder.Mode.fStlZIvY9lS8) {
         Module autoWalk = Modules.get().get(AutoWalk.class);
         if (active && !autoWalk.isActive()) {
            autoWalk.toggle();
         }

         if (!active && autoWalk.isActive()) {
            autoWalk.toggle();
         }
      }
   }

   public static void fStlZIvY9lS8(KeyBinding key, boolean pressed) {
      key.setPressed(pressed);
      Input.setKeyState(key, pressed);
   }

   public static void QfZy2gWnKInxFPr() {
      fStlZIvY9lS8(QfZy2gWnKInxFPr.options.forwardKey, false);
      fStlZIvY9lS8(QfZy2gWnKInxFPr.options.backKey, false);
      fStlZIvY9lS8(QfZy2gWnKInxFPr.options.leftKey, false);
      fStlZIvY9lS8(QfZy2gWnKInxFPr.options.rightKey, false);
      fStlZIvY9lS8(QfZy2gWnKInxFPr.options.jumpKey, false);
   }

   public static void TJImwK2() {
      HighwayBuilder.fStlZIvY9lS8();
      Module BetterHighwayBuilder = Modules.get().get(HighwayBuilder.class);
      BetterHighwayBuilder.toggle();
   }

   public static void wSJeYsJcmjNZf() {
      if (QfZy2gWnKInxFPr.player != null && QfZy2gWnKInxFPr.world != null) {
         fStlZIvY9lS8(FreeLook.class, "mode", Mode.Camera);
         fStlZIvY9lS8(FreeLook.class, "camera-sensitivity", 8.0);
         fStlZIvY9lS8(FreeLook.class, "arrows-control-opposite", false);
         QfZy2gWnKInxFPr.player.setPitch(15.0F);
         ((FreeLook)Modules.get().get(FreeLook.class)).toggle();
      }
   }

   public static <M extends Module, T> void fStlZIvY9lS8(Class<M> module, String setting, T value) {
      if (Modules.get().get(module) != null) {
         Setting<?> s = Modules.get().get(module).settings.get(setting);
         if (Modules.get().get(module).settings.get(setting) != null) {
            try {
               switch (value) {
                  case Integer integer:
                     ((IntSetting)s).set(integer);
                     break;
                  case Double d:
                     ((DoubleSetting)s).set(d);
                     break;
                  case String string:
                     ((StringSetting)s).set(string);
                     break;
                  case Boolean bool:
                     ((BoolSetting)s).set(bool);
                     break;
                  case Item item:
                     ((ItemSetting)s).set(item);
                     break;
                  case Block block:
                     ((BlockSetting)s).set(block);
                     break;
                  case Enum<?> es:
                     ((EnumSetting)s).set(es);
                     break;
                  case null:
                  default:
               }
            } catch (Exception var13) {
               throw new RuntimeException(var13);
            }
         } else {
            ChatUtils.warning("Setting %d not found", new Object[]{setting});
         }
      } else {
         ChatUtils.warning("Module %d not found", new Object[]{module});
      }
   }

   public static void i2Nu6XHKic() {
      assert QfZy2gWnKInxFPr.player != null;

      QfZy2gWnKInxFPr.player.setPitch(30.0F);
      oyDPiOVBGKG.gVOhtBej1x direction = HighwayBuilder.i2Nu6XHKic();
      QfZy2gWnKInxFPr.player.setYaw(oyDPiOVBGKG.gVOhtBej1x.fStlZIvY9lS8(direction));
      QfZy2gWnKInxFPr.player.setHeadYaw(oyDPiOVBGKG.gVOhtBej1x.fStlZIvY9lS8(direction));
      QfZy2gWnKInxFPr.player.setBodyYaw(oyDPiOVBGKG.gVOhtBej1x.fStlZIvY9lS8(direction));
   }

   public static void vGAMA89H() {
      assert QfZy2gWnKInxFPr.player != null;

      oyDPiOVBGKG.gVOhtBej1x direction = HighwayBuilder.i2Nu6XHKic();
      QfZy2gWnKInxFPr.player.setYaw(oyDPiOVBGKG.gVOhtBej1x.fStlZIvY9lS8(direction));
      QfZy2gWnKInxFPr.player.setHeadYaw(oyDPiOVBGKG.gVOhtBej1x.fStlZIvY9lS8(direction));
      QfZy2gWnKInxFPr.player.setBodyYaw(oyDPiOVBGKG.gVOhtBej1x.fStlZIvY9lS8(direction));
   }

   public static void fStlZIvY9lS8(Item item, boolean paveAfterwards) {
      fStlZIvY9lS8(GatherItem.class, "item", item);
      Module gatherItem = Modules.get().get(GatherItem.class);
      if (!qhulAUwd()) {
         gatherItem.toggle();
      }
   }

   public static void QfZy2gWnKInxFPr(boolean paveAfter) {
   }

   public static void QfZy2gWnKInxFPr(KeyBinding key, boolean pressed) {
      key.setPressed(pressed);
      Input.setKeyState(key, pressed);
   }

   public static boolean qhulAUwd() {
      return ((GatherItem)Modules.get().get(GatherItem.class)).isActive();
   }

   public static void BrmKXSYhWhqoB1() {
      if (QfZy2gWnKInxFPr.player != null && QfZy2gWnKInxFPr.world != null) {
         obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
         boolean pressD = false;
         boolean pressA = false;
         if (state.TJImwK2() == oyDPiOVBGKG.gVOhtBej1x.i2Nu6XHKic || state.TJImwK2() == oyDPiOVBGKG.gVOhtBej1x.fStlZIvY9lS8) {
            double currentX = VersionHelper.get().getPlayerPos().getX();
            if (state.TJImwK2() == oyDPiOVBGKG.gVOhtBej1x.i2Nu6XHKic) {
               if (currentX > state.I3psabmOrOI() + 0.15) {
                  pressA = true;
               } else if (currentX < state.I3psabmOrOI() - 0.15) {
                  pressD = true;
               }
            } else if (currentX < state.I3psabmOrOI() - 0.15) {
               pressA = true;
            } else if (currentX > state.I3psabmOrOI() + 0.15) {
               pressD = true;
            }
         } else if (state.TJImwK2() == oyDPiOVBGKG.gVOhtBej1x.qhulAUwd || state.TJImwK2() == oyDPiOVBGKG.gVOhtBej1x.TJImwK2) {
            double currentZ = VersionHelper.get().getPlayerPos().getZ();
            if (state.TJImwK2() == oyDPiOVBGKG.gVOhtBej1x.qhulAUwd) {
               if (currentZ > state.duJ6sPeetAcBh6P() + 0.15) {
                  pressA = true;
               } else if (currentZ < state.duJ6sPeetAcBh6P() - 0.15) {
                  pressD = true;
               }
            } else if (currentZ < state.duJ6sPeetAcBh6P() - 0.15) {
               pressA = true;
            } else if (currentZ > state.duJ6sPeetAcBh6P() + 0.15) {
               pressD = true;
            }
         }

         QfZy2gWnKInxFPr(QfZy2gWnKInxFPr.options.rightKey, pressD);
         QfZy2gWnKInxFPr(QfZy2gWnKInxFPr.options.leftKey, pressA);
      }
   }

   public static void TXWyNb() {
      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      if (state.zbN84Osn9p5lD() == null) {
         assert QfZy2gWnKInxFPr.player != null;

         if (state.qhulAUwd() != null && state.BrmKXSYhWhqoB1() != null && state.TXWyNb() != null) {
            if (state.TJImwK2() != null) {
               int ax = state.qhulAUwd();
               int ay = state.BrmKXSYhWhqoB1();
               int az = state.TXWyNb();
               boolean isNESW = state.TJImwK2() == oyDPiOVBGKG.gVOhtBej1x.vGAMA89H || state.TJImwK2() == oyDPiOVBGKG.gVOhtBej1x.QfZy2gWnKInxFPr;
               obf.pvfA15fQSVM.gVOhtBej1x detected = state.npfimrDr0hky();
               int K = detected != null ? (int)detected.qhulAUwd : (isNESW ? ax + az : ax - az);
               int px = QfZy2gWnKInxFPr.player.getBlockX();
               int pz = QfZy2gWnKInxFPr.player.getBlockZ();
               int diagVal = isNESW ? px + pz : px - pz;
               if (diagVal != K) {
                  int tx;
                  int tz;
                  if (isNESW) {
                     tx = (int)Math.round((px - pz + K) / 2.0);
                     tz = K - tx;
                  } else {
                     tx = (int)Math.round((px + pz + K) / 2.0);
                     tz = tx - K;
                  }

                  obf.GyhoRo.fStlZIvY9lS8(new GoalBlock(tx, ay, tz));
                  MusheorSystem.debug("Aligning player to the diagonal axis at %s %s", tx, tz);
               }
            }
         }
      }
   }

   public static void I3psabmOrOI() {
      if (QfZy2gWnKInxFPr.player != null && QfZy2gWnKInxFPr.world != null) {
         obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
         if (state.zbN84Osn9p5lD() == null) {
            oyDPiOVBGKG.gVOhtBej1x dir = state.TJImwK2();
            if (dir != null) {
               boolean travelX = dir == oyDPiOVBGKG.gVOhtBej1x.qhulAUwd || dir == oyDPiOVBGKG.gVOhtBej1x.TJImwK2;
               boolean travelZ = dir == oyDPiOVBGKG.gVOhtBej1x.i2Nu6XHKic || dir == oyDPiOVBGKG.gVOhtBej1x.fStlZIvY9lS8;
               if (travelX || travelZ) {
                  BlockPos lane = oyDPiOVBGKG.TJImwK2();
                  if (lane != null) {
                     int currentPerp = travelX ? QfZy2gWnKInxFPr.player.getBlockZ() : QfZy2gWnKInxFPr.player.getBlockX();
                     int lanePerp = travelX ? lane.getZ() : lane.getX();
                     if (Math.abs(currentPerp - lanePerp) >= 2) {
                        obf.GyhoRo.fStlZIvY9lS8(new GoalBlock(lane));
                        MusheorSystem.debug("Aligning player to the cardinal highway lane at %s %s", lane.getX(), lane.getZ());
                     }
                  }
               }
            }
         }
      }
   }

   public static void fStlZIvY9lS8(String playerIGN, int length) {
      String command = "!tp " + MessageInteract.fStlZIvY9lS8(length);
      if (!VersionHelper.get().onSameServer(playerIGN)) {
         ChatUtils.error("Cannot find %s, not online?!", new Object[]{playerIGN});
      } else {
         try {
            Class.forName("musheor.plus.PlusPlayerUtils").getMethod("trySendIrcPm", String.class, String.class).invoke(null, playerIGN, command);
         } catch (Exception var4) {
            QfZy2gWnKInxFPr.player.networkHandler.sendPacket(new CommandExecutionC2SPacket("msg " + playerIGN + " " + command));
         }
      }
   }
}
