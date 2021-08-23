import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.nio.file.*;


public class HuffmanApp {
    /**
     * The main class from where the app is run.
     * @param args
     */
    public static void main(String[] args) {

        String function = args[0];
        String fileName = args[1];

        HuffmanCoding huff = new HuffmanCoding("artificial.txt");
    
        if(function.equals("compress")) {
            huff.compress();
        } else if(function.equals("decompress")) {
            huff.decompress();
        } else {
            System.out.println("Invalid input!");
        }
    }
}