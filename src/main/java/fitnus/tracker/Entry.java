package fitnus.tracker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Food Tracker Entry.
 */
public class Entry {
    private final MealType mealType;
    private Food food;
    private final LocalDate date;

    /**
     * Constructor.
     *
     * @param mealType MealType of this Entry (e.g. dinner / lunch).
     * @param food     Food object of this Entry.
     */
    public Entry(MealType mealType, Food food) {
        this.mealType = mealType;
        this.food = food;
        this.date = LocalDate.now();
    }

    /**
     * Constructor.
     *
     * @param mealType MealType of this Entry (e.g. dinner / lunch).
     * @param food     Food object of this Entry.
     * @param date     Date of this Entry.
     */
    public Entry(MealType mealType, Food food, LocalDate date) {
        this.mealType = mealType;
        this.food = food;
        this.date = date;
        assert !date.isAfter(LocalDate.now()) : "date should not be later than today";

    }

    /**
     * Returns the Food object associated with this Entry.
     *
     * @return The Food object associated with this Entry.
     */
    public Food getFood() {
        return this.food;
    }

    /**
     * Sets the Food object of this Entry.
     *
     * @param food The Food object to set.
     */
    public void setFood(Food food) {
        this.food = food;
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

    /**
     * Returns the LocalDate of this Entry.
     *
     * @return The LocalDate of this Entry.
     */
    public LocalDate getRawDate() {
        return this.date;
    }

    /**
     * Returns the MealType of this Entry.
     *
     * @return The MealType of this Entry.
     */
    public MealType getMealType() {
        return this.mealType;
    }

    /**
     * Returns a String representation of the Entry object.
     *
     * @return String representation of the Entry object.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = this.date.format(formatter);
        return "[" + date + "] " + mealType + ": " + food.toString();
    }
}
