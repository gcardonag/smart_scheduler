package tests;

import java.util.Calendar;
import java.util.GregorianCalendar;

import dynamicEventCollection.DynamicEvent;
import dynamicEventCollection.ParetoEisenhowerEvent;

import eventCollection.Event;
import eventCollection.RecurrenceGroup;

public class TestUtils {

	/**Creates a calendar object with the given parameters. Seconds are defaulted to
	 * zero.
	 * @param year - the year.
	 * @param month - the month (e.g. <code>Calendar.JANUARY</code>).
	 * @param day - the day (e.g. <code>Calendar.DAY_OF_MONTH = 02</code>).
	 * @param hour - the hour (e.g. <code>Calendar.HOUR_OF_DAY = 23</code>).
	 * @param minute - the minute.
	 * @return a <code>Calendar</code> object.
	 */
	public static Calendar makeCalendar(int year, int month, int day, int hour, int minute){
		Calendar calendar = (Calendar) Calendar.getInstance() ;
		calendar.set(Calendar.YEAR, year) ;
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day) ;
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute) ;
		calendar.set(Calendar.SECOND, 0);
		return calendar;
	}
	
	/**Creates a calendar with the given hour and minute.
	 * (Used for <code>ScheduleOptions</code> for now.)
	 * @param hour - the hour (e.g. <code>Calendar.HOUR_OF_DAY = 23</code>) 
	 * @param minute - the minute.
	 * @return a <code>Calendar</code> object.
	 */
	public static Calendar makeCalendar(int hour, int minute){
		Calendar calendar = Calendar.getInstance() ;
		calendar.set(Calendar.HOUR_OF_DAY, hour) ;
		calendar.set(Calendar.MINUTE, minute) ;
		calendar.set(Calendar.SECOND, 0) ;
		return calendar;
	}
	
	/**Creates a static event. Using the given start calendar, the given hours and
	 * minutes are used to create the end.
	 * @param name - the name of the event.
	 * @param start - the start of the event.
	 * @param hours - the hours of the event spans to.
	 * @param minutes - the minutes (additional to hours) the event spans to.
	 * @return an <code>Event</code> object.
	 * 
	 * @deprecated Check for events that span between days.
	 */
	public static Event makeStaticEvent(String name, Calendar start, int hours, int minutes){
		Calendar c2 = (Calendar) start.clone() ;
		c2.add(Calendar.HOUR_OF_DAY, hours) ;
		c2.add(Calendar.MINUTE, minutes);
		return new Event(name, start, c2, true, false) ;
	}
	
	/**Creates a calendar with the specified parameters. Hour, minute are set
	 * according to given boolean value. If start is true, then to start of day
	 * <code>12:00 AM</code>, otherwise to <code>11:59 PM</code>.
	 * @param year - the year.
	 * @param month - the month.
	 * @param day - the day.
	 * @param start - a <code>boolean</code> value to set the hour and minute.
	 * @return a <code>Calendar</code> object.
	 */
	public static Calendar makeDynamicCalendar(int year, int month, int day, boolean start){
		GregorianCalendar newCalendar = new GregorianCalendar();
		
		if(start){
			newCalendar.set(year, month, day, 0, 0, 0) ;
		}
		else
			newCalendar.set(year, month, day,23,59,0) ;
		
		return newCalendar; 
	}
	
	/**Creates a dynamic event of type <code>ParetoEisenhowerEvent</code>
	 * @param name - the name of the event.
	 * @param start - the start date of the event.
	 * @param end - the end date of the event.
	 * @param priority - the priority of the event. 
	 * @param hours - the hours the event requires.
	 * @param minutes - the minutes the event requires.
	 * @return an event of type <code>ParetoEisenhowerEvent</code>
	 */
	public static ParetoEisenhowerEvent makeDynamicEvent(String name, 
			Calendar start, Calendar end, int priority, int hours, int minutes){
		ParetoEisenhowerEvent newEvent = 
				new ParetoEisenhowerEvent( name,  start,  end,  priority,  hours,  minutes);
		return newEvent ;
	}
	
	/**Creates a recurrence group for a given dynamic event.
	 * @param event - the event for which to create the recurrence group.
	 * @param recurrence the recurrence type 
	 * 		(e.g. <code>RecurrenceGroup.DAILY</code>).
	 * @param interval - the interval of the recurrence. An <code>int</code> value.
	 * @param weekdays - a <code>boolean</code> array of length 7 which determines the days of ocurrence.
	 */
	public static void makeRecurrenceGroup(DynamicEvent event, 
			int recurrence, int interval, boolean[] weekdays ){
		
		event.setRecurrenceGroup(new RecurrenceGroup(event, recurrence, 
										interval, event.getEnd(), weekdays)) ;
		
	}
	
	/**Sets the hour of a calendar to the given value.
	 * @param c - the calendar.
	 * @param hour - the hour to set to.
	 */
	public static void setHour(Calendar c, int hour){
		c.set(Calendar.HOUR_OF_DAY, hour) ;
	}
	
	/**Sets the hour and the minute of a calendar to the given values.
	 * @param c - the calendar.
	 * @param hour - the hour to set to.
	 * @param minute - the minute to set to.
	 */
	public static void setHourMinute(Calendar c, int hour, int minute){
		c.set(Calendar.HOUR_OF_DAY, hour) ;
		c.set(Calendar.MINUTE, minute) ;
	}
	
	/**Adds a day by default to a given calendar.
	 * @param c - the calendar.
	 */
	public static void addDay(Calendar c){
		c.add(Calendar.DAY_OF_MONTH, 1);	
	}
}
