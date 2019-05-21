package ch.fhnw.mada.jvi.huffman.ui.input;

public enum YesNoType {
    Y, YES, N, NO;

    public boolean isYes() {
        return this == Y || this == YES;
    }

    public boolean isNo() {
        return this == N || this == NO;
    }
}
