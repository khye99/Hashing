package hash;

import java.util.Hashtable;
import java.util.LinkedList;

//
// STRINGTABLE.JAVA
// A hash table mapping Strings to their positions in the the pattern sequence
// You get to fill in the methods for this part.
//
public class StringTable {
    
    private LinkedList<Record>[] buckets; // This is already creating an array of linked list objects, just like how you can create
    									  // an array of integers, this is how to define and array of linkedlist objects
    private int nBuckets; 				  // number of buckets that make up the table

    //
    // number of records currently stored in table --
    // must be maintained by all operations
    //
    public int size;
    
    
    // Create an empty table with nBuckets buckets
    //
    @SuppressWarnings("unchecked")
	public StringTable(int nBuckets) {
    	this.nBuckets = nBuckets;
    	buckets = new LinkedList[nBuckets];		// allocating enough space for all the linkedlist to be in
    	
    	this.size = 0;
    
    	// TODO - fill in the rest of this method to initialize your table
    }
    /**
     * insert - inserts a record to the StringTable
     *
     * @param r
     * @return true if the insertion was successful, or false if a
     *         record with the same key is already present in the table.
     */
    public boolean insert(Record r) {  
    	// have to check if key already exists in table
        int hashcode = stringToHashCode(r.key);				// turn the record's string into a hashcode
        int indx = toIndex(hashcode);						// turn that hashcode into an index of the table
        
        
        //No linked list yet
        if (buckets[indx] == null) {
        	 LinkedList<Record> items = new LinkedList<Record>();
        	 buckets[indx] = items; 						//make that entire linked list and then put that linked list 
        	 												//inside that table cell bcuz each table cell is supposed 
        	 												//to hold a linked list object
        	 buckets[indx].add(r);	
        	 size++;
        	 return true;
        } 
        
        //There's already a list
        else {												
        	// Should check if r is equal to an existing Record
        	for(Record i : buckets[indx]) {
        		if (i.key.equals(r.key)) { 					// if new Record matches existing Record
        			return false;
        		}
        	}
        	buckets[indx].add(r);							// if not, add new Record in
			size++;
			return true;
        }

    }
    
    
    /**
     * find - finds the record with a key matching the input.
     *
     * @param key
     * @return the record matching this key, or null if it does not exist.
     */
    public Record find(String key) {
    	int hashcode = stringToHashCode(key);			
        int indx = toIndex(hashcode);						
        
        if (buckets[indx] == null) {
        	return null;
        }
        else {
        	for (Record i : buckets[indx]) {
        		if (i.key.equals(key)) {
        			return i;						
        		}
        	}
        }
        return null;
    }
    
    
    /**
     * remove - finds a record in the StringTable with the given key
     * and removes the record if it exists.
     *
     * @param key
     */
    public void remove(String key) {
        int hashcode = stringToHashCode(key);				// turn the record's string into a hashcode
        int indx = toIndex(hashcode);						// turn that hashcode into an index of the table

        if (buckets[indx] != null) {						// if there exists a linked list
        	for(Record i : buckets[indx]) {
        		if (i.key.equals(key)) {
        			buckets[indx].remove(i);
        			size--;
        			return;
        		}
        	}
        }
    }
    

    /**
     * toIndex - convert a string's hashcode to a table indexx
     *
     * As part of your hashing computation, you need to convert the
     * hashcode of a key string (computed using the provided function
     * stringToHashCode) to a bucket index in the hash table.
     *
     * You should use a multiplicative hashing strategy to convert
     * hashcodes to indices.  If you want to use the fixed-point
     * computation with bit shifts, you may assume that nBuckets is a
     * power of 2 and compute its log at construction time.
     * Otherwise, you can use the floating-point computation.
     */
    int toIndex(int hashcode) {
    	// Fill in your own hash function here
    	double indx = hashcode * ((Math.sqrt(5) - 1.0) / 2.0) % 1.0;
    	indx = Math.abs(indx);
    	return (int) (indx * nBuckets);
    }
    
    
    
    /**
     * stringToHashCode
     * Converts a String key into an integer that serves as input to
     * hash functions.  This mapping is based on the idea of integer
     * multiplicative hashing, where we do multiplies for successive
     * characters of the key (adding in the position to distinguish
     * permutations of the key from each other).
     *
     * @param string to hash
     * @returns hashcode
     */
    static int stringToHashCode(String key) { // this String key should just be a Record.key? 
    	int A = 1952786893; 							// some random ass number
    	int v = A; 										// storing that number in a variable now?
    	//System.out.println(v);
    	for (int j = 0; j < key.length(); j++) { 		// as long as the length is less than the string's length
    		char c = key.charAt(j); 					// character at that position of the string is going to be hashed
    		v = A * (v + (int) c + j) >> 16;			// char cast into int, add the index and # to that, mult that # and shift right
    		//System.out.println(v);						// for a String, do the above and add gotten val for each char together
	    }
    	return v;
    	
    	// sample outputs: 'h' = 5038
        //				   'i' = -30701
    	//				   'j' = -904
    	//				   'k' = 28893    'K' = -7112
    }

    /**
     * Use this function to print out your table for debugging
     * purposes.
     */
    public String toString() {
    	StringBuilder sb = new StringBuilder();		// declared a String object, will append onto this
	
    	for (int i = 0; i < nBuckets; i++) {		
    		sb.append(i+ "  ");
    		if (buckets[i] == null) {
    			sb.append("\n");
    			continue;
		    }
 
    		for (Record r : buckets[i]) { 
    			sb.append(r.key + "  ");
		    }
    		sb.append("\n");
	    }
    	return sb.toString();

    }
    
    
    public static void main (String[] args) {
    	// print out errors debug
    	StringTable rekt = new StringTable(10);
    	Record k = new Record("karen");
    	Record a = new Record("ye");
    	Record y = new Record("karen");
    	Record kk = new Record("karenal");
    	
    	rekt.insert(a);
    	rekt.insert(k); // both go to bucket 2, error
    	rekt.insert(y);
    	rekt.insert(kk);

    }
}
