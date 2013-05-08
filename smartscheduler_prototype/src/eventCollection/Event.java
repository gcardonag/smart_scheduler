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
	private String id;
	
	private String name;
	protected Calendar start;
	protected Calendar end;
	private boolean staticEvent;
	private boolean repeating;
	protected RecurrenceGroup recurrenceGroup;
	
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
		return later.getStart().before(earlier.getEnd());
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
	
	
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * 
	 * Êvar newEvent = {
 Ê Ê Ê Ê Ê Êid : eventId,
 Ê Ê Ê Ê Ê Êname : eventName,
 Ê Ê Ê Ê Ê Êtype : currentEventType,
 Ê Ê Ê Ê Ê ÊsDate : startDate,
 Ê Ê Ê Ê Ê ÊeDate : endDate,
 Ê Ê Ê Ê Ê ÊsTime : startTime,
 Ê Ê Ê Ê Ê ÊeTime : endTime,
 Ê Ê Ê Ê Ê Êrecurrence : recType,
 Ê Ê Ê Ê Ê Êinterval : recInterval,
 Ê Ê Ê Ê Ê Êdays : recDays, Ê
 Ê Ê Ê Ê Ê Êhours : recHours,
	    	minutes: recMinutes,
 Ê Ê Ê Ê Ê Êpriority : eventPriority
 Ê Ê Ê Ê};
	 * 
	 */
	public String toString() {
		
		String str = "SmartScheduler.Event{"
				//+ " 'ID': " + id  
				+ ", 'Name' : " + name
				+ ", 'CalendarRange' : [ "+ new SimpleDateFormat().format(start.getTime())
					+ " - " + new SimpleDateFormat().format(end.getTime()) + " ]"
				//+ ", 'Static' : "+isStatic()
				//+ ", 'Repeating' : " + isRepeating()
				+ "}"
				;
		
		return str;
	}
	
	public String toJSON(){
		/*
		 * var newEvent = { id : eventId, Ê Ê Ê Ê Ê 
		 * 		name : eventName, Ê Ê Ê Ê Ê
		 * 		type : currentEventType, Ê Ê Ê Ê Ê Ê
		 * 		sDate : startDate, Ê Ê Ê Ê Ê
		 * Ê	eDate : endDate, Ê Ê Ê Ê Ê Ê
		 * 		sTime : startTime, Ê Ê Ê Ê Ê 
		 * 		eTime : endTime, Ê Ê Ê Ê Ê Ê
		 * 		recurrence : recType,
		 * 		interval : recInterval, Ê Ê Ê Ê Ê Ê
		 * 		days : recDays,
		 * 		hours : recHours, 
		 * 		minutes: recMinutes,
		 * 		priority : eventPriority Ê
		 * };
		 * 
		 * String str = "" 
		+ "{ " 
		+ " \"id\" : \"" + this.getId() + "\" , " 
		+ " \"name\" : \"" + this.getName() + "\" , "
		+ " \"type\" : \"" + "notfilled" + "\" , " 
		+ " \"sDate\" : \"" + formatterDate.format(this.getStart().getTime()) + "\" , " 
		+ " \"eDate\" : \"" + formatterDate.format(this.getEnd().getTime()) + "\" , " 
		+ " \"sTime\" : \"" + formatterTime.format(this.getStart().getTime()) + "\" , " 
		+ " \"eTime\" : \"" + formatterTime.format(this.getEnd().getTime()) + "\" , " 
		+ " \"recurrence\" : \"" + "none" + "\" , " 
		+ " \"interval\" : \"" + "notfilled" + "\" , " 
		+ " \"days\" : \"" + "notfilled" + "\" , " 
		+ " \"hours\" : \"" + "notfilled" + "\" , " 
		+ " \"minutes\" : \"" + "notfilled" + "\" , " 
		+ " \"priority\" : \"" + "notfilled" + "\" " 
		+ " }" ;
		 */
		
		SimpleDateFormat formatterDate = new SimpleDateFormat();
		formatterDate.applyPattern("yyyy-MM-dd");
		
		SimpleDateFormat formatterTime = new SimpleDateFormat();
		formatterTime.applyPattern("HH:mm:ss");

		String str = "" 
		+ "{ " 
		+ " id : '" + this.getId() + "' , " 
		+ " name : '" + this.getName() + "' , "
		+ " type : '" + "none" + "' , " 
		+ " sDate : '" + formatterDate.format(this.getStart().getTime()) + "' , " 
		+ " eDate : '" + formatterDate.format(this.getEnd().getTime()) + "' , " 
		+ " sTime : '" + formatterTime.format(this.getStart().getTime()) + "' , " 
		+ " eTime : '" + formatterTime.format(this.getEnd().getTime()) + "' , " 
		+ " recurrence : '" + ((recurrenceGroup!=null) ? recurrenceGroup.getRecurrence() : "none") + "' , " 
		+ " interval : '" + ((recurrenceGroup != null) ? recurrenceGroup.getInterval() : "none") + "' , " 
		+ " days : '" + ((recurrenceGroup != null) ? recurrenceGroup.toStringDays() : "none") + "' , " 
		+ " hours : '" + "static" + "' , " 
		+ " minutes : '" + "static" + "' , " 
		+ " priority : '" + "static" + "' " 
		+ " }" ;
		return str;					
	}
	
}
