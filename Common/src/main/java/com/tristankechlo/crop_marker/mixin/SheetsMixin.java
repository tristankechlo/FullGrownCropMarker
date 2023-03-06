package com.tristankechlo.crop_marker.mixin;

import com.tristankechlo.crop_marker.FullGrownCropMarker;
import com.tristankechlo.crop_marker.util.ResourceLocationHelper;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(Sheets.class)
public abstract class SheetsMixin {

    @Inject(method = "getAllMaterials", at = @At("HEAD"))
    private static void FullGrownCropMarker$getAllMaterials(Consumer<Material> $$0, CallbackInfo ci) {
        //dirty hack to add the marker texture to the block atlas
        Material material = ResourceLocationHelper.MATERIAL;
        $$0.accept(material);
        FullGrownCropMarker.LOGGER.info("Added marker texture to TextureAtlas {}", material.atlasLocation());
    }

}
