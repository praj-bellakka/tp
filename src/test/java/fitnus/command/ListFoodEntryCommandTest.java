package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.tracker.Food;
import fitnus.database.FoodDatabase;
import fitnus.tracker.Gender;
import fitnus.tracker.MealType;
import fitnus.utility.User;
import org.junit.jupiter.api.Test;

class ListFoodEntryCommandTest {

    @Test
    void executeTest_validInputDay() {
        EntryDatabase ed = new EntryDatabase();
        FoodDatabase fd = new FoodDatabase();
        User us = new User(2000, Gender.MALE, 18, 180, 65);

        ListFoodEntryAllCommand c = new ListFoodEntryAllCommand();
        ed.addEntry(MealType.DINNER, new Food("food1", 100, Food.FoodType.MEAL));
        ed.addEntry(MealType.DINNER, new Food("food2", 200, Food.FoodType.SNACK));

        //todo
    }

}