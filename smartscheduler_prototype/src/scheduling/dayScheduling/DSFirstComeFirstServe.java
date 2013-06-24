package scheduling.dayScheduling;

import java.util.ArrayList;

import scheduling.DaySchedule.TimeSlot;
import scheduling.ParetoSchedule;
import dynamicEventCollection.DynamicEvent;
import dynamicEventCollection.ParetoEisenhowerEvent;
import eventCollection.Event;

/**Implementation of the first come, first serve day scheduler.
 * The algorithm it uses simply takes events as they come and assigns
 * as much time available from the time slots to an event before moving
 * to a new event.
 * 
 * @author Anthony Llanos Velazquez
 *
 */
public class DSFirstComeFirstServe extends DSAbstract {

	
	/* (non-Javadoc)
	 * @see timescheduling.TimeScheduler#scheduleEventsForSlots(scheduling.ParetoSchedule, java.util.ArrayList, java.util.ArrayList)
	 */
	@Override
	public ArrayList<Event> scheduleEventsForSlots(ParetoSchedule theDay, ArrayList<DynamicEvent> dynamicEvents, ArrayList<TimeSlot> timeSlots) {
		
		ArrayList<Event> processedEvents = new ArrayList<Event>();
		for (int i = 0; i < dynamicEvents.size() && !timeSlots.isEmpty(); i++) {	
			ParetoEisenhowerEvent currentDynamicEvent = (ParetoEisenhowerEvent) dynamicEvents.get(i);
			
			//FCFS Scheduling.
			while (currentDynamicEvent.takesPlaceOnDate(theDay.getDay()) 
					&& currentDynamicEvent.hasTimeLeft() 
					&& !timeSlots.isEmpty()) {
				Event newEvent = setEventTimeForSlots(currentDynamicEvent, timeSlots);
				processedEvents.add(newEvent);
			}
		}
		return processedEvents;

	}

	/**Sets either the whole time of an event if the time slot has more time than that of the
	 * event, otherwise the time of the timeslot to an event and removes the slot.
	 * @param event - the event to schedule.
	 * @param timeSlots - the available time slots.
	 * @return an <code>Event</code> object that represents the result of scheduling that event.
	 */
	private Event setEventTimeForSlots(ParetoEisenhowerEvent event, ArrayList<TimeSlot> timeSlots){
			
		TimeSlot tr = timeSlots.get(0) ;
		int slotTimeLeft = tr.getTimeLeft() ;
		int eventTimeLeft = event.getTimeLeft() ;
		
		if (slotTimeLeft <= eventTimeLeft) {
			// This day does not have enough time left
			// available for the event.
			event.reduceTime(slotTimeLeft);
			timeSlots.remove(tr);
			return new ParetoEisenhowerEvent(event.getName(), tr.getStart(), 
							tr.getEnd(), event.getPriority(), event.getTime()/60,event.getTime()%60);
		} 
		else {
			// This day has all the time available for
			// the event.
			event.reduceTime(eventTimeLeft);
			ParetoEisenhowerEvent newEvent = tr.getEventForTime(eventTimeLeft,event.getName(),event);
			return newEvent;
		}
	}
}
