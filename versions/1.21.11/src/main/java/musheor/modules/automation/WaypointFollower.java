package musheor.modules.automation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.EnumSetting.Builder;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import meteordevelopment.meteorclient.utils.world.Dimension;
import meteordevelopment.orbit.EventHandler;
import musheor.compat.XearoHelper;
import musheor.modules.features.KekFly;
import musheor.utils.irzXX;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Formatting;
import obf.GyhoRo;

public class WaypointFollower extends Module {
   private final MinecraftClient fStlZIvY9lS8 = MinecraftClient.getInstance();
   private final SettingGroup QfZy2gWnKInxFPr = this.settings.getDefaultGroup();
   private final Setting<WaypointFollower.FlightMode> TJImwK2 = this.QfZy2gWnKInxFPr
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("flight-mode")).description("Which type of flight mode to use"))
               .defaultValue(WaypointFollower.FlightMode.RocketFly))
            .build()
      );
   private final Setting<Boolean> wSJeYsJcmjNZf = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                        .name("draw-path"))
                     .description("Draws a path between waypoints on Xearo's map"))
                  .defaultValue(true))
               .visible(() -> XearoHelper.isLoaded() && this.TJImwK2.get() != WaypointFollower.FlightMode.Baritone))
            .build()
      );
   private final Setting<Integer> i2Nu6XHKic = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("arrival-radius"))
                  .description("XZ distance to a waypoint at which it is considered reached."))
               .defaultValue(32))
            .sliderRange(8, 256)
            .build()
      );
   private final Setting<Double> vGAMA89H = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)new meteordevelopment.meteorclient.settings.DoubleSetting.Builder()
                     .name("degrees-per-tick"))
                  .description("Max yaw rotation per tick — limits how sharply the player turns toward a waypoint."))
               .defaultValue(3.0)
               .sliderRange(0.5, 20.0)
               .decimalPlaces(1)
               .visible(() -> this.TJImwK2.get() == WaypointFollower.FlightMode.RocketFly || this.TJImwK2.get() == WaypointFollower.FlightMode.Custom))
            .build()
      );
   private XearoHelper.WaypointData qhulAUwd = null;
   private int BrmKXSYhWhqoB1 = -1;

   public WaypointFollower() {
      super(musheor.musheor.AUTOMATION, "waypoint-follower", "Automatically follow waypoints in all dimensions using different flight modes.");
   }

   public void onActivate() {
      if (this.fStlZIvY9lS8.player != null && this.fStlZIvY9lS8.world != null) {
         Dimension currentDim = PlayerUtils.getDimension();
         if (this.TJImwK2.get() == WaypointFollower.FlightMode.Baritone && (currentDim == Dimension.End || currentDim == Dimension.Overworld)) {
            Formatting format = currentDim == Dimension.End ? Formatting.DARK_PURPLE : Formatting.RED;
            irzXX.fStlZIvY9lS8(WaypointFollower.class, "flight-mode", WaypointFollower.FlightMode.RocketFly);
            ChatUtils.info(
               Formatting.GOLD
                  + "[WaypointFollower] "
                  + Formatting.WHITE
                  + "Automatically switching to RocketFly: "
                  + format
                  + currentDim.name()
                  + Formatting.WHITE
                  + " does not support Baritone Elytra Flight.",
               new Object[0]
            );
            this.toggle();
         }
      }
   }

   public void onDeactivate() {
      if (this.qhulAUwd != null) {
         GyhoRo.wSJeYsJcmjNZf();
         this.qhulAUwd = null;
      }

      if (XearoHelper.isLoaded()) {
         XearoHelper.get().clearLinesOnMap();
      }

      this.BrmKXSYhWhqoB1 = -1;
      KekFly fly = (KekFly)Modules.get().get(KekFly.class);
      if (fly.isActive() && this.TJImwK2.get() == WaypointFollower.FlightMode.RocketFly) {
         fly.toggle();
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(Pre event) {
      if (this.fStlZIvY9lS8.player != null && this.fStlZIvY9lS8.world != null) {
         switch ((WaypointFollower.FlightMode)this.TJImwK2.get()) {
            case Baritone:
               this.fStlZIvY9lS8();
               break;
            case RocketFly:
               this.QfZy2gWnKInxFPr();
               break;
            case Custom:
               this.TJImwK2();
         }

         if ((Boolean)this.wSJeYsJcmjNZf.get() && this.TJImwK2.get() != WaypointFollower.FlightMode.Baritone) {
            this.wSJeYsJcmjNZf();
         }
      }
   }

   private void fStlZIvY9lS8() {
      if (this.qhulAUwd == null) {
         this.qhulAUwd = XearoHelper.get().getOldestWaypoint(true);
         if (this.qhulAUwd != null) {
            this.fStlZIvY9lS8(this.qhulAUwd);
         }
      } else {
         if (XearoHelper.get().distanceToWaypoint(this.qhulAUwd) < ((Integer)this.i2Nu6XHKic.get()).intValue()) {
            GyhoRo.wSJeYsJcmjNZf();
            ChatUtils.info(
               Formatting.GREEN + "Arrived at " + Formatting.YELLOW + this.qhulAUwd.name() + Formatting.GREEN + ", moving to next...", new Object[0]
            );
            XearoHelper.get().deleteCurrentWaypoint(this.qhulAUwd);
            this.qhulAUwd = null;
         }
      }
   }

   private void QfZy2gWnKInxFPr() {
      if (((KekFly)Modules.get().get(KekFly.class)).isActive()) {
         if (this.qhulAUwd == null) {
            this.qhulAUwd = XearoHelper.get().getOldestWaypoint(true);
            if (this.qhulAUwd == null) {
               return;
            }

            ChatUtils.info(Formatting.GOLD + "[WaypointFollower] " + Formatting.WHITE + "Flying to: " + Formatting.YELLOW + this.qhulAUwd.name(), new Object[0]);
         }

         float desiredYaw = this.QfZy2gWnKInxFPr(this.qhulAUwd);
         float diff = this.fStlZIvY9lS8(this.fStlZIvY9lS8.player.getYaw(), desiredYaw);
         float maxRot = (float)((Double)this.vGAMA89H.get()).doubleValue();
         if (Math.abs(diff) <= maxRot) {
            this.fStlZIvY9lS8.player.setYaw(desiredYaw);
         } else {
            this.fStlZIvY9lS8.player.setYaw(this.fStlZIvY9lS8.player.getYaw() - Math.signum(diff) * maxRot);
         }

         if (XearoHelper.get().distanceToWaypoint(this.qhulAUwd) < ((Integer)this.i2Nu6XHKic.get()).intValue()) {
            ChatUtils.info(
               Formatting.GOLD
                  + "[WaypointFollower] "
                  + Formatting.WHITE
                  + "Arrived at "
                  + Formatting.YELLOW
                  + this.qhulAUwd.name()
                  + Formatting.WHITE
                  + ", moving to next...",
               new Object[0]
            );
            XearoHelper.get().deleteCurrentWaypoint(this.qhulAUwd);
            this.qhulAUwd = null;
         }
      }
   }

   private void TJImwK2() {
      if (this.qhulAUwd == null) {
         this.qhulAUwd = XearoHelper.get().getOldestWaypoint(true);
         if (this.qhulAUwd == null) {
            return;
         }

         ChatUtils.info(Formatting.GOLD + "[WaypointFollower] " + Formatting.WHITE + "Flying to: " + Formatting.YELLOW + this.qhulAUwd.name(), new Object[0]);
      }

      float desiredYaw = this.QfZy2gWnKInxFPr(this.qhulAUwd);
      float diff = this.fStlZIvY9lS8(this.fStlZIvY9lS8.player.getYaw(), desiredYaw);
      float maxRot = (float)((Double)this.vGAMA89H.get()).doubleValue();
      if (Math.abs(diff) <= maxRot) {
         this.fStlZIvY9lS8.player.setYaw(desiredYaw);
      } else {
         this.fStlZIvY9lS8.player.setYaw(this.fStlZIvY9lS8.player.getYaw() - Math.signum(diff) * maxRot);
      }

      if (XearoHelper.get().distanceToWaypoint(this.qhulAUwd) < ((Integer)this.i2Nu6XHKic.get()).intValue()) {
         ChatUtils.info(
            Formatting.GOLD
               + "[WaypointFollower] "
               + Formatting.WHITE
               + "Arrived at "
               + Formatting.YELLOW
               + this.qhulAUwd.name()
               + Formatting.WHITE
               + ", moving to next...",
            new Object[0]
         );
         XearoHelper.get().deleteCurrentWaypoint(this.qhulAUwd);
         this.qhulAUwd = null;
      }
   }

   private void wSJeYsJcmjNZf() {
      if (XearoHelper.isLoaded()) {
         List<XearoHelper.WaypointData> wps = XearoHelper.get().getWaypoints(true);
         if (wps != null) {
            if (wps.size() != this.BrmKXSYhWhqoB1) {
               this.BrmKXSYhWhqoB1 = wps.size();
               if (wps.isEmpty()) {
                  XearoHelper.get().clearLinesOnMap();
               } else {
                  List<XearoHelper.WaypointData> sorted = new ArrayList<>(wps);
                  sorted.sort(Comparator.comparingLong(XearoHelper.WaypointData::createdAt));
                  List<XearoHelper.LineData> lines = new ArrayList<>();
                  double fromX = this.fStlZIvY9lS8.player.getX();
                  double fromZ = this.fStlZIvY9lS8.player.getZ();

                  for (XearoHelper.WaypointData wp : sorted) {
                     lines.add(new XearoHelper.LineData((int)fromX, (int)fromZ, wp.x(), wp.z()));
                     fromX = wp.x();
                     fromZ = wp.z();
                  }

                  XearoHelper.get().drawLinesOnMap(lines, -16733441);
               }
            }
         }
      }
   }

   private void fStlZIvY9lS8(XearoHelper.WaypointData wp) {
      ChatUtils.info(Formatting.GOLD + "[WaypointFollower] " + Formatting.WHITE + "Flying to next waypoint: " + Formatting.GRAY + wp.name(), new Object[0]);
      GyhoRo.fStlZIvY9lS8(wp.x(), wp.z());
   }

   private float QfZy2gWnKInxFPr(XearoHelper.WaypointData wp) {
      double dx = wp.x() - this.fStlZIvY9lS8.player.getX();
      double dz = wp.z() - this.fStlZIvY9lS8.player.getZ();
      return (float)Math.toDegrees(Math.atan2(-dx, dz));
   }

   private float fStlZIvY9lS8(float a, float b) {
      float d = (a - b) % 360.0F;
      if (d > 180.0F) {
         d -= 360.0F;
      }

      if (d < -180.0F) {
         d += 360.0F;
      }

      return d;
   }

   public enum FlightMode {
      Baritone,
      RocketFly,
      Custom;

      FlightMode() {
      }
   }
}
