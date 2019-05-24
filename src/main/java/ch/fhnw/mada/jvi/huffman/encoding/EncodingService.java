package ch.fhnw.mada.jvi.huffman.encoding;

import ch.fhnw.mada.jvi.huffman.file.FileService;

import java.math.BigInteger;
import java.util.Map;
import java.util.stream.Collectors;

import static ch.fhnw.mada.jvi.huffman.encoding.BitStringUtils.appendSuffix;

/**
 * Service to encode and decode a text file using huffman encoding.
 *
 * This service uses the {@link CodeMapService} to generate a huffman code from read in data and uses it to either
 * decode an encoded file or encode a decoded file.
 *
 * This service uses the {@link FileService} to read any needed data. The FileService is also used, to output any
 * encoded or decoded data. As well as to persist a generated huffman code.
 */
public class EncodingService {

    private final FileService fileService;
    private final CodeMapService codeMapService;

    /**
     * Service to encode and decode a text file using huffman encoding.
     *
     * @param fileService - used to load and persist all plaintext, encoded text and codes
     * @param codeMapService - used to generate huffman codes
     */
    public EncodingService(FileService fileService, CodeMapService codeMapService) {
        this.fileService = fileService;
        this.codeMapService = codeMapService;
    }

    /**
     * Encodes the plain text found in the sourceFile configured in the {@link FileService}.
     *
     * The generated huffman code is output to the codeFile configured in the FileService.
     *
     * The generated encoded text is output to the compressedFile configured in the FileService.
     */
    public void encode() {
        String text = readDecoded();
        Map<String, Long> frequencyMap = codeMapService.createFrequencyMap(text);
        Map<Integer, String> codeMap = codeMapService.createCodeMap(frequencyMap);
        byte[] encoded = encode(text, codeMap);

        fileService.writeCodeFile(codeMapService.createCodeMapString(codeMap));
        fileService.writeCompressedFile(encoded);
    }

    /**
     * Decodes the encoded compressedFile configured in the {@link FileService}
     *
     * Decoding is done using the huffman code from the codeFile configured in the FileService
     *
     * The generated decoded text is output to the decompressedFile configured in the FileService
     */
    public void decode() {
        Map<String, Character> codes = readCodes();
        String encodedString = readEncoded();
        String decodedString = decode(encodedString, codes);

        fileService.writeDecodedFile(decodedString);
    }

    private String readDecoded() {
        return fileService.readDecodedFile();
    }

    private Map<String, Character> readCodes() {
        String codeString = fileService.readCodeFile();
        return codeMapService.createCodeMap(codeString);
    }

    private String readEncoded() {
        byte[] encoded = fileService.readEncodedFile();
        String bitString = BitStringUtils.toBitString(encoded);
        return BitStringUtils.removeSuffix(bitString);
    }

    /**
     * Helper method responsible for the acutal decoding of a binary string using the given huffman code.
     *
     * The method works by iterating over the given binary string until a substring is found that can be matched
     * with a value from the huffman code. This is repeated until the complete string has been processed. While
     * iterating over the string the found matches are combined into the resulting plaintext string.
     *
     * A more elegant solution would be to create a huffman tree as done for encoding and traverse the tree until
     * a leaf node is reached. This approach was not implemented as the current implementation is quicker to implement.
     *
     * @param binaryString - encoded String
     * @param codes - huffman code
     * @return String - decoded plaintext
     */
    private String decode(String binaryString, Map<String, Character> codes) {
        StringBuilder result = new StringBuilder();
        String nextToken = "";
        for (int i = 0; i < binaryString.length(); i++) {
            nextToken += binaryString.charAt(i);
            Character code = codes.get(nextToken);
            if (code != null) {
                result.append(code);
                nextToken = "";
            }
        }
        return result.toString();
    }

    /**
     * Helper method responsible for the actual encoding of a plaintext string using the given huffman code.
     *
     * The method works by iterating over each character in a string and assigning it to its corresponding code value.
     * The code values are combined into a single encoded bit string. The resulting bit string is extended with a
     * suffix. The suffix consists of a one and as many zeros as are necessary for the length of the bit string to be
     * dividible by 8. Finally the created bit string is converted into a byte array,
     *
     * @param text - plaintext to be encoded
     * @param code - huffman code used for encoding.
     * @return byte array representing the encoded test
     */
    private byte[] encode(String text, Map<Integer, String> code) {
        StringBuilder compressed = new StringBuilder(text.chars().mapToObj(code::get).collect(Collectors.joining()));
        String bitString = appendSuffix(compressed);
        return new BigInteger(bitString, 2).toByteArray();
    }
}
