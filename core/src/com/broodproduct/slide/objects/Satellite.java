package com.broodproduct.slide.objects;

import com.badlogic.gdx.math.Vector2;

public class Satellite {

    private int width;
    private int height;

    private Vector2 position;

    public Satellite(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        this.position = new Vector2(x, y);
    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
