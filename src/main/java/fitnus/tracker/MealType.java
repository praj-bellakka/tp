package fitnus.tracker;

import java.time.LocalDateTime;


public enum MealType {
    BREAKFAST {
        @Override
        public String toString() {
            return "Breakfast";
        }
    },
    LUNCH {
        @Override
        public String toString() {
            return "Lunch";
        }
    },
    DINNER {
        @Override
        public String toString() {
            return "Dinner";
        }
    },
    SNACK {
        @Override
        public String toString() {
            return "Snack";
        }
    },
    UNDEFINED {
        @Override
        public String toString() {
            return "Undefined";
        }
    };

    //predefined breakfast, lunch and dinner timings (in 24hrs)
    private static final int BREAKFAST_HOUR_LOWER = 6; //6am
    private static final int BREAKFAST_HOUR_UPPER = 10; //10am
    private static final int LUNCH_HOUR_LOWER = 11; //11am
    private static final int LUNCH_HOUR_UPPER = 14; //2pm
    private static final int DINNER_HOUR_LOWER = 18; //6pm
    private static final int DINNER_HOUR_UPPER = 21; //9pm

    /**
     * Finds the current hour (in 24hrs) using system LocalDateTime object.
     * The relevant MealType is returned based on the hourOfDay.
     * 6am to 10am: Breakfast.
     * 11am to 2pm: Lunch.
     * 6pm to 9pm: Dinner.
     * Otherwise: Snack.
     *
     * @return MealType based on hourOfDay.
     */
    public MealType findMealTypeTiming() {
        LocalDateTime currentTime = LocalDateTime.now();
        int hourOfDay = currentTime.getHour();
        if (hourOfDay >= BREAKFAST_HOUR_LOWER && hourOfDay < BREAKFAST_HOUR_UPPER) {
            return MealType.BREAKFAST;
        } else if (hourOfDay >= LUNCH_HOUR_LOWER && hourOfDay < LUNCH_HOUR_UPPER) {
            return MealType.LUNCH;
        } else if (hourOfDay >= DINNER_HOUR_LOWER && hourOfDay < DINNER_HOUR_UPPER) {
            return MealType.DINNER;
        } else {
            return MealType.SNACK; //if current time is outside of these hours, the person is assumed to eat snack.
        }
    }
}
