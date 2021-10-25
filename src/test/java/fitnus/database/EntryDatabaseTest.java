package fitnus.database;

import fitnus.exception.FitNusException;
import fitnus.tracker.Entry;
import fitnus.tracker.Food;
import fitnus.parser.Parser;
import fitnus.tracker.MealType;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EntryDatabaseTest {
    @Test
    public void convertDatabaseToStringTest_emptyList_emptyString() {
        EntryDatabase edb = new EntryDatabase();
        assertEquals("", edb.convertDatabaseToString());
    }


    @Test
    public void convertDatabaseToStringTest_list_stringRepresentation() {
        EntryDatabase edb = new EntryDatabase();
        LocalDate date = null;
        try {
            date = Parser.getDate("2021-10-11");
        } catch (FitNusException e) {
            fail("Parser getDate error");
        }
        Entry entry1 = new Entry(MealType.DINNER,
                new Food("chicken rice", 200, Food.FoodType.MEAL), date);
        Entry entry2 = new Entry(MealType.LUNCH,
                new Food("steak", 900, Food.FoodType.MEAL), date);
        Entry entry3 = new Entry(MealType.BREAKFAST,
                new Food("laksa", 400, Food.FoodType.MEAL), date);
        Entry entry4 = new Entry(MealType.SNACK,
                new Food("hotpot", 1100, Food.FoodType.MEAL), date);
        edb.addEntry(entry1);
        edb.addEntry(entry2);
        edb.addEntry(entry3);
        edb.addEntry(entry4);
        String expectedOutput = String.format("Dinner | chicken rice | 200 | %s | MEAL" + System.lineSeparator()
                + "Lunch | steak | 900 | %s | MEAL" + System.lineSeparator()
                + "Breakfast | laksa | 400 | %s | MEAL" + System.lineSeparator()
                + "Snack | hotpot | 1100 | %s | MEAL" + System.lineSeparator(), date, date, date, date);
        assertEquals(expectedOutput, edb.convertDatabaseToString());
    }

    @Test
    void addEntry_validEntry_entryAddedSuccessfully() throws FitNusException {
        // Instantiate objects
        EntryDatabase edb = new EntryDatabase();
        Food prata = new Food("Prata", 100, Food.FoodType.MEAL);
        Food chickenRice = new Food("Chicken Rice", 325, Food.FoodType.MEAL);
        Entry chickenRiceEntry = new Entry(MealType.DINNER, chickenRice);

        // Add Entries
        edb.addEntry(MealType.DINNER, prata);
        edb.addEntry(chickenRiceEntry);

        // Test
        assertEquals(prata, edb.getEntryAtIndex(1).getFood());
        assertEquals(chickenRice, edb.getEntryAtIndex(2).getFood());
    }

    @Test
    void getTotalCalorie_nonZeroEntries_getTotalCaloriesSuccessfully() {
        // Instantiate objects
        EntryDatabase edb = new EntryDatabase();
        Food prata = new Food("Prata", 100, Food.FoodType.MEAL);
        Food chickenRice = new Food("Chicken Rice", 325, Food.FoodType.MEAL);

        // Add Entries
        edb.addEntry(MealType.DINNER, prata);
        edb.addEntry(MealType.DINNER, chickenRice);

        // Test
        assertEquals(425, edb.getTotalDailyCalorie());
    }

    @Test
    void getTotalCalorie_zeroEntries_getTotalCaloriesSuccessfully() {
        // Instantiate objects
        EntryDatabase edb = new EntryDatabase();

        // Test
        assertEquals(0, edb.getTotalDailyCalorie());
    }

    @Test
    void deleteEntry_validIndex_entryDeletedSuccessfully() throws FitNusException {
        // Instantiate objects
        EntryDatabase edb = new EntryDatabase();
        Food prata = new Food("Prata", 100, Food.FoodType.MEAL);
        Food chickenRice = new Food("Chicken Rice", 325, Food.FoodType.MEAL);

        // Add Entries
        edb.addEntry(MealType.DINNER, prata);
        edb.addEntry(MealType.DINNER, chickenRice);

        // Delete Entry
        edb.deleteEntry(2);

        // Test
        Exception exception = assertThrows(FitNusException.class, () -> edb.getEntryAtIndex(2));

        assertEquals("Sorry the index chosen is invalid! Please try again!", exception.getMessage());
    }

    @Test
    void deleteEntry_invalidIndex_exceptionThrown() {
        // Instantiate objects
        EntryDatabase edb = new EntryDatabase();
        Food prata = new Food("Prata", 100, Food.FoodType.MEAL);
        Food chickenRice = new Food("Chicken Rice", 325, Food.FoodType.BEVERAGE);

        // Add Entries
        edb.addEntry(MealType.DINNER, prata);
        edb.addEntry(MealType.DINNER, chickenRice);

        // Test
        Exception exception1 = assertThrows(FitNusException.class, () -> edb.deleteEntry(0));
        Exception exception2 = assertThrows(FitNusException.class, () -> edb.deleteEntry(-1));
        Exception exception3 = assertThrows(FitNusException.class, () -> edb.deleteEntry(100));

        assertEquals("Sorry the index chosen is invalid! Please try again!", exception1.getMessage());
        assertEquals("Sorry the index chosen is invalid! Please try again!", exception2.getMessage());
        assertEquals("Sorry the index chosen is invalid! Please try again!", exception3.getMessage());
    }

    @Test
    void getEntryAtIndex_validIndex_getEntrySuccessfully() throws FitNusException {
        // Instantiate objects
        EntryDatabase edb = new EntryDatabase();
        Food prata = new Food("Prata", 100, Food.FoodType.MEAL);
        Food chickenRice = new Food("Chicken Rice", 325, Food.FoodType.MEAL);

        // Add Entries
        edb.addEntry(MealType.DINNER, prata);
        edb.addEntry(MealType.DINNER, chickenRice);

        // Test
        assertEquals(prata, edb.getEntryAtIndex(1).getFood());
        assertEquals(chickenRice, edb.getEntryAtIndex(2).getFood());
    }

    @Test
    void getEntryAtIndex_invalidIndex_getEntrySuccessfully() {
        // Instantiate objects
        EntryDatabase edb = new EntryDatabase();
        Food prata = new Food("Prata", 100, Food.FoodType.MEAL);
        Food chickenRice = new Food("Chicken Rice", 325, Food.FoodType.SNACK);

        // Add Entries
        edb.addEntry(MealType.DINNER, prata);
        edb.addEntry(MealType.DINNER, chickenRice);

        // Test
        Exception exception1 = assertThrows(FitNusException.class, () -> edb.getEntryAtIndex(0));
        Exception exception2 = assertThrows(FitNusException.class, () -> edb.getEntryAtIndex(-1));
        Exception exception3 = assertThrows(FitNusException.class, () -> edb.getEntryAtIndex(100));

        assertEquals("Sorry the index chosen is invalid! Please try again!", exception1.getMessage());
        assertEquals("Sorry the index chosen is invalid! Please try again!", exception2.getMessage());
        assertEquals("Sorry the index chosen is invalid! Please try again!", exception3.getMessage());
    }

    @Test
    void preLoadDatabase_validInput_SuccessfullyPreloadDatabase() throws IOException {
        EntryDatabase ed = new EntryDatabase();
        String initialString = "Breakfast | food1 | 100 | 2021-10-12 | MEAL" + System.lineSeparator()
                + "Lunch | food2 | 200 | 2021-10-12 | MEAL";
        InputStream stream = new ByteArrayInputStream(initialString.getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        ed.preloadDatabase(reader);
        assertEquals(" 1.[2021-10-12] Breakfast: food1 (100 Kcal) Type: MEAL"
                + System.lineSeparator() + " 2.[2021-10-12] Lunch: food2 (200 Kcal) Type: MEAL"
                + System.lineSeparator(), ed.listEntries());
    }

    @Test
    void getPastDaysEntryDatabase_existingEntries_SuccessfullyReturnsPastDaysEntries() {
        // Instantiate objects
        EntryDatabase edb = new EntryDatabase();
        Food prata = new Food("Prata", 100, Food.FoodType.MEAL);
        Food chickenRice = new Food("Chicken Rice", 325, Food.FoodType.MEAL);
        Food nasiLemak = new Food("Nasi Lemak", 400, Food.FoodType.MEAL);

        // Add Entries
        edb.addEntry(new Entry(MealType.DINNER, nasiLemak, LocalDate.now().minusDays(2)));
        edb.addEntry(new Entry(MealType.DINNER, prata, LocalDate.now().minusDays(1)));
        edb.addEntry(new Entry(MealType.DINNER, chickenRice));

        // Test
        EntryDatabase edbOne = edb.getPastDaysEntryDatabase(1);
        EntryDatabase edbTwo = edb.getPastDaysEntryDatabase(2);
        EntryDatabase edbThree = edb.getPastDaysEntryDatabase(3);

        assertEquals(" 1.[" + LocalDate.now() + "] Dinner: Chicken Rice (325 Kcal) Type: MEAL"
                + System.lineSeparator(), edbOne.listEntries());
        assertEquals(" 1.[" + LocalDate.now().minusDays(1) + "] Dinner: Prata (100 Kcal) Type: MEAL"
                + System.lineSeparator() + " 2.[" + LocalDate.now() + "] Dinner: Chicken Rice (325 Kcal) Type: MEAL"
                + System.lineSeparator(), edbTwo.listEntries());
        assertEquals(" 1.[" + LocalDate.now().minusDays(2) + "] Dinner: Nasi Lemak (400 Kcal) Type: MEAL"
                + System.lineSeparator() + " 2.[" + LocalDate.now().minusDays(1)
                + "] Dinner: Prata (100 Kcal) Type: MEAL" + System.lineSeparator()
                + " 3.[" + LocalDate.now() + "] Dinner: Chicken Rice (325 Kcal) Type: MEAL" + System.lineSeparator(),
                edbThree.listEntries());
    }

    @Test
    void getPastDaysEntryDatabase_noEntries_SuccessfullyReturnsNoEntries() {
        // Instantiate objects
        EntryDatabase edb = new EntryDatabase();

        // Test
        EntryDatabase edbPastDay = edb.getPastDaysEntryDatabase(1);

        assertEquals("", edbPastDay.listEntries());
    }

    @Test
    void getPastMonthEntryDatabase_existingEntries_SuccessfullyReturnsPastMonthEntries() {
        // Instantiate objects
        EntryDatabase edb = new EntryDatabase();
        Food prata = new Food("Prata", 100, Food.FoodType.MEAL);
        Food chickenRice = new Food("Chicken Rice", 325, Food.FoodType.MEAL);
        Food nasiLemak = new Food("Nasi Lemak", 400, Food.FoodType.MEAL);

        // Add Entries
        edb.addEntry(new Entry(MealType.DINNER, nasiLemak, LocalDate.now().minusMonths(2)));
        edb.addEntry(new Entry(MealType.DINNER, prata, LocalDate.now().minusMonths(1)));
        edb.addEntry(new Entry(MealType.DINNER, chickenRice));

        // Test
        EntryDatabase edbMonth = edb.getPastMonthEntryDatabase();

        assertEquals(" 1.[" + LocalDate.now() + "] Dinner: Chicken Rice (325 Kcal) Type: MEAL"
                + System.lineSeparator(), edbMonth.listEntries());
    }

    @Test
    void getPastMonthEntryDatabase_noEntries_SuccessfullyReturnsNoEntries() {
        // Instantiate objects
        EntryDatabase edb = new EntryDatabase();

        // Test
        EntryDatabase edbPastMonth = edb.getPastMonthEntryDatabase();

        assertEquals("", edbPastMonth.listEntries());
    }

    @Test
    void editEntryAtIndex_validIndex_SuccessfullyEditEntry() throws FitNusException {
        // Instantiate objects
        EntryDatabase edb = new EntryDatabase();
        Food prata = new Food("Prata", 100, Food.FoodType.MEAL);
        Food chickenRice = new Food("Chicken Rice", 325, Food.FoodType.MEAL);
        Food nasiLemak = new Food("Nasi Lemak", 400, Food.FoodType.MEAL);

        // Add Entries
        edb.addEntry(MealType.DINNER, prata);
        edb.addEntry(MealType.DINNER, chickenRice);

        // Edit Entry
        edb.editEntryAtIndex(1, nasiLemak);

        // Test
        assertEquals(" 1.[" + LocalDate.now() + "] Dinner: Nasi Lemak (400 Kcal) Type: MEAL"
                + System.lineSeparator() + " 2.[" + LocalDate.now()
                + "] Dinner: Chicken Rice (325 Kcal) Type: MEAL"
                + System.lineSeparator(), edb.listEntries());
    }

    @Test
    void editEntryAtIndex_invalidIndex_exceptionThrown() {
        // Instantiate objects
        EntryDatabase edb = new EntryDatabase();
        Food prata = new Food("Prata", 100, Food.FoodType.MEAL);
        Food chickenRice = new Food("Chicken Rice", 325, Food.FoodType.MEAL);
        Food nasiLemak = new Food("Nasi Lemak", 400, Food.FoodType.MEAL);

        // Add Entries
        edb.addEntry(MealType.DINNER, prata);
        edb.addEntry(MealType.DINNER, chickenRice);

        // Edit Entry

        Exception exception1 = assertThrows(FitNusException.class, () -> edb.editEntryAtIndex(0, nasiLemak));
        Exception exception2 = assertThrows(FitNusException.class, () -> edb.editEntryAtIndex(-1, nasiLemak));
        Exception exception3 = assertThrows(FitNusException.class, () -> edb.editEntryAtIndex(3, nasiLemak));

        assertEquals("Invalid index!", exception1.getMessage());
        assertEquals("Invalid index!", exception2.getMessage());
        assertEquals("Invalid index!", exception3.getMessage());
    }

    @Test
    void listEntries_existingEntries_SuccessfullyReturnsEntries() {
        // Instantiate objects
        EntryDatabase edb = new EntryDatabase();
        Food prata = new Food("Prata", 100, Food.FoodType.MEAL);
        Food chickenRice = new Food("Chicken Rice", 325, Food.FoodType.MEAL);

        // Add Entries
        edb.addEntry(MealType.DINNER, prata);
        edb.addEntry(MealType.DINNER, chickenRice);

        // Test
        assertEquals(" 1.[" + LocalDate.now() + "] Dinner: Prata (100 Kcal) Type: MEAL"
                        + System.lineSeparator()
                        + " 2.[" + LocalDate.now() + "] Dinner: Chicken Rice (325 Kcal) Type: MEAL"
                        + System.lineSeparator(), edb.listEntries());
    }

    @Test
    void listEntries_noEntries_SuccessfullyReturnsNoEntries() {
        // Instantiate objects
        EntryDatabase edb = new EntryDatabase();

        // Test
        assertEquals("", edb.listEntries());
    }

    @Test
    void findEntries_validKeyword_getMatchingEntries() throws FitNusException {
        String keyword = "Rice";
        EntryDatabase database = new EntryDatabase();
        Food prata = new Food("Prata", 100, Food.FoodType.MEAL);
        Food chickenRice = new Food("Chicken Rice", 325, Food.FoodType.SNACK);
        database.addEntry(MealType.DINNER, prata);
        database.addEntry(MealType.DINNER, chickenRice);
        assertEquals("[[2021-10-26] Dinner: Chicken Rice (325 Kcal) Type: SNACK]",
                database.findEntries(keyword).toString());
    }

    @Test
    void findEntries_emptyStringKeyword_throwsFitNusException() throws FitNusException {
        String keyword = "";
        EntryDatabase database = new EntryDatabase();
        Food prata = new Food("Prata", 100, Food.FoodType.MEAL);
        Food chickenRice = new Food("Chicken Rice", 325, Food.FoodType.SNACK);
        database.addEntry(MealType.DINNER, prata);
        database.addEntry(MealType.DINNER, chickenRice);
        assertThrows(FitNusException.class, () -> database.findEntries(keyword));
    }
}