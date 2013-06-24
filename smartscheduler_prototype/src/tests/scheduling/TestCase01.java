/* @deprecated annotations mean that the class or 
 * method may need revision, not that it should not be used.
 */
package tests.scheduling;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import optionStructures.ScheduleOptions;
import scheduling.ParetoEisenhowerScheduler;
import scheduling.dayScheduling.DSFirstComeFirstServe;
import scheduling.dayScheduling.DayScheduler;
import tests.TestUtils;

import dynamicEventCollection.DynamicEvent;
import dynamicEventCollection.ParetoEisenhowerEvent;

import eventCollection.*;

/**
 * Tester for the dynamic scheduler. Details for this tester are provided
 * in the excel file TestCases.
 */
public class TestCase01{

	/**Test Main Method.
	 * @param args
	 */
	public static void main(String[] args){
		new TestCase().runTest(true);
	}
	
	/**Test Case 01 implementation.
	 * @author Anthony
	 *
	 */
	protected static class TestCase extends SchedulerTester{
		
		public boolean checkResults(EventTree results){
			return results.size() == 35 ;
		}
		
		/* (non-Javadoc)
		 * @see tests.scheduling.SchedulerTester#makeStart()
		 */
		public Calendar makeStart(){
			return TestUtils.makeCalendar(2013, Calendar.MARCH, 4, 0, 0);
		}
		
		/* (non-Javadoc)
		 * @see tests.scheduling.SchedulerTester#makeEnd()
		 */
		public Calendar makeEnd(){
			return TestUtils.makeCalendar(2013, Calendar.MARCH, 23, 23, 59);
		}
		
		/* (non-Javadoc)
		 * @see tests.scheduling.SchedulerTester#makeOptions()
		 */
		public ScheduleOptions makeOptions(){
			ScheduleOptions options = new ScheduleOptions() ;
			Calendar o1 = TestUtils.makeCalendar(0, 0);
			Calendar o2 = TestUtils.makeCalendar(21, 0);

			options.addNewForbiddenHour(o1, 9, 30) ;
			options.addNewForbiddenHour(o2, 2, 59) ;
			return options;
		}
		
		/* (non-Javadoc)
		 * @see tests.scheduling.SchedulerTester#makeStaticEvents()
		 */
		public EventTree makeStaticEvents(){
			EventTree staticEvents = new EventTree();
			Calendar start = TestUtils.makeCalendar(2013, Calendar.MARCH, 04, 00, 00);
			
			staticEvents.add(makeHourEvent("Event.", start)) ; 
			
			Calendar s1 = new GregorianCalendar() ;
			s1.setTime(start.getTime()) ;

			for(int i = 0; i < 20; i++){
				staticEvents.add(makeHourEvent("1hr@0", s1)) ;
				
				TestUtils.setHour(s1,10);
				staticEvents.add(makeHourEvent("1hr@10", s1)) ;
				
				TestUtils.setHour(s1,12);
				staticEvents.add(makeTwoHourEvent("2hr@12", s1)) ;
			
				TestUtils.setHour(s1,15);
				staticEvents.add(makeTwoHourEvent("2hr@15", s1)) ;
				
				TestUtils.addDay(s1);
				TestUtils.setHourMinute(s1,0,0);
			}
			//
			s1.set(Calendar.DAY_OF_MONTH, 23);
			s1.set(Calendar.HOUR_OF_DAY, 23) ;
			s1.set(Calendar.MINUTE, 59) ;
			s1.set(Calendar.SECOND, 59) ;
			
			return staticEvents;
		}
		
