package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Entry;
import fitnus.utility.Ui;
import fitnus.utility.User;

import java.util.ArrayList;

public class FindEntriesCommand extends Command {
    private final String keyword;

    public FindEntriesCommand(String keyword) {
        this.keyword = keyword;
    }

    public String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us) throws FitNusException {
        ArrayList<Entry> matchingEntries = ed.findEntries(keyword);
        Ui.printMatchingEntries(matchingEntries);
        return "Found " + matchingEntries.size() + " matching entries";
    }

}
