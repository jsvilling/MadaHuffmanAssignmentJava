package ch.fhnw.mada.jvi.huffman;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class HuffmanEnCodingService {

    private static final String EMPTY = "";
    private static final String SOURCE_FILE = "text.txt";
    private static final String CODE_FILE = "dec_tab-mada.txt";
    private static final String COMPRESSED_FILE = "output-mada.dat";
    private static final String DECOMPRESSED_FILE = "decompressed.txt";

    private static final String ONE = "1";
    private static final String ZERO = "0";
    private static final String CODE_SEPARATOR = ":";
    private static final String SYMBOL_SEPARATOR = "-";

    public void encode() throws IOException {
        String text = Files.readString(Paths.get(SOURCE_FILE), StandardCharsets.UTF_8);
        Map<String, Long> frequencyMap = buildFrequencyMap(text);
        HuffmanTree huffmanTree = new HuffmanTree(frequencyMap);
        byte[] compressed = compressText(text, huffmanTree.getCodeMap());
        writeCodeToFile(huffmanTree.getCodeMap());
        FileOutputStream fos = new FileOutputStream(COMPRESSED_FILE);
        DataOutputStream dos = new DataOutputStream(fos);
        dos.write(compressed);
        fos.close();
        dos.close();
    }

    public void decode() throws IOException {
        Map<String, Character> codes = readCodes();
        String binaryString = readCompressed();
        String decompressedString = decompressBinaryString(binaryString, codes);
        writeToFile(decompressedString, DECOMPRESSED_FILE);
    }

    private Map<String, Character> readCodes() throws IOException {
        String codeString = Files.readString(Paths.get(CODE_FILE), StandardCharsets.UTF_8);
        String[] split = codeString.split(SYMBOL_SEPARATOR);
        return Stream.of(split).map(s -> s.split(CODE_SEPARATOR)).collect(Collectors.toMap(c -> c[1], c -> (char) Integer.parseInt(c[0])));
    }

    private String readCompressed() throws IOException {
        byte[] bytes = readDataArchive();
        String bitString = StringUtils.toBitString(bytes);
        return StringUtils.removeSuffix(bitString, ONE);
    }

    private String decompressBinaryString(String binaryString, Map<String, Character> codes) {
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

    private byte[] compressText(String text, Map<Integer, String> code) {
        StringBuilder compressed = new StringBuilder(text.chars().mapToObj(code::get).collect(Collectors.joining()));
        compressed.append(ONE);
        while (compressed.length() % 8 != 0) {
            compressed.append(ZERO);
        }
        String bitString = compressed.toString();
        return new BigInteger(bitString, 2).toByteArray();
    }

    private void writeCodeToFile(Map<Integer, String> code) throws IOException {
        String codeString = code.entrySet().stream().map(e -> "" + e.getKey() + CODE_SEPARATOR + e.getValue()).collect(Collectors.joining(SYMBOL_SEPARATOR));
        writeToFile(codeString, CODE_FILE);
    }

    private void writeToFile(String text, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        byte[] out = text.getBytes();
        fos.write(out);
        fos.close();
    }

    private byte[] readDataArchive() throws IOException {
        File file = new File(COMPRESSED_FILE);
        byte[] bFile = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bFile);
        fis.close();
        return bFile;
    }

}
