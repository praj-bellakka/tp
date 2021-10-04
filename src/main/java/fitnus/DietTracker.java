package fitnus;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DietTracker {
    private List<Pair<Food, LocalDateTime>> foodConsumedList;

    DietTracker() {
        this.foodConsumedList = new ArrayList<>();
    }

    public void addConsumedFood(Food food, LocalDateTime time) {
        this.foodConsumedList.add(new Pair<>(food, time));
    }

    public void addConsumedFood(Food food) {
        this.foodConsumedList.add(new Pair<>(food, LocalDateTime.now()));
    }

    public String getAllConsumedFoodInfo() {
        return this.toString();
    }

    public void removeConsumedFood(Object obj) {
        for (Pair i : foodConsumedList) {
            if (i.matchOneElement(obj)) {
                this.foodConsumedList.remove(i);
            }
        }
    }

    public void removeConsumedFood(Food food, LocalDateTime time) {
        Pair<Food, LocalDateTime> target = new Pair<>(food, time);
        for (Pair i : foodConsumedList) {
            if (i.equals(target)) {
                this.foodConsumedList.remove(i);
            }
        }
    }


    @Override
    public String toString() {
        String result = "";
        for (Pair i : foodConsumedList) {
            result += i.toString() + "\n";
        }
        return result;
    }
}


class Pair<T, S> {
    private T first;
    private S second;

    Pair(T first, S second) {
        this.first = first;
        this.second = second;
    }

    T getFirst() {
        return this.first;
    }

    S getSecond() {
        return this.second;
    }

    @Override
    public String toString() {
        return String.format("%s %s", first.toString(), second.toString());
    }

    @Override
    public boolean equals(Object t) {
        if (t instanceof Pair) {
            @SuppressWarnings("unchecked")
            Pair<T,S> pair = (Pair<T, S>) t;
            return pair.getFirst().equals(first)
                    && pair.getSecond().equals(second);
        }
        return false;
    }

     boolean matchOneElement(Object obj) {
        return first.equals(obj) || second.equals(obj);
    }
}