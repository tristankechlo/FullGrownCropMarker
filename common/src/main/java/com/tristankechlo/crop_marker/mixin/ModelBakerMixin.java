package com.tristankechlo.crop_marker.mixin;

import com.tristankechlo.crop_marker.util.ModelBakerAddon;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBakery.ModelBakerImpl.class)
public abstract class ModelBakerMixin implements ModelBakerAddon {

    // need to store this, so we can access this during the bake process
    @Unique
    private ModelResourceLocation FullGrownCropMarker$id;

    @Inject(at = @At("TAIL"), method = "<init>")
    private void FullGrownCropMarker$init(ModelBakery bakery, ModelBakery.TextureGetter getter, ModelResourceLocation id, CallbackInfo ci) {
        this.FullGrownCropMarker$id = id;
    }

    public ModelResourceLocation FullGrownCropMarker$id() {
        return this.FullGrownCropMarker$id;
    }

}
