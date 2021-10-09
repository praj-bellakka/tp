package fitnus;

import fitnus.command.*;
import fitnus.parser.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.opentest4j.MultipleFailuresError;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {

    Parser parser = new Parser();

    @Test
    void parseInputType_correctInput_parsedCorrectly() {
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
    void parseInputType_wrongInput_InvalidCommand() {
        assertTrue(parser.parseCommandType("add food1 | 21") instanceof InvalidCommand);
        assertTrue(parser.parseCommandType("add food1 | 21") instanceof InvalidCommand);
        //assertTrue(parser.parseCommandType("list /intake /DAY") instanceof InvalidCommand)
        //assertTrue(parser.parseCommandType("list /food") instanceof InvalidCommand);
        assertTrue(parser.parseCommandType("genderr /set M/F") instanceof InvalidCommand);
        assertTrue(parser.parseCommandType("remove/food 2") instanceof InvalidCommand);
        assertTrue(parser.parseCommandType("calorie/set GOAL") instanceof InvalidCommand);
        assertTrue(parser.parseCommandType("calories /remain") instanceof InvalidCommand);

    }



}
