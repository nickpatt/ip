package nick.ui;

import java.util.Scanner;

import nick.task.Task;
import nick.task.TaskList;

/**
 * Handles all interaction with the user: reading commands and printing messages.
 */
public class Ui {
    private static final String LINE = "    ____________________________________________________________";

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Reads the next command line entered by the user.
     *
     * @return The line of input.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Returns whether there is more input to read.
     *
     * @return True if another line of input is available.
     */
    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    /**
     * Shows the welcome message greeting the user.
     */
    public void showWelcome() {
        reply("     Hello! I'm Nick.", "     What can I do for you?");
    }

    /**
     * Shows the farewell message shown when the user exits.
     */
    public void showGoodbye() {
        reply("     Bye. Hope to see you again soon!");
    }

    /**
     * Shows an error message to the user.
     *
     * @param message The error description.
     */
    public void showError(String message) {
        reply("     OOPS!!! " + message);
    }

    /**
     * Shows the message confirming a task was added.
     *
     * @param task The task that was added.
     * @param count The number of tasks now in the list.
     */
    public void showAdded(Task task, int count) {
        reply("     Got it. I've added this task:",
                "       " + task.toDisplayString(),
                "     Now you have " + count + " tasks in the list.");
    }

    /**
     * Shows the message confirming a task was removed.
     *
     * @param task The task that was removed.
     * @param count The number of tasks now in the list.
     */
    public void showRemoved(Task task, int count) {
        reply("     Noted. I've removed this task:",
                "       " + task.toDisplayString(),
                "     Now you have " + count + " tasks in the list.");
    }

    /**
     * Shows the message confirming a task was marked as done.
     *
     * @param task The task that was marked.
     */
    public void showMarked(Task task) {
        reply("     Nice! I've marked this task as done:",
                "       " + task.toDisplayString());
    }

    /**
     * Shows the message confirming a task was marked as not done.
     *
     * @param task The task that was unmarked.
     */
    public void showUnmarked(Task task) {
        reply("     OK, I've marked this task as not done yet:",
                "       " + task.toDisplayString());
    }

    /**
     * Shows the full list of tasks, numbered.
     *
     * @param tasks The tasks to display.
     */
    public void showList(TaskList tasks) {
        String[] lines = new String[tasks.size() + 1];
        lines[0] = "     Here are the tasks in your list:";
        for (int i = 0; i < tasks.size(); i++) {
            lines[i + 1] = "     " + (i + 1) + "." + tasks.get(i).toDisplayString();
        }
        reply(lines);
    }

    /**
     * Prints the given lines wrapped between two divider lines.
     *
     * @param lines The lines to print.
     */
    private void reply(String... lines) {
        System.out.println(LINE);
        for (String line : lines) {
            System.out.println(line);
        }
        System.out.println(LINE);
    }
}
