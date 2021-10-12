package fitnus.tracker;

import fitnus.tracker.Food;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Entry {
    Food food;
    LocalDate date;

    public Entry(Food food) {
        this.food = food;
        this.date = LocalDate.now();
    }

    public Entry(Food food, LocalDate date) {
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

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = this.date.format(formatter);
        return "[" + date + "] " + food.toString();
    }

}
