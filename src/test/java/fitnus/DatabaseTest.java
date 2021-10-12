package fitnus;

import fitnus.database.FoodDatabase;
import fitnus.exception.FitNusException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DatabaseTest {

    @Test
    void convertDatabaseToString_defaultFoodData_dataAsString() throws FitNusException {
        FoodDatabase database = new FoodDatabase();
        database.addFood("Nasi Lemak", 500);
        database.addFood("Chicken Rice", 450);
        assertEquals("Nasi Lemak | 500" + System.lineSeparator()
                        + "Chicken Rice | 450" + System.lineSeparator(),
                database.convertDatabaseToString());

    }
}
