package priorityqueue;

import java.util.NoSuchElementException;

public interface PriorityQueue<K, V> { 
	/** The size of the collection…. **/
	int size(); 

	/** Returns true if empty; false, otherwise. **/ 
	boolean isEmpty(); 

	/** Add a new entry (key, value) to the queue. 
		    @param key the key for the entry
		    @param value the value for the entry
		    @return Reference to the Entry object creater for 
				the pair key-value. 
		    @throw IllegalArgumentException if key is invalid. 
	 **/
	Entry<K, V> insert(K key, V value) throws IllegalArgumentException; 

	/** Accesses entry in the collection having minimum value 
		    of the key according to a particular relation order.
		    @return Reference to an entry with min key value. 
		    @throw NoSuchElement if queue is empty. 
	 **/
	Entry<K, V> min() throws NoSuchElementException;


	/** Removes entry in the collection having minimum value 
		    of the key according to a particular relation order.
		    @return Reference to an entry removed. 
		    @throw EmptyPriorityQueueException if queue is empty. 
	 **/
	Entry<K, V> dequeue() throws NoSuchElementException; 
} 