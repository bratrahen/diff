public class EditCommand {
	public enum Type {INSERT, DELETE};
	
	final public Type type;
	final public int position;
	final public Object element;
	
	private EditCommand(Type type, int position, Object element)
	{
		this.type = type;
		this.position = position;
		this.element = element;
	}
	
	public static EditCommand insert(int position, Object element)
	{
		return new EditCommand(EditCommand.Type.INSERT, position, element);
	}
	
	public static EditCommand delete(int position, Object element)
	{
		return new EditCommand(EditCommand.Type.DELETE, position, element);
	}
	
	public String toString()
	{
		return position + (type == Type.DELETE ? "- ":"+ ") + element; 
	}
}
