package fitnus;

public class FitNus {
    public static void main(String[] args) {
        User user = new User(0, 1000); //placeholder inputs, to get user's actual input later
        FoodDatabase database = new FoodDatabase();
        EntryDatabase ed = new EntryDatabase();
        try {
            Storage.createDirectoryAndFiles();
            Storage.initialiseDatabase(database);
            database.listFoods();
            Storage.saveDatabase(database);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
