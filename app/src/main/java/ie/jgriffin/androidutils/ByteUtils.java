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
}
