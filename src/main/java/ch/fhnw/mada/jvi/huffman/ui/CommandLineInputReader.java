package ch.fhnw.mada.jvi.huffman.ui;

import ch.fhnw.mada.jvi.huffman.ui.input.YesNoType;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.function.Function;
import static ch.fhnw.mada.jvi.huffman.ui.input.Label.INVALID_INPUT;

class CommandLineInputReader {
    private final Scanner scanner;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    CommandLineInputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    <T> T readValidatedInput(String request, Function<String, T> validatingValueExtractor) {
        try {
            String input = readInputString(request);
            return validatingValueExtractor.apply(input);
        } catch (Exception e) {
            System.out.println(INVALID_INPUT);
            return readValidatedInput(request, validatingValueExtractor);
        }
    }

    boolean readYesNo(String message) {
        YesNoType answer = readValidatedInput(message, a -> YesNoType.valueOf(a.toUpperCase()));
        return answer.isYes();
    }

    private String readInputString(String request) {
        System.out.println(request);
        return scanner.next();
    }
}
