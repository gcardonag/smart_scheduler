package scheduling.dayScheduling;

import java.util.ArrayList;

import scheduling.DaySchedule.TimeSlot;
import scheduling.ParetoSchedule;
import dynamicEventCollection.DynamicEvent;
import dynamicEventCollection.ParetoEisenhowerEvent;
import eventCollection.Event;

/**The day scheduler that uses the <code>RoundRobin</code> algorithm
 * as seen in use in operating system scheduling techniques. The idea is
 * to switch between tasks after a specified amount of time or quantum to
 * not starve other tasks when the first tasks are long enough to take up
 * all the available time.
 * 
 * @author Anthony Llanos Velazquez
 *
 */
public class DSRoundRobin extends DSAbstract{
	
	/**
	 * The quantum of events or maximum length of every event.
	 */
	public int QUANTUM; //Quantum in minutes.

	/**Initializes the time scheduler with a given quantum.
	 * @param quantum
	 */
	public DSRoundRobin(int quantum){
		this.QUANTUM = quantum;
	}
	
	/* (non-Javadoc)
	 * @see timescheduling.TimeScheduler#scheduleEventsForSlots(scheduling.ParetoSchedule, java.util.ArrayList, java.util.ArrayList)
	 */
	@Override
	public ArrayList<Event> scheduleEventsForSlots(ParetoSchedule theDay,
			ArrayList<DynamicEvent> dynamicEvents, ArrayList<TimeSlot> timeSlots) {
		
		ArrayList<Event> scheduledEvents = new ArrayList<Event>();
		
		while(timeAvailable(timeSlots) && existUnscheduledEvents(dynamicEvents)){
			for(DynamicEvent de : dynamicEvents){
				if(!de.isScheduledCompletely() && !timeSlots.isEmpty()){
					scheduledEvents.add(setEventTimeForSlots((ParetoEisenhowerEvent)de, timeSlots));
				}
			}
		}
		return scheduledEvents;
	}
	
	/**Schedules events using the quantum in the <code>RoundRobin</code> algorithm.
	 * @param event - the event to schedule.
	 * @param timeSlots - the collection of available time slots.
	 * @return a new <code>ParetoEisenhowerEvent</code> object that represents
	 * the result of scheduling a single event.
	 */
	private ParetoEisenhowerEvent setEventTimeForSlots(ParetoEisenhowerEvent event, ArrayList<TimeSlot> timeSlots){
		
		TimeSlot tr = timeSlots.get(0) ;
		int slotTimeLeft = tr.getTimeLeft() ;
		int eventTimeLeft = event.getTimeLeft() ;
		
		if (slotTimeLeft <= eventTimeLeft) {
			// This day does not have enough time left
			// available for the event.

			if(QUANTUM >= slotTimeLeft){
				event.reduceTime(slotTimeLeft);
				timeSlots.remove(tr);
				return new ParetoEisenhowerEvent(event.getName(), tr.getStart(), 
						tr.getEnd(), event.getPriority(), event.getTime()/60,event.getTime()%60);
			}
			else{
				event.reduceTime(QUANTUM);
				return tr.getEventForTime(QUANTUM, event.getName(), event);
			}
		} 
		else{
			//eventTimeLeft is less than slot.
			if(QUANTUM >= eventTimeLeft){
				event.reduceTime(eventTimeLeft);
				return tr.getEventForTime(eventTimeLeft, event.getName(), event);
			}
			else{
				event.reduceTime(QUANTUM);
				return tr.getEventForTime(QUANTUM, event.getName(), event);
			}
		}

		
		
	}
	
	/**Determines whether there are still available time slots.
	 * @param timeSlots - the time slots used for scheduling.
	 * @return <code>true</code> if time available, <code>false</code> otherwise.
	 */
	public boolean timeAvailable(ArrayList<TimeSlot> timeSlots){
		return !timeSlots.isEmpty();
	}
	
	/**Determines whether there still exist events that are to be scheduled or that
	 * have time left to schedule.
	 * @param dynamicEvents - the collection of dynamic events.
	 * @return <code>true</code> if unscheduled events exist, 
	 * <code>false</code> otherwise.
	 */
	public boolean existUnscheduledEvents(ArrayList<DynamicEvent> dynamicEvents){
		for(DynamicEvent de : dynamicEvents){
			if(!de.isScheduledCompletely()){
				return true ;
			}
		}
		return false;
	}
	
	

}
