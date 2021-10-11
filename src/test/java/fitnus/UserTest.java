package fitnus;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    @Test
    void setCalorieGoal_validGoal_goalSetSuccessfully() throws FitNusException {
        User user = new User(1, 1000);
        user.setCalorieGoal(2000);
        assertEquals(2000, user.getCalorieGoal());
    }

    @Test
    void setCalorieGoal_negativeIntegerGoal_exceptionThrown() {
        User user = new User(1, 1000);
        Exception exception = assertThrows(FitNusException.class, () -> user.setCalorieGoal(-1000));
        assertEquals("Calorie Goal cannot be negative! Please try again!", exception.getMessage());
    }

    @Test
    void setCalorieGoal_sameGoal_exceptionThrown() {
        User user = new User(1, 1000);
        Exception exception = assertThrows(FitNusException.class, () -> user.setCalorieGoal(1000));
        assertEquals("Calorie Goal cannot be the same as before! Please try again!", exception.getMessage());
    }

    @Test
    void setGender_validGenderIntegerRepresentation_genderSetSuccessfully() {
        User user = new User(1, 1000);
        user.setGender(0);
        assertEquals(0, user.getGender());
    }

    @Test
    void setGender_invalidGenderIntegerRepresentation_genderRemainsSame() {
        User user = new User(1, 1000);
        user.setGender(3);
        assertEquals(1, user.getGender());
    }
}