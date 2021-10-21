package fitnus.tracker;

import fitnus.database.EntryDatabase;
import fitnus.tracker.viewcalorietrend.ViewDailyCalorieTrend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Summary {
    private ArrayList<Entry> entries;

    public Summary(EntryDatabase ed) {
        ed.sortDatabase();
        this.entries = ed.getEntries();
    }

    private String getMostAndLeastEatenFood() {
        HashMap<String, Integer> occurrence = new HashMap<>();

        for (Entry i: entries) {
            String food = i.getFood().getName();
            int value = occurrence.compute(food, (k, v) -> {
                if (v == null) {
                    return 1;
                }
                return v + 1;
            });
        }

        ArrayList<String> mostFrequentFood = new ArrayList<>();
        ArrayList<String> leastFrequentFood = new ArrayList<>();
        int maxOccurrence = 0;
        int minOccurrence = entries.size();

        Iterator it = occurrence.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            if ((Integer) pair.getValue() > maxOccurrence) {
                maxOccurrence = (Integer) pair.getValue();
                mostFrequentFood = new ArrayList<>();
                mostFrequentFood.add((String) pair.getKey());
            } else if ((Integer) pair.getValue() == maxOccurrence) {
                mostFrequentFood.add((String) pair.getKey());
            }

            if ((Integer) pair.getValue() < minOccurrence) {
                minOccurrence = (Integer) pair.getValue();
                leastFrequentFood = new ArrayList<>();
                leastFrequentFood.add((String) pair.getKey());
            } else if ((Integer) pair.getValue() == minOccurrence) {
                leastFrequentFood.add((String) pair.getKey());
            }
        }
        return String.format("Foods you ate the most frequently are %s, and you took them %d time(s)\n" +
                "Foods you ate the least frequently are %s, and you took them %d time(s)",
                mostFrequentFood, maxOccurrence,
                leastFrequentFood, minOccurrence);
    }

    public String generateSummaryReport() {
        if (entries.size() < 1) {
            return "Oops！ No record found";
        }
        return getMostAndLeastEatenFood();
        //return ViewDailyCalorieTrend.generateTrendGraph(entries);
    }
}
