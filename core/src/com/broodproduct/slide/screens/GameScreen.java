package com.broodproduct.slide.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.broodproduct.slide.render.GameRenderer;
import com.broodproduct.slide.render.GameWorld;
import com.broodproduct.slide.tool.InputHandler;


public class GameScreen implements Screen {
	private GameWorld world;
	private GameRenderer renderer;
	private float runTime;
    private Box2DDebugRenderer debug = new Box2DDebugRenderer( true, true, true, true, true, true);

	public GameScreen() {
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameHeight = 540;
		float gameWidth = 960;
//		float gameWidth = screenHeight / (screenWidth / gameHeight);

		world = new GameWorld(gameWidth, gameHeight);
		Gdx.input.setInputProcessor(new InputHandler(world, screenWidth / gameWidth, screenHeight / gameHeight));
		renderer = new GameRenderer(world, gameWidth, gameHeight);
	}

	@Override
	public void render(float delta) {
		runTime += delta;
		world.update(delta);
		renderer.render(delta, runTime);
        debug.render(world.getBoxWorld(), renderer.getCam().combined);
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
        world.dispose();
        renderer.dispose();
	}

}
