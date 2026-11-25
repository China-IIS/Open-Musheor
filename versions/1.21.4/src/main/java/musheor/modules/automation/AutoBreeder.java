package musheor.modules.automation;

import baritone.api.pathing.goals.GoalBlock;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.EntityTypeListSetting.Builder;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import meteordevelopment.meteorclient.utils.player.Rotations;
import meteordevelopment.orbit.EventHandler;
import musheor.compat.VersionHelper;
import musheor.utils.manager.pvfA15fQSVM;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.util.Hand;
import obf.GyhoRo;

public class AutoBreeder extends Module {
   private final MinecraftClient fStlZIvY9lS8 = MinecraftClient.getInstance();
   private final SettingGroup QfZy2gWnKInxFPr = this.settings.getDefaultGroup();
   private final Setting<Set<EntityType<?>>> TJImwK2 = this.QfZy2gWnKInxFPr
      .add(
         ((Builder)((Builder)new Builder().name("entities")).description("Specific entities to breed."))
            .defaultValue(new EntityType[0])
            .onlyAttackable()
            .build()
      );
   private final Setting<Double> wSJeYsJcmjNZf = this.QfZy2gWnKInxFPr
      .add(
         ((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)new meteordevelopment.meteorclient.settings.DoubleSetting.Builder()
                  .name("distance"))
               .description("Maximum distance to pathfind to new mobs."))
            .defaultValue(32.0)
            .sliderRange(4.0, 128.0)
            .decimalPlaces(1)
            .build()
      );
   private final Map<UUID, Long> i2Nu6XHKic = new HashMap<>();
   private UUID vGAMA89H = null;

   public AutoBreeder() {
      super(musheor.musheor.AUTOMATION, "auto-breeder", "Automatically breeds specific animals.");
   }

   public void onActivate() {
      this.i2Nu6XHKic.clear();
      this.vGAMA89H = null;
   }

   public void onDeactivate() {
      GyhoRo.wSJeYsJcmjNZf();
   }

   @EventHandler
   private void fStlZIvY9lS8(Pre event) {
      if (this.fStlZIvY9lS8.player != null && this.fStlZIvY9lS8.world != null) {
         long now = System.currentTimeMillis();
         this.i2Nu6XHKic.entrySet().removeIf(e -> now - e.getValue() >= 300000L);
         List<AnimalEntity> candidates = new ArrayList<>();

         for (Entity entity : this.fStlZIvY9lS8.world.getEntities()) {
            if (entity instanceof AnimalEntity animal
               && ((Set)this.TJImwK2.get()).contains(animal.getType())
               && !this.i2Nu6XHKic.containsKey(animal.getUuid())
               && !animal.isBaby()
               && animal.isBreedingItem(this.fStlZIvY9lS8.player.getMainHandStack())
               && PlayerUtils.isWithin(animal, (Double)this.wSJeYsJcmjNZf.get())) {
               candidates.add(animal);
            }
         }

         candidates.sort(Comparator.comparingDouble(a -> a.squaredDistanceTo(this.fStlZIvY9lS8.player)));
         if (!candidates.isEmpty()) {
            AnimalEntity first = candidates.getFirst();
            if (!first.isBreedingItem(this.fStlZIvY9lS8.player.getMainHandStack())) {
               for (int slot = 0; slot < this.fStlZIvY9lS8.player.getInventory().main.size(); slot++) {
                  ItemStack stack = this.fStlZIvY9lS8.player.getInventory().getStack(slot);
                  if (first.isBreedingItem(stack) && slot < 8) {
                     this.fStlZIvY9lS8.player.getInventory().selectedSlot = slot;
                  } else {
                     if (!first.isBreedingItem(stack)) {
                        this.info("No breeding items found in inventory, disabling...", new Object[0]);
                        this.toggle();
                        return;
                     }

                     pvfA15fQSVM.fStlZIvY9lS8(stack.getItem());
                  }
               }

               return;
            }
         }

         Iterator var10 = candidates.iterator();
         if (var10.hasNext()) {
            AnimalEntity animal = (AnimalEntity)var10.next();
            if (animal.isInRange(this.fStlZIvY9lS8.player, 2.5)) {
               GyhoRo.wSJeYsJcmjNZf();
               this.vGAMA89H = null;
               float yaw = (float)Rotations.getYaw(animal);
               float pitch = (float)Rotations.getPitch(animal);
               Rotations.rotate(yaw, pitch);
               this.fStlZIvY9lS8
                  .interactionManager
                  .sendSequencedPacket(this.fStlZIvY9lS8.world, sequence -> new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, sequence, yaw, pitch));
               VersionHelper.get().interactEntityAt(animal, Hand.MAIN_HAND);
               this.fStlZIvY9lS8
                  .interactionManager
                  .sendSequencedPacket(this.fStlZIvY9lS8.world, sequence -> PlayerInteractEntityC2SPacket.interact(animal, false, Hand.MAIN_HAND));
               this.i2Nu6XHKic.put(animal.getUuid(), System.currentTimeMillis());
            } else {
               UUID uuid = animal.getUuid();
               if (!uuid.equals(this.vGAMA89H) || !GyhoRo.fStlZIvY9lS8()) {
                  this.vGAMA89H = uuid;
                  GyhoRo.fStlZIvY9lS8(new GoalBlock(animal.getBlockPos()));
               }
            }
         }
      }
   }
}
