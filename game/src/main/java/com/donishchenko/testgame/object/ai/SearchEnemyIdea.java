package com.donishchenko.testgame.object.ai;

import com.donishchenko.testgame.config.EngineConstants;
import com.donishchenko.testgame.engine.Executor;
import com.donishchenko.testgame.object.GameObject;

public class SearchEnemyIdea extends Idea {

    private int rethinkTimer = 0;
    private int rethinkTrigger = 1000 * 1_000_000 / EngineConstants.NANO_PER_TICK;

    public SearchEnemyIdea(GameObject gameObject, int priority) {
        super(gameObject, priority);
    }

    @Override
    public void init() {}

    @Override
    public boolean think() {
        // if target is null - search target
        if (rethinkTimer % 10 == 0 && gameObject.target == null) {
            Executor.submitTask(new SearchEnemyTask(gameObject));
        }
        // search another target by trigger
        if (++rethinkTimer > rethinkTrigger) {
            rethinkTimer = 0;
            Executor.submitTask(new SearchEnemyTask(gameObject));
        }

        // this is service
        return false;
    }
}
