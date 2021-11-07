package fitnus.parser;

import fitnus.command.AddFoodEntryCommand;
import fitnus.command.AddMealPlanEntryCommand;
import fitnus.command.Command;
import fitnus.command.CreateMealPlanCommand;
import fitnus.command.DeleteEntryCommand;
import fitnus.command.DeleteFoodCommand;
import fitnus.command.EditFoodEntryCommand;
import fitnus.command.ExitCommand;
import fitnus.command.FindEntriesCommand;
import fitnus.command.FindFoodsCommand;
import fitnus.command.GenerateCalorieGoalCommand;
import fitnus.command.HelpCommand;
import fitnus.command.ListFoodDatabaseCommand;
import fitnus.command.ListFoodEntryAllCommand;
import fitnus.command.ListUserDataCommand;
import fitnus.command.ListWeightProgressCommand;
import fitnus.command.ListMealPlanDatabaseCommand;
import fitnus.command.SetAgeCommand;
import fitnus.command.SetCalorieGoalCommand;
import fitnus.command.SetGenderCommand;
import fitnus.command.ListFoodEntryCustomCommand;
import fitnus.command.SetHeightCommand;
import fitnus.command.SetWeightCommand;
import fitnus.command.ViewMonthSummaryCommand;
import fitnus.command.ViewRemainingCalorieCommand;
import fitnus.command.ViewSuggestionsCommand;
import fitnus.command.ViewWeekSummaryCommand;
import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Food;
import fitnus.tracker.MealType;
import fitnus.utility.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the extraction of user inputs into relevant components.
 */
