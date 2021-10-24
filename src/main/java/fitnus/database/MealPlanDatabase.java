package fitnus.database;

import fitnus.exception.FitNusException;
import fitnus.tracker.Food;
import fitnus.tracker.MealPlan;

import java.util.ArrayList;

public class MealPlanDatabase {
    private final ArrayList<MealPlan> databaseMealPlans = new ArrayList<>();

    public void addMealPlan(MealPlan plan) throws FitNusException {
//        if (calories <= 0) {
//            throw new FitNusException("Food must have more than 0 calories!");
//        }
//        Food food = new Food(name, calories, type);
        databaseMealPlans.add(plan);
    }
}
