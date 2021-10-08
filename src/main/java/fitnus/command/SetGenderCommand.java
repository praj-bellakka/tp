package fitnus.command;

import fitnus.EntryDatabase;
import fitnus.FoodDatabase;
import fitnus.User;

public class SetGenderCommand extends Command {
    String genderSymbol;

    public SetGenderCommand(String genderSymbol) {
        this.genderSymbol = genderSymbol;
    }

    @Override 
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        us.setGender(genderSymbol.equals("M") ? 0 : 1);
        return "You have set your gender to " + (genderSymbol.equals("M") ? "Male" : "Female");
    }
}
