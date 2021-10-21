package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Food;
import fitnus.utility.User;

public class EditFoodEntryCommand extends Command {
    private final int index;
    private final String foodName;
    private final int calories;
    private final Food food;
    private final boolean isCustom;

    public EditFoodEntryCommand(int index, Food food) {
        this.index = index;
        this.food = food;
        this.foodName = null;
        this.calories = -1;
        this.isCustom = false;
    }

    public EditFoodEntryCommand(int index, String foodName, int calories) {
        this.index = index;
        this.foodName = foodName;
        this.calories = calories;
        this.food = null;
        this.isCustom = true;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) throws FitNusException {
        if (isCustom) {
            Food customFood = new Food(foodName, calories);
            ed.editEntryAtIndex(index, customFood);
            fd.addFood(customFood);
            return "Entry successfully edited to " + customFood;
        } else {
            ed.editEntryAtIndex(index, food);
            return "Entry successfully edited to " + food;
        }
    }
}
