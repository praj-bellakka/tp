package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
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
        User us = new User(Gender.MALE, 1000);

        SetGenderCommand c = new SetGenderCommand("m");
        assertEquals("You have set your gender to Male", c.execute(ed, fd, us));
    }

    @Test
    void executeTest_validInputFemale() throws FitNusException {
        EntryDatabase ed = new EntryDatabase();
        FoodDatabase fd = new FoodDatabase();
        User us = new User(Gender.MALE, 1000);

        SetGenderCommand c = new SetGenderCommand("f");
        assertEquals("You have set your gender to Female", c.execute(ed, fd, us));
    }

    @Test
    void executeTest_invalidInput() {
        EntryDatabase ed = new EntryDatabase();
        FoodDatabase fd = new FoodDatabase();
        User us = new User(Gender.MALE, 1000);

        Exception exception = assertThrows(FitNusException.class, () -> new SetGenderCommand("invalidInput"));
        assertEquals("Invalid input! Please input m for male or "
                + "f for female when setting your gender.", exception.getMessage());
    }
}