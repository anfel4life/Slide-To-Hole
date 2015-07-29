package com.broodproduct.slide.render;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.utils.Array;
import com.broodproduct.slide.objects.Destroyer;
import com.broodproduct.slide.objects.Park;
import com.broodproduct.slide.objects.Satellite;
import com.broodproduct.slide.objects.Ufo;
import com.broodproduct.slide.box.BombContactListener;
import com.broodproduct.slide.tool.Constants;
import com.broodproduct.slide.tool.KeyValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameWorld {
    public final float unitWidth; //60 meters
    public final float unitHeight; //33.75 meters
    private Destroyer destroyer;
    private Ufo ufo;
    private Body bomb;
    private Satellite satellite;
    private Park leftPark;
    private Park rightPark;
    private World boxWorld = new World(new Vector2(0, 0), true);
    private Body groundBody;
    private List<Body> particles = new ArrayList<Body>();
    private List<Body> trash = new ArrayList<Body>();
    private List<KeyValue<Vector2>> rays = new ArrayList<KeyValue<Vector2>>();
    private Random r = new Random();
    private List<Body> ufoParts = new ArrayList<Body>();

    public GameWorld(float width, float height) {
        unitWidth = Constants.scaleToUnit(width);
        unitHeight = Constants.scaleToUnit(height);
        this.ufo = new Ufo(100, 100, 100, 87, boxWorld);
        this.satellite = new Satellite(200, 300, 152, 60, boxWorld);
        this.leftPark = new Park(0, 0, 116, 540);
        this.rightPark = new Park((int) (width - 116), 0, 116, 540);
        this.destroyer = new Destroyer(1,1,22,22,boxWorld);
        //ground
        createWall(0, 0, unitWidth, 0.2f);
        //flor
        createWall(0, unitHeight, unitWidth, 0.2f);
        //left
        createWall(0, 0, 0.2f, unitHeight);

//        createWall(10, 20, 0.2f, 7);
        //right
        createWall(unitWidth, 0, 0.2f, unitHeight);

//        createWall(unitWidth-10, 20, 0.2f, 7);
        //createJoint();

        createBomb();

        //createTrash();

        boxWorld.setContactListener(new BombContactListener());

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
                if (blast)
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

    private void particleExplose() {
        for (Body particle : particles) {
            boxWorld.destroyBody(particle);
        }
        particles.clear();
        final int numRays = 100;
        final float blastPower = 2000;
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
            bd.linearDamping = 7f; // drag due to moving through air
            bd.gravityScale = 0; // ignore gravity
            bd.position.set(20, 20); // start at blast center
            bd.linearVelocity.set(rayDir.cpy().scl(blastPower));

            Body body = boxWorld.createBody(bd);

            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(0.02f); // very small

            FixtureDef fd = new FixtureDef();
            fd.shape = circleShape;
            fd.density = 60f; // very high - shared across all particles
            fd.friction = 0; // friction not necessary
            fd.restitution = 0.99f; // high restitution to reflect off obstacles
            fd.filter.groupIndex = -1; // particles should not collide with each other
            body.createFixture(fd);
            body.setUserData("splinter");
            circleShape.dispose();
            particles.add(body);
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
        bd.position.set(Constants.scaleToUnit(300), Constants.scaleToUnit(200)); //18.75 : 12.5
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

    private void createTrash() {
        int items = 1;

        for (int i = 0; i < items; i++) {
            BodyDef bd = new BodyDef();
            float x = unitWidth * r.nextFloat();
            float y = unitHeight * r.nextFloat();
            float radius = 2 * r.nextFloat();
            bd.position.set(x, y);
            bd.type = BodyDef.BodyType.DynamicBody;

            Shape shape = getShape(radius, r.nextFloat() > 0.5f);

            FixtureDef fd = new FixtureDef();
            fd.density = 0.4f;
            fd.friction = 0.5f;
            fd.restitution = 0.5f;
            fd.shape = shape;

            Body body = boxWorld.createBody(bd);
            body.createFixture(fd).setUserData("Trash " + i);
            shape.dispose();
            trash.add(body);
        }
    }

    private void splitUfo() {
        Array<Fixture> fixtureList = ufo.getBody().getFixtureList();
        int size = fixtureList.size;
        float angle = (float) Math.toRadians(360 / size);
        for (int i = 0; i < size; i++) {
            BodyDef bd = new BodyDef();
            float x = ufo.getBody().getPosition().x;
            float y = ufo.getBody().getPosition().y;
            bd.position.set(x, y);
            bd.type = BodyDef.BodyType.DynamicBody;
            Body body = boxWorld.createBody(bd);

            Fixture fixture1 = fixtureList.first();
            body.createFixture(fixture1.getShape(), fixture1.getDensity());

            float xPart = (float) Math.cos(angle * i);
            float yPart = (float) Math.sin(angle * i);
            int splitPower = 10;
            Vector2 velocity = new Vector2(xPart, yPart).scl(splitPower);

            ufo.getBody().destroyFixture(fixture1);
//            body.setLinearVelocity(velocity);

            Body part = boxWorld.createBody(bd);
            ufoParts.add(part);
        }
    }

    private Shape getShape(float radius, boolean isCube) {
        if (isCube) {
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(radius, radius);
            return shape;
        } else {
            CircleShape shape = new CircleShape();
            shape.setRadius(radius);
            return shape;
        }
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
        //ray(bomb.getWorldCenter(), false);
        this.ufo.update();
        this.satellite.update();
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
        particleExplose();
    }

    public List<KeyValue<Vector2>> getRays() {
        return rays;
    }

    public void restore() {
        ufo.restore();
        satellite.restore();
    }

    public Destroyer getDestroyer() {
        return destroyer;
    }
}
