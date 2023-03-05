package com.tristankechlo.crop_marker.mixin;

import com.mojang.datafixers.util.Either;
import com.tristankechlo.crop_marker.FullGrownCropMarker;
import com.tristankechlo.crop_marker.config.FullGrownCropMarkerConfig;
import com.tristankechlo.crop_marker.types.MarkerOptions;
import com.tristankechlo.crop_marker.types.MarkerPosition;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Mixin(BlockModel.class)
public abstract class BlockModelMixin {

    private static final ResourceLocation FULL_GROWN_CROP_MARKER_TEXTURE = new ResourceLocation(FullGrownCropMarker.MOD_ID, "block/marker");
    private static final Either<Material, String> FULL_GROWN_CROP_MARKER_SPRITE = Either.left(new Material(InventoryMenu.BLOCK_ATLAS, FULL_GROWN_CROP_MARKER_TEXTURE));

    @Inject(at = @At("HEAD"), method = "bake(Lnet/minecraft/client/resources/model/ModelBaker;Lnet/minecraft/client/renderer/block/model/BlockModel;Ljava/util/function/Function;Lnet/minecraft/client/resources/model/ModelState;Lnet/minecraft/resources/ResourceLocation;Z)Lnet/minecraft/client/resources/model/BakedModel;")
    private void FullGrownCropMarker$bake(ModelBaker $$0, BlockModel $$1, Function<Material, TextureAtlasSprite> $$2, ModelState $$3, ResourceLocation id, boolean $$5, CallbackInfoReturnable<BakedModel> cir) {
        MarkerPosition marker = MarkerPosition.fromId(id);
        if (marker != MarkerPosition.NONE) {
            List<BlockElement> all = ((BlockModel) (Object) this).getElements(); //get the original elements, or the elements of the parent model
            elements.clear();
            elements.addAll(all); //add the original elements to the model
            textureMap.put("marker", FULL_GROWN_CROP_MARKER_SPRITE);
            MarkerOptions options = FullGrownCropMarkerConfig.getOptions(id);
            elements.addAll(FullGrownCropMarker$createMarkerTop(options)); //add the marker elements to the model
            FullGrownCropMarker.LOGGER.info("Added the marker to '{}' with {}", id, options);
        }
    }

    //create the ModelElements needed for the marker
    private static List<BlockElement> FullGrownCropMarker$createMarkerTop(MarkerOptions options) {
        final float[] uvsSmall = options.color().getUvsSmall();
        final float[] uvsLarge = options.color().getUvsLarge();
        final MarkerPosition position = options.position();
        final int yOffset = options.yOffset() + position.getOffset().y();
        final boolean animated = options.animated();

        final BlockElementFace faceSmall = new BlockElementFace(Direction.UP, 0, "#marker", new BlockFaceUV(uvsSmall, 0));
        final BlockElementFace faceLarge = new BlockElementFace(Direction.UP, 0, "#marker", new BlockFaceUV(uvsLarge, 0));
        final BlockElementRotation rotation = new BlockElementRotation(new Vector3f(0, 0, 0), Direction.Axis.Y, 0, false);

        Map<Direction, BlockElementFace> facesCube = Direction.stream().collect(HashMap::new, (map, dir) -> map.put(dir, faceSmall), HashMap::putAll);
        Vector3f fromSmall = new Vector3f(7, 1, 7).add(0, yOffset, 0);
        Vector3f toSmall = new Vector3f(9, 3, 9).add(0, yOffset, 0);
        BlockElement smallCube = new BlockElement(fromSmall, toSmall, facesCube, rotation, false);

        Map<Direction, BlockElementFace> facesCuboidTop = Direction.stream()
                .filter(dir -> dir.getAxis().isHorizontal())
                .collect(HashMap::new, (map, dir) -> map.put(dir, faceLarge), HashMap::putAll);
        facesCuboidTop.put(Direction.UP, faceSmall);
        facesCuboidTop.put(Direction.DOWN, faceSmall);
        Vector3f fromLarge = new Vector3f(7, 4, 7).add(0, yOffset, 0);
        Vector3f toLarge = new Vector3f(9, 9, 9).add(0, yOffset, 0);
        BlockElement cuboidTop = new BlockElement(fromLarge, toLarge, facesCuboidTop, rotation, false);

        return List.of(smallCube, cuboidTop);
    }

    @Shadow
    @Final
    private List<BlockElement> elements;
    @Shadow
    @Final
    protected Map<String, Either<Material, String>> textureMap;

}
