import java.util.Scanner;

/**
 * A command line chatbot that lets the user add, list, mark, and unmark tasks.
 * Tasks can be todos, deadlines, or events.
 */
public class Nick {
    private static final String LINE = "    ____________________________________________________________";

    /**
     * Runs the chatbot, reading commands from standard input until the user types "bye".
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        reply("     Hello! I'm Nick.", "     What can I do for you?");

        TaskList tasks = new TaskList();

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String input = sc.nextLine();

            if (input.equals("bye")) {
                reply("     Bye. Hope to see you again soon!");
                break;
            }

            try {
                handle(input, tasks);
            } catch (NickException e) {
                reply("     OOPS!!! " + e.getMessage());
            }
        }
        sc.close();
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
            reply(renderList(tasks));
        } else if (input.startsWith("mark ")) {
            int index = Integer.parseInt(input.substring(5)) - 1;
            tasks.get(index).markAsDone();
            reply("     Nice! I've marked this task as done:",
                    "       " + tasks.get(index).toDisplayString());
        } else if (input.startsWith("unmark ")) {
            int index = Integer.parseInt(input.substring(7)) - 1;
            tasks.get(index).markAsNotDone();
            reply("     OK, I've marked this task as not done yet:",
                    "       " + tasks.get(index).toDisplayString());
        } else if (input.startsWith("todo")) {
            String description = input.substring(4).trim();
            if (description.isEmpty()) {
                throw new NickException("The description of a todo cannot be empty.");
            }
            Task task = new Todo(description);
            tasks.add(task);
            replyAdded(task, tasks.size());
        } else if (input.startsWith("deadline ")) {
            String rest = input.substring(9);
            int byIndex = rest.indexOf(" /by ");
            String description = rest.substring(0, byIndex);
            String by = rest.substring(byIndex + 5);
            Task task = new Deadline(description, by);
            tasks.add(task);
            replyAdded(task, tasks.size());
        } else if (input.startsWith("event ")) {
            String rest = input.substring(6);
            int fromIndex = rest.indexOf(" /from ");
            int toIndex = rest.indexOf(" /to ");
            String description = rest.substring(0, fromIndex);
            String from = rest.substring(fromIndex + 7, toIndex);
            String to = rest.substring(toIndex + 5);
            Task task = new Event(description, from, to);
            tasks.add(task);
            replyAdded(task, tasks.size());
        } else {
            throw new NickException("I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * Prints the given lines wrapped between two divider lines.
     *
     * @param lines The lines to print as the chatbot's reply.
     */
    private static void reply(String... lines) {
        System.out.println(LINE);
        for (String line : lines) {
            System.out.println(line);
        }
        System.out.println(LINE);
    }

    /**
     * Builds the lines that display the numbered list of tasks.
     *
     * @param tasks The tasks to render.
     * @return The heading followed by one numbered line per task.
     */
    private static String[] renderList(TaskList tasks) {
        String[] lines = new String[tasks.size() + 1];
        lines[0] = "     Here are the tasks in your list:";
        for (int i = 0; i < tasks.size(); i++) {
            lines[i + 1] = "     " + (i + 1) + "." + tasks.get(i).toDisplayString();
        }
        return lines;
    }

    /**
     * Prints the confirmation message shown after a task is added.
     *
     * @param task The task that was added.
     * @param count The number of tasks currently in the list.
     */
    private static void replyAdded(Task task, int count) {
        reply("     Got it. I've added this task:",
                "       " + task.toDisplayString(),
                "     Now you have " + count + " tasks in the list.");
    }
}
