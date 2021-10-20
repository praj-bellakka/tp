package fitnus.command.viewcalorietrendcommand;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Entry;
import fitnus.utility.User;

import java.time.LocalDate;
import java.util.ArrayList;

public class ViewDailyCalorieTrendCommand extends ViewCalorieTrend {

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) throws FitNusException {
        ed.sortDatabase();
        ArrayList<Entry> entries = ed.getEntries();
        StringBuilder output = new StringBuilder("");
        LocalDate date;

        if (entries.size() == 0) {
            return "Oops! There is nothing recorded in your entries";
        }

        date = entries.get(0).getRawDate();
        int calories = 0;
        int i = 0;

        // entry day should not be later than today
        // total calorie should not exceed 10000
        do {
            if (entries.get(i).getRawDate().getMonth().equals(date)) {
                calories += entries.get(i).getFood().getCalories();
                i++;
            } else {
                output.append(String.format("%s: %s %d\n", date.toString(),
                        getSqaures(calories, 100), calories));
                date = date.plusDays(1);
                calories = 0;
            }
        } while (i < entries.size());
        output.append(String.format("%s: %s %d\n", date.toString(),
                getSqaures(calories, 100), calories));
        return output.toString();
    }
}
