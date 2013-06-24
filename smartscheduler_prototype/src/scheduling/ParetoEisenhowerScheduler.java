/* @deprecated annotations mean that the class or 
 * method may need revision, not that it should not be used.
 */
package scheduling;

import io.SchedulerServlet;

import java.util.ArrayList;
import java.util.Calendar;

import dynamicEventCollection.DynamicEvent;
import dynamicEventCollection.ParetoEisenhowerEvent;
import eventCollection.Event;
import eventCollection.EventTree;
import eventCollection.EventQueue;
import eventCollection.RecurrenceGroup;
import optionStructures.ScheduleOptions;
import scheduling.dayScheduling.DSAbstract;
import scheduling.dayScheduling.DSFirstComeFirstServe;
import scheduling.dayScheduling.DSShortestTaskFirst;
import scheduling.dayScheduling.DayScheduler;

//For testing/notes only. Will be removed.
@SuppressWarnings("deprecation") 

/**Class that implements a scheduler for dynamic events based on the Pareto approach
 * and the Elsenhower approach. To complete the requirements of both approaches the class
 * uses the ParetoElsenhowerEvent's priority fields and the ParetoElsenhowerSchedule/Day
 * division of time by setting it to 80-20. For high prioriy event 80% of the time
 * of day is reserved, for medium priority events 20% of the time of the day is reserved.
 * Should there be time left from the high priority events, then the medium priority events
 * are scheduled for 80%, then the low priority events. Should there be time left from the 
 * medium priority events then the low priority events will be scheduled then the high 
 * priority events.
 * 
 * @author Anthony Llanos Velazquez
 */
public class ParetoEisenhowerScheduler {
	
	/**(Priority 2 'Do' Events)
	 * The highest category in the Elsenhower hierarchy that needs
	 * scheduling. These tasks receive 80% of the time available for
	 * scheduling.
	 */
	public static final int PE_PRIORITY_HIGH = 0 ;
	
	/**(Priority 3 'Delegate' Events)
	 * The middle category in the Elsenhower hierarchy that needs
	 * scheduling. These tasks are done only in 20% of the time
	 * available.
	 */
	public static final int PE_PRIORITY_MED = 1 ;
	
	/**(Priority 4 'Drop' Events)
	 * The lowest category in the Elsenhower hierarchy that needs
	 * scheduling. These tasks are only done if there is time available after
	 * scheduling higher priority tasks.
	 */
	public static final int PE_PRIORITY_LOW = 2 ;
	
	
	/**
	 * The scheduler used for scheduling the time in each day.
	 */
	DayScheduler ts ;
	
	/** The static events for determining the available time slots
	 *  for each day. */
	private EventTree staticEvents ;
	
	
	/** The options the user has chosen for hours that are
	 *  forbidden for automatic scheduling. */
	private ScheduleOptions options ;
	
	
	/**The start date from which the dynamic scheduling will 
	 * take place. */
	private Calendar counterStart ;
	private Calendar counterEnd ;
	
	/** Holds currently unprocessed events with high priority. */
	private ArrayList<DynamicEvent> unprocessed_P_HIGH = new ArrayList<DynamicEvent>();
	
	/** Holds currently unprocessed events with medium priority. */
	private ArrayList<DynamicEvent> unprocessed_P_MED = new ArrayList<DynamicEvent>();
	
	/** Holds currently unprocessed events with low priority. */
	private ArrayList<DynamicEvent> unprocessed_P_LOW = new ArrayList<DynamicEvent>();
	
	/**Constructor that initializes the scheduler using the given static events and
	 * the schedule options to determine the free time available for each day to be scheduled.
	 * @param staticEvents - the static events already set in the schedule.
	 * @param options - the options the user has set for scheduling.
	 * 
	 * @deprecated Remove the action of having to take out recurrence group events.
	 */
	public ParetoEisenhowerScheduler(EventTree staticEvents, 
										ScheduleOptions options, Calendar start, Calendar end, DayScheduler ts){
		this.staticEvents = staticEvents ;
		this.ts = ts ;
		
		// RG Events
		EventTree newEvents = new EventTree();
		for(Event e: staticEvents){
			if(e.getRecurrenceGroup() != null){
				for(Event ee: e.getRecurrenceGroup()){
					newEvents.add(ee);
				}
			}
		}
		for(Event e: newEvents){
			staticEvents.add(e);
		}
		
		this.options = options ;
		this.counterStart = start ;
		this.counterEnd = end ;
	}
	
	/**Runs the ParetoElsenhower Algorithm on a list of dynamic events.
	 * @param dynamicEvents - the dynamic events to schedule.
	 * @param counterEnd - the end date for which to schedule.
	 * @return an ArrayList of Events that represent the dynamic event scheduling.
	 */
	public EventTree scheduleDynamicEvents(EventTree dynamicEvents){
		
		// Prepare lists for scheduling and results.
		unprocessed_P_HIGH = new ArrayList<DynamicEvent>();
		unprocessed_P_MED = new ArrayList<DynamicEvent>();
		unprocessed_P_LOW = new ArrayList<DynamicEvent>();
		EventTree processedEvents = new EventTree(true);
		
		///////////////////////////////////////////
		//Loop 'till end date.
		while(counterStart.compareTo(counterEnd) <= 0){ 
			
			//Prepare day for scheduling.
			ParetoSchedule currentDay = new ParetoSchedule(counterStart, staticEvents, options);
			
			//Categorization for prioritization occurs here.
			addDynamicEventsInRange(currentDay.getDay(), dynamicEvents) ;
			removeEventsNotInRange(currentDay.getDay()) ;
			
			//Eisenhower scheduling in effect.
			scheduleForHighPriority(currentDay, processedEvents, unprocessed_P_HIGH);
			scheduleForHighPriority(currentDay, processedEvents, unprocessed_P_MED);
			scheduleForHighPriority(currentDay, processedEvents, unprocessed_P_LOW) ;
			scheduleForMediumPriority(currentDay, processedEvents, unprocessed_P_MED);
			scheduleForMediumPriority(currentDay, processedEvents, unprocessed_P_LOW);
			scheduleForMediumPriority(currentDay, processedEvents, unprocessed_P_HIGH);
			
			//Move to next day.
			counterStart.add(Calendar.DAY_OF_MONTH, 1) ;
		}
		return processedEvents ;	
	}
	

