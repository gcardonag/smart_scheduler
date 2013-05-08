/* @deprecated annotations mean that the class or 
 * method may need revision, not that it should not be used.
 */
package scheduling;

import java.util.ArrayList;

import java.util.Calendar;
import optionStructures.ScheduleOptions;
import dynamicEventCollection.ParetoEisenhowerEvent;
import eventCollection.EventTree;

/**Class that determines the schedule for a day in accordance with the
 * Pareto Time Management Technique. In order to achieve this the 
 * class stores two collections of time slots available to scheduling. 
 * The first collection represents 80% of the time available for scheduling. 
 * The second collection represents 20% of the time available for scheduling. 
 * The time slots are determined from given static events and schedule options.
 * @author Anthony Llanos Velazquez
 * 
 */
@SuppressWarnings("deprecation")
public class ParetoSchedule extends DaySchedule {

	/** The time slots that represent the greater percentage 
	 * of the schedule division. */
	private ArrayList<TimeSlot> highPrioritySlots;
	
	
	/** The time slots that represent the lower percentage 
	 * of the schedule division. */
	private ArrayList<TimeSlot> mediumPrioritySlots ;
	
	/**Initializes a ParetoSchedule object based on the day and uses given static events
	 * and scheduling options to determine the time slots avalable for scheduling.
	 * @param day
	 * @param staticEvents
	 * @param options
	 */
	public ParetoSchedule(Calendar day, EventTree staticEvents, ScheduleOptions options) {
		super(day);
		
		this.highPrioritySlots = new ArrayList<TimeSlot>();
		this.mediumPrioritySlots = new ArrayList<TimeSlot>();
		super.processStaticEvents(staticEvents);
		super.processStaticEvents(options.getForbiddenHoursForDay(day));
		
		//TODO: Harcoding pareto.
		//TODO: When implementing percent, should note that if given percent
		// is less than 50.0 then it should switch them, throw an exception or change
		// the implementation logic.
		
		this.getSlotsForPercent(100.0);
		
	}
	

	/**Method for determining the available time slots in a day. This is used
	 * for the setup of the class.
	 * @param firstPercent - the first percentage with which a day will be
	 * divided for scheduling.
	 * @deprecated Fix floating-point arithmetic to allow 
	 * variable Pareto scheduling and not just 80/20. 
	 * Should delete the super-class's
	 * timeslots (almost done).
	 */
	private void getSlotsForPercent(double firstPercent){
	
		double total = 0 ;
		for(TimeSlot ts: super.timeSlots){
			total += ts.getTimeLeft() ;
		}
		
		double firstPart = (firstPercent < 1.0) ? total * firstPercent 
												: total * 0.8 ;
		double secondPart = (firstPercent < 1.0) ?  total * (1.0-firstPercent)
												: total * 0.2 ;
		
		
		
		while(firstPart >= 1.0){
			for(int i = 0; i < timeSlots.size(); i++){
				TimeSlot ts = timeSlots.get(i) ;
				if(firstPart >= ts.getTimeLeft()){
					firstPart -= ts.getTimeLeft() ;
					this.highPrioritySlots.add(ts);
					timeSlots.remove(ts);
					i--;
				}
				else{
					Calendar ta1 = ts.getStart() ;
					Calendar ta2 = (Calendar) ta1.clone();
					ta2.add(Calendar.MINUTE, (int)Math.floor(firstPart)) ;
					firstPart = 0;
					TimeSlot newTimeSlot1 = new TimeSlot(ta1,ta2);
					TimeSlot newTimeSlot2 = new TimeSlot(ta2, ts.getEnd());
					this.highPrioritySlots.add(newTimeSlot1);
					timeSlots.set(i, newTimeSlot2);
				}
			}
		}
		
		while(secondPart >= 1.0){
			for(int i = 0; i < timeSlots.size(); i++){
				TimeSlot ts = timeSlots.get(i) ;
				if(secondPart >= ts.getTimeLeft()){
					secondPart -= ts.getTimeLeft() ;
					this.mediumPrioritySlots.add(ts);
					timeSlots.remove(ts);
					i--;
				}
				else{
					Calendar ta1 = ts.getStart() ;
					Calendar ta2 = (Calendar) ta1.clone();
					ta2.add(Calendar.MINUTE, (int)Math.floor(secondPart)) ;
					secondPart = 0;
					TimeSlot newTimeSlot1 = new TimeSlot(ta1,ta2);
					TimeSlot newTimeSlot2 = new TimeSlot(ta2, ts.getEnd());
					this.mediumPrioritySlots.add(newTimeSlot1);
					timeSlots.set(i, newTimeSlot2);
				}
			}
		}
		
	}
	
