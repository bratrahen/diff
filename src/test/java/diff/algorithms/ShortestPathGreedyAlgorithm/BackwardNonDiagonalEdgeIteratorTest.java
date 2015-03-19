package diff.algorithms.ShortestPathGreedyAlgorithm;

import org.junit.Assert;
import org.junit.Test;

public class BackwardNonDiagonalEdgeIteratorTest {

    @Test
    public void hasNext_WhenEditScriptEmpty_ThenFalse() throws Exception {
        BackwardNonDiagonalEdgeIterator it = new BackwardNonDiagonalEdgeIterator(new EditGraph("", ""));
        Assert.assertFalse(it.hasNext());
    }

    @Test
    public void hasNext_WhenOriginalEqualsNew_ThenFalse() throws Exception {
        BackwardNonDiagonalEdgeIterator it = new BackwardNonDiagonalEdgeIterator(new EditGraph("ABC", "ABC"));
        Assert.assertFalse(it.hasNext());
    }

    @Test
    public void hasNext_WhenOriginalEmptyAndNewHasOneElement_ThenTrue() throws Exception {
        BackwardNonDiagonalEdgeIterator it = new BackwardNonDiagonalEdgeIterator(new EditGraph("", "A"));
        Assert.assertTrue(it.hasNext());
    }

    @Test
    public void hasNext_WhenNewHasOneElementMore_ThenTrue() throws Exception {
        BackwardNonDiagonalEdgeIterator it = new BackwardNonDiagonalEdgeIterator(new EditGraph("AB", "ABC"));
        Assert.assertTrue(it.hasNext());
    }

    @Test
    public void hasNext_WhenNewHasOneElementLessAtEnd_ThenTrue() throws Exception {
        BackwardNonDiagonalEdgeIterator it = new BackwardNonDiagonalEdgeIterator(new EditGraph("AB", "A"));
        Assert.assertTrue(it.hasNext());
    }

    @Test
    public void next_WhenNewHasOneElementLessAtStart_ThenEdgeIsHorizontal() throws Exception {
        BackwardNonDiagonalEdgeIterator it = new BackwardNonDiagonalEdgeIterator(new EditGraph("ABC", "BC"));
        Assert.assertTrue(it.next().isHorizontal());
        Assert.assertFalse("Too many edges", it.hasNext());
    }

    @Test
    public void next_WhenNewHasOneElementLessAtEnd_ThenEdgeIsHorizontal() throws Exception {
        BackwardNonDiagonalEdgeIterator it = new BackwardNonDiagonalEdgeIterator(new EditGraph("ABC", "AB"));
        Assert.assertTrue(it.next().isHorizontal());
        Assert.assertFalse("Too many edges", it.hasNext());
    }

    @Test
    public void next_WhenNewHasOneElementMoreAtStart_ThenEdgeIsVertical() throws Exception {
        BackwardNonDiagonalEdgeIterator it = new BackwardNonDiagonalEdgeIterator(new EditGraph("ABC", "XABC"));
        Assert.assertTrue(it.next().isVertical());
        Assert.assertFalse("Too many edges", it.hasNext());
    }

    @Test
    public void next_WhenNewHasOneElementMoreAtEnd_ThenEdgeIsVertical() throws Exception {
        BackwardNonDiagonalEdgeIterator it = new BackwardNonDiagonalEdgeIterator(new EditGraph("ABC", "ABCX"));
        Assert.assertTrue(it.next().isVertical());
        Assert.assertFalse("Too many edges", it.hasNext());
    }

    @Test
    public void next_WhenNewHasOneMoreAtEndAndStart_ThenTwoVerticalEdges() throws Exception {
        BackwardNonDiagonalEdgeIterator it = new BackwardNonDiagonalEdgeIterator(new EditGraph("ABC", "XABCX"));
        Assert.assertTrue(it.next().isVertical());
        Assert.assertTrue(it.next().isVertical());
        Assert.assertFalse("Too many edges", it.hasNext());
    }

    @Test
    public void next_WhenNewHasOneLessAtEndAndOneMoreAtStart_ThenEdgesHorizontalVertical() throws Exception {
        BackwardNonDiagonalEdgeIterator it = new BackwardNonDiagonalEdgeIterator(new EditGraph("ABC", "XAB"));
        Assert.assertTrue(it.next().isHorizontal());
        Assert.assertTrue(it.next().isVertical());
        Assert.assertFalse("Too many edges", it.hasNext());
    }

    @Test
    public void next_WhenFirstReplaced_ThenEdgesHorizontalAndVertical() throws Exception {
        BackwardNonDiagonalEdgeIterator it = new BackwardNonDiagonalEdgeIterator(new EditGraph("abc", "xab"));
        Assert.assertTrue(it.next().isHorizontal());
        Assert.assertTrue(it.next().isVertical());
        Assert.assertFalse("Too many edges", it.hasNext());
    }

    @Test
    public void next_WhenFirstAndLastReplaced_ThenEdgesHorizontalVerticalHorizontalVertical() throws Exception {
        BackwardNonDiagonalEdgeIterator it = new BackwardNonDiagonalEdgeIterator(new EditGraph("abcdef", "xbcdey"));
        Assert.assertTrue(it.next().isHorizontal());
        Assert.assertTrue(it.next().isVertical());
        Assert.assertTrue(it.next().isHorizontal());
        Assert.assertTrue(it.next().isVertical());
        Assert.assertFalse("Too many edges", it.hasNext());
    }

    @Test
    public void next_BugReproduction() {
        BackwardNonDiagonalEdgeIterator it = new BackwardNonDiagonalEdgeIterator(new EditGraph("abcabba", "cbabac"));

        Assert.assertTrue(it.next().isVertical());
        Assert.assertTrue(it.next().isVertical());
        Assert.assertTrue(it.next().isHorizontal());
        Assert.assertTrue(it.next().isHorizontal());
        Assert.assertTrue(it.next().isHorizontal());
        Assert.assertFalse("Too many edges", it.hasNext());
    }
}