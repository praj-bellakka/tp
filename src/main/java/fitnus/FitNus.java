package fitnus;

import fitnus.command.ExitCommand;
import fitnus.command.ListFoodDatabaseCommand;
import fitnus.parser.Parser;

import java.util.Scanner;

public class FitNus {
    public static void main(String[] args) {
        User user = new User(0, 1000); //placeholder inputs, to get user's actual input later
        FoodDatabase db = new FoodDatabase();
        EntryDatabase ed = new EntryDatabase();
        Parser parser = new Parser();
        Ui ui = new Ui();
        String userInput;
        Scanner in = new Scanner(System.in);
        boolean canExit = false;
        try {
            // Welcome Message
            ui.printWelcomeMessage();
            ui.printHelpMessage();

            // Load From Storage
            Storage.createDirectoryAndFiles();
            Storage.initialiseDatabase(db);
            db.listFoods();
            Storage.saveDatabase(db);
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
                    ui.println(new ExitCommand().execute());
                    break;
                case "add":
                    break;
                case "remove":
                    //TODO: add remove function
                    break;
                case "list":
                    ui.println(new ListFoodDatabaseCommand(db).execute());
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
