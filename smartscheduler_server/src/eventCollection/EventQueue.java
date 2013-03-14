package eventCollection;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * A queue structure designed for holding <code>Event</code> elements prior to processing. Uses <code>LinkedList</code> as it's
 * underlying data structure.
 * @author Nelson Reyes Ciena
 * @see LinkedList
 *
 */
public class EventQueue implements Iterable<Event>, Serializable, Cloneable {

	private static final long serialVersionUID = -591139117032219084L;
	private LinkedList<Event> entries;
	
	/**
	 * Default constructor for the queue.
	 */
	public EventQueue() {
		entries = new LinkedList<Event>();
	}
	
	/**
	 * Constructor to create a clone of this object.
	 * @param source the source <code>EventQueue</code> to clone from
	 */
	public EventQueue(EventQueue source) {
		entries = new LinkedList<Event>();
		for(Event e : source)
			entries.add(e);
	}
	
	public int size() {
		return entries.size();
	}

	/**
	 * Returns true if this queue is empty.
	 * @return true if this queue contains no elements
	 */
	public boolean isEmpty() {
		return entries.isEmpty();
	}

	/**
	 * Inserts the specified <code>Event</code> into this queue
	 * @param event the event to insert
	 * @return
	 */
	public boolean offer(Event event) {
		if(entries.isEmpty()) {
			entries.addFirst(event);
			return true;
		}
		else {
			Iterator<Event> iterator = iterator();
			Event cur = iterator.next();
			while(cur.compareTo(event) <= 0) {
				if(!iterator.hasNext()) {	
					entries.addLast(event);
					//System.out.println("ADDED LAST");
					return true;
				}
				cur = iterator.next();
			}
			entries.add(entries.indexOf(cur), event);
			//System.out.println("ADDED");
			return true;
		}
	}
	
	/**
	 * Retrieves, but does not remove, the head of this queue, returning null if this queue is empty.
	 * @return the head of this queue, or null if this queue is empty.
	 */
	public Event peek() {
		if(entries.isEmpty())
			return null;
		return entries.getFirst();
	}

	/**
	 * Retrieves, but does not remove, the head of this queue.
	 * @return the head of this queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	public Event element() throws NoSuchElementException {
		if(entries.isEmpty())
			throw new NoSuchElementException("event queue is empty");
		return entries.getFirst();
	}
	
	/**
	 * Retrieves and removes the head of this queue, or null if this queue is empty.
	 * @return the head of this queue, or null if this queue is empty.
	 */
	public Event poll() {
		if(entries.isEmpty())
			return null;
		return entries.removeFirst();
	}

	/**
	 * Retrieves and removes the head of this queue. This method differs from the poll method in that it throws an exception if this queue is empty.
	 * @return
	 * @throws NoSuchElementException if the queue is empty
	 */
	public Event remove() throws NoSuchElementException {
		if(entries.isEmpty())
			throw new NoSuchElementException("event queue is empty");
		else {
			//System.out.println("REMOVED: "+min().toString());
			return entries.removeFirst();
		}
	}
	
	/**
	 * Clears all elements from the queue, making it empty.
	 */
	public void clear() {
		entries.clear();
	}
	
	/**
	 * Returns an array containing all of the elements in this queue in the correct order.
	 * @return
	 */
	public Event[] toArray() {
		Event[] a = new Event[size()];
		return entries.toArray(a);
	}

	/**
	 * Returns an iterator over a set of elements of type <code>Event</code>.
	 * @return an iterator
	 */
	public Iterator<Event> iterator() {
		return entries.iterator();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public EventQueue clone() {
		return new EventQueue(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return entries.toString()+" Size: "+size();
	}
	
}
