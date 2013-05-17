/* @deprecated annotations mean that the class or 
 * method may need revision, not that it should not be used.
 */
package scheduling.testing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import optionStructures.ScheduleOptions;
import scheduler.PomodoroCreator;
import scheduling.ParetoEisenhowerScheduler;

import dynamicEventCollection.DynamicEvent;
import dynamicEventCollection.ParetoEisenhowerEvent;

import eventCollection.*;

/**
 * Tester for the dynamic scheduler. Details for this tester are provided
 * in the excel file TestCases
 */
public class TestCase01Pomodoro {

	/**
	 * @param args
	 */
	public static void main(String[] args){

		ArrayList<Event> processed = TestCase01.runTest();
		//Apply Pomodoro to the processed events list
		
		PomodoroCreator pc = new PomodoroCreator(25, 5, 30);
		
		SimpleDateFormat formatter = new SimpleDateFormat();
		
		for(int p=0; p < processed.size(); p++) {
			Iterable<Event> pomodoros = pc.getPomodorosFromEvent(processed.remove(0));
			for(Event e : pomodoros)
				processed.add(e);
		}
		
		System.out.println("\n\nProcessed after applying Pomodoro:") ;
		
		int i = 0;
		boolean dateset = false ;
		
		int count = 0 ;
		int d1 = processed.get(0).getStart().get(Calendar.DAY_OF_YEAR) ;
		
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
		System.out.println("Result Size after Pomodoro: " + processed.size() + " - Time: " + Calendar.getInstance().getTime());
	}
	
	
	

}
