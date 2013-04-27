package io;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

import dynamicEventCollection.DynamicEvent;
import dynamicEventCollection.ParetoElsenhowerEvent;

import scheduling.ParetoElsenhowerScheduler;

import eventCollection.Event;
import eventCollection.EventQueue;
import eventCollection.RecurrenceGroup;

/**Interprets the events that come from the user interface as a
 * string in the form of a collection of object. Uses a parser
 * to store the data into separate objects to later
 * create the respective static and dynamic events.
 * @author Anthony Llanos Velazquez.
 *
 */
public class EventInterpreter {
	
	ArrayList<Event> staticEvents ;
	EventQueue dynamicEvents ;
	String events ;
	
	public EventInterpreter(String events){
		this.events = events ;
		interpretEvents();
	}
	
	public ArrayList<Event> getStaticEvents(){
		return this.staticEvents;
	}
	
	public EventQueue getDynamicEvents(){
		return this.dynamicEvents ;
	}
	
	public void changeEvents(String events){
		this.events = events ;
		this.interpretEvents() ;
	}
	
	/**Given a string in the format
	 * <code>
	 * <div style="padding:10px;">
	 * var newEvent = {
	 * 		<div style="padding:10px;">
	 * 		id : eventId,<br />
	 * 		name : eventName,<br />
	 * 		type : currentEventType,<br />
	 * 		sDate : startDate,<br />
	 * 		eDate : endDate,<br />
	 * 		sTime : startTime,<br />
	 * 		eTime : endTime,<br />
	 * 		recurrence : recType,<br />
	 * 		interval : recInterval,<br />
	 * 		days : recDays,<br />
	 * 		hours : recHours,<br />
	 * 		minutes: recMinutes,<br />
	 * 		priority : eventPriority<br />
	 * 		</div>
	 * };<br />
	 * </div>
	 * <br />
	 * </code>
	 * this algorithm interprets and creates the
	 * collection of events that the string represents.
	 * <br />
	 * 
	 * @param s
	 * @return
	 */
	private void interpretEvents(){
		staticEvents = new ArrayList<Event>();
		dynamicEvents = new EventQueue();
		ArrayList<EventEntry> entries = this.parse(events);
		
		//For every parsed entry.
		for(EventEntry ee: entries){
			
			Calendar start = null ;
			Calendar dstart = null ;
			Calendar end = null ;
			boolean isRecurring ;
	
			int recurrence = -1 ;
			int interval = -1;
			boolean[] days = new boolean[7] ;
			
			//////////////////////////////////////////////////////
			//Begin Switch.
			//Class Type.
			if(ee.getType().equalsIgnoreCase("class")){
				
				start = dstart = ee.getStart();
				end = ee.getEnd();
				isRecurring = ee.getRecurrence() != -1 ;
				
				if(isRecurring){
					recurrence = ee.getRecurrence();
					interval = ee.getInterval() ;
					days = ee.getDays();	
				}
				else{
					recurrence = RecurrenceGroup.WEEKLY;
					interval = 1 ;
					days = parseDays("1111111");
				}
			}
			//Deadline Type
			else if(ee.getType().equalsIgnoreCase("Deadline")){
				
				start = this.parseDate(ee.getEndDate(), "12:00 AM") ;
				dstart = Calendar.getInstance() ;
				end = this.parseDate(ee.getEndDate(), "11:59 PM") ;
				isRecurring = false ;
				
				recurrence = RecurrenceGroup.WEEKLY;
				interval = 1 ;
				days = parseDays("1111111");
				
			}
			//Meeting Type.
			else if(ee.getType().equalsIgnoreCase("Meeting")){
				//TODO: Assumption
				start = this.parseDate(ee.getEndDate(), "12:00 AM") ;
				dstart = Calendar.getInstance() ;
				end = this.parseDate(ee.getEndDate(), "11:59 PM") ;
				isRecurring = false ;
				
				recurrence = RecurrenceGroup.WEEKLY;
				interval = 1 ;
				days = parseDays("1111111");
				
			}
			//Flexible Type.
			else if(ee.getType().equalsIgnoreCase("Flexible")){
				
				start = dstart = ee.getStart();
				end = ee.getEnd() ;
				isRecurring = ee.getRecurrence() != -1 ;
				
				//Dynamic Event.
				recurrence = (isRecurring) ? ee.getRecurrence() : -1 ;
				interval = (isRecurring) ? ee.getInterval() : -1 ;
				days = (isRecurring) ? ee.getDays() : null ;
				
			}
			else{
				System.out.println("------------\nType:" + ee.getType() + ee.getType().equalsIgnoreCase("class"));
				System.out.println("Error in object: \n" + ee) ;
				return ;
			}
			
			//Event Creation.
			Event e = new Event(ee.getName(), start, end, true, isRecurring);
			//TODO: ID.
			e.setId(ee.getId());
			
			if(isRecurring){
				e.setEnd(this.parseDate(ee.getStartDate(), ee.getEndTime()));
				RecurrenceGroup rg = new RecurrenceGroup(e,recurrence,interval,end,days);
				e.setRecurrenceGroup(rg);
			}
			
			ParetoElsenhowerEvent de = 
					new ParetoElsenhowerEvent("D-" + ee.getName(), dstart,end,ee.getPriority(),ee.getHours(),ee.getMinutes()) ;
			//TODO: ID.
			de.setId(ee.getId()) ;
			
			RecurrenceGroup rg = new RecurrenceGroup(de,recurrence, interval, end, days);
			de.setRecurrenceGroup(rg);
			
			staticEvents.add(e);
			dynamicEvents.offer(de) ;
		}
		
	}
	
	
	
