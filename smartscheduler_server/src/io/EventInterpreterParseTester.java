package io;

import io.EventInterpreter.EventEntry;

import java.util.ArrayList;
import java.util.Calendar;

import eventCollection.Event;

/**Tester for Interpreter <code>EventInterpreter</code>.
 * @author Anthony Llanos Velazquez
 *
 */
public class EventInterpreterParseTester {

public static void main(String[] args){
		
		//A quickie tester.

		String s = "{ { " + 
           " name  : SE1, " +
           " type  : Class, " +
           " sDate : 10/10/2013, " +
           " eDate : 12/12/2013, " +
           " sTime : 3:00 PM, " +
           " eTime : 7:00 PM, " +
           " recurrence : weekly, " +
           " interval : 1," + 
           " days : 1111111, " + 
           " hours : 2, " + 
           " priority : MEDIUM " +
           "}, { " + 
           " name  : SE2, " +
           " type  : Meeting, " +
           " sDate : 10/10/2013, " +
           " eDate : 12/12/2013, " +
           " sTime : 9:00 AM, " +
           " eTime : 1:00 PM, " +
           " recurrence : weekly, " +
           " interval : 1," + 
           " days : 1111111, " + 
           " hours : 2, " + 
           " priority : HIGH " +
           "} }";
		
		
		//Tester for the method that interprets the string received from UI.
		System.out.println("---Parser Results:") ;
		testParser(s);
		
		//Tester for the method that joins a date and a time parameter into
		//a Calendar object.
		System.out.println("\n\n---Date Parser Results:") ;
		testDateParser();
		
		//Tester for the method that interprets the the string received
		//from UI and creates the respective static and dynamic events.
		System.out.println("\n\n---Event Interpreter Results:") ;
		testEventInterpreter(s);
		
		
		
		
		
	}

	/**Tester for the method that interprets the string received from UI.
	 * @param s the string to check.
	 */
	public static void testParser(String s){
		EventInterpreter interpreter = new EventInterpreter();
		ArrayList<EventEntry> results = interpreter.parse(s);
		System.out.println(results);
		
	}
	
	/**Tester for the method that joins a date and a time parameter into
	 * a Calendar object.
	 */
	public static void testDateParser(){
		EventInterpreter interpreter = new EventInterpreter();
		Calendar result = interpreter.parseDate("10/02/2013", "10:30 PM") ;
		System.out.println(result.getTime()) ;
	}
	
	/**Tester for the method that interprets the the string received
	 * from UI and creates the respective static and dynamic events.
	 * @param s the string to check.
	 */
	public static void testEventInterpreter(String s){
		EventInterpreter interpreter = new EventInterpreter();
		ArrayList<Event> result = interpreter.interpretEvents(s);
		System.out.println(result);
	}
}
