package fitnus;

import fitnus.exception.FitNusException;
import fitnus.tracker.Gender;
import fitnus.utility.User;
import org.junit.jupiter.api.Test;

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
    void updateWeightAndWeightTracker_validNewWeight_success() throws FitNusException {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        Exception exception = assertThrows(FitNusException.class, () -> user.updateWeightAndWeightTracker(-10));
        assertEquals("An error occurred! The new weight cannot be negative.", exception.getMessage());
    }

    @Test
    void updateWeightAndWeightTracker_negativeNewWeight_exceptionThrown() throws FitNusException {
        User user = new User(2000, Gender.MALE, 18, 180, 65);
        Exception exception = assertThrows(FitNusException.class, () -> user.updateWeightAndWeightTracker(-10));
        assertEquals("An error occurred! The new weight cannot be negative.", exception.getMessage());
    }

}