package musheor.compat;

import com.google.gson.Gson;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.brigadier.context.CommandContext;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import java.util.UUID;
import meteordevelopment.meteorclient.commands.arguments.PlayerListEntryArgumentType;
import meteordevelopment.meteorclient.mixin.ContainerComponentAccessor;
import meteordevelopment.meteorclient.renderer.Renderer2D;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.model.json.ModelVariantMap;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class VersionHelperImpl implements VersionHelper {
   private static final MinecraftClient mc = MinecraftClient.getInstance();

   @Override
   public Gson getGson() {
      return ModelVariantMap.GSON;
   }

   @Override
   public Vec3d getPlayerPos() {
      return mc.player.getPos();
   }

   @Override
   public Vec3d getVelocity(EntityVelocityUpdateS2CPacket packet) {
      return new Vec3d(packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ());
   }

   @Override
   public boolean isPickaxe(ItemStack stack) {
      return stack.getItem() instanceof PickaxeItem;
   }

   @Override
   public boolean isTool(ItemStack stack) {
      return stack.getItem() instanceof MiningToolItem;
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
      mc.world.playSoundAtBlockCenter(pos, sound, category, volume, pitch, false);
   }

   @Override
   public DefaultedList<ItemStack> getStacks(ContainerComponentAccessor container) {
      return container.getStacks();
   }

   @Override
   public NbtCompound getCompound(NbtCompound tag, String string) {
      return tag.getCompound(string);
   }

   @Override
   public NbtList getList(NbtCompound tag, String key, int type) {
      return tag.getList(key, type);
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
      if (mc.player != null && mc.getNetworkHandler() != null) {
         mc.getNetworkHandler()
            .sendPacket(
               new ClickSlotC2SPacket(
                  mc.player.currentScreenHandler.syncId, Integer.MAX_VALUE, 0, 3, SlotActionType.PICKUP, ItemStack.EMPTY, Int2ObjectMaps.emptyMap()
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
      return tag.getString(key);
   }

   @Override
   public int getInt(NbtCompound tag, String key, int defaultValue) {
      return tag.contains(key) ? tag.getInt(key) : defaultValue;
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
      NativeImageBackedTexture texture = new NativeImageBackedTexture(image);
      mc.getTextureManager().registerTexture(id, texture);
      RenderSystem.bindTexture(texture.getGlId());
      GL11.glTexParameteri(3553, 10241, 9728);
      GL11.glTexParameteri(3553, 10240, 9728);
   }

   @Override
   public void drawSkinTexture(Identifier textureId, float x, float y, float w, float h) {
      RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX);
      RenderSystem.setShaderTexture(0, textureId);
      RenderSystem.enableBlend();
      Matrix4f matrix = new Matrix4f();
      BufferBuilder buf = Tessellator.getInstance().begin(net.minecraft.client.render.VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
      buf.vertex(matrix, x, y + h, 0.0F).texture(0.0F, 1.0F);
      buf.vertex(matrix, x + w, y + h, 0.0F).texture(1.0F, 1.0F);
      buf.vertex(matrix, x + w, y, 0.0F).texture(1.0F, 0.0F);
      buf.vertex(matrix, x, y, 0.0F).texture(0.0F, 0.0F);
      BufferRenderer.drawWithGlobalProgram(buf.end());
      RenderSystem.disableBlend();
   }

   @Override
   public void renderColorRenderer() {
      Renderer2D.COLOR.render(null);
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
