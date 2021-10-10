package fitnus;

import fitnus.command.Command;
import fitnus.command.ExitCommand;
import fitnus.command.HelpCommand;
import fitnus.command.InvalidCommand;
import fitnus.parser.Parser;

import java.util.Scanner;

public class FitNus {
    public static void main(String[] args) {
        User user = new User(0, 1000); //placeholder inputs, to get user's actual input later
        FoodDatabase fd = new FoodDatabase();
        EntryDatabase ed = new EntryDatabase();
        Parser parser = new Parser();
        Ui ui = new Ui();


        try {
            // Welcome Message
            ui.printWelcomeMessage();
            ui.println(new HelpCommand().execute(ed, fd, user));

            // Load From Storage
            Storage.createDirectoryAndFiles();
            Storage.initialiseFoodDatabase(fd);
            Storage.initialiseEntryDatabase(ed);
            ui.println("Food database:" + System.lineSeparator()
                    + fd.listFoods());
            ui.println("Entry database:" + System.lineSeparator()
                    + ed.listEntries());
            Storage.saveFoodDatabase(fd);
            Storage.saveEntryDatabase(ed);
        } catch (Exception e) {
            ui.println(e.getMessage());
        }


        while (true) {
            String userInput;
            Command inputType;
            try {
                userInput = ui.readInput();
                inputType = parser.parseCommandType(userInput);
                ui.println(inputType.execute(ed, fd, user));

                if (inputType instanceof ExitCommand) {
                    break;
                }
            } catch (Exception e) {
                ui.println(e.getMessage());
                //ui.println(new InvalidCommand().execute(ed, fd, user));
            }
        }
    }
}
