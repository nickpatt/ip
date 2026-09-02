/**
 * Represents a deadline: a task that needs to be done before a specific date or time.
 */
public class Deadline extends Task {
    protected String by;

    /**
     * Creates a deadline with the given description and due time.
     *
     * @param description Description of the deadline.
     * @param by The date or time the task is due by.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getTypeIcon() {
        return "D";
    }

    @Override
    public String toDisplayString() {
        return super.toDisplayString() + " (by: " + by + ")";
    }
}
