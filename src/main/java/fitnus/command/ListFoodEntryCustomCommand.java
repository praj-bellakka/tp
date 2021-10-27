package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.utility.User;

public class ListFoodEntryCustomCommand extends Command {
    private final int days;

    public ListFoodEntryCustomCommand(int days) {
        this.days = days;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us) {
        EntryDatabase dayEntry = ed.getPastDaysEntryDatabase(days);
        return dayEntry.listEntries();
    }
}
