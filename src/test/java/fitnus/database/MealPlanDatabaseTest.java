package fitnus.database;

import fitnus.exception.FitNusException;
import fitnus.tracker.Food;
import fitnus.tracker.MealPlan;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MealPlanDatabaseTest {

    //initialising
    MealPlanDatabase md = new MealPlanDatabase();
    ArrayList<Food> foodArray = new ArrayList<>();
    MealPlan plan = new MealPlan("test", foodArray);
    MealPlan emptyPlan = new MealPlan("test", new ArrayList<>());
    String initialString = "chicken rice | 213 | SNACK" + System.lineSeparator()
            + "duck rice | 20 | SNACK" + System.lineSeparator()
            + "rice | 1234 | MEAL" + System.lineSeparator() + "-------- | testing";


    MealPlanDatabaseTest() throws FitNusException {
        foodArray.add(new Food("food1", 100, Food.FoodType.BEVERAGE));
        md.addMealPlan(new MealPlan("test", foodArray));
    }

    @Test
    void getMealAtIndex_validIndex_success() throws FitNusException {
        assertEquals(plan.getMealFoods(), md.getMealAtIndex(1).getMealFoods());
        assertEquals(plan.getMealPlanName(), md.getMealAtIndex(1).getMealPlanName());

    }

    @Test
    void getMealAtIndex_invalidIndex_failure() throws FitNusException {
        Exception exception1 = assertThrows(FitNusException.class, () -> md.getMealAtIndex(2).getMealFoods());
        assertEquals("Index specified is outside the range of the database! "
                + "Meal plan could not be found...", exception1.getMessage());

        Exception exception2 = assertThrows(FitNusException.class, () -> md.getMealAtIndex(0).getMealPlanName());
        assertEquals("Index specified is outside the range of the database! "
                + "Meal plan could not be found...", exception2.getMessage());
    }

    @Test
    void addMealPlan_validMealPlan_planAddedSuccessfully() throws FitNusException {
        assertEquals(plan.toString(), md.getMealAtIndex(1).getMealPlanName());
        assertEquals(plan.getMealFoods().get(0), md.getMealAtIndex(1).getMealFoods().get(0));
    }

    @Test
    void addMealPlan_emptyMealPlan_planAddedUnsuccessfully() throws FitNusException {
        Exception exception1 = assertThrows(FitNusException.class, () -> md.addMealPlan(emptyPlan));
        assertEquals("Unable to add meal plan as no food detected.", exception1.getMessage());
    }

    @Test
    void preLoadDatabase_validInput_SuccessfullyPreloadDatabase() throws IOException {
        InputStream stream = new ByteArrayInputStream(initialString.getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        MealPlanDatabase newMd = new MealPlanDatabase();
        newMd.preloadDatabase(reader);
        assertEquals("1. Meal plan: testing" + System.lineSeparator()
                + "chicken rice (213 Kcal) Type: SNACK" + System.lineSeparator()
                + "duck rice (20 Kcal) Type: SNACK" + System.lineSeparator()
                + "rice (1234 Kcal) Type: MEAL" + System.lineSeparator()
                + System.lineSeparator(), newMd.listMealPlan());
    }

}
