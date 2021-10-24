package fitnus.command;

import fitnus.database.MealPlanDatabase;
import fitnus.tracker.Entry;
import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.exception.FitNusException;
import fitnus.utility.User;

public class DeleteFoodEntryCommand extends Command {
    private final int index;

    public DeleteFoodEntryCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us) throws FitNusException {
        Entry deletedEntry = ed.getEntryAtIndex(index);
        ed.deleteEntry(index);
        return "You have successfully deleted " + deletedEntry.getFood();
    }
}