	/**Parses the String received from UI into a collection of
	 * EventEntry object that represent the information of
	 * the individual events contained in the string.
	 * @param s
	 * @return
	 * @deprecated Visibility.
	 */
	private ArrayList<EventEntry> parse(String s){
		
		ArrayList<EventEntry> entries = new ArrayList<EventEntry>();
		
		int index = 0 ;
		if(s.charAt(index) != '[') return null ;
		index ++ ;
		
		while(true){
			
			//Opening Container of Entry.
			index = ignoreWhiteSpace(s, index);
			if(s.charAt(index) != '{') return null ;
			
			index++;
			
			EventEntry newEntry = new EventEntry();
			
			while(true){
				String parameterName = "" ;
				String parameterValue = "" ;
				index = ignoreWhiteSpace(s,index);
				
				//Parameter Name
				while(s.charAt(index) != ' ' && s.charAt(index) != ':'){
					parameterName+= s.charAt(index);
					index++ ;
				}
			
				//Divider
				index = ignoreWhiteSpace(s, index);
				if(s.charAt(index++) != ':') return null ;
				index = ignoreWhiteSpace(s, index);
				
				//Parameter Value
				while(s.charAt(index) != ',' && s.charAt(index) != '}'){
					parameterValue+= s.charAt(index);
					index++ ;
				}
				
				index = ignoreWhiteSpace(s, index);
				
				/////////////////////////////////////////////
				//TODO: Testing.
				
				/*parameterName = parameterName.trim();
				if(parameterName.equalsIgnoreCase("days")){
					newEntry.addParameter(parameterName.trim(), "0101010");
				}
				else if(parameterName.equalsIgnoreCase("hours") ){
					newEntry.addParameter(parameterName.trim(), "1");
				}
				else{
					
				}*/
				newEntry.addParameter(parameterName.trim(), parameterValue.trim());
				if(s.charAt(index++) == '}') break ;
				
				//
				/////////////////////////////////////////////
			}
			/////////////
			newEntry.addParameter("minutes", "0");
			entries.add(newEntry);
			
			//Closing Container of Entry or repeat.
			index = ignoreWhiteSpace(s, index);
			if(s.charAt(index) == ']') break ;
			index++ ;
			
		}
		
		return entries ;
	}
	
	/**Ignores the whitespace in a String starting from the
	 * given index until a non-whitespace character is found. 
	 * @param s the string from which to ignore whitespace.
	 * @param index the index from which to start from.
	 * @return the index it stopped at.
	 */
	private int ignoreWhiteSpace(String s, int index){
		while(index < s.length() && s.charAt(index) == ' '){
			index ++ ;
		}
		return index ;
	}
	
	
	///////////////////////////////////////////////////////////////////////////////
	/**Joins a date and a time parameter into a Calendar object
	 * @param date the given date.
	 * @param time the given time.
	 * @return a Calendar object that represents the union of the given
	 * date and time.
	 * @deprecated TODO: Change visibility after testing.
	 */
	public Calendar parseDate(String date, String time){
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		
		
		Date pDate;
		try {
			pDate = formatter.parse(date + " " + time);
		} 
		catch (ParseException e) {
			e.printStackTrace();
			return null ;
		}
		Calendar cal = Calendar.getInstance() ;
		cal.setTime(pDate);
		return cal ;
	}
	
