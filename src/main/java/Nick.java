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
        System.out.println(LINE);
        System.out.println("     Hello! I'm Nick.");
        System.out.println("     What can I do for you?");
        System.out.println(LINE);

        Task[] tasks = new Task[100];
        int count = 0;

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println(LINE);
                System.out.println("     Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
            } else if (input.equals("list")) {
                System.out.println(LINE);
                System.out.println("     Here are the tasks in your list:");
                for (int i = 0; i < count; i++) {
                    System.out.println("     " + (i + 1) + "." + tasks[i].toDisplayString());
                }
                System.out.println(LINE);
            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5)) - 1;
                tasks[index].markAsDone();
                System.out.println(LINE);
                System.out.println("     Nice! I've marked this task as done:");
                System.out.println("       " + tasks[index].toDisplayString());
                System.out.println(LINE);
            } else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                tasks[index].markAsNotDone();
                System.out.println(LINE);
                System.out.println("     OK, I've marked this task as not done yet:");
                System.out.println("       " + tasks[index].toDisplayString());
                System.out.println(LINE);
            } else if (input.startsWith("todo ")) {
                Task task = new Todo(input.substring(5));
                tasks[count] = task;
                count++;
                printAdded(task, count);
            } else if (input.startsWith("deadline ")) {
                String rest = input.substring(9);
                int byIndex = rest.indexOf(" /by ");
                String description = rest.substring(0, byIndex);
                String by = rest.substring(byIndex + 5);
                Task task = new Deadline(description, by);
                tasks[count] = task;
                count++;
                printAdded(task, count);
            } else if (input.startsWith("event ")) {
                String rest = input.substring(6);
                int fromIndex = rest.indexOf(" /from ");
                int toIndex = rest.indexOf(" /to ");
                String description = rest.substring(0, fromIndex);
                String from = rest.substring(fromIndex + 7, toIndex);
                String to = rest.substring(toIndex + 5);
                Task task = new Event(description, from, to);
                tasks[count] = task;
                count++;
                printAdded(task, count);
            }
        }
        sc.close();
    }

    /**
     * Prints the confirmation message shown after a task is added.
     *
     * @param task The task that was added.
     * @param count The number of tasks currently in the list.
     */
    private static void printAdded(Task task, int count) {
        System.out.println(LINE);
        System.out.println("     Got it. I've added this task:");
        System.out.println("       " + task.toDisplayString());
        System.out.println("     Now you have " + count + " tasks in the list.");
        System.out.println(LINE);
    }
}
