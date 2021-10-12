package fitnus.database;


import java.io.BufferedReader;
import java.io.IOException;

import fitnus.exception.FitNusException;
import fitnus.tracker.Entry;
import fitnus.tracker.Food;
import fitnus.parser.Parser;
import fitnus.utility.Storage;
import fitnus.utility.Ui;

import java.time.LocalDate;
import java.util.ArrayList;

public class EntryDatabase {
    private final ArrayList<Entry> entries;
    private static final String DELIMITER = " | ";

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
        assert caloriesConsumed >= 0 : "calories consumed should be non-negative";
        return caloriesConsumed;
    }

    public String convertDatabaseToString() {
        StringBuilder lines = new StringBuilder();
        for (Entry e : entries) {
            assert e != null : "e should not be null";
            Food food = e.getFood();
            String date = e.getDate();
            String name = food.getName();
            Integer calories = food.getCalories();
            lines.append(name).append(DELIMITER).append(calories).append(DELIMITER)
                    .append(date).append(System.lineSeparator());
        }
        return lines.toString();
    }

    public void preLoadDatabase(BufferedReader reader) throws IOException {
        int preloadEntryCount = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] description = line.trim().split("\\s*[|]\\s*");
            try {
                String name = description[0];
                Integer calories = Integer.parseInt(description[1]);
                Food food = new Food(name, calories);
                LocalDate date = Parser.getDate(line);
                Entry entry = new Entry(food, date);
                this.addEntry(entry);
                preloadEntryCount++;
            } catch (FitNusException e) {
                Ui.println(e.getMessage());
                Ui.printPreloadDatabaseError();
            } catch (IndexOutOfBoundsException e) {
                Ui.printPreloadDatabaseError();
            }
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

    public Entry getEntryAtIndex(int index) throws FitNusException {
        try {
            return entries.get(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new FitNusException("Sorry the index chosen is invalid! Please try again!");
        }
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

