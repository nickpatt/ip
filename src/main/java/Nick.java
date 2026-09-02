import java.util.Scanner;

public class Nick {
    private static final String LINE = "    ____________________________________________________________";

    public static void main(String[] args) {
        System.out.println(LINE);
        System.out.println("     Hello! I'm Nick.");
        System.out.println("     What can I do for you?");
        System.out.println(LINE);

        String[] tasks = new String[100];
        boolean[] done = new boolean[100];
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
                    String icon = done[i] ? "X" : " ";
                    System.out.println("     " + (i + 1) + ".[" + icon + "] " + tasks[i]);
                }
                System.out.println(LINE);
            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5)) - 1;
                done[index] = true;
                System.out.println(LINE);
                System.out.println("     Nice! I've marked this task as done:");
                System.out.println("       [X] " + tasks[index]);
                System.out.println(LINE);
            } else {
                tasks[count] = input;
                count++;
                System.out.println(LINE);
                System.out.println("     added: " + input);
                System.out.println(LINE);
            }
        }
        sc.close();
    }
}
