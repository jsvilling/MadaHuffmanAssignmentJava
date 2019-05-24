package ch.fhnw.mada.jvi.huffman.ui.input;

/**
 * Small boolean wrapper used for confirmation dialogs in the {@link ch.fhnw.mada.jvi.huffman.ui.CommandLineInterface}
 */
public enum YesNoType {
    Y, YES, N, NO;

    public boolean isYes() {
        return this == Y || this == YES;
    }

    public boolean isNo() {
        return this == N || this == NO;
    }
}
