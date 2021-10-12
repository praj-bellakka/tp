package fitnus;

import fitnus.command.AddCustomFoodEntryCommand;
import fitnus.command.AddDefaultFoodEntryCommand;
import fitnus.command.Command;
import fitnus.command.DeleteFoodEntryCommand;
import fitnus.command.ListFoodDatabaseCommand;
import fitnus.command.ListFoodIntakeCommand;
import fitnus.command.SetCalorieGoalCommand;
import fitnus.command.SetGenderCommand;
import fitnus.command.ViewRemainingCalorieCommand;
import fitnus.exception.FitNusException;
import fitnus.parser.Parser;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ParserTest {

    Parser parser = new Parser();
    public static final String INVALID_COMMAND_MESSAGE = "That was an invalid command! PLease try again!";

    @Test
    void parseCommandType_correctInput_parsedCorrectly() throws FitNusException {
        assertTrue(parser.parseCommandType("add /cust food1 | 21") instanceof AddCustomFoodEntryCommand);
        assertTrue(parser.parseCommandType("add /def 10") instanceof AddDefaultFoodEntryCommand);
        assertTrue(parser.parseCommandType("list /food") instanceof ListFoodDatabaseCommand);
        assertTrue(parser.parseCommandType("list /intake /DAY") instanceof ListFoodIntakeCommand);
        assertTrue(parser.parseCommandType("gender /set M/F") instanceof SetGenderCommand);
        assertTrue(parser.parseCommandType("remove /food 2") instanceof DeleteFoodEntryCommand);
        assertTrue(parser.parseCommandType("calorie /set 300") instanceof SetCalorieGoalCommand);
        assertTrue(parser.parseCommandType("calorie /remain") instanceof ViewRemainingCalorieCommand);

    }

    @Test
    void parseCommandType_wrongInput_invalidCommand() {
        Exception exception1 = assertThrows(FitNusException.class, () -> parser.parseCommandType("add food1 | 21"));
        assertEquals(INVALID_COMMAND_MESSAGE, exception1.getMessage());

        Exception exception2 = assertThrows(FitNusException.class, () -> parser.parseCommandType("genderr /set M/F"));
        assertEquals(INVALID_COMMAND_MESSAGE, exception2.getMessage());

        Exception exception3 = assertThrows(FitNusException.class, () -> parser.parseCommandType("remove/food 2"));
        assertEquals(INVALID_COMMAND_MESSAGE, exception3.getMessage());

        Exception exception4 = assertThrows(FitNusException.class, () -> parser.parseCommandType("calorie/set GOAL"));
        assertEquals(INVALID_COMMAND_MESSAGE, exception4.getMessage());

        Exception exception5 = assertThrows(FitNusException.class, () -> parser.parseCommandType("calories /remain"));
        assertEquals(INVALID_COMMAND_MESSAGE, exception5.getMessage());
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

        String localDateInput5 = "dkfjvghkjdfs"; //nonsense values
        Exception exception5 = assertThrows(FitNusException.class, () -> Parser.getDate(localDateInput5));
        assertEquals("Error parsing date!!", exception5.getMessage());

        String localDateInput6 = ""; //empty string
        AssertionError exception6 = assertThrows(AssertionError.class, () -> Parser.getDate(localDateInput6));
        assertEquals("String line should not be empty", exception6.getMessage());

    }

    @Test
    void getDate_validInput_returnDate() throws FitNusException {
        String input1 = "2020-03-03"; //correct format

        assertEquals(LocalDate.class, parser.getDate(input1).getClass());
    }

    @Test
    void parseCommandType_validGenderInput_returnCommand() throws FitNusException {
        String input = "gender /set M";
        Command returnCommand = parser.parseCommandType(input);

        assertEquals(SetGenderCommand.class, returnCommand.getClass());
    }
}
