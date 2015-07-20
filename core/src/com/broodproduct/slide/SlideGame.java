package com.broodproduct.slide;

import com.badlogic.gdx.Game;
import com.broodproduct.slide.screens.GameScreen;
import com.broodproduct.slide.tool.AssetLoader;

/**
 * Created by Anfel
 * Brood Product Ukraine
 */

public class SlideGame extends Game {

    @Override
    public void create() {
        AssetLoader.load();
        setScreen(new GameScreen());
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}
