package com.tristankechlo.crop_marker;

import com.google.auto.service.AutoService;
import com.tristankechlo.crop_marker.platform.IPlatformHelper;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

@AutoService(IPlatformHelper.class)
public final class NeoforgePlatformHelper implements IPlatformHelper {

    @Override
    public Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

}