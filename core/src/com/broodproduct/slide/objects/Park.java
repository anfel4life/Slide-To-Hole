package com.broodproduct.slide.objects;


import com.badlogic.gdx.math.Vector2;

public class Park {

    private int width;
    private int height;

    private Vector2 position;

    public Park(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        this.position = new Vector2(x, y);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Vector2 getPosition() {
        return position;
    }
}
