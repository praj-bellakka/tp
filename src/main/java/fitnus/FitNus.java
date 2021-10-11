package fitnus;

import fitnus.command.Command;
import fitnus.command.ExitCommand;
import fitnus.command.HelpCommand;
import fitnus.parser.Parser;

public class FitNus {
    public static void main(String[] args) {
        User user = new User(0, 1000); //placeholder inputs, to get user's actual input later
        FoodDatabase fd = new FoodDatabase();
        EntryDatabase ed = new EntryDatabase();
        Parser parser = new Parser();
        Ui ui = new Ui();


        try {
            // Welcome Message
            Ui.printWelcomeMessage();
            Ui.println(new HelpCommand().execute(ed, fd, user));

            // Load From Storage
            Storage.createDirectoryAndFiles();
            Storage.initialiseFoodDatabase(fd);
            Storage.initialiseEntryDatabase(ed);
            Storage.initialiseUser(user);
            Ui.println("Food database:" + System.lineSeparator()
                    + fd.listFoods());
            Ui.println("Entry database:" + System.lineSeparator()
                    + ed.listEntries());
            Ui.println(user.listUserData());
            Storage.saveFoodDatabase(fd);
            Storage.saveEntryDatabase(ed);
            Storage.saveUserData(user);
        } catch (Exception e) {
            Ui.println(e.getMessage());
        }


        while (true) {
            String userInput;
            Command inputType;
            try {
                userInput = ui.readInput();
                inputType = parser.parseCommandType(userInput);
                Ui.println(inputType.execute(ed, fd, user));

                if (inputType instanceof ExitCommand) {
                    break;
                }
            } catch (Exception e) {
                Ui.println(e.getMessage());
                //ui.println(new InvalidCommand().execute(ed, fd, user));
            }
        }
    }
}
