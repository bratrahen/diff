package diff.algorithms.ShortestPathGreedyAlgorithm;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class EditGraph {
    private final Object[] originalElements;
    private final Object[] newElements;
    private final int originalSize;
    private final int newSize;
    private final int editScriptMaxSize;

    //Map<k,x> because by definition y = x - k
    private final Map<Integer, Integer> endpointsInDiagonalK = new HashMap<Integer, Integer>();
    private final List<Map<Integer, Integer>> endpointsOfFarthestReachingDPaths = new ArrayList<Map<Integer, Integer>>();

    //==============================================================================
    public EditGraph(Object[] originalElements, Object[] newElements) {
        this.originalElements = originalElements;
        this.newElements = newElements;
        originalSize = originalElements.length;
        newSize = newElements.length;
        editScriptMaxSize = originalSize + newSize;
    }

    //==============================================================================
    private static int zeroIndexFrom(int xOrY) {
        return xOrY - 1;
    }

    //==============================================================================
    public Object getElementInNewCorrespondingTo(Edge edge) {
        return newElements[zeroIndexFrom(edge.positionInNew())];
    }

    //==============================================================================
    public Object getElementInOriginalCorrespondingTo(Edge edge) {
        //convert to zero index
        return originalElements[zeroIndexFrom(edge.positionInOriginal())];
    }

    //==============================================================================
    public boolean isMatchPoint(Point point) {
        return isMatchPoint(point.x, point.y);
    }

    //==============================================================================
    public boolean isMatchPoint(int x, int y) {
        boolean isOutsideGraphBoundaries = x < 1 || x > originalSize || y < 1 || y > newSize;
        if (isOutsideGraphBoundaries)
            return false;

        return originalElements[zeroIndexFrom(x)].equals(newElements[zeroIndexFrom(y)]);
    }

    //==============================================================================
    public int getLengthOfShortestEditScript() {
        getEndpointsOfFarthestReachingDPaths(0);
        return endpointsOfFarthestReachingDPaths.size() - 1; //minus one because we start outside graph boundary
    }

    //==============================================================================
    public int getDiagonalOfLowerRightCorner() {
        return originalSize - newSize; // k = x - y so maxK = maxX - maxY
    }

    //==============================================================================
    public Map<Integer, Integer> getEndpointsOfFarthestReachingDPaths(int D) {
        boolean isCached = endpointsOfFarthestReachingDPaths.size() > 0;
        if (!isCached)
            cacheEndpointsOfFarthestReachingDPaths();

        return endpointsOfFarthestReachingDPaths.get(D);
    }

    //==============================================================================
    private void cacheEndpointsOfFarthestReachingDPaths() {
        //We number the diagonals in the edit graph so that diagonal k consists of points (x,y) for which x - y = k
        //originalElements D-path end solely on odd diagonals when D is odd and even diagonals when D is even
        endpointsInDiagonalK.clear();
        endpointsInDiagonalK.put(1, 0);// k=1 and x=0 so y=-1: required for first iteration

        //D-path is a path starting at (0,0) that has exactly D non-diagonal edges.
        for (int D = 0; D <= editScriptMaxSize; D++) {
            for (int k = -D; k <= D; k += 2) {
                Point endpoint = findEndpointOfFarthestReachingDPathInDiagonalK(D, k);
                endpointsInDiagonalK.put(k, endpoint.x);

                if (isLongestCommonSubsequenceFound(endpoint)) {
                    storeEndpointsOfDPath(endpointsInDiagonalK);
                    return;
                }
            }
            storeEndpointsOfDPath(endpointsInDiagonalK);
        }
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
        return endpoint.x >= originalSize && endpoint.y >= newSize;
    }

    //==============================================================================
    private void storeEndpointsOfDPath(Map<Integer, Integer> endpoints) {
        Map<Integer, Integer> copy = new HashMap<Integer, Integer>();
        copy.putAll(endpoints);
        endpointsOfFarthestReachingDPaths.add(copy);
    }
}
