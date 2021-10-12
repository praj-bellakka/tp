package fitnus;

import fitnus.parser.Parser;
import org.junit.jupiter.api.Test;

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
        Entry entry1 = new Entry(new Food("chicken rice", 200), date);
        Entry entry2 = new Entry(new Food("steak", 900), date);
        Entry entry3 = new Entry(new Food("laksa", 400), date);
        Entry entry4 = new Entry(new Food("hotpot", 1100), date);
        edb.addEntry(entry1);
        edb.addEntry(entry2);
        edb.addEntry(entry3);
        edb.addEntry(entry4);
        String expectedOutput = String.format("chicken rice | 200 | %s\n"
                + "steak | 900 | %s\n"
                + "laksa | 400 | %s\n"
                + "hotpot | 1100 | %s\n", date, date, date, date);
        assertEquals(expectedOutput, edb.convertDatabaseToString());
    }

    @Test
    void addEntry_validEntry_entryAddedSuccessfully() throws FitNusException {
        // Instantiate objects
        EntryDatabase edb = new EntryDatabase();
        Food prata = new Food("Prata", 100);
        Food chickenRice = new Food("Chicken Rice", 325);
        Entry chickenRiceEntry = new Entry(chickenRice);

        // Add Entries
        edb.addEntry(prata);
        edb.addEntry(chickenRiceEntry);

        // Test
        assertEquals(prata, edb.getEntryAtIndex(1).food);
        assertEquals(chickenRice, edb.getEntryAtIndex(2).food);
    }

    @Test
    void getTotalCalorie_nonZeroEntries_getTotalCaloriesSuccessfully() {
        // Instantiate objects
        EntryDatabase edb = new EntryDatabase();
        Food prata = new Food("Prata", 100);
        Food chickenRice = new Food("Chicken Rice", 325);

        // Add Entries
        edb.addEntry(prata);
        edb.addEntry(chickenRice);

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
        edb.addDefaultEntry(fdb, 1);
        edb.addDefaultEntry(fdb, 2);
        edb.addDefaultEntry(fdb, 3);

        // Test
        assertEquals(prata, edb.getEntryAtIndex(1).food);
        assertEquals(chickenRice, edb.getEntryAtIndex(2).food);
        assertEquals(pizza, edb.getEntryAtIndex(3).food);
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
        Exception exception1 = assertThrows(FitNusException.class, () -> edb.addDefaultEntry(fdb, 0));
        Exception exception2 = assertThrows(FitNusException.class, () -> edb.addDefaultEntry(fdb, -1));
        Exception exception3 = assertThrows(FitNusException.class, () -> edb.addDefaultEntry(fdb, 100));

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
        edb.addEntry(prata);
        edb.addEntry(chickenRice);

        // Delete Entry
        edb.deleteEntry(2);

        // Test
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> edb.getEntryAtIndex(2));

        assertEquals("Index 1 out of bounds for length 1", exception.getMessage());
    }

    @Test
    void deleteEntry_invalidIndex_exceptionThrown() {
        // Instantiate objects
        EntryDatabase edb = new EntryDatabase();
        Food prata = new Food("Prata", 100);
        Food chickenRice = new Food("Chicken Rice", 325);

        // Add Entries
        edb.addEntry(prata);
        edb.addEntry(chickenRice);

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
        edb.addEntry(prata);
        edb.addEntry(chickenRice);

        // Test
        assertEquals(prata, edb.getEntryAtIndex(1).food);
        assertEquals(chickenRice, edb.getEntryAtIndex(2).food);
    }

    @Test
    void getEntryAtIndex_invalidIndex_getEntrySuccessfully() {
        // Instantiate objects
        EntryDatabase edb = new EntryDatabase();
        Food prata = new Food("Prata", 100);
        Food chickenRice = new Food("Chicken Rice", 325);

        // Add Entries
        edb.addEntry(prata);
        edb.addEntry(chickenRice);

        // Test
        Exception exception1 = assertThrows(IndexOutOfBoundsException.class, () -> edb.getEntryAtIndex(0));
        Exception exception2 = assertThrows(IndexOutOfBoundsException.class, () -> edb.getEntryAtIndex(-1));
        Exception exception3 = assertThrows(IndexOutOfBoundsException.class, () -> edb.getEntryAtIndex(100));

        assertEquals("Index -1 out of bounds for length 2", exception1.getMessage());
        assertEquals("Index -2 out of bounds for length 2", exception2.getMessage());
        assertEquals("Index 99 out of bounds for length 2", exception3.getMessage());
    }
}