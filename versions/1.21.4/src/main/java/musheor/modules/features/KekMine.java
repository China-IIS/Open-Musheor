package musheor.modules.features;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.BoolSetting.Builder;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import musheor.compat.VersionHelper;
import musheor.modules.automation.HighwayBuilder;
import musheor.modules.automation.KekNuker;
import musheor.utils.jU8xXhmkg;
import musheor.utils.manager.pvfA15fQSVM;
import musheor.utils.system.MusheorSystem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public class KekMine extends Module {
   private static final MinecraftClient voxTwgNzyrpaAnP = MinecraftClient.getInstance();
   private final SettingGroup xileXsbo = this.settings.getDefaultGroup();
   private final SettingGroup nOwp0NYl = this.settings.createGroup("Grim");
   private final SettingGroup umvNbb7iD9UpR = this.settings.createGroup("Render");
   public final Setting<Boolean> fStlZIvY9lS8 = this.xileXsbo
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("prevent-tool-breaking")).description("Prevents you from breaking your tool items"))
               .defaultValue(true))
            .build()
      );
   public final Setting<Integer> QfZy2gWnKInxFPr = this.xileXsbo
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("min-tool-durability"))
                     .description("The minimum durability threshold of a usable tool"))
                  .defaultValue(25))
               .sliderRange(0, 500)
               .visible(this.fStlZIvY9lS8::get))
            .build()
      );
   private final Setting<Boolean> zkcZ4Xkp9Ez7Y = this.xileXsbo
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("silent-swap")).description("Breaks the block without holding the pickaxe")).defaultValue(false))
            .build()
      );
   public final Setting<Integer> TJImwK2 = this.xileXsbo
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                        .name("hold-ticks"))
                     .description(
                        "Ticks to keep the tool selected after a silent break before restoring. Prevents wrong-tool drops when sequenced block packets and slot packets race (0 = restore immediately)"
                     ))
                  .defaultValue(3))
               .min(0)
               .max(10)
               .visible(this.zkcZ4Xkp9Ez7Y::get))
            .build()
      );
   private final Setting<Boolean> flWHzEyn = this.xileXsbo
      .add(
         ((Builder)((Builder)((Builder)((Builder)new Builder().name("debug"))
                     .description("Logs the break packet sequence (start / burst / park / stop / confirm) to chat"))
                  .defaultValue(false))
               .visible(() -> false))
            .build()
      );
   public final Setting<Boolean> wSJeYsJcmjNZf = this.nOwp0NYl
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("grim-bypass")).description("Old Grim V3 bypass, no longer works on 2b2t or other Grim V2 servers."))
               .defaultValue(false))
            .build()
      );
   public final Setting<Double> i2Nu6XHKic = this.nOwp0NYl
      .add(
         ((meteordevelopment.meteorclient.settings.DoubleSetting.Builder)new meteordevelopment.meteorclient.settings.DoubleSetting.Builder()
               .name("break-threshold"))
            .defaultValue(0.7)
            .min(0.1)
            .max(1.0)
            .decimalPlaces(2)
            .build()
      );
   public final Setting<Integer> vGAMA89H = this.nOwp0NYl
      .add(
         ((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)((meteordevelopment.meteorclient.settings.IntSetting.Builder)new meteordevelopment.meteorclient.settings.IntSetting.Builder()
                     .name("break-timeout"))
                  .description("Time in ticks to wait before attempting to break the same block again (higher = safer for high ping)"))
               .defaultValue(5))
            .sliderRange(0, 20)
            .build()
      );
   public final Setting<Boolean> qhulAUwd = this.nOwp0NYl.add(((Builder)((Builder)new Builder().name("double-break")).defaultValue(true)).build());
   public final Setting<Boolean> BrmKXSYhWhqoB1 = this.nOwp0NYl
      .add(
         ((Builder)((Builder)((Builder)((Builder)new Builder().name("alternate"))
                     .description("Alternate between a single and a double break to trick grim's break timeout (prevents you from mine-failing)."))
                  .defaultValue(true))
               .visible(this.qhulAUwd::get))
            .build()
      );
   public final Setting<Boolean> TXWyNb = this.nOwp0NYl
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("validate-break"))
                  .description("Waits for the server to validate the break. Disable when you have high ping (may cause rubberbanding)"))
               .defaultValue(true))
            .build()
      );
   public final Setting<Boolean> I3psabmOrOI = this.nOwp0NYl
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("auto-rebreak")).description("Automatically rebreak the last block in case it gets replaced"))
               .defaultValue(false))
            .build()
      );
   private final Setting<Boolean> FVjbhYkk53UT = this.umvNbb7iD9UpR
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("global-rendering")).defaultValue(true)).description("Synchronize rendering with Musheor-Tab"))
            .build()
      );
   private final Setting<SettingColor> Nt3xeF = this.umvNbb7iD9UpR
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
                     .name("color"))
                  .defaultValue(new SettingColor(Color.cyan))
                  .description("Custom color for rendering (lines / wireframe)"))
               .visible(() -> !(Boolean)this.FVjbhYkk53UT.get()))
            .build()
      );
   public static KekMine duJ6sPeetAcBh6P;
   private KekMine.MineContext zXcO5rZ;
   private KekMine.MineContext jbUe84V1vykfK;
   public BlockPos j376sIQ;
   public final Deque<BlockPos> V7zKJ4n6D = new ArrayDeque<>();
   private int zbN84Osn9p5lD = -1;
   private int ZlNrrNcv3Ylc = -1;
   private int EVSMbf = -1;
   private int pF16zz4NJECfg7 = 0;
   private long WGmfQKK = 0L;
   private boolean icQ738dpWr7W = false;
   private long JAj4YwE2MVx = 0L;
   private boolean JPp6VyZP9f = false;
   private boolean MR1DKzw = true;

   public KekMine() {
      super(musheor.musheor.MAIN, "KekMine", "Grim-safe packet miner with START-offset fast break.");
      duJ6sPeetAcBh6P = this;
   }

   static float fStlZIvY9lS8(BlockState state, BlockPos pos, ItemStack tool) {
      if (voxTwgNzyrpaAnP.player != null && voxTwgNzyrpaAnP.world != null) {
         float hardness = state.getHardness(voxTwgNzyrpaAnP.world, pos);
         int h = state.isToolRequired() && (tool == null || !tool.isSuitableFor(state)) ? 100 : 30;
         float speed = tool != null && !tool.isEmpty() ? tool.getMiningSpeedMultiplier(state) : 1.0F;
         if (speed > 1.0F) {
            int efficiency = Utils.getEnchantmentLevel(tool, Enchantments.EFFICIENCY);
            if (efficiency > 0) {
               speed += efficiency * efficiency + 1;
            }
         }

         if (StatusEffectUtil.hasHaste(voxTwgNzyrpaAnP.player)) {
            speed *= 1.0F + (StatusEffectUtil.getHasteAmplifier(voxTwgNzyrpaAnP.player) + 1) * 0.2F;
         }

         if (voxTwgNzyrpaAnP.player.hasStatusEffect(StatusEffects.MINING_FATIGUE)) {
            float i = switch (voxTwgNzyrpaAnP.player.getStatusEffect(StatusEffects.MINING_FATIGUE).getAmplifier()) {
               case 0 -> 0.3F;
               case 1 -> 0.09F;
               case 2 -> 0.0027F;
               default -> 8.1E-4F;
            };
            speed *= i;
         }

         speed *= (float)voxTwgNzyrpaAnP.player.getAttributeValue(EntityAttributes.BLOCK_BREAK_SPEED);
         if (voxTwgNzyrpaAnP.player.isSubmergedIn(FluidTags.WATER)) {
            speed *= (float)voxTwgNzyrpaAnP.player.getAttributeValue(EntityAttributes.SUBMERGED_MINING_SPEED);
         }

         if (!voxTwgNzyrpaAnP.player.isOnGround()) {
            speed /= 5.0F;
         }

         return speed / hardness / h;
      } else {
         return 0.0F;
      }
   }

   public static boolean fStlZIvY9lS8(BlockPos pos) {
      if (voxTwgNzyrpaAnP.player != null && voxTwgNzyrpaAnP.world != null) {
         BlockState state = voxTwgNzyrpaAnP.world.getBlockState(pos);
         return state.isAir() ? false : fStlZIvY9lS8(state, pos, QfZy2gWnKInxFPr(state)) >= 1.0F;
      } else {
         return false;
      }
   }

   private static boolean fStlZIvY9lS8(ItemStack stack, BlockState state) {
      return !state.isToolRequired() || stack.isSuitableFor(state);
   }

   private static ItemStack QfZy2gWnKInxFPr(BlockState state) {
      if (voxTwgNzyrpaAnP.player == null) {
         return ItemStack.EMPTY;
      }

      ItemStack hand = voxTwgNzyrpaAnP.player.getMainHandStack();
      ItemStack best = hand;
      float bestSpeed = fStlZIvY9lS8(hand, state) ? hand.getMiningSpeedMultiplier(state) : 0.0F;

      for (int i = 0; i < 36; i++) {
         ItemStack stack = voxTwgNzyrpaAnP.player.getInventory().getStack(i);
         if (!stack.isEmpty() && (!(Boolean)duJ6sPeetAcBh6P.fStlZIvY9lS8.get() || pvfA15fQSVM.fStlZIvY9lS8(stack)) && fStlZIvY9lS8(stack, state)) {
            float speed = stack.getMiningSpeedMultiplier(state);
            if (speed > bestSpeed) {
               bestSpeed = speed;
               best = stack;
            }
         }
      }

      return best;
   }

   public static boolean fStlZIvY9lS8(BlockState state) {
      if (voxTwgNzyrpaAnP.player == null) {
         return false;
      }

      ItemStack hand = voxTwgNzyrpaAnP.player.getMainHandStack();
      return fStlZIvY9lS8(hand, state) && hand.getMiningSpeedMultiplier(state) > 1.0F;
   }

   private boolean TJImwK2(BlockState state) {
      if (voxTwgNzyrpaAnP.player == null) {
         return true;
      }

      ItemStack hand = voxTwgNzyrpaAnP.player.getMainHandStack();
      return hand.getMiningSpeedMultiplier(state) >= QfZy2gWnKInxFPr(state).getMiningSpeedMultiplier(state);
   }

   public void QfZy2gWnKInxFPr(BlockPos pos) {
      if (voxTwgNzyrpaAnP.player != null && voxTwgNzyrpaAnP.world != null) {
         int slot = this.wSJeYsJcmjNZf(voxTwgNzyrpaAnP.world.getBlockState(pos));
         pvfA15fQSVM.fStlZIvY9lS8(slot);
      }
   }

   private int vGAMA89H() {
      if (voxTwgNzyrpaAnP.player == null) {
         return 0;
      } else {
         return this.zbN84Osn9p5lD >= 0 && this.EVSMbf >= 0 ? this.EVSMbf : voxTwgNzyrpaAnP.player.getInventory().selectedSlot;
      }
   }

   private boolean fStlZIvY9lS8(int slot, BlockState state) {
      if (voxTwgNzyrpaAnP.player != null && slot >= 0 && slot <= 8) {
         ItemStack stack = voxTwgNzyrpaAnP.player.getInventory().getStack(slot);
         return stack.getMiningSpeedMultiplier(state) >= QfZy2gWnKInxFPr(state).getMiningSpeedMultiplier(state);
      } else {
         return false;
      }
   }

   private void fStlZIvY9lS8(KekMine.MineContext ctx) {
      if (voxTwgNzyrpaAnP.player != null && ctx != null) {
         int selected = this.vGAMA89H();
         if (this.fStlZIvY9lS8(selected, ctx.QfZy2gWnKInxFPr)) {
            if (this.zbN84Osn9p5lD >= 0) {
               this.ZlNrrNcv3Ylc = Math.max(this.ZlNrrNcv3Ylc, this.pF16zz4NJECfg7 + 3);
            }
         } else {
            int realSlot = this.zbN84Osn9p5lD >= 0 ? this.zbN84Osn9p5lD : voxTwgNzyrpaAnP.player.getInventory().selectedSlot;
            int bestSlot = this.wSJeYsJcmjNZf(ctx.QfZy2gWnKInxFPr);
            if (bestSlot != selected) {
               this.fStlZIvY9lS8(bestSlot);
               this.zbN84Osn9p5lD = realSlot;
               this.ZlNrrNcv3Ylc = this.pF16zz4NJECfg7 + 3;
            }
         }
      }
   }

   public void TJImwK2(BlockPos pos) {
      if (voxTwgNzyrpaAnP.world != null) {
         if (BlockUtils.canBreak(pos, voxTwgNzyrpaAnP.world.getBlockState(pos))) {
            if (!this.qhulAUwd(pos)) {
               if (!this.wSJeYsJcmjNZf(pos)) {
                  this.fStlZIvY9lS8(pos, voxTwgNzyrpaAnP.world.getBlockState(pos), this.TJImwK2());
               }
            }
         }
      }
   }

   public boolean wSJeYsJcmjNZf(BlockPos pos) {
      if (this.zXcO5rZ != null && this.zXcO5rZ.fStlZIvY9lS8.equals(pos)) {
         return true;
      } else {
         return this.jbUe84V1vykfK != null && this.jbUe84V1vykfK.fStlZIvY9lS8.equals(pos) ? true : this.V7zKJ4n6D.contains(pos);
      }
   }

   public boolean fStlZIvY9lS8() {
      return this.zXcO5rZ == null && this.jbUe84V1vykfK == null && !this.V7zKJ4n6D.isEmpty();
   }

   public boolean QfZy2gWnKInxFPr() {
      return this.zXcO5rZ == null && this.jbUe84V1vykfK == null && this.V7zKJ4n6D.isEmpty();
   }

   private boolean qhulAUwd() {
      return ((KekNuker)Modules.get().get(KekNuker.class)).isActive() || HighwayBuilder.vGAMA89H() || pvfA15fQSVM.TJImwK2;
   }

   public boolean TJImwK2() {
      return (Boolean)this.zkcZ4Xkp9Ez7Y.get() && !this.qhulAUwd();
   }

   private boolean BrmKXSYhWhqoB1() {
      return this.BrmKXSYhWhqoB1.get() && !this.MR1DKzw
         ? false
         : this.jbUe84V1vykfK == null
            && System.currentTimeMillis() >= this.JAj4YwE2MVx
            && this.zXcO5rZ != null
            && this.zXcO5rZ.i2Nu6XHKic
            && this.zXcO5rZ.vGAMA89H() < 1.0;
   }

   private void TXWyNb() {
      this.MR1DKzw = !this.JPp6VyZP9f;
      this.JPp6VyZP9f = false;
   }

   private void fStlZIvY9lS8(boolean confirmedBroken) {
      this.jbUe84V1vykfK = null;
      this.JAj4YwE2MVx = System.currentTimeMillis() + (confirmedBroken ? 50L : 290L);
   }

   private boolean fStlZIvY9lS8(BlockState a, BlockState b) {
      ItemStack ta = QfZy2gWnKInxFPr(a);
      ItemStack tb = QfZy2gWnKInxFPr(b);
      return ta.isEmpty() && tb.isEmpty() ? true : ta.getItem() == tb.getItem();
   }

   public static void i2Nu6XHKic(BlockPos pos) {
      if (pos != null && voxTwgNzyrpaAnP.player != null) {
         fStlZIvY9lS8(pos, duJ6sPeetAcBh6P.vGAMA89H(pos), duJ6sPeetAcBh6P.TJImwK2());
      }
   }

   public static void fStlZIvY9lS8(BlockPos pos, Direction dir, boolean silent) {
      if (pos != null) {
         duJ6sPeetAcBh6P.QfZy2gWnKInxFPr(pos, dir, silent);
      }
   }

   private void QfZy2gWnKInxFPr(BlockPos pos, Direction dir, boolean silent) {
      if (voxTwgNzyrpaAnP.world != null && voxTwgNzyrpaAnP.player != null && voxTwgNzyrpaAnP.interactionManager != null) {
         if (!this.wSJeYsJcmjNZf(pos)) {
            BlockState state = voxTwgNzyrpaAnP.world.getBlockState(pos);
            if (!state.isAir()) {
               if (!silent && !this.TJImwK2(state)) {
                  this.QfZy2gWnKInxFPr(pos);
                  if (!this.V7zKJ4n6D.contains(pos)) {
                     this.V7zKJ4n6D.addFirst(pos);
                  }
               } else {
                  int prevSlot = this.zbN84Osn9p5lD >= 0 ? this.zbN84Osn9p5lD : voxTwgNzyrpaAnP.player.getInventory().selectedSlot;
                  int bestSlot = silent ? this.wSJeYsJcmjNZf(state) : prevSlot;
                  boolean needSwap = silent && bestSlot != prevSlot;
                  if (needSwap) {
                     this.fStlZIvY9lS8(bestSlot);
                  }

                  voxTwgNzyrpaAnP.interactionManager.sendSequencedPacket(voxTwgNzyrpaAnP.world, sequence -> {
                     this.fStlZIvY9lS8(pos, state);
                     return new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, pos, dir, sequence);
                  });
                  if (needSwap) {
                     int hold = (Integer)this.TJImwK2.get();
                     if (hold > 0) {
                        this.zbN84Osn9p5lD = prevSlot;
                        this.ZlNrrNcv3Ylc = this.pF16zz4NJECfg7 + hold;
                     } else {
                        this.fStlZIvY9lS8(prevSlot);
                        this.zbN84Osn9p5lD = -1;
                     }
                  }

                  HighwayBuilder.fStlZIvY9lS8(state);
                  this.j376sIQ = pos;
                  this.WGmfQKK = System.currentTimeMillis();
                  this.icQ738dpWr7W = false;
               }
            }
         }
      }
   }

   private void fStlZIvY9lS8(BlockPos pos, BlockState state) {
      Block block = state.getBlock();
      state.onBlockBreakStart(voxTwgNzyrpaAnP.world, pos, voxTwgNzyrpaAnP.player);
      block.onBreak(voxTwgNzyrpaAnP.world, pos, state, voxTwgNzyrpaAnP.player);
      FluidState fluid = voxTwgNzyrpaAnP.world.getFluidState(pos);
      boolean removed = voxTwgNzyrpaAnP.world.setBlockState(pos, fluid.getBlockState(), 3);
      if (removed) {
         block.onBroken(voxTwgNzyrpaAnP.world, pos, state);
      }

      voxTwgNzyrpaAnP.world.syncWorldEvent(2001, pos, Block.getRawIdFromState(state));
   }

   public void onDeactivate() {
      if (this.zbN84Osn9p5lD >= 0) {
         this.fStlZIvY9lS8(this.zbN84Osn9p5lD);
      }

      this.zXcO5rZ = null;
      this.jbUe84V1vykfK = null;
      this.V7zKJ4n6D.clear();
      this.j376sIQ = null;
      this.zbN84Osn9p5lD = -1;
      this.ZlNrrNcv3Ylc = -1;
      this.EVSMbf = -1;
      this.pF16zz4NJECfg7 = 0;
      this.WGmfQKK = 0L;
      this.icQ738dpWr7W = false;
      this.JAj4YwE2MVx = 0L;
      this.JPp6VyZP9f = false;
      this.MR1DKzw = true;
   }

   private int wSJeYsJcmjNZf(BlockState state) {
      if (voxTwgNzyrpaAnP.player == null) {
         return 0;
      }

      float bestSpeed = 1.0F;
      int bestSlot = voxTwgNzyrpaAnP.player.getInventory().selectedSlot;

      for (int i = 0; i < 9; i++) {
         ItemStack stack = voxTwgNzyrpaAnP.player.getInventory().getStack(i);
         if ((!(Boolean)this.fStlZIvY9lS8.get() || pvfA15fQSVM.fStlZIvY9lS8(stack)) && fStlZIvY9lS8(stack, state)) {
            float speed = stack.getMiningSpeedMultiplier(state);
            if (speed > bestSpeed) {
               bestSpeed = speed;
               bestSlot = i;
            }
         }
      }

      int bestInvSlot = -1;

      for (int i = 9; i < voxTwgNzyrpaAnP.player.getInventory().main.size(); i++) {
         ItemStack stack = voxTwgNzyrpaAnP.player.getInventory().getStack(i);
         if (!stack.isEmpty() && (!(Boolean)this.fStlZIvY9lS8.get() || pvfA15fQSVM.fStlZIvY9lS8(stack)) && fStlZIvY9lS8(stack, state)) {
            float speed = stack.getMiningSpeedMultiplier(state);
            if (speed > bestSpeed) {
               bestSpeed = speed;
               bestInvSlot = i;
            }
         }
      }

      if (bestInvSlot != -1) {
         int hotbarSlot = pvfA15fQSVM.QfZy2gWnKInxFPr(voxTwgNzyrpaAnP.player.getInventory().getStack(bestInvSlot));
         bestSlot = Math.max(0, Math.min(8, hotbarSlot));
      }

      return bestSlot;
   }

   public void fStlZIvY9lS8(BlockPos pos, BlockState state, boolean silent) {
      this.fStlZIvY9lS8(pos, state, this.vGAMA89H(pos), silent);
   }

   public void fStlZIvY9lS8(BlockPos pos, BlockState state, Direction dir, boolean silent) {
      if (!this.wSJeYsJcmjNZf(pos)) {
         if (!HighwayBuilder.TJImwK2()) {
            if (this.zXcO5rZ == null || (Boolean)this.qhulAUwd.get() && this.BrmKXSYhWhqoB1()) {
               if (this.zXcO5rZ == null) {
                  if (!this.j376sIQ()) {
                     if (!this.V7zKJ4n6D.contains(pos)) {
                        this.V7zKJ4n6D.addLast(pos);
                     }

                     return;
                  }

                  if (!silent && !this.TJImwK2(state)) {
                     this.QfZy2gWnKInxFPr(pos);
                     if (!this.V7zKJ4n6D.contains(pos)) {
                        this.V7zKJ4n6D.addFirst(pos);
                     }

                     return;
                  }

                  this.zXcO5rZ = new KekMine.MineContext(pos, state, true, silent);
                  this.TXWyNb();
                  this.fStlZIvY9lS8(pos, dir, this.zXcO5rZ);
               } else {
                  this.fStlZIvY9lS8("PARK", this.zXcO5rZ.fStlZIvY9lS8);
                  this.fStlZIvY9lS8(Action.STOP_DESTROY_BLOCK, this.zXcO5rZ.fStlZIvY9lS8, this.vGAMA89H(this.zXcO5rZ.fStlZIvY9lS8));
                  this.jbUe84V1vykfK = this.zXcO5rZ.fStlZIvY9lS8();
                  this.zXcO5rZ = new KekMine.MineContext(pos, state, true, silent);
                  if (!this.fStlZIvY9lS8(this.zXcO5rZ.QfZy2gWnKInxFPr, this.jbUe84V1vykfK.QfZy2gWnKInxFPr)) {
                     this.zXcO5rZ.QfZy2gWnKInxFPr(this.jbUe84V1vykfK.duJ6sPeetAcBh6P);
                  }

                  this.JPp6VyZP9f = !this.wSJeYsJcmjNZf(this.zXcO5rZ);
                  this.fStlZIvY9lS8(this.zXcO5rZ.fStlZIvY9lS8, dir, this.zXcO5rZ);
               }
            } else {
               if (!this.V7zKJ4n6D.contains(pos)) {
                  this.V7zKJ4n6D.addLast(pos);
               }
            }
         }
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(Pre event) {
      if (voxTwgNzyrpaAnP.player != null && voxTwgNzyrpaAnP.world != null) {
         this.pF16zz4NJECfg7++;
         if (this.zbN84Osn9p5lD >= 0 && this.pF16zz4NJECfg7 >= this.ZlNrrNcv3Ylc) {
            this.fStlZIvY9lS8(this.zbN84Osn9p5lD);
            this.zbN84Osn9p5lD = -1;
         }

         if (!HighwayBuilder.TJImwK2() && !voxTwgNzyrpaAnP.player.isUsingItem()) {
            this.duJ6sPeetAcBh6P();
            this.QfZy2gWnKInxFPr(this.zXcO5rZ);
            boolean toolConflict = this.zXcO5rZ != null
               && this.jbUe84V1vykfK != null
               && !this.fStlZIvY9lS8(this.zXcO5rZ.QfZy2gWnKInxFPr, this.jbUe84V1vykfK.QfZy2gWnKInxFPr);
            if (this.jbUe84V1vykfK != null && this.jbUe84V1vykfK.vGAMA89H() >= 1.0) {
               this.vGAMA89H(this.jbUe84V1vykfK);
            }

            if (this.zXcO5rZ != null) {
               if (toolConflict && this.jbUe84V1vykfK != null) {
                  this.zXcO5rZ.fStlZIvY9lS8(this.jbUe84V1vykfK.duJ6sPeetAcBh6P);
                  if (this.zXcO5rZ.vGAMA89H() >= 1.0) {
                     this.vGAMA89H(this.zXcO5rZ);
                  }
               } else if (!this.zXcO5rZ.BrmKXSYhWhqoB1 && !this.TJImwK2(this.zXcO5rZ.QfZy2gWnKInxFPr)) {
                  if (this.zXcO5rZ.vGAMA89H() >= 1.0) {
                     this.vGAMA89H(this.zXcO5rZ);
                  } else {
                     this.QfZy2gWnKInxFPr(this.zXcO5rZ.fStlZIvY9lS8);
                  }
               } else {
                  this.zXcO5rZ.fStlZIvY9lS8(QfZy2gWnKInxFPr(this.zXcO5rZ.QfZy2gWnKInxFPr));
                  if (this.zXcO5rZ.vGAMA89H() >= 1.0) {
                     this.vGAMA89H(this.zXcO5rZ);
                  }
               }
            }

            this.I3psabmOrOI();
            if ((Boolean)this.I3psabmOrOI.get()
               && this.j376sIQ != null
               && this.zXcO5rZ == null
               && this.jbUe84V1vykfK == null
               && this.V7zKJ4n6D.isEmpty()
               && !this.qhulAUwd(this.j376sIQ)) {
               this.fStlZIvY9lS8(new KekMine.MineContext(this.j376sIQ, voxTwgNzyrpaAnP.world.getBlockState(this.j376sIQ), true), this.TJImwK2());
            }
         }
      }
   }

   private void I3psabmOrOI() {
      if (this.zXcO5rZ != null && this.BrmKXSYhWhqoB1(this.zXcO5rZ.fStlZIvY9lS8)) {
         this.zXcO5rZ = null;
      }

      if (this.jbUe84V1vykfK != null && this.BrmKXSYhWhqoB1(this.jbUe84V1vykfK.fStlZIvY9lS8)) {
         this.fStlZIvY9lS8(true);
      }

      this.V7zKJ4n6D.removeIf(this::BrmKXSYhWhqoB1);
   }

   private boolean BrmKXSYhWhqoB1(BlockPos pos) {
      BlockState state = voxTwgNzyrpaAnP.world.getBlockState(pos);
      return state.isAir() || this.qhulAUwd(pos);
   }

   private void duJ6sPeetAcBh6P() {
      if (!this.V7zKJ4n6D.isEmpty()) {
         if (this.zXcO5rZ == null) {
            if (!this.j376sIQ()) {
               return;
            }

            BlockPos pos = this.V7zKJ4n6D.pollFirst();
            BlockState state = voxTwgNzyrpaAnP.world.getBlockState(pos);
            boolean silent = this.TJImwK2();
            if (!silent && !this.TJImwK2(state)) {
               this.QfZy2gWnKInxFPr(pos);
               this.V7zKJ4n6D.addFirst(pos);
               return;
            }

            this.zXcO5rZ = new KekMine.MineContext(pos, state, true, silent);
            this.TXWyNb();
            this.fStlZIvY9lS8(this.zXcO5rZ.fStlZIvY9lS8, this.vGAMA89H(this.zXcO5rZ.fStlZIvY9lS8), this.zXcO5rZ);
         }

         if (!this.V7zKJ4n6D.isEmpty() && (Boolean)this.qhulAUwd.get() && this.BrmKXSYhWhqoB1()) {
            this.fStlZIvY9lS8("PARK", this.zXcO5rZ.fStlZIvY9lS8);
            this.fStlZIvY9lS8(Action.STOP_DESTROY_BLOCK, this.zXcO5rZ.fStlZIvY9lS8, this.vGAMA89H(this.zXcO5rZ.fStlZIvY9lS8));
            BlockPos nextPos = this.V7zKJ4n6D.pollFirst();
            BlockState nextState = voxTwgNzyrpaAnP.world.getBlockState(nextPos);
            this.jbUe84V1vykfK = this.zXcO5rZ.fStlZIvY9lS8();
            this.zXcO5rZ = new KekMine.MineContext(nextPos, nextState, true, this.TJImwK2());
            if (!this.fStlZIvY9lS8(this.zXcO5rZ.QfZy2gWnKInxFPr, this.jbUe84V1vykfK.QfZy2gWnKInxFPr)) {
               this.zXcO5rZ.QfZy2gWnKInxFPr(this.jbUe84V1vykfK.duJ6sPeetAcBh6P);
            }

            this.JPp6VyZP9f = !this.wSJeYsJcmjNZf(this.zXcO5rZ);
            this.fStlZIvY9lS8(this.zXcO5rZ.fStlZIvY9lS8, this.vGAMA89H(this.zXcO5rZ.fStlZIvY9lS8), this.zXcO5rZ);
         }
      }
   }

   private void fStlZIvY9lS8(BlockPos pos, Direction dir) {
      if ((Boolean)this.wSJeYsJcmjNZf.get()) {
         this.fStlZIvY9lS8(Action.STOP_DESTROY_BLOCK, pos, dir);
      }

      this.fStlZIvY9lS8(Action.START_DESTROY_BLOCK, pos, dir);
   }

   private void QfZy2gWnKInxFPr(BlockPos pos, Direction dir) {
      this.fStlZIvY9lS8(Action.START_DESTROY_BLOCK, new BlockPos(pos.getX(), 1337, pos.getZ()), dir);
   }

   private void TJImwK2(BlockPos pos, Direction dir) {
      this.fStlZIvY9lS8(Action.STOP_DESTROY_BLOCK, pos, dir);
      this.WGmfQKK = System.currentTimeMillis();
   }

   private void QfZy2gWnKInxFPr(KekMine.MineContext ctx) {
      if (this.TJImwK2(ctx) && System.currentTimeMillis() - ctx.TJImwK2 > 290L) {
         this.i2Nu6XHKic(ctx);
      }
   }

   private void fStlZIvY9lS8(String what, BlockPos pos) {
      if ((Boolean)this.flWHzEyn.get()) {
         this.info(
            "Tick: %d %s %s [PRIM=%s SEC=%s Q=%d]"
               .formatted(
                  this.pF16zz4NJECfg7,
                  what,
                  pos == null ? "-" : pos.toShortString(),
                  this.zXcO5rZ == null ? "-" : this.zXcO5rZ.fStlZIvY9lS8.toShortString() + (this.zXcO5rZ.I3psabmOrOI ? "*" : ""),
                  this.jbUe84V1vykfK == null ? "-" : this.jbUe84V1vykfK.fStlZIvY9lS8.toShortString() + (this.jbUe84V1vykfK.I3psabmOrOI ? "*" : ""),
                  this.V7zKJ4n6D.size()
               ),
            new Object[0]
         );
      }
   }

   private boolean TJImwK2(KekMine.MineContext ctx) {
      return ctx != null && !ctx.I3psabmOrOI && this.wSJeYsJcmjNZf(ctx);
   }

   private boolean wSJeYsJcmjNZf(KekMine.MineContext ctx) {
      return ctx != null && !ctx.j376sIQ ? ctx.TJImwK2() >= 290L : false;
   }

   private void i2Nu6XHKic(KekMine.MineContext ctx) {
      if (ctx != null && voxTwgNzyrpaAnP.player != null) {
         Direction dir = KekNuker.QfZy2gWnKInxFPr(voxTwgNzyrpaAnP.player.getEyePos(), ctx.fStlZIvY9lS8);

         for (int i = 0; i < 22; i++) {
            this.QfZy2gWnKInxFPr(ctx.fStlZIvY9lS8, dir);
         }

         ctx.I3psabmOrOI = true;
         this.fStlZIvY9lS8("BURST", ctx.fStlZIvY9lS8);
      }
   }

   private boolean j376sIQ() {
      return System.currentTimeMillis() - this.WGmfQKK > 275L;
   }

   private void fStlZIvY9lS8(BlockPos pos, Direction dir, KekMine.MineContext ctx) {
      this.fStlZIvY9lS8("START", pos);
      if (ctx.BrmKXSYhWhqoB1 && ctx.j376sIQ && !ctx.V7zKJ4n6D) {
         int prevSlot = this.zbN84Osn9p5lD >= 0 ? this.zbN84Osn9p5lD : voxTwgNzyrpaAnP.player.getInventory().selectedSlot;
         int bestSlot = this.wSJeYsJcmjNZf(ctx.QfZy2gWnKInxFPr);
         boolean needSwap = bestSlot != prevSlot;
         if (needSwap) {
            this.fStlZIvY9lS8(bestSlot);
         }

         this.fStlZIvY9lS8(pos, dir);
         if (needSwap) {
            int hold = (Integer)this.TJImwK2.get();
            if (hold > 0) {
               this.zbN84Osn9p5lD = prevSlot;
               this.ZlNrrNcv3Ylc = this.pF16zz4NJECfg7 + hold;
            } else {
               this.fStlZIvY9lS8(prevSlot);
               this.zbN84Osn9p5lD = -1;
            }
         }
      } else {
         this.fStlZIvY9lS8(pos, dir);
         if (!ctx.j376sIQ && !ctx.TXWyNb) {
            this.QfZy2gWnKInxFPr(pos, dir);
            ctx.TXWyNb = true;
         }
      }
   }

   private void fStlZIvY9lS8(KekMine.MineContext ctx, boolean silent) {
      if (voxTwgNzyrpaAnP.world != null && voxTwgNzyrpaAnP.player != null) {
         Direction dir = this.vGAMA89H(ctx.fStlZIvY9lS8);
         int prevSlot = this.zbN84Osn9p5lD >= 0 ? this.zbN84Osn9p5lD : voxTwgNzyrpaAnP.player.getInventory().selectedSlot;
         int bestSlot = silent ? this.wSJeYsJcmjNZf(ctx.QfZy2gWnKInxFPr) : prevSlot;
         boolean needSwap = silent && bestSlot != prevSlot;
         if (needSwap) {
            this.fStlZIvY9lS8(bestSlot);
         }

         this.fStlZIvY9lS8(Action.STOP_DESTROY_BLOCK, ctx.fStlZIvY9lS8, dir);
         if (!ctx.QfZy2gWnKInxFPr.isAir()) {
            this.WGmfQKK = System.currentTimeMillis();
         }

         if (needSwap) {
            int hold = (Integer)this.TJImwK2.get();
            if (hold > 0) {
               this.zbN84Osn9p5lD = prevSlot;
               this.ZlNrrNcv3Ylc = this.pF16zz4NJECfg7 + hold;
            } else {
               this.fStlZIvY9lS8(prevSlot);
               this.zbN84Osn9p5lD = -1;
            }
         }

         if (!(Boolean)this.TXWyNb.get() && !ctx.QfZy2gWnKInxFPr.isAir()) {
            voxTwgNzyrpaAnP.world.syncWorldEvent(2001, ctx.fStlZIvY9lS8, Block.getRawIdFromState(ctx.QfZy2gWnKInxFPr));
            voxTwgNzyrpaAnP.world.setBlockState(ctx.fStlZIvY9lS8, Blocks.AIR.getDefaultState(), 3);
         }
      }
   }

   private void vGAMA89H(KekMine.MineContext ctx) {
      if (voxTwgNzyrpaAnP.world != null && voxTwgNzyrpaAnP.player != null) {
         if (ctx.i2Nu6XHKic) {
            ctx.i2Nu6XHKic = false;
            ctx.vGAMA89H = this.pF16zz4NJECfg7;
            this.icQ738dpWr7W = ctx.I3psabmOrOI;
            this.fStlZIvY9lS8(ctx.qhulAUwd ? "FINISH-P" : "FINISH-S", ctx.fStlZIvY9lS8);
            Direction face = this.vGAMA89H(ctx.fStlZIvY9lS8);
            if (!ctx.qhulAUwd) {
               if (ctx.BrmKXSYhWhqoB1) {
                  this.fStlZIvY9lS8(ctx);
               }

               this.WGmfQKK = System.currentTimeMillis();
            } else if (ctx.BrmKXSYhWhqoB1 && ctx.j376sIQ) {
               this.WGmfQKK = System.currentTimeMillis();
            } else if (ctx.BrmKXSYhWhqoB1) {
               int prevSlot = this.zbN84Osn9p5lD >= 0 ? this.zbN84Osn9p5lD : voxTwgNzyrpaAnP.player.getInventory().selectedSlot;
               int bestSlot = this.wSJeYsJcmjNZf(ctx.QfZy2gWnKInxFPr);
               boolean needSwap = bestSlot != prevSlot;
               if (needSwap) {
                  this.fStlZIvY9lS8(bestSlot);
               }

               this.TJImwK2(ctx.fStlZIvY9lS8, face);
               if (needSwap) {
                  int hold = (Integer)this.TJImwK2.get();
                  if (hold > 0) {
                     this.zbN84Osn9p5lD = prevSlot;
                     this.ZlNrrNcv3Ylc = this.pF16zz4NJECfg7 + hold;
                  } else {
                     this.fStlZIvY9lS8(prevSlot);
                     this.zbN84Osn9p5lD = -1;
                  }
               }
            } else {
               if (voxTwgNzyrpaAnP.player.getMainHandStack().getItem() != ctx.duJ6sPeetAcBh6P.getItem()) {
                  this.QfZy2gWnKInxFPr(ctx.fStlZIvY9lS8);
               }

               if (!ctx.j376sIQ) {
                  this.TJImwK2(ctx.fStlZIvY9lS8, face);
               } else {
                  this.WGmfQKK = System.currentTimeMillis();
               }
            }

            if (ctx.j376sIQ && !(Boolean)this.TXWyNb.get()) {
               voxTwgNzyrpaAnP.world.syncWorldEvent(2001, ctx.fStlZIvY9lS8, Block.getRawIdFromState(ctx.QfZy2gWnKInxFPr));
               voxTwgNzyrpaAnP.world.setBlockState(ctx.fStlZIvY9lS8, Blocks.AIR.getDefaultState(), 3);
            }

            this.j376sIQ = ctx.fStlZIvY9lS8;
         }

         boolean broke = this.BrmKXSYhWhqoB1(ctx.fStlZIvY9lS8);
         if (!broke && !ctx.qhulAUwd && ctx.BrmKXSYhWhqoB1) {
            this.fStlZIvY9lS8(ctx);
         }

         int timeout = ctx.qhulAUwd ? 5 : 10;
         if (broke || this.pF16zz4NJECfg7 - ctx.vGAMA89H >= timeout) {
            this.fStlZIvY9lS8(broke ? "CONFIRMED" : "TIMEOUT", ctx.fStlZIvY9lS8);
            if (ctx == this.zXcO5rZ) {
               this.zXcO5rZ = null;
            } else if (ctx == this.jbUe84V1vykfK) {
               this.fStlZIvY9lS8(broke);
            }

            if (broke) {
               BlockSoundGroup sg = ctx.QfZy2gWnKInxFPr.getSoundGroup();
               VersionHelper.get()
                  .playSoundAtBlockCenter(ctx.fStlZIvY9lS8, sg.getBreakSound(), SoundCategory.BLOCKS, (sg.getVolume() + 1.0F) / 2.0F, sg.getPitch() * 0.8F);
               HighwayBuilder.fStlZIvY9lS8(ctx.QfZy2gWnKInxFPr);
            }
         }
      }
   }

   private void fStlZIvY9lS8(int slot) {
      if (voxTwgNzyrpaAnP.getNetworkHandler() != null) {
         this.EVSMbf = slot;
         voxTwgNzyrpaAnP.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(slot));
      }
   }

   public void fStlZIvY9lS8(Action action, BlockPos pos) {
      this.fStlZIvY9lS8(action, pos, this.vGAMA89H(pos));
   }

   public Direction vGAMA89H(BlockPos pos) {
      if (voxTwgNzyrpaAnP.player != null && voxTwgNzyrpaAnP.world != null) {
         Vec3d eye = voxTwgNzyrpaAnP.player.getEyePos();
         Direction best = null;
         double bestDist = Double.MAX_VALUE;

         for (Direction d : Direction.values()) {
            Vec3d faceCenter = Vec3d.ofCenter(pos).add(d.getOffsetX() * 0.5, d.getOffsetY() * 0.5, d.getOffsetZ() * 0.5);
            double dist = eye.squaredDistanceTo(faceCenter);
            if (!(dist >= bestDist) && this.fStlZIvY9lS8(eye, pos, d, faceCenter)) {
               bestDist = dist;
               best = d;
            }
         }

         return best != null ? best : KekNuker.QfZy2gWnKInxFPr(eye, pos);
      } else {
         return Direction.UP;
      }
   }

   private boolean fStlZIvY9lS8(Vec3d eye, BlockPos pos, Direction d, Vec3d faceCenter) {
      BlockHitResult hit = voxTwgNzyrpaAnP.world.raycast(new RaycastContext(eye, faceCenter, ShapeType.OUTLINE, FluidHandling.NONE, voxTwgNzyrpaAnP.player));
      return hit != null && hit.getType() == Type.BLOCK && hit.getBlockPos().equals(pos) && hit.getSide() == d;
   }

   public void fStlZIvY9lS8(Action action, BlockPos pos, Direction dir) {
      if (voxTwgNzyrpaAnP.interactionManager != null && voxTwgNzyrpaAnP.world != null) {
         voxTwgNzyrpaAnP.interactionManager.sendSequencedPacket(voxTwgNzyrpaAnP.world, sequence -> new PlayerActionC2SPacket(action, pos, dir, sequence));
      }
   }

   public boolean qhulAUwd(BlockPos pos) {
      return pos == null
         ? false
         : voxTwgNzyrpaAnP.player.getEyePos().distanceTo(pos.toCenterPos()) > (Double)((KekNuker)Modules.get().get(KekNuker.class)).wSJeYsJcmjNZf.get() + 1.0;
   }

   public BlockPos wSJeYsJcmjNZf() {
      return this.zXcO5rZ != null ? this.zXcO5rZ.fStlZIvY9lS8 : null;
   }

   public BlockPos i2Nu6XHKic() {
      return this.jbUe84V1vykfK != null ? this.jbUe84V1vykfK.fStlZIvY9lS8 : null;
   }

   @EventHandler
   private void fStlZIvY9lS8(Render3DEvent event) {
      if (voxTwgNzyrpaAnP.player != null && voxTwgNzyrpaAnP.world != null) {
         if (!((KekNuker)Modules.get().get(KekNuker.class)).isActive()) {
            if ((Boolean)this.FVjbhYkk53UT.get()) {
               jU8xXhmkg.QfZy2gWnKInxFPr(event, this.V7zKJ4n6D.stream().toList());
            } else {
               jU8xXhmkg.fStlZIvY9lS8(
                  event,
                  this.V7zKJ4n6D.stream().toList(),
                  meteordevelopment.meteorclient.utils.render.color.Color.WHITE,
                  meteordevelopment.meteorclient.utils.render.color.Color.WHITE,
                  ShapeMode.Lines
               );
            }
         }

         if (this.jbUe84V1vykfK != null) {
            if ((Boolean)this.FVjbhYkk53UT.get()) {
               this.fStlZIvY9lS8(
                  event,
                  this.jbUe84V1vykfK,
                  (meteordevelopment.meteorclient.utils.render.color.Color)MusheorSystem.Manager.renderSideColor.get(),
                  (meteordevelopment.meteorclient.utils.render.color.Color)MusheorSystem.Manager.renderLineColor.get(),
                  (ShapeMode)MusheorSystem.Manager.renderShape.get()
               );
            } else {
               this.fStlZIvY9lS8(
                  event,
                  this.jbUe84V1vykfK,
                  (meteordevelopment.meteorclient.utils.render.color.Color)this.Nt3xeF.get(),
                  (meteordevelopment.meteorclient.utils.render.color.Color)this.Nt3xeF.get(),
                  ShapeMode.Lines
               );
            }
         }

         if (this.zXcO5rZ != null) {
            if ((Boolean)this.FVjbhYkk53UT.get()) {
               this.fStlZIvY9lS8(
                  event,
                  this.zXcO5rZ,
                  (meteordevelopment.meteorclient.utils.render.color.Color)MusheorSystem.Manager.renderSideColor.get(),
                  (meteordevelopment.meteorclient.utils.render.color.Color)MusheorSystem.Manager.renderLineColor.get(),
                  (ShapeMode)MusheorSystem.Manager.renderShape.get()
               );
            } else {
               this.fStlZIvY9lS8(
                  event,
                  this.zXcO5rZ,
                  (meteordevelopment.meteorclient.utils.render.color.Color)this.Nt3xeF.get(),
                  (meteordevelopment.meteorclient.utils.render.color.Color)this.Nt3xeF.get(),
                  ShapeMode.Lines
               );
            }
         }

         if ((Boolean)this.I3psabmOrOI.get() && this.j376sIQ != null) {
            if ((Boolean)this.FVjbhYkk53UT.get()) {
               jU8xXhmkg.fStlZIvY9lS8(event, this.j376sIQ, voxTwgNzyrpaAnP.world.getBlockState(this.j376sIQ).getBlock());
            } else {
               jU8xXhmkg.fStlZIvY9lS8(
                  event,
                  this.j376sIQ,
                  (meteordevelopment.meteorclient.utils.render.color.Color)this.Nt3xeF.get(),
                  (meteordevelopment.meteorclient.utils.render.color.Color)this.Nt3xeF.get(),
                  ShapeMode.Lines
               );
            }
         }
      }
   }

   private void fStlZIvY9lS8(
      Render3DEvent event,
      KekMine.MineContext ctx,
      meteordevelopment.meteorclient.utils.render.color.Color side,
      meteordevelopment.meteorclient.utils.render.color.Color line,
      ShapeMode shape
   ) {
      double offset = (1.0 - ctx.vGAMA89H()) / 2.0;
      Box box = new Box(
         ctx.fStlZIvY9lS8.getX() + offset,
         ctx.fStlZIvY9lS8.getY() + offset,
         ctx.fStlZIvY9lS8.getZ() + offset,
         ctx.fStlZIvY9lS8.getX() + 1.0 - offset,
         ctx.fStlZIvY9lS8.getY() + 1.0 - offset,
         ctx.fStlZIvY9lS8.getZ() + 1.0 - offset
      );
      event.renderer.box(box, side, line, shape, 0);
   }

   public static class MineContext {
      public final BlockPos fStlZIvY9lS8;
      public final BlockState QfZy2gWnKInxFPr;
      public long TJImwK2;
      public final float wSJeYsJcmjNZf;
      public boolean i2Nu6XHKic = true;
      public int vGAMA89H = -1;
      public final boolean qhulAUwd;
      public final boolean BrmKXSYhWhqoB1;
      public boolean TXWyNb = false;
      public boolean I3psabmOrOI = false;
      public ItemStack duJ6sPeetAcBh6P;
      public boolean j376sIQ;
      public final boolean V7zKJ4n6D;
      public final MinecraftClient voxTwgNzyrpaAnP = MinecraftClient.getInstance();

      public MineContext(BlockPos pos, BlockState state, boolean isPrimary) {
         this(pos, state, isPrimary, false);
      }

      public MineContext(BlockPos pos, BlockState state, boolean isPrimary, boolean silent) {
         this.fStlZIvY9lS8 = pos.toImmutable();
         this.QfZy2gWnKInxFPr = state;
         this.wSJeYsJcmjNZf = state.getHardness(this.voxTwgNzyrpaAnP.world, pos);
         this.qhulAUwd = isPrimary;
         this.BrmKXSYhWhqoB1 = silent;
         this.TJImwK2 = System.currentTimeMillis();
         this.duJ6sPeetAcBh6P = KekMine.QfZy2gWnKInxFPr(state);
         this.wSJeYsJcmjNZf();
         this.V7zKJ4n6D = this.TJImwK2(this.voxTwgNzyrpaAnP.player.getMainHandStack()) >= 1.0F;
      }

      public KekMine.MineContext fStlZIvY9lS8() {
         KekMine.MineContext s = new KekMine.MineContext(this.fStlZIvY9lS8, this.QfZy2gWnKInxFPr, false, this.BrmKXSYhWhqoB1);
         s.TJImwK2 = System.currentTimeMillis();
         s.TXWyNb = this.TXWyNb;
         s.I3psabmOrOI = this.I3psabmOrOI;
         s.duJ6sPeetAcBh6P = this.duJ6sPeetAcBh6P;
         s.wSJeYsJcmjNZf();
         return s;
      }

      private void wSJeYsJcmjNZf() {
         this.j376sIQ = this.TJImwK2(this.duJ6sPeetAcBh6P) >= 1.0F;
      }

      public void fStlZIvY9lS8(ItemStack tool) {
         if (tool != null && (this.duJ6sPeetAcBh6P == null || this.duJ6sPeetAcBh6P.getItem() != tool.getItem())) {
            float oldPerTick = this.TJImwK2(this.duJ6sPeetAcBh6P);
            float newPerTick = this.TJImwK2(tool);
            if (Float.isFinite(oldPerTick) && oldPerTick > 0.0F && Float.isFinite(newPerTick) && newPerTick > 0.0F) {
               float elapsedTicks = Math.max((float)(System.currentTimeMillis() - this.TJImwK2) / 50.0F + 1.0F, 1.0F);
               float rawProgress = oldPerTick * elapsedTicks;
               float newElapsed = rawProgress / newPerTick;
               this.TJImwK2 = System.currentTimeMillis() - (long)((newElapsed - 1.0F) * 50.0F);
            }

            this.duJ6sPeetAcBh6P = tool;
            this.wSJeYsJcmjNZf();
         }
      }

      public void QfZy2gWnKInxFPr(ItemStack tool) {
         if (tool != null) {
            this.duJ6sPeetAcBh6P = tool;
            this.TJImwK2 = System.currentTimeMillis();
            this.wSJeYsJcmjNZf();
         }
      }

      private float i2Nu6XHKic() {
         return this.TJImwK2(this.duJ6sPeetAcBh6P);
      }

      private float TJImwK2(ItemStack bestTool) {
         return KekMine.fStlZIvY9lS8(this.QfZy2gWnKInxFPr, this.fStlZIvY9lS8, bestTool);
      }

      private double vGAMA89H() {
         if (this.voxTwgNzyrpaAnP.player != null && this.voxTwgNzyrpaAnP.world != null && !(this.wSJeYsJcmjNZf < 0.0F)) {
            float perTick = this.i2Nu6XHKic();
            if (perTick <= 0.0F) {
               return 2.147483647E9;
            }

            float elapsedTicks = Math.max((float)(System.currentTimeMillis() - this.TJImwK2 - 50L) / 50.0F + 1.0F, 1.0F);
            float currentProgress = perTick * elapsedTicks;
            float targetProgress = this.qhulAUwd ? ((Double)KekMine.duJ6sPeetAcBh6P.i2Nu6XHKic.get()).floatValue() : 1.0F;
            return Math.min(currentProgress / targetProgress, 1.0);
         } else {
            return 0.0;
         }
      }

      public float QfZy2gWnKInxFPr() {
         return this.TJImwK2(this.duJ6sPeetAcBh6P);
      }

      public long TJImwK2() {
         if (this.voxTwgNzyrpaAnP.player != null && this.voxTwgNzyrpaAnP.world != null) {
            float perTick = this.i2Nu6XHKic();
            if (perTick <= 0.0F) {
               return Long.MAX_VALUE;
            }

            float target = this.qhulAUwd ? ((Double)KekMine.duJ6sPeetAcBh6P.i2Nu6XHKic.get()).floatValue() : 1.0F;
            float ticks = target / perTick;
            return (long)(Math.max(ticks - 1.0F, 0.0F) * 50.0F);
         } else {
            return Long.MAX_VALUE;
         }
      }
   }
}
