package musheor.modules.tech;

import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.IntSetting.Builder;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import musheor.utils.irzXX;
import net.minecraft.client.MinecraftClient;

public class Whisper extends Module {
   private final SettingGroup QfZy2gWnKInxFPr = this.settings.getDefaultGroup();
   private static final MinecraftClient TJImwK2 = MinecraftClient.getInstance();
   public final Setting<Integer> fStlZIvY9lS8 = this.QfZy2gWnKInxFPr
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("anti-spam-length")).description("The length of the randomly generated message")).defaultValue(12))
            .min(1)
            .sliderRange(1, 31)
            .build()
      );
   private final Setting<String> wSJeYsJcmjNZf = this.settings
      .getDefaultGroup()
      .add(
         ((meteordevelopment.meteorclient.settings.StringSetting.Builder)((meteordevelopment.meteorclient.settings.StringSetting.Builder)((meteordevelopment.meteorclient.settings.StringSetting.Builder)new meteordevelopment.meteorclient.settings.StringSetting.Builder()
                     .name("username"))
                  .description("IGN of the player you want to send a message to."))
               .defaultValue(""))
            .build()
      );

   public Whisper() {
      super(musheor.musheor.MAIN, "TPA", "Used for sending TP requests to stasis-bots");
   }

   @EventHandler
   public void fStlZIvY9lS8(Pre event) {
      if (this.isActive()) {
         irzXX.fStlZIvY9lS8((String)this.wSJeYsJcmjNZf.get(), (Integer)this.fStlZIvY9lS8.get());
         this.toggle();
      }
   }
}
