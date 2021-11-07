package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Food;
import fitnus.tracker.MealPlan;
import fitnus.utility.User;

import java.util.ArrayList;

public class CreateMealPlanCommand extends Command {
    private final ArrayList<Food> mealFoods;
    private final String mealPlanName;

    public CreateMealPlanCommand(ArrayList<Food> mealFoods, String mealPlanName) {
        this.mealFoods = mealFoods;
        this.mealPlanName = mealPlanName;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us) throws FitNusException {
        MealPlan newMealPlan = new MealPlan(mealPlanName, mealFoods);
        System.out.println("Adding items to meal plan...");
        md.addMealPlan(newMealPlan);
        newMealPlan.toString();
        return "You have successfully added the following Meal plan: " + newMealPlan.getMealPlanName();

    }
}
