package fitnus.database;

import fitnus.exception.FitNusException;
import fitnus.tracker.Food;
import fitnus.tracker.MealPlan;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MealPlanDatabaseTest {
    @Test
    void getMealAtIndex_validIndex_success() throws FitNusException {
        //initialising
        MealPlanDatabase md = new MealPlanDatabase();
        ArrayList<Food> foodArray = new ArrayList<>();
        foodArray.add(new Food("food1", 100, Food.FoodType.BEVERAGE));
        md.addMealPlan(new MealPlan("test", foodArray));
        MealPlan plan = new MealPlan("test", foodArray);

        assertEquals(plan.getMealFoods(), md.getMealAtIndex(1).getMealFoods());
        assertEquals(plan.getMealPlanName(), md.getMealAtIndex(1).getMealPlanName());

    }

    @Test
    void getMealAtIndex_invalidIndex_failure() throws FitNusException {
        //initialising
        MealPlanDatabase md = new MealPlanDatabase();
        ArrayList<Food> foodArray = new ArrayList<>();
        foodArray.add(new Food("food1", 100, Food.FoodType.BEVERAGE));
        md.addMealPlan(new MealPlan("test", foodArray));
        MealPlan plan = new MealPlan("test", foodArray);

        Exception exception1 = assertThrows(FitNusException.class, () -> md.getMealAtIndex(2).getMealFoods());
        assertEquals("Index specified is outside the range of the database! "
                + "Meal plan could not be found...", exception1.getMessage());

        Exception exception2 = assertThrows(FitNusException.class, () -> md.getMealAtIndex(0).getMealPlanName());
        assertEquals("Index specified is outside the range of the database! "
                + "Meal plan could not be found...", exception2.getMessage());


    }

}
