package fitnus.parser;

import fitnus.command.AddCustomFoodEntryCommand;
import fitnus.command.AddDefaultFoodEntryCommand;
import fitnus.command.Command;
import fitnus.command.DeleteFoodEntryCommand;
import fitnus.command.ExitCommand;
import fitnus.command.InvalidCommand;
import fitnus.command.ListFoodDatabaseCommand;
import fitnus.command.ListFoodIntakeCommand;
import fitnus.command.SetCalorieGoalCommand;
import fitnus.command.SetGenderCommand;
import fitnus.command.ViewRemainingCalorieCommand;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Handles the extraction of user inputs into relevant components.
 */
public class Parser {

    private static final String SPACE_CHARACTER = " ";
    private static final String BACKSLASH_CHARACTER = "/";
    private static final String PIPE_CHARACTER = "|";

    private static final String DESCRIPTOR_CUSTOM = "/cust";
    private static final String DESCRIPTOR_DEFAULT = "/def";
    private static final String DESCRIPTOR_INTAKE = "/intake";
    private static final String DESCRIPTOR_FOOD = "/food";
    private static final String DESCRIPTOR_SET = "/set";
    private static final String DESCRIPTOR_REMAIN = "/remain";

    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_CALORIE = "calorie";
    private static final String COMMAND_REMOVE = "remove";
    private static final String COMMAND_GENDER = "gender";


    public Command parseCommandType(String input) {
        String[] split = input.split(" ");
        try {
            int spaceIndex = input.indexOf(SPACE_CHARACTER);

            if (spaceIndex == -1) {
                return new ExitCommand();
            }

            String inputType = input.substring(0, spaceIndex);
            if (inputType.equals(COMMAND_ADD)) { //add custom food
                return parseAddTypeCommand(input.substring(spaceIndex + 1));
            }

            if (inputType.equals(COMMAND_LIST)) { //list type command
                return parseListTypeCommand(split);
            }

            if (inputType.equals(COMMAND_CALORIE)) { //calorie type command
                return parseCalorieTypeCommand(split);
            }

            if (inputType.equals(COMMAND_GENDER)) { //gender type command
                return parseGenderTypeCommand(split);
            }

            if (inputType.equals(COMMAND_REMOVE)) {
                return parseRemoveTypeCommand(split);
            }

        } catch (Exception e) {
            return new InvalidCommand();
        }
        return new InvalidCommand();
    }


    private Command parseRemoveTypeCommand(String[] splitInput) {
        return new DeleteFoodEntryCommand(Integer.parseInt(splitInput[2]));
    }

    private Command parseListTypeCommand(String[] splitInput) {
        switch (splitInput[1]) {
        case DESCRIPTOR_INTAKE:
            return new ListFoodIntakeCommand(splitInput[2]);
        case DESCRIPTOR_FOOD:
            return new ListFoodDatabaseCommand();
        default:
            return new InvalidCommand();
        }
    }

    private Command parseAddTypeCommand(String input) {
        if (input.contains(DESCRIPTOR_CUSTOM)) {
            String[] foodDescription = input.substring(6).split("\\|");
            return new AddCustomFoodEntryCommand(foodDescription[0].trim(),
                    Integer.parseInt(foodDescription[1].trim()));
        }

        if (input.contains(DESCRIPTOR_DEFAULT)) {
            return new AddDefaultFoodEntryCommand(Integer.parseInt(input.substring(5)));
        }

        return new InvalidCommand();
    }

    private Command parseCalorieTypeCommand(String[] splitInput) {
        switch (splitInput[1]) {
        case DESCRIPTOR_SET:
            return new SetCalorieGoalCommand(Integer.parseInt(splitInput[2]));
        case DESCRIPTOR_REMAIN:
            return new ViewRemainingCalorieCommand();
        default:
            return new InvalidCommand();
        }

    }

    private Command parseGenderTypeCommand(String[] splitInput) {
        if (splitInput[1].equals(DESCRIPTOR_SET)) {
            return new SetGenderCommand(splitInput[2]);
        }
        return new InvalidCommand();
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
     */
    public static LocalDate getDate(String line) {
        String[] description = line.split(" ");
        LocalDate date;
        for (String s : description) {
            date = parseDate(s);
            if (date != null) {
                return date;
            }
        }
        return null;
    }

}
