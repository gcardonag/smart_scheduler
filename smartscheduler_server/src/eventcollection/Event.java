package eventcollection;

import java.util.Calendar;

public class Event implements Comparable<Event> {

	private int id; //not used yet
	private String name;
	private Calendar start;
	private Calendar end;
	private boolean staticEvent;
	private boolean repeating;
	private RecurrenceGroup recurrenceGroup;
	
	public Event(String name, Calendar start, Calendar end, boolean stat, boolean repeating)
	{
		this.name = name;
		this.start = start;
		this.end = end;
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

	public boolean containsTime(Calendar time) {
		return (start.compareTo(time) >= 0 &&
				end.compareTo(time) <= 0);
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
}
