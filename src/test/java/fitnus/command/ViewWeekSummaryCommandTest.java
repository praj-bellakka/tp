package fitnus.command;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Entry;
import fitnus.tracker.Food;
import fitnus.tracker.Gender;
import fitnus.tracker.MealType;
import fitnus.utility.User;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ViewWeekSummaryCommandTest {

    private static final String ROOT = System.getProperty("user.dir");
    private static final Path FILE_PATH_ENTRY_DATA = Paths.get(ROOT, "data", "entry.txt");


    @Test
    public void testWeekSummaryCommand_validInput_weeklyReport() throws FitNusException {
        EntryDatabase database = new EntryDatabase();
        LocalDate today = LocalDate.now();
        database.addEntry(new Entry(MealType.BREAKFAST, new Food("butter", 143, Food.FoodType.OTHERS), today));
        database.addEntry(new Entry(MealType.LUNCH, new Food("beans", 454, Food.FoodType.MEAL), today));
        database.addEntry(new Entry(MealType.DINNER, new Food("bacon", 608, Food.FoodType.MEAL), today));
        database.addEntry(new Entry(MealType.SNACK, new Food("milk", 198, Food.FoodType.BEVERAGE), today));
        database.addEntry(new Entry(MealType.SNACK, new Food("milk", 198, Food.FoodType.BEVERAGE), today));

        database.addEntry(new Entry(MealType.BREAKFAST, new Food("pears", 153, Food.FoodType.OTHERS),
                today.minusDays(1)));
        database.addEntry(new Entry(MealType.LUNCH, new Food("chicken rice", 649, Food.FoodType.MEAL),
                today.minusDays(1)));
        database.addEntry(new Entry(MealType.DINNER, new Food("broccoli", 674, Food.FoodType.MEAL),
                today.minusDays(1)));
        database.addEntry(new Entry(MealType.SNACK, new Food("best choice sugar", 165, Food.FoodType.BEVERAGE),
                today.minusDays(1)));
        database.addEntry(new Entry(MealType.SNACK, new Food("bread", 265, Food.FoodType.SNACK),
                today.minusDays(1)));


        database.addEntry(new Entry(MealType.BREAKFAST, new Food("soy mild", 112, Food.FoodType.BEVERAGE),
                today.minusDays(2)));
        database.addEntry(new Entry(MealType.LUNCH, new Food("beef", 680, Food.FoodType.MEAL),
                today.minusDays(2)));
        database.addEntry(new Entry(MealType.DINNER, new Food("tamale", 689, Food.FoodType.MEAL),
                today.minusDays(2)));
        database.addEntry(new Entry(MealType.SNACK, new Food("pickles", 160, Food.FoodType.OTHERS),
                today.minusDays(2)));
        database.addEntry(new Entry(MealType.SNACK, new Food("cheese", 109, Food.FoodType.OTHERS),
                today.minusDays(2)));
        database.addEntry(new Entry(MealType.SNACK, new Food("sausage", 221, Food.FoodType.SNACK),
                today.minusDays(2)));

        database.addEntry(new Entry(MealType.BREAKFAST, new Food("milk", 198, Food.FoodType.BEVERAGE),
                today.minusDays(3)));
        database.addEntry(new Entry(MealType.LUNCH, new Food("beef", 680, Food.FoodType.MEAL),
                today.minusDays(3)));
        database.addEntry(new Entry(MealType.DINNER, new Food("tuna", 538, Food.FoodType.MEAL),
                today.minusDays(3)));
        database.addEntry(new Entry(MealType.SNACK, new Food("hot dogs", 185, Food.FoodType.SNACK),
                today.minusDays(3)));
        database.addEntry(new Entry(MealType.SNACK, new Food("orange juice", 27, Food.FoodType.BEVERAGE),
                today.minusDays(3)));
        database.addEntry(new Entry(MealType.SNACK, new Food("apples", 66, Food.FoodType.OTHERS),
                today.minusDays(3)));

        database.addEntry(new Entry(MealType.BREAKFAST, new Food("peaches", 64, Food.FoodType.OTHERS),
                today.minusDays(4)));
        database.addEntry(new Entry(MealType.LUNCH, new Food("carrots", 430, Food.FoodType.MEAL),
                today.minusDays(4)));
        database.addEntry(new Entry(MealType.DINNER, new Food("pollock", 465, Food.FoodType.MEAL),
                today.minusDays(4)));
        database.addEntry(new Entry(MealType.SNACK, new Food("cheese", 109, Food.FoodType.OTHERS),
                today.minusDays(4)));
        database.addEntry(new Entry(MealType.SNACK, new Food("orange juice", 27, Food.FoodType.BEVERAGE),
                today.minusDays(4)));
        database.addEntry(new Entry(MealType.SNACK, new Food("pickles", 160, Food.FoodType.OTHERS),
                today.minusDays(4)));

        database.addEntry(new Entry(MealType.BREAKFAST, new Food("protein", 141, Food.FoodType.OTHERS),
                today.minusDays(5)));
        database.addEntry(new Entry(MealType.LUNCH, new Food("bacon", 608, Food.FoodType.MEAL),
                today.minusDays(5)));
        database.addEntry(new Entry(MealType.DINNER, new Food("beef", 680, Food.FoodType.MEAL),
                today.minusDays(5)));
        database.addEntry(new Entry(MealType.SNACK, new Food("bananas", 188, Food.FoodType.OTHERS),
                today.minusDays(5)));

        database.addEntry(new Entry(MealType.BREAKFAST, new Food("butter", 143, Food.FoodType.OTHERS),
                today.minusDays(6)));
        database.addEntry(new Entry(MealType.LUNCH, new Food("carrots", 430, Food.FoodType.MEAL),
                today.minusDays(6)));
        database.addEntry(new Entry(MealType.DINNER, new Food("ham", 487, Food.FoodType.MEAL),
                today.minusDays(6)));
        database.addEntry(new Entry(MealType.SNACK, new Food("bread", 265, Food.FoodType.SNACK),
                today.minusDays(6)));

        String output = String.format("%s: ############# 1325\n%s: ################ 1617\n"
                        + "%s: ############ 1255\n%s: ################ 1694\n"
                        + "%s: ################### 1971\n%s: ################### 1906\n"
                        + "%s: ################ 1601\n",today.minusDays(6),
                        today.minusDays(5), today.minusDays(4), today.minusDays(3), today.minusDays(2),
                today.minusDays(1), today);
        output += String.format("Average Daily Calorie Intake: ################ 1624\n");

        output += String.format("Food eaten most: [beef, milk] [3 time(s)]\n");
        output += String.format("Food eaten least: [apples, bananas, beans, best choice sugar, "
                + "broccoli, chicken rice, ham, hot dogs, peaches, pears, pollock, protein, sausage, "
                + "soy mild, tamale, tuna] [1 time(s)]");
        assertEquals(output, new ViewWeekSummaryCommand().execute(database, new FoodDatabase(), new MealPlanDatabase(),
                new User(2500, Gender.MALE, 25, 185, 80)));

        database.addEntry(new Entry(MealType.BREAKFAST, new Food("butter", 143, Food.FoodType.OTHERS),
                today.minusDays(7)));
        database.addEntry(new Entry(MealType.LUNCH, new Food("carrots", 430, Food.FoodType.MEAL),
                today.minusDays(7)));
        database.addEntry(new Entry(MealType.DINNER, new Food("ham", 487, Food.FoodType.MEAL),
                today.minusDays(7)));
        database.addEntry(new Entry(MealType.SNACK, new Food("bread", 265, Food.FoodType.SNACK),
                today.minusDays(7)));
        assertEquals(output, new ViewWeekSummaryCommand().execute(database, new FoodDatabase(),
                new MealPlanDatabase(),new User(2500, Gender.MALE, 25, 185, 80)));
    }

    @Test
    public void testWeeklyReport_LessThanSevenDays_LessThanSevenDaysReport() throws FitNusException {
        EntryDatabase database = new EntryDatabase();
        LocalDate today = LocalDate.now();
        database.addEntry(new Entry(MealType.BREAKFAST, new Food("butter", 143, Food.FoodType.OTHERS),
                today));
        database.addEntry(new Entry(MealType.LUNCH, new Food("beans", 454, Food.FoodType.MEAL),
                today));
        database.addEntry(new Entry(MealType.DINNER, new Food("bacon", 608, Food.FoodType.MEAL),
                today));
        database.addEntry(new Entry(MealType.SNACK, new Food("milk", 198, Food.FoodType.BEVERAGE),
                today));
        database.addEntry(new Entry(MealType.SNACK, new Food("milk", 198, Food.FoodType.BEVERAGE),
                today));

        database.addEntry(new Entry(MealType.BREAKFAST, new Food("pears", 153, Food.FoodType.OTHERS),
                today.minusDays(1)));
        database.addEntry(new Entry(MealType.LUNCH, new Food("chicken rice", 649, Food.FoodType.MEAL),
                today.minusDays(1)));
        database.addEntry(new Entry(MealType.DINNER, new Food("broccoli", 674, Food.FoodType.MEAL),
                today.minusDays(1)));
        database.addEntry(new Entry(MealType.SNACK, new Food("best choice sugar", 165,
                Food.FoodType.BEVERAGE), today.minusDays(1)));
        database.addEntry(new Entry(MealType.SNACK, new Food("bread", 265, Food.FoodType.SNACK),
                today.minusDays(1)));


        database.addEntry(new Entry(MealType.BREAKFAST, new Food("soy mild", 112, Food.FoodType.BEVERAGE),
                today.minusDays(2)));
        database.addEntry(new Entry(MealType.LUNCH, new Food("beef", 680, Food.FoodType.MEAL),
                today.minusDays(2)));
        database.addEntry(new Entry(MealType.DINNER, new Food("tamale", 689, Food.FoodType.MEAL),
                today.minusDays(2)));
        database.addEntry(new Entry(MealType.SNACK, new Food("pickles", 160, Food.FoodType.OTHERS),
                today.minusDays(2)));
        database.addEntry(new Entry(MealType.SNACK, new Food("cheese", 109, Food.FoodType.OTHERS),
                today.minusDays(2)));
        database.addEntry(new Entry(MealType.SNACK, new Food("sausage", 221, Food.FoodType.SNACK),
                today.minusDays(2)));

        String output = String.format("%s: ################### 1971\n%s: ################### 1906\n"
                + "%s: ################ 1601\n", today.minusDays(2), today.minusDays(1), today);
        output += String.format("Average Daily Calorie Intake: ################## 1826\n");

        output += String.format("Food eaten most: [milk] [2 time(s)]\n");
        output += String.format("Food eaten least: [bacon, beans, beef, best choice sugar, bread, "
                + "broccoli, butter, cheese, chicken rice, pears, pickles, sausage, soy mild, tamale] [1 time(s)]");
        assertEquals(output, new ViewWeekSummaryCommand().execute(database, new FoodDatabase(), new MealPlanDatabase(),
                new User(2500, Gender.MALE, 25, 185, 80)));
    }

    @Test
    public void testWeekReport_noRecord_noRecordWarning() throws FitNusException {
        EntryDatabase database = new EntryDatabase();
        assertEquals("No entries found!", new ViewWeekSummaryCommand().execute(database, new FoodDatabase(),
                new MealPlanDatabase(),new User(2500, Gender.MALE, 25, 185, 80)));
    }

    @Test
    public void testWeekReport_oneDayRecord_oneDayReport() throws FitNusException {
        EntryDatabase database = new EntryDatabase();
        LocalDate today = LocalDate.now();
        database.addEntry(new Entry(MealType.BREAKFAST, new Food("butter", 143, Food.FoodType.OTHERS),
                today));
        database.addEntry(new Entry(MealType.LUNCH, new Food("beans", 454, Food.FoodType.MEAL),
                today));
        database.addEntry(new Entry(MealType.DINNER, new Food("bacon", 608, Food.FoodType.MEAL),
                today));
        database.addEntry(new Entry(MealType.SNACK, new Food("milk", 198, Food.FoodType.BEVERAGE),
                today));
        database.addEntry(new Entry(MealType.SNACK, new Food("milk", 198, Food.FoodType.BEVERAGE),
                today));
        String output = String.format("%s: ################ 1601\n", today);
        output += String.format("Average Daily Calorie Intake: ################ 1601\n");

        output += String.format("Food eaten most: [milk] [2 time(s)]\n");
        output += String.format("Food eaten least: [bacon, beans, butter] [1 time(s)]");
        assertEquals(output, new ViewWeekSummaryCommand().execute(database, new FoodDatabase(),
                new MealPlanDatabase(),new User(2500, Gender.MALE, 25, 185, 80)));
    }
}