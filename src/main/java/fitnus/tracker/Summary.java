package fitnus.tracker;

import fitnus.database.EntryDatabase;
import fitnus.tracker.Entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Summary {
    private ArrayList<Entry> entries;

    public Summary(EntryDatabase ed) {
        ed.sortDatabase();
        this.entries = ed.getEntries();
    }

    private String getMostAndLeastEatenFood() {
        HashMap<String, Integer> occurrence = new HashMap<>();

        for (Entry i: entries) {
            String foodName = i.getFood().getName();
            occurrence.compute(foodName, (key, val) -> {
                if (val == null) {
                    return 1;
                }
                return val + 1;
            });
        }

        ArrayList<String> mostFrequentFoods = new ArrayList<>();
        ArrayList<String> leastFrequentFoods = new ArrayList<>();
        int maxOccurrence = 0;
        int minOccurrence = entries.size();

        // Iterates through hashmap entries to find most frequent food
        for (Map.Entry<String, Integer> e : occurrence.entrySet()) {
            if (e.getValue() > maxOccurrence) {
                // Update max val and most freq foods
                maxOccurrence = e.getValue();
                mostFrequentFoods.clear();
                mostFrequentFoods.add(e.getKey());
            } else if (e.getValue() == maxOccurrence) {
                // Add food to most freq foods
                mostFrequentFoods.add(e.getKey());
            }
        }
        // Iterates through hashmap entries to find least frequent food
        for (Map.Entry<String, Integer> e : occurrence.entrySet()) {
            if (e.getValue() < minOccurrence) {
                // Update min val and least freq foods
                minOccurrence = e.getValue();
                leastFrequentFoods.clear();
                leastFrequentFoods.add(e.getKey());
            } else if (e.getValue() == minOccurrence) {
                // Add food to least freq foods
                leastFrequentFoods.add(e.getKey());
            }
        }

        return String.format("Food eaten most: %s [%d time(s)]\n"
                        + "Food eaten least: %s [%d time(s)]",
                mostFrequentFoods, maxOccurrence,
                leastFrequentFoods, minOccurrence);
    }

    private int getAverageCalories() {
        int totalCalories = 0;
        int totalNumEntries = entries.size();

        for (Entry e : entries) {
            totalCalories += e.getFood().getCalories();
        }

        return totalCalories / totalNumEntries;
    }

    private String getWeekCalorieTrend() {
        //TODO @SIYUAN TO REFACTOR VIEWDAILYCALORIETREND HERE (add a new line at the end thanks :D)
        return null;
    }

    public String generateWeekSummaryReport() {
        if (entries.size() < 1) {
            return "No entries found!";
        }

        String output = String.format(getWeekCalorieTrend()
                        + "Average Daily Calorie Intake: %d\n"
                + getMostAndLeastEatenFood(),
                getAverageCalories());
        return output;
    }

    public String generateMonthSummaryReport() {
        if (entries.size() < 1) {
            return "No entries found!";
        }

        String output = String.format("Average Daily Calorie Intake: %d\n"
                + getMostAndLeastEatenFood(),
                getAverageCalories());
        return output;
    }
}
