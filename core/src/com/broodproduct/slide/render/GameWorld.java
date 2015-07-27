package com.broodproduct.slide.render;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.broodproduct.slide.objects.Park;
import com.broodproduct.slide.objects.Satellite;
import com.broodproduct.slide.objects.Ufo;

public class GameWorld {
    private Ufo ufo;
    private Satellite satellite;
    private Park leftPark;
    private Park rightPark;
    private World boxWorld = new World(new Vector2(0, 0), true);

    public GameWorld(float width, float height) {
        this.ufo = new Ufo(0, 0, 100, 87, boxWorld);
        this.satellite = new Satellite(100, 50, 152, 60, boxWorld);
        this.leftPark = new Park(0, 0, 116, 540);
        this.rightPark = new Park((int) (width - 116), 0, 116, 540);
        createGround();
    }

    private void createGround() {
        BodyDef bd = new BodyDef();
        bd.position.set(0, 0);
        bd.type = BodyDef.BodyType.StaticBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10, 1);

        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 0.5f;
        fd.restitution = 0.5f;
        fd.shape = shape;

        boxWorld.createBody(bd).createFixture(fd);

        shape.dispose();
    }

    public Ufo getUfo() {
        return ufo;
    }

    public Satellite getSatellite() {
        return satellite;
    }

    public void update(float delta) {
        boxWorld.step(1/60f, 10, 10);
        ufo.update(delta);
    }

    public Park getLeftPark() {
        return leftPark;
    }

    public Park getRightPark() {
        return rightPark;
    }

    public void dispose() {
        boxWorld.dispose();
    }

    public World getBoxWorld() {
        return boxWorld;
    }
}
