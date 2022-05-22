/*
 * Name: Jack Kai Lim
 * PID: A16919063
 */

import java.util.Arrays;

/**
 * Hash Table for strings using linear probing Implementation
 * 
 * @author Jack Kai Lim
 * @since 05/18/2022
 */
public class HashTable implements IHashTable {
    /* Constant Variables */
    private final int DEFAULT_SIZE = 15;

    /* the bridge for lazy deletion */
    private static final String BRIDGE = new String("[BRIDGE]".toCharArray());

    /* instance variables */
    private int size; // number of elements stored
    private String[] table; // data table
    private int noRehash;
    private double[] statsLf;
    private int[] statsCollisions;

    /**
     * Constructor method for hash table
     */
    public HashTable() {
        /* Constructor for a default hash table */
        this.table = new String[DEFAULT_SIZE];
        this.size = 0;
        this.statsLf = new double[DEFAULT_SIZE];
        this.statsCollisions = new int[DEFAULT_SIZE];
        this.noRehash = 0;
    }

    /**
     * Constructor for a hash table with custom capacity
     * @param capacity The custo capacity wanted for the hash table
     */
    public HashTable(int capacity) {
        /* Constructor initializes the array of the hash table */
        int MIN_SIZE = 5;
        if (capacity < MIN_SIZE){
            throw new IllegalArgumentException();
        }
        this.table = new String[capacity];
        this.size = 0;
        this.statsLf = new double[DEFAULT_SIZE];
        this.statsCollisions = new int[DEFAULT_SIZE];
        this.noRehash = 0;
    }

    /**
     * Inserts a string to hash table
     * @param value value to insert
     * @return True of insert was successfull, false otherwise
     */
    @Override
    public boolean insert(String value) {
        /* Inserts the string value according to its hash value and handles the collisions using
        linear probing */
        if (value == null) {
            throw new NullPointerException();
        } else if (this.lookup(value)){
            return false;
        } else {
            double LOAD_FACTOR = 0.55;
            if ((double) this.size()/this.capacity() > LOAD_FACTOR) {
                this.rehash();
            }
            int bucket = hashString(value); //Hash key of the string
            int probed = 0;
            while (probed < this.capacity()){
                //Keeps probing until it has cycled the entire array once
                if (this.table[bucket] == null || this.table[bucket].equals(BRIDGE) ){
                    //If bucket is empty or was empty insert element
                    this.table[bucket] = value;
                    this.size++;
                    return true;
                }
                //Increment the bucket by 1, mods returns bucket to the first bucket
                bucket = (bucket + 1)%this.capacity();
                //Increment the number of buckets probed
                probed++;
            }
        }
        return false;
    }

    /**
     * Removes the value given from the hash table
     * @param value value to delete
     * @return True if successfully deleted, false otherwise(When not in the hash table)
     */
    @Override
    public boolean delete(String value) {
        /* Removes the given value from the hash table */
        if (value == null) {
            throw new NullPointerException();
        } else {
            int bucket = hashString(value); //Hash key of the string
            int probed = 0;
            while (probed < this.capacity() && this.table[bucket] != null) {
                //Keeps probing until it has cycled the entire array once or reaches an empty
                // since start bucket
                if (this.table[bucket].equals(value)) {
                    this.table[bucket] = BRIDGE; //Removes the string by adding a placeholder
                    this.size--;
                    return true;
                }
                //Increment the bucket by 1, mods returns bucket to the first bucket
                bucket = (bucket + 1) % this.capacity();
                //Increment the number of buckets probed
                probed++;
            }
        }
        return false;
    }

