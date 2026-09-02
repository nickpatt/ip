import java.util.Scanner;

public class Nick {
    private static final String LINE = "    ____________________________________________________________";

    public static void main(String[] args) {
        System.out.println(LINE);
        System.out.println("     Hello! I'm Nick.");
        System.out.println("     What can I do for you?");
        System.out.println(LINE);

        String[] tasks = new String[100];
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
                for (int i = 0; i < count; i++) {
                    System.out.println("     " + (i + 1) + ". " + tasks[i]);
                }
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
