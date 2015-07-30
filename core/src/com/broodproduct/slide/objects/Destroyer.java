package com.broodproduct.slide.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Anfel
 * Brood Product Ukraine
 */
public class Destroyer extends BaseModel {
    public Destroyer(float x, float y, float width, float height, World boxWorld) {
        super(x, y, width, height, boxWorld);
//        this.body.setFixedRotation(true);
        minBlastDmg = 10000;
    }

    @Override
    protected Body initBody() {
        BodyDef bd = new BodyDef();
        bd.position.set(origX, origY);
        bd.type = BodyDef.BodyType.DynamicBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1, 1);

        FixtureDef fd = new FixtureDef();
        fd.density = 0.4f;
        fd.friction = 0.5f;
        fd.restitution = 0.5f;
        fd.shape = shape;

        Body body = boxWorld.createBody(bd);
        body.createFixture(fd).setUserData("Destroyer");
        shape.dispose();
        return body;
    }

    @Override
    protected Vector2 initOrigin() {
        return new Vector2(origX, origY);
    }

    @Override
    public void update() {
        super.update();
    }

    public void rotate(boolean forward) {
        int val = forward ? 5 : -5;
        this.body.applyAngularImpulse(val, true);
    }

    public void switchSide(boolean forward){
        int x = forward ? 50 : -50;
        this.body.applyLinearImpulse(new Vector2(x, 0), body.getWorldCenter(), true);
    }
}
