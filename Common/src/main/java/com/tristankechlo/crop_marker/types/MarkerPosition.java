package com.tristankechlo.crop_marker.types;

import com.tristankechlo.crop_marker.util.ResourceLocationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import org.joml.Vector3i;

public enum MarkerPosition implements StringRepresentable {

    NONE(new Vector3i(0)),
    TOP(new Vector3i(0, 16, 0));

    private final Vector3i offset;
    private final String name;
    private static final StringRepresentable.EnumCodec<MarkerPosition> CODEC;

    MarkerPosition(Vector3i offset) {
        this.offset = offset;
        this.name = this.name().toLowerCase();
    }

    public Vector3i getOffset() {
        return offset;
    }

    public static MarkerPosition fromId(ResourceLocation id) {
        if (id instanceof ResourceLocationHelper) {
            return ((ResourceLocationHelper) id).FullGrownCropMarker$getMarkerPosition();
        }
        return NONE;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public static MarkerPosition fromString(String string) {
        return CODEC.byName(string, NONE);
    }

    static {
        CODEC = StringRepresentable.fromEnum(MarkerPosition::values);
    }

}
