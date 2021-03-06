package ch.fhnw.mada.jvi.huffman.ui;


import ch.fhnw.mada.jvi.huffman.encoding.CodeMapService;
import ch.fhnw.mada.jvi.huffman.encoding.EncodingService;
import ch.fhnw.mada.jvi.huffman.file.FileService;
import ch.fhnw.mada.jvi.huffman.ui.input.UserAction;

import java.util.Scanner;

import static ch.fhnw.mada.jvi.huffman.ui.input.Label.*;

public class CommandLineInterface {

    private final CommandLineInputReader reader = new CommandLineInputReader(new Scanner(System.in));

    private final EncodingService huffmanEnCodingService = new EncodingService(new FileService(), new CodeMapService());

    public void run() {
        System.out.println(WELCOME);
        do {
            switch (readAction()) {
                case ENCODE:
                    huffmanEnCodingService.encode();
                    break;
                case DECODE:
                    huffmanEnCodingService.decode();
                    break;
            }
        } while (isContinue());

    }

    private UserAction readAction() {
        return reader.readValidatedInput(REQUEST_ACTION.toString(), i -> UserAction.valueOf(i.toUpperCase()));
    }

    private boolean isContinue() {
        return reader.readYesNo(REQUEST_REPEAT.toString());
    }
}
