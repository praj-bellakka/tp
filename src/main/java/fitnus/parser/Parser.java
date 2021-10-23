package fitnus.parser;

import fitnus.command.AddFoodEntryCommand;
import fitnus.command.Command;
import fitnus.command.DeleteFoodEntryCommand;
import fitnus.command.ExitCommand;
import fitnus.command.FindEntryCommand;
import fitnus.command.FindFoodCommand;
import fitnus.command.HelpCommand;
import fitnus.command.ListFoodDatabaseCommand;
import fitnus.command.ListFoodIntakeCommand;
import fitnus.command.SetCalorieGoalCommand;
import fitnus.command.SetGenderCommand;
import fitnus.command.ViewRemainingCalorieCommand;
import fitnus.command.ViewSuggestionsCommand;
import fitnus.database.FoodDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Food;
import fitnus.tracker.MealType;
import fitnus.utility.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the extraction of user inputs into relevant components.
 */
public class Parser {

    //Logger object
    private static final Logger logger = Logger.getLogger(Parser.class.getName());

    private static final String SPACE_CHARACTER = " ";
    private static final String BACKSLASH_CHARACTER = "/";
    private static final String PIPE_CHARACTER = "|";

    //main command types
    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_CALORIE = "calorie";
    private static final String COMMAND_REMOVE = "remove";
    private static final String COMMAND_GENDER = "gender";
    private static final String COMMAND_FIND = "find";
    private static final String COMMAND_SUGGEST = "suggest";

    //specific descriptors of the main command types
    private static final String DESCRIPTOR_CUSTOM = "/cust";
    private static final String DESCRIPTOR_FOOD = "/food";
    private static final String DESCRIPTOR_INTAKE = "/intake";
    private static final String DESCRIPTOR_DEFAULT = "/def";
    private static final String DESCRIPTOR_REMAIN = "/remain";
    private static final String DESCRIPTOR_SET = "/set";
    public static final int INVALID_INPUT = -1;
    public static final String INVALID_COMMAND_MESSAGE = "That was an invalid command! PLease try again!";

    // FoodType related strings
    private static final String MEAL = "/meal";
    private static final String BEVERAGE = "/beverage";
    private static final String SNACK = "/snack";
    private static final String OTHERS = "/others";

    public static final int CALORIE_LIMIT = 5000;

    private static boolean isLoopFlagOn = true;

