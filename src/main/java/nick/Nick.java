package nick;

import java.io.IOException;

import nick.storage.Storage;
import nick.task.Deadline;
import nick.task.Event;
import nick.task.Task;
import nick.task.TaskList;
import nick.task.Todo;
import nick.ui.Ui;

/**
 * A command line chatbot that lets the user add, list, mark, unmark, and delete tasks.
 * Tasks can be todos, deadlines, or events, and are saved to disk between runs.
 */
public class Nick {
    private static final String BY_MARKER = " /by ";
    private static final String FROM_MARKER = " /from ";
    private static final String TO_MARKER = " /to ";
    private static final String DATA_FILE = "data/nick.txt";

    private static final Storage storage = new Storage(DATA_FILE);
    private static final Ui ui = new Ui();

    /**
     * Runs the chatbot, reading commands until the user types "bye".
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        ui.showWelcome();

        TaskList tasks;
        try {
            tasks = storage.load();
        } catch (IOException e) {
            tasks = new TaskList();
        }

        while (ui.hasNextCommand()) {
            String input = ui.readCommand();

            if (input.equals("bye")) {
                ui.showGoodbye();
                break;
            }

            try {
                handle(input, tasks);
                storage.save(tasks);
            } catch (NickException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showError("I couldn't save your tasks: " + e.getMessage());
            }
        }
    }

    /**
     * Carries out a single user command.
     *
     * @param input The full command line entered by the user.
     * @param tasks The list of tasks to act on.
     * @throws NickException If the command is unknown or its argument is invalid.
     */
    private static void handle(String input, TaskList tasks) throws NickException {
        if (input.equals("list")) {
            ui.showList(tasks);
        } else if (input.startsWith("mark")) {
            int index = parseTaskNumber(input.substring(4), tasks);
            tasks.get(index).markAsDone();
            ui.showMarked(tasks.get(index));
        } else if (input.startsWith("unmark")) {
            int index = parseTaskNumber(input.substring(6), tasks);
            tasks.get(index).markAsNotDone();
            ui.showUnmarked(tasks.get(index));
        } else if (input.startsWith("delete")) {
            int index = parseTaskNumber(input.substring(6), tasks);
            Task removed = tasks.remove(index);
            ui.showRemoved(removed, tasks.size());
        } else if (input.startsWith("todo")) {
            String description = input.substring(4).trim();
            if (description.isEmpty()) {
                throw new NickException("The description of a todo cannot be empty.");
            }
            Task task = new Todo(description);
            tasks.add(task);
            ui.showAdded(task, tasks.size());
        } else if (input.startsWith("deadline")) {
            String rest = input.substring(8).trim();
            int byIndex = rest.indexOf(BY_MARKER);
            if (byIndex < 0) {
                throw new NickException("A deadline needs a '/by' time, e.g. deadline return book /by Sunday.");
            }
            String description = rest.substring(0, byIndex).trim();
            String by = rest.substring(byIndex + BY_MARKER.length()).trim();
            if (description.isEmpty() || by.isEmpty()) {
                throw new NickException("A deadline needs both a description and a '/by' time.");
            }
            Task task = new Deadline(description, by);
            tasks.add(task);
            ui.showAdded(task, tasks.size());
        } else if (input.startsWith("event")) {
            String rest = input.substring(5).trim();
            int fromIndex = rest.indexOf(FROM_MARKER);
            int toIndex = rest.indexOf(TO_MARKER);
            if (fromIndex < 0 || toIndex < 0 || toIndex < fromIndex) {
                throw new NickException("An event needs a '/from' and a '/to' time, "
                        + "e.g. event meeting /from Mon 2pm /to 4pm.");
            }
            String description = rest.substring(0, fromIndex).trim();
            String from = rest.substring(fromIndex + FROM_MARKER.length(), toIndex).trim();
            String to = rest.substring(toIndex + TO_MARKER.length()).trim();
            if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new NickException("An event needs a description, a '/from' time, and a '/to' time.");
            }
            Task task = new Event(description, from, to);
            tasks.add(task);
            ui.showAdded(task, tasks.size());
        } else {
            throw new NickException("I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * Parses a task number argument into a zero-based index, checking that it is
     * a number and that it refers to an existing task.
     *
     * @param argument The text following the mark or unmark keyword.
     * @param tasks The current list of tasks.
     * @return The zero-based index of the referenced task.
     * @throws NickException If the argument is missing, not a number, or out of range.
     */
    private static int parseTaskNumber(String argument, TaskList tasks) throws NickException {
        String trimmed = argument.trim();
        if (trimmed.isEmpty()) {
            throw new NickException("Please tell me which task number to update, e.g. mark 2.");
        }

        int index;
        try {
            index = Integer.parseInt(trimmed) - 1;
        } catch (NumberFormatException e) {
            throw new NickException("'" + trimmed + "' is not a valid task number.");
        }

        if (index < 0 || index >= tasks.size()) {
            throw new NickException("There is no task number " + trimmed + " in your list.");
        }
        return index;
    }
}
