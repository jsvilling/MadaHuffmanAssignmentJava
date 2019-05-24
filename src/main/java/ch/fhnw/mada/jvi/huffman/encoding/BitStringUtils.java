package ch.fhnw.mada.jvi.huffman.encoding;

public class BitStringUtils {

    public static final String ONE = "1";
    public static final String ZERO = "0";

    /**
     * This function converts a bytearray into a bit string adding zero padding to the left. The size of the bit mask
     * is size 8.
     *
     * This is a standard implementation of a toBitString function privided by
     * http://www.java2s.com/Tutorials/Java/Data_Type/Array_Convert/Convert_byte_array_to_bit_string_in_Java.htm
     *
     * @param byte[] to convert
     * @return bitstring representation of the passed byte[]
     */
    public static String toBitString(final byte[] b) {
        final char[] bits = new char[8 * b.length];
        for(int i = 0; i < b.length; i++) {
            final byte byteval = b[i];
            int bytei = i << 3;
            int mask = 0x1;
            for(int j = 7; j >= 0; j--) {
                final int bitval = byteval & mask;
                if(bitval == 0) {
                    bits[bytei + j] = '0';
                } else {
                    bits[bytei + j] = '1';
                }
                mask <<= 1;
            }
        }
        return String.valueOf(bits);
    }

    /**
     * Removes the last one and all subsequent zeros from a String.
     *
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
     *
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
