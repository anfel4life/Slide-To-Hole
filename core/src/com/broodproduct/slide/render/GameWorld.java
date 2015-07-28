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

import static com.broodproduct.slide.tool.Constants.scale;

public class GameWorld {
    public final float unitWidth; //60 meters
    public final float unitHeight; //33.75 meters
    private Ufo ufo;
    private Body bomb;
    private Satellite satellite;
    private Park leftPark;
    private Park rightPark;
    private World boxWorld = new World(new Vector2(0, 0), true);
    private Body groundBody;
    private List<KeyValue<Vector2>> rays = new ArrayList<KeyValue<Vector2>>();

    public GameWorld(float width, float height) {
        unitWidth = scale(width);
        unitHeight = scale(height);
        this.ufo = new Ufo(0, 0, 100, 87, boxWorld);
        this.satellite = new Satellite(200, 300, 152, 60, boxWorld);
        this.leftPark = new Park(0, 0, 116, 540);
        this.rightPark = new Park((int) (width - 116), 0, 116, 540);
        //ground
        createWall(0, 0, unitWidth, 1);
        //flor
        createWall(0, unitHeight, unitWidth, 1);
        //left
        createWall(0, 0, 1, unitHeight);
        //right
        createWall(unitWidth, 0, 1, unitHeight);
        //createJoint();

        createBomb();

        // we also need an invisible zero size ground body
        // to which we can connect the mouse joint
        groundBody = boxWorld.createBody(new BodyDef());
    }

    private Vector2 currentRay = null;

    private void ray(final Vector2 center, final boolean blast) {
        final int numRays = 12;
        final float blastPower = 20000;
        rays.clear();
        RayCastCallback callback = new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                currentRay.set(point);
                if(blast)
                    applyBlastImpulse(fixture.getBody(), center, point, (blastPower / (float) numRays));
                return fraction;
            }
        };
        Vector2 blastRadius = new Vector2(1000, 1000);
        for (int i = 0; i < numRays; i++) {
            float angle = (360 / numRays) * i;
            //from degrees to radians
            angle = (float) Math.toRadians(angle);
            float x = (float) Math.cos(angle);
            float y = (float) Math.sin(angle);
            Vector2 rayDir = new Vector2(x, y);
            Vector2 rayEnd = new Vector2(rayDir);
            rayEnd.scl(blastRadius).add(center);
            rays.add(new KeyValue<Vector2>(center, rayEnd));
            currentRay = rayEnd;
            boxWorld.rayCast(callback, center, rayEnd);
        }
    }

    private void particleExplose(final Vector2 center){
        final int numRays = 4;
        final float blastPower = 100;
        for (int i = 0; i < numRays; i++) {
            float angle = (360 / numRays) * i;
            //from degrees to radians
            angle = (float) Math.toRadians(angle);
            float x = (float) Math.cos(angle);
            float y = (float) Math.sin(angle);
            Vector2 rayDir = new Vector2(x, y);

            BodyDef bd = new BodyDef();
            bd.type = BodyDef.BodyType.DynamicBody;
            bd.fixedRotation = true; // rotation not necessary
            bd.bullet = true; // prevent tunneling at high speed
//            bd.linearDamping = 0.001f; // drag due to moving through air
            bd.gravityScale = 0; // ignore gravity
            bd.position.set(center.cpy().add(100,100)); // start at blast center
            bd.linearVelocity.set(rayDir.cpy().scl(blastPower));

            Body body = boxWorld.createBody(bd);

            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(0.2f); // very small

            FixtureDef fd = new FixtureDef();
            fd.shape = circleShape;
            fd.density = 1f; // very high - shared across all particles
            fd.friction = 0; // friction not necessary
            fd.restitution = 0.99f; // high restitution to reflect off obstacles
            fd.filter.groupIndex = -1; // particles should not collide with each other
            body.createFixture(fd);
        }
    }

    private void applyBlastImpulse(Body body, Vector2 blastCenter, Vector2 applyPoint, float blastPower) {
        Vector2 blastDir = applyPoint.cpy().sub(blastCenter);
        float distance = blastDir.cpy().nor().len();
        //ignore bodies exactly at the blast point - blast direction is undefined
        if (distance == 0)
            return;
        float invDistance = 1 / distance;
        float impulseMag = blastPower * invDistance * invDistance;
//        body.applyLinearImpulse(impulseMag * blastDir, applyPoint);
        body.applyLinearImpulse(blastDir.scl(impulseMag), applyPoint, true);
    }

    private void createBomb() {
        BodyDef bd = new BodyDef();
        bd.position.set(scale(300), scale(200));
        bd.type = BodyDef.BodyType.StaticBody;

        CircleShape shape = new CircleShape();
        shape.setRadius(1f);

        FixtureDef fd = new FixtureDef();
        fd.density = 0.4f;
        fd.friction = 0.5f;
        fd.restitution = 0.5f;
        fd.shape = shape;

        bomb = boxWorld.createBody(bd);
        bomb.createFixture(fd).setUserData("bomb");

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
        boxWorld.createBody(bd).createFixture(fd).setUserData("wall");

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
        ray(bomb.getWorldCenter(), false);
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

    public void blastIt() {
        particleExplose(bomb.getWorldCenter());
    }

    public List<KeyValue<Vector2>> getRays() {
        return rays;
    }
}
