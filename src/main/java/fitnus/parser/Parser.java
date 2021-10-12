package fitnus.parser;

import fitnus.exception.FitNusException;
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
            String subString = input.substring(spaceIndex + 1).trim();
            if (inputCommandType.equals(COMMAND_ADD)) { //add custom food
                return parseAddTypeCommand(subString);
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


        } catch (ArrayIndexOutOfBoundsException e) {
            throw new FitNusException("Input format is not correct. Follow the one stated!");
        } catch (NumberFormatException e) {
            throw new FitNusException("Input value is not an integer!");
        } catch (StringIndexOutOfBoundsException e) {
            throw new FitNusException("Did you forget to write the full command? :)");
        }
        throw new FitNusException(INVALID_COMMAND_MESSAGE);
    }

    private Command parseRemoveTypeCommand(String input) throws FitNusException{
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

    private Command parseAddTypeCommand(String input) throws FitNusException {
        int typeDescriptorIndex = input.indexOf(" ");

        if (typeDescriptorIndex == -1) {
            throw new FitNusException(INVALID_COMMAND_MESSAGE);
        }

        String typeDescriptor = input.substring(0, typeDescriptorIndex).trim();
        if (typeDescriptor.equals(DESCRIPTOR_CUSTOM)) {
            String[] foodDescription = input.substring(typeDescriptorIndex).split("\\|");
            String foodName = foodDescription[0].trim();
            int calorie = Integer.parseInt(foodDescription[1].trim());

            if (foodName.equals("")) {
                throw new FitNusException("Food name cannot be empty");
            }
            if (calorie <= 0) {
                throw new FitNusException("Calorie of food cannot be less than or equal to 0");
            }
            return new AddCustomFoodEntryCommand(foodName, calorie);
        }

        if (typeDescriptor.equals(DESCRIPTOR_DEFAULT)) {
            return new AddDefaultFoodEntryCommand(Integer.parseInt(input
                    .substring(typeDescriptorIndex).trim()));
        }

        throw new FitNusException(INVALID_COMMAND_MESSAGE);
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
        switch(typeDescriptor) {
        case DESCRIPTOR_SET:
            int calorieGoal = Integer.parseInt(input.substring(typeDescriptorIndex).trim());
            return new SetCalorieGoalCommand(calorieGoal);

        default:
            throw new FitNusException(INVALID_COMMAND_MESSAGE);
        }

    }

    private Command parseGenderTypeCommand(String input) throws FitNusException {
        int typeDescriptorIndex = input.indexOf(" ");
        String typeDescriptor = input.substring(0, typeDescriptorIndex).trim();
        try {
            if (typeDescriptor.equals(DESCRIPTOR_SET)) {
                return new SetGenderCommand(input.substring(typeDescriptorIndex).trim());
            }
        } catch (Exception e) {
            throw new FitNusException(INVALID_COMMAND_MESSAGE);
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
        logger.log(Level.INFO, "Could not parse date");
        throw new FitNusException("Error parsing date!!");
    }

}
