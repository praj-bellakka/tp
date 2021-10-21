package fitnus.tracker.viewcalorietrend;

import fitnus.command.Command;
import fitnus.database.EntryDatabase;

public abstract class ViewCalorieTrend {
    private static final String SQUARE = "■";

    protected static String getSqaures(int calorie, int unit) {
        StringBuilder builder = new StringBuilder("");
        for (int i = 0; i < calorie / unit; i++) {
            builder.append(SQUARE);
        }
        return builder.toString();
    }
}
