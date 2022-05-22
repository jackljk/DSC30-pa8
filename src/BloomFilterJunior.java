/*
 * NAME: Jack Kai Lim
 * PID: A16919063
 */

/**
 * Bloom Filter Junior Implmentation
 *
 * @author Jack Kai Lim
 * @since 05/19/2022
 */
public class BloomFilterJunior {

    /* Constants */
    private static final int MIN_INIT_CAPACITY = 50;
    private static final int BASE256_LEFT_SHIFT = 8;
    private static final int HORNERS_BASE = 27;

    /* Instance variables */
    private boolean[] table;

    /**
     * Constructor for the BloomFilter
     * @param capacity Initial size of the bloom-filter's boolean array
     */
    public BloomFilterJunior(int capacity) {
        /* Initializes the boolean table */
        if (capacity < MIN_INIT_CAPACITY){
            throw new IllegalArgumentException();
        }
        this.table = new boolean[capacity];
    }

    /**
     * Insert a string value into the table
     * @param value String to be added
     */
    public void insert(String value) {
        /* Calculates the hash number of the string using 3 different formulas, and changes the
        values at the index to be true. */
        if (value == null){
            throw new NullPointerException();
        }

        int base256 = hashBase256(value);
        this.table[base256] = true;
        int CRC = hashCRC(value);
        this.table[CRC] = true;
        int horners = hashHorners(value);
        this.table[horners] = true;

    }

    /**
     * Looksup the value to see if it is present
     * @param value Value to lookup
     * @return True if value was inserted, false otherwise
     */
    public boolean lookup(String value) {
        /* Checks to see if all 3 indexes of the calculated hash numbers are true. All 3 must be
        true to return true */
        if (value == null){
            throw new NullPointerException();
        }
        int base256 = hashBase256(value);
        int CRC = hashCRC(value);
        int horners = hashHorners(value);
        return this.table[base256] && this.table[CRC] && this.table[horners];
    }

    /**
     * Base-256 hash function.
     *
     * @param value string to hash
     * @return hash value
     */
    private int hashBase256(String value) {
        int hash = 0;
        for (char c : value.toCharArray()) {
            hash = ((hash << BASE256_LEFT_SHIFT) + c) % table.length;
        }
        return Math.abs(hash % table.length);
    }

    /**
     * Simplified CRC hash function.
     *
     * @param value string to hash
     * @return hash value
     */
    private int hashCRC(String value) {
        /* Uses CRC to get a hash key for unique strings */
        int hashValue = 0;
        int CRC_LEFT = 5;
        int CRC_RIGHT = 27;
        for (int i = 0;i<value.length();i++){
            int leftValue = hashValue << CRC_LEFT;
            int rightValue = hashValue>>>CRC_RIGHT;
            hashValue = (leftValue | rightValue)^value.charAt(i);
        }
        return Math.abs(hashValue % this.table.length);
    }

    /**
     * Horner's hash function.
     *
     * @param value string to hash
     * @return hash value
     */
    private int hashHorners(String value) {
        int hash = 0;
        for (char c : value.toCharArray()) {
            hash = (hash * HORNERS_BASE + c) % table.length;
        }
        return Math.abs(hash % table.length);
    }
}
