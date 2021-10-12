package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.tracker.Food;
import fitnus.database.FoodDatabase;
import fitnus.utility.User;
import org.junit.jupiter.api.Test;

class ListFoodIntakeCommandTest {

    @Test
    void executeTest_validInputDay() {
        EntryDatabase ed = new EntryDatabase();
        FoodDatabase fd = new FoodDatabase();
        User us = new User(0, 1000);

        ListFoodIntakeCommand c = new ListFoodIntakeCommand("/day");
        ed.addEntry(new Food("food1", 100));
        ed.addEntry(new Food("food2", 200));

        //todo
    }

}