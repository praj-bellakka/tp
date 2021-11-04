package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Entry;
import fitnus.tracker.Food;
import fitnus.tracker.MealPlan;
import fitnus.tracker.MealType;
import fitnus.utility.User;

import java.util.ArrayList;

/**
 * Command is for adding entries based on a meal plan.
 */
public class AddMealPlanEntryCommand extends Command {
    private final MealPlan mealPlan;
    private final MealType mealType;

    public AddMealPlanEntryCommand(MealPlan mealPlan, MealType mealType) {
        this.mealPlan = mealPlan;
        this.mealType = mealType;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us) throws FitNusException {
        System.out.println("Adding items from the meal plan: \"" + mealPlan.getMealPlanName() + "\"...");
        ArrayList<Food> allFoodItems = mealPlan.getMealFoods();
        for (Food food: allFoodItems) {
            ed.addEntry(new Entry(mealType,food));
        }
        return "The following meal plan: \"" + mealPlan + "\" has been added.";

    }
}
