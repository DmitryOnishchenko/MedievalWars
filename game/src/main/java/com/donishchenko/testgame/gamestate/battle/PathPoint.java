package com.donishchenko.testgame.gamestate.battle;

import java.util.LinkedList;
import java.util.List;

public class PathPoint {

    public byte[][] map;

    public int row;
    public int col;
    public boolean blocked;

    public PathPoint parent;

    public int cost;
    public int f;
    public int g;
    public int h;

    public PathPoint(byte[][] map, int col, int row, boolean blocked, int cost, PathPoint parent) {
        this.map = map;
        this.col = col;
        this.row = row;
        this.blocked = blocked;
        this.cost = cost;
        this.g = cost;
        this.parent = parent;
    }

    public List<PathPoint> getFreeNeighbors() {
        List<PathPoint> list = new LinkedList<>();
        // cost is 10 for hor+vert and 14 for diag
        // upper row
        addNotNullFreePlace(row - 1, col - 1, 14,list);
        addNotNullFreePlace(row - 1, col, 10, list);
        addNotNullFreePlace(row - 1, col + 1, 14, list);

        // same row
        addNotNullFreePlace(row, col - 1, 10, list);
        addNotNullFreePlace(row, col + 1, 10, list);

        // bottom row
        addNotNullFreePlace(row + 1, col - 1, 14, list);
        addNotNullFreePlace(row + 1, col, 10, list);
        addNotNullFreePlace(row + 1, col + 1, 14, list);

        return list;
    }

    private void addNotNullFreePlace(int row, int col, int cost, List<PathPoint> list) {
        if (row < 0 || row >= map.length) return;
        if (col < 0 || col >= map[0].length) return;

        if (map[row][col] == 0) {
            PathPoint point = new PathPoint(map, col, row, false, cost, this);
            list.add(point);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PathPoint pathPoint = (PathPoint) o;

        if (row != pathPoint.row) return false;
        return col == pathPoint.col;

    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        return result;
    }

    @Override
    public String toString() {
        return "PathPoint{" +
                "row=" + row +
                ", col=" + col +
                ", f=" + f +
                ", g=" + g +
                ", h=" + h +
                '}';
    }
}
