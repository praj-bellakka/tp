package fitnus;

import fitnus.database.EntryDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Entry;
import fitnus.tracker.Food;
import fitnus.tracker.Gender;
import fitnus.tracker.MealType;
import fitnus.tracker.WeightProgressEntry;
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
    void setCalorieGoal_negativeIntegerGoal_exceptionThrown() {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        Exception exception = assertThrows(FitNusException.class, () -> user.setCalorieGoal(-1000));
        assertEquals("Calorie Goal cannot be negative! Please try again!", exception.getMessage());
    }

    @Test
    void setCalorieGoal_sameGoal_exceptionThrown() {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        Exception exception = assertThrows(FitNusException.class, () -> user.setCalorieGoal(2000));
        assertEquals("Calorie Goal cannot be the same as before! Please try again!", exception.getMessage());
    }

    @Test
    void updateWeightAndWeightTracker_noPreviousEntries_success() throws FitNusException {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        String message = user.updateWeightAndWeightTracker((float) 55.5);
        assertEquals("You have updated your weight for today to 55.5 kg!", message);
        assertEquals((float) 55.5, user.getWeight());

        LocalDate currDate = LocalDate.now();
        ArrayList<WeightProgressEntry> weightProgressEntries = user.getWeightProgressEntries();
        int weightTrackerSize = weightProgressEntries.size();
        assertEquals(currDate.toString(), weightProgressEntries.get(weightTrackerSize - 1).getDate().toString());
        assertEquals((float) 55.5, weightProgressEntries.get(weightTrackerSize - 1).getWeight());
    }

    @Test
    void updateWeightAndWeightTracker_onlyCurrentDayEntryExists_success() throws FitNusException {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        user.updateWeightAndWeightTracker((float) 65.5);
        String message = user.updateWeightAndWeightTracker((float) 55.5);
        assertEquals("You have updated your weight for today to 55.5 kg!", message);
        assertEquals((float) 55.5, user.getWeight());

        LocalDate currDate = LocalDate.now();
        ArrayList<WeightProgressEntry> weightProgressEntries = user.getWeightProgressEntries();
        int weightTrackerSize = weightProgressEntries.size();
        assertEquals(currDate.toString(), weightProgressEntries.get(weightTrackerSize - 1).getDate().toString());
        assertEquals((float) 55.5, weightProgressEntries.get(weightTrackerSize - 1).getWeight());
    }

    @Test
    void updateWeightAndWeightTracker_multipleEntriesExistIncludingCurrentDay_success() throws FitNusException {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        user.addToWeightProgressEntries(new WeightProgressEntry(70, LocalDate.parse("2001-10-03")));
        user.updateWeightAndWeightTracker((float) 65.5);
        String message = user.updateWeightAndWeightTracker((float) 55.5);
        assertEquals("You have updated your weight for today to 55.5 kg! You have lost 14.5 kg "
                + "from the previous weight entry of 70.0 kg on 2001-10-03", message);
        assertEquals((float) 55.5, user.getWeight());

        LocalDate currDate = LocalDate.now();
        ArrayList<WeightProgressEntry> weightProgressEntries = user.getWeightProgressEntries();
        int weightTrackerSize = weightProgressEntries.size();
        assertEquals(currDate.toString(), weightProgressEntries.get(weightTrackerSize - 1).getDate().toString());
        assertEquals((float) 55.5, weightProgressEntries.get(weightTrackerSize - 1).getWeight());
    }

    @Test
    void updateWeightAndWeightTracker_multipleEntriesExistExcludingCurrentDay_success() throws FitNusException {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        user.addToWeightProgressEntries(new WeightProgressEntry(70, LocalDate.parse("2001-10-03")));
        String message = user.updateWeightAndWeightTracker((float) 55.5);
        assertEquals("You have updated your weight for today to 55.5 kg! You have lost 14.5 kg "
                + "from the previous weight entry of 70.0 kg on 2001-10-03", message);
        assertEquals((float) 55.5, user.getWeight());

        LocalDate currDate = LocalDate.now();
        ArrayList<WeightProgressEntry> weightProgressEntries = user.getWeightProgressEntries();
        int weightTrackerSize = weightProgressEntries.size();
        assertEquals(currDate.toString(), weightProgressEntries.get(weightTrackerSize - 1).getDate().toString());
        assertEquals((float) 55.5, weightProgressEntries.get(weightTrackerSize - 1).getWeight());
    }

    @Test
    void updateWeightAndWeightTracker_negativeNewWeight_exceptionThrown() {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        Exception exception = assertThrows(FitNusException.class, () -> user.updateWeightAndWeightTracker(-10));
        assertEquals("An error occurred! The new weight cannot be negative.", exception.getMessage());
    }

    @Test
    void convertWeightRecordsToStringForUi_oneOrMoreWeightEntries_success() throws FitNusException {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        user.addToWeightProgressEntries(new WeightProgressEntry(70, LocalDate.parse("2001-10-03")));
        user.updateWeightAndWeightTracker((float) 55.5);
        assertEquals("2001-10-03: 70.0kg" + System.lineSeparator()
                        + LocalDate.now().toString() + ": 55.5kg" + System.lineSeparator(),
                user.convertWeightRecordsToStringForUi());
    }

    @Test
    void convertWeightRecordsToStringForUi_noWeightEntries_exceptionThrown() {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        Exception exception = assertThrows(FitNusException.class, () -> user.convertWeightRecordsToStringForUi());
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
        int calorieGoal = user.generateCalorieGoal((float) 0.1, "lose");
        assertEquals(2265, calorieGoal);
    }

    @Test
    void generateCalorieGoal_invalidChangeType_exceptionThrown() throws FitNusException {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        Exception exception = assertThrows(FitNusException.class, () -> user.generateCalorieGoal((float) 0.1,
                "invalid"));
        assertEquals("An error has occurred! The change type is invalid.", exception.getMessage());
    }

    @Test
    void generateCalorieGoal_negativeWeeklyChangeValue_exceptionThrown() {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        Exception exception = assertThrows(FitNusException.class, () -> user.generateCalorieGoal((float) -0.1, "lose"));
        assertEquals("Please enter a positive value for the weekly change!", exception.getMessage());
    }

    @Test
    void generateCalorieGoal_weeklyChangeValueTooHigh_exceptionThrown() {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        Exception exception = assertThrows(FitNusException.class, () -> user.generateCalorieGoal((float) 1.2, "lose"));
        assertEquals("In order to lose or gain weight in a safe and healthy way,\n"
                + "FitNUS recommends a weekly change in weight of not more than\n"
                + "1 kg. Please try again with a lower weekly goal!", exception.getMessage());
    }

    @Test
    void getUserDataDisplay_validUserData_success() {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        assertEquals("Calorie Goal: 2000 " + System.lineSeparator()
                + "Gender: m" + System.lineSeparator()
                + "Age: 18" + System.lineSeparator()
                + "Weight: 65.0" + System.lineSeparator()
                + "Height: 180" + System.lineSeparator(), user.getUserDataDisplay());
    }

    @Test
    void convertUserDataToString_validUserData_success() {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        assertEquals("2000 | m | 18 | 180 | 65.0", user.convertUserDataToString());
    }

    @Test
    void convertWeightDataToString_weightEntriesExist_success() {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        user.addToWeightProgressEntries(new WeightProgressEntry(70, LocalDate.parse("2001-10-03")));
        user.addToWeightProgressEntries(new WeightProgressEntry(65, LocalDate.parse("2001-10-04")));
        assertEquals("70.0 | 2001-10-03" + System.lineSeparator()
                + "65.0 | 2001-10-04" + System.lineSeparator(), user.convertWeightDataToString());
    }

    @Test
    void convertWeightDataToString_noWeightEntries_success() {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        assertEquals("", user.convertWeightDataToString());
    }

}