package com.tristankechlo.crop_marker.mixin;

import com.mojang.datafixers.util.Either;
import com.tristankechlo.crop_marker.FullGrownCropMarker;
import com.tristankechlo.crop_marker.config.FullGrownCropMarkerConfig;
import com.tristankechlo.crop_marker.types.MarkerColor;
import com.tristankechlo.crop_marker.types.MarkerOptions;
import com.tristankechlo.crop_marker.types.MarkerPosition;
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
            List<ModelElement> all = ((JsonUnbakedModel) (Object) this).getElements(); //get the original elements, or the elements of the parent model
            elements.clear();
            elements.addAll(all); //add the original elements to the model
            textureMap.put("marker", FULL_GROWN_CROP_MARKER_SPRITE);
            MarkerOptions options = FullGrownCropMarkerConfig.getOptions(id);
            elements.addAll(FullGrownCropMarker$createMarkerTop(options)); //add the marker elements to the model
            FullGrownCropMarker.LOGGER.info("Added the marker to '{}' with {}", id, options);
        }
    }

    //create the ModelElements needed for the marker
    private static List<ModelElement> FullGrownCropMarker$createMarkerTop(MarkerOptions options) {
        final float[] uvsSmall = options.color().getUvsSmall();
        final float[] uvsLarge = options.color().getUvsLarge();
        final MarkerPosition position = options.position();
        final int yOffset = options.yOffset() + position.getOffset().y();
        final boolean animated = options.animated();

        final ModelElementFace faceSmall = new ModelElementFace(Direction.UP, 0, "#marker", new ModelElementTexture(uvsSmall, 0));
        final ModelElementFace faceLarge = new ModelElementFace(Direction.UP, 0, "#marker", new ModelElementTexture(uvsLarge, 0));
        final ModelRotation rotation = new ModelRotation(new Vector3f(0, 0, 0), Direction.Axis.Y, 0, false);

        Map<Direction, ModelElementFace> facesCube = Direction.stream().collect(HashMap::new, (map, dir) -> map.put(dir, faceSmall), HashMap::putAll);
        Vector3f fromSmall = new Vector3f(7, 1, 7).add(0, yOffset, 0);
        Vector3f toSmall = new Vector3f(9, 3, 9).add(0, yOffset, 0);
        ModelElement smallCube = new ModelElement(fromSmall, toSmall, facesCube, rotation, false);

        Map<Direction, ModelElementFace> facesCuboidTop = Direction.stream()
                .filter(dir -> dir.getAxis().isHorizontal())
                .collect(HashMap::new, (map, dir) -> map.put(dir, faceLarge), HashMap::putAll);
        facesCuboidTop.put(Direction.UP, faceSmall);
        facesCuboidTop.put(Direction.DOWN, faceSmall);
        Vector3f fromLarge = new Vector3f(7, 4, 7).add(0, yOffset, 0);
        Vector3f toLarge = new Vector3f(9, 9, 9).add(0, yOffset, 0);
        ModelElement cuboidTop = new ModelElement(fromLarge, toLarge, facesCuboidTop, rotation, false);

        return List.of(smallCube, cuboidTop);
    }

    @Shadow
    @Final
    private List<ModelElement> elements;
    @Shadow
    @Final
    protected Map<String, Either<SpriteIdentifier, String>> textureMap;

}
