package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.utility.User;

public class ListFoodEntryWeekCommand extends Command {

    public static final int DAYS_IN_WEEK = 7;

    public ListFoodEntryWeekCommand() {

    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us) {
        EntryDatabase weekEntry = ed.getPastDaysEntryDatabase(DAYS_IN_WEEK);
        return weekEntry.listEntries();
    }
}
