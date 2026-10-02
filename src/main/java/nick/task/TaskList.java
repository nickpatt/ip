package nick.task;

/**
 * Holds the tasks entered by the user and provides access to them.
 * Assumes there will be no more than {@value #MAX_TASKS} tasks.
 */
public class TaskList {
    private static final int MAX_TASKS = 100;

    private final Task[] tasks = new Task[MAX_TASKS];
    private int count = 0;

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void add(Task task) {
        tasks[count] = task;
        count++;
    }

    /**
     * Returns the task at the given zero-based index.
     *
     * @param index Zero-based position of the task.
     * @return The task at that position.
     */
    public Task get(int index) {
        return tasks[index];
    }

    /**
     * Returns the number of tasks currently in the list.
     *
     * @return The task count.
     */
    public int size() {
        return count;
    }
}
