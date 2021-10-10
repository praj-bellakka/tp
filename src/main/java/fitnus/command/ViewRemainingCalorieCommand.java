package fitnus.command;

import fitnus.EntryDatabase;
import fitnus.FoodDatabase;
import fitnus.User;

public class ViewRemainingCalorieCommand extends Command {
    public ViewRemainingCalorieCommand() {
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        return String.format("The remaining calories before reaching the daily goal is %d",
                us.showCaloriesRemaining(ed));
    }
}
