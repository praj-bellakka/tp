package fitnus.utility;

import fitnus.database.EntryDatabase;
import fitnus.exception.FitNusException;
import fitnus.tracker.WeightProgressEntry;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class User {
    private static final int MALE = 0;
    private static final int FEMALE = 1;
    private int calorieGoal;
    private int gender;
    private int age;
    private int height;
    private static final String DELIMITER = " | ";
    private float weight;
    private final ArrayList<WeightProgressEntry> weightProgressEntries = new ArrayList<>();

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
        assert newGoal >= 0 : "calorie goal cannot be negative";
        this.calorieGoal = newGoal;
    }

    public int getGender() {
        assert gender == FEMALE || gender == MALE : "invalid gender setting";
        return gender;
    }

    public void setGender(int gender) {
        if (gender == MALE || gender == FEMALE) {
            this.gender = gender;
        }
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String updateWeightAndWeightTracker(float newWeight) {
        this.setWeight(newWeight);

        LocalDate currDate = LocalDate.now();
        if (weightProgressEntries.size() == 0) {
            weightProgressEntries.add(new WeightProgressEntry(newWeight, currDate));
            return "You have updated your weight for today to " + newWeight + " kg!";
        }

        WeightProgressEntry latestEntry = weightProgressEntries.get(weightProgressEntries.size() - 1);
        if (latestEntry.getDate().toString().equals(currDate.toString())) { //Update today's weight progress entry
            latestEntry.setWeight(newWeight);
            weightProgressEntries.set(weightProgressEntries.size() - 1, latestEntry);
        } else {
            weightProgressEntries.add(new WeightProgressEntry(newWeight, currDate));
        }

        WeightProgressEntry previousEntry = weightProgressEntries.get(weightProgressEntries.size() - 2);
        float weightDifference = previousEntry.getWeight() - newWeight;
        String weightChange = weightDifference < 0 ? "gained" : "lost";
        if (weightDifference < 0) {
            weightDifference = Math.abs(weightDifference);
        }

        return "You have updated your weight for today to " + newWeight
                + "! You have " + weightChange + " " + weightDifference + " kg from the previous weight entry of "
                + previousEntry.getWeight() + " kg on " + previousEntry.getDate().toString();
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

    public void preloadWeightData(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] description = line.trim().split("\\s*[|]\\s*");
            try {
                float weight = Float.parseFloat(description[0]);
                LocalDate date = LocalDate.parse(description[1]);
                weightProgressEntries.add(new WeightProgressEntry(weight, date));
            } catch (IndexOutOfBoundsException e) {
                //Ui.printPreloadDatabaseError();
            } catch (NumberFormatException e) {
                //Ui.printPreloadUserError();
            }
        }
        System.out.println("Successfully preloaded weight data");
    }

    public String listUserData() {
        return "Calorie goal: " + this.calorieGoal + " " + System.lineSeparator()
                + "Gender: " + (this.gender == 1 ? "Female" : "Male");
    }

    public String convertUserDataToString() {
        return this.calorieGoal + DELIMITER + this.gender;
    }
}
