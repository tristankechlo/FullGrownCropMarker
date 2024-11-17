package com.tristankechlo.crop_marker;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.tristankechlo.crop_marker.render.AlertModel;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Mod.EventBusSubscriber(modid = FullGrownCropMarker.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeClientEvents {

    public static final ModelLayerLocation ALERT = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(FullGrownCropMarker.MOD_ID, "alert"), "main");
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FullGrownCropMarker.MOD_ID, "textures/marker_red.png");
    private static BlockState STATE;
    private static AlertModel MODEL = null;
    private static final Set<BlockPos> POSITIONS = new HashSet<>();

    public static void onClientSetup(FMLClientSetupEvent event) {
        STATE = Blocks.WHEAT.defaultBlockState().setValue(CropBlock.AGE, 7);
        FullGrownCropMarker.LOGGER.error("client init");
    }

    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ALERT, AlertModel::createBodyLayer);
        FullGrownCropMarker.LOGGER.error("register layers");
    }


    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event) {
        POSITIONS.clear();
    }

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        int chunkOriginX = (event.getChunk().getPos().x + 1) * 16;
        int chunkOriginZ = event.getChunk().getPos().z * 16;
        event.getChunk().findBlocks((state) -> state.equals(STATE), (pos, state) -> {
            pos = pos.offset(chunkOriginX, 2, chunkOriginZ);
            FullGrownCropMarker.LOGGER.info("loaded state: '{}' at '{}'", state, pos);
            POSITIONS.add(pos);
        });
    }

    @SubscribeEvent
    public static void onChunkUnload(ChunkEvent.Unload event) {
        int chunkOriginX = (event.getChunk().getPos().x + 1) * 16;
        int chunkOriginZ = event.getChunk().getPos().z * 16;
        event.getChunk().findBlocks((state) -> state.equals(STATE), (pos, state) -> {
            pos = pos.offset(chunkOriginX, 2, chunkOriginZ);
            FullGrownCropMarker.LOGGER.info("loaded state: '{}' at '{}'", state, pos);
            POSITIONS.remove(pos);
        });
    }

    @SubscribeEvent
    public static void renderGame(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS) {
            return;
        }

        if (MODEL == null) {
            MODEL = new AlertModel(Minecraft.getInstance().getEntityModels().bakeLayer(ALERT));
        }

        PoseStack poseStack = new PoseStack();
        MultiBufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer consumer = bufferSource.getBuffer(MODEL.renderType(TEXTURE));

        Camera camera = event.getCamera();
        double camX = camera.getPosition().x;
        double camY = camera.getPosition().y;
        double camZ = camera.getPosition().z;
        float partialTick = Objects.requireNonNull(Minecraft.getInstance().level).getGameTime() + event.getPartialTick();

        for (BlockPos pos : POSITIONS) {
            // move poseStack to the block center
            poseStack.pushPose();
            poseStack.translate((pos.getX() - camX) + 0.5, (pos.getY() - camY) + 0.5, (pos.getZ() - camZ) + 0.5);
            poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));

            // animate and render to buffer
            renderAlertSymbol(poseStack, consumer, null, partialTick);
            poseStack.popPose();
        }
    }

    private static void renderAlertSymbol(PoseStack poseStack, VertexConsumer consumer, BlockState state, float partialTick) {
        poseStack.pushPose();

        double yOffset = Math.sin(partialTick * 0.1) * 0.1;
        poseStack.translate(0, yOffset, 0);
        MODEL.animate(partialTick);
        MODEL.renderToBuffer(poseStack, consumer, 15728880, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
    }

}
