package fitnus;

public class FitNus {
    public static void main(String[] args) {
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
