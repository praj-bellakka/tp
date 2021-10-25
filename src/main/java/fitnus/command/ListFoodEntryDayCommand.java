package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.utility.User;

public class ListFoodEntryDayCommand extends Command {

    public ListFoodEntryDayCommand() {

    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us) {
        EntryDatabase dayEntry = ed.getPastDaysEntryDatabase(1);
        return dayEntry.listEntries();
    }
}
