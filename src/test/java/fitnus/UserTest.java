package fitnus;

import fitnus.database.EntryDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Entry;
import fitnus.tracker.Food;
import fitnus.tracker.Gender;
import fitnus.tracker.MealType;
import fitnus.tracker.WeightRecord;
import fitnus.utility.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {
    @Test
    void setCalorieGoal_validGoal_success() throws FitNusException {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        user.setCalorieGoal(3000);
        assertEquals(3000, user.getCalorieGoal());
    }

    @Test
    void setCalorieGoal_calorieGoalTooLow_exceptionThrown() {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        Exception exception = assertThrows(FitNusException.class, () -> user.setCalorieGoal(-1000));
        assertEquals("Your calorie goal cannot be lower than 1365 kcal as "
                + "this would exceed the recommended healthy amount\n"
                + "of weight loss for your body type!\n"
                + "Please try again.", exception.getMessage());
    }

    @Test
    void updateWeightAndWeightTracker_noPreviousEntries_success() throws FitNusException {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        String message = user.updateWeightAndWeightTracker((float) 55.5);
        assertEquals("You have updated your weight for today to 55.5 kg!", message);
        assertEquals((float) 55.5, user.getWeight());

        LocalDate currDate = LocalDate.now();
        ArrayList<WeightRecord> weightRecords = user.getWeightRecords();
        int weightTrackerSize = weightRecords.size();
        assertEquals(currDate.toString(), weightRecords.get(weightTrackerSize - 1).getDate().toString());
        assertEquals((float) 55.5, weightRecords.get(weightTrackerSize - 1).getWeight());
    }

    @Test
    void updateWeightAndWeightTracker_onlyCurrentDayEntryExists_success() throws FitNusException {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        user.updateWeightAndWeightTracker((float) 65.5);
        String message = user.updateWeightAndWeightTracker((float) 55.5);
        assertEquals("You have updated your weight for today to 55.5 kg!", message);
        assertEquals((float) 55.5, user.getWeight());

        LocalDate currDate = LocalDate.now();
        ArrayList<WeightRecord> weightRecords = user.getWeightRecords();
        int weightTrackerSize = weightRecords.size();
        assertEquals(currDate.toString(), weightRecords.get(weightTrackerSize - 1).getDate().toString());
        assertEquals((float) 55.5, weightRecords.get(weightTrackerSize - 1).getWeight());
    }

    @Test
    void updateWeightAndWeightTracker_multipleEntriesExistIncludingCurrentDay_success() throws FitNusException {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        user.addToWeightRecords(new WeightRecord(70, LocalDate.parse("2001-10-03")));
        user.updateWeightAndWeightTracker((float) 65.5);
        String message = user.updateWeightAndWeightTracker((float) 55.5);
        assertEquals("You have updated your weight for today to 55.5 kg!\nYou have lost 14.5 kg "
                + "from the previous weight entry of 70.0 kg on 2001-10-03", message);
        assertEquals((float) 55.5, user.getWeight());

        LocalDate currDate = LocalDate.now();
        ArrayList<WeightRecord> weightRecords = user.getWeightRecords();
        int weightTrackerSize = weightRecords.size();
        assertEquals(currDate.toString(), weightRecords.get(weightTrackerSize - 1).getDate().toString());
        assertEquals((float) 55.5, weightRecords.get(weightTrackerSize - 1).getWeight());
    }

    @Test
    void updateWeightAndWeightTracker_multipleEntriesExistExcludingCurrentDay_success() throws FitNusException {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        user.addToWeightRecords(new WeightRecord(70, LocalDate.parse("2001-10-03")));
        String message = user.updateWeightAndWeightTracker((float) 55.5);
        assertEquals("You have updated your weight for today to 55.5 kg!\nYou have lost 14.5 kg "
                + "from the previous weight entry of 70.0 kg on 2001-10-03", message);
        assertEquals((float) 55.5, user.getWeight());

        LocalDate currDate = LocalDate.now();
        ArrayList<WeightRecord> weightRecords = user.getWeightRecords();
        int weightTrackerSize = weightRecords.size();
        assertEquals(currDate.toString(), weightRecords.get(weightTrackerSize - 1).getDate().toString());
        assertEquals((float) 55.5, weightRecords.get(weightTrackerSize - 1).getWeight());
    }

    @Test
    void updateWeightAndWeightTracker_negativeNewWeight_exceptionThrown() {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        AssertionError exception = assertThrows(AssertionError.class, () -> user.updateWeightAndWeightTracker(-10));
        assertEquals("newWeight should be greater than 0", exception.getMessage());
    }

    @Test
    void convertWeightRecordsToStringForUi_oneOrMoreWeightEntries_success() throws FitNusException {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        user.addToWeightRecords(new WeightRecord(70, LocalDate.parse("2001-10-03")));
        user.updateWeightAndWeightTracker((float) 55.5);
        assertEquals("2001-10-03: 70.0kg" + System.lineSeparator()
                        + LocalDate.now().toString() + ": 55.5kg",
                user.convertWeightRecordsToStringForUi(user.getWeightRecords()));
    }

    @Test
    void convertWeightRecordsToStringForUi_noWeightEntries_exceptionThrown() {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        Exception exception = assertThrows(FitNusException.class,
            () -> user.convertWeightRecordsToStringForUi(user.getWeightRecords()));
        assertEquals("An error has occurred! No weight records found.", exception.getMessage());
    }

    @Test
    void getCaloriesRemaining_foodTrackerEntriesExist_success() {
        final User user = new User(2000, Gender.MALE, 18, 180, 65);
        EntryDatabase ed = new EntryDatabase();
        ed.addEntry(new Entry(MealType.BREAKFAST, new Food("Bread", 55, Food.FoodType.MEAL),
                LocalDate.parse("2001-10-03")));
        ed.addEntry(new Entry(MealType.BREAKFAST, new Food("Pizza", 100, Food.FoodType.MEAL)));
        ed.addEntry(new Entry(MealType.BREAKFAST, new Food("Rice", 100, Food.FoodType.MEAL)));

        assertEquals(1800, user.getCaloriesRemaining(ed));
    }

    @Test
    void generateCalorieGoal_validInputs_success() throws FitNusException {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        int calorieGoal = user.handleGenerateCalorieGoalCommand((float) 0.1, "lose");
        assertEquals(2265, calorieGoal);
    }

    @Test
    void generateCalorieGoal_invalidChangeType_exceptionThrown() throws FitNusException {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        Exception exception = assertThrows(FitNusException.class,
            () -> user.handleGenerateCalorieGoalCommand((float) 0.1,"invalid"));
        assertEquals("An error has occurred! The change type is invalid.", exception.getMessage());
    }

    @Test
    void generateCalorieGoal_negativeWeeklyChangeValue_exceptionThrown() {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        Exception exception = assertThrows(FitNusException.class,
            () -> user.handleGenerateCalorieGoalCommand((float) -0.1, "lose"));
        assertEquals("Please enter a positive value for the weekly change!", exception.getMessage());
    }

    @Test
    void generateCalorieGoal_weeklyChangeValueTooHigh_exceptionThrown() {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        Exception exception = assertThrows(FitNusException.class,
            () -> user.handleGenerateCalorieGoalCommand((float) 1.2, "lose"));
        assertEquals("In order to lose or gain weight in a safe and healthy way,\n"
                + "FitNUS recommends a weekly change in weight of not more than\n"
                + "1.0 kg. Please try again with a lower weekly goal!", exception.getMessage());
    }

    @Test
    void getUserDataDisplay_validUserData_success() {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        assertEquals("Calorie Goal: 2000 " + System.lineSeparator()
                + "Gender: m" + System.lineSeparator()
                + "Age: 18" + System.lineSeparator()
                + "Weight: 65.0" + System.lineSeparator()
                + "Height: 180", user.getUserDataDisplay());
    }

    @Test
    void convertUserDataToString_validUserData_success() {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        assertEquals("2000 | m | 18 | 180 | 65.0", user.convertUserDataToString());
    }

    @Test
    void convertWeightDataToString_weightEntriesExist_success() {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        user.addToWeightRecords(new WeightRecord(70, LocalDate.parse("2001-10-03")));
        user.addToWeightRecords(new WeightRecord(65, LocalDate.parse("2001-10-04")));
        assertEquals("70.0 | 2001-10-03" + System.lineSeparator()
                + "65.0 | 2001-10-04" + System.lineSeparator(), user.convertWeightDataToString());
    }

    @Test
    void convertWeightDataToString_noWeightEntries_success() {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        assertEquals("", user.convertWeightDataToString());
    }

}