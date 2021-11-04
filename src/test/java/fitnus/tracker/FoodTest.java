package fitnus.tracker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FoodTest {


    @Test
    void getName_validFood_getNameSuccess() {
        Food ramen = new Food("ramen", 700, Food.FoodType.MEAL);
        assertEquals("ramen", ramen.getName());
    }

    @Test
    void getName_nameIsNull_returnNull() {
        Food ramen = new Food(null, 700, Food.FoodType.MEAL);
        assertNull(ramen.getName());
    }

    @Test
    void getCalories_validFood_getCaloriesSuccess() {
        Food fries = new Food("fries", 600, Food.FoodType.SNACK);
        assertEquals(600, fries.getCalories());
    }

    @Test
    void getCalories_calorieIsNull_returnNull() {
        Food fries = new Food("fries", null, Food.FoodType.SNACK);
        assertNull(fries.getCalories());
    }

    @Test
    void testToString_validFood_toStringSuccess() {
        Food ramen = new Food("ramen", 400, Food.FoodType.MEAL);
        assertEquals("ramen (400 Kcal) Type: MEAL", ramen.toString());
    }

    @Test
    void getType_validFood_getTypeSuccess() {
        Food fries = new Food("fries", 300, Food.FoodType.SNACK);
        assertEquals(Food.FoodType.SNACK, fries.getType());
    }

    @Test
    void getType_typeIsNull_returnNull() {
        Food fries = new Food("fries", 300, null);
        assertNull(fries.getType());
    }

    @Test
    void convertToStringForStorage_validFood_convertSuccess() {
        Food chickenRice = new Food("chicken rice", 400, Food.FoodType.MEAL);
        assertEquals("chicken rice | 400 | MEAL" + System.lineSeparator(),
                chickenRice.convertToStringForStorage());
    }
}