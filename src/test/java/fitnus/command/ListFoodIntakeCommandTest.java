package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.tracker.Food;
import fitnus.database.FoodDatabase;
import fitnus.tracker.Gender;
import fitnus.tracker.MealType;
import fitnus.utility.User;
import org.junit.jupiter.api.Test;

class ListFoodIntakeCommandTest {

    @Test
    void executeTest_validInputDay() {
        EntryDatabase ed = new EntryDatabase();
        FoodDatabase fd = new FoodDatabase();
        User us = new User(Gender.MALE, 1000);

        ListFoodIntakeCommand c = new ListFoodIntakeCommand("/day");
        ed.addEntry(MealType.DINNER, new Food("food1", 100, Food.FoodType.MEAL));
        ed.addEntry(MealType.DINNER, new Food("food2", 200, Food.FoodType.SNACK));

        //todo
    }

}