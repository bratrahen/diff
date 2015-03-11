package diff.algorithms.ShortestPathGreedyAlgorithm;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class ConverterToChangeCommandTest {

    @Test
    public void convertAll_FromDeleteInsert_ToChange() {
        List<EditCommand> commands = new LinkedList<EditCommand>();
        commands.add(EditCommand.delete(null, 1));
        commands.add(EditCommand.insert(new Character('A'), 1));

        ConverterToChangeCommand.convertAll(commands);

        Assert.assertEquals(1, commands.size());
        Assert.assertTrue(commands.get(0).isChange());
        Assert.assertEquals(new Character('A'), commands.get(0).element);
    }

    @Test
    public void convertAll_FromDeleteDeleteInsert_ToDeleteChange() {
        List<EditCommand> commands = new LinkedList<EditCommand>();
        commands.add(EditCommand.delete(null, 1));
        commands.add(EditCommand.delete(null, 2));
        commands.add(EditCommand.insert(new Character('A'), 2));

        ConverterToChangeCommand.convertAll(commands);

        Assert.assertEquals(2, commands.size());
        Assert.assertTrue(commands.get(1).isChange());
        Assert.assertEquals(new Character('A'), commands.get(1).element);
    }

    @Test
    public void convertAll_FromDeleteDeleteInsertInsertInsertDelete_ToDeleteChangeInsertChange() {
        List<EditCommand> commands = new LinkedList<EditCommand>();
        commands.add(EditCommand.delete(null, 1));
        commands.add(EditCommand.delete(null, 2));
        commands.add(EditCommand.insert(new Character('A'), 2));
        commands.add(EditCommand.insert(new Character('B'), 3));
        commands.add(EditCommand.insert(new Character('C'), 4));
        commands.add(EditCommand.delete(null, 4));

        ConverterToChangeCommand.convertAll(commands);

        Assert.assertEquals(4, commands.size());
        Assert.assertTrue(commands.get(0).isDelete());
        Assert.assertTrue(commands.get(1).isChange());
        Assert.assertTrue(commands.get(2).isInsert());
        Assert.assertTrue(commands.get(3).isChange());

        Assert.assertEquals(new Character('A'), commands.get(1).element);
        Assert.assertEquals(new Character('B'), commands.get(2).element);
        Assert.assertEquals(new Character('C'), commands.get(3).element);
    }
}