package com.donishchenko.testgame.object.action;

import com.donishchenko.testgame.config.EngineConstants;
import com.donishchenko.testgame.object.GameObject;

import java.awt.image.BufferedImage;

public class SimpleStandAction extends Action {

    public SimpleStandAction(GameObject gameObject) {
        this.gameObject = gameObject;
        init();
    }

    @Override
    public void init() {
        BufferedImage[] leftAnimation = gameObject.graphicsModel.getStandSpritesLeft();
        BufferedImage[] rightAnimation = gameObject.graphicsModel.getStandSpritesRight();
        int rate = gameObject.graphicsModel.getAnimationSpeed() * 1_000_000 / EngineConstants.NANO_PER_TICK;

        animation = new Animation(this);
        animation.setAnimation(rate, leftAnimation, rightAnimation);
    }

    @Override
    public void execute() {

    }

    @Override
    public void animationFinished() {

    }
}
