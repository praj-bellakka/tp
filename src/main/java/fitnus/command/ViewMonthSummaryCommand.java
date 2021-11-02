package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Summary;
import fitnus.utility.User;

import java.time.LocalDate;

public class ViewMonthSummaryCommand extends Command {
    public ViewMonthSummaryCommand() {
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us) throws FitNusException {
        // Retrieves all entries that fall in the past week
        EntryDatabase pastMonthEntries = ed.getPastMonthEntryDatabase();
        Summary sum = new Summary(pastMonthEntries, LocalDate.now().getDayOfMonth());

        return sum.generateMonthSummaryReport();
    }
}
