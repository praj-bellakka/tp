package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.utility.User;

public class GenerateCalorieGoalCommand extends Command {
    private final float weeklyChange;
    private final String changeType;

    private static final float MINIMUM_WEEKLY_CHANGE = (float) 0.01;

    public GenerateCalorieGoalCommand(float weeklyChange, String changeType) {
        this.weeklyChange = weeklyChange;
        this.changeType = changeType;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us) throws FitNusException {
        int newGoal = us.handleGenerateCalorieGoalCommand(weeklyChange, changeType);
        us.setCalorieGoal(newGoal);
        if (weeklyChange < MINIMUM_WEEKLY_CHANGE) {
            return "Your new calorie goal to maintain your current weight is "
                    + newGoal + " kcal daily!";
        }
        return "Your new calorie goal to " + changeType + " " + weeklyChange
                + " kg per week is " + newGoal + " kcal daily!";
    }
}
