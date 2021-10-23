package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Gender;
import fitnus.utility.User;

public class SetGenderCommand extends Command {
    private final Gender genderSymbol;

    private static final String MALE_SYMBOL = "m";
    private static final String FEMALE_SYMBOL = "f";


    public SetGenderCommand(String genderSymbol) {
        this.genderSymbol = Gender.findGender(genderSymbol);
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        if (genderSymbol != null) {
            us.setGender(genderSymbol);
            return "You have set your gender to " + genderSymbol.toString();
        }
        return "Invalid input! Please input m for male or "
               + "f for female when setting your gender.";
    }
}
