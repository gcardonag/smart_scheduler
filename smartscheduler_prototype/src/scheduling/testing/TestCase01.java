/* @deprecated annotations mean that the class or 
 * method may need revision, not that it should not be used.
 */
package scheduling.testing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import optionStructures.ScheduleOptions;
import scheduling.ParetoElsenhowerScheduler;

import dynamicEventCollection.DynamicEvent;
import dynamicEventCollection.ParetoEisenhowerEvent;

import eventCollection.*;

/**
 * Tester for the dynamic scheduler. Details for this tester are provided
 * in the excel file TestCases
 */
public class TestCase01 {

	/**
	 * @param args
	 */
	public static void main(String[] args){
		
		ParetoElsenhowerScheduler pem ;
		
		EventTree staticEvents = makeStaticEvents() ;
		EventQueue dynamicEvents = makeDynamicEvents() ;
		ScheduleOptions options = makeOptions();
		Calendar counterStart = makeStart(); 
		Calendar counterEnd = makeEnd() ;
		
		System.out.println("Static Event Size: " + staticEvents.size()) ;
		System.out.println("Dynamic Event Size: " + dynamicEvents.size()) ;

		
		
		pem = new ParetoElsenhowerScheduler(staticEvents, options, counterStart, counterEnd) ;
		
		ArrayList<Event> processed = pem.scheduleDynamicEvents(dynamicEvents) ;
		System.out.println("\n\nProcessed:") ;
		
		int i = 0;
		boolean dateset = false ;
		
		int count = 0 ;
		int d1 = processed.get(0).getStart().get(Calendar.DAY_OF_YEAR) ;
		
		SimpleDateFormat formatter = new SimpleDateFormat();
		for(Event e: processed){
			
			int d2 = e.getStart().get(Calendar.DAY_OF_YEAR);
			
			if(d2 != d1){
				System.out.println("-- Count: " + count) ;
				System.out.println();
				d1 = d2;
				dateset = false;
				count = 1 ;
			}
			else{
				count++ ;
			}
			
			if(!dateset){
				System.out.println(formatter.format(e.getStart().getTime()));
				dateset = true ;
			}
			
			System.out.println("" + (++i) + " - " + e) ;
		}
		System.out.println("Result Size: " + processed.size() + " - Time: " + Calendar.getInstance().getTime());
	}
	
	
	public static Calendar makeStart(){
		Calendar counterStart = (Calendar) Calendar.getInstance() ;
		counterStart.set(Calendar.MONTH, Calendar.MARCH);
		counterStart.set(Calendar.DAY_OF_MONTH, 4) ;
		counterStart.set(Calendar.HOUR_OF_DAY, 0);
		counterStart.set(Calendar.MINUTE, 0) ;
		counterStart.set(Calendar.SECOND, 0);
		return counterStart;
	}
	
	public static Calendar makeEnd(){
		Calendar counterEnd = new GregorianCalendar() ;
		counterEnd.set(Calendar.DAY_OF_MONTH, 23);
		counterEnd.set(Calendar.HOUR_OF_DAY, 23) ;
		counterEnd.set(Calendar.MINUTE, 59) ;
		counterEnd.set(Calendar.SECOND, 59) ;
		return counterEnd;
	}
	
	public static ScheduleOptions makeOptions(){
		ScheduleOptions options = new ScheduleOptions() ;
		Calendar o1 = Calendar.getInstance() ;
		o1.set(Calendar.HOUR_OF_DAY, 0) ;
		o1.set(Calendar.MINUTE, 0) ;
		o1.set(Calendar.SECOND, 0) ;
		
		Calendar o2 = Calendar.getInstance() ;
		o2.set(Calendar.HOUR_OF_DAY, 21) ;
		o2.set(Calendar.MINUTE, 0) ;
		o2.set(Calendar.SECOND, 0) ;

		options.addNewForbiddenHour(o1, 9, 30) ;
		options.addNewForbiddenHour(o2, 2, 59) ;
		return options;
	}
	
