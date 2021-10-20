package fitnus.tracker;

import java.time.LocalDate;

public class WeightProgressEntry {

    private Integer weight;
    private LocalDate date;

    public WeightProgressEntry(Integer weight, LocalDate date) {
        this.weight = weight;
        this.date = date;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }
}
