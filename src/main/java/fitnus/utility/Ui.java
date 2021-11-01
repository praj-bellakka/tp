package fitnus.utility;

import fitnus.command.FindFoodCommand;
import fitnus.tracker.Entry;
import fitnus.tracker.Food;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

    public String[] readIndexesInput() {
        String userInput = "";
        do {
            userInput = sc.nextLine().toLowerCase().trim();
            if (userInput.equals("")) {
                println("Input cannot be empty! Please try again");
            }
        } while (userInput.equals(""));
        assert !userInput.trim().equals("") : "input cannot be empty";
        return userInput.split(" ");
    }

    private static void printNoMatchingResults() {
        String message = " Sorry, no matching results found!";
        System.out.println(message);
    }

    public static void printMatchingFoods(ArrayList<Food> matchingFoods) {
        if (matchingFoods.size() == 0) {
            printNoMatchingResults();
            return;
        }
        System.out.println(" Here are the matching foods in your database:");
        for (int i = 1; i <= matchingFoods.size(); i++) {
            System.out.println(" " + i + "."
                    + matchingFoods.get(i - 1).toString());
        }
    }

    public static void printMatchingEntries(ArrayList<Entry> matchingEntries) {
        if (matchingEntries.size() == 0) {
            printNoMatchingResults();
            return;
        }
        System.out.println(" Here are the matching entries in your database:");
        for (int i = 1; i <= matchingEntries.size(); i++) {
            System.out.println(" " + i + "."
                    + matchingEntries.get(i - 1).toString());
        }
    }

    public static void printMatchingSuggestions(ArrayList<Food> matchingFoods) {
        if (matchingFoods.size() == 0) {
            printNoMatchingResults();
            return;
        }
        System.out.println(" Here are some suggestions:");
        for (int i = 1; i <= matchingFoods.size(); i++) {
            System.out.println(" " + i + "."
                    + matchingFoods.get(i - 1).toString());
        }
    }
}
