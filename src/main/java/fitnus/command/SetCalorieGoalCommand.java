package fitnus.command;

import fitnus.EntryDatabase;
import fitnus.FitNusException;
import fitnus.FoodDatabase;
import fitnus.User;

public class SetCalorieGoalCommand extends Command {
    private final int calorieGoal;

    public SetCalorieGoalCommand(int calorieGoal) {
        this.calorieGoal = calorieGoal;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) throws FitNusException {
        us.setCalorieGoal(calorieGoal);
        return "The calorie goal has been set to " + this.calorieGoal;
    }
}
