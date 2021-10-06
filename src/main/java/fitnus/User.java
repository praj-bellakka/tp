package fitnus;

import java.util.ArrayList;

public class User {
    private static final int MALE = 0;
    private static final int FEMALE = 1;
    private int calorieGoal;
    private int gender;

    public User(int gender, int calorieGoal) {
        this.calorieGoal = calorieGoal;
        this.gender = (gender == 0)? MALE: FEMALE;
    }




}
