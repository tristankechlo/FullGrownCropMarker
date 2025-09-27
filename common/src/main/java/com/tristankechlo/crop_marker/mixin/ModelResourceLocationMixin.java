package com.tristankechlo.crop_marker.mixin;

import com.tristankechlo.crop_marker.util.ResourceLocationHelper;
import net.minecraft.client.resources.model.ModelResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ModelResourceLocation.class)
public abstract class ModelResourceLocationMixin implements ResourceLocationHelper {

    @Unique
    protected boolean FullGrownCropMarker$shouldHaveMarker = false;

    @Override
    public void FullGrownCropMarker$setShouldHaveMarker(boolean hasMarker) {
        this.FullGrownCropMarker$shouldHaveMarker = hasMarker;
    }

    @Override
    public boolean FullGrownCropMarker$shouldHaveMarker() {
        return this.FullGrownCropMarker$shouldHaveMarker;
    }

}
