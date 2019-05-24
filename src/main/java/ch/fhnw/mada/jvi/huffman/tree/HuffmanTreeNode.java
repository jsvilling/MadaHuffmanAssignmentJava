package ch.fhnw.mada.jvi.huffman.tree;

public class HuffmanTreeNode {

    private final HuffmanTreeNode suNodeLeft;
    private final HuffmanTreeNode subNodeRight;
    private final long characterFrequency;
    private final String character;

    public HuffmanTreeNode(long frequency, String character) {
        this.characterFrequency = frequency;
        this.character = character;
        this.suNodeLeft = null;
        this.subNodeRight = null;
    }

    public HuffmanTreeNode(HuffmanTreeNode left, HuffmanTreeNode right) {
        this.characterFrequency = left.characterFrequency + right.characterFrequency;
        character = left.character + right.character;
        if (left.characterFrequency < right.characterFrequency) {
            this.subNodeRight = right;
            this.suNodeLeft = left;
        } else {
            this.subNodeRight = left;
            this.suNodeLeft = right;
        }
    }

    public boolean isLeaf() {
        return suNodeLeft == null && subNodeRight == null;
    }

    public boolean hasSubnodeLeft() {
        return suNodeLeft != null;
    }

    public boolean hasSubnoteRight() {
        return subNodeRight != null;
    }

    public HuffmanTreeNode getSuNodeLeft() {
        return suNodeLeft;
    }

    public HuffmanTreeNode getSubNodeRight() {
        return subNodeRight;
    }

    public long getCharacterFrequency() {
        return characterFrequency;
    }

    public String getCharacter() {
        return character;
    }
}