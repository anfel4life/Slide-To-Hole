package com.broodproduct.slide.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.broodproduct.slide.objects.Park;
import com.broodproduct.slide.objects.Satellite;
import com.broodproduct.slide.objects.Ufo;
import com.broodproduct.slide.test.BodyEditorLoader;

public class GameWorld {
    private Ufo ufo;
    private Satellite satellite;
    private Park leftPark;
    private Park rightPark;
    private World boxWolrd = new World(new Vector2(0, -10), true);
    private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    private Body ufoBody;
    private Vector2 ufoOrigin;

    public GameWorld(float width, float height) {
        this.ufo = new Ufo(0, 0, 100, 87);
        this.satellite = new Satellite(100, 50, 152, 60);
        this.leftPark = new Park(0, 0, 116, 540);
        this.rightPark = new Park((int) (width - 116), 0, 116, 540);
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

        boxWolrd.createBody(bd).createFixture(fd);

        shape.dispose();
    }

    private void createUfo() {
        // 0. Create a loader for the file saved from the editor.
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("box/slide"));

        // 1. Create a BodyDef, as usual.
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;

        // 2. Create a FixtureDef, as usual.
        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 0.5f;
        fd.restitution = 0.3f;

        // 3. Create a Body, as usual.
        ufoBody = boxWolrd.createBody(bd);

        // 4. Create the body fixture automatically by using the loader.
        loader.attachFixture(ufoBody, "test01", fd, ufo.getWidth());
        ufoOrigin = loader.getOrigin("test01", ufo.getWidth()).cpy();
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
