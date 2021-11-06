package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.database.FoodDatabase;
import fitnus.utility.User;

public class SetCalorieGoalCommand extends Command {
    private final int calorieGoal;

    public SetCalorieGoalCommand(int calorieGoal) {
        this.calorieGoal = calorieGoal;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us) throws FitNusException {
        us.setCalorieGoal(calorieGoal);
        return "Your calorie goal has been set to " + this.calorieGoal + " kcal!";
    }
}
