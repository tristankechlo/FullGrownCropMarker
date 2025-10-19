package com.tristankechlo.crop_marker;

import com.tristankechlo.crop_marker.commands.CropMarkerCommand;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public final class FabricFullGrownCropMarker implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FullGrownCropMarker.init();

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            CropMarkerCommand.register(dispatcher);
        });
    }

}
