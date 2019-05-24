package ch.fhnw.mada.jvi.huffman;

import ch.fhnw.mada.jvi.huffman.tree.HuffmanTreeNode;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ch.fhnw.mada.jvi.huffman.BitStringUtils.ONE;
import static ch.fhnw.mada.jvi.huffman.BitStringUtils.ZERO;
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

    public Map<String, Long> createFrequencyMap(String text) {
        return stream(text.split(EMPTY)).collect(groupingBy(identity(), counting()));
    }

    public Map<String, Character> createCodeMap(String codeString) {
        String[] split = codeString.split(SYMBOL_SEPARATOR);
        return Stream.of(split).map(s -> s.split(CODE_SEPARATOR)).collect(toMap(c -> c[1], c -> (char) parseInt(c[0])));
    }

    public TreeMap<Integer, String> createCodeMap(Map<String, Long> frequencyMap) {
        HuffmanTreeNode rootNode = buildNodeTree(frequencyMap);
        return buildCodeMap(rootNode, EMPTY);
    }

    private HuffmanTreeNode buildNodeTree(Map<String, Long> frequencyMap) {
        PriorityQueue<HuffmanTreeNode> huffmanTreeNodes = new PriorityQueue<>(Comparator.comparingLong(HuffmanTreeNode::getCharacterFrequency));
        frequencyMap.entrySet().stream().map(e -> new HuffmanTreeNode(e.getValue(), e.getKey())).forEach(huffmanTreeNodes::add);
        while (huffmanTreeNodes.size() > 1) {
            huffmanTreeNodes.add(new HuffmanTreeNode(huffmanTreeNodes.poll(), huffmanTreeNodes.poll()));
        }
        return huffmanTreeNodes.peek();
    }

    private TreeMap<Integer, String> buildCodeMap(HuffmanTreeNode huffmanTreeNode, String s) {
        TreeMap<Integer, String> codes = new TreeMap<>();
        if (huffmanTreeNode != null) {
            if (huffmanTreeNode.hasSubnoteRight()) {
                codes.putAll(buildCodeMap(huffmanTreeNode.getSubNodeRight(), s + ONE));
            }
            if (huffmanTreeNode.hasSubnodeLeft()) {
                codes.putAll(buildCodeMap(huffmanTreeNode.getSuNodeLeft(), s + ZERO));
            }
            if (huffmanTreeNode.isLeaf()) {
                codes.put((int) huffmanTreeNode.getCharacter().charAt(0), s);
            }
        }
        return codes;
    }

}
