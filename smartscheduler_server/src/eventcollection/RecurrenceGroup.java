package eventcollection;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class RecurrenceGroup {
	
	private Event parent;
	private int recurrence; // 1-daily, 2-weekly, 3-monthly, 4-yearly (not used)
	private int interval;
	private Calendar end;
	private boolean[] weekly;
	private List<Event> group;
	
	public RecurrenceGroup(Event parent, int recurrence, int interval, Calendar end, boolean[] weekly) {
		this.parent = parent;
		this.recurrence = recurrence;
		this.interval = interval;
		this.end = end;
		this.weekly = weekly;
		group = new LinkedList<Event>();
		parent.setRecurrenceGroup(this);
		process();
	}
	
	public void process() {
		Calendar currentStart = (Calendar) parent.getStart().clone();
		Calendar currentEnd = (Calendar) parent.getEnd().clone();
		
		while(currentStart.compareTo(end) < 0) {
			Calendar newStart = (Calendar) currentStart.clone();
			Calendar newEnd = (Calendar) currentEnd.clone();
			Event r = new Event(parent.getName(), newStart, newEnd, parent.isStatic(), true);
			r.setRecurrenceGroup(this);
			
			// DAILY
			if(recurrence == 1) {
				group.add(r);
				currentStart.add(Calendar.DATE, interval);
				currentEnd.add(Calendar.DATE, interval);
			}
			// WEEKLY
			else if(recurrence == 2) {
				if(weekly[currentStart.get(Calendar.DAY_OF_WEEK)-1]) {
					group.add(r);
				}
				currentStart.add(Calendar.DATE, 1);
				currentEnd.add(Calendar.DATE, 1);
				if(currentStart.get(Calendar.DAY_OF_WEEK) == 7) {
					currentStart.add(Calendar.WEEK_OF_YEAR, interval);
					currentEnd.add(Calendar.WEEK_OF_YEAR, interval);
				}
				
			}
			// MONTHLY
			else if(recurrence == 3) {
				group.add(r);
				currentStart.add(Calendar.MONTH, interval);
				currentEnd.add(Calendar.MONTH, interval);
			}
			
			if(group.size() <= 1) {
				parent.setRepeating(false);
				parent.setRecurrenceGroup(null);
			}
		}
			
	}
	
	public List<Event> getEventsInRecurrenceGroup() {
		return group;
	}
}
