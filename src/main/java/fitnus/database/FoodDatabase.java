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

    /**
     * Adds a Food object to the ArrayList databaseFoods.
     *
     * @param name     Name of the Food object to be added.
     * @param calories Calorie value of the Food object to be added.
     * @param type     Food type of the Food object to be added.
     * @throws FitNusException If the calorie value provided is <= 0.
     */
    public void addFood(String name, Integer calories, Food.FoodType type) throws FitNusException {
        if (calories < 0) {
            throw new FitNusException("Food must have more than 0 calories!");
        }
        Food food = new Food(name, calories, type);
        databaseFoods.add(food);
    }

    /**
     * Adds a Food object to the ArrayList databaseFoods.
     *
     * @param food The Food object to be added.
     * @throws FitNusException If the calorie value of the Food object to be added <= 0.
     */
    public void addFood(Food food) throws FitNusException {
        assert food != null : "food should not be null";
        if (food.getCalories() < 0) {
            throw new FitNusException("Food must have more than 0 calories!");
        }
        databaseFoods.add(food);
    }

    /**
     * Removes a specified Food object from the ArrayList databaseFoods.
     *
     * @param index Index of the Food object to be removed.
     * @throws FitNusException If the index provided is invalid.
     */
    public void deleteFood(int index) throws FitNusException {
        try {
            this.databaseFoods.remove(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new FitNusException("Sorry the index chosen is invalid! Please try again!");
        }
    }

    /**
     * Returns the Food object at the specified index.
     *
     * @param index Index of the Food object.
     * @return The Food object at the specified index.
     * @throws FitNusException If the index provided is invalid.
     */
    public Food getFoodAtIndex(int index) throws FitNusException {
        try {
            return databaseFoods.get(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new FitNusException("Sorry the index chosen is invalid! Please try again!");
        }
    }

    /**
     * Returns the whole databaseFoods ArrayList.
     *
     * @return The databaseFoods ArrayList.
     */
    public ArrayList<Food> getFoodDatabase() {
        return databaseFoods;
    }

    /**
     * Returns the database content as a formatted String (in list form).
     *
     * @return String representation of the database content.
     */
    public String listFoods() {
        if (databaseFoods.size() < 1) {
            return "Oops, there are no records found!";
        }
        String result = "";
        for (int i = 1; i <= databaseFoods.size(); i++) {
            result += String.format(" %d.%s", i, databaseFoods.get(i - 1)
                    + System.lineSeparator());
        }
        return result;
    }

    /**
     * Converts the database content to String form for storage.
     *
     * @return The database content as String.
     */
    public String convertDatabaseToString() {
        StringBuilder lines = new StringBuilder();
        for (Food food : databaseFoods) {
            lines.append(food.convertToStringForStorage());
        }
        return lines.toString();
    }

    /**
     * Preloads the database.
     *
     * @param reader Reads from the file.
     * @throws IOException     If an I/O error occurs.
     * @throws FitNusException If unable to parse Food type.
     */
    public void preloadDatabase(BufferedReader reader) throws IOException, FitNusException {
        int preloadFoodCount = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] description = line.trim().split("\\s*[|]\\s*");
            //assert description.length == 4 : "description does not contain both name and calories";
            try {
                String name = description[0].strip();
                assert !name.equals("") : "name field cannot only contain white spaces";

                String caloriesString = description[1].strip();
                assert !caloriesString.equals("") : "calories field cannot only contain white spaces";

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

    /**
     * Filters Food objects based on the provided keyword and returns
     * matching Food objects in an ArrayList.
     *
     * @param keyword The keyword used to filter Food objects.
     * @return An ArrayList containing matching Food objects.
     * @throws FitNusException If the keyword provided is an empty String.
     */
    public ArrayList<Food> findFoods(String keyword) throws FitNusException {
        if (keyword.equals("")) {
            throw new FitNusException("Please provide a valid keyword");
        }
        return (ArrayList<Food>) databaseFoods.stream()
                .filter(t -> t.getName().contains(keyword))
                .collect(Collectors.toList());
    }

    /**
     * Finds matching Food objects (suggestions) based on the specified Food type and
     * calories. Then returns an ArrayList containing suggestions.
     *
     * @param type     The Food type specified by the user.
     * @param calories The calorie value used to filter Food objects.
     * @param isSort   A boolean indicating whether to sort the resultant ArrayList.
     * @return An ArrayList containing suggestions.
     */
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
