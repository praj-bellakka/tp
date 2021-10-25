package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.utility.User;

public class SetWeightCommand extends Command {
    private final float newWeight;

    public SetWeightCommand(float newWeight) {
        this.newWeight = newWeight;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us) throws FitNusException {
        if (newWeight <= 0) {
            throw new FitNusException("Please enter a positive number for your weight!");
        }

        float newWeightIn2Dp = (float) (Math.round(newWeight * 10.0) / 10.0);
        return us.updateWeightAndWeightTracker(newWeightIn2Dp);
    }
}
