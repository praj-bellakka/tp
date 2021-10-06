package fitnus;

import java.time.LocalDate;

public class Entry {
    Food food;
    LocalDate date;

    public Entry(Food food) {
        this.food = food;
        this.date = LocalDate.now();
    }

    public Food getFood() {
        return this.food;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public String convertToStringForStorage() {
        return String.format("%s | %s", this.food.convertToStringForStorage(), this.date.toString());
    }



}
