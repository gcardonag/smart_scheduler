package io;
import java.io.IOException;
import io.EventInterpreter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import optionStructures.ScheduleOptions;

import scheduler.PomodoroCreator;
import scheduling.ParetoEisenhowerScheduler;

import dynamicEventCollection.DynamicEvent;
import dynamicEventCollection.ParetoEisenhowerEvent;

import eventCollection.*;

/**
 * Servlet implementation class SchedulerServlet
 * @version 0.8
 */
@WebServlet("/SchedulerServlet")
public class SchedulerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    EventTree staticEvents ;
    ArrayList<DynamicEvent> dynamicEvents ;
    public SchedulerServlet() {
        super();
       
        staticEvents = new EventTree();
        dynamicEvents = new ArrayList<DynamicEvent>();
        //TODO: Load From DB.
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		response.setContentType("text/html");
		this.staticEvents = new EventTree();
		this.dynamicEvents = new ArrayList<DynamicEvent>();
		
		//Interpret.
		System.out.println(request.getParameter("eventArrayList"));
		String events = EventInterpreter.jsonToSSFormat(request.getParameter("eventArrayList"));
		System.out.println("IO: " + events) ;
		EventInterpreter interpreter = new EventInterpreter(events);
		
		//Static and Dynamic Events
		EventTree staticEvents = interpreter.getStaticEvents();
		printEvents(staticEvents);
		EventTree dynamicEvents = interpreter.getDynamicEvents();
		
		//Errors/Conflicts
		EventTree conflictingEvents = processNewStaticEvents(staticEvents);
		
		System.out.println("Conflicting event size: " + conflictingEvents.size());
		
		//Setup...
		GregorianCalendar start = getSchedulerStartDate();
		GregorianCalendar end = getSchedulerEndDate();
		ScheduleOptions options  = getScheduleOptions();
		
		System.out.println("==========================================") ;
		
		println("StaticEvents:\n-----------------------------------------");
		println(this.staticEvents.toString());
		
		//Scheduling...
		ParetoEisenhowerScheduler pes = new ParetoEisenhowerScheduler(this.staticEvents,options,start,end);
		EventTree scheduledEvents = pes.scheduleDynamicEvents(dynamicEvents);
		
		println("\n\nScheduledEvents");
		printEvents(scheduledEvents);
		
		//Apply Pomodoro.
		//PomodoroCreator pc = new PomodoroCreator();
		//dynamicEvents = (EventTree) pc.implementPomodoroToList(dynamicEvents);
		//printDynamicEvents(scheduledEvents);
		
		//Translation
		String json = eventsToJSON(this.staticEvents, scheduledEvents);
		
		//Binding
		request.setAttribute("processedEvents", json) ;
		System.out.println(json);
		
		//Dispatcher
		RequestDispatcher rd = request.getRequestDispatcher("test.jsp");
		rd.forward(request, response);	
	}

	

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}
	
	/***********************************************/
	
	/**
	 * @param staticEvents
	 * @return
	 */
	private EventTree processNewStaticEvents(EventTree staticEvents) {
		EventTree conflictingEvents = new EventTree();
		for(Event e: staticEvents){
			if(!this.staticEvents.conflictsWith(e)){
				System.out.println(this.staticEvents.add(e));
			}
			else{
				conflictingEvents.add(e);
			}
		}
		
		return conflictingEvents ;
	}

	public GregorianCalendar getSchedulerStartDate(){
		GregorianCalendar start = new GregorianCalendar();
		start.set(2013, Calendar.MAY, 1, 0, 0);
		return start ;
	}
	
	private GregorianCalendar getSchedulerEndDate() {
		GregorianCalendar end = new GregorianCalendar();
		end.set(2013, Calendar.MAY, 31, 23, 59);
		return end;
	}
	
	public ScheduleOptions getScheduleOptions(){
		ScheduleOptions options = new ScheduleOptions() ;
		Calendar o1 = Calendar.getInstance() ;
		o1.set(Calendar.HOUR_OF_DAY, 0) ;
		o1.set(Calendar.MINUTE, 0) ;
		o1.set(Calendar.SECOND, 0) ;
		
		Calendar o2 = Calendar.getInstance() ;
		o2.set(Calendar.HOUR_OF_DAY, 23) ;
		o2.set(Calendar.MINUTE, 0) ;
		o2.set(Calendar.SECOND, 0) ;

		options.addNewForbiddenHour(o1, 7, 00) ;
		options.addNewForbiddenHour(o2, 0, 59) ;
		return options ;
	}
	
	

	private String eventsToJSON(EventTree staticEvents, EventTree scheduledEvents) {
		addRecurrentEvents(staticEvents) ;
		String json = "[";
		Iterator<Event> s_it = staticEvents.iterator() ;
		
		while(s_it.hasNext()){
			Event e = s_it.next() ;
			json += e.toJSON() ;
			if(s_it.hasNext()){
				json += " , " ;
			}
		}
		
		Iterator<Event> d_it = scheduledEvents.iterator() ;
		while(d_it.hasNext()){
			Event e = d_it.next() ;
			json += " , " ;
			json += e.toJSON() ;
			
		}
		
		json += "]";
		return json;
	}
	
	private void addRecurrentEvents(EventTree eventList){
		EventTree recurrentEvents = new EventTree();
		for(Event e: eventList){
			if(e.getRecurrenceGroup() != null){
				for(Event e2 : e.getRecurrenceGroup().getEventsInRecurrenceGroup()){
					recurrentEvents.add(e2);
				}
			}
		}
		for(Event e : recurrentEvents){
			eventList.add(e);
		}
		
	}
	
	public static void printEvents(EventTree events){
		System.out.println("IO.EI.events(): ") ;
		for(Object e: events){
			System.out.println(e);
		}
	}
	
	public static void printDynamicEvents(EventTree dynamicEvents) {
		// TODO Auto-generated method stub
		System.out.println("IO.EI.dynamicEvents(): ") ;
		for(Event e: dynamicEvents){
			System.out.println((DynamicEvent)e);
			
		}
	}

	public static void println(String s){
		System.out.println(s);
	}


	
	
}
