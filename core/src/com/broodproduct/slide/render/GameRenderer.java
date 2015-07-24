package com.broodproduct.slide.render;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.broodproduct.slide.tool.AssetLoader;


public class GameRenderer {

	private OrthographicCamera cam;
	private SpriteBatch batcher;
    private BitmapFont font;
    private GameWorld world;

	public GameRenderer(GameWorld world) {
		this.world = world;
        cam = new OrthographicCamera();
		cam.setToOrtho(true);
		batcher = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
//		batcher.setProjectionMatrix(cam.combined);

	}

	public void render(float delta, float runTime) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batcher.begin();
        batcher.draw(AssetLoader.background, 0, 0);
        //ufo
        batcher.draw(AssetLoader.ufo, world.getUfo().getPosition().x, world.getUfo().getPosition().y,
                world.getUfo().getWidth(), world.getUfo().getHeight());
        //satellite
        batcher.draw(AssetLoader.satellite, world.getSatellite().getPosition().x, world.getSatellite().getPosition().y,
                world.getSatellite().getWidth(), world.getSatellite().getHeight());
        //left park
        batcher.draw(AssetLoader.leftPark, world.getLeftPark().getPosition().x, world.getLeftPark().getPosition().y);
        batcher.draw(AssetLoader.rightPark, world.getRightPark().getPosition().x, world.getRightPark().getPosition().y);
//        batcher.draw(AssetLoader.rightPark, world.getRightPark().getPosition().x, world.getRightPark().getPosition().y,
//                world.getRightPark().getWidth(), world.getRightPark().getHeight());
		font.draw(batcher, "velocity: " + world.getUfo().getVelocity(), 10, 500);
		font.draw(batcher, "position: " + world.getUfo().getPosition(), 10, 520);
		batcher.end();

	}
}
