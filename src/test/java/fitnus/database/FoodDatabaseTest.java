package fitnus.database;

import fitnus.database.FoodDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Food;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FoodDatabaseTest {

    @Test
    void addFood_validCalorieInt_success() throws FitNusException {
        FoodDatabase fd = new FoodDatabase();

        fd.addFood(new Food("food1", 100));
        assertEquals(100, fd.getFoodAtIndex(1).getCalories());
        assertEquals("food1", fd.getFoodAtIndex(1).getName());

        fd.addFood("food2", 100);
        assertEquals(100, fd.getFoodAtIndex(2).getCalories());
        assertEquals("food2", fd.getFoodAtIndex(2).getName());
    }

    @Test
    void addFood_lessThanZeroCalorieInt_exceptionThrown() {
        FoodDatabase fd = new FoodDatabase();

        Exception exception1 = assertThrows(FitNusException.class, () -> fd.addFood(new Food("food1", -100)));
        assertEquals("Food must have more than 0 calories!", exception1.getMessage());

        Exception exception2 = assertThrows(FitNusException.class, () -> fd.addFood("food2", -100));
        assertEquals("Food must have more than 0 calories!", exception2.getMessage());
    }

    @Test
    void getFoodAtIndex_validIndex_success() throws FitNusException {
        FoodDatabase fd = new FoodDatabase();
        fd.addFood("food1", 100);
        assertEquals("food1", fd.getFoodAtIndex(1).getName());
        assertEquals(100, fd.getFoodAtIndex(1).getCalories());
    }

    @Test
    void getFoodAtIndex_outOfBoundsIndex_exceptionThrown() throws FitNusException {
        FoodDatabase fd = new FoodDatabase();
        fd.addFood("food1", 100);
        assertThrows(IndexOutOfBoundsException.class, () -> fd.getFoodAtIndex(2));
    }


    @Test
    void listFoods_databaseWithFoods_listsFoodsSuccessfully() throws FitNusException {
        FoodDatabase fd = new FoodDatabase();
        fd.addFood("food1", 100);
        fd.addFood("food2", 200);
        assertEquals(" 1.food1 (100 Kcal)" + System.lineSeparator()
                + " 2.food2 (200 Kcal)" + System.lineSeparator(), fd.listFoods());
    }

    @Test
    void convertDatabaseToString_databaseWithFoods_foodsAsString() throws FitNusException {
        FoodDatabase fd = new FoodDatabase();
        fd.addFood("food1", 100);
        fd.addFood("food2", 200);
        assertEquals("food1 | 100" + System.lineSeparator()
                + "food2 | 200" + System.lineSeparator(), fd.convertDatabaseToString());
    }

    @Test
    void preLoadDatabase_validInput_SuccessfullyPreloadDatabase()
            throws FitNusException, IOException {
        FoodDatabase fd = new FoodDatabase();
        String initialString = "food1 | 100" + System.lineSeparator() + "food2 | 200";
        InputStream stream = new ByteArrayInputStream(initialString.getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        fd.preLoadDatabase(reader);
        assertEquals(" 1.food1 (100 Kcal)" + System.lineSeparator()
                + " 2.food2 (200 Kcal)" + System.lineSeparator(), fd.listFoods());
    }
}