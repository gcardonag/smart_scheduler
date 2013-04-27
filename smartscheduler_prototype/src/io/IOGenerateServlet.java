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

import scheduling.ParetoElsenhowerScheduler;

import dynamicEventCollection.DynamicEvent;

import eventCollection.*;

/**
 * Servlet implementation class Redirector_1
 */
@WebServlet("/IOGenerateServlet")
public class IOGenerateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    EventTree staticEvents ;
    ArrayList<DynamicEvent> dynamicEvents ;
    public IOGenerateServlet() {
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
		
		//Interpret.
		System.out.println(request.getParameter("eventArrayList"));
		String events = EventInterpreter.jsonToSSFormat(request.getParameter("eventArrayList"));
		EventInterpreter interpreter = new EventInterpreter(events);
		
		//Static and Dynamic Events
		ArrayList<Event> staticEvents = interpreter.getStaticEvents();
		EventQueue dynamicEvents = interpreter.getDynamicEvents();
		
		//Errors/Conflicts
		ArrayList<Event> conflictingEvents = processNewStaticEvents(staticEvents);
		
		//Setup...
		GregorianCalendar start = getSchedulerStartDate();
		GregorianCalendar end = getSchedulerEndDate();
		ScheduleOptions options  = getScheduleOptions();
		
		//Scheduling...
		ParetoElsenhowerScheduler pes = new ParetoElsenhowerScheduler(this.staticEvents,options,start,end);
		ArrayList<Event> scheduledEvents = pes.scheduleDynamicEvents(dynamicEvents);
		
		//Translation
		String json = eventsToJSON(this.staticEvents, scheduledEvents);
		
		//Binding
		request.setAttribute("attr1", json) ;
		
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
		start.set(2013, Calendar.MARCH, 04, 0, 0);
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
		o2.set(Calendar.HOUR_OF_DAY, 21) ;
		o2.set(Calendar.MINUTE, 0) ;
		o2.set(Calendar.SECOND, 0) ;

		options.addNewForbiddenHour(o1, 9, 30) ;
		options.addNewForbiddenHour(o2, 2, 59) ;
		return options ;
	}
	
	

	private String eventsToJSON(EventTree staticEvents2,
			ArrayList<Event> scheduledEvents) {
		String json = "[";
		Iterator<Event> s_it = this.staticEvents.iterator() ;
		
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
	
	


	
	
}
