package fitnus.tracker;

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
    }
}
