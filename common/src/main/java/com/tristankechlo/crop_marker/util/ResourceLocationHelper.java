package com.tristankechlo.crop_marker.util;

import net.minecraft.resources.ResourceLocation;

public interface ResourceLocationHelper {

    static boolean FullGrownCropMarker$shouldHaveMarker(ResourceLocation id) {
        if (id instanceof ResourceLocationHelper) {
            return ((ResourceLocationHelper) id).FullGrownCropMarker$shouldHaveMarker();
        }
        return false;
    }

    void FullGrownCropMarker$setShouldHaveMarker(boolean hasMarker);

    boolean FullGrownCropMarker$shouldHaveMarker();

}
