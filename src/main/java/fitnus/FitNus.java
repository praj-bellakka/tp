package fitnus;

import fitnus.command.Command;
import fitnus.command.ExitCommand;
import fitnus.command.ListFoodDatabaseCommand;
import fitnus.parser.Parser;

import java.util.Scanner;

public class FitNus {
    public static void main(String[] args) {
        User user = new User(0, 1000); //placeholder inputs, to get user's actual input later
        FoodDatabase fd = new FoodDatabase();
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
            Storage.initialiseDatabase(fd);
            fd.listFoods();
            Storage.saveDatabase(fd);
        } catch (Exception e) {
            ui.println(e.getMessage());
        }


        /* Command handler
        TODO: refactor handler to separate classes.
         */
        while (!canExit) {
            try {
                userInput = in.nextLine().trim();
                Command inputType = parser.parseCommandType(userInput);
                String message = inputType.execute(ed, fd, user);
                ui.println(message);
            } catch (NullPointerException e) {
                System.out.println("Wrong format");
                e.printStackTrace();
            } catch (FitNusException e) {
                ui.println(e.getMessage());
            }
        }
    }
}
