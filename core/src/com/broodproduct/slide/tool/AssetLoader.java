package com.broodproduct.slide.tool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

	public static Texture texture;
	public static TextureRegion asteroid, blackHole, satelite, ufo, leftPark, rightPark, background;

	public static void load() {
		texture = new Texture(Gdx.files.internal("imd/textures.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		asteroid = new TextureRegion(texture, 334, 541, 80, 84);
		blackHole = new TextureRegion(texture, 0, 541, 180, 181);
		satelite = new TextureRegion(texture, 181, 541, 152, 60);
		ufo = new TextureRegion(texture, 181, 602, 100, 87);
		leftPark = new TextureRegion(texture, 961, 0, 116, 540);
		rightPark = new TextureRegion(texture, 1078, 0, 116, 540);
		background = new TextureRegion(texture, 0, 0, 960, 540);
	}

	public static void dispose() {
		texture.dispose();
	}
}