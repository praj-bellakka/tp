package fitnus;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

    @Test
    void testToString() {
        Food food = new Food("Bread", 50);
        assertEquals("Bread (50 Kcal)", food.toString());
    }

    @Test
    void convertToStringForStorage() {
        //todo
    }
}