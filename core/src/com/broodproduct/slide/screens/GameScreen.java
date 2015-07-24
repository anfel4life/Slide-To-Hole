package com.broodproduct.slide.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.broodproduct.slide.render.GameRenderer;
import com.broodproduct.slide.render.GameWorld;
import com.broodproduct.slide.tool.InputHandler;


public class GameScreen implements Screen {
	private GameWorld world;
	private GameRenderer renderer;
	private float runTime;

	public GameScreen() {
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameHeight = 540;
		float gameWidth = 960;
//		float gameWidth = screenHeight / (screenWidth / gameHeight);

		world = new GameWorld(gameWidth, gameHeight);
		Gdx.input.setInputProcessor(new InputHandler(world, screenWidth / gameWidth, screenHeight / gameHeight));
		renderer = new GameRenderer(world);
	}

	@Override
	public void render(float delta) {
		runTime += delta;
		world.update(delta);
		renderer.render(delta, runTime);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
