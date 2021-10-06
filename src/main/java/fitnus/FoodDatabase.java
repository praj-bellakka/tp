package fitnus;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class FoodDatabase {
    private final ArrayList<Food> databaseFoods = new ArrayList<>();
    private static final String DELIMITER = " | ";


    public void addFood(String name, Integer calories) {
        Food food = new Food(name, calories);
        databaseFoods.add(food);
    }

    public void listFoods() {
        for (int i = 1; i <= databaseFoods.size(); i++) {
            System.out.println(" " + i + "." + databaseFoods.get(i - 1));
        }
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

    public void preLoadDatabase(BufferedReader reader) throws IOException {
        int preloadFoodCount = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] description = line.trim().split("\\s*[|]\\s*");
            String name = description[0];
            Integer calories = Integer.parseInt(description[1]);
            this.addFood(name, calories);
            preloadFoodCount++;
        }
        System.out.println("Successfully preloaded " + preloadFoodCount + " foods");
    }

}
