package com.tristankechlo.crop_marker.types;

import com.tristankechlo.crop_marker.util.IdentifierHelper;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import org.joml.Vector3i;

public enum MarkerPosition implements StringIdentifiable {

    NONE(new Vector3i(0)),
    TOP(new Vector3i(0, 16, 0));

    private final Vector3i offset;
    private final String name;
    private static final StringIdentifiable.Codec<MarkerPosition> CODEC;

    MarkerPosition(Vector3i offset) {
        this.offset = offset;
        this.name = this.name().toLowerCase();
    }

    public Vector3i getOffset() {
        return offset;
    }

    public static MarkerPosition fromId(Identifier id) {
        if (id instanceof IdentifierHelper) {
            return ((IdentifierHelper) id).FullGrownCropMarker$getMarkerPosition();
        }
        return NONE;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public static MarkerPosition fromString(String string) {
        return CODEC.byId(string, NONE);
    }

    static {
        CODEC = StringIdentifiable.createCodec(MarkerPosition::values);
    }

}