    /**
     * Checks whether the value given is in the hash table
     * @param value value to look up
     * @return True if value is in the hash table, false otherwise
     */
    @Override
    public boolean lookup(String value) {
        /* Searches the entire hash table for the value given */
        if (value == null){
            throw new NullPointerException();
        } else {
            int bucket = hashString(value); //Hash key of the string
            int probed = 0;
            while (probed < this.capacity() && this.table[bucket] != null){
                //Keeps probing until it has cycled the entire array once or reaches an empty
                // since start bucket
                if (this.table[bucket].equals(value)){
                    return true;
                }
                //Increment the bucket by 1, mods returns bucket to the first bucket
                bucket = (bucket + 1)%this.capacity();
                //Increment the number of buckets probed
                probed++;
            }
        }
        return false;
    }

    /**
     * Getter method that returns the number of elements in the hash table
     * @return Number of elements in the hash table
     */
    @Override
    public int size() {
        /* Returns the size of the hash table */
        return this.size;
    }

    /**
     * Getter method for capacity of Hash Table
     * @return Length of table array
     */
    @Override
    public int capacity() {
        /* Returns the length of the array */
        return this.table.length;
    }

    /**
     * Build a string to show statistics of hash table
     * @return String of statistics
     */
    public String getStatsLog() {
        /* Builds a string which shows the statistics of the Hash table */
        StringBuilder output = new StringBuilder();
        for (int i = 0;i<this.noRehash;i++){
            output.append("Before rehash # ").append(i + 1).append(": load " +
                    "factor ").append(String.format("%.2f", this.statsLf[i])).append(", ")
                    .append(this.statsCollisions[i]).append(" ").append("collisions(s).\n");
        }
        return output.toString();
    }

    /**
     * Resizes and rehashes all values in a hash table
     */
    private void rehash() {
        /* Saves the stats before the Rehash */
        if (this.noRehash == this.statsCollisions.length){
            //Resizing collision array
            int statsColsDoubled = this.statsCollisions.length*2;
            int[] tempCol = this.statsCollisions.clone();
            this.statsCollisions = new int[statsColsDoubled];
            System.arraycopy(tempCol, 0, this.statsCollisions, 0, tempCol.length);
            //Resizing load factor array
            int statsLfDoubled = this.statsLf.length*2;
            double[] tempLf = this.statsLf.clone();
            this.statsLf = new double[statsLfDoubled];
            System.arraycopy(tempCol, 0, this.statsLf, 0, tempLf.length);
        }
        this.statsLf[noRehash] = (double) this.size/this.capacity();
        this.statsCollisions[noRehash] = this.countCollisions();
        this.noRehash++;
        /* Resizes the hash table */
        String[] temp = this.table.clone(); //Make a copy of the current array
        //Creates a new array for the Hash table double the size
        int capacityDoubled = this.capacity()*2;
        this.table = new String[capacityDoubled];
        this.size = 0;
        for (int i = 0;i<temp.length;i++){
            if (temp[i] != null && !temp[i].equals(BRIDGE)){
                //Reinserts all values to the new array
                this.insert(temp[i]);
            }
        }
    }

    /**
     * Calculate the hash value for a string
     * @param value The string to get the hash for
     * @return The hash key
     */
    private int hashString(String value) {
        /* Uses CRC to get a hash key for unique strings */
        int CRC_LEFT = 5;
        int CRC_RIGHT = 27;
        int hashValue = 0;
        for (int i = 0;i<value.length();i++){
            int leftValue = hashValue << CRC_LEFT;
            int rightValue = hashValue>>> CRC_RIGHT;
            hashValue = (leftValue | rightValue)^value.charAt(i);
        }
        return Math.abs(hashValue % this.table.length);
    }

    /**
     * Counts the number of collisions that happen
     * @return Number of collisions
     */
    private int countCollisions(){
        /* Counts the number of collisions by checking if the hash key of the value matches the
        index on the array. If it doesn't match, there was a collision. */
        int count = 0;
        for (int i = 0;i<this.capacity();i++){
            if (this.table[i] != null && !this.table[i].equals(BRIDGE)){
                if (hashString(this.table[i]) != i){
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Returns the string representation of the hash table.
     * This method internally uses the string representation of the table array.
     * DO NOT MODIFY. You can use it to test your code.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return Arrays.toString(table);
    }
}
