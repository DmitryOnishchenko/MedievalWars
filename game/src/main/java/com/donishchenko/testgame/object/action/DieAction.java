package com.donishchenko.testgame.object.action;

import com.donishchenko.testgame.config.EngineConstants;
import com.donishchenko.testgame.object.GameObject;

import java.awt.image.BufferedImage;

public class DieAction extends Action {

    public DieAction(GameObject gameObject) {
        this.gameObject = gameObject;
        init();
    }

    @Override
    public void init() {
        BufferedImage[] leftAnimation = gameObject.graphicsModel.getDieSpritesLeft();
        BufferedImage[] rightAnimation = gameObject.graphicsModel.getDieSpritesRight();
        int rate = gameObject.graphicsModel.getAnimationSpeed() * 1_000_000 / EngineConstants.NANO_PER_TICK;

        animation = new Animation(this);
        animation.setAnimation(rate, leftAnimation, rightAnimation);

        float dirX = gameObject.dir.x;
        if (dirX == 0) {
            dirX = gameObject.physicsModel.getMoveDir().x;
        }
        animation.checkDirection(dirX);
    }

    @Override
    public void execute() {
        // nothing to do
    }

    @Override
    public void animationFinished() {
        // nothing to do
        gameObject.delete = true;
    }
}
