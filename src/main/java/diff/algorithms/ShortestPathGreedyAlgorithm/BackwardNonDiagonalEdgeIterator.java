package diff.algorithms.ShortestPathGreedyAlgorithm;


import java.awt.*;
import java.util.Iterator;

class BackwardNonDiagonalEdgeIterator implements Iterator<Edge> {
    private final EditGraph editGraph;
    private int pathLength;
    private Point currentEndpoint;

    //==============================================================================
    public BackwardNonDiagonalEdgeIterator(EditGraph editGraph) {
        this.editGraph = editGraph;
        this.pathLength = editGraph.lengthOfShortestEditScript();
        this.currentEndpoint = editGraph.lowerRightCorner();
    }

    //==============================================================================
    @Override
    public boolean hasNext() {
        return pathLength > 0;
    }

    //==============================================================================
    @Override
    public Edge next() {
        Point edgeEnd = traverseBackwardAlongDiagonal(currentEndpoint);
        Point edgeStart = translateBackwardPerpendicularly(edgeEnd);
        Edge result = new Edge(edgeStart, edgeEnd);

        currentEndpoint = edgeStart;
        pathLength--;

        return result;
    }

    //==============================================================================
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    //==============================================================================
    private Point traverseBackwardAlongDiagonal(Point endpoint) {
        Point result = new Point(endpoint);
        while (result.x > 0 && result.y > 0 && editGraph.isMatchPoint(result))
            result.translate(-1, -1);

        return result;
    }

    //==============================================================================
    private Point translateBackwardPerpendicularly(Point endpoint) {
        Point translatedHorizontally = new Point(endpoint.x - 1, endpoint.y);
        if (isNeighbourEndpoint(translatedHorizontally))
            return translatedHorizontally;

        Point translatedVertically = new Point(endpoint.x, endpoint.y - 1);
        if (isNeighbourEndpoint(translatedVertically))
            return translatedVertically;

        throw new RuntimeException("Non-diagonal edge expected but not found [? -> " + endpoint + "]");
    }

    //==============================================================================
    private boolean isNeighbourEndpoint(Point endpoint) {
        Point farthestEndpoint = editGraph.getEndpointsOfFarthestReachingDPath(pathLength - 1).get(EditGraph.diagonalNumberAt(endpoint));
        return farthestEndpoint != null && farthestEndpoint.equals(endpoint);
    }
}

