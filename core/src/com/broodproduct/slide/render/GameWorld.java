package com.broodproduct.slide.render;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.broodproduct.slide.objects.Park;
import com.broodproduct.slide.objects.Satellite;
import com.broodproduct.slide.objects.Ufo;

public class GameWorld {
    private final float width;
    private final float height;
    private Ufo ufo;
    private Satellite satellite;
    private Park leftPark;
    private Park rightPark;
    private World boxWorld = new World(new Vector2(0, 0), true);
    private Body groundBody;

    public GameWorld(float width, float height) {
        this.width = width;
        this.height = height;
        this.ufo = new Ufo(0, 0, 100, 87, boxWorld);
        this.satellite = new Satellite(100, 50, 152, 60, boxWorld);
        this.leftPark = new Park(0, 0, 116, 540);
        this.rightPark = new Park((int) (width - 116), 0, 116, 540);
        //ground
        createWall(0, 0, width, 1);
        //flor
        createWall(0, height, width, 1);
        //left
        createWall(0, 0, 1, height);
        //right
        createWall(width, 0, 1, height);
        //createJoint();

        // we also need an invisible zero size ground body
        // to which we can connect the mouse joint
        groundBody = boxWorld.createBody(new BodyDef());
    }

    private void createWall(float x, float y, float width, float height) {
        BodyDef bd = new BodyDef();
        bd.position.set(x, y);
        bd.type = BodyDef.BodyType.StaticBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);

        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 0.5f;
        fd.restitution = 0.5f;
        fd.shape = shape;

        boxWorld.createBody(bd).createFixture(fd);

        shape.dispose();
    }

    private void createJoint(){
        RopeJointDef ropeJointDef = new RopeJointDef();
        ropeJointDef.bodyA = ufo.getBody();
        ropeJointDef.bodyB = satellite.getBody();
        ropeJointDef.maxLength = 4;
        boxWorld.createJoint(ropeJointDef);
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

    public Body getGroundBody() {
        return groundBody;
    }
}
