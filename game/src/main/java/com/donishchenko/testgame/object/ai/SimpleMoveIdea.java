package com.donishchenko.testgame.object.ai;

import com.donishchenko.testgame.config.EngineConstants;
import com.donishchenko.testgame.object.GameObject;
import com.donishchenko.testgame.object.Vector2F;
import com.donishchenko.testgame.object.action.SimpleMoveAction;

public class SimpleMoveIdea extends Idea {

    private int rethinkTimer = 0;
    private int rethinkTrigger = 250 * 1_000_000 / EngineConstants.NANO_PER_TICK;

    public SimpleMoveIdea(GameObject gameObject, int priority) {
        super(gameObject, priority, false);
        init();
    }

    @Override
    public void init() {
        action = new SimpleMoveAction(gameObject);
    }

    @Override
    public boolean think() {
        // if target == null then move simple forward
        if (gameObject.target == null) {
            gameObject.dir = gameObject.physicsModel.getMoveDir();
        }
        // correct the direction by trigger
        else if (rethinkTimer++ == rethinkTrigger) {
            rethinkTimer = 0;
            Vector2F newDir = gameObject.target.pos.copy();
            newDir.sub(gameObject.pos);
            newDir.normalize();
            gameObject.dir = newDir;
        }

        // check animation direction
        action.animation.checkDirection(gameObject.dir.x);

        return true;
    }
}
