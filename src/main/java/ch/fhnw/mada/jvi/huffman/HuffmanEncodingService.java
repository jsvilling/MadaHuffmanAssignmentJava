package ch.fhnw.mada.jvi.huffman;

import ch.fhnw.mada.jvi.huffman.file.FileService;

import java.math.BigInteger;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class HuffmanEncodingService {

    private static final String EMPTY = "";
    private static final String ONE = "1";
    private static final String ZERO = "0";
    private static final String CODE_SEPARATOR = ":";
    private static final String SYMBOL_SEPARATOR = "-";

    private final FileService fileService = new FileService();

    public void encode() {
        String text = fileService.readDecodedFile();
        Map<String, Long> frequencyMap = buildFrequencyMap(text);
        HuffmanTree huffmanTree = new HuffmanTree(frequencyMap);
        byte[] encoded = encode(text, huffmanTree.getCodeMap());

        fileService.writeCodeFile(createCodeString(huffmanTree.getCodeMap()));
        fileService.writeCompressedFile(encoded);
    }

    public void decode() {
        Map<String, Character> codes = readCodes();
        String encodedString = readEncoded();
        String decodedString = decode(encodedString, codes);
        fileService.writeDecodedFile(decodedString);
    }

    private String readEncoded() {
        byte[] encoded = fileService.readEncodedFile();
        String bitString = BitStringUtils.toBitString(encoded);
        return BitStringUtils.removeSuffix(bitString, ONE);
    }

    private Map<String, Character> readCodes() {
        String codeString = fileService.readCodeFile();
        String[] split = codeString.split(SYMBOL_SEPARATOR);
        return Stream.of(split).map(s -> s.split(CODE_SEPARATOR)).collect(Collectors.toMap(c -> c[1], c -> (char) Integer.parseInt(c[0])));
    }

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

    private Map<String, Long> buildFrequencyMap(String text) {
        return stream(text.split(EMPTY)).collect(groupingBy(identity(), counting()));
    }

    private byte[] encode(String text, Map<Integer, String> code) {
        StringBuilder compressed = new StringBuilder(text.chars().mapToObj(code::get).collect(Collectors.joining()));
        compressed.append(ONE);
        while (compressed.length() % 8 != 0) {
            compressed.append(ZERO);
        }
        String bitString = compressed.toString();
        return new BigInteger(bitString, 2).toByteArray();
    }

    private String createCodeString(Map<Integer, String> code) {
        return code.entrySet().stream().map(e -> "" + e.getKey() + CODE_SEPARATOR + e.getValue()).collect(Collectors.joining(SYMBOL_SEPARATOR));
    }
}
