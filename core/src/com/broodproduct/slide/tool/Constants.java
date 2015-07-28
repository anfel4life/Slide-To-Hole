package com.broodproduct.slide.tool;

/**
 * Created by Anfel
 * Brood Product Ukraine
 */
public final class Constants {
    public static final int PIXELS_PER_METER = 16;

    public static float scale(float toScale) {
        return toScale / PIXELS_PER_METER;
    }
}
