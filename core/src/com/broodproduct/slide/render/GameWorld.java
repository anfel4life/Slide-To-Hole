package com.broodproduct.slide.render;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.broodproduct.slide.objects.Park;
import com.broodproduct.slide.objects.Satellite;
import com.broodproduct.slide.objects.Ufo;
import com.broodproduct.slide.tool.KeyValue;

import java.util.ArrayList;
import java.util.List;

public class GameWorld {
    private final float width;
    private final float height;
    private Ufo ufo;
    private Body bomb;
    private Satellite satellite;
    private Park leftPark;
    private Park rightPark;
    private World boxWorld = new World(new Vector2(0, 0), true);
    private Body groundBody;
    private List<KeyValue<Vector2>> rays = new ArrayList<KeyValue<Vector2>>();

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

        createBomb();

        // we also need an invisible zero size ground body
        // to which we can connect the mouse joint
        groundBody = boxWorld.createBody(new BodyDef());
    }

    private void ray(Vector2 center) {
        int numRays = 1;
        rays.clear();
        RayCastCallback callback = new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                rays.get(0).setValue(point);
                return 0;
            }
        };
        Vector2 blastRadius = new Vector2(100, 100);
        for (int i = 0; i < numRays; i++) {
            float angle = (360 / numRays) * i;
            //from degrees to radians
            angle = (float) Math.toRadians(angle);
            float x = (float) Math.cos(angle);
            float y = (float) Math.sin(angle);
            Vector2 rayDir = new Vector2(x, y);
            Vector2 rayEnd = new Vector2(center);
            rayEnd.add(blastRadius).scl(rayDir);
            rays.add(new KeyValue<Vector2>(center, rayEnd));
            boxWorld.rayCast(callback, center, rayEnd);
        }
    }

    private void createBomb() {
        BodyDef bd = new BodyDef();
        bd.position.set(300, 200);
        bd.type = BodyDef.BodyType.DynamicBody;

        CircleShape shape = new CircleShape();
        shape.setRadius(20);

        FixtureDef fd = new FixtureDef();
        fd.density = 0.4f;
        fd.friction = 0.5f;
        fd.restitution = 0.5f;
        fd.shape = shape;

        bomb = boxWorld.createBody(bd);
        bomb.createFixture(fd);

        shape.dispose();
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

    private void createJoint() {
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
        boxWorld.step(1 / 60f, 10, 10);
        ray(bomb.getWorldCenter());
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

    public Body getBomb() {
        return bomb;
    }

    public void blastIt(Vector2 center) {
        ray(center);
    }

    public List<KeyValue<Vector2>> getRays() {
        return rays;
    }
}
