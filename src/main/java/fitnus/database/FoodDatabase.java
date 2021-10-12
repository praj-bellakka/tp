package fitnus.database;

import fitnus.tracker.Food;
import fitnus.utility.Ui;
import fitnus.exception.FitNusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class FoodDatabase {
    private final ArrayList<Food> databaseFoods = new ArrayList<>();
    private static final String DELIMITER = " | ";

    public void addFood(String name, Integer calories) throws FitNusException {
        if (calories <= 0) {
            throw new FitNusException("Food must have more than 0 calories!");
        }
        Food food = new Food(name, calories);
        databaseFoods.add(food);
    }

    public void addFood(Food food) throws FitNusException {
        assert food != null : "food should not be null";
        if (food.getCalories() <= 0) {
            throw new FitNusException("Food must have more than 0 calories!");
        }
        databaseFoods.add(food);
    }

    // Index here starts from 1
    public Food getFoodAtIndex(int index) throws IndexOutOfBoundsException {
        return databaseFoods.get(index - 1);
    }

    public String listFoods() {
        String result = "";
        for (int i = 1; i <= databaseFoods.size(); i++) {
            result += String.format(" %d.%s", i, databaseFoods.get(i - 1)
                    + System.lineSeparator());
        }
        return result;
    }

    public String convertDatabaseToString() {
        StringBuilder lines = new StringBuilder();
        for (Food food : databaseFoods) {
            String name = food.getName();
            Integer calories = food.getCalories();
            lines.append(name).append(DELIMITER).append(calories)
                    .append(System.lineSeparator());
        }
        return lines.toString();
    }

    public void preLoadDatabase(BufferedReader reader) throws IOException, FitNusException {
        int preloadFoodCount = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] description = line.trim().split("\\s*[|]\\s*");
            //assert description.length == 3 : "description does not contain both name and calories";
            try {
                String name = description[0];
                Integer calories = Integer.parseInt(description[1]);
                //assert name.equals("") == false : "name field cannot only contain white spaces";
                //assert calories.equals("") == false : "calories field cannot only contain white spaces";
                this.addFood(name, calories);
                preloadFoodCount++;
            } catch (IndexOutOfBoundsException e) {
                Ui.printPreloadDatabaseError();
            }
        }
        System.out.println("Successfully preloaded " + preloadFoodCount + " foods");
    }
}
