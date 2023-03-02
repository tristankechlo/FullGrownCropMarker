package com.tristankechlo.crop_marker.mixin;

import com.tristankechlo.crop_marker.util.IdentifierHelper;
import com.tristankechlo.crop_marker.util.MarkerPosition;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

/**
 * This mixin allows us to add a new field to the Identifier class.
 * This field is used to store the marker position of a crop.
 */
@Mixin(Identifier.class)
public abstract class IdentifierMixin implements IdentifierHelper {

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
