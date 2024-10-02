package com.tristankechlo.crop_marker.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.tristankechlo.crop_marker.FullGrownCropMarker;
import com.tristankechlo.crop_marker.platform.Services;
import com.tristankechlo.crop_marker.types.MarkerOptions;
import com.tristankechlo.crop_marker.util.ResourceLocationSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


public final class ConfigManager {

    private static final File CONFIG_DIR = Services.PLATFORM.getConfigDirectory().toFile();
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls()
            .registerTypeAdapter(MarkerOptions.class, new MarkerOptions.Serializer())
            .registerTypeAdapter(ResourceLocation.class, new ResourceLocationSerializer())
            .create();
    public static final String FILE_NAME = FullGrownCropMarker.MOD_ID + ".json";

    public static void loadAndVerifyConfig() {
        ConfigManager.createConfigFolder();
        FullGrownCropMarkerConfig.setToDefault();
        File configFile = new File(CONFIG_DIR, FILE_NAME);
        if (configFile.exists()) {
            ConfigManager.loadConfigFromFile(configFile);
            ConfigManager.writeConfigToFile(configFile);
            FullGrownCropMarker.LOGGER.info("Saved the checked/corrected config '{}'", FILE_NAME);
        } else {
            ConfigManager.writeConfigToFile(configFile);
            FullGrownCropMarker.LOGGER.warn("No config '{}' was found, created a new one.", FILE_NAME);
        }
    }

    private static void writeConfigToFile(File file) {
        JsonObject jsonObject = FullGrownCropMarkerConfig.serialize(new JsonObject());
        try {
            JsonWriter writer = new JsonWriter(new FileWriter(file));
            writer.setIndent("\t");
            GSON.toJson(jsonObject, writer);
            writer.close();
        } catch (Exception e) {
            FullGrownCropMarker.LOGGER.error("There was an error writing the config to file '{}'", FILE_NAME);
            e.printStackTrace();
        }
    }

    private static void loadConfigFromFile(File file) {
        JsonObject json = null;
        try {
            JsonElement jsonElement = GsonHelper.fromJson(GSON, new FileReader(file), JsonElement.class);
            json = jsonElement.getAsJsonObject();
        } catch (Exception e) {
            FullGrownCropMarker.LOGGER.error("There was an error loading the config file '{}'", FILE_NAME);
            e.printStackTrace();
        }
        if (json != null) {
            FullGrownCropMarkerConfig.deserialize(json);
            FullGrownCropMarker.LOGGER.info("Config '{}' was successfully loaded.", FILE_NAME);
        } else {
            FullGrownCropMarker.LOGGER.error("Error loading config '{}', config hasn't been loaded.", FILE_NAME);
        }
    }

    public static void resetConfig() {
        FullGrownCropMarkerConfig.setToDefault();
        File configFile = new File(CONFIG_DIR, FILE_NAME);
        ConfigManager.writeConfigToFile(configFile);
        FullGrownCropMarker.LOGGER.info("Config '{}' was set to default.", FILE_NAME);
    }

    public static void reloadConfig() {
        File configFile = new File(CONFIG_DIR, FILE_NAME);
        if (configFile.exists()) {
            ConfigManager.loadConfigFromFile(configFile);
            ConfigManager.writeConfigToFile(configFile);
            FullGrownCropMarker.LOGGER.info("Saved the checked/corrected config " + FILE_NAME);
        } else {
            ConfigManager.writeConfigToFile(configFile);
            FullGrownCropMarker.LOGGER.warn("No config '{}' was found, created a new one", FILE_NAME);
        }
    }

    public static String getConfigPath() {
        File configFile = new File(CONFIG_DIR, FILE_NAME);
        return configFile.getAbsolutePath();
    }

    private static void createConfigFolder() {
        if (!CONFIG_DIR.exists()) {
            if (!CONFIG_DIR.mkdirs()) {
                throw new RuntimeException("Could not create config folder " + CONFIG_DIR.getAbsolutePath());
            }
        }
    }

}
