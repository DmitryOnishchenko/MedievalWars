package com.donishchenko.testgame.gamestate.battle;

import com.donishchenko.testgame.object.GameObject;
import com.donishchenko.testgame.object.Vector2F;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;


public class Grid {
    public static final int ROWS = 7;
    public static final int COLS = 50;
    public static final int CELL_SIZE = 70;
    private static final int BATTLE_HEIGHT = ROWS * CELL_SIZE;
    private static final int BATTLE_WIDTH = COLS * CELL_SIZE;
    public static final int INDENT_TOP = 200;
    private Cell[][] cells = new Cell[ROWS][COLS];

    private Rectangle2D.Float bounds;

    public Grid() {
        init();
    }

    public void init() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Rectangle2D.Float bounds = new Rectangle2D.Float(
                        col * CELL_SIZE, row * CELL_SIZE + INDENT_TOP, CELL_SIZE, CELL_SIZE);
                cells[row][col] = new Cell(this, row, col, bounds);
            }
        }

        bounds = new Rectangle2D.Float(0, INDENT_TOP, BATTLE_WIDTH, BATTLE_HEIGHT);
    }

    public void clear() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col].clear();
            }
        }
    }

    public void add(GameObject gameObject) {
        int x = gameObject.x();
        int y = gameObject.y();

        if (!bounds.contains(x, y)) {
            gameObject.delete = true;
            return;
        }

        int col = (x - (int) bounds.x) / CELL_SIZE;
        int row = (y - INDENT_TOP) / CELL_SIZE;

        Cell cell = cells[row][col];
        cell.add(gameObject);
    }

    public void update() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col].update();
            }
        }
    }

    public void debugRender(Graphics2D g2) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col].debugRender(g2);
            }
        }
    }

    public List<Cell> getCellIfIntersectsWith(Shape shape) {
        List<Cell> list = new ArrayList<>(20);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Cell cell = cells[row][col];
                if (shape.intersects(cell.getBounds())) {
                    list.add(cell);
                }
            }
        }

        return list;
    }

    public void move(float x, float y) {
        bounds.x += x;
        bounds.y += y;

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col].bounds.x += x;
                cells[row][col].bounds.y += y;
            }
        }
    }
}
