package com.broodproduct.slide.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.broodproduct.slide.tool.Constants;
import com.broodproduct.slide.tool.Utils;

public class Ufo extends BaseModel {
    public Ufo(int x, int y, int width, int height, World boxWorld) {
        super(x, y, width, height, boxWorld);
    }

    protected Body initBody() {
        BodyDef bd = new BodyDef();
        bd.position.set(Constants.scaleToUnit(origX), Constants.scaleToUnit(origY));
        bd.type = BodyDef.BodyType.DynamicBody;
        return boxWorld.createBody(bd);
    }

    protected Vector2 initOrigin() {
        FixtureDef fd = new FixtureDef();
        fd.density = 0.2f;
        fd.friction = 0.4f;
        fd.restitution = 0.3f;

        Utils.getBoxLoader().attachFixture(body, "Ufo", fd, Constants.scaleToUnit(width));
        return Utils.getBoxLoader().getOrigin("Ufo", Constants.scaleToUnit(width)).cpy();
    }
}
