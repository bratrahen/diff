import com.google.common.collect.ImmutableList;

interface EditScriptFormatter {

    public String format(ImmutableList<EditCommand> commands);

}