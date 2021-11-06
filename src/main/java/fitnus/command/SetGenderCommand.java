package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Gender;
import fitnus.utility.User;

public class SetGenderCommand extends Command {
    private final Gender gender;

    private static final String MALE_SYMBOL = "Male";
    private static final String FEMALE_SYMBOL = "Female";


    public SetGenderCommand(String genderSymbol) {
        this.gender = Gender.findGender(genderSymbol);
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, MealPlanDatabase md, User us) throws FitNusException {
        us.setGender(gender);
        if (gender.toString().equals("f")) {
            return "You have set your gender to " + FEMALE_SYMBOL;
        }
        return "You have set your gender to " + MALE_SYMBOL;
    }
}
