package com.tristankechlo.crop_marker.mixin;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.tristankechlo.crop_marker.FullGrownCropMarker;
import com.tristankechlo.crop_marker.config.FullGrownCropMarkerConfig;
import com.tristankechlo.crop_marker.types.MarkerOptions;
import com.tristankechlo.crop_marker.util.ModelBakerAddon;
import com.tristankechlo.crop_marker.util.ResourceLocationHelper;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Mixin(BlockModel.class)
public abstract class BlockModelMixin {

    @Unique
    private static final ResourceLocation FULL_GROWN_CROP_MARKER_TEXTURE = ResourceLocation.fromNamespaceAndPath(FullGrownCropMarker.MOD_ID, "block/marker");
    @Unique
    private static final ResourceLocation FULL_GROWN_CROP_MARKER_TEXTURE_ANIMATED = ResourceLocation.fromNamespaceAndPath(FullGrownCropMarker.MOD_ID, "block/marker_animated");
    @Unique
    private static final Either<Material, String> FULL_GROWN_CROP_MARKER_SPRITE = Either.left(new Material(InventoryMenu.BLOCK_ATLAS, FULL_GROWN_CROP_MARKER_TEXTURE));
    @Unique
    private static final Either<Material, String> FULL_GROWN_CROP_MARKER_SPRITE_ANIMATED = Either.left(new Material(InventoryMenu.BLOCK_ATLAS, FULL_GROWN_CROP_MARKER_TEXTURE_ANIMATED));

    @Inject(at = @At("HEAD"), method = "bake(Lnet/minecraft/client/resources/model/ModelBaker;Lnet/minecraft/client/renderer/block/model/BlockModel;Ljava/util/function/Function;Lnet/minecraft/client/resources/model/ModelState;Z)Lnet/minecraft/client/resources/model/BakedModel;")
    private void FullGrownCropMarker$onBake(ModelBaker baker, BlockModel model, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, boolean $$4, CallbackInfoReturnable<BakedModel> cir) {
        if (!(baker instanceof ModelBakerAddon)) {
            return;
        }
        boolean shouldHaveMarker = ((ResourceLocationHelper) (Object) ((ModelBakerAddon) baker).FullGrownCropMarker$id()).FullGrownCropMarker$shouldHaveMarker();
        if (shouldHaveMarker) {
            this.FullGrownCropMarker$addMarker();
        }
    }

    @Unique
    private void FullGrownCropMarker$addMarker() {
        ResourceLocation id = ResourceLocation.parse(this.name);
        MarkerOptions options = FullGrownCropMarkerConfig.getOptions(id);
        if (!options.hasMarker()) {
            FullGrownCropMarker.LOGGER.info("Skipped adding the marker to '{}' with {}", id, options);
            return;
        }
        List<BlockElement> all = List.copyOf(this.getElements()); //get the original elements, or the elements of the parent model
        elements.clear();
        elements.addAll(all); //add the original elements to the model
        textureMap.put("marker", FULL_GROWN_CROP_MARKER_SPRITE);
        textureMap.put("animated_marker", FULL_GROWN_CROP_MARKER_SPRITE_ANIMATED);
        elements.addAll(FullGrownCropMarker$createMarker(options)); //add the marker elements to the model
        FullGrownCropMarker.LOGGER.info("Added the marker to '{}' with {}", id, options);
    }

    @Unique
    private static List<BlockElement> FullGrownCropMarker$createMarker(MarkerOptions options) {
        final float[] uvsSmall = options.color().getUvsSmall();
        final float[] uvsLarge = options.color().getUvsLarge();
        final int yOffset = 16 + options.yOffset();
        String texture = options.animated() ? "#animated_marker" : "#marker";

        List<BlockElement> markerDefault = FullGrownCropMarker$createMarker(texture, uvsSmall, uvsLarge, yOffset);

        if (!options.animated()) {
            return markerDefault;
        }

        final float[] uvsSmallAnimated = options.color().getUvsSmallAnimated();
        final float[] uvsLargeAnimated = options.color().getUvsLargeAnimated();

        List<BlockElement> markerAnimated = FullGrownCropMarker$createMarker(texture, uvsSmallAnimated, uvsLargeAnimated, 1 + yOffset);

        return ImmutableList.<BlockElement>builder().addAll(markerDefault).addAll(markerAnimated).build();
    }

    @Unique
    private static List<BlockElement> FullGrownCropMarker$createMarker(String texture, float[] uvsSmall, float[] uvsLarge, int yOffset) {
        final BlockElementFace faceSmall = new BlockElementFace(Direction.UP, 0, texture, new BlockFaceUV(uvsSmall, 0));
        final BlockElementFace faceLarge = new BlockElementFace(Direction.UP, 0, texture, new BlockFaceUV(uvsLarge, 0));
        final BlockElementRotation rotationElement = new BlockElementRotation(new Vector3f(0.5F, 0, 0.5f), Direction.Axis.Y, 0f, false);

        Map<Direction, BlockElementFace> facesCube = Direction.stream().collect(HashMap::new, (map, dir) -> map.put(dir, faceSmall), HashMap::putAll);
        Vector3f fromSmall = new Vector3f(7, 1 + yOffset, 7);
        Vector3f toSmall = new Vector3f(9, 3 + yOffset, 9);
        BlockElement smallCube = new BlockElement(fromSmall, toSmall, facesCube, rotationElement, false);

        Map<Direction, BlockElementFace> facesCuboidTop = Direction.stream()
                .filter(dir -> dir.getAxis().isHorizontal())
                .collect(HashMap::new, (map, dir) -> map.put(dir, faceLarge), HashMap::putAll);
        facesCuboidTop.put(Direction.UP, faceSmall);
        facesCuboidTop.put(Direction.DOWN, faceSmall);
        Vector3f fromLarge = new Vector3f(7, 4 + yOffset, 7);
        Vector3f toLarge = new Vector3f(9, 9 + yOffset, 9);
        BlockElement cuboidTop = new BlockElement(fromLarge, toLarge, facesCuboidTop, rotationElement, false);

        return List.of(smallCube, cuboidTop);
    }

    @Shadow
    @Final
    private List<BlockElement> elements;

    @Shadow
    @Final
    protected Map<String, Either<Material, String>> textureMap;

    @Shadow
    public abstract List<BlockElement> getElements();

    @Shadow
    public String name;

}
