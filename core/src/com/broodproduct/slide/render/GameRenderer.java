package com.broodproduct.slide.render;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.broodproduct.slide.tool.AssetLoader;


public class GameRenderer {

	private OrthographicCamera cam;
	private SpriteBatch batcher;
    private GameWorld world;

	public GameRenderer(GameWorld world) {
		this.world = world;
        cam = new OrthographicCamera();
		cam.setToOrtho(true);
		batcher = new SpriteBatch();
//		batcher.setProjectionMatrix(cam.combined);

	}

	public void render(float delta, float runTime) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batcher.begin();
        batcher.draw(AssetLoader.background, 0, 0);
        batcher.draw(AssetLoader.ufo, world.getUfo().getPosition().x, world.getUfo().getPosition().y, world.getUfo().getPosition().x, world.getUfo().getPosition().y,
                world.getUfo().getWidth(), world.getUfo().getHeight(),1,1,0);
        batcher.draw(AssetLoader.satellite, world.getSatellite().getPosition().x, world.getSatellite().getPosition().y,
                world.getSatellite().getWidth(), world.getSatellite().getHeight());
		batcher.end();
	}
}
