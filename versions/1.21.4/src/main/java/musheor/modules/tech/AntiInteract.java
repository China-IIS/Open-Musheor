package musheor.modules.tech;

import java.util.List;
import meteordevelopment.meteorclient.events.entity.player.InteractBlockEvent;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.BoolSetting.Builder;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.orbit.EventHandler;
import musheor.musheor;
import musheor.commands.RestockConfig;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

public class AntiInteract extends Module {
   private final SettingGroup fStlZIvY9lS8 = this.settings.getDefaultGroup();
   private final MinecraftClient QfZy2gWnKInxFPr = MinecraftClient.getInstance();
   private final Setting<Boolean> TJImwK2 = this.fStlZIvY9lS8
      .add(((Builder)((Builder)((Builder)new Builder().name("beds")).description("Stops you from interacting with beds")).defaultValue(true)).build());
   private final Setting<Boolean> wSJeYsJcmjNZf = this.fStlZIvY9lS8
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("respawn-anchors")).description("Stops you from interacting with respawn anchors"))
               .defaultValue(true))
            .build()
      );
   private final Setting<List<Block>> i2Nu6XHKic = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.BlockListSetting.Builder)((meteordevelopment.meteorclient.settings.BlockListSetting.Builder)new meteordevelopment.meteorclient.settings.BlockListSetting.Builder()
                  .name("block-list"))
               .description("When enabled, stops you from interacting with beds"))
            .defaultValue(new Block[0])
            .build()
      );

   @EventHandler
   private void fStlZIvY9lS8(InteractBlockEvent event) {
      if (this.QfZy2gWnKInxFPr.player != null && this.QfZy2gWnKInxFPr.world != null) {
         BlockPos pos = event.result.getBlockPos();
         BlockState state = this.QfZy2gWnKInxFPr.world.getBlockState(pos);
         if ((Boolean)this.TJImwK2.get() && state.getBlock() instanceof BedBlock) {
            event.cancel();
            ChatUtils.info("§cPrevented interaction with §e%s", new Object[]{RestockConfig.fStlZIvY9lS8(state.getBlock().asItem())});
         } else if ((Boolean)this.wSJeYsJcmjNZf.get() && state.getBlock() instanceof RespawnAnchorBlock) {
            event.cancel();
            ChatUtils.info("§cPrevented interaction with §e%s", new Object[]{RestockConfig.fStlZIvY9lS8(state.getBlock().asItem())});
         } else if (((List)this.i2Nu6XHKic.get()).contains(state.getBlock())) {
            event.cancel();
            ChatUtils.info("§cPrevented interaction with §e%s", new Object[]{RestockConfig.fStlZIvY9lS8(state.getBlock().asItem())});
         }
      }
   }

   public AntiInteract() {
      super(musheor.MAIN, "anti-interact", "Prevents you from interacting with certain blocks e.g. beds and respawn anchors");
   }
}
