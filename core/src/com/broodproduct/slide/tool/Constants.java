package com.broodproduct.slide.tool;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Anfel
 * Brood Product Ukraine
 */
public final class Constants {
    public static final int PIXELS_PER_METER = 16;

    public static float scaleToUnit(float toScale) {
        return toScale / PIXELS_PER_METER;
    }

    public static Vector2 scaleToUnit(Vector2 vector) {
        return new Vector2(scaleToUnit(vector.x), scaleToUnit(vector.y));
    }

    public static float scaleToPixel(float scale) {
        return scale * PIXELS_PER_METER;
    }

    public static Vector2 scaleToPixel(Vector2 vector) {
        return new Vector2(scaleToPixel(vector.x), scaleToPixel(vector.y));
    }
}
