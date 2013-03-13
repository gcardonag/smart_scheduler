/* @deprecated annotations mean that the class or 
 * method may need revision, not that it should not be used.
 */
package optionStructures;

import java.util.ArrayList;
import java.util.Calendar;

import eventCollection.Event;

/**Holds the scheduling options for the user such as
 * forbidden hours.
 * 
 * @author Anthony Llanos Velazquez
 * @deprecated Implement to hold forbidden hours for different days.
 * 
 */
public class ScheduleOptions {

	private ArrayList<Event> forbiddenHours ;
	
	/**
	 * Instantiates a scheduling option object for
	 * use with scheduling algorithms.
	 */
	public ScheduleOptions(){
		forbiddenHours = new ArrayList<Event>() ;
	}
	
	/**Adds a new forbidden hour to the scheduling options.
	 * @param start The start date.
	 * @param hours the number of hours after the start date.
	 * @param minutes the number of hours after the end date.
	 * @deprecated Implement verification of case when start 
	 * + hours + minutes > 11:59pm IF needed.
	 */
	public void addNewForbiddenHour(Calendar start, int hours, int minutes){
		Calendar end = (Calendar) start.clone() ;
		
		//Just in case.
		hours = (int) (hours + Math.floor(minutes/60)) ;
		minutes = minutes % 60 ;
		
		end.add(Calendar.HOUR_OF_DAY, hours) ;
		end.add(Calendar.MINUTE, minutes) ;
		addNewForbiddenHour(start, end);
	}
	
	/**Adds a new forbidden hour to the scheduling options.
	 * @param start the start date.
	 * @param end the end date.
	 */
	public void addNewForbiddenHour(Calendar start, Calendar end){
		forbiddenHours.add(new Event("", (Calendar) start.clone(),
										(Calendar) end.clone(), false,false)) ;
	}
	
	/**Returns the events that are analogous to the forbidden hours.
	 * @return an ArrayList of events.
	 */
	public ArrayList<Event> getForbiddenHoursForDay(Calendar currentDay){
		
		for(int i = 0; i < forbiddenHours.size() ; i++){
			Event e = forbiddenHours.get(i) ;
			e.getStart().set(Calendar.YEAR, currentDay.get(Calendar.YEAR)) ;
			e.getStart().set(Calendar.MONTH, currentDay.get(Calendar.MONTH)) ;
			e.getStart().set(Calendar.DAY_OF_MONTH, currentDay.get(Calendar.DAY_OF_MONTH)) ;
			e.getEnd().set(Calendar.YEAR, currentDay.get(Calendar.YEAR)) ;
			e.getEnd().set(Calendar.MONTH, currentDay.get(Calendar.MONTH)) ;
			e.getEnd().set(Calendar.DAY_OF_MONTH, currentDay.get(Calendar.DAY_OF_MONTH)) ;
		}
		return forbiddenHours ;
	}
}

