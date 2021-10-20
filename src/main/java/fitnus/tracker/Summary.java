package fitnus.tracker;

import fitnus.database.EntryDatabase;
import fitnus.tracker.Entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Summary {
    private ArrayList<Entry> entries;

    public Summary(EntryDatabase ed) {
        ed.sortDatabase();
        this.entries = ed.getEntries();
    }

    private String getMostAndLeastEatenFood() {
        HashMap<Food, Integer> occurrence = new HashMap<>();

        for (Entry i: entries) {
            Food food = i.getFood();
            occurrence.compute(food, (key, val) -> {
                if (val == null) {
                    return 1;
                }
                return val + 1;
            });
        }

        ArrayList<Food> mostFrequentFoods = new ArrayList<>();
        ArrayList<Food> leastFrequentFoods = new ArrayList<>();
        int maxOccurrence = 0;
        int minOccurrence = entries.size();

        // Iterates through hashmap entries to find most frequent food
        for (Map.Entry<Food, Integer> e : occurrence.entrySet()) {
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
        for (Map.Entry<Food, Integer> e : occurrence.entrySet()) {
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

        return String.format("Food eaten most: %s [%d time(s)]\n" +
                        "Food eaten least: %s [%d time(s)]",
                mostFrequentFoods, maxOccurrence,
                leastFrequentFoods, minOccurrence);
    }

    public String generateSummaryReport() {
        if (entries.size() < 1) {
            return "No entries found!";
        }
        return getMostAndLeastEatenFood();
    }
}
