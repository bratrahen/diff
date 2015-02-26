package diff.EditScriptFormatter;

import com.google.common.collect.ImmutableList;
import diff.algorithms.ShortestPathGreedyAlgorithm.EditCommand;

public interface EditScriptFormatter {

    public String format(ImmutableList<EditCommand> commands);

}