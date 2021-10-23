package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.utility.User;

public class ListFoodIntakeCommand extends Command {

    public ListFoodIntakeCommand() {

    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        EntryDatabase dayEntry = ed.getPastDaysEntryDatabase(1);
        return dayEntry.listEntries();
    }
}


