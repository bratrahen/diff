package diff.algorithms.ShortestPathGreedyAlgorithm;

import java.awt.*;

class Edge {
    private final Point start;
    private final Point end;

    //==============================================================================
    Edge(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    //==============================================================================
    public boolean isVertical() {
        return start.x == end.x;
    }

    //==============================================================================
    public boolean isHorizontal() {
        return start.y == end.y;
    }

    //==============================================================================
    public int positionInOriginal() {
        return end.x;
    }

    //==============================================================================
    public int positionInNew() {
        return end.y;
    }

    @Override
    public String toString() {
        return "[" + start + " -> " + end + "]";
    }
}