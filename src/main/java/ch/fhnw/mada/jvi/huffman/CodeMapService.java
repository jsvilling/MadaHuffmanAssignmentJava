package ch.fhnw.mada.jvi.huffman;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

public class CodeMapService {

    private static final String CODE_SEPARATOR = ":";
    private static final String SYMBOL_SEPARATOR = "-";
    private static final String EMPTY = "";


    public String createCodeMapString(Map<Integer, String> code) {
        return code.entrySet().stream().map(e -> "" + e.getKey() + CODE_SEPARATOR + e.getValue()).collect(Collectors.joining(SYMBOL_SEPARATOR));
    }

    public Map<String, Character> createCodeMap(String codeString) {
        String[] split = codeString.split(SYMBOL_SEPARATOR);
        return Stream.of(split).map(s -> s.split(CODE_SEPARATOR)).collect(toMap(c -> c[1], c -> (char) parseInt(c[0])));
    }

    public Map<String, Long> createFrequencyMap(String text) {
        return stream(text.split(EMPTY)).collect(groupingBy(identity(), counting()));
    }
}
