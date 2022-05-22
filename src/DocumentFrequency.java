/*
 * NAME: Jack Kai Lim
 * PID: A16919063
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Document Frequency Implementation
 *
 * @author Jack Kai Lim
 * @since 05/19/2022
 */
public class DocumentFrequency {

    /* Instance Variables */
    private final ArrayList<HashTable> hashTables;
    private int noDocs;

    /**
     * Constructor for the Document Frequency
     * @param path The filepath of the document to be read
     * @throws IOException Throws when the file cannot be read or does not exist
     */
    public DocumentFrequency(String path) throws IOException {
        /* Constructor the filled in the instance variables and also populates the Hash Table
        with all the unique words in the document */
        this.hashTables = new ArrayList<HashTable>();
        this.noDocs = 0;
        File file = new File(path);
        Scanner scanner = new Scanner(file);

        /* Populate the hash tables with all the unique words in each document */
        while (scanner.hasNextLine()) {
            String[] doc = scanner.nextLine().split(" ");
            HashTable temp = new HashTable();
            for (String s : doc){
                temp.insert(s.toLowerCase());
            }
            this.hashTables.add(temp);
            this.noDocs++;
        }
    }

    /**
     * Getter that returns the number of documents (lines) from the file
     * @return Number of lines
     */
    public int numDocuments() {
        /* Returns the instance variable that keeps count of the number of lines in the file */
        return this.noDocs;
    }

    /**
     * Iterates through the list of hashtables, and checks if the word exist or not in that
     * document.
     * @param word Word to search for
     * @return Number of times the word appears in the document
     */
    public int query(String word) {
        /* Iterates through the list of hashtables and checks if the word exist or not in the
        document */
        int count = 0;
        for (int i = 0;i<this.noDocs;i++){
            if (this.hashTables.get(i).lookup(word.toLowerCase())){
                count++;
            }
        }
        return count;
    }

}
