package fitnus.command;

import fitnus.User;

public class SetCalorieGoalCommand extends Command{
    private int calorieGoal;
    private User user;

    public SetCalorieGoalCommand(int calorieGoal, User user) {
        this.calorieGoal = calorieGoal;
        this.user = user;
    }

    @Override
    public String execute() {
        this.user.setCalorieGoal(calorieGoal);
        return "The calorie goal has been set to " + this.calorieGoal;
    }
}
