package com.broodproduct.slide.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.broodproduct.slide.test.BoxApp;

public class Main {
	public static void main(final String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 500;
        config.height = 600;
        new LwjglApplication(new BoxApp(), config);
	}
}
