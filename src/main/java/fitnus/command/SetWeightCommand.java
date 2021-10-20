package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.utility.User;

public class SetWeightCommand extends Command {
    private final int newWeight;

    public SetWeightCommand(int newWeight) {
        this.newWeight = newWeight;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        return us.updateWeightAndWeightTracker(newWeight);
    }
}
