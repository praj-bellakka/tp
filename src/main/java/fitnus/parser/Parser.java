package fitnus.parser;

/**
 * Handles the extraction of user inputs into relevant components.
 */
public class Parser {

    public static final String SPACE_CHARACTER = " ";
    public static final String BACKSLASH_CHARACTER = "/";
    public static final String CALORIE_KEYWORD = "/cal";
    public static final String DEFAULT_KEYWORD = "/def";
    public static final String DAY_KEYWORD = "/day";


    /** Returns a string of the input type.
     * Parser will assume the first word of the input is the type, and uses space as the end character.
     *
     * @param input user input.
     * @return String containing the type.
     */
    public String parseInputType(String input) {
        String inputType;
        try {
            String cleanedString = input.toLowerCase().trim(); //removes whitespace and converts to lower case
            inputType = cleanedString.substring(0, input.indexOf(SPACE_CHARACTER));
            return inputType;
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a string containing the food name.
     * The function assumes all characters between the first space character and a backslash character is the food.
     *
     * @param input Input containing the food name.
     * @return String of food name.
     */
    public String parseFoodName(String input) {
        String foodName = input.substring(input.indexOf(SPACE_CHARACTER) + 1, input.indexOf(BACKSLASH_CHARACTER));
        return foodName;
    }

    /**
     * Returns an integer value based on keyword specified in input.
     * Function works for the keywords "/cal", "/def" and "/day".
     * Throws NumberFormatException if an integer cannot be detected after the keyword.
     *
     * @param input Input containing a keyword and an integer.
     * @return Integer specified in the string. Returns -1 if no integer is detected.
     */
    public int parseIntegers(String input) {
        int integerVal = 0;
        String cleanedString = input.toLowerCase().trim();
        try {
            if (cleanedString.contains(CALORIE_KEYWORD)) {
                integerVal = Integer.parseInt(cleanedString
                        .substring(cleanedString.indexOf(CALORIE_KEYWORD) + CALORIE_KEYWORD.length())
                        .strip());
            } else if (cleanedString.contains(DEFAULT_KEYWORD)) {
                integerVal = Integer.parseInt(cleanedString
                        .substring(cleanedString.indexOf(DEFAULT_KEYWORD) + DEFAULT_KEYWORD.length())
                        .strip());
            } else if (cleanedString.contains(DAY_KEYWORD)) {
                integerVal = Integer.parseInt(cleanedString
                        .substring(cleanedString.indexOf(DAY_KEYWORD) + DAY_KEYWORD.length())
                        .strip());
            }
            return integerVal;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