		/* (non-Javadoc)
		 * @see tests.scheduling.SchedulerTester#makeDynamicEvents()
		 */
		public EventTree makeDynamicEvents(){
			EventTree dynamicEvents = new EventTree(true) ;
			ParetoEisenhowerEvent de;
			
			//DE1 - From March 4 to March 10 on Mon, Wed, Fri every week. 
			//Requires 2 hrs, priority HIGH.
			de = TestUtils.makeDynamicEvent("DE1", 
					TestUtils.makeDynamicCalendar(2013,Calendar.MARCH,4, true),
					TestUtils.makeDynamicCalendar(2013,Calendar.MARCH,10,false),
					ParetoEisenhowerScheduler.PE_PRIORITY_HIGH, 2,0) ;
			TestUtils.makeRecurrenceGroup(de, RecurrenceGroup.WEEKLY, 1, 
					new boolean[]{false, true, false, true, false, true,false});
			dynamicEvents.add(de) ;
			
			//DE2 - From March 4 to March 10 on Mon Wed Fri every week. 
			//Requires 1 hrs, priority MED.
			de = TestUtils.makeDynamicEvent("DE2", 
					TestUtils.makeDynamicCalendar(2013,Calendar.MARCH,4,true),
					TestUtils.makeDynamicCalendar(2013,Calendar.MARCH,10,false),
					ParetoEisenhowerScheduler.PE_PRIORITY_MED, 1,0) ;
			TestUtils.makeRecurrenceGroup(de, RecurrenceGroup.WEEKLY, 1, 
					new boolean[]{false, true, false, true, false, true,false});
			dynamicEvents.add(de) ;
			
			//DE3 - From March 4 to March 9 on Sun, Tue, Thu, Sat every week. 
			//Requires 4 hrs, priority HIGH.
			de = TestUtils.makeDynamicEvent("DE3", 
					TestUtils.makeDynamicCalendar(2013,Calendar.MARCH,4,true),
					TestUtils.makeDynamicCalendar(2013,Calendar.MARCH,9,false),
					ParetoEisenhowerScheduler.PE_PRIORITY_HIGH, 4,0) ;
			
			TestUtils.makeRecurrenceGroup(de, RecurrenceGroup.WEEKLY, 1, 
					new boolean[]{true, false, true, false, true, false,true});
			dynamicEvents.add(de) ;
			
			//DE4 - From March 4 to March 28 on Sun, Tue, Thu, Sat every week. 
			//Requires 4 hrs, priority MED.
			de = TestUtils.makeDynamicEvent("DE4", 
					TestUtils.makeDynamicCalendar(2013,Calendar.MARCH,4,true),
					TestUtils.makeDynamicCalendar(2013,Calendar.MARCH,28,false),
					ParetoEisenhowerScheduler.PE_PRIORITY_MED, 4,0) ;
			TestUtils.makeRecurrenceGroup(de, RecurrenceGroup.WEEKLY, 1, 
					new boolean[]{true, false, true, false, true, false,true});
			dynamicEvents.add(de) ;
			
			//DE5 - From March 10 to March 12 on Sun, Tue, Thu, Sat every week. 
			//Requires 4 hrs, priority HIGH.
			de = TestUtils.makeDynamicEvent("DE5", 
					TestUtils.makeDynamicCalendar(2013,Calendar.MARCH,10,true),
					TestUtils.makeDynamicCalendar(2013,Calendar.MARCH,12,false),
					ParetoEisenhowerScheduler.PE_PRIORITY_HIGH, 4,0) ;
			TestUtils.makeRecurrenceGroup(de, RecurrenceGroup.WEEKLY, 1, 
					new boolean[]{true, false, true, false, true, false,true});
			dynamicEvents.add(de) ;
			
			//DE6 - From March 4 to March 10 on Mon, Wed, Fri every week. 
			//Requires 2 hrs, priority HIGH.
			de = TestUtils.makeDynamicEvent("DE6", 
					TestUtils.makeDynamicCalendar(2013,Calendar.MARCH,4, true),
					TestUtils.makeDynamicCalendar(2013,Calendar.MARCH,10,false),
					ParetoEisenhowerScheduler.PE_PRIORITY_HIGH, 2,0) ;
			TestUtils.makeRecurrenceGroup(de, RecurrenceGroup.WEEKLY, 1, 
					new boolean[]{false, true, false, true, false, true,false});
			dynamicEvents.add(de) ;
			
			//DE7 - From March 4 to March 28 on Mon, Wed, Fri every week. 
			//Requires 2 hrs, priority MED.
			de = TestUtils.makeDynamicEvent("DE7", 
					 TestUtils.makeDynamicCalendar(2013,Calendar.MARCH,4, true),
					 TestUtils.makeDynamicCalendar(2013,Calendar.MARCH,28,false),
						ParetoEisenhowerScheduler.PE_PRIORITY_MED, 2,0) ;
			TestUtils.makeRecurrenceGroup(de, RecurrenceGroup.WEEKLY, 1, 
						new boolean[]{false, true, false, true, false, true,false});
				dynamicEvents.add(de) ;
			
			return dynamicEvents;
			
		}
		
		@Override
		public DayScheduler makeTimeScheduler() {
			return new DSFirstComeFirstServe();
		}
		
		//////////////////////////
		//Helper Methods.
		//////////////////////////
		
		/**Creates an hour long event starting from a given date.
		 * @param name - the name of the event.
		 * @param c1 - the starting date of the event.
		 * @return <code>Event</code> - an event object.
		 */
		public Event makeHourEvent(String name, Calendar c1){
			
			Calendar c2 = (Calendar) c1.clone() ;
			c2.add(Calendar.HOUR_OF_DAY, 1) ;
			return new Event(name, c1, c2, true, false) ;
		}
		
		public Event makeTwoHourEvent(String name, Calendar c1){
			
			Calendar c2 = (Calendar) c1.clone() ;
			c2.add(Calendar.HOUR_OF_DAY, 2) ;
			return new Event(name, c1, c2, true, false) ;
		}

		
	}
	
	
	
}
