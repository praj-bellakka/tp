package fitnus;

public class Food {
    private final String name;
    private final Integer calories;

    /**
     * Constructor.
     *
     * @param name     Name of food.
     * @param calories Calorie value of food.
     */
    public Food(String name, Integer calories) {

        this.name = name;
        this.calories = calories;
    }

    /**
     * Returns a String representation of the food object
     * including its name and calorie value.
     *
     * @return String representation of food object.
     */
    @Override
    public String toString() {
        return this.name + " " + this.calories.toString();
    }
}
