package ie.jgriffin.androidutils;

/**
 * Created by JGriffin on 24/06/2014.
 */
public class ByteUtils {
    /**
     * Converts a byte to the HEX String representation.
     *
     * @param b The byte passed in which will be parsed to HEX representation.
     * @return String in HEX notation which represents one byte.
     */
    public static String byteToHexString(Byte b) {
        return String.format("%02X", b);
    }

    /**
     * Converts a 4 byte array to an int.
     *
     * @param b byte array to convert.
     * @return int constructed from the passed byte[].
     */
    public static int byteArrayToInt(byte[] b) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (b[i] & 0xFF) << shift;
        }
        return value;
    }

    /**
     * Converts an int to a 4 byte array.
     *
     * @param a int to convert.
     * @return byte array constructed from the passed int.
     */
    public static byte[] intToByteArray(int a) {
        byte[] ret = new byte[4];
        ret[3] = (byte) (a & 0xFF);
        ret[2] = (byte) ((a >> 8) & 0xFF);
        ret[1] = (byte) ((a >> 16) & 0xFF);
        ret[0] = (byte) ((a >> 24) & 0xFF);
        return ret;
    }

    /**
     * Returns true if the specified offset bit is set to 1.
     * The offset should not be greater than 7.
     *
     * @param bite   byte within which lies the bit to check.
     * @param offset the bit to check within the byte.
     * @return true if bit is set to 1.
     */
    public static boolean checkBitIsSet(byte bite, int offset) {
        if (offset > 7 || offset < 0) {
            throw new IllegalArgumentException("Offset must be between 0 and 7!");
        }
        return (bite & (1 << offset)) != 0;
    }

    /**
     * Modifies the specified bit within the passed byte to 1 or 0 depending on the boolean passed.
     * The offset should not be greater than 7.
     *
     * @param bite   byte within which lies the bit to change.
     * @param offset the bit to change within the byte.
     * @param set    if true then set the bit, else unset the bit.
     * @return the modified byte.
     */
    public static byte setBit(byte bite, int offset, boolean set) {
        if (offset > 7 || offset < 0) {
            throw new IllegalArgumentException("Offset must be between 0 and 7!");
        }
        if (set) {
            //bitwise OR will set specified bit to one
            bite = (byte) (bite | (1 << offset));
        } else {
            //bitwise NOT will set new byte to all ones except the specified bit
            //then we AND that with the byte to change the specified bit to 0
            bite = (byte) (bite & ~(1 << offset));
        }
        return bite;
    }
}
