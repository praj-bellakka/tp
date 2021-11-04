package fitnus.database;

import fitnus.exception.FitNusException;
import fitnus.parser.Parser;
import fitnus.tracker.Food;
import fitnus.tracker.MealPlan;
import fitnus.utility.Ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class MealPlanDatabase {
    private final ArrayList<MealPlan> databaseMealPlans = new ArrayList<>();
    private static final String MEAL_PLAN_DECODER = "--------";
    private static final String DELIMITER = " | ";

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
            throw new FitNusException("Unable to add meal plan as no food detected.");
        }
    }

    public String listMealPlan() {
        StringBuilder list = new StringBuilder();
        if (databaseMealPlans.size() == 0) {
            System.out.println("There are no meal plans in the database...");
            return "";
        }
        for (int i = 1; i <= databaseMealPlans.size(); i++) {
            MealPlan plan = this.databaseMealPlans.get(i - 1);
            String mealPlanName = plan.getMealPlanName();
            String foodString = plan.getFoodString();
            list.append(i).append(". Meal plan: ").append(mealPlanName)
                    .append(System.lineSeparator())
                    .append(foodString)
                    .append(System.lineSeparator());

        }
        return list.toString();
    }

    public String convertDatabaseToString() {
        StringBuilder lines = new StringBuilder();
        for (MealPlan plan : databaseMealPlans) {
            String mealPlanName = plan.getMealPlanName();
            lines.append(convertFoodToString(plan.getMealFoods()));
            lines.append(this.MEAL_PLAN_DECODER).append(DELIMITER).append(mealPlanName).append(System.lineSeparator());
        }
        return lines.toString();
    }

    private String convertFoodToString(ArrayList<Food> foodList) {
        StringBuilder lines = new StringBuilder();
        for (Food food: foodList) {
            lines.append(food.convertToStringForStorage());
        }
        return lines.toString();
    }

    public void preloadDatabase(BufferedReader reader) throws IOException {
        int preloadMealPlanCount = 0;
        String line;
        String mealPlanName = "";
        ArrayList<Food> tempArray = new ArrayList<>(); // holds temporary list of Food items per meal plan
        while ((line = reader.readLine()) != null) {
            String[] description = line.trim().split("\\s*[|]\\s*");
            try {
                if (description[0].equals(this.MEAL_PLAN_DECODER)) {
                    mealPlanName = description[1];
                    MealPlan tempMealPlan = new MealPlan(mealPlanName, tempArray);
                    this.addMealPlan(tempMealPlan);
                    preloadMealPlanCount++;
                    tempArray = new ArrayList<>(); //empty the temporary array for the next meal plan to add on
                    continue;
                }
                String name = description[0].strip();
                String caloriesString = description[1].strip();
                assert !caloriesString.equals("") : "calories field cannot only contain white spaces";
                Food.FoodType type = Parser.parseFoodType(description[2]);
                Integer calories = Integer.parseInt(caloriesString);
                tempArray.add(new Food(name, calories, type));

            } catch (IndexOutOfBoundsException | FitNusException e) {
                Ui.printPreloadDatabaseError();
            }
        }
        System.out.println("Successfully preloaded " + preloadMealPlanCount + " meal plans");
    }
}
