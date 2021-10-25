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

    /**
     * Returns String representation of MealPlan object, including all its food under it.
     * @return String representation of MealPlan object
     */
    @Override
    public String toString() {
        System.out.println("Adding the following items from your Meal plan, " + mealPlanName);
        for (Food item: mealFoods) {
            System.out.println(item.toString());
        }
        return this.mealPlanName + "has been added";
    }

}
