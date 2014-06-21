package ie.jgriffin.androidutils;

/**
 * Created by JGriffin on 21/06/2014.
 */
public class GenUtils {

    /**
     * Check if the passed value is withing the range defined by min and max.
     *
     * @param value The value to check
     * @param min The min limit
     * @param max The max limit
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
     * @param c The character to check
     * @return A boolean
     */
    public static boolean isNumeral(char c) {
        return (c > 47 && c < 58);
    }
}
