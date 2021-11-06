package fitnus.database;


import java.io.BufferedReader;
import java.io.IOException;

import fitnus.exception.FitNusException;
import fitnus.tracker.Entry;
import fitnus.tracker.Food;
import fitnus.parser.Parser;
import fitnus.tracker.MealType;
import fitnus.utility.Ui;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Keeps a record of Entry objects and handles functionalities related to Entry objects.
 */
public class EntryDatabase {
    private final ArrayList<Entry> entries;
    private static final String DELIMITER = " | ";

    /**
     * Constructor.
     */
    public EntryDatabase() {
        this.entries = new ArrayList<>();
    }

    /**
     * Adds an Entry object to the database.
     *
     * @param mealType MealType of this Entry (e.g. dinner / lunch).
     * @param food     Food object of this Entry.
     */
    public void addEntry(MealType mealType, Food food) {
        this.entries.add(new Entry(mealType, food));
    }

    /**
     * Adds an Entry object to the database.
     *
     * @param entry The Entry object to be added.
     */
    public void addEntry(Entry entry) {
        this.entries.add(entry);
    }

    /**
     * Sorts the Entry objects in the database in ascending order by date.
     */
    public void sortDatabase() {
        entries.sort(Comparator.comparing(Entry::getDate));
    }

    /**
     * Removes a specified Entry from the database.
     *
     * @param index The index of the Entry to remove.
     * @throws FitNusException If the index provided is not valid.
     */
    public void deleteEntry(int index) throws FitNusException {
        try {
            this.entries.remove(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new FitNusException("Sorry the index chosen is invalid! Please try again!");
        }
    }

    /**
     * Computes and returns the total calories consumed today.
     *
     * @return Calorie intake for today.
     */
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

    /**
     * Converts the database content to String form for storage.
     *
     * @return The database content as String.
     */
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

    /**
     * Preloads the database from data text file.
     *
     * @param reader Reads from the file.
     * @throws IOException If an I/O error occurs.
     */
    public void preloadDatabase(BufferedReader reader) throws IOException {
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
            } catch (NumberFormatException e) {
                Ui.println(e.getMessage());
            }
        }
        Ui.println("Successfully preloaded " + preloadEntryCount + " entries");
    }

    /**
     * Returns the whole entries ArrayList.
     *
     * @return The entries ArrayList.
     */
    public ArrayList<Entry> getEntries() {
        return entries;
    }

    /**
     * Returns the Entry object at the specified index.
     *
     * @param index Index of the Entry object.
     * @return The Entry object at the specified index.
     * @throws FitNusException If the index provided is invalid.
     */
    public Entry getEntryAtIndex(int index) throws FitNusException {
        try {
            return entries.get(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new FitNusException("Sorry the index chosen is invalid! Please try again!");
        }
    }

    /**
     * Returns the database content as a formatted String (in list form).
     *
     * @return String representation of the database content.
     */
    public String listEntries() {
        if (entries.size() == 0) {
            return "Oops, there are no records found!";
        }
        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= entries.size(); i++) {
            result.append(String.format(" %d.%s", i, entries.get(i - 1)
                    + System.lineSeparator()));
        }
        return result.toString();
    }

    /**
     * Filters Entry objects based on the keyword provided and returns matching
     * Entry objects in an ArrayList.
     *
     * @param keyword The keyword used to filter Entry objects.
     * @return An ArrayList containing matching Entry objects.
     * @throws FitNusException If the keyword provided is an empty String.
     */
    public ArrayList<Entry> findEntries(String keyword) throws FitNusException {
        if (keyword.equals("")) {
            throw new FitNusException("Please provide a valid keyword");
        }
        return (ArrayList<Entry>) entries.stream()
                .filter(t -> t.getFood().getName().contains(keyword))
                .collect(Collectors.toList());
    }

    /**
     * Returns an EntryDatabase with all Entry objects added in the past specified number of days.
     *
     * @param days The specified number of past days.
     * @return An EntryDatabase containing Entry objects from the specified number of past days.
     */
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
        Collections.reverse(pastDaysEntries.entries);
        return pastDaysEntries;
    }

    /**
     * Returns an EntryDatabase with all Entry objects added in the current month.
     *
     * @return An EntryDatabase containing Entry objects from the current month.
     */
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

    /**
     * Modifies a specified Entry by setting its associated Food object
     * with a new Food object.
     *
     * @param index The index of the Entry object to modify.
     * @param food  The new Food object to associate with the specified Entry.
     * @throws FitNusException If the index provided is invalid.
     */
    public void editEntryAtIndex(int index, Food food) throws FitNusException {
        try {
            entries.get(index - 1).setFood(food);
        } catch (IndexOutOfBoundsException e) {
            throw new FitNusException("Invalid index!");
        }

    }
}

