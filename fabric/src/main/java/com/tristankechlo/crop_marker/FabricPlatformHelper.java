package com.tristankechlo.crop_marker;

import com.google.auto.service.AutoService;
import com.tristankechlo.crop_marker.platform.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

@AutoService(IPlatformHelper.class)
public final class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

}
