package ie.jgriffin.androidutils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JGriffin on 21/06/2014.
 */
public class GenUtils {

    private static final long MILLIS_IN_SECOND = 1000L;
    private static final long MILLIS_IN_MINUTE = 60000L;
    private static final long MILLIS_IN_HOUR = 3600000L;
    private static final long MILLIS_IN_DAY = 86400000L;
    private static final long MILLIS_IN_30_DAYS = 259200000L;
    private static final long MILLIS_IN_YEAR = 31536000000L;

    /**
     * Check if the passed value is withing the range defined by min and max.
     *
     * @param value The value to check
     * @param min   The min limit
     * @param max   The max limit
     * @return A boolean
     */
    public static boolean isBetween(int value, int min, int max) {
        return ((value > min) && (value < max));
    }


    /**
     * Check if the passed character is a lowercase letter.
     *
     * @param c The character to check
     * @return A boolean
     */
    public static boolean isLowercaseLetter(char c) {
        return (c > 96 && c < 123);
    }

    /**
     * Check if the passed character is an uppercase letter.
     *
     * @param c The character to check
     * @return A boolean
     */
    public static boolean isUppercaseLetter(char c) {
        return (c > 64 && c < 91);
    }


    /**
     * Check if the passed character is a numeral
     *
     * @param c The character to check
     * @return A boolean
     */
    public static boolean isNumeral(char c) {
        return (c > 47 && c < 58);
    }


    /**
     * Parses and returns numbers found within a string.
     *
     * @param str       String to retrieve numbers from.
     * @param arraySize How many times to loop through the string to find numbers.
     * @return An array of numbers found in the string.
     */
    public static int[] getArrayOfNumbersFromString(String str, int arraySize) {
        int[] myIntArray = new int[arraySize];
        int i = 0;
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(str);
        while (m.find() && i < arraySize) {
            myIntArray[i] = Integer.parseInt(m.group());
            i++;
        }
        return myIntArray;
    }

    /**
     * Parses and returns numbers found within a string.
     *
     * @param str String to retrieve numbers from.
     * @return An ArrayList of numbers found in the string.
     */
    public static ArrayList<Integer> getArrayListOfNumbersFromString(String str) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(str);
        while (m.find()) {
            arrayList.add(Integer.parseInt(m.group()));
        }
        return arrayList;
    }

    /**
     * Calculate the number of days that have elapsed and return as a string
     *
     * @param time          The timestamp to subtract from now
     * @param daysAgoString The localised string which equates to "days ago"
     * @return A string which should have the format "X days ago"
     */
    public static String calculateDaysAgoString(long time, String daysAgoString) {
        time = calculateTimeAgo(time);
        time = time / MILLIS_IN_DAY;
        return Long.toString(time) + " " + daysAgoString;
    }


    /**
     * @param time             The time to parse and display
     * @param justNowString    The localised string which equates to "just now"
     * @param secondsAgoString The localised string which equates to "seconds ago"
     * @param minutesAgoString The localised string which equates to "minutes ago"
     * @param hoursAgoString   The localised string which equates to "hours ago"
     * @param daysAgoString    The localised string which equates to "days ago"
     * @return An apt String representation of the amount of time that has elapsed.
     */
    public static String createRecentTimeString(long time, String justNowString,
                                                String secondsAgoString, String minutesAgoString,
                                                String hoursAgoString, String daysAgoString) {

        time = calculateTimeAgo(time);
        if (time < MILLIS_IN_SECOND) {
            return justNowString;
        } else if (time < MILLIS_IN_MINUTE) {
            time = time / MILLIS_IN_SECOND;
            return Long.toString(time) + " " + secondsAgoString;
        } else if (time < MILLIS_IN_HOUR) {
            time = time / MILLIS_IN_MINUTE;
            return Long.toString(time) + " " + minutesAgoString;
        } else if (time < MILLIS_IN_DAY) {
            time = time / MILLIS_IN_HOUR;
            return Long.toString(time) + " " + hoursAgoString;
        } else {
            time = time / MILLIS_IN_DAY;
            return Long.toString(time) + " " + daysAgoString;
        }
    }

    /**
     * Calculate the number of millis that have elapsed from time until now
     *
     * @param time The timestamp to subtract from now
     * @return the number of milliseconds that have elapsed
     */
    public static long calculateTimeAgo(long time) {
        time = System.currentTimeMillis() - time;
        //basic sanity check
        if (time > 0) {
            return time;
        } else {
            return 0;
        }
    }

    /**
     * Calculate the number of days between two timestamps
     *
     * @param time  The start time
     * @param time2 The end time
     * @return A long representing the number of days elapsed
     */
    public static long calculateDaysBetween(long time, long time2) {
        return (time2 - time) / MILLIS_IN_DAY;
    }

    /**
     * Removes any parentheses in a string.
     *
     * @param str string to be modified.
     * @return modified string.
     */
    public static String stripParentheses(String str) {
        str = str.replace("[", "");
        str = str.replace("]", "");
        return str;
    }
}
