package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Food;
import fitnus.utility.Ui;
import fitnus.utility.User;

import java.util.ArrayList;

public class FindFoodCommand extends Command {
    private final String keyword;

    public FindFoodCommand(String keyword) {
        this.keyword = keyword;
    }

    public String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us) throws FitNusException {
        ArrayList<Food> matchingFoods = fd.findFoods(keyword);
        Ui.printMatchingFoods(matchingFoods);
        return "";
    }

}
