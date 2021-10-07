package fitnus.command;

import fitnus.EntryDatabase;
import fitnus.FoodDatabase;
import fitnus.Ui;
import fitnus.User;

public class ListFoodIntakeCommand extends Command {
    private String timeSpan;

    public ListFoodIntakeCommand(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    @Override
    public String execute(EntryDatabase ed, FoodDatabase fd, User us) {
        Ui ui = new Ui();

        if(timeSpan.equals("/day")) {
            String entries = ed.convertDatabaseToString();
            ui.println(entries);
        }
        return null;
    }
}


