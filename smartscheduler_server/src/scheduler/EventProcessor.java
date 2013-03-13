package scheduler;

import java.util.Iterator;
import java.util.List;
import eventCollection.*;

public class EventProcessor {
	
	/**
	 * Distributes static and dynamic events into a corresponding collection or queue
	 * @param events the list of events to be processed
	 * @param q a destination EventQueue
	 * @param c a destination EventCollection
	 */
	public static void processEvents(List<Event> events, EventQueue q, EventCollection c) {
		Iterator<Event> iterator = events.iterator();
		Event current;
		while(iterator.hasNext()) {
			current = iterator.next();
			if(current.isStatic()) {
				c.add(current);
			}
			else {
				q.insert(current);
			}
		}
	}

}
