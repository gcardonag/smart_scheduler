package io;

import io.EventInterpreter.EventEntry;

import java.util.ArrayList;
import java.util.Calendar;

import dynamicEventCollection.DynamicEvent;

import eventCollection.Event;
import eventCollection.EventQueue;
import eventCollection.EventTree;

/**Tester for Interpreter <code>EventInterpreter</code>.
 * @author Anthony Llanos Velazquez
 *
 */
public class EventInterpreterParseTester {

public static void main(String[] args){
		
		//A quickie tester.

		String s = "[ { " + 
		   " id  : SE1, " +
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
           " minutes : 30, " + 
           " priority : MEDIUM " +
           "}, { " +
           " id  : SE1, " +
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
           " minutes : 30, " + 
           " priority : HIGH " +
           "} ]";
		
		//Tester for the method that interprets the the string received
		//from UI and creates the respective static and dynamic events.
		System.out.println("\n\n---Event Interpreter Results:") ;
		testEventInterpreter(s);
		
		
	}
	
	/**Tester for the method that interprets the the string received
	 * from UI and creates the respective static and dynamic events.
	 * @param s the string to check.
	 */
	public static void testEventInterpreter(String s){
		EventInterpreter interpreter = new EventInterpreter(s);
		EventTree staticEvents = interpreter.getStaticEvents() ;
		EventQueue dynamicEvents = interpreter.getDynamicEvents() ;
		System.out.println("\n------\nStatic Events");
		System.out.println(staticEvents);
		System.out.println("\n------\nDynamic Events");
		System.out.println(dynamicEvents);
	}
}
