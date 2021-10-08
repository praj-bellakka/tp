package fitnus.command;

import fitnus.EntryDatabase;
import fitnus.FitNusException;
import fitnus.FoodDatabase;
import fitnus.User;

public class DeleteFoodEntryCommand extends Command {
    private final int index;

    public DeleteFoodEntryCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) throws FitNusException {
        ed.deleteEntry(index);
        return "You have successfully deleted " + ed.getEntryAtIndex(index).getFood();
    }
}
