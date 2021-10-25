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
    private final FoodDatabase fd;
    private final EntryDatabase ed;
    private final User user;
    private final Ui ui;
    private final Parser parser;

    private void initialiseFitNus() {
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
                initialiseGender();
                ui.println("Please enter your age in years:");
                initialiseAge();
                ui.println("Please enter your height in cm:");
                initialiseHeight();
                ui.println("Please enter your weight in kg:");
                initialiseWeight();
                ui.println("Generated your daily calorie needs accordingly.");
                int calorieGoal = user.generateCalorieGoal(0, "gain");
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

    private void initialiseGender() {
        boolean genderInitialised = false;
        while (genderInitialised == false) {
            try {
                String genderString = ui.readInput().strip();
                Command c = new SetGenderCommand(genderString);
                Ui.println(c.execute(ed, fd, user));
                genderInitialised = true;
            } catch (FitNusException e) {
                genderInitialised = false;
                Ui.println(e.getMessage());
            }
        }
    }

    private void initialiseAge() {
        boolean ageInitialised = false;
        while (ageInitialised == false) {
            try {
                int age;
                try {
                    age = Integer.parseInt(ui.readInput().strip());
                } catch (NumberFormatException e) {
                    throw new FitNusException("Input must be an integer!");
                }
                Command c = new SetAgeCommand(age);
                Ui.println(c.execute(ed, fd, user));
                ageInitialised = true;
            } catch (FitNusException e) {
                ageInitialised = false;
                Ui.println(e.getMessage());
            }
        }
    }

    private void initialiseHeight() {
        boolean heightInitialised = false;
        while (heightInitialised == false) {
            try {
                int height;
                try {
                    height = Integer.parseInt(ui.readInput().strip());
                } catch (NumberFormatException e) {
                    throw new FitNusException("Input must be an integer!");
                }
                Command c = new SetHeightCommand(height);
                Ui.println(c.execute(ed, fd, user));
                heightInitialised = true;
            } catch (FitNusException e) {
                heightInitialised = false;
                Ui.println(e.getMessage());
            }
        }
    }

    private void initialiseWeight() {
        boolean weightInitialised = false;
        while (weightInitialised == false) {
            try {
                float weight;
                try {
                    weight = Float.parseFloat(ui.readInput().strip());
                } catch (NumberFormatException e) {
                    throw new FitNusException("Input must be a number!");
                }
                Command c = new SetWeightCommand(weight);
                Ui.println(c.execute(ed, fd, user));
                weightInitialised = true;
            } catch (FitNusException e) {
                weightInitialised = false;
                Ui.println(e.getMessage());
            }
        }
    }

    private void saveFitNus() {
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

    private void run() {
        Ui.println(new HelpCommand().execute(ed, fd, user));
        while (true) {
            try {
                String userInput;
                Command inputType;
                userInput = ui.readInput();
                inputType = parser.parseCommandType(userInput, fd, ed);
                Ui.println(inputType.execute(ed, fd, user));
                ed.sortDatabase();
                saveFitNus();
                if (inputType instanceof ExitCommand) {
                    break;
                }
            } catch (FitNusException e) {
                Ui.println(e.getMessage());
            }
        }
    }

    public void printPreloadedData(FoodDatabase fd, EntryDatabase ed, User user) {
        Ui.println("Food database:" + System.lineSeparator()
                + fd.listFoods());
        Ui.println("Entry database:" + System.lineSeparator()
                + ed.listEntries());
        Ui.println("User data:" + System.lineSeparator()
                + user.listUserData());
    }

    private FitNus() {
        fd = new FoodDatabase();
        ed = new EntryDatabase();
        user = new User(Gender.MALE, 1000);
        ui = new Ui();
        parser = new Parser();

        // Init
        Ui.printWelcomeMessage();
        initialiseFitNus();
        printPreloadedData(fd, ed, user);
    }

    public static void main(String[] args) {
        new FitNus().run();
    }
}
