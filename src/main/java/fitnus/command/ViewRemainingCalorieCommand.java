package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.utility.User;

public class ViewRemainingCalorieCommand extends Command {
    public ViewRemainingCalorieCommand() {
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        return String.format("The remaining calories before reaching the daily goal is %d",
                us.showCaloriesRemaining(ed));
    }
}
