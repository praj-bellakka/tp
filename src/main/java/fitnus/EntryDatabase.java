package fitnus;


import java.io.IOException;
import fitnus.parser.Parser;
import java.util.ArrayList;

public class EntryDatabase {
    private ArrayList<Entry> entries;

    public EntryDatabase() {
        this.entries = new ArrayList<>();
    }

    public void addEntry(Food food) {
        this.entries.add(new Entry(food));
    }

    public void deleteEntry(int index) throws FitNusException {
        try {
            this.entries.remove(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new FitNusException("Sorry the index chosen is invalid! Please try again!");
        }
    }

    public void store() throws IOException {
        Storage.saveData("entry database.txt", this.convertDatabaseToString());
    }

    public int getTotalCalorie() {
        int caloriesConsumed = 0;
        for (Entry e : entries) {
            caloriesConsumed += e.getFood().getCalories();
        }
        return caloriesConsumed;
    }

    public String convertDatabaseToString() {
        String content = "";
        for (Entry e : entries) {
            content += (e.convertToStringForStorage() + "\n");
        }
        return content;
    }
    
    public ArrayList<Entry> getEntries() {
        return entries;
    }

    public void addDefaultEntry(FoodDatabase fd, int index) throws FitNusException {
        try {
            Food food = fd.getFoodAtIndex(index);
            addEntry(food);
        } catch (IndexOutOfBoundsException e) {
            throw new FitNusException("Sorry the index chosen is invalid! Please try again!");
        }
    }

    public Entry getEntryAtIndex(int index) throws IndexOutOfBoundsException {
        return entries.get(index - 1);
    }
}

