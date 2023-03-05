package com.tristankechlo.crop_marker;

import com.tristankechlo.crop_marker.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FullGrownCropMarker {

    public static final String MOD_ID = "crop_marker";
    public static final Logger LOGGER = LoggerFactory.getLogger("FullAgeCropMarker");

    public static void init() {
        LOGGER.info("FullAgeCropMarker is loading...");
        ConfigManager.loadAndVerifyConfig();
    }

}
