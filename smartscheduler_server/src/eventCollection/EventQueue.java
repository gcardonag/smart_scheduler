package eventCollection;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class EventQueue implements Iterable<Event>, Serializable {

	private static final long serialVersionUID = -591139117032219084L;
	private LinkedList<Event> entries;
	
	public EventQueue() {
		entries = new LinkedList<Event>();
	}
	
	public EventQueue(EventQueue q) {
		for(Event e : q)
			entries.add(e);
	}
	
	public int size() {
		return entries.size();
	}

	public boolean isEmpty() {
		return entries.isEmpty();
	}

	public Event insert(Event event) {
		//System.out.println("INSERTING: "+event.toString());
		if(entries.isEmpty()) {
			entries.addFirst(event);
			//System.out.println("ADDED FIRST");
			return event;
		}
		else {
			Iterator<Event> iterator = iterator();
			Event cur = iterator.next();
			while(cur.compareTo(event) <= 0) {
				if(!iterator.hasNext()) {	
					entries.addLast(event);
					//System.out.println("ADDED LAST");
					return event;
				}
				cur = iterator.next();
			}
			entries.add(entries.indexOf(cur), event);
			//System.out.println("ADDED");
			return event;
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
			//System.out.println("REMOVED: "+min().toString());
			return entries.removeFirst();
		}
	}
	
	public void clear() {
		entries.clear();
	}
	
	public Event[] toArray() {
		Event[] a = new Event[size()];
		return entries.toArray(a);
	}

	public Iterator<Event> iterator() {
		return entries.iterator();
	}
	
	public EventQueue clone() {
		return new EventQueue(this);
	}
	
	public String toString() {
		return entries.toString()+" Size: "+size();
	}
	
}
