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

	public GameRenderer(GameWorld world, float width, float height) {
		this.world = world;
        cam = new OrthographicCamera();
		cam.setToOrtho(false, width, height);
		batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
        font = new BitmapFont();
        font.setColor(Color.RED);
	}

	public void render(float delta, float runTime) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batcher.begin();
        batcher.draw(AssetLoader.background, 0, 0);
        //ufo
        batcher.draw(AssetLoader.ufo, world.getUfo().getBody().getPosition().x, world.getUfo().getBody().getPosition().y);
        //satellite
        batcher.draw(AssetLoader.satellite, world.getSatellite().getBody().getPosition().x, world.getSatellite().getBody().getPosition().y,
                world.getSatellite().getWidth(), world.getSatellite().getHeight());
        //left park
        batcher.draw(AssetLoader.leftPark, world.getLeftPark().getPosition().x, world.getLeftPark().getPosition().y);
        batcher.draw(AssetLoader.rightPark, world.getRightPark().getPosition().x, world.getRightPark().getPosition().y);

		font.draw(batcher, "position: " + world.getUfo().getBody().getPosition(), 10, 520);
		batcher.end();

	}

    public OrthographicCamera getCam() {
        return cam;
    }

    public void dispose() {
        batcher.dispose();
        font.dispose();
    }
}
