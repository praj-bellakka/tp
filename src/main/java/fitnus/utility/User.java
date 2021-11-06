package fitnus.utility;

import fitnus.database.EntryDatabase;
import fitnus.exception.FitNusException;
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

    //constants
    private static final String DELIMITER = " | ";
    private static final String GAIN_STRING = "gain";
    private static final String LOSE_STRING = "lose";
    private static final float MINIMUM_WEEKLY_CHANGE = (float) 0.01;
    private static final float MAXIMUM_WEEKLY_CHANGE = (float) 1.0;
    private static final int ALL_MONTHS = 0;
    private static final String[] monthStrings = {"January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"};

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

    /**
     * Sets the user's calorie goal.
     *
     * @param newGoal New calorie goal to be set.
     * @throws FitNusException when the calorie goal is out of the healthy range for the user.
     */
    public void setCalorieGoal(int newGoal) throws FitNusException {
        int minimumCalorieGoal = calculateCalorieGoal(1, "lose");
        int maximumCalorieGoal = calculateCalorieGoal(1, "gain");

        if (newGoal < minimumCalorieGoal) {
            throw new FitNusException("Your calorie goal cannot be lower than " + minimumCalorieGoal
                    + " kcal as this would exceed the recommended healthy amount\n"
                    + "of weight loss for your body type!\n"
                    + "Please try again.");
        } else if (newGoal > maximumCalorieGoal) {
            throw new FitNusException("Your calorie goal cannot be higher than " + maximumCalorieGoal
                    + " kcal as this would exceed the recommended healthy amount\n"
                    + "of weight gain for your body type!\n"
                    + "Please try again.");
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
        assert newWeight > 0 : "newWeight should be greater than 0";

        this.setWeight(newWeight);

        LocalDate currDate = LocalDate.now();
        if (weightProgressEntries.size() == 0) {
            weightProgressEntries.add(new WeightProgressEntry(newWeight, currDate));
            return "You have updated your weight for today to " + newWeight + " kg!";
        }

        updateWeightTrackerIfHavePreviousEntries(newWeight, currDate);

        if (weightProgressEntries.size() >= 2) { //If weight tracker has more than 2 entries after updating
            WeightProgressEntry previousEntry = weightProgressEntries.get(weightProgressEntries.size() - 2);
            float weightDifference = getWeightDifference(newWeight, previousEntry);
            String changeType = getChangeType(weightDifference);
            weightDifference = Math.abs(weightDifference);
            assert weightDifference >= 0 : "weightDifference should not be negative";

            return "You have updated your weight for today to " + newWeight
                    + " kg!\nYou have " + changeType + " " + weightDifference
                    + " kg from the previous weight entry of "
                    + previousEntry.getWeight() + " kg on " + previousEntry.getDate().toString();
        } else {
            return "You have updated your weight for today to " + newWeight + " kg!";
        }
    }

    /**
     * Updates the daily weight tracker for the case where the weight tracker
     * has existing entries.
     *
     * @param newWeight New weight to be set.
     * @param currDate  The current date.
     */
    public void updateWeightTrackerIfHavePreviousEntries(float newWeight, LocalDate currDate) {
        WeightProgressEntry latestEntry = weightProgressEntries.get(weightProgressEntries.size() - 1);
        if (latestEntry.getDate().toString().equals(currDate.toString())) { //Update today's weight progress entry
            latestEntry.setWeight(newWeight);
            weightProgressEntries.set(weightProgressEntries.size() - 1, latestEntry);
        } else {
            weightProgressEntries.add(new WeightProgressEntry(newWeight, currDate));
        }
    }

    /**
     * Gets the difference in weight between the new weight and the previous entry in the weight tracker.
     * The difference is positive if the user lost weight and negative if the user gained weight.
     *
     * @param newWeight New weight to be set.
     * @return The weight difference.
     */
    public float getWeightDifference(float newWeight, WeightProgressEntry previousEntry) {
        float weightDifference = previousEntry.getWeight() - newWeight;
        weightDifference = (float) (Math.round(weightDifference * 10.0) / 10.0);
        return weightDifference;
    }

    /**
     * Gets the correct weight change type string depending on whether weight was gained or lost.
     *
     * @param weightDifference The difference in weight.
     * @return The change type string.
     */
    public String getChangeType(float weightDifference) {
        return weightDifference < 0 ? "gained" : "lost";
    }

    /**
     * Converts the weightProgressEntries ArrayList into a String of the list of weight records.
     *
     * @return The list of weight records.
     * @throws FitNusException if weightProgressEntries is empty.
     */
    public String convertWeightRecordsToStringForUi(ArrayList<WeightProgressEntry> relevantEntries)
            throws FitNusException {
        StringBuilder lines = new StringBuilder();

        if (relevantEntries.size() == 0) {
            throw new FitNusException("An error has occurred! No weight records found.");
        }

        boolean isFirstEntry = true;
        for (WeightProgressEntry e : relevantEntries) {
            assert e != null : "e should not be null";
            float weight = e.getWeight();
            String date = e.getDate().toString();
            if (isFirstEntry) {
                lines.append(date).append(": ").append(weight).append("kg");
                isFirstEntry = false;
            } else {
                lines.append(System.lineSeparator()).append(date).append(": ").append(weight).append("kg");
            }
        }
        return lines.toString();
    }

    /**
     * Creates a String that displays the weight tracker for the UI.
     *
     * @return The weight tracker display.
     * @throws FitNusException if weightProgressEntries is empty.
     */
    public String getWeightProgressDisplay(int month) throws FitNusException {
        ArrayList<WeightProgressEntry> relevantEntries = new ArrayList<>();

        relevantEntries = getRelevantWeightEntries(month);

        if (relevantEntries.size() == 0) {
            if (month == ALL_MONTHS) {
                return "You have not recorded your weight before! "
                        + "Try recording your weight today using the weight /set command.";
            } else {
                return "You did not record your weight in the month of " + monthStrings[month - 1] + "!";
            }
        } else if (relevantEntries.size() == 1) {
            if (month == ALL_MONTHS) {
                return "Your weight progress since the start of your FitNUS journey: \n"
                        + convertWeightRecordsToStringForUi(relevantEntries);
            } else {
                return "Your weight progress in " + monthStrings[month - 1] + ": \n"
                        + convertWeightRecordsToStringForUi(relevantEntries);
            }
        } else {
            WeightProgressEntry previousEntry = relevantEntries.get(0);
            float currentWeight = relevantEntries.get(relevantEntries.size() - 1).getWeight();

            float weightDifference = getWeightDifference(currentWeight, previousEntry);
            String changeType = getChangeType(weightDifference);
            weightDifference = Math.abs(weightDifference);

            if (month == ALL_MONTHS) {
                return "Your weight progress since the start of your FitNUS journey: \n"
                        + convertWeightRecordsToStringForUi(relevantEntries)
                        + "\n"
                        + "You have " + changeType + " " + weightDifference
                        + " kg since the start of your FitNUS Journey!";
            } else {
                return "Your weight progress in " + monthStrings[month - 1] + ": \n"
                        + convertWeightRecordsToStringForUi(relevantEntries)
                        + "\n"
                        + "You have " + changeType + " " + weightDifference + " kg during the month of "
                        + monthStrings[month - 1] + "!";
            }
        }
    }

    /**
     * Gets the relevant weight tracker entries according to timeframe.
     *
     * @param month The integer representation of the month or 0 to represent "all time".
     * @return The relevant weight tracker entries.
     */
    public ArrayList<WeightProgressEntry> getRelevantWeightEntries(int month) {
        ArrayList<WeightProgressEntry> relevantEntries = new ArrayList<>();
        if (month == ALL_MONTHS) {
            relevantEntries = weightProgressEntries;
        } else {
            for (WeightProgressEntry e : weightProgressEntries) {
                if (e.getDate().getMonthValue() == month) {
                    relevantEntries.add(e);
                }
            }
        }
        return relevantEntries;
    }

    /**
     * Gets the number of calories remaining for the day according to the calorie goal.
     *
     * @param entryDB The entry database linked to this user.
     * @return Number of calories remaining for the day.
     */
    public int getCaloriesRemaining(EntryDatabase entryDB) {
        int caloriesConsumed = entryDB.getTotalDailyCalorie();
        return this.calorieGoal - caloriesConsumed;
    }

    /**
     * Handles the generate calorie goal command.
     * It generates a calorie goal according to the user's height,
     * weight, gender and age, as well as the desired weekly change
     * in weight and type of change (gain or lose).
     *
     * @param weeklyChange The desired weekly change in weight in kg.
     * @param changeType   The desired change type (gain or lose)
     * @return Generated calorie goal
     * @throws FitNusException if the provided changeType does not match
     *                         GAIN_STRING or LOSE_STRING or if the weekly change is greater than
     *                         MAXIMUM_WEEKLY_CHANGE or is a negative value
     */
    public int handleGenerateCalorieGoalCommand(float weeklyChange, String changeType) throws FitNusException {
        if (!(changeType == GAIN_STRING || changeType == LOSE_STRING)) {
            throw new FitNusException("An error has occurred! The change type is invalid.");
        }

        if (weeklyChange < 0) {
            throw new FitNusException("Please enter a positive value for the weekly change!");
        } else if (weeklyChange > MAXIMUM_WEEKLY_CHANGE) {
            throw new FitNusException("In order to lose or gain weight in a safe and healthy way,\n"
                    + "FitNUS recommends a weekly change in weight of not more than\n"
                    + MAXIMUM_WEEKLY_CHANGE + " kg. Please try again with a lower weekly goal!");
        } else if (weeklyChange < MINIMUM_WEEKLY_CHANGE) {
            System.out.println("ALERT: If you would like to lose or gain weight,\n"
                    + "please enter a weekly change of "
                    + MINIMUM_WEEKLY_CHANGE + " kg or more!\n"
                    + "The new goal that has been generated will allow you to maintain your current weight.");
        }

        int newGoal = calculateCalorieGoal(weeklyChange, changeType);
        return newGoal;
    }

    /**
     * Calculate a calorie goal according to the user's height,
     * weight, gender and age, as well as the desired weekly change
     * in weight and type of change (gain or lose).
     *
     * @param weeklyChange The desired weekly change in weight in kg.
     * @param changeType   The desired change type (gain or lose)
     * @return The calculated calorie goal.
     */
    public int calculateCalorieGoal(float weeklyChange, String changeType) {
        int calDeficitFor1KgWeekly = 1000;
        int bmr = calculateBasalMetabolicRate(); //basal metabolic rate i.e. calories needed to maintain weight
        int calDiff = Math.round(weeklyChange * calDeficitFor1KgWeekly);
        int newGoal = 0;

        if (changeType == GAIN_STRING) {
            newGoal = bmr + calDiff;
        } else if (changeType == LOSE_STRING) {
            newGoal = bmr - calDiff;
        }

        return newGoal;
    }

    /**
     * Calculate the basal metabolic rate (BMR)
     * according to the Harris-Benedict Equation.
     *
     * @return The calculated BMR.
     */
    public int calculateBasalMetabolicRate() {
        int bmr;
        if (this.gender == Gender.MALE) {
            bmr = (int) Math.round(((655.1 + (9.563 * this.weight)
                    + (1.850 * this.height)
                    - (4.676 * this.age)) * 1.55));
        } else {
            bmr = (int) Math.round(((66.47 + (13.75 * this.weight)
                    + (5.003 * this.height)
                    - (6.755 * this.age)) * 1.55));
        }
        return bmr;
    }

    /**
     * Loads the user data from the storage file to the User object.
     *
     * @param reader BufferedReader reading the user data storage file.
     * @return Integer representing if the user data was preloaded successfully
     * @throws IOException if any I/O operations failed or were interrupted.
     */
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

    /**
     * Loads the weight tracker data from the storage file to the User object.
     *
     * @param reader BufferedReader reading the user data storage file.
     * @throws IOException if any I/O operations failed or were interrupted.
     */
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

    /**
     * Creates a String that displays the user data for the UI.
     *
     * @return The user data display.
     */
    public String getUserDataDisplay() {
        return "Calorie Goal: " + this.calorieGoal + " " + System.lineSeparator()
                + "Gender: " + this.gender.toString() + System.lineSeparator()
                + "Age: " + this.age + System.lineSeparator()
                + "Weight: " + this.weight + System.lineSeparator()
                + "Height: " + this.height + System.lineSeparator();
    }

    /**
     * Converts user data to the correct format for storage.
     *
     * @return The user data in storage appropriate format.
     */
    public String convertUserDataToString() {
        return this.calorieGoal + DELIMITER
                + this.gender + DELIMITER
                + this.age + DELIMITER
                + this.height + DELIMITER
                + this.weight;
    }

    /**
     * Converts weight data to the correct format for storage.
     *
     * @return The weight data in storage appropriate format.
     */
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
