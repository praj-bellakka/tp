package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.utility.User;

public class SetGenderCommand extends Command {
    private final String genderSymbol;

    public static final String MALE_SYMBOL = "m";
    public static final String FEMALE_SYMBOL = "f";


    public SetGenderCommand(String genderSymbol) {
        this.genderSymbol = genderSymbol;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        if (genderSymbol.equals(MALE_SYMBOL) || genderSymbol.equals(FEMALE_SYMBOL)) {
            us.setGender(genderSymbol.equals(MALE_SYMBOL) ? 0 : 1);
            return "You have set your gender to " + (genderSymbol.equals(MALE_SYMBOL) ? "Male" : "Female");
        } else {
            return "Invalid input! Please input " + MALE_SYMBOL + " for male or "
                    + FEMALE_SYMBOL + " for female when setting your gender.";
        }
    }
}
