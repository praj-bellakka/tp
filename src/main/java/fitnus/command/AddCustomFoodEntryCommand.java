package fitnus.command;


import fitnus.database.EntryDatabase;
import fitnus.tracker.Food;
import fitnus.database.FoodDatabase;
import fitnus.utility.User;
import fitnus.exception.FitNusException;

public class AddCustomFoodEntryCommand extends Command {
    private final String foodName;
    private final int calories;

    public AddCustomFoodEntryCommand(String foodName, int calories) {
        this.foodName = foodName;
        this.calories = calories;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) throws FitNusException {
        Food customFood = new Food(foodName, calories);
        ed.addEntry(customFood);
        fd.addFood(customFood);
        return "You have successfully added " + customFood;
    }
}
