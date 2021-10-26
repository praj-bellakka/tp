package fitnus.database;

import fitnus.FitNus;
import fitnus.exception.FitNusException;
import fitnus.parser.Parser;
import fitnus.tracker.Entry;
import fitnus.tracker.Food;
import fitnus.tracker.MealPlan;
import fitnus.tracker.MealType;
import fitnus.utility.Ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MealPlanDatabase {
    private final ArrayList<MealPlan> databaseMealPlans = new ArrayList<>();
    private final String MEALPLAN_DECODER = "--------";
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
            throw new FitNusException("Unable to add Meal plan as no food detected.");
        }
    }

    public String convertDatabaseToString() {
        StringBuilder lines = new StringBuilder();
        for (MealPlan plan : databaseMealPlans) {
            String mealPlanName = plan.getMealPlanName();
            lines.append(convertFoodToString(plan.getMealFoods()));
            lines.append(this.MEALPLAN_DECODER).append(DELIMITER).append(mealPlanName).append(System.lineSeparator());
        }
        return lines.toString();
    }

    private String convertFoodToString(ArrayList<Food> foodList) {
        StringBuilder lines = new StringBuilder();
        for (Food food: foodList) {
            String name = food.getName();
            Integer calories = food.getCalories();
            String type = food.getType().toString();
            lines.append(name).append(DELIMITER).append(calories)
                    .append(DELIMITER).append(type).append(System.lineSeparator());
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
                if (description[0].equals(this.MEALPLAN_DECODER)) {
                    mealPlanName = description[1];
                    this.addMealPlan(new MealPlan(mealPlanName, tempArray));
                    preloadMealPlanCount++;
                }
                String name = description[0].strip();
                String caloriesString = description[1].strip();
                assert caloriesString.equals("") == false : "calories field cannot only contain white spaces";
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
