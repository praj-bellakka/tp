package fitnus;

import fitnus.command.AddCustomFoodEntryCommand;
import fitnus.command.AddDefaultFoodEntryCommand;
import fitnus.command.DeleteFoodEntryCommand;
import fitnus.command.ListFoodDatabaseCommand;
import fitnus.command.ListFoodIntakeCommand;
import fitnus.command.SetCalorieGoalCommand;
import fitnus.command.SetGenderCommand;
import fitnus.command.ViewRemainingCalorieCommand;
import fitnus.parser.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.opentest4j.MultipleFailuresError;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ParserTest {

    Parser parser = new Parser();
    public static final String INVALID_COMMAND_MESSAGE = "That was an invalid command! PLease try again!";

    @Test
    void parseInputType_correctInput_parsedCorrectly() throws FitNusException {
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
    void parseInputType_wrongInput_InvalidCommand() throws FitNusException {
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
}
