package eventCollection;

import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 * Represents a single event
 * @author user
 *
 */
public class Event implements Comparable<Event> {
	
	/**
	 * Format used to parse <code>Event</code> dates with the <code>SimpleDateFormat</code>.
	 * @see SimpleDateFormat
	 */
	public static final String DATE_FORMAT = "d/MMM/yyyy kk:mm";
	
	/**
	 * Unique identifier for this <code>Event</code> object. Currently unused and it's methods are deprecated.
	 */
	private int id;
	
	private String name;
	protected Calendar start;
	protected Calendar end;
	private boolean staticEvent;
	private boolean repeating;
	private RecurrenceGroup recurrenceGroup;
	
	/**
	 * Event duration in minutes.
	 */
	private int duration;
	
	/**
	 * Constructor for a single <code>Event</code> object.
	 * @param name Name and/or description of the event.
	 * @param start a <code>Calendar</code> object specifying when the event starts
	 * @param end a <code>Calendar</code> object specifying when the event ends
	 * @param stat specifies whether the event is static(true) or dynamic(false)
	 * @param repeating specifies whether the event is a recurring event
	 */
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
	
	/**
	 * Constructs an <code>Event</code> clone from another <code>Event</code>.
	 * @param source a source <code>Event</code> to clone from
	 */
	public Event(Event source) {
		name = source.getName();
		start = (Calendar) source.getStart().clone();
		end = (Calendar) source.getEnd().clone();
		duration = source.getDuration();
		staticEvent = source.isStatic();
		repeating = source.isRepeating();
		recurrenceGroup = source.getRecurrenceGroup();
	}
	
	/**
	 * Returns the name for this <code>Event</code>.
	 * @return the name and/or description for this <code>Event</code>.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Sets the start and end date for this <code>Event</code>
	 * @param start the new start date
	 * @param end the new end date
	 */
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
		return earlier.compareTo(later) > 0;
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
		/*SimpleDateFormat f = new SimpleDateFormat(DATE_FORMAT);
		String start_str = f.format(start.getTime());
		String end_str = f.format(end.getTime());
		String s = isStatic() ? "S" : "D";
		String r = isRepeating() ? "R" : "NR";
		return name+"[("+duration+") "+start_str+" - "+end_str+", "+s+", "+r+"]";*/
		
		SimpleDateFormat formatter = new SimpleDateFormat();
		formatter.applyPattern("yyyy-MM-dd-HH:mm:ss");

		String str = "{\n\"event\" : {\n"
				//+ "\t\"id\" : \"" + this.getId() + "\",\n"
				+ "\t\"title\" : \"" + this.getName() + "\",\n"
				//+ "\t\"static\" : \"" + this.isStatic() + "\",\n"
				//+"\t\"repeating\" : \"" + this.isRepeating() + "\",\n"
				+ "\t\"when\" : {\n"
				+ "\t\t" + "\"start\" : " + "\"" + formatter.format(this.getStart().getTime()) + "\",\n"
				+ "\t\t" + "\"end\" : " + "\"" + formatter.format(this.getEnd().getTime()) + "\"\n"
				+ "\t\t}\n"
				//+ "\"recurrence\" : \"" + this.getRecurrenceGroup().getRecurrence() + "\",\n"
				//+ "\"interval\" : \"" + this.getRecurrenceGroup().getInterval() + "\",\n"
				+ "\t}\n"
				+ "}"
				;
		return str;
	}
	
}
