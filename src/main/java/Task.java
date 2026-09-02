/**
 * Represents a task with a description, a done status, and a type.
 * The type is one of "T" (todo), "D" (deadline), or "E" (event).
 * Deadlines carry a "by" time, and events carry a "from" and "to" time.
 */
public class Task {
    protected String description;
    protected boolean isDone;
    protected String type;
    protected String by;
    protected String from;
    protected String to;

    /**
     * Creates a task with the given description and type that is initially not done.
     *
     * @param description Description of the task.
     * @param type Type of the task: "T", "D", or "E".
     */
    public Task(String description, String type) {
        this.description = description;
        this.isDone = false;
        this.type = type;
    }

    /**
     * Returns the status icon of the task: "X" if done, or a space if not done.
     *
     * @return Status icon of the task.
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Returns the task formatted for display, including its type icon, status icon,
     * description, and any date/time details.
     *
     * @return The task as a display string.
     */
    public String toDisplayString() {
        String details = "";
        if (type.equals("D")) {
            details = " (by: " + by + ")";
        } else if (type.equals("E")) {
            details = " (from: " + from + " to: " + to + ")";
        }
        return "[" + type + "][" + getStatusIcon() + "] " + description + details;
    }
}
