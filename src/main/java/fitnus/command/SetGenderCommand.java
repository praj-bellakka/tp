package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Gender;
import fitnus.utility.User;

public class SetGenderCommand extends Command {
    private final Gender genderSymbol;

    private static final String MALE_SYMBOL = "Male";
    private static final String FEMALE_SYMBOL = "Female";


    public SetGenderCommand(String genderSymbol) {
        this.genderSymbol = Gender.findGender(genderSymbol);
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        if (genderSymbol != null) {
            us.setGender(genderSymbol);
            if (genderSymbol.toString().equals("f")) {
                return "You have set your gender to " + FEMALE_SYMBOL;
            }
            return "You have set your gender to " + MALE_SYMBOL;
        }
        return "Invalid input! Please input m for male or "
               + "f for female when setting your gender.";
    }
}
