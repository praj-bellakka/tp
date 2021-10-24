package fitnus.tracker;

import java.util.ArrayList;

public class MealPlan {
    private String mealPlanName;
    private ArrayList<Food> mealFoods;

    public MealPlan(String mealPlanName, ArrayList<Food> mealFoods) {
        this.mealPlanName = mealPlanName;
        this.mealFoods = mealFoods;
    }

    public String getMealPlanName() {
        return mealPlanName;
    }

    public ArrayList<Food> getMealFoods() {
        return mealFoods;
    }

}
