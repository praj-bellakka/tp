package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.exception.FitNusException;
import fitnus.database.FoodDatabase;
import fitnus.utility.User;

public class AddDefaultFoodEntryCommand extends Command {
    private final int index;

    public AddDefaultFoodEntryCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) throws FitNusException {
        ed.addDefaultEntry(fd, index);
        return "You have successfully added " + fd.getFoodAtIndex(index);
    }
}
