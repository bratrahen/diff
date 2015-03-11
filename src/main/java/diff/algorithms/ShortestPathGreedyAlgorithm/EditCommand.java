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

    public static EditCommand change(Object element, int position) {
        return new EditCommand(EditCommand.Type.CHANGE, position, element);
    }

    public boolean isInsert() {
        return type == Type.INSERT;
    }

    public boolean isChange() {
        return type == Type.CHANGE;
    }

    public boolean isDelete() {
        return type == Type.DELETE;
    }

    public String toString() {
        return position + (type == Type.DELETE ? "- " : "+ ") + element;
    }

    public enum Type {INSERT, DELETE, CHANGE, NULL}
}
