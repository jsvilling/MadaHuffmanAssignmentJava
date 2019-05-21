package ch.fhnw.mada.jvi.huffman.ui.input;

public enum Label {
    WELCOME("Welcome the Mada Huffman Assignment"),
    REQUEST_ACTION("Please choose one of the following commands: \n\t\t encode | decode"),
    REQUEST_REPEAT("Do you want to run another command?  [Y/N] "),
    INVALID_INPUT("Invalid Input: ");

    private String message;

    Label(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
