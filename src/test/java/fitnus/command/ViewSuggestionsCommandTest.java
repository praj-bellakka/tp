package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Food;
import fitnus.tracker.Gender;
import fitnus.utility.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ViewSuggestionsCommandTest {

    @Test
    void testViewSuggestionsCommand_validUser_viewSuggestionsSuccess() throws FitNusException {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        FoodDatabase fd = new FoodDatabase();
        EntryDatabase ed = new EntryDatabase();
        MealPlanDatabase md = new MealPlanDatabase();

        fd.addFood(new Food("food1", 100, Food.FoodType.OTHERS));
        assertEquals(100, fd.getFoodAtIndex(1).getCalories());
        assertEquals("food1", fd.getFoodAtIndex(1).getName());

        fd.addFood("food2", 100, Food.FoodType.MEAL);

        fd.addFood("food3", 200, Food.FoodType.MEAL);

        ViewSuggestionsCommand command = new ViewSuggestionsCommand(Food.FoodType.MEAL, true);
        assertEquals("Found " + 2 + " suggestions", command.execute(ed, fd, md, user));
    }
}