package fitnus;

import fitnus.command.Command;
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
        assertEquals("addcustom", parser.parseInputType("add /cust food1 | 21"));
        assertEquals("adddefault", parser.parseInputType("add /def 10"));
        assertEquals("listdatabase", parser.parseInputType("list /food"));
        assertEquals("listintake", parser.parseInputType("list /intake /DAY"));
        assertEquals("genderset", parser.parseInputType("gender /set M/F"));
        assertEquals("remove", parser.parseInputType("remove /food 2"));
        assertEquals("calorieset", parser.parseInputType("calorie /set GOAL"));
        assertEquals("calorieremain", parser.parseInputType("calorie /remain"));

    }

    @Test
    void parseInputType_wrongInput_ExceptionThrown() {
        try {
            Assertions.assertAll(
                () -> assertEquals("addcustom", parser.parseInputType("add food1 | 21")),
                () -> assertEquals("adddefault", parser.parseInputType("add food1 | 21")),
                () -> assertEquals("listdatabase", parser.parseInputType("list /intake /DAY")),
                () -> assertEquals("listintake", parser.parseInputType("list /food")),
                () -> assertEquals("genderset", parser.parseInputType("genderr /set M/F")),
                () -> assertEquals("remove", parser.parseInputType("remove/food 2")),
                () -> assertEquals("calorieset", parser.parseInputType("calorie/set GOAL")),
                () -> assertEquals("calorieremain", parser.parseInputType("calories /remain"))
            );
        } catch (MultipleFailuresError e) {
            System.out.println("Input does not match format");
            e.printStackTrace();
        }
    }



}
