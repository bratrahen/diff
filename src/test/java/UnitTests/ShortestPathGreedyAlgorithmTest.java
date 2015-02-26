package UnitTests;

import EditScriptFormatter.NormalDiffOutputFormatter;
import EditScriptFormatter.SimpleEditScriptFormatter;
import com.google.common.collect.ImmutableList;
import diff.algorithms.ShortestPathGreedyAlgorithm.EditCommand;
import diff.algorithms.ShortestPathGreedyAlgorithm.ShortestPathGreedyAlgorithm;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShortestPathGreedyAlgorithmTest {

    private final String originalSequence = "This part of the\n"
            + "document has stayed the\n"
            + "same from version to\n"
            + "version.  It shouldn't\n"
            + "be shown if it doesn't\n"
            + "change.  Otherwise, that\n"
            + "would not be helping to\n"
            + "compress the size of the\n"
            + "changes.\n"
            + " \n"
            + "This paragraph contains\n"
            + "text that is outdated.\n"
            + "It will be deleted in the\n"
            + "near future.\n"
            + " \n"
            + "It is important to spell\n"
            + "check this dokument. On\n"
            + "the other hand, a\n"
            + "misspelled word isn't\n"
            + "the end of the world.\n"
            + "Nothing in the rest of\n"
            + "this paragraph needs to\n"
            + "be changed. Things can\n"
            + "be added after it.";

    private final String newSequence = "This is an important\n"
            + "notice! It should\n"
            + "therefore be located at\n"
            + "the beginning of this\n"
            + "document!\n"
            + " \n"
            + "This part of the\n"
            + "document has stayed the\n"
            + "same from version to\n"
            + "version.  It shouldn't\n"
            + "be shown if it doesn't\n"
            + "change.  Otherwise, that\n"
            + "would not be helping to\n"
            + "compress anything.\n"
            + " \n"
            + "It is important to spell\n"
            + "check this document. On\n"
            + "the other hand, a\n"
            + "misspelled word isn't\n"
            + "the end of the world.\n"
            + "Nothing in the rest of\n"
            + "this paragraph needs to\n"
            + "be changed. Things can\n"
            + "be added after it.\n"
            + " \n"
            + "This paragraph contains\n"
            + "important new additions\n"
            + "to this document.\n";

    private final String normalDiffOutput = "0a1,6\n"
            + "> This is an important\n"
            + "> notice! It should\n"
            + "> therefore be located at\n"
            + "> the beginning of this\n"
            + "> document!\n"
            + ">\n"
            + "8,14c14\n"
            + "< compress the size of the\n"
            + "< changes.\n"
            + "<\n"
            + "< This paragraph contains\n"
            + "< text that is outdated.\n"
            + "< It will be deleted in the\n"
            + "< near future.\n"
            + "---\n"
            + "> compress anything.\n"
            + "17c17\n"
            + "< check this dokument. On\n"
            + "---\n"
            + "> check this document. On\n"
            + "24a25,28\n"
            + ">\n"
            + "> This paragraph contains\n"
            + "> important new additions\n"
            + "> to this document.";


    @Test
    public void diff_abcabba_cbabac_1D2D3IB6D7IC() {
        final String sequenceA = "abcabba";
        final String sequenceB = "cbabac";
        final String expectedScript = "1D 2D 4D 5Ia 7Ic";

        EditScriptFormatter formatter = new SimpleEditScriptFormatter();
        ImmutableList<EditCommand> editCommands = new ShortestPathGreedyAlgorithm().diff(sequenceA, sequenceB);

        Assert.assertEquals(expectedScript, formatter.format(editCommands));
    }

    @Test
    public void diff_CalledTwice_SameResult() {
        final String sequenceA = "abcabba";
        final String sequenceB = "cbabac";

        ShortestPathGreedyAlgorithm algorithm = new ShortestPathGreedyAlgorithm();
        String result1 = algorithm.diff(sequenceA, sequenceB).toString();
        String result2 = algorithm.diff(sequenceA, sequenceB).toString();

        assertEquals(result1, result2);
    }

    @Test
    public void diff_lines_() {
        Object[] linesA = originalSequence.split("\\n");
        Object[] linesB = newSequence.split("\\n");
        EditScriptFormatter formatter = new NormalDiffOutputFormatter();
        ImmutableList<EditCommand> editCommands = new ShortestPathGreedyAlgorithm().diff(linesA, linesB);

        Assert.assertEquals(normalDiffOutput, formatter.format(editCommands));
    }
}
