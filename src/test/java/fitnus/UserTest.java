package fitnus;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void setCalorieGoal_validGoal_goalSetSuccessfully() throws FitNusException {
        User user = new User(1, 1000);
        user.setCalorieGoal(2000);
        assertEquals(user.getCalorieGoal(), 2000);
    }

    @Test
    void setCalorieGoal_negativeIntegerGoal_exceptionThrown() {
        User user = new User(1, 1000);
        Exception exception = assertThrows(FitNusException.class, () -> user.setCalorieGoal(-1000));
        assertEquals(exception.getMessage(), "Calorie Goal cannot be negative! Please try again!");
    }

    @Test
    void setCalorieGoal_sameGoal_exceptionThrown() {
        User user = new User(1, 1000);
        Exception exception = assertThrows(FitNusException.class, () -> user.setCalorieGoal(1000));
        assertEquals(exception.getMessage(), "Calorie Goal cannot be the same as before! Please try again!");
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

    @Test
    void showCaloriesRemaining() {
        //need?
    }
}