package com.tristankechlo.crop_marker.mixin;

import com.tristankechlo.crop_marker.util.IdentifierHelper;
import com.tristankechlo.crop_marker.util.MarkerPosition;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockModels.class)
public abstract class BlockModelsMixin {

    @Inject(at = @At("RETURN"),
            method = "getModelId(Lnet/minecraft/util/Identifier;Lnet/minecraft/block/BlockState;)Lnet/minecraft/client/util/ModelIdentifier;")
    private static void FullGrownCropMarker$getModelId(Identifier id, BlockState state, CallbackInfoReturnable<ModelIdentifier> cir) {
        Block block = state.getBlock();
        MarkerPosition markerPosition = FullGrownCropMarker$getMarkerPosition(block, state);
        ModelIdentifier modelIdentifier = cir.getReturnValue();
        ((IdentifierHelper) modelIdentifier).FullGrownCropMarker$setMarkerPosition(markerPosition);
    }

    private static MarkerPosition FullGrownCropMarker$getMarkerPosition(Block block, BlockState state) {
        if (block instanceof CropBlock) {
            return FullGrownCropMarker$isMaxAge((CropBlock) block, state) ? MarkerPosition.TOP : MarkerPosition.NONE;
        } //TODO handle other blocks
        return MarkerPosition.NONE;
    }

    //whether or not the blockstate is for the max age, and therefore should have the marker
    private static boolean FullGrownCropMarker$isMaxAge(CropBlock block, BlockState state) {
        return state.get(block.getAgeProperty()) == block.getMaxAge();
    }

}
