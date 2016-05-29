package com.donishchenko.testgame.object.ai;

import com.donishchenko.testgame.object.GameObject;
import com.donishchenko.testgame.object.Vector2F;
import com.donishchenko.testgame.object.action.SimpleAttackAction;

public class SimpleAttackIdea extends Idea {

    public SimpleAttackIdea(GameObject gameObject, int priority) {
        super(gameObject, priority);
    }

    @Override
    public void init() {
        action = new SimpleAttackAction(gameObject);
        action.init();
    }

    @Override
    public boolean think() {
        if (gameObject.target != null && canAttack(gameObject)) {
            gameObject.dir = new Vector2F();
            action.animation.checkDirection(gameObject.target.pos.x - gameObject.pos.x);

            return true;
        }

        return false;
    }

    /**
     * Checks if object can attack its target
     * @param object object with target
     * @return <b>true</b> - if target isAlive and distance < (attackRange + targetHitBoxRadius),
     * otherwise <b>false</b>
     */
    private boolean canAttack(GameObject object) {
        GameObject target = object.target;

        if (!target.isAlive()) {
            return false;
        }

        double attackRange = object.physicsModel.getAttackRange();
        double targetHitBoxRadius = target.hitBox.width / 2;
        double distance = Vector2F.getDistanceOnScreen(object.pos, target.pos);

        return distance < (attackRange + targetHitBoxRadius);
    }
}
