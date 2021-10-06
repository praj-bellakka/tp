package fitnus;

import java.util.ArrayList;
import java.util.Date;

public class User {
    public static ArrayList<TrackerEntry> trackerEntries = new ArrayList<>();
    public static int calorieGoal;
    public static int caloriesRemaining;
    public static String gender;

    public void showCaloriesRemaining(int index) {
        Ui ui = new Ui();

        //ALTERNATIVE 1: if we update caloriesRemaining everytime a new tracker entry is added:
        ui.println("Calories remaining: " + this.caloriesRemaining);

        //ALTERNATIVE 2: Calculate calories remaining everytime this method is called,
        //in which case don't need caloriesRemaining attribute in this class
        int caloriesConsumed = 0;
        for(TrackerEntry trackerEntry : this.trackerEntries) {
            caloriesConsumed += trackerEntry.getFood().getCalories();
        }
        int caloriesRemaining = this.calorieGoal - caloriesConsumed;
        ui.println("Calories remaining: " + caloriesRemaining);
    }
}
