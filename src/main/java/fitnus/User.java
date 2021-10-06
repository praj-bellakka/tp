package fitnus;

import java.util.ArrayList;
import java.util.Date;

public class User {
    private static final int MALE = 0;
    private static final int FEMALE = 1;
    private int calorieGoal;
    private int gender;

    public User(int gender, int calorieGoal) {
        this.calorieGoal = calorieGoal;
        this.gender = (gender == 0) ? MALE : FEMALE;
    }

    public void setCalorieGoal(int newGoal) {
        this.calorieGoal = newGoal;
    }

    public void showCaloriesRemaining(EntryDatabase entryDB) {
        Ui ui = new Ui();
        ArrayList<Entry> entries = entryDB.getEntries();

        int caloriesConsumed = 0;
        for(Entry entry : entries) {
            caloriesConsumed += entry.getFood().getCalories();
        }
        int caloriesRemaining = this.calorieGoal - caloriesConsumed;
        ui.println("Calories remaining: " + caloriesRemaining);
    }
}
