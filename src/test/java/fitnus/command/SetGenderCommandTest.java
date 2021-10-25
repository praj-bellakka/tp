package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Gender;
import fitnus.utility.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SetGenderCommandTest {

    @Test
    void executeTest_validInputMale() throws FitNusException {
        EntryDatabase ed = new EntryDatabase();
        FoodDatabase fd = new FoodDatabase();
        MealPlanDatabase md = new MealPlanDatabase();
        User us = new User(2000, Gender.MALE, 18, 180, 65);

        SetGenderCommand c = new SetGenderCommand("m");
        assertEquals("You have set your gender to Male", c.execute(ed, fd, md, us));
    }

    @Test
    void executeTest_validInputFemale() throws FitNusException {
        EntryDatabase ed = new EntryDatabase();
        FoodDatabase fd = new FoodDatabase();
        MealPlanDatabase md = new MealPlanDatabase();
        User us = new User(2000, Gender.MALE, 18, 180, 65);

        SetGenderCommand c = new SetGenderCommand("f");
        assertEquals("You have set your gender to Female", c.execute(ed, fd, md, us));
    }

    @Test
    void executeTest_invalidInput() {
        EntryDatabase ed = new EntryDatabase();
        FoodDatabase fd = new FoodDatabase();
        MealPlanDatabase md = new MealPlanDatabase();
        User us = new User(2000, Gender.MALE, 18, 180, 65);
        SetGenderCommand c = new SetGenderCommand("invalidInput");
        Exception exception = assertThrows(FitNusException.class, () -> c.execute(ed, fd, md, us));
        assertEquals("Invalid input! Please input m for male or "
                + "f for female when setting your gender.", exception.getMessage());
    }
}