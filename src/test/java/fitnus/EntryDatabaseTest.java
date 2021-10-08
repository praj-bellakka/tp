package fitnus;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntryDatabaseTest {
    @Test
    public void convertDatabaseToStringTest_emptyList_emptyString() {
        EntryDatabase edb = new EntryDatabase();
        assertEquals("", edb.convertDatabaseToString());
    }


    @Test
    public void convertDatabaseToStringTest_list_stringRepresentation() {
        EntryDatabase edb = new EntryDatabase();
        String today = LocalDate.now().toString();
        edb.addEntry(new Food("chicken rice", 200));
        edb.addEntry(new Food("steak", 900));
        edb.addEntry(new Food("laksa", 400));
        edb.addEntry(new Food("hotpot", 1100));
        String expectedOutput = String.format("chicken rice | 200 | %s\n"
                + "steak | 900 | %s\n"
                + "laksa | 400 | %s\n"
                + "hotpot | 1100 | %s\n", today, today, today, today);
        assertEquals(expectedOutput, edb.convertDatabaseToString());
    }

}