package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Food;
import fitnus.utility.Ui;
import fitnus.utility.User;

import java.util.ArrayList;

public class FindFoodCommand extends Command {

    private static final String COMMAND_FIND = "find";
    private final String keyword;

    public FindFoodCommand(String keyword) {
        this.keyword = keyword;
    }

    public String execute(EntryDatabase ed, FoodDatabase fd, User us) throws FitNusException {
        ArrayList<Food> matchingFoods = fd.findFood(keyword);
        Ui.printMatchingFoods(matchingFoods);
        return "";
    }

}
