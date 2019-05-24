package ch.fhnw.mada.jvi.huffman;

import ch.fhnw.mada.jvi.huffman.file.FileService;

import java.math.BigInteger;
import java.util.Map;
import java.util.stream.Collectors;

import static ch.fhnw.mada.jvi.huffman.BitStringUtils.appendSuffix;

public class EncodingService {

    private final FileService fileService = new FileService();
    private final CodeMapService codeMapService = new CodeMapService();

    public void encode() {
        String text = readDecoded();
        Map<String, Long> frequencyMap = codeMapService.createFrequencyMap(text);
        Map<Integer, String> codeMap = codeMapService.createCodeMap(frequencyMap);
        byte[] encoded = encode(text, codeMap);

        fileService.writeCodeFile(codeMapService.createCodeMapString(codeMap));
        fileService.writeCompressedFile(encoded);
    }

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

    private byte[] encode(String text, Map<Integer, String> code) {
        StringBuilder compressed = new StringBuilder(text.chars().mapToObj(code::get).collect(Collectors.joining()));
        String bitString = appendSuffix(compressed);
        return new BigInteger(bitString, 2).toByteArray();
    }
}
