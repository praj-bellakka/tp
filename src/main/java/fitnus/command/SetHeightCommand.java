package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.utility.User;

public class SetHeightCommand extends Command {
    private final int height;

    public SetHeightCommand(int height) {
        this.height = height;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us) throws FitNusException {
        us.setHeight(height);
        return "You have set your height to " + this.height + " cm!";
    }
}
