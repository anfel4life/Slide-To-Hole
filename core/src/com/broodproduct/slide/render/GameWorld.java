package com.broodproduct.slide.render;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.broodproduct.slide.objects.Park;
import com.broodproduct.slide.objects.Satellite;
import com.broodproduct.slide.objects.Ufo;

public class GameWorld {
    private Ufo ufo;
    private Satellite satellite;
    private Park leftPark;
    private Park rightPark;
    private World boxWolrd = new World(new Vector2(0, -10), true);
    private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    public GameWorld(float width, float height) {
        this.ufo = new Ufo(0, 0, 100, 87);
        this.satellite = new Satellite(100, 50, 152, 60);
        this.leftPark = new Park(0, 0, 116, 540);
        this.rightPark = new Park((int) (width - 116), 0, 116, 540);
    }

    public Ufo getUfo() {
        return ufo;
    }

    public Satellite getSatellite() {
        return satellite;
    }

    public void update(float delta) {
        ufo.update(delta);
    }

    public Park getLeftPark() {
        return leftPark;
    }

    public Park getRightPark() {
        return rightPark;
    }
}
