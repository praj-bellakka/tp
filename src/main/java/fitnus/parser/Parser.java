package fitnus.parser;

import fitnus.command.AddFoodEntryCommand;
import fitnus.command.AddMealPlanEntryCommand;
import fitnus.command.Command;
import fitnus.command.CreateMealPlanCommand;
import fitnus.command.DeleteEntryCommand;
import fitnus.command.DeleteFoodCommand;
import fitnus.command.EditFoodEntryCommand;
import fitnus.command.ExitCommand;
import fitnus.command.FindEntryCommand;
import fitnus.command.FindFoodCommand;
import fitnus.command.GenerateCalorieGoalCommand;
import fitnus.command.HelpCommand;
import fitnus.command.ListFoodDatabaseCommand;
import fitnus.command.ListFoodEntryAllCommand;
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
    private static final String DESCRIPTOR_DEFAULT = "/def";
    private static final String DESCRIPTOR_REMAIN = "/remain";
    private static final String DESCRIPTOR_GENERATE = "/generate";
    private static final String DESCRIPTOR_SET = "/set";
    private static final String DESCRIPTOR_MEALPLAN = "/mealplan";
    public static final int INVALID_INPUT = -1;
    public static final String INVALID_COMMAND_MESSAGE = "That was an invalid command! PLease try again!";

    // FoodType related strings
    private static final String MEAL = "/meal";
    private static final String BEVERAGE = "/beverage";
    private static final String SNACK = "/snack";
    private static final String OTHERS = "/others";

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
    private static final int MINIMUM_HEIGHT = 40;

    // Timeframe
    private static final int DAYS_IN_DAY = 1;
    private static final int DAYS_IN_WEEK = 7;

    private static boolean isLoopFlagOn = true;

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
                return parseSuggestTypeCommand(subString);
            }

            if (inputCommandType.equals(COMMAND_SUMMARY)) { //summary type command
                return parseSummaryTypeCommand(subString);
            }

            if (inputCommandType.equals(COMMAND_CREATE)) { //summary type command
                return parseCreateCommand(subString, fd);
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
     * The function finds the MealType of the food using {@link #parseMealType(String, boolean)}.
     * If no meal type was added by the user, it will be automatically allocated based on time of day.
     * The food is then searched thorugh the database using FoodDatabase. If food match exists, user will be prompted to
     * either select from an existing foodlist by entering its index, or create a custom food by entering <0>.
     * If no food match exists, the user will be prompted to enter the food's calorie between 0 to 5000.
     *
     * @param input String containing food name and mealtype (optional).
     * @param fd    FoodDatabase object passed from Main(), containing food in database.
     * @return Command object
     * @throws FitNusException Thrown when foodname is empty.
     */
    private Command parseAddTypeCommand(String input, FoodDatabase fd, MealPlanDatabase md) throws FitNusException {
        //step 1: find meal category and food name
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
        ArrayList<Food> tempFoodDb = fd.findFoods(foodName);

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

    private AddMealPlanEntryCommand parseAddMealPlanFoodCommand(MealPlanDatabase md, String input)
            throws FitNusException {
        int spaceIndex = input.indexOf(SPACE_CHARACTER);
        if (spaceIndex == -1) {
            throw new FitNusException("Invalid format");
        }
        String remainingString = input.substring(spaceIndex).strip();
        int spaceRemainingIndex = remainingString.indexOf(SPACE_CHARACTER);
        if (spaceRemainingIndex == -1) {
            throw new FitNusException("Invalid format");
        }
        MealType mealType = parseMealType(remainingString.substring(0, spaceRemainingIndex), false);
        try {
            int index = Integer.parseInt(remainingString.substring(spaceRemainingIndex).strip());
            return new AddMealPlanEntryCommand(md.getMealAtIndex(index), mealType);
        } catch (NumberFormatException e) {
            throw new FitNusException("Integer index could not be parsed. Check format again!");
        }
    }

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
            mealNameString = input.substring(input.indexOf(SPACE_CHARACTER)).strip();
        }

        //display all current foods TODO: refactor
        System.out.println("We will now create a mealplan! To create a Meal plan, "
                + "enter the indexes of the foods below with spaces in between each index.");
        System.out.println("For example: 1 2 8 4");
        System.out.println("Indexes that are not present/invalid will be ignored. "
                + "Duplicates are allowed, but try to not eat so much food :)");
        System.out.println("Here is a list of all foods present in the database:");
        System.out.println(fd.listFoods());
        Ui newUi = new Ui();
        ArrayList<Food> tempMealFoods = new ArrayList<Food>();

        String[] userInputIndexes = newUi.readIndexesInput();
        //for each index, check if its integer and within range
        for (String i : userInputIndexes) {
            try {
                int inputInt = Integer.parseInt(i);
                if (inputInt > fd.getFoodDatabase().size() || inputInt <= 0) {
                    System.out.println("Input " + inputInt + " was out of range. Ignoring Input");
                    continue;
                }
                tempMealFoods.add(fd.getFoodDatabase().get(inputInt - 1));
            } catch (NumberFormatException e) {
                System.out.println("Input " + i + " was not an integer. Ignoring input.");
            }
        }
        return new CreateMealPlanCommand(tempMealFoods, mealNameString);
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
                                                Ui newUi, boolean multipleEntries) throws FitNusException {
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
                System.out.println("Enter food type (meal, snack, beverage, others):");
                type = parseFoodType(newUi.readInput());
            } while (type == null);

            return new AddFoodEntryCommand(mealType, foodName, userInput, type);
        }
        return new AddFoodEntryCommand(mealType, tempFoodDb.get(userInput - 1));
    }

    private EditFoodEntryCommand returnUserInput(int index, String foodName, ArrayList<Food> tempFoodDb,
                                                 Ui newUi, boolean multipleEntries) throws FitNusException {
        int userInput = 0;
        if (multipleEntries) {
            do {
                userInput = parseInteger(newUi.readInput(), tempFoodDb.size());
            } while (isLoopFlagOn);
        }

        /**
         * If user input is 0, the user specified his input to be a custom food.
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
                System.out.println("Enter food type (meal, snack, beverage, others):");
                type = parseFoodType(newUi.readInput());
            } while (type == null);

            return new EditFoodEntryCommand(index, foodName, userInput, type);
        }
        return new EditFoodEntryCommand(index, tempFoodDb.get(userInput - 1));
    }

    public static Food.FoodType parseFoodType(String type) throws FitNusException {
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
            throw new FitNusException("Unable to parse Food type");
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
        String removeType = input.substring(0, typeDescriptorIndex);
        try {
            if (removeType.equals(DESCRIPTOR_FOOD)) {
                return new DeleteFoodCommand(Integer.parseInt(input
                        .substring(typeDescriptorIndex).trim()));
            } else if (removeType.equals(DESCRIPTOR_INTAKE)) {
                return new DeleteEntryCommand(Integer.parseInt(input
                        .substring(typeDescriptorIndex).trim()));
            }
            throw new FitNusException("Invalid remove command!");
        } catch (NumberFormatException e) {
            throw new FitNusException("Input value is not an integer!");
        }
    }

    private Command parseListTypeCommand(String input) throws FitNusException {
        int typeDescriptorIndex = input.indexOf(" ");
        if (typeDescriptorIndex == -1) {
            if (input.equals(DESCRIPTOR_FOOD)) {
                return new ListFoodDatabaseCommand();
            } else if (input.equals(DESCRIPTOR_INTAKE)) {
                return new ListFoodEntryAllCommand();
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
            String timeFrame = listWeightInputs[1].strip();

            switch (timeFrame) {
            case ALL_TIME:
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
            int calorieGoal = Integer.parseInt(input.substring(typeDescriptorIndex).trim());
            return new SetCalorieGoalCommand(calorieGoal);
        }
        if (DESCRIPTOR_GENERATE.equals(typeDescriptor)) {
            int goalGenerationInputsIndex = input.indexOf(DESCRIPTOR_GENERATE) + DESCRIPTOR_GENERATE.length();
            String goalGenerationInputsString = input.substring(goalGenerationInputsIndex);
            String[] goalGenerationInputs = goalGenerationInputsString.split("\\s+");
            String weightChangeInput = goalGenerationInputs[1].strip();
            String weightChangeType;
            if (weightChangeInput.equals(GAIN)) {
                weightChangeType = "gain";
            } else if (weightChangeInput.equals(LOSE)) {
                weightChangeType = "lose";
            } else {
                throw new FitNusException("Invalid change type! "
                        + "Please enter /gain or /lose as the change type parameter.");
            }
            float weightChangeAmount = Float.parseFloat(goalGenerationInputs[2].strip());

            return new GenerateCalorieGoalCommand(weightChangeAmount, weightChangeType);
        }
        throw new FitNusException(INVALID_COMMAND_MESSAGE);
    }

    private Command parseGenderTypeCommand(String input) throws FitNusException {
        int typeDescriptorIndex = input.indexOf(" ");
        String typeDescriptor = input.substring(0, typeDescriptorIndex).trim();
        if (typeDescriptor.equals(DESCRIPTOR_SET)) {
            String gender = input.substring(typeDescriptorIndex).trim();
            if (gender.toLowerCase().equals("m") || gender.toLowerCase().equals("f")) {
                return new SetGenderCommand(gender);
            }
            throw new FitNusException("Invalid input! Please input m for male or "
                    + "f for female when setting your gender.");
        }
        throw new FitNusException(INVALID_COMMAND_MESSAGE);
    }

    private Command parseAgeTypeCommand(String input) throws FitNusException {
        int typeDescriptorIndex = input.indexOf(" ");
        String typeDescriptor = input.substring(0, typeDescriptorIndex).trim();
        try {
            if (typeDescriptor.equals(DESCRIPTOR_SET)) {
                int age = Integer.parseInt(input.substring(typeDescriptorIndex).trim());
                if (age < MINIMUM_AGE) {
                    throw new FitNusException("Users of FitNUS must be " + MINIMUM_AGE
                            + " years old and above!");
                }

                return new SetAgeCommand(age);
            }
        } catch (NumberFormatException e) {
            throw new FitNusException("The age must be an integer!");
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
                }
                return new SetHeightCommand(height);
            }
        } catch (NumberFormatException e) {
            throw new FitNusException("The height must be an integer!");
        }
        throw new FitNusException(INVALID_COMMAND_MESSAGE);
    }

    private Command parseWeightTypeCommand(String input) throws FitNusException {
        int typeDescriptorIndex = input.indexOf(" ");
        String typeDescriptor = input.substring(0, typeDescriptorIndex).trim();
        try {
            if (typeDescriptor.equals(DESCRIPTOR_SET)) {
                float weight = Float.parseFloat(input.substring(typeDescriptorIndex).trim());
                if (weight <= 0) {
                    throw new FitNusException("Please enter a positive number for your weight!");
                }
                return new SetWeightCommand(weight);
            }
        } catch (NumberFormatException e) {
            throw new FitNusException("The height must be an integer!");
        }
        throw new FitNusException(INVALID_COMMAND_MESSAGE);
    }

    private Command parseFindTypeCommand(String input) throws FitNusException {
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

    private Command parseSuggestTypeCommand(String input) throws FitNusException {
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

    private Command parseSummaryTypeCommand(String input) throws FitNusException {
        if (input.equals(WEEK)) {
            return new ViewWeekSummaryCommand();
        } else if (input.equals(MONTH)) {
            return new ViewMonthSummaryCommand();
        }
        throw new FitNusException("That is an invalid summary timeframe (/week or /month)");
    }

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
        ArrayList<Food> tempFoodDb = fd.findFoods(foodName);

        Ui newUi = new Ui();
        Ui.printMatchingFoods(tempFoodDb); //search database for match
        int userInputLoop;

        //step 3a: prompt the user the suggestions if matches are found
        if (tempFoodDb.size() > 0) {
            //TODO: Beautify the print statement
            System.out.println("Select the food you want by entering the number below. "
                    + "If the food doesn't exist, enter 0 to create a new custom food!");
            return returnUserInput(entryIndex, foodName, tempFoodDb, newUi, true);
        } else if (tempFoodDb.size() == 0) {
            //step 3b: prompt the user to input calorie if not match
            System.out.println("The food you specified does not exist in the database!");
            return returnUserInput(entryIndex, foodName, tempFoodDb, newUi, false);
        }
        throw new FitNusException("Edit Parser Error");
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
