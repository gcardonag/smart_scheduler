package eventCollection;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

/**Class that creates a recurrent group of events based on a given
 * <code><i>time length</i></code> and end date. It also considers events which
 * repeat only on specific days of the week an include recurrence.
 * 
 * @author Nelson Reyes Ciena
 * @author Anthony Llanos
 */
public class RecurrenceGroup {
	
	public static final int DAILY = Calendar.DAY_OF_YEAR;
	public static final int WEEKLY = Calendar.WEEK_OF_YEAR;
	public static final int MONTHLY = Calendar.MONTH;
	
	/** The event that represents the range of recurrent events. */
	private Event parent;
	
	/**
	 * Denotes on which basis the recurrence occurs.
	 * (Daily, Weekly, Monthly)
	 */
	private int recurrence; 
	
	
	/** Denotes the recurrence interval: Event occurs every [ recurrence ] units. */
	private int interval;
	
	/** The end calendar day for the recurring event. */
	private Calendar end;
	
	/** An array that indicates on which days of the week will the ocurrence occur.*/
	private boolean[] weekly;
	
	/** A linked list which contains the recurrent events created by the class.*/
	private List<Event> group;
	
	/**Constructor initializing the recurrence group with the base event,
	 * and the parameters for recurrence (interval, end, weekdays).
	 * @param parent - the base event.
	 * @param recurrence the recurrence flag. If true then recurrent event.
	 * @param interval - The number of skipped units between repeating events.
	 * @param end - the end date of the recurrence.
	 * @param weekly - an array that determines on which days will the recurrence occur.
	 */
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
	
	/** Processes and recreates the concurrent list of events based on the parameters. */
	public void process() {
		Calendar currentStart = (Calendar) parent.getStart().clone();
		
		/////////////////
		//
		Calendar currentEnd = new GregorianCalendar();
		currentEnd.set(currentStart.get(Calendar.YEAR), 
						currentStart.get(Calendar.MONTH), 
						currentStart.get(Calendar.DAY_OF_MONTH),
						parent.getEnd().get(Calendar.HOUR_OF_DAY), 
						parent.getEnd().get(Calendar.MINUTE), 
						parent.getEnd().get(Calendar.SECOND)) ;
		//TimeSlots?
		///////////////////////
		
		//Iterate until end date while incrementing the interval length.
		while(currentStart.compareTo(end) < 0) {
			Event r = new Event(parent.getName(), currentStart, currentEnd ,parent.isStatic(),parent.isRepeating());
			r.setRecurrenceGroup(this);
			r.setRepeating(true);
			
			// DAILY recurrence
			if(recurrence == DAILY) {
				group.add(r);
				currentStart.add(Calendar.DATE, interval);
				currentEnd.add(Calendar.DATE, interval);
				
			}
			// WEEKLY recurrence
			else if(recurrence == WEEKLY) {
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
			else if(recurrence == MONTHLY) {
				group.add(r);
				currentStart.add(Calendar.MONTH, interval);
				currentEnd.add(Calendar.MONTH, interval);
			}
		}
		if(group.size() == 1) {
			Event temp = group.remove(0);
			temp.setRecurrenceGroup(null);
			temp.setRepeating(false);
		}
	}
	
	/**Returns the created list of recurrent events.
	 * @return
	 */
	public List<Event> getEventsInRecurrenceGroup() {
		return group;
	}
	
	/**Returns the recurrence interval for this recurrence group.
	 * @return the recurrence interval.
	 */
	public int getRecurrence(){
		return this.recurrence;
	}
	
	/**Returns the interval at which the recurrence occurs for
	 * this group.
	 * @return an integer that represents the interval.
	 */
	public int getInterval(){
		return this.interval ;
	}
	
	/**Returns whether a this recurrence occurs on a particular
	 * event if its recurrence is weekly. False otherwise.
	 * @param weekday - the day for which to check.
	 * @return a boolean value.
	 */
	public boolean occursOnWeekday(int weekday){
		if(recurrence == WEEKLY){
			return weekly[weekday-1] ;
		}
		return false ;
	}
	
	public String toString(){
		return group.toString();
	}
}
