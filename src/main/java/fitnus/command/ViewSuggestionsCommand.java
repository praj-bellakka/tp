package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Food;
import fitnus.utility.Ui;
import fitnus.utility.User;

import java.util.ArrayList;

public class ViewSuggestionsCommand extends Command {
    private final Food.FoodType type;
    private final boolean isSort;

    public ViewSuggestionsCommand(Food.FoodType type, boolean isSort) {
        this.type = type;
        this.isSort = isSort;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us) throws FitNusException {
        int remaining = us.getCalorieGoal() - ed.getTotalDailyCalorie();
        if (remaining < 0) {
            return "Sorry, you have exceeded your daily calorie goal already!";
        }
        ArrayList<Food> suggestions = fd.findSuggestions(type, remaining, isSort);
        if (suggestions.size() > 0) {
            System.out.println("Here are some suggestions:");
            Ui.printMatchingFoods(suggestions);
        }
        return "Found " + suggestions.size() + " suggestions";
    }

}
