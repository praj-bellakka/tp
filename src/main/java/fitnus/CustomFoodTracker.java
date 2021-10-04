package fitnus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomFoodTracker {
    private List<Food> customFoodList;

    public CustomFoodTracker() {
        this.customFoodList = new ArrayList<>();
    }


    public void addCustomFood(Food foodToAdd) {
        this.customFoodList.add(new CustomFood(foodToAdd));
    }

}


class CustomFood extends Food {
    private LocalDate date;

    CustomFood(Food food) {
        super(food.getFoodName(), food.getFoodCalories());
        this.date = LocalDate.now();
    }
}

