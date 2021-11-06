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

    /**
     * Returns the MealPlan object at the index specified inside databaseMealPlans.
     * Throws FitNusException if index specified is either below 1 or outside the range of the database.
     *
     * @param index Integer specified.
     * @return MealPlan object at the index specified inside databaseMealPlans.
     * @throws FitNusException Thrown when no MealPlan is found at that index.
     */
    public MealPlan getMealAtIndex(int index) throws FitNusException {
        if (index > 0 && index <= databaseMealPlans.size()) {
            return databaseMealPlans.get(index - 1);
        } else {
            throw new FitNusException("Index specified is outside the range of the database! "
                    + "Meal plan could not be found...");
        }
    }

    /**
     * Adds MealPlan object into databaseMealPlans.
     *
     * @param plan MealPlan object to be added.
     * @throws FitNusException Thrown when MealPlan does not contain any Food objects.
     */
    public void addMealPlan(MealPlan plan) throws FitNusException {
        if (plan.getMealFoods().size() > 0) {
            databaseMealPlans.add(plan);
        } else {
            throw new FitNusException("Unable to add meal plan as no food detected.");
        }
    }

    /**
     * Returns String representation of all meal plan objects inside databaseMealPlans.
     * Each meal plan is listed by its name, followed by all its Food items associated with it.
     *
     * @return String representation of all MealPlan objects in database.
     */
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

    /**
     * Converts the database content to String form.
     *
     * @return The database content as String.
     */
    public String convertDatabaseToString() {
        StringBuilder lines = new StringBuilder();
        for (MealPlan plan : databaseMealPlans) {
            String mealPlanName = plan.getMealPlanName();
            lines.append(convertFoodToString(plan.getMealFoods()));
            lines.append(MEAL_PLAN_DECODER).append(DELIMITER).append(mealPlanName).append(System.lineSeparator());
        }
        return lines.toString();
    }

    /**
     * Converts an ArrayList of Food items to String.
     *
     * @return The Food ArrayList as String.
     */
    private String convertFoodToString(ArrayList<Food> foodList) {
        StringBuilder lines = new StringBuilder();
        for (Food food : foodList) {
            lines.append(food.convertToStringForStorage());
        }
        return lines.toString();
    }

    /**
     * Preloads meal plans from meal plan database.
     *
     * @param reader Reads from the file.
     * @throws IOException If an I/O error occurs.
     */
    public void preloadDatabase(BufferedReader reader) throws IOException {
        int preloadMealPlanCount = 0;
        String line;
        String mealPlanName = "";
        ArrayList<Food> tempArray = new ArrayList<>(); // holds temporary list of Food items per meal plan
        while ((line = reader.readLine()) != null) {
            String[] description = line.trim().split("\\s*[|]\\s*");
            try {
                if (description[0].equals(MEAL_PLAN_DECODER)) {
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
