package com.tristankechlo.crop_marker.mixin;

import com.tristankechlo.crop_marker.util.ResourceLocationHelper;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ResourceLocation.class)
public abstract class ResourceLocationMixin implements ResourceLocationHelper {

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
