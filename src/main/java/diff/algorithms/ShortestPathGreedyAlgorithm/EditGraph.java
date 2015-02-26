package diff.algorithms.ShortestPathGreedyAlgorithm;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class EditGraph {
    public final Object[] A;
    public final Object[] B;
    private final int N;
    private final int M;
    private final int MAX;

    private final Map<Integer, Integer> endpointsInDiagonalK = new HashMap<Integer, Integer>();
    private final List<Map<Integer, Integer>> enpointsOfFarthestReachingDPaths = new ArrayList<Map<Integer, Integer>>();

    public EditGraph(Object[] A, Object[] B) {
        this.A = A;
        this.B = B;
        N = A.length;
        M = B.length;
        MAX = N + M;
    }

    //==============================================================================
    public boolean isMatchPoint(int x, int y) {
        boolean isOutsideGraphBoundaries = x < 1 || x > N || y < 1 || y > M;
        if (isOutsideGraphBoundaries)
            return false;

        //convert to zero index
        return A[x - 1].equals(B[y - 1]);
    }

    //==============================================================================
    public int getLengthOfShortestEditScript() {
        getEndpointsOfFarthestReachingDPaths(0);
        return enpointsOfFarthestReachingDPaths.size() - 1;
    }

    //==============================================================================
    public int getDiagonalOfLowerRightCorner() {
        return N - M;
    }

    //==============================================================================
    public Map<Integer, Integer> getEndpointsOfFarthestReachingDPaths(int D) {
        boolean isCached = enpointsOfFarthestReachingDPaths.size() > 0;
        if (!isCached)
            findEndpointsOfFarthestReachingDPaths();

        return enpointsOfFarthestReachingDPaths.get(D);
    }

    //==============================================================================
    private List<Map<Integer, Integer>> findEndpointsOfFarthestReachingDPaths() {
        endpointsInDiagonalK.clear();
        endpointsInDiagonalK.put(1, 0);
        //D-path is a path starting at (0,0) that has exactly D non-diagonal edges.
        outside:
        for (int D = 0; D <= MAX; D++) {
            //We number the diagonals in the edit graph so that diagonal k consists of points (x,y) for which x - y = k
            //A D-path end solely on odd diagonals when D is odd and even diagonals when D is even
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

        return enpointsOfFarthestReachingDPaths;
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
        enpointsOfFarthestReachingDPaths.add(copy);
    }

}
