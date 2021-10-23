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
    private final Food.FoodType type;


    public EditFoodEntryCommand(int index, Food food, Food.FoodType type) {
        this.index = index;
        this.food = food;
        this.foodName = null;
        this.calories = -1;
        this.isCustom = false;
        this.type = type;
    }

    public EditFoodEntryCommand(int index, String foodName, int calories, Food.FoodType type) {
        this.index = index;
        this.foodName = foodName;
        this.calories = calories;
        this.food = null;
        this.isCustom = true;
        this.type = type;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) throws FitNusException {
        if (isCustom) {
            Food customFood = new Food(foodName, calories, type);
            ed.editEntryAtIndex(index, customFood);
            fd.addFood(customFood);
            return "Entry successfully edited to " + customFood;
        } else {
            ed.editEntryAtIndex(index, food);
            return "Entry successfully edited to " + food;
        }
    }
}
