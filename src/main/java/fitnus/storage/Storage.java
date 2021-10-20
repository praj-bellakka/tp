package fitnus.storage;

import fitnus.database.EntryDatabase;
import fitnus.database.FoodDatabase;
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

public class Storage {

    private static final String ROOT = System.getProperty("user.dir");
    private static final Path DIRECTORY_PATH = Paths.get(ROOT, "data");
    private static final Path FILE_PATH_FOOD_DATA = Paths.get(ROOT, "data", "food.txt");
    private static final Path FILE_PATH_USER_DATA = Paths.get(ROOT, "data", "user.txt");
    private static final Path FILE_PATH_ENTRY_DATA = Paths.get(ROOT, "data", "entry.txt");

    public static void createDirectoryAndFiles() throws IOException {
        createDirectory(DIRECTORY_PATH.toString());
        createFile(FILE_PATH_FOOD_DATA.toString());
        createFile(FILE_PATH_USER_DATA.toString());
        createFile(FILE_PATH_ENTRY_DATA.toString());
        assert Files.exists(DIRECTORY_PATH);
        assert Files.exists(FILE_PATH_FOOD_DATA);
        assert Files.exists(FILE_PATH_USER_DATA);
        assert Files.exists(FILE_PATH_ENTRY_DATA);
    }

    public static void initialiseFoodDatabase(FoodDatabase database) throws IOException, FitNusException {
        assert Files.exists(FILE_PATH_FOOD_DATA);
        FileInputStream stream;
        stream = new FileInputStream(FILE_PATH_FOOD_DATA.toString());
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        database.preLoadDatabase(reader);
        reader.close();
    }

    public static void initialiseEntryDatabase(EntryDatabase database) throws IOException {
        assert Files.exists(FILE_PATH_ENTRY_DATA);
        FileInputStream stream;
        stream = new FileInputStream(FILE_PATH_ENTRY_DATA.toString());
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        database.preLoadDatabase(reader);
        reader.close();
    }

    public static void initialiseUser(User user) throws IOException {
        assert Files.exists(FILE_PATH_USER_DATA);
        FileInputStream stream;
        stream = new FileInputStream(FILE_PATH_USER_DATA.toString());
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        user.preloadUserData(reader);
        reader.close();
    }

    public static void saveFoodDatabase(FoodDatabase database) throws IOException {
        assert Files.exists(FILE_PATH_FOOD_DATA);
        String data = database.convertDatabaseToString();
        saveData(FILE_PATH_FOOD_DATA.toString(), data);
    }

    public static void saveEntryDatabase(EntryDatabase database) throws IOException {
        assert Files.exists(FILE_PATH_ENTRY_DATA);
        String data = database.convertDatabaseToString();
        saveData(FILE_PATH_ENTRY_DATA.toString(), data);
    }

    public static void saveUserData(User user) throws IOException {
        assert Files.exists(FILE_PATH_USER_DATA);
        String userData = user.convertUserDataToString();
        saveData(FILE_PATH_USER_DATA.toString(), userData);
    }

    public static void saveData(String filePath, String content) throws IOException {
        File file = new File(filePath);
        FileWriter fw;
        fw = new FileWriter(file);
        fw.write(content);
        fw.close();
    }

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
