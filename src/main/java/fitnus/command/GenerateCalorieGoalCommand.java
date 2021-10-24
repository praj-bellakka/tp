package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Gender;
import fitnus.utility.User;

public class GenerateCalorieGoalCommand extends Command {
    private final float weeklyChange;
    private final String changeType;

    private static final String GAIN_STRING = "gain";
    private static final String LOSE_STRING = "lose";

    public GenerateCalorieGoalCommand(float weeklyChange, String changeType) {
        this.weeklyChange = weeklyChange;
        this.changeType = changeType;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us) throws FitNusException {
        if (weeklyChange <= 1) {
            if ((changeType == GAIN_STRING || changeType == LOSE_STRING)) {
                int newGoal = us.generateCalorieGoal(weeklyChange, changeType);
                us.setCalorieGoal(newGoal);
                return "Your new calorie goal to " + changeType + " " + weeklyChange
                        + " kg per week is " + newGoal + " kcal daily!";
            } else {
                throw new FitNusException("Invalid change type! "
                        + "Please enter /gain or /lose as the change type parameter.");
            }
        } else {
            throw new FitNusException("In order to lose or gain weight in a safe and healthy way, "
                    + "FitNUS recommends a weekly change in weight of not more than "
                    + "1 kg. Please try again with a lower weekly goal!");
        }
    }
}
