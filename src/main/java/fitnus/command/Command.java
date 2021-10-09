package fitnus.command;

import fitnus.EntryDatabase;
import fitnus.FitNusException;
import fitnus.FoodDatabase;
import fitnus.User;

public abstract class Command {

    public abstract String execute(EntryDatabase ed, FoodDatabase fd, User us) throws FitNusException;
}
