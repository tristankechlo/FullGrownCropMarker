package com.tristankechlo.crop_marker.util;

import com.tristankechlo.crop_marker.FullGrownCropMarker;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

public interface ResourceLocationHelper {

    ResourceLocation TEXTURE = new ResourceLocation(FullGrownCropMarker.MOD_ID, "block/marker");
    ResourceLocation TEXTURE_ANIMATED = new ResourceLocation(FullGrownCropMarker.MOD_ID, "block/marker_animated");
    Material MATERIAL = new Material(InventoryMenu.BLOCK_ATLAS, TEXTURE);
    Material MATERIAL_ANIMATED = new Material(InventoryMenu.BLOCK_ATLAS, TEXTURE_ANIMATED);

    static boolean FullGrownCropMarker$shouldHaveMarker(ResourceLocation id) {
        if (id instanceof ResourceLocationHelper) {
            return ((ResourceLocationHelper) id).FullGrownCropMarker$shouldHaveMarker();
        }
        return false;
    }

    void FullGrownCropMarker$setShouldHaveMarker(boolean hasMarker);

    boolean FullGrownCropMarker$shouldHaveMarker();

}
