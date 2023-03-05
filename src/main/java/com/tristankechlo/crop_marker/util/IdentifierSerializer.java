package com.tristankechlo.crop_marker.util;

import com.google.gson.*;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;

public class IdentifierSerializer implements JsonSerializer<Identifier>, JsonDeserializer<Identifier> {

    @Override
    public Identifier deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String identifier = json.getAsString();
        Identifier id = Identifier.tryParse(identifier);
        if (id == null) {
            throw new JsonParseException("Invalid Identifier: " + identifier);
        }
        return id;
    }

    @Override
    public JsonElement serialize(Identifier src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }

}
