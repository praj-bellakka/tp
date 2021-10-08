package fitnus.command;

import fitnus.EntryDatabase;
import fitnus.FoodDatabase;
import fitnus.User;

public class InvalidCommand extends Command {

    public InvalidCommand() {
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        return "invalid command";
    }
}