public class Parser {
    //Logger object
    private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());

    private static final String SPACE_CHARACTER = " ";
    private static final String BACKSLASH_CHARACTER = "/";
    private static final String PIPE_CHARACTER = "|";

    //main command types
    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_EDIT = "edit";
    private static final String COMMAND_CALORIE = "calorie";
    private static final String COMMAND_REMOVE = "remove";
    private static final String COMMAND_GENDER = "gender";
    private static final String COMMAND_AGE = "age";
    private static final String COMMAND_HEIGHT = "height";
    private static final String COMMAND_WEIGHT = "weight";

    private static final String COMMAND_FIND = "find";
    private static final String COMMAND_SUGGEST = "suggest";
    private static final String COMMAND_SUMMARY = "summary";
    private static final String COMMAND_CREATE = "create";

    //specific descriptors of the main command types
    private static final String DESCRIPTOR_CUSTOM = "/cust";
    private static final String DESCRIPTOR_FOOD = "/food";
    private static final String DESCRIPTOR_INTAKE = "/entry";
    private static final String DESCRIPTOR_WEIGHT = "/weight";
    private static final String DESCRIPTOR_USER = "/user";
    private static final String DESCRIPTOR_DEFAULT = "/def";
    private static final String DESCRIPTOR_REMAIN = "/remain";
    private static final String DESCRIPTOR_GENERATE = "/generate";
    private static final String DESCRIPTOR_SET = "/set";
    private static final String DESCRIPTOR_MEALPLAN = "/mealplan";
    public static final int INVALID_INPUT = -1;
    public static final String INVALID_COMMAND_MESSAGE = "That was an invalid command! "
            + "Type 'help' for a list of commands\n"
            + "and their command formats.";

    // FoodType related strings
    private static final String MEAL = "/meal";
    private static final String MEAL_STRING = "meal";
    private static final String BEVERAGE = "/beverage";
    private static final String BEVERAGE_STRING = "beverage";
    private static final String SNACK = "/snack";
    private static final String SNACK_STRING = "snack";
    private static final String OTHERS = "/others";
    private static final String OTHERS_STRING = "others";
    private static final String[] possibleFoodTypes = {"meal", "snack", "beverage", "others"};
    private static final String[] possibleFoodCategories = {"/bfast", "/lunch", "/dinner", "/snack"};

    //Parse suggest command error message
    private static final String PARSE_SUGGEST_ERROR = "Oops! Please double check your command format! Please try:"
            + System.lineSeparator() + "suggest /FOODTYPE (/meal, /beverage, /snack, /others) /sort (optional)";

    // time frame related strings
    private static final String WEEK = "/week";
    private static final String MONTH = "/month";
    private static final String ALL_TIME = "/all";
    private static final int FIRST_MONTH = 1;
    private static final int LAST_MONTH = 12;

    //calorie goal generation related strings
    private static final String GAIN = "/gain";
    private static final String LOSE = "/lose";

    public static final int CALORIE_LIMIT = 5000;
    private static final int MINIMUM_AGE = 12;
    private static final int MAXIMUM_AGE = 100;
    private static final int MINIMUM_HEIGHT = 40;
    private static final int MAXIMUM_HEIGHT = 300;
    private static final float MINIMUM_WEIGHT = 0;
    private static final int MAXIMUM_WEIGHT = 500;


    // Timeframe
    private static final int DAYS_IN_DAY = 1;
    private static final int DAYS_IN_WEEK = 7;

    private static boolean isLoopFlagOn = true;

    /**
     * Takes in an input and returns a Command object corresponding to the input.
     * Throws FitNusException if noo corresponding command is found.
     *
     * @param input String containing a command call.
     * @param fd    Food database object.
     * @param ed    Entry database object.
     * @param md    MealPlan database object.
     * @return Command object correspoonding to the input.
     * @throws FitNusException Thrown when no Command object found.
     */
    public Command parseCommandType(String input, FoodDatabase fd, EntryDatabase ed, MealPlanDatabase md)
            throws FitNusException {
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
                return parseAddTypeCommand(subString, fd, md);
            }

            if (inputCommandType.equals(COMMAND_LIST)) { //list type command
                return parseListTypeCommand(subString);
            }

            if (inputCommandType.equals(COMMAND_EDIT)) { //edit type command
                return parseEditTypeCommand(subString, fd, ed);
            }

            if (inputCommandType.equals(COMMAND_CALORIE)) { //calorie type command
                return parseCalorieTypeCommand(subString);
            }

            if (inputCommandType.equals(COMMAND_GENDER)) { //gender type command
                return parseGenderTypeCommand(subString);
            }

            if (inputCommandType.equals(COMMAND_AGE)) { //gender type command
                return parseAgeTypeCommand(subString);
            }

            if (inputCommandType.equals(COMMAND_HEIGHT)) { //gender type command
                return parseHeightTypeCommand(subString);
            }

            if (inputCommandType.equals(COMMAND_WEIGHT)) { //gender type command
                return parseWeightTypeCommand(subString);
            }

            if (inputCommandType.equals(COMMAND_REMOVE)) {
                return parseRemoveTypeCommand(subString);
            }

            if (inputCommandType.equals(COMMAND_FIND)) {
                return parseFindTypeCommand(subString);
            }

            if (inputCommandType.equals(COMMAND_SUGGEST)) {
                return parseSuggestCommand(subString);
            }

            if (inputCommandType.equals(COMMAND_SUMMARY)) { //summary type command
                return parseSummaryTypeCommand(subString);
            }

            if (inputCommandType.equals(COMMAND_CREATE)) { //summary type command
                return parseCreateCommand(subString, fd);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new FitNusException("Input format is not correct! Type 'help' for a list of commands\n"
                    + "and their command formats.");
        } catch (NumberFormatException e) {
            throw new FitNusException("Input value is not an integer!");
        } catch (StringIndexOutOfBoundsException e) {
            throw new FitNusException("Did you forget to write the full command? Type 'help' for a list of commands\n"
                    + "and their command formats.");
        }
        throw new FitNusException(INVALID_COMMAND_MESSAGE);
    }

    /**
     * Function parses type of add method called and returns a Command object representing the type.
     *
     * @param input String containing food name and mealtype (optional).
     * @param fd    FoodDatabase object passed from Main(), containing food in database.
     * @return Command object.
     * @throws FitNusException Thrown when foodname is empty.
     */
    public Command parseAddTypeCommand(String input, FoodDatabase fd, MealPlanDatabase md) throws FitNusException {
        //find meal category and food name
        int spaceCharacterIndex = input.indexOf(SPACE_CHARACTER);
        String mealTypeString = "";
        if (spaceCharacterIndex == -1) {
            mealTypeString = input;
        } else {
            mealTypeString = input.substring(0, input.indexOf(SPACE_CHARACTER));
        }

        //check if it is adding meal plan
        if (mealTypeString.equals(DESCRIPTOR_MEALPLAN)) {
            return parseAddMealPlanFoodCommand(md, input);
        } else {
            return parseAddFoodCommand(input, fd, mealTypeString);
        }
    }

    /**
     * Parses add food command by returning AddFoodEntryCommand object with its parameters.
     * Method returns a prompt to user if food is already present in database or to enter calorie and meal type of food.
     * Calorie of food must be between 0 and 5000, or prompt continues in infinite loop.
     *
     * @param input          String containing user input.
     * @param fd             Food database containing existing food items.
     * @param mealTypeString String describing the type of food.
     * @return AddFoodEntryCommand object.
     * @throws FitNusException Thrown when command format is not fulfilled.
     */
    public AddFoodEntryCommand parseAddFoodCommand(String input, FoodDatabase fd, String mealTypeString)
            throws FitNusException {
        MealType mealType = parseMealType(mealTypeString, false);
        String foodName = "";

        //if mealType is null, user didn't specify the command; auto tag the meal type
        if (mealType.equals(MealType.UNDEFINED)) {
            foodName = removePipeCharacterFoodName(input, mealType);
            mealType = mealType.findMealTypeTiming();
            Ui.printAutoAddedFoodCategory(mealType.name(), true);
        } else {
            foodName = removePipeCharacterFoodName(input, mealType);
        }

        //search database if food exists
        Ui.print(Ui.DIVIDER);
        System.out.println("Searching for \"" + foodName + "\"...");
        ArrayList<Food> tempDbFoods = fd.findFoods(foodName);
        Ui newUi = new Ui();
        if (tempDbFoods.size() > 0) {
            System.out.println(" [X] Select your desired food from the list below:");
            Ui.printMatchingFoods(tempDbFoods);
        }
        int userInputLoop;

        //prompt the user the suggestions if matches are found
        if (tempDbFoods.size() > 0) {
            Ui.print(Ui.DIVIDER);
            System.out.println("Don't see what you're looking for? Enter 0 to create your own food!");
            Ui.print(Ui.USER_INPUT);
            return returnUserInput(mealType, foodName, tempDbFoods, newUi, true);
        } else if (tempDbFoods.size() == 0) {
            //prompt the user to input calorie if not match
            Ui.printPromptUserFoodInput(foodName);
            return returnUserInput(mealType, foodName, tempDbFoods, newUi, false);
        }
        assert (tempDbFoods.size() >= 0);
        return null;
    }

    /**
     * Returns AddMealPlanEntryCommand to add meal plans currently in mealplan database.
     * Meal plan must exist inside database or FitNusException will be thrown.
     *
     * @param md    Meal plan database containing existing meal plans.
     * @param input String input entered by the user.
     * @return AddMealPlanEntryCommand AddMealPlanEntryCommand with set parameters.
     * @throws FitNusException Thrown when mealplan does not exist.
     */
    public AddMealPlanEntryCommand parseAddMealPlanFoodCommand(MealPlanDatabase md, String input)
            throws FitNusException {
        int spaceIndex = input.indexOf(SPACE_CHARACTER);
        if (spaceIndex == -1) {
            throw new FitNusException("Invalid format");
        }
        String remainingString = input.substring(spaceIndex).strip();
        int spaceRemainingIndex = remainingString.indexOf(SPACE_CHARACTER);
        MealType mealType = MealType.UNDEFINED;
        //mealType has not been specified by the user
        if (spaceRemainingIndex == -1) {
            mealType = mealType.findMealTypeTiming();
            //throw new FitNusException("Invalid format");
        } else {
            mealType = parseMealType(remainingString.substring(0, spaceRemainingIndex), false);
        }
        try {
            int index;
            if (spaceRemainingIndex == -1) {
                index = Integer.parseInt(remainingString.strip());
            } else if (!remainingString.substring(0, spaceRemainingIndex).contains(BACKSLASH_CHARACTER)) {
                throw new FitNusException("Recheck command format!");
            } else {
                index = Integer.parseInt(remainingString.substring(spaceRemainingIndex).strip());
            }
            return new AddMealPlanEntryCommand(md.getMealAtIndex(index), mealType);
        } catch (NumberFormatException e) {
            throw new FitNusException("Integer index could not be parsed. Check format again!");
        }
    }

    /**
     * Returns CreateMealPlanCommand object when called and creates a custom meal plan.
     *
     * @param input String input containing meal plan name.
     * @param fd    FoodDatabase object.
     * @return CreateMealPlanCommand object.
     * @throws FitNusException Thrown when input format is invalid.
     */
    private CreateMealPlanCommand parseCreateCommand(String input, FoodDatabase fd) throws FitNusException {
        String[] arrayFormInput = input.split(SPACE_CHARACTER);
        if (!arrayFormInput[0].equals(DESCRIPTOR_MEALPLAN)) {
            throw new FitNusException("Invalid Command Format!");
        }

        int spaceCharacterIndex = input.indexOf(SPACE_CHARACTER);
        String mealNameString = "";
        if (spaceCharacterIndex == -1) {
            throw new FitNusException("Meal plan name cannot be empty!");
        } else {
            mealNameString = input.substring(input.indexOf(SPACE_CHARACTER))
                    .strip().replaceAll("\\|", "");
        }

        //display all current foods
        Ui newUi = new Ui();
        Ui.printMealPlanCreation(fd);
        ArrayList<Food> tempMealFoods = new ArrayList<Food>();
        String[] userInputIndexes = newUi.readIndexesInput(System.in, System.out);

        //for each index, check if it is an integer and within range
        for (String i : userInputIndexes) {
            try {
                int inputInt = Integer.parseInt(i);
                if (inputInt > fd.getFoodDatabase().size() || inputInt <= 0) {
                    Ui.printOutOfRangeInputInteger(inputInt);
                    continue;
                }
                tempMealFoods.add(fd.getFoodDatabase().get(inputInt - 1));
            } catch (NumberFormatException e) {
                Ui.printInvalidInputInteger(i);
            }
        }
        return new CreateMealPlanCommand(tempMealFoods, mealNameString);
    }

    /**
     * Function receives input from the user when adding food.
     * If the user inputs an invalid entry for calorie, i.e. negative or non-integers, the function continues looping.
     * If the user inputs an invalid entry for selecting choice, i.e. out of range, negative or non-integer,
     * the function will continue looping.
     * {@link #isLoopFlagOn} breakLoopFlag is set to false when user prompt loop is not needed, else loop continues.
     *
     * @param mealType           Type of meal.
     * @param foodName           String name of food.
     * @param tempDbFoods        An arraylist containing Food items matching user entry.
     * @param newUi              Ui element responsible for receiving user input through CLI.
     * @param hasMultipleEntries Boolean variable to run custom food entry. If true, function uses existing food items.
     * @return AddFoodEntryCommand Command object containing relevant details.
     */
    private AddFoodEntryCommand returnUserInput(MealType mealType, String foodName, ArrayList<Food> tempDbFoods,
                                                Ui newUi, boolean hasMultipleEntries) throws FitNusException {
        int userInput = 0;
        if (hasMultipleEntries) {
            do {
                userInput = parseInteger(newUi.readInput(System.in, System.out), tempDbFoods.size());
            } while (isLoopFlagOn);
        }

        /**
         * If user input is 0, the user specified his input to a be a custom food.
         * New loop below will prompt the user to input the calories.
         */
        if (userInput == 0) {
            return (AddFoodEntryCommand) promptUserCalories(0, mealType, foodName, newUi);
        }
        return new AddFoodEntryCommand(mealType, tempDbFoods.get(userInput - 1));
    }

    /**
     * The function takes in the relevant details from parseEditTypeCommand, prompts the user to confirm the food
     * to be edited to, and returns the respective Edit command.
     * @param index Index of the food entry to be edited.
     * @param foodName Food name of the food to be edited to.
     * @param tempDbFoods Temporary FoodDatabase containing the foods that matched user's specifications.
     * @param newUi User interface that will read the user's input.
     * @param hasMultipleEntries A boolean variable that indicated whether there were multiple foods that matched the
     *                           user's specification.
     * @return Returns the respective Edit command that will edit the user's specified entry.
     * @throws FitNusException Throws an exception if the input of the user is invalid.
     */
    private EditFoodEntryCommand returnUserInput(int index, String foodName, ArrayList<Food> tempDbFoods,
                                                 Ui newUi, boolean hasMultipleEntries) throws FitNusException {
        int userInput = 0;
        if (hasMultipleEntries) {
            do {
                userInput = parseInteger(newUi.readInput(System.in, System.out), tempDbFoods.size());
            } while (isLoopFlagOn);
        }

        /**
         * If user input is 0, the user specified his input to a be a custom food.
         * New loop below will prompt the user to input the calories.
         */
        if (userInput == 0) {
            return (EditFoodEntryCommand) promptUserCalories(index, null, foodName, newUi);
        }
        return new EditFoodEntryCommand(index, tempDbFoods.get(userInput - 1));
    }

    /**
     * Prompts user to enter calories between 0 and 5000 when called.
     * Prompt continues in infinite loop until a valid calorie is inputted.
     *
     * @param index    Index if EditFoodEntryCommand is called.
     * @param mealType MealType of food item.
     * @param foodName String name of food.
     * @param newUi    Ui element handling reading of CLI.
     * @return Command subobjects.
     * @throws FitNusException Thrown when invalid food type is detected.
     */
    private Command promptUserCalories(int index, MealType mealType, String foodName, Ui newUi) throws FitNusException {
        int userInput;
        Ui.printAddCalorieToFood(foodName);
        isLoopFlagOn = false;
        do {
            userInput = parseInteger(newUi.readInput(System.in, System.out)); //getting calories
        } while (isLoopFlagOn);

        Food.FoodType type = null;
        do {
            System.out.println("[X] Enter food type (meal, snack, beverage, others):");
            String foodType = newUi.readInput(System.in, System.out);
            if (Arrays.asList(possibleFoodTypes).contains(foodType)) {
                type = parseFoodType(foodType);
            } else {
                type = null;
                Ui.println("The food type provided is invalid! Please try again");
            }
        } while (type == null);

        //check type of Command object to return
        if (mealType == null) {
            return new EditFoodEntryCommand(index, foodName, userInput, type);
        } else {
            return new AddFoodEntryCommand(mealType, foodName, userInput, type);
        }
    }

    /**
     * Removes pipe character from food name and replaces it with a space chacrater.
     *
     * @param input    String that may contain pipe characters.
     * @param mealType MealType of food.
     * @return foodName String without pipe characters.
     */
    public String removePipeCharacterFoodName(String input, MealType mealType) {
        String foodName;
        if (mealType.equals(MealType.UNDEFINED)) {
            foodName = input.strip().replaceAll("\\|", ""); //replace pipe charcter with nothing
        } else {
            Ui.printAutoAddedFoodCategory(mealType.name(), false);
            foodName = input.substring(input.indexOf(SPACE_CHARACTER)).strip().replaceAll("\\|", "");
        }
        return foodName;
    }

    /**
     * Returns a FoodType based on input string.
     * Throws FitNusException if input does not match pre-defined FoodTypes.
     *
     * @param type String containing FoodType.
     * @return FoodType object related to input.
     * @throws FitNusException Thrown when specified FoodType input does not exist.
     */
    public static Food.FoodType parseFoodType(String type) throws FitNusException {
        String typeString = type.toLowerCase(Locale.ROOT);
        switch (typeString) {
        case SNACK_STRING:
            return Food.FoodType.SNACK;
        case BEVERAGE_STRING:
            return Food.FoodType.BEVERAGE;
        case MEAL_STRING:
            return Food.FoodType.MEAL;
        case OTHERS_STRING:
            return Food.FoodType.OTHERS;
        default:
            throw new FitNusException("Unable to parse Food type");
        }
    }

    /**
     * Function takes in an input that may contain the meal type and a boolean isDatabaseRequest.
     * If the meal type matches the predefined MealType enum, the matching MealType is returned.
     * Otherwise, UNDEFINED is returned.
     *
     * @param input             Input that may contain the meal type.
     * @param isDatabaseRequest Boolean representing if method is being called for the database.
     * @return MealType if a match is found; UNDEFINED MealType otherwise.
     */
    public static MealType parseMealType(String input, boolean isDatabaseRequest) throws FitNusException {
        if (isDatabaseRequest) {
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
            ArrayList<String> listStrs = new ArrayList<String>(Arrays.asList(possibleFoodCategories));
            if (input.startsWith(BACKSLASH_CHARACTER) && !listStrs.contains(input)) {
                throw new FitNusException(input + " is an invalid food category. "
                        + "Avoid using the backslash character if food category is not specified.");
            }
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
     * {@link #parseAddTypeCommand(String, FoodDatabase, MealPlanDatabase)} parseAddTypeCommand} is running.
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
     * inside {@link #parseAddTypeCommand(String, FoodDatabase, MealPlanDatabase)} parseAddTypeCommand} is running.
     * Returns calories of food if input is valid, else returns -1.
     *
     * @param input Input containing the calories.
     * @return Integer value of the calories.
     */
    private int parseInteger(String input) {
        try {
            int val = Integer.parseInt(input.strip());
            if (val >= 0 && val <= CALORIE_LIMIT) {
                isLoopFlagOn = false;
                return val;
            } else {
                System.out.println("Calories can only be between 0 and 5000!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter an integer value!");
        }
        isLoopFlagOn = true;
        return -1;
    }

    private Command parseRemoveTypeCommand(String input) throws FitNusException {
        int typeDescriptorIndex = input.indexOf(" ");
        String removeType = input.substring(0, typeDescriptorIndex);
        try {
            int index = Integer.parseInt(input
                    .substring(typeDescriptorIndex).trim());
            if (removeType.equals(DESCRIPTOR_FOOD)) {
                return new DeleteFoodCommand(index);
            } else if (removeType.equals(DESCRIPTOR_INTAKE)) {
                return new DeleteEntryCommand(index);
            }
            throw new FitNusException("Invalid remove command!");
        } catch (NumberFormatException e) {
            throw new FitNusException("Input value is not an integer!");
        }
    }

    /**
     * Parses the input and returns the respective List command.
     * @param input The user input
     * @return Returns the correct List command based on the input
     * @throws FitNusException Thrown when user inputs an invalid command
     */
    private Command parseListTypeCommand(String input) throws FitNusException {
        int typeDescriptorIndex = input.indexOf(" ");
        if (typeDescriptorIndex == -1) {
            if (input.equals(DESCRIPTOR_FOOD)) {
                return new ListFoodDatabaseCommand();
            } else if (input.equals(DESCRIPTOR_INTAKE)) {
                return new ListFoodEntryAllCommand();
            } else if (input.equals(DESCRIPTOR_USER)) {
                return new ListUserDataCommand();
            } else if (input.equals(DESCRIPTOR_MEALPLAN)) {
                return new ListMealPlanDatabaseCommand();
            }
        }

        if (input.contains(DESCRIPTOR_INTAKE)) {
            String timeFrame = input.substring(typeDescriptorIndex);

            switch (timeFrame) {
            case " /day":
                return new ListFoodEntryCustomCommand(DAYS_IN_DAY);
            case " /week":
                return new ListFoodEntryCustomCommand(DAYS_IN_WEEK);
            default:
                throw new FitNusException("Invalid timeframe! (/day, /week)");
            }
        } else if (input.contains(DESCRIPTOR_WEIGHT)) {
            int listWeightInputsIndex = input.indexOf(DESCRIPTOR_WEIGHT) + DESCRIPTOR_WEIGHT.length();
            String listWeightInputsString = input.substring(listWeightInputsIndex);
            String[] listWeightInputs = listWeightInputsString.split("\\s+");

            if (listWeightInputs.length > 3) {
                throw new FitNusException("Additional inputs detected! Please follow the command format:\n"
                        + "list /weight /TIMEFRAME (/month MONTH_INTEGER, /all)\n"
                        + "(e.g.list /weight /all OR list /weight /month 1)");
            }

            String timeFrame;
            try {
                timeFrame = listWeightInputs[1].strip();
            } catch (IndexOutOfBoundsException e) {
                throw new FitNusException("Invalid list weight command! Please enter it as "
                        + "list /weight /all or list /weight /month MONTH_INTEGER"
                        + System.lineSeparator() + "e.g. list /weight /month 1 for January");
            }
            switch (timeFrame) {
            case ALL_TIME:
                if (listWeightInputs.length > 2) {
                    throw new FitNusException("Additional inputs detected! Please follow the command format:\n"
                            + "list /weight /TIMEFRAME (/month MONTH_INTEGER, /all)\n"
                            + "(e.g.list /weight /all OR list /weight /month 1)");
                }
                return new ListWeightProgressCommand(0);
            case MONTH:
                int month;
                try {
                    month = Integer.parseInt(listWeightInputs[2].strip());

                    if (month < FIRST_MONTH || month > LAST_MONTH) {
                        throw new FitNusException("Please enter an integer from 1 to 12 for the month!");
                    }
                } catch (NumberFormatException e) {
                    throw new FitNusException("Please enter the month as an integer! e.g. 1 for January");
                } catch (IndexOutOfBoundsException e) {
                    throw new FitNusException("Please enter the month as an integer after entering /month!"
                            + " e.g. 'list /weight /month 1' for January");
                }
                return new ListWeightProgressCommand(month);
            default:
                throw new FitNusException("Invalid timeframe! Timeframe format is either /all "
                        + "or /month MONTH_INTEGER (e.g. /month 1)");
            }
        }
        throw new FitNusException(INVALID_COMMAND_MESSAGE);
    }

    private Command parseCalorieTypeCommand(String input) throws FitNusException {
        int typeDescriptorIndex = input.indexOf(" ");

        if (typeDescriptorIndex == -1) {
            if (input.equals(DESCRIPTOR_REMAIN)) {
                return new ViewRemainingCalorieCommand();
            }
        }

        String typeDescriptor = input.substring(0, typeDescriptorIndex).trim();
        if (DESCRIPTOR_SET.equals(typeDescriptor)) {
            int calorieGoal;
            try {
                calorieGoal = Integer.parseInt(input.substring(typeDescriptorIndex).trim());
            } catch (NumberFormatException e) {
                throw new FitNusException("Calorie goal entered must be an integer!\n"
                        + "Did you enter an invalid input or any additional inputs by mistake?");
            }
            return new SetCalorieGoalCommand(calorieGoal);
        }

        if (DESCRIPTOR_GENERATE.equals(typeDescriptor)) {
            int goalGenerationInputsIndex = input.indexOf(DESCRIPTOR_GENERATE) + DESCRIPTOR_GENERATE.length();
            String goalGenerationInputsString = input.substring(goalGenerationInputsIndex);
            String[] goalGenerationInputs = goalGenerationInputsString.split("\\s+");

            if (goalGenerationInputs.length > 3) {
                throw new FitNusException("Additional inputs detected! Please follow the command format:\n"
                        + "calorie /generate /CHANGE_TYPE WEEKLY_CHANGE_IN_KG\n"
                        + "(e.g. calorie /generate /lose 0.1)");
            }

            String weightChangeInput;

            weightChangeInput = goalGenerationInputs[1].strip();

            String weightChangeType;
            if (weightChangeInput.equals(GAIN)) {
                weightChangeType = "gain";
            } else if (weightChangeInput.equals(LOSE)) {
                weightChangeType = "lose";
            } else {
                throw new FitNusException("Invalid change type! "
                        + "Please enter /gain or /lose as the change type parameter.");
            }

            float weightChangeAmount;
            try {
                weightChangeAmount = Float.parseFloat(goalGenerationInputs[2].strip());
            } catch (NumberFormatException e) {
                throw new FitNusException("Please enter a number between 0.01 and 1 for the weekly change!");
            } catch (IndexOutOfBoundsException e) {
                throw new FitNusException("Please enter the weekly change! e.g. calorie /generate /lose 0.1");
            }

            return new GenerateCalorieGoalCommand(weightChangeAmount, weightChangeType);
        }
        throw new FitNusException(INVALID_COMMAND_MESSAGE);
    }

    private Command parseGenderTypeCommand(String input) throws FitNusException {
        int typeDescriptorIndex = input.indexOf(" ");
        String typeDescriptor = input.substring(0, typeDescriptorIndex).trim();
        if (typeDescriptor.equals(DESCRIPTOR_SET)) {
            String gender = input.substring(typeDescriptorIndex).trim();
            if (gender.equalsIgnoreCase("m") || gender.equalsIgnoreCase("f")) {
                return new SetGenderCommand(gender);
            }
            throw new FitNusException("Please input m for male or "
                    + "f for female when setting your gender!\n"
                    + "Did you enter an invalid character or any additional inputs by mistake?");
        }
        throw new FitNusException(INVALID_COMMAND_MESSAGE);
    }

    private Command parseAgeTypeCommand(String input) throws FitNusException {
        int typeDescriptorIndex = input.indexOf(" ");
        String typeDescriptor;
        typeDescriptor = input.substring(0, typeDescriptorIndex).trim();

        try {
            if (typeDescriptor.equals(DESCRIPTOR_SET)) {
                int age = Integer.parseInt(input.substring(typeDescriptorIndex).trim());
                if (age < MINIMUM_AGE) {
                    throw new FitNusException("Users of FitNUS must be " + MINIMUM_AGE
                            + " years old and above!");
                } else if (age > MAXIMUM_AGE) {
                    throw new FitNusException("Users of FitNUS cannot be older than " + MAXIMUM_AGE
                            + " years old!");
                }

                return new SetAgeCommand(age);
            }
        } catch (NumberFormatException e) {
            throw new FitNusException("Age entered must be an integer!\n"
                    + "Did you enter an invalid input or any additional inputs by mistake?");
        }
        throw new FitNusException(INVALID_COMMAND_MESSAGE);
    }

    private Command parseHeightTypeCommand(String input) throws FitNusException {
        int typeDescriptorIndex = input.indexOf(" ");
        String typeDescriptor = input.substring(0, typeDescriptorIndex).trim();
        try {
            if (typeDescriptor.equals(DESCRIPTOR_SET)) {
                int height = Integer.parseInt(input.substring(typeDescriptorIndex).trim());
                if (height < MINIMUM_HEIGHT) {
                    throw new FitNusException("Please enter a height of " + MINIMUM_HEIGHT
                            + " cm and above!");
                } else if (height > MAXIMUM_HEIGHT) {
                    throw new FitNusException("Please enter a height of " + MAXIMUM_HEIGHT
                            + " cm and below!");
                }
                return new SetHeightCommand(height);
            }
        } catch (NumberFormatException e) {
            throw new FitNusException("Height entered must be an integer!\n"
                    + "Did you enter an invalid input or any additional inputs by mistake?");
        }
        throw new FitNusException(INVALID_COMMAND_MESSAGE);
    }

    private Command parseWeightTypeCommand(String input) throws FitNusException {
        int typeDescriptorIndex = input.indexOf(" ");
        String typeDescriptor = input.substring(0, typeDescriptorIndex).trim();
        try {
            if (typeDescriptor.equals(DESCRIPTOR_SET)) {
                float weight = Float.parseFloat(input.substring(typeDescriptorIndex).trim());

                if (weight <= MINIMUM_WEIGHT) {
                    throw new FitNusException("Please enter a positive number for your weight!");
                } else if (weight > MAXIMUM_WEIGHT) {
                    throw new FitNusException("Please enter a weight of " + MAXIMUM_WEIGHT + " kg and below!");
                }
                return new SetWeightCommand(weight);
            }
        } catch (NumberFormatException e) {
            throw new FitNusException("Weight entered must be a positive number!\n"
                    + "Did you enter an invalid input or any additional inputs by mistake?");
        }
        throw new FitNusException(INVALID_COMMAND_MESSAGE);
    }

    private Command parseFindTypeCommand(String input) throws FitNusException {
        if (input.contains("/food")) {
            int typeDescriptorIndex = input.indexOf("/food");
            String keyword = input.substring(typeDescriptorIndex + 6);
            return new FindFoodsCommand(keyword);
        } else if (input.contains("/entry")) {
            int typeDescriptorIndex = input.indexOf("/entry");
            String keyword = input.substring(typeDescriptorIndex + 7);
            return new FindEntriesCommand(keyword);
        }
        throw new FitNusException("find command format is wrong. It is supposed to be:\n"
                + "find /food KEYWORD or find /entry KEYWORD");
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
            throw new FitNusException(PARSE_SUGGEST_ERROR);
        }
    }

    private Command parseSummaryTypeCommand(String input) throws FitNusException {
        if (input.equals(WEEK)) {
            return new ViewWeekSummaryCommand();
        } else if (input.equals(MONTH)) {
            return new ViewMonthSummaryCommand();
        }
        throw new FitNusException("That is an invalid summary timeframe (/week or /month)");
    }

    /**
     * Te function returns the respective Edit command with the user specified index and food.
     * @param input The user input.
     * @param fd The existing food database.
     * @param ed The existing entry database.
     * @return Returns the Edit command with the appropriate parameters.
     * @throws FitNusException Thrown when user inputs an invalid command.
     */
    private Command parseEditTypeCommand(String input, FoodDatabase fd, EntryDatabase ed) throws FitNusException {
        int typeDescriptorIndex = input.indexOf(SPACE_CHARACTER);
        int entryIndex = Integer.parseInt(input.substring(0, typeDescriptorIndex));
        int totalNumEntries = ed.getEntries().size();

        // Test for index validity
        if (entryIndex <= 0 || entryIndex > totalNumEntries) {
            throw new FitNusException("Please input a valid index!");
        }

        String foodName = input.substring(input.indexOf(SPACE_CHARACTER)).strip();

        //step 2: search database if food exists
        System.out.println("Searching for \"" + foodName + "\"...");
        ArrayList<Food> tempDbFoods = fd.findFoods(foodName);

        Ui newUi = new Ui();
        if (tempDbFoods.size() > 0) {
            System.out.println(" [X] Select your desired food from the list below:");
            Ui.printMatchingFoods(tempDbFoods);
        }
        int userInputLoop;

        //step 3a: prompt the user the suggestions if matches are found
        if (tempDbFoods.size() > 0) {
            System.out.println("Don't see what you're looking for? Enter 0 to create your own food!");
            Ui.print(Ui.USER_INPUT);
            return returnUserInput(entryIndex, foodName, tempDbFoods, newUi, true);
        } else if (tempDbFoods.size() == 0) {
            //step 3b: prompt the user to input calorie if not match
            System.out.println("Oops! \"" + foodName + "\" does not exist in the database!\n");
            return returnUserInput(entryIndex, foodName, tempDbFoods, newUi, false);
        }
        throw new FitNusException("Edit Parser Error");
    }

    /**
     * Returns LocalDate object based on input description.
     *
     * @param description String containing date.
     * @return LocalDate representing description.
     */
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
        LOGGER.log(Level.INFO, "Could not parse date");
        throw new FitNusException("Error parsing date!!");
    }

}
