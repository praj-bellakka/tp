package fitnus.storage;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
import fitnus.database.MealPlanDatabase;
import fitnus.exception.FitNusException;
import fitnus.utility.User;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Handles dealing with the files and directory used to save data.
 */
public class Storage {

    private static final String ROOT = System.getProperty("user.dir");
    private static final Path DIRECTORY_PATH = Paths.get(ROOT, "data");
    private static final Path FILE_PATH_FOOD_DATA = Paths.get(ROOT, "data", "food.txt");
    private static final Path FILE_PATH_USER_DATA = Paths.get(ROOT, "data", "user.txt");
    private static final Path FILE_PATH_ENTRY_DATA = Paths.get(ROOT, "data", "entry.txt");
    private static final Path FILE_PATH_WEIGHT_DATA = Paths.get(ROOT, "data", "weight.txt");
    private static final Path FILE_PATH_MEALPLAN_DATA = Paths.get(ROOT, "data", "mealplan.txt");


    /**
     * Creates the required directory and files if they do not exist.
     *
     * @throws IOException If an I/O error occurs.
     */
    public static void createDirectoryAndFiles() throws IOException {
        createDirectory(DIRECTORY_PATH.toString());
        createFile(FILE_PATH_FOOD_DATA.toString());
        createFile(FILE_PATH_USER_DATA.toString());
        createFile(FILE_PATH_ENTRY_DATA.toString());
        createFile(FILE_PATH_WEIGHT_DATA.toString());
        createFile(FILE_PATH_MEALPLAN_DATA.toString());
        assert Files.exists(DIRECTORY_PATH);
        assert Files.exists(FILE_PATH_FOOD_DATA);
        assert Files.exists(FILE_PATH_USER_DATA);
        assert Files.exists(FILE_PATH_ENTRY_DATA);
        assert Files.exists(FILE_PATH_WEIGHT_DATA);
        assert Files.exists(FILE_PATH_MEALPLAN_DATA);
    }

    /**
     * Reads the file content and calls preloadDatabase to preload the
     * FoodDatabase with data from the file.
     *
     * @param database FoodDatabase object to preload.
     * @throws IOException     If an I/O error occurs.
     * @throws FitNusException If an error occurs while preloading.
     */
    public static void initialiseFoodDatabase(FoodDatabase database) throws IOException, FitNusException {
        assert Files.exists(FILE_PATH_FOOD_DATA);
        FileInputStream stream;
        stream = new FileInputStream(FILE_PATH_FOOD_DATA.toString());
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        database.preloadDatabase(reader);
        reader.close();
    }

    /**
     * Reads the file content and calls preloadDatabase to preload the
     * EntryDatabase with data from the file.
     *
     * @param database EntryDatabase object to preload.
     * @throws IOException If an I/O error occurs.
     */
    public static void initialiseEntryDatabase(EntryDatabase database) throws IOException {
        assert Files.exists(FILE_PATH_ENTRY_DATA);
        FileInputStream stream;
        stream = new FileInputStream(FILE_PATH_ENTRY_DATA.toString());
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        database.preloadDatabase(reader);
        reader.close();
    }

    /**
     * Reads the file content and calls preloadDatabase to preload the
     * MealPlanDatabase with data from the file.
     *
     * @param database MealPlanDatabase object to preload.
     * @throws IOException If an I/O error occurs.
     */
    public static void initialiseMealPlanDatabase(MealPlanDatabase database) throws IOException {
        assert Files.exists(FILE_PATH_MEALPLAN_DATA);
        FileInputStream stream;
        stream = new FileInputStream(FILE_PATH_MEALPLAN_DATA.toString());
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        database.preloadDatabase(reader);
        reader.close();
    }

    /**
     * Reads the file content and calls preloadUserData to preload
     * the User data with data from the file.
     *
     * @param user User object to preload.
     * @return True if data is valid, false otherwise.
     * @throws IOException If an I/O error occurs.
     */
    public static boolean initialiseUser(User user) throws IOException {
        assert Files.exists(FILE_PATH_USER_DATA);
        FileInputStream stream;
        stream = new FileInputStream(FILE_PATH_USER_DATA.toString());
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        boolean userDataIsValid = user.preloadUserData(reader);
        reader.close();
        return userDataIsValid;
    }

