package fitnus;

import fitnus.command.SetGenderCommand;
import fitnus.command.SetAgeCommand;
import fitnus.command.SetHeightCommand;
import fitnus.command.SetWeightCommand;
import fitnus.command.HelpCommand;
import fitnus.command.ExitCommand;
import fitnus.command.Command;
import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.exception.FitNusException;
import fitnus.parser.Parser;
import fitnus.storage.Storage;
import fitnus.tracker.Gender;
import fitnus.utility.Ui;
import fitnus.utility.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FitNus {
    private static final Logger logger = Logger.getLogger("FitNus");

    private static void initialiseFitNus(FoodDatabase fd, EntryDatabase ed, User user) {
        // Load From Storage
        try {
            Storage.createDirectoryAndFiles();
            Storage.initialiseFoodDatabase(fd);
            Storage.initialiseEntryDatabase(ed);
            Storage.initialiseWeightProgress(user);

            int successfullyInitialisedUser = Storage.initialiseUser(user);

            if (successfullyInitialisedUser == 0) { //did not successfully initialise user data
                Ui ui = new Ui();

                ui.println("Welcome to FitNUS! Please enter your gender (m/f):");
                initialiseGender(ui, ed, fd, user);
                ui.println("Please enter your age in years:");
                initialiseAge(ui, ed, fd, user);
                ui.println("Please enter your height in cm:");
                initialiseHeight(ui, ed, fd, user);
                ui.println("Please enter your weight in kg:");
                initialiseWeight(ui, ed, fd, user);
                ui.println("Generated your daily calorie needs accordingly.");
                int calorieGoal = user.generateCalorieGoal(0, "gain");
                user.setCalorieGoal(calorieGoal);
                ui.println("Your daily calorie need is " + calorieGoal + " kcal.");
                saveFitNus(fd, ed, user);
            }
        } catch (IOException e) {
            logger.log(Level.INFO, "some problems when loading data");
            Ui.println("I/O error! " + e.getMessage());
        } catch (FitNusException e) {
            logger.log(Level.INFO, "some problems when loading data");
            Ui.println(e.getMessage());
        }
    }

    private static void initialiseAttribute(Ui ui, EntryDatabase ed, FoodDatabase fd,
                                            User user, String commandStringFront) {
        Parser parser = new Parser();

        boolean attributeInitialised = false;
        while (attributeInitialised == false) {
            try {
                String requiredInput = ui.readInput().strip();
                String commandString = commandStringFront + requiredInput;
                ui.println(commandString);
                Command c = parser.parseCommandType(commandString, fd, ed);
                Ui.println(c.execute(ed, fd, user));
                attributeInitialised = true;
            } catch (FitNusException e) {
                attributeInitialised = false;
                Ui.println(e.getMessage());
            }
        }
    }

    private static void initialiseGender(Ui ui, EntryDatabase ed, FoodDatabase fd, User user) {
        initialiseAttribute(ui, ed, fd, user, "gender /set ");
    }

    private static void initialiseAge(Ui ui, EntryDatabase ed, FoodDatabase fd, User user) {
        initialiseAttribute(ui, ed, fd, user, "age /set ");
    }

    private static void initialiseHeight(Ui ui, EntryDatabase ed, FoodDatabase fd, User user) {
        initialiseAttribute(ui, ed, fd, user, "height /set ");
    }

    private static void initialiseWeight(Ui ui, EntryDatabase ed, FoodDatabase fd, User user) {
        initialiseAttribute(ui, ed, fd, user, "weight /set ");
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
                inputType = parser.parseCommandType(userInput, fd, ed);
                //inputType = new SetHeightCommand(18);
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
        User user = new User(Gender.MALE, 1000);
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
