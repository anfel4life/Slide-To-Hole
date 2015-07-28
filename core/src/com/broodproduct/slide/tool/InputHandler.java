package com.broodproduct.slide.tool;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.broodproduct.slide.objects.Ufo;
import com.broodproduct.slide.render.GameRenderer;
import com.broodproduct.slide.render.GameWorld;

public class InputHandler implements InputProcessor {
    private final GameRenderer gameRenderer;
    private Ufo ufo;
    private GameWorld gameWorld;
    private Body hitBody = null;
    private float scaleFactorX;
    private float scaleFactorY;
    private MouseJoint mouseJoint = null;
    /**
     * another temporary vector
     **/
    private Vector2 target = new Vector2();
    private Vector3 initiatorPoint = new Vector3();

    public InputHandler(GameWorld gameWorld, GameRenderer gameRenderer, float scaleFactorX,
                        float scaleFactorY) {
        this.gameWorld = gameWorld;
        this.gameRenderer = gameRenderer;
        this.ufo = gameWorld.getUfo();
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
    }

    /**
     * we instantiate this vector and the callback here so we don't irritate the GC
     **/

    private QueryCallback callback = new QueryCallback() {
        @Override
        public boolean reportFixture(Fixture fixture) {
            // if the hit point is inside the fixture of the body
            // we report it
            if (fixture.testPoint(initiatorPoint.x, initiatorPoint.y)) {
                hitBody = fixture.getBody();
                return false;
            } else
                return true;
        }
    };

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float x = scaleX(screenX);
        float y = scaleY(screenY);
        // translate the mouse coordinates to world coordinates
        gameRenderer.getCam().unproject(initiatorPoint.set(x, y, 0));
        // ask the world which bodies are within the given
        // bounding box around the mouse pointer
        hitBody = null;
        gameWorld.getBoxWorld().QueryAABB(callback, initiatorPoint.x - 0.0001f,
                initiatorPoint.y - 0.0001f, initiatorPoint.x + 0.0001f, initiatorPoint.y + 0.0001f);

        if (hitBody == gameWorld.getGroundBody())
            hitBody = null;

        // ignore kinematic bodies, they don't work with the mouse joint
        if (hitBody != null && hitBody.getType() == BodyDef.BodyType.KinematicBody)
            return false;

        // if we hit something we create a new mouse joint
        // and attach it to the hit body.
        if (hitBody != null) {
            MouseJointDef def = new MouseJointDef();
            def.bodyA = gameWorld.getGroundBody();
            def.bodyB = hitBody;
            def.collideConnected = true;
            def.target.set(initiatorPoint.x, initiatorPoint.y);
            def.maxForce = 1000.0f * hitBody.getMass();

            mouseJoint = (MouseJoint) gameWorld.getBoxWorld().createJoint(def);
            hitBody.setAwake(true);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // if a mouse joint exists we simply destroy it
        if (mouseJoint != null) {
            gameWorld.getBoxWorld().destroyJoint(mouseJoint);
            mouseJoint = null;
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(Input.Keys.SPACE == keycode){
            gameWorld.blastIt();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        // if a mouse joint exists we simply update
        // the target of the joint based on the new
        // mouse coordinates
        if (mouseJoint != null) {
            gameRenderer.getCam().unproject(initiatorPoint.set(x, y, 0));
            mouseJoint.setTarget(target.set(initiatorPoint.x, initiatorPoint.y));
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private float scaleX(int screenX) {
        return Constants.scale(screenX / scaleFactorX);
    }

    private float scaleY(int screenY) {
        return Constants.scale(screenY / scaleFactorY);
    }

}
