package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.utility.User;

public class ListMealPlanDatabaseCommand extends Command{
    public ListMealPlanDatabaseCommand() {
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us) {
        return md.listMealPlan();
    }

}
