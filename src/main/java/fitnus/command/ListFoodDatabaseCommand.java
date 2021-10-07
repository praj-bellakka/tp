package fitnus.command;

import fitnus.EntryDatabase;
import fitnus.FoodDatabase;
import fitnus.User;

public class ListFoodDatabaseCommand extends Command {

    private FoodDatabase foodDatabase;

    public ListFoodDatabaseCommand(FoodDatabase foodDatabase) {
        this.foodDatabase = foodDatabase;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        return foodDatabase.listFoods();
    }
}
