package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.utility.User;

public class SetHeightCommand extends Command{
    private final int height;

    public SetHeightCommand(int height) {
        this.height = height;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        us.setHeight(this.height);
        return "You have set your height to " + this.height + " cm!";
    }
}
