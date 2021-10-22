package fitnus.tracker;

public class Food {
    private final String name;
    private final Integer calories;
    private final FoodType type;

    public enum FoodType {
        SNACK,
        MEAL,
        BEVERAGE,
        OTHERS
    }

    /**
     * Constructor.
     *
     * @param name     Name of food.
     * @param calories Calorie value of food.
     */
    public Food(String name, Integer calories, FoodType type) {
        this.name = name;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public Integer getCalories() {
        return this.calories;
    }

    /**
     * Returns a String representation of the food object
     * including its name and calorie value.
     *
     * @return String representation of food object.
     */
    @Override
    public String toString() {
        assert calories > 0 : "calorie of food should not be less than or equal to 0";
        return this.name + " (" + this.calories + " Kcal)" + "Category: " + type.toString();
    }

    public FoodType getType() {
        return this.type;
    }

    public String convertToStringForStorage() {
        assert calories > 0 : "calorie of food should not be less than or equal to 0";
        return String.format("%s | %s", this.name, this.calories);
    }
}
