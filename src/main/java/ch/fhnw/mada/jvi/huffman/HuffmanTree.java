package ch.fhnw.mada.jvi.huffman;

import java.util.*;
import java.util.stream.Collectors;

public class HuffmanTree {

    private static final String EMPTY ="";
    private static final String ONE ="1";
    private static final String ZERO ="0";
    private static final String CODE_SEPARATOR = ":";
    private static final String SYMBOL_SEPARATOR = "-";

    private final TreeMap<Integer, String> codeMap;

    public HuffmanTree(Map<String, Long> frequencyMap) {
        Node rootNode = buildNodeTree(frequencyMap);
        this.codeMap = buildCodeMap(rootNode, EMPTY);
    }

    private Node buildNodeTree(Map<String, Long> frequencyMap) {
        PriorityQueue<Node> nodes = new PriorityQueue<>(Comparator.comparingLong(Node::getCharacterFrequency));
        frequencyMap.entrySet().stream().map(e -> new Node(e.getValue(), e.getKey())).forEach(nodes::add);
        while (nodes.size() > 1) {
            nodes.add(new Node(nodes.poll(), nodes.poll()));
        }
        return nodes.peek();
    }

    private TreeMap<Integer, String> buildCodeMap(Node node, String s) {
        TreeMap<Integer, String> codes = new TreeMap<>();
        if (node != null) {
            if (node.hasSubnoteRight()) {
                codes.putAll(buildCodeMap(node.getSubNodeRight(), s + ONE));
            }
            if (node.hasSubnodeLeft()) {
                codes.putAll(buildCodeMap(node.getSubNodeLeft(), s + ZERO));
            }
            if (node.isLeaf()) {
                codes.put((int) node.getCharacter().charAt(0), s);
            }
        }
        return codes;
    }

    public Map<Integer, String> getCodeMap() {
        return Collections.unmodifiableMap(codeMap);
    }

    private class Node {
        private final Node subNodeLeft;
        private final Node subNodeRight;
        private final long characterFrequency;
        private final String character;

        Node(long frequency, String character) {
            this.characterFrequency = frequency;
            this.character = character;
            this.subNodeLeft = null;
            this.subNodeRight = null;
        }

        Node(Node left, Node right) {
            this.characterFrequency = left.characterFrequency + right.characterFrequency;
            character = left.character + right.character;
            if (left.characterFrequency < right.characterFrequency) {
                this.subNodeRight = right;
                this.subNodeLeft = left;
            } else {
                this.subNodeRight = left;
                this.subNodeLeft = right;
            }
        }

        public boolean isLeaf() {
            return subNodeLeft == null && subNodeRight == null;
        }

        public boolean hasSubnodeLeft() {
            return subNodeLeft != null;
        }

        public boolean hasSubnoteRight() {
            return subNodeRight != null;
        }

        public Node getSubNodeLeft() {
            return subNodeLeft;
        }

        public Node getSubNodeRight() {
            return subNodeRight;
        }

        public long getCharacterFrequency() {
            return characterFrequency;
        }

        public String getCharacter() {
            return character;
        }
    }

}
