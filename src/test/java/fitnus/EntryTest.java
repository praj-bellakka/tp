package fitnus;

import fitnus.tracker.Entry;
import fitnus.tracker.Food;
import fitnus.tracker.MealType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntryTest {

    @Test
    void convertToStringForStorage_() {
        //todo
    }

    @Test
    void testToString() {
        MealType mealType = MealType.BREAKFAST;
        Food food = new Food("Bread", 50, Food.FoodType.SNACK);
        Entry entry = new Entry(mealType, food, LocalDate.parse("2021-01-01"));
        assertEquals("[2021-01-01] Breakfast: Bread (50 Kcal) Category: SNACK", entry.toString());
    }
}