package com.broodproduct.slide.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.broodproduct.slide.tool.Constants;
import com.broodproduct.slide.tool.Utils;

public class Satellite extends BaseModel {

    public Satellite(int x, int y, int width, int height, World boxWorld) {
        super(x, y, width, height, boxWorld);
    }

    @Override
    protected Body initBody() {
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(Constants.scaleToUnit(origX), Constants.scaleToUnit(origY));
        return boxWorld.createBody(bd);
    }

    @Override
    protected Vector2 initOrigin() {
        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 0.5f;
        fd.restitution = 0.3f;
        Utils.getBoxLoader().attachFixture(body, "satelite", fd, Constants.scaleToUnit(width));
        return Utils.getBoxLoader().getOrigin("satelite", Constants.scaleToUnit(width)).cpy();
    }
}
