package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Food;
import fitnus.utility.User;

public class DeleteFoodCommand extends Command {
    private final int index;

    public DeleteFoodCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) throws FitNusException {
        Food deletedFood = fd.getFoodAtIndex(index);
        fd.deleteFood(index);
        return "You have successfully deleted " + deletedFood;
    }
}
