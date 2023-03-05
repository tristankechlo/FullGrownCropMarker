package com.tristankechlo.crop_marker.mixin;

import com.tristankechlo.crop_marker.types.MarkerPosition;
import com.tristankechlo.crop_marker.util.ResourceLocationHelper;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ResourceLocation.class)
public abstract class ResourceLocationMixin implements ResourceLocationHelper {

    protected MarkerPosition FullGrownCropMarker$markerPosition = MarkerPosition.NONE;

    @Override
    public MarkerPosition FullGrownCropMarker$getMarkerPosition() {
        return FullGrownCropMarker$markerPosition;
    }

    @Override
    public void FullGrownCropMarker$setMarkerPosition(MarkerPosition position) {
        FullGrownCropMarker$markerPosition = position;
    }

}
