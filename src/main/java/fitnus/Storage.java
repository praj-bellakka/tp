package fitnus;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileWriter;
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
    }

    public static void initialiseFoodDatabase(FoodDatabase database) throws IOException {
        FileInputStream stream;
        stream = new FileInputStream(FILE_PATH_FOOD_DATA.toString());
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        database.preLoadDatabase(reader);
        reader.close();
    }

    public static void initialiseEntryDatabase(EntryDatabase database) throws IOException {
        FileInputStream stream;
        stream = new FileInputStream(FILE_PATH_ENTRY_DATA.toString());
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        database.preLoadDatabase(reader);
        reader.close();
    }

    public static void saveFoodDatabase(FoodDatabase database) throws IOException {
        String currentFoods = database.convertDatabaseToString();
        saveData(FILE_PATH_FOOD_DATA.toString(), currentFoods);
    }

    public static void saveEntryDatabase(EntryDatabase database) throws IOException {
        String currentFoods = database.convertDatabaseToString();
        saveData(FILE_PATH_ENTRY_DATA.toString(), currentFoods);
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
