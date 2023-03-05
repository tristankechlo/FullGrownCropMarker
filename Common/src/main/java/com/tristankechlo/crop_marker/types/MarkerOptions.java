package com.tristankechlo.crop_marker.types;

import com.google.gson.*;
import com.tristankechlo.crop_marker.FullGrownCropMarker;
import com.tristankechlo.crop_marker.config.ConfigManager;
import net.minecraft.util.GsonHelper;

import java.lang.reflect.Type;

public record MarkerOptions(int yOffset, boolean animated, MarkerPosition position, MarkerColor color) {

    public static final MarkerOptions DEFAULT = new MarkerOptions(0, false, MarkerPosition.TOP, MarkerColor.GREEN);
    private static final String OFFSET = "offset";
    private static final String ANIMATED = "animated";
    private static final String POSITION = "position";
    private static final String COLOR = "color";

    public static MarkerOptions fromJson(JsonElement json) {
        return ConfigManager.GSON.fromJson(json, MarkerOptions.class);
    }

    public static MarkerOptions fromJson(JsonObject json, String key, MarkerOptions defaultValue) {
        JsonElement element = GsonHelper.getAsJsonObject(json, key, null);
        if (element == null) {
            FullGrownCropMarker.LOGGER.warn("Failed to deserialize {}, using default values", key);
            return defaultValue;
        }
        return fromJson(element);
    }

    public JsonElement toJson() {
        return ConfigManager.GSON.toJsonTree(this, MarkerOptions.class);
    }

    @Override
    public String toString() {
        return "MarkerOptions{" + "yOffset=" + yOffset + ", animated=" + animated + ", position=" + position + ", color=" + color + '}';
    }

    public static class Serializer implements JsonSerializer<MarkerOptions>, JsonDeserializer<MarkerOptions> {

        @Override
        public JsonElement serialize(MarkerOptions src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(OFFSET, src.yOffset());
            jsonObject.addProperty(ANIMATED, src.animated());
            jsonObject.addProperty(POSITION, src.position().getSerializedName());
            jsonObject.addProperty(COLOR, src.color().getSerializedName());
            return jsonObject;
        }

        @Override
        public MarkerOptions deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
            JsonObject json = jsonElement.getAsJsonObject();
            int yOffset = GsonHelper.getAsInt(json, OFFSET, 0);
            boolean animatedMarker = GsonHelper.getAsBoolean(json, ANIMATED, false);
            MarkerPosition position = deserializePosition(json);
            MarkerColor color = deserializeColor(json);
            return new MarkerOptions(yOffset, animatedMarker, position, color);
        }

        private MarkerPosition deserializePosition(JsonObject object) {
            String position = GsonHelper.getAsString(object, POSITION, MarkerPosition.TOP.getSerializedName());
            return MarkerPosition.fromString(position.toLowerCase());
        }

        private MarkerColor deserializeColor(JsonObject object) {
            String color = GsonHelper.getAsString(object, COLOR, MarkerColor.GREEN.getSerializedName());
            return MarkerColor.fromString(color.toLowerCase());
        }

    }


}
