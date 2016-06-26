package com.donishchenko.testgame.gamestate.battle;

import com.donishchenko.testgame.object.GameObject;
import com.donishchenko.testgame.object.Side;
import com.donishchenko.testgame.object.Type;
import com.donishchenko.testgame.utils.FastRemoveArrayList;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static com.donishchenko.testgame.gamestate.battle.BattleStateSettings.*;
import static com.donishchenko.testgame.gamestate.battle.Grid.*;

public class Cell {
    private final int row;
    private final int col;
    private Grid grid;
    public Rectangle2D.Float bounds = new Rectangle2D.Float();

    private List<GameObject> leftArmy       = new FastRemoveArrayList<>(5_00);
    private List<GameObject> rightArmy      = new FastRemoveArrayList<>(5_00);
    private List<GameObject> projectiles    = new FastRemoveArrayList<>(5_00);
    // neutrals
    private List<GameObject> neutrals       = new FastRemoveArrayList<>(5_00);

    public Cell(Grid grid, int row, int col, Rectangle2D.Float bounds) {
        this.grid = grid;
        this.row = row;
        this.col = col;
        this.bounds = bounds;
    }

    public Rectangle2D getBounds() {
        return bounds;
    }

    public void add(GameObject gameObject) {
        gameObject.cell = this;
        Side side = gameObject.side;

        if (gameObject.type == Type.Unit) {
            if (side == Side.LeftArmy) {
                leftArmy.add(gameObject);
            } else if (side == Side.RightArmy) {
                rightArmy.add(gameObject);
            } else {
                neutrals.add(gameObject);
            }
        } else {
            projectiles.add(gameObject);
        }
    }

    public void update() {
        updateObjects(leftArmy);
        updateObjects(rightArmy);
        updateObjects(projectiles);
        updateObjects(neutrals);
    }

    private void updateObjects(Collection<GameObject> collection) {
        Iterator<GameObject> iterator = collection.iterator();
        while (iterator.hasNext()) {
            GameObject gameObject = iterator.next();
            // double check
            checkPosition(gameObject);

            if (gameObject.delete) {
                iterator.remove();
            } else if (gameObject.relocate) {
                gameObject.relocate = false;
                iterator.remove();
                grid.add(gameObject);
            } else {
                gameObject.updateAi();
                gameObject.updateAction();
                gameObject.updatePhysics();
                gameObject.updateGraphics();
            }
        }
    }

    public void debugRender(Graphics2D g2) {
        if (DEBUG_GRID) {
            g2.setPaint(Color.BLUE);
            g2.draw(bounds);
            g2.setPaint(Color.RED);
            g2.drawString(row + "|" + col, (int) (bounds.x + 2), (int) (bounds.y + 12));
        }
    }

    public void clear() {
        leftArmy.clear();
        rightArmy.clear();
        projectiles.clear();
        neutrals.clear();
    }

    public List<GameObject> getLeftArmy() {
        return leftArmy;
    }

    public List<GameObject> getRightArmy() {
        return rightArmy;
    }

    public List<GameObject> getProjectiles() {
        return projectiles;
    }

    public List<GameObject> getNeutrals() {
        return neutrals;
    }

    public void checkPosition(GameObject gameObject) {
        if (!bounds.contains(gameObject.x(), gameObject.y())) {
            gameObject.relocate = true;
        } else {
            gameObject.relocate = false;
        }
    }

    @Override
    public String toString() {
        return "Cell{row: " + row + ", col: " + col + "}";
    }
}

