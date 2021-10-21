package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.exception.FitNusException;
import fitnus.utility.User;

public class GenerateCalorieGoalCommand extends Command {
    private final float weeklyChange;
    private final String changeType;

    public static final String GAIN_STRING = "gain";
    public static final String LOSE_STRING = "lose";
    private static final int MALE = 0;
    private static final int FEMALE = 1;
    private static final int calDeficitFor1KgWeekly = 1000;


    public GenerateCalorieGoalCommand(float weeklyChange, String changeType) {
        this.weeklyChange = weeklyChange;
        this.changeType = changeType;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) throws FitNusException {
        if (weeklyChange > 1) {
            return "In order to lose or gain weight in a safe and healthy way, " +
                    "FitNUS recommends a weekly change in weight of not more than" +
                    "1 kg. Please try again with a lower weekly goal!";
        } else {
            int bmr; //basal metabolic rate
            int calDiff = (int) Math.round(weeklyChange * calDeficitFor1KgWeekly);
            int newGoal;
            if (us.getGender() == MALE) {
                bmr = (int) Math.round(((655.1 + (9.563 * us.getWeight())
                        + (1.850 * us.getHeight())
                        - (4.676 * us.getAge())) * 1.55));
            } else {
                bmr = (int) Math.round(((66.47 + (13.75 * us.getWeight())
                        + (5.003 * us.getHeight())
                        - (6.755 * us.getAge())) * 1.55) - weeklyChange * calDeficitFor1KgWeekly);
            }

            if (changeType == GAIN_STRING) {
                newGoal = bmr + calDiff;
            } else {
                newGoal = bmr - calDiff;
            }

            us.setCalorieGoal(newGoal);
            return "Your new calorie goal to " + changeType + " " + weeklyChange
                    + " kg per week is " + newGoal + " kcal daily!";
        }


    }
}
