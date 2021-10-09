package fitnus;


import java.io.BufferedReader;
import java.io.IOException;

import fitnus.parser.Parser;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class EntryDatabase {
    private final ArrayList<Entry> entries;

    public EntryDatabase() {
        this.entries = new ArrayList<>();
    }

    public void addEntry(Food food) {
        this.entries.add(new Entry(food));
    }

    public void addEntry(Entry entry) {
        this.entries.add(entry);
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

    public void preLoadDatabase(BufferedReader reader) throws IOException {
        int preloadEntryCount = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] description = line.trim().split("\\s*[|]\\s*");
            String name = description[0];
            Integer calories = Integer.parseInt(description[1]);
            Food food = new Food(name, calories);
            LocalDateTime dateTime = Parser.parseDateAndTime(line);
            Entry entry = new Entry(food, dateTime);
            this.addEntry(entry);
            System.out.println(entry);
            preloadEntryCount++;
        }
        System.out.println("Successfully preloaded " + preloadEntryCount + " entries");
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

    public String listEntries() {
        String result = "";
        for (int i = 1; i <= entries.size(); i++) {
            result += String.format(" %d.%s", i, entries.get(i - 1)
                    + System.lineSeparator());
        }
        return result;
    }
}