    public Command parseCommandType(String input, FoodDatabase fd) throws FitNusException {
        String[] splitString = input.strip().split(SPACE_CHARACTER);
        try {
            int spaceIndex = input.indexOf(SPACE_CHARACTER);

            /**
             * If no space is detected, the input does not contain any tracker related actions.
             * Help, Exit or Invalid command will be returned based on the input.
             */
            if (spaceIndex == INVALID_INPUT) {
                assert spaceIndex < 0 : "Illegal input";
                switch (input) {
                case "help":
                    return new HelpCommand();
                case "exit":
                    return new ExitCommand();
                default:
                    throw new FitNusException(INVALID_COMMAND_MESSAGE);
                }
            }
            String inputCommandType = input.substring(0, spaceIndex);
            String subString = input.substring(spaceIndex).trim();
            if (inputCommandType.equals(COMMAND_ADD)) { //add custom food
                return parseAddTypeCommand(subString, fd);
            }

            if (inputCommandType.equals(COMMAND_LIST)) { //list type command
                return parseListTypeCommand(subString);
            }

            if (inputCommandType.equals(COMMAND_CALORIE)) { //calorie type command
                return parseCalorieTypeCommand(subString);
            }

            if (inputCommandType.equals(COMMAND_GENDER)) { //gender type command
                return parseGenderTypeCommand(subString);
            }

            if (inputCommandType.equals(COMMAND_REMOVE)) {
                return parseRemoveTypeCommand(subString);
            }

            if (inputCommandType.equals(COMMAND_FIND)) {
                return parseFindCommand(subString);
            }

            if (inputCommandType.equals(COMMAND_SUGGEST)) {
                return parseSuggestCommand(subString);
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new FitNusException("Input format is not correct. Follow the one stated!");
        } catch (NumberFormatException e) {
            throw new FitNusException("Input value is not an integer!");
        } catch (StringIndexOutOfBoundsException e) {
            throw new FitNusException("Did you forget to write the full command? :)");
        }
        throw new FitNusException(INVALID_COMMAND_MESSAGE);
    }

    /**
     * Function handles adding food command,by taking in an input string and the food database.
     * The function finds the MealType of the food using {@link #parseMealType(String)}.If no meal type was added by
     * the user, it will be automatically allocated based on time of day using {@link #findMealTypeTiming()}.
     * The food is then searched thorugh the database using FoodDatabase. If food match exists, user will be prompted to
     * either select from an existing foodlist by entering its index, or create a custom food by entering <0>.
     * If no food match exists, the user will be prompted to enter the food's calorie between 0 to 5000.
     *
     * @param input String containing food name and mealtype (optional).
     * @param fd    FoodDatabase object passed from Main(), containing food in database.
     * @return Command object
     * @throws FitNusException Thrown when foodname is empty.
     */
    private Command parseAddTypeCommand(String input, FoodDatabase fd) throws FitNusException {
        //step 1: find meal category and food name
        int spaceCharacterIndex = input.indexOf(SPACE_CHARACTER);
        String mealTypeString = "";
        if (spaceCharacterIndex == -1) {
            mealTypeString = input;
        } else {
            mealTypeString = input.substring(0, input.indexOf(SPACE_CHARACTER));
        }
        MealType mealType = parseMealType(mealTypeString, false);
        String foodName = "";

        //if mealType is null, user didn't specify the command -> auto tag the meal type
        if (mealType.equals(MealType.UNDEFINED)) {
            //TODO: Add a print statement that tells user that food category has been auto added
            mealType = mealType.findMealTypeTiming();
            foodName = input.strip();
        } else {
            foodName = input.substring(input.indexOf(SPACE_CHARACTER)).strip();
        }

        //step 2: search database if food exists
        ArrayList<Food> tempFoodDb = fd.findFood(foodName);

        Ui newUi = new Ui();
        Ui.printMatchingFoods(tempFoodDb); //search database for match
        int userInputLoop;

        //step 3a: prompt the user the suggestions if matches are found
        if (tempFoodDb.size() > 0) {
            //TODO: Beautify the print statement
            System.out.println("Select the food you want by entering the number below. "
                    + "If the food doesn't exist, enter 0 to create a new custom food!");
            return returnUserInput(mealType, foodName, tempFoodDb, newUi, true);
        } else if (tempFoodDb.size() == 0) {
            //step 3b: prompt the user to input calorie if not match
            System.out.println("The food you specified does not exist in the database!");
            return returnUserInput(mealType, foodName, tempFoodDb, newUi, false);
        }
        return null;
    }

    /**
     * Function is responsible for receiving input from the user again during the adding of food phase.
     * If the user inputs an invalid entry for calorie, i.e. negative or non-integers, the function continues looping.
     * If the user inputs an invalid entry for selecting choice, i.e. out of range, negative or non-integer,
     * the function wll continue looping.
     * {@link #isLoopFlagOn} breakLoopFlag is set to false when user prompt loop is not needed, else loop continues.
     *
     * @param mealType        Type of meal.
     * @param foodName        String name of food.
     * @param tempFoodDb      An arraylist containing Food items matching user entry.
     * @param newUi           Ui element responsible for receiving user input through CLI.
     * @param multipleEntries Boolean variable to run custom food entry. If true, function uses existing food items.
     * @return AddFoodEntryCommand Command object containing relevant details.
     */
    private AddFoodEntryCommand returnUserInput(MealType mealType, String foodName, ArrayList<Food> tempFoodDb,
                                                Ui newUi, boolean multipleEntries) {
        int userInput = 0;
        if (multipleEntries) {
            do {
                userInput = parseInteger(newUi.readInput(), tempFoodDb.size());
            } while (isLoopFlagOn);
        }

        /**
         * If user input is 0, the user specified his input to a be a custom food.
         * Thus the new loop below will prompt the user to input the calories.
         */
        if (userInput == 0) {
            System.out.println("Adding new custom food. Enter the calories of the food");
            isLoopFlagOn = false;
            do {
                userInput = parseInteger(newUi.readInput()); //getting calories
            } while (isLoopFlagOn);

            Food.FoodType type = null;
            do {
                System.out.println("Enter food category (meal, snack, beverage, others):");
                type = parseFoodType(newUi.readInput());
            } while (type == null);

            return new AddFoodEntryCommand(mealType, foodName, userInput, type);
        }
        return new AddFoodEntryCommand(mealType, tempFoodDb.get(userInput - 1));
    }

    public static Food.FoodType parseFoodType(String type) {
        String typeString = type.toLowerCase(Locale.ROOT);
        switch (typeString) {
        case "snack":
            return Food.FoodType.SNACK;
        case "beverage":
            return Food.FoodType.BEVERAGE;
        case "meal":
            return Food.FoodType.MEAL;
        case "others":
            return Food.FoodType.OTHERS;
        default:
            return null;
        }
    }

    /**
     * Function takes in an input that may contain the meal type and a boolean databaseRequest.
     * If the meal type matches the predefined MealType enum, the matching MealType is returned.
     * Otherwise, UNDEFINED is returned.
     *
     * @param input           Input that may contain the meal type.
     * @param databaseRequest Boolean representing if method is being called for the database.
     * @return MealType if a match is found; UNDEFINED MealType otherwise.
     */
    public static MealType parseMealType(String input, boolean databaseRequest) {
        if (databaseRequest) {
            switch (input) {
            case "Breakfast":
                return MealType.BREAKFAST;
            case "Lunch":
                return MealType.LUNCH;
            case "Dinner":
                return MealType.DINNER;
            case "Snack":
                return MealType.SNACK;
            default:
                return MealType.UNDEFINED;
            }
        } else {
            switch (input) {
            case "/bfast":
                return MealType.BREAKFAST;
            case "/lunch":
                return MealType.LUNCH;
            case "/dinner":
                return MealType.DINNER;
            case "/snack":
                return MealType.SNACK;
            default:
                return MealType.UNDEFINED;
            }
        }
    }

    /**
     * Function parses integers from user input when the while loop inside
     * {@link #parseAddTypeCommand(String, FoodDatabase)} parseAddTypeCommand} is running.
     * Returns integer if found within range, else -1.
     *
     * @param input User input.
     * @param size  Size of temporary database.
     * @return Integer input by the user. If invalid integer or out of range, -1 is returned.
     */
    private int parseInteger(String input, int size) {
        try {
            int val = Integer.parseInt(input.strip());
            if (val >= 0 && val <= size) {
                isLoopFlagOn = false;
                return val;
            } else {
                System.out.println("The input is outside the range of the database!");
            }
        } catch (NumberFormatException e) {
            //TODO: add proper Ui print message;
            System.out.println("Please enter an integer value!");
        }
        isLoopFlagOn = true;
        return -1;
    }

    /**
     * Function parses integers from user input when the while loop
     * inside {@link #parseAddTypeCommand(String, FoodDatabase)} parseAddTypeCommand} is running.
     * Returns calories of food if input is valid, else returns -1.
     *
     * @param input Input containing the calories.
     * @return Integer value of the calories.
     */
    private int parseInteger(String input) {
        try {
            int val = Integer.parseInt(input.strip());
            if (val > 0 && val <= CALORIE_LIMIT) {
                isLoopFlagOn = false;
                return val;
            } else {
                System.out.println("Calories can only be between 0 and 5000!");
            }
        } catch (NumberFormatException e) {
            //TODO: add proper Ui print message;
            System.out.println("Please enter an integer value!");
        }
        isLoopFlagOn = true;
        return -1;
    }

    private Command parseRemoveTypeCommand(String input) throws FitNusException {
        int typeDescriptorIndex = input.indexOf(" ");
        try {
            return new DeleteFoodEntryCommand(Integer.parseInt(input
                    .substring(typeDescriptorIndex).trim()));
        } catch (NumberFormatException e) {
            throw new FitNusException("Input value is not an integer!");
        }
    }

    private Command parseListTypeCommand(String input) throws FitNusException {
        int typeDescriptorIndex = input.indexOf(" ");
        if (typeDescriptorIndex == -1) {
            if (input.equals(DESCRIPTOR_FOOD)) {
                return new ListFoodDatabaseCommand();
            }
            throw new FitNusException(INVALID_COMMAND_MESSAGE);
        }

        String typeDescriptor = input.substring(0, typeDescriptorIndex).trim();
        switch (typeDescriptor) {
        case DESCRIPTOR_INTAKE:
            return new ListFoodIntakeCommand(input.substring(typeDescriptorIndex).trim());
        default:
            throw new FitNusException(INVALID_COMMAND_MESSAGE);
        }
    }

    private Command parseCalorieTypeCommand(String input) throws FitNusException {
        int typeDescriptorIndex = input.indexOf(" ");

        if (typeDescriptorIndex == -1) {
            if (input.equals(DESCRIPTOR_REMAIN)) {
                return new ViewRemainingCalorieCommand();
            }
            throw new FitNusException(INVALID_COMMAND_MESSAGE);
        }

        String typeDescriptor = input.substring(0, typeDescriptorIndex).trim();
        if (DESCRIPTOR_SET.equals(typeDescriptor)) {
            int calorieGoal = Integer.parseInt(input.substring(typeDescriptorIndex).trim());
            return new SetCalorieGoalCommand(calorieGoal);
        }
        throw new FitNusException(INVALID_COMMAND_MESSAGE);

    }

    private Command parseGenderTypeCommand(String input) throws FitNusException {
        int typeDescriptorIndex = input.indexOf(" ");
        String typeDescriptor = input.substring(0, typeDescriptorIndex).trim();
        try {
            if (typeDescriptor.equals(DESCRIPTOR_SET)) {
                String gender = input.substring(typeDescriptorIndex).trim();
                if (gender.toLowerCase().equals("m") || gender.toLowerCase().equals("f")) {
                    return new SetGenderCommand(gender);
                }
                throw new FitNusException("Invalid input! Please input m for male or "
                        + "f for female when setting your gender.");
            }
        } catch (FitNusException e) {
            throw e;
        } catch (Exception e) {
            throw new FitNusException(INVALID_COMMAND_MESSAGE);
        }
        throw new FitNusException(INVALID_COMMAND_MESSAGE);
    }

    private Command parseFindCommand(String input) throws FitNusException {
        if (input.contains("/food")) {
            int typeDescriptorIndex = input.indexOf("/food");
            String keyword = input.substring(typeDescriptorIndex + 6);
            return new FindFoodCommand(keyword);
        } else if (input.contains("/entry")) {
            int typeDescriptorIndex = input.indexOf("/entry");
            String keyword = input.substring(typeDescriptorIndex + 7);
            return new FindEntryCommand(keyword);
        }
        throw new FitNusException("parse find error");
    }

    private Command parseSuggestCommand(String input) throws FitNusException {
        boolean isSort = false;
        if (input.contains("/sort")) {
            isSort = true;
            int spaceIndex = input.indexOf(" ");
            input = input.substring(0, spaceIndex);
        }
        switch (input) {
        case MEAL:
            return new ViewSuggestionsCommand(Food.FoodType.MEAL, isSort);
        case SNACK:
            return new ViewSuggestionsCommand(Food.FoodType.SNACK, isSort);
        case BEVERAGE:
            return new ViewSuggestionsCommand(Food.FoodType.BEVERAGE, isSort);
        case OTHERS:
            return new ViewSuggestionsCommand(Food.FoodType.OTHERS, isSort);
        default:
            throw new FitNusException("Parse suggest error");
        }
    }

    private static LocalDate parseDate(String description) {
        LocalDate date;
        try {
            date = LocalDate.parse(description);
        } catch (DateTimeParseException e) {
            return null;
        }
        return date;
    }

    /**
     * Attempts to parse a given String and returns a
     * LocalDate object if successful.
     *
     * @param line Description String to be parsed.
     * @return A LocalDate object if successful, returns null otherwise.
     * @throws FitNusException If unable to parse the input String.
     */
    public static LocalDate getDate(String line) throws FitNusException {
        assert !line.equals("") : "String line should not be empty";
        String[] description = line.split(" ");
        LocalDate date;
        for (String s : description) {
            date = parseDate(s);
            if (date != null) {
                return date;
            }
        }
        logger.log(Level.INFO, "Could not parse date");
        throw new FitNusException("Error parsing date!!");
    }

}
