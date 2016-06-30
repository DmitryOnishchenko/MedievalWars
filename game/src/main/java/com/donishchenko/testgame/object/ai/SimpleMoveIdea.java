package com.donishchenko.testgame.object.ai;

import com.donishchenko.testgame.config.EngineConstants;
import com.donishchenko.testgame.gamestate.battle.Grid;
import com.donishchenko.testgame.gamestate.battle.PathPoint;
import com.donishchenko.testgame.object.GameObject;
import com.donishchenko.testgame.object.Vector2F;
import com.donishchenko.testgame.object.action.SimpleMoveAction;

import java.util.List;

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
        // if target == null then checkPosition simple forward
//        GameObject target = gameObject.target;
//        if (target == null || !target.isAlive()) {
//            gameObject.target = null;
//            gameObject.dir = gameObject.physicsModel.getMoveDir();
//        }

        // correct the direction by trigger
//        else if (rethinkTimer++ == rethinkTrigger) {
//            rethinkTimer = 0;
//            Vector2F newDir = target.pos.copy();
//            newDir.sub(gameObject.pos);
//            newDir.normalize();
//            gameObject.dir = newDir;
//        }

//        if (gameObject.target == null || !gameObject.target.isAlive()) {
//            gameObject.target = null;
//            gameObject.dir = new Vector2F();
//            return false;
//        }

        // if target == null then checkPosition and simple forward
        GameObject target = gameObject.target;
        if (target == null || !target.isAlive()) {
            gameObject.target = null;

        }

        // check animation direction
        action.animation.checkDirection(gameObject.dir.x);

        return false;
    }
}
