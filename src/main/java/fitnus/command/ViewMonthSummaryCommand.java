package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Summary;
import fitnus.utility.User;

public class ViewMonthSummaryCommand extends Command {
    public ViewMonthSummaryCommand() {}

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) throws FitNusException {
        // Retrieves all entries that fall in the past week
        EntryDatabase pastMonthEntries = ed.getPastMonthEntryDatabase();
        Summary sum = new Summary(pastMonthEntries);

        return sum.generateMonthSummaryReport();
    }
}
