package com.donishchenko.testgame.object.ai;

import com.donishchenko.testgame.object.GameObject;
import com.donishchenko.testgame.object.action.Action;

public abstract class Idea {

    public GameObject gameObject;
    public int priority;
    public boolean needLock;
    public Action action;

    public Idea(GameObject gameObject, int priority, boolean needLock) {
        this.gameObject = gameObject;
        this.priority = priority;
        this.needLock = needLock;
    }

    public abstract void init();

    /**
     * Return true if this idea can become the main. Services returns false
     * @return
     */
    public abstract boolean think();

}
