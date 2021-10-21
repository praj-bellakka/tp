package fitnus.tracker;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.utility.Ui;
import fitnus.utility.User;
import org.junit.jupiter.api.Test;


import static fitnus.FitNus.initialiseFitNus;
import static fitnus.FitNus.printPreloadedData;

class SummaryTest {
    @Test
    public void test_generateWeeklyReport() {
        User user = new User(0, 1000); //placeholder inputs, to get user's actual input later
        FoodDatabase fd = new FoodDatabase();
        EntryDatabase ed = new EntryDatabase();

        // Init
        Ui.printWelcomeMessage();
        initialiseFitNus(fd, ed, user);
        printPreloadedData(fd, ed, user);

        System.out.println(new Summary(ed).generateWeekSummaryReport());
    }
}