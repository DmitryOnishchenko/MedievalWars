package com.donishchenko.testgame.object.action;

import com.donishchenko.testgame.config.EngineConstants;
import com.donishchenko.testgame.object.GameObject;

import java.awt.image.BufferedImage;

public class SimpleAttackAction extends Action {

    public SimpleAttackAction(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    @Override
    public void init() {
        BufferedImage[] leftAnimation = gameObject.graphicsModel.getFightSpritesLeft();
        BufferedImage[] rightAnimation = gameObject.graphicsModel.getFightSpritesRight();
        int rate = gameObject.graphicsModel.getAnimationSpeed() * 1_000_000 / EngineConstants.NANO_PER_TICK;

        animation = new Animation(this);
        animation.setAnimation(rate, leftAnimation, rightAnimation);
    }

    @Override
    public void execute() {
        GameObject target = gameObject.target;
        if (target != null) {
            if (!target.isAlive()) {
                gameObject.target = null;
            } else {
                gameObject.target.takeDamage(gameObject.damage);
            }
        }
    }
}
