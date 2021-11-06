package fitnus.database;

import fitnus.exception.FitNusException;
import fitnus.tracker.Food;
import fitnus.tracker.MealType;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.nio.file.Path;
import java.nio.file.Paths;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FoodDatabaseTest {
    @Test
    void addFood_validCalorieInt_success() throws FitNusException {
        FoodDatabase fd = new FoodDatabase();

        fd.addFood(new Food("food1", 100, Food.FoodType.OTHERS));
        assertEquals(100, fd.getFoodAtIndex(1).getCalories());
        assertEquals("food1", fd.getFoodAtIndex(1).getName());

        fd.addFood("food2", 100, Food.FoodType.MEAL);
        assertEquals(100, fd.getFoodAtIndex(2).getCalories());
        assertEquals("food2", fd.getFoodAtIndex(2).getName());
    }

    @Test
    void addFood_lessThanZeroCalorieInt_exceptionThrown() {
        FoodDatabase fd = new FoodDatabase();

        Exception exception1 = assertThrows(FitNusException.class, () -> fd.addFood(
                new Food("food1", -100, Food.FoodType.SNACK)));
        assertEquals("Food must have more than 0 calories!", exception1.getMessage());

        Exception exception2 = assertThrows(FitNusException.class, () -> fd.addFood(
                "food2", -100, Food.FoodType.OTHERS));
        assertEquals("Food must have more than 0 calories!", exception2.getMessage());
    }

    @Test
    void getFoodAtIndex_validIndex_success() throws FitNusException {
        FoodDatabase fd = new FoodDatabase();
        fd.addFood("food1", 100, Food.FoodType.MEAL);
        assertEquals("food1", fd.getFoodAtIndex(1).getName());
        assertEquals(100, fd.getFoodAtIndex(1).getCalories());
    }

    @Test
    void getFoodAtIndex_outOfBoundsIndex_exceptionThrown() throws FitNusException {
        FoodDatabase fd = new FoodDatabase();
        fd.addFood("food1", 100, Food.FoodType.MEAL);
        assertThrows(FitNusException.class, () -> fd.getFoodAtIndex(2));
    }


    @Test
    void listFoods_databaseWithFoods_listsFoodsSuccessfully() throws FitNusException {
        FoodDatabase fd = new FoodDatabase();
        fd.addFood("food1", 100, Food.FoodType.BEVERAGE);
        fd.addFood("food2", 200, Food.FoodType.MEAL);
        assertEquals(" 1.food1 (100 Kcal) Type: BEVERAGE" + System.lineSeparator()
                + " 2.food2 (200 Kcal) Type: MEAL" + System.lineSeparator(), fd.listFoods());
    }

    @Test
    void listFoods_databaseWithoutFoods_noRecordsFound() throws FitNusException {
        FoodDatabase fd = new FoodDatabase();
        assertEquals("Oops, there are no records found!", fd.listFoods());
    }

    @Test
    void convertDatabaseToString_databaseWithFoods_foodsAsString() throws FitNusException {
        FoodDatabase fd = new FoodDatabase();
        fd.addFood("food1", 100, Food.FoodType.MEAL);
        fd.addFood("food2", 200, Food.FoodType.SNACK);
        assertEquals("food1 | 100 | MEAL" + System.lineSeparator()
                + "food2 | 200 | SNACK" + System.lineSeparator(), fd.convertDatabaseToString());
    }

    @Test
    void preLoadDatabase_validInput_SuccessfullyPreloadDatabase()
            throws FitNusException, IOException {
        FoodDatabase database = new FoodDatabase();
        String initialString = "food1 | 100 | MEAL" + System.lineSeparator() + "food2 | 200 | MEAL";
        InputStream stream = new ByteArrayInputStream(initialString.getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        database.preloadDatabase(reader);
        assertEquals(" 1.food1 (100 Kcal) Type: MEAL" + System.lineSeparator()
                + " 2.food2 (200 Kcal) Type: MEAL" + System.lineSeparator(), database.listFoods());
    }

    @Test
    void preLoadDatabase_invalidInput_throwsFitNusException()
            throws IOException {
        FoodDatabase database = new FoodDatabase();
        String initialString = "food1 | 100 | MAL" + System.lineSeparator() + "food2 | | MEAL";
        InputStream stream = new ByteArrayInputStream(initialString.getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        assertThrows(FitNusException.class, () -> database.preloadDatabase(reader));
        reader.close();
    }

    @Test
    void findFoods_validKeyword_getMatchingFoods() throws FitNusException {
        String keyword = "Rice";
        FoodDatabase database = new FoodDatabase();
        Food prata = new Food("Prata", 100, Food.FoodType.MEAL);
        Food chickenRice = new Food("Chicken Rice", 325, Food.FoodType.SNACK);
        database.addFood(prata);
        database.addFood(chickenRice);
        assertEquals("[Chicken Rice (325 Kcal) Type: SNACK]",
                database.findFoods(keyword).toString());
    }

    @Test
    void findFoods_emptyStringKeyword_throwsFitNusException() throws FitNusException {
        String keyword = "";
        FoodDatabase database = new FoodDatabase();
        Food prata = new Food("Prata", 100, Food.FoodType.MEAL);
        Food chickenRice = new Food("Chicken Rice", 325, Food.FoodType.SNACK);
        database.addFood(prata);
        database.addFood(chickenRice);
        assertThrows(FitNusException.class, () -> database.findFoods(keyword));
    }

    @Test
    void findSuggestions_validInput_correctSuggestions() throws FitNusException {
        FoodDatabase database = new FoodDatabase();
        Food prata = new Food("Prata", 100, Food.FoodType.MEAL);
        Food chickenRice = new Food("Chicken Rice", 325, Food.FoodType.SNACK);
        database.addFood(prata);
        database.addFood(chickenRice);
        assertEquals("[Prata (100 Kcal) Type: MEAL]",
                database.findSuggestions(Food.FoodType.MEAL, 1000, true).toString());
    }

    @Test
    void findSuggestions_invalidInput_noMatchingSuggestions() throws FitNusException {
        FoodDatabase database = new FoodDatabase();
        Food prata = new Food("Prata", 100, Food.FoodType.MEAL);
        Food chickenRice = new Food("Chicken Rice", 325, Food.FoodType.SNACK);
        database.addFood(prata);
        database.addFood(chickenRice);
        assertEquals("[]", database.findSuggestions(null, -1000, true).toString());
    }
}