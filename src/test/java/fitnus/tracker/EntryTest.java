package fitnus.tracker;

import fitnus.exception.FitNusException;
import fitnus.parser.Parser;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNull;

class EntryTest {

    @Test
    void getFood_validEntry_getFoodSuccess() {
        LocalDate date = null;
        Food chickenRice = new Food("chicken rice", 200, Food.FoodType.MEAL);
        try {
            date = Parser.getDate("2021-10-11");
        } catch (FitNusException e) {
            fail("Parser getDate error");
        }
        Entry entry1 = new Entry(MealType.DINNER, chickenRice, date);
        assertEquals(chickenRice, entry1.getFood());
    }

    @Test
    void getFood_foodIsNull_returnNull() {
        LocalDate date = null;
        try {
            date = Parser.getDate("2021-10-11");
        } catch (FitNusException e) {
            fail("Parser getDate error");
        }
        Entry entry1 = new Entry(MealType.DINNER, null, date);
        assertNull(entry1.getFood());
    }

    @Test
    void setFood_validFood_setFoodSuccess() {
        LocalDate date = null;
        Food ramen1 = new Food("ramen", 400, Food.FoodType.MEAL);
        Food ramen2 = new Food("ramen", 800, Food.FoodType.MEAL);
        try {
            date = Parser.getDate("2021-08-31");
        } catch (FitNusException e) {
            fail("Parser getDate error");
        }
        Entry entry1 = new Entry(MealType.DINNER, ramen1, date);
        entry1.setFood(ramen2);
        assertEquals(ramen2, entry1.getFood());
    }

    @Test
    void setFood_setNullFood_setFoodAsNull() {
        LocalDate date = null;
        Food ramen1 = new Food("ramen", 400, Food.FoodType.MEAL);
        try {
            date = Parser.getDate("2021-08-31");
        } catch (FitNusException e) {
            fail("Parser getDate error");
        }
        Entry entry1 = new Entry(MealType.DINNER, ramen1, date);
        entry1.setFood(null);
        assertNull(entry1.getFood());
    }

    @Test
    void getDate_validEntry_getDateSuccess() {
        LocalDate date = null;
        Food cake = new Food("cake", 500, Food.FoodType.SNACK);
        try {
            date = Parser.getDate("2021-10-20");
        } catch (FitNusException e) {
            fail("Parser getDate error");
        }
        Entry entry1 = new Entry(MealType.DINNER, cake, date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        assertEquals(date.format(formatter), entry1.getDate());
    }

    @Test
    void getRawDate_validEntry_getRawDateSuccess() {
        LocalDate date = null;
        Food chickenRice = new Food("chicken rice", 200, Food.FoodType.MEAL);
        try {
            date = Parser.getDate("2021-08-11");
        } catch (FitNusException e) {
            fail("Parser getDate error");
        }
        Entry entry1 = new Entry(MealType.DINNER, chickenRice, date);
        assertEquals(date, entry1.getRawDate());
    }

    @Test
    void getMealType_validEntry_getMealTypeSuccess() {
        LocalDate date = null;
        Food salmon = new Food("salmon", 500, Food.FoodType.MEAL);
        try {
            date = Parser.getDate("2021-09-11");
        } catch (FitNusException e) {
            fail("Parser getDate error");
        }
        Entry entry1 = new Entry(MealType.DINNER, salmon, date);
        assertEquals(MealType.DINNER, entry1.getMealType());
    }

    @Test
    void getMealType_mealTypeIsNull_returnsNull() {
        LocalDate date = null;
        Food salmon = new Food("salmon", 500, Food.FoodType.MEAL);
        try {
            date = Parser.getDate("2021-09-11");
        } catch (FitNusException e) {
            fail("Parser getDate error");
        }
        Entry entry1 = new Entry(null, salmon, date);
        assertNull(entry1.getMealType());
    }

    @Test
    void testToString_validEntry_toStringSuccess() {
        LocalDate date = null;
        Food curryRice = new Food("curry rice", 500, Food.FoodType.MEAL);
        try {
            date = Parser.getDate("2021-10-11");
        } catch (FitNusException e) {
            fail("Parser getDate error");
        }
        Entry entry1 = new Entry(MealType.LUNCH, curryRice, date);
        assertEquals("[2021-10-11] Lunch: curry rice (500 Kcal) Type: MEAL",
                entry1.toString());
    }
}