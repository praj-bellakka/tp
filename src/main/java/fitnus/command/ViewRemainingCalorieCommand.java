package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.utility.User;

public class ViewRemainingCalorieCommand extends Command {
    public ViewRemainingCalorieCommand() {
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us) {
        int calorieRemain = us.getCaloriesRemaining(ed);
        int calorieGoal = us.getCalorieGoal();
        if (calorieRemain < 0) {
            return String.format("Oops! You have exceeded the daily calorie goal by %d kcal", Math.abs(calorieRemain));
        }
        return String.format("You have " + calorieRemain
                + " calories remaining before reaching the daily goal of "
                + calorieGoal + "kcal!");
    }
}
