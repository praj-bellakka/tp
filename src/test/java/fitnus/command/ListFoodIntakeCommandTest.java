package fitnus.command;

import fitnus.EntryDatabase;
import fitnus.Food;
import fitnus.FoodDatabase;
import fitnus.User;
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