    /**
     * Reads the file content and calls preloadWeightData to preload
     * the user's weight data with data from the file.
     *
     * @param user User object to preload.
     * @throws IOException If an I/O error occurs.
     */
    public static void initialiseWeightProgress(User user) throws IOException {
        assert Files.exists(FILE_PATH_WEIGHT_DATA);
        FileInputStream stream;
        stream = new FileInputStream(FILE_PATH_WEIGHT_DATA.toString());
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        user.preloadWeightData(reader);
        reader.close();
    }

    /**
     * Saves all the FoodDatabase data to file.
     *
     * @param database FoodDatabase object to save.
     * @throws IOException If an I/O error occurs.
     */
    public static void saveFoodDatabase(FoodDatabase database) throws IOException {
        assert Files.exists(FILE_PATH_FOOD_DATA);
        String data = database.convertDatabaseToString();
        saveData(FILE_PATH_FOOD_DATA.toString(), data);
    }

    /**
     * Saves all the EntryDatabase data to file.
     *
     * @param database EntryDatabase object to save.
     * @throws IOException If an I/O error occurs.
     */
    public static void saveEntryDatabase(EntryDatabase database) throws IOException {
        assert Files.exists(FILE_PATH_ENTRY_DATA);
        String data = database.convertDatabaseToString();
        saveData(FILE_PATH_ENTRY_DATA.toString(), data);
    }

    /**
     * Saves all the MealPlanDatabase data to file.
     *
     * @param database MealPlanDatabase object to save.
     * @throws IOException If an I/O error occurs.
     */
    public static void saveMealPlanDatabase(MealPlanDatabase database) throws IOException {
        assert Files.exists(FILE_PATH_MEALPLAN_DATA);
        String data = database.convertDatabaseToString();
        saveData(FILE_PATH_MEALPLAN_DATA.toString(), data);
    }

    /**
     * Saves all the User data to file.
     *
     * @param user User object to save.
     * @throws IOException If an I/O error occurs.
     */
    public static void saveUserData(User user) throws IOException {
        assert Files.exists(FILE_PATH_USER_DATA);
        String userData = user.convertUserDataToString();
        saveData(FILE_PATH_USER_DATA.toString(), userData);
    }

    /**
     * Saves all weight data to file.
     *
     * @param user User object to save.
     * @throws IOException If an I/O error occurs.
     */
    public static void saveWeightData(User user) throws IOException {
        assert Files.exists(FILE_PATH_WEIGHT_DATA);
        String weightData = user.convertWeightDataToString();
        saveData(FILE_PATH_WEIGHT_DATA.toString(), weightData);
    }

    /**
     * Writes the String content provided to the file at the specified filePath.
     *
     * @param filePath The filePath of the file to be written to.
     * @param content  String content to be written to the file.
     * @throws IOException If an I/O error occurs.
     */
    private static void saveData(String filePath, String content) throws IOException {
        File file = new File(filePath);
        FileWriter fw;
        fw = new FileWriter(file);
        fw.write(content);
        fw.close();
    }

    /**
     * Creates a new file at the filePath if it does not exist.
     *
     * @param filePath The filePath of the File to be created.
     * @throws IOException If an I/O error occurs.
     */
    // @@author brendanlsz-reused
    // Reused from https://www.tutorialspoint.com/java/io/file_createnewfile.htm
    // with modifications
    private static void createFile(String filePath) throws IOException {
        File file = new File(filePath);
        boolean hasCreatedFile = file.createNewFile();
        if (hasCreatedFile) {
            System.out.println("File created at " + file.getCanonicalPath());
        }
    }

    /**
     * Creates a directory at directoryPath if it does not exist.
     *
     * @param directoryPath The directoryPath of the directory to be created.
     */
    // @@author brendanlsz-reused
    // Reused from https://www.tutorialspoint.com/java/io/file_mkdir.htm
    // with modifications
    private static void createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        boolean hasCreatedDirectory = directory.mkdir();
        if (hasCreatedDirectory) {
            System.out.println("New directory created at " + directoryPath);
        }
    }


}
