package com.tristankechlo.crop_marker.mixin;

import com.tristankechlo.crop_marker.types.MarkerPosition;
import com.tristankechlo.crop_marker.util.ResourceLocationHelper;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockModelShaper.class)
public abstract class BlockModelShaperMixin {

    @Inject(at = @At("RETURN"),
            method = "stateToModelLocation(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/client/resources/model/ModelResourceLocation;")
    private static void FullGrownCropMarker$getModelId(ResourceLocation $$0, BlockState state, CallbackInfoReturnable<ModelResourceLocation> cir) {
        Block block = state.getBlock();
        MarkerPosition markerPosition = FullGrownCropMarker$getMarkerPosition(block, state);
        ModelResourceLocation modelIdentifier = cir.getReturnValue();
        ((ResourceLocationHelper) modelIdentifier).FullGrownCropMarker$setMarkerPosition(markerPosition);
    }

    private static MarkerPosition FullGrownCropMarker$getMarkerPosition(Block block, BlockState state) {
        if (block instanceof CropBlock) {
            return FullGrownCropMarker$checkCropBlock((CropBlock) block, state) ? MarkerPosition.TOP : MarkerPosition.NONE;
        }
        if (block instanceof NetherWartBlock) {
            return FullGrownCropMarker$isMaxAge(state, NetherWartBlock.AGE, NetherWartBlock.MAX_AGE) ? MarkerPosition.TOP : MarkerPosition.NONE;
        }
        if (block instanceof SweetBerryBushBlock) {
            int age = state.getValue(SweetBerryBushBlock.AGE);
            return age > 1 ? MarkerPosition.TOP : MarkerPosition.NONE;
        }
        //TODO handle other blocks
        return MarkerPosition.NONE;
    }

    private static boolean FullGrownCropMarker$checkCropBlock(CropBlock block, BlockState state) {
        return state.getValue(block.getAgeProperty()) == block.getMaxAge();
    }

    private static boolean FullGrownCropMarker$isMaxAge(BlockState state, Property<Integer> property, int maxAge) {
        return state.getValue(property) == maxAge;
    }

}
