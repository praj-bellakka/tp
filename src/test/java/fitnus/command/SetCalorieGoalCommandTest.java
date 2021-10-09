package fitnus.command;

import fitnus.EntryDatabase;
import fitnus.FitNusException;
import fitnus.FoodDatabase;
import fitnus.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SetCalorieGoalCommandTest {

    @Test
    void executeTest_validInput() throws FitNusException {
        EntryDatabase ed = new EntryDatabase();
        FoodDatabase fd = new FoodDatabase();
        User us = new User(0, 1000);

        SetCalorieGoalCommand c = new SetCalorieGoalCommand(2000);
        assertEquals("The calorie goal has been set to 2000",  c.execute(ed, fd, us));
    }

    @Test
    void executeTest_invalidInputNegativeGoal() {
        EntryDatabase ed = new EntryDatabase();
        FoodDatabase fd = new FoodDatabase();
        User us = new User(0, 1000);

        SetCalorieGoalCommand c = new SetCalorieGoalCommand(-100);

        Exception exception = assertThrows(FitNusException.class, ()-> c.execute(ed, fd, us));
        assertEquals("Calorie Goal cannot be negative! Please try again!", exception.getMessage());
    }

    @Test
    void executeTest_invalidInputSameGoal() {
        EntryDatabase ed = new EntryDatabase();
        FoodDatabase fd = new FoodDatabase();
        User us = new User(0, 1000);

        SetCalorieGoalCommand c = new SetCalorieGoalCommand(1000);

        Exception exception = assertThrows(FitNusException.class, ()-> c.execute(ed, fd, us));
        assertEquals("Calorie Goal cannot be the same as before! Please try again!", exception.getMessage());
    }
}