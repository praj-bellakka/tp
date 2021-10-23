package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.utility.User;

public class SetAgeCommand extends Command {
    private final int age;

    public SetAgeCommand(int age) {
        this.age = age;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        us.setAge(this.age);
        return "You have set your age to " + this.age + " years old!";
    }
}
