package fitnus;

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
        Food food = new Food("Bread", 50);
        Entry entry = new Entry(food, LocalDate.parse("2021-01-01"));
        assertEquals("[2021-01-01] Bread (50 Kcal)", entry.toString());
    }
}