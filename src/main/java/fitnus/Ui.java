package fitnus;

public class Ui {
    public Ui() {
    }

    public static final String WELCOME_MESSAGE = "------------------ \n" 
                                                + "Welcome to FitNUS Tracker!";
    public static final String HELP_MESSAGE = "------------------ \n" 
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
    
    public void printWelcomeMessage() {
        println(WELCOME_MESSAGE);
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void print(String message) {
        System.out.print(message);
    }

    public void printHelpMessage() {
        println(HELP_MESSAGE);
    }
}
