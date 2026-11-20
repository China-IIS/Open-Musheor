package musheor.modules.features;

import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.settings.EnumSetting.Builder;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.misc.AutoReconnect;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import meteordevelopment.meteorclient.utils.world.Dimension;
import meteordevelopment.orbit.EventHandler;
import musheor.musheor;
import musheor.utils.irzXX;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.text.Text;

public class LogoutGoal extends Module {
   private final SettingGroup QfZy2gWnKInxFPr = this.settings.getDefaultGroup();
   private final Setting<Dimension> TJImwK2 = this.QfZy2gWnKInxFPr
      .add(((Builder)((Builder)((Builder)new Builder().name("dimension")).description("Dimension player is inside.")).defaultValue(Dimension.Nether)).build());
   private final Setting<Integer> wSJeYsJcmjNZf = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("x-coord"))
                  .description("The X coordinate (world border is at ~ 29.999.983)."))
               .defaultValue(0))
            .range(-29999949, 29999949)
            .noSlider()
            .build()
      );
   private final Setting<Integer> i2Nu6XHKic = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("z-coord"))
                  .description("The Z coordinate (world border is at ~ 29.999.983)."))
               .defaultValue(0))
            .range(-29999949, 29999949)
            .noSlider()
            .build()
      );
   private final Setting<Boolean> vGAMA89H = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("disable_auto-reconnect"))
                  .description("Turns off AutoReconnect when logging out."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> qhulAUwd = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("auto-disable"))
                  .description("Automatically disables itself when reaching your destination."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Integer> BrmKXSYhWhqoB1 = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("range"))
                  .description("Distance from the goal at which the player will log out."))
               .defaultValue(32))
            .min(0)
            .sliderRange(0, 200)
            .build()
      );
   private final Setting<String> TXWyNb = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.StringSetting.Builder)((meteordevelopment.meteorclient.settings.StringSetting.Builder)((meteordevelopment.meteorclient.settings.StringSetting.Builder)new meteordevelopment.meteorclient.settings.StringSetting.Builder()
                     .name("text"))
                  .description("Text displayed on disconnect."))
               .defaultValue("Arrived at destination."))
            .build()
      );
   private final Setting<Boolean> I3psabmOrOI = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("Coords"))
                  .description("Display coordinates on logout screen."))
               .defaultValue(true))
            .build()
      );

   public LogoutGoal() {
      super(musheor.MAIN, "logout-goal", "Automatically disconnect when arriving at a specific location.");
   }

   @EventHandler
   private void fStlZIvY9lS8(Pre event) {
      if (this.mc.player != null && this.mc.world != null) {
         int playerX = (int)Math.round(this.mc.player.getX());
         int playerZ = (int)Math.round(this.mc.player.getZ());
         if (this.fStlZIvY9lS8() && this.QfZy2gWnKInxFPr() && PlayerUtils.getDimension() == this.TJImwK2.get()) {
            String message = (String)this.TXWyNb.get();
            if ((Boolean)this.vGAMA89H.get() && Modules.get().isActive(AutoReconnect.class)) {
               ((AutoReconnect)Modules.get().get(AutoReconnect.class)).toggle();
            }

            if ((Boolean)this.qhulAUwd.get()) {
               this.toggle();
            }

            if ((Boolean)this.I3psabmOrOI.get()) {
               message = message + " X: " + playerX + " Z: " + playerZ;
            }

            irzXX.fStlZIvY9lS8(message);
            this.mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket(Text.literal(message)));
         }
      }
   }

   private boolean fStlZIvY9lS8() {
      assert this.mc.player != null;

      return this.mc.player.getX() <= (Integer)this.wSJeYsJcmjNZf.get() + (Integer)this.BrmKXSYhWhqoB1.get()
         && this.mc.player.getX() >= (Integer)this.wSJeYsJcmjNZf.get() - (Integer)this.BrmKXSYhWhqoB1.get();
   }

   private boolean QfZy2gWnKInxFPr() {
      assert this.mc.player != null;

      return this.mc.player.getZ() <= (Integer)this.i2Nu6XHKic.get() + (Integer)this.BrmKXSYhWhqoB1.get()
         && this.mc.player.getZ() >= (Integer)this.i2Nu6XHKic.get() - (Integer)this.BrmKXSYhWhqoB1.get();
   }
}
