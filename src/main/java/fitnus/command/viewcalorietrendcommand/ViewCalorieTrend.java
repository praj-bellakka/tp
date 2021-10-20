package fitnus.command.viewcalorietrendcommand;

import fitnus.command.Command;

public abstract class ViewCalorieTrend extends Command {
    private static final String SQUARE = "■";

    protected String getSqaures(int calorie, int unit) {
        StringBuilder builder = new StringBuilder("");
        for (int i = 0; i < calorie / unit; i++) {
            builder.append(SQUARE);
        }
        return builder.toString();
    }
}
