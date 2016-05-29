package com.donishchenko.testgame.gamestate.battle;

import com.donishchenko.testgame.object.GameObject;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;


public class Grid {
    private static final int BATTLE_HEIGHT = 500;
    private static final int BATTLE_WIDTH = 1480;
    public static final int CELL_SIZE = 70;
    public static final int ROWS = BATTLE_HEIGHT / CELL_SIZE;
    public static final int COLS = BATTLE_WIDTH / CELL_SIZE;
    public static final int INDENT_TOP = 200;
    public static final int INDENT_LEFT = -60;
    private Cell[][] cells = new Cell[ROWS][COLS];

    public Grid() {
        init();
    }

    public void init() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Rectangle2D.Float bounds = new Rectangle2D.Float(
                        col * CELL_SIZE + INDENT_LEFT, row * CELL_SIZE + INDENT_TOP, CELL_SIZE, CELL_SIZE);
                cells[row][col] = new Cell(this, row, col, bounds);
            }
        }
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

        if (x < INDENT_LEFT || y < INDENT_TOP || x > COLS * CELL_SIZE + INDENT_LEFT - 1 || y >= ROWS * CELL_SIZE + INDENT_TOP) {
            gameObject.delete = true;
            return;
        }

        int col = (x - INDENT_LEFT) / CELL_SIZE;
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

}
