package fitnus.database;

import fitnus.parser.Parser;
import fitnus.tracker.Food;
import fitnus.utility.Ui;
import fitnus.exception.FitNusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class FoodDatabase {
    private final ArrayList<Food> databaseFoods = new ArrayList<>();
    private static final String DELIMITER = " | ";

    public void addFood(String name, Integer calories, Food.FoodType type) throws FitNusException {
        if (calories <= 0) {
            throw new FitNusException("Food must have more than 0 calories!");
        }
        Food food = new Food(name, calories, type);
        databaseFoods.add(food);
    }

    public void addFood(Food food) throws FitNusException {
        assert food != null : "food should not be null";
        if (food.getCalories() <= 0) {
            throw new FitNusException("Food must have more than 0 calories!");
        }
        databaseFoods.add(food);
    }

    public void deleteFood(int index) throws FitNusException {
        try {
            this.databaseFoods.remove(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new FitNusException("Sorry the index chosen is invalid! Please try again!");
        }
    }

    // Index here starts from 1
    public Food getFoodAtIndex(int index) throws FitNusException {
        try {
            return databaseFoods.get(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new FitNusException("Sorry the index chosen is invalid! Please try again!");
        }
    }

    //returns ArrayList<Food> db when called.
    public ArrayList<Food> getFoodDatabase() {
        return databaseFoods;
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
            String type = food.getType().toString();
            lines.append(name).append(DELIMITER).append(calories)
                    .append(DELIMITER).append(type).append(System.lineSeparator());
        }
        return lines.toString();
    }

    public void preloadDatabase(BufferedReader reader) throws IOException, FitNusException {
        int preloadFoodCount = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] description = line.trim().split("\\s*[|]\\s*");
            //assert description.length == 4 : "description does not contain both name and calories";
            try {
                String name = description[0].strip();
                //assert name.equals("") == false : "name field cannot only contain white spaces";

                String caloriesString = description[1].strip();
                assert caloriesString.equals("") == false : "calories field cannot only contain white spaces";

                Food.FoodType type = Parser.parseFoodType(description[2]);

                Integer calories = Integer.parseInt(caloriesString);
                this.addFood(name, calories, type);
                preloadFoodCount++;
            } catch (IndexOutOfBoundsException e) {
                Ui.printPreloadDatabaseError();
            }
        }
        Ui.println("Successfully preloaded " + preloadFoodCount + " foods");
    }

    public ArrayList<Food> findFoods(String keyword) throws FitNusException {
        if (keyword.equals("")) {
            throw new FitNusException("Please provide a valid keyword");
        }
        return (ArrayList<Food>) databaseFoods.stream()
                .filter(t -> t.getName().contains(keyword))
                .collect(Collectors.toList());
    }

    public ArrayList<Food> findSuggestions(Food.FoodType type, int calories, boolean isSort) {
        ArrayList<Food> matchingSuggestions = (ArrayList<Food>) databaseFoods.stream()
                .filter(t -> t.getType().equals(type))
                .filter(c -> c.getCalories() < calories)
                .collect(Collectors.toList());
        if (isSort) {
            matchingSuggestions.sort(Comparator.comparing(Food::getCalories));
        }
        return matchingSuggestions;
    }
}
