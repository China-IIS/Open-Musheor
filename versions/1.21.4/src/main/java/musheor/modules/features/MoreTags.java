package musheor.modules.features;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import meteordevelopment.meteorclient.events.entity.EntityAddedEvent;
import meteordevelopment.meteorclient.events.render.Render2DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent.Post;
import meteordevelopment.meteorclient.renderer.Renderer2D;
import meteordevelopment.meteorclient.renderer.text.TextRenderer;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.BoolSetting.Builder;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.render.Freecam;
import meteordevelopment.meteorclient.utils.render.NametagUtils;
import meteordevelopment.meteorclient.utils.render.RenderUtils;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import musheor.compat.VersionHelper;
import musheor.utils.AVZhkxEO;
import musheor.utils.GyhoRo;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3d;

public class MoreTags extends Module {
   private final SettingGroup TJImwK2 = this.settings.createGroup("Player");
   private final SettingGroup wSJeYsJcmjNZf = this.settings.createGroup("Pearl");
   public final Setting<Boolean> fStlZIvY9lS8 = this.TJImwK2
      .add(((Builder)((Builder)((Builder)new Builder().name("enabled")).description("Show custom nametags above players.")).defaultValue(true)).build());
   private final Setting<Double> i2Nu6XHKic = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)new meteordevelopment.meteorclient.settings.DoubleSetting.Builder()
                     .name("scale"))
                  .description("Scale of the player nametag."))
               .defaultValue(1.0)
               .sliderRange(0.5, 3.0)
               .decimalPlaces(1)
               .visible(this.fStlZIvY9lS8::get))
            .build()
      );
   private final Setting<Boolean> vGAMA89H = this.TJImwK2
      .add(
         ((Builder)((Builder)((Builder)((Builder)new Builder().name("distance-scaling")).description("Scales the nametag based on distance from the player."))
                  .defaultValue(false))
               .visible(this.fStlZIvY9lS8::get))
            .build()
      );
   private final Setting<Boolean> qhulAUwd = this.TJImwK2
      .add(
         ((Builder)((Builder)((Builder)((Builder)new Builder().name("ignore-self")).description("Don't render a nametag above your own player."))
                  .defaultValue(true))
               .visible(this.fStlZIvY9lS8::get))
            .build()
      );
   private final Setting<MoreTags.NameColorType> BrmKXSYhWhqoB1 = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)new meteordevelopment.meteorclient.settings.EnumSetting.Builder()
                        .name("name-color"))
                     .description("How to color the player's name."))
                  .defaultValue(MoreTags.NameColorType.Default))
               .visible(this.fStlZIvY9lS8::get))
            .build()
      );
   private final Setting<SettingColor> TXWyNb = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                  .name("name-color-value"))
               .defaultValue(new SettingColor(255, 255, 255))
               .visible(() -> (Boolean)this.fStlZIvY9lS8.get() && this.BrmKXSYhWhqoB1.get() == MoreTags.NameColorType.Color))
            .build()
      );
   private final Setting<SettingColor> I3psabmOrOI = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                  .name("name-gradient-start"))
               .defaultValue(new SettingColor(255, 100, 100))
               .visible(() -> (Boolean)this.fStlZIvY9lS8.get() && this.BrmKXSYhWhqoB1.get() == MoreTags.NameColorType.Gradient))
            .build()
      );
   private final Setting<SettingColor> duJ6sPeetAcBh6P = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                  .name("name-gradient-end"))
               .defaultValue(new SettingColor(100, 100, 255))
               .visible(() -> (Boolean)this.fStlZIvY9lS8.get() && this.BrmKXSYhWhqoB1.get() == MoreTags.NameColorType.Gradient))
            .build()
      );
   private final Setting<SettingColor> j376sIQ = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                     .name("friend-color"))
                  .description("Name color for friends."))
               .defaultValue(new SettingColor(Color.GREEN))
               .visible(this.fStlZIvY9lS8::get))
            .build()
      );
   private final Setting<Boolean> V7zKJ4n6D = this.TJImwK2
      .add(
         ((Builder)((Builder)((Builder)((Builder)new Builder().name("background")).description("Show a background behind the nametag.")).defaultValue(true))
               .visible(this.fStlZIvY9lS8::get))
            .build()
      );
   private final Setting<SettingColor> voxTwgNzyrpaAnP = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                  .name("background-color"))
               .defaultValue(new SettingColor(0, 0, 0, 160))
               .visible(() -> (Boolean)this.fStlZIvY9lS8.get() && (Boolean)this.V7zKJ4n6D.get()))
            .build()
      );
   private final Setting<Double> xileXsbo = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)new meteordevelopment.meteorclient.settings.DoubleSetting.Builder()
                  .name("background-padding"))
               .defaultValue(6.0)
               .sliderRange(0.0, 20.0)
               .decimalPlaces(1)
               .visible(() -> (Boolean)this.fStlZIvY9lS8.get() && (Boolean)this.V7zKJ4n6D.get()))
            .build()
      );
   private final Setting<MoreTags.OutlineType> nOwp0NYl = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)new meteordevelopment.meteorclient.settings.EnumSetting.Builder()
                     .name("outline"))
                  .defaultValue(MoreTags.OutlineType.Disabled))
               .visible(this.fStlZIvY9lS8::get))
            .build()
      );
   private final Setting<SettingColor> umvNbb7iD9UpR = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                  .name("outline-color"))
               .defaultValue(new SettingColor(255, 255, 255, 200))
               .visible(() -> (Boolean)this.fStlZIvY9lS8.get() && this.nOwp0NYl.get() == MoreTags.OutlineType.Color))
            .build()
      );
   private final Setting<Double> zkcZ4Xkp9Ez7Y = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)new meteordevelopment.meteorclient.settings.DoubleSetting.Builder()
                  .name("outline-rounding"))
               .defaultValue(3.0)
               .sliderRange(0.0, 10.0)
               .decimalPlaces(1)
               .visible(() -> (Boolean)this.fStlZIvY9lS8.get() && this.nOwp0NYl.get() != MoreTags.OutlineType.Disabled))
            .build()
      );
   private final Setting<SettingColor> flWHzEyn = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                  .name("outline-gradient-start"))
               .defaultValue(new SettingColor(255, 100, 100))
               .visible(() -> (Boolean)this.fStlZIvY9lS8.get() && this.nOwp0NYl.get() == MoreTags.OutlineType.Gradient))
            .build()
      );
   private final Setting<SettingColor> FVjbhYkk53UT = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                  .name("outline-gradient-end"))
               .defaultValue(new SettingColor(100, 100, 255))
               .visible(() -> (Boolean)this.fStlZIvY9lS8.get() && this.nOwp0NYl.get() == MoreTags.OutlineType.Gradient))
            .build()
      );
   private final Setting<MoreTags.DistanceType> Nt3xeF = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)new meteordevelopment.meteorclient.settings.EnumSetting.Builder()
                     .name("distance"))
                  .defaultValue(MoreTags.DistanceType.Auto))
               .visible(this.fStlZIvY9lS8::get))
            .build()
      );
   private final Setting<SettingColor> zXcO5rZ = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                  .name("distance-color"))
               .defaultValue(new SettingColor(Color.WHITE))
               .visible(() -> (Boolean)this.fStlZIvY9lS8.get() && this.Nt3xeF.get() == MoreTags.DistanceType.Color))
            .build()
      );
   private final Setting<Boolean> jbUe84V1vykfK = this.TJImwK2
      .add(
         ((Builder)((Builder)((Builder)((Builder)new Builder().name("show-skin")).description("Show the player's face icon next to their name."))
                  .defaultValue(true))
               .visible(this.fStlZIvY9lS8::get))
            .build()
      );
   private final Setting<Double> zbN84Osn9p5lD = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)new meteordevelopment.meteorclient.settings.DoubleSetting.Builder()
                  .name("skin-scale"))
               .defaultValue(1.0)
               .sliderRange(0.5, 3.0)
               .decimalPlaces(1)
               .visible(() -> (Boolean)this.fStlZIvY9lS8.get() && (Boolean)this.jbUe84V1vykfK.get()))
            .build()
      );
   private final Setting<Boolean> ZlNrrNcv3Ylc = this.TJImwK2
      .add(
         ((Builder)((Builder)((Builder)((Builder)new Builder().name("show-items")).description("Show armor and held items above the nametag."))
                  .defaultValue(true))
               .visible(this.fStlZIvY9lS8::get))
            .build()
      );
   private final Setting<Double> EVSMbf = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)new meteordevelopment.meteorclient.settings.DoubleSetting.Builder()
                  .name("item-scale"))
               .defaultValue(1.0)
               .sliderRange(0.5, 2.0)
               .decimalPlaces(1)
               .visible(() -> (Boolean)this.fStlZIvY9lS8.get() && (Boolean)this.ZlNrrNcv3Ylc.get()))
            .build()
      );
   private final Setting<Double> pF16zz4NJECfg7 = this.TJImwK2
      .add(
         ((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)new meteordevelopment.meteorclient.settings.DoubleSetting.Builder()
                     .name("row-spacing"))
                  .description("Vertical spacing between nametag rows."))
               .defaultValue(2.0)
               .sliderRange(0.0, 10.0)
               .decimalPlaces(1)
               .visible(this.fStlZIvY9lS8::get))
            .build()
      );
   private final Setting<MoreTags.PearlRenderingType> WGmfQKK = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)new meteordevelopment.meteorclient.settings.EnumSetting.Builder()
                     .name("pearl-rendering"))
                  .description("Choose whether to render only pearls that have been assigned an owner or render all pearls."))
               .defaultValue(MoreTags.PearlRenderingType.Assigned))
            .build()
      );
   private final Setting<SettingColor> icQ738dpWr7W = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                     .name("assigned-rendering-color"))
                  .description("Outline color of a pearl whose owner has been resolved."))
               .defaultValue(new SettingColor(Color.MAGENTA))
               .visible(() -> this.WGmfQKK.get() == MoreTags.PearlRenderingType.Assigned || this.WGmfQKK.get() == MoreTags.PearlRenderingType.Both))
            .build()
      );
   private final Setting<SettingColor> JAj4YwE2MVx = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                     .name("unknown-rendering-color"))
                  .description("Outline color of a pearl whose owner is still unresolved."))
               .defaultValue(new SettingColor(Color.WHITE))
               .visible(() -> this.WGmfQKK.get() == MoreTags.PearlRenderingType.Both))
            .build()
      );
   private final Setting<MoreTags.TagDisplayType> JPp6VyZP9f = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)new meteordevelopment.meteorclient.settings.EnumSetting.Builder()
                     .name("tag-display"))
                  .description("What to display above the pearl."))
               .defaultValue(MoreTags.TagDisplayType.Both))
            .build()
      );
   private final Setting<Boolean> MR1DKzw = this.wSJeYsJcmjNZf
      .add(
         ((Builder)((Builder)((Builder)((Builder)new Builder().name("tag-distance-scaling"))
                     .description("Scales the tag automatically based on the distance from the player."))
                  .defaultValue(true))
               .visible(() -> this.JPp6VyZP9f.get() != MoreTags.TagDisplayType.Disabled))
            .build()
      );
   private final Setting<Boolean> DqGSCWplcjMi = this.wSJeYsJcmjNZf
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("lock-tag"))
                  .description("Locks the tag's position 1 block above the pearl (stops it from bobbing in stasis chambers)."))
               .defaultValue(true))
            .build()
      );
   private final Setting<SettingColor> cO1FU7fW = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                     .name("tag-color"))
                  .description("Color of the owner's tag rendered above a pearl."))
               .defaultValue(new SettingColor(Color.CYAN))
               .visible(() -> this.JPp6VyZP9f.get() != MoreTags.TagDisplayType.Disabled))
            .build()
      );
   private final Setting<Double> g0iiTDJzfIGXdui = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)new meteordevelopment.meteorclient.settings.DoubleSetting.Builder()
                     .name("tag-scale"))
                  .description("Scale of the nametag rendered above pearls."))
               .defaultValue(1.0)
               .decimalPlaces(1)
               .visible(() -> this.JPp6VyZP9f.get() != MoreTags.TagDisplayType.Disabled))
            .build()
      );
   private final Setting<Double> VIbihOaIVyhvJw = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)new meteordevelopment.meteorclient.settings.DoubleSetting.Builder()
                     .name("skin-scale"))
                  .description("Scale of the player face icon rendered above a pearl."))
               .defaultValue(1.0)
               .decimalPlaces(1)
               .visible(() -> this.JPp6VyZP9f.get() == MoreTags.TagDisplayType.Skin || this.JPp6VyZP9f.get() == MoreTags.TagDisplayType.Both))
            .build()
      );
   public static final Map<Integer, String> QfZy2gWnKInxFPr = new HashMap<>();
   private final Set<Integer> RE8q1TMDRWx = new HashSet<>();
   private final Map<Integer, Integer> vLRVDxlh = new HashMap<>();
   private final Map<String, Identifier> HomZ7BbhM7wB = new HashMap<>();
   private final Set<String> lVX1t0MC = new HashSet<>();

   public MoreTags() {
      super(musheor.musheor.MAIN, "kek-tags", "Enhanced nametags for players, pearls and items (WIP)");
   }

   public void onActivate() {
      GyhoRo.wSJeYsJcmjNZf();
      if (this.mc.world != null) {
         for (Entity entity : this.mc.world.getEntities()) {
            if (entity instanceof EnderPearlEntity pearl) {
               Entity owner = pearl.getOwner();
               if (owner != null) {
                  String name = owner.getName().getString();
                  QfZy2gWnKInxFPr.put(pearl.getId(), name);
                  BlockPos pos = this.wSJeYsJcmjNZf(pearl);
                  if (pos != null) {
                     GyhoRo.fStlZIvY9lS8(name, pos, pearl.getId());
                  }
               } else {
                  BlockPos pos = this.wSJeYsJcmjNZf(pearl);
                  if (pos != null) {
                     GyhoRo.gVOhtBej1x record = GyhoRo.fStlZIvY9lS8(pos);
                     if (record != null) {
                        QfZy2gWnKInxFPr.put(pearl.getId(), record.fStlZIvY9lS8);
                        GyhoRo.fStlZIvY9lS8(pos, pearl.getId());
                        continue;
                     }
                  }

                  Integer ownerEntityId = this.vLRVDxlh.get(pearl.getId());
                  if (ownerEntityId != null && this.mc.world.getEntityById(ownerEntityId) instanceof PlayerEntity player) {
                     String name = player.getName().getString();
                     QfZy2gWnKInxFPr.put(pearl.getId(), name);
                     if (pos != null) {
                        GyhoRo.fStlZIvY9lS8(name, pos, pearl.getId());
                     }
                  } else {
                     Iterator var13 = GyhoRo.fStlZIvY9lS8().iterator();

                     while (true) {
                        if (var13.hasNext()) {
                           GyhoRo.gVOhtBej1x record = (GyhoRo.gVOhtBej1x)var13.next();
                           if (record.TJImwK2 != pearl.getId()) {
                              continue;
                           }

                           QfZy2gWnKInxFPr.put(pearl.getId(), record.fStlZIvY9lS8);
                           GyhoRo.fStlZIvY9lS8(record.QfZy2gWnKInxFPr, pearl.getId());
                        }

                        this.RE8q1TMDRWx.add(pearl.getId());
                        break;
                     }
                  }
               }
            }
         }
      }
   }

   public void onDeactivate() {
      QfZy2gWnKInxFPr.clear();
      this.RE8q1TMDRWx.clear();
      this.HomZ7BbhM7wB.forEach((name, id) -> this.mc.getTextureManager().destroyTexture(id));
      this.HomZ7BbhM7wB.clear();
      this.lVX1t0MC.clear();
   }

   @EventHandler(priority = -200)
   private void fStlZIvY9lS8(EntityAddedEvent event) {
      if (this.mc.world != null && this.mc.player != null) {
         if (event.entity instanceof EnderPearlEntity pearl) {
            this.TJImwK2(pearl);
         } else if (event.entity instanceof PlayerEntity player) {
            String name = player.getName().getString();
            if (this.HomZ7BbhM7wB.containsKey(name)) {
               this.mc.getTextureManager().destroyTexture(this.HomZ7BbhM7wB.get(name));
               this.HomZ7BbhM7wB.remove(name);
            }

            this.vLRVDxlh.entrySet().removeIf(entry -> {
               if (entry.getValue() == player.getId()) {
                  int pearlId = entry.getKey();
                  QfZy2gWnKInxFPr.put(pearlId, player.getName().getString());
                  if (this.mc.world.getEntityById(pearlId) instanceof EnderPearlEntity pearl2) {
                     BlockPos pos = this.wSJeYsJcmjNZf(pearl2);
                     if (pos != null) {
                        GyhoRo.fStlZIvY9lS8(player.getName().getString(), pos, pearlId);
                     }
                  }

                  this.RE8q1TMDRWx.remove(pearlId);
                  return true;
               } else {
                  return false;
               }
            });
            this.fStlZIvY9lS8();
         }
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(Post event) {
      if (this.mc.world != null && !this.RE8q1TMDRWx.isEmpty()) {
         this.fStlZIvY9lS8();
      }
   }

   private void TJImwK2(EnderPearlEntity pearl) {
      Entity owner = pearl.getOwner();
      if (owner != null) {
         String name = owner.getName().getString();
         QfZy2gWnKInxFPr.put(pearl.getId(), name);
         BlockPos pos = this.wSJeYsJcmjNZf(pearl);
         if (pos != null) {
            GyhoRo.fStlZIvY9lS8(name, pos, pearl.getId());
         }
      } else {
         BlockPos pos = this.wSJeYsJcmjNZf(pearl);
         if (pos != null) {
            GyhoRo.gVOhtBej1x record = GyhoRo.fStlZIvY9lS8(pos);
            if (record != null) {
               QfZy2gWnKInxFPr.put(pearl.getId(), record.fStlZIvY9lS8);
               GyhoRo.fStlZIvY9lS8(pos, pearl.getId());
               return;
            }
         }

         this.RE8q1TMDRWx.add(pearl.getId());
      }
   }

   private void fStlZIvY9lS8() {
      this.RE8q1TMDRWx.removeIf(id -> {
         Entity entity = this.mc.world.getEntityById(id);
         if (entity == null) {
            return true;
         }

         if (entity instanceof EnderPearlEntity pearl) {
            UUID ownerUuid = this.i2Nu6XHKic(pearl);
            if (ownerUuid == null) {
               return false;
            }

            PlayerEntity owner = this.mc.world.getPlayers().stream().filter(p -> p.getUuid().equals(ownerUuid)).findFirst().orElse(null);
            if (owner == null) {
               return false;
            }

            String name = owner.getName().getString();
            QfZy2gWnKInxFPr.put(id, name);
            BlockPos pos = this.wSJeYsJcmjNZf(pearl);
            if (pos != null) {
               GyhoRo.fStlZIvY9lS8(name, pos, id);
            }

            return true;
         } else {
            return true;
         }
      });
   }

   private BlockPos wSJeYsJcmjNZf(EnderPearlEntity pearl) {
      if (this.mc.world == null) {
         return null;
      }

      BlockPos base = pearl.getBlockPos();

      for (BlockPos candidate : new BlockPos[]{base.down(), base}) {
         if (this.mc.world.getBlockState(candidate).getBlock() instanceof TrapdoorBlock) {
            return candidate;
         }
      }

      return null;
   }

   public void fStlZIvY9lS8(int pearlId, int ownerEntityId) {
      this.vLRVDxlh.put(pearlId, ownerEntityId);
   }

   private UUID i2Nu6XHKic(EnderPearlEntity pearl) {
      try {
         Field f = ProjectileEntity.class.getDeclaredField("ownerUuid");
         f.setAccessible(true);
         return (UUID)f.get(pearl);
      } catch (Exception e) {
         return null;
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(Render2DEvent event) {
      if (this.JPp6VyZP9f.get() != MoreTags.TagDisplayType.Disabled && this.mc.world != null && !QfZy2gWnKInxFPr.isEmpty()) {
         boolean showSkin = this.JPp6VyZP9f.get() == MoreTags.TagDisplayType.Skin || this.JPp6VyZP9f.get() == MoreTags.TagDisplayType.Both;
         boolean showName = this.JPp6VyZP9f.get() == MoreTags.TagDisplayType.NameTag || this.JPp6VyZP9f.get() == MoreTags.TagDisplayType.Both;

         for (Entry<Integer, String> entry : QfZy2gWnKInxFPr.entrySet()) {
            if (this.mc.world.getEntityById(entry.getKey()) instanceof EnderPearlEntity pearl) {
               String playerName = entry.getValue();
               if (showSkin) {
                  this.fStlZIvY9lS8(playerName);
               }

               Vec3d lerpedPos = pearl.getLerpedPos(event.tickDelta);
               double lerpedY = lerpedPos.y + pearl.getHeight() + 0.5;
               Vector3d pos = new Vector3d(lerpedPos.x, !this.DqGSCWplcjMi.get() ? lerpedY : Math.ceil(lerpedY), lerpedPos.z);
               if (NametagUtils.to2D(pos, (Double)this.g0iiTDJzfIGXdui.get(), (Boolean)this.MR1DKzw.get())) {
                  NametagUtils.begin(pos);
                  TextRenderer textRenderer = TextRenderer.get();
                  float iconSize = (float)(8.0 * (Double)this.VIbihOaIVyhvJw.get() * 2.0);
                  boolean hasSkin = showSkin && this.HomZ7BbhM7wB.containsKey(playerName);
                  if (hasSkin) {
                     Identifier skinId = this.HomZ7BbhM7wB.get(playerName);
                     float x0 = showName ? -iconSize - 2.0F : -iconSize / 2.0F;
                     float y0 = -iconSize / 2.0F;
                     VersionHelper.get().drawSkinTexture(skinId, x0, y0, iconSize, iconSize);
                  }

                  if (showName) {
                     textRenderer.beginBig();
                     double w = textRenderer.getWidth(playerName);
                     float nameX = hasSkin ? 2.0F : (float)(-w / 2.0);
                     textRenderer.render(
                        playerName, nameX, -textRenderer.getHeight() / 2.0, (meteordevelopment.meteorclient.utils.render.color.Color)this.cO1FU7fW.get()
                     );
                     textRenderer.end();
                  }

                  NametagUtils.end();
               }
            }
         }
      }
   }

   @EventHandler
   private void QfZy2gWnKInxFPr(Render2DEvent event) {
      if ((Boolean)this.fStlZIvY9lS8.get() && this.mc.world != null) {
         for (PlayerEntity player : this.mc.world.getPlayers()) {
            boolean noSpecialView = !((Freecam)Modules.get().get(Freecam.class)).isActive() && this.mc.options.getPerspective().isFirstPerson();
            if (player != this.mc.player || !(Boolean)this.qhulAUwd.get() && !noSpecialView) {
               String name = player.getName().getString();
               boolean friend = AVZhkxEO.vGAMA89H(player);
               double distance = AVZhkxEO.i2Nu6XHKic(player);
               if ((Boolean)this.jbUe84V1vykfK.get()) {
                  this.fStlZIvY9lS8(name);
               }

               Vec3d headPos = player.getLerpedPos(event.tickDelta).add(0.0, player.getEyeHeight(player.getPose()) + 0.6, 0.0);
               Vector3d pos = new Vector3d(headPos.x, headPos.y, headPos.z);
               if (NametagUtils.to2D(pos, (Double)this.i2Nu6XHKic.get(), (Boolean)this.vGAMA89H.get())) {
                  NametagUtils.begin(pos);
                  TextRenderer tr = TextRenderer.get();
                  float pad = ((Double)this.xileXsbo.get()).floatValue();
                  float spacing = ((Double)this.pF16zz4NJECfg7.get()).floatValue();
                  float iconSize = (float)(8.0 * (Double)this.zbN84Osn9p5lD.get() * 2.0);
                  boolean hasSkin = (Boolean)this.jbUe84V1vykfK.get() && this.HomZ7BbhM7wB.containsKey(name);
                  tr.beginBig();
                  float nameW = (float)tr.getWidth(name);
                  float nameH = (float)tr.getHeight();
                  tr.end();
                  float skinW = hasSkin ? iconSize + 2.0F : 0.0F;
                  float totalNameLineW = nameW + skinW;
                  float totalNameLineH = Math.max(nameH, hasSkin ? iconSize : 0.0F);
                  float bgX = -(totalNameLineW / 2.0F) - pad;
                  float bgY = -(totalNameLineH / 2.0F) - pad;
                  float bgW = totalNameLineW + pad * 2.0F;
                  float bgH = totalNameLineH + pad * 2.0F;
                  if ((Boolean)this.V7zKJ4n6D.get()) {
                     Renderer2D.COLOR.begin();
                     Renderer2D.COLOR.quad(bgX, bgY, bgW, bgH, (meteordevelopment.meteorclient.utils.render.color.Color)this.voxTwgNzyrpaAnP.get());
                     VersionHelper.get().renderColorRenderer();
                  }

                  if (this.nOwp0NYl.get() != MoreTags.OutlineType.Disabled) {
                     int colA;
                     int colB;
                     if (this.nOwp0NYl.get() == MoreTags.OutlineType.Gradient) {
                        colA = this.fStlZIvY9lS8((SettingColor)this.flWHzEyn.get());
                        colB = this.fStlZIvY9lS8((SettingColor)this.FVjbhYkk53UT.get());
                     } else {
                        colA = this.fStlZIvY9lS8((SettingColor)this.umvNbb7iD9UpR.get());
                        colB = colA;
                     }

                     this.fStlZIvY9lS8(bgX, bgY, bgW, bgH, ((Double)this.zkcZ4Xkp9Ez7Y.get()).floatValue(), colA, colB);
                  }

                  if (hasSkin) {
                     Identifier skinId = this.HomZ7BbhM7wB.get(name);
                     float skinX = -(totalNameLineW / 2.0F);
                     float skinY = -iconSize / 2.0F;
                     VersionHelper.get().drawSkinTexture(skinId, skinX, skinY, iconSize, iconSize);
                  }

                  tr.beginBig();
                  float textX = hasSkin ? -(totalNameLineW / 2.0F) + skinW : -(nameW / 2.0F);
                  float textY = -(nameH / 2.0F);
                  if (friend) {
                     tr.render(name, textX, textY, (meteordevelopment.meteorclient.utils.render.color.Color)this.j376sIQ.get());
                  } else if (this.BrmKXSYhWhqoB1.get() == MoreTags.NameColorType.Gradient) {
                     this.fStlZIvY9lS8(tr, name, textX, textY, (SettingColor)this.I3psabmOrOI.get(), (SettingColor)this.duJ6sPeetAcBh6P.get());
                  } else if (this.BrmKXSYhWhqoB1.get() == MoreTags.NameColorType.Color) {
                     tr.render(name, textX, textY, (meteordevelopment.meteorclient.utils.render.color.Color)this.TXWyNb.get());
                  } else {
                     tr.render(name, textX, textY, new SettingColor(255, 255, 255));
                  }

                  tr.end();
                  if (this.Nt3xeF.get() != MoreTags.DistanceType.Disabled) {
                     String distStr = String.format("[%dm]", (int)distance);
                     tr.begin();
                     float distW = (float)tr.getWidth(distStr);
                     float distY = totalNameLineH / 2.0F + spacing;
                     int distCol;
                     if (this.Nt3xeF.get() == MoreTags.DistanceType.Auto) {
                        distCol = AVZhkxEO.fStlZIvY9lS8(distance);
                     } else {
                        SettingColor dc = (SettingColor)this.zXcO5rZ.get();
                        distCol = dc.r << 16 | dc.g << 8 | dc.b;
                     }

                     int dr = distCol >> 16 & 0xFF;
                     int dg = distCol >> 8 & 0xFF;
                     int db = distCol & 0xFF;
                     tr.render(distStr, -(distW / 2.0F), distY, new SettingColor(dr, dg, db, 255));
                     tr.end();
                  }

                  if ((Boolean)this.ZlNrrNcv3Ylc.get() && AVZhkxEO.wSJeYsJcmjNZf(player)) {
                     List<ItemStack> items = new ArrayList<>();
                     items.add(AVZhkxEO.QfZy2gWnKInxFPr(player));
                     items.addAll(AVZhkxEO.fStlZIvY9lS8(player));
                     items.add(AVZhkxEO.TJImwK2(player));
                     items.removeIf(ItemStack::isEmpty);
                     float itemSz = (float)(16.0 * (Double)this.EVSMbf.get());
                     float itemGap = 2.0F;
                     float totalItemW = items.size() * (itemSz + itemGap) - itemGap;
                     float itemStartX = -totalItemW / 2.0F;
                     float itemY = -(totalNameLineH / 2.0F) - pad - itemSz - spacing;

                     for (ItemStack stack : items) {
                        RenderUtils.drawItem(event.drawContext, stack, (int)itemStartX, (int)itemY, ((Double)this.EVSMbf.get()).floatValue(), false);
                        if (stack.getCount() > 1) {
                           String countStr = String.valueOf(stack.getCount());
                           tr.begin(0.5, false, true);
                           float countW = (float)tr.getWidth(countStr);
                           tr.render(countStr, itemStartX + itemSz - countW - 1.0F, itemY + itemSz - (float)tr.getHeight(), new SettingColor(255, 255, 255));
                           tr.end();
                        }

                        itemStartX += itemSz + itemGap;
                     }
                  }

                  NametagUtils.end();
               }
            }
         }
      }
   }

   public boolean fStlZIvY9lS8(EnderPearlEntity pearl) {
      if (this.isActive() && this.WGmfQKK.get() != MoreTags.PearlRenderingType.Disabled) {
         int id = pearl.getId();

         return switch ((MoreTags.PearlRenderingType)this.WGmfQKK.get()) {
            case Assigned -> QfZy2gWnKInxFPr.containsKey(id);
            case Both -> true;
            default -> false;
         };
      } else {
         return false;
      }
   }

   public int QfZy2gWnKInxFPr(EnderPearlEntity pearl) {
      SettingColor color = QfZy2gWnKInxFPr.containsKey(pearl.getId()) ? (SettingColor)this.icQ738dpWr7W.get() : (SettingColor)this.JAj4YwE2MVx.get();
      return color.r << 16 | color.g << 8 | color.b;
   }

   private void fStlZIvY9lS8(String playerName) {
      if (!this.HomZ7BbhM7wB.containsKey(playerName) && !this.lVX1t0MC.contains(playerName)) {
         this.lVX1t0MC.add(playerName);
         Thread thread = new Thread(() -> {
            try {
               URL url = new URL("https://mc-heads.net/avatar/" + playerName + "/64");
               HttpURLConnection conn = (HttpURLConnection)url.openConnection();
               conn.setConnectTimeout(5000);
               conn.setReadTimeout(5000);
               conn.setRequestProperty("User-Agent", "musheor-addon");
               BufferedImage img = ImageIO.read(conn.getInputStream());
               if (img == null) {
                  this.lVX1t0MC.remove(playerName);
                  return;
               }

               boolean online = this.QfZy2gWnKInxFPr(playerName);
               if (!online) {
                  img = this.fStlZIvY9lS8(img);
               }

               NativeImage nativeImage = this.QfZy2gWnKInxFPr(img);
               Identifier id = Identifier.of("musheor", "pearl_skin_" + playerName.toLowerCase());
               this.mc.execute(() -> {
                  VersionHelper.get().registerSkinTexture(nativeImage, playerName, id);
                  this.HomZ7BbhM7wB.put(playerName, id);
                  this.lVX1t0MC.remove(playerName);
               });
            } catch (Exception e) {
               this.lVX1t0MC.remove(playerName);
            }
         });
         thread.setDaemon(true);
         thread.start();
      }
   }

   private boolean QfZy2gWnKInxFPr(String playerName) {
      return VersionHelper.get().onSameServer(playerName);
   }

   private BufferedImage fStlZIvY9lS8(BufferedImage img) {
      BufferedImage gray = new BufferedImage(img.getWidth(), img.getHeight(), 2);

      for (int x = 0; x < img.getWidth(); x++) {
         for (int y = 0; y < img.getHeight(); y++) {
            int argb = img.getRGB(x, y);
            int a = argb >> 24 & 0xFF;
            int r = argb >> 16 & 0xFF;
            int g = argb >> 8 & 0xFF;
            int b = argb & 0xFF;
            int lum = (int)(0.299 * r + 0.587 * g + 0.114 * b);
            gray.setRGB(x, y, a << 24 | lum << 16 | lum << 8 | lum);
         }
      }

      return gray;
   }

   private NativeImage QfZy2gWnKInxFPr(BufferedImage img) {
      NativeImage nativeImage = new NativeImage(img.getWidth(), img.getHeight(), false);

      for (int x = 0; x < img.getWidth(); x++) {
         for (int y = 0; y < img.getHeight(); y++) {
            nativeImage.setColorArgb(x, y, img.getRGB(x, y));
         }
      }

      return nativeImage;
   }

   private void fStlZIvY9lS8(float x, float y, float w, float h, float r, int colA, int colB) {
      if (r < 0.0F) {
         r = 0.0F;
      }

      int n = 10;
      float[][] ctr = new float[][]{{x + r, y + r}, {x + w - r, y + r}, {x + w - r, y + h - r}, {x + r, y + h - r}};
      double[] arcStart = new double[]{Math.PI, Math.PI * 3.0 / 2.0, 0.0, Math.PI / 2};
      Renderer2D.COLOR.begin();
      float prevX = 0.0F;
      float prevY = 0.0F;
      boolean first = true;

      for (int c = 0; c < 4; c++) {
         for (int i = 0; i <= n; i++) {
            double a = arcStart[c] + i * (Math.PI / 2) / n;
            float vx = ctr[c][0] + (float)(Math.cos(a) * r);
            float vy = ctr[c][1] + (float)(Math.sin(a) * r);
            if (!first) {
               this.fStlZIvY9lS8(prevX, prevY, vx, vy, x, w, colA, colB);
            }

            first = false;
            prevX = vx;
            prevY = vy;
         }

         int nextC = (c + 1) % 4;
         float ex = ctr[nextC][0] + (float)(Math.cos(arcStart[nextC]) * r);
         float ey = ctr[nextC][1] + (float)(Math.sin(arcStart[nextC]) * r);
         float dx = (ex - prevX) / n;
         float dy = (ey - prevY) / n;

         for (int i = 0; i < n; i++) {
            float nx = prevX + dx;
            float ny = prevY + dy;
            this.fStlZIvY9lS8(prevX, prevY, nx, ny, x, w, colA, colB);
            prevX = nx;
            prevY = ny;
         }
      }

      VersionHelper.get().renderColorRenderer();
   }

   private void fStlZIvY9lS8(float x1, float y1, float x2, float y2, float bx, float bw, int colA, int colB) {
      float t = Math.max(0.0F, Math.min(1.0F, ((x1 + x2) / 2.0F - bx) / bw));
      int col = AVZhkxEO.fStlZIvY9lS8(colA, colB, t);
      Renderer2D.COLOR.line(x1, y1, x2, y2, new SettingColor(col >> 16 & 0xFF, col >> 8 & 0xFF, col & 0xFF, 255));
   }

   private void fStlZIvY9lS8(TextRenderer tr, String text, float x, float y, SettingColor start, SettingColor end) {
      float cursor = x;
      float totalW = (float)tr.getWidth(text);

      for (int i = 0; i < text.length(); i++) {
         String ch = String.valueOf(text.charAt(i));
         float t = totalW > 0.0F ? (cursor - x) / totalW : 0.0F;
         int col = AVZhkxEO.fStlZIvY9lS8(this.fStlZIvY9lS8(start), this.fStlZIvY9lS8(end), t);
         SettingColor c = new SettingColor(col >> 16 & 0xFF, col >> 8 & 0xFF, col & 0xFF, 255);
         tr.render(ch, cursor, y, c);
         cursor += (float)tr.getWidth(ch);
      }
   }

   private int fStlZIvY9lS8(SettingColor c) {
      return c.r << 16 | c.g << 8 | c.b;
   }

   private enum DistanceType {
      Disabled,
      Color,
      Auto;

      DistanceType() {
      }
   }

   private enum NameColorType {
      Default,
      Color,
      Gradient;

      NameColorType() {
      }
   }

   private enum OutlineType {
      Disabled,
      Color,
      Gradient;

      OutlineType() {
      }
   }

   private enum PearlRenderingType {
      Disabled,
      Assigned,
      Both;

      PearlRenderingType() {
      }
   }

   private enum TagDisplayType {
      Disabled,
      NameTag,
      Skin,
      Both;

      TagDisplayType() {
      }
   }
}
