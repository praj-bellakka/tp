package fitnus.tracker;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MealPlanTest {
    Food food1 = new Food("food 1", 250, Food.FoodType.BEVERAGE);
    Food food2 = new Food("food 2", 350, Food.FoodType.SNACK);
    Food food3 = new Food("food 3", 450, Food.FoodType.MEAL);

    @Test
    void getFoodString_multipleFoods_getStringSuccess() {
        ArrayList<Food> testFoods = new ArrayList<>();
        testFoods.add(food1);
        testFoods.add(food2);
        testFoods.add(food3);
        MealPlan testMp = new MealPlan("testPlan", testFoods);
        String expectedOutput = "food 1 (250 Kcal) Type: BEVERAGE" + System.lineSeparator()
                + "food 2 (350 Kcal) Type: SNACK" + System.lineSeparator()
                + "food 3 (450 Kcal) Type: MEAL" + System.lineSeparator();

        assertEquals(expectedOutput, testMp.getFoodString());
    }

    @Test
    void getFoodString_singleFood_getStringSuccess() {
        ArrayList<Food> testFoods = new ArrayList<>();
        MealPlan testMp = new MealPlan("testPlan", testFoods);
        String expectedOutput = "";

        assertEquals(expectedOutput, testMp.getFoodString());
    }

}
