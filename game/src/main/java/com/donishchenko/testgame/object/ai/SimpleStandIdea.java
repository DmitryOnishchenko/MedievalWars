package com.donishchenko.testgame.object.ai;

import com.donishchenko.testgame.object.GameObject;
import com.donishchenko.testgame.object.action.SimpleStandAction;

public class SimpleStandIdea extends Idea {

    public SimpleStandIdea(GameObject gameObject, int priority) {
        super(gameObject, priority, false);
        init();
    }

    @Override
    public void init() {
        action = new SimpleStandAction(gameObject);
    }

    @Override
    public boolean think() {
        // check animation direction
        action.animation.checkDirection(gameObject.dir.x);

        return true;
    }
}
