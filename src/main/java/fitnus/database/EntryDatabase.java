package fitnus.database;


import java.io.BufferedReader;
import java.io.IOException;

import fitnus.exception.FitNusException;
import fitnus.tracker.Entry;
import fitnus.tracker.Food;
import fitnus.parser.Parser;
import fitnus.tracker.MealType;
import fitnus.storage.Storage;
import fitnus.utility.Ui;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public class EntryDatabase {
    private final ArrayList<Entry> entries;
    private static final String DELIMITER = " | ";

    public EntryDatabase() {
        this.entries = new ArrayList<>();
    }

    public void addEntry(MealType mealType, Food food) {
        this.entries.add(new Entry(mealType, food));
    }

    public void addEntry(Entry entry) {
        this.entries.add(entry);
    }

    public void sortDatabase() {
        entries.sort(Comparator.comparing(Entry::getDate));
    }

    public void deleteEntry(int index) throws FitNusException {
        try {
            this.entries.remove(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new FitNusException("Sorry the index chosen is invalid! Please try again!");
        }
    }

    public int getTotalDailyCalorie() {
        int caloriesConsumed = 0;
        for (Entry e : entries) {
            if (e.getDate().equals(LocalDate.now().toString())) {
                caloriesConsumed += e.getFood().getCalories();
            }
        }
        assert caloriesConsumed >= 0 : "calories consumed should be non-negative";
        return caloriesConsumed;
    }

    public String convertDatabaseToString() {
        StringBuilder lines = new StringBuilder();
        for (Entry e : entries) {
            assert e != null : "e should not be null";
            MealType mealType = e.getMealType();
            Food food = e.getFood();
            String date = e.getDate();
            String name = food.getName();
            Integer calories = food.getCalories();
            String type = food.getType().toString();
            lines.append(mealType).append(DELIMITER).append(name).append(DELIMITER).append(calories).append(DELIMITER)
                    .append(date).append(DELIMITER).append(type).append(System.lineSeparator());
        }
        return lines.toString();
    }

    public void preLoadDatabase(BufferedReader reader) throws IOException {
        int preloadEntryCount = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] description = line.trim().split("\\s*[|]\\s*");
            try {
                MealType mealType = Parser.parseMealType(description[0], true);
                String name = description[1];
                Integer calories = Integer.parseInt(description[2]);
                Food.FoodType type = Parser.parseFoodType(description[4]);
                Food food = new Food(name, calories, type);
                LocalDate date = Parser.getDate(line);
                Entry entry = new Entry(mealType, food, date);
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

    public void addDefaultEntry(MealType mealType, FoodDatabase fd, int index) throws FitNusException {
        try {
            Food food = fd.getFoodAtIndex(index);
            addEntry(mealType, food);
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

    public ArrayList<Entry> findEntry(String keyword) throws FitNusException {
        if (keyword.equals("")) {
            throw new FitNusException("Please provide a valid keyword");
        }
        return (ArrayList<Entry>) entries.stream()
                .filter(t -> t.getFood().getName().contains(keyword))
                .collect(Collectors.toList());
    }

    public EntryDatabase getPastDaysEntryDatabase(int days) {
        sortDatabase();
        ArrayList<Entry> totalEntries = getEntries();
        EntryDatabase pastDaysEntries = new EntryDatabase();

        LocalDate datePointer = LocalDate.now();
        int count = totalEntries.size() - 1;

        for (int i = 0; count >= 0 && i < days; i++) {
            while (count >= 0 && totalEntries.get(count).getRawDate().equals(datePointer)) {
                pastDaysEntries.addEntry(totalEntries.get(count));
                count--;
            }
            datePointer = datePointer.minusDays(1);
        }
        return pastDaysEntries;
    }

    public EntryDatabase getPastMonthEntryDatabase() {
        sortDatabase();
        ArrayList<Entry> totalEntries = getEntries();
        EntryDatabase pastMonthEntries = new EntryDatabase();

        Month monthPointer = LocalDate.now().getMonth();
        int count = totalEntries.size() - 1;

        while (count >= 0 && totalEntries.get(count).getRawDate().getMonth().equals(monthPointer)) {
            pastMonthEntries.addEntry(totalEntries.get(count));
            count--;
        }

        return pastMonthEntries;
    }

    public void editEntryAtIndex(int index, Food food) {
        entries.get(index - 1).setFood(food);

    }
}

