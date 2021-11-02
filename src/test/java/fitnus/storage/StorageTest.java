package fitnus.storage;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.Food;
import fitnus.tracker.MealType;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


class StorageTest {
    private static final String ROOT = System.getProperty("user.dir");
    private static final Path FILE_PATH_FOOD_DATA = Paths.get(ROOT, "data", "food.txt");
    private static final Path FILE_PATH_ENTRY_DATA = Paths.get(ROOT, "data", "entry.txt");

    private static final String FOOD_DATA = "ramen | 600 | MEAL\n"
            + "rice | 800 | MEAL\n";
    private static final String ENTRY_DATA = "Lunch | ramen | 600 | 2021-10-25 | MEAL\n"
            + "Lunch | rice | 800 | 2021-10-25 | MEAL\n";


    //Utility method
    private static void saveData(String filePath, String content) throws IOException {
        File file = new File(filePath);
        FileWriter fw;
        fw = new FileWriter(file);
        fw.write(content);
        fw.close();
    }

    //Utility method
    private void initialiseFileTestContents() throws IOException {
        saveData(FILE_PATH_FOOD_DATA.toString(), FOOD_DATA);
        saveData(FILE_PATH_ENTRY_DATA.toString(), ENTRY_DATA);
    }

    @Test
    void initialiseFoodDatabase_validStorageFile_preloadSuccess()
            throws IOException, FitNusException {
        Storage.createDirectoryAndFiles();
        initialiseFileTestContents();
        FoodDatabase database = new FoodDatabase();
        Storage.initialiseFoodDatabase(database);
        String fileContent = Files.readString(FILE_PATH_FOOD_DATA);
        String expected = fileContent.replaceAll("\n", System.lineSeparator());
        assertEquals(expected, database.convertDatabaseToString());
    }

    @Test
    void initialiseFoodDatabase_fileNotExists_throwsAssertionError() throws IOException {
        Storage.createDirectoryAndFiles();
        initialiseFileTestContents();
        FoodDatabase database = new FoodDatabase();
        File file = new File(FILE_PATH_FOOD_DATA.toString());
        boolean isDeleted = file.delete();
        if (!isDeleted) {
            fail();
        }
        assertThrows(AssertionError.class, () ->
                Storage.initialiseFoodDatabase(database));
    }

    @Test
    void initialiseEntryDatabase_validStorageFile_preloadSuccess()
            throws IOException {
        Storage.createDirectoryAndFiles();
        initialiseFileTestContents();
        EntryDatabase database = new EntryDatabase();
        Storage.initialiseEntryDatabase(database);
        String fileContent = Files.readString(FILE_PATH_ENTRY_DATA);
        String expected = fileContent.replaceAll("\n", System.lineSeparator());
        assertEquals(expected, database.convertDatabaseToString());
    }

    @Test
    void initialiseEntryDatabase_invalidFilePath_throwsAssertionError() throws IOException {
        Storage.createDirectoryAndFiles();
        initialiseFileTestContents();
        EntryDatabase database = new EntryDatabase();
        File file = new File(FILE_PATH_ENTRY_DATA.toString());
        boolean isDeleted = file.delete();
        if (!isDeleted) {
            fail();
        }
        assertThrows(AssertionError.class, () ->
                Storage.initialiseEntryDatabase(database));
    }

    @Test
    void initialiseUser() {
        // Adeline can do this?
    }

    @Test
    void initialiseWeightProgress() {
        // Adeline can do this?
    }

    @Test
    void saveFoodDatabase_validFilePath_saveSuccessfully() throws FitNusException, IOException {
        Storage.createDirectoryAndFiles();
        initialiseFileTestContents();
        FoodDatabase database = new FoodDatabase();
        database.addFood("ramen", 400, Food.FoodType.MEAL);
        database.addFood("rice", 900, Food.FoodType.SNACK);

        String expected = database.convertDatabaseToString();
        Storage.saveFoodDatabase(database);
        String fileContent = Files.readString(FILE_PATH_FOOD_DATA);
        assertEquals(expected, fileContent);
    }

    @Test
    void saveFoodDatabase_invalidFilePath_throwsAssertionError()
            throws FitNusException, IOException {
        Storage.createDirectoryAndFiles();
        initialiseFileTestContents();
        FoodDatabase database = new FoodDatabase();
        database.addFood("ramen", 400, Food.FoodType.MEAL);
        database.addFood("rice", 900, Food.FoodType.SNACK);
        File file = new File(FILE_PATH_FOOD_DATA.toString());
        boolean isDeleted = file.delete();
        if (!isDeleted) {
            fail();
        }
        assertThrows(AssertionError.class, () -> Storage.saveFoodDatabase(database));
    }

    @Test
    void saveEntryDatabase_validFilePath_saveSuccessfully() throws IOException {
        Storage.createDirectoryAndFiles();
        initialiseFileTestContents();
        EntryDatabase database = new EntryDatabase();
        Food prata = new Food("Prata", 100, Food.FoodType.MEAL);
        Food chickenRice = new Food("Chicken Rice", 325, Food.FoodType.SNACK);
        database.addEntry(MealType.DINNER, prata);
        database.addEntry(MealType.DINNER, chickenRice);

        String expected = database.convertDatabaseToString();
        Storage.saveEntryDatabase(database);
        String fileContent = Files.readString(FILE_PATH_ENTRY_DATA);
        assertEquals(expected, fileContent);
    }

    @Test
    void saveEntryDatabase_invalidFilePath_throwsAssertionError() throws IOException {
        Storage.createDirectoryAndFiles();
        initialiseFileTestContents();
        EntryDatabase database = new EntryDatabase();
        Food prata = new Food("Prata", 100, Food.FoodType.MEAL);
        Food chickenRice = new Food("Chicken Rice", 325, Food.FoodType.SNACK);
        database.addEntry(MealType.DINNER, prata);
        database.addEntry(MealType.DINNER, chickenRice);

        File file = new File(FILE_PATH_ENTRY_DATA.toString());
        boolean isDeleted = file.delete();
        if (!isDeleted) {
            fail();
        }

        assertThrows(AssertionError.class, () -> Storage.saveEntryDatabase(database));
    }

    @Test
    void saveUserData() {
        //Adeline
    }

    @Test
    void saveWeightData() {
        //Adeline
    }
}