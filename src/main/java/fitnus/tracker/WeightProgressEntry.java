package fitnus.tracker;

import java.time.LocalDate;

public class WeightProgressEntry {

    private Integer weight;
    private LocalDate date;

    public WeightProgressEntry(Integer weight, LocalDate date) {
        this.weight = weight;
        this.date = date;
    }
}
