package diff;

import com.google.common.collect.ImmutableList;

public interface EditScriptFormatter {

	public String format(ImmutableList<EditCommand> commands);

}