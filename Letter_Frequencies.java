import java.io.*;
import java.util.HashMap;
//import java.util.Arrays;

public class Letter_Frequencies {
    
    private String filename;
    private HashMap<Character, Integer> freq = new HashMap<>();

    /**
     * Letter_Frequencies constructor
     * @param filename
     */
    public Letter_Frequencies(String filename) {
        this.filename = filename;
        setFrequencyTable(buildFrequencyTable());
    }

    // getters 
    public String getFileName() {return filename;}
    public HashMap<Character,Integer> getFrequencyTable() {return freq;}

    // setter
    public void setFrequencyTable(HashMap<Character,Integer> freq) {this.freq = freq;}

    /**
     * This method build the frequency table.
     * @return the HashMap<Character, String> freq
     */
    public HashMap<Character,Integer> buildFrequencyTable() {
            
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

            
            for(char character: fileBuilder.toString().toCharArray()) {
                if(freq.containsKey(character)) {
                    freq.put(character, freq.get(character) +1);
                } else {
                        freq.put(character, 1);
                } /*else {
                    freq.put('\n', freq.getOrDefault('\n',0) +1);
                }*/
            }


            // Always close files.
            bufferedReader.close();   
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                filename + "'");                
        }
        catch(IOException ex) {
            ex.printStackTrace();                
        }
        return freq;
    }

}