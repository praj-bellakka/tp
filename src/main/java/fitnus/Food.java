package fitnus;

public class Food {
    private String name;
    private int calories;

    public Food(String name, int calories) {
        this.name = name;
        this.calories = calories;
    }

    public String getFoodName() {
        return this.name;
    }

    public int getFoodCalories() {
        return this.calories;
    }
}
