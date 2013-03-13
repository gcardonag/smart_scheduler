package eventCollection;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Event implements Comparable<Event> {
	
	public static final String DATE_FORMAT = "d/MMM/yyyy kk:mm";
	private int id; // unused
	private String name;
	protected Calendar start;
	protected Calendar end;
	private boolean staticEvent;
	private boolean repeating;
	private RecurrenceGroup recurrenceGroup;
	private int duration; //event duration in minutes
	
	public Event(String name, Calendar start, Calendar end, boolean stat, boolean repeating)
	{
		this.name = name;
		this.start = (Calendar) start.clone();
		this.end = (Calendar) end.clone();
		setDuration();
		this.repeating = repeating;
		if(!repeating)
			recurrenceGroup = null;
		staticEvent = stat;
	}
	
	public Event(Event source) {
		name = source.getName();
		start = (Calendar) source.getStart().clone();
		end = (Calendar) source.getEnd().clone();
		duration = source.getDuration();
		staticEvent = source.isStatic();
		repeating = source.isRepeating();
		recurrenceGroup = source.getRecurrenceGroup();
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setDate(Calendar start, Calendar end) {
		this.start = (Calendar) start.clone();
		this.end = (Calendar) end.clone();
		setDuration();
	}
	
	public void setStart(Calendar start) {
		this.start = (Calendar) start.clone();
		setDuration();
	}
	
	public Calendar getStart()
	{
		return (Calendar) start.clone();
	}
	
	public void setEnd(Calendar end) {
		this.end = (Calendar) end.clone();
		setDuration();
	}
	
	public Calendar getEnd()
	{
		return (Calendar) end.clone();
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
		return start.compareTo(other.getStart()); //THIS NEEDS TO BE CHANGED
	}
	
	public boolean conflictsWith(Event other)
	{
		Event earlier = start.before(other.getStart()) ? this : other;
		Event later = start.before(other.getStart()) ? other: this;
		return earlier.getEnd().after(later.getStart());
	}

	public boolean containsTime(Calendar time) {
		return start.before(time) && end.after(time);
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
	
	private void setDuration() {
		int s = start.get(Calendar.HOUR_OF_DAY)*60+start.get(Calendar.MINUTE);
		int day_diff = (end.get(Calendar.DAY_OF_YEAR)-start.get(Calendar.DAY_OF_YEAR))*24;
		int e = (day_diff+end.get(Calendar.HOUR_OF_DAY))*60+end.get(Calendar.MINUTE);
		duration = e-s;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public String toString() {
		SimpleDateFormat f = new SimpleDateFormat(DATE_FORMAT);
		String start_str = f.format(start.getTime());
		String end_str = f.format(end.getTime());
		String s = isStatic() ? "S" : "D";
		String r = isRepeating() ? "R" : "NR";
		return name+"[("+duration+") "+start_str+" - "+end_str+", "+s+", "+r+"]";
	}
	
}
