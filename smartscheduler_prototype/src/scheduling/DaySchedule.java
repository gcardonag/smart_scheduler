/* @deprecated annotations mean that the class or 
 * method may need revision, not that it should not be used.
 */
package scheduling;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import dynamicEventCollection.DynamicEvent;
import dynamicEventCollection.ParetoEisenhowerEvent;
import eventCollection.Event;

/**Represents a day's schedule along with the time-slots
 * available in it.
 * @author Anthony Llanos
 * @deprecated Abstracted due to non-use of this specific class 
 * in scheduler. Will provide a more structured approach.
 */
public abstract class DaySchedule{
	
	private Calendar day ;
	protected ArrayList<TimeSlot> timeSlots ;
	
	/**Constructor setting up the time available for
	 * this day schedule based on a given calendar. 
	 * @param day - the calendar that sets up the day of
	 * month of this day schedule.
	 */
	public DaySchedule(Calendar day){
		this.day = day ;
		timeSlots = new ArrayList<TimeSlot>();
		
		int year = day.get(Calendar.YEAR);
		int month = day.get(Calendar.MONTH) ;
		int monthDay = day.get(Calendar.DAY_OF_MONTH) ;
		
		Calendar start =  new GregorianCalendar(year, month, monthDay, 0,0,0) ;
		Calendar end = new GregorianCalendar(year, month, monthDay, 23,59,00) ;
		
		timeSlots.add(new TimeSlot(start,end)) ;
	}
	
	/**Removes already occupied time slots from this day by
	 * examining a given list of already scheduled events.
	 * @param events - the events already scheduled.
	 */
	public void processStaticEvents(Iterable<Event> events){
		for (Event e : events) {
			ArrayList<TimeSlot> newTimeSlots = new ArrayList<TimeSlot>();
			for (TimeSlot t : timeSlots) {
				
				if(e.conflictsWith(t)){
					
					Calendar eStart = e.getStart();
					Calendar eEnd = e.getEnd();
					Calendar tStart = t.getStart();
					Calendar tEnd = t.getEnd();
					 
					if(eStart.compareTo(tStart) <= 0) {
						if(eEnd.compareTo(tEnd) >= 0) {
							//Remove.
						} 
						else{
							tStart = cloneCalendar(eEnd);
							TimeSlot t1 = new TimeSlot(tStart, tEnd);
							newTimeSlots.add(t1) ;
						}
					} 
					else{
						if(eEnd.compareTo(tEnd) >= 0) {
							tEnd = cloneCalendar(eStart);
							TimeSlot t1 = new TimeSlot(tStart, tEnd);
							newTimeSlots.add(t1) ;
						} 
						else{
							newTimeSlots.remove(t);
							TimeSlot t1 = new TimeSlot(tStart, eStart);
							TimeSlot t2 = new TimeSlot(eEnd, tEnd);
							
							if(t1.getTimeLeft() > 0)
								newTimeSlots.add(t1);
							if(t2.getTimeLeft() > 0)
								newTimeSlots.add(t2);
							
						}
					}
				} 
				else{
					newTimeSlots.add(t);
				}
			}
			timeSlots = newTimeSlots ;
		}
	}
	
	
	
	
	/**Returns whether this day schedule still as time available.
	 * @return a boolean value.
	 */
	public boolean hasTimeAvailable(){
		return !timeSlots.isEmpty() ;
	}
	

	/**Returns a copy of this day's Calendar object.
	 * @return a clone of the Calendar object for
	 * this day.
	 */
	public Calendar getDay(){
		this.day.set(Calendar.HOUR_OF_DAY, 0) ;
		this.day.set(Calendar.MINUTE, 0) ;
		this.day.set(Calendar.SECOND, 0) ;
		
		return ((Calendar) this.day.clone()) ;
		
	}
	
	/**Utility function to clone a calendar.
	 * @param c - the calendar to clone.
	 * @return a copy of the given calendar.
	 */
	private Calendar cloneCalendar(Calendar c){
		return (Calendar) c.clone();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String s = "SmartScheduler.DaySchedule{\n" ;
		s+= "'Date' : '" ;
		s+= day.get(Calendar.YEAR) + "/" ;
		s+= day.get(Calendar.MONTH) + "/" ;
		s+= day.get(Calendar.DAY_OF_MONTH) ;
		s+= "',\n" ;
		s+= "'TimeSlots' : \n" ;
		for(TimeSlot tr: timeSlots){
			s+= "\t" + tr + "\n" ;
		}
		s+= "}" ;
		return s ;
	}
	
	////////////////////////////////////////////////////////////
	// Time Slot Class
	////////////////////////////////////////////////////////////
	
	/**TODO: Define outside.
	 * Class that defines a time slot and provides methods for 
	 * quantification of time between two calendar instances.
	 * @deprecated Subclassing of Event may not be needed. Need to
	 * verify use in implementation.
	 */
	public class TimeSlot extends Event{
		
		/**Constructor that initializes a new time slot that last
		 * from the given start and end calendar times.
		 * @param start - the start time of the time slot.
		 * @param end - the end time of the time slot.
		 * @deprecated Should verify that start < end.
		 */
		public TimeSlot(Calendar start, Calendar end){
			super("", cloneCalendar(start), cloneCalendar(end), false, false) ;
		}
		
		/**The number of minutes available for scheduling in
		 * this time slot.
		 * @return the number of minutes available.
		 */
		public int getTimeLeft(){
			int startHour = start.get(Calendar.HOUR_OF_DAY) ;
			int startMinutes = start.get(Calendar.MINUTE) ;
			int endHour = end.get(Calendar.HOUR_OF_DAY) ;
			int endMinutes = end.get(Calendar.MINUTE) ;
			
			startHour *= 60 ;
			endHour *= 60 ;
			
			return (endHour + endMinutes) - (startHour + startMinutes);
		}
		
		/**Returns an event that is scheduled for the
		 * amount of time specified or for the maximum
		 * time available in this slot.
		 * @param timeLength - the amount of time specified.
		 * @return a new Event that is scheduled for the
		 * amount of time specified.
		 * @deprecated Could auto-reduce the time available instead
		 * of using a new time slot object to represent the result
		 * of the reduction but seems less feasible (maybe) when 
		 * managing time slot instances in collections. Also, does
		 * not verify length of time.
		 * Can: exception thrown if given event is longer.
		 */
		public ParetoEisenhowerEvent getEventForTime(int timeLength,String name, ParetoEisenhowerEvent pee){
			
			Calendar start = (Calendar) this.start.clone() ;
			this.start.add(Calendar.MINUTE, timeLength) ;
			Calendar end = (Calendar) this.start.clone() ;
			
			return new ParetoEisenhowerEvent(name, start, end, pee.getPriority(), pee.getTime()/60, pee.getDuration()%60) ;
			
		}
		
		/* (non-Javadoc)
		 * @see eventCollection.Event#toString()
		 */
		public String toString(){
			String s = "" ;
			s += "TimeSlot{" + start.getTime() + " - " + end.getTime() + "}" ;
			return s ;
		}
	}
	
}
