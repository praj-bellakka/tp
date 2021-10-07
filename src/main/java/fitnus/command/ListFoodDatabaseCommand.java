package fitnus.command;

import fitnus.FoodDatabase;

public class ListFoodDatabaseCommand extends Command {

    private FoodDatabase foodDatabase;

    public ListFoodDatabaseCommand(FoodDatabase foodDatabase) {
        this.foodDatabase = foodDatabase;
    }

    @Override
    public String execute() {
        return foodDatabase.listFoods();
    }
}
