package diff.algorithms.ShortestPathGreedyAlgorithm;


import java.awt.*;
import java.util.Iterator;
import java.util.Map;

//==============================================================================
class BackwardNonDiagonalEdgeIterator implements Iterator<Edge> {
    private final EditGraph editGraph;
    private int d;
    private int k;


    public BackwardNonDiagonalEdgeIterator(EditGraph editGraph) {
        this.editGraph = editGraph;
        d = editGraph.getLengthOfShortestEditScript();
        k = editGraph.getDiagonalOfLowerRightCorner();
    }

    @Override
    public boolean hasNext() {
        return d > 0;
    }

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

    @Override
    public void remove() {
        throw new UnsupportedOperationException();

    }

    private Point findEdgeEnd() {
        Point endpoint = getEndpointOfFarthestReachingDPathInDiagonalK(d, k);
        @SuppressWarnings("UnnecessaryLocalVariable") Point dThNonDiagonalEdgeEnd = traverseBackwardAlongDiagonal(endpoint);
        return dThNonDiagonalEdgeEnd;
    }

    private Point findEdgeStart() {
        Map<Integer, Integer> Vd1 = editGraph.getEndpointsOfFarthestReachingDPaths(d - 1);
        int x;
        int y;
        if (Vd1.containsKey(k + 1)) {
            x = Vd1.get(k + 1);
            y = x - (k + 1);
        } else {
            x = Vd1.get(k - 1);
            y = x - (k - 1);
        }


        @SuppressWarnings("UnnecessaryLocalVariable") Point dThNonDiagonalEdgeStart = new Point(x, y);
        return dThNonDiagonalEdgeStart;
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
        Map<Integer, Integer> Vd = editGraph.getEndpointsOfFarthestReachingDPaths(d);
        int x = Vd.get(k);
        int y = x - k;
        return new Point(x, y);
    }
}

