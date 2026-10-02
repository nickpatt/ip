package nick;

import java.io.IOException;

import nick.parser.Parser;
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
    private static final String DATA_FILE = "data/nick.txt";

    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

    /**
     * Creates the chatbot backed by the data file at the given path, loading any
     * previously saved tasks.
     *
     * @param filePath Relative path to the data file.
     */
    public Nick(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showError("I couldn't load your saved tasks, starting with an empty list.");
            tasks = new TaskList();
        }
    }

    /**
     * Runs the chatbot, reading commands until the user types "bye".
     */
    public void run() {
        ui.showWelcome();

        while (ui.hasNextCommand()) {
            String input = ui.readCommand();

            if (Parser.commandWord(input).equals("bye")) {
                ui.showGoodbye();
                break;
            }

            try {
                handle(input);
                storage.save(tasks);
            } catch (NickException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showError("I couldn't save your tasks: " + e.getMessage());
            }
        }
    }

    /**
     * Starts the chatbot.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Nick(DATA_FILE).run();
    }

    /**
     * Carries out a single user command.
     *
     * @param input The full command line entered by the user.
     * @throws NickException If the command is unknown or its argument is invalid.
     */
    private void handle(String input) throws NickException {
        String command = Parser.commandWord(input);
        String argument = Parser.argument(input);

        switch (command) {
        case "list":
            ui.showList(tasks);
            break;
        case "mark": {
            int index = Parser.parseTaskNumber(argument, tasks.size());
            tasks.get(index).markAsDone();
            ui.showMarked(tasks.get(index));
            break;
        }
        case "unmark": {
            int index = Parser.parseTaskNumber(argument, tasks.size());
            tasks.get(index).markAsNotDone();
            ui.showUnmarked(tasks.get(index));
            break;
        }
        case "delete": {
            int index = Parser.parseTaskNumber(argument, tasks.size());
            Task removed = tasks.remove(index);
            ui.showRemoved(removed, tasks.size());
            break;
        }
        case "todo": {
            if (argument.isEmpty()) {
                throw new NickException("The description of a todo cannot be empty.");
            }
            Task task = new Todo(argument);
            tasks.add(task);
            ui.showAdded(task, tasks.size());
            break;
        }
        case "deadline": {
            String[] parts = Parser.parseDeadline(argument);
            Task task = new Deadline(parts[0], parts[1]);
            tasks.add(task);
            ui.showAdded(task, tasks.size());
            break;
        }
        case "event": {
            String[] parts = Parser.parseEvent(argument);
            Task task = new Event(parts[0], parts[1], parts[2]);
            tasks.add(task);
            ui.showAdded(task, tasks.size());
            break;
        }
        default:
            throw new NickException("I'm sorry, but I don't know what that means :-(");
        }
    }
}
