package com.tristankechlo.crop_marker;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FullGrownCropMarker implements ModInitializer {

    public static final String MOD_ID = "crop_marker";
    public static final Logger LOGGER = LoggerFactory.getLogger("FullAgeCropMarker");

    @Override
    public void onInitialize() {
        LOGGER.info("FullAgeCropMarker is loading...");
        //TODO load config, with black and white list for which crops to mark
    }

}
