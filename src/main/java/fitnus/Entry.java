package fitnus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Entry {
    Food food;
    LocalDateTime dateTime;

    public Entry(Food food) {
        this.food = food;
        this.dateTime = LocalDateTime.now();
    }

    public Entry(Food food, LocalDateTime dateTime) {
        this.food = food;
        this.dateTime = dateTime;
    }

    public Food getFood() {
        return this.food;
    }

    /**
     * Gets the dateTime and converts it to the specified format. Then,
     * returns the formatted dateTime as a String.
     *
     * @return Formatted dateTime as a String, representing the date and time.
     */
    protected String getDateAndTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
        return dateTime.format(formatter);
    }

    public String convertToStringForStorage() {
        return String.format("%s | %s", this.food.convertToStringForStorage(), this.getDateAndTime());
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
        String date = this.dateTime.format(formatter);
        return "[" + date + "] " + food.toString();
    }

}
