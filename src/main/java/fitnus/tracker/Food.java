package fitnus.tracker;

public class Food {
    private final String name;
    private final Integer calories;
    private final FoodType type;
    private static final String DELIMITER = " | ";

    public enum FoodType {
        SNACK,
        MEAL,
        BEVERAGE,
        OTHERS
    }

    /**
     * Constructor.
     *
     * @param name     Name of the Food object.
     * @param calories Calorie value of the Food object.
     */
    public Food(String name, Integer calories, FoodType type) {
        this.name = name;
        this.calories = calories;
        this.type = type;
    }

    /**
     * Returns the name of the Food object.
     *
     * @return The name of the Food object.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the calorie value of the Food object.
     *
     * @return The calorie value of the Food object.
     */
    public Integer getCalories() {
        return this.calories;
    }

    /**
     * Returns a String representation of the Food object.
     *
     * @return String representation of the Food object.
     */
    @Override
    public String toString() {
        assert calories > 0 : "calorie of food should not be less than or equal to 0";
        return this.name + " (" + this.calories + " Kcal)" + " Type: " + type.toString();
    }

    /**
     * Returns the FoodType of this Food object.
     *
     * @return The FoodType of this Food object.
     */
    public FoodType getType() {
        return this.type;
    }

    /**
     * Converts the Food object to String form for Storage.
     *
     * @return String form of the Food object for Storage.
     */
    public String convertToStringForStorage() {
        StringBuilder lines = new StringBuilder();
        assert calories > 0 : "calorie of food should not be less than or equal to 0";
        String name = this.getName();
        Integer calories = this.getCalories();
        String type = this.getType().toString();
        lines.append(name).append(DELIMITER).append(calories)
                .append(DELIMITER).append(type).append(System.lineSeparator());
        return lines.toString();
    }
}
