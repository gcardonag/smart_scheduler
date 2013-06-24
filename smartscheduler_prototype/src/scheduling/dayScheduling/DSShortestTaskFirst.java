package scheduling.dayScheduling;

import java.util.ArrayList;

import scheduling.DaySchedule.TimeSlot;
import scheduling.ParetoSchedule;
import dynamicEventCollection.DynamicEvent;
import eventCollection.Event;

/**Implementation of the shortest task first, day scheduler. The idea is
 * to take care of those events that require the least time to
 * complete. Implementation only requires the events to be sorted before
 * scheduled and then applied the <code>FirstComeFirstServe</code> scheduling.
 * @author Anthony Llanos Velazquez.
 *
 */
public class DSShortestTaskFirst extends DSFirstComeFirstServe{

	/* (non-Javadoc)
	 * @see timescheduling.TSFirstComeFirstServe#scheduleEventsForSlots
	 * (scheduling.ParetoSchedule, java.util.ArrayList, java.util.ArrayList)
	 */
	@Override
	public ArrayList<Event> scheduleEventsForSlots(ParetoSchedule theDay,
			ArrayList<DynamicEvent> dynamicEvents, ArrayList<TimeSlot> timeSlots) {
		
		sort(dynamicEvents) ;
		return super.scheduleEventsForSlots(theDay, dynamicEvents, timeSlots);
	}
	
	/**Implementation of the sorting algorithm. Uses selection sort.
	 * @param events - the events to sort.
	 * @deprecated Change sorting algorithm to a more efficient one.
	 */
	public void sort(ArrayList<DynamicEvent> events){
		
		ArrayList<DynamicEvent> sorted = new ArrayList<DynamicEvent>();	
		DynamicEvent cur = null ;
		while(!events.isEmpty()){
			DynamicEvent min = events.get(0);
			for(int i = 0; i < events.size(); i++){
				cur = events.get(i);
				if(min.getTime() > cur.getTime()){
					min = cur ;
				}
			}
			sorted.add(min);
			events.remove(min);
		}
		
		for(DynamicEvent de : sorted){
			events.add(de);
		}
		
	}

}
