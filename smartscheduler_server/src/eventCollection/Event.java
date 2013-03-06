package eventCollection;

import java.util.Calendar;

public class Event implements Comparable<Event> {

	private int id; // for now only being used for testing purposes
	private String name;
	protected Calendar start;
	protected Calendar end;
	private boolean staticEvent;
	private boolean repeating;
	protected RecurrenceGroup recurrenceGroup;
	
	public Event(String name, Calendar start, Calendar end, boolean stat, boolean repeating)
	{
		this.id = 0;
		this.name = name;
		this.start = (Calendar) start.clone();
		this.end = (Calendar) end.clone();
		this.repeating = repeating;
		if(!repeating)
			recurrenceGroup = null;
		staticEvent = stat;
	}
	
	public Event(Event source) {
		name = source.getName();
		start = (Calendar) source.getStart().clone();
		end = (Calendar) source.getEnd().clone();
		staticEvent = source.isStatic();
		repeating = source.isRepeating();
		recurrenceGroup = source.getRecurrenceGroup();
	}
	
	public String getName()
	{
		return name;
	}
	
	public Calendar getStart()
	{
		return start;
	}
	
	public Calendar getEnd()
	{
		return end;
	}
	
	public boolean isRepeating() {
		return repeating;
	}
	
	public void setRepeating(boolean repeating) {
		this.repeating = repeating;
	}
	
	public boolean isStatic()
	{
		return staticEvent;
	}

	public int compareTo(Event other) {
		return start.compareTo(other.start);
	}
	
	public boolean conflictsWith(Event other)
	{
		if (start.compareTo(other.start) < 0)
			return (end.compareTo(other.start) > 0);
		else
			return (start.compareTo(other.end) < 0);
	}

	/**@deprecated
	 * Determines whether a time unit falls in the time range
	 * of this event.
	 * 
	 * @param time - the time unit.
	 * @return true if the unit occurs in this event's time range,
	 * false otherwise.
	 */
	public boolean containsTime(Calendar time) {
		return (start.compareTo(time) <= 0 &&
				end.compareTo(time) >= 0);
	}

	public RecurrenceGroup getRecurrenceGroup() {
		return recurrenceGroup;
	}

	public void setRecurrenceGroup(RecurrenceGroup group) {
		recurrenceGroup = group;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getDuration() {
		return start.compareTo(end);
	}
	
	public String toString() {
		String str = "SmartScheduler.Event{"
				//+ " 'ID': " + id  
				+ ", 'Name' : " + name
				+ ", 'CalendarRange' : [ "+ start.getTime().toString()  
					+ " - " + end.getTime().toString() + " ]"
				//+ ", 'Static' : "+isStatic()
				//+ ", 'Repeating' : " + isRepeating()
				+ "}"
				;
		return str;
		
		
	}
}
