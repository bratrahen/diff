package diff.algorithms.ShortestPathGreedyAlgorithm;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.ArrayUtils;

import java.util.LinkedList;
import java.util.List;

public class ShortestPathGreedyAlgorithm {

    //==============================================================================
    public ImmutableList<EditCommand> diff(String A, String B) {
        return diff(ArrayUtils.toObject(A.toCharArray()), ArrayUtils.toObject(B.toCharArray()));
    }

    //==============================================================================
    public ImmutableList<EditCommand> diff(Object[] A, Object[] B) {
        EditGraph editGraph = new EditGraph(A, B);
        List<EditCommand> editCommands = new LinkedList<EditCommand>();

        NonDiagonalEdgeIterator it = new NonDiagonalEdgeIterator(editGraph);
        while (it.hasNext()) {
            Edge nonDiagonalEdge = it.next();
            int position = nonDiagonalEdge.getEndPoint().x;
            if (nonDiagonalEdge.isVertical()) {
                Object element = editGraph.B[nonDiagonalEdge.getEndPoint().y - 1];
                editCommands.add(0, EditCommand.insert(position, element));
            } else {
                Object element = editGraph.A[nonDiagonalEdge.getEndPoint().x - 1];
                editCommands.add(0, EditCommand.delete(position, element));
            }
        }

        return ImmutableList.copyOf(editCommands);
    }
}

