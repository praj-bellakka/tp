package fitnus;

import fitnus.parser.Parser;
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
        LocalDate date = Parser.getDate("2021-10-11");
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

}