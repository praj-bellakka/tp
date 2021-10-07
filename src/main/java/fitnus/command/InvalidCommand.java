package fitnus.command;

public class InvalidCommand extends Command {

    public InvalidCommand() {
    }

    @Override
    public String execute() {
        return "invalid command";
    }
}
