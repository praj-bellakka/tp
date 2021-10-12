package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.utility.User;

public class ListFoodIntakeCommand extends Command {
    private final String timeSpan;

    public static final String DESCRIPTOR_DAY = "/day";

    public ListFoodIntakeCommand(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        if (timeSpan.equals(DESCRIPTOR_DAY)) {
            return ed.listEntries();
        }
        return null;
    }
}


