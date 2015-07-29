package com.broodproduct.slide.box;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.broodproduct.slide.objects.BaseModel;


public class BombContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();
        Object obj1 =  bodyA.getUserData();
        Object obj2 =  bodyB.getUserData();
        if(checkAndBlast(obj1) | checkAndBlast(obj2)){
            //if our models under the destraction then we have to calc power of crash
            //let it be p = m * v
            Vector2 velocity1 = bodyA.getLinearVelocity();
            Vector2 velocity2 = bodyB.getLinearVelocity();
            WorldManifold worldManifold = contact.getWorldManifold();
            velocity1.cpy();
        }

    }

    private boolean checkAndBlast(Object obj) {
        if(obj != null && obj instanceof BaseModel){
            BaseModel gameObj = (BaseModel)obj;
            gameObj.needBlast();
            return true;
        }
        return false;
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
