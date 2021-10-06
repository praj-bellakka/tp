package fitnus;

import fitnus.parser.Parser;

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

    public ArrayList<Entry> getEntries() {
        return entries;
    }

    public void addDefaultEntry(FoodDatabase fd, int index) {
        try {
            Food food = fd.getFoodAtIndex(index);
            addEntry(food);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("There is no such default food at this index!");
        }
    }

    public void addFromString(FoodDatabase fd, String input) {
        Parser p = new Parser();
        if (input.contains("/def")) {
            int index = p.parseIntegers(input);
            addDefaultEntry(fd, index);
        } else if (input.contains("/cal")) {
            //TODO replace code with addCustomEntry @Siyuan
            String foodName = p.parseFoodName(input);
            int calories = p.parseIntegers(input);
            Food customFood = new Food(foodName, calories);
            addEntry(customFood);
        } else {
            //TODO include exception throw here instead of print
            System.out.println("Wrong add format used!");
        }
    }
}