	/**Parses a string that represents the recurrence
	 * into the class variables of RecurrenceGroup.
	 * @param recurrence the string that represents the recurrence.
	 * @return an int that represents the recurrence.
	 */
	private int parseRecurrence(String recurrence){
		if(recurrence.equalsIgnoreCase("weekly"))
			return RecurrenceGroup.WEEKLY ;
		else if(recurrence.equalsIgnoreCase("daily"))
			return RecurrenceGroup.DAILY ;
		else if(recurrence.equalsIgnoreCase("monthly"))
			return RecurrenceGroup.MONTHLY ;
		else
			return -1 ;
	}
	
	/**Parses a String that represents the days
	 * an event will be scheduled in case of
	 * weekly recurrence.
	 * @param days the string that represents the days.
	 * @return a boolean array of values.
	 */
	private boolean[] parseDays(String days){
		boolean[] edays = new boolean[days.length()] ;
		for(int i = 0; i < days.length(); i++){
			edays[i] = (days.charAt(i) == '1') ;
		}
		return edays ;
	}
	
	/**Parses a String that represents the priority
	 * of an event.
	 * @param priority the string that represents
	 * the priority of an event.
	 * @return an int value.
	 */
	private int parsePriority(String priority){
		if(priority.equalsIgnoreCase("high"))
			return ParetoElsenhowerScheduler.PE_PRIORITY_HIGH ;
		else if(priority.equalsIgnoreCase("medium"))
			return ParetoElsenhowerScheduler.PE_PRIORITY_MED ;
		else
			return ParetoElsenhowerScheduler.PE_PRIORITY_LOW ;
	}
	
	public static String jsonToSSFormat(String jsonEvents){
		
		String ssEvents = "" ;
		for(int i = 0; i < jsonEvents.length(); i++){
			if(jsonEvents.charAt(i) != '"'){
				ssEvents += jsonEvents.charAt(i);
			}
			else{
				ssEvents+= " ";
			}
		}
		return ssEvents;
	}
	//////////////////////////////////////////////////////////////
	/**Class that stores Key-Value pairs using the TreeMap structure.
	 * It interprets the values when accessed and returns them in their
	 * usable types.
	 * @author Anthony Llanos Velazquez
	 */
	public class EventEntry{
		
		TreeMap<String,String> parameters ;
		
		String name ;
		String type ;
		Calendar start ;
		Calendar end ;
		boolean isRecurring ;
		
		int recurrence ;
		int interval ;
		boolean[] days ;
		
		int hours ;
		int priority ;
		
		
		public EventEntry(){
			parameters = new TreeMap<String,String>() ;
		}
		
		public void addParameter(String key, String value){
			parameters.put(key, value);
		}
		
		public String getParameter(String key){
			return parameters.get(key);
		}
	
		public String toString(){
			return "\nEventEntry{\n" + parameters.toString()
					+ " \n}\n";
		}
		
		public String getId(){
			return getParameter("id");
		}
		
		public String getName(){
			return getParameter("name");
		}
		
		public String getType(){
			return getParameter("type");
		}
		
		public Calendar getStart(){
			return parseDate(getParameter("sDate"), getParameter("sTime"));
		}
		
		public Calendar getEnd(){
			return parseDate(getParameter("eDate"), getParameter("eTime"));
		}
		
		public String getStartDate(){
			return getParameter("sDate");
		}
		
		public String getStartTime(){
			return getParameter("sTime");
		}
		
		public String getEndDate(){
			return getParameter("eDate");
		}
		
		public String getEndTime(){
			return getParameter("eTime");
		}
		
		public int getRecurrence(){
			return parseRecurrence(getParameter("recurrence"));
		}
		
		public int getInterval(){
			return Integer.parseInt(getParameter("interval"));
		}
		
		public boolean[] getDays(){
			return parseDays(getParameter("days"));
		}
		
		public int getHours(){
			return Integer.parseInt(getParameter("hours"));
		}
		
		public int getMinutes(){
			return Integer.parseInt(getParameter("minutes"));
		}
		
		public int getPriority(){
			return parsePriority(getParameter("priority"));
		}
		
		
		
	}
	
	
	
	
	
	

}
