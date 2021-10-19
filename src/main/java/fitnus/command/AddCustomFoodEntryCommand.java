package fitnus.command;


import fitnus.database.EntryDatabase;
import fitnus.tracker.Food;
import fitnus.database.FoodDatabase;
import fitnus.tracker.MealType;
import fitnus.utility.User;
import fitnus.exception.FitNusException;

public class AddCustomFoodEntryCommand extends Command {
    private final MealType mealType;
    private final String foodName;
    private final int calories;

    public AddCustomFoodEntryCommand(MealType mealType, String foodName, int calories) {
        this.mealType = mealType;
        this.foodName = foodName;
        this.calories = calories;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) throws FitNusException {
        Food customFood = new Food(foodName, calories);
        ed.addEntry(mealType, customFood);
        fd.addFood(customFood);
        return "You have successfully added " + customFood;
    }
}
