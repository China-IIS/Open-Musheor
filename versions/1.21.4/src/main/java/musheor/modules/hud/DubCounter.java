package musheor.modules.hud;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import meteordevelopment.meteorclient.settings.BoolSetting.Builder;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.hud.HudElement;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.HudRenderer;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import musheor.musheor;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class DubCounter extends HudElement {
   private final MinecraftClient fStlZIvY9lS8 = MinecraftClient.getInstance();
   private final SettingGroup QfZy2gWnKInxFPr = this.settings.getDefaultGroup();
   private final SettingGroup TJImwK2 = this.settings.createGroup("Render");
   private final Setting<Boolean> wSJeYsJcmjNZf = this.QfZy2gWnKInxFPr
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("double-chests")).description("Counts connected double chests (including trapped ones)."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> i2Nu6XHKic = this.QfZy2gWnKInxFPr
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("single-chests")).description("Counts single, unconnected chests (including trapped ones)."))
               .defaultValue(false))
            .build()
      );
   private final Setting<List<BlockEntityType<?>>> vGAMA89H = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.StorageBlockListSetting.Builder)((meteordevelopment.meteorclient.settings.StorageBlockListSetting.Builder)new meteordevelopment.meteorclient.settings.StorageBlockListSetting.Builder()
                  .name("other-storages"))
               .description("Other storage blocks to count. Chests are handled by the settings above."))
            .defaultValue(new BlockEntityType[0])
            .build()
      );
   private final Setting<Boolean> qhulAUwd = this.QfZy2gWnKInxFPr
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("separate-lines")).description("Shows a line per storage type instead of a single total."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Integer> BrmKXSYhWhqoB1 = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("update-delay"))
                  .description("Ticks between rescans of the loaded chunks."))
               .defaultValue(10))
            .min(1)
            .sliderRange(1, 100)
            .build()
      );
   private final Setting<Double> TXWyNb = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)new meteordevelopment.meteorclient.settings.DoubleSetting.Builder()
                  .name("text-scale"))
               .description("Scale of the text."))
            .defaultValue(1.0)
            .sliderRange(0.1, 3.0)
            .decimalPlaces(1)
            .build()
      );
   private final Setting<SettingColor> I3psabmOrOI = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                  .name("label-color"))
               .description("Color of the labels."))
            .defaultValue(new SettingColor(255, 255, 255, 255))
            .build()
      );
   private final Setting<SettingColor> duJ6sPeetAcBh6P = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                  .name("value-color"))
               .description("Color of the counts."))
            .defaultValue(new SettingColor(175, 175, 175, 255))
            .build()
      );
   public static final HudElementInfo<DubCounter> INFO = new HudElementInfo(
      musheor.MUSHEOR_HUD, "dub-counter", "Counts connected double chests (and optionally other storage) in render distance", DubCounter::new
   );
   private int j376sIQ;
   private int V7zKJ4n6D;
   private final Map<BlockEntityType<?>, Integer> voxTwgNzyrpaAnP = new LinkedHashMap<>();
   private int xileXsbo;

   public DubCounter() {
      super(INFO);
   }

   public void tick(HudRenderer renderer) {
      if (this.fStlZIvY9lS8.world != null) {
         if (--this.xileXsbo <= 0) {
            this.xileXsbo = (Integer)this.BrmKXSYhWhqoB1.get();
            this.fStlZIvY9lS8();
         }
      }
   }

   private void fStlZIvY9lS8() {
      this.j376sIQ = 0;
      this.V7zKJ4n6D = 0;
      this.voxTwgNzyrpaAnP.clear();

      for (BlockEntityType<?> type : (List)this.vGAMA89H.get()) {
         if (!fStlZIvY9lS8(type)) {
            this.voxTwgNzyrpaAnP.put(type, 0);
         }
      }

      for (BlockEntity be : Utils.blockEntities()) {
         if (be instanceof ChestBlockEntity) {
            BlockState state = be.getCachedState();
            if (state.contains(ChestBlock.CHEST_TYPE)) {
               ChestType typex = (ChestType)state.get(ChestBlock.CHEST_TYPE);
               if (typex == ChestType.SINGLE) {
                  this.V7zKJ4n6D++;
               } else if (typex == ChestType.LEFT || !this.fStlZIvY9lS8(be.getPos(), state)) {
                  this.j376sIQ++;
               }
            }
         } else {
            this.voxTwgNzyrpaAnP.computeIfPresent(be.getType(), (t, n) -> n + 1);
         }
      }
   }

   private boolean fStlZIvY9lS8(BlockPos pos, BlockState state) {
      BlockPos partner = pos.offset(ChestBlock.getFacing(state));
      return this.fStlZIvY9lS8.world.getChunkManager().isChunkLoaded(partner.getX() >> 4, partner.getZ() >> 4);
   }

   private static boolean fStlZIvY9lS8(BlockEntityType<?> type) {
      return type == BlockEntityType.CHEST || type == BlockEntityType.TRAPPED_CHEST;
   }

   public void render(HudRenderer renderer) {
      List<String[]> rows = new ArrayList<>();
      int total = 0;
      if ((Boolean)this.wSJeYsJcmjNZf.get()) {
         rows.add(new String[]{"Dubs: ", Integer.toString(this.j376sIQ)});
         total += this.j376sIQ;
      }

      if ((Boolean)this.i2Nu6XHKic.get()) {
         rows.add(new String[]{"Single Chests: ", Integer.toString(this.V7zKJ4n6D)});
         total += this.V7zKJ4n6D;
      }

      for (Entry<BlockEntityType<?>, Integer> entry : this.voxTwgNzyrpaAnP.entrySet()) {
         rows.add(new String[]{QfZy2gWnKInxFPr(entry.getKey()) + ": ", Integer.toString(entry.getValue())});
         total += entry.getValue();
      }

      if (!(Boolean)this.qhulAUwd.get() || rows.isEmpty()) {
         String label = rows.size() == 1 ? rows.get(0)[0] : "Storage: ";
         rows.clear();
         rows.add(new String[]{label, Integer.toString(total)});
      }

      double lineHeight = renderer.textHeight(true, (Double)this.TXWyNb.get());
      double width = 0.0;
      double cy = this.y;

      for (String[] row : rows) {
         double labelW = renderer.textWidth(row[0], true, (Double)this.TXWyNb.get());
         renderer.text(row[0], this.x, cy, (Color)this.I3psabmOrOI.get(), true, (Double)this.TXWyNb.get());
         renderer.text(row[1], this.x + labelW, cy, (Color)this.duJ6sPeetAcBh6P.get(), true, (Double)this.TXWyNb.get());
         width = Math.max(width, labelW + renderer.textWidth(row[1], true, (Double)this.TXWyNb.get()));
         cy += lineHeight;
      }

      this.setSize(width, cy - this.y);
   }

   private static String QfZy2gWnKInxFPr(BlockEntityType<?> type) {
      Identifier id = Registries.BLOCK_ENTITY_TYPE.getId(type);
      if (id == null) {
         return "Unknown";
      } else {
         StringBuilder sb = new StringBuilder();

         for (String word : id.getPath().split("_")) {
            if (!word.isEmpty()) {
               if (!sb.isEmpty()) {
                  sb.append(' ');
               }

               sb.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
            }
         }

         return sb.toString();
      }
   }
}
