package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.utility.User;

public class ViewRemainingCalorieCommand extends Command {
    public ViewRemainingCalorieCommand() {
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        int calorieRemain = us.showCaloriesRemaining(ed);
        if (calorieRemain < 0) {
            return String.format("Oops! You have exceeded the daily calorie goal by %d", Math.abs(calorieRemain));
        }
        return String.format("The remaining calories before reaching the daily goal is %d",
                calorieRemain);
    }
}
