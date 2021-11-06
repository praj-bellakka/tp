package fitnus.tracker;

import java.util.ArrayList;

public class MealPlan {
    private final String mealPlanName;
    private final ArrayList<Food> mealFoods;

    /**
     * Constructor.
     *
     * @param mealPlanName     Name of the meal plan object.
     * @param mealFoods ArrayList of Food objects.
     */
    public MealPlan(String mealPlanName, ArrayList<Food> mealFoods) {
        this.mealPlanName = mealPlanName;
        this.mealFoods = mealFoods;
    }

    public String getMealPlanName() {
        return this.mealPlanName;
    }

    public ArrayList<Food> getMealFoods() {
        return this.mealFoods;
    }

    /**
     * Returns a string representation of all Food objects inside mealFoods.
     * Returns empty string if mealFoods is empty.
     *
     * @return String representation of all Food objects inside mealFoods.
     */
    public String getFoodString() {
        String str = "";
        for (Food item : mealFoods) {
            str += item.toString() + System.lineSeparator();
        }
        return str;
    }

    /**
     * Returns String representation of MealPlan object, including all its food under it.
     *
     * @return String representation of MealPlan object
     */
    @Override
    public String toString() {
        for (Food item : mealFoods) {
            System.out.println(item.toString());
        }
        return this.mealPlanName;
    }

}
