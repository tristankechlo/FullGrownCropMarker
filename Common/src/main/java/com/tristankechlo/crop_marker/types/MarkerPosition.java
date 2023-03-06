package com.tristankechlo.crop_marker.types;

import com.tristankechlo.crop_marker.util.ResourceLocationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;

public enum MarkerPosition implements StringRepresentable {

    NONE(0),
    TOP(0, 16, 0);

    private final int xOffset;
    private final int yOffset;
    private final int zOffset;
    private final String name;
    private static final StringRepresentable.EnumCodec<MarkerPosition> CODEC;

    MarkerPosition(int offset) {
        this(offset, offset, offset);
    }

    MarkerPosition(int xOffset, int yOffset, int zOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
        this.name = this.name().toLowerCase();
    }

    public int getYOffset() {
        return yOffset;
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
        MarkerPosition position = CODEC.byName(string);
        return position == null ? NONE : position;
    }

    static {
        CODEC = StringRepresentable.fromEnum(MarkerPosition::values);
    }

}
