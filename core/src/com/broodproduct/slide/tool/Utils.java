package com.broodproduct.slide.tool;

import com.badlogic.gdx.Gdx;
import com.broodproduct.slide.test.BodyEditorLoader;

/**
 * Created by Anfel
 * Brood Product Ukraine
 */
public final class Utils {
    private static BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("box/slide"));

    public static BodyEditorLoader getBoxLoader() {
        return loader;
    }
}
