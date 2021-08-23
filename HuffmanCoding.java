import java.util.HashMap;
import java.util.ArrayList;
import java.io.*;
import java.nio.file.*;

public class HuffmanCoding {

    private String filename;
    private HashMap<Character,Integer> freq;
    private HashMap<Character,String> lookupTable; 
    private Node huffmanTree;

    /**
     * The constructor of HuffmanCoding
     * @param filename
     */
    public HuffmanCoding(String filename) {
        this.filename = filename;
        Letter_Frequencies characters = new Letter_Frequencies(filename);
        setFrequencyTable(characters.getFrequencyTable());
        setHuffmanTree(buildTree());
        LookupTable lookupTable = new LookupTable(huffmanTree);
        setLookupTable(lookupTable.getLookupTable());
    }

    // setters
    public void setFileName(String filename) {this.filename = filename;}
    public void setFrequencyTable(HashMap<Character,Integer> freq) {this.freq = freq;}
    public void setHuffmanTree(Node huffmanTree) {this.huffmanTree = huffmanTree;}
    public void setLookupTable(HashMap<Character,String> lookupTable) {this.lookupTable = lookupTable;}

    // getters
    public String getFileName() {return filename;}
    public HashMap<Character,Integer> getFrequencyTable() {return freq;}
    public Node getHuffmanTree() {return huffmanTree;}
    public HashMap<Character,String> getLookupTable() {return lookupTable;}


    /**
     * This method builds the Huffman Tree.
     */
    public Node buildTree() {

        // put all the Nodes in an ArrayList
        ArrayList<Node> tree = new ArrayList<>();
        for (HashMap.Entry<Character, Integer> pair : freq.entrySet()) {
            tree.add(new Node(pair.getKey(), pair.getValue(), null, null));
        }

        // sorting the ArrayList<Node> using Quicksort
        sortTree(tree, 0, tree.size()-1);

        if(tree.size() == 1) {
            tree.add(new Node('\0', 1, null, null));
        }


        while(tree.size() > 1) {
            // getting the Node with the largest frequency, make it the left node
            Node left = tree.get(0);
            tree.remove(left);
            
            // getting the Node with the next largest frequency, make it the right node
            Node right = tree.get(0);
            tree.remove(right);

            // merge the two together, making a parent node
            Node parent = new Node('\0', left.getFrequency()+right.getFrequency(), left, right);
            // insert it back into the tree
            
            for(int i=0; i<tree.size(); i++) {
                
                if(tree.get(i).getFrequency() >= parent.getFrequency() ) {
                    tree.add(i, parent);
                    break;
                } else if(parent.getFrequency() > tree.get(tree.size() -1).getFrequency()) {
                    tree.add(parent);
                    break;
                }
            }

            if(tree.size() == 0) {
                tree.add(parent);
            }  
        }
        
        return tree.get(0);
    }

    /**
     * This method sorts the ArrayList of <Node>, which will become the Huffman Tree.
     * @param arr
     * @param low
     * @param high
     */
    public void sortTree(ArrayList<Node> arr, int low, int high) {   

        //check for empty or null array
        if (arr == null || arr.size() == 0) {
            return;
        }
         
        if (low >= high) { 
            return;
        }

        //Get the pivot element from the middle of the list
        int middle = low + (high - low) / 2;
        Node pivot = arr.get(middle);
 
        // make left < pivot and right > pivot
        int i = low, j = high;
        while (i <= j) {
            //Check until all values on left side array are lower than pivot
            while (arr.get(i).getFrequency() < pivot.getFrequency()) {
                i++;
            }
            //Check until all values on left side array are greater than pivot
            while (arr.get(j).getFrequency() > pivot.getFrequency()) {
                j--;
            }
            //Now compare values from both side of lists to see if they need swapping 
            //After swapping move the iterator on both lists
            if (i <= j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }
        //Do same operation as above recursively to sort two sub arrays
        if (low < j) {
            sortTree(arr, low, j);
        }
        if (high > i) {
            sortTree(arr, i, high);
        }
    }

    /**
     * This method is used in sortTree, to swap two elements in ArrayList<Node>.
     * @param list
     * @param firstIndex
     * @param secondIndex
     */
    public void swap(ArrayList<Node> list, int firstIndex, int secondIndex) {
        Node element = list.get(firstIndex);
        list.set(firstIndex, list.get(secondIndex));
        list.set(secondIndex, element);
    }

    /**
     * This method converts a string to an array of bytes.
     * @param s
     * @return the byte array
     */
    public byte[] getBinary(String s) {
        StringBuilder sBuilder = new StringBuilder(s);
        while (sBuilder.length() % 8 != 0) {
            sBuilder.append('0');
        }
        s = sBuilder.toString();

        byte[] data = new byte[s.length() / 8];

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '1') {
                data[i >> 3] |= 0x80 >> (i & 0x7);
            }
        }
        return data;
    }

    /**
     * This method converts the bytes into a string.
     * @param bytes
     * @return the string
     */
    public String getString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
        for (int i = 0; i < Byte.SIZE * bytes.length; i++)
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        return sb.toString();
    }

    /**
     * This method encodes the data
     * @return the string of 1s and 0s
     */
    public String getEncodedData() {

        String filename = getFileName();
        StringBuilder encoded = new StringBuilder();

        try {
            // FileReader reads text files.
            FileReader fileReader = new FileReader(filename);

            // Always wrap FileReader in BufferedReader, because FileReader is inefficient.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line = bufferedReader.readLine();
            StringBuilder fileBuilder = new StringBuilder();

            while(line != null) {
                fileBuilder.append(line + '\n');
                line = bufferedReader.readLine();
            }

            String lineString = fileBuilder.toString();

            for(char character: lineString.toCharArray()) {
                if(lookupTable.get(character) != null) {
                    encoded.append(lookupTable.get(character));
                }
            }

            bufferedReader.close();   
            
        } catch(FileNotFoundException ex) {  
            System.out.println("Unable to open file '" + filename + "'"); 
        } catch(IOException ex) {
            ex.printStackTrace();                
        }
    
    return encoded.toString();
    }

    /**
     * Compresses the file.
     */
    public void compress() {

        try{
            String encodedData = getEncodedData();

            byte[] converted = getBinary(encodedData);

            String newFilename = "";
            for(String character : getFileName().split("")) {
                if(character.equals(".")) {
                    break;
                } else {
                    newFilename +=  character;
                }
            }

            OutputStream outputStream = new FileOutputStream(newFilename + "(compressed).bin");
            outputStream.write(converted);
        
        } catch(FileNotFoundException ex) {  
            System.out.println(
                "Unable to open file '" + 
                filename + "'");                
        } catch(IOException ex) {
            ex.printStackTrace();                
        }
    }

    /**
     * Decompresses the file.
     */
    public void decompress() {

        StringBuilder decoded = new StringBuilder();
        Node currentNode = getHuffmanTree();
        String huffmanCode = getEncodedData();

        for(int i=0; i<huffmanCode.length(); i++) {
            currentNode = huffmanCode.charAt(i) == '1' ? currentNode.getRightChild() : currentNode.getLeftChild();
            if(currentNode.getRightChild() == null && currentNode.getLeftChild() == null) {
                decoded.append(currentNode.getCharacter());
                currentNode = getHuffmanTree();
            }
        }

        String mainFileName = "";
        for(String character : getFileName().split("")) {
            if(character.equals(".")) {
                break;                
            } else {
                mainFileName +=  character;
            }
        }
        String decompressedFileName = mainFileName + "(decompressed).txt";

        try {
            File file = new File(decompressedFileName);

            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(decoded.toString());
            bw.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}