package fitnus.command;

import fitnus.EntryDatabase;
import fitnus.FoodDatabase;
import fitnus.User;

public class DeleteFoodEntryCommand extends Command {
    private final int index;

    public DeleteFoodEntryCommand(int index) {
        this.index = index - 1;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        ed.deleteEntry(index);
        return "You have successfully deleted " + ed.getEntryAtIndex(index).getFood();
    }
}
