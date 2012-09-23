package humberto;

import java.util.Date;
import java.util.LinkedList;

public class Event implements Comparable<Event> {

	private String name;
	private Date start;
	private Date end;
	private boolean staticEvent;
	private boolean recurringEvent;
	private LinkedList<Event> recurrenceGroup;
	
	public Event(String name, Date start, Date end)
	{
		this.name = name;
		this.start = start;
		this.end = end;
		staticEvent = true;
		recurringEvent = false;
		recurrenceGroup = null;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Date getStart()
	{
		return start;
	}
	
	public Date getEnd()
	{
		return end;
	}
	
	public boolean isStatic()
	{
		return staticEvent;
	}
	
	public boolean isRecurring()
	{
		return recurringEvent;
	}
	
	public LinkedList<Event> getRecurrenceGroup()
	{
		return recurrenceGroup;
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

	public boolean containsTime(Date time) {
		return (start.compareTo(time) >= 0 &&
				end.compareTo(time) <= 0);
	}
}
