package com.tristankechlo.crop_marker.mixin;

import com.mojang.datafixers.util.Either;
import com.tristankechlo.crop_marker.FullGrownCropMarker;
import com.tristankechlo.crop_marker.util.MarkerPosition;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.Baker;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.json.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
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

@Mixin(JsonUnbakedModel.class)
public abstract class JsonUnbakedModelMixin {

    private static final Identifier FULL_GROWN_CROP_MARKER_TEXTURE = new Identifier(FullGrownCropMarker.MOD_ID, "block/marker");
    private static final Either<SpriteIdentifier, String> FULL_GROWN_CROP_MARKER_SPRITE = Either.left(new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, FULL_GROWN_CROP_MARKER_TEXTURE));

    @Inject(at = @At("HEAD"), method = "bake(Lnet/minecraft/client/render/model/Baker;Lnet/minecraft/client/render/model/json/JsonUnbakedModel;Ljava/util/function/Function;Lnet/minecraft/client/render/model/ModelBakeSettings;Lnet/minecraft/util/Identifier;Z)Lnet/minecraft/client/render/model/BakedModel;")
    private void FullGrownCropMarker$bake(Baker baker, JsonUnbakedModel parent, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings settings, Identifier id, boolean hasDepth, CallbackInfoReturnable<BakedModel> cir) {
        MarkerPosition marker = MarkerPosition.fromId(id);
        if (marker != MarkerPosition.NONE) {
            //FIXME texture for the marker is correct, but the texture for the crop is not rendered
            textureMap.put("marker", FULL_GROWN_CROP_MARKER_SPRITE);
            elements.addAll(FullGrownCropMarker$createMarker());
            FullGrownCropMarker.LOGGER.info("Added the marker element to the model '{}' at position '{}'", id, marker);
        }
    }

    //create the ModelElements needed for the marker
    private static List<ModelElement> FullGrownCropMarker$createMarker() {
        final float[] uvsSmall = new float[]{2, 0, 4, 2};
        final float[] uvsLarge = new float[]{0, 0, 2, 5};
        final ModelElementFace faceSmall = new ModelElementFace(Direction.UP, 0, "#marker", new ModelElementTexture(uvsSmall, 0));
        final ModelElementFace faceLarge = new ModelElementFace(Direction.UP, 0, "#marker", new ModelElementTexture(uvsLarge, 0));
        final ModelRotation rotation = new ModelRotation(new Vector3f(0, 0, 0), Direction.Axis.Y, 0, false);

        Map<Direction, ModelElementFace> facesCube = Direction.stream().collect(HashMap::new, (map, dir) -> map.put(dir, faceSmall), HashMap::putAll);
        ModelElement smallCube = new ModelElement(new Vector3f(7, 15, 7), new Vector3f(9, 17, 9), facesCube, rotation, false);

        Map<Direction, ModelElementFace> facesCuboidTop = Direction.stream()
                .filter(dir -> dir.getAxis().isHorizontal())
                .collect(HashMap::new, (map, dir) -> map.put(dir, faceLarge), HashMap::putAll);
        facesCuboidTop.put(Direction.UP, faceSmall);
        facesCuboidTop.put(Direction.DOWN, faceSmall);
        ModelElement cuboidTop = new ModelElement(new Vector3f(7, 18, 7), new Vector3f(9, 23, 9), facesCuboidTop, rotation, false);

        return List.of(smallCube, cuboidTop);
    }

    @Shadow
    @Final
    private List<ModelElement> elements;
    @Shadow
    @Final
    protected Map<String, Either<SpriteIdentifier, String>> textureMap;

}
