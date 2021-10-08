package fitnus.parser;

import fitnus.FitNusException;
import fitnus.command.AddCustomFoodEntryCommand;
import fitnus.command.AddDefaultFoodEntryCommand;
import fitnus.command.Command;
import fitnus.command.ListFoodDatabaseCommand;
import fitnus.command.DeleteFoodEntryCommand;
import fitnus.command.ExitCommand;
import fitnus.command.ListFoodIntakeCommand;
import fitnus.command.SetCalorieGoalCommand;
import fitnus.command.SetGenderCommand;
import fitnus.command.ViewRemainingCalorieCommand;

/**
 * Handles the extraction of user inputs into relevant components.
 */
public class Parser {

    public static final String SPACE_CHARACTER = " ";
    public static final String BACKSLASH_CHARACTER = "/";
    public static final String PIPE_CHARACTER = "|";

    public static final String DESCRIPTOR_CUSTOM = "/cust";
    public static final String DESCRIPTOR_DEFAULT = "/def";
    public static final String DESCRIPTOR_INTAKE = "/intake";
    public static final String DESCRIPTOR_FOOD = "/food";
    public static final String DESCRIPTOR_SET = "/set";
    public static final String DESCRIPTOR_REMAIN = "/remain";

    public static final String COMMAND_ADD = "add";
    public static final String COMMAND_LIST = "list";
    public static final String COMMAND_CALORIE = "calorie";
    public static final String COMMAND_REMOVE = "remove";
    public static final String COMMAND_GENDER = "gender";


    public Command parseCommandType(String input) throws FitNusException {
        String commandType = parseInputType(input);
        switch (commandType) {
        case "adddefault":
            int idx = parseIntegers(input);
            return new AddDefaultFoodEntryCommand(idx);
        case "addcustom":
            String foodName = parseFoodName(input);
            int cal = parseIntegers(input);
            return new AddCustomFoodEntryCommand(foodName, cal);
        case "listdatabase":
            return new ListFoodDatabaseCommand();
        case "listintake":
            String timeSpan = parseTimeSpan(input);
            return new ListFoodIntakeCommand(timeSpan);
        case "genderset":
            String genderSymbol = parseGenderSymbol(input);
            return new SetGenderCommand(genderSymbol);
        case "remove":
            int index = parseIntegers(input);
            return new DeleteFoodEntryCommand(index);
        case "calorieset":
            int calories = parseIntegers(input);
            return new SetCalorieGoalCommand(calories);
        case "calorieremain":
            return new ViewRemainingCalorieCommand();
        default:
            throw new FitNusException("No such command found! Please try again!");
        }
    }

    /**
     * Returns a string of the input type.
     * Parser will assume the first word of the input is the type, and uses space as the end character.
     *
     * @param input user input.
     * @return String containing the type.
     */
    public String parseInputType(String input) {
        String inputType;
        try {
            String cleanedString = input.toLowerCase().trim(); //removes whitespace and converts to lower case
            inputType = cleanedString.substring(0, input.indexOf(SPACE_CHARACTER));

            //checks for special command within same input type
            if (inputType.equals(COMMAND_ADD) && input.contains(DESCRIPTOR_CUSTOM)) { //add custom food
                return inputType.concat("custom");
            } else if (inputType.equals(COMMAND_ADD) && input.contains(DESCRIPTOR_DEFAULT)) { //add default food
                return inputType.concat("default");
            } else if (inputType.equals(COMMAND_LIST) && input.contains(DESCRIPTOR_FOOD)) { //list food database
                return inputType.concat("database");
            } else if (inputType.equals(COMMAND_LIST) && input.contains(DESCRIPTOR_INTAKE)) { //list food intake
                return inputType.concat("intake");
            } else if (inputType.equals(COMMAND_CALORIE) && input.contains(DESCRIPTOR_SET)) { //set calorie
                return inputType.concat("set");
            } else if (inputType.equals(COMMAND_CALORIE) && input.contains(DESCRIPTOR_REMAIN)) { //view remaining cal
                return inputType.concat("remain");
            } else if (inputType.equals(COMMAND_GENDER) && input.contains(DESCRIPTOR_SET)) { //set gender
                return inputType.concat("set");
            }
            return inputType;
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a string containing the food name.
     *
     * @param input Input containing the food name.
     * @return String of food name.
     */
    public String parseFoodName(String input) {
        try {
            String foodName = input.substring(input.indexOf(DESCRIPTOR_CUSTOM) + DESCRIPTOR_CUSTOM.length(),
                    input.indexOf(PIPE_CHARACTER)).strip();
            return foodName;
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a string containing the gender symbol.
     *
     * @param input Input containing the gender symbol.
     * @return String of gender symbol (M or F).
     */
    public String parseGenderSymbol(String input) {
        try {
            String genderSymbol = input.substring(input.indexOf(DESCRIPTOR_SET) + DESCRIPTOR_SET.length()).strip();
            return genderSymbol;
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a string containing the time span descriptor.
     *
     * @param input Input containing the time span descriptor.
     * @return String of time span descriptor.
     */
    public String parseTimeSpan(String input) {
        try {
            String timeSpan = input.substring(input.indexOf(DESCRIPTOR_INTAKE) + DESCRIPTOR_INTAKE.length()).strip();
            return timeSpan;
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns an integer value based on keyword specified in input.
     * Function works for the keywords "/cal", "/def" and "/day".
     * Throws NumberFormatException if an integer cannot be detected after the keyword.
     *
     * @param input Input containing a keyword and an integer.
     * @return Integer specified in the string. Returns -1 if no integer is detected.
     */
    public int parseIntegers(String input) {
        int integerVal = 0;
        String cleanedString = input.toLowerCase().trim();
        try {
            if (cleanedString.contains(DESCRIPTOR_CUSTOM)) {
                integerVal = Integer.parseInt(cleanedString
                        .substring(cleanedString.indexOf(PIPE_CHARACTER) + PIPE_CHARACTER.length())
                        .strip());
            } else if (cleanedString.contains(BACKSLASH_CHARACTER)) {
                integerVal = Integer.parseInt(cleanedString.replaceAll("[^0-9]", ""));
            }
            return integerVal;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return -1;
    }


}
