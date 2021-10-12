package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.exception.FitNusException;
import fitnus.database.FoodDatabase;
import fitnus.utility.User;

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
