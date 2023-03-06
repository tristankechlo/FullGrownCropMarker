package com.tristankechlo.crop_marker.mixin;

import com.tristankechlo.crop_marker.types.MarkerPosition;
import com.tristankechlo.crop_marker.util.ResourceLocationHelper;
import net.minecraft.client.renderer.block.model.MultiVariant;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Function;

@Mixin(MultiVariant.class)
public abstract class MultiVariantMixin {

    @Inject(method = "bake", at = @At("HEAD"))
    private void FullGrownCropMarker$bake(ModelBakery $$0, Function<Material, TextureAtlasSprite> $$1, ModelState $$2, ResourceLocation id, CallbackInfoReturnable<BakedModel> cir) {
        MarkerPosition marker = MarkerPosition.fromId(id);
        if (marker != MarkerPosition.NONE) {
            this.getVariants().forEach(variant -> {
                //reapply the marker to all the variants of this model
                //this is needed because the models are loaded as a WeightedUnbakedModel from the blockstates json
                //the WeightedUnbakedModel then loads the variant models from the actual models json
                ResourceLocationHelper location = (ResourceLocationHelper) variant.getModelLocation();
                location.FullGrownCropMarker$setMarkerPosition(((ResourceLocationHelper) id).FullGrownCropMarker$getMarkerPosition());
            });
        }
    }

    @Shadow
    public abstract List<Variant> getVariants();

}
