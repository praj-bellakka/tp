package fitnus.command;

import fitnus.EntryDatabase;
import fitnus.FoodDatabase;
import fitnus.User;

public class SetCalorieGoalCommand extends Command {
    private int calorieGoal;
    private User user;

    public SetCalorieGoalCommand(int calorieGoal, User user) {
        this.calorieGoal = calorieGoal;
        this.user = user;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        this.user.setCalorieGoal(calorieGoal);
        return "The calorie goal has been set to " + this.calorieGoal;
    }
}
