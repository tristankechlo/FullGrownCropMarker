package com.tristankechlo.crop_marker.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.tristankechlo.crop_marker.FullGrownCropMarker;
import com.tristankechlo.crop_marker.types.MarkerColor;
import com.tristankechlo.crop_marker.types.MarkerOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public final class FullGrownCropMarkerConfig {

    private static MarkerOptions defaultOptions = MarkerOptions.DEFAULT;
    private static Map<ResourceLocation, MarkerOptions> options = createOptions();
    private static final Type MAP_TYPE = new TypeToken<Map<ResourceLocation, MarkerOptions>>() {}.getType();

    public static MarkerOptions getOptions(ResourceLocation id) {
        return options.getOrDefault(id, defaultOptions);
    }

    public static void setToDefault() {
        defaultOptions = MarkerOptions.DEFAULT;
        options = createOptions();
    }

    public static JsonObject serialize(JsonObject jsonObject) {
        //serialize default options
        jsonObject.add("defaultOptions", defaultOptions.toJson());
        //serialize options for each crop
        JsonElement overrides = ConfigManager.GSON.toJsonTree(options, MAP_TYPE);
        jsonObject.add("overrides", overrides);
        return jsonObject;
    }

    public static void deserialize(JsonObject jsonObject) {
        //deserialize default options
        defaultOptions = MarkerOptions.fromJson(jsonObject, "defaultOptions", MarkerOptions.DEFAULT);
        //deserialize options for each crop
        try {
            JsonObject overrides = GsonHelper.getAsJsonObject(jsonObject, "overrides", null);
            options = ConfigManager.GSON.fromJson(overrides, MAP_TYPE);
        } catch (Exception e) {
            FullGrownCropMarker.LOGGER.error("Error while deserializing config: {}", e.getMessage());
            options = createOptions();
        }
    }

    private static Map<ResourceLocation, MarkerOptions> createOptions() {
        Map<ResourceLocation, MarkerOptions> options = new HashMap<>();
        options.put(getID("wheat_stage7"), new MarkerOptions(1, false, true, MarkerColor.YELLOW));
        options.put(getID("carrots_stage3"), new MarkerOptions(-2, false, true, MarkerColor.ORANGE));
        options.put(getID("beetroots_stage3"), new MarkerOptions(-4, false, true, MarkerColor.RED));
        options.put(getID("potatoes_stage3"), new MarkerOptions(1, false, true, MarkerColor.GREEN));
        options.put(getID("nether_wart_stage2"), new MarkerOptions(-1, false, true, MarkerColor.RED));
        options.put(getID("sweet_berry_bush_stage2"), new MarkerOptions(2, false, true, MarkerColor.GREEN));
        options.put(getID("sweet_berry_bush_stage3"), new MarkerOptions(2, false, true, MarkerColor.RED));
        return options;
    }

    private static ResourceLocation getID(String path) {
        return new ResourceLocation("minecraft", "block/" + path);
    }

}
