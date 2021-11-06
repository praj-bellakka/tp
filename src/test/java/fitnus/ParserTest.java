package fitnus;

import fitnus.command.ExitCommand;
import fitnus.command.HelpCommand;
import fitnus.command.ListFoodDatabaseCommand;
import fitnus.command.ListFoodEntryCustomCommand;
import fitnus.command.ListWeightProgressCommand;
import fitnus.command.SetWeightCommand;
import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.parser.Parser;
import fitnus.tracker.MealType;
import fitnus.tracker.Summary;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ParserTest {
    Parser parser = new Parser();
    public static final String INVALID_COMMAND_MESSAGE = "That was an invalid command! "
            + "Type 'help' for a list of commands\n"
            + "and their command formats.";
    EntryDatabase ed = new EntryDatabase();
    FoodDatabase fd = new FoodDatabase();
    MealPlanDatabase md = new MealPlanDatabase();

    @Test
    void parseCommandType_correctInput_parsedCorrectly() throws FitNusException {
        assertTrue(parser.parseCommandType("help", null, null, null) instanceof HelpCommand);
        assertTrue(parser.parseCommandType("exit", null, null, null) instanceof ExitCommand);
        assertTrue(parser.parseCommandType("list /food", null, null, null) instanceof ListFoodDatabaseCommand);
        assertTrue(parser.parseCommandType("weight /set 120", null, null, null) instanceof SetWeightCommand);

    }


    @Test
    void parseCommandType_wrongInput_invalidCommand() {
        Exception exception1 = assertThrows(FitNusException.class, () -> parser.parseCommandType(
                "add", fd, ed, md)); //test for invalid input
        assertEquals(INVALID_COMMAND_MESSAGE, exception1.getMessage());

        Exception exception2 = assertThrows(FitNusException.class, () -> parser.parseCommandType(
                "invalid command 123", fd, ed, md));
        assertEquals(INVALID_COMMAND_MESSAGE, exception2.getMessage());

    }

    @Test
    void getDate_invalidInput_getException() {
        String localDateInput1 = "2021-10"; //no day specified
        Exception exception1 = assertThrows(FitNusException.class, () -> Parser.getDate(localDateInput1));
        assertEquals("Error parsing date!!", exception1.getMessage());

        String localDateInput2 = "2021-101-20"; //invalid month
        Exception exception2 = assertThrows(FitNusException.class, () -> Parser.getDate(localDateInput2));
        assertEquals("Error parsing date!!", exception2.getMessage());

        String localDateInput3 = "2021-03-50"; //invalid day
        Exception exception3 = assertThrows(FitNusException.class, () -> Parser.getDate(localDateInput3));
        assertEquals("Error parsing date!!", exception3.getMessage());

        String localDateInput4 = "123-10-10"; //invalid year
        Exception exception4 = assertThrows(FitNusException.class, () -> Parser.getDate(localDateInput4));
        assertEquals("Error parsing date!!", exception4.getMessage());

        String localDateInput5 = "nonsensevalues"; //nonsense values
        Exception exception5 = assertThrows(FitNusException.class, () -> Parser.getDate(localDateInput5));
        assertEquals("Error parsing date!!", exception5.getMessage());

        String localDateInput6 = ""; //empty string
        AssertionError exception6 = assertThrows(AssertionError.class, () -> Parser.getDate(localDateInput6));
        assertEquals("String line should not be empty", exception6.getMessage());

    }

    @Test
    void parseMealType_validInput_returnMealType() throws FitNusException {
        String input1 = "Breakfast";
        String input2 = "/bfast";
        assertEquals(MealType.BREAKFAST, Parser.parseMealType(input1, true));
        assertEquals(MealType.BREAKFAST, Parser.parseMealType(input2, false));

        String input3 = "Lunch";
        String input4 = "/lunch";
        assertEquals(MealType.LUNCH, Parser.parseMealType(input3, true));
        assertEquals(MealType.LUNCH, Parser.parseMealType(input4, false));

        String input5 = "Dinner";
        String input6 = "/dinner";
        assertEquals(MealType.DINNER, Parser.parseMealType(input5, true));
        assertEquals(MealType.DINNER, Parser.parseMealType(input6, false));

        String input7 = "Snack";
        String input8 = "/snack";
        assertEquals(MealType.SNACK, Parser.parseMealType(input7, true));
        assertEquals(MealType.SNACK, Parser.parseMealType(input8, false));
    }

    @Test
    void parseMealType_invalidInput_returnUndefinedMealType() throws FitNusException {
        String input1 = "breakfast";
        String input2 = "bfas";
        assertEquals(MealType.UNDEFINED, Parser.parseMealType(input1, true));
        assertEquals(MealType.UNDEFINED, Parser.parseMealType(input2, false));

        String input3 = "random words";
        assertEquals(MealType.UNDEFINED, Parser.parseMealType(input3, true));
        assertEquals(MealType.UNDEFINED, Parser.parseMealType(input3, false));

        String input5 = "";
        assertEquals(MealType.UNDEFINED, Parser.parseMealType(input5, true));
        assertEquals(MealType.UNDEFINED, Parser.parseMealType(input5, false));
    }

    @Test
    void parseMealType_invalidInput_throwFitNusException() throws FitNusException {
        String input1 = "/bfastt";
        Exception exception1 = assertThrows(FitNusException.class, () -> Parser.parseMealType(input1, false));
        assertEquals(input1 + " is an invalid food category. "
                + "Avoid using the backslash character if food category is not specified.", exception1.getMessage());

    }

    @Test
    void getDate_validInput_returnDate() throws FitNusException {
        String input1 = "2020-03-03"; //correct format
        assertEquals(LocalDate.class, parser.getDate(input1).getClass());

        String line = "2021-12-23";
        LocalDate date = Parser.getDate(line);
        assertEquals(LocalDate.of(2021, 12, 23), date);
    }

    @Test
    void parseCommandType_validGenderInput_returnCommand() throws FitNusException {
    //        String input = "gender /set M";
    //        Command returnCommand = parser.parseCommandType(input);
    //
    //        assertEquals(SetGenderCommand.class, returnCommand.getClass());
    }
}
