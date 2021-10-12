package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.utility.User;

public class ExitCommand extends Command {

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        return "Thanks for using FitNUS! See you next time";
    }
}
