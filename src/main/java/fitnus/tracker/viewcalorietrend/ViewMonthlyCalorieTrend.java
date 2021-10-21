package fitnus.tracker.viewcalorietrend;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Entry;
import fitnus.utility.User;

import java.time.Month;
import java.util.ArrayList;

public class ViewMonthlyCalorieTrend extends ViewCalorieTrend {
    private static final int UNIT_PER_SQUARE = 3000;

    public static String generateTrendGraph(ArrayList<Entry> ed) {
        StringBuilder output = new StringBuilder("");
        Month month;

        if (ed.size() == 0) {
            return "Oops! There is nothing recorded in your entries";
        }

        month = ed.get(0).getRawDate().getMonth();
        int calories = 0;
        int i = 0;

        do {
            if (ed.get(i).getRawDate().getMonth().equals(month)) {
                calories += ed.get(i).getFood().getCalories();
                i++;
            } else {
                output.append(String.format("%s: %s %d\n", month.toString(),
                        getSqaures(calories, UNIT_PER_SQUARE), calories));
                month = month.plus(1);
                calories = 0;
            }
        } while (i < ed.size());
        output.append(String.format("%s: %s %d\n", month.toString(),
                getSqaures(calories, UNIT_PER_SQUARE), calories));
        return output.toString();
    }
}
