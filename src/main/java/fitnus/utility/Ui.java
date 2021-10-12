package fitnus.utility;

import java.util.Scanner;

public class Ui {
    private final Scanner sc = new Scanner(System.in);

    public static final String WELCOME_MESSAGE = "------------------ \n"
            + "Welcome to FitNUS Tracker!";

    public static void printWelcomeMessage() {
        println(WELCOME_MESSAGE);
    }

    public static void println(String message) {
        System.out.println(message);
    }

    public static void print(String message) {
        System.out.print(message);
    }

    public static void printPreloadDatabaseError() {
        System.out.println("Error encountered while preloading database :("
                + " some data may have been lost");
    }

    public static void printPreloadUserError() {
        System.out.println("Error encountered while preloading user data :(");
    }

    public String readInput() {
        String userInput = "";
        do {
            userInput = sc.nextLine().toLowerCase().trim();
            if (userInput.equals("")) {
                println("Input cannot be empty! Please try again");
            }
        } while (userInput.equals(""));
        assert !userInput.trim().equals("") : "input cannot be empty";
        return userInput;
    }
}
