package fitnus;

import fitnus.parser.Parser;

import java.util.Scanner;

public class FitNus {
    public static void main(String[] args) {
        FoodDatabase database = new FoodDatabase();
        EntryDatabase ed = new EntryDatabase();
        Parser parser = new Parser();
        String userInput;
        Scanner in = new Scanner(System.in);
        boolean canExit = false;
        try {
            Storage.createDirectoryAndFiles();
            Storage.initialiseDatabase(database);
            database.listFoods();
            Storage.saveDatabase(database);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        /* Command handler
        TODO: refactor handler to separate classes.
         */
        while (!canExit) {
            try {
                userInput = in.nextLine().trim();
                String inputType = parser.parseInputType(userInput);
                switch (inputType) {
                    //TODO: Refactor strings to enum
                case "exit":
                    canExit = true;
                    break;
                case "add":
                    break;
                case "remove":
                    //TODO: add remove function
                    break;
                case "list":
                    //TODO: add list function
                    break;
                case "calorie":
                    //TODO: add calorie function
                    break;
                case "gender":
                    //TODO: add gender function
                    break;
                case "help":
                    //TODO: add help function
                    break;
                default:
                    System.out.println("couldnt find function");
                    break;
                }
            } catch (NullPointerException e) {
                System.out.println("Wrong format");
                e.printStackTrace();
            }
        }
    }
}
