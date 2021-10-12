package fitnus;

import fitnus.parser.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class User {
    private static final int MALE = 0;
    private static final int FEMALE = 1;
    private int calorieGoal;
    private int gender;
    private static final String DELIMITER = " | ";

    public User(int gender, int calorieGoal) {
        this.calorieGoal = calorieGoal;
        this.gender = (gender == 0) ? MALE : FEMALE;
    }

    public int getCalorieGoal() {
        return calorieGoal;
    }

    public void setCalorieGoal(int newGoal) throws FitNusException {
        if (newGoal < 0) {
            throw new FitNusException("Calorie Goal cannot be negative! Please try again!");
        } else if (newGoal == this.calorieGoal) {
            throw new FitNusException("Calorie Goal cannot be the same as before! Please try again!");
        }
        this.calorieGoal = newGoal;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        if (gender == MALE || gender == FEMALE) {
            this.gender = gender;
        }
    }

    public int showCaloriesRemaining(EntryDatabase entryDB) {
        int caloriesConsumed = entryDB.getTotalCalorie();
        return this.calorieGoal - caloriesConsumed;
    }

    public void preloadUserData(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] description = line.trim().split("\\s*[|]\\s*");
            try {
                this.calorieGoal = Integer.parseInt(description[0]);
                gender = Integer.parseInt(description[1]);
                System.out.println("Successfully preloaded user data");
            } catch (IndexOutOfBoundsException e) {
                Ui.printPreloadDatabaseError();
            } catch (NumberFormatException e) {
                Ui.printPreloadUserError();
            }
        }
    }

    public String listUserData() {
        return "Calorie goal: " + this.calorieGoal + " " + System.lineSeparator()
                + "Gender: " + (this.gender == 1 ? "Female" : "Male");
    }

    public String convertUserDataToString() {
        return this.calorieGoal + DELIMITER + this.gender;
    }
}
