package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.database.FoodDatabase;
import fitnus.utility.User;

public abstract class Command {

    public abstract String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us)
            throws FitNusException;
}
