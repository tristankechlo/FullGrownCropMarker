package com.tristankechlo.crop_marker;

import com.tristankechlo.crop_marker.commands.CropMarkerCommand;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

public final class FabricFullGrownCropMarker implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FullGrownCropMarker.init();

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            CropMarkerCommand.register(dispatcher);
        });

        ClientChunkEvents.CHUNK_LOAD.register((level, chunk) -> {});
        ClientChunkEvents.CHUNK_UNLOAD.register((level, chunk) -> {});
        WorldRenderEvents.BEFORE_BLOCK_OUTLINE.register((context, result) -> true);
    }

}
