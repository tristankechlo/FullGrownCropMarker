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
        // ascii art of the word WARNING
        FullGrownCropMarker.LOGGER.warn("============================================================");
        FullGrownCropMarker.LOGGER.warn("__          __       _____   _   _  _____  _   _   _____ ");
        FullGrownCropMarker.LOGGER.warn("\\ \\        / //\\    |  __ \\ | \\ | ||_   _|| \\ | | / ____|");
        FullGrownCropMarker.LOGGER.warn(" \\ \\  /\\  / //  \\   | |__) ||  \\| |  | |  |  \\| || |  __ ");
        FullGrownCropMarker.LOGGER.warn("  \\ \\/  \\/ // /\\ \\  |  _  / | . ` |  | |  | . ` || | |_ |");
        FullGrownCropMarker.LOGGER.warn("   \\  /\\  // ____ \\ | | \\ \\ | |\\  | _| |_ | |\\  || |__| |");
        FullGrownCropMarker.LOGGER.warn("    \\/  \\//_/    \\_\\|_|  \\_\\|_| \\_||_____||_| \\_| \\_____|");
        FullGrownCropMarker.LOGGER.warn("============================================================");
        FullGrownCropMarker.LOGGER.warn("FullGrownCropMarker is a client-side mod!");
        FullGrownCropMarker.LOGGER.warn("Consider removing it from your server, as it does absolutely nothing here!");
        FullGrownCropMarker.LOGGER.warn("============================================================");
    }

}
