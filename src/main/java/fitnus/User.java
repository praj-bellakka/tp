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

    public void setCalorieGoal(int newGoal) throws FitNusException {
        if (newGoal < 0) {
            throw new FitNusException("Calorie Goal cannot be negative! Please try again!");
        } else if (newGoal == this.calorieGoal) {
            throw new FitNusException("Calorie Goal cannot be the same as before! Please try again!");
        }
        this.calorieGoal = newGoal;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int showCaloriesRemaining(EntryDatabase entryDB) {
        int caloriesConsumed = entryDB.getTotalCalorie();
        return this.calorieGoal - caloriesConsumed;
    }
}
