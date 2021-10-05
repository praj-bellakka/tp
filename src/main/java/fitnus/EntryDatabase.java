package fitnus;

import java.time.LocalDate;
import java.util.ArrayList;

public class EntryDatabase {
    private ArrayList<Entry> entries;

    public EntryDatabase() {
        this.entries = new ArrayList<>();
    }

    public void addEntry(Food food) {
        this.entries.add(new Entry(food));
    }

    public void deleteEntry(int index) {
        this.entries.remove(index);
    }
}


class Entry {
    Food food;
    LocalDate date;

    Entry(Food food) {
        this.food = food;
        this.date = LocalDate.now();
    }

    Food getFood() {
        return this.food;
    }

    LocalDate getDate() {
        return this.date;
    }
}
