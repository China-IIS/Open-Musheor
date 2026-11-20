package musheor.modules.hud;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import meteordevelopment.meteorclient.settings.BoolSetting.Builder;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.hud.HudElement;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.HudRenderer;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import musheor.modules.features.CoordHider;
import musheor.musheor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;

public class DimensionalPosition extends HudElement {
   private final MinecraftClient QfZy2gWnKInxFPr = MinecraftClient.getInstance();
   private final SettingGroup TJImwK2 = this.settings.getDefaultGroup();
   private final SettingGroup wSJeYsJcmjNZf = this.settings.createGroup("Render");
   private final Setting<Boolean> i2Nu6XHKic = this.TJImwK2
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("show-opposite-dimension")).description("Show relative position in opposite dimension."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> vGAMA89H = this.TJImwK2
      .add(
         ((Builder)((Builder)((Builder)((Builder)new Builder().name("display-horizontal"))
                     .description("Displays opposite dimension horizontally instead of vertically."))
                  .defaultValue(true))
               .visible(this.i2Nu6XHKic::get))
            .build()
      );
   private final Setting<Double> qhulAUwd = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)new meteordevelopment.meteorclient.settings.DoubleSetting.Builder()
                  .name("text-scale"))
               .description("Scale of the text."))
            .defaultValue(1.0)
            .sliderRange(0.1, 3.0)
            .decimalPlaces(1)
            .build()
      );
   private final Setting<Boolean> BrmKXSYhWhqoB1 = this.TJImwK2
      .add(
         ((Builder)((Builder)((Builder)((Builder)new Builder().name("show-decimal-places")).description("Displays the decimal places of the coordinates."))
                  .defaultValue(true))
               .visible(this.i2Nu6XHKic::get))
            .build()
      );
   private final Setting<SettingColor> TXWyNb = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                  .name("overworld-color"))
               .description("Color for overworld coordinates."))
            .defaultValue(new SettingColor(0, 255, 0, 255))
            .build()
      );
   private final Setting<SettingColor> I3psabmOrOI = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                  .name("nether-color"))
               .description("Color for nether coordinates."))
            .defaultValue(new SettingColor(255, 0, 0, 255))
            .build()
      );
   private final Setting<SettingColor> duJ6sPeetAcBh6P = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                  .name("end-color"))
               .description("Color for end coordinates."))
            .defaultValue(new SettingColor(255, 0, 255, 255))
            .build()
      );
   public static final HudElementInfo<DimensionalPosition> fStlZIvY9lS8 = new HudElementInfo(
      musheor.MUSHEOR_HUD, "dimensional-position", "A hud module that displays your position", DimensionalPosition::new
   );

   public DimensionalPosition() {
      super(fStlZIvY9lS8);
   }

   public void render(HudRenderer renderer) {
      if (this.QfZy2gWnKInxFPr.player != null && this.QfZy2gWnKInxFPr.world != null) {
         CoordHider ch = (CoordHider)Modules.get().get(CoordHider.class);
         boolean hiding = ch != null && ch.isActive();
         double px = this.QfZy2gWnKInxFPr.player.getX();
         double py = this.QfZy2gWnKInxFPr.player.getY();
         double pz = this.QfZy2gWnKInxFPr.player.getZ();
         String sx = hiding ? "?" : this.fStlZIvY9lS8(px);
         String sy = hiding ? "?" : this.fStlZIvY9lS8(py);
         String sz = hiding ? "?" : this.fStlZIvY9lS8(pz);
         List<String[]> rows = new ArrayList<>();
         List<SettingColor> colors = new ArrayList<>();
         if (this.QfZy2gWnKInxFPr.world.getRegistryKey() == World.OVERWORLD) {
            rows.add(new String[]{"Overworld:", sx, sy, sz});
            colors.add((SettingColor)this.TXWyNb.get());
            if ((Boolean)this.i2Nu6XHKic.get()) {
               rows.add(new String[]{"Nether:", hiding ? "?" : this.fStlZIvY9lS8(px / 8.0), sy, hiding ? "?" : this.fStlZIvY9lS8(pz / 8.0)});
               colors.add((SettingColor)this.I3psabmOrOI.get());
            }
         } else if (this.QfZy2gWnKInxFPr.world.getRegistryKey() == World.NETHER) {
            rows.add(new String[]{"Nether:", sx, sy, sz});
            colors.add((SettingColor)this.I3psabmOrOI.get());
            if ((Boolean)this.i2Nu6XHKic.get()) {
               rows.add(new String[]{"Overworld:", hiding ? "?" : this.fStlZIvY9lS8(px * 8.0), sy, hiding ? "?" : this.fStlZIvY9lS8(pz * 8.0)});
               colors.add((SettingColor)this.TXWyNb.get());
            }
         } else if (this.QfZy2gWnKInxFPr.world.getRegistryKey() == World.END) {
            rows.add(new String[]{"End:", sx, sy, sz});
            colors.add((SettingColor)this.duJ6sPeetAcBh6P.get());
         }

         if (!rows.isEmpty()) {
            double lineHeight = renderer.textHeight(true, (Double)this.qhulAUwd.get());
            double sepW = renderer.textWidth(", ", true, (Double)this.qhulAUwd.get());
            double gapW = renderer.textWidth(" ", true, (Double)this.qhulAUwd.get());
            if (rows.size() == 2 && (Boolean)this.vGAMA89H.get()) {
               double cx = this.x;

               for (int i = 0; i < rows.size(); i++) {
                  if (i > 0) {
                     cx += renderer.textWidth("   ", true, (Double)this.qhulAUwd.get());
                  }

                  cx = this.fStlZIvY9lS8(renderer, rows.get(i), colors.get(i), cx, this.y, sepW, gapW);
               }

               this.setSize(cx - this.x, lineHeight);
            } else {
               double labelW = 0.0;
               double xW = 0.0;
               double yW = 0.0;
               double zW = 0.0;

               for (String[] row : rows) {
                  labelW = Math.max(labelW, renderer.textWidth(row[0], true, (Double)this.qhulAUwd.get()));
                  xW = Math.max(xW, renderer.textWidth(row[1], true, (Double)this.qhulAUwd.get()));
                  yW = Math.max(yW, renderer.textWidth(row[2], true, (Double)this.qhulAUwd.get()));
                  zW = Math.max(zW, renderer.textWidth(row[3], true, (Double)this.qhulAUwd.get()));
               }

               double totalWidth = labelW + gapW + xW + sepW + yW + sepW + zW;
               double currentY = this.y;

               for (int i = 0; i < rows.size(); i++) {
                  String[] row = rows.get(i);
                  SettingColor color = colors.get(i);
                  double cx = this.x;
                  renderer.text(
                     row[0], cx + labelW - renderer.textWidth(row[0], true, (Double)this.qhulAUwd.get()), currentY, color, true, (Double)this.qhulAUwd.get()
                  );
                  cx += labelW + gapW;
                  renderer.text(
                     row[1], cx + xW - renderer.textWidth(row[1], true, (Double)this.qhulAUwd.get()), currentY, color, true, (Double)this.qhulAUwd.get()
                  );
                  cx += xW;
                  renderer.text(" ", cx, currentY, color, true, (Double)this.qhulAUwd.get());
                  cx += sepW;
                  renderer.text(
                     row[2], cx + yW - renderer.textWidth(row[2], true, (Double)this.qhulAUwd.get()), currentY, color, true, (Double)this.qhulAUwd.get()
                  );
                  cx += yW;
                  renderer.text(" ", cx, currentY, color, true, (Double)this.qhulAUwd.get());
                  cx += sepW;
                  renderer.text(
                     row[3], cx + zW - renderer.textWidth(row[3], true, (Double)this.qhulAUwd.get()), currentY, color, true, (Double)this.qhulAUwd.get()
                  );
                  currentY += lineHeight;
               }

               this.setSize(totalWidth, currentY - this.y);
            }
         }
      }
   }

   private double fStlZIvY9lS8(HudRenderer renderer, String[] row, SettingColor color, double startX, double startY, double sepW, double gapW) {
      renderer.text(row[0], startX, startY, color, true, (Double)this.qhulAUwd.get());
      double cx = startX + (renderer.textWidth(row[0], true, (Double)this.qhulAUwd.get()) + gapW);
      renderer.text(row[1], cx, startY, color, true, (Double)this.qhulAUwd.get());
      cx += renderer.textWidth(row[1], true, (Double)this.qhulAUwd.get());
      renderer.text(" ", cx, startY, color, true, (Double)this.qhulAUwd.get());
      cx += sepW;
      renderer.text(row[2], cx, startY, color, true, (Double)this.qhulAUwd.get());
      cx += renderer.textWidth(row[2], true, (Double)this.qhulAUwd.get());
      renderer.text(" ", cx, startY, color, true, (Double)this.qhulAUwd.get());
      cx += sepW;
      renderer.text(row[3], cx, startY, color, true, (Double)this.qhulAUwd.get());
      return cx + renderer.textWidth(row[3], true, (Double)this.qhulAUwd.get());
   }

   private String fStlZIvY9lS8(double v) {
      return !this.BrmKXSYhWhqoB1.get() ? String.format(Locale.US, "%,.0f", v) : String.format(Locale.US, "%,.1f", v);
   }
}
