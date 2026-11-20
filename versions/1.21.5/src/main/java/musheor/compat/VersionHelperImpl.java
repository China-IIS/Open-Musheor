package musheor.compat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.brigadier.context.CommandContext;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import java.util.UUID;
import meteordevelopment.meteorclient.commands.arguments.PlayerListEntryArgumentType;
import meteordevelopment.meteorclient.mixin.ContainerComponentAccessor;
import meteordevelopment.meteorclient.renderer.Renderer2D;
import meteordevelopment.meteorclient.utils.render.color.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.screen.sync.ItemStackHash;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class VersionHelperImpl implements VersionHelper {
   private static final Gson GSON_INSTANCE = new GsonBuilder().setPrettyPrinting().create();
   private static final MinecraftClient mc = MinecraftClient.getInstance();

   @Override
   public Gson getGson() {
      return GSON_INSTANCE;
   }

   @Override
   public Vec3d getPlayerPos() {
      return mc.player.getPos();
   }

   @Override
   public Vec3d getVelocity(EntityVelocityUpdateS2CPacket packet) {
      return new Vec3d(packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ());
   }

   public static void init() {
      VersionHelper.setInstance(new VersionHelperImpl());
   }

   @Override
   public void playSoundPlayer(SoundEvent sound) {
      mc.player.playSoundToPlayer(sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
   }

   @Override
   public void playSoundAtBlockCenter(BlockPos pos, SoundEvent sound, SoundCategory category, float volume, float pitch) {
      mc.world.playSoundAtBlockCenterClient(pos, sound, category, volume, pitch, false);
   }

   @Override
   public boolean isPickaxe(ItemStack stack) {
      return stack.isIn(ItemTags.PICKAXES);
   }

   @Override
   public boolean isTool(ItemStack stack) {
      return stack.getComponents().contains(DataComponentTypes.TOOL);
   }

   @Override
   public DefaultedList<ItemStack> getStacks(ContainerComponentAccessor container) {
      return container.getStacks();
   }

   @Override
   public NbtCompound getCompound(NbtCompound tag, String string) {
      return tag.getCompoundOrEmpty(string);
   }

   @Override
   public NbtList getList(NbtCompound tag, String key, int type) {
      return tag.getList(key).orElse(new NbtList());
   }

   @Override
   public String getName(CommandContext<?> context) {
      return PlayerListEntryArgumentType.get(context).getProfile().getName();
   }

   @Override
   public UUID getUUID(PlayerListEntry entry) {
      return entry.getProfile().getId();
   }

   @Override
   public boolean onSameServer(String ign) {
      return mc.getNetworkHandler() != null && mc.getNetworkHandler().getPlayerList().stream().anyMatch(p -> p.getProfile().getName().equalsIgnoreCase(ign));
   }

   @Override
   public void syncInventory() {
      if (mc.player != null && mc.interactionManager != null) {
         mc.getNetworkHandler()
            .sendPacket(
               new ClickSlotC2SPacket(
                  mc.player.currentScreenHandler.syncId,
                  Integer.MAX_VALUE,
                  (short)0,
                  (byte)3,
                  SlotActionType.PICKUP,
                  Int2ObjectMaps.emptyMap(),
                  ItemStackHash.EMPTY
               )
            );
      }
   }

   @Override
   public void interactEntityAt(Entity entity, Hand hand) {
      mc.interactionManager.sendSequencedPacket(mc.world, sequence -> PlayerInteractEntityC2SPacket.interactAt(entity, false, hand, entity.getPos()));
   }

   @Override
   public String getString(NbtCompound tag, String key) {
      return tag.getString(key).orElse("");
   }

   @Override
   public int getInt(NbtCompound tag, String key, int defaultValue) {
      return tag.getInt(key).orElse(defaultValue);
   }

   @Override
   public boolean hasControlDown() {
      return Screen.hasControlDown();
   }

   @Override
   public void sendCommand(String command) {
      if (mc.player != null && mc.getNetworkHandler() != null) {
         mc.getNetworkHandler().sendCommand(command);
      }
   }

   @Override
   public void registerSkinTexture(NativeImage image, String playerName, Identifier id) {
      NativeImageBackedTexture texture = new NativeImageBackedTexture(() -> playerName, image);
      mc.getTextureManager().registerTexture(id, texture);
      texture.upload();
   }

   @Override
   public void drawSkinTexture(Identifier textureId, float x, float y, float w, float h) {
      AbstractTexture tex = mc.getTextureManager().getTexture(textureId);
      if (tex != null) {
         GpuTexture gpuTexture = tex.getGlTexture();
         if (gpuTexture != null) {
            Renderer2D.TEXTURE.begin();
            Renderer2D.TEXTURE.texQuad(x, y, w, h, Color.WHITE);
            Renderer2D.TEXTURE.render(gpuTexture);
         }
      }
   }

   @Override
   public void renderColorRenderer() {
      Renderer2D.COLOR.render();
   }

   @Override
   public void pushHudScale(DrawContext context, float scale) {
      MatrixStack matrices = context.getMatrices();
      matrices.push();
      matrices.scale(scale, scale, 1.0F);
   }

   @Override
   public void popHudScale(DrawContext context) {
      context.getMatrices().pop();
   }
}
