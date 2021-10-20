package fitnus.tracker;

import fitnus.tracker.Food;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Entry {
    private final MealType mealType;
    private final Food food;
    private final LocalDate date;

    public Entry(MealType mealType, Food food) {
        this.mealType = mealType;
        this.food = food;
        this.date = LocalDate.now();
    }

    public Entry(MealType mealType, Food food, LocalDate date) {
        this.mealType = mealType;
        this.food = food;
        this.date = date;
        assert !date.isAfter(LocalDate.now()) : "date should not be later than today";

    }

    public Food getFood() {
        return this.food;
    }

    /**
     * Gets the date and converts it to the specified format. Then,
     * returns the formatted date as a String.
     *
     * @return Formatted date as a String.
     */
    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    public LocalDate getRawDate() {
        return this.date;
    }

    public MealType getMealType() {
        return this.mealType;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = this.date.format(formatter);
        return "[" + date + "] " + mealType + ": " + food.toString();
    }
}
