package fitnus.tracker;

import fitnus.tracker.Food;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Entry {
    Food food;
    LocalDate date;
    private static Logger entryLogger = Logger.getLogger("entry");

    public Entry(Food food) {
        this.food = food;
        this.date = LocalDate.now();
    }

    public Entry(Food food, LocalDate date) {
        this.food = food;
        this.date = date;
        entryLogger.log(Level.INFO, "date should not be later than today");
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
