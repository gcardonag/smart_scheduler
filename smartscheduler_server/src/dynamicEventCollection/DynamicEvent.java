/* @deprecated annotations mean that the class or 
 * method may need revision, not that it should not be used.
 */
package dynamicEventCollection;

import java.util.Calendar;
import eventCollection.*;

/** Class that represents a dynamic event which contains
 * methods and variables to store the state of its scheduling.
 * 
 * @author Anthony Llanos Velazquez
 */
public abstract class DynamicEvent extends Event {

	/** The time in minutes to schedule for this event in a period. */
	protected int time ;
	
	/** The time left for scheduling in a particular period. */
	protected int timeLeft ;
	
	/** Represents the time in minutes of the dynamic event that 
	 * hasn't been scheduled from past periods.*/
	protected int unscheduledTime;
	
	/**
	 * Initializes a dynamic event based its name, start date, end date,
	 * hours, and minutes.
	 * @param name the name of the event.
	 * @param start the start date of the event
	 * @param end the end date of the event.
	 * @param hours an integer that denotes how many hours on its recurrence and interval.
	 * @param minutes an integer that denotes how many minutes on its recurrence and interval.
	 */
	public DynamicEvent(String name, Calendar start, Calendar end, int hours, int minutes) {
		super(name, start, end, false, true);
		this.time = (hours * 60) + minutes ;
		this.unscheduledTime = 0 ;
		this.timeLeft = 0 ;
	}
	
	/**Returns whether this event has time left for
	 * scheduling.
	 * @return a boolean value. True if time available, false
	 * otherwise.
	 */
	public boolean hasTimeLeft(){
		return (getTimeLeft() > 0 || this.unscheduledTime > 0) ;
	}
	
	/**Returns in minutes how much time is left 
	 * to schedule for this dynamic event.
	 * @return the number of minutes left for scheduling.
	 */
	public int getTimeLeft(){
		return this.timeLeft ;
	}
	
	
	/**Reduces the amount of time left to schedule for
	 * this event.
	 * @param timeDelta - the amount of time left to schedule.
	 */
	public void reduceTime(int timeDelta){
		this.timeLeft -= timeDelta ;
	}
	
	/**
	 * Resets the time left for this event to
	 * allow for reschedule.
	 */
	public void resetTime(){
		this.timeLeft = this.time + this.timeLeft + this.unscheduledTime ;
		unscheduledTime = 0 ;
	}
	
	/**
	 * Resets the time left for this event to
	 * allow for reschedule.
	 */
	public void resetUnscheduledTime(){
		this.timeLeft = this.timeLeft + unscheduledTime ;
		unscheduledTime = 0 ;
	}
	
	/**
	 * Zero the time available for scheduling for this event.
	 */
	public void zeroTime(){
		unscheduledTime += this.timeLeft ;
		this.timeLeft = 0 ;
	}
	
	/**Determines whether this dynamic event occurs on a given
	 * date by inspecting its start date, end date, the recurrence
	 * type and the recurrence interval.
	 * @return true if it takes place on the given date, false otherwise.
	 */
	public boolean takesPlaceOnDate(Calendar theDay){
		if(!this.containsDay(theDay))
			return false ;
		
		RecurrenceGroup rg = this.getRecurrenceGroup() ;
	
		if(rg == null)
			return false ;
		
		if(rg.getRecurrence() == RecurrenceGroup.DAILY){
			
			//Simple Daily Occurrence.
			if(rg.getInterval() == 1) 
				return true ;
			
			//Interval Daily Occurrence.
			Calendar cur = (Calendar) this.getStart().clone() ;
			this.getStart().getTime() ;  // Refresh. For some reason needed.
			
			//TODO: Verify compareTo.
			while(cur.compareTo(theDay) < 0){
				cur.add(Calendar.DAY_OF_MONTH, rg.getInterval());
			}
			
			return (cur.get(Calendar.DAY_OF_MONTH) == theDay.get(Calendar.DAY_OF_MONTH));
			
			
		}
		else if(rg.getRecurrence() == RecurrenceGroup.WEEKLY){
			
			int interval = rg.getInterval() ;
			
			//Simple Weekly Recurrence (Every Week).
			if(interval == 1)
				return rg.occursOnWeekday(theDay.get(Calendar.DAY_OF_WEEK)) ;
			
			
			//Interval Weekly Recurrence.
			Calendar cursor = (Calendar) this.getStart().clone();
			
			Calendar cursorStartOfWeek = (Calendar) cursor.clone();
			Calendar cursorEndOfWeek = (Calendar) cursor.clone();
			
			cursorStartOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			
			cursorEndOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
			cursorEndOfWeek.set(Calendar.HOUR_OF_DAY, 23);
			cursorEndOfWeek.set(Calendar.MINUTE, 59);
			cursorEndOfWeek.set(Calendar.SECOND, 00);
			
			while(true){
				if(this.compareDayWithRange(theDay, cursorStartOfWeek, cursorEndOfWeek) > 0) {
					//Add a weekly interval to determine whether it happens in the next one.
					cursorStartOfWeek.add(Calendar.WEEK_OF_YEAR, interval);
					cursorEndOfWeek.add(Calendar.WEEK_OF_YEAR, interval);
				} 
				else if(this.compareDayWithRange(theDay, cursorStartOfWeek, cursorEndOfWeek) == 0) {	
					//Event occurs on the given day's week. Now determine if it 
					//occurs on the given day of week.
					return rg.occursOnWeekday(theDay.get(Calendar.DAY_OF_WEEK));
				} 
				else{ 
					//Means the day does not fall in the event's schedule. The event
					//does not occur on this day.
					return false;
				}
			}
		}
		else if(rg.getRecurrence() == RecurrenceGroup.MONTHLY){
			
			int interval = rg.getInterval() ;
			
			//Simple Monthly Ocurrence.
			if(interval == 1)
				return (this.getStart().get(Calendar.DAY_OF_MONTH) 
						== theDay.get(Calendar.DAY_OF_MONTH)) ;
			
			//Interval Monthly Occurrence.
			Calendar cursorMonth = (Calendar) this.getStart().clone();
			this.getStart().getTime(); //Refresh. For some reason needed.
			
			while(cursorMonth.get(Calendar.MONTH) < theDay.get(Calendar.MONTH))
				cursorMonth.add(Calendar.MONTH, interval);
			
			if (theDay.get(Calendar.MONTH) == cursorMonth.get(Calendar.MONTH)) {
				return (this.getStart().get(Calendar.DAY_OF_MONTH) 
						== theDay.get(Calendar.DAY_OF_MONTH));
			} 
			else
				return false;	
		}
		else{
			//Expansion.
		}
		
		return false ;
	}

