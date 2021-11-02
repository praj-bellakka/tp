package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Summary;
import fitnus.utility.User;

import java.time.LocalDate;
import java.time.Month;

public class ViewMonthSummaryCommand extends Command {
    private Month month;
    public ViewMonthSummaryCommand(int month) {
        this.month = Month.of(month);
    }

    public ViewMonthSummaryCommand() {
        this.month = LocalDate.now().getMonth();
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us) throws FitNusException {
        // Retrieves all entries that fall in the past week
        EntryDatabase pastMonthEntries = ed.getPastMonthEntryDatabase(month);
        Summary sum = new Summary(pastMonthEntries, month.length(LocalDate.now().isLeapYear()));

        return sum.generateMonthSummaryReport();
    }
}
