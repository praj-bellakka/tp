package fitnus.database;

import fitnus.FitNus;
import fitnus.exception.FitNusException;
import fitnus.tracker.Food;
import fitnus.tracker.MealPlan;

import java.util.ArrayList;

public class MealPlanDatabase {
    private final ArrayList<MealPlan> databaseMealPlans = new ArrayList<>();

    public MealPlan getMealAtIndex(int index) throws FitNusException {
        if (index > 0 && index <= databaseMealPlans.size()) {
            return databaseMealPlans.get(index - 1);
        } else {
            throw new FitNusException("Index specified is outside the range of the database! "
                    + "Meal plan could not be found...");
        }
    }

    public void addMealPlan(MealPlan plan) throws FitNusException {
        if (plan.getMealFoods().size() > 0) {
            databaseMealPlans.add(plan);
        } else {
            throw new FitNusException("Unable to add Meal plan as no food detected.");
        }
    }

}
