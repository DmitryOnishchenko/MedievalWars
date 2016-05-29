package com.donishchenko.testgame.object.action;

import com.donishchenko.testgame.object.GameObject;

import java.awt.image.BufferedImage;

public abstract class Action {

    public GameObject gameObject;
    public Animation animation;

    public abstract void init();

    public abstract void execute();

    public void updateAnimation() {
        animation.next();
    }

    public BufferedImage getCurrentFrame() {
        return animation.getCurrentFrame();
    }

    public abstract void animationFinished();

}
