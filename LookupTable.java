import java.util.HashMap;

public class LookupTable {

    private Node root;
    private HashMap<Character,String> lookupTable = new HashMap<>();

    /**
     * The constructor of LookupTable
     * @param root
     */
    public LookupTable(Node root) {
        this.root = root;
        setLookupTable(buildLookupTable());
    }

    // getters
    public Node getRoot() {return root;}
    public HashMap<Character,String> getLookupTable() {return lookupTable;}

    // setters
    public void setLookupTable(HashMap<Character,String> lookupTable) {this.lookupTable = lookupTable;}

    /**
     * This method build the lookup table
     * @return the HashMap<Character, Integer> lookupTable
     */
    public HashMap<Character,String> buildLookupTable() {

        HashMap<Character,String> lookupTable = new HashMap<>();
        addToLookupTable(root, "", lookupTable);

        return lookupTable;
    }

    /**
     * Traverses the tree and sets the binary value for each character.
     * @param node
     * @param bit
     * @param lookupTable
     */
    public void addToLookupTable(Node node, String bit, HashMap<Character,String> lookupTable) {

        if(node.getHasChildren()) {
            addToLookupTable(node.getLeftChild(), bit + "0", lookupTable);
            addToLookupTable(node.getRightChild(), bit + "1", lookupTable);
        } else { 
            lookupTable.put(node.getCharacter(), bit);
        }
    } 
}