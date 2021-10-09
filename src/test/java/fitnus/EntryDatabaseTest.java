package fitnus;

import fitnus.parser.Parser;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        LocalDateTime dateTime = Parser.parseDateAndTime("2021-10-11 10:23");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
        String dateTimeString = dateTime.format(formatter);
        Entry entry1 = new Entry(new Food("chicken rice", 200), dateTime);
        Entry entry2 = new Entry(new Food("steak", 900), dateTime);
        Entry entry3 = new Entry(new Food("laksa", 400), dateTime);
        Entry entry4 = new Entry(new Food("hotpot", 1100), dateTime);
        edb.addEntry(entry1);
        edb.addEntry(entry2);
        edb.addEntry(entry3);
        edb.addEntry(entry4);
        String expectedOutput = String.format("chicken rice | 200 | %s\n"
                + "steak | 900 | %s\n"
                + "laksa | 400 | %s\n"
                + "hotpot | 1100 | %s\n", dateTimeString, dateTimeString,
                dateTimeString, dateTimeString);
        assertEquals(expectedOutput, edb.convertDatabaseToString());
    }

}