package com.broodproduct.slide.render;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.broodproduct.slide.tool.AssetLoader;
import com.broodproduct.slide.tool.KeyValue;


public class GameRenderer {

    private OrthographicCamera cam, debugCam;
    private SpriteBatch batcher;
    private BitmapFont font;
    private GameWorld world;
    private ShapeRenderer sr = new ShapeRenderer();


    public GameRenderer(GameWorld world, float width, float height) {
        this.world = world;
        cam = new OrthographicCamera(width, height);
        cam.setToOrtho(false);
        debugCam = new OrthographicCamera(world.unitWidth, world.unitHeight);
        debugCam.setToOrtho(false, world.unitWidth, world.unitHeight);

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
        //batcher.draw(AssetLoader.ufo, world.getUfo().getBody().getPosition().x, world.getUfo().getBody().getPosition().y);
        //satellite
        //batcher.draw(AssetLoader.satellite, world.getSatellite().getBody().getPosition().x, world.getSatellite().getBody().getPosition().y,
        //      world.getSatellite().getWidth(), world.getSatellite().getHeight());
        //left park
        batcher.draw(AssetLoader.leftPark, world.getLeftPark().getPosition().x, world.getLeftPark().getPosition().y);
        batcher.draw(AssetLoader.rightPark, world.getRightPark().getPosition().x, world.getRightPark().getPosition().y);
        font.draw(batcher, "fps: " + Gdx.graphics.getFramesPerSecond(), 10, 520);
        font.draw(batcher, "ufo hitpoints: " + world.getUfo().getHitPoints(), 10, 500);
        font.draw(batcher, "satellite hitpoints: " + world.getSatellite().getHitPoints(), 10, 480);
        batcher.end();

        sr.setProjectionMatrix(cam.combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        for (KeyValue<Vector2> ray : world.getRays()) {
            sr.line(ray.getKey(), ray.getValue());
        }
        sr.end();

    }

    public OrthographicCamera getCam() {
        return cam;
    }

    public OrthographicCamera getDebugCam() {
        return debugCam;
    }

    public void dispose() {
        batcher.dispose();
        font.dispose();
    }
}
