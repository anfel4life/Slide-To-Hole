package com.broodproduct.slide.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.broodproduct.slide.tool.Utils;

public class Ufo extends BaseModel {
    private Vector2 velocity;
    private Vector2 acceleration;

    public Ufo(int x, int y, int width, int height, World boxWorld) {
        super(width, height, boxWorld);
        this.width = width;
        this.height = height;
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(16, 0);
        initBody();
    }

    protected Body initBody() {
        BodyDef bd = new BodyDef();
        bd.position.set(300, 200);
        bd.type = BodyDef.BodyType.DynamicBody;
        return boxWorld.createBody(bd);
    }

    protected Vector2 initOrigin() {
        FixtureDef fd = new FixtureDef();
        fd.density = 0.5f;
        fd.friction = 0.4f;
        fd.restitution = 0.3f;

        Utils.getBoxLoader().attachFixture(body, "Ufo", fd, width);
        return Utils.getBoxLoader().getOrigin("Ufo", width).cpy();
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void moveForward() {
        this.body.applyForce(60000.0f, 0.0f, body.getPosition().x, body.getPosition().y, true);
    }

    public void update(float delta) {
        this.velocity.add(acceleration.cpy().scl(delta));
    }
}
