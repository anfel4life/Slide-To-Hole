package com.broodproduct.slide.render;

import com.broodproduct.slide.objects.Satellite;
import com.broodproduct.slide.objects.Ufo;

public class GameWorld {
    private Ufo ufo;
    private Satellite satellite;

    public GameWorld() {
        this.ufo = new Ufo(0, 0, 100, 87);
        this.satellite = new Satellite(100, 50, 152, 60);
    }

    public Ufo getUfo() {
        return ufo;
    }

    public Satellite getSatellite() {
        return satellite;
    }

    public void update(float delta) {
        ufo.update(delta);
    }
}
