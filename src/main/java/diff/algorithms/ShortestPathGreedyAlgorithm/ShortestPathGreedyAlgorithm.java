package diff.algorithms.ShortestPathGreedyAlgorithm;

import com.google.common.collect.ImmutableList;

import java.util.LinkedList;
import java.util.List;

public class ShortestPathGreedyAlgorithm {

    //==============================================================================
    public ImmutableList<EditCommand> diff(String originalString, String newString) {
        return diff(new EditGraph(originalString, newString));
    }

    //==============================================================================
    public ImmutableList<EditCommand> diff(Object[] originalElements, Object[] newElements) {
        return diff(new EditGraph(originalElements, newElements));
    }

    //==============================================================================
    private ImmutableList<EditCommand> diff(EditGraph editGraph) {
        List<EditCommand> editCommands = new LinkedList<EditCommand>();

        BackwardNonDiagonalEdgeIterator it = new BackwardNonDiagonalEdgeIterator(editGraph);
        while (it.hasNext()) {
            Edge edge = it.next();

            if (edge.isVertical()) {
                Object element = editGraph.getElementInNewCorrespondingTo(edge);
                editCommands.add(0, EditCommand.insert(element, edge.positionInOriginal()));
            } else if (edge.isHorizontal()) {
                Object element = editGraph.getElementInOriginalCorrespondingTo(edge);
                editCommands.add(0, EditCommand.delete(element, edge.positionInOriginal()));
            } else {
                throw new RuntimeException("Non-diagonal edge is neither vertical nor horizontal.");
            }
        }
        return ImmutableList.copyOf(editCommands);
    }
}

