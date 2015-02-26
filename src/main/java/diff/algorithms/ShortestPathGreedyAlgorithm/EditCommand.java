package diff.algorithms.ShortestPathGreedyAlgorithm;

public class EditCommand {
    final public Type type;
    final public int position;
    final public Object element;

    private EditCommand(Type type, int position, Object element) {
        this.type = type;
        this.position = position;
        this.element = element;
    }

    public static EditCommand insert(Object element, int position) {
        return new EditCommand(EditCommand.Type.INSERT, position, element);
    }

    public static EditCommand delete(Object element, int position) {
        return new EditCommand(EditCommand.Type.DELETE, position, element);
    }

    public String toString() {
        return position + (type == Type.DELETE ? "- " : "+ ") + element;
    }

    public enum Type {INSERT, DELETE}
}
