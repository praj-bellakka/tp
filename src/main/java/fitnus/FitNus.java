package fitnus;

import fitnus.command.Command;
import fitnus.command.ExitCommand;
import fitnus.command.HelpCommand;
import fitnus.parser.Parser;

import java.io.IOException;

public class FitNus {

    private static void initialiseFitNus(FoodDatabase fd, EntryDatabase ed, User user) {
        // Load From Storage
        try {
            Storage.createDirectoryAndFiles();
            Storage.initialiseFoodDatabase(fd);
            Storage.initialiseEntryDatabase(ed);
            Storage.initialiseUser(user);
        } catch (IOException e) {
            Ui.println("I/O error! " + e.getMessage());
        } catch (FitNusException e) {
            Ui.println(e.getMessage());
        }
    }

    private static void saveFitNus(FoodDatabase fd, EntryDatabase ed, User user) {
        try {
            Storage.saveFoodDatabase(fd);
            Storage.saveEntryDatabase(ed);
            Storage.saveUserData(user);
        } catch (IOException e) {
            Ui.println("I/O error! " + e.getMessage());
        }
    }

    private static void run(Ui ui, Parser parser, FoodDatabase fd, EntryDatabase ed,
                            User user) throws FitNusException {
        while (true) {
            String userInput;
            Command inputType;
            userInput = ui.readInput();
            inputType = parser.parseCommandType(userInput);
            Ui.println(inputType.execute(ed, fd, user));
            saveFitNus(fd, ed, user);
            if (inputType instanceof ExitCommand) {
                break;
            }
        }
    }

    public static void printPreloadedData(FoodDatabase fd, EntryDatabase ed, User user) {
        Ui.println("Food database:" + System.lineSeparator()
                + fd.listFoods());
        Ui.println("Entry database:" + System.lineSeparator()
                + ed.listEntries());
        Ui.println("User data:" + System.lineSeparator()
                + user.listUserData());
    }

    public static void main(String[] args) {
        User user = new User(0, 1000); //placeholder inputs, to get user's actual input later
        FoodDatabase fd = new FoodDatabase();
        EntryDatabase ed = new EntryDatabase();
        Parser parser = new Parser();
        Ui ui = new Ui();

        Ui.printWelcomeMessage();

        initialiseFitNus(fd, ed, user);

        printPreloadedData(fd, ed, user);

        try {
            Ui.println(new HelpCommand().execute(ed, fd, user));
            run(ui, parser, fd, ed, user);
        } catch (FitNusException e) {
            Ui.println(e.getMessage());
        }
    }
}
