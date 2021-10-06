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

//    public void store() {
//        Storage.save("entry database", this.convertDatabaseToString());
//    }

    public String convertDatabaseToString() {
        String content = "";
        for (Entry e : entries) {
            content += (e.convertToStringForStorage() + "\n");
        }
        return content;
    }
}

