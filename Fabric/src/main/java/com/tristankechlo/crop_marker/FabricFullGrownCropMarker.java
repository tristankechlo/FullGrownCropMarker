package com.tristankechlo.crop_marker;

import net.fabricmc.api.ClientModInitializer;

public final class FabricFullGrownCropMarker implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FullGrownCropMarker.init();
    }

}
