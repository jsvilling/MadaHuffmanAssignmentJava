package ch.fhnw.mada.jvi.huffman;

public class BitStringUtils {

    public static final String ONE = "1";
    public static final String ZERO = "0";

    /**
     * This function converts a bytearray into a bit string adding zero padding to the left. The size of the bit mask
     * is size 8.
     *
     * Source http://www.java2s.com/Tutorials/Java/Data_Type/Array_Convert/Convert_byte_array_to_bit_string_in_Java.htm
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

    public static String removeSuffix(String bitString) {
        int lastIndexOfOne = bitString.lastIndexOf(ONE);
        return bitString.substring(0, lastIndexOfOne);
    }

    public static String appendSuffix(StringBuilder original) {
        original.append(ONE);
        while (original.length() % 8 != 0) {
            original.append(ZERO);
        }
        return original.toString();
    }
}