	/**Removes dynamic events from the lists of those that are not in range.
	 * @param day - the day to check if the dynamic event is valid on.
	 */
	private void removeEventsNotInRange(Calendar day){
		removeEventsNotInRange(day, unprocessed_P_HIGH);
		removeEventsNotInRange(day, unprocessed_P_MED);
		removeEventsNotInRange(day, unprocessed_P_LOW);
	}
	
	/**Remove events whose schedule ends before the given day. 
	 * This implies that scheduling for the removed day is done.
	 * @param day - the day to consider.
	 * @param unprocessedEvents - the list of unprocessed events
	 * from which to remove from. 
	 * @deprecated Verify and confirm for when events should be allowed to be 
	 * scheduled for unscheduled time.
	 */
	private void removeEventsNotInRange(Calendar day, ArrayList<DynamicEvent> unprocessedEvents){
		for(int i = 0 ; i < unprocessedEvents.size(); i++){
			DynamicEvent de = unprocessedEvents.get(i) ;
			if(!de.containsDay(day)){
				unprocessedEvents.remove(i) ;
				i-- ;
			}
			else{
				if(de.takesPlaceOnDate(day)){
					resetEventTime(day,de) ;
				}
				else{
					de.zeroTime() ;
				}
			}
		}
	}
	
	/**Add the dynamic events from the event collection that fall in the range
	 * of the given day according to their priorities.
	 * @param day - the day for which to check if in range.
	 * @param dynamicEvents - the dynamic events to check if in range.
	 */
	private void addDynamicEventsInRange(Calendar day, EventTree dynamicEvents){	
		while( !dynamicEvents.isEmpty() && ((ParetoEisenhowerEvent)dynamicEvents.getMinimum()).containsDay(day) ){
			ParetoEisenhowerEvent event = (ParetoEisenhowerEvent) dynamicEvents.getMinimum();
			dynamicEvents.remove(event);
			
			//if(!event.containsDay(day)) continue ;
			//dynamicEvents.remove(i);
			
			if(event.getPriority() == PE_PRIORITY_HIGH){	
				unprocessed_P_HIGH.add(event) ;
			}
			else if(event.getPriority() == PE_PRIORITY_MED){
				unprocessed_P_MED.add(event) ;
			}
			else{
				unprocessed_P_LOW.add(event) ;
			}
			event.zeroTime();
		}
	}
	
	/** Schedules the given events for the high priority category of a given 
	 * Pareto-Elsenhower day schedule.
	 * @param currentDay - the day in which the events will be scheduled.
	 * @param processedEvents - the list of events that have been succesfully scheduled. This is for storing results.
	 * @param unprocessedEvents - the dynamic events which haven't yet been processed.
	 */
	private void scheduleForHighPriority(ParetoSchedule currentDay, EventTree processedEvents, 
														ArrayList<DynamicEvent> unprocessedEvents){
		if(currentDay.hasHighPriorityTimeAvailable()){
			
			ArrayList<Event> pe = this.ts.scheduleEventsForSlots(currentDay, 
													unprocessedEvents,  
													currentDay.getHighPrioritySlots());
			for(Event e: pe){
				processedEvents.add(e);
			}
		}
	}
	
	/** Schedules the given events for the medium priority category of a given 
	 * Pareto-Elsenhower day schedule.
	 * @param currentDay - the day in which the events will be scheduled.
	 * @param processedEvents - the list of events that have been succesfully scheduled.
	 * This is for storing results
	 * @param unprocessedEvents - the dynamic events which haven't yet been processed.
	 */
	
	private void scheduleForMediumPriority(ParetoSchedule currentDay, EventTree processedEvents,
														ArrayList<DynamicEvent> unprocessedEvents) {
		
		if(currentDay.hasMediumPrioritySlotsAvailable()){
			
			ArrayList<Event> pe = this.ts.scheduleEventsForSlots(currentDay, 
														unprocessedEvents, 
														currentDay.getMediumPrioritySlots());
			for(Event e: pe){
				processedEvents.add(e);
			}
		}
		
	}
	
	/**Resets the event time available for a day depending on time started. If the
	 * scheduling is for weekly events and the day for which the time will be restarted occurs on 
	 * the week in which the event starts, the method uses the dynamic event's method to 
	 * determine the first day of the week in which the event starts. If they do not, then the
	 * first day of the week in which the event reccurs is used to determine if it should be reset.
	 * @param day - the dynamic event for which to reset the time.
	 * @param de - the day the in which the event is being scheduled.
	 */
	private void resetEventTime(Calendar day, DynamicEvent de){
		RecurrenceGroup rg = de.getRecurrenceGroup() ;
		if(rg.getRecurrence() == RecurrenceGroup.WEEKLY){
			int result = de.firstDayOfWeekWithRespectToDate(day) ;
			if(day.get(Calendar.DAY_OF_WEEK) == result){
				de.resetTime() ;
			}
			else{
				de.resetUnscheduledTime();
			}
		}
		else{
			de.resetTime() ;
		}
		
	}
	
	
	
}
