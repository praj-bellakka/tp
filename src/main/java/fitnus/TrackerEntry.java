package fitnus;

import java.util.Date;

public class TrackerEntry {
    Food food;
    Date date;

    public TrackerEntry(Food food, Date date) {
        this.food = food;
        this.date = date;
    }

    public static void addDefaultFoodEntry(int index) {
        Food defaultFood = Database.databaseFoods.get(index - 1);
        Date currDate = new Date();

        TrackerEntry entry = new TrackerEntry(defaultFood, currDate);
        User.trackerEntries.add(entry);
    }

    @Override
    public String toString() {
        return this.food + " " + this.date;
    }
}
