package com.tristankechlo.crop_marker;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(value = FullGrownCropMarker.MOD_ID)
public final class ForgeFullGrownCropMarker {

    public ForgeFullGrownCropMarker() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> FullGrownCropMarker::init);
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> ForgeFullGrownCropMarker::onServerStarting);
    }

    private static void onServerStarting() {
        //TODO log that the mod is loaded on the server
    }

}
