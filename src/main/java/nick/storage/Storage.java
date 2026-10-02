package nick.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import nick.task.TaskList;

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
}
