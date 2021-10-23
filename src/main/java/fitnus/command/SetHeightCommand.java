package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.exception.FitNusException;
import fitnus.utility.User;

public class SetHeightCommand extends Command {
    private int height;

    private static final int MINIMUM_HEIGHT = 40;

    public SetHeightCommand(int height) {
        this.height = height;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) throws FitNusException {
        if (height < MINIMUM_HEIGHT) {
            throw new FitNusException("Please enter a height of " + MINIMUM_HEIGHT
                    + " cm and above!");
        }

        us.setHeight(height);
        return "You have set your height to " + this.height + " cm!";
    }
}