	/**
	 * Creates an static event that represents the scheduled dynamic event. It assigns
	 * as much time available from the first time slot available and reduces the
	 * event time that was assigned. This is done for this day's high priority events.
	 * @param event - the dynamic event that needs to be scheduled.
	 * @return
	 * 
	 * @deprecated Can attempt to schedule as much time available, and return a list
	 * of created events but will create inflexibility for the class.
	 */
	public ParetoEisenhowerEvent setHighPriorityEventTime(ParetoEisenhowerEvent event){
		return setEventTimeForTimeSlots(event, highPrioritySlots);
	}
	
	/**
	 * Creates an static event that represents the scheduled dynamic event. It assigns
	 * as much time available from the first time slot available and reduces the
	 * event time that was assigned. This is done for this day's high priority events.
	 * @param event - the dynamic event that needs to be scheduled.
	 * @return
	 * 
	 * @deprecated Can attempt to schedule as much time available, and return a list
	 * of created events but will create inflexibility for the class.
	 */
	public ParetoEisenhowerEvent setMediumPriorityEventTime(ParetoEisenhowerEvent event){
		return setEventTimeForTimeSlots(event, mediumPrioritySlots);
	}
	
	/* #Inherited
	 * (non-Javadoc)
	 * @see scheduler.DaySchedule#hasTimeAvailable()
	 */
	public boolean hasTimeAvailable(){
		return (!highPrioritySlots.isEmpty() &&
				!mediumPrioritySlots.isEmpty());
	}
	
	/**Returns whether this day has high priority time available.
	 * @return true if high priority time available, false otherwise.
	 */
	public boolean hasHighPriorityTimeAvailable(){
		return !highPrioritySlots.isEmpty();
	}
	
	/**Returns whether this day has medium priority time available.
	 * @return true if has time available, false otherwise.
	 */
	public boolean hasMediumPrioritySlotsAvailable(){
		return !mediumPrioritySlots.isEmpty();
	}

	/* TODO: Attempt to hide this from other objects (i.e. private).
	 * (non-Javadoc)
	 * @see scheduler.DaySchedule#setEventTimeForTimeSlots(dynamicEventCollection.DynamicEvent, java.util.ArrayList)
	 */
	private ParetoEisenhowerEvent setEventTimeForTimeSlots(ParetoEisenhowerEvent event, ArrayList<TimeSlot> timeSlots){
		
		TimeSlot tr = timeSlots.get(0) ;
		int slotTimeLeft = tr.getTimeLeft() ;
		int eventTimeLeft = event.getTimeLeft() ;
		
		if (slotTimeLeft <= eventTimeLeft) {
			// This day does not have enough time left
			// available for the event.

			event.reduceTime(slotTimeLeft);
			timeSlots.remove(tr);
			return new ParetoEisenhowerEvent(event.getName(), tr.getStart(), 
							tr.getEnd(), event.getPriority(), event.getDuration() / 60, 
							event.getDuration() % 60);
		} 
		else {
			// This day has all the time available for
			// the event.

			event.reduceTime(eventTimeLeft);
		
			ParetoEisenhowerEvent newEvent = tr.getEventForTime(eventTimeLeft,event.getName(),event);
			return newEvent;
		}
		
		
	}

	
	/* TODO: Testing purposes only for now.
	 * (non-Javadoc)
	 * @see scheduler.DaySchedule#toString()
	 */
	public String toString(){
		String s = "{" ;
		s += "\nHigh Priority:\n";
		for(TimeSlot e: this.highPrioritySlots){
			s += "\n" + e ;
		}
		
		s += "\n\nMedium Priority:\n";
		
		for(TimeSlot e: this.mediumPrioritySlots){
			s += "\n" + e ;
		}
		
		s += "\n}" ;
		
		return s ;
	}
}
