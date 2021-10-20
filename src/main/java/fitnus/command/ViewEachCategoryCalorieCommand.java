package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Entry;
import fitnus.tracker.MealType;
import fitnus.utility.User;

import java.util.ArrayList;

public class ViewEachCategoryCalorieCommand extends Command {
    private int totalMealType = 5;

    public ViewEachCategoryCalorieCommand() {

    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) throws FitNusException {
        ArrayList<Entry> entries = ed.getEntries();
        int[] typeFrequency = {0, 0, 0, 0, 0};
        int[] typeCalories = {0, 0, 0, 0, 0};
        String[] types = {"Breakfast", "Lunch", "Dinner", "Snack", "Undefined"};
        for (Entry entry: entries) {
            int i = 0;
            switch (entry.getMealType()) {
            case BREAKFAST:
                i = 0;
                break;
            case LUNCH:
                i = 1;
                break;
            case DINNER:
                i = 2;
                break;
            case SNACK:
                i = 3;
                break;
            default:
                i = 4;
            }
            typeFrequency[i] += 1;
            typeCalories[i] += entry.getFood().getCalories();
        }

        StringBuilder result = new StringBuilder("This is the overview of your diet for different meal types: \n");

        for (int i = 0; i < totalMealType; i++) {
            result.append(String.format("[%s] Frequency:%d  Total Calories:%d\n", types[i],
                    typeFrequency[i], typeCalories[i]));
        }
        return result.toString();
    }
}
