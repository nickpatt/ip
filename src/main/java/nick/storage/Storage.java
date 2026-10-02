package nick.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import nick.task.Deadline;
import nick.task.Event;
import nick.task.Task;
import nick.task.TaskList;
import nick.task.Todo;

/**
 * Saves tasks to, and loads them from, a data file on disk.
 */
public class Storage {
    private final String filePath;

    /**
     * Creates a storage backed by the file at the given path.
     *
     * @param filePath Relative path to the data file, e.g. "data/nick.txt".
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves all tasks to the data file, creating the parent folder if needed.
     * Each task is written on its own line in save format.
     *
     * @param tasks The tasks to save.
     * @throws IOException If the file cannot be written.
     */
    public void save(TaskList tasks) throws IOException {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null) {
            parent.mkdirs();
        }

        try (FileWriter writer = new FileWriter(file)) {
            for (int i = 0; i < tasks.size(); i++) {
                writer.write(tasks.get(i).toSaveFormat() + System.lineSeparator());
            }
        }
    }

    /**
     * Loads tasks from the data file.
     *
     * @return The tasks read from the file.
     * @throws IOException If the file exists but cannot be read.
     */
    public TaskList load() throws IOException {
        TaskList tasks = new TaskList();
        File file = new File(filePath);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                tasks.add(parseTask(line));
            }
        }
        return tasks;
    }

    /**
     * Parses a single save-format line into the matching task.
     *
     * @param line A line in the form {@code <type> | <doneFlag> | <description> | ...}.
     * @return The task represented by the line.
     */
    private Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        switch (type) {
        case "D":
            task = new Deadline(description, parts[3]);
            break;
        case "E":
            task = new Event(description, parts[3], parts[4]);
            break;
        default:
            task = new Todo(description);
            break;
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}
