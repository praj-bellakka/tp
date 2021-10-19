package fitnus.database;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
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
        Entry entry1 = new Entry(MealType.DINNER, new Food("chicken rice", 200), date);
        Entry entry2 = new Entry(MealType.LUNCH, new Food("steak", 900), date);
        Entry entry3 = new Entry(MealType.BREAKFAST, new Food("laksa", 400), date);
        Entry entry4 = new Entry(MealType.SNACK, new Food("hotpot", 1100), date);
        edb.addEntry(entry1);
        edb.addEntry(entry2);
        edb.addEntry(entry3);
        edb.addEntry(entry4);
        String expectedOutput = String.format("Dinner | chicken rice | 200 | %s" + System.lineSeparator()
                + "Lunch | steak | 900 | %s" + System.lineSeparator()
                + "Breakfast | laksa | 400 | %s" + System.lineSeparator()
                + "Snack | hotpot | 1100 | %s" + System.lineSeparator(), date, date, date, date);
        assertEquals(expectedOutput, edb.convertDatabaseToString());
    }

    @Test
    void addEntry_validEntry_entryAddedSuccessfully() throws FitNusException {
        // Instantiate objects
        EntryDatabase edb = new EntryDatabase();
        Food prata = new Food("Prata", 100);
        Food chickenRice = new Food("Chicken Rice", 325);
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
        Food prata = new Food("Prata", 100);
        Food chickenRice = new Food("Chicken Rice", 325);

        // Add Entries
        edb.addEntry(MealType.DINNER, prata);
        edb.addEntry(MealType.DINNER, chickenRice);

        // Test
        assertEquals(425, edb.getTotalCalorie());
    }

    @Test
    void getTotalCalorie_zeroEntries_getTotalCaloriesSuccessfully() {
        // Instantiate objects
        EntryDatabase edb = new EntryDatabase();

        // Test
        assertEquals(0, edb.getTotalCalorie());
    }

    @Test
    void addDefaultEntry_validIndex_entryAddedSuccessfully() throws FitNusException {
        // Instantiate objects
        FoodDatabase fdb = new FoodDatabase();
        Food prata = new Food("Prata", 100);
        Food chickenRice = new Food("Chicken Rice", 325);
        Food pizza = new Food("Pizza", 260);

        // Add Food to database
        fdb.addFood(prata);
        fdb.addFood(chickenRice);
        fdb.addFood(pizza);

        // Add Entries
        EntryDatabase edb = new EntryDatabase();
        edb.addDefaultEntry(MealType.DINNER, fdb, 1);
        edb.addDefaultEntry(MealType.DINNER, fdb, 2);
        edb.addDefaultEntry(MealType.DINNER, fdb, 3);

        // Test
        assertEquals(prata, edb.getEntryAtIndex(1).getFood());
        assertEquals(chickenRice, edb.getEntryAtIndex(2).getFood());
        assertEquals(pizza, edb.getEntryAtIndex(3).getFood());
    }

    @Test
    void addDefaultEntry_invalidIndex_exceptionThrown() throws FitNusException {
        // Instantiate objects
        EntryDatabase edb = new EntryDatabase();
        FoodDatabase fdb = new FoodDatabase();
        Food prata = new Food("Prata", 100);
        Food chickenRice = new Food("Chicken Rice", 325);
        Food pizza = new Food("Pizza", 260);

        // Add Food to database
        fdb.addFood(prata);
        fdb.addFood(chickenRice);
        fdb.addFood(pizza);

        // Test
        Exception exception1 = assertThrows(FitNusException.class,
            () -> edb.addDefaultEntry(MealType.DINNER, fdb, 0));
        Exception exception2 = assertThrows(FitNusException.class,
            () -> edb.addDefaultEntry(MealType.DINNER, fdb, -1));
        Exception exception3 = assertThrows(FitNusException.class,
            () -> edb.addDefaultEntry(MealType.DINNER, fdb, 100));

        assertEquals("Sorry the index chosen is invalid! Please try again!", exception1.getMessage());
        assertEquals("Sorry the index chosen is invalid! Please try again!", exception2.getMessage());
        assertEquals("Sorry the index chosen is invalid! Please try again!", exception3.getMessage());
    }

    @Test
    void deleteEntry_validIndex_entryDeletedSuccessfully() throws FitNusException {
        // Instantiate objects
        EntryDatabase edb = new EntryDatabase();
        Food prata = new Food("Prata", 100);
        Food chickenRice = new Food("Chicken Rice", 325);

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
        Food prata = new Food("Prata", 100);
        Food chickenRice = new Food("Chicken Rice", 325);

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
        Food prata = new Food("Prata", 100);
        Food chickenRice = new Food("Chicken Rice", 325);

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
        Food prata = new Food("Prata", 100);
        Food chickenRice = new Food("Chicken Rice", 325);

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
    void preLoadDatabase_validInput_SuccessfullyPreloadDatabase()
            throws IOException {
        EntryDatabase ed = new EntryDatabase();
        String initialString = "Breakfast | food1 | 100 | 2021-10-12" + System.lineSeparator()
                + "Lunch | food2 | 200 | 2021-10-12";
        InputStream stream = new ByteArrayInputStream(initialString.getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        ed.preLoadDatabase(reader);
        assertEquals(" 1.[2021-10-12] Breakfast: food1 (100 Kcal)" + System.lineSeparator()
                + " 2.[2021-10-12] Lunch: food2 (200 Kcal)" + System.lineSeparator(), ed.listEntries());
    }
}