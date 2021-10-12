package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.tracker.Food;
import fitnus.database.FoodDatabase;
import fitnus.utility.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ViewRemainingCalorieCommandTest {

    @Test
    void executeTest() {
        EntryDatabase ed = new EntryDatabase();


        ed.addEntry(new Food("food1", 100));
        ed.addEntry(new Food("food2", 200));
        ed.addEntry(new Food("food3", 300));

        FoodDatabase fd = new FoodDatabase();
        User us = new User(0, 1000);
        ViewRemainingCalorieCommand c = new ViewRemainingCalorieCommand();
        assertEquals("The remaining calories before reaching the daily goal is 400",
                c.execute(ed, fd, us));
    }
}