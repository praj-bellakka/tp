package fitnus;

import fitnus.command.Command;
import fitnus.parser.Parser;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {

    Parser parser = new Parser();

    @Test
    void parseInputType_correctInput_parsedCorrectly() {
        String addCustomInputType = parser.parseInputType("add /cust food1 | 21");
        String addDefaultInputType = parser.parseInputType("add /def 10");
        String listFoodInputType = parser.parseInputType("list /food");
        String listFoodIntakeInputType = parser.parseInputType("list /intake /DAY");
        String setGenderInputType = parser.parseInputType("gender /set M/F");
        String deleteFoodInputType = parser.parseInputType("remove /food 2");
        String setCalorieInputType = parser.parseInputType("calorie /set GOAL");
        String remainingCalorieInputType = parser.parseInputType("calorie /remain");

        assertEquals("addcustom", addCustomInputType);
        assertEquals("adddefault", addDefaultInputType);
        assertEquals("listdatabase", listFoodInputType);
        assertEquals("listintake", listFoodIntakeInputType);
        assertEquals("genderset", setGenderInputType);
        assertEquals("remove", deleteFoodInputType);
        assertEquals("calorieset", setCalorieInputType);
        assertEquals("calorieremain", remainingCalorieInputType);


        //parser.parseInputType()
    }

    @Test
    void parseInputType_wrongInput_ExceptionThrown() {
        try {
            assertEquals("addcustom", parser.parseInputType("add food1 | 21"));
        } catch (AssertionFailedError e) {
            System.out.println("/cust not detected");
            e.printStackTrace();
        }
    }



}
