package diff.algorithms.ShortestPathGreedyAlgorithm;


import java.awt.*;
import java.util.Iterator;
import java.util.Map;

class BackwardNonDiagonalEdgeIterator implements Iterator<Edge> {
    private final EditGraph editGraph;
    private int d;
    private int k;


    //==============================================================================
    public BackwardNonDiagonalEdgeIterator(EditGraph editGraph) {
        this.editGraph = editGraph;
        d = editGraph.lengthOfShortestEditScript();
        k = editGraph.getDiagonalOfLowerRightCorner();
    }

    //==============================================================================
    @Override
    public boolean hasNext() {
        return d > 0;
    }

    //==============================================================================
    @Override
    public Edge next() {
        Point start = findEdgeStart();
        Point end = findEdgeEnd();
        Edge result = new Edge(start, end);

        d = d - 1;
        if (result.isVertical())
            k = k + 1;
        else if (result.isHorizontal())
            k = k - 1;
        else
            throw new RuntimeException("Non-diagonal edge is neither vertical nor horizontal.");

        return result;
    }

    //==============================================================================
    @Override
    public void remove() {
        throw new UnsupportedOperationException();

    }

    //==============================================================================
    private Point findEdgeEnd() {
        Point endpoint = getEndpointOfFarthestReachingDPathInDiagonalK(d, k);
        @SuppressWarnings("UnnecessaryLocalVariable") Point dThNonDiagonalEdgeEnd = traverseBackwardAlongDiagonal(endpoint);
        return dThNonDiagonalEdgeEnd;
    }

    //==============================================================================
    private Point findEdgeStart() {
        Map<Integer, Point> Vd1 = editGraph.getEndpointsOfFarthestReachingDPath(d - 1);

        if (Vd1.containsKey(k + 1))
            return Vd1.get(k + 1);
        else
            return Vd1.get(k - 1);
    }

    //==============================================================================
    private Point traverseBackwardAlongDiagonal(Point endpoint) {
        Point result = new Point(endpoint);
        while (result.x > 0 && result.y > 0 && editGraph.isMatchPoint(result))
            result.translate(-1, -1);

        return result;
    }

    //==============================================================================
    private Point getEndpointOfFarthestReachingDPathInDiagonalK(int d, int k) {
        Map<Integer, Point> Vd = editGraph.getEndpointsOfFarthestReachingDPath(d);
        return Vd.get(k);
    }
}

