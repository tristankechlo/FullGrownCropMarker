package com.tristankechlo.crop_marker.util;

import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Type;

public class ResourceLocationSerializer implements JsonSerializer<ResourceLocation>, JsonDeserializer<ResourceLocation> {

    @Override
    public ResourceLocation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String identifier = json.getAsString();
        ResourceLocation id = ResourceLocation.tryParse(identifier);
        if (id == null) {
            throw new JsonParseException("Invalid Identifier: " + identifier);
        }
        return id;
    }

    @Override
    public JsonElement serialize(ResourceLocation src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }

}
