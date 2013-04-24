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
 * Servlet WONT WORK IN CURRENT PROJECT SETUP.
 * Only here for backup purposes.
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
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		
		System.out.println("EventsS:" + request.getParameter("eventArrayList"));
		String events = EventInterpreter.jsonToSSFormat(request.getParameter("eventArrayList"));
		System.out.println("Events:" + events);
		EventInterpreter interpreter = new EventInterpreter(events);
		ArrayList<Event> staticEvents = interpreter.getStaticEvents();
		
		EventQueue dynamicEvents = interpreter.getDynamicEvents();
		
		for(Event e: staticEvents){
			if(!this.staticEvents.conflictsWith(e)){
				this.staticEvents.add(e);
			}
		}
		
		System.out.println("SE: " + this.staticEvents.size() + " - DE: " + this.dynamicEvents.size()) ;
		GregorianCalendar start = new GregorianCalendar();
		start.set(2013, Calendar.MARCH, 04, 0, 0);
		
		GregorianCalendar end = new GregorianCalendar();
		end.set(2013, Calendar.MAY, 31, 23, 59);
		
		ScheduleOptions options  = makeScheduleOptions();
		
		ParetoElsenhowerScheduler pes = new ParetoElsenhowerScheduler(this.staticEvents,options,start,end);
		ArrayList<Event> scheduledEvents = pes.scheduleDynamicEvents(dynamicEvents);
		
		System.out.println("SS: "+ scheduledEvents.size()) ;
		
		String json = "{";
		Iterator<Event> s_it = this.staticEvents.iterator() ;
		
		while(s_it.hasNext()){
			Event e = s_it.next() ;
			json += "<li>" + e.toJSON() ;
			if(s_it.hasNext()){
				json += ",</li>\n" ;
			}
		}
		
		Iterator<Event> d_it = scheduledEvents.iterator() ;
		System.out.println("HAS???? - " + d_it.hasNext()) ;
		while(d_it.hasNext()){
			Event e = d_it.next() ;
			json += "</li>,\n" ;
			json += "<li>" + e.toJSON() ;
			
		}
		
		json += "}";
		
		request.setAttribute("attr1", json) ;
		
		RequestDispatcher rd = request.getRequestDispatcher("test.jsp");
		rd.forward(request, response);
		
		//response.sendRedirect("menu1.html");
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}
	
	public ScheduleOptions makeScheduleOptions(){
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

	
}
