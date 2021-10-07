package fitnus.command;

import fitnus.EntryDatabase;
import fitnus.User;

public class ViewRemainingCalorieCommand extends Command{
    private User user;
    private EntryDatabase edb;

    public ViewRemainingCalorieCommand(User user, EntryDatabase edb) {
        this.user = user;
        this.edb = edb;
    }

    @Override
    public String execute() {
        return String.format("The remaining calories before reaching the daily goal is %d",
                user.showCaloriesRemaining(edb));
    }

}
