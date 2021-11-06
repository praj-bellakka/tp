package fitnus.tracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MealTypeTest {
    private static final int BREAKFAST_HOUR_LOWER = 6; //6am
    private static final int BREAKFAST_HOUR_UPPER = 10; //10am
    private static final int LUNCH_HOUR_LOWER = 11; //11am
    private static final int LUNCH_HOUR_UPPER = 14; //2pm
    private static final int DINNER_HOUR_LOWER = 18; //6pm
    private static final int DINNER_HOUR_UPPER = 21; //9pm
    MealType mealType;

    @Test
    void findMealTypeTiming_currentTiming_returnCorrectType() {
        int hourOfDay = LocalDateTime.now().getHour();
        if (hourOfDay >= BREAKFAST_HOUR_LOWER && hourOfDay < BREAKFAST_HOUR_UPPER) {
            assertEquals(MealType.BREAKFAST, mealType.findMealTypeTiming());
        } else if (hourOfDay >= LUNCH_HOUR_LOWER && hourOfDay < LUNCH_HOUR_UPPER) {
            assertEquals(MealType.LUNCH, mealType.findMealTypeTiming());
        } else if (hourOfDay >= DINNER_HOUR_LOWER && hourOfDay < DINNER_HOUR_UPPER) {
            assertEquals(MealType.DINNER, mealType.findMealTypeTiming());
        }
    }
}
