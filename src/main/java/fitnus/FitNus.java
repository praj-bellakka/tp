package fitnus;

public class FitNus {
    public static void main(String[] args) {
        FoodDatabase database = new FoodDatabase();
        EntryDatabase ed = new EntryDatabase();
        Ui ui = new Ui();
        try {
            // Welcome Message
            ui.printWelcomeMessage();
            ui.printHelpMessage();

            // Load From Storage
            Storage.createDirectoryAndFiles();
            Storage.initialiseDatabase(database);
            database.listFoods();
            Storage.saveDatabase(database);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
