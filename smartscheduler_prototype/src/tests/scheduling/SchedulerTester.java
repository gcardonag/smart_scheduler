package tests.scheduling;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import optionStructures.ScheduleOptions;
import scheduling.ParetoEisenhowerScheduler;
import scheduling.dayScheduling.DayScheduler;

import eventCollection.Event;
import eventCollection.EventTree;

/**Base abstract superclass for running tests on the scheduler.
 * The tests running using this class as their superclass only require
 * the implementation of the abstract methods (below). Then the subclass must be
 * instantiated and its <code>runTest()</code> method invoked to run the test.
 * 
 * <h2>Methods to Implement</h2>
 * <ul>
 * <code>
 * <li>checkResults()</li>
 * <li>makeDynamicEvents()</li>
 * <li>makeStaticEvents()</li>
 * <li>makeOptions()</li>
 * <li>makeStart()</li>
 * <li>makeEnd()</li>
 * <li>makeTimeScheduler()</li>
 * </code>
 * </ul>
 * @author Anthony Llanos Velazquez
 *
 */
public abstract class SchedulerTester{
	
	/**Runs the test.
	 * @param outputResults - true if the results should be printed.
	 * @return a <code>boolean</code> value.
	 */
	public boolean runTest(boolean outputResults){
		
		long t1 = System.currentTimeMillis() ;
		EventTree processed = runScheduler();
		long t2 = System.currentTimeMillis() ;
		
		if(outputResults){
			System.out.println("\n\nProcessed:") ;
			printEvents(processed);
		}
		
		boolean result = checkResults(processed);
		System.out.println("\nTest Run Result:");
		System.out.println("--[ "+((result) ? "Successful" : "Failure") + " ]");
		System.out.println("Time: " + (t2-t1) + " ms.") ;
		
		return result;
	}
	
	/**Runs the scheduler. Requires that all the methods of the
	 * <code>SchedulerTester</code> interface be implemented.
	 * @return an <code>EventTree</code> object containing the
	 * scheduled dynamic events.
	 */
	protected EventTree runScheduler(){
		
		EventTree staticEvents = makeStaticEvents() ;
		EventTree dynamicEvents = makeDynamicEvents() ;
		ScheduleOptions options = makeOptions();
		
		Calendar counterStart = makeStart(); 
		Calendar counterEnd = makeEnd() ;
		
		DayScheduler ts = makeTimeScheduler();
		
		ParetoEisenhowerScheduler pem ;
		pem = new ParetoEisenhowerScheduler(staticEvents, options, counterStart, counterEnd, ts) ;
		EventTree processed = pem.scheduleDynamicEvents(dynamicEvents) ;
		
		System.out.println("\n\nProcessed:") ;
		printEvents(processed);
		
		return processed ;
	}
	
	
	/**Outputs the events by day.
	 * @param results - events to output.
	 */
	protected void printEvents(EventTree results){
		int i = 0;
		boolean dateset = false ;
		
		int count = 0 ;
		int d1 = results.getMinimum().getStart().get(Calendar.DAY_OF_YEAR) ;
		
		SimpleDateFormat formatter = new SimpleDateFormat();
		for(Event e: results){
			
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
		System.out.println("Result Size: " + results.size() + " - Date: " + Calendar.getInstance().getTime());
	}
	
	/**Runs the test with no output of the scheduled events.
	 * @return a <code>boolean</code> value of whether the test
	 * was successful.
	 */
	public boolean runTest(){
		return runTest(false);
	}
	
	
	/**Determines whether the results are correct.
	 * @param results - the scheduled events.
	 * @return a <code>boolean</code> value.
	 */
	protected abstract boolean checkResults(EventTree results);
	
	
	/**Creates the dynamic events that will be used in
	 * the test case for scheduling.
	 * @return An <code>EventTree</code> object that
	 * contains that contains the dynamic events.
	 */
	protected abstract EventTree makeDynamicEvents();
	
	/**Creates the static events that will be used
	 * in the test case for scheduling.
	 * @return An <code>EventTree</code> object that
	 * contains that contains the static events.
	 */
	protected abstract EventTree makeStaticEvents();
	
	/**Creates the options that will be used for the
	 * scheduler to configure the scheduling process.
	 * @return A <code>ScheduleOptions</code> object
	 * that contains the settings.
	 */
	protected abstract ScheduleOptions makeOptions();
	
	/**Creates the start date of the scheduler.
	 * @return A <code>Calendar</code> object.
	 */
	protected abstract Calendar makeStart();
	
	
	/**Creates the end date of the scheduler.
	 * @return A <code>Calendar</code> object.
	 */
	protected abstract Calendar makeEnd();
	
	/**Creates the scheduler to be used.
	 * @return A <code>ParetoEisenhowerScheduler</code> object.
	 */
	protected abstract DayScheduler makeTimeScheduler();

	
}
