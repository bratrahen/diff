package diff.algorithms.ShortestPathGreedyAlgorithm;

import java.util.List;

public class ConverterToChangeCommand {//==============================================================================

    public static void convertAll(List<EditCommand> editCommands) {
        for (int i = 0; i < editCommands.size() - 1; i++) {
            EditCommand cmd1 = editCommands.get(i);
            EditCommand cmd2 = editCommands.get(i + 1);

            if (isChangeCommand(cmd1, cmd2)) {
                EditCommand deleteCommand = getDeletePartOfChange(cmd1, cmd2);
                editCommands.remove(deleteCommand);

                EditCommand insertCommand = getInsertPartOfChange(cmd1, cmd2);
                editCommands.remove(insertCommand);

                EditCommand changeCommand = EditCommand.change(insertCommand.element, insertCommand.position);
                editCommands.add(i, changeCommand);
            }
        }
    }//==============================================================================

    private static EditCommand getDeletePartOfChange(EditCommand cmd1, EditCommand cmd2) {
        if (cmd1.isDelete())
            return cmd1;
        else if (cmd2.isDelete())
            return cmd2;
        else
            throw new IllegalArgumentException();
    }//==============================================================================

    private static EditCommand getInsertPartOfChange(EditCommand cmd1, EditCommand cmd2) {
        if (cmd1.isInsert())
            return cmd1;
        else if (cmd2.isInsert())
            return cmd2;
        else
            throw new IllegalArgumentException();
    }//==============================================================================

    private static boolean isChangeCommand(EditCommand cmd1, EditCommand cmd2) {
        return cmd1.position == cmd2.position && (cmd1.isInsert() && cmd2.isDelete() || cmd1.isDelete() && cmd2.isInsert());
    }
}