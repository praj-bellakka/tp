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
                Ui.println(Ui.DIVIDER);
                Ui.println(Ui.INIT_GENDER);
                initialiseAttribute(ui, entryDatabase, foodDatabase, mealPlanDatabase,
                        user, "gender /set ");
                Ui.println(Ui.DIVIDER);
                Ui.println(Ui.INIT_AGE);
                initialiseAttribute(ui, entryDatabase, foodDatabase, mealPlanDatabase,
                        user, "age /set ");
                Ui.println(Ui.DIVIDER);
                Ui.println(Ui.INIT_HEIGHT);
                initialiseAttribute(ui, entryDatabase, foodDatabase, mealPlanDatabase,
                        user, "height /set ");
                Ui.println(Ui.DIVIDER);
                Ui.println(Ui.INIT_WEIGHT);
                initialiseAttribute(ui, entryDatabase, foodDatabase, mealPlanDatabase,
                        user, "weight /set ");
                Ui.println(Ui.INIT_SUCCESS);
                int calorieGoal = user.calculateCalorieGoal(0, "gain");
                user.setCalorieGoal(calorieGoal);
                Ui.printCalorieGoal(calorieGoal);
                Ui.println(Ui.DIVIDER);
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
        while (!attributeInitialised) {
            try {
                String requiredInput = ui.readInput(System.in, System.out).strip();
                String commandString = commandStringFront + requiredInput;
                Command c = parser.parseCommandType(commandString, fd, ed, md);
                String msg = c.execute(ed, fd, md, user);
                if (!(c instanceof ViewWeekSummaryCommand)) {
                    Ui.println(msg);
                }
                attributeInitialised = true;
            } catch (FitNusException e) {
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
        Ui.print(Ui.DIVIDER);
        while (true) {
            try {
                String userInput;
                Command inputType;
                Ui.print(Ui.USER_INPUT);
                userInput = ui.readInput(System.in, System.out);
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
            Ui.print(Ui.DIVIDER);
        }
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
        Ui.printPreloadedData(foodDatabase, entryDatabase, mealPlanDatabase, user);
    }


    public static void main(String[] args) {
        new FitNus().run();
    }
}
