package diff.EditScriptFormatter;

import com.google.common.collect.ImmutableList;
import diff.algorithms.ShortestPathGreedyAlgorithm.EditCommand;

public class NormalDiffOutputFormatter implements EditScriptFormatter {

    @Override
    public String format(ImmutableList<EditCommand> commands) {
        if (commands.size() == 0)
            return "";

        String result = "";
        String header;
        String body;

        int start = 0;
        int end = 0;

        body = (commands.get(0).type == EditCommand.Type.INSERT ? "> " : "< ") + commands.get(0).element + "\n";

        for (int i = 1; i < commands.size(); i++) {
            if (commands.get(i).type == commands.get(i - 1).type && i < commands.size() - 1) {
                end++;
                body += (commands.get(i).type == EditCommand.Type.INSERT ? "> " : "< ") + commands.get(i).element + "\n";
            } else {
                if (i == commands.size() - 1) {
                    end++;
                    body += (commands.get(i).type == EditCommand.Type.INSERT ? "> " : "< ") + commands.get(i).element + "\n";
                }

                if (commands.get(i - 1).type == EditCommand.Type.INSERT)
                    header = start + "a" + (start + 1) + "," + (end + 1) + "\n";
                else
                    header = start + "," + end + "d" + (start - 1) + "\n";
                result += header + body;

                start = commands.get(i).position;
                end = start;
                body = (commands.get(i).type == EditCommand.Type.INSERT ? "> " : "< ") + commands.get(i).element + "\n";
            }
        }

        return result;
    }


}
