package fitnus.parser;

import fitnus.FitNusException;
import fitnus.command.AddCustomFoodEntryCommand;
import fitnus.command.AddDefaultFoodEntryCommand;
import fitnus.command.Command;
import fitnus.command.DeleteFoodEntryCommand;
import fitnus.command.ExitCommand;
import fitnus.command.HelpCommand;
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

    //main command types
    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_CALORIE = "calorie";
    private static final String COMMAND_REMOVE = "remove";
    private static final String COMMAND_GENDER = "gender";

    //specific descriptors of the main command types
    private static final String DESCRIPTOR_CUSTOM = "/cust";
    private static final String DESCRIPTOR_FOOD = "/food";
    private static final String DESCRIPTOR_INTAKE = "/intake";
    private static final String DESCRIPTOR_DEFAULT = "/def";
    private static final String DESCRIPTOR_REMAIN = "/remain";
    private static final String DESCRIPTOR_SET = "/set";
    public static final int INVALID_INPUT = -1;
    public static final String INVALID_COMMAND_MESSAGE = "That was an invalid command! PLease try again!";


    public Command parseCommandType(String input) throws FitNusException {
        String[] splitString = input.strip().split(" ");
        try {
            int spaceIndex = input.indexOf(SPACE_CHARACTER);

            /**
             * If no space is detected, the input does not contain any tracker related actions.
             * Help, Exit or Invalid command will be returned based on the input.
             */
            if (spaceIndex == INVALID_INPUT) {
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
            if (inputCommandType.equals(COMMAND_ADD)) { //add custom food
                String subString = input.substring(spaceIndex + 1);
                return parseAddTypeCommand(subString);
            }

            if (inputCommandType.equals(COMMAND_LIST)) { //list type command
                return parseListTypeCommand(splitString);
            }

            if (inputCommandType.equals(COMMAND_CALORIE)) { //calorie type command
                return parseCalorieTypeCommand(splitString);
            }

            if (inputCommandType.equals(COMMAND_GENDER)) { //gender type command
                return parseGenderTypeCommand(splitString);
            }

            if (inputCommandType.equals(COMMAND_REMOVE)) {
                return parseRemoveTypeCommand(splitString);
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

    private Command parseRemoveTypeCommand(String[] splitInput) {
        return new DeleteFoodEntryCommand(Integer.parseInt(splitInput[2]));
    }

    private Command parseListTypeCommand(String[] splitInput) throws FitNusException {
        switch (splitInput[1]) {
        case DESCRIPTOR_INTAKE:
            return new ListFoodIntakeCommand(splitInput[2]);
        case DESCRIPTOR_FOOD:
            return new ListFoodDatabaseCommand();
        default:
            throw new FitNusException(INVALID_COMMAND_MESSAGE);
        }
    }

    private Command parseAddTypeCommand(String input) throws FitNusException {
        if (input.contains(DESCRIPTOR_CUSTOM)) {
            String[] foodDescription = input.substring(6).split("\\|");
            return new AddCustomFoodEntryCommand(foodDescription[0].trim(),
                    Integer.parseInt(foodDescription[1].trim()));
        }

        if (input.contains(DESCRIPTOR_DEFAULT)) {
            return new AddDefaultFoodEntryCommand(Integer.parseInt(input.substring(5)));
        }

        throw new FitNusException(INVALID_COMMAND_MESSAGE);
    }

    private Command parseCalorieTypeCommand(String[] splitInput) throws FitNusException {
        switch (splitInput[1]) {
        case DESCRIPTOR_SET:
            return new SetCalorieGoalCommand(Integer.parseInt(splitInput[2]));
        case DESCRIPTOR_REMAIN:
            return new ViewRemainingCalorieCommand();
        default:
            throw new FitNusException(INVALID_COMMAND_MESSAGE);
        }

    }

    private Command parseGenderTypeCommand(String[] splitInput) throws FitNusException {
        if (splitInput[1].equals(DESCRIPTOR_SET)) {
            return new SetGenderCommand(splitInput[2]);
        }
        throw new FitNusException(INVALID_COMMAND_MESSAGE);
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
        throw new FitNusException("Error parsing date!!");
    }

}
