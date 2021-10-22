package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Food;
import fitnus.utility.Ui;
import fitnus.utility.User;

import java.util.ArrayList;

public class ViewSuggestionsCommand extends Command {
    private final Food.FoodType type;

    public ViewSuggestionsCommand(Food.FoodType type) {
        this.type = type;
    }

    public String execute(EntryDatabase ed, FoodDatabase fd, User us) throws FitNusException {
        int remaining = us.getCalorieGoal() - ed.getTotalDailyCalorie();
        ArrayList<Food> suggestions = fd.findSuggestions(type, remaining);
        Ui.printMatchingFoods(suggestions);
        return "";
    }

}
