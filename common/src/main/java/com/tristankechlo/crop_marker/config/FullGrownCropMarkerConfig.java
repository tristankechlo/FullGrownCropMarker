package com.tristankechlo.crop_marker.config;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tristankechlo.crop_marker.FullGrownCropMarker;
import com.tristankechlo.crop_marker.types.MarkerColor;
import com.tristankechlo.crop_marker.types.MarkerOptions;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public record FullGrownCropMarkerConfig(MarkerOptions defaultOptions, Map<ResourceLocation, MarkerOptions> options) {

    private static FullGrownCropMarkerConfig INSTANCE = new FullGrownCropMarkerConfig(MarkerOptions.DEFAULT, createOptions());
    public static final Codec<FullGrownCropMarkerConfig> CODEC = RecordCodecBuilder.create(
            (instance) -> instance.group(
                    MarkerOptions.CODEC.fieldOf("defaultOptions").forGetter(FullGrownCropMarkerConfig::defaultOptions),
                    Codec.unboundedMap(ResourceLocation.CODEC, MarkerOptions.CODEC).fieldOf("overrides").forGetter(FullGrownCropMarkerConfig::options)
            ).apply(instance, FullGrownCropMarkerConfig::new)
    );

    public static MarkerOptions getOptions(ResourceLocation id) {
        return INSTANCE.options().getOrDefault(id, INSTANCE.defaultOptions());
    }

    public static void setToDefault() {
        INSTANCE = new FullGrownCropMarkerConfig(MarkerOptions.DEFAULT, createOptions());
    }

    public static JsonElement serialize() {
        DataResult<JsonElement> result = CODEC.encodeStart(JsonOps.INSTANCE, INSTANCE);
        result.error().ifPresent((partial) -> FullGrownCropMarker.LOGGER.error(partial.message()));
        return result.result().orElseThrow();
    }

    public static void deserialize(JsonElement json) {
        DataResult<FullGrownCropMarkerConfig> result = CODEC.parse(JsonOps.INSTANCE, json);
        result.error().ifPresent((partial) -> FullGrownCropMarker.LOGGER.error(partial.message()));
        INSTANCE = result.result().orElseThrow();
    }

    private static Map<ResourceLocation, MarkerOptions> createOptions() {
        Map<ResourceLocation, MarkerOptions> options = new HashMap<>();
        options.put(getID("wheat_stage7"), new MarkerOptions(1, false, true, MarkerColor.YELLOW));
        options.put(getID("carrots_stage3"), new MarkerOptions(-2, false, true, MarkerColor.ORANGE));
        options.put(getID("beetroots_stage3"), new MarkerOptions(-4, false, true, MarkerColor.RED));
        options.put(getID("potatoes_stage3"), new MarkerOptions(1, false, true, MarkerColor.GREEN));
        options.put(getID("nether_wart_stage2"), new MarkerOptions(-1, false, true, MarkerColor.RED));
        options.put(getID("sweet_berry_bush_stage2"), new MarkerOptions(2, false, true, MarkerColor.YELLOW));
        options.put(getID("sweet_berry_bush_stage3"), new MarkerOptions(2, false, true, MarkerColor.GREEN));
        return options;
    }

    private static ResourceLocation getID(String path) {
        return ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + path);
    }

}
