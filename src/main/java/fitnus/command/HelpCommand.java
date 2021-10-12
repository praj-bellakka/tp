package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.utility.User;

public class HelpCommand extends Command {
    public HelpCommand() {
        super();
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        return "------------------ \n"
                + "Here are some commands that you can use!\n"
                + "------------------ \n"
                + "[X] List out available commands: help\n"
                + "[X] List foods in database: list /food\n"
                + "[X] Add food entry from database: add /def INDEX_OF_FOOD\n"
                + "[X] Add custom food entry: add /cust FOOD_NAME|CALORIES\n"
                + "[X] Delete food entry: remove /food INDEX_OF_FOOD\n"
                + "[X] Set Gender: gender /set M/F (Select one)\n"
                + "[X] View food intake for the day: list /intake /DAY\n"
                + "[X] Set calorie goal: calorie /set GOAL\n"
                + "[X] View remaining calories for the day: calorie /remain\n"
                + "[X] Exit FitNUS: exit\n"
                + "------------------ \n";
    }
}
