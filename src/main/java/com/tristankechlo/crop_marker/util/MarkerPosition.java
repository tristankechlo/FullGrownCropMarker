package com.tristankechlo.crop_marker.util;

import net.minecraft.util.Identifier;

public enum MarkerPosition {

    NONE, TOP;

    public static MarkerPosition fromId(Identifier id) {
        if (id instanceof IdentifierHelper) {
            return ((IdentifierHelper) id).FullGrownCropMarker$getMarkerPosition();
        }
        return NONE;
    }

}
