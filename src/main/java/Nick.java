import java.util.Scanner;

/**
 * A command line chatbot that lets the user add, list, mark, and unmark tasks.
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
                    Task t = tasks[i];
                    System.out.println("     " + (i + 1) + ".[" + t.getStatusIcon() + "] " + t.getDescription());
                }
                System.out.println(LINE);
            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5)) - 1;
                tasks[index].markAsDone();
                System.out.println(LINE);
                System.out.println("     Nice! I've marked this task as done:");
                System.out.println("       [" + tasks[index].getStatusIcon() + "] " + tasks[index].getDescription());
                System.out.println(LINE);
            } else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                tasks[index].markAsNotDone();
                System.out.println(LINE);
                System.out.println("     OK, I've marked this task as not done yet:");
                System.out.println("       [" + tasks[index].getStatusIcon() + "] " + tasks[index].getDescription());
                System.out.println(LINE);
            } else {
                tasks[count] = new Task(input);
                count++;
                System.out.println(LINE);
                System.out.println("     added: " + input);
                System.out.println(LINE);
            }
        }
        sc.close();
    }
}
