package fitnus.command;

public class ListFoodIntakeCommand extends Command {
    private String timeSpan;
    private

    public ListFoodIntakeCommand(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    @Override
    public String execute() {
        if(timeSpan.equals("/day")) {

        }
        return null;
    }
}


