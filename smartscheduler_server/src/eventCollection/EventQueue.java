package eventCollection ;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class EventQueue implements Iterable<Event> {

	private int size;
	private LinkedList<Event> entries;
	
	public EventQueue() {
		entries = new LinkedList<Event>();
		size = 0;
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean insert(Event event) {
		if(entries.isEmpty()) {
			entries.addLast(event);
			size++;
			return true;
		}
		else {
			Iterator<Event> iterator = iterator();
			Event cur = iterator.next();
			while(cur.compareTo(event) < 0) {
				if(!iterator.hasNext())
					return false;
				cur = iterator.next();
			}
			return true;
		}
	}

	public Event min() throws NoSuchElementException {
		if(entries.isEmpty())
			throw new NoSuchElementException("event queue is empty");
		else
			return entries.getFirst();
	}

	public Event removeMin() throws NoSuchElementException {
		if(entries.isEmpty())
			throw new NoSuchElementException("event queue is empty");
		else {
			size--;
			return entries.removeFirst();
		}
	}
	
	public void clear() {
		entries.clear();
	}
	
	public Event[] toArray() {
		Event[] a = new Event[size];
		return entries.toArray(a);
	}

	public Iterator<Event> iterator() {
		return entries.iterator();
	}
	
}
