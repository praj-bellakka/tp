package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Gender;
import fitnus.utility.User;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ViewMonthSummaryCommandTest {
    private static final String ROOT = System.getProperty("user.dir");
    private static final Path FILE_PATH_ENTRY_DATA = Paths.get(ROOT, "testing-data", "month_entry_month_data.txt");
    private static final Path EMPTY_DATA = Paths.get(ROOT, "testing-data", "empty_data.txt");


    @Test
    public void testMothReport_currentMonthData_validMonthSummary() throws FitNusException {
        EntryDatabase database = new EntryDatabase();
        try {
            FileInputStream stream;
            stream = new FileInputStream(FILE_PATH_ENTRY_DATA.toString());
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            database.preloadDatabase(reader);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        String output = String.format("Average Daily Calorie Intake: %d\n", 6144 / LocalDate.now().getDayOfMonth());

        output += String.format("Food eaten most: [cantaloupe, chick] [2 time(s)]\n");
        output += String.format("Food eaten least: [bananas, beef, best choice sugar, broccoli, butter, "
                + "ham, lunchmeat, milk, peanut butter, pickles, protein, sausage, spinach, turkey] [1 time(s)]");
        assertEquals(output, new ViewMonthSummaryCommand().execute(database, new FoodDatabase(),
                new MealPlanDatabase(),new User(2500, Gender.MALE, 25, 185, 80)));
    }

    @Test
    public void testMonthSummary_emptyData_warning() throws FitNusException {
        EntryDatabase database = new EntryDatabase();
        try {
            FileInputStream stream;
            stream = new FileInputStream(EMPTY_DATA.toString());
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            database.preloadDatabase(reader);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("No entries found!", new ViewMonthSummaryCommand().execute(database, new FoodDatabase(),
                new MealPlanDatabase(),new User(2500, Gender.MALE, 25, 185, 80)));
    }
}