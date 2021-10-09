package fitnus.command;

import fitnus.EntryDatabase;
import fitnus.FoodDatabase;
import fitnus.Ui;
import fitnus.User;

public class ListFoodIntakeCommand extends Command {
    private final String timeSpan;

    public static final String DESCRIPTOR_DAY = "/day";

    public ListFoodIntakeCommand(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        if (timeSpan.equals(DESCRIPTOR_DAY)) {
            String entries = ed.convertDatabaseToString();
            return entries;
        }
        return null;
    }
}


