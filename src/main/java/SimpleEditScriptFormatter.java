import com.google.common.collect.ImmutableList;
import diff.algorithms.ShortestPathGreedyAlgorithm.EditCommand;

public class SimpleEditScriptFormatter implements EditScriptFormatter {

    @Override
    public String format(ImmutableList<EditCommand> commands) {
        String result = "";
        for (EditCommand cmd : commands) {
            if (cmd.type == EditCommand.Type.INSERT)
                result += cmd.position + "I" + cmd.element;
            else
                result += cmd.position + "D";
            result += " ";
        }

        return result.trim();
    }

}
