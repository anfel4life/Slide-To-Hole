package com.broodproduct.slide.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anfel
 * Brood Product Ukraine
 */
public abstract class BaseModel {
    protected float origX, origY;
    protected Body body;
    protected Vector2 origin;
    protected World boxWorld;
    protected float width;
    protected float height;
    protected List<Body> brokenParts;
    protected boolean needBlastFlag;
    protected float hitPoints;
    private float blastDmg;

    public BaseModel(float x, float y, float width, float height, World boxWorld) {
        this.width = width;
        this.height = height;
        this.origX = x;
        this.origY = y;
        this.boxWorld = boxWorld;
        this.brokenParts = new ArrayList<Body>();
        this.body = initBody();
        this.origin = initOrigin();
        this.body.setUserData(this);
        this.hitPoints = 10;
    }

    protected abstract Body initBody();

    protected abstract Vector2 initOrigin();

    public Body getBody() {
        return body;
    }

    public Vector2 getOrigin() {
        return origin;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void needBlast(float blastDmg){
        this.needBlastFlag = true;
        this.blastDmg = blastDmg;
    }

    protected void blast() {
        Array<Fixture> fixtureList = body.getFixtureList();
        int size = fixtureList.size;
        float angle = (float) Math.toRadians(360 / size);
        for(int i = 0; i < size; i++){
            BodyDef bd = new BodyDef();
            float x = body.getPosition().x;
            float y = body.getPosition().y;
            bd.position.set(x, y);
            bd.type = BodyDef.BodyType.DynamicBody;
            Body partBody = boxWorld.createBody(bd);

            Fixture fixture1 = fixtureList.first();
            partBody.createFixture(fixture1.getShape(), fixture1.getDensity());

            float xPart = (float) Math.cos(angle*i);
            float yPart = (float) Math.sin(angle * i);
            int splitPower = 10;
            Vector2 velocity = new Vector2(xPart, yPart).scl(splitPower);

            body.destroyFixture(fixture1);
//            body.setLinearVelocity(velocity);

            brokenParts.add(partBody);
        }
    }

    public void restore() {
        for (Body brokenPart : brokenParts) {
            boxWorld.destroyBody(brokenPart);
        }
        brokenParts.clear();
        boxWorld.destroyBody(this.body);
        this.body = initBody();
        this.origin = initOrigin();
        this.body.setUserData(this);
    }

    public void update() {
        if(needBlastFlag) {
            this.blast();
            this.needBlastFlag = false;
        }
    }
}
