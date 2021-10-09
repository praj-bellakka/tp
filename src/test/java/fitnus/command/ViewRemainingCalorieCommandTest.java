package fitnus.command;

import fitnus.EntryDatabase;
import fitnus.Food;
import fitnus.FoodDatabase;
import fitnus.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ViewRemainingCalorieCommandTest {

    @Test
    void executeTest() {
        EntryDatabase ed = new EntryDatabase();
        FoodDatabase fd = new FoodDatabase();
        User us = new User(0, 1000);

        ViewRemainingCalorieCommand c = new ViewRemainingCalorieCommand();

        ed.addEntry(new Food("food1", 100));
        ed.addEntry(new Food("food2", 200));
        ed.addEntry(new Food("food3", 300));

        assertEquals("The remaining calories before reaching the daily goal is 400",
                c.execute(ed, fd, us));
    }
}