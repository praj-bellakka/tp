package fitnus.command;

import fitnus.EntryDatabase;
import fitnus.Food;
import fitnus.FoodDatabase;
import fitnus.User;

public class AddDefaultFoodEntryCommand extends Command {
    private final int index;

    public AddDefaultFoodEntryCommand(int index) {
        this.index = index - 1;
    }
    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        ed.addDefaultEntry(fd, index);
        return "You have successfully added " + fd.getFoodAtIndex(index);
    }
}
