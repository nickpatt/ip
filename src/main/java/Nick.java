import java.util.Scanner;

public class Nick {
    /** A horizontal line used to separate chatbot responses. */
    private static final String LINE = "    ____________________________________________________________";

    public static void main(String[] args) {
        // Greeting shown once when the program starts.
        String greeting = LINE + "\n" +
                "     Hello! I'm Nick.\n" +
                "     What can I do for you?\n" +
                LINE;
        System.out.println(greeting);
    }
}
