package fitnus;

import java.time.LocalDate;

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
}
