package fitnus;

import fitnus.command.Command;
import fitnus.command.ExitCommand;
import fitnus.command.HelpCommand;
import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.exception.FitNusException;
import fitnus.parser.Parser;
import fitnus.storage.Storage;
import fitnus.utility.Ui;
import fitnus.utility.User;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FitNus {
    private static Logger logger = Logger.getLogger("FitNus");

    private static void initialiseFitNus(FoodDatabase fd, EntryDatabase ed, User user) {
        // Load From Storage
        try {
            Storage.createDirectoryAndFiles();
            Storage.initialiseFoodDatabase(fd);
            Storage.initialiseEntryDatabase(ed);
            Storage.initialiseUser(user);
            Storage.initialiseWeightProgress(user);
        } catch (IOException e) {
            logger.log(Level.INFO, "some problems when loading data");
            Ui.println("I/O error! " + e.getMessage());
        } catch (FitNusException e) {
            logger.log(Level.INFO, "some problems when loading data");
            Ui.println(e.getMessage());
        }
    }

    private static void saveFitNus(FoodDatabase fd, EntryDatabase ed, User user) {
        try {
            Storage.createDirectoryAndFiles();
            Storage.saveFoodDatabase(fd);
            Storage.saveEntryDatabase(ed);
            Storage.saveUserData(user);
            Storage.saveWeightData(user);
        } catch (IOException e) {
            logger.log(Level.INFO, "some problems when saving data");
            Ui.println("I/O error! " + e.getMessage());
        }
    }

    private static void run(Ui ui, Parser parser, FoodDatabase fd, EntryDatabase ed,
                            User user) {
        Ui.println(new HelpCommand().execute(ed, fd, user));
        while (true) {
            try {
                String userInput;
                Command inputType;
                userInput = ui.readInput();
                inputType = parser.parseCommandType(userInput, fd);
                //inputType = new SetWeightCommand(Float.parseFloat(userInput));
                Ui.println(inputType.execute(ed, fd, user));
                ed.sortDatabase();
                saveFitNus(fd, ed, user);
                if (inputType instanceof ExitCommand) {
                    break;
                }
            } catch (FitNusException e) {
                Ui.println(e.getMessage());
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

        // Init
        Ui.printWelcomeMessage();
        initialiseFitNus(fd, ed, user);
        printPreloadedData(fd, ed, user);

        // Run
        Ui ui = new Ui();
        Parser parser = new Parser();
        run(ui, parser, fd, ed, user);
    }
}
