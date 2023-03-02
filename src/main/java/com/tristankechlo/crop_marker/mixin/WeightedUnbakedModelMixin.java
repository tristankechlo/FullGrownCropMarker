package com.tristankechlo.crop_marker.mixin;

import com.tristankechlo.crop_marker.util.IdentifierHelper;
import com.tristankechlo.crop_marker.util.MarkerPosition;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.Baker;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.json.ModelVariant;
import net.minecraft.client.render.model.json.WeightedUnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Function;

@Mixin(WeightedUnbakedModel.class)
public abstract class WeightedUnbakedModelMixin {

    @Inject(method = "bake", at = @At("HEAD"))
    private void FullGrownCropMarker$bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId, CallbackInfoReturnable<@Nullable BakedModel> cir) {
        MarkerPosition marker = MarkerPosition.fromId(modelId);
        if (marker != MarkerPosition.NONE) {
            this.getVariants().forEach(variant -> {
                //reapply the marker to all the variants of this model
                //this is needed because the models are loaded as a WeightedUnbakedModel from the blockstates json
                //the WeightedUnbakedModel then loads the variant models from the actual models json
                IdentifierHelper location = (IdentifierHelper) variant.getLocation();
                location.FullGrownCropMarker$setMarkerPosition(((IdentifierHelper) modelId).FullGrownCropMarker$getMarkerPosition());
            });
        }
    }

    @Shadow
    public abstract List<ModelVariant> getVariants();

}
