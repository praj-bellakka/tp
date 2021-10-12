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

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    Parser parser = new Parser();

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
//        assertTrue(parser.parseCommandType("add food1 | 21") instanceof InvalidCommand);
//        assertTrue(parser.parseCommandType("add food1 | 21") instanceof InvalidCommand);
//        //assertTrue(parser.parseCommandType("list /intake /DAY") instanceof InvalidCommand)
//        //assertTrue(parser.parseCommandType("list /food") instanceof InvalidCommand);
//        assertTrue(parser.parseCommandType("genderr /set M/F") instanceof InvalidCommand);
//        assertTrue(parser.parseCommandType("remove/food 2") instanceof InvalidCommand);
//        assertTrue(parser.parseCommandType("calorie/set GOAL") instanceof InvalidCommand);
//        assertTrue(parser.parseCommandType("calories /remain") instanceof InvalidCommand);

        Exception exception1 = assertThrows(FitNusException.class, () -> parser.parseCommandType("add food1 | 21"));
        Exception exception2 = assertThrows(FitNusException.class, () -> parser.parseCommandType("genderr /set M/F"));
        Exception exception3 = assertThrows(FitNusException.class, () -> parser.parseCommandType("remove/food 2"));
        Exception exception4 = assertThrows(FitNusException.class, () -> parser.parseCommandType("calorie/set GOAL"));
        Exception exception5 = assertThrows(FitNusException.class, () -> parser.parseCommandType("calories /remain"));

        assertEquals("That was an invalid command! PLease try again!", exception1.getMessage());
        assertEquals("That was an invalid command! PLease try again!", exception2.getMessage());
        assertEquals("That was an invalid command! PLease try again!", exception3.getMessage());
        assertEquals("That was an invalid command! PLease try again!", exception4.getMessage());
        assertEquals("That was an invalid command! PLease try again!", exception5.getMessage());
    }
}
