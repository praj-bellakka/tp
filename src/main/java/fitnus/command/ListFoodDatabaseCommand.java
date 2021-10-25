package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.utility.User;

public class ListFoodDatabaseCommand extends Command {
    public ListFoodDatabaseCommand() {
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us) {
        return fd.listFoods();
    }
}
