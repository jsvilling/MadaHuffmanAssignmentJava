package ch.fhnw.mada.jvi.huffman.encoding;

public class BitStringUtils {

    public static final String ONE = "1";
    public static final String ZERO = "0";

    /**
     * This function converts a bytearray into a bit string adding zero padding to the left. The size of the bit mask
     * is size 8.
     *
     * @param byte[] to convert
     * @return bitstring representation of the passed byte[]
     */
    public static String toBitString(final byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(toBits(b));
        }
        return stringBuilder.toString();
    }

    /**
     * Create a bit string of length 8 from a byte.
     *
     * This method masks the given byte using bit wise and with 0xFF. This means, only the last 8 bits will be
     * considered when creating the string. This also makes sure that no bit string of length > 8 can be created.
     *
     * If the resulting bit string has a length of < 8 zero-padding is added on the left until the length is exactly 8.
     */
    private static String toBits(final byte val) {
        return String.format("%8s", Integer.toBinaryString(val & 0xFF)).replace(' ', '0');
    }


    /**
     * Removes the last one and all subsequent zeros from a String.
     * <p>
     * This can be used to remove the suffix appended by BitStringUtils{@link #appendSuffix(StringBuilder)}
     *
     * @param bitString
     * @return
     */
    public static String removeSuffix(String bitString) {
        int lastIndexOfOne = bitString.lastIndexOf(ONE);
        return bitString.substring(0, lastIndexOfOne);
    }

    /**
     * Appends a single one to a bit string. If the length of the resulting String is not dividible by 8 it
     * appends zeros until it is.
     * <p>
     * This can be used to append a suffix to a bit string. Which will allow for it to be convieniently converted
     * to a byte array. Further the suffix can be used to indicate the bit strings end.
     *
     * @param original
     * @return
     */
    public static String appendSuffix(StringBuilder original) {
        original.append(ONE);
        while (original.length() % 8 != 0) {
            original.append(ZERO);
        }
        return original.toString();
    }
}