	/**Determines whether the given day is in the range of this
	 * event's schedule.
	 * @param day - the day to check.
	 * @return true if is within range of schedule, false otherwise.
	 */
	public boolean containsDay(Calendar day){
		
		int dyear = day.get(Calendar.YEAR) ;
		int dmonth = day.get(Calendar.MONTH);
		int dday = day.get(Calendar.DAY_OF_YEAR) ;
		
		int syear = this.getStart().get(Calendar.YEAR) ;
		int smonth = this.getStart().get(Calendar.MONTH);
		int sday = this.getStart().get(Calendar.DAY_OF_YEAR) ;
		
		int eyear = this.getEnd().get(Calendar.YEAR) ;
		int emonth = this.getEnd().get(Calendar.MONTH);
		int eday = this.getEnd().get(Calendar.DAY_OF_YEAR) ;
		
		if( dyear >= syear && dmonth >= smonth && dday >= sday){
			if(dyear <= eyear && dmonth <= emonth && dday <= eday){
				return true ;
			}
		}
		return false ;
		
	}
	
	/**Determines whether a day occures before, during, or after a
	 * given date range.
	 * @param day - the day
	 * @param start -  the start of the date range.
	 * @param end - the end of the date range.
	 * @return 0 if day is in range, -1 if is before range, +1 if after.
	 * @deprecated Verify when start > end.
	 */
	private int compareDayWithRange(Calendar day, Calendar start, Calendar end){
		
		//TODO: Exception if start < end.
		
		int dyear = day.get(Calendar.YEAR) ;
		int dmonth = day.get(Calendar.MONTH);
		int dday = day.get(Calendar.DAY_OF_YEAR) ;
		
		int syear = start.get(Calendar.YEAR) ;
		int smonth = start.get(Calendar.MONTH);
		int sday = start.get(Calendar.DAY_OF_YEAR) ;
		
		int eyear = end.get(Calendar.YEAR) ;
		int emonth = end.get(Calendar.MONTH);
		int eday = end.get(Calendar.DAY_OF_YEAR) ;
		
		if( dyear >= syear && dmonth >= smonth && dday >= sday){
			if(dyear <= eyear && dmonth <= emonth && dday <= eday){
				return 0 ;
			}
			else{
				return 1 ;
			}
		}
		return -1 ;	
	}
	
	/**Determines the first day of the week in which an event occurs.
	 * @param day
	 * @return
	 */
	public int firstDayOfWeekWithRespectToDate(Calendar day){
	
		RecurrenceGroup rg = this.getRecurrenceGroup() ;
		if(!(rg.getRecurrence() == RecurrenceGroup.WEEKLY))
			return  -1 ;
		
		Calendar start = (Calendar) this.start.clone() ;
		Calendar end = (Calendar) start.clone() ;
		
		while(end.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY){
			end.add(Calendar.DAY_OF_YEAR, 1);
		}
		
		int occursInStart = compareDayWithRange(day, start, end) ;
		
		if(occursInStart < 0){
			return -1 ;
		}
		else if(occursInStart == 0){
			int first = start.get(Calendar.DAY_OF_WEEK);
			while(!rg.occursOnWeekday(first)){
				first++ ;
				if(first > Calendar.SATURDAY){
					return -1 ;
				}
			}
			return first ;
		}
		else{
			
			int result = -1 ;
			if(rg.occursOnWeekday(Calendar.SUNDAY)){
				result = Calendar.SUNDAY ;
			}
			else if(rg.occursOnWeekday(Calendar.MONDAY)){
				result = Calendar.MONDAY ;
			}
			else if(rg.occursOnWeekday(Calendar.TUESDAY)){
				result = Calendar.TUESDAY ;
			}
			else if(rg.occursOnWeekday(Calendar.WEDNESDAY)){
				result = Calendar.WEDNESDAY ;
			}
			else if(rg.occursOnWeekday(Calendar.THURSDAY)){
				result = Calendar.THURSDAY ;
			}
			else if(rg.occursOnWeekday(Calendar.FRIDAY)){
				result = Calendar.FRIDAY ;
			}
			else{
				result = Calendar.SATURDAY ;
			}
			
			return result ;
		}
		
	}
	
	/* (non-Javadoc)
	 * @see eventCollection.Event#toString()
	 */
	public String toString(){
		String str = "SmartScheduler.DynamicEvent{"
				+ " 'ID': " + super.getId()  
				+ ", 'Name' : " + super.getName()
				+ ", 'CalendarRange' : [ "+ start.getTime().toString()  
				+ " - " + end.getTime().toString() + " ]"
				+ ", 'Time' : '" + this.time + "',"  
				+ ", 'TimeLeft' : '" + this.timeLeft
				+ "}" ;
		
		return str;
	}
	
}
