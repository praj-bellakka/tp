package fitnus.command;

import fitnus.EntryDatabase;
import fitnus.FoodDatabase;
import fitnus.User;

public class SetGenderCommand extends Command {
    String genderSymbol;

    public static final String MALE_SYMBOL = "M";
    public static final String FEMALE_SYMBOL = "F";


    public SetGenderCommand(String genderSymbol) {
        this.genderSymbol = genderSymbol;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        if(genderSymbol.equals(MALE_SYMBOL) || genderSymbol.equals(FEMALE_SYMBOL)) {
            us.setGender(genderSymbol.equals("M") ? 0 : 1);
            return "You have set your gender to " + (genderSymbol.equals("M") ? "Male" : "Female");
        } else {
            return "Invalid input! Please input M for male or F for female when setting your gender.";
        }
    }
}
