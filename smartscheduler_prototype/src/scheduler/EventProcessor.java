package scheduler;

import java.util.Iterator;
import java.util.List;
import eventCollection.*;

/**
 * Distrubutes events to a queue or a tree according to it's type.
 * @author Nelson Reyes Ciena
 * @deprecated
 * 
 */
public class EventProcessor {
	
	/**
	 * Distributes static and dynamic events into a corresponding collection or queue
	 * @param events the list of events to be processed
	 * @param q a destination EventQueue
	 * @param c a destination EventCollection
	 */
	public static void processEvents(List<Event> events, EventQueue q, EventTree c) {
		Iterator<Event> iterator = events.iterator();
		Event current;
		while(iterator.hasNext()) {
			current = iterator.next();
			if(current.isStatic()) {
				c.add(current);
			}
			else {
				q.offer(current);
			}
		}
	}

}
