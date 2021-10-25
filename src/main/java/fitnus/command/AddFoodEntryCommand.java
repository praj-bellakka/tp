package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Food;
import fitnus.tracker.MealType;
import fitnus.utility.User;

public class AddFoodEntryCommand extends Command {
    private final MealType mealType;
    private final String foodName;
    private final int calories;
    private final Food food;
    private final boolean isCustom;
    private Food.FoodType type = null;

    public AddFoodEntryCommand(MealType mealType, Food food) {
        this.mealType = mealType;
        this.food = food;
        this.foodName = null;
        this.calories = -1;
        this.isCustom = false;
    }

    public AddFoodEntryCommand(MealType mealType, String foodName, int calories, Food.FoodType type) {
        this.mealType = mealType;
        this.foodName = foodName;
        this.calories = calories;
        this.food = null;
        this.isCustom = true;
        this.type = type;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us) throws FitNusException {
        if (isCustom) {
            Food customFood = new Food(foodName, calories, type);
            ed.addEntry(mealType, customFood);
            fd.addFood(customFood);
            return "You have successfully added " + customFood;
        } else {
            ed.addEntry(mealType, food);
            return "You have successfully added " + food;
        }
    }
}
