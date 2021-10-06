package fitnus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Entry {
    Food food;
    LocalDate date;

    Entry(Food food) {
        this.food = food;
        this.date = LocalDate.now();
    }

    Food getFood() {
        return this.food;
    }

    LocalDate getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = this.date.format(formatter);
        return "[" + date + "] " + food.toString();
    }
}
