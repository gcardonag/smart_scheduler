package tests.scheduling;

import java.util.Calendar;

import dynamicEventCollection.DynamicEvent;

import optionStructures.ScheduleOptions;
import scheduling.ParetoEisenhowerScheduler;
import scheduling.dayScheduling.DSShortestTaskFirst;
import scheduling.dayScheduling.DayScheduler;
import tests.TestUtils;
import eventCollection.EventTree;
import eventCollection.RecurrenceGroup;

/**Tests the <code>ShortestTaskFirst</code> time scheduler.
 * @author Anthony
 *
 */
public class TestCase02_ShortestTaskFirst {

	/** Main method.
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TestCase().runTest();
	}

	/**Implementation of this test case.
	 * @author Anthony
	 *
	 */
	protected static class TestCase extends SchedulerTester{

		@Override
		protected boolean checkResults(EventTree results) {
			return results.size() == 16;
		}

		@Override
		protected EventTree makeDynamicEvents() {
			EventTree dynamicEvents = new EventTree(true);
			DynamicEvent de;
			
			de = TestUtils.makeDynamicEvent("DE-1", 
					TestUtils.makeDynamicCalendar(2013, Calendar.JANUARY, 01, true),
					TestUtils.makeDynamicCalendar(2013, Calendar.JANUARY, 02, false), 
					ParetoEisenhowerScheduler.PE_PRIORITY_HIGH, 3, 0);
			TestUtils.makeRecurrenceGroup(de, RecurrenceGroup.DAILY, 1, null);
			dynamicEvents.add(de);
			
			de = TestUtils.makeDynamicEvent("DE-2", 
					TestUtils.makeDynamicCalendar(2013, Calendar.JANUARY, 01, true),
					TestUtils.makeDynamicCalendar(2013, Calendar.JANUARY, 02, false), 
					ParetoEisenhowerScheduler.PE_PRIORITY_HIGH, 2, 0);
			TestUtils.makeRecurrenceGroup(de, RecurrenceGroup.DAILY, 1, null);
			dynamicEvents.add(de);
			
			de = TestUtils.makeDynamicEvent("DE-3", 
					TestUtils.makeDynamicCalendar(2013, Calendar.JANUARY, 01, true),
					TestUtils.makeDynamicCalendar(2013, Calendar.JANUARY, 02, false), 
					ParetoEisenhowerScheduler.PE_PRIORITY_HIGH, 1, 0);
			TestUtils.makeRecurrenceGroup(de, RecurrenceGroup.DAILY, 1, null);
			dynamicEvents.add(de);
			
			de = TestUtils.makeDynamicEvent("DE-4", 
					TestUtils.makeDynamicCalendar(2013, Calendar.JANUARY, 01, true),
					TestUtils.makeDynamicCalendar(2013, Calendar.JANUARY, 02, false), 
					ParetoEisenhowerScheduler.PE_PRIORITY_MED, 2, 0);
			TestUtils.makeRecurrenceGroup(de, RecurrenceGroup.DAILY, 1, null);
			dynamicEvents.add(de);
			
			de = TestUtils.makeDynamicEvent("DE-5", 
					TestUtils.makeDynamicCalendar(2013, Calendar.JANUARY, 01, true),
					TestUtils.makeDynamicCalendar(2013, Calendar.JANUARY, 02, false), 
					ParetoEisenhowerScheduler.PE_PRIORITY_MED, 1, 0);
			TestUtils.makeRecurrenceGroup(de, RecurrenceGroup.DAILY, 1, null);
			dynamicEvents.add(de);
			
			//System.out.println("Dynamic Event Size: " + dynamicEvents.size());
			return dynamicEvents;
		}

		@Override
		protected EventTree makeStaticEvents() {
			
			EventTree se = new EventTree(true);
			
			Calendar c = TestUtils.makeCalendar(2013, Calendar.JANUARY, 01, 11, 00);
			
			for(int i = 0 ; i < 2; i++){
				se.add(TestUtils.makeStaticEvent("SE-1hr@11am", c, 1, 0));
				TestUtils.setHour(c, 14);
				
				se.add(TestUtils.makeStaticEvent("SE-2hr@2pm", c, 2, 0));
				
				TestUtils.setHour(c, 11);
				TestUtils.addDay(c);
				
			}
			
			return se;
		}

		@Override
		protected ScheduleOptions makeOptions() {
			ScheduleOptions options = new ScheduleOptions();
			
			options.addNewForbiddenHour(TestUtils.makeCalendar(00, 00), 9, 30);
			options.addNewForbiddenHour(TestUtils.makeCalendar(21, 00), 2, 59);
			return options;
		}

		@Override
		protected Calendar makeStart() {
			return TestUtils.makeCalendar(2013, Calendar.JANUARY, 01, 00, 00);
		}

		@Override
		protected Calendar makeEnd() {
			return TestUtils.makeCalendar(2013, Calendar.JANUARY, 02, 23, 59);
		}

		@Override
		protected DayScheduler makeTimeScheduler() {
			return new DSShortestTaskFirst();
		}

		
		
	}

}
