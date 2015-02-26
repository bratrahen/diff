package diff.algorithms.ShortestPathGreedyAlgorithm;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.ArrayUtils;

import java.util.LinkedList;
import java.util.List;

public class ShortestPathGreedyAlgorithm {

    //==============================================================================
    public ImmutableList<EditCommand> diff(String originalString, String newString) {
        return diff(ArrayUtils.toObject(originalString.toCharArray()), ArrayUtils.toObject(newString.toCharArray()));
    }

    //==============================================================================
    public ImmutableList<EditCommand> diff(Object[] originalElements, Object[] newElements) {
        EditGraph editGraph = new EditGraph(originalElements, newElements);
        List<EditCommand> editCommands = new LinkedList<EditCommand>();

        BackwardNonDiagonalEdgeIterator it = new BackwardNonDiagonalEdgeIterator(editGraph);
        while (it.hasNext()) {
            Edge edge = it.next();
            int positionInOriginal = edge.getEndPoint().x;

            if (edge.isVertical()) {
                Object element = editGraph.getElementInNewCorrespondingTo(edge);
                editCommands.add(0, EditCommand.insert(element, positionInOriginal));
            } else if (edge.isHorizontal()) {
                Object element = editGraph.getElementInOriginalCorrespondingTo(edge);
                editCommands.add(0, EditCommand.delete(element, positionInOriginal));
            } else {
                throw new RuntimeException("Non-diagonal edge is neither vertical nor horizontal.");
            }
        }
        return ImmutableList.copyOf(editCommands);
    }
}

