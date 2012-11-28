package scheduler;

import java.util.Iterator;
import java.util.List;
import eventcollection.*;

public class EventProcessor {
	
	public static boolean processEvents(List<Event> list, EventQueue q, EventCollection c) {
		Iterator<Event> iterator = list.iterator();
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
		if(list.size() != (q.size() + c.size()))
			return false;
		return true;
	}
	
	public static void processQueue(EventQueue q, EventCollection c) {
		
	}

}