	public static EventTree makeStaticEvents(){
		EventTree staticEvents = new EventTree();
		GregorianCalendar start = new GregorianCalendar();
		start.set(2013, Calendar.MARCH, 04, 0, 0);
		
		staticEvents.add(createHourEvent("Event.", start)) ; 
		
		Calendar s1 = new GregorianCalendar() ;
		s1.setTime(start.getTime()) ;
		s1.set(Calendar.HOUR_OF_DAY, 0) ;
		s1.set(Calendar.MINUTE, 0) ;
		s1.set(Calendar.SECOND, 0) ;
		s1 = (Calendar) s1.clone() ;
		
		for(int i = 0; i < 20; i++){
			Event e = createHourEvent("1hr@0", s1) ;
			staticEvents.add(e) ;
			
			s1.set(Calendar.HOUR_OF_DAY, 10) ;
			e = createHourEvent("1hr@10", s1) ;
			staticEvents.add(e) ;
			
			s1.set(Calendar.HOUR_OF_DAY, 12) ;
			e = createTwoHourEvent("2hr@12", s1) ;
			staticEvents.add(e) ;
		
			s1.set(Calendar.HOUR_OF_DAY, 15) ;
			e = createTwoHourEvent("2hr@15", s1) ;
			staticEvents.add(e) ;
			
			s1.add(Calendar.DAY_OF_MONTH, 1);
			s1.set(Calendar.HOUR_OF_DAY, 0) ;
			s1.set(Calendar.MINUTE, 0) ;
			s1.set(Calendar.SECOND, 0) ;
		}
		
		s1.set(Calendar.DAY_OF_MONTH, 23);
		s1.set(Calendar.HOUR_OF_DAY, 23) ;
		s1.set(Calendar.MINUTE, 59) ;
		s1.set(Calendar.SECOND, 59) ;
		
		return staticEvents;
	}
	/**Creates an hour long event starting from a given date.
	 * @param name - the name of the event.
	 * @param c1 - the starting date of the event.
	 * @return <code>Event</code> - an event object.
	 */
	public static Event createHourEvent(String name, Calendar c1){
		
		Calendar c2 = (Calendar) c1.clone() ;
		c2.add(Calendar.HOUR_OF_DAY, 1) ;
		return new Event(name, c1, c2, true, false) ;
	}
	
	public static Event createTwoHourEvent(String name, Calendar c1){
		
		Calendar c2 = (Calendar) c1.clone() ;
		c2.add(Calendar.HOUR_OF_DAY, 2) ;
		return new Event(name, c1, c2, true, false) ;
	}
	
	
	public static GregorianCalendar makeCalendarFromDate(int year, int month, int day, int hour, int minutes, int seconds){
		GregorianCalendar newCalendar = new GregorianCalendar();
		newCalendar.set(year, month, day, hour, minutes, seconds) ;
		return newCalendar; 
	}
	
	
	public static GregorianCalendar makeDynamicCalendarFromDate(int year, int month, int day, boolean start){
		GregorianCalendar newCalendar = new GregorianCalendar();
		
		if(start){
			newCalendar.set(year, month, day,0,0,0) ;
		}
		else
			newCalendar.set(year, month, day,23,59,0) ;
		
		return newCalendar; 
	}
	
	public static ParetoEisenhowerEvent createDynamicEvent(String name, 
			Calendar start, Calendar end, int priority, int hours, int minutes){
		
		ParetoEisenhowerEvent newEvent = 
				new ParetoEisenhowerEvent( name,  start,  end,  priority,  hours,  minutes);
		return newEvent ;
	}
	
	public static void makeRecurrenceGroupFor(DynamicEvent event, 
			int recurrence, int interval, boolean[] weekdays ){
		
		event.setRecurrenceGroup(new RecurrenceGroup(event, recurrence, 
										interval, event.getEnd(), weekdays)) ;
		
	}
	
