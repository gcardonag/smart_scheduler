package scheduling.dayScheduling;

import java.util.ArrayList;

import scheduling.ParetoSchedule;
import scheduling.DaySchedule.TimeSlot;
import dynamicEventCollection.DynamicEvent;
import eventCollection.Event;

/**Interface declaration of a day scheduler. Used primarily 
 * for scheduling events during a day only.
 * @author Anthony Llanos Velazquez
 *
 */
public interface DayScheduler {
	
	/**Schedules the given events in a given day according to
	 * a time scheduling algorithm.
	 * @param theDay - the day in which to schedule.
	 * @param dynamicEvents - the dynamic events to schedule.
	 * @param timeSlots - the available time slots of the day.
	 * @return a collection of scheduled events.
	 * @deprecated Pass the EventTree for storage of results?
	 */
	public ArrayList<Event> scheduleEventsForSlots(ParetoSchedule theDay, 
									ArrayList<DynamicEvent> dynamicEvents, 
									ArrayList<TimeSlot> timeSlots);

	
}
