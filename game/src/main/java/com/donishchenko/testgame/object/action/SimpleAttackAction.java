package com.donishchenko.testgame.object.action;

import com.donishchenko.testgame.config.EngineConstants;
import com.donishchenko.testgame.object.GameObject;

import java.awt.image.BufferedImage;

public class SimpleAttackAction extends Action {

    private int attackTrigger;
    private int counter;

    public SimpleAttackAction(GameObject gameObject) {
        this.gameObject = gameObject;
        this.attackTrigger = (gameObject.physicsModel.getAttackSpeed() * 1_000_000) / EngineConstants.NANO_PER_TICK;
        this.counter = attackTrigger;
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
        if (counter++ != attackTrigger) {
            return;
        }
        counter = 0;

        GameObject target = gameObject.target;
        if (target != null) {
            gameObject.target.takeDamage(gameObject.damage);
        }
    }

    @Override
    public void animationFinished() {
        System.out.println(gameObject.name + " anim finished");

        GameObject target = gameObject.target;
        if (target == null || !target.isAlive()) {
            gameObject.target = null;
            // free action for brain
            gameObject.brain.free();
        }
    }
}
