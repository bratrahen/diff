package diff.algorithms.ShortestPathGreedyAlgorithm;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.Map;

public class EditGraphTest {
    @Test
    public void lengthOfShortestEditScript_Identical_LengthIsZero() throws Exception {
        String A = "ABCD";
        String B = "ABCD";
        EditGraph graph = new EditGraph(A, B);

        Assert.assertEquals(0, graph.lengthOfShortestEditScript());
    }

    @Test
    public void lengthOfShortestEditScript_NewCompletelyDifferent_LengthIsSumOfOriginalAndNew() throws Exception {
        String A = "ABCD";
        String B = "abcde";
        EditGraph graph = new EditGraph(A, B);

        Assert.assertEquals(4 + 5, graph.lengthOfShortestEditScript());
    }

    @Test
    public void lengthOfShortestEditScript_NewHasMore_LengthOfMore() throws Exception {
        String A = "ABCD";
        String B = "ABCDEFG";
        EditGraph graph = new EditGraph(A, B);

        Assert.assertEquals(3, graph.lengthOfShortestEditScript());
    }

    @Test
    public void lengthOfShortestEditScript_NewHasLess_LengthOfLess() throws Exception {
        String A = "ABCD";
        String B = "ABC";
        EditGraph graph = new EditGraph(A, B);

        Assert.assertEquals(1, graph.lengthOfShortestEditScript());
    }

    @Test
    public void lengthOfShortestEditScript_NewReplaces_LengthIsTwiceOfReplaced() throws Exception {
        String A = "ABCD";
        String B = "GHID";
        EditGraph graph = new EditGraph(A, B);

        Assert.assertEquals(6, graph.lengthOfShortestEditScript());
    }

    @Test
    public void getEndpointsOfFarthestReachingDPaths_Identical_0PathWithFarthestEndpointAtLowerRightCorner() throws Exception {
        String A = "ABCD";
        String B = "ABCD";
        EditGraph graph = new EditGraph(A, B);

        int D = 0;
        Map<Integer, Point> endpoints = graph.getEndpointsOfFarthestReachingDPath(D);

        int cornerX = A.length();
        int cornerY = B.length();
        int diagonalK = 0;

        Assert.assertTrue(endpoints.keySet().contains(diagonalK));
        int furthestEndpointX = endpoints.get(diagonalK).x;
        int furthestEndpointY = furthestEndpointX - diagonalK;

        Assert.assertEquals(cornerX, furthestEndpointX);
        Assert.assertEquals(cornerY, furthestEndpointY);
    }

    @Test
    public void getEndpointsOfFarthestReachingDPaths_NewLongerByD_DPathWithFarthestEndpointAtLowerRightCorner() throws Exception {
        String A = "ABCD";
        String B = "ABCDEFG";
        EditGraph graph = new EditGraph(A, B);

        int D = B.length() - A.length();
        Map<Integer, Point> endpoints = graph.getEndpointsOfFarthestReachingDPath(D);

        int cornerX = A.length();
        int cornerY = B.length();
        int diagonalK = cornerX - cornerY;

        Assert.assertTrue(endpoints.keySet().contains(diagonalK));
        int furthestEndpointX = endpoints.get(diagonalK).x;
        int furthestEndpointY = furthestEndpointX - diagonalK;

        Assert.assertEquals(cornerX, furthestEndpointX);
        Assert.assertEquals(cornerY, furthestEndpointY);
    }

    @Test
    public void getEndpointsOfFarthestReachingDPaths_NewShorterByD_DPathWithFarthestEndpointAtLowerRightCorner() throws Exception {
        String A = "ABCD";
        String B = "A";
        EditGraph graph = new EditGraph(A, B);

        int D = A.length() - B.length();
        Map<Integer, Point> endpoints = graph.getEndpointsOfFarthestReachingDPath(D);

        int cornerX = A.length();
        int cornerY = B.length();
        int diagonalK = cornerX - cornerY;

        Assert.assertTrue(endpoints.keySet().contains(diagonalK));
        int furthestEndpointX = endpoints.get(diagonalK).x;
        int furthestEndpointY = furthestEndpointX - diagonalK;

        Assert.assertEquals(cornerX, furthestEndpointX);
        Assert.assertEquals(cornerY, furthestEndpointY);
    }

    @Test
    public void getEndpointsOfFarthestReachingDPaths_NewReplacesD_2DPathWithFarthestEndpointAtLowerRightCorner() throws Exception {
        String A = "ABCD";
        String B = "AEGD";
        EditGraph graph = new EditGraph(A, B);

        int D = 2 * 2;
        Map<Integer, Point> endpoints = graph.getEndpointsOfFarthestReachingDPath(D);

        int cornerX = A.length();
        int cornerY = B.length();
        int diagonalK = 0;

        Assert.assertTrue(endpoints.keySet().contains(diagonalK));
        int furthestEndpointX = endpoints.get(diagonalK).x;
        int furthestEndpointY = furthestEndpointX - diagonalK;

        Assert.assertEquals(cornerX, furthestEndpointX);
        Assert.assertEquals(cornerY, furthestEndpointY);
    }
}