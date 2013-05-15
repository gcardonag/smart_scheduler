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
	 * @deprecated Functionality is already implemented directly in the scheduler.
	 */
	public static void processEvents(List<Event> events, EventTree q, EventTree c) {
		Iterator<Event> iterator = events.iterator();
		Event current;
		while(iterator.hasNext()) {
			current = iterator.next();
			if(current.isStatic()) {
				c.add(current);
			}
			else {
				q.add(current);
			}
		}
	}

}
