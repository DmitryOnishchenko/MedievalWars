package com.donishchenko.testgame.object.ai;

import com.donishchenko.testgame.gamestate.battle.BattleState;
import com.donishchenko.testgame.gamestate.battle.Cell;
import com.donishchenko.testgame.object.GameObject;
import com.donishchenko.testgame.object.Side;
import com.donishchenko.testgame.object.Vector2F;

import java.util.List;

public class SearchEnemyTask implements Runnable {

    private GameObject gameObject;

    public SearchEnemyTask(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    @Override
    public void run() {
        double minLength = Double.MAX_VALUE;
        Vector2F newDir = null;
        GameObject newTarget = null;
        List<Cell> cells = BattleState.grid.getCellIfIntersectsWith(gameObject.searchCircle);

        for (Cell cell : cells) {
            //TODO test task end
            if (gameObject.target != null) {
                return;
            }
            List<GameObject> targets;
            if (gameObject.side == Side.LeftArmy) {
                targets = cell.getRightArmy();
            } else {
                targets = cell.getLeftArmy();
            }
//            targets = cell.getAllUnits();

            for (GameObject target : targets) {
                //TODO test task end
                if (gameObject.target != null) {
                    return;
                }
                if (target.isAlive() && gameObject.side != target.side && intersects(gameObject, target)) {
                    newDir = target.pos.copy();
                    newDir.sub(gameObject.pos);
                    double length = newDir.length();

                    if (length <= minLength) {
                        minLength = length;
                        newTarget = target;
                    }
                }
            }
        }

        if (newTarget != null) {
            gameObject.target = newTarget;
            newDir.normalize();
            gameObject.dir = newDir;
        } else {
            gameObject.target = null;
            gameObject.dir = gameObject.physicsModel.getMoveDir();
        }
    }

    public boolean intersects(GameObject gameObject, GameObject other) {
        double unitSearchRange = gameObject.physicsModel.getSearchRange();
        double enemyHitBoxRadius = other.hitBox.width / 2;
        double distance = Vector2F.getDistanceOnScreen(gameObject.pos, other.pos);

        return distance < (unitSearchRange + enemyHitBoxRadius);
    }
}
