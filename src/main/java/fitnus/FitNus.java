package fitnus;

import fitnus.command.HelpCommand;
import fitnus.command.ExitCommand;
import fitnus.command.Command;
import fitnus.command.ViewWeekSummaryCommand;
import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.parser.Parser;
import fitnus.storage.Storage;
import fitnus.tracker.Gender;
import fitnus.utility.Ui;
import fitnus.utility.User;

import javax.swing.text.View;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FitNus {
    private static final Logger logger = Logger.getLogger("FitNus");
    private final FoodDatabase foodDatabase;
    private final EntryDatabase entryDatabase;
    private final MealPlanDatabase mealPlanDatabase;
    private final User user;
    private final Ui ui;
    private final Parser parser;

    private void initialiseFitNus() {
        // Load From Storage
        try {
            Storage.createDirectoryAndFiles();
            Storage.initialiseFoodDatabase(foodDatabase);
            Storage.initialiseEntryDatabase(entryDatabase);
            Storage.initialiseMealPlanDatabase(mealPlanDatabase);
            Storage.initialiseWeightProgress(user);

            int successfullyInitialisedUser = Storage.initialiseUser(user);

            if (successfullyInitialisedUser == 0) { //did not successfully initialise user data
                Ui ui = new Ui();

                ui.println("Please enter your gender (m/f):");
                initialiseAttribute(ui, entryDatabase, foodDatabase, mealPlanDatabase, user, "gender /set ");
                ui.println("Please enter your age in years:");
                initialiseAttribute(ui, entryDatabase, foodDatabase, mealPlanDatabase, user, "age /set ");
                ui.println("Please enter your height in cm:");
                initialiseAttribute(ui, entryDatabase, foodDatabase, mealPlanDatabase, user, "height /set ");
                ui.println("Please enter your weight in kg:");
                initialiseAttribute(ui, entryDatabase, foodDatabase, mealPlanDatabase, user, "weight /set ");
                ui.println("Generated your daily calorie needs accordingly.");
                int calorieGoal = user.calculateCalorieGoal(0, "gain");
                user.setCalorieGoal(calorieGoal);
                ui.println("Your daily calorie need is " + calorieGoal + " kcal.");
                saveFitNus();
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
                                            MealPlanDatabase md, User user, String commandStringFront) {
        Parser parser = new Parser();

        boolean attributeInitialised = false;
        while (attributeInitialised == false) {
            try {
                String requiredInput = ui.readInput().strip();
                String commandString = commandStringFront + requiredInput;
                Command c = parser.parseCommandType(commandString, fd, ed, md);
                String msg = c.execute(ed, fd, md, user);
                if (!(c instanceof ViewWeekSummaryCommand)) {
                    Ui.println(msg);
                }
                attributeInitialised = true;
            } catch (FitNusException e) {
                attributeInitialised = false;
                Ui.println(e.getMessage());
            }
        }
    }

    private void saveFitNus() {
        try {
            Storage.createDirectoryAndFiles();
            Storage.saveFoodDatabase(foodDatabase);
            Storage.saveEntryDatabase(entryDatabase);
            Storage.saveMealPlanDatabase(mealPlanDatabase);
            Storage.saveUserData(user);
            Storage.saveWeightData(user);
        } catch (IOException e) {
            logger.log(Level.INFO, "some problems when saving data");
            Ui.println("I/O error! " + e.getMessage());
        }
    }

    private void run() {
        Ui.println(new HelpCommand().execute(entryDatabase, foodDatabase, mealPlanDatabase, user));
        while (true) {
            try {
                String userInput;
                Command inputType;
                userInput = ui.readInput();
                inputType = parser.parseCommandType(userInput, foodDatabase, entryDatabase, mealPlanDatabase);
                Ui.println(inputType.execute(entryDatabase, foodDatabase, mealPlanDatabase, user));
                entryDatabase.sortDatabase();
                saveFitNus();
                if (inputType instanceof ExitCommand) {
                    break;
                }
            } catch (FitNusException e) {
                Ui.println(e.getMessage());
            }
            Ui.println("-------------------------");
        }
    }

    public static void printPreloadedData(FoodDatabase fd, EntryDatabase ed, MealPlanDatabase md, User user) {
        Ui.println("Food database:" + System.lineSeparator()
                + fd.listFoods());
        Ui.println("Entry database:" + System.lineSeparator()
                + ed.listEntries());
        Ui.println("User data:" + System.lineSeparator()
                + user.getUserDataDisplay());
    }


    private FitNus() {
        foodDatabase = new FoodDatabase();
        entryDatabase = new EntryDatabase();
        mealPlanDatabase = new MealPlanDatabase();
        user = new User(2000, Gender.MALE, 18, 180, 65);
        ui = new Ui();
        parser = new Parser();

        // Init
        Ui.printWelcomeMessage();
        initialiseFitNus();
        printPreloadedData(foodDatabase, entryDatabase, mealPlanDatabase, user);
    }


    public static void main(String[] args) {
        new FitNus().run();
    }
}
