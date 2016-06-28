package com.donishchenko.testgame.gamestate.battle;

import com.donishchenko.testgame.object.GameObject;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;


public class Grid {
    public static final int ROWS = 7;
    public static final int COLS = 50;
    public static final int CELL_SIZE = 70;
    public static final int BATTLE_HEIGHT = ROWS * CELL_SIZE;
    public static final int BATTLE_WIDTH = COLS * CELL_SIZE;
    public static final int INDENT_TOP = 200;
    private Cell[][] cells = new Cell[ROWS][COLS];

    public Rectangle2D.Float bounds;

    // pathfinding
    public static final int PLACE_SIZE = 10;
    public byte[][] map = new byte[BATTLE_HEIGHT / PLACE_SIZE][BATTLE_WIDTH / PLACE_SIZE];

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

        // init map
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                map[row][col] = 0;
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
        // test path - clear map
//        for (int row = 0; row < map.length; row++) {
//            for (int col = 0; col < map[0].length; col++) {
//                map[row][col] = 0;
//            }
//        }

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col].update();
            }
        }
    }

    public void debugRender(Graphics2D g2) {
        // test map render
        for (int row = 0; row < map.length; row++) {
            float y = row * PLACE_SIZE + INDENT_TOP;

            for (int col = 0; col < map[0].length; col++) {
                float x = col * PLACE_SIZE + bounds.x;

                Rectangle2D.Float rect = new Rectangle2D.Float(x, y, PLACE_SIZE, PLACE_SIZE);
                if (map[row][col] == 0) {
                    g2.setPaint(Color.YELLOW);
                    g2.draw(rect);
                } else {
                    g2.setPaint(Color.RED);
                    g2.fill(rect);
                }
            }
        }

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

    // a star test
    public List<PathPoint> aStar(PathPoint start, PathPoint goal) {
        Set<PathPoint> open = new HashSet<>();
        Set<PathPoint> closed = new HashSet<>();

        start.g = 0;
        start.h = estimateDistance(start, goal);
        start.f = start.h;

        open.add(start);

        while (true) {
            PathPoint current = null;

            if (open.size() == 0) {
                return Collections.emptyList();
            }

            for (PathPoint node : open) {
                if (current == null || node.f < current.f) {
                    current = node;
                }
            }

            if (current.equals(goal)) {
                goal = current;
                break;
            }

            open.remove(current);
            closed.add(current);

            List<PathPoint> freeNeighbors = current.getFreeNeighbors();
            for (PathPoint neighbor : freeNeighbors) {
                if (neighbor == null) {
                    continue;
                }

                int nextG = current.g + neighbor.cost;

                if (nextG < neighbor.g) {
                    open.remove(neighbor);
                    closed.remove(neighbor);
                }

                if (!open.contains(neighbor) && !closed.contains(neighbor)) {
                    neighbor.g = nextG;
                    neighbor.h = estimateDistance(neighbor, goal);
                    neighbor.f = neighbor.g + neighbor.h;
                    neighbor.parent = current;
                    open.add(neighbor);
                }
            }
        }

        List<PathPoint> nodes = new ArrayList<>();
        PathPoint current = goal;
        while (current.parent != null) {
            nodes.add(current);
            current = current.parent;
        }
        nodes.add(start);

        Collections.reverse(nodes);

        return nodes;
    }

    public int estimateDistance(PathPoint pathPoint1, PathPoint pathPoint2) {
        return 10 * Math.abs(pathPoint1.col - pathPoint2.col) + Math.abs(pathPoint1.row - pathPoint2.row);
    }
}
