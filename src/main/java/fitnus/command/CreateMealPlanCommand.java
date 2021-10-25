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
    private final ArrayList<Food> mealFood;
    private final String mealPlanName;

    public CreateMealPlanCommand(ArrayList<Food> mealFood, String mealPlanName) {
        this.mealFood = mealFood;
        this.mealPlanName = mealPlanName;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us) throws FitNusException {
        MealPlan newMealPlan = new MealPlan(mealPlanName, mealFood);
        md.addMealPlan(newMealPlan);
        //        ed.addEntry(mealType, customFood);
        //        fd.addFood(customFood);
        newMealPlan.toString();
        return "You have successfully added the following Meal plan: " + newMealPlan.getMealPlanName();

    }
}
