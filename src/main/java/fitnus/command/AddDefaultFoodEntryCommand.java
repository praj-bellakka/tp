package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.exception.FitNusException;
import fitnus.database.FoodDatabase;
import fitnus.tracker.MealType;
import fitnus.utility.User;

public class AddDefaultFoodEntryCommand extends Command {
    private final MealType mealType;
    private final int index;

    public AddDefaultFoodEntryCommand(MealType mealType, int index) {
        this.mealType = mealType;
        this.index = index;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) throws FitNusException {
        ed.addDefaultEntry(mealType, fd, index);
        return "You have successfully added " + fd.getFoodAtIndex(index);
    }
}
