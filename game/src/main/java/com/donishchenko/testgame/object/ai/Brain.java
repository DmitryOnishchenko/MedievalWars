package com.donishchenko.testgame.object.ai;

import com.donishchenko.testgame.object.GameObject;

public class Brain {

    private GameObject gameObject;
    private Idea[] ideas;
    private Idea mainIdea;
    private boolean locked;

    public Brain(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public void init() {
        ideas = new Idea[3];

        /* Move */
        ideas[0] = new SimpleMoveIdea(gameObject, 1);

        /* Search enemy */
        ideas[1] = new SearchEnemyIdea(gameObject, 10);

        /* Attack */
        ideas[2] = new SimpleAttackIdea(gameObject, 5);

        // unlock brain
        unlock();
    }

    public void update() {
        if (!locked && gameObject.isAlive()) {
            for (Idea idea : ideas) {
                if (idea.priority >= mainIdea.priority && idea.think()) {
                    mainIdea = idea;
                }
            }
            // set lock
            locked = mainIdea.needLock;
            // set action
            gameObject.action = mainIdea.action;
        }
    }

    public void lock() {
        locked = true;
    }

    public void unlock() {
        // unlock
        locked = false;
        // Set default main idea
        mainIdea = ideas[0];
        gameObject.action = mainIdea.action;
    }
}
