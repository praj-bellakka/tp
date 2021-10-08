package fitnus.command;

import fitnus.EntryDatabase;
import fitnus.FoodDatabase;
import fitnus.User;

public class ViewRemainingCalorieCommand extends Command {
    private User user;
    private EntryDatabase edb;

    public ViewRemainingCalorieCommand(User user, EntryDatabase edb) {
        this.user = user;
        this.edb = edb;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        return String.format("The remaining calories before reaching the daily goal is %d",
                user.showCaloriesRemaining(edb));
    }

}
