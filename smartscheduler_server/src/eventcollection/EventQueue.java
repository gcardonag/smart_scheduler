package eventcollection;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class EventQueue<K, V> {

	private int size;
	private LinkedList<EventEntry<K, V>> entries;
	private Comparator<K> c;
	
	public EventQueue(Comparator<K> comp) {
		entries = new LinkedList<EventEntry<K, V>>();
		c = comp;
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public EventEntry<K, V> insert(K key, V value) throws IllegalArgumentException {
		// TODO check key method required to throw exception
		EventEntry<K, V> EventEntry = new EventEntry<K, V>(key, value);
		insertEventEntry(EventEntry);
		return EventEntry;
	}

	public EventEntry<K, V> min() throws NoSuchElementException {
		if(entries.isEmpty())
			throw new NoSuchElementException("event queue is empty");
		else
			return entries.getFirst();
	}

	public EventEntry<K, V> removeMin() throws NoSuchElementException {
		if(entries.isEmpty())
			throw new NoSuchElementException("event queue is empty");
		else
			return entries.removeFirst();
	}
	
	private void insertEventEntry(EventEntry<K, V> e) {
		if(entries.isEmpty()) {
			entries.addFirst(e);
		}
		else if (c.compare(e.getKey(), entries.getLast().getKey()) > 0) {
			entries.addLast(e);
		}
		else {
			int curr = 1;
			while (c.compare(e.getKey(), entries.get(curr).getKey()) > 0) {
				curr++;
			}
			entries.add(curr-1, e);
		}
	}
	
	/**
	 * Internal class for queue entries
	 */
	private static class EventEntry<K, V> {
		
		private K k; // key
		private V v; // value
		
		public EventEntry(K key, V value) {
			k = key;
			v = value;
		}
		
		public K getKey() {
			return k;
		}
		
		public V getValue() {
			return v;
		}
	}
	
}
