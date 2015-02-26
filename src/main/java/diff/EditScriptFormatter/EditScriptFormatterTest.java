package diff.EditScriptFormatter;

import com.google.common.collect.ImmutableList;
import diff.algorithms.ShortestPathGreedyAlgorithm.EditCommand;

public class EditScriptFormatterTest implements EditScriptFormatter {

    @Override
    public String format(ImmutableList<EditCommand> commands) {
        String result = "";
        for (EditCommand cmd : commands) {
            if (cmd.type == EditCommand.Type.INSERT)
                result += formatInsertCommand(cmd);
            else
                result += formatDeleteCommand(cmd);
            result += "\n";
        }

        return result;
    }


    private String formatInsertCommand(EditCommand command) {
        return command.position + "+ " + command.element;
    }

    private String formatDeleteCommand(EditCommand command) {
        return command.position + "- " + command.element;
    }

}