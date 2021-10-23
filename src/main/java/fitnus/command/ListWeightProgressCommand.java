package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.tracker.WeightProgressEntry;
import fitnus.utility.User;

import java.util.ArrayList;

public class ListWeightProgressCommand extends Command {
    public ListWeightProgressCommand() {
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        ArrayList<WeightProgressEntry> weightProgressEntries = us.getWeightProgressEntries();
        float startingWeight = weightProgressEntries.get(0).getWeight();
        float currentWeight = weightProgressEntries.get(weightProgressEntries.size() - 1).getWeight();

        float weightChange = startingWeight - currentWeight;
        String changeType = weightChange < 0 ? "gained" : "lost";

        weightChange = Math.abs(weightChange);

        return "Your weight progress: \n"
                + us.convertWeightRecordsToStringForUi()
                + "You have " + changeType + " " + weightChange + " kg since the start of your FitNUS journey!";
    }
}
