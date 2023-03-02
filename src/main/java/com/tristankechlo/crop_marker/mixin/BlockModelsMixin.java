package com.tristankechlo.crop_marker.mixin;

import com.tristankechlo.crop_marker.util.MarkerPosition;
import com.tristankechlo.crop_marker.util.IdentifierHelper;
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
        if (FullGrownCropMarker$isCropBlock(block) && FullGrownCropMarker$isMaxAge((CropBlock) block, state)) {
            ModelIdentifier modelIdentifier = cir.getReturnValue();
            ((IdentifierHelper) modelIdentifier).FullGrownCropMarker$setMarkerPosition(MarkerPosition.TOP);
        }
    }

    //whether or not the block should have the marker in general (correct type of block)
    private static boolean FullGrownCropMarker$isCropBlock(Block block) {
        return block instanceof CropBlock; //TODO handle other crop blocks
    }

    //whether or not the blockstate is for the max age, and therefore should have the marker
    private static boolean FullGrownCropMarker$isMaxAge(CropBlock block, BlockState state) {
        return state.get(block.getAgeProperty()) == block.getMaxAge();
    }

}
