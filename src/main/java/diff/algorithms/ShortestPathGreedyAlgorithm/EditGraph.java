package diff.algorithms.ShortestPathGreedyAlgorithm;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class EditGraph {
    public final Object[] originalElements;
    public final Object[] newElements;
    private final int N;
    private final int M;
    private final int MAX;

    private final Map<Integer, Integer> endpointsInDiagonalK = new HashMap<Integer, Integer>();
    private final List<Map<Integer, Integer>> endpointsOfFarthestReachingDPaths = new ArrayList<Map<Integer, Integer>>();

    //==============================================================================
    public EditGraph(Object[] originalElements, Object[] newElements) {
        this.originalElements = originalElements;
        this.newElements = newElements;
        N = originalElements.length;
        M = newElements.length;
        MAX = N + M;
    }

    //==============================================================================
    public Object getElementInNewCorrespondingTo(Edge edge) {
        //convert to zero index
        return newElements[edge.getEndPoint().y - 1];
    }

    //==============================================================================
    public Object getElementInOriginalCorrespondingTo(Edge edge) {
        //convert to zero index
        return originalElements[edge.getEndPoint().x - 1];
    }

    //==============================================================================
    public boolean isMatchPoint(int x, int y) {
        boolean isOutsideGraphBoundaries = x < 1 || x > N || y < 1 || y > M;
        if (isOutsideGraphBoundaries)
            return false;

        //convert to zero index
        return originalElements[x - 1].equals(newElements[y - 1]);
    }

    //==============================================================================
    public int getLengthOfShortestEditScript() {
        getEndpointsOfFarthestReachingDPaths(0);
        return endpointsOfFarthestReachingDPaths.size() - 1;
    }

    //==============================================================================
    public int getDiagonalOfLowerRightCorner() {
        return N - M;
    }

    //==============================================================================
    public Map<Integer, Integer> getEndpointsOfFarthestReachingDPaths(int D) {
        boolean isCached = endpointsOfFarthestReachingDPaths.size() > 0;
        if (!isCached)
            findEndpointsOfFarthestReachingDPaths();

        return endpointsOfFarthestReachingDPaths.get(D);
    }

    //==============================================================================
    private List<Map<Integer, Integer>> findEndpointsOfFarthestReachingDPaths() {
        endpointsInDiagonalK.clear();
        endpointsInDiagonalK.put(1, 0);
        //D-path is a path starting at (0,0) that has exactly D non-diagonal edges.
        outside:
        for (int D = 0; D <= MAX; D++) {
            //We number the diagonals in the edit graph so that diagonal k consists of points (x,y) for which x - y = k
            //originalElements D-path end solely on odd diagonals when D is odd and even diagonals when D is even
            for (int k = -D; k <= D; k += 2) {
                Point endpoint = findEndpointOfFarthestReachingDPathInDiagonalK(D, k);
                endpointsInDiagonalK.put(k, endpoint.x);

                if (isLongestCommonSubsequenceFound(endpoint)) {
                    storeEndpointsOfDPath(endpointsInDiagonalK);
                    break outside;
                }
            }
            storeEndpointsOfDPath(endpointsInDiagonalK);
        }

        return endpointsOfFarthestReachingDPaths;
    }

    //==============================================================================
    private Point findEndpointOfFarthestReachingDPathInDiagonalK(int D, int k) {
        int x;
        if (k == -D || k != D && endpointsInDiagonalK.get(k - 1) < endpointsInDiagonalK.get(k + 1)) {
            x = endpointsInDiagonalK.get(k + 1);
        } else {
            x = endpointsInDiagonalK.get(k - 1) + 1;
        }

        int y = x - k;

        Point result = new Point(x, y);
        traverseForwardAlongDiagonal(result);
        return result;
    }

    //==============================================================================
    private void traverseForwardAlongDiagonal(Point result) {
        while (!isLongestCommonSubsequenceFound(result) && isMatchPoint(result.x + 1, result.y + 1)) {
            result.translate(1, 1);
        }
    }

    //==============================================================================
    private boolean isLongestCommonSubsequenceFound(Point endpoint) {
        return endpoint.x >= N && endpoint.y >= M;
    }

    //==============================================================================
    private void storeEndpointsOfDPath(Map<Integer, Integer> endpoints) {
        Map<Integer, Integer> copy = new HashMap<Integer, Integer>();
        copy.putAll(endpointsInDiagonalK);
        endpointsOfFarthestReachingDPaths.add(copy);
    }

}
