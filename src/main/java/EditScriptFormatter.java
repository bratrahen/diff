import com.google.common.collect.ImmutableList;
import diff.algorithms.ShortestPathGreedyAlgorithm.EditCommand;

interface EditScriptFormatter {

    public String format(ImmutableList<EditCommand> commands);

}