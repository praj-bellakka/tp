package fitnus.command;

import fitnus.*;

public class DeleteFoodEntryCommand extends Command {
    private final int index;

    public DeleteFoodEntryCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) throws FitNusException {
        Entry deletedEntry = ed.getEntryAtIndex(index);
        ed.deleteEntry(index);
        return "You have successfully deleted " + deletedEntry.getFood();
    }
}
