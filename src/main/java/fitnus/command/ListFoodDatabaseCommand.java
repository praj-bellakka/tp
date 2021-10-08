package fitnus.command;

import fitnus.EntryDatabase;
import fitnus.FoodDatabase;
import fitnus.User;

public class ListFoodDatabaseCommand extends Command {
    public ListFoodDatabaseCommand() {
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        return fd.listFoods();
    }
}
