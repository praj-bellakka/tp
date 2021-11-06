package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.utility.User;

public class ListWeightProgressCommand extends Command {
    int month;

    public ListWeightProgressCommand(int month) {
        this.month = month;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us) throws FitNusException {
        return us.getWeightTrackerDisplay(month);
    }
}
