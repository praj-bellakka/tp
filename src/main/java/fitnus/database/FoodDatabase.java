package fitnus.database;

import fitnus.parser.Parser;
import fitnus.tracker.Food;
import fitnus.utility.Ui;
import fitnus.exception.FitNusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Keeps a record of Food objects and handles functionalities related to Food objects.
 */
public class FoodDatabase {
    private final ArrayList<Food> databaseFoods = new ArrayList<>();

    private static final String UTOWN_FOOD_LIST = "finefood - bbq beef set | 504 | MEAL\n"
            + "finefood - bbq chicken set | 510 | MEAL\n"
            + "finefood - bibimbap | 400 | MEAL\n"
            + "finefood - black pepper beef bibimbap | 405 | MEAL\n"
            + "finefood - kimchi shin ramen | 280 | MEAL\n"
            + "finefood - beef veg soup set | 237 | MEAL\n"
            + "finefood - chicken cutlet + fried ebi bento | 243 | MEAL\n"
            + "finefood - salmon + chicken cutlet bento | 515 | MEAL\n"
            + "finefood - lemon tea | 90 | BEVERAGE\n"
            + "finefood - green tea | 120 | BEVERAGE\n"
            + "finefood - oolong tea | 5 | BEVERAGE\n"
            + "finefood - milk foam oolong tea | 200 | BEVERAGE\n"
            + "finefood - honey green tea | 70 | BEVERAGE\n"
            + "finefood - gold grill combo | 605 | MEAL\n"
            + "finefood - honey chicken chop rice | 510 | MEAL\n"
            + "finefood - x-large chicken chop rice | 579 | MEAL\n"
            + "finefood - salted crispy chicken rice | 550 | MEAL\n"
            + "finefood - sesame seed chicken rice | 509 | MEAL\n"
            + "finefood - taiwanese sweet & sour chicken rice | 341 | MEAL\n"
            + "finefood - teriyaki chicken rice | 430 | MEAL\n"
            + "finefood - taiwanese braised pork rice | 434 | MEAL\n"
            + "finefood - charcoal roasted duck rice set meal | 673 | MEAL\n"
            + "finefood - crispy roasted chicken rice/noodle | 618 | MEAL\n"
            + "finefood - rose soy sauce chicken rice/noodle | 605 | MEAL\n"
            + "finefood - char siew rice/noodle | 412 | MEAL\n"
            + "finefood - duck drumstick rice/noodle | 420 | MEAL\n"
            + "finefood - chicken drumstick rice/noodle | 415 | MEAL\n"
            + "finefood - happy combo noodle (dry/soup) | 450 | MEAL\n"
            + "finefood - signature minipot noodle (dry/soup) | 460 | MEAL\n"
            + "finefood - original bak chor mee (dry/soup) | 412 | MEAL\n"
            + "finefood - teochew fish ball noodle (dry/soup) | 390 | MEAL\n"
            + "finefood - fried wanton noodle (dry/soup) | 395 | MEAL\n"
            + "finefood - handmade shrimp dumpling noodle (dry/sooup) | 414 | MEAL\n"
            + "finefood - sweet & sour chicken cube rice | 462 | MEAL\n"
            + "finefood - marmite chicken cube rice | 470 | MEAL\n"
            + "finefood - kung pao chicken rice | 469 | MEAL\n"
            + "finefood - ginger & onion sliced chicken cube rice | 490 | MEAL\n"
            + "finefood - beef fried rice | 505 | MEAL\n"
            + "finefood - seafood fried rice | 495 | MEAL\n"
            + "finefood - kampung fried rice | 480 | MEAL\n"
            + "finefood - 2 vegetables + 1 meat + rice | 380 | MEAL\n"
            + "finefood - signature fish bee hoon | 349 | MEAL\n"
            + "finefood - seafood clam five grains bee hoon | 297 | MEAL\n"
            + "finefood - original fish bee hoon | 300 | MEAL\n"
            + "finefood - sour and spicy fish bee hoon | 349 | MEAL\n"
            + "finefood - golden soup fish bee hoon | 349 | MEAL\n"
            + "finefood - mushroom with chicken rice | 380 | MEAL\n"
            + "finefood - minced pork with salted egg rice | 380 | MEAL\n"
            + "finefood - minced pork with pickled mustard rice | 395 | MEAL\n"
            + "finefood - minced meat peanut porridge | 349 | MEAL\n"
            + "finefood - minced meat century egg porridge | 349 | MEAL\n"
            + "food clique - chicken chop rice | 600 | MEAL\n"
            + "food clique - pork chop fried rice with fish roe | 450 | MEAL\n"
            + "food clique - shrimp fried rice with fish roe | 341 | MEAL\n"
            + "food clique - vinegar beef noodle | 406 | MEAL\n"
            + "food clique - chilli oil chive dumpling (10 pcs) | 450 | SNACK\n"
            + "waa cow - original wagyu beef | 489 | MEAL\n"
            + "waa cow - mentaiko wagyu beef | 521 | MEAL\n"
            + "waa cow - truffle wagyu beef | 529 | MEAL\n"
            + "waa cow - original chirashi | 400 | MEAL\n"
            + "waa cow - mentaiko salmon | 432 | MEAL\n"
            + "waa cow - truffle fries | 300 | SNACK\n"
            + "hwang's - dak bulgogi | 311 | MEAL\n"
            + "hwang's - bibimbap | 490 | MEAL\n"
            + "hwang's - doeji bulgogi | 330 | MEAL\n"
            + "hwang's - gulgogi | 395 | MEAL\n"
            + "hwang's - kimchi jigae | 316 | MEAL\n"
            + "hwang's - la myun | 470 | MEAL\n";

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
        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= databaseFoods.size(); i++) {
            result.append(String.format(" %d.%s", i, databaseFoods.get(i - 1)
                    + System.lineSeparator()));
        }
        return result.toString();
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
            boolean hasLoadedSuccessfully = loadFood(line);
            if (hasLoadedSuccessfully) {
                preloadFoodCount++;
            }
        }
        if (this.databaseFoods.size() == 0) {
            Reader inputString = new StringReader(UTOWN_FOOD_LIST);
            BufferedReader newReader = new BufferedReader(inputString);
            while ((line = newReader.readLine()) != null) {
                boolean hasLoadedSuccessfully = loadFood(line);
                if (hasLoadedSuccessfully) {
                    preloadFoodCount++;
                }
            }
        }
        Ui.println("Preloaded " + preloadFoodCount + " foods");
    }

    /**
     * Preloads a food into the database.
     *
     * @param line A line of food information to be processed and added.
     * @return True if successful and false if unsuccessful.
     * @throws FitNusException If unable to parse Food type.
     */
    public boolean loadFood(String line) throws FitNusException {
        String[] description = line.trim().split("\\s*[|]\\s*");
        try {
            String name = description[0].strip();
            assert !name.equals("") : "name field cannot only contain white spaces";

            String caloriesString = description[1].strip();
            assert !caloriesString.equals("") : "calories field cannot only contain white spaces";

            Food.FoodType type = Parser.parseFoodType(description[2]);

            Integer calories = Integer.parseInt(caloriesString);
            this.addFood(name, calories, type);
            return true; //success
        } catch (IndexOutOfBoundsException e) {
            Ui.printPreloadDatabaseError();
            return false; //failure
        }
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
        String keywordSearch = keyword.trim();
        if (keywordSearch.equals("")) {
            throw new FitNusException("Please provide a valid keyword");
        }
        return (ArrayList<Food>) databaseFoods.stream()
                .filter(t -> t.getName().contains(keywordSearch))
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
