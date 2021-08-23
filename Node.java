public class Node {

    private Character character;
    private int frequency;
    private Node leftChild;
    private Node rightChild;
    private boolean hasChildren;

    /**
     * Node constructor
     * @param character
     * @param frequency
     * @param leftChild
     * @param rightChild
     */
    public Node(char character, int frequency, Node leftChild, Node rightChild) {
        this.character = character;
        this.frequency = frequency;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        setHasChildren();
    }
    
    // setter
    private void setHasChildren() {
        if(leftChild == null && rightChild == null) {
            hasChildren = false;
        } else {
            hasChildren = true;
        }
    }

    // getters
    public char getCharacter() {return character;}
    public int getFrequency() {return frequency;}
    public Node getLeftChild() {return leftChild;}
    public Node getRightChild() {return rightChild;}
    public boolean getHasChildren() {return hasChildren;}

}