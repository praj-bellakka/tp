package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.utility.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SetGenderCommandTest {

    @Test
    void executeTest_validInputMale() {
        EntryDatabase ed = new EntryDatabase();
        FoodDatabase fd = new FoodDatabase();
        User us = new User(0, 1000);

        SetGenderCommand c = new SetGenderCommand("m");
        assertEquals("You have set your gender to Male", c.execute(ed, fd, us));
    }

    @Test
    void executeTest_validInputFemale() {
        EntryDatabase ed = new EntryDatabase();
        FoodDatabase fd = new FoodDatabase();
        User us = new User(0, 1000);

        SetGenderCommand c = new SetGenderCommand("f");
        assertEquals("You have set your gender to Female", c.execute(ed, fd, us));
    }

    @Test
    void executeTest_invalidInput() {
        EntryDatabase ed = new EntryDatabase();
        FoodDatabase fd = new FoodDatabase();
        User us = new User(0, 1000);

        SetGenderCommand c = new SetGenderCommand("invalidInput");
        assertEquals("Invalid input! Please input M for male or F for female when setting your gender.",
                c.execute(ed, fd, us));
    }
}