package fitnus.command;

import fitnus.EntryDatabase;
import fitnus.FoodDatabase;
import fitnus.User;

public class ExitCommand extends Command {

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        return "Thanks for using FitNUS! See you next time";
    }
}
