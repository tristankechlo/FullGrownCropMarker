package com.tristankechlo.crop_marker.types;

import net.minecraft.util.StringRepresentable;

public enum MarkerColor implements StringRepresentable {

    RED("red", 2, 10, 0, 10),
    GREEN("green", 2, 0, 0, 0),
    BLUE("blue", 2, 5, 0, 5),
    YELLOW("yellow", 10, 0, 8, 0),
    ORANGE("orange", 10, 10, 8, 10),
    PURPLE("purple", 10, 5, 8, 5);

    private final String name;
    private final float[] uvsSmall;
    private final float[] uvsLarge;
    private final float[] uvsSmallAnimated;
    private final float[] uvsLargeAnimated;
    private static final StringRepresentable.EnumCodec<MarkerColor> CODEC;

    MarkerColor(String name, float x1, float y1, float x2, float y2) {
        this.name = name;
        this.uvsSmall = new float[]{x1, y1, x1 + 2, y1 + 2};
        this.uvsLarge = new float[]{x2, y2, x2 + 2, y2 + 5};
        this.uvsSmallAnimated = new float[]{x1 + 4, y1, x1 + 6, y1 + 2};
        this.uvsLargeAnimated = new float[]{x2 + 4, y2, x2 + 6, y2 + 5};
    }

    public float[] getUvsSmall() {
        return uvsSmall;
    }

    public float[] getUvsLarge() {
        return uvsLarge;
    }

    public float[] getUvsSmallAnimated() {
        return uvsSmallAnimated;
    }

    public float[] getUvsLargeAnimated() {
        return uvsLargeAnimated;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public static MarkerColor fromString(String string) {
        return CODEC.byName(string, GREEN);
    }

    static {
        CODEC = StringRepresentable.fromEnum(MarkerColor::values);
    }

}
