package com.donishchenko.testgame.object.action;

import com.donishchenko.testgame.config.EngineConstants;
import com.donishchenko.testgame.object.GameObject;

import java.awt.image.BufferedImage;

public class SimpleMoveAction extends Action {

    public SimpleMoveAction(GameObject gameObject) {
        this.gameObject = gameObject;
        init();
    }

    @Override
    public void init() {
        BufferedImage[] leftAnimation = gameObject.graphicsModel.getMoveSpritesLeft();
        BufferedImage[] rightAnimation = gameObject.graphicsModel.getMoveSpritesRight();
        int rate = gameObject.graphicsModel.getAnimationSpeed() * 1_000_000 / EngineConstants.NANO_PER_TICK;

        animation = new Animation(this);
        animation.setAnimation(rate, leftAnimation, rightAnimation);
    }

    @Override
    public void execute() {
        float shiftX                = gameObject.speed * gameObject.dir.x;
        gameObject.pos.x            += shiftX;
        gameObject.hitBox.x         += shiftX;
        gameObject.attackBox.x      += shiftX;
        gameObject.searchCircle.x   += shiftX;
        gameObject.bounds.x         += shiftX;

        float shiftY                = gameObject.speed * gameObject.dir.y;
        gameObject.pos.y            += shiftY;
        gameObject.hitBox.y         += shiftY;
        gameObject.attackBox.y      += shiftY;
        gameObject.searchCircle.y   += shiftY;
        gameObject.bounds.y         += shiftY;

        gameObject.cell.checkPosition(gameObject);
    }

    @Override
    public void animationFinished() {
        // nothing to do
    }
}
