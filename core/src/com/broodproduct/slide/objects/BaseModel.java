package com.broodproduct.slide.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Anfel
 * Brood Product Ukraine
 */
public abstract class BaseModel {
    protected Body body;
    protected Vector2 origin;
    protected World boxWorld;
    protected float width;
    protected float height;

    public BaseModel(float width, float height, World boxWorld) {
        this.width = width;
        this.height = height;
        this.boxWorld = boxWorld;
        this.body = initBody();
        this.origin = initOrigin();
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
}
