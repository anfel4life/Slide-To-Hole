package com.broodproduct.slide.objects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ufo {

    private float rotation;
    private int width;
    private int height;

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private Rectangle boundRectangle;

    public Ufo(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(160, 0);
        boundRectangle = new Rectangle();
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void moveForward(){
        velocity.x = -40;
    }

    public void update(float delta) {
        this.velocity.add(acceleration.cpy().scl(delta));
        this.position.add(velocity.cpy().scl(delta));
    }
}
