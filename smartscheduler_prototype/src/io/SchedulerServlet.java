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

import scheduling.ParetoEisenhowerScheduler;

import dynamicEventCollection.DynamicEvent;
import dynamicEventCollection.ParetoEisenhowerEvent;

import eventCollection.*;

/**
 * Servlet implementation class Redirector_1
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
		System.out.println("=========================================");
		//Static and Dynamic Events
		ArrayList<Event> staticEvents = interpreter.getStaticEvents();
		printEvents(staticEvents);
		EventQueue dynamicEvents = interpreter.getDynamicEvents();
		printDynamicEvents(dynamicEvents);
		
		//Errors/Conflicts
		ArrayList<Event> conflictingEvents = processNewStaticEvents(staticEvents);
		
		//Setup...
		GregorianCalendar start = getSchedulerStartDate();
		GregorianCalendar end = getSchedulerEndDate();
		ScheduleOptions options  = getScheduleOptions();
		
		System.out.println("==========================================") ;
		//Scheduling...
		ParetoEisenhowerScheduler pes = new ParetoEisenhowerScheduler(this.staticEvents,options,start,end);
		ArrayList<Event> scheduledEvents = pes.scheduleDynamicEvents(dynamicEvents);
		
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
	
	private ArrayList<Event> processNewStaticEvents(ArrayList<Event> staticEvents) {
		ArrayList<Event> conflictingEvents = new ArrayList<Event>();
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
	
	

	private String eventsToJSON(EventTree staticEvents, ArrayList<Event> scheduledEvents) {
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
		ArrayList<Event> recurrentEvents = new ArrayList<Event>();
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
	
	public static void printEvents(ArrayList<Event> events){
		System.out.println("IO.EI.events(): ") ;
		for(Object e: events){
			System.out.println(e);
		}
	}
	
	public static void printDynamicEvents(EventQueue dynamicEvents) {
		// TODO Auto-generated method stub
		System.out.println("IO.EI.dynamicEvents(): ") ;
		for(Event e: dynamicEvents){
			System.out.println((DynamicEvent)e);
			System.out.println((((ParetoEisenhowerEvent)e).getPriority() == ParetoEisenhowerScheduler.PE_PRIORITY_MED)?"MEDIUM!":"ELSE!");
			
		}
	}



	
	
}
