package fitnus.tracker;

import java.time.LocalDate;

public class WeightProgressEntry {

    private float weight;
    private LocalDate date;

    public WeightProgressEntry(float weight, LocalDate date) {
        this.weight = weight;
        this.date = date;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }
}
