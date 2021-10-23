package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.exception.FitNusException;
import fitnus.utility.User;

public class SetAgeCommand extends Command {
    private final int age;

    private static final int MINIMUM_AGE = 12;

    public SetAgeCommand(int age) {
        this.age = age;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) throws FitNusException {
        if (age < MINIMUM_AGE) {
            throw new FitNusException("Users of FitNUS must be " + MINIMUM_AGE
                    + " years old and above!");
        }

        us.setAge(this.age);
        return "You have set your age to " + this.age + " years old!";
    }
}
