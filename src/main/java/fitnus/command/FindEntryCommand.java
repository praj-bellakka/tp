package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Entry;
import fitnus.tracker.Food;
import fitnus.utility.Ui;
import fitnus.utility.User;

import java.util.ArrayList;

public class FindEntryCommand extends Command {

    private static final String COMMAND_FIND = "find";
    private final String keyword;

    public FindEntryCommand(String keyword) {
        this.keyword = keyword;
    }

    public String execute(EntryDatabase ed, FoodDatabase fd, User us) throws FitNusException {
        ArrayList<Entry> matchingEntries = ed.findEntry(keyword);
        Ui.printMatchingEntries(matchingEntries);
        return "";
    }

}
