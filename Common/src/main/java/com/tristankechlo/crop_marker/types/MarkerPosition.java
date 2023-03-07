package com.tristankechlo.crop_marker.types;

import com.tristankechlo.crop_marker.util.ResourceLocationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum MarkerPosition implements StringRepresentable {

    NONE(0),
    TOP(0, 16, 0);

    private final int xOffset;
    private final int yOffset;
    private final int zOffset;
    private final String name;
    private static final MarkerPosition[] VALUES = values();
    private static final Map<String, MarkerPosition> CODEC = Arrays.stream(VALUES).collect(Collectors.toMap(MarkerPosition::toString, ($$0) -> $$0));

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
        MarkerPosition position = CODEC.get(string);
        return position == null ? NONE : position;
    }

}
