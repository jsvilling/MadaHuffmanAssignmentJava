package ch.fhnw.mada.jvi.huffman.tree;

/**
 * This class can be used to represent the nodes of a HuffmanTree.
 *
 * A root can be an root, branch or leaf node. The different node types have the following properties:
 *
 * <ul>
 *     <li>Root Node</li>
 *          <li>A root node is the top level node. As such it is no sub node of any other node</li>
 *          <li>If the text to en/decode has exactly one distinct character (which can be repeated multiple times), the
 *              root node can be a leaf node an does not have any sub nodes</li>
 *          <li>Otherwise it has the same property as a branch node.</li>
 *          <li>The root node can be used to construct the encoded values of a huffman code</li>
 *     <li>Branch Node</li>
 *          <li>A branch node is the direct sub node of exactly one parent node</li>
 *          <li>It has at least one sub node which can be either a branch or a leaf node</li>
 *          <li>It has at most two direct sub nodes</li>
 *          <li>The frequency of a branch node is the combined frequency of all direct sub nodes</li>
 *          <li>A branch is not assigned to a character</li>
 *     <li>Leaf Node</li>
 *          <li>A leaf node is the direct sub node of exactly one parent node</li>
 *          <li>It does not have any sub nodes</li>
 *          <li>It is assigned to exactley one character</li>
 *          <li>The frequency of a leaf node is the frequency of the associated character in text to be en/decoded</li>
 * </ul>
 *
 * For more information on the creation of a huffman code see {@link ch.fhnw.mada.jvi.huffman.encoding.EncodingService}
 * and {@link ch.fhnw.mada.jvi.huffman.encoding.CodeMapService}
 */
public class HuffmanTreeNode {

    private final HuffmanTreeNode suNodeLeft;
    private final HuffmanTreeNode subNodeRight;
    private final long characterFrequency;
    private final String character;

    /**
     * Constructor used for creating a leaf node.
     *
     * @param frequency - Frequency of the associated character
     * @param character - The associated character
     */
    public HuffmanTreeNode(long frequency, String character) {
        this.characterFrequency = frequency;
        this.character = character;
        this.suNodeLeft = null;
        this.subNodeRight = null;
    }

    /**
     * Constructor used for branch and parent nodes.
     *
     * The passed sub nodes will always be re-arranged so that the sub node with the smaller frequency is to the left.
     *
     * The frequency of the node is set to the combined frequency of both sub nodes.
     *
     * @param left - subnode to the left
     * @param right - sub node to the right
     */
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

    public long getFrequency() {
        return characterFrequency;
    }

    public String getCharacter() {
        return character;
    }
}