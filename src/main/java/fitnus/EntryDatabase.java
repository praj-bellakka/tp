package fitnus;

import java.util.ArrayList;

public class EntryDatabase {
    private ArrayList<Entry> entries;

    public EntryDatabase() {
        this.entries = new ArrayList<>();
    }

    public void addEntry(Food food) {
        this.entries.add(new Entry(food));
    }

    public void deleteEntry(int index) {
        this.entries.remove(index);
    }

    public void addDefaultEntry(FoodDatabase fd, int index) {
        try {
            Food food = fd.getFoodAtIndex(index);
            addEntry(food);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("There is no such default food at this index!");
        }
    }

}