	public static EventQueue makeDynamicEvents(){
		EventQueue dynamicEvents = new EventQueue() ;
		ParetoEisenhowerEvent de = createDynamicEvent("DE1", 
				makeDynamicCalendarFromDate(2013,Calendar.MARCH,4, true),
				makeDynamicCalendarFromDate(2013,Calendar.MARCH,10,false),
				ParetoElsenhowerScheduler.PE_PRIORITY_HIGH, 2,0) ;
		makeRecurrenceGroupFor(de, RecurrenceGroup.WEEKLY, 1, 
				new boolean[]{false, true, false, true, false, true,false});
		dynamicEvents.offer(de) ;
		
		de = createDynamicEvent("DE2", 
				makeDynamicCalendarFromDate(2013,Calendar.MARCH,4,true),
				makeDynamicCalendarFromDate(2013,Calendar.MARCH,10,false),
				ParetoElsenhowerScheduler.PE_PRIORITY_MED, 1,0) ;
		makeRecurrenceGroupFor(de, RecurrenceGroup.WEEKLY, 1, 
				new boolean[]{false, true, false, true, false, true,false});
		dynamicEvents.offer(de) ;
		
		de = createDynamicEvent("DE3", 
				makeDynamicCalendarFromDate(2013,Calendar.MARCH,4,true),
				makeDynamicCalendarFromDate(2013,Calendar.MARCH,9,false),
				ParetoElsenhowerScheduler.PE_PRIORITY_HIGH, 4,0) ;
		
		makeRecurrenceGroupFor(de, RecurrenceGroup.WEEKLY, 1, 
				new boolean[]{true, false, true, false, true, false,true});
		dynamicEvents.offer(de) ;
		
		de = createDynamicEvent("DE4", 
				makeDynamicCalendarFromDate(2013,Calendar.MARCH,4,true),
				makeDynamicCalendarFromDate(2013,Calendar.MARCH,28,false),
				ParetoElsenhowerScheduler.PE_PRIORITY_MED, 4,0) ;
		makeRecurrenceGroupFor(de, RecurrenceGroup.WEEKLY, 1, 
				new boolean[]{true, false, true, false, true, false,true});
		dynamicEvents.offer(de) ;
		
		de = createDynamicEvent("DE5", 
				makeDynamicCalendarFromDate(2013,Calendar.MARCH,10,true),
				makeDynamicCalendarFromDate(2013,Calendar.MARCH,12,false),
				ParetoElsenhowerScheduler.PE_PRIORITY_HIGH, 4,0) ;
		makeRecurrenceGroupFor(de, RecurrenceGroup.WEEKLY, 1, 
				new boolean[]{true, false, true, false, true, false,true});
		dynamicEvents.offer(de) ;
		
		 de = createDynamicEvent("DE6", 
				makeDynamicCalendarFromDate(2013,Calendar.MARCH,4, true),
				makeDynamicCalendarFromDate(2013,Calendar.MARCH,10,false),
				ParetoElsenhowerScheduler.PE_PRIORITY_HIGH, 2,0) ;
		makeRecurrenceGroupFor(de, RecurrenceGroup.WEEKLY, 1, 
				new boolean[]{false, true, false, true, false, true,false});
		dynamicEvents.offer(de) ;
		
		 de = createDynamicEvent("DE7", 
					makeDynamicCalendarFromDate(2013,Calendar.MARCH,4, true),
					makeDynamicCalendarFromDate(2013,Calendar.MARCH,28,false),
					ParetoElsenhowerScheduler.PE_PRIORITY_MED, 2,0) ;
			makeRecurrenceGroupFor(de, RecurrenceGroup.WEEKLY, 1, 
					new boolean[]{false, true, false, true, false, true,false});
			dynamicEvents.offer(de) ;
		
		return dynamicEvents;
		
	}
	
}
