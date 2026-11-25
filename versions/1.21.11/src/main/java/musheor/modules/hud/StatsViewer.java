package musheor.modules.hud;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.DoubleSetting.Builder;
import meteordevelopment.meteorclient.systems.hud.HudElement;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.HudRenderer;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import musheor.modules.automation.HighwayBuilder;
import musheor.utils.oX7ku;
import net.minecraft.client.MinecraftClient;
import obf.U6PQSXqW;

public class StatsViewer extends HudElement {
   private final SettingGroup fStlZIvY9lS8 = this.settings.getDefaultGroup();
   public static final HudElementInfo<StatsViewer> INFO = new HudElementInfo(
      musheor.musheor.MUSHEOR_HUD, "stats-viewer", "View your stats while using the HighwayBuilder module.", StatsViewer::new
   );
   private final Setting<Double> QfZy2gWnKInxFPr = this.fStlZIvY9lS8
      .add(((Builder)((Builder)new Builder().name("scale")).description("I mean cmon do I really have to explain??")).defaultValue(1.0).build());
   private final Setting<Integer> TJImwK2 = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("section-size"))
                  .description("Distance or size of a single highway section"))
               .defaultValue(10000))
            .build()
      );
   private final Setting<Boolean> wSJeYsJcmjNZf = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("display-session"))
                  .description("Display statistics of the current session."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> i2Nu6XHKic = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("display-blocks"))
                  .description("Display statistics of blocks mined and placed."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> vGAMA89H = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("display-performance"))
                  .description("Display statistics of paver's performance."))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> qhulAUwd = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("display-info"))
                  .description("Display extra information about the environment, inventory and ETA."))
               .defaultValue(false))
            .build()
      );
   private final Setting<Boolean> BrmKXSYhWhqoB1 = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                     .name("display-lifetime"))
                  .description("Display lifetime statistics about the paver."))
               .defaultValue(true))
            .build()
      );
   private final Setting<SettingColor> TXWyNb = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                  .name("prefix-color"))
               .description("Text color of the hud info lines."))
            .defaultValue(new SettingColor(255, 255, 225, 255))
            .build()
      );
   private final Setting<SettingColor> I3psabmOrOI = this.fStlZIvY9lS8
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                  .name("value-color"))
               .description("Text color of the hud info lines."))
            .defaultValue(new SettingColor(255, 0, 208, 255))
            .build()
      );

   public StatsViewer() {
      super(INFO);
   }

   private String[][] fStlZIvY9lS8() {
      List<String[]> lines = new ArrayList<>();
      U6PQSXqW state = U6PQSXqW.fStlZIvY9lS8();
      String[][] session = new String[][]{
         {"Latest session: ", ""},
         {"  Runtime: ", oX7ku.fStlZIvY9lS8((long)state.pF16zz4NJECfg7())},
         {"  Direction: ", String.valueOf(HighwayBuilder.i2Nu6XHKic()).toLowerCase()},
         {"  Distance travelled: ", String.valueOf(oX7ku.QfZy2gWnKInxFPr())}
      };
      String[][] blocks = new String[][]{
         {"Blocks", ""},
         {"  Obsidian Placed: ", oX7ku.wSJeYsJcmjNZf(state.DqGSCWplcjMi())},
         {"  Obsidian Mined: ", oX7ku.wSJeYsJcmjNZf(state.g0iiTDJzfIGXdui())},
         {"  Enderchests mined: ", oX7ku.wSJeYsJcmjNZf(state.HomZ7BbhM7wB())},
         {"  Netherrack mined: ", oX7ku.wSJeYsJcmjNZf(state.RE8q1TMDRWx())},
         {"  Total mined: ", oX7ku.wSJeYsJcmjNZf(state.g0iiTDJzfIGXdui() + state.RE8q1TMDRWx() + state.HomZ7BbhM7wB())}
      };
      String[][] performance = new String[][]{
         {"Performance", ""},
         {"  Placements / s: ", oX7ku.fStlZIvY9lS8(oX7ku.TJImwK2())},
         {"  Placements / h: ", oX7ku.QfZy2gWnKInxFPr(oX7ku.TJImwK2())},
         {"  Breaks / s: ", oX7ku.TJImwK2(oX7ku.wSJeYsJcmjNZf())},
         {"  Distance / s: ", oX7ku.i2Nu6XHKic(oX7ku.QfZy2gWnKInxFPr())},
         {"  Distance / h: ", oX7ku.vGAMA89H(oX7ku.QfZy2gWnKInxFPr())}
      };
      String[][] info = new String[][]{
         {"Info", ""},
         {"  Distance to next section: ", String.valueOf(oX7ku.TJImwK2((Integer)this.TJImwK2.get()))},
         {"  Percentage completed: ", oX7ku.fStlZIvY9lS8((Integer)this.TJImwK2.get())},
         {"  Material Count: ", String.valueOf(oX7ku.i2Nu6XHKic())},
         {"  ETA to next section: ", oX7ku.qhulAUwd((Integer)this.TJImwK2.get())},
         {"  ETA to next refill: ", oX7ku.vGAMA89H()}
      };
      String[][] lifetime = new String[][]{
         {"Lifetime: ", ""},
         {"  Obsidian Placed: ", oX7ku.wSJeYsJcmjNZf(state.xarqg2oUiwotDG4())},
         {"  Obsidian Mined: ", oX7ku.wSJeYsJcmjNZf(state.RFflNsLXot())},
         {"  Enderchests mined: ", oX7ku.wSJeYsJcmjNZf(state.bDU9FLOzvL())},
         {"  Netherrack mined: ", oX7ku.wSJeYsJcmjNZf(state.PuZZ3s4mpaTz())},
         {"  Total mined: ", oX7ku.wSJeYsJcmjNZf(state.F4hivB())}
      };
      if ((Boolean)this.wSJeYsJcmjNZf.get()) {
         lines.addAll(Arrays.asList(session));
      }

      if ((Boolean)this.i2Nu6XHKic.get()) {
         lines.addAll(Arrays.asList(blocks));
      }

      if ((Boolean)this.vGAMA89H.get()) {
         lines.addAll(Arrays.asList(performance));
      }

      if ((Boolean)this.qhulAUwd.get()) {
         lines.addAll(Arrays.asList(info));
      }

      if ((Boolean)this.BrmKXSYhWhqoB1.get()) {
         lines.addAll(Arrays.asList(lifetime));
      }

      return lines.toArray(new String[0][]);
   }

   public void render(HudRenderer renderer) {
      if (MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().world != null) {
         String[][] lines = this.fStlZIvY9lS8();
         double lineHeight = renderer.textHeight(true, (Double)this.QfZy2gWnKInxFPr.get());
         double width = 0.0;

         for (String[] line : lines) {
            double lineWidth = renderer.textWidth(line[0], true)
               + renderer.textWidth(": ", true)
               + renderer.textWidth(line[1], true, (Double)this.QfZy2gWnKInxFPr.get());
            width = Math.max(width, lineWidth);
         }

         this.setSize(width, lineHeight * lines.length);
         double currentY = this.y;

         for (String[] line : lines) {
            double currentX = this.x;
            renderer.text(line[0], currentX, currentY, (Color)this.TXWyNb.get(), true, (Double)this.QfZy2gWnKInxFPr.get());
            currentX += renderer.textWidth(line[0], true, (Double)this.QfZy2gWnKInxFPr.get());
            renderer.text("", currentX, currentY, (Color)this.TXWyNb.get(), true, (Double)this.QfZy2gWnKInxFPr.get());
            currentX += renderer.textWidth("", true, (Double)this.QfZy2gWnKInxFPr.get());
            if (line[0].equals("Progress")) {
               String percentageText = line[1];
               String doneText = " done";
               double percentageWidth = renderer.textWidth(percentageText, true);
               renderer.textWidth(doneText, true, (Double)this.QfZy2gWnKInxFPr.get());
               renderer.text(percentageText, currentX, currentY, (Color)this.I3psabmOrOI.get(), true, (Double)this.QfZy2gWnKInxFPr.get());
               currentX += percentageWidth;
               renderer.text(doneText, currentX, currentY, (Color)this.TXWyNb.get(), true, (Double)this.QfZy2gWnKInxFPr.get());
            } else {
               renderer.text(line[1], currentX, currentY, (Color)this.I3psabmOrOI.get(), true, (Double)this.QfZy2gWnKInxFPr.get());
            }

            currentY += lineHeight;
         }
      }
   }
}
