package com.tristankechlo.crop_marker;

import com.tristankechlo.crop_marker.commands.CropMarkerCommand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(value = FullGrownCropMarker.MOD_ID)
public final class ForgeFullGrownCropMarker {

    public ForgeFullGrownCropMarker() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> this::onClientStarting);
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> this::onServerStarting);
    }

    private void onClientStarting() {
        FullGrownCropMarker.init();

        MinecraftForge.EVENT_BUS.addListener(this::onClientCommands);
    }

    private void onClientCommands(RegisterClientCommandsEvent event) {
        CropMarkerCommand.register(event.getDispatcher());
    }

    private void onServerStarting() {
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
