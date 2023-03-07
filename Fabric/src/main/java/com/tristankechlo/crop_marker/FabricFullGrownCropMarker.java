package com.tristankechlo.crop_marker;

import com.tristankechlo.crop_marker.commands.CropMarkerCommand;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;

public final class FabricFullGrownCropMarker implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FullGrownCropMarker.init();

        ClientCommandManager.DISPATCHER.register(CropMarkerCommand.get());
        FullGrownCropMarker.LOGGER.info("Command '/{}' registered", FullGrownCropMarker.MOD_ID);
    }

}
