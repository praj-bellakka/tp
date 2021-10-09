package fitnus;

import java.util.Locale;
import java.util.Scanner;

public class Ui {
    private Scanner sc = new Scanner(System.in);
    public Ui() {
    }

    public static final String WELCOME_MESSAGE = "------------------ \n"
            + "Welcome to FitNUS Tracker!";

    public void printWelcomeMessage() {
        println(WELCOME_MESSAGE);
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void print(String message) {
        System.out.print(message);
    }

    public String readInput() {
        String userInput = "";
        do {
            userInput = sc.nextLine().toLowerCase().trim();
            if (userInput.equals("")) {
                this.println("Input cannot be empty! Please try again");
            }
        } while (userInput.equals(""));
        return userInput;
    }
}
