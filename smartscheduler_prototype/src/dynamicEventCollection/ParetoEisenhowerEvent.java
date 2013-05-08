/* @deprecated annotations mean that the class or 
 * method may need revision, not that it should not be used.
 */
package dynamicEventCollection;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import scheduling.ParetoElsenhowerScheduler;

public class ParetoEisenhowerEvent extends DynamicEvent {

	/**The priority of the task in the Elsenhower Matrix:
	 * <ul>
	 * <li>Priority 2 'Do' : Tasks are of high importance second 
	 * only to static events. 80% time dedication.</li>
	 * <li>Priority 3 'Delegate' : Tasks are of medium importance 
	 * and are dedicated only 20% of the time.</li>
	 * <li>Priority 4 'Drop' : Tasks that are only done on free time.</li>
	 * </ul>
	 * 
	 */
	private int priority ;

	
	/**Creates an instance of the event that will be managed by the
	 * Pareto-Elsenhower Time Management method.
	 * @param name - the name of the event.
	 * @param start - the start date calendar of the event.
	 * @param end - the end date calendar of the event.
	 * @param priority - the priority of the event in the Pareto-Elsenhower hierarchy.
	 */
	public ParetoEisenhowerEvent(String name, Calendar start, Calendar end, int priority, int hours, int minutes){
		super(name, start, end, hours, minutes);
		this.priority = priority ;
	}
	
	/**Returns the event's priority in the Elsenhower Matrix.
	 * @return
	 */
	public int getPriority(){
		return this.priority;
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
		
		String p = "" ;
		switch(priority){
			case ParetoElsenhowerScheduler.PE_PRIORITY_HIGH : p = "High" ; break ;
			case ParetoElsenhowerScheduler.PE_PRIORITY_MED : p = "Medium" ; break ;
			case ParetoElsenhowerScheduler.PE_PRIORITY_LOW : p = "Low" ; break ;
			default: break ;
		}
		
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
		+ " hours : '" + this.scheduleTime / 60 + "' , " 
		+ " minutes : '" + this.scheduleTime % 60 + "' , " 
		+ " priority : '" + p + "' " 
		+ " }" ;
		return str;
		
		
		
		
						
	}

}
