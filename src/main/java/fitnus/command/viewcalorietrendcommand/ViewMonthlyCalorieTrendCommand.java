package fitnus.command.viewcalorietrendcommand;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Entry;
import fitnus.utility.User;

import java.time.Month;
import java.util.ArrayList;

public class ViewMonthlyCalorieTrendCommand extends ViewCalorieTrend {

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) throws FitNusException {
        ed.sortDatabase();
        ArrayList<Entry> entries = ed.getEntries();
        StringBuilder output = new StringBuilder("");
        Month month;

        if (entries.size() == 0) {
            return "Oops! There is nothing recorded in your entries";
        }

        month = entries.get(0).getRawDate().getMonth();
        int calories = 0;
        int i = 0;

        do {
            if (entries.get(i).getRawDate().getMonth().equals(month)) {
                calories += entries.get(i).getFood().getCalories();
                i++;
            } else {
                output.append(String.format("%s: %s %d\n", month.toString(),
                        getSqaures(calories, 1000), calories));
                month = month.plus(1);
                calories = 0;
            }
        } while (i < entries.size());
        output.append(String.format("%s: %s %d\n", month.toString(),
                getSqaures(calories, 1000), calories));
        return output.toString();
    }
}
