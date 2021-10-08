package fitnus;

public class FitNusException extends Exception {
    public FitNusException(String exception) {
        super(exception);
        Ui ui = new Ui();
        ui.println(exception);
    }
}
