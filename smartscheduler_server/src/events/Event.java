package events;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import practiceclasses.CalendarExample;
import practiceclasses.CalendarLogic;
import practiceclasses.DateTime;

/**
 * 
 * @author Server Team
 *
 */


public class Event {
	private String eName,name;
	private TimeInterval tInterval;
	private int priority;
	private Date date,start,end;
	private LinkedList<Event> recurrenceGroup;
	private Event next, prev,now;
	private boolean validSTime;
	private boolean validETime;
	private boolean staticEvent;
	private boolean recurringEvent;
	

	public Event(String name, Date start, Date end)
	{
		this.name = name;
		this.start = start;
		this.end = end;
		staticEvent = true;
		recurringEvent = false;
		recurrenceGroup = null;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Date getStart()
	{
		return start;
	}
	
	public Date getEnd()
	{
		return end;
	}
	
	public boolean isStatic()
	{
		return staticEvent;
	}
	
	public boolean isRecurring()
	{
		return recurringEvent;
	}
	
	public LinkedList<Event> getRecurrenceGroup()
	{
		return recurrenceGroup;
	}

	public Event(String name, int sT,int eT, int priority,String date) throws ParseException{
		this.eName=name;
		this.tInterval=new TimeInterval(sT,eT);
		this.priority=priority;
		this.date= DateFormat.getDateInstance().parse(date);
		
	}
	public Event(String e,int p,Date date){
		this.eName=e;
		this.priority=p;
		this.date=date;
		this.tInterval = new TimeInterval(0, 0);
	}
	public Event(String e,TimeInterval time){
		try{
		if(time.getEndTime()< time.getStartTime()){
				throw new Exception("Invalid Time");
		}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		this.eName=e;
		this.tInterval=time;
		
	}
	public Event(String e,TimeInterval time,Date date){
		try{
		if(time.getEndTime()< time.getStartTime()){
				throw new Exception("Invalid Time");
		}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		this.eName=e;
		this.tInterval=time;
		this.date=date;
	}
	public Event(String e,TimeInterval time,Event n,Event p){
		try{
			if(time.getEndTime()< time.getStartTime()){
					throw new Exception("Invalid Time");
			}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
		this.eName=e;
		this.tInterval=time;
		this.next=n;
		this.prev=p;
	}
	public Event(String e,Date date,TimeInterval time,Event n,Event p){
		try{
			if(time.getEndTime()< time.getStartTime()){
					throw new Exception("Invalid Time");
			}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
		this.eName=e;
		this.tInterval=time;
		this.next=n;
		this.prev=p;
		this.date=date;
	}
	public Event(String e,Date date,TimeInterval time,Event n,Event p,int p1){
		try{
			if(time.getEndTime()< time.getStartTime()){
					throw new Exception("Invalid Time");
			}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
		this.eName=e;
		this.tInterval=time;
		this.next=n;
		this.prev=p;
		this.date=date;
		this.priority=p1;
	}
	public Event(String e,TimeInterval time,int p){
		try{
			if(time.getEndTime()< time.getStartTime()){
					throw new Exception("Invalid Time");
			}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
		this.eName=e;
		this.tInterval=time;
		this.priority=p;
	}
	public Event(String e,Date date,TimeInterval time,int p){
		try{
			if(time.getEndTime()< time.getStartTime()){
					throw new Exception("Invalid Time");
			}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
		this.eName=e;
		this.date=date;
		this.tInterval=time;
		this.priority=p;
	}
	public Event(String e,TimeInterval time,Event n,Event p,int p1){
		try{
			if(time.getEndTime()< time.getStartTime()){
					throw new Exception("Invalid Time");
			}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
		this.eName=e;
		this.tInterval=time;
		this.next=n;
		this.prev=p;
		this.priority=p1;
	}
	/** INSERT EXPLANATION */
	public Event(int sT, int eT){
		try{
			if(eT < sT){
					throw new Exception("Invalid Time");
			}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
		this.eName=null;
		this.tInterval = new TimeInterval(sT, eT);
	}
	public Event(int sT, int eT,int p){
		try{
			if(eT< sT){
					throw new Exception("Invalid Time");
			}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		this.eName=null;
		this.priority=p;
		this.tInterval = new TimeInterval(sT, eT);
	}
	/** INSERT EXPLANATION */
	public Event(String e, int sT, int eT) {
		try{
			if(eT< sT){
					throw new Exception("Invalid Time");
			}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		this.eName = e;
		this.tInterval = new TimeInterval(sT, eT);

	}

	public Event(String e, int sT, int eT,int p) {
		try{
			if(eT< sT){
					throw new Exception("Invalid Time");
			}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		this.eName = e;
		this.tInterval = new TimeInterval(sT, eT);
		this.priority=p;
	}
	
	/** INSERT EXPLANATION */
	public Event(String e, int sT, int eT, Date day) {
		try{
			if(eT< sT){
					throw new Exception("Invalid Time");
			}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		this.eName = e;
		this.date=day;
		this.tInterval = new TimeInterval(sT, eT);
	}
	public Event(String e, int sT, int eT, Date day,int p) {
		try{
			if(eT< sT){
					throw new Exception("Invalid Time");
			}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		this.eName = e;
		this.date=day;
		this.priority=p;
		this.tInterval = new TimeInterval(sT, eT);
	}
	/** Creates an event node with a given name, its start and end time, and the previous and next event nodes. */
	public Event(String e, int sT, int eT, Event n, Event p){
		try{
			if(eT< sT){
					throw new Exception("Invalid Time");
			}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		this.eName = e;
		this.tInterval = new TimeInterval(sT, eT);
		this.next = n;
		this.prev = p;
	}
	public Event(String e, int sT, int eT, Event n, Event p1,int p){
		try{
			if(eT< sT){
					throw new Exception("Invalid Time");
			}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		this.eName = e;
		this.tInterval = new TimeInterval(sT, eT);
		this.next = n;
		this.prev = p1;
		this.priority=p;
	}
	/** Creates an event node with a given name, and its previous and next nodes, but without start or end times. */
	public Event(String e, Event n, Event p){
		this.eName = e;
		this.tInterval = new TimeInterval(0, 0);
		this.next = n;
		this.prev = p;
	}
	public Event(String e, Event n, Event p1,int p){
		this.eName = e;
		this.tInterval = new TimeInterval(0, 0);
		this.next = n;
		this.prev = p1;
		this.priority=p;
	}
	
	
/*-------------------------------------------------*
 *      Event node accessor and mutator methods    *
 *-------------------------------------------------*/
	public void setName(String e){
		this.eName = e;
	}
	
	public String getName(){
		return eName;
	}
	
	public Calendar geteDate() {
		return eDate;
	}
	
	public void seteDate(Calendar eDate) {
		this.eDate = eDate;
	}
	
	public void setNext(Event newNext){
		this.next = newNext;
	}
	
	public Event getNext(){
		return next;
	}
	
	public void setPrev(Event newPrev) {
		this.prev = newPrev;
	}
	
	public Event getPrev() {
		return prev;
	}
	
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}

	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void removeLinks() { 
		prev = next = null; 
	}
	
/*-------------------------------------------------*
 * Event TimeInterval accessor and mutator methods *
 *-------------------------------------------------*/
	public void setStartTime(int sT){
		this.tInterval.setStartTime(sT);
	
	}
	public int getStartTime(){
		return this.tInterval.getStartTime();
	}
	
	public void setEndTime(int eT){
		this.tInterval.setEndTime(eT);
	
	}
	public int getEndTime(){
		return this.tInterval.getEndTime();
	}
	
	public TimeInterval getInterval(Event e){
		return tInterval;
	}

	public boolean isvalidSTime(int time){
		if(time > 0 && time < 1399){
			return this.validSTime=true;
		}
		else
			return validSTime=false;
	}
	
	public boolean isvalidETime(int time){
		if(time > 0 && time < 1399){
			return this.validETime=true;
		}
		else
			return validETime=false;
	}
	
	public int returnHour(int time){
		return time/60;
	}
	
	public int returnMinutes(int time){
		return time%60;
	}
	
	
	public String durationEvent(){
		if(this.tInterval.getEndTime()-this.tInterval.getStartTime() <= 0)
			return "Invalid Time";
		else{
			int hour = returnHour(this.tInterval.getEndTime()-this.tInterval.getStartTime()) ;
			int min = returnMinutes(this.tInterval.getEndTime()-this.tInterval.getStartTime());
			return "" + hour + " hour and " + "" + min + " minutes.";}
	}
	
	public boolean checkBreak(Event e1,Event e2){
		if(e1.getEndTime() >= e2.getStartTime()){
			return false;
		}
	else
		return true;
	}
	
	public String toStringTimeInterval(){
		return "" + returnHour(getStartTime())+ ":" + returnMinutes(getStartTime()) +
				"-" +  returnHour(getEndTime())+ ":" + returnMinutes(getEndTime());
	}
	
	public String toString(){
		return "" + getName()+ " : "+ returnHour(getStartTime())+ ":" + returnMinutes(getStartTime()) +
				"-" +  returnHour(getEndTime())+ ":" + returnMinutes(getEndTime());
	}
	
	public boolean isRecurring(){
			if(this.group.contains(this.now)){
				return true;
			}
			else
				return false;
	}
	
	public int compareTo(Event other) {
		return start.compareTo(other.start);
	}
	
	public boolean conflictsWith(Event other)
	{
		if (start.compareTo(other.start) < 0)
			return (end.compareTo(other.start) > 0);
		else
			return (start.compareTo(other.end) < 0);
	}

	public boolean containsTime(Date time) {
		return (start.compareTo(time) >= 0 &&
				end.compareTo(time) <= 0);
		
	public boolean isStatic(){
		if(this.group.contains(this.now)){
			return false;
		}
		else
			return true;
	}
	
	//public
	
	public LinkedList<Event> recurrenceGroup(ArrayList<Event> a){
		this.group=new LinkedList<Event>(); 
		int index=0;
		for(int j=0;j<a.size();j++){
		Event aEvent=a.get(j);
		for(int i=0;i<a.size();i++){
			if(aEvent.getName().equals(a.get(i).getName())){
				Integer a1=aEvent.getStartTime();
				Integer a2=a.get(i).getStartTime();
				if(a1.compareTo(a2)==0){
					Integer b1=aEvent.getStartTime();
					Integer b2=a.get(i).getStartTime();
					if(b1.compareTo(b2) ==0){
						group.add(index, a.get(i));
						index++;
					}
				}
			}
		}
		}
		return this.group;
		
	}
	
	
	/****************************** EN PROCESO *********************************************/
	int ERROR = 2;
	int SAME = 1;
	int NOT_SAME = 0;
	
	public String insertTime(String date, String hour, String mins) {
		String dateTime = date + " " + format(hour, mins);
		return dateTime;
	}
	
	public String format(String strHour, String strMinutes) {
		if (strHour.length() == 1)
			strHour = "0" + strHour;
		if (strHour.length() != 2)
			return "ERROR";
		if (strMinutes.length() == 1)
			strMinutes = "0" + strMinutes;
		if (strMinutes.length() != 2)
			return "ERROR";
		return strHour + ":" + strMinutes;
	}
	public String format(String strYear, String strMonth, String strDay,
			String strHour, String strMinutes) {
		if (strYear.length() != 4)
			return "ERROR";
		if (strMonth.length() == 1)
			strMonth = "0" + strMonth;
		if (strMonth.length() != 2)
			return "ERROR";
		if (strDay.length() == 1)
			strDay = "0" + strDay;
		if (strDay.length() != 2)
			return "ERROR";
		if (strHour.length() == 1)
			strHour = "0" + strHour;
		if (strHour.length() != 2)
			return "ERROR";
		if (strMinutes.length() == 1)
			strMinutes = "0" + strMinutes;
		if (strMinutes.length() != 2)
			return "ERROR";
		return strDay + "/" + strMonth + "/" + strYear + " " + strHour + ":"
				+ strMinutes;
	}
	public String format(int hour, int minutes) {
		String strHour = hour + "";
		if (strHour.length() == 1)
			strHour = "0" + strHour;
		if (strHour.length() != 2)
			return "ERROR";
		String strMinutes = minutes + "";
		if (strMinutes.length() == 1)
			strMinutes = "0" + strMinutes;
		if (strMinutes.length() != 2)
			return "ERROR";
		return strHour + ":" + strMinutes;
	}
	

	/****************************** DATE *********************************************/

	/* validate the format of the entry */
	/* year */
	public int convertYear(String pYear) {
		try {
			int year = Integer.parseInt(pYear);
			return year;
		} catch (Exception e) {
			return 0;
		}
	}

	/* month: [1..12] */
	public int convertMonth(String pMonth) {
		try {
			int month = Integer.parseInt(pMonth);
			if (month < 1 || month > 12)
				return 0;
			return month;
		} catch (Exception e) {
			return 0;
		}
	}

	/* day: [1..31] depends of the month and year */
	public int convertDay(String pYear, String pMonth, String pDay) {
		try {
			int year = convertYear(pYear);
			if (year == 0)
				return 0;
			int month = convertMonth(pMonth);
			if (month == 0)
				return 0;
			int day = Integer.parseInt(pDay);
			int daysOfMonth = getDaysOfMonths(pYear, pMonth);
			if (daysOfMonth > 0) {
				if (day >= 1 && day <= daysOfMonth)
					return day;
				return 0;
			}
			return 0;

		} catch (Exception e) {
			return 0;
		}
	}

	public int getDaysOfMonths(String pYear, String pMonth) {
		try {
			int year = convertYear(pYear);
			if (year == 0)
				return 0;
			int month = convertMonth(pMonth);
			if (month == 0)
				return 0;
			/* january, march, may, july, august,october, december */
			if (month == 1 || month == 3 || month == 5 || month == 7
					|| month == 8 || month == 10 || month == 12)
				return 31;
			/* april, june,september,november */
			if (month == 4 || month == 6 || month == 9 || month == 11)
				return 30;
			/* february */
			if (month == 2 && isLeap(pYear))
				return 29;
			if (month == 2 && !isLeap(pYear))
				return 28;
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	public boolean isLeap(String pYear) {
		try {
			int year = convertYear(pYear);
			if (year == 0)
				return false;
			/* divisible per 4 but not divisible per 100 */
			if (year % 4 == 0 && year % 100 != 0)
				return true;
			return false;

		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * validate if is the same date(day/month/year) OUTPUT: 0 not is the same
	 * date 1 is the same date 2 error
	 */
	public int isSameDate(String year1, String month1, String day1,
			String year2, String month2, String day2) {
		// convert is correct
		int nYear1 = convertYear(year1);
		int nYear2 = convertYear(year2);
		int nMonth1 = convertMonth(month1);
		int nMonth2 = convertMonth(month2);
		int nDay1 = convertDay(year1, month1, day1);
		int nDay2 = convertDay(year2, month2, day2);
		if (nYear1 == 0 || nYear2 == 0 || nMonth1 == 0 || nMonth2 == 0
				|| nDay1 == 0 || nDay2 == 0)
			return ERROR;
		if (nYear1 == nYear2 && nMonth1 == nMonth2 && nDay1 == nDay2)
			return SAME;
		return NOT_SAME;
	}

	/*
	 * validate if is the date dd_1/mm_1/yyyy_1 > dd_2/mm_2/yyyy_2 OUTPUT: 0
	 * dd_1/mm_1/yyyy_1 IS BEFORE dd_2/mm_2/yyyy_2 1 dd_1/mm_1/yyyy_1 AFTER
	 * dd_2/mm_2/yyyy_2 2 error
	 */
	public int isBeforeAfterDate(String year1, String month1, String day1,
			String year2, String month2, String day2) {
		int BEFORE = 0;
		int AFTER = 1;
		// convert is correct
		int nYear1 = convertYear(year1);
		int nYear2 = convertYear(year2);
		int nMonth1 = convertMonth(month1);
		int nMonth2 = convertMonth(month2);
		int nDay1 = convertDay(year1, month1, day1);
		int nDay2 = convertDay(year2, month2, day2);
		if (nYear1 == 0 || nYear2 == 0 || nMonth1 == 0 || nMonth2 == 0
				|| nDay1 == 0 || nDay2 == 0)
			return ERROR;

		if (nYear1 < nYear2)
			return BEFORE;
		if (nYear1 > nYear2)
			return AFTER;
		if (nMonth1 < nMonth2)
			return BEFORE;
		if (nMonth1 > nMonth2)
			return AFTER;
		if (nDay1 < nDay2)
			return BEFORE;
		if (nDay1 > nDay2)
			return AFTER;

		return ERROR;
	}

	/* add days to a date : return a date with the format : dd/mm/yyyy */
	public String addDays(String year, String month, String day, int daysToAdd) {
		String date = format(year, month, day);
		for (int i = 0; i < daysToAdd; i++) {
			date = format(year, month, day);
			if (date.equals("ERROR"))
				return "ERROR";
			date = addDay(year, month, day);
			year = getYearFromFormat(date);
			month = getMonthFromFormat(date);
			day = getDayFromFormat(date);
		}
		return date;
	}

	/* add day to a date : return a date with the format : dd/mm/yyyy */
	public String addDay(String year, String month, String day) {
		int nYear = convertYear(year);
		int nMonth = convertMonth(month);
		int nDay = convertDay(year, month, day);
		if (nYear == 0 || nMonth == 0 || nDay == 0)
			return "ERROR";
		/* depends of the month and day */
		/* last day of the year */
		if (nMonth == 12 && getDaysOfMonths(year, month) == nDay) {
			nYear++;
			nMonth = 1;
			nDay = 1;
		} else {
			/* last day of the month */
			if (getDaysOfMonths(year, month) == nDay) {
				nMonth++;
				nDay = 1;
			} else {
				nDay++;
			}
		}
		return format(nYear, nMonth, nDay);

	}

	public String format(String strYear, String strMonth, String strDay) {
		if (strYear.length() != 4)
			return "ERROR";
		if (strMonth.length() == 1)
			strMonth = "0" + strMonth;
		if (strMonth.length() != 2)
			return "ERROR";
		if (strDay.length() == 1)
			strDay = "0" + strDay;
		if (strDay.length() != 2)
			return "ERROR";
		return strDay + "/" + strMonth + "/" + strYear;
	}

	public String format(int year, int month, int day) {
		String strYear = year + "";
		if (strYear.length() != 4)
			return "ERROR";
		String strMonth = month + "";
		if (strMonth.length() == 1)
			strMonth = "0" + strMonth;
		if (strMonth.length() != 2)
			return "ERROR";
		String strDay = day + "";
		if (strDay.length() == 1)
			strDay = "0" + strDay;
		if (strDay.length() != 2)
			return "ERROR";
		return strDay + "/" + strMonth + "/" + strYear;
	}

	// return yyyy from dd/mm/yyyy hh:mm
	public String getYearFromFormat(String datetime) {
		String date = datetime.substring(0, 10);
		// if(date.length() == 10){
		String year = date.substring(6, date.length());
		int nYear = convertYear(year);
		if (nYear == 0)
			return "ERROR";
		return year;
		// }
		// return "ERROR";
	}

	// return mm from dd/mm/yyyy hh:mm
	public String getMonthFromFormat(String datetime) {
		String date = datetime.substring(0, 10);
		// if(date.length() == 10){
		String month = date.substring(3, 5);
		int nMonth = convertMonth(month);
		if (nMonth == 0)
			return "ERROR";
		return month;
		// }
		// return "ERROR";
	}

	// return dd from dd/mm/yyyy hh:mm
	public String getDayFromFormat(String datetime) {
		String date = datetime.substring(0, 10);
		// if(date.length() == 10){
		String year = getYearFromFormat(date);
		String month = getMonthFromFormat(date);
		String day = date.substring(0, 2);
		int nDay = convertDay(year, month, day);
		if (nDay == 0)
			return "ERROR";
		return day;
		// }
		// return "ERROR";
	}

	public int getNumberWeekday(String year, String month, String day) {
		// convert is correct
		int nYear = convertYear(year);
		int nMonth = convertMonth(month);
		int nDay = convertDay(year, month, day);
		if (nYear == 0 || nMonth == 0 || nDay == 0)
			return ERROR;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, nDay);
		cal.set(Calendar.MONTH, nMonth - 1);
		cal.set(Calendar.YEAR, nYear);
		return cal.get(Calendar.DAY_OF_WEEK);
	}
}
