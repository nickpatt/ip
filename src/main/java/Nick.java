import java.util.Scanner;

public class Nick {
    private static final String LINE = "    ____________________________________________________________";

    public static void main(String[] args) {
        System.out.println(LINE);
        System.out.println("     Hello! I'm Nick.");
        System.out.println("     What can I do for you?");
        System.out.println(LINE);

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            if (input.equals("bye")) {
                System.out.println(LINE);
                System.out.println("     Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
            }
            System.out.println(LINE);
            System.out.println("     " + input);
            System.out.println(LINE);
        }
        sc.close();
    }
}
