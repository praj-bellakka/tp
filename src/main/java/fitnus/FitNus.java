package fitnus;

public class FitNus {
    public static void main(String[] args) {
        FoodDatabase database = new FoodDatabase();
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
