package fitnus.utility;

import fitnus.database.EntryDatabase;
import fitnus.exception.FitNusException;
import fitnus.parser.Parser;
import fitnus.tracker.Gender;
import fitnus.tracker.WeightProgressEntry;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {
    private static final Logger logger = Logger.getLogger(User.class.getName());

    private int calorieGoal;
    private Gender gender;
    private int age;
    private int height;
    private float weight;
    private final ArrayList<WeightProgressEntry> weightProgressEntries = new ArrayList<>();

    private static final int MALE = 0;
    private static final int FEMALE = 1;
    private static final String DELIMITER = " | ";
    private static final String GAIN_STRING = "gain";
    private static final String LOSE_STRING = "lose";

    public User(int calorieGoal, Gender gender, int age, int height, float weight) {
        this.calorieGoal = calorieGoal;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    public int getCalorieGoal() {
        return calorieGoal;
    }

    public void setCalorieGoal(int newGoal) throws FitNusException {
        if (newGoal < 0) {
            logger.log(Level.INFO, "Calorie goal entered was negative");
            throw new FitNusException("Calorie Goal cannot be negative! Please try again!");
        } else if (newGoal == this.calorieGoal) {
            logger.log(Level.INFO, "Calorie goal entered was same as before");
            throw new FitNusException("Calorie Goal cannot be the same as before! Please try again!");
        }
        assert newGoal >= 0 : "calorie goal cannot be negative";
        this.calorieGoal = newGoal;
    }

    public Gender getGender() {
        assert gender.equals(Gender.FEMALE) || gender.equals(Gender.MALE) : "invalid gender setting";
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ArrayList<WeightProgressEntry> getWeightProgressEntries() {
        return weightProgressEntries;
    }

    public void addToWeightProgressEntries(WeightProgressEntry entry) {
        weightProgressEntries.add(entry);
    }

    /**
     * Sets the user's weight to the new weight and updates the daily weight tracker accordingly.
     *
     * @param newWeight New weight to be set.
     * @return The outcome message.
     */
    public String updateWeightAndWeightTracker(float newWeight) {
        logger.log(Level.INFO, "Begin attempt to update weight and weight tracker");

        this.setWeight(newWeight);

        LocalDate currDate = LocalDate.now();
        if (weightProgressEntries.size() == 0) {
            weightProgressEntries.add(new WeightProgressEntry(newWeight, currDate));
            logger.log(Level.INFO, "New weight entry added to weight tracker");
            return "You have updated your weight for today to " + newWeight + " kg!";
        }

        WeightProgressEntry latestEntry = weightProgressEntries.get(weightProgressEntries.size() - 1);
        if (latestEntry.getDate().toString().equals(currDate.toString())) { //Update today's weight progress entry
            latestEntry.setWeight(newWeight);
            weightProgressEntries.set(weightProgressEntries.size() - 1, latestEntry);
            logger.log(Level.INFO, "Latest weight tracker entry updated (No new entry added)");
        } else {
            weightProgressEntries.add(new WeightProgressEntry(newWeight, currDate));
            logger.log(Level.INFO, "New weight entry added to weight tracker");
        }

        logger.log(Level.INFO, "Update weight and weight tracker completed");

        if (weightProgressEntries.size() >= 2) { //If weight tracker has
            // more than 2 entries after updating the weight accordingly
            WeightProgressEntry previousEntry = weightProgressEntries.get(weightProgressEntries.size() - 2);
            float weightDifference = previousEntry.getWeight() - newWeight;
            weightDifference = (float) (Math.round(weightDifference * 10.0) / 10.0);
            String weightChange = weightDifference < 0 ? "gained" : "lost";
            if (weightDifference < 0) {
                weightDifference = Math.abs(weightDifference);
            }

            return "You have updated your weight for today to " + newWeight
                    + " kg! You have " + weightChange + " " + weightDifference
                    + " kg from the previous weight entry of "
                    + previousEntry.getWeight() + " kg on " + previousEntry.getDate().toString();
        } else {
            return "You have updated your weight for today to " + newWeight + " kg!";
        }
    }

    public String convertWeightRecordsToStringForUi() throws FitNusException {
        StringBuilder lines = new StringBuilder();

        if (weightProgressEntries.size() == 0) {
            throw new FitNusException("An error has occurred! No weight records found.");
        }

        for (WeightProgressEntry e : weightProgressEntries) {
            assert e != null : "e should not be null";
            float weight = e.getWeight();
            String date = e.getDate().toString();
            lines.append(date).append(": ").append(weight).append("kg").append(System.lineSeparator());
        }
        return lines.toString();
    }

    public String getWeightProgressDisplay() throws FitNusException {
        if (weightProgressEntries.size() == 0) {
            return "You have not recorded your weight before! "
                    + "Try recording your weight today using the weight /set command.";
        } else if (weightProgressEntries.size() == 1) {
            return "Your weight progress: \n"
                    + convertWeightRecordsToStringForUi();
        } else {
            float startingWeight = weightProgressEntries.get(0).getWeight();
            float currentWeight = weightProgressEntries.get(weightProgressEntries.size() - 1).getWeight();

            float weightChange = startingWeight - currentWeight;
            String changeType = weightChange < 0 ? "gained" : "lost";

            weightChange = Math.abs(weightChange);

            return "Your weight progress: \n"
                    + convertWeightRecordsToStringForUi()
                    + "\n"
                    + "You have " + changeType + " " + weightChange + " kg since the start of your FitNUS journey!";
        }
    }

    public int getCaloriesRemaining(EntryDatabase entryDB) {
        int caloriesConsumed = entryDB.getTotalDailyCalorie();
        return this.calorieGoal - caloriesConsumed;
    }

    public int generateCalorieGoal(float weeklyChange, String changeType) throws FitNusException {
        if (!(changeType == GAIN_STRING || changeType == LOSE_STRING)) {
            throw new FitNusException("An error has occurred! The change type is invalid.");
        }

        if (weeklyChange < 0) {
            throw new FitNusException("Please enter a positive value for the weekly change!");
        } else if (weeklyChange >= 1) {
            throw new FitNusException("In order to lose or gain weight in a safe and healthy way,\n"
                    + "FitNUS recommends a weekly change in weight of not more than\n"
                    + "1 kg. Please try again with a lower weekly goal!");
        }

        int calDeficitFor1KgWeekly = 1000;

        int bmr; //basal metabolic rate i.e. calories needed to maintain weight
        int calDiff = Math.round(weeklyChange * calDeficitFor1KgWeekly);
        int newGoal = 0;
        if (this.gender == Gender.MALE) {
            bmr = (int) Math.round(((655.1 + (9.563 * this.weight)
                    + (1.850 * this.height)
                    - (4.676 * this.age)) * 1.55));
        } else {
            bmr = (int) Math.round(((66.47 + (13.75 * this.weight)
                    + (5.003 * this.height)
                    - (6.755 * this.age)) * 1.55));
        }

        if (changeType == GAIN_STRING) {
            newGoal = bmr + calDiff;
        } else if (changeType == LOSE_STRING) {
            newGoal = bmr - calDiff;
        }

        return newGoal;
    }

    public int preloadUserData(BufferedReader reader) throws IOException {
        String line;
        boolean successfullyPreloadedData = false;

        if ((line = reader.readLine()) != null) { //user data file not empty
            String[] description = line.trim().split("\\s*[|]\\s*");

            try {
                this.calorieGoal = Integer.parseInt(description[0]);
                this.gender = Gender.findGender(description[1]);
                this.age = Integer.parseInt(description[2]);
                this.height = Integer.parseInt(description[3]);
                this.weight = Float.parseFloat(description[4]);
                System.out.println("Successfully preloaded user data");
                successfullyPreloadedData = true;
            } catch (IndexOutOfBoundsException e) {
                successfullyPreloadedData = false;
                logger.log(Level.WARNING, "Error processing user data (missing inputs)");
                Ui.printPreloadDatabaseError();
            } catch (NumberFormatException e) {
                successfullyPreloadedData = false;
                logger.log(Level.WARNING, "Error processing user data (invalid inputs for numerical fields)");
                Ui.printPreloadUserError();
            }
        }

        if (successfullyPreloadedData == false) {
            return 0; //failure
        }

        return 1; //success
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
                logger.log(Level.WARNING, "Error processing weight data (missing inputs)");
                Ui.printPreloadDatabaseError();
            } catch (NumberFormatException e) {
                logger.log(Level.WARNING, "Error processing weight data (invalid inputs for numerical fields)");
                Ui.printPreloadUserError();
            }
        }
        System.out.println("Successfully preloaded weight data");
    }

    public String getUserDataDisplay() {
        return "Calorie Goal: " + this.calorieGoal + " " + System.lineSeparator()
                + "Gender: " + this.gender.toString() + System.lineSeparator()
                + "Age: " + this.age + System.lineSeparator()
                + "Weight: " + this.weight + System.lineSeparator()
                + "Height: " + this.height + System.lineSeparator();
    }

    public String convertUserDataToString() {
        return this.calorieGoal + DELIMITER
                + this.gender + DELIMITER
                + this.age + DELIMITER
                + this.height + DELIMITER
                + this.weight;
    }

    public String convertWeightDataToString() {
        StringBuilder lines = new StringBuilder();
        for (WeightProgressEntry e : weightProgressEntries) {
            assert e != null : "e should not be null";
            float weight = e.getWeight();
            String date = e.getDate().toString();
            lines.append(weight).append(DELIMITER).append(date).append(System.lineSeparator());
        }
        return lines.toString();
    }
}
